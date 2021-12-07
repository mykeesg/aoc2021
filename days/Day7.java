package days;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.TreeMap;

public class Day7 extends Base {
    @Override
    void runFirst() throws Throwable {
        System.out.println(String.format("Fuel spent with constant movement costs: %1$s", checkCrabMoves(true)));
    }

    @Override
    void runSecond() throws Throwable {
        System.out.println(String.format("Fuel spent with increasing movement costs: %1$s", checkCrabMoves(false)));
    }

    long checkCrabMoves(boolean constantFuel) throws Throwable {
        Map<Integer, Integer> crabs = readLines();
        int largestPos = crabs.keySet().stream().max(Integer::compareTo).get();

        long[] movementCostsTo = new long[largestPos + 1];

        for (int position = 0; position < movementCostsTo.length; ++position) {
            for (Map.Entry<Integer, Integer> entry : crabs.entrySet()) {
                long currentValue = movementCostsTo[position];
                int currentPosition = entry.getKey();
                long distance = Math.abs(currentPosition - position);
                int crabCount = entry.getValue();
                long cost;

                if (constantFuel) {
                    cost = distance * crabCount;
                } else {
                    // sum of ints in range 1...n == n*(n+1)/2
                    cost = crabCount * ((distance * (distance + 1)) / 2);
                }
                movementCostsTo[position] = currentValue + cost;
            }
        }

        int minPos = 0;
        for (int ii = 1; ii < movementCostsTo.length; ++ii) {
            if (movementCostsTo[ii] < movementCostsTo[minPos]) {
                minPos = ii;
            }
        }

        return movementCostsTo[minPos];
    }

    private Map<Integer, Integer> readLines() throws Throwable {
        Map<Integer, Integer> crabs = new TreeMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("inputs/day7.txt"))) {
            String line = reader.readLine();
            while (line != null) {
                String[] data = line.split(",");
                for (String str : data) {
                    int value = Integer.parseInt(str);
                    crabs.compute(value, (k, v) -> {
                        return v == null ? 1 : v + 1;
                    });
                }
                line = reader.readLine();
            }
        }
        return crabs;
    }
}
