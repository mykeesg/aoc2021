package days;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import util.Pair;

public class Day4 extends Base {

    private static class Board {
        final int[][] table = new int[5][5];
        final boolean[][] drawn = new boolean[5][5];

        // lookup table for positions
        final Map<Integer, Pair<Integer, Integer>> positions = new TreeMap<>();

        void addRow(int row, int[] data) {
            table[row] = data;
            for (int jj = 0; jj < 5; ++jj) {
                positions.put(data[jj], Pair.of(row, jj));
            }
        }

        void setDrawn(int value) {
            Pair<Integer, Integer> pos = positions.get(value);
            // we have this number
            if (pos != null) {
                drawn[pos.first()][pos.second()] = true;
            }
        }

        boolean hasWinning() {
            // yeah, i know.. not the prettiest, but gets the job done.
            for (int ii = 0; ii < 5; ++ii) {
                int countRows = 0;
                int countCols = 0;
                for (int jj = 0; jj < 5; ++jj) {
                    if (drawn[ii][jj]) {
                        countRows += 1;
                    }
                    if (drawn[jj][ii]) {
                        countCols += 1;
                    }
                }
                if (countRows == 5 || countCols == 5) {
                    return true;
                }
            }
            return false;
        }

        long calculate(int number) {
            long sum = 0;
            for (int ii = 0; ii < 5; ++ii) {
                for (int jj = 0; jj < 5; ++jj) {
                    if (!drawn[ii][jj]) {
                        sum += table[ii][jj];
                    }
                }
            }
            return sum * number;
        }
    }

    @Override
    public void runFirst() throws Throwable {
        System.out.println(String.format("First winning Bingo table value: %1$s", solve1()));
    }

    @Override
    public void runSecond() throws Throwable {
        System.out.println(String.format("Last winning Bingo table value: %1$s", solve2()));
    }

    List<Integer> readDrawnNumbers(BufferedReader reader) throws Throwable {
        return Arrays.asList(reader.readLine().split(","))
                .stream()
                .map(Integer::parseInt).collect(Collectors.toList());
    }

    List<Board> readBoards(BufferedReader reader) throws Throwable {
        List<Board> boards = new ArrayList<>();
        String line = reader.readLine();
        while (line != null) {
            if (!line.isEmpty()) {
                Board board = new Board();
                for (int ii = 0; ii < 5; ++ii) {
                    board.addRow(ii, Arrays.asList(line.split(" "))
                            .stream()
                            .map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .map(Integer::parseInt)
                            .mapToInt(Integer::intValue)
                            .toArray());
                    line = reader.readLine();
                }
                boards.add(board);
            } else {
                line = reader.readLine();
            }
        }
        return boards;
    }

    private long solve1() throws Throwable {
        long result = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("inputs/day4.txt"))) {

            List<Integer> drawnNumbers = readDrawnNumbers(reader);
            List<Board> boards = readBoards(reader);

            for (int num : drawnNumbers) {
                boolean won = false;
                for (Board board : boards) {
                    board.setDrawn(num);
                    if (board.hasWinning()) {
                        won = true;
                        result = board.calculate(num);
                    }
                }
                if (won) {
                    break;
                }
            }
        }
        return result;
    }

    private long solve2() throws Throwable {
        long result = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("inputs/day4.txt"))) {

            List<Integer> drawnNumbers = readDrawnNumbers(reader);
            List<Board> boards = readBoards(reader);

            for (int num : drawnNumbers) {
                Iterator<Board> it = boards.iterator();
                while (it.hasNext()) {
                    Board board = it.next();
                    board.setDrawn(num);
                    if (board.hasWinning() && boards.size() > 1) {
                        it.remove();
                    }
                }

                if (boards.size() == 1) {
                    Board last = boards.get(0);
                    last.setDrawn(num);
                    if (last.hasWinning()) {
                        result = last.calculate(num);
                        break;
                    }
                }
            }
        }
        return result;
    }
}