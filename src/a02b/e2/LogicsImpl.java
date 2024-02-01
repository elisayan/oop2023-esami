package a02b.e2;

import java.util.ArrayList;
import java.util.List;

public class LogicsImpl implements Logics {
    private List<Pair<Integer, Integer>> square = new ArrayList<>();
    private List<Pair<Integer, Integer>> direction = new ArrayList<>();
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

        if (direction.contains(new Pair<>(x, y))) {
            moveSquare(x, y);
            return true;
        }

        return false;
    }

    private void setSquare(int x, int y) {
        end = new Pair<>(x, y);
        for (int i = x - 2; i <= x + 2; i++) {
            for (int j = y - 2; j <= y + 2; j++) {
                Pair<Integer, Integer> cell = new Pair<>(i, j);
                if (!isCorner(cell)) {
                    square.add(cell);
                } else {
                    direction.add(cell);
                }
            }
        }
    }

    private void moveSquare(int x, int y) {
        int dx = x - end.getX();
        int dy = y - end.getY();

        square.clear();
        setSquare(end.getX() + 2 * dx, end.getY() + 2 * dy);
    }

    private boolean isCorner(Pair<Integer, Integer> cell) {
        int dx = Math.abs(cell.getX() - end.getX());
        int dy = Math.abs(cell.getY() - end.getY());
        return (dx == 1 && dy == 1);
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
