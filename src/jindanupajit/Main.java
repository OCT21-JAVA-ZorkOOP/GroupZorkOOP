package jindanupajit;


public class Main {
    /**
     * Run Cli runnable object
     * <p>Run the Runnable Cli object as a Thread</p>
     * @param args ignored, can be null.
     * @see Cli
     * @see Thread
     */
    public static void main(String[] args) {
       Cli cli = new Cli(System.out, System.in);
       Thread thread = new Thread(cli);

       thread.start();
    }
}
