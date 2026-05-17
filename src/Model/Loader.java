package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Loader {

    private static final String INCOMPLETE_FOLDER = "incomplete";
    private static final String CURRENT_GAME_FILE = INCOMPLETE_FOLDER + "/current.csv";
    private static final String LOG_FILE = INCOMPLETE_FOLDER + "/log.txt";

    public void saveGame(int[][] board, String difficulty) throws IOException {
        if (hasCurrentGame()) {
            saveCurrentGame(board);
        } else {
            saveBoard(board, difficulty);
        }
    }

    private void saveBoard(int[][] board, String difficulty) throws IOException {
        String filename = difficulty.toLowerCase() + "/" + difficulty.toLowerCase() + "_" + System.currentTimeMillis() + ".csv";
        Files.createDirectories(Paths.get(difficulty.toLowerCase()));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (j > 0) writer.write(",");
                    writer.write(board[i][j] + "");
                }
                writer.newLine();
            }
        }
    }

    private void saveCurrentGame(int[][] board) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (j > 0) sb.append(",");
                sb.append(board[i][j]);
            }
            sb.append("\n");
        }

        Files.createDirectories(Paths.get(INCOMPLETE_FOLDER));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CURRENT_GAME_FILE))) {
            writer.write(sb.toString());
        }
    }

    public boolean hasCurrentGame() {
        return new java.io.File(CURRENT_GAME_FILE).exists();
    }

    public void deleteCurrentGame() throws IOException {
        new java.io.File(CURRENT_GAME_FILE).delete();
    }
    
     public void clearLog() throws IOException {
        new java.io.File(LOG_FILE).delete();
    }

    public Game loadGame(String difficulty) throws Exception {
        List<String> games = new ArrayList<>();
        String prefix = difficulty.toLowerCase();

        for (String fileName : new java.io.File(".").list()) {
            if (fileName.startsWith(prefix) && fileName.endsWith(".csv")) {
                games.add(fileName);
            }
        }

        if (games.isEmpty()) {
            throw new IOException("No saved games for " + difficulty);
        }

        String selectedGame = games.get(new Random().nextInt(games.size()));
        int[][] board = readCsv(selectedGame);

        return new Game(board, difficulty);
    }

    private int[][] readCsv(String fileName) throws Exception {
        return BoardReader.read(fileName);
    }

    public void startNewGame(int[][] newBoard, String difficulty) throws IOException {
        deleteCurrentGame();
        saveBoard(newBoard, difficulty);
    }
    
    public int[][] loadSolvedSudoku(String filePath) throws IOException {
        int[][] board = new int[9][9];
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null && row < 9) {
                String[] values = line.split(",");
                for (int col = 0; col < 9; col++) {
                    board[row][col] = Integer.parseInt(values[col].trim());
                }
                row++;
            }
        }
        return board;
    }

  
}