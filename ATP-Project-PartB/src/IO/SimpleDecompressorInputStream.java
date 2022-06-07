package IO;

import java.io.IOException;
import java.io.InputStream;

/**
 * The class SimpleDecompressorInputStream extends InputStream.
 * This class adds the functionality of decompressing of the input according to the method
 * of simple compression we have implemented in the class SimpleCompressor.
 */
public class SimpleDecompressorInputStream extends InputStream {
    InputStream in;

    /**
     * Constructor
     * @param in
     */
    public SimpleDecompressorInputStream(InputStream in)

    {
        this.in = in;
    }

    /**
     * @return
     * @throws IOException
     */
    @Override
    public int read() throws IOException {
        return in.read();
    }

    /**
     * Extract the information of the input through the input stream into array b according
     * to the method of compression.
     * @param b, we "inflate" the array with the extracted details.
     * @return
     * @throws IOException
     */
    @Override
    public int read(byte[] b) throws IOException {
        int currNumber = 0;
        int extractedIndex = 0;
        int currInput = this.in.read();
        while (currInput != -1)
        {
            if (extractedIndex < 12){
                b[extractedIndex] = (byte)currInput;
                extractedIndex++;
            }
            else {
                for (int j=0; j < currInput; j++)
                {
                    b[extractedIndex] = (byte)currNumber;
                    extractedIndex++;
                }
                currNumber = 1 - currNumber;
            }
            currInput = this.in.read();
        }
        return extractedIndex;
    }
}
