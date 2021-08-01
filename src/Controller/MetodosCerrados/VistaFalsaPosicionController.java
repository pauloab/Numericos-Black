package Controller.MetodosCerrados;


import Vistas.Components.Math.FormulaFunctionButtons;
import Vistas.Components.Math.FormulaInput;
import Modelos.MetodosCerrados.FalsaPosicion;
import Util.Graficos;
import Util.MetodosUniversales;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


/**
 * FXML Controller class de la vista Falsa Posicion
 *
 * @author Geovanny Vega
 */
public class VistaFalsaPosicionController implements Initializable {

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
    private ScrollPane scrollPaneFormula;
    @FXML
    private Pane pFormulaButtons;
    @FXML
    private Button btProcesar;
    @FXML
    private Button btLimpiar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Se carga el componente de fórmulas
        FormulaInput formulaInput = new FormulaInput();
        scrollPaneFormula.setContent(formulaInput);
        FormulaFunctionButtons formulaButtons = new FormulaFunctionButtons(formulaInput);
        pFormulaButtons.getChildren().add(formulaButtons);
        
        // Se agrega la validación de los inputs
        Graficos.convertirEnInputFlotantes(tfcotainferior);
        Graficos.convertirEnInputFlotantes(tfcotasuperior);
        Graficos.convertirEnInputFlotantes(tfErrorTolerancia);
        Graficos.convertirEnInputEnteros(tfIterMax);
        
        btProcesar.setOnMouseClicked(event -> {
            String funcion = formulaInput.getFormula();
            FalsaPosicion falsaposicion;
            boolean error = false;
            if (MetodosUniversales.validarExpresion(funcion)) {
                Double cotainferior = Graficos.validarTextFieldDouble(tfcotainferior);
                Double cotasuperior = Graficos.validarTextFieldDouble(tfcotasuperior);
                Double eTolerancia = Graficos.validarTextFieldDouble(tfErrorTolerancia);
                Integer imax = Graficos.validarTextFieldEnteros(tfIterMax);
                if (cotainferior != null && cotasuperior != null && eTolerancia != null && imax != null) {
                    falsaposicion = new FalsaPosicion(funcion, eTolerancia, imax, cotainferior, cotasuperior);
                    try {
                        falsaposicion.metodoFalsaPosicion();
                        String[] headers = {"xl","xu", "xr","f(xl)","f(xu)","f(xr)", "Error de aproximación"};
                        Graficos.cargarEnTableView(tvResultados, falsaposicion.getMatrizDeDatos(), headers);
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
            formulaInput.clearFormula();
            tfErrorTolerancia.setText("");
            tfIterMax.setText("");
            tfcotainferior.setText("");
            tfcotasuperior.setText("");
            tvResultados.getItems().clear();
        });
    }    
}
