package efrem;

public class Room {
    private Coordinate coordinate;
    private Connection connection;

    public Room() {
    }

    /**
     *
     * @param coordinate  the location of the room
     * @param connection has a sub class where all the directions we can navigate are stored
     */


    public Room(Coordinate coordinate, Connection connection) {
        this.coordinate = coordinate;
        this.connection = connection;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     *
     * @return this will give you the coordinate anf the connection in String form
     */
    @Override
    public String toString() {
        return
                "  coordinate =" + coordinate + "\n"+
                "  connection =" + connection + "\n"
                ;
    }
}

