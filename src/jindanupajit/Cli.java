package jindanupajit;

import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * Command Line Interface
 */
public class Cli implements Runnable {

    private String prompt = "% ";
    private PrintStream printStream = System.out;
    private InputStream inputStream = System.in;

    private boolean shortcutShown = false;
    private boolean developerMode = false;
    private final RoomGenerator roomGenerator;
    private final RoomDatabase roomDatabase;
    private final Player player;

    /**
     * Command Line Interface :: Constructor
     */
    public Cli() {

        roomGenerator = new RoomGenerator(new RoomDatabase(10, 10), 50);
        roomGenerator.generate();
        roomDatabase = roomGenerator.getRoomDatabase();
        player = new Player(roomDatabase.getRandomRoom(), roomDatabase);
    }

    /**
     * Command Line Interface :: Constructor
     * @param printStream where to print, default: System.out
     * @param inputStream where to take input, default: System.in
     *
     */
    public Cli(PrintStream printStream, InputStream inputStream) {
        this();
        this.printStream = printStream;
        this.inputStream = inputStream;
    }

    public static void main(String[] args) {
        Cli cli = new Cli();
        cli.run();
    }

    private Method getMethod(String prefix, String name) {
        try {
            return this.getClass().getMethod(prefix + "_" + name, String[].class);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private Object invokeMethod(Method method, String[] args) {
        try {
            return method.invoke(this, new Object[]{args});
        } catch (IllegalAccessException e) {
            System.out.println("Access denied.\n");
            if (developerMode)
                e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.out.println("Error thrown:\n");
            if (developerMode)
                e.printStackTrace();
        }
        return null;
    }

    /**
     * Run the Command Line Interface
     * @see Thread
     */
    @Override
    public void run() {

        System.out.println("Welcome to Zork\n");
        System.out.println("type 'help' for list of command\n");
        String[] args;
        while (true) {
            args = this.args();
            if ((args.length == 0) || (args[0].equals("")))
                continue;
            Method method;

            if (args[0].equals("return"))
                return;
            else if ((this.developerMode) && (null != (method = getMethod("dev", args[0]))))
                invokeMethod(method, args);
            else if (null != (method = getMethod("cmd", args[0])))
                invokeMethod(method, args);
            else
                System.out.printf("'%s' is not recognized as a command.\n\n", args[0]);
        }
    }

    private String[] args() {
        final Scanner scanner = new Scanner(inputStream);
        printStream.print(this.prompt);
        return scanner.nextLine().split(" ");
    }

    public void cmd_exit(String[] args) {
        printStream.print("Bye.\n\n");
        System.exit(0);
    }

    public void cmd_help(String[] args) {
        ArrayList<Method> methodDatabase = new ArrayList<>(Arrays.asList(this.getClass().getDeclaredMethods()));
        ArrayList<String> methodName = new ArrayList<>();
        for (Method method : methodDatabase) {
            String name = method.getName();
            if (name.equals("cmd_dev") &&(!developerMode))
                continue;
            if (name.startsWith("cmd_")) {
                methodName.add(name);
            } else if (name.startsWith("dev_") && (developerMode)) {
                methodName.add(name);
            }
        }

        Collections.sort(methodName);

        for (String name : methodName) {
            if (name.startsWith("dev_"))
                System.out.printf("\t# %s\n", name.substring(4));
             else
                System.out.printf("\t%% %s\n", name.substring(4));
        }
    }

    public void dev_where(String[] args) {
        printStream.printf("You are in room #%s %s:%d\n", player.where(), player.getCurrentRoom().getCoordinate(), player.getRoomKey());
        printStream.printf("   -->  %s\n\n",player.getCurrentRoom().getConnection());
    }
    public void cmd_where(String[] args) {
        printStream.printf("You are in room #%s\n\n", player.where());
    }

    public void dev_map(String[] args) {
        printStream.printf("%s\n\n", roomDatabase);
    }

    public void cmd_dev(String[] args) {
        if (args.length == 1) {
            printStream.printf("Usage: %s on|off\n\n", args[0]);
            return;
        }

        switch (args[1].toLowerCase()) {
            case "on":
                this.developerMode = true;
                this.prompt = "(dev)# ";
                printStream.print("DEVELOPER MODE IS ON\n\n");
                break;
            case "off":
                this.developerMode = false;
                this.prompt = "% ";
                printStream.print("Developer mode is OFF\n\n");
                break;
            default:
                printStream.printf("Usage: %s on|off\n\n", args[0]);
        }
    }

    public void cmd_move(String[] args) {

        if (args.length != 2) {
            System.out.println("Usage: %s north|south|east|west\n\n");
            return;
        }

        switch(args[1]) {
            case "north": cmd_8(null);
                break;
            case "south": cmd_2(null);
                break;
            case "east": cmd_6(null);
                break;
            case "west": cmd_4(null);
                break;
            default:
                System.out.println("Usage: %s north|south|east|west\n\n");
                return;
        }
        if (!shortcutShown) {
            System.out.println("Shortcut available: \n\t8 = move north\n\t2 = move south\n\t6 = move east\n\t4 = move west\n\n");
            shortcutShown = true;
        }
    }

    /**
     * Move NORTH
     * @param args discarded
     */
    public void cmd_8(String[] args) {
        if (player.canMoveTo(player.getCurrentRoom().getConnection().getNorth())) {

            if (developerMode)
                printStream.printf(" * Can move to %s\n",player.getCurrentRoom().getConnection().getNorth());

            player.setCurrentRoom(roomDatabase.get(player.getCurrentRoom().getConnection().getNorth()));
            System.out.printf("Moved to room #%s\n", player.where());
        }
        else
            printStream.printf(" * Cannot move\n");

        if (developerMode)
            dev_where(null);
        else
            cmd_where(null);
    }

    /**
     * Move SOUTH
     * @param args discarded
     */
    public void cmd_2(String[] args) {
        if (player.canMoveTo(player.getCurrentRoom().getConnection().getSouth())) {

            if (developerMode)
                printStream.printf(" * Can move to %s\n",player.getCurrentRoom().getConnection().getSouth());

            player.setCurrentRoom(roomDatabase.get(player.getCurrentRoom().getConnection().getSouth()));
            System.out.printf("Moved to room #%s\n", player.where());
        }
        else
            printStream.printf(" * Cannot move\n");

        if (developerMode)
            dev_where(null);
        else
            cmd_where(null);
    }

    /**
     * Move EAST
     * @param args discarded
     */
    public void cmd_6(String[] args) {
        if (player.canMoveTo(player.getCurrentRoom().getConnection().getEast())) {

            if (developerMode)
                printStream.printf(" * Can move to %s\n",player.getCurrentRoom().getConnection().getEast());

            player.setCurrentRoom(roomDatabase.get(player.getCurrentRoom().getConnection().getEast()));
            System.out.printf("Moved to room #%s\n", player.where());
        }
        else
            printStream.printf(" * Cannot move\n");

        if (developerMode)
            dev_where(null);
        else
            cmd_where(null);
    }

    /**
     * Move WEST
     * @param args discarded
     */
    public void cmd_4(String[] args) {
        if (player.canMoveTo(player.getCurrentRoom().getConnection().getWest())) {

            if (developerMode)
                printStream.printf(" * Can move to %s\n",player.getCurrentRoom().getConnection().getWest());

            player.setCurrentRoom(roomDatabase.get(player.getCurrentRoom().getConnection().getWest()));
            System.out.printf("Moved to room #%s\n", player.where());
        }
        else
            printStream.printf(" * Cannot move\n");

        if (developerMode)
            dev_where(null);
        else
            cmd_where(null);
    }

}
