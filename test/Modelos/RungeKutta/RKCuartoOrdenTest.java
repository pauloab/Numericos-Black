package Modelos.RungeKutta;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Test del método de Runge-kutta de cuarto orden
 * 
 * @author Paulo Aguilar
 */
public class RKCuartoOrdenTest {
    private RKCuartoOrden RK4;
    private static final double delta = 0.001;

    @Test
    public void probrarPrimerCaso() throws Exception {
        System.out.println("\nPrimer caso\n");
        String EDO = "2y-e^x";
        double x0 = 0;
        double y0 = -1;
        double b = 0.8;
        double h = 0.2;
        String[][] datosAproximados;
        double[] valoresEsperados = {-1.00000,-1.76209,-2.95878,-4.81703,-7.67834};
        RK4 = new RKCuartoOrden(x0, y0,b,EDO,h);
        RK4.RKCuarto();
        datosAproximados = RK4.getDatos();
        for (int i = 1; i < datosAproximados.length; i++) {
            assertEquals(valoresEsperados[i], Double.parseDouble(datosAproximados[i][1]), delta);
            System.out.println("Valor Aproximado: "+datosAproximados[i][1]+
                    " - Valor esperado: "+valoresEsperados[i]);
        }
        RK4.imprimirMatriz();
    }
    
    @Test
    public void probrarSegundoCaso() throws Exception {
        System.out.println("\nSegundo caso\n");
        String EDO = "x-y";
        double x0 = 0;
        double y0 = 0;
        double b = 2;
        double h = 0.5;
        String[][] datosAproximados;
        double[] valoresEsperados = {0.00000,0.10677,0.36817,0.72340,1.13555,1.58225,2.04991,2.53028,3.01837,};
        RK4 = new RKCuartoOrden(x0, y0,b,EDO,h);
        RK4.RKCuarto();
        datosAproximados = RK4.getDatos();
        for (int i = 1; i < datosAproximados.length; i++) {
            assertEquals(valoresEsperados[i], Double.parseDouble(datosAproximados[i][1]), delta);
            System.out.println("Valor Aproximado: "+datosAproximados[i][1]+
                    " - Valor esperado: "+valoresEsperados[i]);
        }
        RK4.imprimirMatriz();
    }
    
    @Test
    public void probrarTercerCaso() throws Exception {
        System.out.println("\nTercer caso\n");
        String EDO = "-2*x*y";
        double x0 = 0;
        double y0 = 2;
        double b = 3;
        double h = 0.25;
        String[][] datosAproximados;
        double[] valoresEsperados = {
        2.00000,
1.87882,
1.55759,
1.13957,
0.73587,
0.41956,
0.21142,
0.09434,
0.03742,
0.01328,
0.00427,
0.00126,
0.00035,

        };
        RK4 = new RKCuartoOrden(x0, y0,b,EDO,h);
        RK4.RKCuarto();
        datosAproximados = RK4.getDatos();
        RK4.imprimirMatriz();
        for (int i = 1; i < datosAproximados.length; i++) {
            assertEquals(valoresEsperados[i], Double.parseDouble(datosAproximados[i][1]), delta);
            System.out.println("Valor Aproximado: "+datosAproximados[i][1]+
                    " - Valor esperado: "+valoresEsperados[i]);
        }
        
    }
}
