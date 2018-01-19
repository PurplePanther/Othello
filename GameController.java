import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * THe gamecontroller class.
 */
public class GameController implements Initializable {


    // the game params.

    private Player firstPlayer;
    private Player secondPlayer;
    private Player currentPlayerPlaying;
    private ReversiBoard gameBoard;
    private String startingFirst;

    int n1,n2;

    // the fxml elements.

    @FXML
    private GridPane root;

    @FXML
    private Label currentPlayingLabel, player1ScoreLabel, player2ScoreLabel;

    private boolean gameOver;

    /**
     * The init function of the class.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initializing the game parameters.
        gameOver = false;

        ArrayList<String> gameSettings = SettingsController.loadFromFile();

        //loading the game settings from the file.

        this.startingFirst = gameSettings.get(2);

        this.gameBoard = new ReversiBoard( Integer.valueOf(gameSettings.get(3)),
                Integer.valueOf(gameSettings.get(3)));

        if(startingFirst.compareTo("Player1") == 0){
            firstPlayer = new Player(1, Color.valueOf(gameSettings.get(0)));
            secondPlayer = new Player(0,Color.valueOf(gameSettings.get(1)));
        }else if(startingFirst.compareTo("Player2") == 0){
            secondPlayer = new Player(0, Color.valueOf(gameSettings.get(0)));
            firstPlayer = new Player(1,Color.valueOf(gameSettings.get(1)));
        }
        gameBoard.setPlayerColor(firstPlayer.getPlayerColor(),
                secondPlayer.getPlayerColor());
        currentPlayerPlaying = firstPlayer;

        firstPlayer.setGameBoard(gameBoard);
        secondPlayer.setGameBoard(gameBoard);

        //initializing the labels.
        setLabels();

        //if game isn't over play another turn.
        if(gameOver == false) {
            gameBoard.setOnMouseClicked(event -> {
                this.playOneTurn();
            });
        }
        //drawing the gameboard.
        this.gameBoard.drawBoard((int) root.getPrefHeight(), (int) root.getPrefWidth());
        root.getChildren().add(this.gameBoard);

    }

    /**
     * The function runs one turn.
     */
    private void playOneTurn() {

        //letting both players play 1 turn.
        n1 = playerTurn();
        n2 = playerTurn();
        if((n1 == -1 && n2 == -1) || this.gameBoard.isFull()){
            chooseWinner();
            gameOver = true;
        }




    }

    /**
     * The function chooses a winner and displays a message on screen.
     */
    private void chooseWinner() {
        String Winner = "";
        int score1,score2;
        score1 = gameBoard.countXCells();
        score2 = gameBoard.countOCells();
        if(score1 > score2){
            Winner = "Player1";
        }else if(score2 > score1){
            Winner = "Player2";
        }else{
            Winner = "It's a Tie!";
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Game Over!");
        alert.setContentText("congratulations The Winner is : " + Winner);

        alert.showAndWait();

    }

    /**
     * The function lets the current player play his turn.
     * @return - int containing 1 if the turn was played.
     */
    private int playerTurn() {
        if(!currentPlayerPlaying.hasValidMoves()){
            if(gameBoard.isFull()){
                return -1;
            }
            //displaying an alert | no possible moves.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Play passes to next player");
            alert.setContentText("current player has no possible moves.");

            alert.showAndWait();
            //changing the current player.
            changePlayingPlayer();
            return -1;
        }
        Cell move = this.gameBoard.pressedCell();
        if (move == null) {
            return 0;
        }
        if (!gameBoard.isValidCell(move.getX(),move.getY(),
                currentPlayerPlaying.getColor())){
            //alerting the player that chosen cell is an invalid move.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Not A Valid Move!");
            alert.setContentText("you cant play this chosen cell.");

            alert.showAndWait();
            return 0;
        }
        currentPlayerPlaying.playOneTurn(move);
        this.gameBoard.drawBoard((int) root.getPrefHeight(), (int) root.getPrefWidth());
        changePlayerScore();
        changePlayingPlayer();

        //everything went accordingly.
        return 1;
    }

    /**
     * Changing the current playing player to the next.
     */
    private void changePlayingPlayer() {
        if(this.currentPlayerPlaying.getColor() == 1){
            currentPlayerPlaying = this.secondPlayer;
            this.currentPlayingLabel.setText("Player2");
        }else{
            currentPlayerPlaying = this.firstPlayer;
            this.currentPlayingLabel.setText("Player1");
        }
    }

    /**
     * updating the displayed score labels.
     */
    private void changePlayerScore() {
        int player1Score = this.gameBoard.countXCells();
        int player2Score = this.gameBoard.countOCells();
        this.player1ScoreLabel.setText(player1Score + "");
        this.player2ScoreLabel.setText(player2Score + "");

    }

    /**
     * setting the gameWindow labels.
     */
    private void setLabels() {
        player1ScoreLabel.setText("2");
        player2ScoreLabel.setText("2");
        this.currentPlayingLabel.setText(startingFirst);

    }
}
