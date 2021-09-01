package Modelos.IntegracionNewtonCotes;

import Util.Matematico;

/**
 * Modelo para procesamiento del método de integración númerica de Newton-Cotes
 * con Regla del Trapecio
 *
 * @author Geovanny Vega
 */
public class ReglaTrapecio {

    private String funcion;
    private double x0;
    private double x1;
    private int n;

    /**
     * Constructor de la clase para calcular con multiples segmentos.
     *
     * @param funcion Función a evaluar para la integral.
     * @param x0 Limete inferior de la integral.
     * @param x1 Límete superior de la integral.
     * @param n Número de segmentos.
     */
    public ReglaTrapecio(String funcion, double x0, double x1, int n) {
        this.funcion = funcion;
        this.x0 = x0;
        this.x1 = x1;
        this.n = n;
    }

    /**
     * Constructor de la clase para calcular Regla de Trapecio simple.
     * @param funcion Función a evaluar para la integral.
     * @param x0 Limete inferior de la integral.
     * @param x1 Limete inferior de la integral.
     */
    public ReglaTrapecio(String funcion, double x0, double x1) {
        this.funcion = funcion;
        this.x0 = x0;
        this.x1 = x1;
        this.n = 1;
    }

    /**
     * Getter
     *
     * @return
     */
    public String getFuncion() {
        return funcion;
    }

    public double getH() {
        return (x1 - x0) / n;
    }

    /**
     * Método para calcular la regla del Trapecio en múltiples segmentos.
     *
     * @return El resultado de la integral por el método.
     * @throws Exception al evaluar la función.
     */
    public double trapecioSegmentosMultiples() throws Exception {
        double Trap = 0, h = 0, f0 = 0, f1 = 0, sum = 0, xaux;
        h = (x1 - x0) / n;
        f0 = Matematico.evaluarFuncion(funcion, x0);
        f1 = Matematico.evaluarFuncion(funcion, x1);
        xaux = x0 + h;
        for (int i = 1; i <= (n - 1); i++) {
            sum += Matematico.evaluarFuncion(funcion, xaux);
            xaux = xaux + h;
        }
        sum = f0 + 2 * sum + f1;
        Trap = (h / 2) * sum;
        return Trap;
    }
    
    /**
     * Método para calcular la regla del Trapecio simple.
     * @return El resultado de la integral por el método.
     * @throws Exception al evaluar la función.
     */
    public double trapecioSimple() throws Exception{
        double Trap = 0, f0 = 0, f1 = 0, h = 0;
        h = x1-x0;
        f0 = Matematico.evaluarFuncion(funcion, x0);
        f1 = Matematico.evaluarFuncion(funcion, x1);
        Trap = h*(f0+f1)/2;
        return Trap;
    }

}
