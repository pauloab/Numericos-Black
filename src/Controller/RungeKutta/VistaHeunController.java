package Controller.RungeKutta;
import Modelos.MetodosAproxRaices.Muller;
import Modelos.RungeKutta.Euler;
import Modelos.RungeKutta.Heun;
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
 * FXML Controller class de la vista Heun
 *
 * @author Javier Matamoros
 */
public class VistaHeunController implements Initializable {

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
    private TextField tfxitermax;
    @FXML
    private TextField tfxerrorTolerancia;
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
    //private Euler euler;
    private Heun heun;

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
                Integer iter = Graficos.validarTextFieldEnteros(tfxitermax);
                Double ea = Graficos.validarTextFieldDouble(tfxerrorTolerancia);
                if ((b-x0)%h!=0) {
                    error = true;
                    Graficos.lanzarMensajeError("Error de ingreso", "El tamaño de paso no permite "
                            + "dividir el intervalo en partes iguales.");
                }
                if (b<x0) {
                    error = true;
                    Graficos.lanzarMensajeError("Error de ingreso", "b debe der menor que la cota inferior (x0).");
                }
                if (x0 != null && x1 != null && b != null && h != null && !error && iter != null && ea != null) {
                    heun = new Heun(x0, x1,h,funcion,b,iter,ea);
                    try {
                        heun.heun();
                        String[] headers = {"Xi", "F(Xi, Yi)", "Y^0i+1","f(Xi+1,Y^0i+1)" ,"Yi+1","Ea"};
                        Graficos.cargarEnTableViewSelectivo(tvResultados, heun.getDatos(), headers);
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
            tfxitermax.setText("");
            tfxerrorTolerancia.setText("");
            tvResultados.getItems().clear();
        });
    }

    private void Graficar() {
        try {
            String[][] datos = heun.getDatos();
            CoordinatePair[] puntos = new CoordinatePair[datos.length];
            for (int i = 0; i < datos.length;i++) {
                if(datos[i][0] != null){
                 puntos[i] = new CoordinatePair(Double.parseDouble(datos[i][0]), Double.parseDouble(datos[i][4]));   
                }
            }
            ArrayList<CoordinatePair[]> dataset = new ArrayList<>();
            dataset.add(puntos);
            Graficos.plotBairstow(dataset, bpChart, graphManager);
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
