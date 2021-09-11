package Modelos.RungeKutta;

import Util.Matematico;

/**
 * Modelo para el cálculo por fórmulas de Runge-Kutta por fórmula de Euler
 *
 * @author Paulo Aguilar
 */
public class Euler {

    private double x0;
    private double y0;
    private double b;
    private String EDO;
    private double h;
    private String[][] datos;

    public Euler(double x0, double y0, double b, String EDO, double h) {
        this.x0 = x0;
        this.y0 = y0;
        this.b = b;
        this.EDO = EDO;
        this.h = h;
    }

    public String[][] euler() throws Exception {
        double dydx, x, y = 0, fx;
        int nc = (int) ((b - x0) / h);
        this.datos = new String[nc + 1][3];
        x = x0;
        y = y0;
        for (int i = 0; i < nc; i++) {
            fx = Matematico.evaluarFuncionDosVariables(EDO, x, y);
            datos[i][0] = x + "";
            datos[i][2] = y + "";
            datos[i + 1][1] = fx + "";
            dydx = y + fx * h;
            y = dydx;
            x += h;
        }
        datos[nc][0] = x + "";
        datos[nc][2] = y + "";
        return datos;
    }

    public String[][] getDatos() {
        return datos;
    }
    
    public void imprimirMatriz() {
        int x = 0, y = 1, fx = 2;

        System.out.format("%5s %20s %20s %20s",
                "iter.", "x", "f(x)", "y\n");

        for (int i = 0; i < datos.length; i++) {
            System.out.format("%5s %20s %20s %20s",
                    i, datos[i][x],  (datos[i][y] != null ? datos[i][y] : ""), (datos[i][fx] != null ? datos[i][fx] : "") + "\n");
        }
    }
}
