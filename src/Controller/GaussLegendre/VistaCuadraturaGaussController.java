package Controller.GaussLegendre;

import Modelos.GaussLegendre.CuadraturaGauss;
import Modelos.MetodosAproxRaices.Muller;
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
 * FXML Controller class de la vista de cuadratura de Gauss
 *
 * @author Freddy Lamar
 */
public class VistaCuadraturaGaussController implements Initializable {

    @FXML
    private TextField tfFormula;
    @FXML
    private TextField tfA;
    @FXML
    private TextField tfB;
    @FXML
    private TextField tfN;
    @FXML
    private TableView tvResultados;
    @FXML
    private Pane pFormula;
    @FXML
    private Button btProcesar;
    @FXML
    private Button btLimpiar;
    @FXML
    private BorderPane bpChart;
    @FXML
    private Label lbResultado;

    @FXML
    private TextField tfYU, tfYD, tfXL, tfXR;
    @FXML
    private JFXButton btAjustar;
    private GraphManager graphManager;
    private double yu = 30, yd = -30, xl = -30, xr = 30;
    private final double DEFAULT_AXIS_VALUES = 30;
    private String funcion;
    private Double punto = null;
    private Double a, b;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        graphManager = new GraphManager(a,b,false);
        tvResultados.setPlaceholder(new Label(""));
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

        Graficos.convertirEnInputFlotantes(tfA);
        Graficos.convertirEnInputFlotantes(tfB);
        Graficos.convertirEnInputEnteros(tfN);

        btProcesar.setOnMouseClicked(event -> {
            funcion = tfFormula.getText();
            CuadraturaGauss cGauss;
            boolean error = false;
            if (Matematico.validarExpresion(funcion)) {
                a = Graficos.validarTextFieldDouble(tfA);
                b = Graficos.validarTextFieldDouble(tfB);
                Integer n = Graficos.validarTextFieldEnteros(tfN);
                if (a != null && b != null && n != null) {
                    if (n > 0 && n <= CuadraturaGauss.MAX_NUM_PUNTOS) {
                        if (b > a) {
                            cGauss = new CuadraturaGauss(funcion, a, b, n);
                            try {
                                punto = cGauss.cuadraturaDeGauss();
                                lbResultado.setText("" + punto);
                                String[] headers = {"Cn", "Xn"};
                                Graficos.cargarEnTableView(tvResultados, cGauss.getCoeficientes(), headers);
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
                            Graficos.lanzarMensajeError("Error de validación", "El valor de 'b' debe ser mayor a 'a'.");
                        }
                    } else {
                        Graficos.lanzarMensajeError("Error de validación", "El valor de n debe ser mayor a cero, y menor o igual a " + CuadraturaGauss.MAX_NUM_PUNTOS);
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
            funcion = null;
            lbResultado.setText("");
            graphManager.setDomain(-DEFAULT_AXIS_VALUES, DEFAULT_AXIS_VALUES);
            graphManager.setRange(-DEFAULT_AXIS_VALUES, DEFAULT_AXIS_VALUES);
            tfXL.setText("-" + DEFAULT_AXIS_VALUES);
            tfXR.setText("" + DEFAULT_AXIS_VALUES);
            tfYU.setText("" + DEFAULT_AXIS_VALUES);
            tfYD.setText("-" + DEFAULT_AXIS_VALUES);
            definirLimites();
            tfFormula.clear();
            tfA.setText("");
            tfB.setText("");
            tfN.setText("");
            tvResultados.getItems().clear();
        });
    }

    private void Graficar() {
        try {
            ArrayList<CoordinatePair[]> dataset = new ArrayList<>();
            graphManager.setX0(a);
            graphManager.setX1(b);
            dataset.add(Matematico.evaluarFuncion(funcion, xl, xr));
            dataset.add(Matematico.evaluarFuncion(funcion, a, b));
            Graficos.plotNoInterseciones(dataset, pFormula, graphManager);
        } catch (Exception e) {
            e.printStackTrace();
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
