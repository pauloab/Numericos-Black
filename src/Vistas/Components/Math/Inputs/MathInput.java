package Vistas.Components.Math.Inputs;

import Vistas.Components.Math.GenericComponent;
import Vistas.Components.Math.Seccion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/**
 * Campo de input donde se pueden escribir dígitos del 0-9 y x
 * @author Paulo Aguilar
 */
public class MathInput extends GenericComponent{
    private TextField textField;
    
    /**
     * Constructor de la clase
     * @param section Sección padre del input
     */
    public MathInput(Seccion section){
        super(section);
        textField = new TextField();
        textField.setPrefWidth(20);
        textField.setAlignment(Pos.CENTER);
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                onTextChange(newValue, oldValue);
            }
        });
        textField.setOnKeyPressed(e -> {
            if (e.getCode()==KeyCode.DELETE) {
                getParentSeccion().deleteMe(this);
            }
        });
        this.getChildren().add(textField);
    }

    /**
     * Función de cambio del TextField que valida y agrega las operaciones 
     * de un solo caractér.
     * @param newValue Valor nuevo
     * @param oldValue Valor anterior
     */
    public void onTextChange(String newValue, String oldValue) {
        if (!newValue.matches("[0,1,2,3,4,5,6,7,8,9,x]")) {
            textField.setText(newValue.replaceAll("[^0,1,2,3,4,5,6,7,8,9,x]", ""));

        }

        String symbol = newValue.length() > 0
                ? newValue.substring(newValue.length() - 1, newValue.length())
                : "";

        if (symbol.equals("+") || symbol.equals("-") || symbol.equals("*")
                || symbol.equals("/") || symbol.equals("^") || symbol.equals("e")
                || symbol.equals("(") || symbol.equals("{")) {
            getParentSeccion().addSymbol(symbol, this);
        }

        textField.setPrefWidth(textField.getText().length() * 9 + 20);
    }
    
    /**
     * Elabora la representación simbólica del componente
     *
     * @return Cadena representativa.
     */
    @Override
    public String getSymbolOrNumber() {
        return textField.getText();
    }
    
    @Override
    public void requestFocus(){
        textField.requestFocus();
    }
    
}
