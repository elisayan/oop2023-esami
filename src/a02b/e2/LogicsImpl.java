package a02b.e2;

import java.util.*;

public class LogicsImpl implements Logics {

    private Set<Pair<Integer, Integer>> square = new HashSet<>();
    private Pair<Integer, Integer> central;
    private List<Pair<Integer, Integer>> diagonal = new LinkedList<>();
    private boolean over = false;

    @Override
    public boolean hit(int x, int y) {
        Pair<Integer, Integer> pair = new Pair<Integer, Integer>(x, y);

        if (square.isEmpty()) {
            square = calculateSquare(x, y);
            return true;
        }

        if (!square.isEmpty() && diagonal.contains(pair)) {
            square = setSquare(pair);
            return true;
        }

        if (!square.isEmpty() && central.equals(pair)) {
            this.over = true;
            return true;
        }

        return false;
    }

    private Set<Pair<Integer,Integer>> setSquare(Pair<Integer,Integer> move){
        Set<Pair<Integer,Integer>> output = new HashSet<>();
        if (move.getX() == central.getX() - 1 && move.getY() == central.getY() - 1) {
            output = calculateSquare(move.getX() - 2, move.getY() - 2);
        }
        if (move.getX() == central.getX() + 1 && move.getY() == central.getY() + 1) {
            output = calculateSquare(move.getX() + 2, move.getY() + 2);
        }
        if (move.getX() == central.getX() - 1 && move.getY() == central.getY() + 1) {
            output = calculateSquare(move.getX() - 2, move.getY() + 2);
        }
        if (move.getX() == central.getX() + 1 && move.getY() == central.getY() - 1) {
            output = calculateSquare(move.getX() + 2, move.getY() - 2);
        }
        output.add(move);
        return output;
    }

    private Set<Pair<Integer,Integer>> calculateSquare(int x, int y){
        Set<Pair<Integer,Integer>> mysquare = new HashSet<>();
        central = new Pair<Integer,Integer>(x, y);
        for (int i = x - 2; i <= x + 2; i++) {
            for (int j = y - 2; j <= y + 2; j++) {
                mysquare.add(new Pair<Integer, Integer>(i, j));
            }
        }

        diagonal.add(new Pair<>(x - 1, y - 1));
        diagonal.add(new Pair<>(x + 1, y - 1));
        diagonal.add(new Pair<>(x - 1, y + 1));
        diagonal.add(new Pair<>(x + 1, y + 1));

        mysquare.removeAll(diagonal);

        return mysquare;
    }

    @Override
    public boolean isSquare(int x, int y) {
        return square.contains(new Pair<>(x, y));
    }

    @Override
    public boolean isOver() {
        return this.over;
    }
}
