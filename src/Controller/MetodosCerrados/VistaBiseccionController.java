package Controller.MetodosCerrados;



import Modelos.MetodosCerrados.Biseccion;
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
 * FXML Controller class de la vista Biseccion
 *
 * @author Geovanny Vega
 */
public class VistaBiseccionController implements Initializable {

    @FXML
    private TextField tfFormula;
    @FXML
    private TextField tfcotainferior;
    @FXML
    private TextField tfcotasuperior;
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
        Graficos.convertirEnInputFlotantes(tfcotainferior);
        Graficos.convertirEnInputFlotantes(tfcotasuperior);
        Graficos.convertirEnInputFlotantes(tfErrorTolerancia);
        Graficos.convertirEnInputEnteros(tfIterMax);
        
        btProcesar.setOnMouseClicked(event -> {
            String funcion = tfFormula.getText();
            Biseccion biseccion;
            boolean error = false;
            if (MetodosUniversales.validarExpresion(funcion)) {
                Double cotainferior = Graficos.validarTextFieldDouble(tfcotainferior);
                Double cotasuperior = Graficos.validarTextFieldDouble(tfcotasuperior);
                Double eTolerancia = Graficos.validarTextFieldDouble(tfErrorTolerancia);
                Integer imax = Graficos.validarTextFieldEnteros(tfIterMax);
                if (cotainferior != null && cotasuperior != null && eTolerancia != null && imax != null) {
                    biseccion = new Biseccion(funcion, eTolerancia, imax, cotainferior, cotasuperior);
                    try {
                        biseccion.metodoBiseccion();
                        String[] headers = {"xl","xu", "xr","f(xl)","f(xu)","f(xr)", "Error de aproximación"};
                        Graficos.cargarEnTableView(tvResultados, biseccion.getMatrizDeDatos(), headers);
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
            tfcotainferior.setText("");
            tfcotasuperior.setText("");
            tvResultados.getItems().clear();
        });
    }    
}
