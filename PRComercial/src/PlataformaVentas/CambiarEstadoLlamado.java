/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PlataformaVentas;

import clases.Conexion;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Diego González Román
 */
public class CambiarEstadoLlamado extends javax.swing.JFrame {

    Conexion con = new Conexion();
    Connection cn = con.conecta();

    public CambiarEstadoLlamado() {
        initComponents();
        jPanel1.setBackground(new Color(0, 0, 0, 30));

    }

    public void LlenarTablas() {
        try {
            System.out.println("id previo a query" + lblIDUsuario.getText());
            int id = Integer.parseInt(lblIDUsuario.getText());

            System.out.println("ID EN QUERY: " + id);

            String query1 = "Select co.IDContacto, co.correo AS 'Correo',co.Nombre, co.Numero,\n"
                    + "org.NombreOrganizacion,r.nombreRegion AS 'Region',com.NombreComuna,\n"
                    + "co.EstadoRespuesta as 'Estado de Respuesta' FROM contactos co join region r on co.idRegion = r.idRegion\n"
                    + "left join region reg on reg.idRegion = co.IDRegion left join comuna com on com.IDRegion = reg.IDRegion\n"
                    + "left join organizacion org on org.IDOrganizacion = co.IDOrganizacion WHERE CO.IDUSUARIO = ? "
                    + "AND co.EstadoRespuesta = 'Llamado' \n"
                    + "Group by co.IDContacto;";
            PreparedStatement pst1 = cn.prepareStatement(query1);
            pst1.setInt(1, id);
            ResultSet rs1 = pst1.executeQuery();
            tblLlamados.setModel(DbUtils.resultSetToTableModel(rs1));

            String query2 = "Select co.IDContacto,co.correo AS 'Correo',co.Nombre,co.Numero,org.NombreOrganizacion,r.nombreRegion AS 'Region',\n"
                    + "com.NombreComuna,co.EstadoRespuesta as 'Estado de Respuesta'\n"
                    + "FROM contactos co join region r on co.idRegion = r.idRegion\n"
                    + "left join region reg on reg.idRegion = co.IDRegion\n"
                    + "left join comuna com on com.IDRegion = reg.IDRegion\n"
                    + "left join organizacion org on org.IDOrganizacion = co.IDOrganizacion\n"
                    + "WHERE CO.IDUSUARIO = ? AND co.EstadoRespuesta = 'No Llamado' OR co.EstadoRespuesta='Sin Respuesta'\n"
                    + "OR co.EstadoRespuesta= 'Llamado y sin respuesta'\n"
                    + "Group by co.IDContacto;";
            PreparedStatement pst2;
            pst2 = cn.prepareStatement(query2);
            pst2.setInt(1, id);
            ResultSet rs2 = pst2.executeQuery();
            tblNoLlamadosSinRespuesta.setModel(DbUtils.resultSetToTableModel(rs2));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLlamados = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblNoLlamadosSinRespuesta = new javax.swing.JTable();
        cmbEstado = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JButton();
        b_Titulo = new javax.swing.JLabel();
        lblIDUsuario = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PlataformaVentas/Imagenes/acima-logo-200p.png"))); // NOI18N

        jPanel4.setBackground(new java.awt.Color(219, 219, 219));

        jButton1.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jButton1.setText("Volver");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tblLlamados = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int celIndex){
                return false;
            }
        };
        tblLlamados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblLlamados);

        jTabbedPane2.addTab("Llamados", jScrollPane1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Contactos Llamados", jPanel2);

        tblNoLlamadosSinRespuesta  = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int celIndex){
                return false;
            }
        };
        tblNoLlamadosSinRespuesta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tblNoLlamadosSinRespuesta);

        jTabbedPane3.addTab("No Llamados - Llamado y Sin Respuesta - Sin Respuesta", jScrollPane5);

        cmbEstado.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione Estado", "Llamado", "Llamado y sin respuesta", "Sin Respuesta", "No Llamado" }));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel3.setText("Cambiar estado de Contacto:");

        btnConfirmar.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane3)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConfirmar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Contactos No Llamados", jPanel3);

        jScrollPane2.setViewportView(jTabbedPane1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 1114, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        b_Titulo.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        b_Titulo.setText("Clientes Llamados");

        lblIDUsuario.setText("0");
        lblIDUsuario.setVisible(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblIDUsuario)
                        .addGap(330, 330, 330))))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(462, 462, 462)
                    .addComponent(b_Titulo)
                    .addContainerGap(462, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblIDUsuario)
                        .addGap(14, 14, 14)))
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(24, 24, 24)
                    .addComponent(b_Titulo)
                    .addContainerGap(613, Short.MAX_VALUE)))
        );

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PlataformaVentas/Imagenes/BackgroundNew.png"))); // NOI18N
        jLabel2.setText("jLabel2");

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1280, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 572, Short.MAX_VALUE))
        );
        jLayeredPane1.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        String respuesta = JOptionPane.showInputDialog(null, "Escriba Su codigo de autorización.", "Permisos de acción", JOptionPane.INFORMATION_MESSAGE);
        if (Integer.parseInt(respuesta) == Integer.parseInt(lblIDUsuario.getText())) {
            //Label de ID COTIZACION
            TableModel modelo = tblNoLlamadosSinRespuesta.getModel();
            int selectedRows = tblNoLlamadosSinRespuesta.getSelectedRow();
            int idContacto = Integer.parseInt(tblNoLlamadosSinRespuesta.getValueAt(selectedRows, 0).toString());
            // create the java mysql update preparedstatement
            if (cmbEstado.getSelectedIndex() == 1) {
                try {
                    String query = "update contactos set EstadoRespuesta = 'Llamado' where idcontacto = ?";
                    PreparedStatement preparedStmt = cn.prepareStatement(query);
                    preparedStmt.setInt(1, idContacto);
                    // execute the java preparedstatement
                    preparedStmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Contacto Actualizado");

                } catch (SQLException ex) {
                    Logger.getLogger(CambiarEstadoLlamado.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else if (cmbEstado.getSelectedIndex() == 2) {
                try {
                    String query = "update contactos set EstadoRespuesta = 'Llamado y sin respuesta' where idcontacto = ?";
                    PreparedStatement preparedStmt = cn.prepareStatement(query);
                    preparedStmt.setInt(1, idContacto);
                    // execute the java preparedstatement
                    preparedStmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Contacto Actualizado");

                } catch (SQLException ex) {
                    Logger.getLogger(CambiarEstadoLlamado.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else if (cmbEstado.getSelectedIndex() == 3) {
                try {
                    String query = "update contactos set EstadoRespuesta = 'Sin Respuesta' where idcontacto = ?";
                    PreparedStatement preparedStmt = cn.prepareStatement(query);
                    preparedStmt.setInt(1, idContacto);
                    // execute the java preparedstatement
                    preparedStmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Contacto Actualizado");

                } catch (SQLException ex) {
                    Logger.getLogger(CambiarEstadoLlamado.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else if (cmbEstado.getSelectedIndex() == 4) {
                try {
                    String query = "update contactos set EstadoRespuesta = 'No Llamado' where idcontacto = ?";
                    PreparedStatement preparedStmt = cn.prepareStatement(query);
                    preparedStmt.setInt(1, idContacto);
                    // execute the java preparedstatement
                    preparedStmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Contacto Actualizado");

                } catch (SQLException ex) {
                    Logger.getLogger(CambiarEstadoLlamado.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione Opción Válida");
            }

            LlenarTablas();
        } else {
            JOptionPane.showMessageDialog(null, "El codigo de autorización no es válido");
        }


    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CambiarEstadoLlamado.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CambiarEstadoLlamado.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CambiarEstadoLlamado.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CambiarEstadoLlamado.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CambiarEstadoLlamado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel b_Titulo;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JComboBox cmbEstado;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    public javax.swing.JLabel lblIDUsuario;
    public javax.swing.JTable tblLlamados;
    public javax.swing.JTable tblNoLlamadosSinRespuesta;
    // End of variables declaration//GEN-END:variables
}