package Modelos.AjusteDeCurvas;

import Modelos.MetodosAbiertos.Bairstow;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Paulo Aguilar
 */
public class RegresionLinealTest {

    private RegresionLineal regresor;
    private static final double delta = 0.001;

    @Test
    public void probrarPrimerCaso() throws Exception {
        System.out.println("\nPrimer caso\n");
        double[] xCordenadas = {1,2,3,4,5,6,7};
        double[] yCordenadas = {0.5, 2.5, 2, 4, 3.5, 6, 5.5};
        double[] pronosticosEsperados = {0.910714286, 1.75, 2.589285714, 3.428571429, 4.267857143, 5.107142857, 5.946428571};
        double desviacionEsperada = 1.94569121;
        double errorEstandar = 0.773443137;
        double coeficienteDeterminacion = 0.86831761;
        double coeficienteCorrelacion = 0.931835613;
        regresor = new RegresionLineal(xCordenadas, yCordenadas);
        regresor.regresionLineal();
        assertEquals(desviacionEsperada, regresor.getDesviacionEstandar(), delta);
        assertEquals(errorEstandar, regresor.getErrorEstandar(), delta);
        assertEquals(coeficienteDeterminacion, regresor.getCoeficienteDeterminacion(), delta);
        assertEquals(coeficienteCorrelacion , regresor.getCoeficienteCorrelacion(), delta);
        for (int i = 0; i < pronosticosEsperados.length; i++) {
            System.out.println("X = "+xCordenadas[i]+" Y = "+regresor.pronosticar(xCordenadas[i]));
            assertEquals(pronosticosEsperados[i], regresor.pronosticar(xCordenadas[i]), delta);
 
        }
    }

    @Test
    public void probrarSegundoCaso() throws Exception {
        System.out.println("\nSegundo caso\n");
        double[] xCordenadas = {2,3,5,7,8};
        double[] yCordenadas = {14,20,32,42,44};
        double[] pronosticosEsperados = {14.93846154,20.09230769,30.4,40.70769231,45.86153846};
        double desviacionEsperada = 13.2211951;
        double errorEstandar = 1.691608247;
        double coeficienteDeterminacion = 0.987722232;
        double coeficienteCorrelacion = 0.993842156;
        regresor = new RegresionLineal(xCordenadas, yCordenadas);
        regresor.regresionLineal();
        assertEquals(desviacionEsperada, regresor.getDesviacionEstandar(), delta);
        assertEquals(errorEstandar, regresor.getErrorEstandar(), delta);
        assertEquals(coeficienteDeterminacion, regresor.getCoeficienteDeterminacion(), delta);
        assertEquals(coeficienteCorrelacion , regresor.getCoeficienteCorrelacion(), delta);
        for (int i = 0; i < pronosticosEsperados.length; i++) {
            System.out.println("X = "+xCordenadas[i]+" Y = "+regresor.pronosticar(xCordenadas[i]));
            assertEquals(pronosticosEsperados[i], regresor.pronosticar(xCordenadas[i]), delta);
        }
    }

}
