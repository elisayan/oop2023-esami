package a01a.e2;

import java.util.*;

public class LogicsImpl implements Logics {

    private final int size;
    private List<Pair<Integer, Integer>> selected = new LinkedList<>();
    private boolean moving = false;

    public LogicsImpl(int size) {
        this.size = size;
    }

    @Override
    public String hit(int x, int y) {
        if (isAdjiacent(x, y) || moving) {
            moving = true;
            selected = move();
            return null;
        }

        if (selected.contains(new Pair<>(x, y))) {
            return String.valueOf(selected.indexOf(new Pair<>(x, y)));
        }
        selected.add(new Pair<Integer, Integer>(x, y));
        return String.valueOf(selected.size());
    }

    private boolean isAdjiacent(int x, int y) {
        if (selected.isEmpty()) {
            return false;
        }

        return selected.stream().anyMatch(p -> (p.getX() - 1 == x && p.getY() - 1 == y) ||
                (p.getX() + 1 == x && p.getY() - 1 == y) ||
                (p.getX() - 1 == x && p.getY() + 1 == y) ||
                (p.getX() + 1 == x && p.getY() + 1 == y) ||
                (p.getX() == x && p.getY() - 1 == y) ||
                (p.getX() == x && p.getY() + 1 == y) ||
                (p.getX() + 1 == x && p.getY() == y) ||
                (p.getX() - 1 == x && p.getY() == y));
    }

    private List<Pair<Integer, Integer>> move() {
        List<Pair<Integer, Integer>> moveList = new LinkedList<>();


        for (Pair<Integer, Integer> pair : selected) {
            moveList.add(new Pair<Integer, Integer>(pair.getX() + 1, pair.getY() - 1));
        }
        return moveList;
    }

    @Override
    public boolean isOver() {
        return selected.stream().anyMatch(e -> e.getX() < 0 || e.getY() < 0 || e.getX() > size || e.getY() > size);
    }

    @Override
    public String getMark(int x, int y) {
        Pair<Integer, Integer> pair = new Pair<>(x, y);
        int index = selected.indexOf(pair);

        if (index >= 0) {
            return String.valueOf(index + 1);
        } else {
            return null;
        }
    }

}
