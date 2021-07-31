package Vistas.Components.Math;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

/**
 * Componente gráfico de ingreso de fórmulas con todas las funcionalidades 
 * implementadas.
 * @author Paulo Aguilar
 */
public class FormulaInput extends HBox {
    
    private Seccion seccionPrincipal;
    
    /**
     * Constructor de la clase
     */
    public FormulaInput(){
        super();
        this.setAlignment(Pos.BOTTOM_LEFT);
        seccionPrincipal = new Seccion();
        this.setStyle("-fx-padding: 10;");
        this.getChildren().add(seccionPrincipal);
    }
    
    /**
     * Función que permite limpiar la fórmula de su sección raíz
     */
    public void clearFormula(){
        seccionPrincipal = new Seccion();
        this.getChildren().clear();
        this.getChildren().add(seccionPrincipal);
    }
    
    /**
     * Obtiene la expresión descrita en fórmula standard admitida por JEP y dJep
     * @return cadena de texto con la representación textual de la función.
     */
    public String getFormula(){
        return seccionPrincipal.getSymbolOrNumber();
    }
    
}
