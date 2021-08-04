package Controller;

import Modelos.MetodosAbiertos.Muller;
import Modelos.SerieTaylor;
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
 * FXML Controller class de la vista Sertie de Taylor
 *
 * @author Paulo Aguilar
 */
public class VistaSerieTaylorController implements Initializable {

    @FXML
    private TextField tfH;
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Se agrega la validación de los inputs
        Graficos.convertirEnInputFlotantes(tfX1);
        Graficos.convertirEnInputFlotantes(tfH);
        Graficos.convertirEnInputFlotantes(tfErrorTolerancia);
        Graficos.convertirEnInputEnteros(tfIterMax);
        
        btProcesar.setOnMouseClicked(event -> {
            String funcion = tfFormula.getText();
            SerieTaylor taylor;
            boolean error = false;
            if (MetodosUniversales.validarExpresion(funcion)) {
                Double x1 = Graficos.validarTextFieldDouble(tfX1);
                Double h = Graficos.validarTextFieldDouble(tfH);
                Double eTolerancia = Graficos.validarTextFieldDouble(tfErrorTolerancia);
                Integer imax = Graficos.validarTextFieldEnteros(tfIterMax);
                if (x1 != null && h != null && eTolerancia != null && imax != null) {
                    try {
                        taylor = new SerieTaylor(funcion, eTolerancia, imax, x1, h);
                        taylor.SerieTaylor();
                        String[] headers = {"xAprox", "Error verdadero", "Error de aproximación"};
                        Graficos.cargarEnTableView(tvResultados, taylor.getMatrizDeDatos(), headers);
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
            tfH.setText("");
            tfX1.setText("");
            tvResultados.getItems().clear();
        });
    }    
}