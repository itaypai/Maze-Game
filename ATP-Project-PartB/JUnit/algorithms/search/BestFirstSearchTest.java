package algorithms.search;

import algorithms.mazeGenerators.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class that tests the functions of best first search class and the functionality of the algorithm.
 */

class BestFirstSearchTest {
    private BestFirstSearch bestFirstSearchForTest = new BestFirstSearch();

    /**
     * Test for getName function.
     */
    @Test
    void getName()
    {
        assertEquals("BestFirstSearch", bestFirstSearchForTest.getName());
    }

    /**
     * Test to the case of receiving null in the input.
     */
    @Test
    void nullInputTest() {
        assertTrue(bestFirstSearchForTest.solve(null) == null);
    }

    /**
     * Test for getNumberOfNodesEvaluated function.
     */
    @Test
    public void getNumberOfNodesEvaluatedFirst()
    {
        AMazeGenerator newMyMaze = new MyMazeGenerator();
        Maze newMaze = newMyMaze.generate(6, 6);
        assertEquals(0, bestFirstSearchForTest.getNumberOfNodesEvaluated());
    }

    /**
     * Another test for getNumberOfNodesEvaluated function.
     */
    @Test
    public void getNumberOfNodesEvaluatedSecond()
    {
        AMazeGenerator newEmptyMaze = new EmptyMazeGenerator();
        Maze maze = newEmptyMaze.generate(2,2);
        ISearchable searchableMaze = new SearchableMaze(maze);
        maze.setGoalPosition(new Position(0,1));
        bestFirstSearchForTest.solve(searchableMaze);
        assertEquals(1, bestFirstSearchForTest.getNumberOfNodesEvaluated());
    }

    /**
     * Tests the time it takes to the algorithm to solve the problem and find a solution to the maze.
     */
    @Test
    public void solve()
    {
        AMazeGenerator myMaze = new MyMazeGenerator();
        Maze newMaze= myMaze.generate(1000,1000);
        ISearchable searchable = new SearchableMaze(newMaze);
        long beforeSolve = System.currentTimeMillis();
        bestFirstSearchForTest.solve(searchable);
        long afterSolve = System.currentTimeMillis();
        assertFalse(afterSolve - beforeSolve > 60000);
    }

}