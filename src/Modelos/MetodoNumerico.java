package Modelos;

/**
 * Clase padre de todos los métodos numéricos
 * @author Paulo Aguilar
 */
public class MetodoNumerico {
    private String funcion;
    private double errorTolerancia;
    private int iteracionesMaximas;
    private String[][] matrizDeDatos;

    /**
     * Constructor que inicializa los parámetros básicos de un método Numérico
     * @param funcion Función a evaluar
     * @param errorTolerancia Error de tolerancia a considerar
     * @param iteracionesMaximas Número límite de iteraciones
     * @param matriz Matriz de datos vacía
     */
    public MetodoNumerico(String funcion, double errorTolerancia, int iteracionesMaximas,String[][] matriz){
        super();
        this.funcion = funcion;
        this.errorTolerancia = errorTolerancia;
        this.iteracionesMaximas = iteracionesMaximas;
        this.matrizDeDatos = matriz;
    }
    
    public String getFuncion() {
        return funcion;
    }

    public double getErrorTolerancia() {
        return errorTolerancia;
    }

    public int getIteracionesMaximas() {
        return iteracionesMaximas;
    }

    public String[][] getMatrizDeDatos() {
        return matrizDeDatos;
    }

    public void setErrorTolerancia(double errorTolerancia) {
        this.errorTolerancia = errorTolerancia;
    }

    
}
