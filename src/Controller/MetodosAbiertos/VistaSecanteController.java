package Controller.MetodosAbiertos;

import Vistas.Components.Math.FormulaFunctionButtons;
import Vistas.Components.Math.FormulaInput;
import Modelos.MetodosAbiertos.Secante;
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
 * FXML Controller class de la vista Secante
 *
 * @author Geovanny Vega
 */
public class VistaSecanteController implements Initializable {

    @FXML
    private TextField tfxi;
    @FXML
    private TextField tfximenos1;
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
        Graficos.convertirEnInputFlotantes(tfxi);
        Graficos.convertirEnInputFlotantes(tfximenos1);
        Graficos.convertirEnInputFlotantes(tfErrorTolerancia);
        Graficos.convertirEnInputEnteros(tfIterMax);
        
        btProcesar.setOnMouseClicked(event -> {
            String funcion = formulaInput.getFormula();
            Secante secante;
            boolean error = false;
            if (MetodosUniversales.validarExpresion(funcion)) {
                Double xi = Graficos.validarTextFieldDouble(tfxi);
                Double ximenos1 = Graficos.validarTextFieldDouble(tfximenos1);
                Double eTolerancia = Graficos.validarTextFieldDouble(tfErrorTolerancia);
                Integer imax = Graficos.validarTextFieldEnteros(tfIterMax);
                if (xi != null && ximenos1 != null && eTolerancia != null && imax != null) {
                    secante = new Secante(funcion, eTolerancia, imax, xi, ximenos1);
                    try {
                        secante.metodoSecante();
                        String[] headers = {"xi+1","fxi-1", "fxi", "Error de aproximación"};
                        Graficos.cargarEnTableView(tvResultados, secante.getMatrizDeDatos(), headers);
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
            tfxi.setText("");
            tfximenos1.setText("");
            tvResultados.getItems().clear();
        });
    }    
}
