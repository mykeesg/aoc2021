package days;

import java.io.BufferedReader;
import java.io.FileReader;

import util.CaveSystem;

public class Day12 extends Base {
    private final CaveSystem caveSystem;

    public Day12() throws Throwable {
        caveSystem = readInput();
    }

    @Override
    void runFirst() throws Throwable {
        System.out.println(String.format("Paths from 'start' to 'end' with once per small cave: %1$s",
                caveSystem.countPaths(true)));
    }

    @Override
    void runSecond() throws Throwable {
        System.out.println(String.format("Paths from 'start' to 'end' with one double-visit: %1$s",
                caveSystem.countPaths(false)));
    }

    private CaveSystem readInput() throws Throwable {
        CaveSystem caveSystem = new CaveSystem();
        try (BufferedReader reader = new BufferedReader(new FileReader("inputs/day12.txt"))) {
            String line = reader.readLine();
            while (line != null) {
                String[] data = line.split("-");
                caveSystem.addPath(data[0], data[1]);
                line = reader.readLine();
            }
        }

        return caveSystem;
    }
}
