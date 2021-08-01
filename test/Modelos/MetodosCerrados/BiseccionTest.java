
package Modelos.MetodosCerrados;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Geovanny Vega
 */
public class BiseccionTest {

    private Biseccion biseccion;
    private static final double delta = 0.001;

    @Test
    public void probrarPrimerCaso() throws Exception {
        String funcion = "x^3 - 7*x^2 + 14*x - 6 ";
        double cotaInferior = 1;
        double cotaSuperior = 3.2;
        int numeroIteracionesMaxima = 20;
        double errorTolerancia = 0.01;
        double valorEsperado = 3;
        double resultadoRaizAproximadaMetodoBiseccion = 0;
        biseccion = new Biseccion(funcion, errorTolerancia, numeroIteracionesMaxima, cotaInferior, cotaSuperior);
        resultadoRaizAproximadaMetodoBiseccion = biseccion.metodoBiseccion();
        System.out.println("Primer Caso:");
        System.out.println("El resultado es: " + resultadoRaizAproximadaMetodoBiseccion);
        assertEquals(valorEsperado, resultadoRaizAproximadaMetodoBiseccion, delta);
        biseccion.imprimirResultados();
    }

    @Test
    public void probrarSegundoCaso() throws Exception {
        String funcion = "x^3 - 7*x^2 + 14*x - 6 ";
        double cotaInferior = 0;
        double cotaSuperior = 2;
        int numeroIteracionesMaxima = 20;
        double errorTolerancia = 0.01;
        double valorEsperado = 0.5857864376269;
        double resultadoRaizAproximadaMetodoBiseccion = 0;
        biseccion = new Biseccion(funcion, errorTolerancia, numeroIteracionesMaxima, cotaInferior, cotaSuperior);
        resultadoRaizAproximadaMetodoBiseccion = biseccion.metodoBiseccion();
        System.out.println("Segundo Caso:");
        System.out.println("El resultado es: " + resultadoRaizAproximadaMetodoBiseccion);
        assertEquals(valorEsperado, resultadoRaizAproximadaMetodoBiseccion, delta);
        biseccion.imprimirResultados();
    }

    @Test
    public void probrarTercerCaso() throws Exception {
        String funcion = "x^3 - 7*x^2 + 14*x - 6 ";
        double cotaInferior = 3.1;
        double cotaSuperior = 5;
        int numeroIteracionesMaxima = 20;
        double errorTolerancia = 0.01;
        double valorEsperado = 3.4142135623731;
        double resultadoRaizAproximadaMetodoBiseccion = 0;
        biseccion = new Biseccion(funcion, errorTolerancia, numeroIteracionesMaxima, cotaInferior, cotaSuperior);
        resultadoRaizAproximadaMetodoBiseccion = biseccion.metodoBiseccion();
        System.out.println("Tecer Caso:");
        System.out.println("El resultado es: " + resultadoRaizAproximadaMetodoBiseccion);
        assertEquals(valorEsperado, resultadoRaizAproximadaMetodoBiseccion, delta);
        biseccion.imprimirResultados();
    }

    @Test
    public void probrarCautoCaso() throws Exception {
        String funcion = "(4-3)*(x^2)*x - 7*(1)*x*x + 14*(x^3)*(x^-1) - 9 + 3 ";
        System.out.println(funcion);
        String res = "";
        res = Util.MetodosUniversales.simplificarExpresion(funcion);
         System.out.println("Caurto Caso:"+ res);
        
    }

}
