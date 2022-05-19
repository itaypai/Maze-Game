package algorithms.mazeGenerators;
import java.util.Random;

/**
 *
 */
public class SimpleMazeGenerator extends AMazeGenerator {
    @Override
    public Maze generate(int numOfRows, int numOfCols) {
        //Create a maze with default size if some of the parameters are incorrect.
        if(numOfRows <=0 || numOfCols <= 0 || (numOfRows == 1 && numOfCols == 1)) {
            numOfRows = 10;
            numOfCols = 10;
        }
        int [][] mazeArr = new int[numOfRows][numOfCols];
        int randomStartRow = (int) ((numOfRows - 1) * Math.random());
        int randomGoalRow = (int) ((numOfRows - 1) * Math.random());
        //If the start and goal positions are equal choose randomly again until different numbers are achieved.
        if (numOfCols ==1 && randomStartRow == randomGoalRow){
            while (randomStartRow == randomGoalRow)
            {
                randomStartRow = (int) ((numOfRows - 1) * Math.random());
                randomGoalRow = (int) ((numOfRows - 1) * Math.random());
            }
        }
        //Set the starting position of the maze on the left wall.
        //Set the goal position of the maze on the right wall.
        Position startPosition = new Position(randomStartRow,0);
        Position goalPosition = new Position(randomGoalRow, numOfCols - 1);
        Random r = new Random();
        int randomPositionValue;
        for(int row=0; row < numOfRows; row++)
        {
           for (int col=0; col < numOfCols; col++)
           {
                randomPositionValue = r.nextInt(5);
                if (randomPositionValue % 3 == 0){
                    mazeArr[row][col] = 0;
                }
                //Most of the positions of the maze should be walls.
                //The value of the positions in the maze that represents walls will be equal to 1.
                //By the way it is defined, the probability of getting a wall is greater.
                else{
                    mazeArr[row][col] = 1;
                }
           }
        }
        //Maze initialization according to the start and goal positions and the array that are randomly selected.
        Maze newMaze = new Maze(startPosition, goalPosition, mazeArr);
        //Set the start and goal positions to be zero.
        newMaze.setPosInMaze(newMaze.getStartPosition(),0);
        newMaze.setPosInMaze(newMaze.getGoalPosition(),0);

        int startRow = newMaze.getStartPosition().getRowIndex();
        int startCol = newMaze.getStartPosition().getColumnIndex();
        Position currPosition  = new Position(startRow, startCol);
        this.ensurePossiblePath(newMaze, currPosition);
        return newMaze;
    }

    /**
     * @param newSimpleMaze
     * @param currPosition
     */
    private void ensurePossiblePath(Maze newSimpleMaze, Position currPosition)
    {
        Random random = new Random();
        int randNum;

        while(currPosition.getRowIndex() != newSimpleMaze.getGoalPosition().getRowIndex() ||
            currPosition.getColumnIndex() != newSimpleMaze.getGoalPosition().getColumnIndex())
        {
            randNum = random.nextInt(3);
            //Ensure the move right.
            if (randNum == 0){
                if (currPosition.getColumnIndex() < newSimpleMaze.getGoalPosition().getColumnIndex()){
                    currPosition.setColumnIndex(currPosition.getColumnIndex() + 1);
                }
            }
            //Ensure the move up.
            else if (randNum == 1){
                if (currPosition.getRowIndex() > newSimpleMaze.getGoalPosition().getRowIndex()){
                    currPosition.setRowIndex(currPosition.getRowIndex() - 1);
                }
            }
            //Ensure the move down.
            else{
                if (currPosition.getRowIndex() < newSimpleMaze.getGoalPosition().getRowIndex()){
                    currPosition.setRowIndex(currPosition.getRowIndex() + 1);
                }
            }
            newSimpleMaze.setPosInMaze(currPosition, 0);
        }
    }

}
