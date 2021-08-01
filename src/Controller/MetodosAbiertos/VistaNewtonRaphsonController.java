package Controller.MetodosAbiertos;

import Vistas.Components.Math.FormulaFunctionButtons;
import Vistas.Components.Math.FormulaInput;
import Modelos.MetodosAbiertos.NewtonRaphson;
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
 * FXML Controller class de la vista NewtonRaphson
 *
 * @author Geovanny Vega
 */
public class VistaNewtonRaphsonController implements Initializable {

    @FXML
    private TextField tfx0;
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
        Graficos.convertirEnInputFlotantes(tfx0);
        Graficos.convertirEnInputFlotantes(tfErrorTolerancia);
        Graficos.convertirEnInputEnteros(tfIterMax);
        
        btProcesar.setOnMouseClicked(event -> {
            String funcion = formulaInput.getFormula();
            NewtonRaphson newtonraphson;
            boolean error = false;
            if (MetodosUniversales.validarExpresion(funcion)) {
                Double x0 = Graficos.validarTextFieldDouble(tfx0);
                Double eTolerancia = Graficos.validarTextFieldDouble(tfErrorTolerancia);
                Integer imax = Graficos.validarTextFieldEnteros(tfIterMax);
                if (x0 != null && eTolerancia != null && imax != null) {
                    newtonraphson = new NewtonRaphson(funcion, eTolerancia, imax, x0);
                    try {
                        newtonraphson.metodoNewtonRaphson();
                        String[] headers = {"xi+1","f(xi)", "f'(xi)", "Error de aproximación"};
                        Graficos.cargarEnTableView(tvResultados, newtonraphson.getMatrizDeDatos(), headers);
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
            tfx0.setText("");
            tvResultados.getItems().clear();
        });
    }    
}
