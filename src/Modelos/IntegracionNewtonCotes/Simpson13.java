package Modelos.IntegracionNewtonCotes;

import Util.Matematico;

/**
 * Modelo para procesamiento del método de integración númerica de Newton-Cotes
 * con método de simpson 1/3
 *
 * @author Geovanny Vega
 */
public class Simpson13 {

    private String funcion;
    private double x0;
    private double x1;
    private double x2;
    private int n;

    /**
     * Constructor de la clase para calcular Método de Simpson 1/3 segmentos
     * múltpiles.
     *
     * @param funcion Función a evaluar para la integral.
     * @param x0
     * @param x2
     * @param n
     */
    public Simpson13(String funcion, double x0, double x2, int n) {
        this.funcion = funcion;
        this.x0 = x0;
        this.x1 = 0;
        this.x2 = x2;
        this.n = n;
    }

    /**
     * Constructor de la clase para calcular Método de Simpson 1/3 simple.
     *
     * @param funcion Función a evaluar para la integral.
     * @param x0
     * @param x2
     */
    public Simpson13(String funcion, double x0, double x2) {
        this.funcion = funcion;
        this.x0 = x0;
        this.x1 = (x0+x2)/2;
        this.x2 = x2;
        this.n = 2;
    }

    // Getters
    public double getH() {
        return (x2 - x0) / n;
    }

    public String getFuncion() {
        return funcion;
    }

    /**
     * Método para calcular la Método de Simpson 1/3 simple.
     * @return El resultado de la integral por el método.
     * @throws Exception al evaluar la función.
     */
    public double Simpson13Simple() throws Exception {
        double Simp13 = 0, f0 = 0, f1 = 0, f2 = 0;
        f0 = Matematico.evaluarFuncion(funcion, x0);
        f1 = Matematico.evaluarFuncion(funcion, x1);
        f2 = Matematico.evaluarFuncion(funcion, x2);
        Simp13 = 2 * getH() * (f0 + 4 * (f1) + f2) / 6;
        return Simp13;
    }

    /**
     * Método para calcular la Método de Simpson 1/3 segemntos múltiples.
     * @return El resultado de la integral por el método.
     * @throws Exception al evaluar la función.
     */
    public double Simpson13MultipleSegmentos() throws Exception {
        double Simp13m = 0, f0 = 0, f2 = 0, sum = 0, xaux = 0;
        f0 = Matematico.evaluarFuncion(funcion, x0);
        f2 = Matematico.evaluarFuncion(funcion, x2);
        sum += f0 + f2;
        xaux = x0 + getH();
        for (int i = 0; i < (n - 2); i += 2) {
            sum += 4 * Matematico.evaluarFuncion(funcion, xaux) + 2 * Matematico.evaluarFuncion(funcion, xaux + getH());
            xaux +=  + getH() * 2;
        }
        sum += 4 * Matematico.evaluarFuncion(funcion, x2 - getH());
        Simp13m = getH() * sum / 3;
        return Simp13m;
    }
}
