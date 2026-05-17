package View;

import java.io.IOException;
import Controller.*;
import Model.*;

public class GameView implements Viewable {

    private final Controllable controller;

    public GameView(Controllable controller) {
        this.controller = controller;
    }

    @Override
    public void completeCurrentGame() throws IOException {
        controller.completeCurrentGame();
    }

    public Catalog getCatalog() {
        boolean[] c = controller.getCatalog();
        return new Catalog(c[0], c[1]);
    }

    @Override
    public Game getGame(DifficultyEnum level) throws NotFoundException {
        int[][] b = controller.getGame(level.name().charAt(0));
        return new Game(b, level.name());
    }

    @Override
    public void driveGames(Game sourceGame) throws SolutionInvalidException {
        controller.driveGames(sourceGame.getDifficulty());
    }
    @Override
public void saveCurrentGame(Game game) throws IOException {
    controller.saveCurrentGame(game.getBoard());
}


    @Override
    public String verifyGame(Game game) {
        boolean[][] ok = controller.verifyGame(game.getBoard());
        boolean bad = false, zero = false;

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (game.getBoard()[i][j] == 0) {
                    zero = true;
                }
                if (!ok[i][j]) {
                    bad = true;
                    if (sb.length() > 0) {
                        sb.append(" ");
                    }
                    sb.append(i + 1).append(",").append(j + 1);
                }
            }
        }

        if (!bad && !zero) {
            return "valid";
        }
        if (!bad) {
            return "incomplete";
        }
        return "invalid " + sb;
    }

    @Override
    public int[] solveGame(Game game) throws InvalidGame {
        int[][] sol = controller.solveGame(game.getBoard());
        int[] r = new int[sol.length];
        for (int i = 0; i < sol.length; i++) {
            r[i] = sol[i][2];
        }
        return r;
    }

    @Override
    public void logUserAction(String s) throws IOException {
        String[] p = s.split(",");
        controller.logUserAction(
                new UserAction(
                        Integer.parseInt(p[0]),
                        Integer.parseInt(p[1]),
                        Integer.parseInt(p[2]),
                        Integer.parseInt(p[3])
                )
        );
    }

    @Override
    public void undo(Game game) throws IOException {
        controller.undo(game.getBoard());
    }

}
