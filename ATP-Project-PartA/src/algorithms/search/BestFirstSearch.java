package algorithms.search;

/**
 * BestFirstSearch extends BreadthFirstSearch algorithm.
 * The difference between them is that the best first search algorithm gives meaning to the queue of priorities
 * and does not treat all steps equally.
 */
public class BestFirstSearch extends BreadthFirstSearch {

    public BestFirstSearch()
    {
        super();
        this.useCost = true;
    }
    @Override
    public String getName()
    {
        return "BestFirstSearch";
    }

}
