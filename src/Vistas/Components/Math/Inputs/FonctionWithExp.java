package Vistas.Components.Math.Inputs;

import Vistas.Components.Math.ComposedComponent;
import Vistas.Components.Math.Seccion;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Componente de función con exponente y base no modificable.
 * @author Paulo Aguilar
 */
public class FonctionWithExp extends ComposedComponent{
    private Seccion expSection;
    private GridPane gridLayout;
    private Label functionLabel;
    private String funcitonName;
    
    /**
     * Constructor de la clase
     * @param parent Sección padre de este componente
     * @param funcitonName Cadena de texto representativa para la construcción 
     * de símbolo
     * @param labeled Cadena representativa para la UI que irá en el label. 
     */
    public FonctionWithExp(Seccion parent, String funcitonName, String labeled) {
        super();
        this.funcitonName = funcitonName;
        this.expSection = new Seccion(parent,this);
        this.gridLayout = new GridPane();
        
        this.functionLabel = new Label(labeled);
        gridLayout.add(expSection, 1, 0);
        gridLayout.setAlignment(Pos.CENTER);
        gridLayout.add(functionLabel, 0, 1);
        this.getChildren().add(gridLayout);
    }

    /**
     * Elabora la representación simbólica del componente
     *
     * @return Cadena representativa.
     */
    @Override
    public String getSymbolOrNumber() {
        return funcitonName + "(" + expSection.getSymbolOrNumber() + ")";
    }

}
