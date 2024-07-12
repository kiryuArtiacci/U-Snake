import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Controlador {
    private Stage menuprincipal;
    private Scene escena1;
    private Parent root;

    private ServerSocket serverSocket;
    private ClientSocket clientSocket;

    @FXML
    private Button btnSalir;
    @FXML
    private Button btnHost;
    @FXML
    private Button btnJoin;
    @FXML
    private TextField txtHostAddress;

    public void cambiaraescena1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        menuprincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
        escena1 = new Scene(root);
        menuprincipal.setScene(escena1);
        menuprincipal.show();
    }

    public void cambiaraescena2(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Juego.fxml"));
        root = loader.load();
        menuprincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
        escena1 = new Scene(root);
        menuprincipal.setScene(escena1);
        menuprincipal.show();

        Juego juego = new Juego();
        juego.start(menuprincipal);
    }

    public void cambiaraescena3(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Multiplayer.fxml"));
        menuprincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
        escena1 = new Scene(root);
        menuprincipal.setScene(escena1);
        menuprincipal.show();
    }

    @FXML
    private void salir() {
        Platform.exit();
    }

    @FXML
    private void hostGame(ActionEvent event) {
        try {
            serverSocket = new ServerSocket(5000, 2048);
            serverSocket.addListener("connected", (data, handler) -> {
                // Handle new connection
            });
            serverSocket.run();

            // Switch to game scene after hosting
            cambiaraescena2(event);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void joinGame(ActionEvent event) {
        try {
            String hostAddress = txtHostAddress.getText();
            InetAddress address = InetAddress.getByName(hostAddress);

            clientSocket = new ClientSocket(address, 5000, 2048);
            clientSocket.addListener("connected", data -> {
                // Handle successful connection
            });
            clientSocket.connect();

            // Switch to game scene after joining
            cambiaraescena2(event);

        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
