import java.util.Scanner;

public class OfflinePlayer extends Player {

    public OfflinePlayer(int color) {
        super(color);
    }

    @Override
    Cell chooseMove() {

        System.out.print( "Please enter your move row col (for example 3 5): ");
        Scanner reader = new Scanner(System.in);
        int x = reader.nextInt();
        int y = reader.nextInt();
        Cell chosenXY = new Cell(x,y);
        return chosenXY;
    }
}
