package Vistas.Components.Math.Inputs;

import Vistas.Components.Math.ComposedComponent;
import Vistas.Components.Math.GenericComponent;
import Vistas.Components.Math.Seccion;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Componente de símbolos de agrupación agrupación
 * @author Paulo Aguilar
 */
public class Aggrupation extends ComposedComponent {
    
    private Seccion section;
    private HBox horizontalBox;
    
    /**
     * Consturctor de la clase
     * @param parent Seccion padre
     * @param startBracket símbolo de aprtura
     * @param endBracket símbolo de cierre
     */
    public Aggrupation(Seccion parent, String startBracket, String endBracket){
        super();
        this.section = new Seccion(parent,this);
        this.horizontalBox = new HBox(5);
        this.horizontalBox.setAlignment(Pos.BOTTOM_LEFT);
        this.getChildren().add(horizontalBox);
        horizontalBox.getChildren().add(new Label(startBracket));
        horizontalBox.getChildren().add(section);
        horizontalBox.getChildren().add(new Label(endBracket));
    }
    
    /**
     * Constructor con valor inicial
     * @param parent Sección padrede este componente
     * @param startBracket Símbolo de apertura
     * @param endBracket Símbolo de cierre
     * @param initial Input inicial de este componente
     */
    public Aggrupation(Seccion parent, String startBracket, String endBracket, GenericComponent initial){
        super();
        this.section = new Seccion(parent, initial,this);
        this.horizontalBox = new HBox(5);
        this.horizontalBox.setAlignment(Pos.CENTER);
        this.getChildren().add(horizontalBox);
        horizontalBox.getChildren().add(new Label(startBracket));
        horizontalBox.getChildren().add(section);
        horizontalBox.getChildren().add(new Label(endBracket));
    }

    /**
     * Constructor para componentes padres
     * @param parent Sección padrede este componente
     * @param startBracket Símbolo de apertura
     * @param endBracket Símbolo de cierre
     * @param component Componente complejo padre de este componente
     */
    public Aggrupation(Seccion parent, String startBracket, String endBracket, ComposedComponent component){
        super();
        this.section = new Seccion(parent,component);
        this.horizontalBox = new HBox(5);
        this.horizontalBox.setAlignment(Pos.BOTTOM_LEFT);
        this.getChildren().add(horizontalBox);
        horizontalBox.getChildren().add(new Label(startBracket));
        horizontalBox.getChildren().add(section);
        horizontalBox.getChildren().add(new Label(endBracket));
    }
    
    /**
     * Elabora la representación simbólica del componente
     * @return Cadena representativa.
     */
    @Override
    public String getSymbolOrNumber(){
        String out = "(";
        out += section.getSymbolOrNumber();
        out += ")";
        return out;
    }
   
}
