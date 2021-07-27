
package Modelos.MetodosAbiertos;
import Util.MetodosUniversales;
import Modelos.MetodoImprimible;
import Modelos.MetodoNumerico;

/**
 * Columna 1 = xi + 1
 * Columna 2 = f(xi)
 * Columna 3 = f'(xi)
 * Columna 4 = ea
 * @author Freddy Lamar
 * @version 1.0
 */


public class NewtonRaphson extends MetodoNumerico implements MetodoImprimible{

    private double x0;
    
    /**
     * Constructor de Newton Raphson, crea el objeto con los parametros
     * requeridos.
     *
     * @param funcion funcion de la cual se desea obtener la raiz.
     * @param x0 Parametro x0
     * @param iMax valor maximo de iteraciones.
     * @param es Error Aproximado
     */
    public NewtonRaphson(String funcion, double es, int iMax, double x0) {
        super(funcion, es, iMax, new String[iMax][4]);
        this.x0 = x0;
    }
    
    public double getX0() {
        return x0;
    }

    public void setX0(double x0) {
        this.x0 = x0;
    }

    /**
     * Implementacion del metodo de falsa posición
     *
     * @throws Exception Esta excepcion va a ocurrir cuando no se pueda evaluar
     * la funcion en un determinado punto.
     */
    public double metodoNewtonRaphson() throws Exception {
        double ea = 1;
        int i = 1;
        double xol, fxi1, xi1, fxi1p;
        xi1 = x0;

        getMatrizDeDatos()[0][0] = xi1 + "";
        getMatrizDeDatos()[0][1] = "";
        getMatrizDeDatos()[0][2] = "";
        getMatrizDeDatos()[0][3] = "";

        do {
            xol = xi1;
            fxi1 = MetodosUniversales.evaluarFuncion(getFuncion(), xol);
            fxi1p = MetodosUniversales.evaluarFuncion(MetodosUniversales.derivar(getFuncion()), xol);

            if (fxi1p == 0) {
                return xi1;
            } else {
                xi1 = xol - (fxi1 / fxi1p);
            }
            ea = MetodosUniversales.errorAprox(xi1, xol);

            getMatrizDeDatos()[i][0] = xi1 + "";
            getMatrizDeDatos()[i][1] = fxi1 + "";
            getMatrizDeDatos()[i][2] = fxi1p + "";
            getMatrizDeDatos()[i][3] = ea + "";

            i++;
        } while (ea >= getErrorTolerancia() && i < getIteracionesMaximas());
        return xi1;
    }
    
    /**
     * Método encargado de mostrar los resultados del metodo de newtonRaphson en
     * una matriz de datos
     */
    @Override
    public void imprimirResultados() {

        int valorInicial = 0, fxi = 1, fxip = 2, erroAproximacion = 3;  
           System.out.format("%5s %20s %20s %20s %20s",
                "iter.", "xi+1","f(xi)", "f'(xi)","ea\n");
        for (int i = 0; i < getMatrizDeDatos().length; i ++){
            // Verifica que no se imprima la parte vacia de la matriz
            if (getMatrizDeDatos()[i][fxip] != null) {
                System.out.format("%5s %20s %20s %20s %20s", 
                i, getMatrizDeDatos()[i][valorInicial], getMatrizDeDatos()[i][fxi], 
                getMatrizDeDatos()[i][fxip], getMatrizDeDatos()[i][erroAproximacion] + "\n");
            }
            System.out.flush(); 
        } 
    }
    
}

