package Controller;

import Model.Loader;
import java.io.IOException;
import View.UserAction;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class ControllerFacade implements Controllable {

    private static ControllerFacade instance;
    private final GameController controller;

    private ControllerFacade() {
        controller = new GameController(new Loader());
    }

    public static ControllerFacade getInstance() {
        if (instance == null) {
            instance = new ControllerFacade();
        }
        return instance;
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
    public boolean[] getCatalog() {
        return controller.getCatalog();
    }

    @Override
    public int[][] getGame(char level) throws NotFoundException {
        return controller.getGame(level);
    }

    @Override
    public void driveGames(String sourcePath) throws SolutionInvalidException {
        controller.driveGames(sourcePath);
    }

    @Override
    public boolean[][] verifyGame(int[][] game) {
        return controller.verifyGame(game);
    }

    @Override
    public int[][] solveGame(int[][] game) throws InvalidGame {
        return controller.solveGame(game);
    }

    @Override
    public void logUserAction(UserAction userAction) throws IOException {
        controller.logUserAction(userAction);
    }

    @Override
    public int[][] undo(int[][] board) throws IOException {
        return controller.undo(board);
    }

    @Override
    public void completeCurrentGame() throws IOException {
        controller.completeCurrentGame();
    }

}
