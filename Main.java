public class Main {
    public static void main(String[] args) throws Throwable {
        if (args.length == 0) {
            System.out.println("Usage: Main <day_number>");
            return;
        }
        // Yeah, I know, giantic switch-case.. It was faster/easier this way.
        switch (Integer.parseInt(args[0])) {
            case 1:
                new Day1().run();
                break;
            case 2:
                new Day2().run();
                break;
            case 3:
                new Day3().run();
                break;
            case 4:
                new Day4().run();
                break;
            case 5:
                new Day5().run();
                break;
            case 6:
                new Day6().run();
                break;
            default:
                System.out.println("Solution not yet defined.");
                break;
        }
    }
}
