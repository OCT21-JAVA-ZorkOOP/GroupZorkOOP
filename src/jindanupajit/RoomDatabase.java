package jindanupajit;


import efrem.Coordinate;
import efrem.Room;

import java.util.*;

public class RoomDatabase extends HashMap<Integer, Room> {

    private int width = 0;
    private int height = 0;

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

    public RoomDatabase(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static Integer getKey(Integer width, Integer x, Integer y) {
        return (y*width)+x;
    }

    public static  Coordinate getCoordinate(Integer width, Integer key) {
        Integer y = key/width;
        Integer x = key-y;
        return new Coordinate(x,y);
    }

    public Room get(int x, int y) {
        return get(width, x, y);
    }

    public Room get(Integer width, Integer x, Integer y) {
        return super.get(getKey(width,x,y));
    }

    public Room add(Room room) {
        return this.put(getKey(room), room);
    }

    public Room put(int x, int y, Room value) {
        return put(width, x, y, value);
    }

    public Room put(Integer width, Integer x, Integer y, Room value) {
        return super.put(getKey(width,x,y), value);
    }

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
