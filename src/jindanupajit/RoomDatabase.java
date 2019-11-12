package jindanupajit;


import efrem.Coordinate;
import efrem.Room;

import java.util.*;

public class RoomDatabase extends HashMap<Integer, Room> {

    private Integer width = 0;
    private Integer height = 0;

    public RoomDatabase(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public RoomDatabase(int initialCapacity) {
        super(initialCapacity);
    }

    public RoomDatabase() {
    }

    public RoomDatabase(Map<? extends Integer, ? extends Room> m) {
        super(m);
    }

    /**
     * Constructor (overload)
     * @param width the grid width
     * @param height the grid height
     */
    public RoomDatabase(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Convert x,y to HashMap key
     * @param width the grid width of game
     * @param x coordinate x start from 0
     * @param y coordinate y start from 0
     * @return (y*width)+x
     */
    public static Integer getKey(Integer width, Integer x, Integer y) {
        return (y*width)+x;
    }

    /**
     * Convert HashMap key back to Coordinate
     * @param width the grid width of game
     * @param key key you want to convert
     * @return Coordinate x,y
     * @see Coordinate
     */
    public static  Coordinate getCoordinate(Integer width, Integer key) {
        Integer y = key/width;
        Integer x = key-(y*width);
        return new Coordinate(x,y);
    }

    /**
     * Get Room by coordinate
     * @param x
     * @param y
     * @return Room associated with x,y
     * @see Room
     */
    public Room get(int x, int y) {
        return get(width, x, y);
    }

    /**
     * Get Room by coordinate, and grid width
     * @param width
     * @param x
     * @param y
     * @return Room associated with x,y
     * @see Room
     */
    public Room get(Integer width, Integer x, Integer y) {
        return super.get(getKey(width,x,y));
    }

    /**
     * Add a room to database, and automatically generate key for HashMap<Integer, Room>
     * @param room the room that you want to add
     * @return The room that you just add
     * @see HashMap
     *
     */
    public Room add(Room room) {
        return this.put(getKey(room), room);
    }

    /**
     * Put Room that associated with x,y
     * @param x
     * @param y
     * @param room you want to put
     * @return Room that you just put
     * @see Room
     */
    public Room put(int x, int y, Room room) {
        return put(width, x, y, room);
    }

    /**
     * Put Room that associated with x,y
     * @param width
     * @param x
     * @param y
     * @param room you want to put
     * @return Room that you just put
     * @see Room
     */
    public Room put(Integer width, Integer x, Integer y, Room room) {
        return super.put(getKey(width,x,y), room);
    }

    /**
     * Check if that coordinate has a Room associated with
     * @param xy
     * @return true/false according to the existing of the Room
     * @see Room
     */
    public boolean hasRoomAt(Coordinate xy) {
        return containsKey(xy.getX(), xy.getY());
    }

    /**
     * Overload containsKey with x,y
     * @param x
     * @param y
     * @return
     */
  public boolean containsKey(int x, int y) {
        return containsKey(width, x,y);
  }

    public boolean containsKey(Integer width, Integer x, Integer y) {
        return super.containsKey(getKey(width,x,y));
    }

    public Integer getKey(Room room) {
        return getKey(room.getCoordinate().getX(), room.getCoordinate().getY());
    }

    public Integer getKey(int x, int y) {

        return getKey(width,x,y);
    }

    public Integer getRandomKey() {
        ArrayList<Integer> keySet = new ArrayList<>(this.keySet());
        Random r = new Random();

        return keySet.get(r.nextInt(keySet.size()));
    }

    public Coordinate getRandomCoordinate() {
        return getCoordinate(getRandomKey());
    }

    public Room getRandomRoom() {
        return this.get(getRandomKey());
    }

    public Coordinate getCoordinate(int key) {
        return getCoordinate(width, key);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
