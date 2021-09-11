package Modelos.RungeKutta;

import Util.Matematico;

/**
 * Método de Rungen-Kutta de cuarto orden (Cláfica)
 *
 * @author Paulo Aguilar
 */
public class RKCuartoOrden {

    private double x0;
    private double y0;
    private double b;
    private String EDO;
    private double h;
    private String[][] datos;

    public RKCuartoOrden(double x0, double y0, double b, String EDO, double h) {
        this.x0 = x0;
        this.y0 = y0;
        this.b = b;
        this.EDO = EDO;
        this.h = h;
    }

    public void RKCuarto() throws Exception {
        double x = x0, y = y0, ym, k1, k2, k3, k4, ye, pendiente;
        int nc = (int) ((b - x0) / h)+1;
        datos = new String[nc][6];
        for (int i = 0; i < nc; i++) {
            datos[i][0] = x+"";
            datos[i][1] = y+"";
            k1 = Matematico.evaluarFuncionDosVariables(EDO, x, y);
            ym = y + k1 * h / 2;
            k2 = Matematico.evaluarFuncionDosVariables(EDO, x + h / 2, ym);
            ym = y + k2 * h / 2;
            k3 = Matematico.evaluarFuncionDosVariables(EDO, x + h / 2, ym);
            ye = y + k3 * h;
            k4 = Matematico.evaluarFuncionDosVariables(EDO, x + h, ye);
            pendiente = (k1 + 2 * (k2 + k3) + k4) / 6;
            datos[i][2]=k1+"";
            datos[i][3]=k2+"";
            datos[i][4]=k3+"";
            datos[i][5]=k4+"";
            x += h;
            y += pendiente * h;
        }
    }

    public String[][] getDatos() {
        return datos;
    }

    public void setX0(double x0) {
        this.x0 = x0;
    }

    public void setY0(double y0) {
        this.y0 = y0;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void setEDO(String EDO) {
        this.EDO = EDO;
    }

    public void setH(double h) {
        this.h = h;
    }
    
     public void imprimirMatriz() {
        int x = 0, y = 1, k1 = 2, k2 = 3, k3 = 4, k4 = 5;

        System.out.format("%5s %20s %20s %20s %20s %20s %20s",
                "iter.", "x", "y", "k1", "k2", "k3", "k4\n");

        for (int i = 0; i < datos.length; i++) {
            System.out.format("%5s %20s %20s %20s %20s %20s %20s",
                    i, datos[i][x],datos[i][y],datos[i][k1],datos[i][k2],datos[i][k3],
                    datos[i][k4]+ "\n");
        }
    }
}
