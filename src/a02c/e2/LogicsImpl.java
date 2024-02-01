package a02c.e2;

import java.util.*;

public class LogicsImpl implements Logics {

    private final int size;
    private List<Pair<Integer, Integer>> contour = new LinkedList<>();
    private List<Pair<Integer, Integer>> angle = new LinkedList<>();

    public LogicsImpl(int size) {
        this.size = size;
    }

    @Override
    public void hit(int x, int y) {
        Pair<Integer, Integer> pair = new Pair<Integer, Integer>(x, y);

        if (contour.isEmpty()) {
            expandContour(x-1, y-1, x+1, y+1);
        }

        if (!contour.isEmpty() && angle.contains(pair)) {
            if (pair.equals(angle.get(0))) {
                expandContour(x-1, y-1, angle.get(3).getX(), angle.get(3).getY());
            }

            if (pair.equals(angle.get(1))) {
                expandContour(x-1, y+1, angle.get(2).getX(), angle.get(2).getY());
            }

            if (pair.equals(angle.get(2))) {
                expandContour(x+1, y-1, angle.get(1).getX(), angle.get(1).getY());
            }

            if (pair.equals(angle.get(3))) {
                expandContour(x+1, y+1, angle.get(0).getX(), angle.get(0).getY());
            }
        }
    }

    private void setAngle() {
        List<Integer> listX = new LinkedList<>();
        List<Integer> listY = new LinkedList<>();
        

        for (Pair<Integer, Integer> pair : contour) {
            listX.add(pair.getX());
            listY.add(pair.getY());
        }

        int minX = listX.stream().min(Integer::compareTo).orElseThrow();
        int minY = listX.stream().min(Integer::compareTo).orElseThrow();
        int maxX = listX.stream().max(Integer::compareTo).orElseThrow();
        int maxY = listX.stream().max(Integer::compareTo).orElseThrow();
        
        angle.clear();
        angle.add(new Pair<Integer, Integer>(minX, minY));
        angle.add(new Pair<Integer, Integer>(minX, maxY));
        angle.add(new Pair<Integer, Integer>(maxX, minY));
        angle.add(new Pair<Integer, Integer>(maxX, maxY));
    }

    private void expandContour(int x, int y, int x2, int y2) {
        contour.clear();
        for (int i = Math.min(x2, x); i <= Math.max(x2, x); i++) {
            for (int j = Math.min(y, y2); j <= Math.max(y, y2); j++) {
                if (i == x || i == x2 || j == y || j == y2) {
                    contour.add(new Pair<Integer, Integer>(i, j));
                }

            }
        }
        setAngle();
    }

    @Override
    public boolean isOver() {
        return contour.stream()
                .anyMatch(e -> e.getX() == 0 || e.getY() == 0 || e.getX() == size - 1 || e.getY() == size - 1);
    }

    @Override
    public boolean isContour(int x, int y) {
        return contour.contains(new Pair<>(x, y));
    }

}
