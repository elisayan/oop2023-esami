package a01c.e2;

public interface Logics {
    enum NumberClick {
        FIRST, SECOND, THIRD, OTHER
    }

    NumberClick hit(int x, int y);

    boolean isSquare(int x, int y);

    boolean isOver();
}
