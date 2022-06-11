package algorithms.search;

import java.io.Serializable;

/**
 * AState is an abstract class represents state in a searching problem.
 * Cost of arrival is the cost for reaching this state.
 * Father state is the state we came from to this state.
 */
public abstract class AState implements Comparable<AState>, Serializable {
    private int costOfArrival;
    private AState fatherState;

    /**
     * Constructor
     * @param cost
     */
    public AState(int cost)
    {
        this.costOfArrival = cost;
        this.fatherState = null;
    }

    /**
     * Set the cost
     * @param costOfArrival
     */
    public void setCostOfArrival(int costOfArrival)

    {
        this.costOfArrival = costOfArrival;
    }

    /**
     * Set father state.
     * @param fatherState
     */
    public void setFatherState(AState fatherState)
    {
        this.fatherState = fatherState;
    }

    /**
     * @return the cost.
     */
    public int getCostOfArrival()
    {
        return this.costOfArrival;
    }

    /**
     * @return the father state of this
     */
    public AState getFatherState()
    {
        return this.fatherState;
    }

    public int compareTo(AState state)
    {
        return Integer.compare(this.getCostOfArrival(),state.getCostOfArrival());
    }
}
