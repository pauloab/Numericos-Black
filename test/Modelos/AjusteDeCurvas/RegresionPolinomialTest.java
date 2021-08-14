package Modelos.AjusteDeCurvas;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


/**
 * 
 * @author Paulo Aguilar
 */
public class RegresionPolinomialTest {
    private RegresionPolinomial regresor;
    private static final double delta = 0.001;

    @Test
    public void probrarPrimerCaso() throws Exception {
        System.out.println("\nPrimer caso\n");
        double[] xCordenadas = {0,1,2,3,4,5};
        double[] yCordenadas = {2.1, 7.7,13.6, 27.2, 40.9, 61.1};
        double[] coeficientesEsperados = {2.478571428571,2.359285714286,1.860714285714};
        double[] pronosticosEsperados = {2.478571429,6.698571429, 14.64, 26.30285714, 41.68714286, 60.79285714};
        double desviacionEsperada = 22.42049657;
        double errorEstandar = 0.865629416;
        double coeficienteDeterminacion = 0.998509357;
        double coeficienteCorrelacion = 0.999254401;
        regresor = new RegresionPolinomial(xCordenadas, yCordenadas, 2);
        regresor.regresionLineal();
        assertEquals(desviacionEsperada, regresor.getDesviacionEstandar(), delta);
        assertEquals(errorEstandar, regresor.getErrorEstandar(), delta);
        assertEquals(coeficienteDeterminacion, regresor.getCoeficienteDeterminacion(), delta);
        assertEquals(coeficienteCorrelacion , regresor.getCoeficienteCorrelacion(), delta);
        for (int i = 0; i < pronosticosEsperados.length; i++) {
            System.out.println("X = "+xCordenadas[i]+" Y = "+regresor.pronosticar(xCordenadas[i]));
            assertEquals(pronosticosEsperados[i], regresor.pronosticar(xCordenadas[i]), delta);
        }
        for (int i = 0; i < coeficientesEsperados.length; i++) {
            assertEquals(coeficientesEsperados[i], regresor.getCoeficientesResultantes()[i], delta);
        }
    }
    
    @Test
    public void probrarSegundoCaso() throws Exception {
        System.out.println("\nPrimer caso\n");
        double[] xCordenadas = {0,2,3,5};
        double[] yCordenadas = {-1,0,2,5};
        double[] coeficientesEsperados = {-0.266667,1.83333,-2.1};
        double[] pronosticosEsperados = {2.478571429,6.698571429, 14.64, 26.30285714, 41.68714286, 60.79285714};
        //double desviacionEsperada = 22.42049657;
        //double errorEstandar = 0;
        double coeficienteDeterminacion = 1;
        double coeficienteCorrelacion = 1;
        regresor = new RegresionPolinomial(xCordenadas, yCordenadas, 3);
        regresor.regresionLineal();
        //assertEquals(desviacionEsperada, regresor.getDesviacionEstandar(), delta);
        //assertEquals(errorEstandar, regresor.getErrorEstandar(), delta);
        assertEquals(coeficienteDeterminacion, regresor.getCoeficienteDeterminacion(), delta);
        assertEquals(coeficienteCorrelacion , regresor.getCoeficienteCorrelacion(), delta);
        /*for (int i = 0; i < pronosticosEsperados.length; i++) {
            System.out.println("X = "+xCordenadas[i]+" Y = "+regresor.pronosticar(xCordenadas[i]));
            assertEquals(pronosticosEsperados[i], regresor.pronosticar(xCordenadas[i]), delta);
        }*/
        for (int i = 0; i < coeficientesEsperados.length; i++) {
            assertEquals(coeficientesEsperados[i], regresor.getCoeficientesResultantes()[i], delta);
        }
    }
}
