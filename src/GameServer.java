import java.io.IOException;
import java.util.HashMap;

public class GameServer {
    private ServerSocket serverSocket;
    private HashMap<String, Culebra> snakes = new HashMap<>();

    public GameServer(int port) throws IOException {
        serverSocket = new ServerSocket(port, 2048);

        serverSocket.addListener("connect", (data, handler) -> {
            String clientName = handler.getAddress().toString() + ":" + handler.getPort();
            snakes.put(clientName, new Culebra());
            handler.emit("connected", null);
        });

        serverSocket.addListener("move", (data, handler) -> {
            String clientName = handler.getAddress().toString() + ":" + handler.getPort();
            String direction = new String(data);
            Culebra snake = snakes.get(clientName);
            int dir = directionToInteger(direction);
            snake.setCurrentDirection(dir);
            snake.move();
            if (snake.checkCollision()) {
                handler.emit("disconnected", null);
                snakes.remove(clientName);
            }
            updateGameState();
        });

        serverSocket.addListener("disconnect", (data, handler) -> {
            String clientName = handler.getAddress().toString() + ":" + handler.getPort();
            snakes.remove(clientName);
            handler.emit("disconnected", null);
        });
    }

    private void updateGameState() {
        for (Culebra snake : snakes.values()) {
            // Serializar el estado de cada serpiente y emitir la actualización
            byte[] state = SocketSerializer.serialize(snake);
            serverSocket.emit("update", state);
        }
    }

    private int directionToInteger(String direction) {
        switch (direction) {
            case "RIGHT":
                return Culebra.RIGHT;
            case "LEFT":
                return Culebra.LEFT;
            case "UP":
                return Culebra.UP;
            case "DOWN":
                return Culebra.DOWN;
            default:
                return -1;
        }
    }

    public void run() {
        serverSocket.run();
    }
}
