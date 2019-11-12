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

    public RoomDatabase getRoomDatabase() {
        return roomDatabase;
    }

    public void setRoomDatabase(RoomDatabase roomDatabase) {
        this.roomDatabase = roomDatabase;
    }



    public boolean canMoveTo(Coordinate xy) {
        if (xy.getX() < 0) return false;
        if (xy.getY() < 0) return false;
        if (!roomDatabase.containsKey(xy.getX(), xy.getY()))
            return false;

        if ((currentRoom.getConnection().getNorth() != null) &&
            (currentRoom.getConnection().getNorth().compareTo(xy) == 0)) {
               currentRoom = roomDatabase.get(xy.getX(), xy.getY());
               return true;
        }

        if ((currentRoom.getConnection().getSouth() != null) &&
                (currentRoom.getConnection().getSouth().compareTo(xy) == 0)) {
            currentRoom = roomDatabase.get(xy.getX(), xy.getY());
            return true;
        }

        if ((currentRoom.getConnection().getEast() != null) &&
                (currentRoom.getConnection().getEast().compareTo(xy) == 0)) {
            currentRoom = roomDatabase.get(xy.getX(), xy.getY());
            return true;
        }

        if ((currentRoom.getConnection().getWest() != null) &&
                (currentRoom.getConnection().getWest().compareTo(xy) == 0)) {
            currentRoom = roomDatabase.get(xy.getX(), xy.getY());
            return true;
        }

        return false;
    }
}
