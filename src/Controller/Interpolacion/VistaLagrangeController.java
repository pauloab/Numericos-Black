package Controller.Interpolacion;

import Modelos.Interpolacion.InterpolacionLagrange;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class de la vista de interpolación de Lagrange
 *
 * @author Paulo Aguilar
 */
public class VistaLagrangeController implements Initializable {

    @FXML
    private Button btProcesar;
    @FXML
    private Button btLimpiar;
    @FXML
    private Button btAdd;
    @FXML
    private Button btRemove;
    @FXML
    private VBox vbXValues, vbYValues;
    @FXML
    private Label lbResultado, lbGrado;
    @FXML
    private TextArea taFuncion;
    @FXML
    private TextField tfX;
    @FXML
    private TextField tfYU, tfYD, tfXL, tfXR;
    @FXML
    private JFXButton btAjustar;
    @FXML
    private BorderPane bpChart;

    private GraphManager graphManager;
    private double yu = 30, yd = -30, xl = -30, xr = 30;
    private final double DEFAULT_AXIS_VALUES = 30;
    private final int MAX_PUNTOS = 11;

    private InterpolacionLagrange interpolador;
    private CoordinatePair[] dataInput;
    private String funcion;

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
            if (interpolador != null && limitesDefinidos) {
                Graficar(dataInput);
            }
        });

        Graficos.convertirEnInputFlotantes(tfXL);
        Graficos.convertirEnInputFlotantes(tfXR);
        Graficos.convertirEnInputFlotantes(tfYU);
        Graficos.convertirEnInputFlotantes(tfYD);
        Graficos.convertirEnInputFlotantes(tfX);
        addCordFilds();
        addCordFilds();

        // Se agrega un elemnto al vector de coeficientes
        btAdd.setOnMouseClicked(event -> {
            if (vbXValues.getChildren().size() == MAX_PUNTOS) {
                Graficos.lanzarMensajeAdvertencia("Máximo de puntos",
                        "Ha alcanzado el número máximo de puntos a agregar.");
            } else {
                addCordFilds();
            }
        });

        btProcesar.setOnMouseClicked(event -> {
            boolean error = false;
            int n = vbXValues.getChildren().size();
            double[] x = new double[n];
            double[] y = new double[n];
            Double xTemp, yTemp, xIn;
            double res;
            xIn = Graficos.validarTextFieldDouble(tfX);
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
                if (error == false && xIn != null) {
                    try {
                        interpolador = new InterpolacionLagrange(x, y, xIn);
                    } catch (Exception ex) {
                        error = true;
                        Graficos.lanzarMensajeError("Error de validación",
                                "Verifique que x se encuentre en el dominio de los datos");
                    }
                    if (!error) {
                        res = interpolador.interpolacion();
                        lbResultado.setText("" + res);
                        funcion = interpolador.getFuncion();
                        taFuncion.setText("f" + (n - 1) + "(x) = " + funcion);
                        dataInput = new CoordinatePair[n];
                        for (int i = 0; i < n; i++) {
                            dataInput[i] = new CoordinatePair(x[i], y[i]);
                        }
                        Graficar(dataInput);
                    }
                } else {
                    Graficos.lanzarMensajeError("Error de conversión", "Por favor, verifica el ingreso de datos antes de proceder.");
                }
            } else {
                Graficos.lanzarMensajeError("Error de validación", "Verifique que los puntos de x sean en las coordenadas distintos.");
            }

        });

        btRemove.setOnMouseClicked(e -> {
            if (vbXValues.getChildren().size() > 2) {
                vbYValues.getChildren().remove(vbYValues.getChildren().size() - 1);
                vbXValues.getChildren().remove(vbXValues.getChildren().size() - 1);
                lbGrado.setText("" + (vbXValues.getChildren().size() - 1));
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
            lbResultado.setText("");
            taFuncion.setText("");
            interpolador = null;
            tfX.clear();
            for (int i = vbXValues.getChildren().size() - 1; i >= 0; i--) {
                if (i > 1) {
                    vbYValues.getChildren().remove(vbYValues.getChildren().size() - 1);
                    vbXValues.getChildren().remove(vbXValues.getChildren().size() - 1);
                } else {
                    ((TextField) vbXValues.getChildren().get(i)).clear();
                    ((TextField) vbYValues.getChildren().get(i)).clear();
                }
            }
            lbGrado.setText("" + (vbXValues.getChildren().size() - 1));

        });
    }

    /**
     * Añade un input de par de coordenadas en la vista.
     */
    private void addCordFilds() {
        TextField tf = new TextField();
        Graficos.convertirEnInputFlotantes(tf);
        vbXValues.getChildren().add(tf);
        tf = new TextField();
        Graficos.convertirEnInputFlotantes(tf);
        vbYValues.getChildren().add(tf);
        lbGrado.setText("" + (vbXValues.getChildren().size() - 1));
    }

    /**
     * Se encarga de procesar y gestionar los sets de datos para ser impresos en
     * la gráfica.
     *
     * @param dataInput Set de datos de entrada.
     */
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

    /**
     * Intenta definir los límites de la gráfica (top,left,right,bottom)
     *
     * @return true si cosiguió definir los límites correctamente
     */
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
}
