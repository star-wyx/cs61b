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
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public Frame(int WIDTH, int HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        world = new TETile[WIDTH][HEIGHT];
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

    public void buildMap() {
        int[] start = new int[2];
        int[] end;
        int[] entry;
        start[0] = RANDOM.nextInt(WIDTH - 3);
        start[1] = RANDOM.nextInt(HEIGHT - 3);
        end = dotEnd(start);
        buildRoom(start, end);
        entry = dotEntry(start, end);
        buildHallway(entry);
        world[entry[0]][entry[1]] = Tileset.FLOWER;
    }

    /**
     * @param start the left & down dot
     * @return the random right & up dot
     */
    public int[] dotEnd(int[] start) {
        int[] res = new int[]{-1, -1};
        while (res[0] == -1 || res[0] >= WIDTH)
            res[0] = RANDOM.nextInt(5) + start[0] + 3;
        while (res[1] == -1 || res[1] >= HEIGHT)
            res[1] = RANDOM.nextInt(8) + start[1] + 3;
        while (!isNothing(start, res)) {
            res = dotEnd(start);
        }
        return res;
    }

    /**
     * @return the random entry dot for Hallway in a room.
     */
    public int[] dotEntry(int[] start, int[] end) {
        int[] res = new int[3];
        int direction = RANDOM.nextInt(4);
        res[2] = direction;
        if (direction == 0 && end[1] == HEIGHT - 1) {
            direction = 1;
        } else if (direction == 1 && start[1] == 0) {
            direction = 0;
        } else if (direction == 2 && start[0] == 0) {
            direction = 3;
        } else if (direction == 3 && end[0] == WIDTH - 1) {
            direction = 2;
        }

        if (direction == 0) { //up
            res[0] = RANDOM.nextInt(end[0] - start[0] - 1) + start[0] + 1;
            res[1] = end[1];
        } else if (direction == 1) { //down
            res[0] = RANDOM.nextInt(end[0] - start[0] - 1) + start[0] + 1;
            res[1] = start[1];
        } else if (direction == 2) { //left
            res[0] = start[0];
            res[1] = RANDOM.nextInt(end[1] - start[1] - 1) + start[1] + 1;
        } else { //right
            res[0] = end[0];
            res[1] = RANDOM.nextInt(end[1] - start[1] - 1) + start[1] + 1;
        }
        return res;
    }

    /**
     * Check if there is all empty in the given area.
     */
    public boolean isNothing(int[] start, int[] end) {
        for (int i = start[0]; i <= end[0]; i++) {
            for (int j = start[1]; j <= end[1]; j++) {
                if (world[i][j] != Tileset.NOTHING)
                    return false;
            }
        }
        return true;
    }

    public void buildRoom(int[] start, int[] end) {
        for (int i = start[0]; i <= end[0]; i++) {
            for (int j = start[1]; j <= end[1]; j++) {
                if (i == start[0] || i == end[0] || j == start[1] || j == end[1])
                    world[i][j] = Tileset.WALL;
                else
                    world[i][j] = Tileset.FLOOR;
            }
        }
    }

    public int[] buildHallway(int[] entry) {
        int length = RANDOM.nextInt(10) + 3;

        if (entry[2] == 0) {
            goUp(entry, length);
        } else if (entry[2] == 1) {
            goDown(entry, length);
        } else if (entry[2] == 2) {
            goLeft(entry, length);
        } else if (entry[2] == 3) {
            goRight(entry, length);
        }
        return null;
    }

    public void goUp(int[] entry, int length) {
        int x = entry[0];
        for (int j = entry[1]; j < entry[1] + length; j++) {
            world[x][j] = Tileset.FLOOR;
            world[x - 1][j] = Tileset.WALL;
            world[x + 1][j] = Tileset.WALL;
        }
    }

    public void goDown(int[] entry, int length) {
        int x = entry[0];
        for (int j = entry[1] - length + 1; j <= entry[1]; j++) {
            world[x][j] = Tileset.FLOOR;
            world[x - 1][j] = Tileset.WALL;
            world[x + 1][j] = Tileset.WALL;
        }
    }

    public void goLeft(int[] entry, int length) {
        int y = entry[1];
        for (int i = entry[0] - length + 1; i <= entry[0]; i++) {
            world[i][y] = Tileset.FLOOR;
            world[i][y - 1] = Tileset.WALL;
            world[i][y + 1] = Tileset.WALL;
        }
    }

    public void goRight(int[] entry, int length) {
        int y = entry[1];
        for (int i = entry[0]; i < entry[0] + length; i++) {
            world[i][y] = Tileset.FLOOR;
            world[i][y - 1] = Tileset.WALL;
            world[i][y + 1] = Tileset.WALL;
        }
    }

}
