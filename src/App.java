import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage menuprincipal) {
        try {
            // Cargar el archivo FXML
            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            
            // Crear la escena
            Scene escena1 = new Scene(root);
            
            // Añadir la hoja de estilo CSS
            // Asegúrate de que la ruta sea correcta y que application.css esté en el mismo paquete que App.java
            String css = getClass().getResource("application.css").toExternalForm();
            escena1.getStylesheets().add(css);
            
            // Configurar el escenario
            menuprincipal.setScene(escena1);
            menuprincipal.setTitle("U-Snake");
            menuprincipal.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
