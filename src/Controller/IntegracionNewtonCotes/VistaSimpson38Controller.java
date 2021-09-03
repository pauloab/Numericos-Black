package Controller.IntegracionNewtonCotes;

import Controller.MetodosAproxRaices.*;
import Modelos.IntegracionNewtonCotes.Simpson38;
import Modelos.Interpolacion.InterpolacionLagrange;
import Modelos.MetodosAproxRaices.PuntoFijo;
import Plotter.Models.CoordinatePair;
import Plotter.Views.GraphManager;
import Util.Graficos;
import Util.Matematico;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class de la vista Simpson 3/8
 *
 * @author Freddy Lamar
 */
public class VistaSimpson38Controller implements Initializable {

    @FXML
    private TextField tfx0;
    @FXML
    private TextField tfFormula;
    @FXML
    private TextField tfx1;
    @FXML
    private Button btProcesar;
    @FXML
    private Button btLimpiar;
    @FXML
    private Label lbResultado;
    @FXML
    private BorderPane bpChart;
    @FXML
    private TextField tfYU, tfYD, tfXL, tfXR;
    @FXML
    private JFXButton btAjustar;
    private GraphManager graphManager;
    private double yu = 30, yd = -30, xl = -30, xr = 30;
    private final double DEFAULT_AXIS_VALUES = 30;
    private String funcion;
    private Double punto = null;
    private double x0, x1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        graphManager = new GraphManager(x0, x1, false);
        tfXL.setText(xl + "");
        tfXR.setText(xr + "");
        tfYU.setText(yu + "");
        tfYD.setText(yd + "");
        definirLimites();

        bpChart.setCenter(graphManager.getGraph());
        funcion = null;
        btAjustar.setOnMouseClicked(event -> {
            boolean limitesDefinidos = definirLimites();
            if (funcion != null && limitesDefinidos) {
                Graficar();
            }
        });

        Graficos.convertirEnInputFlotantes(tfXL);
        Graficos.convertirEnInputFlotantes(tfXR);
        Graficos.convertirEnInputFlotantes(tfYU);
        Graficos.convertirEnInputFlotantes(tfYD);

        // Se agrega la validación de los inputs
        Graficos.convertirEnInputFlotantes(tfx0);
        Graficos.convertirEnInputFlotantes(tfx1);

        btProcesar.setOnMouseClicked(event -> {
            funcion = tfFormula.getText();
            Simpson38 simpson38;
            boolean error = false;
            if (Matematico.validarExpresion(funcion)) {
                Double x0 = Graficos.validarTextFieldDouble(tfx0);
                Double x1 = Graficos.validarTextFieldDouble(tfx1);
                if (x0 != null && x1 != null && x0 != null) {
                    if (x1 > x0) {
                        simpson38 = new Simpson38(funcion, x0, x1);
                        try {
                            punto = simpson38.Simpson38Simple();
                            this.x0 = x0;
                            this.x1 = x1;
                            lbResultado.setText("" + punto);
                        } catch (Exception ex) {
                            error = true;
                            Graficos.lanzarMensajeError("Error de procesamiento", "Tuvimos un inconveniente al "
                                    + "interpretar o procesar la función "
                                    + "a travéz de este método.");
                        }
                        if (!error) {
                            Graficar();
                        }
                    } else {
                        Graficos.lanzarMensajeAdvertencia("Verifique el intervalo", "Verifique que el punto a sea menor que b");
                    }
                } else {
                    Graficos.lanzarMensajeError("Error de conversión", "Por favor, verifica el ingreso de datos antes de proceder.");
                }
            } else {
                Graficos.lanzarMensajeError("Error de conversión", "Hubo un error al interpretar la fórmula ingresada.");
            }
        });
        btLimpiar.setOnMouseClicked(e -> {
            graphManager.getGraph().getData().clear();
            lbResultado.setText("");
            funcion = null;
            graphManager.setDomain(-DEFAULT_AXIS_VALUES, DEFAULT_AXIS_VALUES);
            graphManager.setRange(-DEFAULT_AXIS_VALUES, DEFAULT_AXIS_VALUES);
            tfXL.setText("-" + DEFAULT_AXIS_VALUES);
            tfXR.setText("" + DEFAULT_AXIS_VALUES);
            tfYU.setText("" + DEFAULT_AXIS_VALUES);
            tfYD.setText("-" + DEFAULT_AXIS_VALUES);
            definirLimites();
            tfFormula.clear();
            tfx0.setText("");
            tfx1.setText("");
        });
    }

    private boolean definirLimites() {
        boolean res = false;
        Double xl = Graficos.validarTextFieldDouble(tfXL);
        Double xr = Graficos.validarTextFieldDouble(tfXR);
        Double yu = Graficos.validarTextFieldDouble(tfYU);
        Double yd = Graficos.validarTextFieldDouble(tfYD);
        if (xl != null && xr != null && yu != null && yd != null) {
            if (xl < xr && yu > yd) {
                if (Math.abs(xl - xr) <= Graficos.RANGO_GRAFICACION_MAX) {
                    if (Math.abs(yu - yd) <= Graficos.RANGO_GRAFICACION_MAX) {
                        this.xl = xl;
                        this.xr = xr;
                        this.yu = yu;
                        this.yd = yd;
                        graphManager.setDomain(xl, xr);
                        graphManager.setRange(yd, yu);
                        res = true;
                    } else {
                        Graficos.lanzarMensajeError("Error de validación",
                                "La diferencia entre yMax y yMin debe ser hasta " + Graficos.RANGO_GRAFICACION_MAX);
                    }
                } else {
                    Graficos.lanzarMensajeError("Error de validación",
                            "La diferencia entre xMax y xMin debe ser hasta " + Graficos.RANGO_GRAFICACION_MAX);
                }
            } else {
                Graficos.lanzarMensajeAdvertencia("Verifique los intervalos.",
                        "Verifique que el rango y el dominio. El intervalo debe ir de menor a mayor.");
            }
        } else {
            Graficos.lanzarMensajeError("Error de conversión.",
                    "Verifique los valores ingresados en los campos de control de gráfica.");
        }
        return res;
    }

    private void Graficar() {

        try {
            graphManager.setX0(x0);
            graphManager.setX1(x1);
            ArrayList<CoordinatePair[]> dataset = new ArrayList<>();
            dataset.add(Matematico.evaluarFuncion(funcion, xl, xr));
            if (x0 > x1) {
                double aux = x0;
                x0 = x1;
                x1 = aux;
            }
            double[] xCordenadasTrabajo = {x0, ((x1 - x0) / 3) + x0, ((x1 - x0) / 3) * 2 + x0, x1};
            double[] yCordenadasTrabajo = {Matematico.evaluarFuncion(funcion, x0), Matematico.evaluarFuncion(funcion, ((x1 - x0) / 3) + x0), Matematico.evaluarFuncion(funcion, ((x1 - x0) / 3) * 2 + x0), Matematico.evaluarFuncion(funcion, x1)};
            InterpolacionLagrange interpolar = new InterpolacionLagrange(xCordenadasTrabajo, yCordenadasTrabajo, x0 + (x1 - x0) / 4);
            interpolar.interpolacion();
            CoordinatePair[] puntosGraficar = Matematico.evaluarFuncion(interpolar.getFuncion(), x0, x1);
            dataset.add(puntosGraficar);
            Graficos.plotNoInterseciones(dataset, bpChart, graphManager);

        } catch (Exception e) {
            e.printStackTrace();
            Graficos.lanzarMensajeError("Error de Graficación", "Tuvimos un inconveniente al "
                    + "interpretar o procesar la función "
                    + "a travéz de este método, por tanto"
                    + "la gráfica no se pudo procesar.");
        }
    }
}
