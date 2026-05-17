package Model;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class PermutationsIterator implements Iterator<int[]> {

    private final List<int[]> permutations;
    private int currentIndex = 0;

    public PermutationsIterator() {
        permutations = new ArrayList<>();
        generatePermutations();
    }

    private void generatePermutations() {
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                for (int k = 1; k <= 9; k++) {
                    for (int l = 1; l <= 9; l++) {
                        for (int m = 1; m <= 9; m++) {
                            permutations.add(new int[]{i, j, k, l, m});
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean hasNext() {
        return currentIndex < permutations.size();
    }

    @Override
    public int[] next() {
        return permutations.get(currentIndex++);
    }
}
