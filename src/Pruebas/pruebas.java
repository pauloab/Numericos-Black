
package Pruebas;

import Modelos.Biseccion;
import Modelos.FalsaPosicion;
import Util.MetodosUniversales;


public class pruebas {

    
    public static void main(String[] args) throws Exception {
        //mostrarBiseccion();
        mostrarFalsaPosicion();
    }
    
    public static void mostrarBiseccion() throws Exception{
         Biseccion op = new Biseccion("- 12 - 21*x + 18*x^2 - 2.75*x^3 ", -1, 0, 9 ,1);

        System.out.println("El resultado es: "+ op.metodoBiseccion());
        String res = "";

        for (int i = 0; i < op.getMatriz().length; i ++){
            for (int j = 0; j < op.getMatriz()[i].length; j++ ){
                if ( j ==0 ){
                        switch(i){
                        case 0:
                            res += "xl: ";
                            break;
                        case 1:
                            res += "xu: ";
                            break;
                        case 2:
                            res += "xr: ";
                            break;
                        case 3:
                            res += "fxl: ";
                            break;
                        case 4:
                            res += "fxu: ";
                            break;
                        case 5:
                            res += "fxr: ";
                            break;
                        case 6:
                            res += "ea: ";
                            break;
                    }
                } 
                res += op.getMatriz()[i][j] + "\t";
            }
            res += "\n";
        }
        System.out.println(res);
    }
    /*
    Método de mostrarFalsaPosicion crea un objeto para inicializar una prueba
    */
        public static void mostrarFalsaPosicion() throws Exception{
         FalsaPosicion op = new FalsaPosicion(" (pi*x^2*(9-x)/3)-30 ", 0, 3, 10 ,0.1);
        System.out.println("El resultado es: "+ op.metodoFalsaPosicion());
        MetodosUniversales.ImprimirFalsaPosicion(op.getMatriz());
    }
    
}
