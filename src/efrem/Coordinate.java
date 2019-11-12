package efrem;

public class Coordinate implements Comparable <Coordinate>{
    private int x;
    private int y;

    public Coordinate() {
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int compareTo(Coordinate otherXY) {
        if (y < otherXY.y) return 1;
        if (x < otherXY.x) return 1;
        if (y > otherXY.y) return -1;
        if (x > otherXY.x) return -1;
        return 0;
    }
}
