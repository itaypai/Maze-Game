package algorithms.mazeGenerators;

/**
 * IMazeGenerator is an interface of mazeGenerators. Any generator that implements the interface must implement
 * generate and measureAlgorithmTimeMillis functions.
 */
public interface IMazeGenerator {
    /**
     * @param numOfRows, The amount of rows in the maze that created.
     * @param numOfCols, The amount of columns in the maze that created.
     * @return Maze, the maze created according to the algorithm of the class that implements the interface.
     */
    public Maze generate(int numOfRows, int numOfCols);

    /**
     * @param numOfRows, The amount of rows in the maze that created.
     * @param numOfCols, The amount of columns in the maze that created.
     * @return long that represents the time in milliseconds it took to create the maze
     * with the sizes given to the function.
     */
    public long measureAlgorithmTimeMillis(int numOfRows, int numOfCols);
}
