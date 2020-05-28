import org.apache.commons.compress.compressors.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class App {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(App.class);
//        try {
//            Parser parser = new Parser("/RU-NVS.osm.bz2");
//            parser.countUserEdits().forEach((k, v) ->
//                    logger.info("User: " + k + " | Edits count: " + v));
//        }
//        catch (IOException | XMLStreamException | CompressorException | JAXBException e) {
//            logger.error(e.getMessage());
//        }

        try {
            DatabaseController dc = new DatabaseController();
            Parser parser = new Parser("/RU-NVS.osm.bz2");
            var nodesList = parser.getNodes();

//            dc.clearTable();
            long time = System.currentTimeMillis();
//            dc.loadByExecuteQuery(nodesList);
//            logger.info(String.valueOf(System.currentTimeMillis() - time));

            dc.clearTable();
            time = System.currentTimeMillis();
            dc.loadByPreparedStatement(nodesList);
            logger.info(String.valueOf(System.currentTimeMillis() - time));

            dc.clearTable();
            time = System.currentTimeMillis();
            dc.loadByBatch(nodesList);
            logger.info(String.valueOf(System.currentTimeMillis() - time));
        }
        catch (SQLException | JAXBException | CompressorException | XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
