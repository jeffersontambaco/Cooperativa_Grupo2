/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.practica.banca_1.clases;

//import Interfaces.FrmMenu;

import ec.edu.espe.practica.banca_1.interfaces.JFogin;



/**
 *
 * @author Jessii
 */
public class Banca {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFogin jfLogin =new JFogin();
        jfLogin.setVisible(true);
        jfLogin.setLocationRelativeTo(null);
    }    
}
