package algorithms.search;

import algorithms.mazeGenerators.Position;
import java.util.Objects;

/**
 * Object adapter between AState that represents state in any problem and Position that represents state in Maze problem.
 */
public class MazeState extends AState{
    private Position mazeState;

    /**
     * Constructor
     * @param state
     * @param cost
     */
    public MazeState(Position state, int cost)
    {
        super(cost);
        this.mazeState = state;
    }

    /**
     * Sets maze state.
     * @param mazeState
     */
    public void setMazeState(Position mazeState) {
        this.mazeState = mazeState;
    }

    /**
     * @return position that represents maze state.
     */
    public Position getMazeState()
    {
        return this.mazeState;
    }

    @Override
    public String toString()
    {
        return this.mazeState.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        MazeState mazeStateObj = (MazeState) o;
        return this.mazeState.equals(mazeStateObj.mazeState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mazeState);
    }
}
