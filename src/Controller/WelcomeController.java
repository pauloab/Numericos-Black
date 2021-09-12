package Controller;

import Util.Graficos;
import Util.Mensajes;
import Vistas.Components.Utils.SidenavItem;
import Vistas.Components.Utils.SidenavContainer;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
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
    private BorderPane panefront, panelCarga, bpTopBar;

    private SidenavContainer sidenavContainer;

    private BorderPane bpBiseccion, bpFalsaPosicion, bpNewtonRaphson, bpSecante,
            bpBairstow, bpMuller, bpTaylor, bpPuntoFijo, bpRegresionLineal, bpLagrange,
            bpPolinomial, bpTrapecio, bpSimpson13, bpSimpson38, bpGaussL,
            bpInterpolacionNewton, bpEuler, bpRK4, bpHeun, bpPuntoMedio,
            bpAcercaDe;

    private ArrayList<JFXButton> sideNavSubItems;

    private boolean maximized;
    private double x, y = 0;
    private Stage stage;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sideNavSubItems = new ArrayList<>();
        maximized = false;
        panefront.setPickOnBounds(false);
        panedelante.setPickOnBounds(false);
        sidenavContainer = new SidenavContainer(menu, panedelante);
        sidenavContainer.setPickOnBounds(false);
       
        precargarVistas();
        
        JFXButton bt;
        ArrayList<JFXButton> buttonList = new ArrayList<>();

        buttonList = new ArrayList<>();

        bt = new JFXButton("Serie de Taylor");
        bt.setOnMouseClicked(e -> CargarVista(bpTaylor, e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        sidenavContainer.addItem(new SidenavItem("Serie de Taylor", buttonList), ivTaylor);

        buttonList = new ArrayList<>();

        bt = new JFXButton("Método de Newton-Raphson");
        bt.setOnMouseClicked(e -> CargarVista(bpNewtonRaphson, e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        bt = new JFXButton("Método de la Secante");
        bt.setOnMouseClicked(e -> CargarVista(bpSecante, e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        bt = new JFXButton("Método de Müller");
        bt.setOnMouseClicked(e -> CargarVista(bpMuller, e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        bt = new JFXButton("Método de Bairstow");
        bt.setOnMouseClicked(e -> CargarVista(bpBairstow, e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        bt = new JFXButton("Método de Punto Fijo");
        bt.setOnMouseClicked(e -> CargarVista(bpPuntoFijo, e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        bt = new JFXButton("Método de Bisección");
        bt.setOnMouseClicked(e -> CargarVista(bpBiseccion, e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        bt = new JFXButton("Método de Falsa Posición");
        bt.setOnMouseClicked(e -> CargarVista(bpFalsaPosicion, e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        sidenavContainer.addItem(new SidenavItem("Aproximación de raíces", buttonList), ivAproximacionRaices);

        buttonList = new ArrayList<>();
        bt = new JFXButton("Regresión Lineal");
        bt.setOnMouseClicked(e -> CargarVista(bpRegresionLineal, e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        bt = new JFXButton("Regresión Cuadrática");
        bt.setOnMouseClicked(e -> CargarVista(bpPolinomial, e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        sidenavContainer.addItem(new SidenavItem("Ajuste de curvas", buttonList), ivCurves);

        buttonList = new ArrayList<>();
        bt = new JFXButton("Lagrange");
        bt.setOnMouseClicked(e -> CargarVista(bpLagrange, e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        bt = new JFXButton("Newton por DD");
        bt.setOnMouseClicked(e -> CargarVista(bpInterpolacionNewton, e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        sidenavContainer.addItem(new SidenavItem("Interpolación", buttonList), ivInterpolacion);

        buttonList = new ArrayList<>();
        bt = new JFXButton("Regla del Trapecio");
        bt.setOnMouseClicked(e -> CargarVista(bpTrapecio, e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        bt = new JFXButton("Regla de Simpson 1/3");
        bt.setOnMouseClicked(e -> CargarVista(bpSimpson13, e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        bt = new JFXButton("Regla de Simpson 3/8");
        bt.setOnMouseClicked(e -> CargarVista(bpSimpson38, e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        sidenavContainer.addItem(new SidenavItem("Integración numérica", buttonList), ivIntegracionNumerica);

        buttonList = new ArrayList<>();

        bt = new JFXButton("Coeficientes indeterminados");
        bt.setOnMouseClicked(e -> CargarVista(bpGaussL, e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        sidenavContainer.addItem(new SidenavItem("Integración de ecuaciones", buttonList), ivIntegracionEcuaciones);

        buttonList = new ArrayList<>();

        bt = new JFXButton("Método de Euler");
        bt.setOnMouseClicked(e -> CargarVista(bpEuler, e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        bt = new JFXButton("RK de Cuarto Orden");
        bt.setOnMouseClicked(e -> CargarVista(bpRK4, e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        bt = new JFXButton("Heun con Iteraciones");
        bt.setOnMouseClicked(e -> CargarVista(bpHeun, e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        bt = new JFXButton("Método de Punto Medio");
        bt.setOnMouseClicked(e -> CargarVista(bpPuntoMedio, e));
        buttonList.add(bt);
        sideNavSubItems.add(bt);

        sidenavContainer.addItem(new SidenavItem("Métodos Runge-Kutta", buttonList), ivRungeKutta);

        buttonList = new ArrayList<>();

        SidenavItem sniAcercaDe = new SidenavItem("Acerca De", buttonList);
        
        sniAcercaDe.getTopButton().setOnMouseClicked(e -> CargarVista(bpAcercaDe, e));

        sidenavContainer.addItem(sniAcercaDe, ivAcercaDe);
        panefront.setLeft(sidenavContainer);

        exit.setOnMouseClicked(
                event -> {
                    System.exit(0);
                }
        );

        maximo.setOnMouseClicked(this::maximizar);

        minimo.setOnMouseClicked(
                event -> {
                    ((Stage) minimo.getScene().getWindow()).setIconified(true);
                }
        );

    }

    public void maximizar(MouseEvent event) {

        ObservableList<Screen> screens = Screen.getScreensForRectangle(new Rectangle2D(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight()));
        Rectangle2D bounds = screens.get(0).getVisualBounds();
        if (!maximized) {
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());

        } else {
            stage.setWidth(stage.getMinWidth());
            stage.setHeight(stage.getMinHeight());
            stage.setX((bounds.getWidth() / 2) - (stage.getWidth() / 2));
            stage.setY((bounds.getHeight() / 2) - (stage.getHeight() / 2));
        }
        maximized = !maximized;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        bpTopBar.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        bpTopBar.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (event.getClickCount() == 2) {
                    maximizar(event);
                }
            }
        });
        bpTopBar.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
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

            bpHeun = FXMLLoader.load(
                    getClass().getResource("/Vistas/RungeKutta/VistaHeun.fxml")
            );
            bpHeun.setPadding(new Insets(0, 0, 0, 64));

            bpPuntoMedio = FXMLLoader.load(
                    getClass().getResource("/Vistas/RungeKutta/VistaPuntoMedio.fxml")
            );
            bpPuntoMedio.setPadding(new Insets(0, 0, 0, 64));

            bpRK4 = FXMLLoader.load(
                    getClass().getResource("/Vistas/RungeKutta/VistaRKCuarto.fxml")
            );
            bpRK4.setPadding(new Insets(0, 0, 0, 64));

            bpAcercaDe = FXMLLoader.load(
                    getClass().getResource("/Vistas/AcercaDe.fxml")
            );
            bpAcercaDe.setPadding(new Insets(0, 0, 0, 64));

        } catch (IOException ex) {
            Graficos.lanzarMensajeError(Mensajes.E_CARGA_ECURSOS, Mensajes.ERROR_CARGA_RECURSOS_VISTA);
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
            if (((JFXButton) event.getSource()) != e) {
                e.getStyleClass().remove("side-navbar-subitem-selected");
            } else {
                e.getStyleClass().add("side-navbar-subitem-selected");
            }
        });

        panelCarga.setCenter(panel);
        sidenavContainer.hideOrShow();
    }

}
