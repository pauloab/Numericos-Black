
package Modelos;

import Util.MetodosUniversales;

/**
 * 
 * @author Geovanny Vega
 * Clase que contiene el metodo de falsa posición
 * @version 1.0
 */
public class FalsaPosicion {
    
    private String funcion ;
    private double cotaInferior;
    private double cotaSuperior;
    private int iteracionesMaximas;
    private double es;
    private double matriz [][];

    /**
     * 
     * @param funcion funcion de la cual se desea obtener la raiz
     * @param cotaInferior cota inferior del intervalo
     * @param cotaSuperior cota superior del intervalo
     * @param iteracionesMaximas valor maximo de iteraciones
     * @param cifras cifras significativas que se desean
     */
    public FalsaPosicion(String funcion, double cotaInferior, double cotaSuperior, int iteracionesMaximas,double es) {
        this.funcion = funcion;
        this.cotaInferior = cotaInferior;
        this.cotaSuperior = cotaSuperior;
        this.iteracionesMaximas = iteracionesMaximas;
        this.es = es;
        this.matriz = new double [7][iteracionesMaximas]; 
        
        
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public double getXl() {
        return cotaInferior;
    }

    public void setXl(double cotaInferior) {
        this.cotaInferior = cotaInferior;
    }

    public double getXu() {
        return cotaSuperior;
    }

    public void setXu(double cotaSuperior) {
        this.cotaSuperior = cotaSuperior;
    }

    public int getImax() {
        return iteracionesMaximas;
    }

    public void setImax(int iteracionesMaximas) {
        this.iteracionesMaximas = iteracionesMaximas;
    }

    public double getEs() {
        return es;
    }

    public void setEs(double es) {
        this.es = es;
    }
    /**
     * fila 1 = cotaInferior
     * fila 2 = cotaSuperior
     * fila 3 = xr
     * fila 4 = f(cotaInferior)
     * fila 5 = f(cotaSuperior)
     * fila 6 = f(xr)
     * fila 7 = ea;
     * @return Matriz con los datos del algortimo
     */
     public double[][] getMatriz() {
        return matriz;
    }
    
     /**
      * Implementacion del metodo de falsa posición
      * @throws Exception Esta excepcion va a ocurrir cuando no se pueda evaluar la funcion en un determinado punto.
      */
    public double metodoFalsaPosicion() throws Exception{
        double ea = es + 1;
        int i = 0;
        double xol = 0, fxr = 0, xr = 0,fcotaInferior = 0, fcotaSuperior = 0, fcotaInferiorx = 0;
        
       do {
           xol = xr;
           fxr = MetodosUniversales.evaluarFuncion(funcion, xr);
           fcotaInferior = MetodosUniversales.evaluarFuncion(funcion, cotaInferior);
           fcotaSuperior =  MetodosUniversales.evaluarFuncion(funcion, cotaSuperior);
           xr = cotaSuperior - ((fcotaSuperior*(cotaInferior-cotaSuperior))/(fcotaInferior-fcotaSuperior));
           fcotaInferiorx = fcotaInferior * fxr;
           if (i > 0 ){
                 ea = MetodosUniversales.errorAprox(xr,xol);
           }
         
               matriz [0][i] = cotaInferior;
               matriz [1][i] = cotaSuperior; 
               matriz [2][i] = xr; 
               matriz [3][i] = fcotaInferior; 
               matriz [4][i] = fcotaSuperior; 
               matriz [5][i] = fxr; 
               matriz [6][i] = ea; 
               
           
           if(fcotaInferiorx < 0){
               cotaSuperior = xr;
           }else if (fcotaInferiorx > 0){
               cotaInferior = xr;
               
           }else {
               ea = 0;
           }
         i++;
         
       }while (ea >= es && i < iteracionesMaximas);
       return xr;
    }
    
}
