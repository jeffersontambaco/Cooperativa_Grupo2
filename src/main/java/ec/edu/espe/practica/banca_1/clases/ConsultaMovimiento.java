/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.practica.banca_1.clases;

import com.toedter.calendar.JDateChooser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sofia Gomez
 */
public class ConsultaMovimiento {

    private final Conexion conn;
    private ResultSet resultado;
    private Date fechaIni;
    private Date fechaFin;

    public ConsultaMovimiento() {
        conn = new Conexion();
    }

    public void Buscar(JDateChooser fechaIni, JDateChooser fechaFin, JComboBox jmbCuenta, JTable Mostrar) {
        String cod_cuenta = jmbCuenta.getSelectedItem().toString();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Código Movimiento");
        model.addColumn("Tipo");
        model.addColumn("Fecha");
        model.addColumn("Monto");
        model.addColumn("Saldo");
        Mostrar.setModel(model);
        String[] Datos = new String[5];
        this.fechaIni = fechaIni.getDate();
        this.fechaFin = fechaFin.getDate();
        if (this.fechaIni.compareTo(this.fechaFin) == 0) {
            JOptionPane.showMessageDialog(null, "Las fechas no deben ser iguales");
        } else if (this.fechaIni.compareTo(this.fechaFin) > 0) {
            JOptionPane.showMessageDialog(null, "La fecha de fin debe ser despues de la de inicio");
        } else if (this.fechaIni.compareTo(this.fechaFin) < 0) {
            SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
            String fecha1 = parseador.format(this.fechaIni);
            String fecha2 = parseador.format(this.fechaFin);
            try {
                resultado = conn.ejecutarSQLSelect("select codigo_movimiento,tipo,fecha,monto,saldo from movimiento where substring(fecha,1,10) in ('" + fecha1 + "','" + fecha2 + "') and codigo_cuenta like '%" + cod_cuenta + "%'");
                while (resultado.next()) {
                    Datos[0] = resultado.getString(1);
                    Datos[1] = resultado.getString(2);
                    Datos[2] = resultado.getString(3);
                    Datos[3] = resultado.getString(4);
                    Datos[4] = resultado.getString(5);
                    model.addRow(Datos);
                }
                Mostrar.setModel(model);
            } catch (SQLException ex) {

            }
        }
    }

    public void buscarCuenta(JComboBox cuentas, String ced) {
        ArrayList<String> Cuenta = new ArrayList<>();
        String cod;
        try {
            resultado = conn.ejecutarSQLSelect("Select CODIGO_CUENTA from CUENTA where CEDULA like '%" + ced + "%'");
            while (resultado.next()) {
                cod = resultado.getString(1);
                Cuenta.add(cod);
                cod = " ";
            }
        } catch (SQLException ex) {

        }
        for (int j = 0; j < Cuenta.size(); j++) {
            cuentas.addItem(Cuenta.get(j));
        }
    }
}
