package org.ognjenmilic.wincent.dodgeball;

public record Position(int index, long x, long y) {

    public long deltaX(Position p) {
        return p.x - x;
    }

    public long deltaY(Position p) {
        return p.y - y;
    }

    public double distance(Position p) {
        return Math.sqrt(deltaX(p) * deltaX(p) + deltaY(p) * deltaY(p));
    }

    public int direction(Position p) {
        if (p.y > y) {
            return 1;
        }
        if (p.y < y) {
            return -1;
        }
        if (p.x > x) {
            return 1;
        }
        return -1;
    }
}
