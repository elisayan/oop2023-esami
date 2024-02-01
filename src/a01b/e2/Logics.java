package a01b.e2;

public interface Logics {
    enum NumberCells {
        FIRST, SECOND, THIRD, FOURTH, FIFTH, OTHER
    }

    String hit(int x, int y);
    String getMark(int x, int y);
    boolean isOver();
}
