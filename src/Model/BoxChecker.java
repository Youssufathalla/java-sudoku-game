package Model;

public class BoxChecker extends AbstractChecker {

    public BoxChecker(int[][] board, DuplicateResult result, int boxIndex) {
        super(board, result, boxIndex);
    }

    
    public void run() {
        int[] count = new int[10];
        StringBuilder[] positions = new StringBuilder[10];

        int startRow = (index / 3) * 3;
        int startCol = (index % 3) * 3;

        int pos = 1;
        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                int v = board[r][c];
                if (v == 0) {
                    pos++;
                    continue;
                }
                count[v]++;
                if (positions[v] == null) {
                    positions[v] = new StringBuilder();
                }
                if (positions[v].length() > 0) {
                    positions[v].append(",");
                }
                positions[v].append(pos);
                pos++;
            }
        }

        for (int digit = 1; digit <= 9; digit++) {
            if (count[digit] > 1) {
                result.addError(
                        "BOX " + (index + 1) + " #" + digit
                        + " [" + positions[digit] + "]"
                );
            }
        }
    }
}