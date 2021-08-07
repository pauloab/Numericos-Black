
package Modelos.MetodosAproxRaices;

import Util.Matematico;
import Modelos.MetodoImprimible;
import Modelos.MetodoAproximadorRaices;

/**
 * Clase que contiene el metodo de biseccion
 * Formato de la matríz de datos
 * Columna 1 = cotaInferior
 * Columna 2 = cotaSuperior
 * Columna 3 = xr
 * Columna 4 = f(cotaInferior)
 * Columna 5 = f(cotaSuperior)
 * Columna 6 = f(xr)
 * Columna 7 = ea
 * @author Javier Matamoros
 * @version 1.0
 */
public class Biseccion extends MetodoAproximadorRaices implements MetodoImprimible {
    
    private double cotaInferior;
    private double cotaSuperior;
    
    /**
     * 
     * @param funcion funcion de la cual se desea obtener la raiz
     * @param cotaInferior cota inferior del intervalo
     * @param cotaSuperior cota superior del intervalo
     * @param iteracionesMax valor maximo de iteraciones
     * @param errorTolerancia error de tolerancia
     */
    public Biseccion(String funcion, double errorTolerancia, int iteracionesMax, double cotaInferior, double cotaSuperior) {
        super(funcion, errorTolerancia, iteracionesMax, new String[iteracionesMax][7]);
        this.cotaInferior = cotaInferior;
        this.cotaSuperior = cotaSuperior;
   
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
    
     /**
      * Implementacion del metodo de biseccion
      * @throws Exception Esta excepcion va a ocurrir cuando no se pueda evaluar la funcion en un determinado punto.
      */
    public double metodoBiseccion() throws Exception{
        double ea = 1;
        int i = 0;
        double xol, fxr, xr = 0,fcotaInferior, fcotaSuperior, fcotaInferiorx;
        
        do {
           xol = xr;
           xr = (cotaInferior + cotaSuperior)/2;
           fxr = Matematico.evaluarFuncion(getFuncion(), xr);
           fcotaInferior = Matematico.evaluarFuncion(getFuncion(), cotaInferior);
           fcotaSuperior =  Matematico.evaluarFuncion(getFuncion(), cotaSuperior);
           fcotaInferiorx = fcotaInferior * fxr;
           if (i > 0 ){
                 ea = Matematico.errorAprox(xr,xol);
           }
         
               getMatrizDeDatos()[i][0] = cotaInferior+"";
               getMatrizDeDatos()[i][1] = cotaSuperior+"";
               getMatrizDeDatos()[i][2] = xr+"";
               getMatrizDeDatos()[i][3] = fcotaInferior+""; 
               getMatrizDeDatos()[i][4] = fcotaSuperior+"";
               getMatrizDeDatos()[i][5] = fxr+"";
               getMatrizDeDatos()[i][6] = i==0?"":ea+""; 
               
           
           if(fcotaInferiorx < 0){
               cotaSuperior = xr;
           }else if (fcotaInferiorx > 0){
               cotaInferior = xr;
               
           }else {
               ea = 0;
           }
         i++;
         
       }while (ea >= getErrorTolerancia() && i < getIteracionesMaximas());
       return xr;
    }
    /**
     * Método encargado de mostrar los resultados del metodo de biseccion en
     * una matriz de datos
     */
    @Override
    public void imprimirResultados() {
        int  xl = 0, xu = 1, xr = 2, fxl =3, fxu = 4, fxr =5, erroAproximacion=6;
        
        System.out.format("%5s %20s %20s %20s %25s %25s %25s %25s",
                "iter.", "xl","xu", "xr","f(xl)","f(xu)","f(xr)","ea\n");

        for (int i = 0; i < getMatrizDeDatos().length; i ++){
            // Verifica que no se imprima la parte vacia de la matriz
            if (getMatrizDeDatos()[i][0] != null) {
                System.out.format("%5s %20s %20s %20s %25s %25s %25s %25s",
                i, getMatrizDeDatos()[i][xl], getMatrizDeDatos()[i][xu], getMatrizDeDatos()[i][xr], 
                getMatrizDeDatos()[i][fxl], getMatrizDeDatos()[i][fxu], getMatrizDeDatos()[i][fxr], 
                getMatrizDeDatos()[i][erroAproximacion] +"\n");
            }
        }
    }
    
}
