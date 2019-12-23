/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Diego Gonzalez
 */
public class Conexion {
    /*
     private String driver = "com.mysql.jdbc.Driver";
     private String cadenaConeccion = "jdbc:mysql://acima:3306/acimabasededatos";
     private String usuario = "Gonzalo";
     private String pass = "MiAnCaF31";
     Connection con = null;
     */

    private String driver = "com.mysql.jdbc.Driver";
    private String cadenaConeccion = "jdbc:mysql://localhost:3306/acimabasededatos";
    private String usuario = "root";
    private String pass = "admin";
    Connection con = null;

    public Connection conecta() {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(cadenaConeccion, usuario, pass);
            //JOptionPane.showMessageDialog(null, "DEBUG: Conectado a la base de datos");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar " + e.getMessage());
        }
        return con;
    }

}
