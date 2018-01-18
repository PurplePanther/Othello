import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.TRANSPARENT;
import static javafx.scene.paint.Color.WHITE;

public class ReversiBoard extends GridPane {

    private int numRows;
    private int numCols;
    private Enums.PlayersColors[][] boardMatrix;
    private List<BoardRectangle> rectangles;
    private Color player1,player2;
    /**
     * Constructor.
     *
     * @param numRows number of rows.
     * @param numCols number of columns.
     */
    ReversiBoard(int numRows, int numCols) {
        this.getChildren().clear();
        this.setGridLinesVisible(false);
        this.setGridLinesVisible(true);

        this.numRows = numRows;
        this.numCols = numCols;
        boardMatrix = new Enums.PlayersColors[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                boardMatrix[i][j] = Enums.PlayersColors.NoColor;
            }
        }
        this.rectangles = new ArrayList<>();
    }

    public void setColors(Color player1,Color player2){
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * Take symbol and put it on board in wanted row and column.
     *
     * @param row    wanted row.
     * @param column wanted column.
     * @param color  wanted color.
     */
    void putSymbolOnBoard(int row, int column, Enums.PlayersColors color) {
        boardMatrix[row - 1][column - 1] = color;
    }

    //Returns true if row and column is

    /**
     * Checks if given coordinate is on board.
     *
     * @param row    wanted row.
     * @param column wanted column.
     * @return true if on board, else otherwise.
     */
    boolean isOnBoard(int row, int column) {
        return ((row > 0) && (row <= numRows) && (column > 0) && (column <= numCols));
    }

    //Returns symbol that is in input row and column place.

    /**
     * Return symbol by place.
     *
     * @param row    row place.
     * @param column column place.
     * @return symbol from wanted place.
     */
    Enums.PlayersColors getSymbolByPlace(int row, int column) {
        if (!isOnBoard(row, column)) {
            throw new RuntimeException("Invalid place!");
        }
        return boardMatrix[row - 1][column - 1];
    }

    /**
     * Getter.
     *
     * @return numRows.
     */
    int getNumRows() {
        return this.numRows;
    }

    /**
     * Getter
     *
     * @return numCols.
     */
    int getNumCols() {
        return this.numCols;
    }

    public void drawBoard(int rootPrefHeight, int rootPrefWidth) {

        int guiHeight = rootPrefHeight - 50;
        int guiWidth = rootPrefWidth - 50;
        int cellHeight = guiHeight / this.numCols;
        int cellWidth = guiWidth / this.numRows;
        int radius = (cellHeight + cellWidth)/8;
        for (int i = 0; i < this.numRows; i++) {
            for (int j = 0; j < this.numCols; j++) {
                BoardRectangle newRec = new BoardRectangle(cellWidth, cellHeight, TRANSPARENT, j, i);
                rectangles.add(newRec);
                this.add(newRec.getRectangle(), i, j);
                if (getSymbolByPlace(i + 1, j + 1) == Enums.PlayersColors.Black) {
                    Circle black = new Circle(radius);
                    black.setFill(this.player1);
                    this.add(black, j, i);
                    GridPane.setValignment(black, VPos.CENTER);
                    GridPane.setHalignment(black, HPos.CENTER);
                } else if (getSymbolByPlace(i + 1, j + 1) == Enums.PlayersColors.White) {
                    Circle white = new Circle(radius);
                    white.setFill(this.player2);
                    white.setStroke(BLACK);
                    this.add(white, j, i);
                    GridPane.setValignment(white, VPos.CENTER);
                    GridPane.setHalignment(white, HPos.CENTER);
                }
            }
        }

    }

    public Cell getClickedPlace() {
        for (int i = 0; i < rectangles.size(); i++) {
            for (int j = 0; j < rectangles.size(); j++) {
                if (this.rectangles.get(i).isClicked()) {
                    this.rectangles.get(i).resetIsClicked();
                    BoardRectangle currentRectangle = rectangles.get(i);
                    return new Cell(currentRectangle.getRow() + 1,
                            currentRectangle.getCol() + 1);
                }
            }
        }
        return null;
    }


}
