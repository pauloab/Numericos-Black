package Modelos.RungeKutta;

import Modelos.Interpolacion.InterpolacionLagrange;
import Util.Matematico;
import static org.junit.Assert.assertEquals;
import org.junit.Test;


/**
 * Test para el m[etodo de Euler
 * 
 * @author Paulo Aguilar
 */
public class EulerTest {
    
    private Euler euler;
    private static final double delta = 0.001;

    @Test
    public void probrarPrimerCaso() throws Exception {
        System.out.println("\nPrimer caso\n");
        String EDO = "x-y";
        double x0 = 0;
        double y0 = 0;
        double b = 2;
        double h = 0.5;
        String[][] datosAproximados;
        double[] valoresEsperados = { 0.00000, 0.00000, 0.25000,0.62500,1.06250,1.53125,2.01563,2.50781,3.00391};
        euler = new Euler(x0, y0,b,EDO,h);
        datosAproximados = euler.euler();
        for (int i = 1; i < datosAproximados.length; i++) {
            assertEquals(valoresEsperados[i], Double.parseDouble(datosAproximados[i][2]), delta);
            System.out.println("Valor Aproximado: "+datosAproximados[i][2]+
                    " - Valor esperado: "+valoresEsperados[i]);
        }
        euler.imprimirMatriz();
    }
    
    @Test
    public void probrarSegundoCaso() throws Exception {
        System.out.println("\nSegundo caso\n");
        String EDO = "-0.06sqrt(y)";
        double x0 = 0;
        double y0 = 3;
        double b = 5;
        double h = 0.5;
        String[][] datosAproximados;
   

        double[] valoresEsperados = { 3.00000,2.94804,2.89653,2.84547,2.79487,2.74471,2.69501,2.64576,2.59696,2.54862,2.50073};
        
        euler = new Euler(x0, y0,b,EDO,h);
        datosAproximados = euler.euler();
        for (int i = 1; i < datosAproximados.length; i++) {
            assertEquals(valoresEsperados[i], Double.parseDouble(datosAproximados[i][2]), delta);
            System.out.println("Valor Aproximado: "+datosAproximados[i][2]+
                    " - Valor esperado: "+valoresEsperados[i]);
        }
        euler.imprimirMatriz();
    }
}
