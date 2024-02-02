package a01b.e2;

import java.util.*;

public interface Logics {
    Optional<String> hit (int x, int y);
    boolean isOver();
    Optional<Integer> getMark(int x, int y);
}
