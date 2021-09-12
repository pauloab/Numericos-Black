package Controller.IntegracionNewtonCotes;

import Modelos.IntegracionNewtonCotes.Simpson13;
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
import Modelos.Interpolacion.InterpolacionLagrange;
import Util.Mensajes;

/**
 * FXML Controller class de la vista Simpson 1/3
 *
 * @author Freddy Lamar
 */
public class VistaSimpson13Controller implements Initializable {

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
        graphManager = new GraphManager(x0, x1, false);
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

        cbTipoTrapecio.getItems().setAll("Simpson 1/3 Simple", "Simpson 1/3 múltiple");
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
                if (!cbTipoTrapecio.getSelectionModel().selectedItemProperty().getValue().equalsIgnoreCase("Simpson 1/3 Simple")) {
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
            Simpson13 simpson13;
            boolean error = false;
            if (cbTipoTrapecio.getSelectionModel().selectedItemProperty().getValue() != null) {
                if (cbTipoTrapecio.getSelectionModel().selectedItemProperty().getValue().equalsIgnoreCase("Simpson 1/3 Simple")) {
                    if (Matematico.validarExpresion(funcion)) {
                        x0 = Graficos.validarTextFieldDouble(tfx0);
                        x1 = Graficos.validarTextFieldDouble(tfx1);
                        n = 2;
                        if (x0 != null && x1 != null) {
                            if (x1 > x0) {
                                simpson13 = new Simpson13(funcion, x0, x1);
                                try {
                                    punto = simpson13.Simpson13Simple();
                                    lbResultado.setText("" + punto);
                                } catch (Exception ex) {
                                    error = true;
                                    Graficos.lanzarMensajeError(Mensajes.E_PROCESAMIENTO, Mensajes.ERROR_PROCESAMIENTO_METODO);
                                }
                                if (!error) {
                                    Graficar(x0, x1);
                                }
                            } else {
                                Graficos.lanzarMensajeAdvertencia(Mensajes.A_INTERVALOS, Mensajes.ERROR_VALIDACION_B);
                            }
                        } else {
                            Graficos.lanzarMensajeError(Mensajes.E_CONVERSION, Mensajes.ERROR_CONVERSION);
                        }
                    } else {
                        Graficos.lanzarMensajeError(Mensajes.E_CONVERSION, Mensajes.ERROR_CONVERSION_FORMULA);
                    }
                } else {
                    labeln.setVisible(true);
                    tfn.setVisible(true);
                    if (Matematico.validarExpresion(funcion)) {
                        x0 = Graficos.validarTextFieldDouble(tfx0);
                        x1 = Graficos.validarTextFieldDouble(tfx1);
                        n = Graficos.validarTextFieldEnteros(tfn);
                        if (x0 != null && x1 != null && n != null) {
                            if (x1 > x0) {
                                if (n > 1 && n <= 50) {
                                    simpson13 = new Simpson13(funcion, x0, x1, n);
                                    try {
                                        punto = simpson13.Simpson13MultipleSegmentos();
                                        lbResultado.setText("" + punto);
                                    } catch (Exception ex) {
                                        error = true;
                                        Graficos.lanzarMensajeError(Mensajes.E_PROCESAMIENTO, Mensajes.ERROR_PROCESAMIENTO_METODO);
                                    }
                                    if (!error) {
                                        Graficar(x0, x1);
                                    }
                                } else {
                                    Graficos.lanzarMensajeError(Mensajes.E_VALIDACION, Mensajes.ERROR_VALIDACION_SEGMENTOS);
                                }
                            } else {
                                Graficos.lanzarMensajeAdvertencia(Mensajes.E_VALIDACION, Mensajes.ERROR_VALIDACION_B);
                            }
                        } else {
                            Graficos.lanzarMensajeError(Mensajes.E_CONVERSION, Mensajes.ERROR_CONVERSION);
                        }
                    } else {
                        Graficos.lanzarMensajeError(Mensajes.E_CONVERSION, Mensajes.ERROR_CONVERSION_FORMULA);
                    }
                }
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
            ArrayList<CoordinatePair[]> dataset2 = new ArrayList<>();
            dataset.add(Matematico.evaluarFuncion(funcion, xl, xr));
            if (cbTipoTrapecio.getSelectionModel().selectedItemProperty().getValue().equalsIgnoreCase("Simpson 1/3 Simple")) {
                double[] xCordenadasTrabajo = {x0, (x0 + x1) / n, x1};
                double[] yCordenadasTrabajo = {Matematico.evaluarFuncion(funcion, x0), Matematico.evaluarFuncion(funcion, (x0 + x1) / n), Matematico.evaluarFuncion(funcion, x1)};
                InterpolacionLagrange interpolar = new InterpolacionLagrange(xCordenadasTrabajo, yCordenadasTrabajo, x0 + (x1 - x0) / 3);
                interpolar.interpolacion();
                CoordinatePair[] puntosGraficar = Matematico.evaluarFuncion(interpolar.getFuncion(), x0, x1);
                dataset.add(puntosGraficar);
            } else {
                double xs0 = x0, xs1, xs2;
                for (int i = 0; i < n; i++) {
                    xs1 = xs0 + (x1 - x0) / n;
                    xs2 = xs1 + (x1 - x0) / n;
                    double[] xCordenadasTrabajo = {xs0, xs1, xs2};
                    double[] yCordenadasTrabajo = {Matematico.evaluarFuncion(funcion, xs0), Matematico.evaluarFuncion(funcion, xs1), Matematico.evaluarFuncion(funcion, xs2)};
                    InterpolacionLagrange interpolar = new InterpolacionLagrange(xCordenadasTrabajo, yCordenadasTrabajo, xs0 + (xs1 - xs0) / 3);
                    interpolar.interpolacion();
                    CoordinatePair[] puntosGraficar = Matematico.evaluarFuncion(interpolar.getFuncion(), xs0, xs2);
                    xs0 = xs2;
                    dataset2.add(puntosGraficar);
                }
                int j = 0;
                CoordinatePair[] set2 = new CoordinatePair[1501 * dataset2.size()];
                for (CoordinatePair[] coordinatePair : dataset2) {
                    for (int i = 0; i < 1501; i++) {
                        set2[j] = coordinatePair[i];
                        j++;
                    }
                }
                dataset.add(set2);
            }
            Graficos.plotNoInterseciones(dataset, bpChart, graphManager);

        } catch (Exception e) {
            Graficos.lanzarMensajeError(Mensajes.E_GRAFICA, Mensajes.ERROR_GRAFICA);
        }
    }

    private boolean definirLimites() {
        boolean res = false;
        Double xl = Graficos.validarTextFieldDouble(tfXL);
        Double xr = Graficos.validarTextFieldDouble(tfXR);
        Double yu = Graficos.validarTextFieldDouble(tfYU);
        Double yd = Graficos.validarTextFieldDouble(tfYD);
        if (xl != null && xr != null && yu != null && yd != null) {
            if (xl < xr && yu > yd) {
                if (Math.abs(xl - xr) <= Graficos.RANGO_GRAFICACION_MAX) {
                    if (Math.abs(yu - yd) <= Graficos.RANGO_GRAFICACION_MAX) {
                        this.xl = xl;
                        this.xr = xr;
                        this.yu = yu;
                        this.yd = yd;
                        graphManager.setDomain(xl, xr);
                        graphManager.setRange(yd, yu);
                        res = true;
                    } else {
                        Graficos.lanzarMensajeError(Mensajes.E_VALIDACION,
                                Mensajes.ERROR_VALIDACION_EJE_Y + Graficos.RANGO_GRAFICACION_MAX);
                    }
                } else {
                    Graficos.lanzarMensajeError(Mensajes.E_VALIDACION,
                            Mensajes.ERROR_VALIDACION_EJE_X + Graficos.RANGO_GRAFICACION_MAX);
                }
            } else {
                Graficos.lanzarMensajeAdvertencia(Mensajes.A_INTERVALOS, Mensajes.ADVERTENCIA_INTERVALOS);
            }
        } else {
            Graficos.lanzarMensajeError(Mensajes.ERROR_CONVERSION, Mensajes.ERROR_CONVERSION_CONTROL_EJES);
        }
        return res;
    }
}
