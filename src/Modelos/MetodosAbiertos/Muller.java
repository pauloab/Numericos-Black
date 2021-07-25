/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.MetodosAbiertos;

import Modelos.MetodosNumericos;
import Util.MetodosUniversales;

/**
 *
 * @author Geovanny Vega
 */
public class Muller implements MetodosNumericos {

    private String funcion;
    private double xSub2;
    private double xSub1;
    private double xSub0;
    private int iteracionesMaximas;
    private double errorAproximado;
    private double matriz[][];

    /**
     * Constructor de Muller, crea el objeto con los parametros requeridos.
     *
     * @param funcion funcion de la cual se desea obtener la raiz.
     * @param xSub2 valor inicial de xSub2 para construir la parabola.
     * @param xSub1 valor inicial de xSub1 para construir la parabola.
     * @param xSub0 valor inicial de xSub0 para construir la parabola.
     * @param iteracionesMaximas valor maximo de iteraciones.
     * @param cifras cifras significativas que se desean.
     */
    public Muller(String funcion, double xSub2, double xSub1, double xSub0, int iteracionesMaximas, double errorAproximado) {
        this.funcion = funcion;
        this.xSub2 = xSub2;
        this.xSub1 = xSub1;
        this.xSub0 = xSub0;
        this.iteracionesMaximas = iteracionesMaximas;
        this.errorAproximado = errorAproximado;
        this.matriz = new double[5][iteracionesMaximas];

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
    public double getxSub2() {
        return xSub2;
    }

    /**
     *
     * @param xSub2
     */
    public void setxSub2(double xSub2) {
        this.xSub2 = xSub2;
    }

    /**
     *
     * @return
     */
    public double getxSub1() {
        return xSub1;
    }

    /**
     *
     * @param xSub1
     */
    public void setxSub1(double xSub1) {
        this.xSub1 = xSub1;
    }

    /**
     *
     * @return
     */
    public double getxSub0() {
        return xSub0;
    }

    /**
     *
     * @param xSub0
     */
    public void setxSub0(double xSub0) {
        this.xSub0 = xSub0;
    }

    /**
     *
     * @return
     */
    public int getIteracionesMaximas() {
        return iteracionesMaximas;
    }

    /**
     *
     * @param iteracionesMaximas
     */
    public void setIteracionesMaximas(int iteracionesMaximas) {
        this.iteracionesMaximas = iteracionesMaximas;
    }

    /**
     *
     * @return
     */
    public double getErrorAproxmado() {
        return errorAproximado;
    }

    /**
     *
     * @param es
     */
    public void setErrorAproxmado(double es) {
        this.errorAproximado = es;
    }

    /**
     * columna 1 = x0. 
     * columna 2 = x1. 
     * columna 3 = x2. 
     * columna 4 = xr. 
     * columna 7= ea.
     * @return Matriz con los datos del algortimo
     */
    public double[][] getMatriz() {
        return matriz;
    }

    /**
     * Implementacion del metodo de muller
     *
     * @throws Exception Esta excepcion va a ocurrir cuando no se pueda evaluar
     * la funcion en un determinado punto.
     */
    public double metodoMuller() throws Exception {
        double ea = errorAproximado + 1;
        int i = 0;
        double x0 = 0, x1 = 0, x2 = 0, xr = 0, h0 = 0, h1 = 0, d0 = 0, d1 = 0, a = 0, b = 0, c = 0, rad = 0, den = 0, fx0 = 0, fx1 = 0, fx2 = 0, dxr = 0;

        x2 = xSub2;
        x1 = xSub1;
        x0 = xSub0;

        do {

            // h0 y h1 son tamños de paso entre los intervalos
            h0 = x1 - x0;
            h1 = x2 - x1;
            // funcion evaluada en x0, x1, x2
            fx0 = MetodosUniversales.evaluarFuncion(funcion, x0);
            fx1 = MetodosUniversales.evaluarFuncion(funcion, x1);
            fx2 = MetodosUniversales.evaluarFuncion(funcion, x2);
            // Pendientes de los intervalos
            d0 = (fx1 - fx0) / h0;
            d1 = (fx2 - fx1) / h1;
            // Coeficientes para evaluar en la fórmula cuadrática 
            a = (d1 - d0) / (h1 + h0);
            b = (a * h1 + d1);
            c = fx2;
            // discrimante de la formula
            rad = Math.sqrt((Math.pow(b, 2) - (4 * a * c)));
            // denominador de la fórmula cuadrática 
            den = Math.abs(b + rad) > Math.abs(b - rad) ? (b + rad) : (b - rad);
            // calculo parcial de la fórmula cuadrática 
            dxr = (-2 * c) / den;
            // calculo de fórmula cuadrática 
            xr = x2 + dxr;
            // calculo del error aproximado
            ea = MetodosUniversales.errorAprox(xr, x2);

            // Se evalua que no haya superado el tamaño de la matriz
            if (!(i >= iteracionesMaximas)) {
                matriz[0][i] = x0;
                matriz[1][i] = x1;
                matriz[2][i] = x2;
                matriz[3][i] = xr;
                matriz[4][i] = ea;

            }
            // Se evalua la condicionde parada
            if (ea < errorAproximado || i >= iteracionesMaximas) {
                break;
            }

            // asignacion de los valores para la siguiente iteracion si errorAproximado el caso
            x0 = x1;
            x1 = x2;
            x2 = xr;

            i++;

        } while (true);
        return xr;
    }

    /**
     * Método encargado de mostrar los resultados del metodo de muller en
     * una matriz de datos
     */
    @Override
    public void imprimirResultados() {

        int x0 = 0, x1 = 1, x2 = 2, valorRaizAproximada = 3, erroAproximacion = 4;
        System.out.format("%5s %20s %20s %20s %20s %20s",
                "iter.", "x0", "x1", "x2", "xr", "ea\n");
        for (int i = 0; i < matriz[x0].length; i++) {
            // Verifica que no se imprima la parte vacia de la matriz
            if (matriz[erroAproximacion][i] != 0) {
                System.out.format("%5s %20s %20s %20s %20s %20s",
                        i, matriz[x0][i], matriz[x1][i], matriz[x2][i],
                        matriz[valorRaizAproximada][i], matriz[erroAproximacion][i] + "\n");
            }
        }
    }
}
