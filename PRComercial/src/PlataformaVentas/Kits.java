/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PlataformaVentas;

import clases.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author The_S
 */
public class Kits extends javax.swing.JFrame {

    Conexion con = new Conexion();
    Connection cn = con.conecta();

    public Kits() {
        try {
            initComponents();

            cmbCM.removeAllItems();
            String queryCM = "select subcategoria from categoria;";
            PreparedStatement pstCM = cn.prepareStatement(queryCM);
            java.sql.ResultSet rsCM = pstCM.executeQuery();
            while (rsCM.next()) {
                cmbCM.addItem(rsCM.getString(1));
            }

            cmbMaterial.removeAllItems();
            String queryMat = "select material from material;";
            PreparedStatement pstMat = cn.prepareStatement(queryMat);
            java.sql.ResultSet rsMat = pstMat.executeQuery();
            while (rsMat.next()) {
                cmbMaterial.addItem(rsMat.getString(1));
            }
            cmbTalla.removeAllItems();
            String queryTa = "select talla from talla;";
            PreparedStatement pstTa = cn.prepareStatement(queryTa);
            java.sql.ResultSet rsTa = pstTa.executeQuery();
            while (rsTa.next()) {
                cmbTalla.addItem(rsTa.getString(1));
            }

            int idProducto = 0;
            String queryprod = "SELECT \n"
                    + "    CASE\n"
                    + "        WHEN ID IS NULL THEN 1\n"
                    + "        WHEN ID IS NOT NULL THEN MAX(ID)+1\n"
                    + "    END\n"
                    + "FROM\n"
                    + "    acimabasededatos.inventario;";
            PreparedStatement pstProd = cn.prepareStatement(queryprod);

            java.sql.ResultSet rsProd = pstProd.executeQuery();
            while (rsProd.next()) {
                idProducto = rsProd.getInt("CASE\n"
                        + "        WHEN ID IS NULL THEN 1\n"
                        + "        WHEN ID IS NOT NULL THEN MAX(ID)+1\n"
                        + "    END");
            }

            txtIDINV.setText(Integer.toString(idProducto));
        } catch (SQLException ex) {
            Logger.getLogger(Kits.class.getName()).log(Level.SEVERE, null, ex);
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

        AgregarProducto = new javax.swing.JFrame();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNombreProducto = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtPrecioCompra = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtPrecioVenta = new javax.swing.JTextField();
        btnConfirmar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtStock = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtIDKit = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        btnAgregarGrupo = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtNombreKit = new javax.swing.JTextField();
        btnConfirmarKit = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cmbCM = new javax.swing.JComboBox<>();
        cmbMaterial = new javax.swing.JComboBox<>();
        cmbTalla = new javax.swing.JComboBox<>();
        txtIDINV = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnBorrar = new javax.swing.JButton();

        AgregarProducto.setMinimumSize(new java.awt.Dimension(1148, 260));
        AgregarProducto.setPreferredSize(new java.awt.Dimension(1148, 260));
        AgregarProducto.setResizable(false);
        AgregarProducto.setSize(new java.awt.Dimension(1148, 260));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel3.setText("Nombre de Producto:");

        txtNombreProducto.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel4.setText("Precio de Compra:");

        txtPrecioCompra.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        txtPrecioCompra.setText("0");
        txtPrecioCompra.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel5.setText("Precio de Venta:");

        txtPrecioVenta.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        btnConfirmar.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel7.setText("Cantidad:");

        txtStock.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnConfirmar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 889, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecioVenta)
                            .addComponent(txtPrecioCompra)
                            .addComponent(txtNombreProducto))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtPrecioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(btnConfirmar)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout AgregarProductoLayout = new javax.swing.GroupLayout(AgregarProducto.getContentPane());
        AgregarProducto.getContentPane().setLayout(AgregarProductoLayout);
        AgregarProductoLayout.setHorizontalGroup(
            AgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AgregarProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        AgregarProductoLayout.setVerticalGroup(
            AgregarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AgregarProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel1.setText("ID de Convenio Marco:");

        txtIDKit.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        txtIDKit.setToolTipText("");

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SKU", "Nombre de Producto", "Precio Compra", "Precio Venta", "Cantidad"
            }
        ));
        tblProductos.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblProductos);

        btnAgregarGrupo.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnAgregarGrupo.setText("Agregar Producto a grupo");
        btnAgregarGrupo.setToolTipText("");
        btnAgregarGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarGrupoActionPerformed(evt);
            }
        });

        btnVolver.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnVolver.setText("Volver");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel6.setText("Nombre de Kit");

        txtNombreKit.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        btnConfirmarKit.setBackground(new java.awt.Color(0, 153, 0));
        btnConfirmarKit.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnConfirmarKit.setText("Confirmar Kit");
        btnConfirmarKit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarKitActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel2.setText("SKU:");

        cmbCM.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        cmbCM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione Convenio Marco" }));

        cmbMaterial.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        cmbMaterial.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione Material" }));

        cmbTalla.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        cmbTalla.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione Talla" }));

        txtIDINV.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        txtIDINV.setText("0");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel8.setText("K-");

        btnBorrar.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnBorrar.setText("Borrar Producto");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnConfirmarKit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1348, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAgregarGrupo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBorrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVolver))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIDKit)
                            .addComponent(txtNombreKit)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbCM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbTalla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIDINV)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIDKit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIDINV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTalla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbCM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtNombreKit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarGrupo)
                    .addComponent(btnVolver)
                    .addComponent(btnBorrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnConfirmarKit)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarGrupoActionPerformed
        int rowCount = tblProductos.getRowCount();
        if (rowCount == 0) {
            AgregarProducto.setVisible(true);
        } else {
            int dialogResult = JOptionPane.showConfirmDialog(null, "¿Desea agregar otro producto?");
            if (dialogResult == JOptionPane.YES_OPTION) {
                AgregarProducto.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "No se agregarán mas productos");
            }

        }
    }//GEN-LAST:event_btnAgregarGrupoActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        try {
            int idMaterial = 0;
            String queryMaterial = "select idMaterial from material where material = ?";
            PreparedStatement pstMat = cn.prepareStatement(queryMaterial);
            pstMat.setString(1, cmbMaterial.getSelectedItem().toString());
            java.sql.ResultSet rsMat = pstMat.executeQuery();
            while (rsMat.next()) {
                idMaterial = rsMat.getInt("idMaterial");
            }
            System.out.println("Id de material: " + idMaterial);

            int idTalla = 0;
            String queryTalla = "select id_talla from talla where talla = ?";
            PreparedStatement pstTalla = cn.prepareStatement(queryTalla);
            pstTalla.setString(1, cmbTalla.getSelectedItem().toString());
            java.sql.ResultSet rsTalla = pstTalla.executeQuery();
            while (rsTalla.next()) {
                idTalla = rsTalla.getInt("id_talla");
            }
            System.out.println("Id de Talla: " + idTalla);

            int idCategoria = 0;
            String queryCategoria = "select id from categoria where subcategoria = ?";
            PreparedStatement pstCategoria = cn.prepareStatement(queryCategoria);
            pstCategoria.setString(1, cmbCM.getSelectedItem().toString());
            java.sql.ResultSet rsCategoria = pstCategoria.executeQuery();
            while (rsCategoria.next()) {
                idCategoria = rsCategoria.getInt("id");
            }

            Object[] row = new Object[15];

            DefaultTableModel modeloNuevo = (DefaultTableModel) tblProductos.getModel();
            //idCategoria + idMaterial + idTalla + txtIDINV.getText();
            row[0] = "-";
            row[1] = txtNombreProducto.getText();
            row[2] = txtPrecioCompra.getText();
            row[3] = txtPrecioVenta.getText();
            row[4] = txtStock.getText();

            modeloNuevo.addRow(row);

            AgregarProducto.dispose();
            txtNombreProducto.setText("");
            txtPrecioCompra.setText("");
            txtPrecioVenta.setText("");
            txtStock.setText("");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnConfirmarKitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarKitActionPerformed
        //Una vez confirmado el kit
        try {
            int rowCount = tblProductos.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                //Poner el sku previamente ingresado
                tblProductos.setValueAt("K-" + Integer.toString(cmbCM.getSelectedIndex()) + Integer.toString(cmbMaterial.getSelectedIndex()) + Integer.toString(cmbTalla.getSelectedIndex()) + txtIDINV.getText(),
                         i, 0);

                String query = "INSERT INTO KITS (idProducto,nombreKit,sku,producto,precioCosto,precioVenta,stock) VALUES (?,?,?,?,?,?,?);";
                PreparedStatement insert = cn.prepareStatement(query);
                insert.setString(1, txtIDKit.getText());
                insert.setString(2, txtNombreKit.getText());
                insert.setString(3, "K-" + Integer.toString(cmbCM.getSelectedIndex()) + Integer.toString(cmbMaterial.getSelectedIndex()) + Integer.toString(cmbTalla.getSelectedIndex()) + txtIDINV.getText());
                insert.setString(4, tblProductos.getValueAt(i, 1).toString());
                insert.setInt(5, Integer.parseInt(tblProductos.getValueAt(i, 2).toString()));
                insert.setInt(6, Integer.parseInt(tblProductos.getValueAt(i, 3).toString()));
                insert.setInt(7, Integer.parseInt(tblProductos.getValueAt(i, 4).toString()));

                int x = insert.executeUpdate();
            }

            JOptionPane.showMessageDialog(null, "Kit " + txtNombreKit.getText() + " registrado.");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnConfirmarKitActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        DefaultTableModel modeloTabla = (DefaultTableModel) tblProductos.getModel();
        // Se obtiene el numero de columna seleccionado
        try {
            if (tblProductos.getSelectionModel().isSelectionEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un producto de la lista");
            } else {
                //Obtener la fila
                int seleccion1 = tblProductos.getSelectedRow();

                //Borrar el elemento
                modeloTabla.removeRow(seleccion1);
                JOptionPane.showMessageDialog(null, "Producto borrado de Selección");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
        }

    }//GEN-LAST:event_btnBorrarActionPerformed

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
            java.util.logging.Logger.getLogger(Kits.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Kits.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Kits.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Kits.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Kits().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame AgregarProducto;
    private javax.swing.JButton btnAgregarGrupo;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnConfirmarKit;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> cmbCM;
    private javax.swing.JComboBox<String> cmbMaterial;
    private javax.swing.JComboBox<String> cmbTalla;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextField txtIDINV;
    private javax.swing.JTextField txtIDKit;
    private javax.swing.JTextField txtNombreKit;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txtPrecioCompra;
    private javax.swing.JTextField txtPrecioVenta;
    private javax.swing.JTextField txtStock;
    // End of variables declaration//GEN-END:variables
}
