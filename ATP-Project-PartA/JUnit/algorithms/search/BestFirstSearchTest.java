package algorithms.search;

import algorithms.mazeGenerators.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {
    private BestFirstSearch bestFirstSearchForTest = new BestFirstSearch();

    @Test
    void getName()
    {
        assertEquals("BestFirstSearch", bestFirstSearchForTest.getName());
    }

    @Test
    void nullInputTest() throws Exception{
        assertTrue(bestFirstSearchForTest.solve(null) == null);
    }

    @Test
    public void getNumberOfNodesEvaluatedFirst()
    {
        AMazeGenerator newMyMaze = new MyMazeGenerator();
        Maze newMaze = newMyMaze.generate(6, 6);
        assertEquals(0, bestFirstSearchForTest.getNumberOfNodesEvaluated());
    }

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