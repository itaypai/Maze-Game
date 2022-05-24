package algorithms.search;

/**
 *
 */
public interface ISearchingAlgorithm {
    /**
     * @param searchableDomain
     * @return
     */
    Solution solve(ISearchable searchableDomain);

    /**
     * @return
     */
    String getName();

    /**
     * @return
     */
    int getNumberOfNodesEvaluated();
}
