package Controller;

import Util.Graficos;
import Vistas.Components.Utils.SidenavItem;
import Vistas.Components.Utils.SidenavContainer;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class dela vista Principal
 *
 * @author Geovanny Vega
 */
public class WelcomeController implements Initializable {

    @FXML
    private Pane  panedelante;

    @FXML
    private ImageView exit, menu, maximo, minimo, ivCerrados, ivAbiertos;

    @FXML
    private BorderPane panefront;

    @FXML
    private StackPane panelCarga;
    
    private SidenavContainer sidenavContainer;
    
    private BorderPane bpBiseccion, bpFalsaPosicion, bpNewtonRaphson, bpSecante, 
                       bpBairstow, bpMuller;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sidenavContainer = new SidenavContainer(menu, panedelante);
        
        precargarVistas();
        
        JFXButton bt;
        ArrayList<JFXButton> buttonList = new ArrayList<>();

        bt = new JFXButton("Método de Bisección");
        bt.setOnMouseClicked(e -> CargarVista(bpBiseccion));
        buttonList.add(bt);

        bt = new JFXButton("Método de Falsa Posición");
        bt.setOnMouseClicked(e -> CargarVista(bpFalsaPosicion));
        buttonList.add(bt);

        sidenavContainer.addItem(new SidenavItem("Métodos Cerrados", buttonList), ivCerrados);

        buttonList = new ArrayList<>();

        bt = new JFXButton("Método de Newton-Raphson");
        bt.setOnMouseClicked(e -> CargarVista(bpNewtonRaphson));
        buttonList.add(bt);

        bt = new JFXButton("Método de la Secante");
        bt.setOnMouseClicked(e -> CargarVista(bpSecante));
        buttonList.add(bt);

        bt = new JFXButton("Método de Müller");
        bt.setOnMouseClicked(e -> CargarVista(bpMuller));
        buttonList.add(bt);

        bt = new JFXButton("Método de Bairstow");
        bt.setOnMouseClicked(e -> CargarVista(bpBairstow));
        buttonList.add(bt);
        
        sidenavContainer.addItem(new SidenavItem("Métodos Abiertos", buttonList), ivAbiertos);

        panefront.setLeft(sidenavContainer);

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

    }

    public void precargarVistas(){
        try {
            bpBiseccion = FXMLLoader.load(
                    getClass().getResource("/Vistas/MetodosCerrados/VistaBiseccion.fxml")
            );
            bpBiseccion.setPadding(new Insets(0, 0, 0,64 ));
            
            bpFalsaPosicion = FXMLLoader.load(
                    getClass().getResource("/Vistas/MetodosCerrados/VistaFalsaPosicion.fxml")
            );
            bpFalsaPosicion.setPadding(new Insets(0, 0, 0,64 ));
            
            bpNewtonRaphson = FXMLLoader.load(
                    getClass().getResource("/Vistas/MetodosAbiertos/VistaNewtonRaphson.fxml")
            );
            bpNewtonRaphson.setPadding(new Insets(0, 0, 0,64 ));
            
            bpSecante = FXMLLoader.load(
                    getClass().getResource("/Vistas/MetodosAbiertos/VistaSecante.fxml")
            );
            bpSecante.setPadding(new Insets(0, 0, 0,64 ));
            
            bpMuller = FXMLLoader.load(
                    getClass().getResource("/Vistas/MetodosAbiertos/VistaMuller.fxml")
            );
            bpMuller.setPadding(new Insets(0, 0, 0,64 ));
            
            bpBairstow = FXMLLoader.load(
                    getClass().getResource("/Vistas/MetodosAbiertos/VistaBairstow.fxml")
            );
            bpBairstow.setPadding(new Insets(0, 0, 0,64 ));
            
        } catch (IOException ex) {
            Graficos.lanzarMensajeError("Error de carga de recursos", "Existe un "
                    + "problema al cargar la vista.");
            System.exit(-1);
        }
    }
    
    /**
     * Funcion para cargar las vistas
     * @param panel Panel a cargar en la vista.
     */
    public void CargarVista(BorderPane panel) {
        panelCarga.getChildren().set(0,panel);
        sidenavContainer.hideOrShow();
    }

}
