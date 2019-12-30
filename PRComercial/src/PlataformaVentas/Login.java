/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PlataformaVentas;

import clases.Conexion;
import clases.Usuario;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Diego Alfaro Fierro, Diego González Romàn
 */
public class Login extends javax.swing.JFrame {

    Conexion con = new Conexion();
    Connection cn = con.conecta();

    /**
     * Creates new form Login
     */
    public Login() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();

    }

    public void Login() {
        try {
            if (txtCorreo1.getText() != null && String.valueOf(txtPass1.getPassword()) != null) {
                String user, pass, nombre, credencial;
                int idUsuario = 0, codigo = 0;
                user = txtCorreo1.getText();
                pass = String.valueOf(txtPass1.getPassword());
                nombre = "";
                String query = "Select * from usuario Where CorreoUsuario='" + txtCorreo1.getText() + "' and pass='" + String.valueOf(txtPass1.getPassword()) + "'";
                PreparedStatement pst = cn.prepareStatement(query);
                ResultSet rs = pst.executeQuery();
                String query2 = "select IDUsuario,NombreUsuario,Credencial,codigo_autorizacion from usuario where CorreoUsuario=?";
                PreparedStatement pst2 = cn.prepareStatement(query2);
                pst2.setString(1, user);
                ResultSet rs2 = pst2.executeQuery();

                if (rs.next()) {
                    //JOptionPane.showMessageDialog(null, "DEBUG: Ha iniciado Sesión");
                    //Ingresar al menu principal

                    //Cerrar ventana de Login
                    if (rs2.next()) {
                        idUsuario = rs2.getInt("IDUsuario");
                        nombre = rs2.getString("NombreUsuario");
                        credencial = rs2.getString("Credencial");
                        codigo = rs2.getInt("codigo_autorizacion");

                        /*if (credencial.equals("Venezuela")) {
                         Menu menu = new Menu();
                         menu.setVisible(true);
                         this.dispose();
                         menu.lblIDUsuario.setText(Integer.toString(idUsuario));
                         menu.lblUsuario.setText(nombre);
                         Usuario usr = new Usuario();
                         usr.setCorreo(nombre);
                         } else if (credencial.equals("Administrador")) {
                         int resp = JOptionPane.showConfirmDialog(null, "¿Esta seguro de entrar a la plataforma como administrador?\n"
                         +"Se mostraran los registros completos de la Plataforma Ventas Venezuela", "Alerta!", JOptionPane.YES_NO_OPTION);
                         if (resp==0) {
                         Menu menu = new Menu();
                         menu.setVisible(true);
                         this.dispose();
                         menu.lblIDUsuario.setText(Integer.toString(idUsuario));
                         menu.lblUsuario.setText(nombre);
                         Usuario usr = new Usuario();
                         usr.setCorreo(nombre);
                         }

                         } else {
                         JOptionPane.showMessageDialog(this, "Usuario no habilitado para entrar a la Plataforma.", "Error el inciar sesión.", JOptionPane.ERROR_MESSAGE);
                         }*/
                        Menu menu = new Menu();
                        menu.setVisible(true);
                        this.dispose();
                        String id = Integer.toString(idUsuario);
                        menu.idMenu.setText(id);
                        System.out.println("id en login: " + id);
                        menu.menuUsuario.setText(nombre);
                        menu.codigoMenu.setText(Integer.toString(codigo));
                        Usuario usr = new Usuario();
                        usr.setCorreo(nombre);

                        String queryMovimiento = "INSERT INTO actividad (idUsuario,accion,tiempoAccion) "
                                + "VALUES(?,?,Now())";
                        PreparedStatement pstMovimiento;
                        pstMovimiento = cn.prepareStatement(queryMovimiento);
                        pstMovimiento.setInt(1, Integer.parseInt(id));
                        pstMovimiento.setString(2, "Inicio de Sesión");
                        int up = pstMovimiento.executeUpdate();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Usuario Inválido");
                }
            }
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(this, err.getMessage());
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblContraseña1 = new javax.swing.JLabel();
        lblCorreo1 = new javax.swing.JLabel();
        txtPass1 = new javax.swing.JPasswordField();
        btnLogin1 = new javax.swing.JButton();
        txtCorreo1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Inicio de Sesión\n");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        setSize(new java.awt.Dimension(1280, 740));

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane2.setMaximumSize(new java.awt.Dimension(1280, 740));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(1280, 740));

        jPanel2.setMaximumSize(new java.awt.Dimension(1280, 740));
        jPanel2.setPreferredSize(new java.awt.Dimension(1280, 740));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setMaximumSize(new java.awt.Dimension(300, 400));
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 400));

        lblContraseña1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lblContraseña1.setText("Contraseña");

        lblCorreo1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lblCorreo1.setText("Correo");

        txtPass1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        txtPass1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPass1KeyPressed(evt);
            }
        });

        btnLogin1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        btnLogin1.setText("Iniciar Sesion");
        btnLogin1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLogin1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        txtCorreo1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        txtCorreo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCorreo1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblContraseña1)
                    .addComponent(lblCorreo1))
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnLogin1, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCorreo1, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                    .addComponent(txtPass1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(txtCorreo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblCorreo1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPass1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblContraseña1))
                .addGap(35, 35, 35)
                .addComponent(btnLogin1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 310, 526, 291));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PlataformaVentas/Imagenes/Background.png"))); // NOI18N
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jScrollPane2.setViewportView(jPanel2);

        getContentPane().add(jScrollPane2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        Login();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void txtCorreo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Login();
        }
    }//GEN-LAST:event_txtCorreo1KeyPressed

    private void txtPass1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPass1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Login();
        }
    }//GEN-LAST:event_txtPass1KeyPressed

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
            java.util.logging.Logger.getLogger(Login.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblContraseña1;
    private javax.swing.JLabel lblCorreo1;
    private javax.swing.JTextField txtCorreo1;
    private javax.swing.JPasswordField txtPass1;
    // End of variables declaration//GEN-END:variables

}
