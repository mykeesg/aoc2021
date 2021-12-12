package util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CaveSystem {
    public static class Path {
        public final String from, to;

        public Path(String from, String to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            Path that = (Path) obj;
            return Objects.equals(this.from, that.from) && Objects.equals(this.to, that.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }

        @Override
        public String toString() {
            return String.format("(%1$s -> %2$s)", from, to);
        }
    }

    private final List<Path> paths = new LinkedList<>();

    private List<String> connections(String from) {
        return paths
                .stream()
                .filter(p -> p.from.equals(from))
                .map(p -> p.to)
                .collect(Collectors.toList());
    }

    public void addPath(String from, String to) {
        paths.add(new Path(from, to));
        paths.add(new Path(to, from));
    }

    private boolean isSmallCave(String caveName) {
        return caveName.equals(caveName.toLowerCase());
    }

    private long count = 0;

    public long countPaths(boolean smallOnlyOnce) {
        count = 0;
        DFS("start", "end", new ArrayList<>(), smallOnlyOnce);
        return count;
    }

    private void DFS(String from, String to, List<String> alreadyVisited, boolean smallOnlyOnce) {
        alreadyVisited.add(from);
        if (from.equals(to)) {
            ++count;
        } else {
            for (String cave : connections(from)) {
                if (allowedToVisit(cave, alreadyVisited, smallOnlyOnce)) {
                    DFS(cave, to, new ArrayList<>(alreadyVisited), smallOnlyOnce);
                }
            }
        }
    }

    private boolean allowedToVisit(String cave, List<String> alreadyVisited, boolean smallOnlyOnce) {
        // ! (alreadyVisited.contains(cave) && isSmallCave(cave))
        if (smallOnlyOnce) {
            return !(alreadyVisited.contains(cave) && isSmallCave(cave));
        }
        if (cave.equals("start")) {
            return false;
        }
        if (!alreadyVisited.contains(cave) || !isSmallCave(cave)) {
            return true;
        }

        long smallVisits = alreadyVisited.stream().filter(this::isSmallCave).count();
        long uniqueSmallVisits = alreadyVisited.stream().filter(this::isSmallCave).distinct().count();
        return smallVisits == uniqueSmallVisits;
    }
}
