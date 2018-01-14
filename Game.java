public class Game {

    ReversiBoard gameBoard;
    Player playerOne;
    Player playerTwo;


    public Game(int BoardWidth,int BoardLength){
        this.gameBoard = new ReversiBoard(BoardWidth, BoardLength);
        this.initialize();
    }

    private void initialize(){
        this.playerOne = new OfflinePlayer(1);
        this.playerTwo = new OfflinePlayer(0);

        this.playerOne.setGameBoard(this.gameBoard);
        this.playerTwo.setGameBoard(this.gameBoard);

        gameBoard.printBoard();
    }


    private void playOneTurn(){
        this.playerOne.playOneTurn();
        this.playerTwo.playOneTurn();
    }



    public void gameLoop(){
        boolean winCondition = false;
        while(!winCondition) {

            playOneTurn();
            boolean bothPlayersCantMove = !this.playerOne.hasValidMoves()
                    && !this.playerTwo.hasValidMoves();

            if (this.gameBoard.isFull() || bothPlayersCantMove) {
                chooseWinner();
                winCondition = true;
            }
        }
    }



    public void chooseWinner(){
        int scoreX, scoreY;
        scoreX = this.playerOne.getScore();
        scoreY = this.playerTwo.getScore();
        if (scoreX > scoreY) {
            System.out.println("The Winner Is : X with " + scoreX + "Points!");
        } else if (scoreX < scoreY) {
            System.out.println("The Winner Is : O with " + scoreY + "Points!");
        } else {
            System.out.println("We Have a Draw!" );
        }

    }

    public void start(){
        this.gameLoop();
    }
}
