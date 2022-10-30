package mouli;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class LockScreenPattern {

    public static void main(String[] args) {
        // write your code here
        printTitle("Lockscreen pattern");

        printTitle("Random sequences");
        DrawPatternOnCanvas.draw(generateRandomSeq());

        printTitle("Default sequences");
        DrawPatternOnCanvas.draw(getDefaultExamples());
    }

    private static int[][] generateRandomSeq() {
        int[][] seqs = new int[7][];
        for (int i = 0; i < seqs.length; i++) {
            int randLength = 4 + new Random().nextInt(6);
            seqs[i] = Generator.getRandomSeq(randLength);
        }
        return seqs;
    }

    private static int[][] getDefaultExamples() {
        return new int[][]{
                {7, 5, 3, 6, 4, 1, 9},
                {2, 5, 7, 3, 6, 8, 4, 1, 9},
                {5, 4, 6, 2, 8, 9, 7, 1},
                {8, 7, 2, 6, 3, 9, 5, 4},
                {4, 2, 1, 5, 6, 9, 8},
                {6, 9, 2, 7, 5, 1, 3, 8},
        };
    }

    private static void printTitle(String plainText) {
        String titleCase = Arrays.stream(plainText.split(" "))
                .map(t -> Character.toUpperCase(t.charAt(0)) + t.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));

        int len = titleCase.length();

        System.out.printf("%s\n#  %s  #\n%s\n\n%n", "-".repeat(len + 6), titleCase, "-".repeat(len + 6));
    }
}
