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
    
    

    public static double errorAprox(double vactual, double vanterior) {
        double res = 0;
        res = Math.abs((vactual - vanterior) / vactual) * 100;
        return res;
    }

    public static double errorTolerancia(int cifras) {
        return (0.5 * Math.pow(10, 2 - cifras)) / 100;
    }
    
        
    }


