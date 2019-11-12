package jindanupajit;

import efrem.Connection;
import efrem.Coordinate;
import efrem.Room;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        Random r = new Random();
//        RoomDatabase roomDatabase = new RoomDatabase();
//        Integer width = 4;
//        Integer height = 4;
//        Integer maxRoom = 10;
//        roomDatabase.setWidth(width);
//        roomDatabase.setHeight(height);
//
//
//        for (int loc = 0; loc < maxRoom ; loc++) {
//            Room newRoom = new Room();
//
//
//            if (loc == 0) {
//                int x = r.nextInt(width);
//                int y = r.nextInt(height);
//                Coordinate xy = new Coordinate(x,y);
//
//                newRoom.setCoordinate(xy);
//                System.out.printf("First room %d,%d - generated\n", newRoom.getCoordinate().getX(), newRoom.getCoordinate().getY());
//                roomDatabase.add(newRoom);
//            }
//            else {
//               Room currentRoom = roomDatabase.getRandomRoom();
//               int direction  = r.nextInt(4);
//               newRoom.setCoordinate(new Coordinate());
//               newRoom.getCoordinate().setX(currentRoom.getCoordinate().getX());
//               newRoom.getCoordinate().setY(currentRoom.getCoordinate().getY());
//
//                System.out.printf("Current %d,%d (w %d,h %d, loc %d) - ", currentRoom.getCoordinate().getX(), currentRoom.getCoordinate().getY(), width, height, loc);
//               switch(direction) {
//                   case 0: // NORTH
//                      System.out.println("N");
//                       if (currentRoom.getCoordinate().getY() > 0)
//                           newRoom.getCoordinate().setY(currentRoom.getCoordinate().getY()-1);
//                       else
//                           newRoom = null;
//                       break;
//                   case 1: // SOUTH
//                       System.out.println("S");
//                       if (currentRoom.getCoordinate().getY() < (height-1))
//                           newRoom.getCoordinate().setY(currentRoom.getCoordinate().getY()+1);
//                       else
//                           newRoom = null;
//                       break;
//                   case 2: // EAST
//                       System.out.println("E");
//                       if (currentRoom.getCoordinate().getX() < (width-1))
//                           newRoom.getCoordinate().setX(currentRoom.getCoordinate().getX()+1);
//                       else
//                           newRoom = null;
//                       break;
//                   case 3: // WEST
//                       System.out.println("W");
//                       if (currentRoom.getCoordinate().getX() > 0)
//                           newRoom.getCoordinate().setX(currentRoom.getCoordinate().getX()-1);
//                       else
//                           newRoom = null;
//                       break;
//               }
//                if (newRoom != null)
//                System.out.printf("(%d,%d) - generated\n", newRoom.getCoordinate().getX(), newRoom.getCoordinate().getY());
//
//                if (newRoom == null) {
//                    loc--;
//                    System.out.println(" * Out of bound loc--");
//                }
//                else if ( roomDatabase.containsKey(newRoom.getCoordinate().getX(), newRoom.getCoordinate().getY()) ) {
//                    loc--;
//                    System.out.println(" * Duplicated loc--");
//                }
//                else {
//                    roomDatabase.add(newRoom);
//                    System.out.println(" ++ Recorded");
//                }
//
//            }
//        }
//
//        roomDatabase.forEach(
//                (key, room) -> {
//                   Coordinate xy =  roomDatabase.getCoordinate(key);
//                    room.setConnection(new Connection());
//
//                   if (xy.getY() > 0) { // NORTH
//                       if (roomDatabase.containsKey(xy.getX(),xy.getY()-1)) {
//                           room.getConnection().setNorth(new Coordinate());
//                           room.getConnection().getNorth().setX(xy.getX());
//                           room.getConnection().getNorth().setX(xy.getY()-1);
//                       }
//                   }
//
//                    if (xy.getY() < (height-1)) { // SOUTH
//                        if (roomDatabase.containsKey(xy.getX(),xy.getY()+1)) {
//                            room.getConnection().setSouth(new Coordinate());
//                            room.getConnection().getSouth().setX(xy.getX());
//                            room.getConnection().getSouth().setX(xy.getY()+1);
//                        }
//                    }
//
//                    if (xy.getX() < (width-1)) { // EAST
//                        if (roomDatabase.containsKey(xy.getX()+1,xy.getY())) {
//                            room.getConnection().setEast(new Coordinate());
//                            room.getConnection().getEast().setX(xy.getX()+1);
//                            room.getConnection().getEast().setX(xy.getY());
//                        }
//                    }
//
//                    if (xy.getX() > 0) { // WEST
//                        if (roomDatabase.containsKey(xy.getX()-1,xy.getY())) {
//                            room.getConnection().setWest(new Coordinate());
//                            room.getConnection().getWest().setX(xy.getX()-1);
//                            room.getConnection().getWest().setX(xy.getY());
//                        }
//                    }
//                }
//        );

        RoomGenerator roomGenerator = new RoomGenerator(new RoomDatabase(4,4), 10);
        roomGenerator.generate();

        RoomDatabase roomDatabase = roomGenerator.getRoomDatabase();

        printRoom(roomDatabase);

        Player player = new Player(roomDatabase.getRandomRoom(), roomDatabase);


        while(true) {
            int x = player.getCurrentRoom().getCoordinate().getX();
            int y = player.getCurrentRoom().getCoordinate().getY();
            System.out.printf("You are at (%d, %d) %d\n", x, y, roomDatabase.getKey(player.getCurrentRoom()));

            Coordinate xy = menu(player);

            if (player.canMoveTo(xy))
                player.setCurrentRoom(roomDatabase.get(xy.getX(), xy.getY()));
            else
                System.out.println("Wrong move\n");

        }
    }


    public static Coordinate menu(Player player) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nSelect direction you want to move to ");
        System.out.print("8=North, 2=South, 6=East, 4=West, 0=Quit >");

        Coordinate xy = new Coordinate();
        xy.setX(player.getCurrentRoom().getCoordinate().getX());
        xy.setY(player.getCurrentRoom().getCoordinate().getY());
        switch(scanner.nextLine()) {
            case "8": // N
                xy.setY(xy.getY()-1);
                return xy;
            case "2": // S
                xy.setY(xy.getY()+1);
                return xy;
            case "6": // E
                xy.setX(xy.getX()+1);
                return xy;
            case "4": // W
                xy.setX(xy.getX()-1);
                return xy;
            case "0":
                System.out.println("Bye.");
                System.exit(1);
            default:
                System.out.println("Wrong choice!");

        }

        return xy;
    }

    public static void printRoom (RoomDatabase roomDatabase) {

        for (Room room : roomDatabase.values()) {
            Coordinate xy = room.getCoordinate();
            Connection c = room.getConnection();

            System.out.printf("(%d,%d) #%d\n", xy.getX(), xy.getY(), roomDatabase.getKey(xy.getX(), xy.getY()));

            if (c.getNorth() != null)
                System.out.printf("  * N = (%d,%d) #%d\n", c.getNorth().getX(), c.getNorth().getY(), roomDatabase.getKey(c.getNorth().getX(), c.getNorth().getY()));

            if (c.getSouth() != null)
                System.out.printf("  * S = (%d,%d) #%d\n", c.getSouth().getX(), c.getSouth().getY(), roomDatabase.getKey(c.getSouth().getX(), c.getSouth().getY()));

            if (c.getEast() != null)
                System.out.printf("  * E = (%d,%d) #%d\n", c.getEast().getX(), c.getEast().getY(), roomDatabase.getKey(c.getEast().getX(), c.getEast().getY()));

            if (c.getWest() != null)
                System.out.printf("  * W = (%d,%d) #%d\n", c.getWest().getX(), c.getWest().getY(), roomDatabase.getKey(c.getWest().getX(), c.getWest().getY()));

        }
    }
}
