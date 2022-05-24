package algorithms.search;

import java.util.ArrayList;

/**
 *
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    private int numOfNodesEvaluated;

    /**
     * @return
     */
    @Override
    public int getNumberOfNodesEvaluated()
    {
        return this.numOfNodesEvaluated;
    }

    /**
     * @param searchable
     * @return
     */
    protected ArrayList<AState> createSolutionPath(ISearchable searchable)
    {
        AState currState = searchable.getGoalState();
        ArrayList<AState> searchableSolutionPath = new ArrayList<>();
        while (currState != null)
        {
            searchableSolutionPath.add(0, currState);
            currState = currState.getFatherState();
        }
        return searchableSolutionPath;
    }

}
