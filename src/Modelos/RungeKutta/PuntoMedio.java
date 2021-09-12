
package Modelos.RungeKutta;

/**
 *Modelo para el cálculo por fórmulas de Runge-Kutta por mètodo de Punto Medio
 * 
 * @author Javier Matamoros
 */
public class PuntoMedio {
    
    private double x0;
    private double y0;
    private double b;
    private String EDO;
    private double h;
    private String[][] datos;

    public PuntoMedio(double x0, double y0, double b, String EDO, double h) {
        this.x0 = x0;
        this.y0 = y0;
        this.b = b;
        this.EDO = EDO;
        this.h = h;
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

    public void setDatos(String[][] datos) {
        this.datos = datos;
    }

    public String[][] getDatos() {
        return datos;
    }
    
    public void puntoMedio() throws Exception{
        double dx = 0, x = x0, y = y0, ym =0, dx2 = 0;
        int nc = (int) ((b - x) / h);
        this.datos = new String[nc + 1][3];
        for (int i = 0; i < nc+1 ; i++  ) {
            datos[i][0] = x + "";
            datos[i][1] = y + "";  
            dx = Util.Matematico.evaluarFuncionDosVariables(EDO, x, y);
            ym = y + dx * h / 2;
            dx2 = Util.Matematico.evaluarFuncionDosVariables(EDO, x + h/2, ym);
            datos[i][2] = dx2 + "";
            y = y + dx2 * h;
            x += h;
        }
        
    }
    
    public void imprimirMatriz(){
        int x = 0, y = 1, k2 = 2;
        System.out.format("%5s %20s %20s %20s",
                "iter.", "x", "yi+1", "K2\n");
        for (int i = 0; i < datos.length; i++) {
            System.out.format("%5s %20s %20s %20s",
                    i, datos[i][x],  (datos[i][y] != null ? datos[i][y] : ""), (datos[i][k2] != null ? datos[i][k2] : "") + "\n");
        }
    }
    
    
}
