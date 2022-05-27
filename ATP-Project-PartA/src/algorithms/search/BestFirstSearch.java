package algorithms.search;

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
