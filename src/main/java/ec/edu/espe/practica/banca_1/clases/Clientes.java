/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.practica.banca_1.clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author juans
 */
public class Clientes {
    private final Conexion conn;
    private ResultSet resultado;
    //Atributos para nuevo cliente
    private String Nombre;
    private String Cedula;
    private int Codigo;
    private int control;
    String[] Datos=new String[2];

    public Clientes() {
        conn = new Conexion();
        control = 0;
    }
    
    public void buscarClieentes(String Cedula, JTable Tabla){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Cédula");
        model.addColumn("Nombre");
        Tabla.setModel(model);
        try {
            resultado = conn.ejecutarSQLSelect("select * from Cliente where CEDULA like '%"+Cedula+"%'");
            if(resultado != null){
                while (resultado.next()) {
                    Datos[0] = resultado.getString(1);
                    Datos[1] = resultado.getString(2);
                    model.addRow(Datos);
                    control = 99;
                }
                Tabla.setModel(model);
            }else{
                JOptionPane.showMessageDialog(null, "Cliente no registrado");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void generarCodigo(){
        String cod = "0";       
        try {
            resultado = conn.ejecutarSQLSelect("Select max(U_CODIGO) from usuario");
            while (resultado.next()) {
                cod = resultado.getString(1);
            }
            Codigo = Integer.parseInt(cod);
            Codigo++;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public void setCedula(String Cedula) {
        this.Cedula = Cedula;
    }
       
    public void nuevoCliente(){
        if(control != 99){
            conn.ejecutarSQL("INSERT INTO cliente(CEDULA,NOMBRE)" +
                "VALUES ('"+Cedula+"','"+Nombre+"')"); 
        }else{
            JOptionPane.showMessageDialog(null, "Cliente ya registrado con ese número de cédula");
        }
    }
    
    public void actualizarCliente(){        
        if(control == 99){
            if(Datos[1].equals(Nombre)){
                JOptionPane.showMessageDialog(null, "No a modificado la información");
            }else{
                conn.ejecutarSQL("UPDATE cliente set NOMBRE='"+Nombre+"' where CEDULA='"+Cedula+"'"); 
            }
        }else{
            JOptionPane.showMessageDialog(null, "Cliente no registrado");
        }
    }
    
    public void borrarCliente(){
        JOptionPane.showMessageDialog(null, "Cedula: "+Cedula);
        resultado = conn.ejecutarSQLSelect("select * from Cliente where CEDULA like '%"+Cedula+"%'");
        if(resultado != null){
            conn.ejecutarSQL("DELETE FROM cliente WHERE CEDULA='"+Cedula+"'");
        }else{
            JOptionPane.showMessageDialog(null, "Cliente no registrado");
        }
    }
    
    public boolean validarCedula(String cedula){
        boolean cedulaCorrecta = false; 
        try {
            if (cedula.length() == 10) // ConstantesApp.LongitudCedula
            {
                int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
                if (tercerDigito < 6) {
                    // Coeficientes de validación cédula
                    // El decimo digito se lo considera dígito verificador
                    int[] coefValCedula = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
                    int verificador = Integer.parseInt(cedula.substring(9,10));
                    int suma = 0;
                    int digito = 0;
                    for (int i = 0; i < (cedula.length() - 1); i++) {
                        digito = Integer.parseInt(cedula.substring(i, i + 1))* coefValCedula[i];
                        suma += ((digito % 10) + (digito / 10));
                    }
                    if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                        cedulaCorrecta = true;
                    }
                    else if ((10 - (suma % 10)) == verificador) {
                        cedulaCorrecta = true;
                    } else {
                        cedulaCorrecta = false;
                    }
                } else {
                    cedulaCorrecta = false;
                }
            } else {//primero
                cedulaCorrecta = false;
            }
        } catch (NumberFormatException nfe) {
            cedulaCorrecta = false;
        } catch (Exception err) {
            JOptionPane.showMessageDialog(null,"Una excepcion ocurrio en el proceso de validadcion" + err);
        cedulaCorrecta = false;
        }
        if (!cedulaCorrecta) {
            JOptionPane.showMessageDialog(null,"La Cédula ingresada es Incorrecta");        
        }
        return cedulaCorrecta;
    }
}
