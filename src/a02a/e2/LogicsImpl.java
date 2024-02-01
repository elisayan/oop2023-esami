package a02a.e2;

import java.util.*;

public class LogicsImpl implements Logics{

    private final int size;
    private Set<Pair<Integer, Integer>> asterisch = new HashSet<>();
    private List<Pair<Integer, Integer>> selected = new LinkedList<>();

    public LogicsImpl(int size) {
        this.size = size;
        setAsterisch();
    }

    private void setAsterisch(){
        for (int i = 1; i < size; i+=2) {
            for (int j = 0; j < size; j++) {
                asterisch.add(new Pair<Integer,Integer>(i, j));
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 1; j < size; j+=2) {
                asterisch.add(new Pair<Integer,Integer>(i, j));
            }
        }
    }

    @Override
    public boolean isAsterisch(int x, int y) {
        return asterisch.contains(new Pair<>(x,y));
    }

    @Override
    public boolean hit(int x, int y) {
        if (!asterisch.contains(new Pair<>(x,y))) {
            selected.add(new Pair<Integer,Integer>(x, y));
            return true;
        }

        return false;
    }

    @Override
    public boolean isOver() {
        if (selected.size()>=4) {
            List<Pair<Integer, Integer>> lastFour = selected.subList(selected.size()-4, selected.size());

            List<Integer> xList = lastFour.stream().map(Pair::getX).distinct().sorted().toList();
            List<Integer> yList = lastFour.stream().map(Pair::getY).distinct().sorted().toList();

            return xList.size() == 2 && yList.size() == 2 &&
                    xList.get(0).equals(xList.get(xList.size() - 1) - 2) &&
                    yList.get(0).equals(yList.get(yList.size() - 1) - 2);
        }
        return false;
    }
    
}
