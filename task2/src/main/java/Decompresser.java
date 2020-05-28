import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

import java.io.*;

public class Decompresser {
    public BufferedReader Decompress(String path) throws FileNotFoundException, CompressorException {
        FileInputStream fin = new FileInputStream(path);
        BufferedInputStream bis = new BufferedInputStream(fin);
        CompressorInputStream input = new CompressorStreamFactory().createCompressorInputStream(bis);
        return new BufferedReader(new InputStreamReader(input));
    }
}
