package IO;

import java.io.IOException;
import java.io.OutputStream;

/**
 * SimpleCompressorOutputStream class extends OutputStream and implements a compressor by counting the number of
 * sequences of unity and zeros in the maze.
 */
public class SimpleCompressorOutputStream extends OutputStream {
    OutputStream out;

    /**
     * Constructor
     * @param out
     */
    public SimpleCompressorOutputStream(OutputStream out)
    {
        this.out = out;
    }

    /**
     * @param b
     * @throws IOException
     */
    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    /**
     * An array of bytes containing all the details of the maze. The details of the maze are compressed
     * according to the method of counting the 0's and 1's in the maze.
     * All the details of the maze are compressed and sent through the output stream.
     * @param b, an array of bytes containing all the details of the maze.
     * @throws IOException
     */
    @Override
    public void write(byte[] b) throws IOException
    {
        int sequenceCount = 0;
        for (int i=0; i < b.length; i++)
        {
            if (i < 12){
                this.write(b[i]);
            }
            else{
                if (i == 12){
                    if (b[i] == 1) {
                        this.write(0);
                    }
                    sequenceCount++;
                }
                else {
                    if (b[i] == b[i - 1]){
                        sequenceCount++;
                    }
                    else{
                        while (sequenceCount > 255)
                        {
                            this.write(255);
                            this.write(0);
                            sequenceCount = sequenceCount - 255;
                        }
                        this.write(sequenceCount);
                        sequenceCount = 1;
                    }
                }
            }
        }
        this.write(sequenceCount);
    }
}
