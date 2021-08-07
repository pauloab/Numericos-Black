/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.MetodosAproxRaices;

import Util.MetodosUniversales;
import Modelos.MetodoImprimible;
import Modelos.MetodoNumerico;

/**
 * columna 1 = x0. 
 * columna 2 = x1. 
 * columna 3 = x2. 
 * columna 4 = xr. 
 * columna 5 = ea.
 * @author Geovanny Vega
 */
public class Muller extends MetodoNumerico implements MetodoImprimible {

    private double xSub2;
    private double xSub1;
    private double xSub0;

    /**
     * Constructor de Muller, crea el objeto con los parametros requeridos.
     *
     * @param funcion funcion de la cual se desea obtener la raiz.
     * @param xSub2 valor inicial de xSub2 para construir la parabola.
     * @param xSub1 valor inicial de xSub1 para construir la parabola.
     * @param xSub0 valor inicial de xSub0 para construir la parabola.
     * @param iteracionesMaximas valor maximo de iteraciones.
     * @param erorTolerancia cifras significativas que se desean.
     */
    public Muller(String funcion, double erorTolerancia, int iteracionesMaximas, double xSub2, double xSub1, double xSub0) {
        super(funcion, erorTolerancia, iteracionesMaximas, new String[iteracionesMaximas][5]);
        this.xSub2 = xSub2;
        this.xSub1 = xSub1;
        this.xSub0 = xSub0;
    }

    public double getxSub2() {
        return xSub2;
    }

    public void setxSub2(double xSub2) {
        this.xSub2 = xSub2;
    }

    public double getxSub1() {
        return xSub1;
    }

    public void setxSub1(double xSub1) {
        this.xSub1 = xSub1;
    }

    public double getxSub0() {
        return xSub0;
    }

    public void setxSub0(double xSub0) {
        this.xSub0 = xSub0;
    }

    /**
     * Implementacion del metodo de muller
     *
     * @throws Exception Esta excepcion va a ocurrir cuando no se pueda evaluar
     * la funcion en un determinado punto.
     */
    public double metodoMuller() throws Exception {
        double ea = 1;
        int i = 0;
        double x0, x1, x2, xr, h0, h1, d0, d1, a, b, c, rad, den, fx0, fx1, fx2, dxr;

        x2 = xSub2;
        x1 = xSub1;
        x0 = xSub0;

        do {

            // h0 y h1 son tamños de paso entre los intervalos
            h0 = x1 - x0;
            h1 = x2 - x1;
            // funcion evaluada en x0, x1, x2
            fx0 = MetodosUniversales.evaluarFuncion(getFuncion(), x0);
            fx1 = MetodosUniversales.evaluarFuncion(getFuncion(), x1);
            fx2 = MetodosUniversales.evaluarFuncion(getFuncion(), x2);
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
            if (!(i >= getIteracionesMaximas())) {
                getMatrizDeDatos()[i][0] = x0+"";
                getMatrizDeDatos()[i][1] = x1+"";
                getMatrizDeDatos()[i][2] = x2+"";
                getMatrizDeDatos()[i][3] = xr+"";
                getMatrizDeDatos()[i][4] = ea+"";

            }
            // Se evalua la condicionde parada
            if (ea < getErrorTolerancia() || i >= getIteracionesMaximas()) {
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
        for (int i = 0; i < getMatrizDeDatos().length; i++) {
            // Verifica que no se imprima la parte vacia de la matriz
            if (getMatrizDeDatos()[i][0] != null) {
                System.out.format("%5s %20s %20s %20s %20s %20s",
                        i, getMatrizDeDatos()[i][x0], getMatrizDeDatos()[i][x1], getMatrizDeDatos()[i][x2],
                        getMatrizDeDatos()[i][valorRaizAproximada], getMatrizDeDatos()[i][erroAproximacion] + "\n");
            }
        }
    }
}
