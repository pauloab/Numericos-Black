package Modelos.IntegracionNewtonCotes;

import Util.Matematico;

/**
 * * Modelo para procesamiento del método de integración númerica de
 * Newton-Cotes con método de simpson 3/8
 *
 * @author Geovanny Vega
 */
public class Simpson38 {

    private String funcion;
    private double x0;
    private double x1;
    private double x2;
    private double x3;
    private int n;


    /**
     * Constructor de la clase para calcular Método de Simpson 3/8 simple.
     *
     * @param funcion Función a evaluar para la integral.
     * @param x0
     * @param x3
     */
    public Simpson38(String funcion, double x0, double x3) {
        this.funcion = funcion;
        this.x0 = x0;
        this.x3 = x3;
        this.n = 3;
        this.x1 = x0+getH();
        this.x2 = x1+getH();
        
    }

    // Getters
    public double getH() {
        return (x3 - x0) / n;
    }

    public String getFuncion() {
        return funcion;
    }

    /**
     * Método para calcular la Método de Simpson 3/8 simple.
     *
     * @return El resultado de la integral por el método.
     * @throws Exception al evaluar la función.
     */
    public double Simpson38Simple() throws Exception {
        double Simp38 = 0, f0 = 0, f1 = 0, f2 = 0, f3 = 0;
        f0 = Matematico.evaluarFuncion(funcion, x0);
        f1 = Matematico.evaluarFuncion(funcion, x1);
        f2 = Matematico.evaluarFuncion(funcion, x2);
        f3 = Matematico.evaluarFuncion(funcion, x3);
        Simp38 = 3 * getH() * (f0 + 3 * (f1)+3*(f2) + f3) / 8;
        return Simp38;
    }



}
