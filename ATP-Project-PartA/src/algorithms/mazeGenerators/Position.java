package algorithms.mazeGenerators;
import java.util.Objects;

/**
 *
 */
public class Position {
    private int positionRow;
    private int positionColumn;

    public Position(int row, int col)
    {
        this.positionRow = row;
        this.positionColumn = col;
    }

    @Override
    public String toString() {
        return "{" + this.positionRow + "," + this.positionColumn + '}';
    }

    public int getRowIndex()
    {
        return this.positionRow;
    }
    public int getColumnIndex()
    {
        return this.positionColumn;
    }

    public void setRowIndex(int positionRow) {
        this.positionRow = positionRow;
    }

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
