package algorithms.search;

import java.util.ArrayList;

/**
 * ISearchable is an interface.
 * The classes that implement it are classes that represents searchable problems.
 */
public interface ISearchable {
    /**
     * @return the start state of the problem.
     */
    AState getStartState();

    /**
     * @return the goal state of the problem.
     */
    AState getGoalState();

    /**
     * @return a list of possible neighbors states in the searchable that can be reached from the specific state.
     */
    ArrayList<AState> getAllPossibleStates(AState state, boolean useCost);

}
