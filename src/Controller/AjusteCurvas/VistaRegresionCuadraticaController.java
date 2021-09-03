package Controller.AjusteCurvas;

import Modelos.AjusteDeCurvas.RegresionCuadratica;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Javier Matamoros
 */
public class VistaRegresionCuadraticaController implements Initializable {

    @FXML
    private Button btProcesar;
    @FXML
    private Button btLimpiar;
    @FXML
    private Button btAdd;
    @FXML
    private Button btRemove;
    @FXML
    private Button btPronosticar;
    @FXML
    private VBox vbXValues, vbYValues;
    @FXML
    private Label lbasubcero, lbasubuno, lbasubdos, lbDesviacion, lbErrorEstandar,
            lbCoeficienteDet, lbCoeficienteCor;
    @FXML
    private TextField tfInput, tfOutput;
    @FXML
    private TextField tfYU, tfYD, tfXL, tfXR;
    @FXML
    private JFXButton btAjustar;
    @FXML
    private BorderPane bpChart;

    private GraphManager graphManager;
    private double yu = 30, yd = -30, xl = -30, xr = 30;
    private final double DEFAULT_AXIS_VALUES = 30;

    private RegresionCuadratica polinomial;
    private CoordinatePair[] dataInput;
    private String funcion;

    private static final int NUM_MAXIMO_COORDS = 30;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        graphManager = new GraphManager();
        tfXL.setText(xl + "");
        tfXR.setText(xr + "");
        tfYU.setText(yu + "");
        tfYD.setText(yd + "");
        definirLimites();

        bpChart.setCenter(graphManager.getGraph());
        btAjustar.setOnMouseClicked(event -> {
            boolean limitesDefinidos = definirLimites();
            if (polinomial != null && limitesDefinidos) {
                Graficar(dataInput);
            }
        });

        Graficos.convertirEnInputFlotantes(tfXL);
        Graficos.convertirEnInputFlotantes(tfXR);
        Graficos.convertirEnInputFlotantes(tfYU);
        Graficos.convertirEnInputFlotantes(tfYD);

        addCordFilds();
        addCordFilds();

        // Se agrega un elemnto al vector de coeficientes
        btAdd.setOnMouseClicked(event -> {
            addCordFilds();
        });

        btProcesar.setOnMouseClicked(event -> {
            boolean error = false;
            int n = vbXValues.getChildren().size();
            double[] x = new double[n];
            double[] y = new double[n];
            Double xTemp, yTemp;
            for (int i = 0; i < n; i++) {
                TextField xField = (TextField) vbXValues.getChildren().get(i);
                TextField yField = (TextField) vbYValues.getChildren().get(i);
                xTemp = Graficos.validarTextFieldDouble(xField);
                yTemp = Graficos.validarTextFieldDouble(yField);
                if (xTemp != null && yTemp != null) {
                    x[i] = xTemp;
                    y[i] = yTemp;
                } else {
                    error = true;
                }
            }
            if (Matematico.validarRepetidosDouble(x)) {
                if (error == false) {
                    polinomial = new RegresionCuadratica(x, y, 2);
                    try {
                        polinomial.regresionPolinomial();
                    } catch (Exception ex) {
                        Graficos.lanzarMensajeError("Error de procesamiento", "EL metodo no converge");
                    }
                    lbasubcero.setText("a0 = " + polinomial.getCoeficientesResultantes()[0]);
                    lbasubuno.setText("a1 = " + polinomial.getCoeficientesResultantes()[1]);
                    lbasubdos.setText("a2 = " + polinomial.getCoeficientesResultantes()[2]);
                    lbDesviacion.setText("Sy = " + polinomial.getDesviacionEstandar());
                    lbErrorEstandar.setText("Sy/x = " + polinomial.getErrorEstandar());
                    lbCoeficienteCor.setText("r2 = " + polinomial.getCoeficienteCorrelacion());
                    lbCoeficienteDet.setText("r = " + polinomial.getCoeficienteDeterminacion());
                    btPronosticar.setDisable(false);
                    dataInput = new CoordinatePair[n];
                    funcion = polinomial.getFuncionRegresion();
                    for (int i = 0; i < n; i++) {
                        dataInput[i] = new CoordinatePair(x[i], y[i]);
                    }
                    Graficar(dataInput);
                } else {
                    Graficos.lanzarMensajeError("Error de conversión", "Por favor, verifica el ingreso de datos antes de proceder.");
                }
            } else {
                Graficos.lanzarMensajeError("Error de validación", "Verifique que los puntos de x sean en las coordenadas distintos.");
            }
        });

        btPronosticar.setOnAction(event -> {
            if (polinomial != null) {
                Double in = Graficos.validarTextFieldDouble(tfInput);
                if (in != null) {
                    try {
                        tfOutput.setText("" + polinomial.pronosticar(in));
                    } catch (Exception ex) {
                        Graficos.lanzarMensajeError("Error de procesamiento", "No se pudo validar la funcion");
                    }
                }
            }
        });

        btRemove.setOnMouseClicked(e -> {
            if (vbXValues.getChildren().size() > 2) {
                vbYValues.getChildren().remove(vbYValues.getChildren().size() - 1);
                vbXValues.getChildren().remove(vbXValues.getChildren().size() - 1);
            }
            if (vbXValues.getChildren().size() == 2) {
                btPronosticar.setDisable(true);
            } else {
                btPronosticar.setDisable(false);
            }
        });

        btLimpiar.setOnMouseClicked(e -> {
            graphManager.getGraph().getData().clear();
            graphManager.setDomain(-DEFAULT_AXIS_VALUES, DEFAULT_AXIS_VALUES);
            graphManager.setRange(-DEFAULT_AXIS_VALUES, DEFAULT_AXIS_VALUES);
            tfXL.setText("-" + DEFAULT_AXIS_VALUES);
            tfXR.setText("" + DEFAULT_AXIS_VALUES);
            tfYU.setText("" + DEFAULT_AXIS_VALUES);
            tfYD.setText("-" + DEFAULT_AXIS_VALUES);
            definirLimites();
            lbasubcero.setText("");
            lbasubuno.setText("");
            lbasubdos.setText("");
            lbDesviacion.setText("");
            lbErrorEstandar.setText("");
            lbCoeficienteCor.setText("");
            lbCoeficienteDet.setText("");
            polinomial = null;
            tfOutput.clear();
            tfInput.clear();
            funcion = null;
            btPronosticar.setDisable(true);
            for (int i = vbXValues.getChildren().size() - 1; i >= 0; i--) {
                if (i > 1) {
                    vbYValues.getChildren().remove(vbYValues.getChildren().size() - 1);
                    vbXValues.getChildren().remove(vbXValues.getChildren().size() - 1);
                } else {
                    ((TextField) vbXValues.getChildren().get(i)).clear();
                    ((TextField) vbYValues.getChildren().get(i)).clear();
                }
            }
        });
    }

    private void addCordFilds() {
        if (NUM_MAXIMO_COORDS > vbXValues.getChildren().size()) {
            TextField tf = new TextField();
            Graficos.convertirEnInputFlotantes(tf);
            vbXValues.getChildren().add(tf);
            tf = new TextField();
            Graficos.convertirEnInputFlotantes(tf);
            vbYValues.getChildren().add(tf);
        } else {
            Graficos.lanzarMensajeAdvertencia("Número máximo de puntos",
                    "Ha alcanzado el número máximo de puntos que se pueden ingresar: "
                    + NUM_MAXIMO_COORDS);
        }

    }

    private void Graficar(CoordinatePair[] dataInput) {
        try {
            graphManager.getGraph().getData().clear();
            ArrayList<CoordinatePair[]> dataset = new ArrayList<>();
            dataset.add(dataInput);
            dataset.add(Matematico.evaluarFuncion(funcion, xl, xr));
            Graficos.plotPuntosLineas(dataset, bpChart, graphManager);
        } catch (Exception e) {
            Graficos.lanzarMensajeError("Error de Graficación", "Tuvimos un inconveniente al "
                    + "interpretar o procesar la función "
                    + "a travéz de este método, por tanto"
                    + "la gráfica no se pudo procesar.");
        }
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
                                "La diferencia entre yMax y yMin debe ser hasta "+Graficos.RANGO_GRAFICACION_MAX);
                    }
                } else {
                    Graficos.lanzarMensajeError("Error de validación",
                            "La diferencia entre xMax y xMin debe ser hasta "+Graficos.RANGO_GRAFICACION_MAX);
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
}
