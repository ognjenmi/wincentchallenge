package org.ognjenmilic.wincent.dodgeball;

import java.util.Objects;

public class Line {
    private final int k;
    private final long n;

    public Line(int k, long n) {
        this.k = k;
        this.n = n;
    }

    @Override
    public int hashCode() {
        return Objects.hash(k, n);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Line)) {
            return false;
        }
        return k == ((Line) obj).k && n == ((Line) obj).n;
    }

    public int getK() {
        return k;
    }

    public long getN() {
        return n;
    }
}
