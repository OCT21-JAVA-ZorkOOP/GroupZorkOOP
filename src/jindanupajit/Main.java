package jindanupajit;

import efrem.Connection;
import efrem.Coordinate;
import efrem.Room;

import java.util.Random;
import java.util.Scanner;

public class Main {
    /**
     * Run Cli runnable object
     * <p>Run the Runnable Cli object as a Thread</p>
     * @param args ignored, can be null.
     * @see Cli
     * @see Thread
     */
    public static void main(String[] args) {
       Cli cli = new Cli();
       Thread thread = new Thread(cli);

       thread.start();
    }
}
