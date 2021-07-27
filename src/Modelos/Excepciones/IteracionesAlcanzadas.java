
package Modelos.Excepciones;

/**
 * Excepción que se lanza cuando se han alcanzado el número máximo de iteraciones.
 * @author Javier Matamoros
 */
public class IteracionesAlcanzadas extends Exception{
    public IteracionesAlcanzadas(){
        super("Numero de iteraciones máximas alcanzado.");
    }
}
