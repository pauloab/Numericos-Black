package Principal;

import Controller.WelcomeController;
import Util.ResizeHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Welcome.fxml"));
        Parent root = (Parent) loader.load();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(true);
        
        WelcomeController controller = (WelcomeController) loader.getController();
        controller.setStage(primaryStage);
        
        Image image = new Image(getClass().getResource("/images/logoapp.png").toExternalForm());
        primaryStage.getIcons().add(image);

        primaryStage.setTitle("Numéricos Blacks - Aproximador por métodos numéricos");
        primaryStage.setScene(new Scene(root, 1280, 740));
        primaryStage.setMinHeight(740);
        primaryStage.setMinWidth(1280);
        ResizeHelper.addResizeListener(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
