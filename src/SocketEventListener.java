@FunctionalInterface
public interface SocketEventListener {
  void onEvent(byte[] data, ClientHandler handler);
}
