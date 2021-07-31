package Vistas.Components.Math.Inputs;

import Vistas.Components.Math.ComposedComponent;
import Vistas.Components.Math.GenericComponent;
import Vistas.Components.Math.Seccion;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

/**
 * Componente que representa la potencia de un número o símbolo
 * @author Paulo Aguilar
 */
public class Pow extends ComposedComponent{
    private Seccion expSection;
    private Seccion baseSection;
    private GridPane gridLayout;
    
    /**
     * Constructor de la clase
     * @param parent Sección padre de este componente.
     * @param initial Input inicial que va en la base de la potencia.
     */
    public Pow(Seccion parent, GenericComponent initial) {
        super();
        this.gridLayout = new GridPane();
        this.baseSection = new Seccion(parent,this);
        this.expSection = new Seccion(parent,this);
        Aggrupation agrup ;
        if (!initial.getSymbolOrNumber().equalsIgnoreCase("pow")) {
            agrup = new Aggrupation(baseSection, "(", ")",initial);
        }else{
            agrup = new Aggrupation(baseSection, "(", ")");
        }
        baseSection.getHorizontalLayoutChildren().setAll(agrup);
        gridLayout.add(expSection, 1, 0);
        gridLayout.add(baseSection, 0, 1);
        gridLayout.setAlignment(Pos.BASELINE_LEFT);
        this.getChildren().setAll(gridLayout);
    }
    
    /**
     * Elabora la representación simbólica del componente
     *
     * @return Cadena representativa.
     */
    @Override
    public String getSymbolOrNumber() {
        String out = "(";
        out += baseSection.getSymbolOrNumber();
        out += "^(";
        out += expSection.getSymbolOrNumber();
        out += "))";
        return out;
    }
}
