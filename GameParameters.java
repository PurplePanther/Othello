import javafx.scene.paint.Color;

public class GameParameters {

    private ReversiBoard gameBoard;
    private Enums.PlayersColors startFirst;
    private GameLogic gameLogic;
    private Player player1;
    private Player player2;


    /**
     * Game parameter constructor. sets default parameters to start first to be player1, and
     * the game to be standart game logic.
     *
     * @param player1Type     what type player 1 is.
     * @param player1Color    what symbol will represent player 1.
     * @param player2Type     what type player 2 is.
     * @param player2Color    what symbol will represent player 2.
     * @param startFirst      who start first. by default its player1.
     * @param gameLogicOption what game logic to play. by default its standart game logic.
     */
    GameParameters( Color player1Color, Color player2Color,
                   int boardSize,
                   Enums.PlayersColors startFirst, Enums.GameLogicOptions gameLogicOption) {
        //Set who start first.
        this.startFirst = startFirst;
        //Create new game board.
        gameBoard = new ReversiBoard(boardSize, boardSize);
        //Create game logic by input.
        switch (gameLogicOption) {
            case StandartGame: {
                gameLogic = new GameLogic(gameBoard);
                break;
            }
        }
        if(startFirst == Enums.PlayersColors.Black) {
            player1 = new Player(Enums.PlayersColors.Black, player1Color, gameBoard, gameLogic);
            player2 = new Player(Enums.PlayersColors.White, player2Color, gameBoard, gameLogic);
        }else{
            player2 = new Player(Enums.PlayersColors.Black, player1Color, gameBoard, gameLogic);
            player1 = new Player(Enums.PlayersColors.White, player2Color, gameBoard, gameLogic);

        }
        //Create player 1 by it's symbol and type.

    }


    /**
     * Getter.
     *
     * @return Returns game board.
     */
    ReversiBoard getGameBoard() {
        return this.gameBoard;
    }

    /**
     * Getter.
     *
     * @return Returns player1.
     */
    Player getPlayer1() {
        return this.player1;
    }

    /**
     * Getter.
     *
     * @return Returns player2.
     */
    Player getPlayer2() {
        return this.player2;
    }

    /**
     * Returns who start first.
     *
     * @return who start first.
     */
    Enums.PlayersColors getStartFirst() {
        return this.startFirst;
    }



}
