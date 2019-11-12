package jindanupajit;

import efrem.Connection;
import efrem.Coordinate;
import efrem.Room;

import java.util.Random;

/**
 *
 */
public class RoomGenerator {

    RoomDatabase roomDatabase = new RoomDatabase();
    Integer maxRoom;

    public RoomGenerator() {
    }

    public RoomGenerator(RoomDatabase roomDatabase, Integer maxRoom) {
        this.roomDatabase = roomDatabase;
        this.maxRoom = maxRoom;
    }


    public RoomDatabase getRoomDatabase() {
        return roomDatabase;
    }

    public void setRoomDatabase(RoomDatabase roomDatabase) {
        this.roomDatabase = roomDatabase;
    }

    public Integer getMaxRoom() {
        return maxRoom;
    }

    /**
     * To set maximum room number
     * @param maxRoom Maximum room number
     */
    public void setMaxRoom(Integer maxRoom) {
        // Correct maxRoom number out of bound
        // must less or equal than width * height
        this.maxRoom = Math.min(maxRoom, roomDatabase.getWidth()*roomDatabase.getHeight());

    }


    /**
     * Generate random Room database
     */
    public void generate() {
        Random r = new Random();


        if ( (roomDatabase.getWidth() == 0) || (roomDatabase.getHeight() == 0) )
            return;
        Integer width = roomDatabase.getWidth();
        Integer height = roomDatabase.getHeight();
        Integer maxRoom = this.maxRoom;


        for (int loc = 0; loc < maxRoom ; loc++) {
            Room newRoom = new Room();


            if (loc == 0) {
                /**
                 * The first room is randomly generate
                 */
                int x = r.nextInt(width);
                int y = r.nextInt(height);
                Coordinate xy = new Coordinate(x,y);

                newRoom.setCoordinate(xy);
                roomDatabase.add(newRoom);
            }
            else {
                /**
                 * The next room is randomly select from direction (N,S,E,W)
                 *
                 */
                Room currentRoom = roomDatabase.getRandomRoom();
                int direction  = r.nextInt(4);
                newRoom.setCoordinate(new Coordinate());
                newRoom.getCoordinate().setX(currentRoom.getCoordinate().getX());
                newRoom.getCoordinate().setY(currentRoom.getCoordinate().getY());

                switch(direction) {
                    case 0: // NORTH

                        if (currentRoom.getCoordinate().getY() > 0)
                            newRoom.getCoordinate().setY(currentRoom.getCoordinate().getY()-1);
                        else
                            newRoom = null;
                        break;
                    case 1: // SOUTH

                        if (currentRoom.getCoordinate().getY() < (height-1))
                            newRoom.getCoordinate().setY(currentRoom.getCoordinate().getY()+1);
                        else
                            newRoom = null;
                        break;
                    case 2: // EAST

                        if (currentRoom.getCoordinate().getX() < (width-1))
                            newRoom.getCoordinate().setX(currentRoom.getCoordinate().getX()+1);
                        else
                            newRoom = null;
                        break;
                    case 3: // WEST

                        if (currentRoom.getCoordinate().getX() > 0)
                            newRoom.getCoordinate().setX(currentRoom.getCoordinate().getX()-1);
                        else
                            newRoom = null;
                        break;
                }


                if (newRoom == null) {
                    /**
                     * The newRoom is out of bound
                     */
                    loc--;

                }
                else if ( roomDatabase.containsKey(newRoom.getCoordinate().getX(), newRoom.getCoordinate().getY()) ) {
                    /**
                     * The newRoom is duplicate
                     */
                    loc--;

                }
                else {
                    /**
                     * Add newRoom to database
                     */
                    roomDatabase.add(newRoom);

                }

            }
        }

        /**
         * Link each room to their neighboors
         */
        roomDatabase.forEach(
                (key, room) -> {
                    Coordinate xy =  roomDatabase.getCoordinate(key);
                    room.setConnection(new Connection());

                    if (xy.getY() > 0) { // NORTH
                        if (roomDatabase.hasRoomAt(xy.getNorth())) {
                            room.getConnection().setNorth(xy.getNorth());
                        }
                    }

                    if (xy.getY() < (height-1)) { // SOUTH
                        if (roomDatabase.hasRoomAt(xy.getSouth())){
                            room.getConnection().setSouth(xy.getSouth());
                        }
                    }

                    if (xy.getX() < (width-1)) { // EAST
                        if (roomDatabase.hasRoomAt(xy.getEast())) {
                            room.getConnection().setEast(xy.getEast());
                        }
                    }

                    if (xy.getX() > 0) { // WEST
                        if (roomDatabase.hasRoomAt(xy.getWest())) {
                            room.getConnection().setWest(xy.getWest());

                        }
                    }
                }
        );
    }
}
