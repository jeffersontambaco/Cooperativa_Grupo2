/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.practica.banca_1.clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juans
 */
public class LoginC {

    private final Conexion conn;
    private ResultSet resultado;

    public LoginC() {
        conn = new Conexion();
    }

    public boolean verificarUsuario(String Usuario) {
        try {
            String vUsuario = " ";
            resultado = conn.ejecutarSQLSelect("select U_NOMBRE ,U_CLAVE from Usuario where U_NOMBRE like '" + Usuario + "'");
            while (resultado.next()) {
                vUsuario = resultado.getString(1);
            }
            return Usuario.equals(vUsuario);
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean VerificarContraseña(String Usuario, char[] Contraseña) {
        try {
            String vContraseña = " ";
            resultado = conn.ejecutarSQLSelect("select U_NOMBRE ,U_CLAVE from Usuario where U_NOMBRE like '" + Usuario + "'");
            while (resultado.next()) {
                vContraseña = resultado.getString(2);
            }
            return Arrays.equals(vContraseña.toCharArray(), Contraseña);
        } catch (SQLException ex) {
            Logger.getLogger(LoginC.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void cerrarConexion() throws SQLException {
        conn.cerrarConexion();
    }
}
