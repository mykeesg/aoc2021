public class Main {
    public static void main(String[] args) throws Throwable {
        if (args.length == 0) {
            System.out.println("Usage: Main <day_number>");
            return;
        }
        switch (Integer.parseInt(args[0])) {
            case 1:
                new Day1().run();
                break;
            default:
                System.out.println("Solution not yet defined.");
                break;
        }
    }
}
