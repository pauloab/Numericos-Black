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
public class NewtonRaphsonTest {

    private NewtonRaphson newtonraphson;
    private static final double delta = 0.001;

    @Test
    public void probrarPrimerCaso() throws Exception {
        String funcion = " x^3-7*x^2+14*x-6 ";
        double valorInicial = 1;
        int numeroIteracionesMaxima = 8;
        double errorTolerancia = 0.01;
        double valorEsperado = 0.5857864376269;
        double resultadoRaizAproximadaMetodoNewtonRaphson = 0;
        newtonraphson = new NewtonRaphson(funcion, valorInicial, numeroIteracionesMaxima, errorTolerancia);
        resultadoRaizAproximadaMetodoNewtonRaphson = newtonraphson.metodoNewtonRaphson();
        System.out.println("Primer Caso:");
        System.out.println("El resultado es: " + resultadoRaizAproximadaMetodoNewtonRaphson);
        assertEquals(valorEsperado, resultadoRaizAproximadaMetodoNewtonRaphson, delta);
        newtonraphson.imprimirResultados();
    }

    @Test
    public void probrarSegundoCaso() throws Exception {
        String funcion = " x^3-7*x^2+14*x-6 ";
        double valorInicial = 2.5;
        int numeroIteracionesMaxima = 8;
        double errorTolerancia = 0.01;
        double valorEsperado = 3;
        double resultadoRaizAproximadaMetodoNewtonRaphson = 0;
        newtonraphson = new NewtonRaphson(funcion, valorInicial, numeroIteracionesMaxima, errorTolerancia);
        resultadoRaizAproximadaMetodoNewtonRaphson = newtonraphson.metodoNewtonRaphson();
        System.out.println("Segundo Caso:");
        System.out.println("El resultado es: " + resultadoRaizAproximadaMetodoNewtonRaphson);
        assertEquals(valorEsperado, resultadoRaizAproximadaMetodoNewtonRaphson, delta);
        newtonraphson.imprimirResultados();
    }

    @Test
    public void probrarTercerCaso() throws Exception {
        String funcion = " x^3-7*x^2+14*x-6 ";
        double valorInicial = 5;
        int numeroIteracionesMaxima = 8;
        double errorTolerancia = 0.01;
        double valorEsperado = 3.4142135623731;
        double resultadoRaizAproximadaMetodoNewtonRaphson = 0;
        newtonraphson = new NewtonRaphson(funcion, valorInicial, numeroIteracionesMaxima, errorTolerancia);
        resultadoRaizAproximadaMetodoNewtonRaphson = newtonraphson.metodoNewtonRaphson();
        System.out.println("Tercer Caso:");
        System.out.println("El resultado es: " + resultadoRaizAproximadaMetodoNewtonRaphson);
        assertEquals(valorEsperado, resultadoRaizAproximadaMetodoNewtonRaphson, delta);
        newtonraphson.imprimirResultados();
    }

}
