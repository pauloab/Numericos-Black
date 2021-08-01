
package Modelos.MetodosAbiertos;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Geovanny Vega
 */
public class SecanteTest {

    private Secante secante;
    private static final double delta = 0.001;

    @Test
    public void probrarPrimerCaso() throws Exception {
        String funcion = "(e^(-x)) - x - 2";
        double xSubI = 1.5;
        double xSubIMenos1 = 2;
        int numeroIteracionesMaxima = 10;
        double errorTolerancia = 0.005;
        double valorEsperado = -0.4428544010371;
        double resultadoRaizAproximadaMetodoSecante = 0;
        secante = new Secante(funcion,errorTolerancia,numeroIteracionesMaxima, xSubI, xSubIMenos1);
        resultadoRaizAproximadaMetodoSecante = secante.metodoSecante();
        System.out.println("Primer Caso:");
        System.out.println("El resultado es: " + resultadoRaizAproximadaMetodoSecante);
        assertEquals(valorEsperado, resultadoRaizAproximadaMetodoSecante, delta);
        secante.imprimirResultados();
    }

    @Test
    public void probrarSegundoCaso() throws Exception {
        String funcion = "(e^(-x)) - x - 2";
        double xSubI = -3;
        double xSubIMenos1 = -2;
        int numeroIteracionesMaxima = 10;
        double errorTolerancia = 0.005;
        double valorEsperado = -0.4428544010371;
        double resultadoRaizAproximadaMetodoSecante = 0;
        secante = new Secante(funcion,errorTolerancia,numeroIteracionesMaxima, xSubI, xSubIMenos1);
        resultadoRaizAproximadaMetodoSecante = secante.metodoSecante();
        System.out.println("Segundo Caso:");
        System.out.println("El resultado es: " + resultadoRaizAproximadaMetodoSecante);
        assertEquals(valorEsperado, resultadoRaizAproximadaMetodoSecante, delta);
        secante.imprimirResultados();
    }

}
