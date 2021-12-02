import java.io.BufferedReader;
import java.io.FileReader;

public class Day2 extends Base {
    @Override
    public void runFirst() throws Throwable {
        System.out.println(String.format("Final position multiplied: %1$s", solve1()));
    }

    @Override
    public void runSecond() throws Throwable {
        System.out.println(String.format("Final position multiplied: %1$s", solve2()));
    }

    private long solve1() throws Throwable {
        try (BufferedReader reader = new BufferedReader(new FileReader("day2.txt"))) {
            long hx = 0, vy = 0;
            String line = reader.readLine();
            while (line != null) {
                String[] data = line.split(" ");
                long value = Long.parseLong(data[1]);
                switch (data[0]) {
                    case "forward": {
                        hx += value;
                        break;
                    }
                    case "up": {
                        vy -= value;
                        break;
                    }
                    case "down": {
                        vy += value;
                        break;
                    }
                }
                line = reader.readLine();
            }
            return hx * vy;
        }
    }

    private long solve2() throws Throwable {
        try (BufferedReader reader = new BufferedReader(new FileReader("day2.txt"))) {
            long hx = 0, vy = 0, aim = 0;
            String line = reader.readLine();
            while (line != null) {
                String[] data = line.split(" ");
                long value = Long.parseLong(data[1]);
                switch (data[0]) {
                    case "forward": {
                        hx += value;
                        vy += aim * value;
                        break;
                    }
                    case "up": {
                        aim -= value;
                        break;
                    }
                    case "down": {
                        aim += value;
                        break;
                    }
                }
                line = reader.readLine();
            }
            return hx * vy;
        }
    }
}