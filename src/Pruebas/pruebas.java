
package Pruebas;

import Modelos.Biseccion;
import Modelos.FalsaPosicion;
import Modelos.Muller;



public class pruebas {

    
    public static void main(String[] args) throws Exception {
        //mostrarBiseccion();
        //mostrarFalsaPosicion();
        mostarMuller();
    }
    
    public static void mostrarBiseccion() throws Exception{
         Biseccion op = new Biseccion("x^3 - 7*x^2 + 14*x - 6 ", 3.1, 5, 20 ,0.01);
         System.out.println("El resultado es: "+ op.metodoBiseccion());
         op.imprimirResultados();
       
    }
    /*
    Método de mostrarFalsaPosicion crea un objeto para inicializar una prueba
    */
        public static void mostrarFalsaPosicion() throws Exception{
        FalsaPosicion op = new FalsaPosicion(" (pi*x^2*(9-x)/3)-30 ", 0, 3, 10 ,0.1);
        System.out.println("El resultado es: "+ op.metodoFalsaPosicion());
        op.imprimirResultados();
        
    }
        
        public static void mostarMuller()throws Exception{
           Muller op = new Muller(" -3*x^5+2*x^4+15*x^2-10*x-1 ", 1, 0.5, 1 ,0.0005);
        System.out.println("El resultado es: "+ op.metodoMuller());
        op.imprimirResultados(); 
        }
    
}
