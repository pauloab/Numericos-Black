
package Modelos.MetodosCerrados;

import Util.MetodosUniversales;
import Modelos.MetodoImprimible;
import Modelos.MetodoNumerico;

/**
 * Clase que contiene el metodo de falsa posición.
 * Forma de la matríz de datos
 * columna 1 = cotaInferior. 
 * columna 2 = cotaSuperior. 
 * columna 3 = xr.
 * columna 4 = f(cotaInferior). 
 * columna 5 = f(cotaSuperior). 
 * columna 6 = f(xr). 
 * columna 7 = ea.
 * @author Geovanny Vega 
 * @version 1.0
 */
public class FalsaPosicion extends MetodoNumerico implements MetodoImprimible {

    private double cotaInferior;
    private double cotaSuperior;

    /**
     * Constructor de FalsaPosicion, crea el objeto con los parametros requeridos.
     * 
     * @param funcion            funcion de la cual se desea obtener la raiz.
     * @param cotaInferior       cota inferior del intervalo.
     * @param cotaSuperior       cota superior del intervalo.
     * @param iteracionesMaximas valor maximo de iteraciones.
     * @param es                 Errror de tolerancia.
     */
    public FalsaPosicion(String funcion, double es,int iteracionesMaximas, double cotaInferior, double cotaSuperior) {
        super(funcion, es, iteracionesMaximas, new String[iteracionesMaximas][7]);
        this.cotaInferior = cotaInferior;
        this.cotaSuperior = cotaSuperior;
    }

    public double getXl() {
        return cotaInferior;
    }

    public void setXl(double cotaInferior) {
        this.cotaInferior = cotaInferior;
    }

    public double getXu() {
        return cotaSuperior;
    }

    public void setXu(double cotaSuperior) {
        this.cotaSuperior = cotaSuperior;
    }

    /**
     * Implementacion del metodo de falsa posición
     * @throws Exception Esta excepcion va a ocurrir cuando no se pueda evaluar la
     *                   funcion en un determinado punto.
     */
    public double metodoFalsaPosicion() throws Exception {
        double ea = getErrorTolerancia() + 1;
        int i = 0;
        double xol = 0, fxr = 0, xr = 0, fcotaInferior = 0, fcotaSuperior = 0, fcotaInferiorx = 0;

        do {
            xol = xr;
            fxr = MetodosUniversales.evaluarFuncion(getFuncion(), xr);
            fcotaInferior = MetodosUniversales.evaluarFuncion(getFuncion(), cotaInferior);
            fcotaSuperior = MetodosUniversales.evaluarFuncion(getFuncion(), cotaSuperior);
            xr = cotaSuperior - ((fcotaSuperior * (cotaInferior - cotaSuperior)) / (fcotaInferior - fcotaSuperior));
            fcotaInferiorx = fcotaInferior * fxr;
            if (i > 0) {
                ea = MetodosUniversales.errorAprox(xr, xol);
            }

            getMatrizDeDatos()[i][0] = cotaInferior+"";
            getMatrizDeDatos()[i][1] = cotaSuperior+"";
            getMatrizDeDatos()[i][2] = xr+"";
            getMatrizDeDatos()[i][3] = fcotaInferior+"";
            getMatrizDeDatos()[i][4] = fcotaSuperior+"";
            getMatrizDeDatos()[i][5] = fxr+"";
            getMatrizDeDatos()[i][6] = i==0?"":ea+"";

            if (fcotaInferiorx < 0) {
                cotaSuperior = xr;
            } else if (fcotaInferiorx > 0) {
                cotaInferior = xr;

            } else {
                ea = 0;
            }
            i++;

        } while (ea >= getErrorTolerancia() && i < getIteracionesMaximas());
        return xr;
    }
    
    /**
     * Método encargado de mostrar los resultados del metodo de falsaPosicion en
     * una matriz de datos
     */
    @Override
    public void imprimirResultados() {

        int  xl = 0, xu = 1, xr = 2, fxl =3, fxu = 4, fxr =5, erroAproximacion=6;  
        System.out.format("%5s %20s %20s %20s %20s %20s %20s %20s",
                "iter.", "xl","xu", "xr","f(xl)","f(xu)","f(xr)","ea\n");
        for (int i = 0; i < getMatrizDeDatos().length; i ++){
            // Verifica que no se imprima la parte vacia de la matriz
            if (getMatrizDeDatos()[i][0] != null) {
                System.out.format("%5s %20s %20s %20s %20s %20s %20s %20s",
                i, getMatrizDeDatos()[i][xl], getMatrizDeDatos()[i][xu], getMatrizDeDatos()[i][xr], 
                getMatrizDeDatos()[i][fxl], getMatrizDeDatos()[i][fxu], getMatrizDeDatos()[i][fxr], 
                getMatrizDeDatos()[i][erroAproximacion] +"\n");
            }
        }
    }

}
