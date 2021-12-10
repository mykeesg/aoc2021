package util;

import java.util.Objects;

public class Pair<U, V> {
    private final U first;
    private final V second;

    public Pair(U first, V second) {
        this.first = first;
        this.second = second;
    }

    public U first() {
        return first;
    }

    public V second() {
        return second;
    }

    @Override
    public String toString() {
        return String.format("(%1$s;%2$s)", first(), second());
    }

    public static <U, V> Pair<U, V> of(U first, V second) {
        return new Pair<U, V>(first, second);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Pair<?, ?> other = (Pair<?, ?>) obj;
        return Objects.equals(first(), other.first()) && Objects.equals(second, other.second());
    }

    @Override
    public int hashCode() {
        return Objects.hash(first(), second());
    }
}
