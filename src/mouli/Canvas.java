package mouli;


import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Canvas {
    private final int width;
    private final int height;
    private final char[][] cnv;
    private final char marker = '.';
    private final char lineMarker = '$';

    public Canvas(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException();
        }

        this.width = width;
        this.height = height;
        cnv = new char[height][width];

        IntStream.range(0, height).forEach(i -> Arrays.fill(cnv[i], ' '));
    }

    public void putChar(int x, int y, char c) {
        cnv[x][y] = c;
    }


    private void line(int x1, int y1, int x2, int y2) {

        int dX = x2 - x1;
        int dY = y2 - y1;
        double distance = Math.hypot(dX, dY);

        double xShiftPerStep = dX / distance;
        double yShiftPerStep = dY / distance;

        IntStream.iterate(0, i -> i <= distance, i -> i + 1)
                .forEach(i -> {
                    int x = x1 + (int) (i * xShiftPerStep);
                    int y = y1 + (int) (i * yShiftPerStep);
                    cnv[y][x] = lineMarker;
                });
    }

    public void drawLine(int x1, int y1, int x2, int y2) {

        if ((x1 < 0 || x1 >= width) ||
            (x2 < 0 || x2 >= width) ||
            (y1 < 0 || y1 >= height) ||
            (y2 < 0 || y2 >= height)) {
            throw new IllegalArgumentException("can't draw");
        }

        line(x1, y1, x2, y2);
    }


    private void ellipse(int x, int y, int rx, int ry, int arcAngle, int rotatingAngle) {

        double sinb = Math.sin(Math.toRadians(rotatingAngle));
        double cosb = Math.cos(Math.toRadians(rotatingAngle));

        DoubleStream.iterate(0, i -> i < arcAngle, i -> i + 0.1).forEach(i -> {
            double sina = Math.sin(Math.toRadians(i));
            double cosa = Math.cos(Math.toRadians(i));
            int cx = (int) Math.round(x + rx * cosa * cosb - ry * sina * sinb);
            int cy = (int) Math.round(y + rx * cosa * sinb + ry * sina * cosb);
            safeDraw(cx, cy);
        });
    }

    private void safeDraw(int x, int y) {
        if ((x < 0 || x >= width * 2) ||
            (y < 0 || y >= height * 2))
            throw new IllegalArgumentException();

        cnv[x][y] = marker;
    }

    public void drawEllipse(int x, int y, int rx, int ry, int arcAngle, int rotationAngle) {
        ellipse(x, y, rx, ry, arcAngle, rotationAngle);
    }


    public void drawCanvas() {
        final var res = new StringBuilder();
        final char horizontalBorder = '-';
        final char verticalBorder = '|';

        final String border = String.valueOf(horizontalBorder).repeat(Math.max(0, width + 2));

        res.append(border).append('\n');

        for (int i = 0; i < height; i++) {
            res.append(verticalBorder);

            for (int j = 0; j < width; j++) {
                res.append(cnv[i][j]);
            }

            res.append(verticalBorder).append('\n');
        }
        res.append(border);

        System.out.println(res);
    }
}

