package Vistas.Components.Math.Inputs;

import Vistas.Components.Math.ComposedComponent;
import Vistas.Components.Math.Seccion;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Componente de función generica (log, ln, sin, cos...)
 * @author Paulo Aguilar
 */
public class GenericFunction extends ComposedComponent {
    private HBox horizontlLayout;
    private Label functionLabel;
    private Seccion operand;
    
    /**
     * Constructor de la clase
     * @param parent Sección padre de este componente
     * @param function Cadena de texto del nombre de la función.
     */
    public GenericFunction(Seccion parent,String function) {
        super();
        this.operand = new Seccion(parent,this);
        Aggrupation ag = new Aggrupation(parent,"(",")",this);
        this.operand.getHorizontalLayoutChildren().setAll(ag);
        this.horizontlLayout = new HBox(5);
        this.functionLabel = new Label(function);
        horizontlLayout.setAlignment(Pos.BASELINE_CENTER);
        horizontlLayout.getChildren().add(functionLabel);
        horizontlLayout.getChildren().add(operand);
        this.getChildren().add(horizontlLayout);
    }
    
    /**
     * Elabora la representación simbólica del componente
     *
     * @return Cadena representativa.
     */
    @Override
    public String getSymbolOrNumber() {
        String out = functionLabel.getText();
        out += operand.getSymbolOrNumber();
        return out;
    }

}
