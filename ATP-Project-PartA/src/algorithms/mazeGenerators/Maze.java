package algorithms.mazeGenerators;

public class Maze {
    private Position startPosition;
    private Position goalPosition;
    private int[][] mazeArray;

    public Maze(Position startPos, Position goalPos, int[][] mazeArray)
    {
        this.startPosition = startPos;
        this.goalPosition = goalPos;
        this.mazeArray = mazeArray;
    }

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

    public void setPosInMaze(Position position, int newValue)
    {
        if(this.isPosExist(position) && (newValue == 0 || newValue == 1)) {
            this.mazeArray[position.getRowIndex()][position.getColumnIndex()] = newValue;
        }
    }

    public boolean isPosExist(Position p)
    {
        if(p.getRowIndex() < 0 || p.getRowIndex() > this.getNumOfRows() - 1 || p.getColumnIndex() < 0 ||
                 p.getColumnIndex() > this.getNumOfCols() - 1) {
            return false;
        }
        return true;
    }
    public Position getStartPosition()
    {
        return this.startPosition;
    }

    public Position getGoalPosition()
    {
        return this.goalPosition;
    }

    public int[][] getMazeArray()
    {
        return this.mazeArray;
    }

    public int getNumOfRows()
    {
        return this.mazeArray.length;
    }

    public int getNumOfCols()
    {
        return this.mazeArray[0].length;
    }

    public void print()
    {

    }
}
