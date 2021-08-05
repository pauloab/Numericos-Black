package Modelos.AjusteDeCurvas;

/**
 * Calculo de egresión lineal simpe a partir de un dataset.
 * @author Paulo Aguilar
 */
public class RegresionLineal {
    
    private double[] xCordenadas;
    private double[] yCordenadas;
    private double pendiente;
    private double interseccion;
    private double desviacionEstandar;
    private double errorEstandar;
    private double coeficienteDeterminacion;
    
    /**
     * Constructor de la clase
     * @param xCordenadas Vector de datos de entrada en X
     * @param yCordenadas Vector de datos de entrada en Y
     */
    public RegresionLineal(double[] xCordenadas, double[] yCordenadas){
        this.xCordenadas = xCordenadas;
        this.yCordenadas = yCordenadas;
    }

    public double getPendiente() {
        return pendiente;
    }

    public double getInterseccion() {
        return interseccion;
    }

    public double getDesviacionEstandar() {
        return desviacionEstandar;
    }

    public double getErrorEstandar() {
        return errorEstandar;
    }

    public double getCoeficienteDeterminacion() {
        return coeficienteDeterminacion;
    }
    
    public double getCoeficienteCorrelacion() {
        return Math.sqrt(coeficienteDeterminacion);
    }
    
    /**
     * Calcula la pendeinte e interseccion de la función de regresión.
     */
    public void regresionLineal(){
        double xiyi = 0;
        int n = xCordenadas.length;
        double xi = 0,yi = 0,xi2=0, promedioY, promedioX;
        double st = 0,sr = 0;
        for (int i = 0; i < n; i++) {
            xiyi += xCordenadas[i]*yCordenadas[i];
            xi += xCordenadas[i];
            yi += yCordenadas[i];
            xi2 += xCordenadas[i]*xCordenadas[i];
        }
        promedioY = yi/n;
        promedioX = xi/n;
        pendiente = (n*xiyi - (xi*yi))/((n*xi2)-xi*xi);
        interseccion = promedioY-pendiente*promedioX;
        for (int i = 0; i < n; i++) {
            st += Math.pow(yCordenadas[i]-promedioY,2);
            sr += Math.pow(yCordenadas[i]-pronosticar(xCordenadas[i]),2);
        }
        desviacionEstandar = Math.sqrt(st/(n-1));
        errorEstandar = Math.sqrt(sr/(n-2));
        coeficienteDeterminacion = (st-sr)/st;
    }
    
    public double pronosticar(double x){
        return pendiente*x+interseccion;
    }
}
