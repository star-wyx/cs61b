package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[][] grid;
    private final WeightedQuickUnionUF map;
    private final int N;
    private int openSites;
    boolean[] connectTop;
    boolean[] connectDown;
    boolean isPercolate;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.N = N;
        map = new WeightedQuickUnionUF(N * N);
        grid = new boolean[N][N];
        openSites = 0;
        connectTop = new boolean[N * N];
        connectDown = new boolean[N * N];
        isPercolate = false;
    }

    // convert the xy to an integer
    private int xyTo1D(int r, int c) {
        return r * N + c;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        int num = xyTo1D(row, col);
        if (isOpen(row, col)) {
            return;
        }
        grid[row][col] = true;
        boolean top = false;
        boolean down = false;
        if (row > 0 && isOpen(row - 1, col)) {
            if (connectTop[map.find(xyTo1D(row - 1, col))] || connectTop[map.find(num)]) {
                top = true;
            }
            if (connectDown[map.find(xyTo1D(row - 1, col))] || connectDown[map.find(num)]) {
                down = true;
            }
            map.union(num, xyTo1D(row - 1, col));
        }
        if (row < N - 1 && isOpen(row + 1, col)) {
            if (connectTop[map.find(xyTo1D(row + 1, col))] || connectTop[map.find(num)]) {
                top = true;
            }
            if (connectDown[map.find(xyTo1D(row + 1, col))] || connectDown[map.find(num)]) {
                down = true;
            }
            map.union(num, xyTo1D(row + 1, col));
        }
        if (col > 0 && isOpen(row, col - 1)) {
            if (connectTop[map.find(xyTo1D(row, col - 1))] || connectTop[map.find(num)]) {
                top = true;
            }
            if (connectDown[map.find(xyTo1D(row, col - 1))] || connectDown[map.find(num)]) {
                down = true;
            }
            map.union(num, xyTo1D(row, col - 1));
        }
        if (col < N - 1 && isOpen(row, col + 1)) {
            if (connectTop[map.find(xyTo1D(row, col + 1))] || connectTop[map.find(num)]) {
                top = true;
            }
            if (connectDown[map.find(xyTo1D(row, col + 1))] || connectDown[map.find(num)]) {
                down = true;
            }
            map.union(num, xyTo1D(row, col + 1));
        }
        openSites++;
        if (row == 0) {
            top = true;
        }
        if (row == N - 1) {
            down = true;
        }
        connectTop[map.find(num)] = top;
        connectDown[map.find((num))] = down;
        if (top && down) {
            isPercolate = true;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return connectTop[map.find(xyTo1D(row, col))];
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return isPercolate;
    }

    // use for unit testing (not required)
    public static void main(String[] args) {
        Percolation p = new Percolation(2);
        p.open(0, 0);
        System.out.println("0,0" + p.isFull(0, 0));
        p.open(1, 1);
        System.out.println("0,0" + p.isFull(1, 1));
        p.open(0, 1);
        System.out.println("0,0" + p.isFull(0, 1));
        System.out.println(p.percolates());
    }

}
