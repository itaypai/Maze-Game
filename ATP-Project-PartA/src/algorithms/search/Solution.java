package algorithms.search;

import java.util.ArrayList;

/**
 *
 */
public class Solution {
    private ArrayList<AState> solutionPath;

    /**
     * @param solPath
     */
    public Solution(ArrayList<AState> solPath)

    {
        this.solutionPath = solPath;
    }

    /**
     * @return
     */
    public ArrayList<AState> getSolutionPath()

    {
        return this.solutionPath;
    }

}
