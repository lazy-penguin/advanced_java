import generated_classes.Node;
import generated_classes.Osm;
import org.apache.commons.compress.compressors.CompressorException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Parser {
    private final String _path;
    Unmarshaller _u;
    XMLEventReader _reader;

    public Parser(String path) throws JAXBException {
        _path = path;
        JAXBContext jc = JAXBContext.newInstance("generated_classes");
        _u = jc.createUnmarshaller();
    }

    private void openNewStream() throws FileNotFoundException, CompressorException, XMLStreamException {

        var bufferedStream = new Decompresser().Decompress(getClass().getResource(_path).getPath());
        _reader = XMLInputFactory.newInstance().createXMLEventReader(bufferedStream);

    }

    public List<Node> getNodes() throws JAXBException, FileNotFoundException, XMLStreamException, CompressorException {
        openNewStream();

        JAXBElement<Osm> element = _u.unmarshal(_reader, Osm.class);
        return new ArrayList<>(element.getValue().getNode());
    }

    public Map<String, Integer> countUserEdits() throws FileNotFoundException, XMLStreamException, CompressorException, JAXBException {
        openNewStream();

        var map = new HashMap<String, Integer>();
        JAXBElement<Osm> element = _u.unmarshal(_reader, Osm.class);
        for (var node: element.getValue().getNode()) {
            var user = node.getUser();
            int count = 0;
            if (map.containsKey(user))
                count = map.get(user);

            map.put(user, ++count);
        }

        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
