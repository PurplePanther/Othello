import java.util.*;

public class GameLogic {

    private ReversiBoard board;

    /**
     * Constructor.
     *
     * @param board board to play on.
     */
    public GameLogic(ReversiBoard board) {
        this.board = board;
    }

    Map<Cell, List<Cell>> getPossibleGameMoves(List<Cell> playerMoves, Enums.PlayersColors playerColor) {
        Map<Cell, List<Cell>> possibleMoves = new HashMap<>();
        Cell resultCoordinate;
        //For each player move, create vector of it's possible flip symbols of opponent.
        for (int i = 0; i < playerMoves.size(); i++) {
            List<Cell> movementsForCurrentMove = new ArrayList<>();
            //Get column.
            int column = playerMoves.get(i).getColumn();
            //Get row.
            int row = playerMoves.get(i).getRow();
            //Check right possible plays.
            resultCoordinate = CheckByDirection(row, column, Enums.DirectionCheck.Right, playerColor);
            if ((resultCoordinate.getRow() != -1) && (resultCoordinate.getColumn() != -1)) {
                movementsForCurrentMove.add(resultCoordinate);
            }
            //Check left possible plays.
            resultCoordinate = CheckByDirection(row, column, Enums.DirectionCheck.Left, playerColor);
            if ((resultCoordinate.getRow() != -1) && (resultCoordinate.getColumn() != -1)) {
                movementsForCurrentMove.add(resultCoordinate);
            }
            //Check up possible plays.
            resultCoordinate = CheckByDirection(row, column, Enums.DirectionCheck.Up, playerColor);
            if ((resultCoordinate.getRow() != -1) && (resultCoordinate.getColumn() != -1)) {
                movementsForCurrentMove.add(resultCoordinate);
            }
            //Check down possible plays.
            resultCoordinate = CheckByDirection(row, column, Enums.DirectionCheck.Down, playerColor);
            if ((resultCoordinate.getRow() != -1) && (resultCoordinate.getColumn() != -1)) {
                movementsForCurrentMove.add(resultCoordinate);
            }
            //Check up left possible plays.
            resultCoordinate = CheckByDirection(row, column, Enums.DirectionCheck.UpLeft, playerColor);
            if ((resultCoordinate.getRow() != -1) && (resultCoordinate.getColumn() != -1)) {
                movementsForCurrentMove.add(resultCoordinate);
            }
            //Check down right possible plays.
            resultCoordinate = CheckByDirection(row, column, Enums.DirectionCheck.DownRight, playerColor);
            if ((resultCoordinate.getRow() != -1) && (resultCoordinate.getColumn() != -1)) {
                movementsForCurrentMove.add(resultCoordinate);
            }
            //Check up right possible plays.
            resultCoordinate = CheckByDirection(row, column, Enums.DirectionCheck.UpRight, playerColor);
            if ((resultCoordinate.getRow() != -1) && (resultCoordinate.getColumn() != -1)) {
                movementsForCurrentMove.add(resultCoordinate);
            }
            //Check down left possible plays.
            resultCoordinate = CheckByDirection(row, column, Enums.DirectionCheck.DownLeft, playerColor);
            if ((resultCoordinate.getRow() != -1) && (resultCoordinate.getColumn() != -1)) {
                movementsForCurrentMove.add(resultCoordinate);
            }
            //Sort moves.
            Collections.sort(movementsForCurrentMove);
            //Add it to map by relevant move.
            possibleMoves.put(playerMoves.get(i), movementsForCurrentMove);
        }
        //Return possible moves.
        return possibleMoves;

    }

    List<Cell> flipSymbols(Map<Cell, List<Cell>> allChoices, Cell wantedChoice, Enums.PlayersColors playerColor) {
        List<Cell> removePlaces = new ArrayList<>();
        //For each coordinate check if player choice is in it and flip the row.
        for (Map.Entry<Cell, List<Cell>> moves : allChoices.entrySet()) {
            //Get start coordinate.
            Cell fromCoordinate = moves.getKey();
            //Get all possible moves.
            List<Cell> availableMoves = moves.getValue();
            //Check if wanted choice is in available moves.
            if (availableMoves.contains(wantedChoice)) {
                //Flip wanted row.
                List<Cell> singleRow = singleRowToFlip(fromCoordinate,
                        wantedChoice, playerColor);
                flipOnBoard(singleRow, playerColor);
                //insert flipped row into remove places vector.
                for (int i = 0; i < singleRow.size(); ++i) {
                    if (!(removePlaces.contains(singleRow.get(i)))) {
                        removePlaces.add(singleRow.get(i));
                    }
                }

            }
        }
        return removePlaces;
    }


    /**
     * Check possible moves by direction.
     *
     * @param startRow     row from where to look
     * @param startColumn  column from where to look
     * @param direction    for looking
     * @param playerColor player symbol to know what not to look.
     * @return possible moves.
     */
    private Cell CheckByDirection(int startRow, int startColumn,
                                  Enums.DirectionCheck direction, Enums.PlayersColors playerColor) {
        int rowStep, columnStep, counter = 0;
        //Set by direction steps of row and column.
        switch (direction) {
            case Up: {
                rowStep = -1;
                columnStep = 0;
                break;
            }
            case Down: {
                rowStep = 1;
                columnStep = 0;
                break;
            }
            case Left: {
                rowStep = 0;
                columnStep = -1;
                break;
            }
            case Right: {
                rowStep = 0;
                columnStep = 1;
                break;
            }
            case UpLeft: {
                rowStep = -1;
                columnStep = -1;
                break;
            }
            case UpRight: {
                rowStep = -1;
                columnStep = 1;
                break;
            }
            case DownRight: {
                rowStep = 1;
                columnStep = 1;
                break;
            }
            case DownLeft: {
                rowStep = 1;
                columnStep = -1;
                break;
            }
            default: {
                rowStep = 1;
                columnStep = 1;
                break;
            }
        }

        int rowMovement = startRow + rowStep;
        int columnMovement = startColumn + columnStep;
        Enums.PlayersColors currentSymbol = null;
        //Check if wanted row and col to check is valid.
        if (this.board.isOnBoard(rowMovement, columnMovement)) {
            //Run on board while you see opponent symbol.
            do {
                currentSymbol = this.board.getSymbolByPlace(rowMovement, columnMovement);
                rowMovement = rowMovement + rowStep;
                columnMovement = columnMovement + columnStep;
                counter++;
            } while (this.board.isOnBoard(rowMovement, columnMovement)
                    && (currentSymbol != playerColor)
                    && (currentSymbol != Enums.PlayersColors.NoColor));
        }
        //Check if coordinate is valid and return it.
        if (this.board.isOnBoard(rowMovement - rowStep, columnMovement - columnStep)
                && (counter > 1) && (currentSymbol == Enums.PlayersColors.NoColor)) {
            return new Cell(rowMovement - rowStep, columnMovement - columnStep);
        } else {
            //Mark that BoardCoordinate isn't valid.
            return new Cell(-1, -1);
        }
    }

    /**
     * Return single row that can be flipped.
     *
     * @param start        from where to flip.
     * @param end          where to flip.
     * @param playerColor symbol to put instead.
     * @return all board coordinates that flipped.
     */
    List<Cell> singleRowToFlip(Cell start,
                               Cell end, Enums.PlayersColors playerColor) {
        List<Cell> flipCoordinates = new ArrayList<>();
        //Get difference of row and col to know wanted direction.
        int rowDiff = start.getRow() - end.getRow();
        int colDiff = start.getColumn() - end.getColumn();

        int stepRow = 0, stepCol = 0, numberOfFlips = 0;

        //Set step row by the difference.
        if (rowDiff < 0) {
            stepRow = 1;
        } else if (rowDiff > 0) {
            stepRow = -1;
        }
        //Set step col by the difference.
        if (colDiff < 0) {
            stepCol = 1;
        } else if (colDiff > 0) {
            stepCol = -1;
        }

        //Check how many flips we need to do.
        if (colDiff != 0) {
            numberOfFlips = Math.abs(colDiff);
        } else {
            numberOfFlips = Math.abs(rowDiff);
        }

        int rowMovement = start.getRow();
        int colMovement = start.getColumn();
        int flipCounter = 0;
        //Flip symbols
        do {
            //Step by row.
            rowMovement = rowMovement + stepRow;
            //Step by column.
            colMovement = colMovement + stepCol;
            //Put symbols in wanted place on board.
            //getBoard().putSymbolOnBoard(rowMovement, colMovement, playerSymbol);
            //Remember flipped coordinates.
            flipCoordinates.add(new Cell(rowMovement, colMovement));
            flipCounter++;
        } while (flipCounter < numberOfFlips);
        //Return all flipped coordinates.
        return flipCoordinates;

    }


    protected void flipOnBoard(List<Cell> coordinatesToFlip, Enums.PlayersColors playerColor) {
        for (int i = 0; i < coordinatesToFlip.size(); i++) {
            board.putSymbolOnBoard(coordinatesToFlip.get(i).getRow(),
                    coordinatesToFlip.get(i).getColumn(), playerColor);
        }
    }

}
