package a02c.e2;

import java.util.*;

public class LogicsImpl implements Logics {

    private List<Pair<Integer, Integer>> contour = new LinkedList<>();
    private List<Pair<Integer, Integer>> angle = new LinkedList<>();
    private int size;

    public LogicsImpl(int size) {
        this.size = size;
    }

    @Override
    public boolean hit(int x, int y) {
        Pair<Integer, Integer> actual = new Pair<Integer, Integer>(x, y);

        if (contour.isEmpty()) {
            contour = setContour(x - 1, y - 1, x + 1, y + 1);
            calculateAngle();
            return true;
        }

        if (!contour.isEmpty() && angle.contains(actual)) {
            contour = extendContour(x, y);
            calculateAngle();
            return true;
        }
        return false;
    }

    private void calculateAngle() {
        List<Integer> xList = new LinkedList<>();
        List<Integer> yList = new LinkedList<>();

        for (Pair<Integer, Integer> pair : contour) {
            xList.add(pair.getX());
            yList.add(pair.getY());
        }

        int minX = xList.stream().min(Integer::compareTo).orElseThrow();
        int maxX = xList.stream().max(Integer::compareTo).orElseThrow();
        int minY = yList.stream().min(Integer::compareTo).orElseThrow();
        int maxY = yList.stream().max(Integer::compareTo).orElseThrow();

        angle.clear();
        angle.add(new Pair<Integer, Integer>(minX, minY));
        angle.add(new Pair<Integer, Integer>(minX, maxY));
        angle.add(new Pair<Integer, Integer>(maxX, minY));
        angle.add(new Pair<Integer, Integer>(maxX, maxY));
    }

    private List<Pair<Integer, Integer>> extendContour(int x, int y) {
        List<Pair<Integer, Integer>> extention = new LinkedList<>();
        Pair<Integer, Integer> actual = new Pair<Integer, Integer>(x, y);

        if (angle.get(0).equals(actual)) {
            extention = setContour(x - 1, y - 1, angle.get(3).getX(),
                    angle.get(3).getY());
        }

        if (angle.get(1).equals(actual)) {
            extention = setContour(x - 1, y + 1, angle.get(2).getX(),
                    angle.get(2).getY());
        }

        if (angle.get(2).equals(actual)) {
            extention = setContour(x + 1, y - 1, angle.get(1).getX(),
                    angle.get(1).getY());
        }

        if (angle.get(3).equals(actual)) {
            extention = setContour(x + 1, y + 1, angle.get(0).getX(),
                    angle.get(0).getY());
        }

        return extention;
    }

    private List<Pair<Integer, Integer>> setContour(int x, int y, int x2, int y2) {
        List<Pair<Integer, Integer>> myContour = new LinkedList<>();

        for (int i = Math.min(x, x2); i <= Math.max(x, x2); i++) {
            for (int j = Math.min(y, y2); j <= Math.max(y, y2); j++) {
                if (i == x || i == x2 || j == y || j == y2) {
                    myContour.add(new Pair<>(i, j));
                }
            }
        }
        return myContour;
    }

    @Override
    public boolean isHitted(int x, int y) {
        return contour.contains(new Pair<>(x, y));
    }

    @Override
    public boolean isOver() {
        return contour.stream()
                .anyMatch(e -> e.getX() == 0 || e.getY() == 0 || e.getX() == size - 1 || e.getY() == size - 1);
    }
}
