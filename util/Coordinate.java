package util;
public class Coordinate extends Pair<Integer, Integer> {

    public static Coordinate from(String data) {
        return from(data, ",");
    }

    public static Coordinate from(String data, String delimiter) {
        String[] items = data.split(delimiter);
        return new Coordinate(Integer.parseInt(items[0]), Integer.parseInt(items[1]));
    }

    public Coordinate(int x, int y) {
        super(x, y);
    }

    public int x() {
        return first();
    }

    public int y() {
        return second();
    }
}
