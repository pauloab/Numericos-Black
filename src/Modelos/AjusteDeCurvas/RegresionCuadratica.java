/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.AjusteDeCurvas;

import Util.Matematico;

/**
 *
 * @author Paulo Aguilar
 */
public class RegresionCuadratica {
       
    private double[] xCordenadas;
    private double[] yCordenadas;
    private String funcionRegresion;
    private double[] coeficientesResultantes;
    private double desviacionEstandar;
    private double errorEstandar;
    private double coeficienteDeterminacion;
    private int grado;
    
    /**
     * Constructor de la clase
     * @param xCordenadas Vector de datos de entrada en X
     * @param yCordenadas Vector de datos de entrada en Y
     * @param grado Grado del que se desea el polinomio resultante
     */
    public RegresionCuadratica(double[] xCordenadas, double[] yCordenadas, int grado){
        this.xCordenadas = xCordenadas;
        this.yCordenadas = yCordenadas;
        this.funcionRegresion = null;
        this.grado = grado;
        coeficientesResultantes = null;
    }

    public String getFuncionRegresion() {
        return funcionRegresion;
    }

    public int getGrado() {
        return grado;
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

    public double[] getCoeficientesResultantes() {
        return coeficientesResultantes;
    }
    
    /**
     * Genera la ecuación de regresión cuadrática
     */
    public void regresionPolinomial() throws Exception{
        double elementoIX, tIndependienteY;
        int n = xCordenadas.length;
        double yi = 0, promedioY;
        double st = 0,sr = 0;
        double[][] coeficientes = new double[grado+1][grado+1];
        double[] tIndependientes = new double[grado+1];
        for (int i = 0; i < coeficientes.length; i++) {
            for (int j = 0; j < coeficientes.length; j++) {
                elementoIX = 0;
                for (int k = 0; k < xCordenadas.length; k++) {
                    elementoIX += Math.pow(xCordenadas[k],i+j); 
                }
                coeficientes[i][j] = elementoIX;   
            }
        }
        for (int i = 0; i < tIndependientes.length; i++) {
            tIndependienteY = 0;
            for (int j = 0; j < xCordenadas.length; j++) {
                tIndependienteY += Math.pow(xCordenadas[j],i)*yCordenadas[j];
            }
            tIndependientes[i] = tIndependienteY;
        }
        coeficientesResultantes = Matematico.resovlerSistemaEcuaiones(coeficientes, tIndependientes);
        
        funcionRegresion = "";
        for (int i = 0; i < coeficientesResultantes.length; i++) {
            funcionRegresion += "+("+coeficientesResultantes[i]+"*(x^"+i+"))";
        }
        for (int i = 0; i < n; i++) {
            yi += yCordenadas[i];
        }
        promedioY = yi/n;
        for (int i = 0; i < n; i++) {
            st += Math.pow(yCordenadas[i]-promedioY,2);
            sr += Math.pow(yCordenadas[i]-pronosticar(xCordenadas[i]),2);
        }
        desviacionEstandar = Math.sqrt(st/(n-1));
        errorEstandar = Math.sqrt(sr/(n-1));
        coeficienteDeterminacion = (st-sr)/st;
    }
    
    public double pronosticar(double x) throws Exception{
        return Matematico.evaluarFuncion(funcionRegresion,x);
    }
}
