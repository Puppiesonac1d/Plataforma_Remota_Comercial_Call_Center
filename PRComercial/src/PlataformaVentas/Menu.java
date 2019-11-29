package PlataformaVentas;

import clases.Conexion;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Diego Alfaro Fierro, Diego González Romàn
 */
public class Menu extends javax.swing.JFrame {

    Conexion con = new Conexion();
    Connection cn = con.conecta();

    public Menu() {
        initComponents();

        System.out.println(jPanel1.getWidth());
        jPanel1.setBackground(new Color(0, 0, 0, 30));
        jPanel1.revalidate();
        jPanel1.repaint();
        //Fecha
        Date sistFecha = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MMM/YYYY");
        menuFecha.setText(formato.format(sistFecha));

        //Hora
        Timer tiempo = new Timer(100, new Menu.horas());
        tiempo.start();

        /*btnStatus.setEnabled(false);
         btnClientesRecientes.setEnabled(false);
         btnAgregarCliente.setEnabled(false);*/
    }

    class horas implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Date sistHora = new Date();
            String pmAm = "hh:mm:ss a";
            SimpleDateFormat format = new SimpleDateFormat(pmAm);
            Calendar hoy = Calendar.getInstance();
            menuHora.setText(String.format(format.format(sistHora), hoy));
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
        lblLogo = new javax.swing.JLabel();
        b_Titulo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnListaContactos = new javax.swing.JButton();
        btnAgregarCliente = new javax.swing.JButton();
        btnSeleccionaCotizacion = new javax.swing.JButton();
        btnClientesRecientes1 = new javax.swing.JButton();
        btnClientesRecientes = new javax.swing.JButton();
        btnStatus = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        lblFondo = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        menuUsuario = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        menuFecha = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        menuHora = new javax.swing.JMenu();
        codigoMenu = new javax.swing.JMenu();
        credencialMenu = new javax.swing.JMenu();
        idMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Menu Principal");
        setMinimumSize(new java.awt.Dimension(1280, 685));
        setPreferredSize(new java.awt.Dimension(1280, 685));
        setResizable(false);
        setSize(new java.awt.Dimension(1280, 685));

        jLayeredPane1.setMaximumSize(new java.awt.Dimension(1280, 720));
        jLayeredPane1.setMinimumSize(new java.awt.Dimension(1280, 720));
        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(1280, 700));

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PlataformaVentas/Imagenes/acima-logo-200p.png"))); // NOI18N

        b_Titulo.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        b_Titulo.setText("<html> <p align=\"center\">Menú<br/>Principal</p>  </html>");

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));
        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setMaximumSize(new java.awt.Dimension(100, 100));
        jPanel2.setMinimumSize(new java.awt.Dimension(100, 100));
        jPanel2.setPreferredSize(new java.awt.Dimension(100, 100));
        jPanel2.setLayout(new java.awt.GridLayout(4, 2, 10, 10));

        btnListaContactos.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnListaContactos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PlataformaVentas/Imagenes/android-contacts_icon-icons.com_50530.png"))); // NOI18N
        btnListaContactos.setText("Lista de Contactos");
        btnListaContactos.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnListaContactos.setIconTextGap(50);
        btnListaContactos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListaContactosActionPerformed(evt);
            }
        });
        jPanel2.add(btnListaContactos);

        btnAgregarCliente.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnAgregarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PlataformaVentas/Imagenes/1490129329-rounded38_82203 (1).png"))); // NOI18N
        btnAgregarCliente.setText("Agregar Cliente");
        btnAgregarCliente.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnAgregarCliente.setIconTextGap(50);
        btnAgregarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarClienteActionPerformed(evt);
            }
        });
        jPanel2.add(btnAgregarCliente);

        btnSeleccionaCotizacion.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnSeleccionaCotizacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PlataformaVentas/Imagenes/shopping-cart-add-button_icon-icons.com_56132.png"))); // NOI18N
        btnSeleccionaCotizacion.setText("Generar Cotización");
        btnSeleccionaCotizacion.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnSeleccionaCotizacion.setIconTextGap(50);
        btnSeleccionaCotizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionaCotizacionActionPerformed(evt);
            }
        });
        jPanel2.add(btnSeleccionaCotizacion);

        btnClientesRecientes1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnClientesRecientes1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PlataformaVentas/Imagenes/1491254405-recenttimesearchreloadtime_82966.png"))); // NOI18N
        btnClientesRecientes1.setText("Clientes Recientes");
        btnClientesRecientes1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnClientesRecientes1.setIconTextGap(50);
        btnClientesRecientes1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesRecientes1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnClientesRecientes1);

        btnClientesRecientes.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnClientesRecientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PlataformaVentas/Imagenes/person-of-a-call-center-in-communication-with-headphones.png"))); // NOI18N
        btnClientesRecientes.setText("Clientes Llamados");
        btnClientesRecientes.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnClientesRecientes.setIconTextGap(50);
        btnClientesRecientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesRecientesActionPerformed(evt);
            }
        });
        jPanel2.add(btnClientesRecientes);

        btnStatus.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PlataformaVentas/Imagenes/353436-box-health-pulse-status_107505 (1).png"))); // NOI18N
        btnStatus.setText("Estado de Cotización");
        btnStatus.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnStatus.setIconTextGap(50);
        btnStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatusActionPerformed(evt);
            }
        });
        jPanel2.add(btnStatus);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PlataformaVentas/Imagenes/mercado-público_.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);

        btnSalir.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PlataformaVentas/Imagenes/exit_icon-icons.com_70975.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnSalir.setIconTextGap(50);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel2.add(btnSalir);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblLogo)
                        .addGap(325, 325, 325)
                        .addComponent(b_Titulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 541, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(b_Titulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLogo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLayeredPane1.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1260, 590));

        lblFondo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PlataformaVentas/Imagenes/BackgroundNew.png"))); // NOI18N
        lblFondo.setToolTipText("");
        lblFondo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblFondo.setMaximumSize(new java.awt.Dimension(1280, 720));
        lblFondo.setMinimumSize(new java.awt.Dimension(1280, 720));
        lblFondo.setPreferredSize(new java.awt.Dimension(1280, 720));
        jLayeredPane1.add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 670));

        jMenu5.setText("Usuario:");
        jMenuBar1.add(jMenu5);
        jMenuBar1.add(menuUsuario);

        jMenu3.setText("Estado:");
        jMenu3.setFocusable(false);
        jMenuBar1.add(jMenu3);

        jMenu1.setText("Conectado");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Fecha:");
        jMenu2.setToolTipText("");
        jMenuBar1.add(jMenu2);
        jMenuBar1.add(menuFecha);

        jMenu4.setText("Hora:");
        jMenuBar1.add(jMenu4);
        jMenuBar1.add(menuHora);

        codigoMenu.setVisible(false);
        jMenuBar1.add(codigoMenu);

        credencialMenu.setVisible(false);
        jMenuBar1.add(credencialMenu);

        idMenu.setVisible(false);
        idMenu.setText("0");
        jMenuBar1.add(idMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 663, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        getAccessibleContext().setAccessibleName("Menú Principal");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnListaContactosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListaContactosActionPerformed
        try {
            JOptionPane.showMessageDialog(null, "Cargando Recursos... \n"
                    + "Por favor haga click en 'Aceptar' para continuar.");
            ListadoContactos cliente = new ListadoContactos();
            cliente.setVisible(true);
            cliente.lblCredencial.setText(credencialMenu.getText());
            cliente.lblIDUsuario.setText(idMenu.getText());
            cliente.lbCodigo.setText(codigoMenu.getText());
            cliente.LlenarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnListaContactosActionPerformed

    private void btnSeleccionaCotizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionaCotizacionActionPerformed
        BuscarProducto busca = new BuscarProducto();
        busca.lblUsuario.setText(menuUsuario.getText());
        busca.lblIDUsuario.setText(idMenu.getText());
        busca.lbCodigo.setText(codigoMenu.getText());
        busca.setVisible(true);
    }//GEN-LAST:event_btnSeleccionaCotizacionActionPerformed

    private void btnAgregarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarClienteActionPerformed
        DatosCliente datos = new DatosCliente();
        datos.lblCredencial.setText(credencialMenu.getText());
        datos.lblIDUsuario.setText(idMenu.getText());
        datos.lbCodigo.setText(codigoMenu.getText());
        datos.setVisible(true);
    }//GEN-LAST:event_btnAgregarClienteActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnClientesRecientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesRecientesActionPerformed

        try {
            CambiarEstadoLlamado llamado = new CambiarEstadoLlamado();
            llamado.lblIDUsuario.setText(idMenu.getText());
            System.out.println("ID RECIBIDA : " + llamado.lblIDUsuario.getText());
            llamado.setVisible(true);
            System.out.println("id previo a query" + llamado.lblIDUsuario.getText());
            int id = Integer.parseInt(llamado.lblIDUsuario.getText());

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
            llamado.tblLlamados.setModel(DbUtils.resultSetToTableModel(rs1));

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
            llamado.tblNoLlamadosSinRespuesta.setModel(DbUtils.resultSetToTableModel(rs2));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
        }


    }//GEN-LAST:event_btnClientesRecientesActionPerformed

    private void btnStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatusActionPerformed
        try {
            Status stat = new Status();
            stat.lblIDUsuario.setText(idMenu.getText());
            stat.lblCredencial.setText(credencialMenu.getText());
            stat.lbCodigo.setText(codigoMenu.getText());
            String query = "SELECT cot.IDCotizacion AS 'N° Cotizacion',cli.Rut AS 'Rut',\n"
                    + "org.NombreOrganizacion AS 'Organización',cli.Nombre AS 'Contacto',\n"
                    + "CONCAT('$',FORMAT(cot.bruto,0)) AS 'Total',date_format(cot.FechaCotizacion,'%d-%m-%Y') AS 'Fecha Emision'\n"
                    + "FROM cotizacion cot join usuario usr on cot.IDUsuario = usr.IDUsuario\n"
                    + "join cliente cli on cli.idCliente = cot.idCliente\n"
                    + "join organizacion org ON cli.IDOrganizacion = org.IDOrganizacion\n"
                    + "where cot.idUsuario=?";
            String param = idMenu.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, param);
            ResultSet rs = pst.executeQuery();
            stat.tblCotizacionesPendientes.setModel(DbUtils.resultSetToTableModel(rs));
            stat.setVisible(true);
            stat.AjusteTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }//GEN-LAST:event_btnStatusActionPerformed

    private void btnClientesRecientes1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesRecientes1ActionPerformed
        ClientesRecientes reciente = new ClientesRecientes();
        reciente.LlenarTabla();
        reciente.setVisible(true);
    }//GEN-LAST:event_btnClientesRecientes1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            JOptionPane.showMessageDialog(null, "Cargando Información");
            HistorialOC HISTORIALOC = new HistorialOC();
            HISTORIALOC.lblCodigoMenu.setText(codigoMenu.getText());
            HISTORIALOC.setVisible(true);
            String query = "select idOrdenTrabajo as 'N° Nota de Venta', codigoOrdenCompra as 'Orden de Compra', rutCliente as 'Rut', demandante as 'Demandante',unidadCompra as 'Unidad de Compra',\n"
                    + "contactoOC as 'Nombre', ordentrabajo.emailEnvioFactura as 'Mail' ,ordentrabajo.telefono as 'Teléfono', neto as 'Neto', fechaEnvioOC as 'Fecha de Envío',fechaAceptacion as 'Fecha de Aceptación' ,\n"
                    + "usuario.nombreUsuario as 'Ejecutivo' from ordenTrabajo join usuario on ordenTrabajo.codigo_autorizacion = usuario.codigo_autorizacion \n"
                    + "ORDER BY idOrden;";
            PreparedStatement pst;
            pst = cn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            HISTORIALOC.tblHistorialOC3.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
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
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel b_Titulo;
    private javax.swing.JButton btnAgregarCliente;
    private javax.swing.JButton btnClientesRecientes;
    private javax.swing.JButton btnClientesRecientes1;
    private javax.swing.JButton btnListaContactos;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSeleccionaCotizacion;
    private javax.swing.JButton btnStatus;
    public javax.swing.JMenu codigoMenu;
    public javax.swing.JMenu credencialMenu;
    public javax.swing.JMenu idMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JMenu menuFecha;
    private javax.swing.JMenu menuHora;
    public javax.swing.JMenu menuUsuario;
    // End of variables declaration//GEN-END:variables
}
