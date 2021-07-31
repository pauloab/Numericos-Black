package Controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Geovanny Vega
 */
public class WelcomeController implements Initializable {

  @FXML
  private VBox pane2, vBoxIconos;

  @FXML
  private ImageView exit, menu, maximo, minimo;

  @FXML
  private BorderPane panedetras, panedelante, panefront;

  @FXML
  private Button btAbiertos;

  private int openPane;

  private ArrayList<JFXButton> itemLists;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    openPane = 0;
    exit.setOnMouseClicked(
      event -> {
        System.exit(0);
      }
    );

    maximo.setOnMouseClicked(
      event -> {
        Stage stage;
        stage = ((Stage) maximo.getScene().getWindow());
        stage.setMaximized(!stage.isMaximized());
      }
    );

    minimo.setOnMouseClicked(
      event -> {
        ((Stage) minimo.getScene().getWindow()).setIconified(true);
      }
    );

    panedelante.setVisible(false);

    FadeTransition fadeTransition = new FadeTransition(
      Duration.seconds(0.5),
      panedelante
    );
    fadeTransition.setFromValue(1);
    fadeTransition.setToValue(0);
    fadeTransition.play();

    TranslateTransition translateTransition = new TranslateTransition(
      Duration.seconds(0.5),
      pane2
    );
    translateTransition.setByX(-600);
    translateTransition.play();

    menu.setOnMouseClicked(
      event -> {
        if (openPane == 1) {
          openPane = -1;
          panedetras.setVisible(true);

          FadeTransition fadeTransition1 = new FadeTransition(
            Duration.seconds(0.2),
            panedelante
          );
          fadeTransition1.setFromValue(0.15);
          fadeTransition1.setToValue(0);
          fadeTransition1.play();

          fadeTransition1.setOnFinished(
            event1 -> {
              panedelante.setVisible(false);
              panefront.setVisible(false);
            }
          );

          TranslateTransition translateTransition1 = new TranslateTransition(
            Duration.seconds(0.5),
            pane2
          );
          translateTransition1.setByX(-600);
          translateTransition1.play();
          translateTransition1.setOnFinished(
            e -> {
              openPane = 0;
            }
          );
        } else if (openPane == 0) {
          openPane = -1;
          panedelante.setVisible(true);
          panedetras.setVisible(false);
          panefront.setVisible(true);
          FadeTransition fadeTransition1 = new FadeTransition(
            Duration.seconds(0.5),
            panedelante
          );
          fadeTransition1.setFromValue(0);
          fadeTransition1.setToValue(0.15);
          fadeTransition1.play();

          TranslateTransition translateTransition1 = new TranslateTransition(
            Duration.seconds(0.5),
            pane2
          );
          translateTransition1.setByX(+600);
          translateTransition1.play();
          translateTransition1.setOnFinished(
            e -> {
              openPane = 1;
            }
          );
        }
      }
    );

    btAbiertos.setOnMouseClicked(
      e -> {
        try {
          BorderPane root = FXMLLoader.load(
            getClass().getResource("/Vistas/VistaMuller.fxml")
          );
          panedetras.setCenter(root);
        } catch (IOException ex) {
          Logger
            .getLogger(WelcomeController.class.getName())
            .log(Level.SEVERE, null, ex);
        }
      }
    );
  }
}
