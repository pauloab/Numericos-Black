package Modelos.GaussLegendre;

import Util.Matematico;

/**
 * Objeto para calcular la cuadratura de Gauss
 * 
 * @author Freddy Lamar
 */
public class CuadraturaGauss {
    
    private String funcion;
    private double a;
    private double b;
    private int n;
    
    public static final double[][] coeficientes = {
        {1, 1},
        {0.5555556, 0.8888889, 0.5555556},
        {0.3478548, 0.6521452, 0.6521452, 0.3478548},
        {0.2369269, 0.4786287, 0.5688889, 0.4786287, 0.2369269},
        {0.1713245, 0.3607616, 0.4679139, 0.4679139, 0.3607616, 0.1713245}
    };
    
    public static final double[][] xCoeficientes = {
        {-0.577350269, 0.577350269},
        {-0.774596669, 0, 0.774596669},
        {-0.861136312, -0.339981044, 0.339981044, 0.861136312},
        {-0.906179846, -0.538469310, 0, 0.538469310, 0.906179846},
        {-0.932469514, -0.661209386, -0.238619186, 0.238619186, 0.661209386, 0.932469514}
    };

    public static final int MAX_NUM_PUNTOS = xCoeficientes.length;
    
    /**
     * Constructor de la clase
     * @param funcion Función a integrar 
     * @param a valor minimo de x
     * @param b valor máximo de x
     * @param n Número de puntos de la cuadratura (de 2 a 6)
     */
    public CuadraturaGauss(String funcion, double a, double b, int n) {
        this.funcion = funcion;
        this.a = a;
        this.b = b;
        this.n = n;
    }

    public String getFuncion() {
        return funcion;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public int getN() {
        return n;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public void setA(double a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void setN(int n) {
        this.n = n;
    }
    
    /**
     * Método que aproxima el área bajo la curva de la función ingresada
     * mediante el método de cuadratura de Gauss
     * 
     * @return Aproximación resultante
     * @throws Exception Ocurre en caso de que no se pueda evaluar la función
     */
    public double cuadraturaDeGauss() throws Exception{
        double sum = 0;
        double x, dx;
        if (n >= 1) {
            dx = (b-a)/2;
            for (int i = 0; i < n; i++) {
                x = ((b+a)/2)+((b-a)/2)*xCoeficientes[n-2][i];
                sum += coeficientes[n-2][i]*Matematico.evaluarFuncion(funcion,x)*dx;
            }
        }
        return sum;
    }
    
    /**
     * Extrae y retorna los coeficientes utilizados para el cálculo de la aproximación
     * 
     * @return matriz de datos, con los coeficientes de c en la posicion 0 y x en la posición 1
     */
    public String[][] getCoeficientes(){
        String[][] datos = new String[n][2];
        for (int i = 0; i < n; i++) {
            datos[i][0] = ""+coeficientes[n-2][i];
            datos[i][1] = ""+xCoeficientes[n-2][i];
        }
        return datos;
    }
}
