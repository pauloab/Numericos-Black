
package Modelos.Excepciones;

/**
 * Excepcion que se lanza cuando el método ha llegado a su máxima aproximación posible.
 * @author Javier Matamoros
 */
public class ValoresResueltos  extends Exception{
    public ValoresResueltos(){
        super("Se han resuelto los valores a su mínima expresión.");
    }
}
