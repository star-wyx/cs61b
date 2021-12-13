package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.LinkedList;

public class Percolation {
    private boolean[][] grid;
    private WeightedQuickUnionUF map;
    private int N;
    private int openSites;
    private LinkedList<Integer> topOpen;
    private LinkedList<Integer> downOpen;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        this.N = N;
        map = new WeightedQuickUnionUF(N * N);
        grid = new boolean[N][N];
        for (boolean[] row : grid) {
            for (boolean b : row) {
                b = false;
            }
        }
        openSites = 0;
        topOpen = new LinkedList<>();
        downOpen = new LinkedList<>();
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
        if (row > 0 && isOpen(row - 1, col)) {
            map.union(num, xyTo1D(row - 1, col));
        }
        if (row < N - 1 && isOpen(row + 1, col)) {
            map.union(num, xyTo1D(row + 1, col));
        }
        if (col > 0 && isOpen(row, col - 1)) {
            map.union(num, xyTo1D(row, col - 1));
        }
        if (col < N - 1 && isOpen(row, col + 1)) {
            map.union(num,xyTo1D(row, col + 1));
        }
        openSites++;
        if (row == 0) {
            topOpen.add(num);
        } else if (row == N - 1) {
            downOpen.add(num);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (topOpen == null) {
            return false;
        }
        int num = xyTo1D(row, col);
        for (Integer i : topOpen) {
            if (map.connected(num, i))
                return true;
        }
        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (downOpen == null) {
            return false;
        }
        for (Integer down : downOpen) {
            if(isFull(down / N, down % N))
                return true;
        }
        return false;
    }

    // use for unit testing (not required)
    public static void main(String[] args) {

    }

}
