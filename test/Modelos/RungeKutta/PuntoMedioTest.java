package Modelos.RungeKutta;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Test de prueba de ejercicios del metodo de Punto Medio
 *
 * @author Javier Matamoros
 */
public class PuntoMedioTest {

    private PuntoMedio punto;
    private static final double delta = 0.001;

    @Test
    public void probarPrimerCaso() throws Exception {
        System.out.println("\nPrimer caso\n");
        double x0 = 0;
        double y0 = 1;
        double b = 2;
        String EDO = "2x+y";
        double h = 0.25;
        double[] valoresEsperados = {1.3750000000,
            2.3242187500,
            3.5404052734,
            5.0986442566,
            7.0951379538,
            9.6531455033,
            12.9305926760,
            17.1298218662,
            22.5100842660,};
        String[][] datosAproximados;
        punto = new PuntoMedio(x0, y0, b, EDO, h);
        punto.puntoMedio();
        datosAproximados = punto.getDatos();
        punto.imprimirMatriz();
        for (int i = 0; i < datosAproximados.length; i++) {
            assertEquals(valoresEsperados[i], Double.parseDouble(datosAproximados[i][2]), delta);
            System.out.println("Valor Aproximado: " + datosAproximados[i][2]
                    + " - Valor esperado: " + valoresEsperados[i]);
        }
    }

    @Test
    public void probarSegundoCaso() throws Exception {
        System.out.println("\nSegundo caso\n");
        double x0 = 0;
        double y0 = -1;
        double b = 0.8;
        String EDO = "2y-e^x";
        double h = 0.2;
        double[] valoresEsperados = {-3.70517,
            -5.77262,
            -8.89643,
            -13.59780,
            -20.65128
        };
        String[][] datosAproximados;
        punto = new PuntoMedio(x0, y0, b, EDO, h);
        punto.puntoMedio();
        datosAproximados = punto.getDatos();
        punto.imprimirMatriz();
        for (int i = 0; i < datosAproximados.length; i++) {
            assertEquals(valoresEsperados[i], Double.parseDouble(datosAproximados[i][2]), delta);
            System.out.println("Valor Aproximado: " + datosAproximados[i][2]
                    + " - Valor esperado: " + valoresEsperados[i]);
        }
    }

}
