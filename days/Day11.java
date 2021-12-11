package days;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.Coordinate;

public class Day11 extends Base {

    private int[][] input = new int[10][10];

    static final int MAX_ROUNDS = 100;
    int currentRound = 0;

    Integer firstBigFlash = null;
    long flashCount = 0;

    @Override
    void runFirst() throws Throwable {
        readInput();
        for (currentRound = 0; currentRound < MAX_ROUNDS; ++currentRound) {
            runCore();
        }
        System.out.println("Flashing after 100 rounds: " + flashCount);
    }

    @Override
    void runSecond() throws Throwable {
        readInput();
        currentRound = 0;
        flashCount = 0;
        while (firstBigFlash == null) {
            runCore();
            currentRound += 1;
        }
        System.out.println("First big flash is at round: " + firstBigFlash);
    }

    void print() {
        for (int ii = 0; ii < input.length; ++ii) {
            for (int jj = 0; jj < input[ii].length; ++jj) {
                System.out.print(input[ii][jj]);
            }
            System.out.println();
        }
    }

    void runCore() {
        final Set<Coordinate> flashing = new HashSet<>();
        final Set<Coordinate> alreadyFlashed = new HashSet<>();

        for (int ii = 0; ii < input.length; ++ii) {
            for (int jj = 0; jj < input[ii].length; ++jj) {
                input[ii][jj] += 1;
                if (input[ii][jj] > 9) {
                    flashing.add(new Coordinate(ii, jj));
                }
            }
        }

        for (Coordinate coord : flashing) {
            flash(alreadyFlashed, coord);
        }

        flashCount += alreadyFlashed.size();

        for (int ii = 0; ii < input.length; ++ii) {
            for (int jj = 0; jj < input[ii].length; ++jj) {
                if (input[ii][jj] > 9) {
                    input[ii][jj] = 0;
                }

            }
        }
        if (alreadyFlashed.size() == 100 && firstBigFlash == null) {
            firstBigFlash = currentRound + 1;
            return;
        }
    }

    void flash(final Set<Coordinate> alreadyFlashed, Coordinate coord) {
        if (alreadyFlashed.contains(coord)) {
            return;
        }
        alreadyFlashed.add(coord);
        getNeighbours(coord).forEach(n -> {
            input[n.x()][n.y()] += 1;
        });

        getNeighbours(coord).forEach(n -> {
            if (input[n.x()][n.y()] > 9) {
                flash(alreadyFlashed, n);
            }
        });
    }

    void readInput() throws Throwable {
        try (BufferedReader reader = new BufferedReader(new FileReader("inputs/day11.txt"))) {
            String line = reader.readLine();
            int row = 0;
            while (line != null) {
                int col = 0;
                for (char ch : line.toCharArray()) {
                    input[row][col] = Character.digit(ch, 10);
                    ++col;
                }
                line = reader.readLine();
                ++row;
            }
        }
    }

    boolean isValidIndex(int i, int j) {
        return i >= 0 && i < input.length && j >= 0 && j < input[i].length;
    }

    List<Coordinate> getNeighbours(Coordinate coordinate) {
        return getNeighbours(coordinate.x(), coordinate.y());
    }

    List<Coordinate> getNeighbours(final int i, final int j) {
        List<Coordinate> neighbours = new ArrayList<>(9);
        for (int xx = i - 1; xx <= i + 1; ++xx) {
            for (int yy = j - 1; yy <= j + 1; ++yy) {
                if (isValidIndex(xx, yy)) {
                    neighbours.add(new Coordinate(xx, yy));
                }
            }
        }
        return neighbours;
    }
}
