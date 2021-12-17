package lab11.graphs;

/**
 * @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private Maze maze;
    private int[] parents;
    private boolean isCycle;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        parents = new int[maze.V()];
        isCycle = false;
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        parents[0] = 0;
        dfs(0);
    }

    // Helper methods go here
    public void dfs(int v) {
        marked[v] = true;
        announce();
        if (isCycle) {
            return;
        }

        for (int w : maze.adj(v)) {
            if (!marked[w]) {
                parents[w] = v;
                dfs(w);
                if(isCycle){
                    return;
                }
            } else if (parents[v] != w) {
                int cur = v;
                int pre = w;
                while(cur!=w){
                    int tmp = parents[cur];
                    parents[cur] = pre;
                    edgeTo[cur] = pre;
                    pre= cur;
                    cur = tmp;
                    announce();
                }
                edgeTo[w] = pre;
                parents[w] = pre;
                isCycle = true;
                announce();
                return;
            }
        }
    }
}

