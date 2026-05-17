package Model;

import java.util.ArrayList;
import java.util.List;

public class DuplicateResult {

    private final List<String> errors = new ArrayList<>();
    private boolean valid = true;

    public void addError(String s) {
        errors.add(s);
        valid = false;
    }

    public boolean isValid() {
        return valid;
    }

    public List<String> getErrors() {
        return errors;
    }
}