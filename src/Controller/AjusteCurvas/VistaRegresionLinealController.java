package Controller.AjusteCurvas;

import Modelos.AjusteDeCurvas.RegresionLineal;
import Plotter.Models.CoordinatePair;
import Plotter.Views.GraphManager;
import Util.Graficos;
import Util.Matematico;
import Util.Mensajes;
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
 * FXML Controller class de la vista Baistow
 *
 * @author Paulo Aguilar
 */
public class VistaRegresionLinealController implements Initializable {

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
    private Label lbPendiente, lbInterseccion, lbDerviacion, lbErrorEstandar,
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

    private RegresionLineal regresor;
    private CoordinatePair[] dataInput;

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
            if (regresor != null && limitesDefinidos) {
                Graficar(dataInput, regresor.getInterseccion(), regresor.getPendiente());
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
                    regresor = new RegresionLineal(x, y);
                    regresor.regresionLineal();
                    lbPendiente.setText("a1 = " + regresor.getPendiente());
                    lbInterseccion.setText("a0 = " + regresor.getInterseccion());
                    lbDerviacion.setText("Sy = " + regresor.getDesviacionEstandar());
                    lbErrorEstandar.setText("Sy/x = " + regresor.getErrorEstandar());
                    lbCoeficienteCor.setText("r = " + regresor.getCoeficienteCorrelacion());
                    lbCoeficienteDet.setText("r2 = " + regresor.getCoeficienteDeterminacion());
                    btPronosticar.setDisable(false);
                    dataInput = new CoordinatePair[n];
                    for (int i = 0; i < n; i++) {
                        dataInput[i] = new CoordinatePair(x[i], y[i]);
                    }
                    Graficar(dataInput, regresor.getInterseccion(), regresor.getPendiente());
                } else {
                    Graficos.lanzarMensajeError(Mensajes.E_CONVERSION, Mensajes.ERROR_CONVERSION);
                }
            } else {
                Graficos.lanzarMensajeError(Mensajes.E_VALIDACION, Mensajes.ERROR_VALIDACION_CORD_DISTINTAS);
            }
        });

        btPronosticar.setOnAction(event -> {
            if (regresor != null) {
                Double in = Graficos.validarTextFieldDouble(tfInput);
                if (in != null) {
                    tfOutput.setText("" + regresor.pronosticar(in));
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
            lbPendiente.setText("");
            lbInterseccion.setText("");
            lbDerviacion.setText("");
            lbErrorEstandar.setText("");
            lbCoeficienteCor.setText("");
            lbCoeficienteDet.setText("");
            regresor = null;
            tfOutput.clear();
            tfInput.clear();
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
            Graficos.lanzarMensajeAdvertencia(Mensajes.A_MAX_PUNTOS,Mensajes.ADVERENCIA_MAX_PUNTOS+ NUM_MAXIMO_COORDS);
        }
    }

    private void Graficar(CoordinatePair[] dataInput, double a0, double a1) {
        try {
            graphManager.getGraph().getData().clear();
            ArrayList<CoordinatePair[]> dataset = new ArrayList<>();
            dataset.add(dataInput);
            dataset.add(Matematico.evaluarRegresionLineal(a0, a1, xl, xr));
            Graficos.plotPuntosLineas(dataset, bpChart, graphManager);
        } catch (Exception e) {
            Graficos.lanzarMensajeError(Mensajes.E_GRAFICA, Mensajes.ERROR_GRAFICA);
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
                        Graficos.lanzarMensajeError(Mensajes.E_VALIDACION,
                                Mensajes.ERROR_VALIDACION_EJE_Y+Graficos.RANGO_GRAFICACION_MAX);
                    }
                } else {
                    Graficos.lanzarMensajeError(Mensajes.E_VALIDACION,
                            Mensajes.ERROR_VALIDACION_EJE_X+Graficos.RANGO_GRAFICACION_MAX);
                }
            } else {
                Graficos.lanzarMensajeAdvertencia(Mensajes.A_INTERVALOS,Mensajes.ADVERTENCIA_INTERVALOS);
            }
        } else {
            Graficos.lanzarMensajeError(Mensajes.ERROR_CONVERSION,Mensajes.ERROR_CONVERSION_CONTROL_EJES);
        }
        return res;
    }
}
