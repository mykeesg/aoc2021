package days;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Day14 extends Base {

    private final Map<String, String> productionRules;
    private final Map<Character, Long> characterCount;
    private final Map<String, Long> occurences;
    private String initial;

    public Day14() throws Throwable {
        productionRules = new HashMap<>();
        characterCount = new HashMap<>();
        occurences = new HashMap<>();
        readInput();
    }

    @Override
    void runFirst() throws Throwable {
        init();
        for (int i = 0; i < 10; ++i) {
            iterate();
        }
        long max = characterCount.values().stream().mapToLong(e -> e).max().getAsLong();
        long min = characterCount.values().stream().mapToLong(e -> e).min().getAsLong();
        System.out.println("Difference of max and min after 10 iterations: " + (max - min));
    }

    @Override
    void runSecond() throws Throwable {
        init();
        for (int i = 0; i < 40; ++i) {
            iterate();
        }
        long max = characterCount.values().stream().mapToLong(e -> e).max().getAsLong();
        long min = characterCount.values().stream().mapToLong(e -> e).min().getAsLong();
        System.out.println("Difference of max and min after 40 iterations: " + (max - min));
    }

    void iterate() {
        Map<String, Long> newOccurences = new HashMap<>();
        for (var pair : occurences.entrySet()) {
            String rule = pair.getKey();
            long occ = pair.getValue();

            if (productionRules.containsKey(rule)) {
                String newValue = productionRules.get(rule);
                String firstValue = "" + rule.charAt(0) + newValue;
                String secondValue = newValue + rule.charAt(1);
                increaseCountBy(newOccurences, firstValue, occ);
                increaseCountBy(newOccurences, secondValue, occ);
                increaseCountBy(characterCount, newValue.charAt(0), occ);
            } else {
                increaseCountBy(newOccurences, rule, occ);
            }
        }
        occurences.clear();
        occurences.putAll(newOccurences);
    }

    void init() {
        characterCount.clear();
        occurences.clear();
        for (int ii = 0; ii < initial.length() - 1; ++ii) {
            String currentSequence = initial.substring(ii, ii + 2);
            increaseCountBy(occurences, currentSequence, 1);
        }
        initial.chars().forEach(ch -> increaseCountBy(characterCount, (char) ch, 1));
    }

    private <T> void increaseCountBy(Map<T, Long> map, T key, long value) {
        map.put(key, map.getOrDefault(key, 0L) + value);
    }

    private void readInput() throws Throwable {
        try (BufferedReader reader = new BufferedReader(new FileReader("inputs/day14.txt"))) {
            initial = reader.readLine();
            reader.readLine(); // empty
            String line = reader.readLine();
            while (line != null) {
                String[] data = line.split(" -> ");
                productionRules.put(data[0], data[1]);
                line = reader.readLine();
            }
        }
    }

    void print() {
        System.out.println("Occurences: " + occurences);
        System.out.println("Counts: " + characterCount);
    }
}
