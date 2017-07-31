package ec.edu.espe.practica.banca_1.clases;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

/**
 *
 * @author CRIS
 */
public class Cuenta {

    //Atributos
    private final Conexion conn;
    private String Codigo;
    private String Tipo;
    private String Estado;
    private ResultSet resultado;
    private String Cedula;
    private float Saldo;

    public Cuenta() {
        conn = new Conexion();
    }

    public String generarCodigo(String c) {
        Random random = new Random();
        String codigo = "";
        try {
            resultado = conn.ejecutarSQLSelect("Select CEDULA from cliente where cedula like '" + c + "'");
            while (resultado.next()) {
                codigo = resultado.getString(1);
            }
            Codigo = codigo.substring(6, 10) + new BigInteger(20, random).toString(32);
            conn.cerrarConexion();
        } catch (SQLException ex) {
        }
        return Codigo;
    }

    public int BuscarCliente(String ced) throws SQLException {
        String c = "";
        int band = 0;
        resultado = conn.ejecutarSQLSelect("select cedula from cliente where cedula like '" + ced + "'");
        while (resultado.next()) {
            c = resultado.getString(1);
            if (c != null) {
                JOptionPane.showMessageDialog(null, "Cliente registrado");
                band = 1;
            } else {
                JOptionPane.showMessageDialog(null, c);
                band = 2;
            }
        }
        return band;
    }

    public String getTipo(JComboBox a) {
        if (a.getSelectedIndex() == 0) {
            Tipo = "Ahorros";
        } else {
            if (a.getSelectedIndex() == 1) {
                Tipo = "Corriente";
            }
        }
        return Tipo;
    }

    public String getEstado(JRadioButton a, JRadioButton i) {
        if (a.isSelected()) {
            Estado = "Activa";

        } else if (i.isSelected()) {
            Estado = "Inactiva";
        }
        return Estado;
    }

    public int getSaldo(int s) {
        int Saldo = 0;
        Saldo += s;
        return Saldo;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    public void setCedula(String Cedula) {
        this.Cedula = Cedula;
    }

    public String getCedula() {
        return Cedula;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public float getSaldo() {
        return Saldo;
    }

    public void setSaldo(float Saldo) {
        this.Saldo = Saldo;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public void GuardarCuenta() {
        conn.ejecutarSQL("INSERT INTO cuenta(CODIGO_CUENTA,CEDULA,ESTADO,SALDO,TIPO)"
                + "VALUES ('" + Codigo + "','" + Cedula + "','" + Estado + "'," + Saldo + ",'" + Tipo + "')");
        JOptionPane.showMessageDialog(null, "registrado");
    }

    public void GuardarCuenta2(String codigo, String cedula, String estado, float saldo, String tipo) {
        conn.ejecutarSQL("INSERT INTO cuenta(CODIGO_CUENTA,CEDULA,ESTADO,SALDO,TIPO)"
                + "VALUES ('" + codigo + "','" + cedula + "','" + estado + "'," + saldo + ",'" + tipo + "')");
        JOptionPane.showMessageDialog(null, codigo);
    }

    public void ConsultaCuenta() {
        resultado = conn.ejecutarSQLSelect("Select cedula from cliente");
    }
}
