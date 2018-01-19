import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class FXCell extends StackPane {

    // the variables of the cell.
    public int row;
    public int col;
    public int rechHeight;
    public boolean isClicked;
    public javafx.scene.shape.Rectangle rectangle;

    /**
     * The base constructor method of the cell.
     * @param recWidth - the rectangle width.
     * @param recHeight - the rectangle height.
     * @param color - the color of the player.
     * @param row - row of the cell on the board.
     * @param col - col of the cell on the board.
     */
    public FXCell(double recWidth, double recHeight,
                         javafx.scene.paint.Color color, double row, double col) {
        this.rechHeight = (int) recHeight;
        this.row = (int) row;
        this.col = (int) col;
        this.isClicked = false;
        this.rectangle = new Rectangle(recWidth, rechHeight, color);
        this.rectangle.setOnMouseClicked(event -> {
            this.isClicked = true;
        });
    }

    /**
     * the function sets the parameter isClicked of the FXCELL.
     * @param click - true if current cell is clicked | false otherwise.
     */
    public void setClicked(boolean click){
        this.isClicked = click;
    }
}
