package Model;

import java.io.BufferedReader;
import java.io.FileReader;

public class BoardReader {

    public static int[][] read(String file) throws Exception {

        int[][] board = new int[9][9];
        BufferedReader br = new BufferedReader(new FileReader(file));

        for (int i = 0; i < 9; i++) {

            String line = br.readLine();

            if (line == null) {
                for (int j = 0; j < 9; j++) {
                    board[i][j] = 0;
                }
                continue;
            }

            String[] parts = line.split(",");

            for (int j = 0; j < 9; j++) {

                if (j >= parts.length) {
 
                    board[i][j] = 0;
                    continue;
                }

                String cell = parts[j].trim();

   
                if (cell.isEmpty()) {
                    board[i][j] = 0;
                    continue;
                }

                int val;

                try {
                    val = Integer.parseInt(cell);
                } catch (Exception e) {
                    br.close();
                    throw new Exception("INVALID INPUT");
                }


                if (val < 0 || val > 9) {
                    br.close();
                    throw new Exception("INVALID INPUT");
                }

                board[i][j] = val;
            }
        }

        br.close();
        return board;
    }
}
