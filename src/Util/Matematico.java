package Util;

import Plotter.Models.CoordinatePair;
import org.lsmp.djep.djep.DJep;
import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;

public class Matematico {

  public static String derivar(String funcion) throws ParseException {
    DJep djep;
    Node nodoFuncion;
    Node nodoDerivada;

      djep = new DJep();
      // agrega funciones estandares cos(x), sin(x)
      djep.addStandardFunctions();

      // agrega constantes estandares, pi, e, etc
      djep.addStandardConstants();

      // por si existe algun numero complejo
      djep.addComplex();

      // permite variables no declarables
      djep.setAllowUndeclared(true);

      // permite asignaciones
      djep.setAllowAssignment(true);

      // regla de multiplicacion o para sustraccion y sumas
      djep.setImplicitMul(true);

      // regla de multiplicacion o para sustraccion y sumas
      djep.addStandardDiffRules();

      // coloca el nodo con una funcion preestablecida
      nodoFuncion = djep.parse(funcion);

      // deriva la funcion con respecto a x
      Node diff = djep.differentiate(nodoFuncion, "x");

      // Simplificamos la funcion con respecto a x
      nodoDerivada = djep.simplify(diff);

      // Convertimos el valor simplificado en un String
      funcion = djep.toString(nodoDerivada);

    return funcion;
  }

  public static double evaluarFuncion(String funcion, double x)
    throws Exception {
    JEP jep;
    jep = new JEP();

    jep.addStandardFunctions();
    jep.addStandardConstants();
    jep.addVariable("x", x);
    jep.parseExpression(funcion);
    if (jep.hasError()) {
      throw new Exception("No se pudo evaluar la funcion");
    }
    return jep.getValue();
  }

  /**
   * Calcula el factorial de un número dado
   * @param n Número a calcular su factorial
   * @return factorial de n
   */
  public static long factorial(int n) {
    long result = 1;
    for (int i = n; i > 1; i--) {
      result = i * result;
    }
    return result;
  }

  public static double errorAprox(double vactual, double vanterior) {
    double res = 0;
    res = Math.abs((vactual - vanterior) / vactual) * 100;
    return res;
  }

  public static double errorTolerancia(int cifras) {
    return (0.5 * Math.pow(10, 2 - cifras)) / 100;
  }

  public static boolean validarExpresion(String exrpesion) {
    JEP jep = new JEP();

    jep.addStandardFunctions();
    jep.addStandardConstants();
    jep.addVariable("x", 0);
    jep.parseExpression(exrpesion);
    return !jep.hasError();
  }

  /**
   * Crea un vector de numeros con los valores en y de una función, basado
   * en un arreglo de x.
   * @param funcion Funcion de entrada a evaluar
   * @param x Vector de valores en x sobre los que se va a evaluar.
   * @return Arreglo de valores de y del input x
   * @throws Exception Ocurre cuando no se puede evaluar la funci[on en un determinado punto
   */
  public static double[] graficarFuncion(String funcion, double[] x)
    throws Exception {
    double[] results = new double[x.length];
    for (int i = 0; i < x.length; i++) {
      results[i] = evaluarFuncion(funcion, x[i]);
    }
    return results;
  }

  /**
   * Evalúa una función basado en su mínimo y máximo en fracciones de 1500
   * @param funcion Funcion a evaluar
   * @param xMin Valor minimo del dominio
   * @param xMax Valor máximo del dominio
   * @return Vector de pares de coordenadas
   */
  public static CoordinatePair[] evaluarFuncion(
    String funcion,
    double xMin,
    double xMax
  )
    throws Exception {
    int intervalo = 1500;
    double paso = (xMax - xMin) / intervalo;
    CoordinatePair[] paresCordenadas = new CoordinatePair[intervalo + 1];
    int iterador = 0;
    for (double i = xMin; i < xMax; i += paso) {
      paresCordenadas[iterador] =
        new CoordinatePair(i, evaluarFuncion(funcion, i));
      iterador++;
    }
    return paresCordenadas;
  }

  /**
   * Evalúa una función de regresión lineal a travéz de sus coeficientes
   * en un rango definido a intervalos de una milésima.
   * @param a0 Intersecion de la función de regresión
   * @param a1 Pendiente de la función de regresión
   * @param xMin Cota mínima del dominio
   * @param xMax Cota máxima del dominio
   * @return Vector de coordenadas que representan la recta
   * @throws Exception
   */
  public static CoordinatePair[] evaluarRegresionLineal(
    double a0,
    double a1,
    double xMin,
    double xMax
  )
    throws Exception {
    int intervalo = 1500;
    double paso = (xMax - xMin) / intervalo;
    CoordinatePair[] paresCordenadas = new CoordinatePair[intervalo + 1];
    int iterador = 0;
    for (double i = xMin; i < xMax; i += paso) {
      paresCordenadas[iterador] = new CoordinatePair(i, a1 * i + a0);
      iterador++;
    }
    return paresCordenadas;
  }
  public static double[] resovlerSistemaEcuaiones(double[][] coheficientes, double[] tIndependientes) throws Exception {
        double[] resultado = new double[tIndependientes.length];
        double[][] matrizInversa = matrizInversa(coheficientes);
        double elementoI;
        for (int i = 0; i < tIndependientes.length; i++) {
            System.out.print (tIndependientes[i]+ " ");
        }
        for (int i = 0; i < coheficientes.length; i++) {
            elementoI=0;
            for (int j = 0; j < tIndependientes.length; j++) {
                elementoI += matrizInversa[i][j]*tIndependientes[j];
            }
            resultado[i] = elementoI;
        }
        return resultado;
    }
    
    private static double[][] matrizInversa(double[][] matriz) throws Exception{
        double determinante = determinanteN(matriz);
        double subDeterminante;
        double[] filaInvertida = new double[matriz.length];
        double[][] matrizInvertida = new double[matriz.length][matriz.length];
        if (determinante==0) {
            throw new Exception("Error de cálculo, no existe inversa");
        }
        int maxLen = matriz.length-1;
        double[] subVector = new double[maxLen];
        double[][] subMatriz = new double[maxLen][maxLen];
        double[][][] matrizDeMatrices = new double[(maxLen+1)*(maxLen+1)][maxLen][maxLen];
        int i = 0, j = 0, k =0, f=0;
        //bucle de bloqueo en X
        for (int bloqX = 0; bloqX < matriz.length; bloqX++) {
            // bucle de bloqueo en Y
            for (int bloqY = 0; bloqY < matriz[bloqX].length; bloqY++) {
                // bucle de acceso en x
                for (int accX = 0; accX < matriz.length; accX++) {
                    if (accX != bloqX) {
                        // bucle de acceso en Y
                        for (int accY = 0; accY < matriz[accX].length; accY++) {
                            if (accY != bloqY) {
                                subVector[i++] = matriz[accX][accY];
                                if (i==maxLen) {
                                    subMatriz[j++] = subVector; 
                                    i = 0;
                                    subVector = new double[maxLen];
                                    if (j == maxLen) {
                                        j = 0;
                                        matrizDeMatrices[k++] = subMatriz;
                                        subMatriz = new double[maxLen][maxLen];   
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        i=1;
        f=1;
        maxLen++;
        //Operando con adjuntas
        for (int l = 0; l < matrizDeMatrices.length; l++) {
            subMatriz = matrizDeMatrices[l];
            subDeterminante = determinanteN(subMatriz);
            if ((i+f)%2==0)  {
                filaInvertida[i-1] = subDeterminante/determinante;
            }else{
                filaInvertida[i-1] = subDeterminante*-1/determinante;
            }
            if (i==maxLen) {
                f+=1;
                i=0;
                matrizInvertida[f-2] = filaInvertida;
                filaInvertida =  new double[matriz.length]; 
            }
            i+=1;
        }
        
        matrizInvertida = transponer(matrizInvertida);
        

        
        return matrizInvertida;
    }

    private static double sarows(double[][] matriz) {
        double diagJ, diagI;
        double diagP=0, diagS=0;
        int j = 0, l = 0;
        double[][] matrixAux = new double[5][3];
        for (int i = 0; i < matriz.length; i++) {
            for (int k = 0; k < matriz.length; k++) {
                matrixAux[i][k] = matriz[i][k];
                if (i<2) {
                    matrixAux[i+3][k] = matriz[i][k];
                }
            }
        }
        for (int k = matrixAux[0].length-1; k >= 0 ; k--) {
            j = l++;
            diagI = 1;
            diagJ = 1;
            for (int i = 0; i < matrixAux[k].length; i++) {
                diagI *= matrixAux[j][i];
                diagJ *= matrixAux[j][3-i-1];
                j++;
            }
            diagP+=diagI;
            diagS+=diagJ;
        }   
        return diagP-diagS;
    }

    private static double determinanteN(double[][] matriz){
        double determinante = 0;
        int maxLen = matriz.length-1;
        double[] subVector = new double[maxLen];
        double[][] subMatriz = new double[maxLen][maxLen];
        double[][][] matrizDeMatrices = new double[(maxLen+1)*(maxLen+1)][maxLen][maxLen];
        int i = 0, j = 0, k =0;
        double cofactor;
        switch (matriz.length) {
            case 3:
                //Resolución por Sarrows
                return sarows(matriz);
            case 2:
                // multiplicación simple
                return matriz[0][0]*matriz[1][1]
                        - matriz[0][1]*matriz[1][0];
            case 1:
                return matriz[0][0];
        }
        //bucle de bloqueo en X
        for (int bloqX = 0; bloqX < matriz.length; bloqX++) {
            // bucle de bloqueo en Y
            for (int bloqY = 0; bloqY < matriz[bloqX].length; bloqY++) {
                // bucle de acceso en x
                for (int accX = 0; accX < matriz.length; accX++) {
                    if (accX != bloqX) {
                        // bucle de acceso en Y
                        for (int accY = 0; accY < matriz[accX].length; accY++) {
                            if (accY != bloqY) {
                                subVector[i++] = matriz[accX][accY];
                                if (i==maxLen) {
                                    subMatriz[j++] = subVector; 
                                    i = 0;
                                    subVector = new double[maxLen];
                                    if (j == maxLen) {
                                        j = 0;
                                        matrizDeMatrices[k++] = subMatriz;
                                        subMatriz = new double[maxLen][maxLen];   
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        j = 0;
        i = 0;
        // Operacion con co-factores
        for (int l = 0; l < matrizDeMatrices.length; l++) {
            subMatriz = matrizDeMatrices[l];
            
            if (subMatriz.length == 3) {
                cofactor = sarows(subMatriz);
                // Verifica si el cofactor es par
                if ((j+1)%2==0) {
                    determinante += matriz[i][j]*cofactor*-1;
                }else{
                    determinante += matriz[i][j]*cofactor;
                }
                if (j==subMatriz.length) {
                    i++;
                    break;
                }
                j++;
            } else{
                determinante = determinanteN(subMatriz);
                break;
            }
        }
        return determinante;
    }

    private static double[][] transponer(double[][] matriz){
        double[][] resultado = new double[matriz.length][matriz[0].length];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                resultado[i][j] = matriz[j][i];
            }
        }
        return resultado;
    }
  
}
