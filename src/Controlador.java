import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controlador {
    private Stage menuprincipal;
    private Scene escena1;
    private Parent root;


    public void cambiaraescena1(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        menuprincipal = (Stage)((Node)event.getSource()).getScene().getWindow();
        escena1 = new Scene(root);
        menuprincipal.setScene(escena1);
        menuprincipal.show();
    }

    public void cambiaraescena2(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("Juego.fxml"));
        menuprincipal = (Stage)((Node)event.getSource()).getScene().getWindow();
        escena1 = new Scene(root);
        menuprincipal.setScene(escena1);
        menuprincipal.show();
    }
}
