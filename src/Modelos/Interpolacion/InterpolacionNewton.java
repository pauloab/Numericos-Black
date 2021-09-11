package Modelos.Interpolacion;

/**
 * Clase para interpolación de newton por diferencias divididas
 * 
 * @author Paulo Aguilar
 */
public class InterpolacionNewton {
    
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
    public InterpolacionNewton(double[] xCordenadasTrabajo, double[] yCordenadasTrabajo, double x) throws Exception {
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
    
    public double diferenciasDivididas(){
        funcion = "";
        int grado = xCordenadasTrabajo.length;
        double[][] fdd = new double[grado][grado];
        double xterm = 1,yInt2;
        double[] yInt = new double[grado];
        for (int i = 0; i < grado; i++) {
            fdd[i][0] = yCordenadasTrabajo[i];
        }
        for (int j = 1; j < grado; j++) {
            for (int i = 0; i < grado-j; i++) {
                fdd[i][j] = (fdd[i+1][j-1]-fdd[i][j-1])/(xCordenadasTrabajo[j+i]-xCordenadasTrabajo[i]);
            }
        }
        yInt[0] = fdd[0][0]; 
        funcion += fdd[0][0]+"";
        for (int i = 1; i < grado; i++) {
            funcion += "+";
            funcion += "(x - "+xCordenadasTrabajo[i-1]+")";
            xterm *= (x-xCordenadasTrabajo[i-1]);
            for (int j = 1; j < i; j++) {
                funcion += "*(x - "+xCordenadasTrabajo[j-1]+")";
            }
            funcion += "*"+fdd[0][i];
            yInt2 = yInt[i-1] + fdd[0][i]*xterm;
            yInt[i] = yInt2;
        }
        
        return yInt[grado-1];
    }

    public String getFuncion() {
        return funcion;
    }
    
    
}
