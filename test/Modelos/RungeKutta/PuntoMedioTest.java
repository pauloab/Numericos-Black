
package Modelos.RungeKutta;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


/**
 *Test de prueba de ejercicios del metodo de Punto Medio
 * 
 * @author Javier Matamoros
 */
public class PuntoMedioTest {
    
    private PuntoMedio punto;
    private static final double delta = 0.001;
    @Test
    public void probarPrimerCaso() throws Exception{
    double x0 = 0;
    double y0 = 1;
    double b = 2;
    String EDO = "2x+y";
    double h = 0.25;
        double[] valoresEsperados = {1.37500,
            2.33356,
            3.56436,
            5.14473,
            7.17396,
            9.77952,
            13.12510,
            17.42089,
            22.93675,};
        String[][] datosAproximados;
        punto = new PuntoMedio(x0, y0, b, EDO, h);
        punto.puntoMedio();
        datosAproximados = punto.getDatos();
        punto.imprimirMatriz();
        for (int i = 1; i < datosAproximados.length; i++) {
            assertEquals(valoresEsperados[i], Double.parseDouble(datosAproximados[i][2]), delta);
            System.out.println("Valor Aproximado: " + datosAproximados[i][2]+
                    " - Valor esperado: "+valoresEsperados[i]);
        }
       
        
    }
    
    
    @Test
    public void probarSegundoCaso(){
        
    }
    
    
}
