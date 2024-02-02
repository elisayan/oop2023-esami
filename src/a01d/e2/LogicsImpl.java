package a01d.e2;

import java.util.*;

public class LogicsImpl implements Logics {

    private final int size;
    private List<Pair<Integer, Integer>> square = new LinkedList<>();
    private Pair<Integer, Integer> heart;

    public LogicsImpl(int size) {
        this.size = size;
    }

    @Override
    public boolean hit(int x, int y) {
        if (square.isEmpty()) {
            setSquare(x, y);
            heart = new Pair<Integer, Integer>(x, y);
            return true;
        }

        if (isOnBorder(x, y)) {
            moveSquare(x, y);
            return true;
        }

        return false;
    }

    private boolean isOnBorder(int x, int y){
        return x == 0 || y == 0 || x == size - 1 || y == size - 1;
    }

    private void setSquare(int x, int y) {
        for (int i = x - 2; i <= x + 2; i++) {
            for (int j = y - 2; j <= y + 2; j++) {
                square.add(new Pair<Integer, Integer>(i, j));
            }
        }
    }

    private void moveSquare(int x, int y) {
        square.clear();

        heart = new Pair<Integer, Integer>(heart.getX() + (x == 0 ? -1 : (x == size - 1 ? 1 : 0)),
                heart.getY() + (y == 0 ? -1 : (y == size - 1 ? 1 : 0)));

        setSquare(heart.getX(), heart.getY());
    }

    @Override
    public boolean isOver() {
        return square.stream().anyMatch(e -> e.getX() < 0 || e.getX() >= size || e.getY() < 0 || e.getY() >= size);
    }

    @Override
    public boolean isSquare(int x, int y) {
        return square.contains(new Pair<>(x, y));
    }

}
