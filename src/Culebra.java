import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Culebra {
    public static final int RIGHT = 0;
    public static final int LEFT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;

    private List<Point> body;
    private Point head;
    private int currentDirection;
    private int rows;
    private int columns;
    private int squareSize;

    public Culebra(int rows, int columns, int squareSize) {
        this.rows = rows;
        this.columns = columns;
        this.squareSize = squareSize;
        body = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            body.add(new Point(5, rows / 2));
        }
        head = body.get(0);
        currentDirection = RIGHT;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.web("6aa84f"));
        gc.fillRoundRect(head.getX() * squareSize, head.getY() * squareSize, squareSize - 1, squareSize - 1, 35, 35);

        for (int i = 1; i < body.size(); i++) {
            gc.fillRoundRect(body.get(i).getX() * squareSize, body.get(i).getY() * squareSize, squareSize - 1, squareSize - 1, 20, 20);
        }
    }

    public void move() {
        for (int i = body.size() - 1; i >= 1; i--) {
            body.get(i).x = body.get(i - 1).x;
            body.get(i).y = body.get(i - 1).y;
        }

        switch (currentDirection) {
            case RIGHT:
                head.x++;
                break;
            case LEFT:
                head.x--;
                break;
            case UP:
                head.y--;
                break;
            case DOWN:
                head.y++;
                break;
        }
    }

    public boolean checkCollision(int width, int height) {
        return head.x < 0 || head.y < 0 || head.x * squareSize >= width || head.y * squareSize >= height;
    }

    public boolean checkSelfCollision() {
        for (int i = 1; i < body.size(); i++) {
            if (head.x == body.get(i).getX() && head.getY() == body.get(i).getY()) {
                return true;
            }
        }
        return false;
    }

    public boolean eat(Comida comida) {
        if (head.getX() == comida.getX() && head.getY() == comida.getY()) {
            body.add(new Point(-1, -1));
            return true;
        }
        return false;
    }

    public int getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(int currentDirection) {
        this.currentDirection = currentDirection;
    }

    public List<Point> getBody() {
        return body;
    }
}
