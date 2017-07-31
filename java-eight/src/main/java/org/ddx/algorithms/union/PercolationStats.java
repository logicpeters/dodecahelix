package org.ddx.algorithms.union;

import org.ddx.util.Stats;

import java.security.SecureRandom;

/**
 *  Runs a "Monte Carlo Simulation' on the Percolation solution.
 *
 *  Part of the assignment in the Union-Find class of Coursera's Algorithms I class.
 */
public class PercolationStats {

    private final double[] results;
    private final double mean;
    private final double stddev;

    /**
     * Perform independent experiments on an N-by-N grid.
     *
     * @param n
     * @param trials
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("invalid parameters, grid size and number of trials must be >=0");

        results = new double[trials];
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int randomRow = random.nextInt(n) + 1;
                int randomColumn = random.nextInt(n) + 1;
                if (!percolation.isOpen(randomRow, randomColumn)) {
                    percolation.open(randomRow, randomColumn);
                }
            }

            double result = percolation.numberOfOpenSites() / (double) (n * n);
            results[i] = result;
        }

        mean = Stats.mean(results);
        stddev = Stats.standardDeviation(results);
    }

    /**
     * Sample mean of percolation threshold.
     *
     * @return
     */
    public double mean() {
        return mean;
    }

    /**
     * Sample standard deviation of percolation threshold.
     *
     * @return
     */
    public double standardDeviation() {
        return stddev;
    }

    /**
     * Low endpoint of 95% confidence interval
     *
     * @return
     */
    public double confidenceLo() {
        return (mean - 1.96 * stddev / (Math.sqrt(results.length)));
    }

    /**
     * High endpoint of 95% confidence interval
     *
     * @return
     */
    public double confidenceHi() {
        return (mean + 1.96 * stddev / (Math.sqrt(results.length)));
    }

    /**
     * Test client
     *
     * @param args
     */
    public static void main(String[] args) {
        int gridSize = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(gridSize, trials);

        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.standardDeviation());
        System.out.println("95% confidence interval = ["
            + stats.confidenceLo()
            + ", "
            + stats.confidenceHi() + "]");
    }

}
