package lab11.graphs;

import edu.princeton.cs.algs4.In;

import javax.xml.soap.Node;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private int targetX;
    private int targetY;
    private PriorityQueue<Integer> pq;
    private Comparator<Integer> cmp;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        this.targetX = targetX;
        this.targetY = targetY;
        distTo[s] = 0;
        edgeTo[s] = s;
        cmp = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (h(o1) + distTo[o1] < h(o2) + distTo[o2]) {
                    return -1;
                } else if (h(o1) + distTo[o1] == h(o2) + distTo[o2]) {
                    return 0;
                } else {
                    return 1;
                }
            }
        };
        pq = new PriorityQueue<>(cmp);
    }

    /**
     * Estimate of the distance from v to the target.
     */
    private int h(int v) {
        int sourceX = maze.toX(v);
        int sourceY = maze.toY(v);
        return Math.abs(sourceX - targetX) + Math.abs(sourceY - targetY);
    }

    /**
     * Finds vertex estimated to be closest to target.
     */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /**
     * Performs an A star search from vertex s.
     */
    private void astar(int s) {
        // TODO
        marked[s] = true;
        distTo[s] = 0;
        announce();
        pq.add(s);
        while (pq != null) {
            int v = pq.poll();
            for (int tmp : maze.adj(v)) {
                if (!marked[tmp]) {
                    distTo[tmp] = distTo[v] + 1;
                    edgeTo[tmp] = v;
                    marked[tmp] = true;
                    announce();
                    pq.add(tmp);
                } else {
                    if (distTo[tmp] > distTo[v] + 1) {
                        distTo[tmp] = distTo[v] + 1;
                    }
                }
                if(tmp == t){
                    return;
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

