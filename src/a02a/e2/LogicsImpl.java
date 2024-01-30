package a02a.e2;

import java.util.*;

public class LogicsImpl implements Logics {

    private int size;
    private Set<Pair<Integer, Integer>> asterischs = new HashSet<>();
    private List<Pair<Integer, Integer>> clicked = new LinkedList<>();

    public LogicsImpl(int size) {
        this.size = size;
    }

    private void calculateAsterischs() {
        for (int i = 1; i < size; i += 2) {
            for (int j = 0; j < size; j++) {
                asterischs.add(new Pair<Integer, Integer>(i, j));
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 1; j < size; j += 2) {
                asterischs.add(new Pair<Integer, Integer>(i, j));
            }
        }
    }

    @Override
    public boolean isAsterisch(int x, int y) {
        calculateAsterischs();
        return asterischs.contains(new Pair<>(x, y));
    }

    @Override
    public boolean isValid(int x, int y) {
        if (asterischs.contains(new Pair<>(x, y))) {
            return false;
        }

        clicked.add(new Pair<Integer, Integer>(x, y));
        return true;
    }

    @Override
    public boolean isOver() {
        if (clicked.size() >= 4) {
            List<Pair<Integer, Integer>> lastFour = clicked.subList(clicked.size() - 4, clicked.size());
            Set<Integer> xSet = new HashSet<>();
            Set<Integer> ySet = new HashSet<>();

            for (Pair<Integer, Integer> pair : lastFour) {
                xSet.add(pair.getX());
                ySet.add(pair.getY());
                System.out.println("xSet: " + xSet);
                System.out.println("ySet: " + ySet);
            }

            return xSet.equals(ySet);
        }

        return false;
    }

}
