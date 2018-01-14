
public class Cell {
    int color;
    int x,y;
    boolean containingValue;



    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.color = -1;
        this.containingValue = false;
    }

    public int getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isEmpty() {
        return !containingValue;
    }


    public void setColor(int color) {
        this.color = color;
    }

    public void setXY(int x,int y) {
        this.x = x;
        this.y = y;
    }


    public void setContainingValue(boolean containingValue) {
        this.containingValue = containingValue;
    }
}


