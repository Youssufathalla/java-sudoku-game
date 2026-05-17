package Model;

public class Game {

    private final int[][] board;
    private final String difficulty;

    public Game(int[][] board, String difficulty) {
        this.board = board;
        this.difficulty = difficulty;
    }

    public int[][] getBoard() {
        return board;
    }

    public String getDifficulty() {
        return difficulty;
    }
}
