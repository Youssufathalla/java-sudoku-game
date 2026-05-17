package Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameDriver {

    private final int[][] solvedBoard;

    public static final String EASY = "Easy";
    public static final String MEDIUM = "Medium";
    public static final String HARD = "Hard";

    public GameDriver(int[][] solvedBoard) {
        this.solvedBoard = solvedBoard;
    }

    public Map<String, int[][]> generateAll() throws Exception {

        Verifier verifier = new Verifier(solvedBoard);
        if (verifier.run() != GameState.VALID) {
            throw new Exception("Source board is not valid");
        }

        int[][] easy = copyBoard(solvedBoard);
        int[][] medium = copyBoard(solvedBoard);
        int[][] hard = copyBoard(solvedBoard);

        removeCells(easy, 10);
        removeCells(medium, 20);
        removeCells(hard, 25);

        Map<String, int[][]> games = new HashMap<>();
        games.put(EASY, easy);
        games.put(MEDIUM, medium);
        games.put(HARD, hard);

        return games;
    }

    private int[][] copyBoard(int[][] original) {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                copy[i][j] = original[i][j];
            }
        }
        return copy;
    }

    private void removeCells(int[][] board, int count) {
        RandomPairs randomPairs = new RandomPairs();
        List<int[]> pairs = randomPairs.generateDistinctPairs(count);

        for (int[] pair : pairs) {
            int r = pair[0];
            int c = pair[1];

            if (board[r][c] != 0) {
                board[r][c] = 0;
            }
        }
    }
}
