package a01a.e2;

import java.util.*;

public interface Logics {
    Optional<Integer> hit (int x, int y);
    boolean isOver();
    Optional<Integer> getMark(int x, int y);
}
