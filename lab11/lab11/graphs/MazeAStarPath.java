package lab11.graphs;

import edu.princeton.cs.algs4.In;

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
    private boolean isTargetFound;
    private PriorityQueue<Node> pq;

    private class Node {
        public int v;
        public int priority;

        public Node(int v, int distance) {
            this.v = v;
            this.priority = distance + h(v);
        }
    }

    private class NodeCompare implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.priority - o2.priority;
        }
    }

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        this.targetX = targetX;
        this.targetY = targetY;
        distTo[s] = 0;
        edgeTo[s] = s;
        pq = new PriorityQueue<>(new NodeCompare());
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
        dfs(s);
    }

    private void dfs(int v) {
        if (v == t) {
            isTargetFound = true;
            return;
        }

        for (int tmp : maze.adj(v)) {
            if (!marked[tmp]) {
                distTo[tmp] = distTo[v] + 1;
                edgeTo[tmp] = v;
                marked[tmp] = true;
                announce();
                pq.add(new Node(tmp, distTo[tmp]));
            }
        }

        while (pq.size() != 0) {
            Node tmp = pq.poll();
            dfs(tmp.v);
            if (isTargetFound) {
                return;
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

