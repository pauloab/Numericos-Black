/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.Excepciones;

/**
 *
 * @author Javier Matamoros
 */
public class ValoresResueltos  extends Exception{
    public ValoresResueltos(){
        super("Se han resuelto los valores a su mínima expresión.");
    }
}
