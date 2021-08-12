package Modelos.AjusteDeCurvas;

import Util.Matematico;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Paulo Aguilar
 */
public class InterpolacionLagrangeTest {
    
    private InterpolacionLagrange interpolador;
    private static final double delta = 0.001;

    @Test
    public void probrarPrimerCaso() throws Exception {
        System.out.println("\nPrimer caso\n");
        double[] xCordenadas = {3,5};
        double[] yCordenadas = {4,5.6};
        double x = 4;
        double yEsperado = 4.8, yPronostico;
        interpolador = new InterpolacionLagrange(xCordenadas, yCordenadas,x);
        yPronostico = interpolador.interpolacion();
        assertEquals(yEsperado, yPronostico, delta);
        System.out.println("Pronóstico: "+yPronostico);
        System.out.println(interpolador.getFuncion());
        yPronostico = Matematico.evaluarFuncion(interpolador.getFuncion(),x);
        assertEquals(yEsperado, yPronostico, delta);
        System.out.println("Pronóstico por evaluación de función: "+yPronostico);
    }
    
    @Test
    public void probrarSegundoCaso() throws Exception {
        System.out.println("\nSegundo caso\n");
        double[] xCordenadas = {3,5,2};
        double[] yCordenadas = {4,5.6,3.5};
        double x = 4;
        double yEsperado = 4.7, yPronostico;
        interpolador = new InterpolacionLagrange(xCordenadas, yCordenadas,x);
        yPronostico = interpolador.interpolacion();
        assertEquals(yEsperado, yPronostico, delta);
        System.out.println("Pronóstico: "+yPronostico);
        System.out.println(interpolador.getFuncion());
        yPronostico = Matematico.evaluarFuncion(interpolador.getFuncion(),x);
        assertEquals(yEsperado, yPronostico, delta);
        System.out.println("Pronóstico por evaluación de función: "+yPronostico);
    }
}
