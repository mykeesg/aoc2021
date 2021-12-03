import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day3 extends Base {
    @Override
    public void runFirst() throws Throwable {
        System.out.println(String.format("Final position multiplied: %1$s", solve1()));
    }

    @Override
    public void runSecond() throws Throwable {
        System.out.println(String.format("Final position multiplied: %1$s", solve2()));
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

            if (data.isEmpty()) {
                return 0;
            }

            BiFunction<List<String>, Integer, Character> leftmostSignifical = (list, idx) -> {
                if (list.isEmpty()) {
                    return 0;
                }
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

            List<String> oxygen = new ArrayList<>(data);
            int idx = 0;
            while (oxygen.size() != 1) {
                char bit = leftmostSignifical.apply(oxygen, idx);
                final int ii = idx;
                oxygen = oxygen.stream().filter(str -> str.charAt(ii) == bit).collect(Collectors.toList());
                idx += 1;
            }

            List<String> co2 = new ArrayList<>(data);
            idx = 0;
            while (co2.size() != 1) {
                char bit = leftmostSignifical.apply(co2, idx);
                final int ii = idx;
                co2 = co2.stream().filter(str -> str.charAt(ii) != bit).collect(Collectors.toList());
                idx += 1;
            }

            result = Long.parseLong(oxygen.get(0), 2) * Long.parseLong(co2.get(0), 2);
        }
        return result;
    }
}