import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsWindowController implements Initializable {
    final String propertyFileName = "GameProperties";

    @FXML
    private RadioButton startsFirstBlack;
    @FXML
    private RadioButton startsFirstWhite;
    @FXML
    private ColorPicker firstPlayerColor;
    @FXML
    private ColorPicker secondPlayerColor;
    @FXML
    private ComboBox<String> comboBoxBoardSize;
    @FXML
    private Label warning;

    private SettingsGameManager settingsGameManager;

    public SettingsWindowController() {
        this.settingsGameManager = new SettingsGameManager(propertyFileName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String firstPlayerColors = this.settingsGameManager.getFirstPlayerColor();
        String secondPlayerColors = this.settingsGameManager.getSecondPlayerColor();
        Enums.PlayersColors startFirstOptions = this.settingsGameManager.getStartFirstOptions();
        int size = this.settingsGameManager.getBoardSize();

        ObservableList<String> sizeOptions = FXCollections.observableArrayList();
        for (int i = 4; i <= 20; i = i + 2) {
            sizeOptions.add(i + "x" + i);
        }
        this.comboBoxBoardSize.setItems(sizeOptions);
        this.comboBoxBoardSize.setValue(size + "x" + size);

        firstPlayerColor.setValue(Color.BLACK);
        secondPlayerColor.setValue(Color.WHITE);

    }

    @FXML
    private void changeStartFirstPlayerBlack() {
        this.startsFirstBlack.setSelected(true);
        this.settingsGameManager.setStartFirstOptions(Enums.PlayersColors.Black);
    }

    @FXML
    private void changeStartFirstPlayerWhite() {
        this.startsFirstWhite.setSelected(true);
        this.settingsGameManager.setStartFirstOptions(Enums.PlayersColors.White);
    }

    @FXML
    private void changeBoardSize() {
        final int doubleDigitSizeString = 5;
        String boardSize = this.comboBoxBoardSize.getValue();
        String numberString;
        if (boardSize.length() == doubleDigitSizeString) {
            numberString = boardSize.substring(0, 2);
        } else {
            numberString = boardSize.substring(0, 1);
        }
        int boardSizeInt = Integer.parseInt(numberString);
        this.settingsGameManager.setBoardSize(boardSizeInt);
    }

    @FXML
    private void save() {
        String firstColor = firstPlayerColor.getValue().toString();
        String secondColor = secondPlayerColor.getValue().toString();

        if (firstPlayerColor.getValue().toString().compareTo(
                secondPlayerColor.getValue().toString()) == 0) {
            this.warning.setText("Players can't use the same color!");
        } else {
            try {
                this.settingsGameManager.setFirstPlayerColor(firstColor);
                this.settingsGameManager.setSecondPlayerColor(secondColor);
                this.settingsGameManager.save();
;
            } catch (Exception e) {
                System.out.println("Can't save new file.");
            }
            // get a handle to the stage
            Stage stage = (Stage) comboBoxBoardSize.getScene().getWindow();
            // do what you have to do
            stage.close();
        }
    }
}
