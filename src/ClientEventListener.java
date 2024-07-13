@FunctionalInterface
public interface ClientEventListener {
  void onEvent(byte[] data);
}