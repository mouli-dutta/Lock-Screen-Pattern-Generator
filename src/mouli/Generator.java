package mouli;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Generator {
    private Generator() {}

    public static int[] getRandomSeq(int patternLength) {
        final String[] KEY_PAD = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String start = KEY_PAD[new Random().nextInt(KEY_PAD.length)];

        List<String> combs = getAllCombinations(start, patternLength)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        return combs.size() > 0 ? combs.get(0)
                .chars()
                .mapToObj(c -> String.valueOf(c - '0'))
                .mapToInt(Integer::parseInt)
                .toArray() : new int[]{7, 5, 3, 6, 4, 1, 9};
    }

    private static Stream<String> getAllCombinations(String start, int patternLen) {
        if (patternLen == 0) return Stream.of(start);

        return nextPossibilities(start)
                .stream()
                .filter(c -> !start.contains(c))
                .flatMap(c -> getAllCombinations(start + c, patternLen - 1));
    }

    private static List<String> nextPossibilities(String s) {
        char end = s.charAt(s.length() - 1);
        return switch (end) {
            case '1' -> Arrays.asList(
                    s.contains("2") ? "3" : "2",
                    s.contains("4") ? "7" : "4",
                    s.contains("5") ? "9" : "5",
                    "6", "8"
            );
            case '2' -> Arrays.asList("1", "3", "4", "6", "7", "9", s.contains("5") ? "8" : "5");

            case '3' -> Arrays.asList(
                    s.contains("2") ? "1" : "2",
                    s.contains("6") ? "9" : "6",
                    s.contains("5") ? "7" : "5",
                    "4", "8"
            );
            case '4' -> Arrays.asList("1", "2", "3", "7", "8", "9", s.contains("5") ? "6" : "5");

            case '5' -> Arrays.asList("1", "2", "3", "4", "6", "7", "8", "9");

            case '6' -> Arrays.asList("1", "2", "3", "7", "8", "9", s.contains("5") ? "4" : "5");

            case '7' -> Arrays.asList(
                    s.contains("4") ? "1" : "4",
                    s.contains("8") ? "9" : "8",
                    s.contains("5") ? "3" : "5",
                    "2", "6"
            );

            case '8' -> Arrays.asList("1", "3", "4", "6", "7", "9", s.contains("5") ? "2" : "5");

            case '9' -> Arrays.asList(
                    s.contains("6") ? "3" : "6",
                    s.contains("8") ? "7" : "8",
                    s.contains("5") ? "1" : "5",
                    "2", "4"
            );
            default -> null;
        };
    }
}
