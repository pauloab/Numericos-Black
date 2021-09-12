package Controller.MetodosAproxRaices;

import Modelos.MetodosAproxRaices.PuntoFijo;
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
import javafx.scene.layout.Pane;

/**
 * FXML Controller class de la vista Punto Fijo
 *
 * @author Freddy Lamar
 */
public class VistaPuntoFijoController implements Initializable {

    @FXML
    private TextField tfx0;
    @FXML
    private TextField tfFormula;
    @FXML
    private TextField tfErrorTolerancia;
    @FXML
    private TextField tfIterMax;
    @FXML
    private TableView tvResultados;
    @FXML
    private Pane pFormula;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tvResultados.setPlaceholder(new Label(""));
        graphManager = new GraphManager();
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
        Graficos.convertirEnInputFlotantes(tfErrorTolerancia);
        Graficos.convertirEnInputEnteros(tfIterMax);

        btProcesar.setOnMouseClicked(event -> {
            funcion = tfFormula.getText();
            PuntoFijo puntofijo;
            boolean error = false;
            if (Matematico.validarExpresion(funcion)) {
                Double x0 = Graficos.validarTextFieldDouble(tfx0);
                Double eTolerancia = Graficos.validarTextFieldDouble(tfErrorTolerancia);
                Integer imax = Graficos.validarTextFieldEnteros(tfIterMax);
                if (x0 != null && eTolerancia != null && imax != null) {
                    puntofijo = new PuntoFijo(funcion, eTolerancia, imax, x0);
                    try {
                        punto = puntofijo.metodoPuntoFijo();
                        lbResultado.setText("" + punto);
                        String[] headers = {"xi+1", "g(x)", "Error de aproximación"};
                        Graficos.cargarEnTableView(tvResultados, puntofijo.getMatrizDeDatos(), headers);
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
            tfErrorTolerancia.setText("");
            tfIterMax.setText("");
            tfx0.setText("");
            tvResultados.getItems().clear();
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

    private void Graficar() {
        try {
            CoordinatePair puntoCords = new CoordinatePair(punto,
                    Matematico.evaluarFuncion(funcion, punto));

            ArrayList<CoordinatePair[]> dataset = new ArrayList<>();
            dataset.add(Matematico.evaluarFuncion(funcion, xl, xr));
            Graficos.plotPoints(dataset, bpChart, graphManager, puntoCords);
        } catch (Exception e) {
            Graficos.lanzarMensajeError(Mensajes.E_GRAFICA, Mensajes.ERROR_GRAFICA);
        }
    }
}
