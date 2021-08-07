package Controller.MetodosAproxRaices;

import Modelos.MetodosAproxRaices.Muller;
import Util.Graficos;
import Util.MetodosUniversales;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


/**
 * FXML Controller class de la vista Muller
 *
 * @author Paulo Aguilar
 */
public class VistaMullerController implements Initializable {

    @FXML
    private TextField tfFormula;
    @FXML
    private TextField tfx0;
    @FXML
    private TextField tfx1;
    @FXML
    private TextField tfx2;
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        // Se agrega la validación de los inputs
        Graficos.convertirEnInputFlotantes(tfx0);
        Graficos.convertirEnInputFlotantes(tfx1);
        Graficos.convertirEnInputFlotantes(tfx2);
        Graficos.convertirEnInputFlotantes(tfErrorTolerancia);
        Graficos.convertirEnInputEnteros(tfIterMax);
        
        btProcesar.setOnMouseClicked(event -> {
            String funcion = tfFormula.getText();
            Muller muller;
            boolean error = false;
            if (MetodosUniversales.validarExpresion(funcion)) {
                Double x0 = Graficos.validarTextFieldDouble(tfx0);
                Double x1 = Graficos.validarTextFieldDouble(tfx1);
                Double x2 = Graficos.validarTextFieldDouble(tfx2);
                Double eTolerancia = Graficos.validarTextFieldDouble(tfErrorTolerancia);
                Integer imax = Graficos.validarTextFieldEnteros(tfIterMax);
                if (x0 != null && x1 != null && x2 != null && eTolerancia != null && imax != null) {
                    muller = new Muller(funcion, eTolerancia, imax, x0, x1, x2);
                    try {
                        muller.metodoMuller();
                        String[] headers = {"x0", "x1", "x2", "xr", "Error de aproximación"};
                        Graficos.cargarEnTableView(tvResultados, muller.getMatrizDeDatos(), headers);
                    } catch (Exception ex) {
                        error = true;
                        Graficos.lanzarMensajeError("Error de procesamiento", "Tuvimos un inconveniente al "
                                + "interpretar o procesar la función "
                                + "a travéz de este método.");
                    }
                    if (!error) {
                        try {
                           // Aquí se debe cargar la gráfica
                        } catch (Exception e) {
                            Graficos.lanzarMensajeError("Error de Graficación", "Tuvimos un inconveniente al "
                                    + "interpretar o procesar la función "
                                    + "a travéz de este método, por tanto"
                                    + "la gráfica no se pudo procesar.");
                        }
                    }    
                }else{
                    Graficos.lanzarMensajeError("Error de conversión","Por favor, verifica el ingreso de datos antes de proceder.");
                }
            } else {
                Graficos.lanzarMensajeError("Error de conversión", "Hubo un error al interpretar la fórmula ingresada.");
            }
        });
        btLimpiar.setOnMouseClicked(e -> {
            tfFormula.clear();
            tfErrorTolerancia.setText("");
            tfIterMax.setText("");
            tfx0.setText("");
            tfx1.setText("");
            tfx2.setText("");
            tvResultados.getItems().clear();
        });
    }    
}
