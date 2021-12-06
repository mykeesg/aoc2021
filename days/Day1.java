package days;

import java.io.BufferedReader;
import java.io.FileReader;

public class Day1 extends Base {
    @Override
    public void runFirst() throws Throwable {
        System.out.println(String.format("Total increments: %1$s", solve(1)));
    }

    @Override
    public void runSecond() throws Throwable {
        System.out.println(String.format("Total increments: %1$s", solve(3)));
    }

    private int solve(int windowSize) throws Throwable {
        try (BufferedReader reader = new BufferedReader(new FileReader("inputs/day1.txt"))) {
            /*
             * A different solution could be a list/queue with the specified length, where
             * the first element is always popped, and the new one is instered to the end.
             * With that, indexing would not be a problem, but I wanted to avoid
             * unneccessary (un)boxings of int/Integers, I went with the array one.
             */
            int[] window = new int[windowSize];
            int count = 0;
            String line = reader.readLine();

            while (line != null && count < windowSize) {
                window[count] = Integer.parseInt(line);
                line = reader.readLine();
                ++count;
            }

            int currentSum = sum(window);

            count = 0; // was used for indexing
            int idx = 0;
            while (line != null) {
                int prevSum = currentSum;
                window[idx] = Integer.parseInt(line);
                currentSum = sum(window);
                if (prevSum < currentSum) {
                    ++count;
                }

                line = reader.readLine();
                idx = (idx + 1) % windowSize;
            }
            return count;
        }
    }

    private int sum(int[] array) {
        int total = 0;
        for (int i : array) {
            total += i;
        }
        return total;
    }
}