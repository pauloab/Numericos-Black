
package Pruebas;

import Modelos.Biseccion;
import Util.MetodosUniversales;


public class pruebas {

    
    public static void main(String[] args) throws Exception {
        mostrarBiseccion();
    }
    
    public static void mostrarBiseccion() throws Exception{
         Biseccion op = new Biseccion("((pi * x^2 * (9 - x))/3) - 30 ", 0, 3, 12 ,0.05);
         System.out.println("El resultado es: "+ op.metodoBiseccion());
         MetodosUniversales.ImprimirBiseccion(op.getMatriz());
       
    }
    
}
