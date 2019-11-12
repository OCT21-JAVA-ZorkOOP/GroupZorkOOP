package jindanupajit;

import efrem.Connection;
import efrem.Coordinate;
import efrem.Room;

import java.util.Random;

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

    public void setMaxRoom(Integer maxRoom) {
        this.maxRoom = Math.min(maxRoom, roomDatabase.getWidth()*roomDatabase.getHeight());

    }

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
                int x = r.nextInt(width);
                int y = r.nextInt(height);
                Coordinate xy = new Coordinate(x,y);

                newRoom.setCoordinate(xy);
               // System.out.printf("First room %d,%d - generated\n", newRoom.getCoordinate().getX(), newRoom.getCoordinate().getY());
                roomDatabase.add(newRoom);
            }
            else {
                Room currentRoom = roomDatabase.getRandomRoom();
                int direction  = r.nextInt(4);
                newRoom.setCoordinate(new Coordinate());
                newRoom.getCoordinate().setX(currentRoom.getCoordinate().getX());
                newRoom.getCoordinate().setY(currentRoom.getCoordinate().getY());

                //System.out.printf("Current %d,%d (w %d,h %d, loc %d) - ", currentRoom.getCoordinate().getX(), currentRoom.getCoordinate().getY(), width, height, loc);
                switch(direction) {
                    case 0: // NORTH
                        //System.out.println("N");
                        if (currentRoom.getCoordinate().getY() > 0)
                            newRoom.getCoordinate().setY(currentRoom.getCoordinate().getY()-1);
                        else
                            newRoom = null;
                        break;
                    case 1: // SOUTH
                        //System.out.println("S");
                        if (currentRoom.getCoordinate().getY() < (height-1))
                            newRoom.getCoordinate().setY(currentRoom.getCoordinate().getY()+1);
                        else
                            newRoom = null;
                        break;
                    case 2: // EAST
                        //System.out.println("E");
                        if (currentRoom.getCoordinate().getX() < (width-1))
                            newRoom.getCoordinate().setX(currentRoom.getCoordinate().getX()+1);
                        else
                            newRoom = null;
                        break;
                    case 3: // WEST
                        //System.out.println("W");
                        if (currentRoom.getCoordinate().getX() > 0)
                            newRoom.getCoordinate().setX(currentRoom.getCoordinate().getX()-1);
                        else
                            newRoom = null;
                        break;
                }
                //if (newRoom != null)
                 //   System.out.printf("(%d,%d) - generated\n", newRoom.getCoordinate().getX(), newRoom.getCoordinate().getY());

                if (newRoom == null) {
                    loc--;
                   // System.out.println(" * Out of bound loc--");
                }
                else if ( roomDatabase.containsKey(newRoom.getCoordinate().getX(), newRoom.getCoordinate().getY()) ) {
                    loc--;
                  //  System.out.println(" * Duplicated loc--");
                }
                else {
                    roomDatabase.add(newRoom);
                 //   System.out.println(" ++ Recorded");
                }

            }
        }

        roomDatabase.forEach(
                (key, room) -> {
                    Coordinate xy =  roomDatabase.getCoordinate(key);
                    room.setConnection(new Connection());

                    if (xy.getY() > 0) { // NORTH
                        if (roomDatabase.containsKey(xy.getX(),xy.getY()-1)) {
                            room.getConnection().setNorth(new Coordinate());
                            room.getConnection().getNorth().setX(xy.getX());
                            room.getConnection().getNorth().setX(xy.getY()-1);
                        }
                    }

                    if (xy.getY() < (height-1)) { // SOUTH
                        if (roomDatabase.containsKey(xy.getX(),xy.getY()+1)) {
                            room.getConnection().setSouth(new Coordinate());
                            room.getConnection().getSouth().setX(xy.getX());
                            room.getConnection().getSouth().setX(xy.getY()+1);
                        }
                    }

                    if (xy.getX() < (width-1)) { // EAST
                        if (roomDatabase.containsKey(xy.getX()+1,xy.getY())) {
                            room.getConnection().setEast(new Coordinate());
                            room.getConnection().getEast().setX(xy.getX()+1);
                            room.getConnection().getEast().setX(xy.getY());
                        }
                    }

                    if (xy.getX() > 0) { // WEST
                        if (roomDatabase.containsKey(xy.getX()-1,xy.getY())) {
                            room.getConnection().setWest(new Coordinate());
                            room.getConnection().getWest().setX(xy.getX()-1);
                            room.getConnection().getWest().setX(xy.getY());
                        }
                    }
                }
        );
    }
}