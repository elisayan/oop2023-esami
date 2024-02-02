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
    public Optional<String> hit(int x, int y) {
        Pair<Integer, Integer> pair = new Pair<Integer, Integer>(x, y);

        if (selected.size() < 5 && !selected.contains(pair)) {
            selected.add(pair);
            return Optional.of(String.valueOf(selected.size()));
        }

        if (selected.contains(pair)) {
            return Optional.of(String.valueOf(selected.indexOf(pair) + 1));
        }

        if (selected.size() == 5) {
            moveSelected();
        }
        return Optional.empty();
    }

    private void moveSelected() {
        if (!changeDirection && selected.stream().anyMatch(e -> e.getX() == 0)) {
            changeDirection = true;
        }
        selected.replaceAll(pair -> new Pair<Integer, Integer>(pair.getX() + (changeDirection ? 1 : -1), pair.getY()));
    }

    @Override
    public boolean isOver() {
        return selected.stream().anyMatch(e -> e.getX() >= size);
    }

    @Override
    public Optional<Integer> getMark(int x, int y) {
        Pair<Integer, Integer> pair = new Pair<Integer, Integer>(x, y);

        return Optional.of(this.selected.indexOf(pair)).filter(i -> i >= 0).map(i -> i + 1);
    }

}
