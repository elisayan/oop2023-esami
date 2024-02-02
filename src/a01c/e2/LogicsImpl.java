package a01c.e2;

import java.util.*;

public class LogicsImpl implements Logics {
    private final int size;
    private List<Pair<Integer, Integer>> selected = new LinkedList<>();
    private List<Pair<Integer, Integer>> rectangle = new LinkedList<>();
    private Pair<Integer, Integer> start;
    private Pair<Integer, Integer> end;

    public LogicsImpl(int size) {
        this.size = size;
    }

    @Override
    public String hit(int x, int y) {
        selected.add(new Pair<Integer, Integer>(x, y));
        if (selected.size() == 1) {
            return "1";
        }

        if (selected.size() == 2) {
            return "2";
        }

        if (selected.size() == 3) {
            start = selected.get(0);
            end = selected.get(1);

        }

        if (selected.size() >= 4) {
            start = new Pair<Integer, Integer>(start.getX() - 1, start.getY() - 1);
            end = new Pair<Integer, Integer>(end.getX() + 1, end.getY() + 1);
        }
        setRectangle();

        return null;
    }

    private void setRectangle() {
        rectangle.clear();
        for (int i = Math.min(start.getX(), end.getX()); i <= Math.max(start.getX(), end.getX()); i++) {
            for (int j = Math.min(start.getY(), end.getY()); j <= Math.max(start.getY(), end.getY()); j++) {
                if (!isSelectedCell(i, j)) {
                    rectangle.add(new Pair<Integer, Integer>(i, j));
                }
            }
        }
    }

    private boolean isSelectedCell(int i, int j){
        List<Pair<Integer, Integer>> fistSecond = selected.subList(0, 2);        
        return fistSecond.stream().anyMatch(e->e.getX()==i && e.getY()==j);
    }

    @Override
    public boolean isOver() {
        return rectangle.size() >= size * size;
    }

    @Override
    public boolean isRectangle(int x, int y) {
        return rectangle.contains(new Pair<>(x,y));
    }

}
