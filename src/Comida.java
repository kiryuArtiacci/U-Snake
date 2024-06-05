import javafx.scene.image.Image;
import java.awt.Point;
import java.util.List;

/**
 * Clase que representa la comida en el juego.
 */
public class Comida {
    private static final int ROWS = 20;
    private static final int COLUMNS = ROWS;
    private static final String[] FOODS_IMAGE = new String[]{
        "/img/ic_orange.png", "/img/ic_apple.png", "/img/ic_cherry.png",
        "/img/ic_berry.png", "/img/ic_coconut_.png", "/img/ic_peach.png",
        "/img/ic_watermelon.png", "/img/ic_orange.png", "/img/ic_pomegranate.png"};

    private Image image;
    private int x;
    private int y;

    /**
     * Constructor de la comida. Genera una nueva comida en una posición aleatoria.
     * @param snakeBody El cuerpo de la culebra para evitar colocar la comida en la misma posición.
     */
    public Comida(List<Point> snakeBody) {
        generateFood(snakeBody);
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Genera una nueva comida en una posición aleatoria que no esté ocupada por la culebra.
     * @param snakeBody El cuerpo de la culebra para evitar colocar la comida en la misma posición.
     */
    public void generateFood(List<Point> snakeBody) {
        start:
        while (true) {
            x = (int) (Math.random() * ROWS);
            y = (int) (Math.random() * COLUMNS);

            for (Point snake : snakeBody) {
                if (snake.getX() == x && snake.getY() == y) {
                    continue start;
                }
            }
            image = new Image(FOODS_IMAGE[(int) (Math.random() * FOODS_IMAGE.length)]);
            break;
        }
    }
}
