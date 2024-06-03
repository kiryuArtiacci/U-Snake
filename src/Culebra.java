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
    private int direction;

    public Culebra(int startX, int startY, int length) {
        body = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            body.add(new Point(startX, startY));
        }
        head = body.get(0);
        direction = RIGHT;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void move() {
        for (int i = body.size() - 1; i >= 1; i--) {
            body.get(i).x = body.get(i - 1).x;
            body.get(i).y = body.get(i - 1).y;
        }

        switch (direction) {
            case RIGHT -> head.x++;
            case LEFT -> head.x--;
            case UP -> head.y--;
            case DOWN -> head.y++;
        }
    }

    public void draw(GraphicsContext gc, int squareSize) {
        gc.setFill(Color.web("6aa84f"));
        gc.fillRoundRect(head.getX() * squareSize, head.getY() * squareSize, squareSize - 1, squareSize - 1, 35, 35);

        for (int i = 1; i < body.size(); i++) {
            gc.fillRoundRect(body.get(i).getX() * squareSize, body.get(i).getY() * squareSize, squareSize - 1,
                    squareSize - 1, 20, 20);
        }
    }

    public boolean checkCollision(int width, int height) {
        if (head.x < 0 || head.y < 0 || head.x * 40 >= width || head.y * 40 >= height) {
            return true;
        }
        for (int i = 1; i < body.size(); i++) {
            if (head.x == body.get(i).getX() && head.getY() == body.get(i).getY()) {
                return true;
            }
        }
        return false;
    }

    public boolean eatFood(Comida comida) {
        return head.getX() == comida.getX() && head.getY() == comida.getY();
    }

    public void grow() {
        body.add(new Point(-1, -1));
    }

    public List<Point> getBody() {
        return body;
    }
}
