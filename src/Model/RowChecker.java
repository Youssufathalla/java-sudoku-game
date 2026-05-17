package Model;

public class RowChecker extends AbstractChecker {

    public RowChecker(int[][] board, DuplicateResult result, int rowIndex) {
        super(board, result, rowIndex);
    }

    
    public void run() {
        int[] count = new int[10];
        StringBuilder[] positions = new StringBuilder[10];

        for (int col = 0; col < 9; col++) {
            int v = board[index][col];
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
            positions[v].append(col + 1);
        }

        for (int digit = 1; digit <= 9; digit++) {
            if (count[digit] > 1) {
                result.addError(
                        "ROW " + (index + 1) + " #" + digit
                        + " [" + positions[digit] + "]"
                );
            }
        }
    }
}
