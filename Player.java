import java.util.ArrayList;

public abstract class Player {
    ReversiBoard gameBoard;
    int color;


    public Player(int color) {
        this.color = color;
    }


    int getColor(){
        return this.color;
    }

    int getScore(){
        int score=0;
        if(this.color == 0){
            score=this.gameBoard.countOCells();
        } else {
            score=this.gameBoard.countXCells();
        }
        return score;
    }

    void playOneTurn(){
        Cell x = null;
        char sign;

        if(this.color == 1) {
            sign = 'X';
        } else {
            sign = 'O';
        }

        //checking if player can move
        if(this.hasValidMoves()){

            System.out.println(sign + ": It's your move.");
            System.out.print("Your possible moves: ");

            ArrayList<Cell> array = this.gameBoard.possibleMoves(this.color);
            System.out.println(array.size());
            for(int i=0 ; i < array.size() ;i++){
                    System.out.print(array.get(i));
                    if(i != array.size()-1){
                        System.out.print(",");
                    }
            }
            System.out.println();
            System.out.println();

            boolean acceptablemove = false;
            while(acceptablemove == false){
                x = this.chooseMove();
                if(this.gameBoard.isValidCell(x.getX()-1, x.getY()-1,this.color)){
                    this.gameBoard.placePiece(this.color, x.getX(), x.getY());
                    acceptablemove = true;

                }else{
                    System.out.println("Error, not a valid move,");
                    System.out.println("Please choose one of the valid moves above.");


                }
            }
            array.clear();
        } else {
            //player has no valid moves.
            System.out.println(sign + ": It's your move.");
            System.out.println("No possible Moves. Play passes back to the other player.");

            return;
        }
        //printing the updated board.
        this.gameBoard.printBoard();
        System.out.println(sign + " Played" + "(" + x.getX() + "," + x.getY() + ")");
    }

    void setGameBoard(ReversiBoard board){
            this.gameBoard = board;
    }

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

    abstract Cell chooseMove();
}
