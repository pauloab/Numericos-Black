package Vistas.Components.Math.Inputs;

import Vistas.Components.Math.ComposedComponent;
import Vistas.Components.Math.Seccion;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

/**
 * Componente que representa la raíz cuadrada
 * @author Paulo Aguilar
 */
public class SQRT extends ComposedComponent {

    private Seccion section;
    private HBox horizontalLayout;
    private VBox verticalLayout;
    private Line diagLine;
    private Line shortDiagLine;
    private Line topLine;
    private Pane pane;

    /**
     * Constructor de la clase
     * @param parent Sección padre del componente
     */
    public SQRT(Seccion parent) {
        super();
        this.section = new Seccion(parent, this);
        this.horizontalLayout = new HBox(0);
        this.verticalLayout = new VBox(2);
        this.diagLine = new Line(10, 40, 20, 0);
        this.shortDiagLine = new Line(0, 20, 10, 40);
        this.topLine = new Line(0, 0, 1, 0);

        this.pane = new Pane();
        verticalLayout.getChildren().add(topLine);
        verticalLayout.getChildren().add(section);
        verticalLayout.setAlignment(Pos.BASELINE_LEFT);
        verticalLayout.widthProperty().addListener(e -> {
            topLine.setEndX(verticalLayout.getWidth() - 3);
        });
        horizontalLayout.setAlignment(Pos.CENTER);
        horizontalLayout.heightProperty().addListener(e -> {
            diagLine.setStartY(horizontalLayout.getHeight() - 8);
            shortDiagLine.setEndY(horizontalLayout.getHeight() - 8);
            shortDiagLine.setStartY((horizontalLayout.getHeight() - 8) / 2);
        });
        pane.getChildren().add(shortDiagLine);
        pane.getChildren().add(diagLine);

        horizontalLayout.getChildren().add(pane);
        horizontalLayout.getChildren().add(verticalLayout);
        this.getChildren().add(horizontalLayout);
    }

    /**
     * Elabora la representación simbólica del componente
     *
     * @return Cadena representativa.
     */
    @Override
    public String getSymbolOrNumber() {
        String out = "sqrt(";
        out += section.getSymbolOrNumber();
        out += ")";
        return out;
    }

}
