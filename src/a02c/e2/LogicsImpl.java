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
        Pair<Integer, Integer> actual = new Pair<>(x, y);

        if (contour.isEmpty()) {
            contour = setContour(x - 1, y - 1, x + 1, y + 1);
            calculateAngle();
            return true;
        }

        if (angle.contains(actual)) {
            contour = extendContour(x, y);
            calculateAngle();
            return true;
        }
        return false;
    }

    private void calculateAngle() {
        List<Integer> xList = new ArrayList<>();
        List<Integer> yList = new ArrayList<>();

        for (Pair<Integer, Integer> pair : contour) {
            xList.add(pair.getX());
            yList.add(pair.getY());
        }

        int minX = Collections.min(xList);
        int maxX = Collections.max(xList);
        int minY = Collections.min(yList);
        int maxY = Collections.max(yList);

        angle.clear();
        angle.add(new Pair<>(minX, minY));
        angle.add(new Pair<>(minX, maxY));
        angle.add(new Pair<>(maxX, minY));
        angle.add(new Pair<>(maxX, maxY));
    }

    private List<Pair<Integer, Integer>> extendContour(int x, int y) {
        Pair<Integer, Integer> topLeft = new Pair<>(angle.get(0).getX() - 1, angle.get(0).getY() - 1);
        Pair<Integer, Integer> bottomRight = new Pair<>(angle.get(3).getX() + 1, angle.get(3).getY() + 1);
        return setContour(topLeft.getX(), topLeft.getY(), bottomRight.getX(), bottomRight.getY());
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
    public boolean isContour(int x, int y) {
        return contour.contains(new Pair<>(x, y));
    }

    @Override
    public boolean isOver() {
        return contour.stream()
                .anyMatch(e -> e.getX() == -1 || e.getY() == -1 || e.getX() == size || e.getY() == size);
    }
}
