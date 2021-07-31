package Vistas.Components.Math;

import Vistas.Components.Math.Inputs.MathInput;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.ReadOnlyProperty;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

/**
 * Componente gráfico que contiene los botones de función de
 * los componentes matemáticos
 * @author Paulo Aguilar
 */
public class FormulaFunctionButtons extends FlowPane {
    
    private FormulaInput boundedInput;
    
    /**
     * Constructor de la clase
     * @param fi Input de formulas asociado a esta sección de botones
     */
    public FormulaFunctionButtons(FormulaInput fi){
        super();
        boundedInput = fi;
        
        JFXButton btn;
        btn = new JFXButton("sin(x)");
        btn.focusTraversableProperty().set(false);
        btn.setOnMouseClicked(e -> onFunctionPressed("sen"));
        getChildren().add(btn);
        
        btn = new JFXButton("cos(x)");
        btn.focusTraversableProperty().set(false);
        btn.setOnMouseClicked(e -> onFunctionPressed("cos"));
        getChildren().add(btn);
        
        btn = new JFXButton("tan(x)");
        btn.focusTraversableProperty().set(false);
        btn.setOnMouseClicked(e -> onFunctionPressed("tan"));
        getChildren().add(btn);
        
        btn = new JFXButton("ctg(x)");
        btn.focusTraversableProperty().set(false);
        btn.setOnMouseClicked(e -> onFunctionPressed("ctg"));
        getChildren().add(btn);
        
        btn = new JFXButton("sec(x)");
        btn.focusTraversableProperty().set(false);
        btn.setOnMouseClicked(e -> onFunctionPressed("sec"));
        getChildren().add(btn);
        
        btn = new JFXButton("csc(x)");
        btn.focusTraversableProperty().set(false);
        btn.setOnMouseClicked(e -> onFunctionPressed("csc"));
        getChildren().add(btn);
        
        btn = new JFXButton("ln(x)");
        btn.focusTraversableProperty().set(false);
        btn.setOnMouseClicked(e -> onFunctionPressed("ln"));
        getChildren().add(btn);
        
        btn = new JFXButton("log(x)");
        btn.focusTraversableProperty().set(false);
        btn.setOnMouseClicked(e -> onFunctionPressed("log"));
        getChildren().add(btn);
        
        btn = new JFXButton("potencia(x,y)");
        btn.focusTraversableProperty().set(false);
        btn.setOnMouseClicked(e -> onFunctionPressed("pow"));
        getChildren().add(btn);
        
        btn = new JFXButton("raiz(x)");
        btn.focusTraversableProperty().set(false);
        btn.setOnMouseClicked(e -> onFunctionPressed("sqrt"));
        getChildren().add(btn);
        
        btn = new JFXButton("Limpiar Función");
        btn.focusTraversableProperty().set(false);
        btn.setOnMouseClicked(e -> boundedInput.clearFormula());
        getChildren().add(btn);
        boundedInput.clearFormula();
    }
    
    /**
     * Evento de click de todos los botones de función. 
     * Toma el componente enfocado y llama a su sección padre para insertar 
     * un nuevo componente hijo, según el boton presionado.
     * @param function Cadena de texto de la función a añadir.
     */
    private void onFunctionPressed(String function) {
        ReadOnlyProperty<Node> focusProperty = this.getScene().focusOwnerProperty();
        Node n = focusProperty.getValue();
        if (n.getParent() instanceof MathInput) {
            MathInput mi  =((MathInput)(n.getParent()));
            mi.getParentSeccion().addSymbol(function, mi);
        }
    }
    
}
