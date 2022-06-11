package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Solution is a class that represents the solution to the problem.
 * A path made up of states from the start to till the goal that together represent a solution.
 */
public class Solution implements Serializable {
    private ArrayList<AState> solutionPath;

    /**
     * Constructor
     * @param solPath
     */
    public Solution(ArrayList<AState> solPath)

    {
        this.solutionPath = solPath;
    }

    /**
     * @return solution path list.
     */
    public ArrayList<AState> getSolutionPath()

    {
        return this.solutionPath;
    }

}
