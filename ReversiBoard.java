import java.util.ArrayList;

public class ReversiBoard {
    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    //board size X*Y.
    int sizeX, sizeY;

    //2D array of Cells.
    Cell[][] gameBoard;

    public Cell[][] getGameBoard() {
        return gameBoard;
    }

    public ReversiBoard(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        this.gameBoard = new Cell[sizeX][sizeY];
        
        intiBoard();
        
    }

    private void intiBoard() {
        for (int i = 0; i < sizeX; i++) {
            for(int j = 0; j < sizeY; j++) {
                this.gameBoard[i][j] = new Cell(i,j);
                this.gameBoard[i][j].setContainingValue(false);
                this.gameBoard[i][j].setColor(-1);
            }
        }

        int mid=sizeX/2;
        //starting position of O's
        this.gameBoard[mid-1][mid-1].setContainingValue(true);
        this.gameBoard[mid][mid].setContainingValue(true);
        this.gameBoard[mid-1][mid-1].setColor(0);
        this.gameBoard[mid][mid].setColor(0);

        //starting position of X's
        this.gameBoard[mid-1][mid].setContainingValue(true);
        this.gameBoard[mid][mid-1].setContainingValue(true);
        this.gameBoard[mid-1][mid].setColor(1);
        this.gameBoard[mid][mid-1].setColor(1);

    }

    private boolean pointIsValid(int x, int y) {
        if( x < 0 || y < 0 ||
                x >= this.sizeX || y >= this.sizeY){
            return false;
        }

        return true;
    }



    public void flipCellsBetween(Cell x, Cell y){
        int directionX = 0;
        int directionY = 0;
        int cellXX = x.getX();
        int cellYX = y.getX();
        //getting direction X.
        if (cellXX > cellYX) {
            directionX = -1;
        } else if(cellXX < cellYX) {
            directionX = 1;
        } else {
            directionX = 0;
        }

        int cellXY = x.getY();
        int cellYY = y.getY();
        //getting direction Y.
        if (cellXY > cellYY) {
            directionY = -1;
        } else if(x.getY() < y.getY()) {
            directionY = 1;
        } else {
            directionY = 0;
        }

        while((cellYX != cellXX) || (cellXY != cellYY)) {
            cellXX+=directionX;
            cellXY+=directionY;
            if(x.getColor() == 1) {
                this.gameBoard[cellXX][cellXY].setColor(1);
            }else{
                this.gameBoard[cellXX][cellXY].setColor(0);
            }
        }
    }


    public void placePiece(int color, int positionX, int positionY){
        ArrayList<Cell> listConnections =  connectionsWith(gameBoard[positionX-1][positionY-1],color);
        this.gameBoard[positionX-1][positionY-1].setColor(color);
        this.gameBoard[positionX-1][positionY-1].setContainingValue(true);
        int i = 0;
        for (Cell currentCell: listConnections){
            this.flipCellsBetween(gameBoard[positionX-1][positionY-1],currentCell);
        }


        listConnections.clear();
    }

    public ArrayList<Cell> connectionsWith(Cell x, int color){
        ArrayList<Cell> Connections = new ArrayList<Cell>();

        //checking if the cell is a valid cell.
        if(!this.isValidCell(x.getX(),x.getY(),color)){

            return Connections;
        } else {

            int currentX = x.getX();
            int currentY = x.getY();

            //checking if the cell we are looking at is currently empty.
            if(this.gameBoard[currentX][currentY].isEmpty()) {

                //checking for surrounding cells.
                for (int i = -1; i <= 1 ; ++i){
                    for (int j = -1; j <= 1; ++j){
                        // check if surrounding cells are in the boundaries.
                        if(!this.pointIsValid(currentX+i,currentY+j) || (i==0 && j==0)) {
                            continue;
                        }
                        //we are making sure the current neighbor cell we are looking at isn't empty.
                        boolean isNeighborEmpty = this.gameBoard[currentX+i][currentY+j].isEmpty();

                        //if the neighbor cell is empty we move on to another neighbor cell.
                        if(isNeighborEmpty) {
                            continue;
                        }
                        //we are making sure the sign of the neighbor cell is different than ours.
                        boolean hasDiffSign = (this.gameBoard[currentX+i][currentY+j].getColor() != color);

                        if(!isNeighborEmpty && hasDiffSign){
                            boolean found = false;
                            int posX = currentX+i+i;
                            int posY = currentY+j+j;

                            if(!pointIsValid(posX,posY)){
                                continue; // testing continue v break;
                            }
                            boolean friendlyNeighbor = this.gameBoard[posX][posY].isEmpty();
                            //checking if there is a piece that we can possibly connect with so we can flip.
                            if(friendlyNeighbor) {
                                continue;
                            }

                            //checking if possible connection Cell is friendly.
                            boolean hasSameSign = (this.gameBoard[posX][posY].getColor() == color);
                            if(!friendlyNeighbor && hasSameSign){

                                // this cell can be played.
                                Connections.add(new Cell(posX,posY));
                            } else if (!hasSameSign) {
                                while (!found){
                                    posX = posX + i;
                                    posY = posY + j;

                                    if(!pointIsValid(posX,posY)){
                                        break;
                                    }
                                    if(this.gameBoard[posX][posY].getColor() == color) {
                                        Connections.add(new Cell(posX,posY));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return Connections;
    }

    public boolean isValidCell(int x, int y, int sign) {

        //if the cell we are looking for is out of boundaries.
        if(!pointIsValid(x,y)) {
            return false;
        }else{
            int currentX = x;
            int currentY = y;
            //checking if the cell we are looking at is empty.
            if(this.gameBoard[currentX][currentY].isEmpty()) {

                //checking for surrounding cells.
                for (int i = -1; i <= 1 ; ++i){
                    for (int j = -1; j <= 1; ++j){
                        // check if surrounding cells are in the boundaries.
                        if(!pointIsValid(currentX+i,currentY+j) || (i==0 && j==0)) {
                            continue;
                        }
                        //we are making sure the current neighbor cell we are looking at isn't empty.
                        boolean isNeighborEmpty = this.gameBoard[currentX+i][currentY+j].isEmpty();

                        //if the neighbor cell is empty we move on to another neighbor cell.
                        if(isNeighborEmpty) {
                            continue;
                        }
                        //we are making sure the sign of the neighbor cell is different than ours.
                        boolean hasDiffSign = (this.gameBoard[currentX+i][currentY+j].getColor() != sign);
                        if(!isNeighborEmpty && hasDiffSign){
                            boolean found = false;
                            int posX = currentX+i+i;
                            int posY = currentY+j+j;

                            if(!pointIsValid(posX,posY)) {
                                continue;
                            }

                            boolean friendlyNeighbor = this.gameBoard[posX][posY].isEmpty();

                            //checking if there is a piece that we can possibly connect with so we can flip.
                            if(friendlyNeighbor) {
                                continue;
                            }
                            //checking if possible connection Cell is friendly.
                            boolean hasSameSign = (this.gameBoard[posX][posY].getColor() == sign);
                            if(!friendlyNeighbor && hasSameSign){

                                // this cell can be played.
                                return true;
                            } else if (!hasSameSign) {
                                while (!found){
                                    posX += i;
                                    posY += j;
                                    if(!pointIsValid(posX,posY)){
                                        break;
                                    }
                                    if(this.gameBoard[posX][posY].getColor() == sign) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    public int countXCells() {
        int x = 0;
        for(int i = 0; i < this.sizeX ; ++i) {
            for(int j = 0; j < this.sizeY ; ++j) {
                if(!this.gameBoard[i][j].isEmpty()){
                    if(this.gameBoard[i][j].getColor() == 1){
                        x++;
                    }
                }
            }
        }
        return x;
    }

    public int countOCells() {
        int x = 0;
        for(int i = 0; i < this.sizeX ; ++i) {
            for(int j = 0; j < this.sizeY ; ++j) {
                if(!this.gameBoard[i][j].isEmpty()){
                    if(this.gameBoard[i][j].getColor() == 0){
                        x++;
                    }
                }
            }
        }
        return x;
    }


    public void printBoard() {
        System.out.println("Current board");
        //printing the row #'s.
        System.out.print(" " + "|");
        for (int i = 1; i <= this.sizeX ; ++i) {
            System.out.print(i + " | ");
        }
        //printing the line separation.
        System.out.println();
        for (int i = 0; i <= this.sizeX-1; ++i){
            System.out.print("----");
        }
        System.out.print("--");
        System.out.println();

        char y;
        for (int i = 0; i < this.sizeX ; ++i) {
            System.out.print(i+1 + " |");
            for (int j = 0; j < this.sizeY; ++j) {
                if(this.gameBoard[i][j].isEmpty()) {
                    y = ' ';
                }else if(this.gameBoard[i][j].getColor() == 1) {
                    y = 'X';
                }else{
                    y = 'O';
                }
                System.out.print(y + " | ");
            }

            //printing the line separation.
            System.out.println();
            for (int f = 0; f <= this.sizeX-1; ++f){
                System.out.print("----");
            }
            System.out.print("--");
            System.out.println();
            //printing the line separation.

        }
        return;
    }


    public boolean isFull(){
            if(this.countOCells() + this.countXCells() == this.sizeX*this.sizeY) {
                return true;
            }
            return false;
    }


    public ArrayList<Cell> possibleMoves(int sign) {
        ArrayList<Cell> possibleCells = new ArrayList<Cell>();

        // going over the entire board looking for possible moves.
        for (int i = 0; i < this.sizeX; i++) {
            for (int j = 0; j < this.sizeY; j++) {
                //checking is cell i,j is playable.
                if (this.isValidCell(i, j, sign)) {
                    possibleCells.add(new Cell(i, j));

                }
            }
        }
        return possibleCells;
    }

}
