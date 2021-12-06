import java.io.BufferedReader;
import java.io.FileReader;

public class Day6 extends Base {
    @Override
    void runFirst() throws Throwable {
        System.out.println(String.format("Number lanternfish after 80 days: %1$s", checkDays(80)));
    }

    @Override
    void runSecond() throws Throwable {
        System.out.println(String.format("Number lanternfish after 256 days: %1$s", checkDays(256)));
    }

    static final int resetDay = 6;

    long checkDays(int days) throws Throwable {
        long[] fishes = readLines();
        for (int ii = 0; ii < days; ++ii) {
            long newborn = fishes[0];
            for (int jj = 0; jj < fishes.length - 1; ++jj) {
                fishes[jj] = fishes[jj + 1];
            }
            fishes[resetDay] += newborn;
            fishes[fishes.length - 1] = newborn;
        }

        long result = 0;
        for (int jj = 0; jj < fishes.length; ++jj) {
            result += fishes[jj];
        }
        return result;
    }

    private long[] readLines() throws Throwable {
        long[] fish = new long[9]; // 0..8
        try (BufferedReader reader = new BufferedReader(new FileReader("day6.txt"))) {
            String line = reader.readLine();
            while (line != null) {
                String[] data = line.split(",");
                for (String value : data) {
                    fish[Integer.parseInt(value)]++;
                }
                line = reader.readLine();
            }
        }
        return fish;
    }
}
