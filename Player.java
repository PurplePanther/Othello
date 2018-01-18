import javafx.scene.paint.Color;

import java.util.*;

public class Player {

    protected Enums.PlayersColors sign;
    protected ReversiBoard board;
    protected List<Cell> playerMoves;
    protected GameLogic gameLogic;
    private Color color;

    public Color getColor() {
        return color;
    }

    Player(Enums.PlayersColors color, Color playersColor, ReversiBoard board,
           GameLogic gameLogic) {
        this.sign = color;
        this.gameLogic = gameLogic;
        this.board = board;
        this.color = playersColor;
        this.playerMoves = new ArrayList<>();
    }


    void addPlayerSymbol(int row, int column) {
        //Create new coordinate.
        Cell newCoordinate = new Cell(row, column);
        //Insert it to player moves.
        playerMoves.add(newCoordinate);
        //Put this symbol on board.
        board.putSymbolOnBoard(row, column, this.sign);
    }

    void updatePlayerSymbolRemoved(List<Cell> removeCoordinates) {
        //Remove each coordinate from vector.
        for (int i = 0; i < removeCoordinates.size(); ++i) {
            if (playerMoves.contains(removeCoordinates.get(i))) {
                playerMoves.remove(removeCoordinates.get(i));
            }
        }
    }

    /**
     * Gets add coordinates and add it to player moves vector.
     *
     * @param addCoordinates coordinates to add.
     */
    void updatePlayerSymbolAdd(List<Cell> addCoordinates) {
        //Insert coordinates
        for (int i = 0; i < addCoordinates.size(); ++i) {
            if (!this.playerMoves.contains(addCoordinates.get(i))) {
                this.playerMoves.add(addCoordinates.get(i));
            }
        }
    }

    int getScore() {
        return this.playerMoves.size();
    }

    Map<Cell, List<Cell>> playOneTurn(Cell cell) {
        //Print who it's turn to play.
        //this.display.printPlayerTurn(getSymbol());
        //Get map of all possible moves.
        Map<Cell, List<Cell>> possibleMoves = this.gameLogic.getPossibleGameMoves(playerMoves,
                sign);
        List<Cell> allMoves = new ArrayList<>();
        List<Cell> flippedSymbols = new ArrayList<>();
        Map<Cell, List<Cell>> playerMove = new HashMap<>();

        //For board coordinate, get its possible move and insert in to all moves vector.

        for (Map.Entry<Cell, List<Cell>> moves : possibleMoves.entrySet()) {
            for (int i = 0; i < moves.getValue().size(); i++) {
                if (!allMoves.contains(moves.getValue().get(i))) {
                    allMoves.add(moves.getValue().get(i));
                }
            }
        }

        //Check if there are no possible moves and notify player about it.
        if (allMoves.isEmpty()) {
            //this.display.printMessage("No possible moves. Play passes back to the other player" +
            //        ". Press enter to continue.");
            //cin.ignore();
            //Return empty map.
            return playerMove;
        }

        //Print all possible moves.
        //this.display.printPossibleMoves(allMoves);
        //printPossibleMoves(allMoves);
        //Get player choice.
        //Cell playerChoice = getPlayerChoice(allMoves);

        //Get flipped symbols vector.
        flippedSymbols = this.gameLogic.flipSymbols(possibleMoves,
                cell, getSymbol());
        //Return them.
        playerMove.put(cell, flippedSymbols);
        return playerMove;
    }

    Boolean checkIfValidMove(Cell coordinates) {
        //Print who it's turn to play.
        //this.display.printPlayerTurn(getSymbol());
        //Get map of all possible moves.
        Map<Cell, List<Cell>> possibleMoves = this.gameLogic.getPossibleGameMoves(playerMoves,
                sign);
        List<Cell> allMoves = new ArrayList<>();
        List<Cell> flippedSymbols = new ArrayList<>();
        Map<Cell, List<Cell>> playerMove = new HashMap<>();

        //For board coordinate, get its possible move and insert in to all moves vector.

        for (Map.Entry<Cell, List<Cell>> moves : possibleMoves.entrySet()) {
            for (int i = 0; i < moves.getValue().size(); i++) {
                if (!allMoves.contains(moves.getValue().get(i))) {
                    allMoves.add(moves.getValue().get(i));
                }
            }
        }

        return allMoves.contains(coordinates);
    }

    Boolean isThereMoves() {
        //Print who it's turn to play.
        //this.display.printPlayerTurn(getSymbol());
        //Get map of all possible moves.
        Map<Cell, List<Cell>> possibleMoves = this.gameLogic.getPossibleGameMoves(playerMoves,
                sign);
        List<Cell> allMoves = new ArrayList<>();
        List<Cell> flippedSymbols = new ArrayList<>();
        Map<Cell, List<Cell>> playerMove = new HashMap<>();

        //For board coordinate, get its possible move and insert in to all moves vector.

        for (Map.Entry<Cell, List<Cell>> moves : possibleMoves.entrySet()) {
            for (int i = 0; i < moves.getValue().size(); i++) {
                if (!allMoves.contains(moves.getValue().get(i))) {
                    allMoves.add(moves.getValue().get(i));
                }
            }
        }

        return (!allMoves.isEmpty());
    }

    Enums.PlayersColors getSymbol() {
        return this.sign;
    }


}

