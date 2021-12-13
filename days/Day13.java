package days;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import util.Coordinate;
import util.Pair;

public class Day13 extends Base {
    private int[][] paper;
    private List<Pair<String, Integer>> folds = new ArrayList<>();
    private int maxX, maxY;

    public Day13() throws Throwable {
        readInput();
    }

    @Override
    void runFirst() throws Throwable {
        foldPaper(true);
        long count = 0;
        for (int ii = 0; ii < maxY; ++ii) {
            for (int jj = 0; jj < maxX; ++jj) {
                if (paper[ii][jj] > 0) {
                    count += 1;
                }
            }
        }
        System.out.println("Visible dots after 1 fold: " + count);
    }

    @Override
    void runSecond() throws Throwable {
        readInput();
        foldPaper(false);
        print();
    }

    private void foldPaper(boolean firstOnly) {
        if (firstOnly) {
            makeFold(folds.get(0));
        } else {
            for (var pair : folds) {
                makeFold(pair);
            }
        }
    }

    private void makeFold(Pair<String, Integer> pair) {
        String axis = pair.first();
        if ("x".equals(axis)) {
            // vertical fold |
            int col = pair.second();
            for (int ii = 0; ii < maxY; ++ii) {
                for (int jj = col + 1, otherCol = col - 1; jj < maxX; ++jj, --otherCol) {
                    paper[ii][otherCol] += paper[ii][jj];
                }
            }
            maxX = col;
        } else {
            // horizontal fold ----
            int row = pair.second();
            for (int ii = row + 1, otherRow = row - 1; ii < maxY; ++ii, --otherRow) {
                for (int jj = 0; jj < maxX; ++jj) {
                    paper[otherRow][jj] += paper[ii][jj];
                }
            }
            maxY = row;
        }

    }

    void readInput() throws Throwable {
        List<Coordinate> coordinates = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("inputs/day13.txt"))) {
            maxX = 0;
            maxY = 0;
            String line = reader.readLine();
            while (!line.isEmpty()) {
                Coordinate current = Coordinate.from(line, ",");
                coordinates.add(current);
                if (current.x() > maxX) {
                    maxX = current.x();
                }
                if (current.y() > maxY) {
                    maxY = current.y();
                }
                line = reader.readLine();
            }
            maxX += 1;
            maxY += 1;
            paper = new int[maxY][maxX];
            coordinates.forEach(
                    c -> {
                        paper[c.y()][c.x()] = 1;
                    });

            line = reader.readLine();

            while (line != null) {
                String[] info = line.substring("fold along ".length()).split("=");
                folds.add(Pair.of(info[0], Integer.valueOf(info[1])));
                line = reader.readLine();
            }
        }
    }

    void print() {
        for (int ii = 0; ii < maxY; ++ii) {
            for (int jj = 0; jj < maxX; ++jj) {
                System.out.print((paper[ii][jj] == 0 ? "." : "#"));
            }
            System.out.println();
        }
        System.out.println();
    }

}
