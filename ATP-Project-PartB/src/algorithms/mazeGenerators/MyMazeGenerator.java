package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

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
        //Create a maze with simple maze generator with default size if some parameters are incorrect.
        if (numOfRows <= 0 || numOfCols <= 0 || (numOfRows == 1 && numOfCols == 1)) {
            numOfRows = 10;
            numOfCols = 10;
            SimpleMazeGenerator simpleMazeGen = new SimpleMazeGenerator();
            Maze simpleMaze = simpleMazeGen.generate(numOfRows, numOfCols);
            return simpleMaze;
        }
        int[][] mazeArr = new int[numOfRows][numOfCols];
        int randomStartRow = (int) ((numOfRows - 1) * Math.random());
        int randomGoalRow = (int) ((numOfRows - 1) * Math.random());
        //If the start and goal positions are equal choose randomly again until different numbers are achieved.
        if (numOfCols == 1 && randomStartRow == randomGoalRow) {
            while (randomStartRow == randomGoalRow) {
                randomStartRow = (int) ((numOfRows - 1) * Math.random());
                randomGoalRow = (int) ((numOfRows - 1) * Math.random());
            }
        }
        //Set the starting position of the maze on the left wall.
        //Set the goal position of the maze on the right wall.
        Position startPosition = new Position(randomStartRow, 0);
        Position goalPosition = new Position(randomGoalRow, numOfCols - 1);

        //The algorithm starts from a maze of only walls.
        for (int row = 0; row < numOfRows; row++) {
            for (int col = 0; col < numOfCols; col++) {
                mazeArr[row][col] = 1;
            }
        }
        Maze newMaze = new Maze(startPosition, goalPosition, mazeArr);
        newMaze.setPosInMaze(newMaze.getStartPosition(), 0);
        newMaze.setPosInMaze(newMaze.getGoalPosition(), 0);
        mazeDepthFirstSearch(newMaze);

        return newMaze;
    }

    /**
     * A function that returns the neighbors of the current position that the algorithm has not visited yet.
     * @param isVisited, an array that represents which cells we have already visited.
     * @param currPositionList, list with the neighbors of the curr position.
     * @return, returns a list of the neighbors who have not yet visited them out of all the neighbors of the position.
     */
    private ArrayList<Position> getUnvisitedNeighbors(boolean[][] isVisited, ArrayList<Position> currPositionList)
    {
        ArrayList<Position> unvisitedNeighborsList = new ArrayList<Position>();
        for (Position pos: currPositionList)
        {
            if (isVisited[pos.getRowIndex()][pos.getColumnIndex()] != true){
                unvisitedNeighborsList.add(pos);
            }
        }
        if (unvisitedNeighborsList.size() == 0){
            return null;
        }
        else{
            return unvisitedNeighborsList;
        }
    }

    /**
     * The function goes over the maze and breaks the walls according to the iterative algorithm of dfs.
     * @param mazeToGenerate, a maze that is initialized so that all its positions are walls and equals to 1.
     */
    private void mazeDepthFirstSearch(Maze mazeToGenerate)
    {
        //As long as the stack is not empty the algorithm pop and push the maze cells into it.
        Stack<Position> mazePositionsStack = new Stack<>();
        //Two-dimensional array according to the sizes of the maze so that each cell in the array shows whether
        //the algorithm visited the particular position.
        //True if so, otherwise false.
        boolean[][] isVisited = new boolean[mazeToGenerate.getNumOfRows()][mazeToGenerate.getNumOfCols()];

        Position initialPosition = mazeToGenerate.getStartPosition();
        isVisited[initialPosition.getRowIndex()][initialPosition.getColumnIndex()] = true;
        mazePositionsStack.push(initialPosition);
        Position currPosition;
        Position unvisitedNeighbor;
        Position wallPosition;
        int wallPosRow, wallPosCol;
        ArrayList<Position> currNeighborsList;
        ArrayList<Position> currUnvisitedNeighbors;
        Random random = new Random();
        int randIndex;
        while (mazePositionsStack.isEmpty() != true)
        {
            currPosition = mazePositionsStack.pop();
            if (mazeToGenerate.getNumOfCols() % 2 == 0 && currPosition.getColumnIndex() == mazeToGenerate.getNumOfCols() - 2){
                Position goalPosition = new Position(currPosition.getRowIndex(), currPosition.getColumnIndex() + 1);
                mazeToGenerate.setPosInMaze(goalPosition, 0);
                mazeToGenerate.setGoalPosition(goalPosition);
            }
            if (mazeToGenerate.getNumOfCols() % 2 == 1 && currPosition.getColumnIndex() == mazeToGenerate.getNumOfCols() - 1){
                mazeToGenerate.setGoalPosition(currPosition);
            }
            currNeighborsList = this.getPositionNeighbors(mazeToGenerate, currPosition);
            currUnvisitedNeighbors = this.getUnvisitedNeighbors(isVisited, currNeighborsList);
            if (currUnvisitedNeighbors != null){
                if (currUnvisitedNeighbors.size() > 1){
                    randIndex = random.nextInt(currUnvisitedNeighbors.size());
                }
                else{
                    randIndex = 0;
                }
                mazePositionsStack.push(currPosition);
                unvisitedNeighbor = currUnvisitedNeighbors.get(randIndex);
                mazeToGenerate.setPosInMaze(unvisitedNeighbor, 0);
                //Create a new position with the indexes of the wall between the curr position and the neighbor position
                //according to the average of the two points in the 2D array.
                wallPosRow = (currPosition.getRowIndex() + unvisitedNeighbor.getRowIndex()) / 2;
                wallPosCol = (currPosition.getColumnIndex() + unvisitedNeighbor.getColumnIndex()) / 2;
                //Removing the wall between the current cell and the selected cell.
                wallPosition = new Position(wallPosRow, wallPosCol);
                mazeToGenerate.setPosInMaze(wallPosition, 0);
                isVisited[unvisitedNeighbor.getRowIndex()][unvisitedNeighbor.getColumnIndex()] = true;
                isVisited[wallPosRow][wallPosCol] = true;
                mazePositionsStack.push(unvisitedNeighbor);
            }
        }
    }

    /**
     * @param mazeToGenerate the maze that we want to generate.
     * @param pos any position in the maze.
     * @return ArrayList of positions with the possible neighbors of pos.
     */
    private ArrayList<Position> getPositionNeighbors(Maze mazeToGenerate, Position pos)
    {
        ArrayList<Position> neighborsList= new ArrayList<Position>();
        int rowIndex = pos.getRowIndex();
        int colIndex = pos.getColumnIndex();
        Position neighborPos;

        //Check if the left neighbor exists.
        if (colIndex > 1) {
            neighborPos = new Position(rowIndex, colIndex - 2);
            neighborsList.add(neighborPos);
        }
        //Check if the right neighbor exists.
        if (colIndex < mazeToGenerate.getNumOfCols() - 2) {
            neighborPos = new Position(rowIndex, colIndex + 2);
            neighborsList.add(neighborPos);
        }
        //Check if the top neighbor exists.
        if (rowIndex > 1) {
            neighborPos = new Position(rowIndex - 2, colIndex);
            neighborsList.add(neighborPos);
        }
        //Check if the lower neighbor exists.
        if (rowIndex < mazeToGenerate.getNumOfRows() - 2) {
            neighborPos = new Position(rowIndex + 2, colIndex);
            neighborsList.add(neighborPos);
        }
        return neighborsList;
    }
}
































