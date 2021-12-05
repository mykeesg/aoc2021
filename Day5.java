import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Day5 extends Base {

    class Line {
        Coordinate from, to;

        public Line(Coordinate from, Coordinate to) {
            this.from = from;
            this.to = to;
        }

        public List<Coordinate> getPoints() {
            List<Coordinate> points = new ArrayList<>();
            points.add(from);
            int xDiff = 0, yDiff = 0;

            if (isHorizontal()) {
                yDiff = from.y() < to.y() ? 1 : -1;
                for (int y = from.y() + yDiff; y != to.y(); y += yDiff) {
                    points.add(new Coordinate(from.x(), y));
                }
            }

            if (isVertical()) {
                xDiff = from.x() < to.x() ? 1 : -1;
                for (int x = from.x() + xDiff; x != to.x(); x += xDiff) {
                    points.add(new Coordinate(x, to.y()));
                }
            }

            if (isDiagonal()) {
                if ((from.x() < to.x() && from.y() < to.y()) || (from.x() > to.x() && from.y() > to.y())) {
                    // the diagonal is / <- this slope.
                    if (from.x() < to.x()) {
                        xDiff = 1;
                        yDiff = 1;
                    } else {
                        xDiff = -1;
                        yDiff = -1;
                    }
                } else {
                    // the diagonal is \ <- this slope.
                    if (from.x() < to.x()) {
                        xDiff = 1;
                        yDiff = -1;
                    } else {
                        xDiff = -1;
                        yDiff = 1;
                    }
                }
                for (int x = from.x() + xDiff, y = from.y() + yDiff; x != to.x()
                        && y != to.y(); x += xDiff, y += yDiff) {
                    points.add(new Coordinate(x, y));
                }
            }
            points.add(to);
            return points;
        }

        @Override
        public String toString() {
            return String.format("{%1$s -> %2$s}", from, to);
        }

        public boolean isHorizontal() {
            return from.x() == to.x();
        }

        public boolean isVertical() {
            return from.y() == to.y();
        }

        public boolean isDiagonal() {
            return Math.abs(from.x() - to.x()) == Math.abs(from.y() - to.y());
        }
    }

    @Override
    void runFirst() throws Throwable {
        System.out.println(String.format("Number of grid points with overlaps: %1$s", countLines(false)));
    }

    @Override
    void runSecond() throws Throwable {
        System.out.println(String.format("Number of grid points with overlaps: %1$s", countLines(true)));
    }

    int countLines(boolean useDiagonals) throws Throwable {

        Pair<List<Line>, Coordinate> input = readLines();
        List<Line> lines = input.first();
        int[][] grid = new int[input.second().x()][input.second().y()];
        for (Line line : lines) {
            if (line.isHorizontal() || line.isVertical() || (line.isDiagonal() && useDiagonals)) {
                List<Coordinate> points = line.getPoints();
                points.forEach(p -> grid[p.x()][p.y()]++);
            }
        }

        int count = 0;
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[i].length; ++j) {
                if (grid[i][j] > 1) {
                    ++count;
                }
            }
        }
        return count;
    }

    private Pair<List<Line>, Coordinate> readLines() throws Throwable {
        List<Line> lines = new ArrayList<>();
        int maxX = 0, maxY = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("day5.txt"))) {
            String line = reader.readLine();
            while (line != null) {
                String[] data = line.split("\\s+");
                Coordinate from = Coordinate.from(data[0]);
                if (from.x() > maxX) {
                    maxX = from.x();
                }
                if (from.y() > maxY) {
                    maxY = from.y();
                }
                Coordinate to = Coordinate.from(data[2]);

                if (to.x() > maxX) {
                    maxX = to.x();
                }
                if (to.y() > maxY) {
                    maxY = to.y();
                }

                lines.add(new Line(from, to));
                line = reader.readLine();
            }
        }
        return Pair.of(lines, new Coordinate(maxX + 1, maxY + 1));
    }
}
