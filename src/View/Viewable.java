package View;

import java.io.IOException;
import Model.Catalog;
import Model.Game;
import Model.DifficultyEnum;
import Controller.NotFoundException;
import Controller.SolutionInvalidException;
import Controller.InvalidGame;

public interface Viewable {

    Catalog getCatalog();

    Game getGame(DifficultyEnum level) throws NotFoundException;

    void driveGames(Game sourceGame) throws SolutionInvalidException;

    String verifyGame(Game game);

    int[] solveGame(Game game) throws InvalidGame;

    void logUserAction(String userAction) throws IOException;
    void completeCurrentGame() throws IOException;
     void undo(Game game) throws IOException;
     void saveCurrentGame(Game game) throws IOException;

}
