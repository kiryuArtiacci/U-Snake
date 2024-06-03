import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage menuprincipal) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene escena1 = new Scene(root);
            menuprincipal.setScene(escena1);
            menuprincipal.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}