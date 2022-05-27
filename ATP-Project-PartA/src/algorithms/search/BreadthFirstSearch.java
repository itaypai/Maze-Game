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
        boolean finishSearch = false;
        bfsQueue.add(currState);
        visitedStates.add(currState);

        while (!bfsQueue.isEmpty() && finishSearch != true)
        {
            this.increaseNumOfNodesEvaluated();
            currState = bfsQueue.poll();
            finishSearch = currState.equals(goalState);

            ArrayList<AState> possibleNeighbors = searchableDomain.getAllPossibleStates(currState);
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












