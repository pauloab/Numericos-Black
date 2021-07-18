
package Modelos;
import Util.MetodosUniversales;
/**
 *
 * @author Freddy Lamar
 * @version 1.0
 */


public class NewtonRaphson implements MetodosNumericos{
    private String funcion ;
    private double x0;

    private int iMax;
    private double es;
    private double matriz [][];
    
    /**
        * Constructor de Newton Raphson, crea el objeto con los parametros requeridos.
        * 
        * @param funcion    funcion de la cual se desea obtener la raiz.
        * @param x0         Parametro x0
        * @param iMax       valor maximo de iteraciones.
        * @param es         Error Aproximado
        */
      
    public NewtonRaphson(String funcion, double x0,  int iMax, double es) {
        this.funcion = funcion;
        this.x0 = x0;
        this.iMax = iMax;
        this.es = es;
        this.matriz = new double [5][iMax];
    }
    
         public NewtonRaphson() {
    }       
         /**
          * 
          * @param funcion 
          */
         
    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }
    /**
     * 
     * @return 
     */
    public double getX0() {
        return x0;
    }
    /**
     * 
     * @param x0 
     */
    public void setX0(double x0) {
        this.x0 = x0;
    }

    /**
     * 
     * @return 
     */
    public int getiMax() {
        return iMax;
    }
    /**
     * 
     * @param iMax 
     */
    public void setiMax(int iMax) {
        this.iMax = iMax;
    }
    /**
     * 
     * @return 
     */
    public double getEs() {
        return es;
    }
    /**
     * 
     * @param es 
     */
    public void setEs(double es) {
        this.es = es;
    }
     /***
     * Columna 1 = Iteraciones
     * Columna 2 = xi + 1
     * Columna 3 = f(xi)
     * Columna 4 = f'(xi)
     * Columna 5 = ea
     * @return Matriz con los datos del algortimo
     */
    public double[][] getMatriz() {
        return matriz;
    }
    
    public void setMatriz(double[][] matriz) {
        this.matriz = matriz;
    }
    
      /**
      * Implementacion del metodo de falsa posición
      * @throws Exception Esta excepcion va a ocurrir cuando no se pueda evaluar la funcion en un determinado punto.
      */

    public double metodoNewtonRaphson() throws Exception{       
        double ea = es + 1;
        int i = 1;
        double xol = 0, fxi1 = 0, xi1 = 0, fxi1p = 0;
        xi1 = x0;
        
               matriz [0][0] = 0;
               matriz [1][0] = xi1;
       do {
           xol = xi1;
           fxi1 = MetodosUniversales.evaluarFuncion(funcion, xol);
           fxi1p = MetodosUniversales.evaluarFuncion(MetodosUniversales.derivar(funcion), xol);
           
           if (fxi1p == 0) {
              return xi1;
           }else{
                xi1 = xol - (fxi1/fxi1p); 
           }
                 ea = MetodosUniversales.errorAprox(xi1,xol);
                 
      
               matriz [0][i] = i;
               matriz [1][i] = xi1; 
               matriz [2][i] = fxi1; 
               matriz [3][i] = fxi1p; 
               matriz [4][i] = ea; 
               
         i++;
         
       }while (ea >= es && i < iMax);
       return xi1;
    }
    
    @Override
    public void imprimirResultados() {

        int ite = 0, xi = 1, fxi =2, fxip =3, ea = 4 ;  
           System.out.format("%5s %20s %20s %20s %20s",
                "iter.", "xi+1","f(xi)", "f'(xi)","ea\n");
        for (int i = 0; i < matriz[ite].length; i ++){
            // La condición evalua si fxip es diferente de cero, si resultada imprime.
            // Escogimos fxip porque es un valor que si es cero significa que esa zona de la matriz hacia abajo esta vacía.
            // Porque la matriz se crea con iMax y algunos filas no siempre se llenan.
            if (matriz [fxip][i] != 0 || i == 0 ) {
                System.out.format("%5s %20s %20s %20s %20s", 
                matriz[ite][i], matriz[xi][i], matriz[fxi][i], 
                matriz[fxip][i], matriz[ea][i] + "\n");
            }
            System.out.flush(); 
        } 
    }
    
}

