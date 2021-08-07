package Modelos.SerieDeTaylor;

import Modelos.MetodoImprimible;
import Modelos.MetodoNumerico;
import Util.MetodosUniversales;

/**
 * Clase que aproxima un valor mediante la serie de Taylor
 *
 * @author Paulo Aguilar
 */
public class SerieTaylor extends MetodoNumerico implements MetodoImprimible {

    private double valorAproximar;
    private double tamanoPaso;
    private double valorVerdadero;

    /**
     * Constructor de la clase
     *
     * @param funcion Función a aproximar
     * @param errorTolerancia Error de tolerancia para el cálculo.
     * @param iteracionesMaximas Iteraciones máximas a las que se someterá la
     * aproximación.
     * @param valorAproximar Valor a aproximar con la serie.
     * @param tamanoPaso Tamaño de paso bajo el cual calcular la serie.
     * @throws Exception Ocurre cuando no se puede evaluar el valor verdadero de
     * la función ingresada.
     */
    public SerieTaylor(String funcion, double errorTolerancia,
            int iteracionesMaximas, double valorAproximar,
            double tamanoPaso) throws Exception {

        super(funcion, errorTolerancia, iteracionesMaximas, new String[iteracionesMaximas][3]);
        this.valorAproximar = valorAproximar;
        this.tamanoPaso = tamanoPaso;
        this.valorVerdadero = MetodosUniversales.evaluarFuncion(getFuncion(), valorAproximar);
    }

    /**
     * Aproxima la función ingresada mediante la serie de Taylor
     *
     * @return Valor aproximado Calculado.
     * @throws Exception Ocurre cuando hay un error al evaluar una función.
     */
    public double SerieTaylor() throws Exception {
        double valorInicial = valorAproximar - tamanoPaso;
        double errorAprox = 1, errorVerdadero, valorAproximado = 0, valorAnt = 0;
        String derivada, terminoN, funcion = getFuncion();
        int i = 0;
        getMatrizDeDatos()[0][2] = "N/A";
        do {
            valorAnt = valorAproximado;
            if (i == 0) {
                valorAproximado = MetodosUniversales.evaluarFuncion(getFuncion(), valorInicial);
            } else {
                derivada = MetodosUniversales.derivar(funcion);
                funcion = derivada;
                terminoN = "((" + derivada + ")*(" + tamanoPaso + "^" + i + "))/"
                        + MetodosUniversales.factorial(i);
                valorAproximado += MetodosUniversales.evaluarFuncion(terminoN, valorInicial);
            }
            if (i >= 1) {
                errorAprox = MetodosUniversales.errorAprox(valorAproximado, valorAnt);
                getMatrizDeDatos()[i][2] = errorAprox + " %";
            }
            errorVerdadero = MetodosUniversales.errorAprox(valorVerdadero, valorAproximado);
            getMatrizDeDatos()[i][0] = valorAproximado + "";
            getMatrizDeDatos()[i][1] = errorAprox + " %";
            i++;
        } while (errorAprox > getErrorTolerancia() && i < getIteracionesMaximas());
        return valorAproximado;
    }

    /**
     * Método encargado de mostrar los resultados de la serie de Taylor en una
     * matriz de datos
     */
    @Override
    public void imprimirResultados() {

        int valor = 0, errorVerdadero = 1, errorAprox = 2;
        System.out.format("%5s %20s %20s %20s",
                "iter.", "xAprox", "Et", "Ea\n");
        for (int i = 0; i < getMatrizDeDatos().length; i++) {
            // Verifica que no se imprima la parte vacia de la matriz
            if (getMatrizDeDatos()[i][0] != null) {
                System.out.format("%5s %20s %20s %20s",
                        i, getMatrizDeDatos()[i][valor],
                        getMatrizDeDatos()[i][errorVerdadero],
                        getMatrizDeDatos()[i][errorAprox] + "\n");
            }
        }
    }

    public void setValorAproximar(double valorAproximar) {
        this.valorAproximar = valorAproximar;
    }

    public void setTamanoPaso(double tamanoPaso) {
        this.tamanoPaso = tamanoPaso;
    }

    public void setValorVerdadero(double valorVerdadero) {
        this.valorVerdadero = valorVerdadero;
    }

    public double getValorAproximar() {
        return valorAproximar;
    }

    public double getTamanoPaso() {
        return tamanoPaso;
    }

    public double getValorVerdadero() {
        return valorVerdadero;
    } 
    
}
