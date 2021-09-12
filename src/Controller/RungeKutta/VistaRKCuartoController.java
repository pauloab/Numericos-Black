package Controller.RungeKutta;

import Modelos.RungeKutta.RKCuartoOrden;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class de la vista Euler
 *
 * @author Paulo Aguilar
 */
public class VistaRKCuartoController implements Initializable {

    @FXML
    private TextField tfFormula;
    @FXML
    private TextField tfx0;
    @FXML
    private TextField tfY0;
    @FXML
    private TextField tfB;
    @FXML
    private TextField tfH;
    @FXML
    private TableView tvResultados;
    @FXML
    private Button btProcesar;
    @FXML
    private Button btLimpiar;
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
    private RKCuartoOrden rK4;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        graphManager = new GraphManager();
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

        Graficos.convertirEnInputFlotantes(tfx0);
        Graficos.convertirEnInputFlotantes(tfY0);
        Graficos.convertirEnInputFlotantes(tfB);
        Graficos.convertirEnInputFlotantes(tfH);

        btProcesar.setOnMouseClicked(event -> {
            funcion = tfFormula.getText();
            boolean error = false;
            if (Matematico.validarExpresionDosVariables(funcion)) {
                Double x0 = Graficos.validarTextFieldDouble(tfx0);
                Double x1 = Graficos.validarTextFieldDouble(tfY0);
                Double b = Graficos.validarTextFieldDouble(tfB);
                Double h = Graficos.validarTextFieldDouble(tfH);
                if ((b - x0) % h != 0) {
                    error = true;
                    Graficos.lanzarMensajeError(Mensajes.E_VALIDACION, Mensajes.ERROR_VALIDACION_TAMANO_PASO);
                }
                if (b < x0) {
                    error = true;
                    Graficos.lanzarMensajeError(Mensajes.E_VALIDACION, Mensajes.EROR_VALIDACION_B_X0);
                }
                if (x0 != null && x1 != null && b != null && h != null && !error) {
                    rK4 = new RKCuartoOrden(x0, x1, b, funcion, h);
                    try {
                        rK4.RKCuarto();
                        String[] headers = {"x", "y", "K1", "K2", "K3", "K4"};
                        Graficos.cargarEnTableView(tvResultados, rK4.getDatos(), headers);
                    } catch (Exception ex) {
                        error = true;
                        Graficos.lanzarMensajeError(Mensajes.E_PROCESAMIENTO, Mensajes.ERROR_PROCESAMIENTO_METODO);
                    }
                    if (!error) {
                        Graficar();
                    }
                } else {
                    Graficos.lanzarMensajeError(Mensajes.E_CONVERSION, Mensajes.ERROR_CONVERSION);
                }
            } else {
                Graficos.lanzarMensajeError(Mensajes.E_CONVERSION, Mensajes.ERROR_CONVERSION_FORMULA);
            }
        });
        btLimpiar.setOnMouseClicked(e -> {
            graphManager.getGraph().getData().clear();
            funcion = null;
            graphManager.setDomain(-DEFAULT_AXIS_VALUES, DEFAULT_AXIS_VALUES);
            graphManager.setRange(-DEFAULT_AXIS_VALUES, DEFAULT_AXIS_VALUES);
            tfXL.setText("-" + DEFAULT_AXIS_VALUES);
            tfXR.setText("" + DEFAULT_AXIS_VALUES);
            tfYU.setText("" + DEFAULT_AXIS_VALUES);
            tfYD.setText("-" + DEFAULT_AXIS_VALUES);
            definirLimites();
            tfFormula.clear();
            tfH.setText("");
            tfB.setText("");
            tfx0.setText("");
            tfY0.setText("");
            tvResultados.getItems().clear();
        });
    }

    private void Graficar() {
        try {
            String[][] datos = rK4.getDatos();
            CoordinatePair[] puntos = new CoordinatePair[datos.length];
            for (int i = 0; i < datos.length; i++) {
                puntos[i] = new CoordinatePair(Double.parseDouble(datos[i][0]), Double.parseDouble(datos[i][1]));
            }
            ArrayList<CoordinatePair[]> dataset = new ArrayList<>();
            dataset.add(puntos);
            Graficos.plotBairstow(dataset, bpChart, graphManager);
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
                                Mensajes.ERROR_VALIDACION_EJE_Y + Graficos.RANGO_GRAFICACION_MAX);
                    }
                } else {
                    Graficos.lanzarMensajeError(Mensajes.E_VALIDACION,
                            Mensajes.ERROR_VALIDACION_EJE_X + Graficos.RANGO_GRAFICACION_MAX);
                }
            } else {
                Graficos.lanzarMensajeAdvertencia(Mensajes.A_INTERVALOS, Mensajes.ADVERTENCIA_INTERVALOS);
            }
        } else {
            Graficos.lanzarMensajeError(Mensajes.ERROR_CONVERSION, Mensajes.ERROR_CONVERSION_CONTROL_EJES);
        }
        return res;
    }
}
