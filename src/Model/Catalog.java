package Model;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Catalog {

    private boolean current;
    private boolean allModesExist;

    public Catalog() {
        this.current = checkUnfinishedGame();
        this.allModesExist = checkGamesInDifficultyFiles();
    }

    public Catalog(boolean current, boolean allModesExist) {
        this.current = current;
        this.allModesExist = allModesExist;
    }

    public boolean hasUnfinishedGame() {
        return current;
    }

    public boolean hasGamesInDifficultyFiles() {
        return allModesExist;
    }

    private boolean checkUnfinishedGame() {
        File f = new File("incomplete/current.csv");
        return f.exists();
    }

    private boolean checkGamesInDifficultyFiles() {
        return hasGame("easy") && hasGame("medium") && hasGame("hard");
    }

    private boolean hasGame(String folder) {
        File dir = new File(folder);
        if (!dir.exists() || !dir.isDirectory()) return false;

        File[] files = dir.listFiles((d, name) -> name.endsWith(".csv"));
        return files != null && files.length > 0;
    }
}
