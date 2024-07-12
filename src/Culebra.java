import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa la culebra en el juego.
 */
public class Culebra implements Serializable {
    private static final long serialVersionUID = 1L;  // Asegura la compatibilidad de la serialización

    public static final int RIGHT = 0;
    public static final int LEFT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;
    private static final int SQUARE_SIZE = 800 / 20;

    private List<Point> body = new ArrayList<>();
    private Point head;
    private int currentDirection;

    /**
     * Constructor de la culebra. Inicializa el cuerpo de la culebra con 3 segmentos.
     */
    public Culebra() {
        for (int i = 0; i < 3; i++) {
            body.add(new Point(5, 10));
        }
        head = body.get(0);
    }

    public List<Point> getBody() {
        return body;
    }

    public Point getHead() {
        return head;
    }

    public int getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(int direction) {
        this.currentDirection = direction;
    }

    /**
     * Mueve la culebra en la dirección actual.
     */
    public void move() {
        for (int i = body.size() - 1; i >= 1; i--) {
            body.get(i).x = body.get(i - 1).x;
            body.get(i).y = body.get(i - 1).y;
        }

        switch (currentDirection) {
            case RIGHT:
                moveRight();
                break;
            case LEFT:
                moveLeft();
                break;
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
        }
    }

    /**
     * Hace crecer la culebra añadiendo un nuevo segmento al final.
     */
    public void grow() {
        body.add(new Point(-1, -1));
    }

    private void moveRight() {
        head.x++;
    }

    private void moveLeft() {
        head.x--;
    }

    private void moveUp() {
        head.y--;
    }

    private void moveDown() {
        head.y++;
    }

    /**
     * Verifica si la culebra ha colisionado con los bordes del área de juego o consigo misma.
     * @return true si hay una colisión, false en caso contrario.
     */
    public boolean checkCollision() {
        if (head.x < 0 || head.y < 0 || head.x * SQUARE_SIZE >= 800 || head.y * SQUARE_SIZE >= 800) {
            return true;
        }

        for (int i = 1; i < body.size(); i++) {
            if (head.x == body.get(i).getX() && head.getY() == body.get(i).getY()) {
                return true;
            }
        }

        return false;
    }
}
