import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * The player class.
 */
public class Player {
    //the variables.
    ReversiBoard gameBoard;
    int color;
    Color playerColor;

    /**
     * The function returns the players sign a.k.a color.
     * @return - an integer containing either 1 for x || 0 for O.
     */
    public Color getPlayerColor() {
        return playerColor;
    }

    /**
     * The base constructor.
     * @param sign - the players sign.
     * @param playerColor - the players color.
     */
    public Player(int sign, Color playerColor) {
        this.color = sign;
        this.playerColor = playerColor;
    }


    /**
     * returning the players color.
     * @return - 1 for X | 0 for O.
     */
    int getColor(){
        return this.color;
    }

    /**
     * the function plays the passed move.
     * @param move - players move.
     */
    void playOneTurn(Cell move){
        Cell x = move;

        //checking if player can move
        if(this.hasValidMoves()){

            boolean acceptablemove = false;
            while(acceptablemove == false){
                if(this.gameBoard.isValidCell(x.getX(), x.getY(),this.color)){
                    this.gameBoard.placePiece(this.color, x.getX(), x.getY());
                    acceptablemove = true;
                }
            }
        }

    }

    /**
     * the function passes the player the gameBoard.
     * @param board - the game board we are playing on.
     */
    void setGameBoard(ReversiBoard board){
        this.gameBoard = board;
    }

    /**
     * The function checks if the player has any valid moves.
     * @return - true if there is, false otherwise.
     */
    boolean hasValidMoves(){
        ArrayList<Cell> array;
        array = this.gameBoard.possibleMoves(this.color);
        if(array.isEmpty()){
            array.clear();
            return false;
        }else{
            array.clear();
            return true;
        }
    }

}