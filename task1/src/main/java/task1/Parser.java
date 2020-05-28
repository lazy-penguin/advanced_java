package task1;

import org.apache.commons.compress.compressors.CompressorException;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Parser {
    private XMLEventReader _reader;
    private final String _path;

    public Parser(String path) {
        _path = path;
    }

    private void openNewStreamReader() throws XMLStreamException, FileNotFoundException, CompressorException {
        var factory = XMLInputFactory.newInstance();
        var bufferedStream = new Decompresser().Decompress(getClass().getResource(_path).getPath());
        _reader = factory.createXMLEventReader(bufferedStream);
    }

    public Map<String, Integer> countUserEdits() throws XMLStreamException, FileNotFoundException, CompressorException {
        var map = new HashMap<String, Integer>();
        openNewStreamReader();

        while(_reader.hasNext()) {
            var event = _reader.nextEvent();
            if (!event.isStartElement())
                continue;

            var startElement = event.asStartElement();
            if (!startElement.getName().getLocalPart().equals("node"))
                continue;

            var userAttr = startElement.getAttributeByName(new QName("user"));
            if (userAttr != null) {
                int count = 0;
                if (map.containsKey(userAttr.getValue()))
                    count = map.get(userAttr.getValue());

                map.put(userAttr.getValue(), ++count);
            }
        }

        return map.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(Collectors.toMap(
                    Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public Map<String, Integer> countUniqueKeys() throws XMLStreamException, FileNotFoundException, CompressorException {
        var map = new HashMap<String, Integer>();
        openNewStreamReader();

        while(_reader.hasNext()) {
            var event = _reader.nextEvent();

            if (!event.isStartElement() || !event.asStartElement().getName().getLocalPart().equals("node"))
                continue;
            _reader.nextEvent();

            while(_reader.hasNext()) {
                var nextEvent = _reader.peek();
                if (!nextEvent.isStartElement())
                    break;

                var startElement = nextEvent.asStartElement();
                if (!startElement.getName().getLocalPart().equals("tag"))
                    break;

                var keyAttr = startElement.getAttributeByName(new QName("k"));
                if (keyAttr != null) {
                    int count = 0;
                    if (map.containsKey(keyAttr.getValue())) {
                        count = map.get(keyAttr.getValue());
                    }
                    map.put(keyAttr.getValue(), ++count);
                }

                _reader.nextEvent();
                if (!_reader.hasNext()) {
                    _reader.nextEvent();
                }
            }
        }

        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
