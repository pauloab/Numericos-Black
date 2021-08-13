package Modelos.Interpolacion;

/**
 * Modelo para procesamiento del método de ajuste de curvas de interpolación de
 * lagrange
 *
 * @author Paulo Aguilar
 */
public class InterpolacionLagrange {

    private double[] xCordenadasTrabajo;
    private double[] yCordenadasTrabajo;
    private double x;
    private String funcion;

    /**
     * Constructor de la clase
     * @param x Punto al cual se desea aproximar
     * @param xCordenadasTrabajo
     * @param yCordenadasTrabajo 
     */
    public InterpolacionLagrange(double[] xCordenadasTrabajo, double[] yCordenadasTrabajo, double x) throws Exception {
        this.xCordenadasTrabajo = xCordenadasTrabajo;
        this.yCordenadasTrabajo = yCordenadasTrabajo;
        
        boolean mayor = false, menor = false;
        for (int i = 0; i < xCordenadasTrabajo.length; i++) {
            if (xCordenadasTrabajo[i]>x && !mayor) {
                mayor = true;
            }
            if (xCordenadasTrabajo[i]<x && !menor) {
                menor = true;
            }
        }
        if (!menor || !mayor) {
            throw new Exception("Verifique que el punto esté entre las "
                    + "coordenadas de trabajo");
        }
        funcion = "";
        this.x = x;
    }

    public String getFuncion() {
        return funcion;
    }
    
    public double interpolacion(){
        int grado = xCordenadasTrabajo.length;
        double res = 0;
        double producto;
        for (int i = 0; i < grado; i++) {
            producto = yCordenadasTrabajo[i];
            funcion += "("+producto+")*(";
            for (int j = 0; j < grado; j++) {
                if (i!=j) {
                    funcion += "((x-("+xCordenadasTrabajo[j]+"))"
                            + "/("+(xCordenadasTrabajo[i]-xCordenadasTrabajo[j])+"))*";
                    producto *= (x-xCordenadasTrabajo[j])/(xCordenadasTrabajo[i]-xCordenadasTrabajo[j]);
                }
            }
            funcion = funcion.substring(0,funcion.length()-1);
            funcion += ")+";
            res += producto;
        }
        funcion = funcion.substring(0,funcion.length()-1);
        return res;
    }
}
