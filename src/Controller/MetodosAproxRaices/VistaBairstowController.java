package Controller.MetodosAproxRaices;

import Modelos.Excepciones.IteracionesAlcanzadas;
import Modelos.Excepciones.ValoresResueltos;
import Modelos.MetodosAproxRaices.Bairstow;
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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
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
 

    private int grado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        grado = 1;
        
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
            funcion = "";
            for (int i = 0; i < a.length; i++) {              
                funcion += "+("+a[i]+"*(x^"+ i +"))";               
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
                    Graficar();
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
            graphManager.getGraph().getData().clear();
            funcion = null;
            graphManager.setDomain(-DEFAULT_AXIS_VALUES, DEFAULT_AXIS_VALUES);
            graphManager.setRange(-DEFAULT_AXIS_VALUES, DEFAULT_AXIS_VALUES);
            tfXL.setText("-" + DEFAULT_AXIS_VALUES);
            tfXR.setText("" + DEFAULT_AXIS_VALUES);
            tfYU.setText("" + DEFAULT_AXIS_VALUES);
            tfYD.setText("-" + DEFAULT_AXIS_VALUES);
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
            System.out.println(funcion);
            ArrayList<CoordinatePair[]> dataset = new ArrayList<>();
            dataset.add(Matematico.evaluarFuncion(funcion, xl, xr));
            Graficos.plotBairstow(dataset, bpChart, graphManager);
        } catch (Exception e) {
            e.printStackTrace();
            Graficos.lanzarMensajeError("Error de Graficación", "Tuvimos un inconveniente al "
                    + "interpretar o procesar la función "
                    + "a travéz de este método, por tanto "
                    + "la gráfica no se pudo procesar.");
        }
    }
    
    
}
