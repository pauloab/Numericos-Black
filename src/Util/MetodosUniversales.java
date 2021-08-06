package Util;

import Plotter.Models.CoordinatePair;
import org.lsmp.djep.djep.DJep;
import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;



public class MetodosUniversales {

    public static String derivar(String funcion) {
        DJep djep;
        Node nodoFuncion;
        Node nodoDerivada;

        try {
            djep = new DJep();
            // agrega funciones estandares cos(x), sin(x)
            djep.addStandardFunctions();

            // agrega constantes estandares, pi, e, etc
            djep.addStandardConstants();

            // por si existe algun numero complejo
            djep.addComplex();

            // permite variables no declarables
            djep.setAllowUndeclared(true);

            // permite asignaciones
            djep.setAllowAssignment(true);

            // regla de multiplicacion o para sustraccion y sumas
            djep.setImplicitMul(true);

            // regla de multiplicacion o para sustraccion y sumas
            djep.addStandardDiffRules();

            // coloca el nodo con una funcion preestablecida
            nodoFuncion = djep.parse(funcion);

            // deriva la funcion con respecto a x
            Node diff = djep.differentiate(nodoFuncion, "x");

            // Simplificamos la funcion con respecto a x
            nodoDerivada = djep.simplify(diff);

            // Convertimos el valor simplificado en un String
            funcion = djep.toString(nodoDerivada);

        } catch (ParseException e) {
            funcion = "NaN";
            System.out.println("Error: " + e.getErrorInfo());
        }
        return funcion;
    }

    public static double evaluarFuncion(String funcion, double x) throws Exception {
        JEP jep;
        jep = new JEP();

        jep.addStandardFunctions();
        jep.addStandardConstants();
        jep.addVariable("x", x);
        jep.parseExpression(funcion);
        if (jep.hasError()) {
            throw new Exception("No se pudo evaluar la funcion");
        }
        return jep.getValue();
    }
    
    /**
     * Calcula el factorial de un número dado
     * @param n Número a calcular su factorial
     * @return factorial de n
     */
    public static long factorial(int n){
        long result = 1;
        for (int i = n; i > 1; i--) {
            result = i * result;
        }
        return result;
    }

    public static double errorAprox(double vactual, double vanterior) {
        double res = 0;
        res = Math.abs((vactual - vanterior) / vactual) * 100;
        return res;
    }

    public static double errorTolerancia(int cifras) {
        return (0.5 * Math.pow(10, 2 - cifras)) / 100;
    }
    
    public static boolean validarExpresion(String exrpesion){
        JEP jep = new JEP();

        jep.addStandardFunctions();
        jep.addStandardConstants();
        jep.addVariable("x", 0);
        jep.parseExpression(exrpesion);
        return !jep.hasError();
    }
    
    /**
     * Crea un vector de numeros con los valores en y de una función, basado
     * en un arreglo de x.
     * @param funcion Funcion de entrada a evaluar
     * @param x Vector de valores en x sobre los que se va a evaluar.
     * @return Arreglo de valores de y del input x
     * @throws Exception Ocurre cuando no se puede evaluar la funci[on en un determinado punto
     */
    public static double[] graficarFuncion(String funcion, double[] x) throws Exception{
        double[] results = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            results[i] = evaluarFuncion(funcion, x[i]);
        }
        return results;
    } 
    
    /**
     * Evalúa una función basado en su mínimo y máximo en fracciones de 1500
     * @param funcion Funcion a evaluar
     * @param xMin Valor minimo del dominio
     * @param xMax Valor máximo del dominio
     * @return Vector de pares de coordenadas 
     */
    public static CoordinatePair[] evaluarFuncion(String funcion, double xMin, double xMax) throws Exception{
        int intervalo = 1500;
        double paso = (xMax-xMin)/intervalo;
        CoordinatePair[] paresCordenadas = new CoordinatePair[intervalo+1];
        int iterador = 0;
        for (double i = xMin; i < xMax ; i += paso) {
            paresCordenadas[iterador] = new CoordinatePair(i, evaluarFuncion(funcion, i) );
            iterador++;
        }
        return paresCordenadas;
    }
    
    
}
