package algorithms.mazeGenerators;
import java.util.Objects;

/**
 * A class that represents a valid position in the maze with fields of the row index and the column index in the maze.
 */
public class Position {
    private int positionRow;
    private int positionColumn;

    /**
     * Constructor
     * @param row
     * @param col
     */
    public Position(int row, int col)
    {
        this.positionRow = row;
        this.positionColumn = col;
    }

    /**
     * @return to string method of Position object.
     */
    @Override
    public String toString() {
        return "{" + this.positionRow + "," + this.positionColumn + '}';
    }

    /**
     * @return the row index
     */
    public int getRowIndex()
    {
        return this.positionRow;
    }

    /**
     * @return the column index
     */
    public int getColumnIndex()
    {
        return this.positionColumn;
    }

    /**
     * Sets the row index of position to be the row index from the input.
     * @param positionRow
     */
    public void setRowIndex(int positionRow) {
        this.positionRow = positionRow;
    }

    /**
     * Sets the col index of position to be the col index from the input.
     * @param positionColumn
     */
    public void setColumnIndex(int positionColumn) {
        this.positionColumn = positionColumn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        else{
            Position position = (Position) o;
            return this.positionRow == position.positionRow && this.positionColumn == position.positionColumn;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionRow, positionColumn);
    }
}
