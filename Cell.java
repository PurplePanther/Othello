/**
 * The Cell CLass.
 */
public class Cell{
    //variables
    int color;
    int x,y;
    boolean containingValue;


    /**
     * default constructor method.
     * @param x - the x location on the board.
     * @param y - the y location on the board.
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.color = -1;
        this.containingValue = false;
    }

    /**
     * the function returns the cell current sign a.k.a color.
     * @return - the color of the cell.
     */
    public int getColor() {
        return color;
    }

    /**
     * The function returns the x value of the cell.
     * @return - returns the x of the cell.
     */
    public int getX() {
        return x;
    }

    /**
     * The function returns the y value of the cell.
     * @return - returns the y of the cell.
     */
    public int getY() {
        return y;
    }

    /**
     * the function returns if cell is empty (not a signed cell).
     * @return - boolean true if cell is empty | false otherwise.
     */
    public boolean isEmpty() {
        return !containingValue;
    }


    /**
     * the function sets the color of the cell.
     * @param color - int representing the color of the cell.
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * the function sets the containingvalue param of the cell.
     * @param containingValue - boolean.
     */
    public void setContainingValue(boolean containingValue) {
        this.containingValue = containingValue;
    }

    /**
     * the function prints the cell.
     * @return - a string
     */
    @Override
    public String toString() {
        return "("+ (this.x + 1) +"," + (this.y + 1) + ")";
    }
}
