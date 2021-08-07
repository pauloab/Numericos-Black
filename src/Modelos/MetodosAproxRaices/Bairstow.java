package Modelos.MetodosAproxRaices;

import Modelos.Excepciones.IteracionesAlcanzadas;
import Modelos.Excepciones.ValoresResueltos;
import Modelos.MetodoAproximadorRaices;

/**
 * Aproxima las raices de un polinomio mediante el método de Bairstow. Contiene
 * y retorna toda la información relevante sobre el procedimiento
 *
 * @author Javier Matamoros
 * @version 1.0
 */
public class Bairstow extends MetodoAproximadorRaices{

    private double r;
    private double s;
    private int gradoFuncion;
    private double[] coeficientesA;
    private double[] coeficientesB;
    private double[] coeficientesC;
    private double[] raicesReales;
    private double[] raicesImaginarias;

    public Bairstow(String funcion, double es, int imax, double r, double s, int grado, double[] a ) {
        super(funcion, es, imax, null);
        this.r = r;
        this.s = s;
        this.gradoFuncion = grado;
        this.coeficientesA = a;
        this.coeficientesB = new double[grado+1];
        this.coeficientesC = new double[grado+1];
        this.raicesReales = new double[grado];
        this.raicesImaginarias = new double[grado];
    }

    /**
     * Calcula el método de Bairstow una sola vez, para calcular las raices de
     * n-2 grado
     *
     * @return Vetor de string con el par de raices (de ser una sola, retorna
     * cadena vacía)
     * @throws IteracionesAlcanzadas Ocurre cuendo el método se setiene por el
     * número máximo de iteraciones y no por convergencia.
     * @throws ValoresResueltos Ocurre cuando se lanza el método pero ya se han
     * resuelto todos los grados posibles del polinomio.
     */
    public String[] bairstowN() throws IteracionesAlcanzadas, ValoresResueltos {
        int iterador = 0;
        int accesor = gradoFuncion;
        int accesorResultados = gradoFuncion-1;
        double determinante, deltaR, deltaS;
        String[] resultado = new String[2];
        double[] resFormulaGeneral;
        String parteImaginaria = "";
        double errorAproxR = 1, errorAproxS = 1;
        if (iterador < getIteracionesMaximas()) {
            if (gradoFuncion >= 3) {
                do {
                    iterador++;

                    coeficientesB[accesor] = coeficientesA[accesor];
                    coeficientesB[accesor - 1] = coeficientesA[accesor - 1] + r * coeficientesB[accesor];

                    coeficientesC[accesor] = coeficientesB[accesor];
                    coeficientesC[accesor - 1] = coeficientesB[accesor - 1] + r * coeficientesC[accesor];

                    for (int i = accesor - 2; i >= 0; i--) {
                        coeficientesB[i] = coeficientesA[i] + r * coeficientesB[i + 1] + s * coeficientesB[i + 2];
                        coeficientesC[i] = coeficientesB[i] + r * coeficientesC[i + 1] + s * coeficientesC[i + 2];
                    }

                    determinante = coeficientesC[2] * coeficientesC[2] - coeficientesC[3] * coeficientesC[1];
                    if (determinante != 0) {

                        deltaR = (-coeficientesB[1] * coeficientesC[2] + coeficientesB[0] * coeficientesC[3]) / determinante;
                        deltaS = (-coeficientesB[0] * coeficientesC[2] + coeficientesB[1] * coeficientesC[1]) / determinante;

                        r += deltaR;
                        s += deltaS;

                        errorAproxR = r != 0 ? Math.abs(deltaR / r) * 100 : 0;
                        errorAproxS = s != 0 ? Math.abs(deltaS / s) * 100 : 0;
                    } else {
                        r += 1;
                        s += 1;
                        iterador = 0;
                    }
                } while ((errorAproxR > getErrorTolerancia() || errorAproxS > getErrorTolerancia()) && iterador < getIteracionesMaximas());

                resFormulaGeneral = formulaGeneral(r, s);

                raicesReales[accesorResultados] = resFormulaGeneral[0];
                raicesImaginarias[accesorResultados] = resFormulaGeneral[2];
                raicesReales[accesorResultados - 1] = resFormulaGeneral[1];
                raicesImaginarias[accesorResultados - 1] = resFormulaGeneral[3];

                if (raicesImaginarias[accesorResultados] > 0) {
                    parteImaginaria = "+" + raicesImaginarias[accesorResultados] + "i";
                } else if (raicesImaginarias[accesorResultados] < 0) {
                    parteImaginaria = raicesImaginarias[accesorResultados] + "i";
                }
                resultado[0] = raicesReales[accesorResultados] + parteImaginaria;

                if (raicesImaginarias[accesorResultados - 1] > 0) {
                    parteImaginaria = "+" + raicesImaginarias[accesorResultados - 1] + "i";
                } else if (raicesImaginarias[accesorResultados - 1] < 0) {
                    parteImaginaria = raicesImaginarias[accesorResultados - 1] + "i";
                }
                resultado[1] = raicesReales[accesorResultados - 1] + parteImaginaria;

                for (int i = 0; i < accesor-1; i++) {
                    coeficientesA[i] = coeficientesB[i + 2];
                }
                gradoFuncion -= 2;
                accesor -= 2;
            } else if (gradoFuncion == 2) {

                r = -coeficientesA[1] / coeficientesA[2];
                s = -coeficientesA[0] / coeficientesA[2];

                resFormulaGeneral = formulaGeneral(r, s);
                raicesReales[accesorResultados] = resFormulaGeneral[0];
                raicesImaginarias[accesorResultados] = resFormulaGeneral[2];
                raicesReales[accesorResultados - 1] = resFormulaGeneral[1];
                raicesImaginarias[accesorResultados - 1] = resFormulaGeneral[3];

                if (raicesImaginarias[accesorResultados] > 0) {
                    parteImaginaria = "+" + raicesImaginarias[accesorResultados] + "i";
                } else if (raicesImaginarias[accesorResultados] < 0) {
                    parteImaginaria = raicesImaginarias[accesorResultados] + "i";
                }
                resultado[0] = raicesReales[accesorResultados] + parteImaginaria;

                if (raicesImaginarias[accesorResultados - 1] > 0) {
                    parteImaginaria = "+" + raicesImaginarias[accesorResultados - 1] + "i";
                } else if (raicesImaginarias[accesorResultados - 1] < 0) {
                    parteImaginaria = raicesImaginarias[accesorResultados - 1] + "i";
                }
                resultado[1] = raicesReales[accesorResultados - 1] + parteImaginaria;
                gradoFuncion-=2;
            } else if (gradoFuncion == 1) {

                raicesReales[accesorResultados] = -coeficientesA[0] / coeficientesA[1];
                raicesImaginarias[accesorResultados] = 0;

                if (raicesImaginarias[accesorResultados] > 0) {
                    parteImaginaria = "+" + raicesImaginarias[accesorResultados] + "i";
                } else if (raicesImaginarias[accesorResultados] < 0) {
                    parteImaginaria = raicesImaginarias[accesorResultados] + "i";
                }
                resultado[0] = raicesReales[accesorResultados] + parteImaginaria;

                resultado[1] = "";
                gradoFuncion--;
            } else {
                throw new ValoresResueltos();
            }
        } else {
            throw new IteracionesAlcanzadas();
        }
        return resultado;
    }

    /**
     * Método que aplica la fórmula general adaptada para el algoritmo de
     * Bairstow.
     *
     * @param r Valor inicial r
     * @param s Valor inicial s
     * @return Matriz de datos [r1, r2, i1, i2]
     */
    private double[] formulaGeneral(double r, double s) {
        double discriminante = Math.pow(r, 2) + 4 * s;
        double[] resultados = new double[4];
        // Verifica que el discriminante no sea 0 (no hay raiz de cero)
        if (discriminante > 0) {
            // Extrae la raiz con positivo y negativo
            resultados[0] = (r + Math.sqrt(discriminante)) / 2;
            resultados[1] = (r - Math.sqrt(discriminante)) / 2;
            resultados[2] = 0;
            resultados[3] = 0;
        } else {
            // extrae los factores sin operar
            resultados[0] = r / 2;
            resultados[1] = resultados[0];
            resultados[2] = Math.sqrt(Math.abs(discriminante)) / 2;
            resultados[3] = -resultados[2];
        }
        return resultados;
    }

    public double getR() {
        return r;
    }

    public double getS() {
        return s;
    }

    public int getGradoFuncion() {
        return gradoFuncion;
    }

    public double[] getCoeficientesA() {
        return coeficientesA;
    }

    public double[] getCoeficientesB() {
        return coeficientesB;
    }

    public double[] getCoeficientesC() {
        return coeficientesC;
    }

    public double[] getRaicesReales() {
        return raicesReales;
    }

    public double[] getRaicesImaginarias() {
        return raicesImaginarias;
    }
}
