package a01c.e2;

import java.util.*;

public class LogicsImpl implements Logics {

    private int size;
    private List<Pair<Integer, Integer>> selected = new LinkedList<>();
    private Set<Pair<Integer, Integer>> square = new HashSet<>();
    private Pair<Integer, Integer> myFirst;
    private Pair<Integer, Integer> mySecond;

    public LogicsImpl(int size) {
        this.size = size;
    }

    @Override
    public NumberClick hit(int x, int y) {
        Pair<Integer, Integer> pair = new Pair<>(x, y);
        selected.add(pair);

        switch (selected.size()) {
            case 1:
                myFirst = pair;
                return NumberClick.FIRST;
            case 2:
                mySecond = pair;
                return NumberClick.SECOND;
            case 3:
                square = setSquare(myFirst, mySecond);
                return NumberClick.THIRD;
            default:
                square = expandSquare();
                return NumberClick.OTHER;
        }
    }

    private Set<Pair<Integer, Integer>> setSquare(Pair<Integer, Integer> first, Pair<Integer, Integer> second) {
        Set<Pair<Integer, Integer>> mySquare = new HashSet<>();

        for (int i = Math.min(first.getX(), second.getX()); i <= Math.max(first.getX(), second.getX()); i++) {
            for (int j = Math.min(first.getY(), second.getY()); j <= Math.max(first.getY(), second.getY()); j++) {
                mySquare.add(new Pair<>(i, j));
            }
        }

        mySquare.remove(selected.get(0));
        mySquare.remove(selected.get(1));
        return mySquare;
    }

    private Set<Pair<Integer, Integer>> expandSquare() {
        myFirst = new Pair<>(myFirst.getX() - 1, myFirst.getY() - 1);
        mySecond = new Pair<>(mySecond.getX() + 1, mySecond.getY() + 1);
        return setSquare(myFirst, mySecond);
    }

    @Override
    public boolean isOver() {
        return square.size() >= size * size;
    }

    @Override
    public boolean isSquare(int x, int y) {
        return square.contains(new Pair<>(x, y));
    }
}