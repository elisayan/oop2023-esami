package a01d.e2;

import java.util.*;

public class LogicsImpl implements Logics {

    private int size;
    private List<Pair<Integer, Integer>> square = new LinkedList<>();
    private Pair<Integer, Integer> start;

    public LogicsImpl(int size) {
        this.size = size;
    }

    @Override
    public boolean hit(int x, int y) {
        if (square.isEmpty()) {
            square = setSquare(x, y);
            start = new Pair<Integer, Integer>(x, y);
            return true;
        }

        if (!square.isEmpty() && (x == 0 || y == 0 || x == size - 1 || y == size - 1)) {
            square = moveSquare(x, y);
            return true;
        }

        return false;
    }

    private List<Pair<Integer, Integer>> moveSquare(int x, int y) {
        List<Pair<Integer, Integer>> update = new LinkedList<>();

        if (y == 0) {
            update = setSquare(start.getX(), start.getY() - 1);
        }

        if (x == 0) {
            update = setSquare(start.getX() - 1, start.getY());
        }

        if (x == size - 1) {
            update = setSquare(start.getX() + 1, start.getY());
        }

        if (y == size - 1) {
            update = setSquare(start.getX(), start.getY() + 1);
        }

        return update;
    }

    private List<Pair<Integer, Integer>> setSquare(int x, int y) {
        List<Pair<Integer, Integer>> mySquare = new LinkedList<>();
        for (int i = x - 2; i <= x + 2; i++) {
            for (int j = y - 2; j <= y + 2; j++) {
                mySquare.add(new Pair<Integer, Integer>(i, j));
            }
        }
        start = new Pair<Integer, Integer>(x, y);
        return mySquare;
    }

    @Override
    public boolean isOver() {
        return square.stream()
                .anyMatch(e -> e.getX() == 0 || e.getX() == size - 1 || e.getY() == 0 || e.getY() == size - 1);
    }

    @Override
    public boolean isHitted(int x, int y) {
        return square.contains(new Pair<Integer, Integer>(x, y));
    }

}
