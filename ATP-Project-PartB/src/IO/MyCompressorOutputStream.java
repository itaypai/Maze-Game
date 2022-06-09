package IO;

import java.io.IOException;
import java.io.OutputStream;

/**
 * MyCompressorOutputStream class extends OutputStream and implements a compressor by method of
 * treating every eight binary bytes (o's or 1's) in the uncompressed array as individual bits.
 * Every eight such bits will be represented by a number that will enter the compressed array as a single byte.
 * Such compression will only be done for bytes that are from index 12 to the end, because these represent the contents of the maze.
 */
public class MyCompressorOutputStream extends OutputStream {
    OutputStream out;

    /**
     * Constructor
     * @param out
     */
    public MyCompressorOutputStream(OutputStream out)
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
     * according to the method of MyCompressorOutputStream that treats all eight bytes of the maze as individual bits
     * and compresses them into a single byte represented by a number.
     * All the details of the maze are compressed and sent through the output stream.
     * @param b, an array of bytes containing all the details of the maze.
     * @throws IOException
     */
    @Override
    public void write(byte[] b) throws IOException
    {
        int compressedNum;
        int currIndex = 0;
        while (currIndex < b.length)
        {
            if (currIndex < 12){
                this.write(b[currIndex]);
                currIndex++;
            }
            else{
                compressedNum = this.fromBytesToByte(b, currIndex);
                this.write(compressedNum);
                currIndex = currIndex + 8;
            }
        }
    }

    /**
     * A method that converts all 8 bytes in the uncompressed array into a single byte.
     * @param b, uncompressed array of bytes containing all the details of the maze.
     * @param currIndex, the index from which the current eight begins.
     * @return the single byte after compressing.
     */
    private int fromBytesToByte(byte[] b, int currIndex)
    {
        int compressedNum = 0;
        int index = 0;
        //Check if we have enough bytes to compress another eight.
        if (currIndex + 8 <= b.length){
            for (int byteCount = 0; byteCount < 8; byteCount++)
            {
                compressedNum += b[currIndex] * Math.pow(2, 7 - byteCount);
                currIndex++;
            }
        }
        //The last compression in which the number of remaining bytes is less than 8.
        else{
            while (currIndex != b.length)
            {
                compressedNum += b[currIndex] * Math.pow(2, 7 - index);
                currIndex++;
                index++;
            }
        }
        return compressedNum;
    }
}





