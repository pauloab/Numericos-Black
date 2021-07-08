
package Modelos;

import Util.MetodosUniversales;

/**
 * 
 * @author Geovanny Vega Clase que contiene el metodo de falsa posición
 * @version 1.0
 */
public class FalsaPosicion implements MetodosNumericos {

    private String funcion;
    private double cotaInferior;
    private double cotaSuperior;
    private int iteracionesMaximas;
    private double es;
    private double matriz[][];

    /**
     * Constructor de FalsaPosicion, crea el objeto con los parametros requeridos.
     * 
     * @param funcion            funcion de la cual se desea obtener la raiz.
     * @param cotaInferior       cota inferior del intervalo.
     * @param cotaSuperior       cota superior del intervalo.
     * @param iteracionesMaximas valor maximo de iteraciones.
     * @param cifras             cifras significativas que se desean.
     */
    public FalsaPosicion(String funcion, double cotaInferior, double cotaSuperior, int iteracionesMaximas, double es) {
        this.funcion = funcion;
        this.cotaInferior = cotaInferior;
        this.cotaSuperior = cotaSuperior;
        this.iteracionesMaximas = iteracionesMaximas;
        this.es = es;
        this.matriz = new double[7][iteracionesMaximas];

    }

    /**
     * 
     * @return
     */
    public String getFuncion() {
        return funcion;
    }

    /**
     * 
     * @param funcion
     */
    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    /**
     * 
     * @return
     */
    public double getXl() {
        return cotaInferior;
    }

    /**
     * 
     * @param cotaInferior
     */
    public void setXl(double cotaInferior) {
        this.cotaInferior = cotaInferior;
    }

    /**
     * 
     * @return
     */
    public double getXu() {
        return cotaSuperior;
    }

    /**
     * 
     * @param cotaSuperior
     */
    public void setXu(double cotaSuperior) {
        this.cotaSuperior = cotaSuperior;
    }

    /**
     * 
     * @return
     */
    public int getImax() {
        return iteracionesMaximas;
    }

    /**
     * 
     * @param iteracionesMaximas
     */
    public void setImax(int iteracionesMaximas) {
        this.iteracionesMaximas = iteracionesMaximas;
    }

    /**
     * 
     * @return
     */
    public double getEs() {
        return es;
    }

    public void setEs(double es) {
        this.es = es;
    }

    /**
     * columna 1 = cotaInferior. 
     * columna 2 = cotaSuperior. 
     * columna 3 = xr.
     * columna 4 = f(cotaInferior). 
     * columna 5 = f(cotaSuperior). 
     * columna 6 = f(xr). 
     * columna 7 = ea.
     * @return Matriz con los datos del algortimo
     */
    public double[][] getMatriz() {
        return matriz;
    }

    /**
     * Implementacion del metodo de falsa posición
     * 
     * @throws Exception Esta excepcion va a ocurrir cuando no se pueda evaluar la
     *                   funcion en un determinado punto.
     */
    public double metodoFalsaPosicion() throws Exception {
        double ea = es + 1;
        int i = 0;
        double xol = 0, fxr = 0, xr = 0, fcotaInferior = 0, fcotaSuperior = 0, fcotaInferiorx = 0;

        do {
            xol = xr;
            fxr = MetodosUniversales.evaluarFuncion(funcion, xr);
            fcotaInferior = MetodosUniversales.evaluarFuncion(funcion, cotaInferior);
            fcotaSuperior = MetodosUniversales.evaluarFuncion(funcion, cotaSuperior);
            xr = cotaSuperior - ((fcotaSuperior * (cotaInferior - cotaSuperior)) / (fcotaInferior - fcotaSuperior));
            fcotaInferiorx = fcotaInferior * fxr;
            if (i > 0) {
                ea = MetodosUniversales.errorAprox(xr, xol);
            }

            matriz[0][i] = cotaInferior;
            matriz[1][i] = cotaSuperior;
            matriz[2][i] = xr;
            matriz[3][i] = fcotaInferior;
            matriz[4][i] = fcotaSuperior;
            matriz[5][i] = fxr;
            matriz[6][i] = ea;

            if (fcotaInferiorx < 0) {
                cotaSuperior = xr;
            } else if (fcotaInferiorx > 0) {
                cotaInferior = xr;

            } else {
                ea = 0;
            }
            i++;

        } while (ea >= es && i < iteracionesMaximas);
        return xr;
    }

    @Override
    public void imprimirResultados() {

        int  xl = 0, xu = 1, xr = 2, fxl =3, fxu = 4, fxr =5, ea=6;  
        System.out.format("%5s %20s %20s %20s %20s %20s %20s %20s",
                "iter.", "xl","xu", "xr","f(xl)","f(xu)","f(xr)","ea\n");
        for (int i = 0; i < matriz[xl].length; i ++){
            // La condición evalua si fxl es diferente de cero, si resultada imprime.
            // Escogimos fxl porque es un valor que si es cero significa que esa zona de la matriz hacia abajo esta vacía.
            // Porque la matriz se crea con iMax y algunos filas no siempre se llenan.
            if (matriz[fxl][i] != 0) {
                System.out.format("%5s %20s %20s %20s %20s %20s %20s %20s",
                i, matriz[xl][i], matriz[xu][i], matriz[xr][i], 
                matriz[fxl][i], matriz[fxu][i], matriz[fxr][i], matriz[ea][i] +"\n");
            }
        }
    }

}
