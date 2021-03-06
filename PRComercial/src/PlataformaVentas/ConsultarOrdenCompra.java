/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PlataformaVentas;

import clases.Conexion;
import clases.Operaciones;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Diego
 */
public class ConsultarOrdenCompra extends javax.swing.JFrame {

    /**
     * Creates new form ConsultarOrdenCompra
     */
    Conexion con = new Conexion();
    Connection cn = con.conecta();

    public ConsultarOrdenCompra() {
        initComponents();
        tbl_OC.setVisible(false);
        jPanel1.setBackground(new Color(0, 0, 0, 30));
        Operaciones ope = new Operaciones();

        /*
         SELECT codigoOrdenCompra as 'Código de Orden de Commpra', nombre_proveedor as 'Nombre de Proveedor',RUTCliente as 'Rut de Cliente',
         ContactoOC as 'Contacto de Cliente', telefono as 'Telefono de Cliente',emailEnvioFactura AS 'Email de Envío de Factura', unidadCompra as 'Unidad de Compra', SUBSTRING(fechaEnvioOC,1,10) AS 'Fecha de Envío de OC',
         moneda as 'Moneda', neto as 'Neto Total',dcto as 'Descuento', subTotal as 'Sub Total', iva as 'IVA', impuestoEspecifico AS 'Impuesto Especifico', total as 'Total'
         FROM acimabasededatos.ordendecompra;

         */
        try {
            String query = "SELECT codigoOrdenCompra as 'Código de Orden de Commpra', nombre_proveedor as 'Nombre de Proveedor',RUTCliente as 'Rut de Cliente',\n"
                    + "         ContactoOC as 'Contacto de Cliente', telefono as 'Telefono de Cliente',emailEnvioFactura AS 'Email de Envío de Factura', unidadCompra as 'Unidad de Compra', SUBSTRING(fechaEnvioOC,1,10) AS 'Fecha de Envío de OC',\n"
                    + "         moneda as 'Moneda', neto as 'Neto Total',dcto as 'Descuento', subTotal as 'Sub Total', iva as 'IVA', impuestoEspecifico AS 'Impuesto Especifico', total as 'Total'\n"
                    + "         FROM acimabasededatos.ordendecompra;";
            //String param = txt_CodigoOC.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            // pst.setString(1, "%" + param + "%");
            ResultSet rs = pst.executeQuery();
            tblOC_Main.setModel(DbUtils.resultSetToTableModel(rs));
            //ope.OcultarColumnsOC(tbl_OC);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        try {
            String query = "SELECT idOrden, codigoOrdenCompra 'Codigo de OC', rutCliente 'Rut Cliente', DireccionDemandante, Telefono, Demandante 'Demandante', unidadCompra 'Unidad de Compra', fechaEnvioOC, codigoEstado, NombreOrdenCompra 'Nombre de OC', fechaEntregaProductos, direccionesDespacho, direccionEnvioFactura, idTipoDespacho, contactoPago, idFormaPAgo, contactoOC, emailEnvioFactura, especificacionComprador, especificacionProveedor,neto, dcto, subtotal, iva, impuestoEspecifico, total 'Total', codigo_autorizacion,nombre_proveedor 'Proveedor' FROM ordendecompra";
            //String param = txt_CodigoOC.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            // pst.setString(1, "%" + param + "%");
            ResultSet rs = pst.executeQuery();
            tbl_OC.setModel(DbUtils.resultSetToTableModel(rs));
            //ope.OcultarColumnsOC(tbl_OC);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
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
        lb_Titulo = new javax.swing.JLabel();
        lblVolver = new javax.swing.JButton();
        btnDetalles = new javax.swing.JButton();
        lblIDUsuario = new javax.swing.JLabel();
        lblCredencial = new javax.swing.JLabel();
        lbCodigo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jpnl_Demandante = new javax.swing.JPanel();
        lb_Demandante = new javax.swing.JLabel();
        txt_Demandante = new javax.swing.JTextField();
        jPnl_NombreOC = new javax.swing.JPanel();
        lb_NombreOC = new javax.swing.JLabel();
        txt_NombreOC = new javax.swing.JTextField();
        jPnl_Empresa = new javax.swing.JPanel();
        lb_Empresa = new javax.swing.JLabel();
        cb_Empresa = new javax.swing.JComboBox<>();
        jPnl_Rut = new javax.swing.JPanel();
        lb_Rut = new javax.swing.JLabel();
        txt_Rut = new javax.swing.JTextField();
        jPnl_CodigoOC = new javax.swing.JPanel();
        lb_CodigoOC = new javax.swing.JLabel();
        txt_CodigoOC = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOC_Main = new javax.swing.JTable();
        jScrollPnl_OC = new javax.swing.JScrollPane();
        tbl_OC = new javax.swing.JTable();
        Fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Consulta de Ordenes de Compra");
        setMaximumSize(new java.awt.Dimension(1280, 740));
        setResizable(false);

        jLayeredPane1.setMaximumSize(new java.awt.Dimension(1280, 686));
        jLayeredPane1.setMinimumSize(new java.awt.Dimension(1280, 686));

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lb_Titulo.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lb_Titulo.setText("<html> <p align=\"center\">Consultar <br/>de <br/>Ordenes de Compra </p>  </html>");

        lblVolver.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        lblVolver.setText("Volver");
        lblVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblVolverActionPerformed(evt);
            }
        });

        btnDetalles.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        btnDetalles.setText("Detalles");
        btnDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetallesActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PlataformaVentas/Imagenes/acima-logo-200p.png"))); // NOI18N

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTabbedPane1.setMinimumSize(new java.awt.Dimension(690, 200));

        lb_Demandante.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lb_Demandante.setText("Demandante:");

        txt_Demandante.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_Demandante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_DemandanteKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jpnl_DemandanteLayout = new javax.swing.GroupLayout(jpnl_Demandante);
        jpnl_Demandante.setLayout(jpnl_DemandanteLayout);
        jpnl_DemandanteLayout.setHorizontalGroup(
            jpnl_DemandanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnl_DemandanteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_Demandante)
                .addGap(6, 6, 6)
                .addComponent(txt_Demandante, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(118, Short.MAX_VALUE))
        );
        jpnl_DemandanteLayout.setVerticalGroup(
            jpnl_DemandanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnl_DemandanteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnl_DemandanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_Demandante, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_Demandante, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Demandante", jpnl_Demandante);

        lb_NombreOC.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lb_NombreOC.setText("Nombre OC:");

        txt_NombreOC.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_NombreOC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_NombreOCKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPnl_NombreOCLayout = new javax.swing.GroupLayout(jPnl_NombreOC);
        jPnl_NombreOC.setLayout(jPnl_NombreOCLayout);
        jPnl_NombreOCLayout.setHorizontalGroup(
            jPnl_NombreOCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnl_NombreOCLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_NombreOC)
                .addGap(3, 3, 3)
                .addComponent(txt_NombreOC, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(128, Short.MAX_VALUE))
        );
        jPnl_NombreOCLayout.setVerticalGroup(
            jPnl_NombreOCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnl_NombreOCLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnl_NombreOCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_NombreOC, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_NombreOC, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Nombre OC", jPnl_NombreOC);

        lb_Empresa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lb_Empresa.setText("Proveedor");

        cb_Empresa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cb_Empresa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione Proveedor", "Acima Soluciones Integrales", "ACIMA GLOBAL SPA" }));
        cb_Empresa.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cb_EmpresaItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPnl_EmpresaLayout = new javax.swing.GroupLayout(jPnl_Empresa);
        jPnl_Empresa.setLayout(jPnl_EmpresaLayout);
        jPnl_EmpresaLayout.setHorizontalGroup(
            jPnl_EmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnl_EmpresaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_Empresa)
                .addGap(11, 11, 11)
                .addComponent(cb_Empresa, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPnl_EmpresaLayout.setVerticalGroup(
            jPnl_EmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnl_EmpresaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnl_EmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_Empresa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_Empresa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Proveedor", jPnl_Empresa);

        lb_Rut.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lb_Rut.setText("Rut Cliente:");

        txt_Rut.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_Rut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_RutKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPnl_RutLayout = new javax.swing.GroupLayout(jPnl_Rut);
        jPnl_Rut.setLayout(jPnl_RutLayout);
        jPnl_RutLayout.setHorizontalGroup(
            jPnl_RutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnl_RutLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_Rut)
                .addGap(4, 4, 4)
                .addComponent(txt_Rut, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(133, Short.MAX_VALUE))
        );
        jPnl_RutLayout.setVerticalGroup(
            jPnl_RutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnl_RutLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnl_RutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_Rut, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_Rut, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Rut Cliente", jPnl_Rut);

        jPnl_CodigoOC.setToolTipText("dd");

        lb_CodigoOC.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lb_CodigoOC.setText("Código OC:");

        txt_CodigoOC.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_CodigoOC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_CodigoOCKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPnl_CodigoOCLayout = new javax.swing.GroupLayout(jPnl_CodigoOC);
        jPnl_CodigoOC.setLayout(jPnl_CodigoOCLayout);
        jPnl_CodigoOCLayout.setHorizontalGroup(
            jPnl_CodigoOCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnl_CodigoOCLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_CodigoOC)
                .addGap(4, 4, 4)
                .addComponent(txt_CodigoOC, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(134, Short.MAX_VALUE))
        );
        jPnl_CodigoOCLayout.setVerticalGroup(
            jPnl_CodigoOCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnl_CodigoOCLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnl_CodigoOCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_CodigoOC, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_CodigoOC, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Código OC", jPnl_CodigoOC);

        tblOC_Main.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblOC_Main);

        tbl_OC.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbl_OC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11", "Title 12", "Title 13", "Title 14", "Title 15", "Title 16", "Title 17", "Title 18", "Title 19", "Title 20", "Title 21", "Title 22", "Title 23", "Title 24", "Title 25", "Title 26", "Title 27", "Title 28", "Title 29", "Title 30", "Title 31", "Title 32", "Title 33", "Title 34"
            }
        ));
        tbl_OC.setEnabled(false);
        tbl_OC.getTableHeader().setReorderingAllowed(false);
        jScrollPnl_OC.setViewportView(tbl_OC);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(770, Short.MAX_VALUE)))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPnl_OC)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1194, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addContainerGap()))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(5, 5, 5)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(354, Short.MAX_VALUE)))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap(105, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jScrollPnl_OC, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(244, 244, 244)
                        .addComponent(lb_Titulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnDetalles, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(29, 29, 29))))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 96, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lbCodigo)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(340, 340, 340)
                            .addComponent(lblIDUsuario)
                            .addGap(10, 10, 10)
                            .addComponent(lblCredencial)))
                    .addGap(0, 819, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addComponent(lb_Titulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDetalles, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 44, Short.MAX_VALUE)
                    .addComponent(lbCodigo)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(60, 60, 60)
                            .addComponent(lblIDUsuario))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(100, 100, 100)
                            .addComponent(lblCredencial)))
                    .addGap(0, 518, Short.MAX_VALUE)))
        );

        Fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PlataformaVentas/Imagenes/BackgroundNew.png"))); // NOI18N

        jLayeredPane1.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Fondo, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addComponent(Fondo, javax.swing.GroupLayout.PREFERRED_SIZE, 1293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addComponent(Fondo)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(402, 402, 402))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_CodigoOCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_CodigoOCKeyPressed
        Operaciones ope = new Operaciones();
        try {
            String query = ope.getConsultarOC()
                    + "WHERE codigoOrdenCompra like ?";
            String param = txt_CodigoOC.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, "%" + param + "%");
            ResultSet rs = pst.executeQuery();
            tbl_OC.setModel(DbUtils.resultSetToTableModel(rs));
            ope.OcultarColumnsOC(tbl_OC);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_txt_CodigoOCKeyPressed

    private void txt_DemandanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_DemandanteKeyPressed
        Operaciones ope = new Operaciones();
        try {
            String query = ope.getConsultarOC()
                    + "WHERE Demandante like ?";
            String param = txt_Demandante.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, "%" + param + "%");
            ResultSet rs = pst.executeQuery();
            tbl_OC.setModel(DbUtils.resultSetToTableModel(rs));
            ope.OcultarColumnsOC(tbl_OC);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_txt_DemandanteKeyPressed

    private void txt_NombreOCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_NombreOCKeyPressed
        Operaciones ope = new Operaciones();
        try {
            String query = ope.getConsultarOC()
                    + "WHERE NombreOrdenCompra like ?";
            String param = txt_NombreOC.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, "%" + param + "%");
            ResultSet rs = pst.executeQuery();
            tbl_OC.setModel(DbUtils.resultSetToTableModel(rs));
            ope.OcultarColumnsOC(tbl_OC);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_txt_NombreOCKeyPressed

    private void txt_RutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_RutKeyPressed
        Operaciones ope = new Operaciones();
        try {
            String query = ope.getConsultarOC()
                    + "WHERE rutCliente like ?";
            String param = txt_Rut.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, "%" + param + "%");
            ResultSet rs = pst.executeQuery();
            tbl_OC.setModel(DbUtils.resultSetToTableModel(rs));
            ope.OcultarColumnsOC(tbl_OC);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_txt_RutKeyPressed

    private void lblVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblVolverActionPerformed
        this.dispose();
    }//GEN-LAST:event_lblVolverActionPerformed

    private void btnDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetallesActionPerformed
        if (tblOC_Main.getSelectedRow() != -1) {

            TableModel cotizacion = tbl_OC.getModel();
            int indexs[] = tblOC_Main.getSelectedRows();
            DetalleOC det = new DetalleOC();
            String codigo = "";
            for (int i = 0; i < indexs.length; i++) {
                codigo = cotizacion.getValueAt(indexs[i], 1).toString();

                det.CodigoOC.setText(cotizacion.getValueAt(indexs[i], 1).toString());
                det.RutCliente.setText(cotizacion.getValueAt(indexs[i], 2).toString());
                det.DireccionDemandante.setText(cotizacion.getValueAt(indexs[i], 3).toString());
                det.Telefono.setText(cotizacion.getValueAt(indexs[i], 4).toString());
                det.Demandante.setText(cotizacion.getValueAt(indexs[i], 5).toString());
                det.UnidadCompra.setText(cotizacion.getValueAt(indexs[i], 6).toString());
                det.FechaEnvio.setText(cotizacion.getValueAt(indexs[i], 7).toString());
                det.NombreOC.setText(cotizacion.getValueAt(indexs[i], 9).toString());
                det.FechaEntrega.setText(cotizacion.getValueAt(indexs[i], 10).toString());
                det.DireccionDespacho.setText(cotizacion.getValueAt(indexs[i], 11).toString());
                det.DireccionEnvioFactura.setText(cotizacion.getValueAt(indexs[i], 12).toString());
                det.ContactoPago.setText(cotizacion.getValueAt(indexs[i], 13).toString());
                det.ContactoOC.setText(cotizacion.getValueAt(indexs[i], 16).toString());
                det.MailEnvioFactura.setText(cotizacion.getValueAt(indexs[i], 17).toString());
                det.EspecificacionCoprador.setText(cotizacion.getValueAt(indexs[i], 18).toString());
                det.EspecificacionVendedor.setText(cotizacion.getValueAt(indexs[i], 19).toString());
                det.Neto.setText(cotizacion.getValueAt(indexs[i], 20).toString());
                det.Dcto.setText(cotizacion.getValueAt(indexs[i], 21).toString());
                det.SubTotal.setText(cotizacion.getValueAt(indexs[i], 22).toString());
                det.IVA.setText(cotizacion.getValueAt(indexs[i], 23).toString());
                det.ImpuestoEspecifico.setText(cotizacion.getValueAt(indexs[i], 24).toString());
                det.Total.setText(cotizacion.getValueAt(indexs[i], 25).toString());
                det.Proveedor.setText(cotizacion.getValueAt(indexs[i], 27).toString());
            }
            try {
                String query4 = "SELECT codigoProducto 'Codigo del Producto',nombreProducto 'Nombre del Producto',cantidad 'Cantidad',precioUnitario 'Precio Unitario',descuento 'Descuento',cargos 'Cargos',\n"
                        + "valorTotal 'Total' FROM detalleordencompra where codigoOrdenCompra=?";
                PreparedStatement pst4 = cn.prepareStatement(query4);
                pst4.setString(1, codigo);
                ResultSet rs4 = pst4.executeQuery();
                det.tblDetalle.setModel(DbUtils.resultSetToTableModel(rs4));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "ERROR: " + ex);
            }

            det.show();
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para proceder.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnDetallesActionPerformed

    private void cb_EmpresaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cb_EmpresaItemStateChanged
        Operaciones ope = new Operaciones();
        try {
            String query = ope.getConsultarOC()
                    + "WHERE nombre_proveedor = ?";
            String param = cb_Empresa.getSelectedItem().toString();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, param);
            ResultSet rs = pst.executeQuery();
            tbl_OC.setModel(DbUtils.resultSetToTableModel(rs));
            ope.OcultarColumnsOC(tbl_OC);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_cb_EmpresaItemStateChanged

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
            java.util.logging.Logger.getLogger(ConsultarOrdenCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConsultarOrdenCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConsultarOrdenCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConsultarOrdenCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConsultarOrdenCompra().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fondo;
    private javax.swing.JButton btnDetalles;
    private javax.swing.JComboBox<String> cb_Empresa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPnl_CodigoOC;
    private javax.swing.JPanel jPnl_Empresa;
    private javax.swing.JPanel jPnl_NombreOC;
    private javax.swing.JPanel jPnl_Rut;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPnl_OC;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel jpnl_Demandante;
    public javax.swing.JLabel lbCodigo;
    private javax.swing.JLabel lb_CodigoOC;
    private javax.swing.JLabel lb_Demandante;
    private javax.swing.JLabel lb_Empresa;
    private javax.swing.JLabel lb_NombreOC;
    private javax.swing.JLabel lb_Rut;
    private javax.swing.JLabel lb_Titulo;
    public javax.swing.JLabel lblCredencial;
    public javax.swing.JLabel lblIDUsuario;
    private javax.swing.JButton lblVolver;
    private javax.swing.JTable tblOC_Main;
    public javax.swing.JTable tbl_OC;
    private javax.swing.JTextField txt_CodigoOC;
    private javax.swing.JTextField txt_Demandante;
    private javax.swing.JTextField txt_NombreOC;
    private javax.swing.JTextField txt_Rut;
    // End of variables declaration//GEN-END:variables
}
