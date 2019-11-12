package jindanupajit;

import efrem.Connection;
import efrem.Coordinate;
import efrem.Room;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


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

        for (Integer key : roomDatabase.keySet()) {
            Room room = roomDatabase.get(key);

            Coordinate xy = room.getCoordinate();
            Connection c = room.getConnection();

            System.out.printf("(%d,%d) #%d key=%d\n", xy.getX(), xy.getY(), roomDatabase.getKey(xy.getX(), xy.getY()), key);

            System.out.printf("%s\n", room);
//            if (c.getNorth() != null)
//                System.out.printf("  * N = (%d,%d) #%d\n", c.getNorth().getX(), c.getNorth().getY(), roomDatabase.getKey(c.getNorth().getX(), c.getNorth().getY()));
//
//            if (c.getSouth() != null)
//                System.out.printf("  * S = (%d,%d) #%d\n", c.getSouth().getX(), c.getSouth().getY(), roomDatabase.getKey(c.getSouth().getX(), c.getSouth().getY()));
//
//            if (c.getEast() != null)
//                System.out.printf("  * E = (%d,%d) #%d\n", c.getEast().getX(), c.getEast().getY(), roomDatabase.getKey(c.getEast().getX(), c.getEast().getY()));
//
//            if (c.getWest() != null)
//                System.out.printf("  * W = (%d,%d) #%d\n", c.getWest().getX(), c.getWest().getY(), roomDatabase.getKey(c.getWest().getX(), c.getWest().getY()));

        }
    }
}
