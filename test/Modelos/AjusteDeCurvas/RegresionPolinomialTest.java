package Modelos.AjusteDeCurvas;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


/**
 * 
 * @author Paulo Aguilar
 */
public class RegresionPolinomialTest {
    private RegresionCuadratica regresor;
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
        regresor = new RegresionCuadratica(xCordenadas, yCordenadas, 2);
        regresor.regresionPolinomial();
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
        System.out.println("\nSegundoCaso\n");
        double[] xCordenadas = {273,283,293,303,313,323,333,343,353,363,373};
        double[] yCordenadas = {1.00738,1.00129,0.99883,0.99802,0.998004,0.99854,0.99943,1.00067,1.00229,1.00437,1.00697};
        double[] coeficientesEsperados = {1.347032903635,-0.002185998788,0.000003422424};
        double[] pronosticosEsperados = {1.005325091, 1.002493782, 1.000346958,0.998884618,0.998106764,0.998013394,0.998604509,0.999880109,1.001840194,1.004484764,1.007813818};
        double desviacionEsperada = 0.003443135;
        double errorEstandar = 0.001185392;
        double coeficienteDeterminacion = 0.905178757;
        double coeficienteCorrelacion = 0.951408827;
        regresor = new RegresionCuadratica(xCordenadas, yCordenadas, 2);
        regresor.regresionPolinomial();
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
}
