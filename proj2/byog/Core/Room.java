package byog.Core;

import java.util.Random;

public class Room {
    int x1;
    int y1;
    int x2;
    int y2;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public Room(int x1, int y1, int x2, int y2) {
        this.x1 = Math.min(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.x2 = Math.max(x1, x2);
        this.y2 = Math.max(y1, y2);
    }

    public boolean isOverlap(Room a) {
        if( a.x1<=x1 && x1<=a.x2 || a.x1<=x2 && x2<=a.x2 ){
            return a.y1 <= y1 && y1 <= a.y2 || a.y1 <= y2 && y2 <= a.y2;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Room{" +
                "x1=" + x1 +
                ", y1=" + y1 +
                ", x2=" + x2 +
                ", y2=" + y2 +
                '}';
    }
}
