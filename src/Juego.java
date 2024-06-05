import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.Point;

/**
 * Clase principal que maneja la lógica del juego.
 */
public class Juego {
    private static final int WIDTH = 800;
    private static final int HEIGHT = WIDTH;
    private static final int ROWS = 20;
    private static final int COLUMNS = ROWS;
    private static final int SQUARE_SIZE = WIDTH / ROWS;

    private GraphicsContext gc;
    private Culebra culebra;
    private Comida comida;
    private boolean gameOver;
    private boolean paused;
    private int score = 0;

    private AnimationTimer timer;
    private long lastUpdate = 0;
    private static final long UPDATE_INTERVAL = 140_000_000;

    /**
     * Inicia el juego y la ventana principal.
     * @param primaryStage El escenario principal del juego.
     */
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Snake");
        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        gc = canvas.getGraphicsContext2D();

        iniciarJuego();

        scene.setOnKeyPressed(event -> {
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
            } else if (code == KeyCode.ESCAPE) {
                if (!gameOver) {
                    paused = !paused;
                    if (paused) {
                        timer.stop();
                    } else {
                        timer.start();
                    }
                }
            } else if (code == KeyCode.R) {
                if (gameOver) {
                    iniciarJuego();
                    timer.start();
                }
            }
        });

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= UPDATE_INTERVAL) {
                    run(gc);
                    lastUpdate = now;
                }
            }
        };
        timer.start();
    }

    /**
     * Reinicia el juego.
     */
    private void iniciarJuego() {
        culebra = new Culebra();
        comida = new Comida(culebra.getBody());
        gameOver = false;
        paused = false;
        score = 0;
    }

    /**
     * Método principal que se ejecuta en cada frame del juego.
     * @param gc El contexto gráfico para dibujar en el canvas.
     */
    private void run(GraphicsContext gc) {
        if (gameOver) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("Digital-7", 70));
            gc.fillText("Fin del juego", WIDTH / 3.5, HEIGHT / 2);
            gc.setFont(new Font("Digital-7", 30));
            gc.fillText("Presiona R para reiniciar", WIDTH / 3.5, HEIGHT / 2 + 50);
            return;
        }

        if (paused) {
            gc.setFill(Color.YELLOW);
            gc.setFont(new Font("Digital-7", 70));
            gc.fillText("Pausa", WIDTH / 2 - 100, HEIGHT / 2);
            return;
        }

        crearfondo(gc);
        crearcomida(gc);
        crearculebra(gc);
        puntaje();

        culebra.move();

        if (culebra.checkCollision()) {
            gameOver = true;
        }

        if (culebra.getHead().getX() == comida.getX() && culebra.getHead().getY() == comida.getY()) {
            culebra.grow();
            comida.generateFood(culebra.getBody());
            score += 5;
        }
    }

    /**
     * Dibuja el fondo del área de juego.
     * @param gc El contexto gráfico para dibujar en el canvas.
     */
    private void crearfondo(GraphicsContext gc) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if ((i + j) % 2 == 0) {
                    gc.setFill(Color.web("0193BD"));
                } else {
                    gc.setFill(Color.web("0288D1"));
                }
                gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }

    /**
     * Dibuja la comida en el área de juego.
     * @param gc El contexto gráfico para dibujar en el canvas.
     */
    private void crearcomida(GraphicsContext gc) {
        gc.drawImage(comida.getImage(), comida.getX() * SQUARE_SIZE, comida.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

    /**
     * Dibuja la culebra en el área de juego.
     * @param gc El contexto gráfico para dibujar en el canvas.
     */
    private void crearculebra(GraphicsContext gc) {
        gc.setFill(Color.web("6aa84f"));
        gc.fillRoundRect(culebra.getHead().getX() * SQUARE_SIZE, culebra.getHead().getY() * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1, 35, 35);

        for (int i = 1; i < culebra.getBody().size(); i++) {
            gc.fillRoundRect(culebra.getBody().get(i).getX() * SQUARE_SIZE, culebra.getBody().get(i).getY() * SQUARE_SIZE, SQUARE_SIZE - 1,
                    SQUARE_SIZE - 1, 20, 20);
        }
    }

    /**
     * Muestra el puntaje actual en la pantalla.
     */
    private void puntaje() {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Digital-7", 35));
        gc.fillText("Score: " + score, 10, 35);
    }
}
