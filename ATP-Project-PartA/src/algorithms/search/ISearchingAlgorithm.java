package algorithms.search;

/**
 * ISearchingAlgorithm is an interface.
 * Implemented by class that represents searching algorithms.
 */
public interface ISearchingAlgorithm {
    /**
     * @param searchableDomain
     * @return a solution to the searchable domain.
     */
    Solution solve(ISearchable searchableDomain);

    /**
     * @return the name of the searching algorithm.
     */
    String getName();

    /**
     * @return the number of evaluated nodes.
     */
    int getNumberOfNodesEvaluated();
}
