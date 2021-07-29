/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.layout.BorderPane;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Geovanny Vega
 */
public class WelcomeController implements Initializable {

    @FXML
    private VBox pane2,vBoxIconos;

    @FXML
    private ImageView exit, menu, maximo, minimo;
    
    @FXML
    private BorderPane  panedetras, panedelante;

    /*@FXML
    private FlowPane leftfpane;*/
    private boolean openPane = false;
    
    private ArrayList<JFXButton> itemLists;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        

        
        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        maximo.setOnMouseClicked(event -> {
            Stage stage;
            stage = ((Stage) maximo.getScene().getWindow());
            stage.setMaximized(!stage.isMaximized());
        });

        minimo.setOnMouseClicked(event -> {
            ((Stage) minimo.getScene().getWindow()).setIconified(true);
        });

        panedelante.setVisible(false);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), panedelante);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), pane2);
        translateTransition.setByX(-600);
        translateTransition.play();

        menu.setOnMouseClicked(event -> {

            
            if (openPane == true) {
                openPane = false;
                panedetras.setVisible(true);
                FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.2), panedelante);
                fadeTransition1.setFromValue(0.15);
                fadeTransition1.setToValue(0);
                fadeTransition1.play();

                fadeTransition1.setOnFinished(event1 -> {
                    panedelante.setVisible(false);
                });

                TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
                translateTransition1.setByX(-600);
                translateTransition1.play();
            } else {
                openPane = true;
                panedelante.setVisible(true);
                panedetras.setVisible(false);
                FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), panedelante);
                fadeTransition1.setFromValue(0);
                fadeTransition1.setToValue(0.15);
                fadeTransition1.play();

                TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
                translateTransition1.setByX(+600);
                translateTransition1.play();

            }

        });

    }

}
