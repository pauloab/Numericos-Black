
package Modelos;

import Util.MetodosUniversales;

/**
 * Clase creada para la implementacion del metodo de la secante.
 * @author Javier Matamoros 
 * @version 1.0
 * 
 */
public class Secante implements MetodosNumericos {
    
    
    private String funcion;
    private double xSubI;
    private double xSubIMenos1;
    private int iteracionesMax;
    private double errorTolerancia;
    private double matriz [][];

    /**
     * Constructor del metodo de la secante encagardo de inicizalizar cada uno de los atributos
     * @param funcion atributo encargado de guardar la funcion de la cual se desea obtener la raiz
     * @param xSubI valores iniciales de x
     * @param xSubIMenos1 valores iniciales de x
     * @param iteracionesMax valor maximo de iteraciones
     * @param errorTolerancia error de tolerancia
     */
    public Secante(String funcion, double xSubI, double xSubIMenos1, int iteracionesMax, double errorTolerancia) {
        this.funcion = funcion;
        this.xSubI = xSubI;
        this.xSubIMenos1 = xSubIMenos1;
        this.iteracionesMax = iteracionesMax + 1;
        this.errorTolerancia = errorTolerancia;
        this.matriz = new double [4][iteracionesMax + 1];
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public double getxSubI() {
        return xSubI;
    }

    public void setxSubI(double xSubI) {
        this.xSubI = xSubI;
    }

    public double getxSubIMenos1() {
        return xSubIMenos1;
    }

    public void setxSubIMenos1(double xSubIMenos1) {
        this.xSubIMenos1 = xSubIMenos1;
    }

    public int getIteracionesMax() {
        return iteracionesMax;
    }

    public void setIteracionesMax(int iteracionesMax) {
        this.iteracionesMax = iteracionesMax;
    }

    public double getErrorTolerancia() {
        return errorTolerancia;
    }

    public void setErrorTolerancia(double errorTolerancia) {
        this.errorTolerancia = errorTolerancia;
    }

    /**
     * fila 1 = x sub i menos 1
     * fila 2 = f(xi-1) funcion evaluada en el punto x sub i menos 1
     * fila 3 = f (xi) funcion evaluada en el punto x sub i
     * fila 4 = error de aproximacion (ea)
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
        
        double ea = errorTolerancia + 1;
        int i = 2;
        double xtemp = 0, fxSubi = 0, fxSubiMenos1 = 0;
        matriz[0][0] = xSubIMenos1;
        matriz[0][1] = xSubI;
        ea = MetodosUniversales.errorAprox(xSubI,xSubIMenos1);
        matriz[3][1] = ea;
        do {
          
           fxSubi = MetodosUniversales.evaluarFuncion(funcion, xSubI);
           fxSubiMenos1 = MetodosUniversales.evaluarFuncion(funcion, xSubIMenos1);
           xtemp = xSubI - (fxSubi*(xSubIMenos1-xSubI)/(fxSubiMenos1 - fxSubi));
           
           xSubIMenos1 = xSubI;
           xSubI= xtemp;
           if (i >= 0) {
                ea = MetodosUniversales.errorAprox(xSubI,xSubIMenos1); 
            }
           matriz[0][i] = xtemp;
           matriz[1][i] = fxSubiMenos1;
           matriz[2][i] = fxSubi;
           matriz[3][i] = ea;
           
            i++;

        } while (ea >= errorTolerancia && i < iteracionesMax);
        return xSubI;
    }
    /**
     * @return Metodo encargado de mostrar los resultados del metodo de la secante en una matriz de datos
     */
    @Override
    public void imprimirResultados() {
         int  xl = 0, fx1 = 1, fx0 = 2, ea =3;
        
        System.out.format("%5s %20s %20s %20s %20s",
                "iter.", "xi+1","fxi-1", "fxi","ea\n");

        for (int i = 0; i < matriz[xl].length; i ++){
            if (matriz[ea][i] != 0 || i ==0) {
                System.out.format("%5s %20s %20s %20s %20s",
                i, matriz[xl][i], matriz[fx1][i], matriz[fx0][i], 
                matriz[ea][i] +"\n");
            }
        }
    }
    
    
}
