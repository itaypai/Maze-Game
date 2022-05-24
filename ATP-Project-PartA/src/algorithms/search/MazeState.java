package algorithms.search;

import algorithms.mazeGenerators.Position;
import java.util.Objects;

/**
 *
 */
public class MazeState extends AState{
    private Position mazeState;

    /**
     * @param state
     * @param cost
     */
    public MazeState(Position state, int cost)
    {
        super(cost);
        this.mazeState = state;
    }

    /**
     * @param mazeState
     */
    public void setMazeState(Position mazeState) {
        this.mazeState = mazeState;
    }

    /**
     * @return
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
