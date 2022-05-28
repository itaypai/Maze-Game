package algorithms.search;

import java.util.*;

/**
 * BreadthFirstSearch class extends the abstract class ASearchingAlgorithm.
 * The class implements solve according to the breadth first search algorithm.
 */
public class BreadthFirstSearch extends ASearchingAlgorithm {
    protected PriorityQueue<AState> bfsQueue;
    protected boolean useCost;
    /**
     * Constructor
     * Refers to the priority queue as a regular queue with no difference between the steps.
     */
    public BreadthFirstSearch()
    {
        super();
        this.useCost = false;
        bfsQueue = new PriorityQueue<>();
    }


    /**
     * @return the name of the search algorithm.
     */
    @Override
    public String getName()
    {
        return "BreadthFirstSearch";
    }

    /**
     * @param searchableDomain
     * @return a solution to the problem according to bfs algorithm.
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












