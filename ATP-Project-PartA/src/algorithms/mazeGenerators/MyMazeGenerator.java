package algorithms.mazeGenerators;

/**
 * The class MyMazeGenerator extends the abstract class AMazeGenerator.
 * This class implements generate function and creates a maze according to the iterative implementation
 * of the randomized depth-first search algorithm.
 */
public class MyMazeGenerator extends AMazeGenerator {
    /**
     * @param numOfRows, The amount of rows in the maze that created.
     * @param numOfCols, The amount of columns in the maze that created.
     * @return Maze, maze created according to the algorithm of randomized dfs.
     */
    @Override
    public Maze generate(int numOfRows, int numOfCols) {
        //Create a maze with simple maze generator with default size if some of the parameters are incorrect.
        if(numOfRows <=0 || numOfCols <= 0 || (numOfRows == 1 && numOfCols == 1)) {
            numOfRows = 2;
            numOfCols = 2;
            SimpleMazeGenerator simpleMazeGen = new SimpleMazeGenerator();
            Maze simpleMaze = simpleMazeGen.generate(numOfRows, numOfCols);
            return simpleMaze;
        }
        int [][] mazeArr = new int[numOfRows][numOfCols];
        int randomStartRow = (numOfRows - 1) * (int)Math.random();
        int randomGoalRow = (numOfRows - 1) * (int)Math.random();
        //If the start and goal positions are equal choose randomly again until different numbers are achieved.
        if (numOfCols ==1 && randomStartRow == randomGoalRow){
            while (randomStartRow == randomGoalRow)
            {
                randomStartRow = (numOfRows - 1) * (int)Math.random();
                randomGoalRow = (numOfRows - 1) * (int)Math.random();
            }
        }
        //Set the starting position of the maze on the left wall.
        //Set the goal position of the maze on the right wall.
        Position startPosition = new Position(randomStartRow,0);
        Position goalPosition = new Position(randomGoalRow, numOfCols - 1);

        //


    }
}





































