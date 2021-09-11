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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class dela vista Principal
 *
 * @author Geovanny Vega
 */
public class WelcomeController implements Initializable {

    @FXML
    private Pane panedelante;

    @FXML
    private ImageView exit, menu, maximo, minimo, ivAproximacionRaices,
            ivTaylor, ivCurves, ivIntegracionNumerica, ivRungeKutta, ivInterpolacion, ivIntegracionEcuaciones, ivAcercaDe;

    @FXML
    private BorderPane panefront, panelCarga;

    private SidenavContainer sidenavContainer;

    private BorderPane bpBiseccion, bpFalsaPosicion, bpNewtonRaphson, bpSecante,
            bpBairstow, bpMuller, bpTaylor, bpPuntoFijo, bpRegresionLineal, bpLagrange,
            bpPolinomial, bpTrapecio, bpSimpson13, bpSimpson38, bpGaussL,
            bpInterpolacionNewton,bpEuler;

    private ArrayList<JFXButton> sideNavSubItems;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sideNavSubItems = new ArrayList<>();
        panefront.setPickOnBounds(false);
        panedelante.setPickOnBounds(false);
        sidenavContainer = new SidenavContainer(menu, panedelante);
        sidenavContainer.setPickOnBounds(false);
        precargarVistas();

        JFXButton bt;
        ArrayList<JFXButton> buttonList = new ArrayList<>();

        buttonList = new ArrayList<>();

        bt = new JFXButton("Serie de Taylor");
        bt.setOnMouseClicked(e -> CargarVista(bpTaylor,e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);
        
        sidenavContainer.addItem(new SidenavItem("Serie de Taylor", buttonList), ivTaylor);

        buttonList = new ArrayList<>();

        bt = new JFXButton("Método de Newton-Raphson");
        bt.setOnMouseClicked(e -> CargarVista(bpNewtonRaphson,e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        bt = new JFXButton("Método de la Secante");
        bt.setOnMouseClicked(e -> CargarVista(bpSecante,e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        bt = new JFXButton("Método de Müller");
        bt.setOnMouseClicked(e -> CargarVista(bpMuller,e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        bt = new JFXButton("Método de Bairstow");
        bt.setOnMouseClicked(e -> CargarVista(bpBairstow,e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        bt = new JFXButton("Método de Punto Fijo");
        bt.setOnMouseClicked(e -> CargarVista(bpPuntoFijo,e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        bt = new JFXButton("Método de Bisección");
        bt.setOnMouseClicked(e -> CargarVista(bpBiseccion,e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        bt = new JFXButton("Método de Falsa Posición");
        bt.setOnMouseClicked(e -> CargarVista(bpFalsaPosicion,e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);
        
        sidenavContainer.addItem(new SidenavItem("Aproximación de raíces", buttonList), ivAproximacionRaices);

        buttonList = new ArrayList<>();
        bt = new JFXButton("Regresión Lineal");
        bt.setOnMouseClicked(e -> CargarVista(bpRegresionLineal,e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);
        

        bt = new JFXButton("Regresión Cuadrática");
        bt.setOnMouseClicked(e -> CargarVista(bpPolinomial,e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        sidenavContainer.addItem(new SidenavItem("Ajuste de curvas", buttonList), ivCurves);

        buttonList = new ArrayList<>();
        bt = new JFXButton("Lagrange");
        bt.setOnMouseClicked(e -> CargarVista(bpLagrange,e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);
        
        bt = new JFXButton("Newton por DD");
        bt.setOnMouseClicked(e -> CargarVista(bpInterpolacionNewton,e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        sidenavContainer.addItem(new SidenavItem("Interpolación", buttonList), ivInterpolacion);

        buttonList = new ArrayList<>();
        bt = new JFXButton("Regla del Trapecio");
        bt.setOnMouseClicked(e -> CargarVista(bpTrapecio,e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        bt = new JFXButton("Regla de Simpson 1/3");
        bt.setOnMouseClicked(e -> CargarVista(bpSimpson13,e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);
        
        bt = new JFXButton("Regla de Simpson 3/8");
        bt.setOnMouseClicked(e -> CargarVista(bpSimpson38,e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        sidenavContainer.addItem(new SidenavItem("Integración numérica", buttonList), ivIntegracionNumerica);

        buttonList = new ArrayList<>();
        
        bt = new JFXButton("Gauss-Legendre");
        bt.setOnMouseClicked(e -> CargarVista(bpGaussL,e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);
        
        sidenavContainer.addItem(new SidenavItem("Integración de ecuaciones", buttonList), ivIntegracionEcuaciones);
        
        buttonList = new ArrayList<>();
                
        bt = new JFXButton("Método de Euler");
        bt.setOnMouseClicked(e -> CargarVista(bpEuler,e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);
        
        
        sidenavContainer.addItem(new SidenavItem("Métodos Runge-Kutta", buttonList), ivRungeKutta);
        
        buttonList = new ArrayList<>();
        sidenavContainer.addItem(new SidenavItem("Acerca De", buttonList), ivAcercaDe);
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

    public void precargarVistas() {
        try {
            bpBiseccion = FXMLLoader.load(
                    getClass().getResource("/Vistas/MetodosAproxRaices/VistaBiseccion.fxml")
            );
            bpBiseccion.setPadding(new Insets(0, 0, 0, 64));

            bpFalsaPosicion = FXMLLoader.load(
                    getClass().getResource("/Vistas/MetodosAproxRaices/VistaFalsaPosicion.fxml")
            );
            bpFalsaPosicion.setPadding(new Insets(0, 0, 0, 64));

            bpNewtonRaphson = FXMLLoader.load(
                    getClass().getResource("/Vistas/MetodosAproxRaices/VistaNewtonRaphson.fxml")
            );
            bpNewtonRaphson.setPadding(new Insets(0, 0, 0, 64));

            bpSecante = FXMLLoader.load(
                    getClass().getResource("/Vistas/MetodosAproxRaices/VistaSecante.fxml")
            );
            bpSecante.setPadding(new Insets(0, 0, 0, 64));

            bpMuller = FXMLLoader.load(
                    getClass().getResource("/Vistas/MetodosAproxRaices/VistaMuller.fxml")
            );
            bpMuller.setPadding(new Insets(0, 0, 0, 64));

            bpBairstow = FXMLLoader.load(
                    getClass().getResource("/Vistas/MetodosAproxRaices/VistaBairstow.fxml")
            );
            bpBairstow.setPadding(new Insets(0, 0, 0, 64));

            bpTaylor = FXMLLoader.load(
                    getClass().getResource("/Vistas/SerieTaylor/VistaSerieTaylor.fxml")
            );
            bpTaylor.setPadding(new Insets(0, 0, 0, 64));

            bpPuntoFijo = FXMLLoader.load(
                    getClass().getResource("/Vistas/MetodosAproxRaices/VistaPuntoFijo.fxml")
            );
            bpPuntoFijo.setPadding(new Insets(0, 0, 0, 64));

            bpRegresionLineal = FXMLLoader.load(
                    getClass().getResource("/Vistas/AjusteCurvas/VistaRegresionLineal.fxml")
            );
            bpRegresionLineal.setPadding(new Insets(0, 0, 0, 64));
            bpPolinomial = FXMLLoader.load(
                    getClass().getResource("/Vistas/AjusteCurvas/VistaRegresionCuadratica.fxml")
            );
            bpPolinomial.setPadding(new Insets(0, 0, 0, 64));

            bpLagrange = FXMLLoader.load(
                    getClass().getResource("/Vistas/Interpolacion/VistaLagrange.fxml")
            );
            bpLagrange.setPadding(new Insets(0, 0, 0, 64));

            bpInterpolacionNewton = FXMLLoader.load(
                    getClass().getResource("/Vistas/Interpolacion/VistaInterpolacionNewton.fxml")
            );
            bpInterpolacionNewton.setPadding(new Insets(0, 0, 0, 64));
            
            bpTrapecio = FXMLLoader.load(
                    getClass().getResource("/Vistas/IntegracionNewtonCotes/VistaReglaTrapecio.fxml")
            );
            bpTrapecio.setPadding(new Insets(0, 0, 0, 64));
            
            bpSimpson13 = FXMLLoader.load(
                    getClass().getResource("/Vistas/IntegracionNewtonCotes/VistaSimpson13.fxml")
            );
            bpSimpson13.setPadding(new Insets(0, 0, 0, 64));
            
            bpSimpson38 = FXMLLoader.load(
                    getClass().getResource("/Vistas/IntegracionNewtonCotes/VistaSimpson38.fxml")
            );
            bpSimpson38.setPadding(new Insets(0, 0, 0, 64));
            
            bpGaussL = FXMLLoader.load(
                    getClass().getResource("/Vistas/GaussLegendre/VistaCuadraturaGauss.fxml")
            );
            bpGaussL.setPadding(new Insets(0, 0, 0, 64));
            
            bpEuler = FXMLLoader.load(
                    getClass().getResource("/Vistas/RungeKutta/VistaEuler.fxml")
            );
            bpEuler.setPadding(new Insets(0, 0, 0, 64));

        } catch (IOException ex) {
            ex.printStackTrace();
            Graficos.lanzarMensajeError("Error de carga de recursos", "Existe un "
                    + "problema al cargar la vista.");
            System.exit(-1);
        }
    }

    /**
     * Funcion para cargar las vistas
     *
     * @param panel Panel a cargar en la vista.
     */
    public void CargarVista(BorderPane panel, MouseEvent event) {
        sideNavSubItems.forEach(e -> {
            if (((JFXButton)event.getSource()) != e) {
                e.getStyleClass().remove("side-navbar-subitem-selected");
            }else{
                e.getStyleClass().add("side-navbar-subitem-selected");
            }    
        });
        
        panelCarga.setCenter(panel);
        sidenavContainer.hideOrShow();
    }

}
