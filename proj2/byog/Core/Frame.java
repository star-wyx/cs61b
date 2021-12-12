package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class Frame {
    private final int WIDTH;
    private final int HEIGHT;
    private final TETile[][] world;
    private LinkedList<Room> list;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public Frame(int WIDTH, int HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        world = new TETile[WIDTH][HEIGHT];
        list = new LinkedList<>();
        initial();
    }

    public TETile[][] getWorld() {
        return world;
    }

    public void initial() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
    }

    public void buildRooms() {
        while (list.size() < 20) {
            int x1 = RANDOM.nextInt(WIDTH);
            int y1 = RANDOM.nextInt(HEIGHT);
            int addx = RANDOM.nextInt(5) + 3;
            int addy = RANDOM.nextInt(5) + 9;
            if (x1 + addx >= WIDTH || y1 + addy >= HEIGHT)
                continue;
            buildRoom(new Room(x1, y1, x1 + addx, y1 + addy));
        }
        for (Room r : list) {
            System.out.println(r);
        }
    }

    public void buildRoom(Room room) {
        if (list != null) {
            for (Room r : list) {
                if (room.isOverlap(r))
                    return;
            }
        }
        for (int i = room.x1; i <= room.x2; i++) {
            for (int j = room.y1; j <= room.y2; j++) {
                if (i == room.x1 || i == room.x2 || j == room.y1 || j == room.y2)
                    world[i][j] = Tileset.WALL;
                else
                    world[i][j] = Tileset.FLOOR;
            }
        }
        list.add(room);
//        buildHallway(room);
    }

    public int[] buildHallway(Room r) {
        int x = r.x2;
        int y = RANDOM.nextInt(r.y2 - r.y1 - 2) + r.y1 + 1;
        int length = RANDOM.nextInt(4) + 5;
        horizon(x, y, length);
        return new int[]{x,y};
    }

    public void vertical(int x, int y, int length) {
        for (int i = x; i <= x + 2; i++) {
            for (int j = y; j <= y + length; j++) {
                if (i == x || i == x + 2 || j == y || j == y + length)
                    world[i][j] = Tileset.WALL;
                else
                    world[i][j] = Tileset.FLOOR;
            }
        }
    }

    public void horizon(int x, int y, int length) {
        for (int i = x; i <= x + length; i++) {
            for (int j = y; j <= y + 2; j++) {
                if (i == x || i == x + length || j == y || j == y + 2)
                    world[i][j] = Tileset.WALL;
                else
                    world[i][j] = Tileset.FLOOR;
            }
        }
    }

    public void breakWall() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (world[i][j] == Tileset.WALL) {
                    if (i - 1 >= 0 && i + 1 < WIDTH && world[i - 1][j] == Tileset.FLOOR && world[i + 1][j] == Tileset.FLOOR)
                        world[i][j] = Tileset.FLOOR;
                    if (j - 1 >= 0 && j + 1 < HEIGHT && world[i][j - 1] == Tileset.FLOOR && world[i][j + 1] == Tileset.FLOOR)
                        world[i][j] = Tileset.FLOOR;
                }
            }
        }
    }

}
