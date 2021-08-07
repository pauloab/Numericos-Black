
package Modelos.MetodosAproxRaices;


import Modelos.MetodosAproxRaices.PuntoFijo;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Freddy Lamar
 */
public class PuntoFijoTest {
    
   private PuntoFijo puntoFijo;
   private static final double delta = 0.001;
     @Test
    public void probrarPrimerCaso() throws Exception {
        String funcion = " exp(-x)";
        double valorInicial = 0;
        int numeroIteracionesMaxima = 30;
        double errorTolerancia = 0.0005;
        double valorEsperado = 0.567143316130232;
        double resultadoRaizAproximadaMetodoPuntoFijo = 0;
        puntoFijo = new PuntoFijo(funcion, errorTolerancia, numeroIteracionesMaxima, valorInicial);
        resultadoRaizAproximadaMetodoPuntoFijo = puntoFijo.metodoPuntoFijo();
        System.out.println("Primer Caso:");
        System.out.println("El resultado es: " + resultadoRaizAproximadaMetodoPuntoFijo);
        assertEquals(valorEsperado, resultadoRaizAproximadaMetodoPuntoFijo, delta);
        puntoFijo.imprimirResultados();
    }

    @Test
    public void probrarSegundoCaso() throws Exception {
      String funcion = " 0.4*exp(x^2)";
        double valorInicial = 0;
        int numeroIteracionesMaxima = 30;
        double errorTolerancia = 0.01;
        double valorEsperado = 0.5293814825897;
        double resultadoRaizAproximadaMetodoPuntoFijo = 0;
        puntoFijo = new PuntoFijo(funcion, errorTolerancia, numeroIteracionesMaxima, valorInicial);
        resultadoRaizAproximadaMetodoPuntoFijo = puntoFijo.metodoPuntoFijo();
        System.out.println("Segundo Caso:");
        System.out.println("El resultado es: " + resultadoRaizAproximadaMetodoPuntoFijo);
        assertEquals(valorEsperado, resultadoRaizAproximadaMetodoPuntoFijo, delta);
        puntoFijo.imprimirResultados();
    }
}
