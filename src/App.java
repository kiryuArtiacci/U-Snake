import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = WIDTH;
    private static final int ROWS = 20;
    private static final int COLUMNS = ROWS;
    private static final int SQUARE_SIZE = WIDTH / ROWS;

    private GraphicsContext gc;
    private Culebra culebra;
    private Comida comida;
    private boolean gameOver;
    private int score = 0;
    private Timeline timeline;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Culebra");
        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        gc = canvas.getGraphicsContext2D();

        culebra = new Culebra(ROWS, COLUMNS, SQUARE_SIZE);
        comida = new Comida(ROWS, COLUMNS, SQUARE_SIZE);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode code = event.getCode();
                if (code == KeyCode.RIGHT || code == KeyCode.D) {
                    if (culebra.getCurrentDirection() != Culebra.LEFT) {
                        culebra.setCurrentDirection(Culebra.RIGHT);
                    }
                } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                    if (culebra.getCurrentDirection() != Culebra.RIGHT) {
                        culebra.setCurrentDirection(Culebra.LEFT);
                    }
                } else if (code == KeyCode.UP || code == KeyCode.W) {
                    if (culebra.getCurrentDirection() != Culebra.DOWN) {
                        culebra.setCurrentDirection(Culebra.UP);
                    }
                } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                    if (culebra.getCurrentDirection() != Culebra.UP) {
                        culebra.setCurrentDirection(Culebra.DOWN);
                    }
                }
            }
        });

        timeline = new Timeline(new KeyFrame(Duration.millis(130), e -> run(gc)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void run(GraphicsContext gc) {
        if (gameOver) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("Digital-7", 70));
            gc.fillText("Fin del juego", WIDTH / 3.5, HEIGHT / 2);
            return;
        }

        Campo.drawBackground(gc, ROWS, COLUMNS, SQUARE_SIZE);
        comida.draw(gc);
        culebra.draw(gc);
        drawScore();

        culebra.move();

        if (culebra.checkCollision(WIDTH, HEIGHT) || culebra.checkSelfCollision()) {
            gameOver = true;
        }

        if (culebra.eat(comida)) {
            comida.generate(culebra);
            score += 5;
        }
    }

    private void drawScore() {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Digital-7", 35));
        gc.fillText("Score: " + score, 10, 35);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
