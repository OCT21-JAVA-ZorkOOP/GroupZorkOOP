package efrem;

/**
 * Connections has north, south, east and west variable
 */
public class Connection {
    private Coordinate north;
    private Coordinate south;
    private Coordinate east;
    private Coordinate west;

    public Connection(Coordinate north, Coordinate south, Coordinate east, Coordinate west) {
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }


    public Connection() {
    }

    public Coordinate getNorth() {
        return north;
    }

    public void setNorth(Coordinate north) {
        this.north = north;
    }

    public Coordinate getSouth() {
        return south;
    }

    public void setSouth(Coordinate south) {
        this.south = south;
    }

    public Coordinate getEast() {
        return east;
    }

    public void setEast(Coordinate east) {
        this.east = east;
    }

    public Coordinate getWest() {
        return west;
    }

    public void setWest(Coordinate west) {
        this.west = west;
    }

    /**
     *
     * @return this will return the directions in string format as north,south,east,west
     */
    @Override
    public String toString() {
        return String.format("N=%s, S=%s, E=%s, W=%s", north,south,east,west);
    }
}
