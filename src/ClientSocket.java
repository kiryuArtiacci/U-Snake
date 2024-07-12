import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientSocket {
    DatagramSocket socket;
    InetAddress address;
    int port;

    boolean isConnected = false;
    boolean shouldClose = false;

    byte[] inBuffer;
    byte[] outBuffer;

    Integer timeoutMs = 5 * 1000;
    Long connectionTimeout;

    HashMap<String, ArrayList<ClientEventListener>> listeners = new HashMap<>();

    public ClientSocket(InetAddress address, int port, int packetSize) throws IOException {
        this.socket = new DatagramSocket();
        this.address = address;
        this.port = port;
        this.inBuffer = new byte[packetSize];
        this.outBuffer = new byte[packetSize];

        // Verificar si la dirección es alcanzable
        if (!address.isReachable(timeoutMs)) {
            throw new IOException("Address " + address.getHostAddress() + " is not reachable");
        }
    }

    public void connect() {
        new Thread(this::receiveEventsLoop).start();
        new Thread(this::connectionLoop).start();

        connectionTimeout = System.currentTimeMillis() + timeoutMs;
        try {
            emit("connect", null);
        } catch (Exception e) {
            e.printStackTrace();
            shouldClose = true;
            socket.close();
        }
    }

    public void disconnect() {
        emit("disconnect", null);
        shouldClose = true;
        socket.close();
    }

    private void receiveEventsLoop() {
        while (true) {
            if (shouldClose) {
                break;
            }

            try {
                DatagramPacket packet = new DatagramPacket(inBuffer, inBuffer.length);
                socket.receive(packet);

                SocketEvent event = (SocketEvent) SocketSerializer.deserialize(packet.getData());

                String eventName = event.getName();

                if (eventName.equals("connected") && !isConnected) {
                    isConnected = true;
                    connectionTimeout = null;
                }

                runListeners(event);
            } catch (Exception e) {
                System.err.println("Error in receiveEventsLoop: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void connectionLoop() {
        while (true) {
            if (shouldClose) {
                break;
            }

            try {
                Thread.sleep(1000);

                if (connectionTimeout != null && System.currentTimeMillis() > connectionTimeout) {
                    shouldClose = true;
                    socket.close();
                    runListeners(new SocketEvent("connectionError", null));
                    break;
                }

                emit("ping", null);
            } catch (Exception e) {
                System.err.println("Error in connectionLoop: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void addListener(String name, ClientEventListener listener) {
        listeners.computeIfAbsent(name, k -> new ArrayList<>()).add(listener);
    }

    private void runListeners(SocketEvent event) {
        if (listeners.containsKey(event.getName())) {
            for (ClientEventListener listener : listeners.get(event.getName())) {
                listener.onEvent(event.getData());
            }
        }
    }

    public void emit(String name, byte[] data) {
        SocketEvent event = new SocketEvent(name, data);
        try {
            outBuffer = SocketSerializer.serialize(event);
            DatagramPacket packet = new DatagramPacket(outBuffer, outBuffer.length, address, port);

            // Verificar si la dirección es alcanzable antes de enviar
            if (!address.isReachable(timeoutMs)) {
                throw new IOException("Address " + address.getHostAddress() + " is not reachable");
            }

            socket.send(packet);
        } catch (Exception e) {
            System.err.println("Error in emit: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }

    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getByName("127.0.0.1"); // Asegúrate de usar la dirección correcta
            ClientSocket client = new ClientSocket(address, 12345, 2048);
            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
