import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import java.awt.Point;

public class ControladorJuego {
    @FXML
    private Canvas canvas;

    private ClientSocket clientSocket;
    private GraphicsContext gc;
    private Culebra culebra;

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
        initializeMultiplayer();
    }

    private void initializeMultiplayer() {
        clientSocket.addListener("update", this::onGameUpdate);
        clientSocket.addListener("connected", this::onConnected);
        clientSocket.addListener("disconnected", this::onDisconnected);
    }

    private void onGameUpdate(byte[] data) {
        Platform.runLater(() -> {
            Culebra updatedCulebra = (Culebra) SocketSerializer.deserialize(data);
            updateCulebra(updatedCulebra);
        });
    }

    private void onConnected(byte[] data) {
        Platform.runLater(() -> {
            // Lógica de conexión exitosa
        });
    }

    private void onDisconnected(byte[] data) {
        Platform.runLater(() -> {
            // Lógica de desconexión
        });
    }

    private void updateCulebra(Culebra updatedCulebra) {
        culebra = updatedCulebra;
        draw();
    }

    @FXML
    private void initialize() {
        gc = canvas.getGraphicsContext2D();
        // Inicializar el juego aquí
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        String direction = event.getCode().toString();
        clientSocket.emit("move", direction.getBytes());
    }

    private void draw() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.GREEN);
        for (Point point : culebra.getBody()) {
            gc.fillRect(point.x * 20, point.y * 20, 20, 20);
        }
    }
}
