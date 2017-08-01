
package ec.edu.espe.practica.banca_1.clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Sofia Gomez
 */
public class Movimientos {

    final Conexion conn;
    ResultSet resultado;
    private int codigo;
    private String cod_cuenta;
    private String tipo;
    private String fecha;
    private float monto;
    private float saldo;
    java.util.Date dt = new java.util.Date();
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String currentTime = sdf.format(dt);

    public Movimientos(String cod_cuenta, String tipo, float monto, float saldo) {
        this.cod_cuenta = cod_cuenta;
        this.tipo = tipo;
        this.monto = monto;
        this.saldo = saldo;
        conn = new Conexion();
    }

    public Movimientos() {
        conn = new Conexion();
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public int generarCodigo(JTextField codi) {
        int codigo = 0;
        String cod = "0";
        try {
            resultado = conn.ejecutarSQLSelect("Select max(CODIGO_MOVIMIENTO) from movimiento");
            while (resultado.next()) {
                cod = resultado.getString(1);
            }
            codigo = Integer.parseInt(cod);
            codigo++;
            codi.setText(String.valueOf(codigo));
        } catch (SQLException ex) {
        }
        return codigo;
    }

    public void registrarMov(JTextField saldo) {
        float nuevoS = validarMonto(monto, tipo);
        conn.ejecutarSQL("INSERT INTO movimiento(CODIGO_MOVIMIENTO,CODIGO_CUENTA,TIPO,FECHA,MONTO,SALDO)"
                + "VALUES ('" + codigo + "','" + cod_cuenta + "','" + tipo + "','" + currentTime + "','" + monto + "','" + nuevoS + "')");
        conn.ejecutarSQL("UPDATE CUENTA SET SALDO ='" + nuevoS + "' WHERE CODIGO_CUENTA = '" + cod_cuenta + "'");
        JOptionPane.showMessageDialog(null, "Registro realizado con éxito \n Su nuevo Saldoes: " + nuevoS);
        saldo.setText(String.valueOf(nuevoS));
    }

    public float obtenerSaldo() {
        float aux = 0;
        String cod = "0";
        try {
            resultado = conn.ejecutarSQLSelect("Select SALDO from CUENTA where CODIGO_CUENTA like '%" + cod_cuenta + "%'");
            while (resultado.next()) {
                cod = resultado.getString(1);
            }
            aux = Float.parseFloat(cod);
        } catch (SQLException ex) {

        }
        return aux;
    }

    public float validarMonto(float aux, String tipo) {
        float saldoOriginal = obtenerSaldo();
        float saldoNuevo = 0;
        if (tipo.equals("RET")) {
            if (aux > 0 && aux <= saldoOriginal) {
                saldoNuevo = saldoOriginal - aux;
            }
        } else {
            if (tipo.equals("DEP")) {
                saldoNuevo = saldoOriginal + aux;
            }
        }
        return saldoNuevo;
    }

    public void buscarCuenta(JComboBox cuentas, String ced) {
        ArrayList<String> Cuenta = new ArrayList<String>();
        String cod = "0";
        int i = 0;
        try {
            resultado = conn.ejecutarSQLSelect("Select CODIGO_CUENTA from CUENTA where CEDULA like '%" + ced + "%'");
            while (resultado.next()) {
                cod = resultado.getString(1);
                Cuenta.add(cod);
                cod = "";
            }
        } catch (SQLException ex) {

        }
        for (int j = 0; j < Cuenta.size(); j++) {
            cuentas.addItem(Cuenta.get(j));
        }
    }
}
