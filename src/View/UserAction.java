package View;
public class UserAction {
    private int x;       
    private int y;         
    private int current;   
    private int previous;  

   
    public UserAction(int x, int y, int current, int previous) {
        this.x = x;
        this.y = y;
        this.current = current;
        this.previous = previous;
    }

 
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPrevious() {
        return previous;
    }

    public void setPrevious(int previous) {
        this.previous = previous;
    }

    @Override
    public String toString() {
        return String.format("UserAction{x=%d, y=%d, current=%d, previous=%d}", x, y, current, previous);
    }
}
