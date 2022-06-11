package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class of maze that represents a two-dimensional maze.
 * Start position represents the starting point of the maze.
 * Goal position represents the exit point from the maze.
 * The purpose of the maze is to find a path of a solution that starts at the starting pos and ends at the goal pos.
 * The maze is represented by the two-dimensional array so that the number 1 represents a wall and the number 0 represents an empty space.
 */
public class Maze implements Serializable {
    private Position startPosition;
    private Position goalPosition;
    private int[][] mazeArray;

    /**
     * Constructor of maze class.
     * @param startPos, the entrance point of the maze.
     * @param goalPos, the exit point of the maze
     * @param mazeArray , 2D array of 0 and 1 positions.
     */
    public Maze(Position startPos, Position goalPos, int[][] mazeArray)
    {
        this.startPosition = startPos;
        this.goalPosition = goalPos;
        this.mazeArray = mazeArray;
    }

    /**
     * Constructor
     * The constructor receives an array of bytes that is not compressed and uses it to build Maze
     * according to the format we use to initialize the array.
     * @param mazeByteArray the array that containing all the details about the maze.
     */
    public Maze(byte[] mazeByteArray)
    {
        int rowsNum = this.fromByteToInt(mazeByteArray[0], mazeByteArray[1]);
        int colsNum = this.fromByteToInt(mazeByteArray[2], mazeByteArray[3]);
        //Init the maze array with the correct sizes and fill all the positions with the correct values.
        this.mazeArray = new int[rowsNum][colsNum];
        //The index in the byte array from which the values of the maze positions begin.
        int byteArrayIndex = 12;
        for (int row=0; row < rowsNum; row++)
        {
            for (int col=0; col < colsNum; col++)
            {
                mazeArray[row][col] = this.fromByteToInt(mazeByteArray[byteArrayIndex]);
                byteArrayIndex++;
            }
        }

        //Set start position
        int startPosRow = this.fromByteToInt(mazeByteArray[4], mazeByteArray[5]);
        int startPosCol = this.fromByteToInt(mazeByteArray[6], mazeByteArray[7]);
        Position newStartPos = new Position(startPosRow, startPosCol);
        this.startPosition = newStartPos;
        //Set Goal position
        int goalPosRow = this.fromByteToInt(mazeByteArray[8], mazeByteArray[9]);
        int goalPosCol = this.fromByteToInt(mazeByteArray[10], mazeByteArray[11]);
        Position newGoalPos = new Position(goalPosRow, goalPosCol);
        this.goalPosition = newGoalPos;
    }

    /**
     * Sets the start position to be the position given in the input.
     * @param startPos, start position
     */
    public void setStartPosition(Position startPos)
    {
        int newRowIndex;
        int newColIndex;
        if(startPos.getRowIndex() < 0) {
            startPos.setRowIndex(0);
        }
        if(startPos.getColumnIndex() < 0) {
            startPos.setColumnIndex(0);
        }
        if(startPos.getRowIndex() > this.getNumOfRows() - 1) {
            startPos.setRowIndex(this.getNumOfRows() - 1);
        }
        if(startPos.getColumnIndex() > this.getNumOfCols() - 1) {
            startPos.setColumnIndex(this.getNumOfCols() - 1);
        }

        newRowIndex = startPos.getRowIndex();
        newColIndex = startPos.getColumnIndex();
        this.startPosition.setRowIndex(newRowIndex);
        this.startPosition.setColumnIndex(newColIndex);
    }

    /**
     * Sets the goal position to be the position given in the input.
     * @param goalPos, goal position
     */
    public void setGoalPosition(Position goalPos)
    {
        int newRowIndex;
        int newColIndex;
        if(goalPos.getRowIndex() < 0) {
            goalPos.setRowIndex(0);
        }
        if(goalPos.getColumnIndex() < 0) {
            goalPos.setColumnIndex(0);
        }
        if(goalPos.getRowIndex() > this.getNumOfRows() - 1) {
            goalPos.setRowIndex(this.getNumOfRows() - 1);
        }
        if(goalPos.getColumnIndex() > this.getNumOfCols() - 1) {
            goalPos.setColumnIndex(this.getNumOfCols() - 1);
        }

        newRowIndex = goalPos.getRowIndex();
        newColIndex = goalPos.getColumnIndex();
        this.goalPosition.setRowIndex(newRowIndex);
        this.goalPosition.setColumnIndex(newColIndex);
    }

    /**
     * Sets the value of the specific position in the maze to the new value.
     * @param position, the position we want to set.
     * @param newValue, the new value for this position.
     */
    public void setPosInMaze(Position position, int newValue)
    {
        if(this.isPosExist(position) && (newValue == 0 || newValue == 1)) {
            this.mazeArray[position.getRowIndex()][position.getColumnIndex()] = newValue;
        }
    }

    /**
     * Returns whether such a Position exists in the maze.
     * @param p
     * @return
     */
    public boolean isPosExist(Position p)
    {
        if(p.getRowIndex() < 0 || p.getRowIndex() > this.getNumOfRows() - 1 || p.getColumnIndex() < 0 ||
                 p.getColumnIndex() > this.getNumOfCols() - 1) {
            return false;
        }
        return true;
    }

    /**
     * @return the start position of the maze.
     */
    public Position getStartPosition()
    {
        return this.startPosition;
    }

    /**
     * @return the goal position of the maze.
     */
    public Position getGoalPosition()
    {
        return this.goalPosition;
    }

    /**
     * @return the array that represents the maze.
     */
    public int[][] getMazeArray()
    {
        return this.mazeArray;
    }

    /**
     * @return the number of rows in the maze.
     */
    public int getNumOfRows()
    {
        return this.mazeArray.length;
    }

    /**
     * @return the number of columns in the maze.
     */
    public int getNumOfCols()
    {
        return this.mazeArray[0].length;
    }

    /**
     * Prints the maze according to the print format of a maze.
     * Prints the start position with the letter S.
     * Prints the goal position with the letter E.
     */
    public void print()
    {
        int startRow = this.startPosition.getRowIndex();
        int startCol = this.startPosition.getColumnIndex();
        int goalRow =  this.goalPosition.getRowIndex();
        int goalCol = this.goalPosition.getColumnIndex();
        int row,col;
        String valueToPrint;
        System.out.print("{{");

        for (row=0; row < this.getNumOfRows(); row++)
        {
            if (row > 0){
                System.out.print(" {");
            }
            for (col=0; col < this.getNumOfCols(); col++)
            {
                if (row == startRow && col == startCol){
                    valueToPrint = " S";
                }
                else if (row == goalRow && col == goalCol){
                    valueToPrint = " E";
                }
                else if (this.mazeArray[row][col] == 0){
                    valueToPrint = " 0";
                }
                else {
                    valueToPrint = " 1";
                }
                if (col < this.getNumOfCols() - 1) {
                    valueToPrint += ",";
                }
                System.out.print(valueToPrint);
            }
            if (row < this.getNumOfRows() - 1) {
                System.out.println("},");
            }
        }
        System.out.println("}}");
    }

    /**
     * A method that returns an array of bytes that represents all the relevant information about the maze so that it can be restored later.
     * The order in which the elements in the array are kept is:
     * Indexes 0,1 - the num of rows in the maze.
     * Indexes 2,3 - the num of columns in the maze.
     * Indexes 4-7 - the start position of the maze.
     * Indexes 8-11 - the goal position of the maze.
     * Indexes 12-end - all maze positions values.
     * @return the byte array according to the structure described above.
     */
    public byte[] toByteArray()
    {
        ArrayList<Byte> byteArrayList = new ArrayList<>();

        //The sizes of the maze.
        byte[] numOfRows = this.fromIntToByte(this.getNumOfRows());
        byteArrayList.add(numOfRows[0]);
        byteArrayList.add(numOfRows[1]);
        byte[] numOfCols = this.fromIntToByte(this.getNumOfCols());
        byteArrayList.add(numOfCols[0]);
        byteArrayList.add(numOfCols[1]);

        //The starting and goal positions of the maze.
        //Start position
        byte[] startPosRowIndex = this.fromIntToByte(this.startPosition.getRowIndex());
        byteArrayList.add(startPosRowIndex[0]);
        byteArrayList.add(startPosRowIndex[1]);
        byte[] startPosColIndex = this.fromIntToByte(this.startPosition.getColumnIndex());
        byteArrayList.add(startPosColIndex[0]);
        byteArrayList.add(startPosColIndex[1]);
        //Goal position
        byte[] goalPosRowIndex = this.fromIntToByte(this.goalPosition.getRowIndex());
        byteArrayList.add(goalPosRowIndex[0]);
        byteArrayList.add(goalPosRowIndex[1]);
        byte[] goalPosColIndex = this.fromIntToByte(this.goalPosition.getColumnIndex());
        byteArrayList.add(goalPosColIndex[0]);
        byteArrayList.add(goalPosColIndex[1]);

        //Maze array
        for (int row=0; row < this.getNumOfRows(); row++)
        {
            for (int col=0; col < this.getNumOfCols(); col++)
            {
                byteArrayList.add((byte)this.mazeArray[row][col]);
            }
        }

        Byte[] mazeByteArray = byteArrayList.toArray(new Byte[byteArrayList.size()]);
        return this.toPrimitive(mazeByteArray);
    }

    /**
     * Method that cast array of Bytes to an array of bytes.
     * @param mazeByteArray, the array of Bytes we want to turn into an array of bytes (primitive).
     * @return the array of bytes.
     */
    private byte[] toPrimitive(Byte[] mazeByteArray) {
        byte[] output = new byte[mazeByteArray.length];
        for (int i = 0; i < mazeByteArray.length; i++) {
            output[i] = mazeByteArray[i];
        }
        return output;
    }

    /**
     * A method that receives an int number as input and makes it represented by an array of 2 bytes.
     * @param number, the number we want to change to be represented by bytes array.
     * @return byte array representing the number.
     */
    private byte[] fromIntToByte(int number)
    {
        //We can assume that the sizes of the maze (rows, columns) will not be larger than the number 65,535, so 2 bytes are enough to
        //represent them and not 4 as the amount of int.
        byte[] resAfterChange = new byte[2];
        resAfterChange[0] = (byte)(number / 256);
        resAfterChange[1] = (byte)(number % 256);
        return resAfterChange;
    }

    /**
     * Convert the number represented by byte to int.
     * @param number we want to cast.
     * @return int that representing the number.
     */
    private int fromByteToInt(byte number)
    {
        int res = 0;
        res += (((int)number) * 1);
        return res;
    }

    /**
     * Convert the number represented by 2 bytes to int.
     * @param number1 the number is multiplied by 256.
     * @param number2 the number is multiplied by 1.
     * @return int that representing the number.
     */
    private int fromByteToInt(byte number1, byte number2)
    {
        int res = 0;
        res += (((int)number1) * 256) + (((int)number2) * 1);
        return res;
    }

}
