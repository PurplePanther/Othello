public class Cell implements Comparable {

    private int row;
    private int column;

    //Board Coordinates constructor.
    Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * to String overload
     *
     * @return string to represent board coordinate.
     */
    public String toString() {
        String result = "(" + this.row + ", " + this.column + ')';
        return result;
    }


    /**
     * Getter.
     *
     * @return Returns row parameter.
     */
    int getRow() {
        return this.row;
    }

    /**
     * Getter.
     *
     * @return Returns column parameter.
     */
    int getColumn() {
        return this.column;
    }

    @Override
    public int compareTo(Object o) {
        Cell boardCoordinate = (Cell) o;

        if (this.getRow() == boardCoordinate.getRow()) {
            if (this.getColumn() < boardCoordinate.getColumn())
                return -1;
            else
                return 1;
        } else if (this.getColumn() == boardCoordinate.getColumn()) {
            return 0;
        } else {
            if (this.getRow() < boardCoordinate.getRow()) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        Cell boardCoordinate = (Cell) other;
        return ((this.getRow() == boardCoordinate.getRow()) &&
                (this.getColumn() == boardCoordinate.getColumn()));

    }

}
