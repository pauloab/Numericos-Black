package Controller.AjusteCurvas;

import Controller.MetodosAbiertos.*;
import Modelos.AjusteDeCurvas.RegresionLineal;
import Modelos.Excepciones.IteracionesAlcanzadas;
import Modelos.Excepciones.ValoresResueltos;
import Modelos.MetodosAbiertos.Bairstow;
import Util.Graficos;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class de la vista Baistow
 *
 * @author Paulo Aguilar
 */
public class VistaRegresionLinealController implements Initializable {

    @FXML
    private Button btProcesar;
    @FXML
    private Button btLimpiar;
    @FXML
    private Button btAdd;
    @FXML
    private Button btRemove;
    @FXML
    private Button btPronosticar;
    @FXML
    private VBox vbXValues, vbYValues;
    @FXML
    private Label lbPendiente, lbInterseccion, lbDerviacion,lbErrorEstandar,
            lbCoeficienteDet,lbCoeficienteCor;
    @FXML
    private TextField tfInput, tfOutput;
    
    private RegresionLineal regresor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        addCordFilds();
        addCordFilds();

        // Se agrega un elemnto al vector de coeficientes
        btAdd.setOnMouseClicked(event -> {
            addCordFilds();
        });

        btProcesar.setOnMouseClicked(event -> {
            boolean error = false;
            int n = vbXValues.getChildren().size();
            double[] x = new double[n];
            double[] y = new double[n];
            Double xTemp, yTemp;
            for (int i = 0; i < n; i++) {
                TextField xField = (TextField) vbXValues.getChildren().get(i);
                TextField yField = (TextField) vbYValues.getChildren().get(i);
                xTemp = Graficos.validarTextFieldDouble(xField);
                yTemp = Graficos.validarTextFieldDouble(yField);
                if (xTemp != null && yTemp != null) {
                    x[i] = xTemp;
                    y[i] = yTemp;
                } else {
                    error = true;
                }
            }

            if (error == false) {
                regresor = new RegresionLineal(x, y);
                regresor.regresionLineal();
                lbPendiente.setText("a1 = "+regresor.getPendiente());
                lbInterseccion.setText("a0 = "+regresor.getInterseccion());
                lbDerviacion.setText("Sy = "+regresor.getDesviacionEstandar());
                lbErrorEstandar.setText("Sy/x = "+regresor.getErrorEstandar());
                lbCoeficienteCor.setText("r2 = "+regresor.getCoeficienteCorrelacion());
                lbCoeficienteDet.setText("r = "+regresor.getCoeficienteDeterminacion());
                btPronosticar.setDisable(false);
                try {
                    // Aquí se debe cargar la gráfica
                } catch (Exception e) {
                    Graficos.lanzarMensajeError("Error de Graficación", "Tuvimos un inconveniente al "
                            + "interpretar o procesar la función "
                            + "a travéz de este método, por tanto"
                            + "la gráfica no se pudo procesar.");
                }
            } else {
                Graficos.lanzarMensajeError("Error de conversión", "Por favor, verifica el ingreso de datos antes de proceder.");
            }
        });

        btPronosticar.setOnAction(event -> {
            if (regresor != null) {
                Double in = Graficos.validarTextFieldDouble(tfInput);
                if (in != null) {
                    tfOutput.setText("" + regresor.pronosticar(in));
                }
            }
        });

        btRemove.setOnMouseClicked(e -> {
            if (vbXValues.getChildren().size() > 2) {
                vbYValues.getChildren().remove(vbYValues.getChildren().size() - 1);
                vbXValues.getChildren().remove(vbXValues.getChildren().size() - 1);
            }
            if (vbXValues.getChildren().size() == 2) {
                btPronosticar.setDisable(true);
            } else {
                btPronosticar.setDisable(false);
            }
        });

        btLimpiar.setOnMouseClicked(e -> {
            lbPendiente.setText("");
            lbInterseccion.setText("");
            lbDerviacion.setText("");
            lbErrorEstandar.setText("");
            lbCoeficienteCor.setText("");
            lbCoeficienteDet.setText("");
            regresor = null;
            tfOutput.clear();
            tfInput.clear();
            btPronosticar.setDisable(true);
            for (int i = vbXValues.getChildren().size() - 1; i >= 0; i--) {
                if (i > 1) {
                    vbYValues.getChildren().remove(vbYValues.getChildren().size() - 1);
                    vbXValues.getChildren().remove(vbXValues.getChildren().size() - 1);
                } else {
                    ((TextField) vbXValues.getChildren().get(i)).clear();
                    ((TextField) vbYValues.getChildren().get(i)).clear();
                }
            }
        });
    }

    private void addCordFilds() {
        TextField tf = new TextField();
        Graficos.convertirEnInputFlotantes(tf);
        vbXValues.getChildren().add(tf);
        tf = new TextField();
        Graficos.convertirEnInputFlotantes(tf);
        vbYValues.getChildren().add(tf);
    }
}
