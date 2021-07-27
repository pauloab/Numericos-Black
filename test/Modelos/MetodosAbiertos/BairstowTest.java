
package Modelos.MetodosAbiertos;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Javier Matamoros
 */
public class BairstowTest {
    
    private Bairstow bairstow;
    private static final double delta = 0.001;

    @Test
    public void probrarPrimerCaso() throws Exception {
        System.out.println("\nPrimer caso\n");
        String funcion = " (x^5)-3.5*(x^4)+2.75*(x^3)+2.125*(x^2)-3.875*x+1.25 ";
        double r = -1;
        double s = -1;
        int grado = 5;
        int numeroIteracionesMaxima = 50;
        double errorTolerancia = 1;
        double[] valoresEsperados = { 2.00708769652864,1.00002550154853,1.00002550154853,-1.000000036584600,0.499999971320072 };
        double[] a = {1.25,-3.875,2.125,2.75,-3.5,1};
        bairstow = new Bairstow(funcion,r,s,grado,a,numeroIteracionesMaxima,errorTolerancia);
        String[] resultados = null;
        String resultado;
        
        for (int i = 0; i < 3; i++) {
            resultados = bairstow.bairstowN();
            resultado = resultados[0]+" "+resultados[1];
            System.out.println(resultado);
        }
        
        for (int i = 0; i < grado; i++) {
            assertEquals(valoresEsperados[i], bairstow.getRaicesReales()[i],delta);
        }        
    }

    @Test
    public void probrarSegundoCaso() throws Exception {
        System.out.println("\nSegundo caso\n");
        String funcion = " (x^6)+(x^5)-3.5*(x^4)+2.75*(x^3)+2.125*(x^2)-3.875*x+1.25 ";
        double r = 1;
        double s = 1;
        int grado = 6;
        int numeroIteracionesMaxima = 50;
        double errorTolerancia = 1;
        double[] valoresEsperados = { -2.55893986436024,-1.06861218909067,0.683222437709076,0.683222437709076,0.517732766503199,0.743033275158310 };
        double[] a = {1.25,-3.875,2.125,2.75,-3.5,1,1};
        bairstow = new Bairstow(funcion,r,s,grado,a,numeroIteracionesMaxima,errorTolerancia);
        String[] resultados = null;
        String resultado;
        
        for (int i = 0; i < 3; i++) {
            resultados = bairstow.bairstowN();
            resultado = resultados[0]+" "+resultados[1];
            System.out.println(resultado);
        }
        
        for (int i = 0; i < grado; i++) {
            assertEquals(valoresEsperados[i], bairstow.getRaicesReales()[i],delta);
        }        
    }
}
