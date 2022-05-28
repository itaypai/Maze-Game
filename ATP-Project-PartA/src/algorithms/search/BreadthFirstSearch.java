package algorithms.search;

import java.util.*;

/**
 *
 */
public class BreadthFirstSearch extends ASearchingAlgorithm {
    protected PriorityQueue<AState> bfsQueue;
    protected boolean useCost;
    /**
     *
     */
    public BreadthFirstSearch()
    {
        super();
        this.useCost = false;
        bfsQueue = new PriorityQueue<>();
    }


    /**
     * @return
     */
    @Override
    public String getName()
    {
        return "BreadthFirstSearch";
    }

    /**
     * @param searchableDomain
     * @return
     */
    @Override
    public Solution solve(ISearchable searchableDomain)
    {
        if (searchableDomain == null){
            return null;
        }
        AState currState = searchableDomain.getStartState();
        AState goalState = searchableDomain.getGoalState();
        bfsQueue.add(currState);
        visitedStates.add(currState);

        while (!bfsQueue.isEmpty())
        {
            currState = bfsQueue.poll();
            if (currState.equals(goalState)){
                break;
            }
            ArrayList<AState> possibleNeighbors = searchableDomain.getAllPossibleStates(currState, useCost);
            this.increaseNumOfNodesEvaluated();
            for (AState neighbor: possibleNeighbors)
            {
                if (!visitedStates.contains(neighbor)){
                    bfsQueue.add(neighbor);
                    visitedStates.add(neighbor);
                    neighbor.setFatherState(currState);
                }
            }
        }
        ArrayList<AState> solutionPath = this.createSolutionPath(currState);
        Solution searchableSolution = new Solution(solutionPath);
        return searchableSolution;
    }
}












