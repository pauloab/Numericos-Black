package Principal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class main extends Application {

    double x, y = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Vistas/Welcome.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        
        Image image = new Image(getClass().getResource("/images/logoapp.png").toExternalForm());
        primaryStage.getIcons().add(image);
        
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
        });
        primaryStage.setTitle("Numéricos Blacks - Aproximador por métodos numéricos");
        primaryStage.setScene(new Scene(root, 1280, 800));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
