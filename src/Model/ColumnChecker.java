package Model;

public class ColumnChecker extends AbstractChecker {

    public ColumnChecker(int[][] board, DuplicateResult result, int colIndex) {
        super(board, result, colIndex);
    }

    
    public void run() {
        int[] count = new int[10];
        StringBuilder[] positions = new StringBuilder[10];

        for (int row = 0; row < 9; row++) {
            int v = board[row][index];
            if (v == 0) {
                continue;   
            }
            count[v]++;
            if (positions[v] == null) {
                positions[v] = new StringBuilder();
            }
            if (positions[v].length() > 0) {
                positions[v].append(",");
            }
            positions[v].append(row + 1);
        }

        for (int digit = 1; digit <= 9; digit++) {
            if (count[digit] > 1) {
                result.addError(
                        "COL " + (index + 1) + " #" + digit
                        + " [" + positions[digit] + "]"
                );
            }
        }
    }
}