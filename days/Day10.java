package days;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day10 extends Base {

    final Set<Character> opening = Set.of('(', '[', '{', '<');

    final Map<Character, Integer> corruptedScoreValues = new HashMap<>();
    final Map<Character, Integer> incompleteScoreValues = new HashMap<>();
    final Map<Character, Character> pairs = new HashMap<>();

    public Day10() {
        corruptedScoreValues.put(')', 3);
        corruptedScoreValues.put(']', 57);
        corruptedScoreValues.put('}', 1197);
        corruptedScoreValues.put('>', 25137);

        incompleteScoreValues.put(')', 1);
        incompleteScoreValues.put(']', 2);
        incompleteScoreValues.put('}', 3);
        incompleteScoreValues.put('>', 4);

        pairs.put('(', ')');
        pairs.put('[', ']');
        pairs.put('{', '}');
        pairs.put('<', '>');
    }

    @Override
    void runFirst() throws Throwable {
        System.out.println("Corrupted score is: " + run(true));
    }

    @Override
    void runSecond() throws Throwable {
        System.out.println("Incomplete score is: " + run(false));
    }

    long run(boolean calcCorrupted) throws Throwable {
        long score = 0;

        List<Long> incompleteScores = new ArrayList<>();

        List<String> tokens = readInput();
        for (String line : tokens) {
            LinkedList<Character> opened = new LinkedList<>();

            boolean corrupted = false;
            for (int ii = 0; ii < line.length(); ++ii) {
                char current = line.charAt(ii);
                if (opening.contains(current)) {
                    opened.add(current);
                } else {
                    if (opened.isEmpty()) {
                        score += corruptedScoreValues.get(current);
                        corrupted = true;
                        break;
                    }
                    char lastOpened = opened.get(opened.size() - 1);
                    if (pairs.get(lastOpened) != current) {
                        score += corruptedScoreValues.get(current);
                        corrupted = true;
                        break;
                    } else {
                        opened.remove(opened.size() - 1);
                    }
                }
            }

            if (!corrupted) {
                long localScore = 0;
                while (!opened.isEmpty()) {
                    char last = opened.removeLast();
                    localScore *= 5;
                    localScore += incompleteScoreValues.get(pairs.get(last));
                }
                incompleteScores.add(localScore);
            }
        }
        if (calcCorrupted) {
            return score;
        } else {
            Collections.sort(incompleteScores);
            return incompleteScores.get(incompleteScores.size() / 2);
        }
    }

    List<String> readInput() throws Throwable {
        List<String> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("inputs/day10.txt"))) {
            String line = reader.readLine();
            while (line != null) {
                list.add(line);
                line = reader.readLine();
            }
        }
        return list;
    }
}
