package a02a.e2;

import java.util.*;
import java.util.stream.Collectors;

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
            List<Integer> xSet = new LinkedList<>();
            List<Integer> ySet = new LinkedList<>();

            for (Pair<Integer, Integer> pair : lastFour) {
                xSet.add(pair.getX());
                ySet.add(pair.getY());
            }

            xSet = xSet.stream().distinct().sorted().collect(Collectors.toList());
            ySet = ySet.stream().distinct().sorted().collect(Collectors.toList());

            return (xSet.size() == 2 && ySet.size() == 2) && xSet.get(0).equals(xSet.get(xSet.size() - 1) - 2)
                    && ySet.get(0).equals(ySet.get(ySet.size() - 1) - 2);
        }

        return false;
    }

}
