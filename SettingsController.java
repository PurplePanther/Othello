import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    // the variables.
    @FXML
    ComboBox<String> startingPlayer,boardSize;

    @FXML
    ColorPicker player1ColorPicker,player2ColorPicker;

    String player1Color,player2Color,startingPlayerString;

    /**
     * The init function which initializes the class.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startingPlayer.setValue("Player1");
        this.startingPlayer.getItems().add("Player1");
        this.startingPlayer.getItems().add("Player2");

        this.boardSize.setValue("4x4");
        for(int i=4; i<=20;i++){
            this.boardSize.getItems().add(i + "x" + i);

        }

        player1ColorPicker.setValue(Color.BLACK);
        player2ColorPicker.setValue(Color.WHITE);


        ArrayList<String> defaultSettings = loadFromFile();

        int boardSizeTemp = Integer.valueOf(defaultSettings.get(3));
        boardSize.setValue(boardSizeTemp + "x" + boardSizeTemp);

        startingPlayer.setValue(defaultSettings.get(2));

        player1ColorPicker.setValue(Color.valueOf(defaultSettings.get(0)));
        player2ColorPicker.setValue(Color.valueOf(defaultSettings.get(1)));


    }


    /**
     * The function saves the chosen settings to file.
     */
    public void saveToFile(){
        String charBoardSize;
        String FILEPATH = "settings.txt";

        // opening the file.
        File file = new File(FILEPATH);
        BufferedWriter bufferedWriter = null;

        String tempBoardSize = this.boardSize.getValue().toString();
        if(tempBoardSize.length() == 3) {
            charBoardSize = tempBoardSize.substring(0, 1);
        }else {
            charBoardSize = tempBoardSize.substring(0, 2);

        }
        player1Color = player1ColorPicker.getValue().toString();
        player2Color = player2ColorPicker.getValue().toString();

        startingPlayerString = startingPlayer.getValue().toString();


        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            Writer writer = bufferedWriter;
            // writing first player color
            writer.write("Player1 Color: " + player1Color + "\n");
            // writing second player color
            writer.write("Player2 Color: " + player2Color + "\n");
            // writing starting player
            writer.write("Starting Player: " + startingPlayerString + "\n");
            // writing board size
            writer.write("Size: " + charBoardSize + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        // get a handle to the stage
        Stage stage = (Stage) startingPlayer.getScene().getWindow();
        // do what you have to do
        stage.close();
    }


    /**
     * The function loads the chosen settings from file and returns them.
     * @return - an arraylist of strings containing the loaded settings.
     */
    public static ArrayList<String> loadFromFile(){
        String FILEPATH = "settings.txt";
        ArrayList<String> options = new ArrayList<>();
    // opening the file.
        File file = null;
        try {
            file = new File(FILEPATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileReader fileReader;
        if(file.exists()) {
            String lineRead;
            BufferedReader bufferedReader = null;
            try {
                fileReader = new FileReader(FILEPATH);
                bufferedReader = new BufferedReader(fileReader);
                // reading player1 color
                lineRead = bufferedReader.readLine();
                lineRead = lineRead.replace("Player1 Color: ", "");
                options.add(lineRead);
                // reading player2 color
                lineRead = bufferedReader.readLine();
                lineRead = lineRead.replace("Player2 Color: ", "");
                options.add(lineRead);
                lineRead = bufferedReader.readLine();
                // reading starting player
                lineRead = lineRead.replace("Starting Player: ", "");
                options.add(lineRead);
                lineRead = bufferedReader.readLine();
                // reading board size
                lineRead = lineRead.replace("Size: ", "");
                options.add(lineRead);
            } catch (IOException e) {
                System.out.println("Error reading from file");
            } finally {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    System.out.println("Error closing the file");
                }
            }
        }else{
            options.add("#000000");
            options.add("#ffffff");
            options.add("Player1");
            options.add(4 + "");
        }

        return options;
    }
}
