package Plotter.Views;

import java.awt.Stroke;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import org.nfunk.jep.function.Assign;

/**
 * Design for Graph
 *
 * @author Jack Shendrikov
 *
 */
public class GraphManager {

    private NumberAxis xAxis = new NumberAxis();
    private NumberAxis yAxis = new NumberAxis();
    private LineChart graph;
    private Double x0;
    private Double x1;

    public GraphManager(Double xcero, Double xuno, boolean mostrarBordes) {
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);

        xAxis.setTickUnit(1);
        xAxis.setLowerBound(-10);
        xAxis.setUpperBound(10);

        yAxis.setTickUnit(1);
        yAxis.setLowerBound(-10);
        yAxis.setUpperBound(10);

        graph = new LineChart(xAxis, yAxis) {
            ArrayList<Polygon> miPoligono = new ArrayList<>();

            public void limpiar() {
                for (Polygon polygon : miPoligono) {
                    getPlotChildren().remove(polygon);
                }
            }
            
            @Override
            public void updateAxisRange(){
                super.updateAxisRange();
                limpiar();
            }
            @Override
            protected void layoutPlotChildren() {
                super.layoutPlotChildren();
                if (getData().size() >= 2) {
                    Series series = (Series) getData().get(1);
                    ObservableList<Data<Number, Number>> listOfData = series.getData();
                    limpiar();

                    for (int i = 0; i < listOfData.size() - 1; i++) {
                        // Check for Y value >=3
                        if (listOfData.get(i).getXValue().doubleValue() >= x0
                                && listOfData.get(i + 1).getXValue().doubleValue() <= x1) {
                            double x1 = getXAxis().getDisplayPosition(listOfData.get(i).getXValue());
                            double y1 = getYAxis().getDisplayPosition(0);
                            double x2 = getXAxis().getDisplayPosition(listOfData.get((i + 1)).getXValue());
                            double y2 = getYAxis().getDisplayPosition(0);
                            Polygon polygon = new Polygon();
                            miPoligono.add(polygon);
                            polygon.getPoints().addAll(new Double[]{
                                x1, y1,
                                x1, getYAxis().getDisplayPosition(listOfData.get(i).getYValue()),
                                x2, getYAxis().getDisplayPosition(listOfData.get((i + 1)).getYValue()),
                                x2, y2
                            });
                            getPlotChildren().add(polygon);
                            polygon.toFront();
                            if(mostrarBordes){
                                polygon.setStroke(new Color(1, 0, 0.1, 0.80));
                            }
                            polygon.setFill(new Color(1, 0, 0.1, 0.60));
                        }
                    }
                }
            }
        };

        graph.setLegendVisible(false);
        graph.setAnimated(false);

        graph.setPrefSize(880, 800);
        graph.setLayoutY(0);

        this.x0 = xcero;
        this.x1 = xuno;
    }

    public GraphManager() {
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);

        xAxis.setTickUnit(1);
        xAxis.setLowerBound(-10);
        xAxis.setUpperBound(10);

        yAxis.setTickUnit(1);
        yAxis.setLowerBound(-10);
        yAxis.setUpperBound(10);

        graph = new LineChart(xAxis, yAxis);

        graph.setLegendVisible(false);
        graph.setAnimated(false);

        graph.setPrefSize(880, 800);
        graph.setLayoutY(0);

    }

    public void setDomain(double lowerDomain, double upperDomain) {
        xAxis.setLowerBound(lowerDomain);
        xAxis.setUpperBound(upperDomain);
        xAxis.setTickUnit((upperDomain - lowerDomain) / 20);
    }

    public void setRange(double lowerRange, double upperRange) {
        yAxis.setLowerBound(lowerRange);
        yAxis.setUpperBound(upperRange);
        yAxis.setTickUnit((upperRange - lowerRange) / 20);
    }

    public void setX0(Double x0) {
        this.x0 = x0;
    }

    public void setX1(Double x1) {
        this.x1 = x1;
    }

    public LineChart getGraph() {
        return graph;
    }
}
