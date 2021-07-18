
package Modelos;

import Util.MetodosUniversales;

/**
 * 
 * @author Javier Matamoros
 * Clase que contiene el metodo de biseccion
 * @version 1.0
 */
public class Biseccion implements MetodosNumericos {
    
    private String funcion ;
    private double cotaInferior;
    private double cotaSuperior;
    private int iteracionesMax;
    private double errorTolerancia;
    private double matriz [][];

    /**
     * 
     * @param funcion funcion de la cual se desea obtener la raiz
     * @param cotaInferior cota inferior del intervalo
     * @param cotaSuperior cota superior del intervalo
     * @param iteracionesMax valor maximo de iteraciones
     * @param errorTolerancia error de tolerancia
     */
    public Biseccion(String funcion, double cotaInferior, double cotaSuperior, int iteracionesMax,double errorTolerancia) {
        this.funcion = funcion;
        this.cotaInferior = cotaInferior;
        this.cotaSuperior = cotaSuperior;
        this.iteracionesMax = iteracionesMax;
        this.errorTolerancia = errorTolerancia;
        this.matriz = new double [7][iteracionesMax]; 
        
        
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
        return iteracionesMax;
    }

    public void setImax(int iteracionesMax) {
        this.iteracionesMax = iteracionesMax;
    }

    public double getEs() {
        return errorTolerancia;
    }

    public void setEs(double errorTolerancia) {
        this.errorTolerancia = errorTolerancia;
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
      * Implementacion del metodo de biseccion
      * @throws Exception Esta excepcion va a ocurrir cuando no se pueda evaluar la funcion en un determinado punto.
      */
    public double metodoBiseccion() throws Exception{
        double ea = errorTolerancia + 1;
        int i = 0;
        double xol = 0, fxr = 0, xr = 0,fcotaInferior = 0, fcotaSuperior = 0, fcotaInferiorx = 0;
        
       do {
           xol = xr;
           xr = (cotaInferior + cotaSuperior)/2;
           fxr = MetodosUniversales.evaluarFuncion(funcion, xr);
           fcotaInferior = MetodosUniversales.evaluarFuncion(funcion, cotaInferior);
           fcotaSuperior =  MetodosUniversales.evaluarFuncion(funcion, cotaSuperior);
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
               matriz [6][i] = i==0?0:ea; 
               
           
           if(fcotaInferiorx < 0){
               cotaSuperior = xr;
           }else if (fcotaInferiorx > 0){
               cotaInferior = xr;
               
           }else {
               ea = 0;
           }
         i++;
         
       }while (ea >= errorTolerancia && i < iteracionesMax);
       return xr;
    }

    @Override
    public void imprimirResultados() {
        int  xl = 0, xu = 1, xr = 2, fxl =3, fxu = 4, fxr =5, ea=6;
        
        System.out.format("%5s %20s %20s %20s %25s %25s %25s %25s",
                "iter.", "xl","xu", "xr","f(xl)","f(xu)","f(xr)","ea\n");

        for (int i = 0; i < matriz[xl].length; i ++){
            if (matriz[fxu][i] != 0) {
                System.out.format("%5s %20s %20s %20s %25s %25s %25s %25s",
                i, matriz[xl][i], matriz[xu][i], matriz[xr][i], 
                matriz[fxl][i], matriz[fxu][i], matriz[fxr][i], matriz[ea][i] +"\n");
            }
        }
    }
    
}
