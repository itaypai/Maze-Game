package algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {
    private int numOfNodesEvaluated;
    protected HashSet<AState> visitedStates;

    /**
     *
     */
    public ASearchingAlgorithm()
    {
        visitedStates = new HashSet<AState>();
        numOfNodesEvaluated = 0;
    }
    /**
     * @return
     */
    @Override
    public int getNumberOfNodesEvaluated()
    {
        return this.numOfNodesEvaluated;
    }

    /**
     *
     */
    protected void increaseNumOfNodesEvaluated()
    {
       this.numOfNodesEvaluated = this.numOfNodesEvaluated + 1;
    }


    /**
     * @param goalState
     * @return
     */
    protected ArrayList<AState> createSolutionPath(AState goalState)
    {
        AState currState = goalState;
        ArrayList<AState> searchableSolutionPath = new ArrayList<>();
        while (currState != null)
        {
            searchableSolutionPath.add(0, currState);
            currState = currState.getFatherState();
        }
        return searchableSolutionPath;
    }

}
