import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Day3 extends Base {
    @Override
    public void runFirst() throws Throwable {
        System.out.println(String.format("Power consumption: %1$s", solve1()));
    }

    @Override
    public void runSecond() throws Throwable {
        System.out.println(String.format("Life support rate: %1$s", solve2()));
    }

    private long solve1() throws Throwable {
        long result = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("day3.txt"))) {
            String line = reader.readLine();
            if (line != null) {
                // number of bits is the length of the string.
                int[] bits = new int[line.length()];
                while (line != null) {
                    for (int ii = 0; ii < line.length(); ++ii) {
                        if (line.charAt(ii) == '1') {
                            bits[ii]++;
                        } else {
                            bits[ii]--;
                        }
                    }
                    line = reader.readLine();
                }

                StringBuffer bufferGamma = new StringBuffer(bits.length);
                StringBuffer bufferEpsilon = new StringBuffer(bits.length);
                for (int bit : bits) {
                    if (bit > 0) {
                        bufferGamma.append("1");
                        bufferEpsilon.append("0");
                    } else if (bit < 0) {
                        bufferGamma.append("0");
                        bufferEpsilon.append("1");
                    } else {
                        throw new Error("What now?");
                    }
                }
                long gamma = Long.parseLong(bufferGamma.toString(), 2);
                long epsilon = Long.parseLong(bufferEpsilon.toString(), 2);

                result = gamma * epsilon;
            }
        }
        return result;
    }

    private long solve2() throws Throwable {
        long result = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("day3.txt"))) {
            List<String> data = new ArrayList<>();
            String line = reader.readLine();
            while (line != null) {
                data.add(line);
                line = reader.readLine();
            }

            if (!data.isEmpty()) {
                long oxygen = calculate(data, true);
                long co2 = calculate(data, false);
                result = oxygen * co2;
            }
        }
        return result;
    }

    private final BiFunction<List<String>, Integer, Character> mostSignificantAt = (list, idx) -> {
        int sum = 0;
        for (String current : list) {
            if (current.charAt(idx) == '1') {
                sum += 1;
            } else {
                sum -= 1;
            }
        }
        return sum >= 0 ? '1' : '0';
    };

    private long calculate(List<String> data, boolean eq) {
        int idx = 0;
        while (data.size() != 1) {
            char bit = mostSignificantAt.apply(data, idx);
            final int ii = idx;
            data = data
                    .stream()
                    .filter(str -> (eq ? str.charAt(ii) == bit : str.charAt(ii) != bit))
                    .collect(Collectors.toList());
            idx += 1;
        }
        return Long.parseLong(data.get(0), 2);
    }
}