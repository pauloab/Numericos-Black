/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.MetodosAbiertos;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Geovanny Vega
 */
public class MullerTest {

    private Muller muller;
    private static final double delta = 0.001;

    @Test
    public void probrarPrimerCaso() throws Exception {
        String funcion = " -3*x^5+2*x^4+15*x^2-10*x-1 ";
        double xSub2 = 1;
        double xSub1 = 2;
        double xSub0 = 3;
        int numeroIteracionesMaxima = 10;
        double errorTolerancia = 0.0005;
        double valorEsperado = 0.762596089785;
        double resultadoRaizAproximadaMetodoMuller = 0;
        muller = new Muller(funcion, xSub2, xSub1, xSub0, numeroIteracionesMaxima, errorTolerancia);
        resultadoRaizAproximadaMetodoMuller = muller.metodoMuller();
        System.out.println("Primer Caso:");
        System.out.println("El resultado es: " + resultadoRaizAproximadaMetodoMuller);
        assertEquals(valorEsperado, resultadoRaizAproximadaMetodoMuller, delta);
        muller.imprimirResultados();
    }

    @Test
    public void probrarSegundoCaso() throws Exception {
        String funcion = " x^3+9*x^2+26*x+24 ";
        double xSub2 = -5.5;
        double xSub1 = -3.5;
        double xSub0 = -4.5;
        int numeroIteracionesMaxima = 10;
        double errorTolerancia = 0.0005;
        double valorEsperado = -4;
        double resultadoRaizAproximadaMetodoMuller = 0;
        muller = new Muller(funcion, xSub2, xSub1, xSub0, numeroIteracionesMaxima, errorTolerancia);
        resultadoRaizAproximadaMetodoMuller = muller.metodoMuller();
        System.out.println("Segundo Caso:");
        System.out.println("El resultado es: " + resultadoRaizAproximadaMetodoMuller);
        assertEquals(valorEsperado, resultadoRaizAproximadaMetodoMuller, delta);
        muller.imprimirResultados();
    }

}
