package days;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import util.Coordinate;

public class Day9 extends Base {

    @Override
    void runFirst() throws Throwable {
        List<List<Integer>> input = readInput();
        long sum = 0;
        for (int ii = 0; ii < input.size(); ++ii) {
            for (int jj = 0; jj < input.get(ii).size(); ++jj) {
                int count = 0;
                int currentValue = input.get(ii).get(jj);
                if (isValidIndex(input, ii - 1, jj) && input.get(ii - 1).get(jj) > currentValue) {
                    count += 1;
                }

                if (isValidIndex(input, ii + 1, jj) && input.get(ii + 1).get(jj) > currentValue) {
                    count += 1;
                }

                if (isValidIndex(input, ii, jj - 1) && input.get(ii).get(jj - 1) > currentValue) {
                    count += 1;
                }

                if (isValidIndex(input, ii, jj + 1) && input.get(ii).get(jj + 1) > currentValue) {
                    count += 1;
                }

                if (count == neighbourCount(input, ii, jj)) {
                    sum += (1 + currentValue);
                }
            }
        }
        System.out.println("Sum of low point heights: " + sum);
    }

    @Override
    void runSecond() throws Throwable {
        List<List<Integer>> input = readInput();

        List<Set<Coordinate>> basins = new ArrayList<>();

        for (int ii = 0; ii < input.size(); ++ii) {
            for (int jj = 0; jj < input.get(ii).size(); ++jj) {
                int count = 0;
                int currentValue = input.get(ii).get(jj);
                if (isValidIndex(input, ii - 1, jj) && input.get(ii - 1).get(jj) > currentValue) {
                    count += 1;
                }

                if (isValidIndex(input, ii + 1, jj) && input.get(ii + 1).get(jj) > currentValue) {
                    count += 1;
                }

                if (isValidIndex(input, ii, jj - 1) && input.get(ii).get(jj - 1) > currentValue) {
                    count += 1;
                }

                if (isValidIndex(input, ii, jj + 1) && input.get(ii).get(jj + 1) > currentValue) {
                    count += 1;
                }

                if (count == neighbourCount(input, ii, jj)) {
                    basins.add(findPoints(input, ii, jj));
                }
            }
        }

        Collections.sort(basins, (s1, s2) -> {
            return Integer.compare(s2.size(), s1.size());
        });
        long answer = basins.get(0).size() * basins.get(1).size() * basins.get(2).size();
        System.out.println("Basins: " + answer);
    }

    final Set<Coordinate> visited = new HashSet<>();

    Set<Coordinate> findPoints(List<List<Integer>> input, int xx, int yy) {
        Coordinate current = new Coordinate(xx, yy);
        if (visited.contains(current) || !isValidIndex(input, xx, yy) || input.get(xx).get(yy) == 9) {
            return Collections.emptySet();
        }
        visited.add(current);
        Set<Coordinate> set = new HashSet<>();
        set.add(current);

        set.addAll(findPoints(input, xx - 1, yy));
        set.addAll(findPoints(input, xx + 1, yy));

        set.addAll(findPoints(input, xx, yy - 1));
        set.addAll(findPoints(input, xx, yy + 1));

        return set;
    }

    List<List<Integer>> readInput() throws Throwable {
        List<List<Integer>> list = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("inputs/day9.txt"))) {
            String line = reader.readLine();
            while (line != null) {
                List<Integer> data = new ArrayList<>();
                for (char ch : line.toCharArray()) {
                    data.add(Character.digit(ch, 10));
                }
                list.add(data);
                line = reader.readLine();
            }
        }
        return list;
    }

    boolean isValidIndex(List<List<Integer>> input, int i, int j) {
        return i >= 0 && i < input.size() && j >= 0 && j < input.get(i).size();
    }

    int neighbourCount(List<List<Integer>> input, int i, int j) {
        int count = 0;
        if (i > 0) {
            count++;
        }
        if (j > 0) {
            count++;
        }
        if (i < input.size() - 1) {
            count++;
        }
        if (j < input.get(i).size() - 1) {
            count++;
        }
        return count;
    }
}
