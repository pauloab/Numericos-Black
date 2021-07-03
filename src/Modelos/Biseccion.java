
package Modelos;

import Util.MetodosUniversales;

/**
 * 
 * @author Javier Matamoros
 * Clase que contiene el metodo de biseccion
 * @version 1.0
 */
public class Biseccion {
    
    private String funcion ;
    private double xl;
    private double xu;
    private int imax;
    private double es;
    private double matriz [][];

    /**
     * 
     * @param funcion funcion de la cual se desea obtener la raiz
     * @param xl cota inferior del intervalo
     * @param xu cota superior del intervalo
     * @param imax valor maximo de iteraciones
     * @param cifras cifras significativas que se desean
     */
    public Biseccion(String funcion, double xl, double xu, int imax,double es) {
        this.funcion = funcion;
        this.xl = xl;
        this.xu = xu;
        this.imax = imax;
        this.es = es;
        this.matriz = new double [7][imax]; 
        
        
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public double getXl() {
        return xl;
    }

    public void setXl(double xl) {
        this.xl = xl;
    }

    public double getXu() {
        return xu;
    }

    public void setXu(double xu) {
        this.xu = xu;
    }

    public int getImax() {
        return imax;
    }

    public void setImax(int imax) {
        this.imax = imax;
    }

    public double getEs() {
        return es;
    }

    public void setEs(double es) {
        this.es = es;
    }
    /**
     * fila 1 = xl
     * fila 2 = xu
     * fila 3 = xr
     * fila 4 = f(xl)
     * fila 5 = f(xu)
     * fila 6 = f(xr)
     * fila 7 = ea;
     * @return Matriz con los datos del algortimo
     */
     public double[][] getMatriz() {
        return matriz;
    }
    
     /**
      * Implementacion del metodo de biseccion
      * @throws Exception Esta excepcion va a ocurrir cuando no se pueda evaluar la funcion en un determinado punto.
      */
    public double metodoBiseccion() throws Exception{
        double ea = es + 1;
        int i = 0;
        double xol = 0, fxr = 0, xr = 0,fxl = 0, fxu = 0, fxlx = 0;
        
       do {
           xol = xr;
           xr = (xl + xu)/2;
           fxr = MetodosUniversales.evaluarFuncion(funcion, xr);
           fxl = MetodosUniversales.evaluarFuncion(funcion, xl);
           fxu =  MetodosUniversales.evaluarFuncion(funcion, xu);
           fxlx = fxl * fxr;
           if (i > 0 ){
                 ea = MetodosUniversales.errorAprox(xr,xol);
           }
         
               matriz [0][i] = xl;
               matriz [1][i] = xu; 
               matriz [2][i] = xr; 
               matriz [3][i] = fxl; 
               matriz [4][i] = fxu; 
               matriz [5][i] = fxr; 
               matriz [6][i] = ea; 
               
           
           if(fxlx < 0){
               xu = xr;
           }else if (fxlx > 0){
               xl = xr;
               
           }else {
               ea = 0;
           }
         i++;
         
       }while (ea >= es && i < imax);
       return xr;
    }
    
}
