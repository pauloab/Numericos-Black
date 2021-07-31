package Vistas.Components.Math.Inputs;

import Vistas.Components.Math.ComposedComponent;
import Vistas.Components.Math.GenericComponent;
import Vistas.Components.Math.Seccion;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

/**
 * Componente que representa la fracción con dos secciones, arriba y abajo
 * @author Paulo Aguilar
 */
public class Fraction extends ComposedComponent{
    private VBox verticalLayout;
    private Seccion numeratorSeccion;
    private Seccion denominatorSeccion;
    private Line lineaHorizontal;
    
    /**
     * Constructor de la clase
     * @param parent Sección padre de este componente
     * @param numerator Input del numerador precargada
     */
    public Fraction(Seccion parent, GenericComponent numerator){
        super();
        this.numeratorSeccion = new Seccion(parent, numerator,this);
        this.denominatorSeccion = new Seccion(parent,this);
        this.lineaHorizontal = new Line(0,0,1,0);
        this.verticalLayout = new VBox(5);
        verticalLayout.widthProperty().addListener(e -> {
            lineaHorizontal.setEndX(verticalLayout.getWidth()-11);
        });
        verticalLayout.setAlignment(Pos.CENTER);
        verticalLayout.setPadding(new Insets(0, 5, 0, 5));
        verticalLayout.getChildren().add(numeratorSeccion);
        verticalLayout.getChildren().add(lineaHorizontal);
        verticalLayout.getChildren().add(denominatorSeccion);
        this.getChildren().add(verticalLayout);
    }
    
    /**
     * Constructor de la clase
     * @param parent Sección padre de este componente 
     */
    public Fraction(Seccion parent){
        super();
        MathInput mi = new MathInput(null);
        this.numeratorSeccion = new Seccion(parent,mi,this);
        this.denominatorSeccion = new Seccion(parent,this);
        this.lineaHorizontal = new Line(0,0,1,0);
        this.verticalLayout = new VBox(5);
        verticalLayout.widthProperty().addListener(e -> {
            lineaHorizontal.setEndX(verticalLayout.getWidth()-11);
        });
        verticalLayout.setAlignment(Pos.CENTER);
        verticalLayout.setPadding(new Insets(0, 5, 0, 5));
        verticalLayout.getChildren().add(numeratorSeccion);
        verticalLayout.getChildren().add(lineaHorizontal);
        verticalLayout.getChildren().add(denominatorSeccion);
        this.getChildren().add(verticalLayout);    }

    /**
     * Elabora la representación simbólica del componente
     *
     * @return Cadena representativa.
     */
    @Override
    public String getSymbolOrNumber() {
        String out = "(";
        out += numeratorSeccion.getSymbolOrNumber();
        out += "/";
        out += denominatorSeccion.getSymbolOrNumber();
        out += ")";
        return out;
    }
    
    
}
