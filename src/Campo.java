import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Campo {

    public static void drawBackground(GraphicsContext gc, int rows, int columns, int squareSize) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((i + j) % 2 == 0) {
                    gc.setFill(Color.web("0193BD"));
                } else {
                    gc.setFill(Color.web("005f80"));
                }
                gc.fillRect(i * squareSize, j * squareSize, squareSize, squareSize);
            }
        }
    }
}
