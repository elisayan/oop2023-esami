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

        if (!square.isEmpty() && x == 0 || y == 0 || x == size - 1 || y == size - 1) {
            moveSquare(x, y);
            return true;
        }

        return false;
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
        if (x == 0) {
            heart = new Pair<Integer, Integer>(heart.getX() - 1, heart.getY());
            setSquare(heart.getX(), heart.getY());
        }

        if (y == 0) {
            heart = new Pair<Integer, Integer>(heart.getX(), heart.getY() - 1);
            setSquare(heart.getX(), heart.getY());
        }

        if (x == size - 1) {
            heart = new Pair<Integer, Integer>(heart.getX() + 1, heart.getY());
            setSquare(heart.getX(), heart.getY());
        }

        if (y == size - 1) {
            heart = new Pair<Integer, Integer>(heart.getX(), heart.getY() + 1);
            setSquare(heart.getX(), heart.getY());
        }
    }

    @Override
    public boolean isOver() {
        return square.stream().anyMatch(e -> e.getX() == -1 || e.getX() == size || e.getY() == -1 || e.getY() == size);
    }

    @Override
    public boolean isSquare(int x, int y) {
        return square.contains(new Pair<>(x, y));
    }

}
