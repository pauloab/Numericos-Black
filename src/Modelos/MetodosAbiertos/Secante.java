
package Modelos.MetodosAbiertos;

import Modelos.MetodosNumericos;
import Util.MetodosUniversales;

/**
 * Clase creada para la implementacion del metodo de la secante.
 * @author Javier Matamoros 
 * @version 1.0
 * 
 */
public class Secante implements MetodosNumericos {
    
    
    private String funcion;
    private double valorXSubI;
    private double valorXSubIMenosUno;
    private int iteracionesMaximas;
    private double errorTolerancia;
    private double matriz [][];

    /**
     * Constructor del metodo de la secante encagardo de inicizalizar cada uno de los atributos
     * @param funcion variable encargado de guardar la funcion de la cual se desea obtener la raiz
     * @param valorXSubI valores iniciales de valorXSubIMenosUno
     * @param valorXSubIMenosUno valores iniciales de valorXSubIMenosUno
     * @param iteracionesMaximas valor maximo de iteraciones
     * @param errorTolerancia error de tolerancia
     */
    public Secante(String funcion, double xSubI, double xSubIMenosUno, int iteracionesMax, double errorTolerancia) {
        this.funcion = funcion;
        this.valorXSubI = xSubI;
        this.valorXSubIMenosUno = xSubIMenosUno;
        this.iteracionesMaximas = iteracionesMax + 1;
        this.errorTolerancia = errorTolerancia;
        this.matriz = new double [4][iteracionesMax + 1];
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public double getValorXSubI() {
        return valorXSubI;
    }

    public void setValorXSubI(double valorXSubI) {
        this.valorXSubI = valorXSubI;
    }

    public double getValorXSubIMenosUno() {
        return valorXSubIMenosUno;
    }

    public void setValorXSubIMenosUno(double valorXSubIMenosUno) {
        this.valorXSubIMenosUno = valorXSubIMenosUno;
    }

    public int getIteracionesMaximas() {
        return iteracionesMaximas;
    }

    public void setIteracionesMaximas(int iteracionesMaximas) {
        this.iteracionesMaximas = iteracionesMaximas;
    }

    public double getErrorTolerancia() {
        return errorTolerancia;
    }

    public void setErrorTolerancia(double errorTolerancia) {
        this.errorTolerancia = errorTolerancia;
    }

    /**
     * fila 1 = valorXSubIMenosUno sub iterador menos 1
       fila 2 = f(xi-1) funcion evaluada en el punto valorXSubIMenosUno sub iterador menos 1
       fila 3 = f (xi) funcion evaluada en el punto valorXSubIMenosUno sub iterador
       fila 4 = error de aproximacion (errorAproximacion)
     * @return Retorna una matriz con los datos del metodo
     */
    public double[][] getMatriz() {
        return matriz;
    }
    /**
     * 
     * @return Implementacion del metodo de la secante 
     * @throws Exception Esta excepcion va a ocurrir cuando no se pueda evaluar la funcion en un determinado punto.
     */
    public double metodoSecante() throws Exception{
        
        double errorAproximacion = errorTolerancia + 1;
        int iterador = 2;
        double auxiliar = 0, fxSubI = 0, fxSubIMenosUno = 0;
        matriz[0][0] = valorXSubIMenosUno;
        matriz[0][1] = valorXSubI;
        errorAproximacion = MetodosUniversales.errorAprox(valorXSubI,valorXSubIMenosUno);
        matriz[3][1] = errorAproximacion;
        do {
          
           fxSubI = MetodosUniversales.evaluarFuncion(funcion, valorXSubI);
           fxSubIMenosUno = MetodosUniversales.evaluarFuncion(funcion, valorXSubIMenosUno);
           auxiliar = valorXSubI - (fxSubI*(valorXSubIMenosUno-valorXSubI)/(fxSubIMenosUno - fxSubI));
           
           valorXSubIMenosUno = valorXSubI;
           valorXSubI= auxiliar;
           if (iterador >= 0) {
                errorAproximacion = MetodosUniversales.errorAprox(valorXSubI,valorXSubIMenosUno); 
            }
           matriz[0][iterador] = auxiliar;
           matriz[1][iterador] = fxSubIMenosUno;
           matriz[2][iterador] = fxSubI;
           matriz[3][iterador] = errorAproximacion;
           
            iterador++;

        } while (errorAproximacion >= errorTolerancia && iterador < iteracionesMaximas);
        return valorXSubI;
    }
    /**
     * @return Metodo encargado de mostrar los resultados del metodo de la secante en una matriz de datos
     */
    @Override
    public void imprimirResultados() {
         int  xSubIMasUno = 0, fxSubIMenosUno = 1, fxSubI = 2, errorAproximacion = 3;
        
        System.out.format("%5s %20s %20s %20s %20s",
                "iter.", "xi+1","fxi-1", "fxi","ea\n");

        for (int i = 0; i < matriz[xSubIMasUno].length; i ++){
            // La condición evalua si el error de aproximacion es diferente de cero, si resulta asi, imprime.
            // Escogimos el error de aporoximacion porque es un valor que si es cero significa que esa zona de la matriz hacia abajo esta vacía.
            // Porque la matriz se crea con iMax y algunos filas no siempre se llenan.
            if (matriz[errorAproximacion][i] != 0 || i == 0) {
                System.out.format("%5s %20s %20s %20s %20s",
                i, matriz[xSubIMasUno][i], matriz[fxSubIMenosUno][i], matriz[fxSubI][i], 
                matriz[errorAproximacion][i] +"\n");
            }
        }
    }
    
    
}
