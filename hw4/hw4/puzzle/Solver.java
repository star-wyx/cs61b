package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
//import sun.awt.image.ImageWatched;

import java.util.*;

public class Solver {

    private class Node implements Comparable {
        private Node pre;
        private WorldState world;
        int move;

        public Node(Node pre, WorldState world, int move) {
            this.pre = pre;
            this.world = world;
            this.move = move;
        }

        @Override
        public int compareTo(Object o) {
            int step1;
            int step2;

            if (map.containsKey(this)) {
                step1 = map.get(this);
            } else {
                step1 = this.world.estimatedDistanceToGoal() + this.move;
                map.put(this,step1);
            }

            if (map.containsKey(((Node) o))) {
                step2 = map.get(((Node) o));
            } else {
                step2 = ((Node) o).world.estimatedDistanceToGoal() + ((Node) o).move;
                map.put(((Node) o),step2);
            }

            return step1 - step2;
        }
    }

    private MinPQ<Node> pq;
    private Node last;
    private Set<WorldState> set;
    private Map<Node, Integer> map;

    public Solver(WorldState initial) {
        pq = new MinPQ<>();
        set = new HashSet<>();
        map = new HashMap<>();
        Node init = new Node(null, initial, 0);

        pq.insert(init);
        set.add(initial);
        map.put(init, init.world.estimatedDistanceToGoal());
        while (!pq.isEmpty()) {
            Node cur = pq.delMin();
            if (cur.world.isGoal()) {
                last = cur;
                break;
            }
            for (WorldState w : cur.world.neighbors()) {
                if (!set.contains(w)) {
                    set.add(w);
                    pq.insert(new Node(cur, w, cur.move + 1));
                }
            }
        }
    }

    public int moves() {
        return last.move;
    }

    public Iterable<WorldState> solution() {
        LinkedList<WorldState> res = new LinkedList<>();
        Node tmp = last;
        while (tmp.pre != null) {
            res.addFirst(tmp.world);
            tmp = tmp.pre;
        }
        res.addFirst(tmp.world);
        return res;
    }
}
