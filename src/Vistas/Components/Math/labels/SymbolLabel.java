package Vistas.Components.Math.labels;

import Vistas.Components.Math.GenericComponent;
import javafx.scene.control.Label;

/**
 * Label que representa un símbolo no modificable
 * @author Paulo Aguilar
 */
public class SymbolLabel extends GenericComponent{
    private Label label;
    
    /**
     * Constructor de la clase
     * @param symbol Símbolo que se cargará en el label
     */
    public SymbolLabel(String symbol){
        this.label = new Label(symbol);
        this.label.setPrefHeight(40);
        super.getChildren().add(label);
    }
    
    public void setText(String text){
        label.setText(text);
    }
    
    /**
     * Elabora la representación simbólica del componente
     * @return Cadena representativa.
     */
    @Override
    public String getSymbolOrNumber(){
        return label.getText();
    }
}
