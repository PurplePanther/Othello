import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;


import java.net.URL;
import java.util.*;

public class GameBoardController implements Initializable {

    private GameParameters gameParameters;
    private boolean gameOverIndicator;
    private Player firstPlayer;
    private Player secondPlayer;
    private Player currentPlayerPlaying;

    @FXML
    private GridPane root;
    @FXML
    private ReversiBoard gameBoard;
    @FXML
    private Label currentPlayer;
    @FXML
    private Label blackPlayerScore;
    @FXML
    private Label whitePlayerScore;
    @FXML
    private Label winnerPlayer;


    public GameBoardController(GameParameters gameParameters) {
        this.gameParameters = gameParameters;
        //Get game board.
        this.gameBoard = gameParameters.getGameBoard();
        //Get enum who start first.
        Enums.PlayersColors startFirstOptions = gameParameters.getStartFirst();
        //Initialize players by who start first options.
        switch (startFirstOptions) {
            case Black: {
                if (gameParameters.getPlayer1().getSymbol() == Enums.PlayersColors.Black) {
                    firstPlayer = gameParameters.getPlayer1();
                    secondPlayer = gameParameters.getPlayer2();
                } else {
                    secondPlayer = gameParameters.getPlayer1();
                    firstPlayer = gameParameters.getPlayer2();
                }
                break;
            }

            case White: {
                if (gameParameters.getPlayer1().getSymbol() == Enums.PlayersColors.White) {
                    firstPlayer = gameParameters.getPlayer1();
                    secondPlayer = gameParameters.getPlayer2();
                } else {
                    secondPlayer = gameParameters.getPlayer1();
                    firstPlayer = gameParameters.getPlayer2();
                }
                break;
            }
        }
        putSymbolStartPlace();
        currentPlayerPlaying = firstPlayer;

        this.gameBoard.setColors(firstPlayer.getColor(),secondPlayer.getColor());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //this.gameBoard = new GridPane();
        changeCurrentPlayer(currentPlayerPlaying.getSymbol());
        //Print player 1 score.
        changePlayerScore(this.firstPlayer.getSymbol(), this.firstPlayer.getScore());
        //Print player 2 score.
        changePlayerScore(this.secondPlayer.getSymbol(), this.secondPlayer.getScore());
        gameBoard.setOnMouseClicked(event -> {
            this.playTurn();
        });

        this.gameBoard.drawBoard((int) root.getPrefHeight(), (int) root.getPrefWidth());
        root.getChildren().add(this.gameBoard);

    }

    public void putSymbolStartPlace() {
        //Get number of columns.
        int numCols = this.gameBoard.getNumCols();
        //Get number of rows.
        int numRows = this.gameBoard.getNumRows();
        //Divide by 2 to put start symbols in the middle.
        int middlePlaceCol = numCols / 2;
        int middlePlaceRow = numRows / 2;

        if (firstPlayer.getSymbol() == Enums.PlayersColors.Black) {
            //Put symbols by its start position.
            this.secondPlayer.addPlayerSymbol(middlePlaceRow + 1, middlePlaceCol + 1);
            this.secondPlayer.addPlayerSymbol(middlePlaceRow, middlePlaceCol);

            //Put player1 symbols by its start position.
            this.firstPlayer.addPlayerSymbol(middlePlaceRow, middlePlaceCol + 1);
            this.firstPlayer.addPlayerSymbol(middlePlaceRow + 1, middlePlaceCol);
        } else {
            //Put symbols by its start position.
            this.firstPlayer.addPlayerSymbol(middlePlaceRow + 1, middlePlaceCol + 1);
            this.firstPlayer.addPlayerSymbol(middlePlaceRow, middlePlaceCol);

            //Put player1 symbols by its start position.
            this.secondPlayer.addPlayerSymbol(middlePlaceRow, middlePlaceCol + 1);
            this.secondPlayer.addPlayerSymbol(middlePlaceRow + 1, middlePlaceCol);
        }
    }

    public void changeCurrentPlayer(Enums.PlayersColors color) {
        if(color == Enums.PlayersColors.Black){
            this.currentPlayer.setText("Player1");
        }else{
            this.currentPlayer.setText("Player2");

        }
    }

    public void changePlayerScore(Enums.PlayersColors color, int score) {
        switch (color) {
            case Black:
                this.blackPlayerScore.setText(String.valueOf(score));
                break;
            case White:
                this.whitePlayerScore.setText(String.valueOf(score));
                break;
        }
    }

    public void printWinnerPlayer() {
        final String winnerMessage = "Winner is: ";
        final String tieMessage = "It's a tie!";
        if (this.firstPlayer.getScore() > this.secondPlayer.getScore()) {
            this.winnerPlayer.setText(winnerMessage + this.firstPlayer.getSymbol().toString());
        } else if (this.firstPlayer.getScore() == this.secondPlayer.getScore()) {
            this.winnerPlayer.setText(tieMessage);
        } else {
            this.winnerPlayer.setText(winnerMessage + this.secondPlayer.getSymbol().toString());
        }
    }


    public void playTurn() {
        Cell move = this.gameBoard.getClickedPlace();
        if (move == null) {
            return;
        } else if (currentPlayerPlaying.checkIfValidMove(move)) {
            Map<Cell, List<Cell>> mapOfLastMove = new HashMap<>();
            List<Cell> symbolsToUpdate = new ArrayList<>();
            mapOfLastMove = this.currentPlayerPlaying.playOneTurn(move);
            //Check if map of moves is not empty.
            if (!mapOfLastMove.isEmpty()) {
                //Extract the vector of cell to update from map.
                Map.Entry<Cell, List<Cell>> entry = mapOfLastMove.entrySet().iterator().next();
                symbolsToUpdate = entry.getValue();

                Player nextPlayer;
                if (this.currentPlayerPlaying == this.firstPlayer) {
                    nextPlayer = this.secondPlayer;
                } else {
                    nextPlayer = this.firstPlayer;
                }

                //Check if there is any symbols to updated.
                if (symbolsToUpdate.isEmpty()) {
                    //If not, that means that player didn't have any move.
                    //gameOverIndicator++;
                } else {
                    //Set game over indicator to be 0.
                    //gameOverIndicator = 0;
                    //Update first player of his new symbols.
                    this.currentPlayerPlaying.updatePlayerSymbolAdd(symbolsToUpdate);
                    //Update second player of symbols that he lost.
                    nextPlayer.updatePlayerSymbolRemoved(symbolsToUpdate);
                }

                this.gameBoard.drawBoard((int) root.getPrefHeight(), (int) root.getPrefWidth());

                //Print player 1 score.
                changePlayerScore(this.firstPlayer.getSymbol(), this.firstPlayer.getScore());
                //Print player 2 score.
                changePlayerScore(this.secondPlayer.getSymbol(), this.secondPlayer.getScore());


                if (nextPlayer.isThereMoves()) {
                    this.currentPlayerPlaying = nextPlayer;
                    changeCurrentPlayer(currentPlayerPlaying.getSymbol());
                } else {
                    if (!this.currentPlayerPlaying.isThereMoves()) {
                        printWinnerPlayer();
                        this.gameOverIndicator = true;
                    }
                }
            }
        }

    }
}
