package jindanupajit;

import efrem.Coordinate;
import efrem.Room;

public class Player {

    private Room currentRoom = new Room();
    private RoomDatabase roomDatabase = new RoomDatabase();

    public Player() {
    }

    public Player(Room currentRoom, RoomDatabase roomDatabase) {
        this.currentRoom = currentRoom;
        this.roomDatabase = roomDatabase;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public int getRoomKey() {
        return roomDatabase.getKey(getCurrentRoom());
    }

    public String where() {
        return String.format("%d",getRoomKey()+1);
    }

    public RoomDatabase getRoomDatabase() {
        return roomDatabase;
    }

    public void setRoomDatabase(RoomDatabase roomDatabase) {
        this.roomDatabase = roomDatabase;
    }



    public boolean canMoveTo(Coordinate xy) {

        if (xy == null) // null coordinate
            return false;

        else if (xy.getX() < 0)  // out of bound
            return false;

        else if (xy.getY() < 0) // out of bound
            return false;

        else if (!roomDatabase.containsKey(xy)) // not in database  key
            return false;

        else if (roomDatabase.get(xy) == null) // database value is null
            return false;

        else
            return true;
    }
}
