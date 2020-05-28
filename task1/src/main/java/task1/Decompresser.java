package task1;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

import java.io.*;

public class Decompresser {
    public BufferedReader Decompress(String path) throws FileNotFoundException, CompressorException {
        var fin = new FileInputStream(path);
        var bis = new BufferedInputStream(fin);
        var input = new CompressorStreamFactory().createCompressorInputStream(bis);
        return new BufferedReader(new InputStreamReader(input));
    }
}
