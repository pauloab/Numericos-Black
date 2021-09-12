

package Modelos.RungeKutta;

import java.util.ArrayList;

/**
 *Modelo para el cálculo por fórmulas de Runge-Kutta por mètodo de Heun
 * 
 * @author Javier Matamoros
 */
public class Heun {
    
    private double xSubCero;
    private double ySubCero;
    private double tamanoPaso;
    private String EDO;
    private double tope;
    private int iteracionesMaximas;
    private double errorTolerancia;
    private ArrayList<String[]> datos;

    public Heun(double xSubCero, double ySubCero, double tamanoPaso, String EDO, double top, int iteracionesMaximas, double errorTolerancia) {
        this.xSubCero = xSubCero;
        this.ySubCero = ySubCero;
        this.tamanoPaso = tamanoPaso;
        this.EDO = EDO;
        this.tope = top;
        this.iteracionesMaximas = iteracionesMaximas;
        this.errorTolerancia = errorTolerancia;
    }

    public void setxSubCero(double xSubCero) {
        this.xSubCero = xSubCero;
    }

    public void setySubCero(double ySubCero) {
        this.ySubCero = ySubCero;
    }

    public void setTamanoPaso(double tamanoPaso) {
        this.tamanoPaso = tamanoPaso;
    }

    public void setEDO(String EDO) {
        this.EDO = EDO;
    }

    public void setIteracionesMaximas(int iteracionesMaximas) {
        this.iteracionesMaximas = iteracionesMaximas;
    }

    public void setErrorTolerancia(double errorTolerancia) {
        this.errorTolerancia = errorTolerancia;
    }

    public void setTope(double tope) {
        this.tope = tope;
    }

    public String[][] getDatos() {
        String [] [] matriz = new String[datos.size()][6];
        for(int i = 0; i<matriz.length; i++){
            matriz [i] = datos.get(i);
        }
        return matriz;
    }
    
    
    
    public void heun() throws Exception{
       double dx = 0,x = xSubCero,y = ySubCero, predictora = 0, errorAproximacion =0, aux = 0, dx2 = 0, slope = 0;
       datos = new ArrayList<>();
       int iter =0;
       int nc = (int) ((tope - x) / tamanoPaso);
       String [] fila =  new String [6];
       fila [0] = x +"";
       fila [4] = y + "";
       datos.add(fila);
        for ( int i = 0; i < nc; i++ ) {
            dx = Util.Matematico.evaluarFuncionDosVariables(EDO, x, y);
            predictora = y + dx * tamanoPaso;
            fila = new String [6];
            fila [0] = x + "";
            fila [1] = dx + "";
            fila [2] = predictora + "";
            iter=0;
            do {
                aux = predictora;
                dx2 = Util.Matematico.evaluarFuncionDosVariables(EDO, x + tamanoPaso, predictora);
                slope = (dx + dx2) / 2;
                predictora = y + slope * tamanoPaso;
                iter++;
                errorAproximacion = Util.Matematico.errorAprox(predictora, aux);
                fila [3] = dx2 + "";
                fila[4] = predictora + "";
                fila[5] = errorAproximacion + "";
                datos.add(fila);
                fila = new String [6];
            } while (errorAproximacion >= errorTolerancia && iter <= iteracionesMaximas);
            y = predictora;
            x += tamanoPaso;
        }
       
    }
    
    public void imprimirMatriz(){
        int x = 0, fxiyi = 1, yCero = 2, dx2 = 3, y = 4, ea = 5;
        System.out.format("%5s %20s %20s %20s %20s %20s %20s",
                "iter.", "Xi", "F(Xi, Yi)", "Y^0i+1","f(Xi+1,Y^0i+1)" ,"Yi+1","Ea\n");
        for (int i = 0; i < getDatos().length; i++) {
            System.out.format("%5s %20s %20s %20s %20s %20s %20s",
                    i, getDatos()[i][x], getDatos()[i][fxiyi],getDatos()[i][yCero],getDatos()[i][dx2], getDatos()[i][y],getDatos()[i][ea] + "\n");
        }
    }
  
}
