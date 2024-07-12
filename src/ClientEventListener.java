// Interface para el listener de eventos del cliente
interface ClientEventListener {
    void onEvent(byte[] data);
}

// Clase para representar eventos de socket
class SocketEvent implements java.io.Serializable {
    private String name;
    private byte[] data;

    public SocketEvent(String name, byte[] data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public byte[] getData() {
        return data;
    }
}