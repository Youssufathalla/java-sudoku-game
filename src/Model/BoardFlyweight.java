package Model;

import java.util.List;

public class BoardFlyweight {

    private final int[][] board;

    public BoardFlyweight(int[][] board) {
        this.board = board;
    }

    public boolean isValidCombination(int[] combination, List<int[]> emptyCells) {
        int[][] temp = copy(board);

        for (int i = 0; i < emptyCells.size(); i++) {
            int[] p = emptyCells.get(i);
            temp[p[0]][p[1]] = combination[i];
        }

        return GameState.VALID.equals(new Verifier(temp).run());
    }

    private int[][] copy(int[][] b) {
        int[][] c = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(b[i], 0, c[i], 0, 9);
        }
        return c;
    }
}
