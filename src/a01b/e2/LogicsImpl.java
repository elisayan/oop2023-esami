package a01b.e2;

import java.util.*;

public class LogicsImpl implements Logics {

    private final static int NUMBER_OF_MARKS = 5;
    private final int size;
    private List<Pair<Integer, Integer>> selected = new LinkedList<>();
    private boolean changeDirection = false;

    public LogicsImpl(int size) {
        this.size = size;
    }

    @Override
    public String hit(int x, int y) {
        Pair<Integer, Integer> pair = new Pair<Integer, Integer>(x, y);
        if (selected.size() == NUMBER_OF_MARKS) {
            selected = move();
            return null;
        }

        if (selected.contains(pair)) {
            return String.valueOf(selected.indexOf(pair));
        }
        selected.add(pair);
        return String.valueOf(selected.size());
    }

    private List<Pair<Integer, Integer>> move() {
        List<Pair<Integer, Integer>> mySelected = new LinkedList<>();
        if (!changeDirection && selected.stream().anyMatch(e -> e.getX() == 0)) {
            changeDirection = true;
        }

        for (Pair<Integer, Integer> pair : selected) {
            if (changeDirection) {
                mySelected.add(new Pair<Integer, Integer>(pair.getX() + 1, pair.getY()));
            } else {
                mySelected.add(new Pair<Integer, Integer>(pair.getX() - 1, pair.getY()));
            }

        }

        return mySelected;
    }

    @Override
    public boolean isOver() {
        return selected.stream().anyMatch(e -> e.getX() == size);
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
