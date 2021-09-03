package Controller.SerieTaylor;

import Modelos.SerieDeTaylor.SerieTaylor;
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
 * FXML Controller class de la vista Sertie de Taylor
 *
 * @author Paulo Aguilar
 */
public class VistaSerieTaylorController implements Initializable {

    @FXML
    private TextField tfx0;
    @FXML
    private TextField tfFormula;
    @FXML
    private TextField tfX1;
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
    private BorderPane bpChart;
    @FXML
    private Label lbResultado,lbH;

    @FXML
    private TextField tfYU, tfYD, tfXL, tfXR;
    @FXML
    private JFXButton btAjustar;
    private GraphManager graphManager;
    private double yu = 30, yd = -30, xl = -30, xr = 30;
    private final double DEFAULT_AXIS_VALUES = 30;
    private String funcion;
    private Double punto = null, x1 = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

        Graficos.convertirEnInputFlotantes(tfX1);
        Graficos.convertirEnInputFlotantes(tfx0);
        Graficos.convertirEnInputFlotantes(tfErrorTolerancia);
        Graficos.convertirEnInputEnteros(tfIterMax);

        btProcesar.setOnMouseClicked(event -> {
            funcion = tfFormula.getText();
            SerieTaylor taylor;
            boolean error = false;
            if (Matematico.validarExpresion(funcion)) {
                x1 = Graficos.validarTextFieldDouble(tfX1);
                Double x0 = Graficos.validarTextFieldDouble(tfx0);
                Double eTolerancia = Graficos.validarTextFieldDouble(tfErrorTolerancia);
                Integer imax = Graficos.validarTextFieldEnteros(tfIterMax);
                if (x1 != null && x0 != null && eTolerancia != null && imax != null) {
                    try {
                        taylor = new SerieTaylor(funcion, eTolerancia, imax, x1, 2);
                        taylor.setValorInicial(x0);
                        punto = taylor.SerieTaylor();
                        lbH.setText(""+(x1-x0));
                        lbResultado.setText("" + punto);
                        String[] headers = {"f(x) Aprox.", "Error verdadero", "Error de aproximación"};
                        Graficos.cargarEnTableView(tvResultados, taylor.getMatrizDeDatos(), headers);
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
                    Graficos.lanzarMensajeError("Error de conversión", "Por favor, verifica el ingreso de datos antes de proceder.");
                }
            } else {
                Graficos.lanzarMensajeError("Error de conversión", "Hubo un error al interpretar la fórmula ingresada.");
            }
        });
        btLimpiar.setOnMouseClicked(e -> {
            lbResultado.setText("");
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
            tfErrorTolerancia.setText("");
            tfIterMax.setText("");
            tfx0.setText("");
            lbH.setText("");
            tfX1.setText("");
            tvResultados.getItems().clear();
        });
    }

    private void Graficar() {
        try {
            graphManager.getGraph().getData().clear();
            CoordinatePair puntoCords1 = new CoordinatePair(x1,
                    Matematico.evaluarFuncion(funcion, x1));
            CoordinatePair puntoCords2 = new CoordinatePair(x1,punto);
            CoordinatePair[] set2 = {puntoCords1,puntoCords2};
            
            ArrayList<CoordinatePair[]> dataset = new ArrayList<>();
            dataset.add(set2);
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
        boolean res = true;
        Double xl = Graficos.validarTextFieldDouble(tfXL);
        Double xr = Graficos.validarTextFieldDouble(tfXR);
        Double yu = Graficos.validarTextFieldDouble(tfYU);
        Double yd = Graficos.validarTextFieldDouble(tfYD);
        if (xl != null && xr != null && yu != null && yd != null) {
            if (xl < xr && yu > yd) {
                this.xl = xl;
                this.xr = xr;
                this.yu = yu;
                this.yd = yd;
                graphManager.setDomain(xl, xr);
                graphManager.setRange(yd, yu);
            } else {
                Graficos.lanzarMensajeAdvertencia("Verifique los intervalos.",
                        "Verifique que el rango y el dominio. El intervalo debe ir de menor a mayor.");
                res = false;
            }
        } else {
            Graficos.lanzarMensajeError("Error de conversión.",
                    "Verifique los valores ingresados en los campos de control de gráfica.");
            res = false;
        }
        return res;
    }
}
