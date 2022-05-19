package algorithms.mazeGenerators;

/**
 * The class EmptyMazeGenerator extends the abstract class AMazeGenerator.
 * EmptyMazeGenerator implements the function generate and creates an empty maze of zeros.
 */
public class EmptyMazeGenerator extends AMazeGenerator {
    /**
     * @param numOfRows int, num of rows.
     * @param numOfCols int, num of cols.
     * @return Maze, empty maze of zeros.
     */
    @Override
    public Maze generate(int numOfRows, int numOfCols) {
        //Create a maze with default size if some of the parameters are incorrect.
        if(numOfRows <=0 || numOfCols <= 0 || (numOfRows == 1 && numOfCols == 1)) {
            numOfRows = 10;
            numOfCols = 10;
        }
        //Initialize a 2D array of zeros with the size of the empty maze.
        int [][] emptyMaze = new int[numOfRows][numOfCols];
        for(int i=0; i < numOfRows; i++)
        {
            for(int j=0; j < numOfCols; j++)
            {
                emptyMaze[i][j] = 0;
            }
        }
        //Set the starting position in the upper left corner.
        //Set the goal position in the lower right corner.
        //Initialize the empty maze with the start and end positions.
        Position startPosition = new Position(0,0);
        Position goalPosition = new Position(numOfRows-1, numOfCols-1);
        Maze maze = new Maze(startPosition, goalPosition, emptyMaze);
        return maze;
    }
}
