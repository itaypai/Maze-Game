package algorithms.mazeGenerators;

/**
 * AMazeGenerator is an abstract class that implements the MazeGenerator interface.
 * This class implements measureAlgorithmTimeMillis for all its subclasses.
 */
public abstract class AMazeGenerator implements IMazeGenerator {
/**
 * The function measureAlgorithmTimeMillis returns the time it took to run the generate function and create
 * the maze with size of the number of rows multiplied by the number of columns we received as input.
 * @param numOfRows int, the number of rows of the maze
 * @param numOfCols int, the number of columns of the maze
 * @return long, the time it takes for the generate function to run
 */
    @Override
    public long measureAlgorithmTimeMillis(int numOfRows, int numOfCols) {
        long beforeGenerate;
        long afterGenerate;
        long totalGenerateTime;
        beforeGenerate = System.currentTimeMillis();
        this.generate(numOfRows, numOfCols);
        afterGenerate = System.currentTimeMillis();
        totalGenerateTime = afterGenerate - beforeGenerate;
        return totalGenerateTime;
    }
}
