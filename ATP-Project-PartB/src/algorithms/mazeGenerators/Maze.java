package algorithms.mazeGenerators;

/**
 * A class of maze that represents a two-dimensional maze.
 * Start position represents the starting point of the maze.
 * Goal position represents the exit point from the maze.
 * The purpose of the maze is to find a path of a solution that starts at the starting pos and ends at the goal pos.
 * The maze is represented by the two-dimensional array so that the number 1 represents a wall and the number 0 represents an empty space.
 */
public class Maze {
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

}
