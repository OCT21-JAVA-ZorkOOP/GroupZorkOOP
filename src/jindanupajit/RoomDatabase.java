package jindanupajit;


import java.util.HashMap;
import java.util.Map;

public class RoomDatabase extends HashMap<Integer, Room> {

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

    public Room get(Integer rowWidth, Integer x, Integer y) {
        return super.get(getKey(rowWidth,x,y));
    }

    public Room put(Integer rowWidth, Integer x, Integer y, Room value) {
        return super.put(getKey(rowWidth,x,y), value);
    }

    public Integer getKey(Integer rowWidth, Integer x, Integer y) {
        return (y*rowWidth)+x;
    }

}
