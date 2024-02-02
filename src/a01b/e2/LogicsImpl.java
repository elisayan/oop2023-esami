package a01b.e2;

import java.util.*;

public class LogicsImpl implements Logics {

    private final int size;
    private List<Pair<Integer, Integer>> selected = new LinkedList<>();
    private boolean changeDirection = false;

    public LogicsImpl(int size) {
        this.size = size;
    }

    @Override
    public String hit(int x, int y) {
        Pair<Integer, Integer> pair = new Pair<Integer, Integer>(x, y);

        if (selected.size() < 5 && !selected.contains(pair)) {
            selected.add(pair);
            return String.valueOf(selected.size());
        }

        if (selected.contains(pair)) {
            return String.valueOf(selected.indexOf(pair) + 1);
        }

        if (selected.size() == 5) {
            moveSelected();
        }
        return null;
    }

    private void moveSelected() {
        List<Pair<Integer, Integer>> mySelected = new LinkedList<>();

        if (selected.stream().anyMatch(e -> e.getX() == 0)) {
            changeDirection = true;
        }

        for (Pair<Integer, Integer> pair : selected) {
            mySelected.add(new Pair<Integer, Integer>(pair.getX() + (changeDirection ? 1 : -1), pair.getY()));
        }

        selected = mySelected;
    }

    @Override
    public boolean isOver() {
        return selected.stream().anyMatch(e -> e.getX() >= size);
    }

    @Override
    public String getMark(int x, int y) {
        Pair<Integer, Integer> pair = new Pair<Integer, Integer>(x, y);

        if (selected.contains(pair)) {
            return String.valueOf(selected.indexOf(pair) + 1);
        }

        return null;
    }

}
