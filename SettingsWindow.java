import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class SettingsWindow {

    // defaults values for the game.
    private static final String FIRSTPLAYER = "Player1";
    private static final String FIRSTPLAYERCOLOR = "#000000";
    private static final String SECONDPLAYERCOLOR = "#ffffff";
    private static final int DEFAULTBOARDSIZE = 4;

    private static final String FILEPATH = "src/settings.txt";


    private static ComboBox<String> startingPlayerComboBox = new ComboBox<>();
    private static ComboBox<String> boardSizeComboBox = new ComboBox<>();
    private static ColorPicker firstPlayerColorPicker = new ColorPicker();
    private static ColorPicker secondPlayerColorPicker = new ColorPicker();

    private Label boardSizeLabel = new Label();


    public void displaySettingsWindow() {
        int windowHeight,windowWidth;
        windowHeight = 400;
        windowWidth = 300;

        //setting up the stage.
        Stage settingsWindow = new Stage();

        settingsWindow.initModality(Modality.APPLICATION_MODAL);
        settingsWindow.setTitle("settings");
        settingsWindow.setResizable(false);

        //displaying the gui.
        Label title = new Label("Settings");
        title.setFont(new Font(50.0));


        Label startingPlayer = new Label("select who's the first player:");
        startingPlayer.setFont(new Font(15.0));
        startingPlayerComboBox.setValue("Player1");
        startingPlayerComboBox.getItems().clear();
        startingPlayerComboBox.getItems().add("Player1");
        startingPlayerComboBox.getItems().add("Player2");


        Label gameBoardSize = new Label("select the board size:");
        gameBoardSize.setFont(new Font(15.0));
        boardSizeComboBox.setValue("2x2");
        boardSizeComboBox.getItems().clear();
        for (int i = 4; i <= 20 ; i++){
            boardSizeComboBox.getItems().add(i+"x"+i);
        }


        Label playerOneColor = new Label("select player1 color:");
        playerOneColor.setFont(new Font(15.0));
        firstPlayerColorPicker.setValue(Color.BLACK);


        Label playerTwoColor = new Label("select player2 color:");
        playerTwoColor.setFont(new Font(15.0));
        secondPlayerColorPicker.setValue(Color.WHITE);

        //loading the current settings from file.
        loadSettings();


        // setting the button that closes the window.
        Button saveButton = new Button("Close & save");
        // set action to the "save & close" button.
        saveButton.setOnAction(e -> {
            String tempBoardSize = boardSizeComboBox.getValue().toString();
            String charSize = tempBoardSize.substring(0,1);
            int boardSizeTemp = Integer.parseInt(charSize);
            // write the current settings into the settings file.
            saveSettings(boardSizeTemp, startingPlayerComboBox.getValue().toString(),
                    firstPlayerColorPicker.getValue().toString(),
                    secondPlayerColorPicker.getValue().toString());
            settingsWindow.close();
        });


        // setting the layout of the window.
        VBox layout = new VBox(15);
        Scene scene = new Scene(layout, windowWidth, windowHeight);
        settingsWindow.setScene(scene);


        VBox innerLayout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        innerLayout.setAlignment(Pos.CENTER);

        // add all of the wanted objects to the layouts
        innerLayout.getChildren().add(saveButton);

        layout.getChildren().addAll(title, startingPlayer,startingPlayerComboBox, playerOneColor,
                firstPlayerColorPicker, playerTwoColor, secondPlayerColorPicker,
                gameBoardSize,boardSizeComboBox, innerLayout);

        // set the windows scene to be the layout

        // display window
        settingsWindow.showAndWait();

    }


    private static void saveSettings(int boardSize,String startingPlayer,
                                     String player1Color,String player2Color ){
        // opening the file.
        File file = new File(FILEPATH);
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            Writer writer = bufferedWriter;
            // writing first player color
            writer.write("Player1 Color: " + player1Color + "\n");
            // writing second player color
            writer.write("Player2 Color: " + player2Color + "\n");
            // writing starting player
            writer.write("Starting Player: " + startingPlayer + "\n");
            // writing board size
            writer.write("Size: " + boardSize + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static void loadSettings(){
        // opening the file.
        File file = new File(FILEPATH);
        FileReader fileReader;
        if(file.exists()) {
            String player1Color, player2Color, startingPlayer, lineRead;
            BufferedReader bufferedReader = null;
            try {
                fileReader = new FileReader(FILEPATH);
                bufferedReader = new BufferedReader(fileReader);
                // reading first player color
                lineRead = bufferedReader.readLine();
                lineRead = lineRead.replace("Player1 Color: ", "");
                player1Color = lineRead;
                // reading second player color
                lineRead = bufferedReader.readLine();
                lineRead = lineRead.replace("Player2 Color: ", "");
                player2Color = lineRead;
                lineRead = bufferedReader.readLine();
                // reading starting player
                lineRead = lineRead.replace("Starting Player: ", "");
                startingPlayer = lineRead;
                lineRead = bufferedReader.readLine();
                // reading board size
                lineRead = lineRead.replace("Size: ", "");
                // set values
                boardSizeComboBox.setValue(Integer.valueOf(lineRead)+"x"+Integer.valueOf(lineRead));
                startingPlayerComboBox.setValue(startingPlayer);
                firstPlayerColorPicker.setValue(Color.valueOf(player1Color));
                secondPlayerColorPicker.setValue(Color.valueOf(player2Color));

            } catch (IOException e) {
                System.out.println("Error reading from file");
            } finally {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    System.out.println("Error closing the file");
                }
            }
                   // if the file is empty
        } else {
            saveSettings((int)DEFAULTBOARDSIZE,"Player1",
                    "#000000", "#ffffff");

        }
        return ;
    }


    }
