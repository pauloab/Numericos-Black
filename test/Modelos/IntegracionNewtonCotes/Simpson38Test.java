
package Modelos.IntegracionNewtonCotes;


import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Geovanny Vega
 */
public class Simpson38Test {
        private Simpson38 simpson38;
    private static final double delta = 0.001;

    @Test
    public void probrarPrimerCaso() throws Exception {
        System.out.println("\nPrimer caso\n");
        String funcion = " x^3 -2*x + 1";
        double x0 = 1;
        double x3 = 2;
        double res = 0;
        double valorEsperado = 1.75;
        simpson38 = new Simpson38(funcion, x0,x3);
        res = simpson38.Simpson38Simple();
        assertEquals(valorEsperado, res, delta);
        System.out.println("El resultado de la integración es: " + res);
    }
   
        @Test
    public void probrarSegundoCaso() throws Exception {
        System.out.println("\nSegundo caso\n");
        String funcion = " ln(x)";
        double x0 = 0.5;
        double x3 = 1;
        double res = 0;
        double valorEsperado = -0.1534264097;
        simpson38 = new Simpson38(funcion, x0,x3);
        res = simpson38.Simpson38Simple();
        assertEquals(valorEsperado, res, delta);
        System.out.println("El resultado de la integración es: " + res);
    }
    
}
