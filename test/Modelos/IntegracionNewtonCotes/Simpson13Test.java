package Modelos.IntegracionNewtonCotes;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Geovanny Vega
 */
public class Simpson13Test {

    private Simpson13 simpson13;
    private static final double delta = 0.001;

    @Test
    public void probrarPrimerCaso() throws Exception {
        System.out.println("\nPrimer caso\n");
        String funcion = " x^3 -2*x + 1";
        double x0 = 1;
        double x1 = 2;
        double res = 0;
        double valorEsperado = 1.75;
        simpson13 = new Simpson13(funcion, x0, x1);
        res = simpson13.Simpson13Simple();
        assertEquals(valorEsperado, res, delta);
        System.out.println("El resultado de la integración es: " + res);
    }
    
    @Test
    public void probrarSegundoCaso() throws Exception {
        // Multiple
        System.out.println("\nSegundo caso\n");
        String funcion = " x^3 -2*x + 1";
        double x0 = 1;
        double x1 = 2;
        int n = 4;
        double res = 0;
        double valorEsperado = 1.75;
        simpson13 = new Simpson13(funcion, x0, x1,n);
        res = simpson13.Simpson13MultipleSegmentos();
        assertEquals(valorEsperado, res, delta);
        System.out.println("El resultado de la integración es: " + res);
    }

    @Test
    public void probrarTercerCaso() throws Exception {
        // Multiple
        System.out.println("\nTercer caso\n");
        String funcion = " e^x -x";
        double x0 = 0;
        double x1 = 1;
        double res = 0;
        double valorEsperado = 1.218281828;
        simpson13 = new Simpson13(funcion, x0, x1);
        res = simpson13.Simpson13Simple();
        assertEquals(valorEsperado, res, delta);
        System.out.println("El resultado de la integración es: " + res);
    }
}
