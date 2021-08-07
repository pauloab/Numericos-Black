package Controller.MetodosAproxRaices;

import Modelos.Excepciones.IteracionesAlcanzadas;
import Modelos.Excepciones.ValoresResueltos;
import Modelos.MetodosAproxRaices.Bairstow;
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
 * @author Geovanny Vega
 */
public class VistaBairstowController implements Initializable {

    @FXML
    private TextField tfr;
    @FXML
    private TextField tfs;
    @FXML
    private TextField tfa0;
    @FXML
    private TextField tfa1;
    @FXML
    private TextField tfErrorTolerancia;
    @FXML
    private TextField tfIterMax;
    @FXML
    private TableView tvResultados;
    @FXML
    private Button btProcesar;
    @FXML
    private Button btLimpiar;
    @FXML
    private Button btAddCoeficiente;
    @FXML
    private Button btRemoveCoeficiente;
    @FXML
    private VBox vBoxCoeficientes;

    private int grado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        grado = 1;

        // Se agrega la validación de los inputs
        Graficos.convertirEnInputFlotantes(tfr);
        Graficos.convertirEnInputFlotantes(tfs);
        Graficos.convertirEnInputFlotantes(tfa0);
        Graficos.convertirEnInputFlotantes(tfa1);
        Graficos.convertirEnInputFlotantes(tfErrorTolerancia);
        Graficos.convertirEnInputEnteros(tfIterMax);

        // Se agrega un elemnto al vector de coeficientes
        btAddCoeficiente.setOnMouseClicked(event -> {
            HBox hBox = new HBox(35);
            hBox.setAlignment(Pos.CENTER);
            hBox.setPrefWidth(198);
            hBox.setPrefHeight(42);
            Label label = new Label();
            TextField tf = new TextField();
            tf.setPrefWidth(91);
            tf.setPrefHeight(26);
            label.setText("a" + (grado + 1));
            hBox.getChildren().addAll(label, tf);
            vBoxCoeficientes.getChildren().add(0, hBox);
            grado++;
            Graficos.convertirEnInputFlotantes(tf);

        });

        btProcesar.setOnMouseClicked(event -> {
            Bairstow bairstow;
            int cont;
            boolean error = false;
            Double r = Graficos.validarTextFieldDouble(tfr);
            Double s = Graficos.validarTextFieldDouble(tfs);
            Double eTolerancia = Graficos.validarTextFieldDouble(tfErrorTolerancia);
            Integer imax = Graficos.validarTextFieldEnteros(tfIterMax);

            double[] a = new double[grado + 1];
            cont = grado;
            for (Node element : vBoxCoeficientes.getChildren()) {
                if (element instanceof HBox) {

                    HBox hBoxelement = (HBox) element;
                    if (hBoxelement.getChildren().get(1) instanceof TextField) {
                        TextField tfelement = (TextField) hBoxelement.getChildren().get(1);
                        Double coeficiente = Graficos.validarTextFieldDouble(tfelement);
                        if (coeficiente == null) {
                            error = true;
                        } else {
                            a[cont--] = coeficiente;
                        }
                    }

                }

            }

            if (r != null && s != null && eTolerancia != null && imax != null && error == false) {
                bairstow = new Bairstow("", eTolerancia, imax, r, s, grado, a);
                try {
                    int g = grado - 1;
                    String[][] matriz = new String[g + 1][2];
                    String[] res = null;
                    do {
                        try {
                            if (res != null) {
                                matriz[g][0] = "" + (g + 1);
                                matriz[g][1] = res[0];
                                if (g != 0) {
                                    matriz[g - 1][0] = "" + (g);
                                    matriz[g - 1][1] = res[1];
                                }
                                g = g - 2;

                            }
                            res = bairstow.bairstowN();
                        } catch (ValoresResueltos ex) {
                            break;
                        }
                    } while (true);
                    String[] headers = {"Raíz", "Valor"};
                    Graficos.cargarEnTableViewBairstow(tvResultados, matriz, headers);
                } catch (IteracionesAlcanzadas ex) {
                    error = true;
                    Graficos.lanzarMensajeError("Error de convergncia", "Número de iteraciones máxima alcanzadas "
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
            } else {
                Graficos.lanzarMensajeError("Error de conversión", "Por favor, verifica el ingreso de datos antes de proceder.");
            }
        });

        btRemoveCoeficiente.setOnMouseClicked(e -> {

            if (vBoxCoeficientes.getChildren().size() > 3) {
                grado = grado - 1;
                vBoxCoeficientes.getChildren().remove(0);
            }
        });

        btLimpiar.setOnMouseClicked(e -> {
            grado =2;
            tfErrorTolerancia.setText("");
            tfIterMax.setText("");
            tfr.setText("");
            tfs.setText("");
            tfa0.setText("");
            tfa1.setText("");
            tvResultados.getItems().clear();
            while (vBoxCoeficientes.getChildren().size() > 3) {
                vBoxCoeficientes.getChildren().remove(0);
            }
        });
    }
}
