package Vistas.Components.Math;

import Vistas.Components.Math.labels.SymbolLabel;
import Vistas.Components.Math.Inputs.Aggrupation;
import Vistas.Components.Math.Inputs.Fraction;
import Vistas.Components.Math.Inputs.MathInput;
import Vistas.Components.Math.Inputs.Pow;
import Vistas.Components.Math.Inputs.SQRT;
import Vistas.Components.Math.Inputs.FonctionWithExp;
import Vistas.Components.Math.Inputs.GenericFunction;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * Componente de seccion que contiene grupos de inputs y componentes complejos 
 * que representan operaciones complejas. Se encarga del manejo y administración
 * del fujo de operaciones.
 * @author Paulo Aguilar
 */
public class Seccion extends GenericComponent implements Deleteable {

    private HBox horizontalBox;
    private ComposedComponent parentComponent;
    
    /**
     * Constructor de la clase
     */
    public Seccion() {
        super();
        this.parentComponent = null;
        this.horizontalBox = new HBox(5);
        getChildren().add(horizontalBox);
        horizontalBox.setAlignment(Pos.BOTTOM_LEFT);
        MathInput nb = new MathInput(this);
        horizontalBox.getChildren().add(nb);
        nb.requestFocus();
    }

    /**
     * Constructor de la clase con componente padre
     * @param parentSeccion Sección padre de esta seción.
     * @param parentComponent Componente complejo padre de esta sección.
     */
    public Seccion(Seccion parentSeccion, ComposedComponent parentComponent) {
        this();
        this.parentComponent = parentComponent;
        super.setParentSeccion(parentSeccion);
    }

    /**
     * Constructor para secciones con inputs precargados
     * @param parentSeccion Secion Padre de esta sección.
     * @param initial Inpput inicial precargado.
     * @param parentComponent Componente complejo padre de esta sección.
     */
    public Seccion(Seccion parentSeccion, GenericComponent initial, ComposedComponent parentComponent) {
        this(parentSeccion,parentComponent);
        horizontalBox.getChildren().clear();
        horizontalBox.getChildren().add(initial);
        initial.setParentSeccion(this);
    }

    /**
     * Añade una nueva operación a la sección, a travéz de un código relacionado
     * relacionado a la operaión que se desea insertar.
     * @param newValue Código de operación.
     * @param caller Input que llama a la función.
     */
    public void addSymbol(String newValue, GenericComponent caller) {
        int actualNodeIndex = getHorizontalLayoutChildren().indexOf(caller);;
        actualNodeIndex = actualNodeIndex >= 0 ? actualNodeIndex: 0;
        // Se extrae el último caracter de la cadena de texto
        String symbol = newValue.length() > 0
                ? newValue.substring(newValue.length() - 1, newValue.length())
                : "";
        // Se obtienen todos los símbolos de esta sección.
        String usedSymbols = getSymbolOrNumber();
        
        // Operaciones de agregación de operadores
        if (symbol.equalsIgnoreCase("+") || symbol.equalsIgnoreCase("-") || symbol.equalsIgnoreCase("*")) {
            if (caller.getSymbolOrNumber().length()>0) {
                // Inserta un campo nuevo simple y le otorga el foco.
                horizontalBox.getChildren().add(new SymbolLabel(symbol));
                MathInput nb = new MathInput(this);
                horizontalBox.getChildren().add(nb);
                nb.requestFocus();
            }else{              
                if (actualNodeIndex > 0 && !symbol.equals("/") && !symbol.equals("/")) {
                    ((SymbolLabel)getHorizontalLayoutChildren().get(actualNodeIndex-1)).setText(symbol);
                }else if (symbol.equals("-")){
                    
                    horizontalBox.getChildren().add(actualNodeIndex,new SymbolLabel("-"));
                }
            }
            
        } else {
            
            // Operaiones complejas y funciones
            if (newValue.equalsIgnoreCase("sqrt")) {
                horizontalBox.getChildren().add(actualNodeIndex,new SQRT(this));
            } else if (newValue.equalsIgnoreCase("e")) {
                horizontalBox.getChildren().add(actualNodeIndex,new FonctionWithExp(this, "exp", "e"));
            } else if (newValue.equalsIgnoreCase("ln")) {
                horizontalBox.getChildren().add(actualNodeIndex,new FonctionWithExp(this, "ln", "ln"));
            } else if (newValue.equalsIgnoreCase("sen") || newValue.equalsIgnoreCase("sin")) {
                horizontalBox.getChildren().add(actualNodeIndex,new GenericFunction(this, "sen"));
            } else if (newValue.equalsIgnoreCase("cos")) {
                horizontalBox.getChildren().add(actualNodeIndex,new GenericFunction(this, "cos"));
            } else if (newValue.equalsIgnoreCase("tan") || newValue.equalsIgnoreCase("tn")) {
                horizontalBox.getChildren().add(actualNodeIndex,new GenericFunction(this, "tan"));
            } else if (newValue.equalsIgnoreCase("atan") || newValue.equalsIgnoreCase("ctg")) {
                horizontalBox.getChildren().add(actualNodeIndex,new GenericFunction(this, "atan"));
            } else if (newValue.equalsIgnoreCase("csc") || newValue.equalsIgnoreCase("asin")) {
                horizontalBox.getChildren().add(actualNodeIndex,new GenericFunction(this, "asin"));
            } else if (newValue.equalsIgnoreCase("sec") || newValue.equalsIgnoreCase("acos")) {
                horizontalBox.getChildren().add(actualNodeIndex,new GenericFunction(this, "acos"));
            } else if (newValue.equalsIgnoreCase("log")) {
                horizontalBox.getChildren().add(actualNodeIndex,new GenericFunction(this, "log"));
            } else if (symbol.equals("^") || newValue.equalsIgnoreCase("pow")) {
                horizontalBox.getChildren().add(actualNodeIndex,new Pow(this, caller));
            horizontalBox.getChildren().add(new MathInput(this));
            } else if (symbol.equals("/")) {
                horizontalBox.getChildren().add(actualNodeIndex,new Fraction(this));
            } else if (symbol.equals("(") || symbol.equals("{") || symbol.equals("[")) {
                String closeSymbol = ")";
                switch (symbol) {
                    case "{":
                        closeSymbol = "}";
                        break;
                    case "[":
                        closeSymbol = "]";
                        break;
                }
                horizontalBox.getChildren().add(actualNodeIndex,new Aggrupation(this, symbol, closeSymbol));
            }
            horizontalBox.getChildren().add(actualNodeIndex+1, new SymbolLabel("+"));
        }
    }

    /**
     * Carga en una cadena de texto las operaciones transformadas de todas sus 
     * operaciones hijas.
     * @return Cadena de texto con la fórmula.
     */
    @Override
    public String getSymbolOrNumber() {
        String symbols = "";
        for (Node node : horizontalBox.getChildren()) {
            if (node instanceof Symbolic) {
                symbols += ((Symbolic) (node)).getSymbolOrNumber();
            }
        }
        if (!symbols.equals("")) {
            // Si termina en símbolo, se recorta.
            String lastSymbol = symbols.substring(symbols.length() - 1);
            if (lastSymbol.equals("-") || lastSymbol.equals("+")
                    || lastSymbol.equals("*") || lastSymbol.equals("/")) {
                symbols = symbols.substring(0, symbols.length() - 1);
            }
        }
        return symbols;
    }

    /**
     * Función de eliminación propagada en reversa. Intenta buscar la operación 
     * invocante dentro de sus hijos, de no haberlos y tener una seccipon padre,
     * propaga la operaión hacia su sección padre, y repite la operaión hasta 
     * llear a la raíz de ser necesario.
     * @param caller Input invocante de la eliminación.
     */
    @Override
    public void deleteMe(GenericComponent caller) {
        int inputsCount = countInputElements();
        int callerIndex = horizontalBox.getChildren().indexOf(caller);
        int componentsCount = countComponents();
        boolean seccionDeleting = false;
        
        if (caller instanceof Seccion) {
            
            callerIndex = horizontalBox.getChildren().indexOf(((Seccion)caller).getParentComponent());
            horizontalBox.getChildren().remove(((Seccion)caller).getParentComponent());
            if (callerIndex == 0 && horizontalBox.getChildren().size() > 0) {
                //Se elimina el símbolo sucesor
                horizontalBox.getChildren().remove(callerIndex);
            } else if (callerIndex > 1 ) {
                //Se elimina el símbolo antecesor
                horizontalBox.getChildren().remove(callerIndex );
            }
            seccionDeleting = true;
        }
        if (inputsCount > 1 ) {
            if (callerIndex == 0) {
                horizontalBox.getChildren().remove(caller);
                // Se elimina el símbolo sucesor
                horizontalBox.getChildren().remove(callerIndex);
            } else if (callerIndex > 1 && callerIndex + 1 < getHorizontalLayoutChildren().size()) {
                horizontalBox.getChildren().remove(caller);
                // Se elimina el símbolo antecesor
                horizontalBox.getChildren().remove(callerIndex);
            } else {
                horizontalBox.getChildren().remove(caller);
                horizontalBox.getChildren().remove(callerIndex - 1);
            }
        }else if (inputsCount+componentsCount == 1) {
            if (getParentComponent() != null && !seccionDeleting) {
                getParentSeccion().deleteMe(this);
            }
        }

    }

    /**
     * Obtiene los hijos de su hBox (Componentes de operaciones hijas)
     * @return lista de componentes de operaciones hijas.
     */
    public ObservableList<Node> getHorizontalLayoutChildren() {
        return horizontalBox.getChildren();
    }
    
    /**
     * Obtiene el componente complejo padre, en casod e no existir, returna nullñ
     * @return Componente padre de la sección.
     */
    public ComposedComponent getParentComponent(){
        return this.parentComponent;
    }
    
    /**
     * Cuenta los componentes de input de la seción.
     * @return conteo de componentes input hijos.
     */
    private int countInputElements(){
        int n = 0;
        for (Node node : getHorizontalLayoutChildren()) {
             n += node instanceof MathInput ? 1 : 0 ;
             
        }
        return n;
    }
    
    /**
     * Cuenta los componentes cmplejos de la sección.
     * @return conteo de componentes complejos hijos.
     */
    private int countComponents(){
        int n = 0;
        for (Node node : getHorizontalLayoutChildren()) {
             n += node instanceof ComposedComponent ? 1 : 0 ;
        }
        return n;
    }
    
}
