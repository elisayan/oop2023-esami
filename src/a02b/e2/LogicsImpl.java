package a02b.e2;

import java.util.*;

public class LogicsImpl implements Logics {
    private List<Pair<Integer, Integer>> square = new LinkedList<>();
    private List<Pair<Integer, Integer>> direction = new LinkedList<>();
    private Pair<Integer, Integer> end;
    private boolean over = false;

    @Override
    public boolean hit(int x, int y) {
        if (square.isEmpty()) {
            setSquare(x, y);
            return true;
        }

        if (end.equals(new Pair<>(x, y))) {
            over = true;
            return false;
        }

        if (direction.contains(new Pair<>(x,y))) {
            moveSquare(x,y);
            return true;
        }

        return false;
    }

    private void setSquare(int x, int y) {
        end = new Pair<Integer, Integer>(x, y);
        for (int i = x - 2; i <= x + 2; i++) {
            for (int j = y - 2; j <= y + 2; j++) {
                if (!((i == x - 1 && j == y - 1) || (i == x + 1 && j == y + 1) || (i == x - 1 && j == y + 1)
                        || (i == x + 1 && j == y - 1))) {
                    square.add(new Pair<Integer, Integer>(i, j));
                } else {
                    direction.add(new Pair<Integer, Integer>(i, j));
                }
            }
        }
    }

    private void moveSquare(int x, int y) {
        if (x == end.getX() - 1 && y == end.getY() - 1) {
            square.clear();
            setSquare(x - 2, y - 2);
        }

        if (x == end.getX() + 1 && y == end.getY() + 1) {
            square.clear();
            setSquare(x + 2, y + 2);
        }

        if (x == end.getX() - 1 && y == end.getY() + 1) {
            square.clear();
            setSquare(x - 2, y + 2);
        }

        if (x == end.getX() + 1 && y == end.getY() - 1) {
            square.clear();
            setSquare(x + 2, y - 2);
        }
    }

    @Override
    public boolean isOver() {
        return over;
    }

    @Override
    public boolean isSquare(int x, int y) {
        return square.contains(new Pair<>(x, y));
    }
}
