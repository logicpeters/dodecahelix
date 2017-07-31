package org.ddx.algorithms.union;

/**
 *  Determines whether or not a N x N grid of "sites" percolates.
 *
 *  Assignment from Coursera Algorithms course.
 *
 *  Each "site" (cell) in the grid can be blocked or open.
 *  Percolation means that there exists a straight path from the bottom row of the grid to the top row of open sites.
 *  (diagonals are not allowed)
 *
 *  Optimized for N log N performance using the Weighted Average Union Find with Path Compression.
 *
 *
 */
public class Percolation {


    /**
     * N x N grid of open/blocked cells.
     */
    private boolean[][] grid;

    /**
     * The number of columns or rows in the grid (N)
     */
    private final int gridSize;

    /**
     * Virtual site at the top of the grid, which connects all of the sites in the top row of the grid.
     * <p>
     * This makes it easier to test for percolation.
     */
    private final int topPointId;

    /**
     * Virtual site at the bottom of the grid, which connects all of the sites in the bottom row of the grid.
     * <p>
     * Makes it easier to test for percolation.
     */
    private final int bottomPointId;

    /**
     * Union-Find algorithm for determining percolation.
     */
    private final UnionFind unionFind;

    /**
     * Create n-by-n grid, with all sites blocked.
     *
     * @param n
     */
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("argument must be greater than zero");

        this.gridSize = n;
        grid = new boolean[n][n];

        // add 2 extra for the virtual "sites"
        int numberOfPoints = n * n + 2;

        unionFind = new WgtAvgPathCmpUnionFind(numberOfPoints);

        for (int gr = 0; gr < n; gr++) {
            for (int gc = 0; gc < n; gc++) {
                // block the cell
                grid[gc][gr] = false;
            }
        }

        // put two virtual points on the top and bottom of the grid,
        // connecting the top row's "sites" to the top virtual point
        // and the bottom row's "sites" to the bottom virtual point
        topPointId = n * n;
        bottomPointId = n * n + 1;
        for (int i = 0; i < n; i++) {
            unionFind.union(topPointId, i);
            int lastRowId = i + ((n * n) - n);
            unionFind.union(bottomPointId, lastRowId);
        }
    }

    /**
     * Open site (row, col) if it is not open already.
     *
     * @param row
     * @param col
     */
    public void open(int row, int col) {
        validateRowAndColumn(row, col);

        if (!isOpen(row, col)) {
            grid[col - 1][row - 1] = true;
            int pointId = getPointByRowAndColumn(row, col);
            if (row > 1 && isOpen(row - 1, col)) {
                unionFind.union(pointId, pointId - gridSize);
            }
            if (row < gridSize && isOpen(row + 1, col)) {
                unionFind.union(pointId, pointId + gridSize);
            }
            if (col > 1 && isOpen(row, col - 1)) {
                unionFind.union(pointId, pointId - 1);
            }
            if (col < gridSize && isOpen(row, col + 1)) {
                unionFind.union(pointId, pointId + 1);
            }
        }
    }

    private int getPointByRowAndColumn(int row, int col) {
        return (col - 1) + ((row - 1) * gridSize);
    }

    private void validateRowAndColumn(int row, int col) {
        if (row < 1 || row > gridSize) throw new IllegalArgumentException("invalid row, must be > 0 and < N");
        if (col < 1 || col > gridSize) throw new IllegalArgumentException("invalid column, must be > 0 and < N");
    }

    /**
     * Is site (row, col) open?
     *
     * @param row
     * @param col
     * @return
     */
    public boolean isOpen(int row, int col) {
        validateRowAndColumn(row, col);
        return grid[col - 1][row - 1];
    }

    /**
     * Is site (row, col) full?
     *
     * @param row
     * @param col
     * @return
     */
    public boolean isFull(int row, int col) {
        validateRowAndColumn(row, col);
        if (!isOpen(row, col)) {
            return false;
        }

        int pointId = getPointByRowAndColumn(row, col);
        return (unionFind.find(pointId) == unionFind.find(topPointId));
    }

    /**
     * Return number of open sites.
     *
     * @return
     */
    public int numberOfOpenSites() {
        int numOpenSites = 0;
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (grid[col][row]) {
                    numOpenSites++;
                }
            }
        }
        return numOpenSites;
    }

    /**
     * Does this system percolate?
     *
     * @return
     */
    public boolean percolates() {
        return (unionFind.find(bottomPointId) == unionFind.find(topPointId));
    }

    public static void main(String[] args) {
        Percolation perc = new Percolation(3);

        // Simple test of percolation
        System.out.println("starting percolation = " + perc.percolates());
        perc.open(3, 2);
        System.out.println("percolation after opening 1,2 = " + perc.percolates());
        perc.open(2, 2);
        System.out.println("percolation after opening 2,2 = " + perc.percolates());
        perc.open(2, 1);
        System.out.println("percolation after opening 2,3 = " + perc.percolates());
        perc.open(1, 1);
        System.out.println("percolation after opening 3,3 = " + perc.percolates());
    }


}
