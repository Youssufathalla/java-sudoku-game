package Controller;

import java.io.IOException;
import View.UserAction;

public interface Controllable {

    boolean[] getCatalog();

    int[][] getGame(char level) throws NotFoundException;

    void driveGames(String sourcePath) throws SolutionInvalidException;

    boolean[][] verifyGame(int[][] game);

    int[][] solveGame(int[][] game) throws InvalidGame;

    void logUserAction(UserAction userAction) throws IOException;

    void completeCurrentGame() throws IOException;

    int[][] undo(int[][] board) throws IOException;
    void saveCurrentGame(int[][] game) throws IOException;

}
