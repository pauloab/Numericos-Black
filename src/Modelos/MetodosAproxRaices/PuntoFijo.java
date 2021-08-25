package Modelos.MetodosAproxRaices;
import Util.Matematico;
import Modelos.MetodoImprimible;
import Modelos.MetodoAproximadorRaices;

/**
 * Clase que contiene el metodo de Punto Fijo Formato de la matríz de datos
 * Columna 1 = xi+1 
 * Columna 2 = g(x) 
 * Columna 3 = ea
 * @author Freddy Lamar
 * @version 1.0
 */
public class PuntoFijo extends MetodoAproximadorRaices implements MetodoImprimible {

    private double x0;
    /**
     *
     * @param funcion funcion despejada la cual se va evaluar el metodo.
     * @param x0 valor inicial
     * @param iteracionesMax valor maximo de iteraciones
     * @param errorTolerancia error de tolerancia
     */
    public PuntoFijo(String funcion, double errorTolerancia, int iteracionesMax, double x0) {
        super(funcion, errorTolerancia, iteracionesMax, new String[iteracionesMax][3]);
        this.x0 = x0;

    }

    public double getX0() {
        return x0;
    }

    public void setX0(double x0) {
        this.x0 = x0;
    }

    /**
     * Implementacion del metodo de Punto Fijo
     *
     * @throws Exception Esta excepcion va a ocurrir cuando no se pueda evaluar
     * la funcion en un determinado punto.
     */
    public double metodoPuntoFijo() throws Exception {
        double ea = 1;
        int i = 0;
        double xol, fxr, xr = x0;
        getMatrizDeDatos()[i][0] = xr + "";
        getMatrizDeDatos()[i][1] =  "";
        getMatrizDeDatos()[i][2] =  "";
        do {
            i++;
            xol = xr;
            xr = Matematico.evaluarFuncion(getFuncion(), xr);

            if (i > 0 && xr != 0) {
                ea = Matematico.errorAprox(xr, xol);
            }

            getMatrizDeDatos()[i][0] = xr + "";
            getMatrizDeDatos()[i][1] = xr + "";
            getMatrizDeDatos()[i][2] = ea + "";

        } while (ea >= getErrorTolerancia() && i < getIteracionesMaximas());
        return xr;
    }

    /**
     * Método encargado de mostrar los resultados del metodo de Punto Fijo en
     * una matriz de datos
     */
    @Override
    public void imprimirResultados() {
        int xi = 0, gx = 1, ea = 2;

        System.out.format("%5s %20s %20s %20s",
                "iter.", "xi+1", "g(x)","ea\n");

        for (int i = 0; i < getMatrizDeDatos().length; i++) {
            // Verifica que no se imprima la parte vacia de la matriz
            if (getMatrizDeDatos()[i][0] != null) {
                System.out.format("%5s %20s %20s %20s",
                        i, getMatrizDeDatos()[i][xi], getMatrizDeDatos()[i][gx], getMatrizDeDatos()[i][ea]
                         + "\n");
            }
        }
    }

}
