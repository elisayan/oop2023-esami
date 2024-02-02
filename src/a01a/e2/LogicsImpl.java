package a01a.e2;

import java.util.*;

public class LogicsImpl implements Logics{
    private final int size;
    private List<Pair<Integer,Integer>> selected = new LinkedList<>();
    private boolean moving = false;

    public LogicsImpl(int size) {
        this.size = size;
    }

    @Override
    public Optional<Integer> hit(int x, int y) {
        Pair<Integer,Integer> pair= new Pair<Integer,Integer>(x, y);

        if (isAdjacent(x, y)) {
            moving=true;
            move();
            return Optional.empty();
        }

        if (moving) {
            move();
            return Optional.empty();
        }

        if (selected.contains(pair)) {
            return Optional.of(selected.indexOf(pair)+1);
        }

        selected.add(pair);
        return Optional.of(selected.size());
    }

    private boolean isAdjacent(int x, int y){
        return selected.stream()
                .anyMatch(e -> Math.abs(e.getX() - x) <= 1 && Math.abs(e.getY() - y) <= 1);
    }

    private void move(){
        selected.replaceAll(pair -> new Pair<Integer, Integer>(pair.getX() + 1, pair.getY() - 1));
    }

    @Override
    public boolean isOver() {
        return selected.stream().anyMatch(e -> e.getX() >= size || e.getY() < 0);
    }

    @Override
    public Optional<Integer> getMark(int x, int y) {
        Pair<Integer,Integer> pair= new Pair<Integer,Integer>(x, y);
        return Optional.of(selected.indexOf(pair)).filter(i -> i > -1).map(i -> i + 1);
    }
}
