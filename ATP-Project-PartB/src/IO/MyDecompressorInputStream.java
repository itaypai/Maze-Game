package IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Stack;

/**
 * The class MyDecompressorInputStream extends InputStream.
 * This class adds the functionality of decompressing of the input according to the method
 * of my compression we have implemented in the class MyCompressorOutputStream.
 */
public class MyDecompressorInputStream extends InputStream {
    InputStream in;

    /**
     * Constructor
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
     * Extract the information of the input through the input stream into array b according
     * to the method of my compression.
     * @param b, we "inflate" the array with the extracted details.
     * @return
     * @throws IOException
     */
    @Override
    public int read(byte[] b) throws IOException
    {
        int currInput = this.in.read();
        int extractedIndex = 0;
        int numOfRows;
        int numOfCols;
        int size = 0; //The total number of states.
        int count = 0 ; //The number states that has already been extracted.
        Stack<Byte> currEightBytes;
        while (currInput != -1)
        {
            if (extractedIndex < 12) {
                b[extractedIndex] = (byte) currInput;
                extractedIndex++;
                if (extractedIndex == 11){
                    numOfRows = (((int)b[0]) * 256) + ((int)b[1]);
                    numOfCols = (((int)b[2]) * 256) + ((int)b[3]);
                    size = numOfRows * numOfCols;
                }
            }
            else{
                currEightBytes = this.fromNumToBytes(currInput);
                if (count + 8 <= size){
                    while (!currEightBytes.isEmpty())
                    {
                        b[extractedIndex] = currEightBytes.pop();
                        extractedIndex++;
                    }
                    count += 8;
                }
                //Pop from the last eight the amount of elements left to get all the values of the states.
                else{
                    for (int i=0; i < (size - count); i++)
                    {
                        b[extractedIndex] = currEightBytes.pop();
                        extractedIndex++;
                    }
                }
            }
            currInput = this.in.read();
        }
        return extractedIndex;
    }

    /**
     * A method that converts each byte represented by a number in the compressed array into 8 bytes that
     * represent the contents of the states.
     * @param compressedNum, the compressed number we want to extract and turn into 8 bytes.
     * @return, returns an eight-size stack that contains the eight bytes that represent the number received in the input.
     */
    private Stack<Byte> fromNumToBytes(int compressedNum)
    {
        Stack<Byte> res= new Stack<>();
        int currStackSize = 0;
        while (compressedNum >= 1)
        {
            res.push((byte)(compressedNum % 2));
            compressedNum = compressedNum / 2;
        }
        currStackSize = res.size();
        //If the number is represented by less than 8 bits add zeros to reach size 8.
        for (int i=0; i < (8 - currStackSize); i++)
        {
            res.push((byte)0);
        }
        return res;
    }
}
