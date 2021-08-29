package Controller.IntegracionNewtonCotes;

import Controller.MetodosAproxRaices.*;
import Modelos.IntegracionNewtonCotes.Simpson38;
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
    private double yu = 50, yd = -50, xl = -50, xr = 50;
    private final double DEFAULT_AXIS_VALUES = 50;
    private String funcion;
    private Double punto = null;
    
    
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
                    simpson38 = new Simpson38(funcion, x0, x1);
                    try {
                        punto = simpson38.Simpson38Simple();
                        lbResultado.setText(""+punto);                       
                    } catch (Exception ex) {
                        error = true;
                        Graficos.lanzarMensajeError("Error de procesamiento", "Tuvimos un inconveniente al "
                                + "interpretar o procesar la función "
                                + "a travéz de este método.");
                    }
                    if (!error) {
                      Graficar();
                        
                    }    
                }else{
                    Graficos.lanzarMensajeError("Error de conversión","Por favor, verifica el ingreso de datos antes de proceder.");
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
      private void Graficar() {
        try {
            CoordinatePair puntoCords = new CoordinatePair(punto,
                    Matematico.evaluarFuncion(funcion, punto));

            ArrayList<CoordinatePair[]> dataset = new ArrayList<>();
            dataset.add(Matematico.evaluarFuncion(funcion, xl, xr));
            Graficos.plotPoints(dataset, bpChart, graphManager, puntoCords);
        } catch (Exception e) {
            e.printStackTrace();
            Graficos.lanzarMensajeError("Error de Graficación", "Tuvimos un inconveniente al "
                    + "interpretar o procesar la función "
                    + "a travéz de este método, por tanto"
                    + "la gráfica no se pudo procesar.");
        }
    }
}
