import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Controlador {
    private Stage menuprincipal;
    private Scene escena1;
    private Parent root;

    public void cambiaraescena1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        menuprincipal = (Stage)((Node)event.getSource()).getScene().getWindow();
        escena1 = new Scene(root);
        menuprincipal.setScene(escena1);
        menuprincipal.show();
    }

    public void cambiaraescena2(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Juego.fxml"));
        root = loader.load();
        menuprincipal = (Stage)((Node)event.getSource()).getScene().getWindow();
        escena1 = new Scene(root);
        menuprincipal.setScene(escena1);
        menuprincipal.show();

        Juego juego = new Juego();
        juego.start(menuprincipal);
    }
}
