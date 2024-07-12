import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientHandler {
    private DatagramSocket socket;
    private InetAddress address;
    private int port;
    private byte[] buffer = new byte[2048];

    public ClientHandler(DatagramSocket serverSocket, InetAddress address, int port, int packetSize) {
        this.socket = serverSocket;
        this.address = address;
        this.port = port;
        this.buffer = new byte[packetSize];
    }

    public void emit(String name, byte[] data) {
        try {
            SocketEvent event = new SocketEvent(name, data);
            buffer = SocketSerializer.serialize(event);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
}
