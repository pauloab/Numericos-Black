
package Modelos.RungeKutta;

import static org.junit.Assert.assertEquals;
import org.junit.Test;



/**
 * Test de prueba del metodo de Heun
 * 
 * @author Javier Matamoros
 * 
 */
public class HeunTest {
    
    private Heun heun;
    private static final double delta = 0.001;
    
    @Test
    public void probarPrimerCaso() throws Exception{
        System.out.println("\nPrimer Caso\n");
        String EDO = "x-y";
        double x0 = 0;
        double y0 = 0;
        double b = 2;
        double h = 0.5;
        int itermax = 7;
        double errorTolerancia = 0.001;
        double[] valoresEsperados = {0.000000000,
            0.125000000,
            0.093750000,
            0.101562500,
            0.099609375,
            0.100097656,
            0.099975586,
            0.100006104,
            0.099998474,
            0.374999046,
            0.356249094,
            0.360936582,
            0.359764710,
            0.360057678,
            0.359984436,
            0.360002747,
            0.359998169,
            0.724998856,
            0.713748913,
            0.716561399,
            0.715858277,
            0.716034057,
            0.715990112,
            0.716001099,
            0.715998352,
            1.134998970,
            1.128249022,
            1.129936509,
            1.129514637,
            1.129620105,
            1.129593738,
            1.129600330,
            1.129598682,
            1.580999176,
            1.576949217,
            1.577961707,
            1.577708585,
            1.577771865,
            1.577756045,
            1.577760000,
            1.577759011,
            2.048599382,
            2.530374614,
            3.018984134};
        heun = new Heun(x0,y0,h,EDO,b,itermax,errorTolerancia);
        heun.heun();
        String[][] datosAproximados = heun.getDatos();
        heun.imprimirMatriz();
        for (int i = 1; i < datosAproximados.length; i++) {
            assertEquals(valoresEsperados[i], Double.parseDouble(datosAproximados[i][4]), delta);
            System.out.println("Valor Aproximado: "+datosAproximados[i][4]+
                    " - Valor esperado: "+valoresEsperados[i]);
        }
    }
    @Test
    public void probarSegundoCaso() throws Exception {
        System.out.println("\nSegundo Caso\n");
        String EDO = "2x+y";
        double x0 = 0;
        double y0 = 1;
        double b = 1.25;
        double h = 0.25;
        int itermax = 8;
        double errorTolerancia = 0.00001;
        double[] valoresEsperados = {1.000000000,
            1.343750000,
            1.355468750,
            1.356933594,
            1.357116699,
            1.357139587,
            1.357142448,
            1.357142806,
            1.357142851,
            1.941964278,
            1.957031242,
            1.958914612,
            1.959150034,
            1.959179461,
            1.959183140,
            1.959183600,
            1.959183657,
            2.853954061,
            2.873325872,
            2.875747348,
            2.876050033,
            2.876087868,
            2.876092598,
            2.876093189,
            2.876093263,
            4.169369493,
            4.194276107,
            4.197389434,
            4.197778600,
            4.197827246,
            4.197833326,
            4.197834086,
            4.197834181,
            6.003475045,
            6.035497835,
            6.039500683,
            6.040001040,
            6.040063584,
            6.040071402,
            6.040072379,
            6.040072502,
            8.504467893,
            11.802599487,
            16.168955593};
        heun = new Heun(x0, y0, h, EDO, b, itermax, errorTolerancia);
        heun.heun();
        String[][] datosAproximados = heun.getDatos();
        heun.imprimirMatriz();
        for (int i = 1; i < datosAproximados.length; i++) {
            assertEquals(valoresEsperados[i], Double.parseDouble(datosAproximados[i][4]), delta);
            System.out.println("Valor Aproximado: " + datosAproximados[i][4]
                    + " - Valor esperado: " + valoresEsperados[i]);
        }
    }

}
