package task1;
import org.apache.commons.compress.compressors.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(App.class);
        logger.info("Hello World");

        try {
            Parser parser = new Parser("/RU-NVS.osm.bz2");

            parser.countUserEdits().forEach((k, v) ->
                    logger.info("User: " + k + " | Edits count: " + v));

            parser.countUniqueKeys().forEach((k, v) ->
                    logger.info("Key name: " + k + " | Nodes count: " + v));
        }
        catch (IOException | XMLStreamException | CompressorException e) {
            logger.error(e.getMessage());
        }
    }
}
