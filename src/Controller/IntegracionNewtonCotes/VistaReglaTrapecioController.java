package Controller.IntegracionNewtonCotes;

import Modelos.IntegracionNewtonCotes.ReglaTrapecio;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class de la vista Regla de Trapecio
 *
 * @author Geovanny Vega
 */
public class VistaReglaTrapecioController implements Initializable {

    @FXML
    private TextField tfFormula;
    @FXML
    private TextField tfx0;
    @FXML
    private TextField tfx1;
    @FXML
    private TextField tfn;
    @FXML
    private Label labeln;
    @FXML
    private Button btProcesar;
    @FXML
    private Button btLimpiar;
    @FXML
    private BorderPane bpChart;
    @FXML
    private Label lbResultado;
    @FXML
    private ComboBox<String> cbTipoTrapecio;
    @FXML
    private TextField tfYU, tfYD, tfXL, tfXR;
    @FXML
    private JFXButton btAjustar;
    @FXML
    private StackPane spGrafico;
    private GraphManager graphManager;
    private double yu = 30, yd = -30, xl = -30, xr = 30;
    private final double DEFAULT_AXIS_VALUES = 30;
    private String funcion;
    private Double punto = null;
    private Double x0;
    private Double x1;
    private Integer n;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        graphManager = new GraphManager(x0, x1, true);
        tfXL.setText(xl + "");
        tfXR.setText(xr + "");
        tfYU.setText(yu + "");
        tfYD.setText(yd + "");
        definirLimites();

        spGrafico.getChildren().add(0, graphManager.getGraph());

        funcion = null;
        btAjustar.setOnMouseClicked(event -> {
            boolean limitesDefinidos = definirLimites();
            if (funcion != null && limitesDefinidos) {
                Graficar(x0, x1);
            }
        });

        cbTipoTrapecio.getItems().setAll("Trapecio simple", "Trapecio múltiple segmentos");
        labeln.setVisible(false);
        tfn.setVisible(false);
        tfFormula.setEditable(false);
        tfx0.setEditable(false);
        tfx1.setEditable(false);
        tfn.setEditable(false);

        Graficos.convertirEnInputFlotantes(tfXL);
        Graficos.convertirEnInputFlotantes(tfXR);
        Graficos.convertirEnInputFlotantes(tfYU);
        Graficos.convertirEnInputFlotantes(tfYD);

        Graficos.convertirEnInputFlotantes(tfx0);
        Graficos.convertirEnInputFlotantes(tfx1);
        Graficos.convertirEnInputEnteros(tfn);

        cbTipoTrapecio.getSelectionModel().selectedItemProperty().addListener(e
                -> {
            if (cbTipoTrapecio.getSelectionModel().selectedItemProperty().getValue() != null) {
                if (!cbTipoTrapecio.getSelectionModel().selectedItemProperty().getValue().equalsIgnoreCase("Trapecio simple")) {
                    labeln.setVisible(true);
                    tfn.setVisible(true);
                    tfFormula.setEditable(true);
                    tfx0.setEditable(true);
                    tfx1.setEditable(true);
                    tfn.setEditable(true);
                } else {
                    labeln.setVisible(false);
                    tfn.setVisible(false);
                    tfFormula.setEditable(true);
                    tfx0.setEditable(true);
                    tfx1.setEditable(true);
                }
            }

        });

        btProcesar.setOnMouseClicked(event -> {
            funcion = tfFormula.getText();
            ReglaTrapecio reglatrapecio;
            boolean error = false;
            if (cbTipoTrapecio.getSelectionModel().selectedItemProperty().getValue() != null) {
                if (cbTipoTrapecio.getSelectionModel().selectedItemProperty().getValue().equalsIgnoreCase("Trapecio simple")) {
                    if (Matematico.validarExpresion(funcion)) {
                        x0 = Graficos.validarTextFieldDouble(tfx0);
                        x1 = Graficos.validarTextFieldDouble(tfx1);
                        n = 1;
                        if (x0 != null && x1 != null) {
                            reglatrapecio = new ReglaTrapecio(funcion, x0, x1);
                            try {
                                punto = reglatrapecio.trapecioSimple();
                                lbResultado.setText("" + punto);
                            } catch (Exception ex) {
                                error = true;
                                Graficos.lanzarMensajeError("Error de procesamiento", "Tuvimos un inconveniente al "
                                        + "interpretar o procesar la función "
                                        + "a travéz de este método.");
                            }
                            if (!error) {
                                Graficar(x0, x1);
                            }
                        } else {
                            Graficos.lanzarMensajeError("Error de conversión", "Por favor, verifica el ingreso de datos antes de proceder.");
                        }
                    } else {
                        Graficos.lanzarMensajeError("Error de conversión", "Hubo un error al interpretar la fórmula ingresada.");
                    }
                } else {
                    labeln.setVisible(true);
                    tfn.setVisible(true);
                    if (Matematico.validarExpresion(funcion)) {
                        x0 = Graficos.validarTextFieldDouble(tfx0);
                        x1 = Graficos.validarTextFieldDouble(tfx1);
                        n = Graficos.validarTextFieldEnteros(tfn);
                        if (x0 != null && x1 != null && n != null) {
                            if (n > 1 && n <= 50) {
                                reglatrapecio = new ReglaTrapecio(funcion, x0, x1, n);
                                try {
                                    punto = reglatrapecio.trapecioSegmentosMultiples();
                                    lbResultado.setText("" + punto);
                                } catch (Exception ex) {
                                    error = true;
                                    Graficos.lanzarMensajeError("Error de procesamiento", "Tuvimos un inconveniente al "
                                            + "interpretar o procesar la función "
                                            + "a travéz de este método.");
                                }
                                if (!error) {
                                    Graficar(x0, x1);
                                }
                            } else {
                                Graficos.lanzarMensajeError("Error de validación", "Tuvimos un inconveniente al "
                                        + "interpretar el número se segmentos "
                                        + "a través de este método."
                                + " El número de segmentos máximo es 50");
                            }

                        } else {
                            Graficos.lanzarMensajeError("Error de conversión", "Por favor, verifica el ingreso de datos antes de proceder.");
                        }
                    } else {
                        Graficos.lanzarMensajeError("Error de conversión", "Hubo un error al interpretar la fórmula ingresada.");
                    }
                }
            } else {
                Graficos.lanzarMensajeError("Error de procesamiento", "Tuvimos un inconveniente al "
                        + "interpretar o procesar la función "
                        + "a travéz de este método.");
            }
        });
        btLimpiar.setOnMouseClicked(e -> {
            graphManager.getGraph().getData().clear();
            funcion = null;
            lbResultado.setText("");
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
            tfn.setText("");
            labeln.setVisible(false);
            tfn.setVisible(false);
            tfFormula.setEditable(false);
            tfx0.setEditable(false);
            tfx1.setEditable(false);
            tfn.setEditable(false);
            cbTipoTrapecio.getSelectionModel().clearSelection();
            x0 = null;
            x1 = null;
            n = null;
        });
    }

    private void Graficar(Double x0, Double x1) {
        try {
            graphManager.setX0(x0);
            graphManager.setX1(x1);
            ArrayList<CoordinatePair[]> dataset = new ArrayList<>();
            dataset.add(Matematico.evaluarFuncion(funcion, xl, xr));
            CoordinatePair[] puntosGraficar;
            if (n == 1) {
                puntosGraficar = new CoordinatePair[4];
            } else {
                puntosGraficar = new CoordinatePair[n + 1];
            }

            double x = x0;
            for (int i = 0; i < (puntosGraficar.length); i++) {

                puntosGraficar[i] = new CoordinatePair(x, Matematico.evaluarFuncion(funcion, x));
                x += (x1 - x0) / n;
            }
            dataset.add(puntosGraficar);
            Graficos.plotBairstow(dataset, bpChart, graphManager);

        } catch (Exception e) {
            e.printStackTrace();
            Graficos.lanzarMensajeError("Error de Graficación", "Tuvimos un inconveniente al "
                    + "interpretar o procesar la función "
                    + "a través de este método, por tanto"
                    + "la gráfica no se pudo procesar.");
        }
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
}
