package algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * ASearchingAlgorithm is an abstract class that implements SearchingAlgorithm interface.
 * The abstract class is inherited by the sub-class which are the various search algorithms.
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {
    private int numOfNodesEvaluated;
    protected HashSet<AState> visitedStates;

    /**
     * Constructor
     */
    public ASearchingAlgorithm()
    {
        visitedStates = new HashSet<AState>();
        numOfNodesEvaluated = 0;
    }
    /**
     * @return the number of evaluated nodes.
     */
    @Override
    public int getNumberOfNodesEvaluated()
    {
        return this.numOfNodesEvaluated;
    }

    /**
     * Add 1 to the num of evaluated nodes.
     */
    protected void increaseNumOfNodesEvaluated()
    {
       this.numOfNodesEvaluated = this.numOfNodesEvaluated + 1;
    }


    /**
     * Runs from a certain state on the father field until it reaches null and thus creates the solution path.
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
