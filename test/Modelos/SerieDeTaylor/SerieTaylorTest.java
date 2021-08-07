/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.SerieDeTaylor;

import Modelos.SerieDeTaylor.SerieTaylor;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Paulo Aguilar
 */
public class SerieTaylorTest {
   private SerieTaylor taylor;
    private static final double delta = 0.001;

    @Test
    public void probrarPrimerCaso() throws Exception {
        String funcion = "exp(x)";
        double x = 1;
        double tamanoPaso = 1;
        int numeroIteracionesMaxima = 10;
        double errorTolerancia = 0.0005;
        double valorVerdadero  = 2.7182818284591;
        double resultado = 0;
        taylor = new SerieTaylor(funcion, errorTolerancia, numeroIteracionesMaxima, x, tamanoPaso);
        resultado = taylor.SerieTaylor();
        System.out.println("Primer Caso:");
        System.out.println("El resultado es: " + resultado);
        taylor.imprimirResultados();
        assertEquals(valorVerdadero, resultado, delta);
    }

    @Test
    public void probarSegundoCaso() throws Exception {
        String funcion = " 1/(1-x) ";
        double x = 0.15;
        double tamanoPaso = 0.15;
        int numeroIteracionesMaxima = 10;
        double errorTolerancia = 0.0005;
        double valorVerdadero  = 1.1764707882352900;
        double resultado = 0;
        taylor = new SerieTaylor(funcion, errorTolerancia, numeroIteracionesMaxima, x, tamanoPaso);
        resultado = taylor.SerieTaylor();
        System.out.println("Segundo Caso:");
        System.out.println("El resultado es: " + resultado);
        taylor.imprimirResultados();
        assertEquals(valorVerdadero, resultado, delta);
    }
}
