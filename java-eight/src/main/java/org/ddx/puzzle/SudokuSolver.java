package org.ddx.puzzle;

import java.util.*;

/**
 *   A fast, home-grown Sudoku solving algorithm.
 *
 *   Written as a single class for the Hackerrank Sudoku challenge.
 *   https://www.hackerrank.com/challenges/sudoku
 *
 *   Algorithm:
 *   1.  Maintain a set of "blocked" numbers for each cell of the grid.
 *   2.  When a cell has eight blocked numbers, it will "resolve" to the final unblocked number (1-9).
 *   3.  Cycle through all cells in the grid.  If any number is found to be resolved, it will add this number to the set of
 *   blocked numbers for each of the other cells in the column, row and subgrid (3x3).  (One time only)
 *   4.  If, after a cycle without resolving anything new, then attempt an inclusive approach:
 *   5.  Cycle through each of the 9 subgrids.  For each number (1-9), it will check if there is only cell in the subgrid
 *   where that number is not blocked.  If found, it will resolve the cell to that number (and repeat step 3).
 *   6.  If, after cycling through the previous 5 steps and nothing can be resolved, then make a "guess" on one of the remaining cells
 *   using an unblocked number.
 *   6.  The cell to guess against should be the one with the highest count of blocked numbers.  In the case of multiple cells
 *   with the same count, choose any.
 *   7.  A snapshot of the state of the grid will be made before resolving the guess.  This is to roll back in the event that
 *   the guess produces an unsolvable condition.
 *   8.  Continue the previous steps until either the entire grid is resolved, or an unsolvable condition occurs
 *   (i.e; all numbers in a cell become blocked).  If that happens, roll back the previous guess.
 *   9.  When rolling back, the failed guess is added to the set of blocked numbers for that cell.
 *   10.  If the guess has exhausted all of the numbers in its cell, then roll back to an earlier guess.
 *   11.  If guessing does not solve the grid, then the Sudoku is unsolvable.
 *
 */
public class SudokuSolver {

    public static final int TOTAL_CELLS = 81;

    private final SudokuCell[][] puzzle = new SudokuCell[9][9];

    public SudokuSolver() {
        initialize();
    }

    /**
     *  Initializes (or reinitializes) an empty puzzle.
     */
    public void initialize() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                SudokuCell cell = new SudokuSolver.SudokuCell();
                cell.resolvedValue = 0;
                puzzle[col][row] = cell;
            }
        }
    }

    /**
     *  Loads the puzzle from a 2d array.
     *
     * @param input
     */
    public void loadFromArray(int[][] input) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                puzzle[col][row].resolvedValue = input[col][row];
            }
        }
    }

    /**
     *  Loads the puzzle from a list of rows in an unsolved Sudoko grid.
     *
     *  The numbers in each row should be separated by a space.
     *
     * @param puzzleLines
     */
    public void loadFromLines(List<String> puzzleLines) {
        for (int row=0; row<9; row++) {
            String rowLine = puzzleLines.get(row);
            String[] columnCells = rowLine.split(" ");
            for (int col = 0; col<9; col++) {
                int colValue = Integer.parseInt(columnCells[col]);
                puzzle[col][row].resolvedValue = colValue;
            }
        }
    }

    /**
     * Returns a 9x9 array of the currently resolved values of the puzzle.
     *
     */
    public int[][] getPuzzle() {
        int[][] grid = new int[9][9];
        for (int col=0; col<9; col++) {
            for (int row=0; row<9; row++) {
                grid[col][row] = puzzle[col][row].resolvedValue;
            }
        }
        return grid;
    }

    /**
     * Creates a snapshot of the current puzzle state.
     *
     * @return
     */
    private SudokuCell[][] cloneState() {
        SudokuCell[][] clone = new SudokuCell[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                SudokuCell cell = new SudokuCell();
                cell.resolved = puzzle[col][row].resolved;
                cell.resolvedValue = puzzle[col][row].resolvedValue;
                cell.blockedValues = new HashSet<>(puzzle[col][row].blockedValues);
                clone[col][row] = cell;
            }
        }
        return clone;
    }

    /**
     * Restores the puzzle state from a snapshot/clone.
     *
     * @param clone
     */
    private void restoreClonedState(SudokuCell[][] clone) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                SudokuCell cell = clone[col][row];
                puzzle[col][row].resolved = cell.resolved;
                puzzle[col][row].resolvedValue = cell.resolvedValue;
                puzzle[col][row].blockedValues = new HashSet<>(cell.blockedValues);
            }
        }
    }

    /**
     *  Prints the current puzzle state to the System.out.
     */
    public void printPuzzle() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                System.out.print(puzzle[col][row].resolvedValue);
                System.out.print(" ");
            }
            System.out.println("");
        }
    }

    /**
     * Solves the puzzle using the algorithm described above.
     *
     * @throws SudokuSolverException
     */
    public void solve() throws SudokuSolverException {
        // maintain a snapshot of the puzzle before each "guess" in order to roll-back if the solution becomes unsolvable.
        Deque<PreGuessState> guesses = new ArrayDeque<PreGuessState>();

        int resolvedCellCount = 0;
        while (resolvedCellCount < TOTAL_CELLS) {
            // has any action occurred in this pass? used to determine if a guess should be made.
            boolean hadAction = false;

            // blocking pass - attempt to solve by eliminating duplicate values in columns, rows and subgrids
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    SudokuCell cell = puzzle[col][row];

                    // resolving a cell is done in two passes.  In the first pass, the resolved value is set,
                    //  in the second pass, the columns/rows/subgrids are updated so that the value can never be duplicated.
                    if (!cell.resolved) {
                        if (cell.resolvedValue > 0) {
                            resolveCell(cell, col, row);
                            resolvedCellCount++;
                            hadAction = true;
                        } else if (cell.blockedValues.size() == 8) {
                            try {
                                cell.resolvedValue = findUnblocked(cell.blockedValues);
                            } catch (SudokuSolverException e) {
                                resolvedCellCount = rollbackGuess(guesses);
                            }
                            hadAction = true;
                        }
                    }
                }
            }

            if (!hadAction) {
                // attempt to solve by determining if a number can only be in one location for each subgrid
                for (int row = 0; row < 9; row = row + 3) {
                    for (int col = 0; col < 9; col = col + 3) {
                        for (int i = 1; i < 10; i++) {
                            try {
                                if (resolveForSubgrid(i, col, row)) {
                                    hadAction = true;
                                }
                            } catch (SudokuSolverException sse) {
                                resolvedCellCount = rollbackGuess(guesses);
                                hadAction = true;
                            }
                        }
                    }
                }
            }

            if (!hadAction) {
                // did not resolve any calls in last pass -- attempt an educated guess for one of the cells
                guessValue(guesses, resolvedCellCount);
            }

        }
    }

    /**
     * Returns the first number (from 1 to 9) that is not in this Set.
     *
     * @param blockedValues
     * @return
     * @throws SudokuSolverException
     */
    private int findUnblocked(Set<Integer> blockedValues) throws SudokuSolverException {
        for (int i = 1; i < 10; i++) {
            if (!blockedValues.contains(i)) {
                return i;
            }
        }
        throw new SudokuSolverException("all numbers 1-9 are blocked");
    }



    /**
     * Checks to see if the given number can be resolved for a 3x3 subgrid.
     *
     * @param i - number we are trying to resolve
     * @param rowTop - index of top row of subgrid
     * @param colTop - index of left column of subgrid
     * @return - true if the number was resolved
     */
    private boolean resolveForSubgrid(int i, int colTop, int rowTop) throws SudokuSolverException {
        int foundCol = -1;
        int foundRow = -1;
        for (int row = rowTop; row < rowTop + 3; row++) {
            for (int col = colTop; col < colTop + 3; col++) {
                if (puzzle[col][row].resolvedValue == i) {
                    return false;
                }

                if (!puzzle[col][row].blockedValues.contains(i)) {
                    if (foundCol >= 0) {
                        // found more than one, return
                        return false;
                    } else {
                        foundCol = col;
                        foundRow = row;
                    }
                }
            }
        }

        if (foundCol < 0) {
            throw new SudokuSolverException("number " + i + " does not fit anywhere in the subgrid " + colTop + ", " + rowTop);
        }

        puzzle[foundCol][foundRow].resolvedValue = i;
        return true;
    }

    /**
     *  Sets the cell state to resolved and blocks the number's further usage from row/column and subgrid.
     *
     * @param cell
     * @param col - index of column where the cell resides
     * @param row - index of row where the cell resides
     */
    private void resolveCell(SudokuCell cell, int col, int row) {
        cell.resolved = true;
        int resolutionNumber = cell.resolvedValue;
        for (int c = 0; c < 9; c++) {
            puzzle[c][row].blockedValues.add(resolutionNumber);
        }
        for (int r = 0; r < 9; r++) {
            puzzle[col][r].blockedValues.add(resolutionNumber);
        }

        int subgridCol = Math.floorDiv(col, 3) * 3;
        int subgridRow = Math.floorDiv(row, 3) * 3;
        for (int r = subgridRow; r < subgridRow + 3; r++) {
            for (int c = subgridCol; c < subgridCol + 3; c++) {
                puzzle[c][r].blockedValues.add(resolutionNumber);
            }
        }

        // block the other numbers from this cell.
        for (int i = 1; i < 10; i++) {
            puzzle[col][row].blockedValues.add(i);
        }
    }

    /**
     * Make an educated guess of a unresolved value in the subgrid.
     *
     * Finds a cell with the highest number of blocked elements and resolves using one of those unblocked elements.
     *
     * @param guesses - history of the state of puzzle before each prior guess
     * @param currentResolvedCells - number of unresolved cells before making this guess (for rollback)
     */
    private void guessValue(Deque<PreGuessState> guesses, int currentResolvedCells) throws SudokuSolverException {
        SudokuCell guessCell = null;
        int guessCellBlocked = 0;
        int guessRow = -1;
        int guessCol = -1;

        // find the cell with the highest number of blocked elements and choose a value from the unblocked elements
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                SudokuCell cell = puzzle[col][row];
                if (cell.resolvedValue == 0 && (cell.blockedValues.size() > guessCellBlocked)) {
                    guessCell = cell;
                    guessCellBlocked = cell.blockedValues.size();
                    guessRow = row;
                    guessCol = col;
                }
            }
        }

        // snapshot the puzzle before making the guess -- for rollback purposes
        PreGuessState historyItem = new PreGuessState();
        historyItem.clonedState = cloneState();
        historyItem.attemptedGuesses = new HashSet<>();
        historyItem.guessCol = guessCol;
        historyItem.guessRow = guessRow;
        historyItem.resolvedCells = currentResolvedCells;

        int guessVal = findUnblocked(guessCell.blockedValues);
        historyItem.attemptedGuesses.add(guessVal);
        guesses.push(historyItem);

        guessCell.resolvedValue = guessVal;
    }

    /**
     * If the puzzle gets in an inconsistent state, attempt to roll-back and retry a previous guess.
     *
     * @param guesses - history of guesses
     * @return - the number of resolved cells after reverting to the previous state.
     */
    private int rollbackGuess(Deque<PreGuessState> guesses) throws SudokuSolverException {
        int restoredResolveCount = 0;

        boolean retry = true;
        while (retry) {
            retry = false;
            if (guesses.isEmpty()) {
                throw new SudokuSolverException("guesses exhausted -- unable to solve Sudoku");
            }
            PreGuessState historyItem = guesses.pop();
            restoreClonedState(historyItem.clonedState);
            restoredResolveCount = historyItem.resolvedCells;

            SudokuCell guessCell = puzzle[historyItem.guessCol][historyItem.guessRow];
            guessCell.blockedValues.addAll(historyItem.attemptedGuesses);
            try {
                int nextGuess = findUnblocked(guessCell.blockedValues);
                historyItem.attemptedGuesses.add(nextGuess);
                guessCell.resolvedValue = nextGuess;
                guesses.push(historyItem);
            } catch (SudokuSolverException sse) {
                retry = true;
            }
        }

        return restoredResolveCount;
    }

    public static class SudokuCell {
        int resolvedValue = 0;
        Set<Integer> blockedValues = new HashSet<Integer>();
        boolean resolved = false;
    }

    private static class PreGuessState {
        SudokuCell[][] clonedState;
        int resolvedCells;
        int guessCol;
        int guessRow;
        Set<Integer> attemptedGuesses;
    }

    /**
     *  Checked exception to trigger rollback.
     */
    public static class SudokuSolverException extends Exception {
        public SudokuSolverException(String s) {
            super(s);
        }
    }

    public static void main(String args[]) {
        // scan lines of input (used for Hackerrank solution)
        Scanner in = new Scanner(System.in);

        SudokuSolver solver = new SudokuSolver();

        // first input is number of puzzles to solve
        int n = in.nextInt();
        for (int i = 0; i < n; i++) {
            solver.initialize();
            int[][] grid = new int[9][9];
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    grid[col][row] = in.nextInt();
                }
            }
            solver.loadFromArray(grid);

            try {
                solver.solve();
            } catch (SudokuSolverException e) {
                e.printStackTrace();
            }
            solver.printPuzzle();
        }
    }
}

