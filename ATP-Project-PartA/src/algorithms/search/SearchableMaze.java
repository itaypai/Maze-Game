package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

/**
 *
 */
public class SearchableMaze implements ISearchable {
    Maze maze;

    /**
     * @param searchable
     */
    public SearchableMaze(Maze searchable)
    {
        this.maze = searchable;
    }

    /**
     * @return
     */
    @Override
    public AState getStartState()
    {
        Position startPosition = maze.getStartPosition();
        AState startState = new MazeState(startPosition, 0);
        return startState;
    }

    /**
     * @return
     */
    @Override
    public AState getGoalState()
    {
        Position goalPosition = maze.getGoalPosition();
        AState goalState = new MazeState(goalPosition, 0);
        return goalState;
    }

    /**
     * @return
     */
    @Override
    public ArrayList<AState> getAllPossibleStates(AState state) {
        MazeState stateInMaze = (MazeState) state;
        ArrayList<AState> possibleNeighbors = new ArrayList<>();
        int stateRow = stateInMaze.getMazeState().getRowIndex();
        int stateCol = stateInMaze.getMazeState().getColumnIndex();

        //Booleans of the four directions of the neighbors in order to know if it is possible to reach the
        //neighbors in the diagonals.
        boolean upperNeighbor = false;
        boolean rightNeighbor = false;
        boolean bottomNeighbor = false;
        boolean leftNeighbor = false;

        //Positions of the four directions.
        int upperPosition = this.maze.getMazeArray()[stateRow - 1][stateCol];
        int rightPosition = this.maze.getMazeArray()[stateRow][stateCol + 1];
        int bottomPosition = this.maze.getMazeArray()[stateRow + 1][stateCol];
        int leftPosition = this.maze.getMazeArray()[stateRow][stateCol - 1];
        //Positions of the diagonals.
        int upperRightPosition = this.maze.getMazeArray()[stateRow - 1][stateCol + 1];
        int bottomRightPosition = this.maze.getMazeArray()[stateRow + 1][stateCol + 1];
        int bottomLeftPosition = this.maze.getMazeArray()[stateRow + 1][stateCol - 1];
        int upperLeftPosition = this.maze.getMazeArray()[stateRow - 1][stateCol - 1];

        //Check and find all the neighbors' states that can be reached from the curr state in the maze.
        //Up
        if (stateRow > 0 && upperPosition == 0) {
            upperNeighbor = true;
            Position upPos = new Position(stateRow - 1, stateCol);
            MazeState upState = new MazeState(upPos, 10);
            possibleNeighbors.add(upState);
        }
        //Right
        if (stateCol < this.maze.getNumOfCols() - 1 && rightPosition == 0) {
            rightNeighbor = true;
            Position rightPos = new Position(stateRow, stateCol + 1);
            MazeState rightState = new MazeState(rightPos, 10);
            possibleNeighbors.add(rightState);
        }
        //Diagonal up right
        if (stateRow > 0 && stateCol < this.maze.getNumOfCols() - 1) {
            if ((upperNeighbor == true || rightNeighbor == true) && upperRightPosition == 0) {
                Position upRightPos = new Position(stateRow - 1, stateCol + 1);
                MazeState upRightState = new MazeState(upRightPos, 15);
                possibleNeighbors.add(upRightState);
            }
        }
        //Down
        if (stateRow < this.maze.getNumOfRows() - 1 && bottomPosition == 0) {
            bottomNeighbor = true;
            Position bottomPos = new Position(stateRow + 1, stateCol);
            MazeState downState = new MazeState(bottomPos, 10);
            possibleNeighbors.add(downState);
        }
        //Diagonal down right
        if (stateRow < this.maze.getNumOfRows() - 1 && stateCol < this.maze.getNumOfCols() - 1) {
            if ((rightNeighbor == true || bottomNeighbor == true) && bottomRightPosition == 0) {
                Position downRightPos = new Position(stateRow + 1, stateCol + 1);
                MazeState downRightState = new MazeState(downRightPos, 15);
                possibleNeighbors.add(downRightState);
            }
        }
        //Left
        if (stateCol > 0 && leftPosition == 0) {
            leftNeighbor = true;
            Position leftPos = new Position(stateRow, stateCol - 1);
            MazeState leftState = new MazeState(leftPos, 10);
            possibleNeighbors.add(leftState);
        }
        //Diagonal down left
        if (stateRow < this.maze.getNumOfRows() - 1 && stateCol > 0) {
            if ((leftNeighbor == true || bottomNeighbor == true) && bottomLeftPosition == 0) {
                Position downLeftPos = new Position(stateRow + 1, stateCol - 1);
                MazeState downLeftState = new MazeState(downLeftPos, 15);
                possibleNeighbors.add(downLeftState);
            }
        }
        //Diagonal up left
        if (stateRow > 0 && stateCol > 0) {
            if ((upperNeighbor == true || leftNeighbor == true) && upperLeftPosition == 0) {
                Position upLeftPos = new Position(stateRow - 1, stateCol - 1);
                MazeState upLeftState = new MazeState(upLeftPos, 15);
                possibleNeighbors.add(upLeftState);
            }
        }
        return possibleNeighbors;
    }

}