package Modelos.IntegracionNewtonCotes;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Geovanny Vega
 */
public class ReglaTrapecioTest {

    private ReglaTrapecio reglaTrapecio;
    private static final double delta = 0.001;

    @Test
    public void probrarPrimerCaso() throws Exception {
        System.out.println("\nPrimer caso\n");
        String funcion = " ln(x) ";
        double x0 = 0.5;
        double x1 = 1;
        int n = 3;
        double res = 0;
        double valorEsperado = -0.155726792;
        reglaTrapecio = new ReglaTrapecio(funcion, x0, x1, n);
        res = reglaTrapecio.trapecioSegmentosMultiples();
        assertEquals(valorEsperado, res, delta);
        System.out.println("El resultado de la integración es: " + res);

    }

    @Test
    public void probrarSegundaCaso() throws Exception {
        System.out.println("\nSegunda caso\n");
        String funcion = " ln(x) ";
        double x0 = 0.5;
        double x1 = 1;
        double res = 0;
        double valorEsperado = -0.1732867951;
        reglaTrapecio = new ReglaTrapecio(funcion, x0, x1);
        res = reglaTrapecio.trapecioSimple();
        assertEquals(valorEsperado, res, delta);
        System.out.println("El resultado de la integración es: " + res);
    }

    @Test
    public void probrarTercerCaso() throws Exception {
        System.out.println("\nTercer caso\n");
        String funcion = " sin(x)*cos(x) ";
        double x0 = 0;
        double x1 = 4;
        int n = 5;
        double res = 0;
        double valorEsperado = 0.22250527158;
        reglaTrapecio = new ReglaTrapecio(funcion, x0, x1,n);
        res = reglaTrapecio.trapecioSegmentosMultiples();
        assertEquals(valorEsperado, res, delta);
        System.out.println("El resultado de la integración es: " + res);
    }
}
