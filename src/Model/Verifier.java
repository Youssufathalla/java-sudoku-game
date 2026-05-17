package Model;

public class Verifier {

    private final int[][] board;

    public Verifier(int[][] board) {
        this.board = board;
    }

    public String run() {

        DuplicateResult result = new DuplicateResult();

        for (int i = 0; i < 9; i++) {
            new RowChecker(board, result, i).run();
            new ColumnChecker(board, result, i).run();
            new BoxChecker(board, result, i).run();
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    return GameState.INCOMPLETE;
                }
            }
        }

        if (!result.isValid()) {
            return GameState.INVALID;
        }

        return GameState.VALID;
    }
}
