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
}
