package org.ognjenmilic.wincent.dodgeball;

import java.util.function.Function;

public enum SideVector {
    NE(1, 1, p -> p.y() - p.x()), E(0, 1, Position::y), SE(-1,  -1, p -> p.y() + p.x()),
    S(-2, -1, Position::x), SW(1, -1, p -> p.y() - p.x()), W(0,-1, Position::y),
    NW(-1, 1, p -> p.y() + p.x()), N(-2, 1, Position::x);

    public final int coefficient;
    public final int sign;
    Function<Position, Long> getN;

    SideVector(int coefficient, int sign, Function<Position, Long> getN) {
        this.coefficient = coefficient;
        this.sign = sign;
        this.getN = getN;
    }

    boolean isInSameDirection(Position p1, Position p2) {
        if (p1.x() == p2.x()) {
            if (coefficient != -2) {
                return false;
            }
            return (p2.y() > p1.y()) ? sign == 1 : sign == -1;
        }

        if (p1.y() == p2.y()) {
            if (coefficient != 0) {
                return false;
            }
            return (p2.x() > p1.x()) ? sign == 1 : sign == -1;
        }

        long dx = p2.x() - p1.x();
        long dy = p2.y() - p1.x();
        if (Math.abs(dx) != Math.abs(dy)) {
            return false;
        }

        if (dx * dy > 0) {
            if (coefficient != 1) {
                return false;
            }
            return p2.y() > p1.y() ? sign == 1 : sign == -1;
        }

        if (coefficient != -1) {
            return false;
        }

        return (p2.y() > p1.y()) ? sign == 1 : sign == -1;
    }

    static boolean isCandidate(Position p1, Position p2) {
        if (p1.deltaX(p2) == 0 || p1.deltaY(p2) == 0) {
            return true;
        }

        return Math.abs(p2.x() - p1.x()) == Math.abs(p2.y() - p1.y());
    }

    SideVector next() {
        return this.ordinal() < 7 ? SideVector.class.getEnumConstants()[this.ordinal() + 1] : NE ;
    }

    SideVector opposite() {
        for (SideVector v : SideVector.values()) {
            if (v.coefficient == coefficient && v.sign != sign) {
                return v;
            }
        }
        return this;
    }
}
