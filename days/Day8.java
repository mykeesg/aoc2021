package days;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import util.Pair;

public class Day8 extends Base {

    enum Digit {
        ZERO('a', 'b', 'c', 'e', 'f', 'g'),
        ONE('c', 'f'),
        TWO('a', 'c', 'd', 'e', 'g'),
        THREE('a', 'c', 'd', 'f', 'g'),
        FOUR('b', 'c', 'd', 'f'),
        FIVE('a', 'b', 'd', 'f', 'g'),
        SIX('a', 'b', 'd', 'e', 'f', 'g'),
        SEVEN('a', 'c', 'f'),
        EIGHT('a', 'b', 'c', 'd', 'e', 'f', 'g'),
        NINE('a', 'b', 'c', 'd', 'f', 'g');

        private final char[] sections;

        Digit(char... chars) {
            sections = chars;
        }

        int segments() {
            return sections.length;
        }

        boolean has(char c) {
            for (int ii = 0; ii < sections.length; ++ii) {
                if (sections[ii] == c) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    void runFirst() throws Throwable {
        var input = readInput();
        long count = 0;
        for (var pair : input) {
            var numbers = pair.second();
            for (String str : numbers) {
                switch (str.length()) {
                    case 2:
                    case 3:
                    case 4:
                    case 7:
                        count += 1;
                }
            }
        }
        System.out.println("Total count of digits for 1,4,7,8: " + count);
    }

    @Override
    void runSecond() throws Throwable {
        /*
         * Every segment can be calculated as a if we were thinking about
         * the numbers as sets, and their differences.
         * As the order can be important to avoid amibguity, we're giving it in the
         * calculation order.
         * 
         * The length (active segments) can also indicate which number is it / which
         * segments are in use
         * 
         * 2 -> #1
         * 3 -> #7
         * 4 -> #4
         * 7 -> #8
         * 5 -> #2, #3, #5
         * 6 -> #0, #6, #9
         * 
         * Also, the number of total segments used across the 10 digits have some
         * information as well.
         * 'a' -> 8
         * 'b' -> 6
         * 'c' -> 8
         * 'd' -> 7
         * 'e' -> 4
         * 'f' -> 9
         * 'g' -> 7
         * 
         * segments with unique count:
         * 'e' -> 4
         * 'b' -> 6
         * 'f' -> 9
         * 
         * With this information we can deduce some stuff.
         * 
         * 'a' -> 7 - 1
         * 
         * 'bd' -> 7 - 4
         * 'eg' -> 8 - 7 - 4
         * 
         * 'c' -> 8 - 6
         * 'e' -> 8 - 9
         * '' ->
         * '' ->
         * '' ->
         */

        var input = readInput();
        long count = 0;
        Map<Integer, Set<Character>> digitCircuits = new HashMap<>();
        for (int ii = 0; ii <= 9; ++ii) {
            digitCircuits.put(ii, new TreeSet<>());
        }
        for (var pair : input) {
            for (int ii = 0; ii <= 9; ++ii) {
                digitCircuits.get(ii).clear();
            }
            // The circuit 'a' is present X times.
            Map<Character, Integer> counts = new HashMap<>();
            Map<Character, Character> actualMappings = new HashMap<>();
            // The digit 1 is using the circuits c,f (actualMappings(c), actualMappings(f)),
            // etc.

            Consumer<Character> increase = c -> {
                counts.put(c, 1 + counts.getOrDefault(c, 0));
            };

            var digits = pair.first();
            for (String str : digits) {
                for (char c : str.toCharArray()) {
                    increase.accept(c);
                }
                switch (str.length()) {
                    case 2 -> digitCircuits.get(1)
                            .addAll(str.chars().mapToObj(ch -> (char) ch).collect(Collectors.toSet()));
                    case 3 -> digitCircuits.get(7)
                            .addAll(str.chars().mapToObj(ch -> (char) ch).collect(Collectors.toSet()));
                    case 4 -> digitCircuits.get(4)
                            .addAll(str.chars().mapToObj(ch -> (char) ch).collect(Collectors.toSet()));
                    case 7 -> digitCircuits.get(8)
                            .addAll(str.chars().mapToObj(ch -> (char) ch).collect(Collectors.toSet()));
                }
            }
            for (var entry : counts.entrySet()) {
                int occurence = entry.getValue();
                switch (occurence) {
                    case 4 -> actualMappings.put(entry.getKey(), 'e');
                    case 6 -> actualMappings.put(entry.getKey(), 'b');
                    case 9 -> actualMappings.put(entry.getKey(), 'f');
                }
            }
            System.err.println("Counts: " + counts);
            System.err.println("Mappings: " + actualMappings);
            System.err.println("DigitCircuits: " + digitCircuits);
        }

    }

    List<Pair<List<String>, List<String>>> readInput() throws Throwable {
        List<Pair<List<String>, List<String>>> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("inputs/day8.txt"))) {
            String line = reader.readLine();
            while (line != null) {
                Pair<List<String>, List<String>> current = Pair.of(new ArrayList<>(10), new ArrayList<>(4));
                String[] data = line.split(" ");

                for (int ii = 0; ii < 10; ++ii) {
                    current.first().add(data[ii]);
                }
                for (int ii = 11; ii < 15; ++ii) {
                    current.second().add(data[ii]);
                }
                list.add(current);
                line = reader.readLine();
            }
        }
        return list;
    }
}