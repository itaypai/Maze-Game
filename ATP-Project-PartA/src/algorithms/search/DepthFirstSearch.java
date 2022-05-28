package algorithms.search;

import java.util.ArrayList;
import java.util.Stack;

/**
 * BreadthFirstSearch class extends the abstract class ASearchingAlgorithm.
 * The class implements solve according to the depth first search algorithm.
 */
public class DepthFirstSearch extends ASearchingAlgorithm {
    private Stack<AState> dfsStack;

    public DepthFirstSearch()
    {
        super();
        dfsStack = new Stack<AState>();
    }

    @Override
    public String getName()
    {
        return "DepthFirstSearch";
    }

    /**
     * @param searchableDomain
     * @return a solution to the problem according to dfs algorithm.
     */
    @Override
    public Solution solve(ISearchable searchableDomain)
    {
        AState currState = searchableDomain.getStartState();
        AState goalState = searchableDomain.getGoalState();
        dfsStack.push(currState);
        visitedStates.add(currState);

        while (!dfsStack.isEmpty())
        {
            currState = dfsStack.pop();
            if (currState.equals(goalState)){
                break;
            }
            ArrayList<AState> possibleNeighbors = searchableDomain.getAllPossibleStates(currState, false);
            this.increaseNumOfNodesEvaluated();
            for (AState neighbor: possibleNeighbors)
            {
                if (!visitedStates.contains(neighbor)){
                    dfsStack.push(neighbor);
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
