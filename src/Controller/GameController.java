package Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import Model.*;
import View.UserAction;

public class GameController implements Controllable {

    private final Loader loader;

    public GameController(Loader loader) {
        this.loader = loader;
    }

    public void completeCurrentGame() throws IOException {
        loader.deleteCurrentGame();
        loader.clearLog();
    }
    private final Random rng = new Random();

    @Override
    public boolean[] getCatalog() {
        Catalog c = new Catalog();
        return new boolean[]{c.hasUnfinishedGame(), c.hasGamesInDifficultyFiles()};
    }
    public void saveCurrentGame(int[][] board) throws IOException {
    new File("incomplete").mkdirs();

    try (BufferedWriter w =
         new BufferedWriter(new FileWriter("incomplete/current.csv"))) {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (j > 0) w.write(",");
                w.write(String.valueOf(board[i][j]));
            }
            w.newLine();
        }
    }
}


    @Override
    public int[][] getGame(char level) throws NotFoundException {
        String diff = mapLevel(level);
        File dir = new File(diff.toLowerCase());

        if (!dir.exists()) {
            throw new NotFoundException("No games for difficulty " + diff);
        }

        File[] files = dir.listFiles((d, n) -> n.endsWith(".csv"));
        if (files == null || files.length == 0) {
            throw new NotFoundException("No games for difficulty " + diff);
        }

        File chosen = files[rng.nextInt(files.length)];
        try {
            return BoardReader.read(chosen.getPath());
        } catch (Exception e) {
            throw new NotFoundException("Failed to read game");
        }
    }

    @Override
    public void driveGames(String sourcePath) throws SolutionInvalidException {
        try {
            int[][] solved = BoardReader.read(sourcePath);
            Verifier v = new Verifier(solved);

            if (!GameState.VALID.equals(v.run())) {
                throw new SolutionInvalidException("Source solution invalid");
            }

            GameDriver driver = new GameDriver(solved);
            Map<String, int[][]> games = driver.generateAll();

            saveGame(games.get(GameDriver.EASY), "easy");
            saveGame(games.get(GameDriver.MEDIUM), "medium");
            saveGame(games.get(GameDriver.HARD), "hard");

        } catch (Exception e) {
            throw new SolutionInvalidException(e.getMessage());
        }
    }

    @Override
    public boolean[][] verifyGame(int[][] game) {
        boolean[][] ok = new boolean[9][9];
        for (int i = 0; i < 9; i++) {
            Arrays.fill(ok[i], true);
        }

        String state = new Verifier(game).run();
        if (GameState.VALID.equals(state) || GameState.INCOMPLETE.equals(state)) {
            return ok;
        }

        for (int r = 0; r < 9; r++) {
            Map<Integer, List<Integer>> pos = new HashMap<>();
            for (int c = 0; c < 9; c++) {
                int v = game[r][c];
                if (v == 0) {
                    continue;
                }
                pos.computeIfAbsent(v, k -> new ArrayList<>()).add(c);
            }
            for (Map.Entry<Integer, List<Integer>> e : pos.entrySet()) {
                if (e.getValue().size() > 1) {
                    for (int c : e.getValue()) {
                        ok[r][c] = false;
                    }
                }
            }
        }

        for (int c = 0; c < 9; c++) {
            Map<Integer, List<Integer>> pos = new HashMap<>();
            for (int r = 0; r < 9; r++) {
                int v = game[r][c];
                if (v == 0) {
                    continue;
                }
                pos.computeIfAbsent(v, k -> new ArrayList<>()).add(r);
            }
            for (Map.Entry<Integer, List<Integer>> e : pos.entrySet()) {
                if (e.getValue().size() > 1) {
                    for (int r : e.getValue()) {
                        ok[r][c] = false;
                    }
                }
            }
        }

        for (int b = 0; b < 9; b++) {
            int sr = (b / 3) * 3;
            int sc = (b % 3) * 3;

            Map<Integer, List<int[]>> pos = new HashMap<>();
            for (int r = sr; r < sr + 3; r++) {
                for (int c = sc; c < sc + 3; c++) {
                    int v = game[r][c];
                    if (v == 0) {
                        continue;
                    }
                    pos.computeIfAbsent(v, k -> new ArrayList<>()).add(new int[]{r, c});
                }
            }
            for (Map.Entry<Integer, List<int[]>> e : pos.entrySet()) {
                if (e.getValue().size() > 1) {
                    for (int[] rc : e.getValue()) {
                        ok[rc[0]][rc[1]] = false;
                    }
                }
            }
        }

        return ok;
    }

    @Override
    public int[][] solveGame(int[][] game) throws InvalidGame {
        List<int[]> empty = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (game[i][j] == 0) {
                    empty.add(new int[]{i, j});
                }
            }
        }

        if (empty.size() != 5) {
            throw new InvalidGame("Expected 5 empty cells");
        }

        int[][] work = copy(game);
        BoardFlyweight fly = new BoardFlyweight(work);
        PermutationsIterator it = new PermutationsIterator();

        while (it.hasNext()) {
            int[] comb = it.next();
            if (fly.isValidCombination(comb, empty)) {
                int[][] res = new int[5][3];
                for (int k = 0; k < 5; k++) {
                    res[k][0] = empty.get(k)[0];
                    res[k][1] = empty.get(k)[1];
                    res[k][2] = comb[k];
                }
                new File("incomplete/current.csv").delete();
                new File("incomplete/log.txt").delete();
                return res;
            }
        }
        throw new InvalidGame("No solution");
    }

    @Override
    public void logUserAction(UserAction ua) throws IOException {
        new File("incomplete").mkdirs();
        try (BufferedWriter w = new BufferedWriter(new FileWriter("incomplete/log.txt", true))) {
            w.write(ua.getX() + "," + ua.getY() + "," + ua.getCurrent() + "," + ua.getPrevious());
            w.newLine();
        }
    }

    private void saveGame(int[][] board, String d) throws IOException {
        new File(d).mkdirs();
        String f = d + "/" + d + "_" + System.currentTimeMillis() + ".csv";
        try (BufferedWriter w = new BufferedWriter(new FileWriter(f))) {
            for (int[] r : board) {
                for (int j = 0; j < 9; j++) {
                    if (j > 0) {
                        w.write(",");
                    }
                    w.write(String.valueOf(r[j]));
                }
                w.newLine();
            }
        }
    }

    private int[][] copy(int[][] b) {
        int[][] c = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(b[i], 0, c[i], 0, 9);
        }
        return c;
    }

    private String mapLevel(char c) throws NotFoundException {
        c = Character.toLowerCase(c);
        if (c == 'e' || c == '1') {
            return "easy";
        }
        if (c == 'm' || c == '2') {
            return "medium";
        }
        if (c == 'h' || c == '3') {
            return "hard";
        }
        throw new NotFoundException("Invalid level");
    }

    @Override
    public int[][] undo(int[][] board) throws IOException {
        File log = new File("incomplete/log.txt");
        if (!log.exists()) {
            return board;
        }

        List<String> lines = java.nio.file.Files.readAllLines(log.toPath());
        if (lines.isEmpty()) {
            return board;
        }

        String last = lines.remove(lines.size() - 1);
        String[] p = last.split(",");

        int x = Integer.parseInt(p[0]);
        int y = Integer.parseInt(p[1]);
        int prev = Integer.parseInt(p[3]);

        board[x][y] = prev;

        java.nio.file.Files.write(log.toPath(), lines);

        try (BufferedWriter w = new BufferedWriter(new FileWriter("incomplete/current.csv"))) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (j > 0) {
                        w.write(",");
                    }
                    w.write(String.valueOf(board[i][j]));
                }
                w.newLine();
            }
        }

        return board;
    }

}
