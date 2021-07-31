package Vistas.Components.Math;

import javafx.scene.Group;

/**
 * Componente generico no complejo.
 * @author Paulo Aguilar
 */
public abstract class GenericComponent extends Group implements Symbolic{

    private Seccion parentSeccion;
    
    /**
     * Constructor de la clase
     */
    public GenericComponent(){
        super();
        this.parentSeccion = null;
    }
    
    /**
     * Constructor de la clase
     * @param parentSeccion Sección padre de este componente
     */
    public GenericComponent(Seccion parentSeccion){
        super();
        this.parentSeccion = parentSeccion;
    }
   
    public Seccion getParentSeccion(){
        return this.parentSeccion;
    }
    
    public void setParentSeccion(Seccion seccion){
        this.parentSeccion  = seccion;
    }
}
