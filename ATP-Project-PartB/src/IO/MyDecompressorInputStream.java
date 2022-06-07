package IO;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 */
public class MyDecompressorInputStream extends InputStream {
    InputStream in;

    /**
     * @param in
     */
    public MyDecompressorInputStream(InputStream in)
    {
        this.in = in;
    }

    /**
     * @return
     * @throws IOException
     */
    @Override
    public int read() throws IOException
    {
        return in.read();
    }

    /**
     * @param b
     * @return
     * @throws IOException
     */
    @Override
    public int read(byte[] b) throws IOException
    {

    }
}
