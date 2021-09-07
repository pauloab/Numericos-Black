package Modelos.GaussLegendre;

import Modelos.MetodosAproxRaices.Biseccion;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Test de cuadratura de Gauss
 * 
 * @author Freddy Lamar
 */
public class CuadraturaGaussTest {
    
    private CuadraturaGauss cGauss;
    private static final double delta = 0.001;

    @Test   
    public void probrarPrimerCaso() throws Exception {
        String funcion = "ln(x)";
        double a = 1;   
        double b = 2;
        int n = 2;
        double valorEsperado = 0.386300422;
        double resultadoRaizAproximada;
        cGauss = new CuadraturaGauss(funcion, a, b, n);
        resultadoRaizAproximada = cGauss.cuadraturaDeGauss();
        System.out.println("Primer Caso:");
        System.out.println("El resultado es: " + resultadoRaizAproximada);
        assertEquals(valorEsperado, resultadoRaizAproximada, delta);
    }
    
    @Test   
    public void probrarTercerCaso() throws Exception {
        String funcion = "ln(x)";
        double a = 1;   
        double b = 2;
        int n = 3;
        double valorEsperado = 0.386594944;
        double resultadoRaizAproximada;
        cGauss = new CuadraturaGauss(funcion, a, b, n);
        resultadoRaizAproximada = cGauss.cuadraturaDeGauss();
        System.out.println("Tercer Caso:");
        System.out.println("El resultado es: " + resultadoRaizAproximada);
        assertEquals(valorEsperado, resultadoRaizAproximada, delta);
    }
    
    @Test   
    public void probrarSegundoCaso() throws Exception {
        String funcion = "(exp(x)-x)";
        double a = 0;   
        double b = 1;
        int n = 3;
        double valorEsperado = 1.217896378;
        double resultadoRaizAproximada;
        cGauss = new CuadraturaGauss(funcion, a, b, n);
        resultadoRaizAproximada = cGauss.cuadraturaDeGauss();
        System.out.println("Segundo Caso:");
        System.out.println("El resultado es: " + resultadoRaizAproximada);
        assertEquals(valorEsperado, resultadoRaizAproximada, delta);
    }
}
