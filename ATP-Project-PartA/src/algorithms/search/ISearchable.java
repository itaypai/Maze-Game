package algorithms.search;

import java.util.ArrayList;

/**
 *
 */
public interface ISearchable {
    /**
     * @return
     */
    AState getStartState();

    /**
     * @return
     */
    AState getGoalState();

    /**
     * @return
     */
    ArrayList<AState> getAllPossibleStates(AState state);

}
