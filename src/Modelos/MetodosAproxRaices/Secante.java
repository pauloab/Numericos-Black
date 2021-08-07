
package Modelos.MetodosAproxRaices;

import Util.MetodosUniversales;
import Modelos.MetodoImprimible;
import Modelos.MetodoNumerico;

/**
 * Clase creada para la implementacion del metodo de la secante.
 * @author Javier Matamoros 
 * @version 1.0
 * 
 */
public class Secante extends MetodoNumerico implements MetodoImprimible {
    
    private double valorXSubI;
    private double valorXSubIMenosUno;

    /**
     * Constructor del metodo de la secante encagardo de inicizalizar cada uno de los atributos
     * @param funcion variable encargado de guardar la funcion de la cual se desea obtener la raiz
     * @param errorTolerancia error de tolerancia
     * @param iteracionesMax valor maximo de iteraciones
     * @param xSubI valores iniciales de valorXSubIMenosUno
     * @param xSubIMenosUno valores iniciales de valorXSubIMenosUno
     */
    public Secante(String funcion, double errorTolerancia, int iteracionesMax, double xSubI, double xSubIMenosUno) {
        super(funcion, errorTolerancia, iteracionesMax+1, new String[iteracionesMax + 1][4]);
        this.valorXSubI = xSubI;
        this.valorXSubIMenosUno = xSubIMenosUno;
    }


    public double getValorXSubI() {
        return valorXSubI;
    }

    public void setValorXSubI(double valorXSubI) {
        this.valorXSubI = valorXSubI;
    }

    public double getValorXSubIMenosUno() {
        return valorXSubIMenosUno;
    }

    public void setValorXSubIMenosUno(double valorXSubIMenosUno) {
        this.valorXSubIMenosUno = valorXSubIMenosUno;
    }
    
    /**
     * Calcula la aproximación a travéz del método de la secante
     * @return Implementacion del metodo de la secante 
     * @throws Exception Esta excepcion va a ocurrir cuando no se pueda evaluar la funcion en un determinado punto.
     */
    public double metodoSecante() throws Exception{
        
        double errorAproximacion;
        int iterador = 2;
        double auxiliar, fxSubI, fxSubIMenosUno;
        getMatrizDeDatos()[0][0] = valorXSubIMenosUno+"";
        getMatrizDeDatos()[0][1] = "";
        getMatrizDeDatos()[0][2] = "";
        getMatrizDeDatos()[0][3] = "";
        getMatrizDeDatos()[1][0] = valorXSubI+"";
        getMatrizDeDatos()[1][1] = "";
        getMatrizDeDatos()[1][2] = "";
        getMatrizDeDatos()[1][3] = "";
        errorAproximacion = MetodosUniversales.errorAprox(valorXSubI,valorXSubIMenosUno);
        getMatrizDeDatos()[1][3] = errorAproximacion+"";
        do {
          
           fxSubI = MetodosUniversales.evaluarFuncion(getFuncion(), valorXSubI);
           fxSubIMenosUno = MetodosUniversales.evaluarFuncion(getFuncion(), valorXSubIMenosUno);
           auxiliar = valorXSubI - (fxSubI*(valorXSubIMenosUno-valorXSubI)/(fxSubIMenosUno - fxSubI));
           
           valorXSubIMenosUno = valorXSubI;
           valorXSubI= auxiliar;
           if (iterador >= 0) {
                errorAproximacion = MetodosUniversales.errorAprox(valorXSubI,valorXSubIMenosUno); 
            }
           getMatrizDeDatos()[iterador][0] = auxiliar+"";
           getMatrizDeDatos()[iterador][1] = fxSubIMenosUno+"";
           getMatrizDeDatos()[iterador][2] = fxSubI+"";
           getMatrizDeDatos()[iterador][3] = errorAproximacion+"";
           
           iterador++;

        } while (errorAproximacion >= getErrorTolerancia() && iterador < getIteracionesMaximas());
        return valorXSubI;
    }
    /**
     * Método encargado de mostrar los resultados del metodo de la secante en una matriz de datos
     */
    @Override
    public void imprimirResultados() {
         int  xSubIMasUno = 0, fxSubIMenosUno = 1, fxSubI = 2, errorAproximacion = 3;
        
        System.out.format("%5s %20s %20s %20s %20s",
                "iter.", "xi+1","fxi-1", "fxi","ea\n");

        for (int i = 0; i < getMatrizDeDatos().length; i ++){
            // Verifica que no se imprima la parte vacia de la matriz
            if (getMatrizDeDatos()[i][0] != null) {
                System.out.format("%5s %20s %20s %20s %20s",
                i, getMatrizDeDatos()[i][xSubIMasUno], getMatrizDeDatos()[i][fxSubIMenosUno], getMatrizDeDatos()[i][fxSubI], 
                getMatrizDeDatos()[i][errorAproximacion] +"\n");
            }
        }
    }
    
    
}
