import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.Point;
import java.util.List;

public class Comida {
    private int x;
    private int y;
    private Image image;
    private int rows;
    private int columns;
    private int squareSize;
    private static final String[] FOODS_IMAGE = new String[]{
            "/img/ic_orange.png", "/img/ic_apple.png", "/img/ic_cherry.png",
            "/img/ic_berry.png", "/img/ic_coconut_.png", "/img/ic_peach.png",
            "/img/ic_watermelon.png", "/img/ic_orange.png", "/img/ic_pomegranate.png"};

    public Comida(int rows, int columns, int squareSize) {
        this.rows = rows;
        this.columns = columns;
        this.squareSize = squareSize;
        generate(null);
    }

    public void generate(Culebra culebra) {
        start:
        while (true) {
            x = (int) (Math.random() * rows);
            y = (int) (Math.random() * columns);

            if (culebra != null) {
                List<Point> body = culebra.getBody();
                for (Point point : body) {
                    if (point.getX() == x && point.getY() == y) {
                        continue start;
                    }
                }
            }
            image = new Image(FOODS_IMAGE[(int) (Math.random() * FOODS_IMAGE.length)]);
            break;
        }
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(image, x * squareSize, y * squareSize, squareSize, squareSize);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
