package org.ddx.puzzle;

import org.ddx.util.FileReaderUtil;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created on 7/12/2017.
 */
public class SudokuSolverTest {

    private static final Logger logger = LoggerFactory.getLogger(SudokuSolverTest.class);

    @Test
    public void testSolveSudokus() throws IOException, SudokuSolver.SudokuSolverException {
        for (int i=1;i<=7;i++) {
            String sudokuPuzzlePath = "datasets/sudokus/unsolved_" + i + ".txt";
            logger.debug("solving Sudoku for {}", sudokuPuzzlePath);
            List<String> unresolvedSudokuInput = FileReaderUtil.readFileAsStringList(sudokuPuzzlePath);
            testSolveSudoku(unresolvedSudokuInput);
        }
    }

    private void testSolveSudoku(List<String> unresolvedSudokuInput) throws IOException, SudokuSolver.SudokuSolverException {
        SudokuSolver sudokuSolver = new SudokuSolver();
        sudokuSolver.loadFromLines(unresolvedSudokuInput);
        sudokuSolver.solve();
        sudokuSolver.printPuzzle();
        int[][] solved = sudokuSolver.getPuzzle();

        // check rows
        for (int row=0; row<9; row++) {
            Set<Integer> uniques = new HashSet<>();
            for (int col=0; col<9; col++) {
                uniques.add(solved[col][row]);
            }
            assertEquals(9, uniques.size());
        }

        // check columns
        for (int col=0; col<9; col++) {
            Set<Integer> uniques = new HashSet<>();
            for (int row=0; row<9; row++) {
                uniques.add(solved[col][row]);
            }
            assertEquals(9, uniques.size());
        }

        // check 3x3 subgrids
        for (int row=0; row<9; row=row+3) {
            for (int col=0; col<9; col=col+3) {
                verifySubgrid(col, row, solved);
            }
        }
    }

    private void verifySubgrid(int colTop, int rowTop, int[][] solution) {
        Set<Integer> uniques = new HashSet<>();
        for (int r=rowTop; r<(rowTop+3); r++) {
            for (int c=colTop; c<(colTop+3); c++) {
                uniques.add(solution[c][r]);
            }
        }
        assertEquals(9, uniques.size());
    }

}
