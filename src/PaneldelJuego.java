import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PaneldelJuego {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    public void start(Stage primaryStage) { //Pantalla principal del juego
        primaryStage.setTitle("Snake Game Menu");
        VBox menuRoot = new VBox(10);
        Scene menuScene = new Scene(menuRoot, WIDTH, HEIGHT);

        Button btnStart = new Button("Un Jugador"); 
        btnStart.setOnAction((ActionEvent event) -> startGame(primaryStage));

        Button btnExit = new Button("Salir");
        btnExit.setOnAction(e -> primaryStage.close());

        menuRoot.getChildren().addAll(btnStart, btnExit);
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    private void startGame(Stage primaryStage) { //Inicia el juego
        Juego juego = new Juego();
        juego.start(primaryStage);
    }
}
