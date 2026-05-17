package Model;

public abstract class AbstractChecker  {

    protected final int[][] board;
    protected final DuplicateResult result;
    protected final int index;

    public AbstractChecker(int[][] board, DuplicateResult result, int index) {
        this.board = board;
        this.result = result;
        this.index = index;
    }

    
}