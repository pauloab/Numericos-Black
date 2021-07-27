/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.MetodosCerrados;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Geovanny Vega
 */
public class FalsaPosicionTest {

    private static FalsaPosicion falsaposicion;
    private static final double delta = 0.001;

    @Test
    public void probrarPrimerCaso() throws Exception {
        String funcion = " (pi*x^2*(9-x)/3)-30 ";
        double cotaInferior = 0;
        double cotaSuperior = 3;
        int numeroIteracionesMaxima = 10;
        double errorTolerancia = 0.1;
        double valorEsperado = 2.02690572831;
        double resultadoRaizAproximadaMetodoFalsaPosicion = 0;
        falsaposicion = new FalsaPosicion(funcion, errorTolerancia, numeroIteracionesMaxima, cotaInferior, cotaSuperior );
        resultadoRaizAproximadaMetodoFalsaPosicion = falsaposicion.metodoFalsaPosicion();
        System.out.println("Primer Caso:");
        System.out.println("El resultado es: " + resultadoRaizAproximadaMetodoFalsaPosicion);
        assertEquals(valorEsperado, resultadoRaizAproximadaMetodoFalsaPosicion, delta);
        falsaposicion.imprimirResultados();
    }

    @Test
    public void probrarSegundoCaso() throws Exception {
        String funcion = " (pi*x^2*(9-x)/3)-30 ";
        double cotaInferior = -2.5;
        double cotaSuperior = -1;
        int numeroIteracionesMaxima = 20;
        double errorTolerancia = 0.01;
        double valorEsperado = -1.6408123847858;
        double resultadoRaizAproximadaMetodoFalsaPosicion = 0;
        falsaposicion = new FalsaPosicion(funcion, errorTolerancia, numeroIteracionesMaxima, cotaInferior, cotaSuperior );
        resultadoRaizAproximadaMetodoFalsaPosicion = falsaposicion.metodoFalsaPosicion();
        System.out.println("Segundo Caso:");
        System.out.println("El resultado es: " + resultadoRaizAproximadaMetodoFalsaPosicion);
        assertEquals(valorEsperado, resultadoRaizAproximadaMetodoFalsaPosicion, delta);
        falsaposicion.imprimirResultados();
    }

}
