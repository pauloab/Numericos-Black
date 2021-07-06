package Util;

import org.lsmp.djep.djep.DJep;
import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;

public class MetodosUniversales {

    public static void derivar(String funcion) {
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

    }
    public static void ImprimirFalsaPosicion(double matriz [][]){
        
        System.out.format("%5s %20s %20s %20s %20s %20s %20s %20s",
                "iter.", "xl","xu", "xr","fxl","fxu","fxr","ea\n");
        for (int i = 0; i < matriz[0].length; i ++){
            if (matriz[5][i] != 0) {
                System.out.format("%5s %20s %20s %20s %20s %20s %20s %20s",
                i, matriz[0][i], matriz[1][i], matriz[2][i], 
                matriz[3][i], matriz[4][i], matriz[5][i], matriz[6][i] +"\n");
            }
        }
    }

    public static double evaluarFuncion(String funcion, double x) throws Exception {
        double resultado = 0;
        JEP jep;
        jep = new JEP();

        jep.addStandardFunctions();
        jep.addStandardConstants();
        jep.addVariable("x", x);
        jep.parseExpression(funcion);
        resultado = jep.getValue();
        if (jep.hasError()) {
            throw new Exception("No se pudo evaluar la funcion");
        }
        return resultado;
    }
    
    

    public static double errorAprox(double vactual, double vanterior) {
        double res = 0;
        res = Math.abs((vactual - vanterior) / vactual) * 100;
        return res;
    }

    public static double errorTolerancia(int cifras) {
        return (0.5 * Math.pow(10, 2 - cifras)) / 100;
    }

    public static double errorVerdadero() {
        double res = 0;
        return res;
    }

}
