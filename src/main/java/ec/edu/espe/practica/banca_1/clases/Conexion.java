/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.practica.banca_1.clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class Conexion {
    private static java.sql.Connection conexion;
   

    public Connection getConexion() {
        return conexion;
    }

    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public Conexion() {
        conexion = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/cooperativa2", "root", "espe2016");

            if (conexion != null) {
                System.out.println("Conexion Establecida...");
            } else {
                System.out.println("Error en la Conexion...");
            }
        } catch (SQLException | ClassNotFoundException ex) {
          System.out.println("error al conectar" + ex);
        }
    }

    public boolean ejecutarSQL(String sql) {
        try {
            Statement sentencia = conexion.createStatement();
            sentencia.executeUpdate(sql);
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }

    public ResultSet ejecutarSQLSelect(String sql) {
        ResultSet resultado;
        try {
            Statement sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println("ejecutarSQLSelect nulo");
            return null;
        }

        return resultado;
    }
    
}
