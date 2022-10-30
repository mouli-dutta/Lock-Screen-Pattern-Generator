package mouli;


import java.util.*;
import java.util.stream.IntStream;

public class DrawPatternOnCanvas implements ICanvasValueHolder {
    private DrawPatternOnCanvas(){}

    public static void draw(int[][] seqs) {
        int i = 1;
        for (int[] seq : seqs) {
            Canvas ctx = new Canvas(cnvWidth, cnvHeight);
            System.out.printf("Seq #%d -> #%s %n", i++, Arrays.toString(seq));
            drawNumpad(ctx);
            drawUnlockPattern(seq, ctx);
            ctx.drawCanvas();
            System.out.println();
        }
    }

    // draw the num pad
    private static void drawNumpad(Canvas ctx) {
        int ASCII_ONE = 49;

        for (int i = colStart; i <= colEnd; i += colStep) {
            for (int j = rowStart; j <= rowEnd; j += rowStep) {
                ctx.drawEllipse(i, j, xRadius, yRadius, arcAngle, 0);
                ctx.putChar(i, j, (char) ASCII_ONE++);
            }
        }
    }

    // coordinate of each number on the numpad
    private static Map<Integer, List<Integer>> numCoords() {
        final Map<Integer, List<Integer>> coordinates = new HashMap<>();
        int NUMS = 1;
        for (int i = colStart; i <= colEnd; i += colStep) {
            for (int j = rowStart; j <= rowEnd; j += rowStep) {
                coordinates.put(NUMS++, List.of(j, i));
            }
        }
        return coordinates;
    }

    // draw line from one number to another in the numpad
    public static void drawUnlockPattern(int[] arr, Canvas ctx) {

        IntStream.range(0, arr.length - 1).forEachOrdered(i -> {
            ArrayList<List<Integer>> lists = new ArrayList<>();
            lists.add(numCoords().get(arr[i]));
            lists.add(numCoords().get(arr[i + 1]));
            ctx.drawLine(lists.get(0).get(0), lists.get(0).get(1),
                    lists.get(1).get(0), lists.get(1).get(1)
            );
        });

    }
}

