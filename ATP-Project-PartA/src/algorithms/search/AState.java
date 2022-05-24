package algorithms.search;

/**
 *
 */
public class AState {
    private int costOfArrival;
    private AState fatherState;

    /**
     * @param cost
     */
    public AState(int cost)
    {
        this.costOfArrival = cost;
        this.fatherState = null;
    }

    /**
     * @param costOfArrival
     */
    public void setCostOfArrival(int costOfArrival)

    {
        this.costOfArrival = costOfArrival;
    }

    /**
     * @param fatherState
     */
    public void setFatherState(AState fatherState)
    {
        this.fatherState = fatherState;
    }

    /**
     * @return
     */
    public int getCostOfArrival()
    {
        return this.costOfArrival;
    }

    /**
     * @return
     */
    public AState getFatherState()
    {
        return this.fatherState;
    }


}
