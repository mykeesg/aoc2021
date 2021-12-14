import days.*;

public class Main {
    public static void main(String[] args) throws Throwable {
        if (args.length == 0) {
            System.out.println("Usage: Main <day_number>");
            return;
        }
        // Yeah, I know, giantic switch-case.. It was faster/easier this way.
        switch (Integer.parseInt(args[0])) {
            case 1 -> new Day1().run();
            case 2 -> new Day2().run();
            case 3 -> new Day3().run();
            case 4 -> new Day4().run();
            case 5 -> new Day5().run();
            case 6 -> new Day6().run();
            case 7 -> new Day7().run();
            case 8 -> new Day8().run();
            case 9 -> new Day9().run();
            case 10 -> new Day10().run();
            case 11 -> new Day11().run();
            case 12 -> new Day12().run();
            case 13 -> new Day13().run();
            case 14 -> new Day14().run();
            default -> System.out.println("Solution not yet defined.");
        }
    }
}
