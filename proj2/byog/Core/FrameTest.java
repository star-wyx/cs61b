package byog.Core;

import byog.TileEngine.TETile;
import org.junit.Test;

import java.util.Random;

public class FrameTest {

    private final int WIDTH = 80;
    private final int HEIGHT = 30;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    @Test
    public void dotTest(){
        Frame frame = new Frame(WIDTH,HEIGHT);
        int[] start = new int[2];
        start[0] = RANDOM.nextInt(WIDTH-2);
        start[1] = RANDOM.nextInt(HEIGHT-2);
        frame.dotEnd(start);
    }

    @Test
    public void RandTest(){
        for(int i=0;i<20;i++){
            System.out.println(RANDOM.nextInt(10));
        }
    }
}
