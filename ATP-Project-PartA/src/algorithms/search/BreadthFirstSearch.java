package algorithms.search;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 */
public class BreadthFirstSearch extends ASearchingAlgorithm {
    protected Queue<AState> bfsQueue;

    /**
     *
     */
    public BreadthFirstSearch()
    {
        super();
        bfsQueue = new LinkedList<>();
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
            ArrayList<AState> possibleNeighbors = searchableDomain.getAllPossibleStates(currState);
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
        ArrayList<AState> solutionPath = this.createSolutionPath(searchableDomain);
        Solution searchableSolution = new Solution(solutionPath);
        return searchableSolution;
    }
}












