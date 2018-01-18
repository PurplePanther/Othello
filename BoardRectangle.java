import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class BoardRectangle extends StackPane {

    private int row;
    private int col;
    private int rechHeight;
    private boolean isClicked;
    private javafx.scene.shape.Rectangle rectangle;

    public BoardRectangle(int recWidth, int recHeight,
                          javafx.scene.paint.Color color, int row, int col) {
        this.rechHeight = recHeight;
        this.rectangle = new Rectangle(recWidth, rechHeight, color);
        this.rectangle.setOnMouseClicked(event -> {
            this.isClicked = true;
        });
        this.row = row;
        this.col = col;
        this.isClicked = false;
    }

    public void resetIsClicked(){
        this.isClicked = false;
    }

    public boolean isClicked(){
        return isClicked;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Rectangle getRectangle(){
        return this.rectangle;
    }
}
