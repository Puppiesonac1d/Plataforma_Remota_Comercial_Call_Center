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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author The_S
 */
public class HistorialOC extends javax.swing.JFrame {

    Conexion con = new Conexion();
    Connection cn = con.conecta();

    public HistorialOC() {
        initComponents();
        jPanel1.setBackground(new Color(0, 0, 0, 30));
        jPanel1.revalidate();
        jPanel1.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane22 = new javax.swing.JLayeredPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel46 = new javax.swing.JPanel();
        jScrollPane22 = new javax.swing.JScrollPane();
        tblHistorialOC3 = new javax.swing.JTable();
        jButton11 = new javax.swing.JButton();
        btnVolverMenu9 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        b_Titulo = new javax.swing.JLabel();
        lblCodigoMenu = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtFiltrarOC = new javax.swing.JTextField();
        btnBuscarPorOC = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNombreDemandante = new javax.swing.JTextField();
        btnBuscarNombreDemandante = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtNombreEjecutivo = new javax.swing.JTextField();
        btnBuscarPorNombreEjecutivo = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        txtFecha = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        btnBuscarFecha = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        lblFondoHistorialOC = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1280, 685));
        setMinimumSize(new java.awt.Dimension(1280, 685));
        setResizable(false);

        jLayeredPane22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel46.setBackground(new java.awt.Color(0, 0, 0));

        tblHistorialOC3 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int celIndex){
                return false;
            }
        };
        tblHistorialOC3.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane22.setViewportView(tblHistorialOC3);

        jButton11.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton11.setText("Consultar Mercado Público");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        btnVolverMenu9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnVolverMenu9.setText("Volver");
        btnVolverMenu9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverMenu9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane22, javax.swing.GroupLayout.DEFAULT_SIZE, 883, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton11)
                    .addComponent(btnVolverMenu9, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane22, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addComponent(jButton11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVolverMenu9)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PlataformaVentas/Imagenes/acima-logo-200p.png"))); // NOI18N

        b_Titulo.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        b_Titulo.setText("Historial de Notas de Venta");

        lblCodigoMenu.setVisible(false);
        lblCodigoMenu.setText("0");

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        jLabel2.setText("Código de Orden de Compra:");

        btnBuscarPorOC.setText("Buscar");
        btnBuscarPorOC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPorOCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFiltrarOC, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarPorOC)
                .addContainerGap(171, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtFiltrarOC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarPorOC))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Filtrar por Código de Orden de Compra", jPanel3);

        jLabel3.setText("Nombre del Demandante:");

        btnBuscarNombreDemandante.setText("Buscar");
        btnBuscarNombreDemandante.setToolTipText("");
        btnBuscarNombreDemandante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarNombreDemandanteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombreDemandante, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarNombreDemandante)
                .addContainerGap(189, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNombreDemandante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarNombreDemandante))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Filtrar por Nombre del Demandante", jPanel4);

        jLabel4.setText("Nombre del Ejecutivo:");
        jLabel4.setToolTipText("");

        btnBuscarPorNombreEjecutivo.setText("Buscar");
        btnBuscarPorNombreEjecutivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPorNombreEjecutivoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombreEjecutivo, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarPorNombreEjecutivo)
                .addContainerGap(156, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNombreEjecutivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarPorNombreEjecutivo))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Filtrar por Ejecutivo", jPanel5);

        txtFecha.setDateFormatString("yyyy-MM-dd");

        jLabel5.setText("Fecha:");

        btnBuscarFecha.setText("Buscar");
        btnBuscarFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarFechaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarFecha)
                .addContainerGap(266, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnBuscarFecha)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Filtrar por Fecha", jPanel6);

        jButton1.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jButton1.setText("Reiniciar Filtros");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 726, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(b_Titulo)
                .addGap(83, 83, 83)
                .addComponent(lblCodigoMenu)
                .addContainerGap(310, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(b_Titulo)
                            .addComponent(lblCodigoMenu))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(383, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(316, Short.MAX_VALUE)
                    .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(24, 24, 24)))
        );

        jLayeredPane22.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 1240, 690));

        lblFondoHistorialOC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PlataformaVentas/Imagenes/BackgroundNew.png"))); // NOI18N
        lblFondoHistorialOC.setMaximumSize(new java.awt.Dimension(1280, 685));
        lblFondoHistorialOC.setMinimumSize(new java.awt.Dimension(1280, 685));
        lblFondoHistorialOC.setPreferredSize(new java.awt.Dimension(1280, 685));
        jLayeredPane22.add(lblFondoHistorialOC, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 730));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLayeredPane22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane22)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        if (tblHistorialOC3.getSelectionModel().isSelectionEmpty() == true) {
            ConsultaMP consultar = new ConsultaMP();
            consultar.setVisible(true);
            consultar.lblCodigo.setText(lblCodigoMenu.getText());
            this.dispose();
        } else {
            int row = tblHistorialOC3.getSelectedRow();
            TableModel historialModel = tblHistorialOC3.getModel();
            ConsultaMP consulta = new ConsultaMP();
            consulta.setVisible(true);
            consulta.lblCodigo.setText(lblCodigoMenu.getText());
            consulta.txtOC.setText(historialModel.getValueAt(row, 1).toString());
            this.dispose();
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void btnVolverMenu9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverMenu9ActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnVolverMenu9ActionPerformed

    private void btnBuscarPorOCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPorOCActionPerformed
        try {
            String query = "select idOrdenTrabajo as 'N° Nota de Venta', codigoOrdenCompra as 'Orden de Compra', rutCliente as 'Rut', demandante as 'Demandante',unidadCompra as 'Unidad de Compra',\n"
                    + "contactoOC as 'Nombre', ordentrabajo.emailEnvioFactura as 'Mail' ,ordentrabajo.telefono as 'Teléfono', neto as 'Neto', fechaEnvioOC as 'Fecha de Envío',fechaAceptacion as 'Fecha de Aceptación' ,usuario.nombreUsuario as 'Ejecutivo' \n"
                    + "from ordenTrabajo join usuario on ordenTrabajo.codigo_autorizacion = usuario.codigo_autorizacion "
                    + "WHERE ordenTrabajo.codigoOrdenCompra LIKE ?";
            String param = txtFiltrarOC.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, param);
            java.sql.ResultSet rs = pst.executeQuery();
            tblHistorialOC3.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnBuscarPorOCActionPerformed

    private void btnBuscarNombreDemandanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarNombreDemandanteActionPerformed
        try {
            String query = "select idOrdenTrabajo as 'N° Nota de Venta', codigoOrdenCompra as 'Orden de Compra', rutCliente as 'Rut', demandante as 'Demandante',unidadCompra as 'Unidad de Compra',\n"
                    + "contactoOC as 'Nombre', ordentrabajo.emailEnvioFactura as 'Mail' ,ordentrabajo.telefono as 'Teléfono', neto as 'Neto', fechaEnvioOC as 'Fecha de Envío',fechaAceptacion as 'Fecha de Aceptación' ,usuario.nombreUsuario as 'Ejecutivo' \n"
                    + "from ordenTrabajo join usuario on ordenTrabajo.codigo_autorizacion = usuario.codigo_autorizacion "
                    + "WHERE ordenTrabajo.demandante LIKE ?";
            String param = txtNombreDemandante.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, param);
            java.sql.ResultSet rs = pst.executeQuery();
            tblHistorialOC3.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnBuscarNombreDemandanteActionPerformed

    private void btnBuscarPorNombreEjecutivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPorNombreEjecutivoActionPerformed
        try {
            String query = "select idOrdenTrabajo as 'N° Nota de Venta', codigoOrdenCompra as 'Orden de Compra', rutCliente as 'Rut', demandante as 'Demandante',unidadCompra as 'Unidad de Compra',\n"
                    + "contactoOC as 'Nombre', ordentrabajo.emailEnvioFactura as 'Mail' ,ordentrabajo.telefono as 'Teléfono', neto as 'Neto', fechaEnvioOC as 'Fecha de Envío',fechaAceptacion as 'Fecha de Aceptación' ,usuario.nombreUsuario as 'Ejecutivo' \n"
                    + "from ordenTrabajo join usuario on ordenTrabajo.codigo_autorizacion = usuario.codigo_autorizacion "
                    + "WHERE usuario.nombreUsuario LIKE ?";
            String param = txtNombreEjecutivo.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, param);
            java.sql.ResultSet rs = pst.executeQuery();
            tblHistorialOC3.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnBuscarPorNombreEjecutivoActionPerformed

    private void btnBuscarFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarFechaActionPerformed
        Date date = txtFecha.getDate();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String strDate = simpleDateFormat.format(date);
        System.out.println(strDate);
        try {
            String query = "select idOrdenTrabajo as 'N° Nota de Venta', codigoOrdenCompra as 'Orden de Compra', rutCliente as 'Rut', demandante as 'Demandante',unidadCompra as 'Unidad de Compra',\n"
                    + "contactoOC as 'Nombre', ordentrabajo.emailEnvioFactura as 'Mail' ,ordentrabajo.telefono as 'Teléfono', neto as 'Neto', fechaEnvioOC as 'Fecha de Envío',fechaAceptacion as 'Fecha de Aceptación' ,usuario.nombreUsuario as 'Ejecutivo' \n"
                    + "from ordenTrabajo join usuario on ordenTrabajo.codigo_autorizacion = usuario.codigo_autorizacion "
                    + "WHERE ordenTrabajo.fechaEnvioOC RLIKE ?";
            String param = txtNombreEjecutivo.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, strDate);
            java.sql.ResultSet rs = pst.executeQuery();
            tblHistorialOC3.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnBuscarFechaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            String query = "select idOrdenTrabajo as 'N° Nota de Venta', codigoOrdenCompra as 'Orden de Compra', rutCliente as 'Rut', demandante as 'Demandante',unidadCompra as 'Unidad de Compra',\n"
                    + "contactoOC as 'Nombre', ordentrabajo.emailEnvioFactura as 'Mail' ,ordentrabajo.telefono as 'Teléfono', neto as 'Neto', fechaEnvioOC as 'Fecha de Envío',fechaAceptacion as 'Fecha de Aceptación' ,usuario.nombreUsuario as 'Ejecutivo' \n"
                    + "from ordenTrabajo join usuario on ordenTrabajo.codigo_autorizacion = usuario.codigo_autorizacion ;";
            PreparedStatement pst;
            pst = cn.prepareStatement(query);
            java.sql.ResultSet rs = pst.executeQuery();
            tblHistorialOC3.setModel(DbUtils.resultSetToTableModel(rs));
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
            java.util.logging.Logger.getLogger(HistorialOC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HistorialOC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HistorialOC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HistorialOC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HistorialOC().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel b_Titulo;
    private javax.swing.JButton btnBuscarFecha;
    private javax.swing.JButton btnBuscarNombreDemandante;
    private javax.swing.JButton btnBuscarPorNombreEjecutivo;
    private javax.swing.JButton btnBuscarPorOC;
    private javax.swing.JButton btnVolverMenu9;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLayeredPane jLayeredPane22;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JLabel lblCodigoMenu;
    private javax.swing.JLabel lblFondoHistorialOC;
    public javax.swing.JTable tblHistorialOC3;
    private com.toedter.calendar.JDateChooser txtFecha;
    private javax.swing.JTextField txtFiltrarOC;
    private javax.swing.JTextField txtNombreDemandante;
    private javax.swing.JTextField txtNombreEjecutivo;
    // End of variables declaration//GEN-END:variables
}
