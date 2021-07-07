
package Pruebas;

import Modelos.Biseccion;
import Util.MetodosUniversales;


public class pruebas {

    
    public static void main(String[] args) throws Exception {
        mostrarBiseccion();
    }
    
    public static void mostrarBiseccion() throws Exception{
         Biseccion op = new Biseccion("x^3 - 7*x^2 + 14*x - 6 ", 3.1, 5, 20 ,0.01);
         System.out.println("El resultado es: "+ op.metodoBiseccion());
         op.imprimirResultados();
       
    }
    
}
