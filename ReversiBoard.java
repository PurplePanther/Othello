import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.TRANSPARENT;

public class ReversiBoard extends GridPane {

    /**
     * the function returns the # of cols in ReversiBoard's.
     * @return
     */
    public int getSizeX() {
        return sizeX;
    }

    /**
     * the function returns the # of rows in ReversiBoard's.
     * @return
     */
    public int getSizeY() {
        return sizeY;
    }

    //board size X*Y.
    int sizeX, sizeY;

    //2D array of Cells.
    Cell[][] gameBoard;
    private ArrayList<FXCell> rectangles;
    Color player1Color,player2Color;

    /**
     * the function lets the board know the colors of both players.
     * @param player1Color
     * @param player2Color
     */
    public void setPlayerColor(Color player1Color,Color player2Color) {
        this.player1Color = player1Color;
        this.player2Color = player2Color;
    }

    /**
     * the function returns the Cell board itself.
     * @return
     */
    public Cell[][] getGameBoard() {
        return gameBoard;
    }

    /**
     * the base constructor method which takes Rows & Cols.
     * @param sizeX
     * @param sizeY
     */
    public ReversiBoard(int sizeX, int sizeY) {
        this.setGridLinesVisible(true);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.rectangles = new ArrayList<>();
        this.gameBoard = new Cell[sizeX][sizeY];

        intiBoard();

    }

    /**
     * The function initializes the board.
     */
    private void intiBoard() {
        for (int i = 0; i < sizeX; i++) {
            for(int j = 0; j < sizeY; j++) {
                this.gameBoard[i][j] = new Cell(i,j);
                this.gameBoard[i][j].setContainingValue(false);
                this.gameBoard[i][j].setColor(-1);
            }
        }

        int mid=sizeX/2;
        //starting position of O's
        this.gameBoard[mid-1][mid-1].setContainingValue(true);
        this.gameBoard[mid][mid].setContainingValue(true);
        this.gameBoard[mid-1][mid-1].setColor(0);
        this.gameBoard[mid][mid].setColor(0);

        //starting position of X's
        this.gameBoard[mid-1][mid].setContainingValue(true);
        this.gameBoard[mid][mid-1].setContainingValue(true);
        this.gameBoard[mid-1][mid].setColor(1);
        this.gameBoard[mid][mid-1].setColor(1);

    }

    /**
     * the function checks if the input cell is located on the board.
     * @param x
     * @param y
     * @return a boolean containing true if it is || false otherwise.
     */
    private boolean pointIsValid(int x, int y) {
        if( x < 0 || y < 0 ||
                x >= this.sizeX || y >= this.sizeY){
            return false;
        }

        return true;
    }


    /**
     * the function flips the cells between the 2 input cells.
     * @param x
     * @param y
     */
    public void flipCellsBetween(Cell x, Cell y){
        int directionX = 0;
        int directionY = 0;
        int cellXX = x.getX();
        int cellYX = y.getX();
        //getting direction X.
        if (cellXX > cellYX) {
            directionX = -1;
        } else if(cellXX < cellYX) {
            directionX = 1;
        } else {
            directionX = 0;
        }

        int cellXY = x.getY();
        int cellYY = y.getY();
        //getting direction Y.
        if (cellXY > cellYY) {
            directionY = -1;
        } else if(x.getY() < y.getY()) {
            directionY = 1;
        } else {
            directionY = 0;
        }

        while((cellYX != cellXX) || (cellXY != cellYY)) {
            cellXX+=directionX;
            cellXY+=directionY;
            if(x.getColor() == 1) {
                this.gameBoard[cellXX][cellXY].setColor(1);
            }else{
                this.gameBoard[cellXX][cellXY].setColor(0);
            }
        }
    }


    /**
     * the function places a piece on the board.
     * @param color - the piece's color.
     * @param positionX - the x position.
     * @param positionY - the y position.
     */
    public void placePiece(int color, int positionX, int positionY){
        ArrayList<Cell> listConnections =  connectionsWith(gameBoard[positionX][positionY],color);
        this.gameBoard[positionX][positionY].setColor(color);
        this.gameBoard[positionX][positionY].setContainingValue(true);
        int i = 0;
        for (Cell currentCell: listConnections){
            this.flipCellsBetween(gameBoard[positionX][positionY],currentCell);
        }


        listConnections.clear();
    }

    /**
     * the function returns an arraylist containing all the connection with input cell.
     * @param x - input cell.
     * @param color - the input cell's player color.
     * @return - array list containing all the possible connections.
     */
    public ArrayList<Cell> connectionsWith(Cell x, int color){
        ArrayList<Cell> Connections = new ArrayList<Cell>();

        //checking if the cell is a valid cell.
        if(!this.isValidCell(x.getX(),x.getY(),color)){

            return Connections;
        } else {

            int currentX = x.getX();
            int currentY = x.getY();

            //checking if the cell we are looking at is currently empty.
            if(this.gameBoard[currentX][currentY].isEmpty()) {

                //checking for surrounding cells.
                for (int i = -1; i <= 1 ; ++i){
                    for (int j = -1; j <= 1; ++j){
                        // check if surrounding cells are in the boundaries.
                        if(!this.pointIsValid(currentX+i,currentY+j) || (i==0 && j==0)) {
                            continue;
                        }
                        //we are making sure the current neighbor cell we are looking at isn't empty.
                        boolean isNeighborEmpty = this.gameBoard[currentX+i][currentY+j].isEmpty();

                        //if the neighbor cell is empty we move on to another neighbor cell.
                        if(isNeighborEmpty) {
                            continue;
                        }
                        //we are making sure the sign of the neighbor cell is different than ours.
                        boolean hasDiffSign = (this.gameBoard[currentX+i][currentY+j].getColor() != color);

                        if(!isNeighborEmpty && hasDiffSign){
                            boolean found = false;
                            int posX = currentX+i+i;
                            int posY = currentY+j+j;

                            if(!pointIsValid(posX,posY)){
                                continue; // testing continue v break;
                            }
                            boolean friendlyNeighbor = this.gameBoard[posX][posY].isEmpty();
                            //checking if there is a piece that we can possibly connect with so we can flip.
                            if(friendlyNeighbor) {
                                continue;
                            }

                            //checking if possible connection Cell is friendly.
                            boolean hasSameSign = (this.gameBoard[posX][posY].getColor() == color);
                            if(!friendlyNeighbor && hasSameSign){

                                // this cell can be played.
                                Connections.add(new Cell(posX,posY));
                            } else if (!hasSameSign) {
                                while (!found){
                                    posX = posX + i;
                                    posY = posY + j;

                                    if(!pointIsValid(posX,posY)){
                                        break;
                                    }
                                    if(this.gameBoard[posX][posY].getColor() == color) {
                                        Connections.add(new Cell(posX,posY));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return Connections;
    }

    /**
     * the function checks if the input cell position and sign are playable.
     * @param x
     * @param y
     * @param sign
     * @return
     */
    public boolean isValidCell(int x, int y, int sign) {

        //if the cell we are looking for is out of boundaries.
        if(!pointIsValid(x,y)) {
            return false;
        }else{
            int currentX = x;
            int currentY = y;
            //checking if the cell we are looking at is empty.
            if(this.gameBoard[currentX][currentY].isEmpty()) {

                //checking for surrounding cells.
                for (int i = -1; i <= 1 ; ++i){
                    for (int j = -1; j <= 1; ++j){
                        // check if surrounding cells are in the boundaries.
                        if(!pointIsValid(currentX+i,currentY+j) || (i==0 && j==0)) {
                            continue;
                        }
                        //we are making sure the current neighbor cell we are looking at isn't empty.
                        boolean isNeighborEmpty = this.gameBoard[currentX+i][currentY+j].isEmpty();

                        //if the neighbor cell is empty we move on to another neighbor cell.
                        if(isNeighborEmpty) {
                            continue;
                        }
                        //we are making sure the sign of the neighbor cell is different than ours.
                        boolean hasDiffSign = (this.gameBoard[currentX+i][currentY+j].getColor() != sign);
                        if(!isNeighborEmpty && hasDiffSign){
                            boolean found = false;
                            int posX = currentX+i+i;
                            int posY = currentY+j+j;

                            if(!pointIsValid(posX,posY)) {
                                continue;
                            }

                            boolean friendlyNeighbor = this.gameBoard[posX][posY].isEmpty();

                            //checking if there is a piece that we can possibly connect with so we can flip.
                            if(friendlyNeighbor) {
                                continue;
                            }
                            //checking if possible connection Cell is friendly.
                            boolean hasSameSign = (this.gameBoard[posX][posY].getColor() == sign);
                            if(!friendlyNeighbor && hasSameSign){

                                // this cell can be played.
                                return true;
                            } else if (!hasSameSign) {
                                while (!found){
                                    posX += i;
                                    posY += j;
                                    if(!pointIsValid(posX,posY)){
                                        break;
                                    }
                                    if(this.gameBoard[posX][posY].getColor() == sign) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    /**
     * the function returns the amount of X cell's on the board (player 1 Cell's).
     * @return
     */
    public int countXCells() {
        int x = 0;
        for(int i = 0; i < this.sizeX ; ++i) {
            for(int j = 0; j < this.sizeY ; ++j) {
                if(!this.gameBoard[i][j].isEmpty()){
                    if(this.gameBoard[i][j].getColor() == 1){
                        x++;
                    }
                }
            }
        }
        return x;
    }

    /**
     * The function returns the amount of O cell's on the board (player 2 Cell's).
     * @return
     */
    public int countOCells() {
        int x = 0;
        for(int i = 0; i < this.sizeX ; ++i) {
            for(int j = 0; j < this.sizeY ; ++j) {
                if(!this.gameBoard[i][j].isEmpty()){
                    if(this.gameBoard[i][j].getColor() == 0){
                        x++;
                    }
                }
            }
        }
        return x;
    }


    /**
     * The function returns true if gameboard is full.
     * @return
     */
    public boolean isFull(){
        if(this.countOCells() + this.countXCells() == this.sizeX*this.sizeY) {
            return true;
        }
        return false;
    }


    /**
     * the function returns list of possible moves for the input sign.
     * @param sign
     * @return
     */
    public ArrayList<Cell> possibleMoves(int sign) {
        ArrayList<Cell> possibleCells = new ArrayList<Cell>();

        // going over the entire board looking for possible moves.
        for (int i = 0; i < this.sizeX; i++) {
            for (int j = 0; j < this.sizeY; j++) {
                //checking is cell i,j is playable.
                if (this.isValidCell(i, j, sign)) {
                    possibleCells.add(new Cell(i, j));

                }
            }
        }
        return possibleCells;
    }


    /**
     * The function draws the board out on the GRIDPANE.
     * @param rootPrefHeight
     * @param rootPrefWidth
     */
    public void drawBoard(int rootPrefHeight, int rootPrefWidth) {

        int guiHeight = rootPrefHeight - 50;
        int guiWidth = rootPrefWidth - 50;
        int cellHeight = guiHeight / this.getSizeX();
        int cellWidth = guiWidth / this.getSizeY();
        int radius = (cellHeight + cellWidth)/8;
        for (int i = 0; i < this.getSizeY(); i++) {
            for (int j = 0; j < this.getSizeX(); j++) {
                FXCell newRec = new FXCell(cellWidth, cellHeight, TRANSPARENT, j, i);
                rectangles.add(newRec);
                this.add(newRec.rectangle, i, j);
                switch (gameBoard[i][j].getColor()){
                    case 1:
                        Circle black = new Circle(radius);
                        black.setFill(this.player1Color);
                        black.setStroke(BLACK);
                        this.add(black, j, i);
                        GridPane.setValignment(black, VPos.CENTER);
                        GridPane.setHalignment(black, HPos.CENTER);
                        break;
                    case 0:
                        Circle white = new Circle(radius);
                        white.setFill(this.player2Color);
                        white.setStroke(BLACK);
                        this.add(white, j, i);
                        GridPane.setValignment(white, VPos.CENTER);
                        GridPane.setHalignment(white, HPos.CENTER);
                        break;
                    default:
                        break;
                }
            }
        }

    }

    /**
     * The function returns the pressed Cell.
     * @return - the pressed cell.
     */
    public Cell pressedCell() {
        int i = 0;
        int j = 0;
        while (i < rectangles.size()){
            j = 0;
            while(j < rectangles.size()){
                if (this.rectangles.get(i).isClicked) {
                    this.rectangles.get(i).setClicked(false);
                    FXCell currentRectangle = rectangles.get(i);
                    return new Cell(currentRectangle.row,
                            currentRectangle.col);
                }
                j++;
            }
            i++;
        }
        return null;
    }



}