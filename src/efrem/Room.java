package efrem;

public class Room {
    private Coordinate coordinate;
    private Connection connection;

    public Room() {
    }

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
}

