/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PlataformaVentas;

import clases.Conexion;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author The_S
 */
public class Reportes extends javax.swing.JFrame {

    Conexion con = new Conexion();
    Connection cn = con.conecta();

    public Reportes() {
        initComponents();

        try {
            String query1 = "SELECT \n"
                    + "    CODIGOPRODUCTO AS 'Código de Producto',\n"
                    + "    nombreProducto AS 'Nombre de Producto',\n"
                    + "    SUM(CANTIDAD) AS 'Vendidos',\n"
                    + "    (SELECT \n"
                    + "            SUM(CANTIDAD)\n"
                    + "        FROM\n"
                    + "            DETALLEORDENTRABAJO) AS 'Total de productos',\n"
                    + "    ((SUM(CANTIDAD) * 100) / (SELECT \n"
                    + "            SUM(CANTIDAD)\n"
                    + "        FROM\n"
                    + "            DETALLEORDENTRABAJO)) AS 'PORCENTAJE DE VENDIDOS VS TOTAL DE PRODUCTOS'\n"
                    + "FROM\n"
                    + "    detalleordentrabajo\n"
                    + "GROUP BY NOMBREPRODUCTO\n"
                    + "ORDER BY SUM(CANTIDAD) DESC;";
            PreparedStatement pst1 = cn.prepareStatement(query1);

            ResultSet rs1 = pst1.executeQuery();
            tblProductosVendidos.setModel(DbUtils.resultSetToTableModel(rs1));
        } catch (SQLException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String query = "SELECT nombreUsuario FROM acimabasededatos.usuario;";
            PreparedStatement pst = cn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                cmbEjecutivoVentas.addItem(rs.getString(1));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        try {
            String query1 = "SELECT CODIGOORDENCOMPRA AS 'CÓDIGO DE ORDEN DE COMPRA',DEMANDANTE AS 'DEMANDANTE',"
                    + " NOMBRE_PROVEEDOR AS 'EMPRESA',USUARIO.NOMBREUSUARIO AS 'EJECUTIVO' \n"
                    + "FROM ORDENTRABAJO join usuario ON ordentrabajo.codigo_autorizacion = usuario.codigo_autorizacion "
                    + "group by CODIGOORDENCOMPRA;";
            PreparedStatement pst1 = cn.prepareStatement(query1);

            ResultSet rs1 = pst1.executeQuery();

            tblVentasEjecutivos.setModel(DbUtils.resultSetToTableModel(rs1));

        } catch (SQLException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Facturación por empresa
        try {
            String query = "SELECT nombreDistribuidor FROM acimabasededatos.distribuidor;";
            PreparedStatement pst = cn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                cmbDistribuidor.addItem(rs.getString(1));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        try {
            String query1 = "SELECT \n"
                    + "    ot.CODIGOORDENCOMPRA AS 'CÓDIGO DE ORDEN DE COMPRA',\n"
                    + "    DEMANDANTE AS 'DEMANDANTE',\n"
                    + "    NOMBRE_PROVEEDOR AS 'EMPRESA',\n"
                    + "    dot.moneda AS 'MONEDA',\n"
                    + "    CASE\n"
                    + "        WHEN dot.moneda = 'USD' THEN REPLACE(IFNULL(total, 0), '$', '')\n"
                    + "        WHEN\n"
                    + "            dot.moneda = 'CLP'\n"
                    + "        THEN\n"
                    + "            REPLACE(REPLACE(IFNULL(total, 0), '$', ''),\n"
                    + "                '.',\n"
                    + "                '')\n"
                    + "        ELSE 0\n"
                    + "    END AS 'TOTAL'\n"
                    + "FROM\n"
                    + "    ORDENTRABAJO ot\n"
                    + "        JOIN\n"
                    + "    detalleordentrabajo dot ON ot.idOrden = dot.idOrden\n"
                    + "WHERE\n"
                    + "    NOMBRE_PROVEEDOR = 'Acima Soluciones Integrales '\n"
                    + "GROUP BY ot.CODIGOORDENCOMPRA;";
            PreparedStatement pst1 = cn.prepareStatement(query1);
            ResultSet rs1 = pst1.executeQuery();
            tblFacturacionEmpresa.setModel(DbUtils.resultSetToTableModel(rs1));
        } catch (SQLException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
        try {

            String total = "";
            String query1 = "SELECT\n"
                    + "SUM(REPLACE(REPLACE(total, '$', ''),\n"
                    + "        '.',\n"
                    + "        ''))\n"
                    + "FROM\n"
                    + "    acimabasededatos.ordentrabajo\n"
                    + "    WHERE NOMBRE_PROVEEDOR = ?\n"
                    + "GROUP BY NOMBRE_PROVEEDOR;";
            PreparedStatement pst1 = cn.prepareStatement(query1);
            pst1.setString(1, cmbDistribuidor.getSelectedItem().toString());
            ResultSet rs1 = pst1.executeQuery();
            while (rs1.next()) {
                total = rs1.getString(1);
            }
            System.out.println("total:" + total);
            DecimalFormat formatea = new DecimalFormat("###,###.##");
            int totalFormat = 0;
            if (total != "") {
                totalFormat = Integer.parseInt(total);
            } else {
                totalFormat = 0;
            }

            lblFacturacion.setText("$" + formatea.format(totalFormat));

        } catch (SQLException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {

            String total = "";
            String query1 = "SELECT\n"
                    + "SUM(REPLACE(REPLACE(total, '$', ''),\n"
                    + "        '.',\n"
                    + "        ''))\n"
                    + "FROM\n"
                    + "    acimabasededatos.ordentrabajo;";
            PreparedStatement pst1 = cn.prepareStatement(query1);
            ResultSet rs1 = pst1.executeQuery();
            while (rs1.next()) {
                total = rs1.getString(1);
            }
            System.out.println("total:" + total);
            DecimalFormat formatea = new DecimalFormat("###,###.##");
            int totalFormat = 0;
            if (total != "") {
                totalFormat = Integer.parseInt(total);
            } else {
                totalFormat = 0;
            }

            lblTotalFacturacion.setText("$" + formatea.format(totalFormat));

        } catch (SQLException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
         */
        cmbDistribuidor.setSelectedItem(3);
        //Inventario de productos
        try {
            String queryProducto = "SELECT \n"
                    + "    idProducto AS 'ID producto',\n"
                    + "    SKU,\n"
                    + "    categoria AS 'Categoría',\n"
                    + "    nombreProducto AS 'Producto',\n"
                    + "    FORMAT(precioVenta, 'es_CL') AS 'Precio Venta',\n"
                    + "    FORMAT(precioCosto, 'es_CL') AS 'Precio Costo',\n"
                    + "    stock AS 'Stock'\n"
                    + "FROM\n"
                    + "    inventario r\n"
                    + "        LEFT JOIN\n"
                    + "    distribuidor d ON r.IDDISTRIBUIDOR = d.idDistribuidor;";
            PreparedStatement pstProducto = cn.prepareStatement(queryProducto);
            ResultSet rsProducto = pstProducto.executeQuery();
            tblProductoInventarioBodega.setModel(DbUtils.resultSetToTableModel(rsProducto));
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

        btnSalir = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabPanelElementos = new javax.swing.JTabbedPane();
        panelProductos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtIDProducto = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnReiniciarID = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNombreProducto = new javax.swing.JTextField();
        btnBuscarNombre = new javax.swing.JButton();
        btnReiniciarNombre = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductosVendidos = new javax.swing.JTable();
        btnExportarProductosExcel = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jTabbedPane7 = new javax.swing.JTabbedPane();
        jTabbedPane8 = new javax.swing.JTabbedPane();
        jPanel17 = new javax.swing.JPanel();
        jLabel86 = new javax.swing.JLabel();
        txtFiltrarNombreInventario = new javax.swing.JTextField();
        btnBuscarNombre2 = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jLabel87 = new javax.swing.JLabel();
        txtSKUInventarioBodega = new javax.swing.JTextField();
        btnBuscarSKU2 = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        jLabel88 = new javax.swing.JLabel();
        cmbStatusFiltrarInventario = new javax.swing.JComboBox<>();
        btnBuscarStatus1 = new javax.swing.JButton();
        jScrollPane12 = new javax.swing.JScrollPane();
        tblProductoInventarioBodega = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        btnReiniciarTablaProducto5 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        panelVentasEjecutivo = new javax.swing.JPanel();
        btnExportarEjecutivo = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cmbEjecutivoVentas = new javax.swing.JComboBox<>();
        btnReiniciarEjecutivos = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblVentasEjecutivos = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblFacturacionEmpresa = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        lblFacturacion = new javax.swing.JLabel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        cmbDistribuidor = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        lblValorDolar = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblTotalFacturacion = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1280, 740));
        setSize(new java.awt.Dimension(1280, 740));

        btnSalir.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        tabPanelElementos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabPanelElementosMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Productos más vendidos");

        jTabbedPane1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setText("ID de producto:");

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnReiniciarID.setText("Reiniciar Filtro");
        btnReiniciarID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReiniciarIDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtIDProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReiniciarID)
                .addContainerGap(620, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtIDProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)
                    .addComponent(btnReiniciarID))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Filtrar por ID de producto", jPanel1);

        jLabel3.setText("Nombre de Producto");

        btnBuscarNombre.setText("Buscar");
        btnBuscarNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarNombreActionPerformed(evt);
            }
        });

        btnReiniciarNombre.setText("Reiniciar Filtro");
        btnReiniciarNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReiniciarNombreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarNombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReiniciarNombre)
                .addGap(505, 505, 505))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarNombre)
                    .addComponent(btnReiniciarNombre))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1215, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );

        jTabbedPane1.addTab("Filtrar por Nombre de Producto", jPanel2);

        tblProductosVendidos = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int celIndex){
                return false;
            }
        };
        tblProductosVendidos.setModel(new javax.swing.table.DefaultTableModel(
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
        tblProductosVendidos.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblProductosVendidos);

        btnExportarProductosExcel.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnExportarProductosExcel.setText("Exportar a Excel");
        btnExportarProductosExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarProductosExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelProductosLayout = new javax.swing.GroupLayout(panelProductos);
        panelProductos.setLayout(panelProductosLayout);
        panelProductosLayout.setHorizontalGroup(
            panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelProductosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnExportarProductosExcel))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        panelProductosLayout.setVerticalGroup(
            panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExportarProductosExcel)
                .addGap(10, 10, 10))
        );

        tabPanelElementos.addTab("Productos Más Vendidos", panelProductos);

        jTabbedPane7.setBackground(new java.awt.Color(0, 153, 153));

        jTabbedPane8.setBackground(new java.awt.Color(0, 153, 153));
        jTabbedPane8.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);

        jPanel17.setBackground(new java.awt.Color(252, 252, 252));

        jLabel86.setText("Nombre Genérico:");
        jLabel86.setToolTipText("");

        txtFiltrarNombreInventario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFiltrarNombreInventarioKeyPressed(evt);
            }
        });

        btnBuscarNombre2.setText("Buscar");
        btnBuscarNombre2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarNombre2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel86)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFiltrarNombreInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarNombre2)
                .addContainerGap(476, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel86)
                    .addComponent(txtFiltrarNombreInventario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarNombre2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane8.addTab("Busqueda de productos por Nombre", jPanel17);

        jPanel18.setBackground(new java.awt.Color(252, 252, 252));

        jLabel87.setText("Buscar por SKU:");

        txtSKUInventarioBodega.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSKUInventarioBodegaKeyPressed(evt);
            }
        });

        btnBuscarSKU2.setText("Buscar");
        btnBuscarSKU2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarSKU2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel87)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSKUInventarioBodega, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarSKU2)
                .addContainerGap(692, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnBuscarSKU2)
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSKUInventarioBodega)
                        .addComponent(jLabel87)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane8.addTab("Busqueda de productos por SKU", jPanel18);

        jPanel19.setBackground(new java.awt.Color(252, 252, 252));

        jLabel88.setText("Busqueda por Status:");

        cmbStatusFiltrarInventario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Status", "Publicado", "Sin Stock", "Sin Información" }));

        btnBuscarStatus1.setText("Buscar");
        btnBuscarStatus1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarStatus1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel88)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbStatusFiltrarInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarStatus1)
                .addContainerGap(566, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel88)
                    .addComponent(cmbStatusFiltrarInventario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarStatus1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane8.addTab("Busqueda de productos por Status", jPanel19);

        jTabbedPane7.addTab("Filtrar Productos", jTabbedPane8);

        tblProductoInventarioBodega = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int celIndex){
                return false;
            }
        };
        tblProductoInventarioBodega.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblProductoInventarioBodega.getTableHeader().setReorderingAllowed(false);
        jScrollPane12.setViewportView(tblProductoInventarioBodega);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Inventario de Productos");

        btnReiniciarTablaProducto5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnReiniciarTablaProducto5.setText("Reiniciar Productos");
        btnReiniciarTablaProducto5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReiniciarTablaProducto5ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton2.setText("Exportar Inventario a Excel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jTabbedPane7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReiniciarTablaProducto5))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton2)
                            .addComponent(jScrollPane12))
                        .addGap(9, 9, 9)))
                .addGap(10, 10, 10))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel8)
                .addGap(10, 10, 10)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReiniciarTablaProducto5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );

        tabPanelElementos.addTab("Inventario de Productos", jPanel6);

        btnExportarEjecutivo.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnExportarEjecutivo.setText("Exportar a Excel");
        btnExportarEjecutivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarEjecutivoActionPerformed(evt);
            }
        });

        jTabbedPane2.setMinimumSize(new java.awt.Dimension(170, 50));
        jTabbedPane2.setPreferredSize(new java.awt.Dimension(170, 50));

        jLabel5.setText("Ejecutivo:");

        cmbEjecutivoVentas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Ejecutivo" }));
        cmbEjecutivoVentas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbEjecutivoVentasItemStateChanged(evt);
            }
        });

        btnReiniciarEjecutivos.setText("Reiniciar");
        btnReiniciarEjecutivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReiniciarEjecutivosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbEjecutivoVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReiniciarEjecutivos)
                .addContainerGap(739, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbEjecutivoVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReiniciarEjecutivos))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Filtrar por Ejecutivo", jPanel4);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Ventas por Ejecutivo");

        tblVentasEjecutivos = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int celIndex){
                return false;
            }
        };
        tblVentasEjecutivos.setModel(new javax.swing.table.DefaultTableModel(
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
        tblVentasEjecutivos.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tblVentasEjecutivos);

        javax.swing.GroupLayout panelVentasEjecutivoLayout = new javax.swing.GroupLayout(panelVentasEjecutivo);
        panelVentasEjecutivo.setLayout(panelVentasEjecutivoLayout);
        panelVentasEjecutivoLayout.setHorizontalGroup(
            panelVentasEjecutivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVentasEjecutivoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelVentasEjecutivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1226, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelVentasEjecutivoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnExportarEjecutivo)))
                .addContainerGap())
        );
        panelVentasEjecutivoLayout.setVerticalGroup(
            panelVentasEjecutivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelVentasEjecutivoLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 696, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExportarEjecutivo)
                .addContainerGap())
        );

        tabPanelElementos.addTab("Ventas por Ejecutivo", panelVentasEjecutivo);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Facturación por Empresa");

        tblFacturacionEmpresa = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int celIndex){
                return false;
            }
        };
        tblFacturacionEmpresa.setModel(new javax.swing.table.DefaultTableModel(
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
        tblFacturacionEmpresa.getTableHeader().setReorderingAllowed(false);
        tblFacturacionEmpresa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFacturacionEmpresaMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblFacturacionEmpresa);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel7.setText("Resumen de Facturación:");

        lblFacturacion.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblFacturacion.setText("$0");

        jLabel9.setText("Empresa:");

        cmbDistribuidor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione Empresa" }));
        cmbDistribuidor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbDistribuidorItemStateChanged(evt);
            }
        });

        jLabel11.setText("Valor del Dólar:");

        lblValorDolar.setText("$0");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbDistribuidor, 0, 250, Short.MAX_VALUE)
                    .addComponent(lblValorDolar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(874, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cmbDistribuidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblValorDolar))
                .addContainerGap())
        );

        jTabbedPane3.addTab("Filtrar por empresa", jPanel7);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel10.setText("Total de Facturación:");

        lblTotalFacturacion.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblTotalFacturacion.setText("$0");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFacturacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTotalFacturacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPane3)))
                .addGap(18, 18, 18))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblFacturacion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lblTotalFacturacion))
                .addContainerGap())
        );

        tabPanelElementos.addTab("Facturación por Empresa", jPanel5);

        jScrollPane2.setViewportView(tabPanelElementos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1260, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSalir)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalir)
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            String query1 = "SELECT \n"
                    + "    CODIGOPRODUCTO AS 'Código de Producto',\n"
                    + "    nombreProducto AS 'Nombre de Producto',\n"
                    + "    SUM(CANTIDAD) AS 'Vendidos',\n"
                    + "    (SELECT \n"
                    + "            SUM(CANTIDAD)\n"
                    + "        FROM\n"
                    + "            DETALLEORDENTRABAJO) AS 'Total de productos',\n"
                    + "    ((SUM(CANTIDAD) * 100) / (SELECT \n"
                    + "            SUM(CANTIDAD)\n"
                    + "        FROM\n"
                    + "            DETALLEORDENTRABAJO)) AS 'PORCENTAJE DE VENDIDOS VS TOTAL DE PRODUCTOS'\n"
                    + "FROM\n"
                    + "    detalleordentrabajo\n"
                    + "    WHERE CODIGOPRODUCTO = ?\n"
                    + "GROUP BY NOMBREPRODUCTO\n"
                    + "ORDER BY SUM(CANTIDAD) DESC;";
            PreparedStatement pst1 = cn.prepareStatement(query1);
            pst1.setString(1, txtIDProducto.getText());
            ResultSet rs1 = pst1.executeQuery();
            tblProductosVendidos.setModel(DbUtils.resultSetToTableModel(rs1));

        } catch (SQLException ex) {
            Logger.getLogger(Reportes.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnReiniciarIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReiniciarIDActionPerformed
        try {
            String query1 = "SELECT \n"
                    + "    CODIGOPRODUCTO AS 'Código de Producto',\n"
                    + "    nombreProducto AS 'Nombre de Producto',\n"
                    + "    SUM(CANTIDAD) AS 'Vendidos',\n"
                    + "    (SELECT \n"
                    + "            SUM(CANTIDAD)\n"
                    + "        FROM\n"
                    + "            DETALLEORDENTRABAJO) AS 'Total de productos',\n"
                    + "    ((SUM(CANTIDAD) * 100) / (SELECT \n"
                    + "            SUM(CANTIDAD)\n"
                    + "        FROM\n"
                    + "            DETALLEORDENTRABAJO)) AS 'PORCENTAJE DE VENDIDOS VS TOTAL DE PRODUCTOS'\n"
                    + "FROM\n"
                    + "    detalleordentrabajo\n"
                    + "GROUP BY NOMBREPRODUCTO\n"
                    + "ORDER BY SUM(CANTIDAD) DESC;";
            PreparedStatement pst1 = cn.prepareStatement(query1);
            ResultSet rs1 = pst1.executeQuery();
            tblProductosVendidos.setModel(DbUtils.resultSetToTableModel(rs1));

        } catch (SQLException ex) {
            Logger.getLogger(Reportes.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnReiniciarIDActionPerformed

    private void btnBuscarNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarNombreActionPerformed
        try {
            String query1 = "SELECT \n"
                    + "    CODIGOPRODUCTO AS 'Código de Producto',\n"
                    + "    nombreProducto AS 'Nombre de Producto',\n"
                    + "    SUM(CANTIDAD) AS 'Vendidos',\n"
                    + "    (SELECT \n"
                    + "            SUM(CANTIDAD)\n"
                    + "        FROM\n"
                    + "            DETALLEORDENTRABAJO) AS 'Total de productos',\n"
                    + "    ((SUM(CANTIDAD) * 100) / (SELECT \n"
                    + "            SUM(CANTIDAD)\n"
                    + "        FROM\n"
                    + "            DETALLEORDENTRABAJO)) AS 'PORCENTAJE DE VENDIDOS VS TOTAL DE PRODUCTOS'\n"
                    + "FROM\n"
                    + "    detalleordentrabajo\n"
                    + "    WHERE nombreProducto RLIKE ?\n"
                    + "GROUP BY NOMBREPRODUCTO\n"
                    + "ORDER BY SUM(CANTIDAD) DESC;";
            PreparedStatement pst1 = cn.prepareStatement(query1);
            pst1.setString(1, txtNombreProducto.getText());
            ResultSet rs1 = pst1.executeQuery();
            tblProductosVendidos.setModel(DbUtils.resultSetToTableModel(rs1));

        } catch (SQLException ex) {
            Logger.getLogger(Reportes.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBuscarNombreActionPerformed

    private void btnReiniciarNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReiniciarNombreActionPerformed
        try {
            String query1 = "SELECT \n"
                    + "    CODIGOPRODUCTO AS 'Código de Producto',\n"
                    + "    nombreProducto AS 'Nombre de Producto',\n"
                    + "    SUM(CANTIDAD) AS 'Vendidos',\n"
                    + "    (SELECT \n"
                    + "            SUM(CANTIDAD)\n"
                    + "        FROM\n"
                    + "            DETALLEORDENTRABAJO) AS 'Total de productos',\n"
                    + "    ((SUM(CANTIDAD) * 100) / (SELECT \n"
                    + "            SUM(CANTIDAD)\n"
                    + "        FROM\n"
                    + "            DETALLEORDENTRABAJO)) AS 'PORCENTAJE DE VENDIDOS VS TOTAL DE PRODUCTOS'\n"
                    + "FROM\n"
                    + "    detalleordentrabajo\n"
                    + "GROUP BY NOMBREPRODUCTO\n"
                    + "ORDER BY SUM(CANTIDAD) DESC;";
            PreparedStatement pst1 = cn.prepareStatement(query1);
            ResultSet rs1 = pst1.executeQuery();
            tblProductosVendidos.setModel(DbUtils.resultSetToTableModel(rs1));

        } catch (SQLException ex) {
            Logger.getLogger(Reportes.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnReiniciarNombreActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnExportarProductosExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarProductosExcelActionPerformed
        try {

            String ruta = "";

            JFileChooser dlg = new JFileChooser();
            dlg.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int option = dlg.showOpenDialog(this);

            if (option == JFileChooser.APPROVE_OPTION) {
                File f = dlg.getSelectedFile();
                ruta = f.toString();
            }
            //Fecha
            Date sistFecha = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("dd-MMM-YYYY");
            Date sistHora = new Date();
            String pmAm = "hh:mm a";
            SimpleDateFormat format = new SimpleDateFormat(pmAm);
            Calendar hoy = Calendar.getInstance();
            String hora = (String.format(format.format(sistHora), hoy));
            hora = hora.replace(":", "-");

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Productos más vendidos");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.RED.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Create a Row
            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < tblProductosVendidos.getColumnCount(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(tblProductosVendidos.getColumnName(i));
                cell.setCellStyle(headerCellStyle);
            }

            // Create Other rows and cells with contacts data
            int rowNum = 1;

            for (int i = 0; i < tblProductosVendidos.getRowCount(); i++) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(tblProductosVendidos.getValueAt(i, 0).toString());
                row.createCell(1).setCellValue(tblProductosVendidos.getValueAt(i, 1).toString());
                row.createCell(2).setCellValue(tblProductosVendidos.getValueAt(i, 2).toString());
                row.createCell(3).setCellValue(tblProductosVendidos.getValueAt(i, 3).toString());
                row.createCell(4).setCellValue(tblProductosVendidos.getValueAt(i, 4).toString());

            }

            // Resize all columns to fit the content size
            for (int i = 0; i < tblProductosVendidos.getColumnCount(); i++) {
                sheet.autoSizeColumn(i);
            }

            try ( // Write the output to a file
                    FileOutputStream fileOut = new FileOutputStream(ruta + "\\" + "analisis_ventas_productos" + formato.format(sistFecha) + "_hora_" + hora + ".xlsx")) {
                workbook.write(fileOut);
            }
            JOptionPane.showMessageDialog(null, "Documento Creado");
        } catch (HeadlessException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex);
        }
    }//GEN-LAST:event_btnExportarProductosExcelActionPerformed

    private void cmbEjecutivoVentasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbEjecutivoVentasItemStateChanged
        try {
            int codigo = 0;
            String query1 = "SELECT codigo_autorizacion FROM acimabasededatos.usuario WHERE NOMBREUSUARIO = ?;";
            PreparedStatement pst1 = cn.prepareStatement(query1);
            pst1.setString(1, cmbEjecutivoVentas.getSelectedItem().toString());
            ResultSet rs1 = pst1.executeQuery();
            while (rs1.next()) {
                codigo = rs1.getInt(1);
            }
            System.out.println("Codigo: " + codigo);

            String query2 = "SELECT CODIGOORDENCOMPRA AS 'CÓDIGO DE ORDEN DE COMPRA',DEMANDANTE AS 'DEMANDANTE',"
                    + " NOMBRE_PROVEEDOR AS 'EMPRESA',USUARIO.NOMBREUSUARIO AS 'EJECUTIVO' \n"
                    + "FROM ORDENTRABAJO join usuario ON ordentrabajo.codigo_autorizacion = usuario.codigo_autorizacion "
                    + "WHERE usuario.codigo_autorizacion = ? \n"
                    + "group by CODIGOORDENCOMPRA;";
            PreparedStatement pst2 = cn.prepareStatement(query2);
            pst2.setInt(1, codigo);
            ResultSet rs2 = pst2.executeQuery();
            tblVentasEjecutivos.setModel(DbUtils.resultSetToTableModel(rs2));

        } catch (SQLException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cmbEjecutivoVentasItemStateChanged

    private void btnReiniciarEjecutivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReiniciarEjecutivosActionPerformed
        try {
            String query1 = "SELECT CODIGOORDENCOMPRA AS 'CÓDIGO DE ORDEN DE COMPRA',DEMANDANTE AS 'DEMANDANTE',"
                    + " NOMBRE_PROVEEDOR AS 'EMPRESA',USUARIO.NOMBREUSUARIO AS 'EJECUTIVO' \n"
                    + "FROM ORDENTRABAJO join usuario ON ordentrabajo.codigo_autorizacion = usuario.codigo_autorizacion "
                    + "group by CODIGOORDENCOMPRA;";
            PreparedStatement pst1 = cn.prepareStatement(query1);
            ResultSet rs1 = pst1.executeQuery();
            tblVentasEjecutivos.setModel(DbUtils.resultSetToTableModel(rs1));
        } catch (SQLException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnReiniciarEjecutivosActionPerformed

    private void btnExportarEjecutivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarEjecutivoActionPerformed
        try {

            String ruta = "";

            JFileChooser dlg = new JFileChooser();
            dlg.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int option = dlg.showOpenDialog(this);

            if (option == JFileChooser.APPROVE_OPTION) {
                File f = dlg.getSelectedFile();
                ruta = f.toString();
            }
            //Fecha
            Date sistFecha = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("dd-MMM-YYYY");
            Date sistHora = new Date();
            String pmAm = "hh:mm a";
            SimpleDateFormat format = new SimpleDateFormat(pmAm);
            Calendar hoy = Calendar.getInstance();
            String hora = (String.format(format.format(sistHora), hoy));
            hora = hora.replace(":", "-");

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Ventas realizadas por ejecutivo");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.RED.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Create a Row
            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < tblVentasEjecutivos.getColumnCount(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(tblVentasEjecutivos.getColumnName(i));
                cell.setCellStyle(headerCellStyle);
            }

            // Create Other rows and cells with contacts data
            int rowNum = 1;

            for (int i = 0; i < tblVentasEjecutivos.getRowCount(); i++) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(tblVentasEjecutivos.getValueAt(i, 0).toString());
                row.createCell(1).setCellValue(tblVentasEjecutivos.getValueAt(i, 1).toString());
                row.createCell(2).setCellValue(tblVentasEjecutivos.getValueAt(i, 2).toString());
                row.createCell(3).setCellValue(tblVentasEjecutivos.getValueAt(i, 3).toString());

            }

            // Resize all columns to fit the content size
            for (int i = 0; i < tblVentasEjecutivos.getColumnCount(); i++) {
                sheet.autoSizeColumn(i);
            }

            try ( // Write the output to a file
                    FileOutputStream fileOut = new FileOutputStream(ruta + "\\" + "analisis_ventas_ejecutivos" + formato.format(sistFecha) + "_hora_" + hora + ".xlsx")) {
                workbook.write(fileOut);
            }
            JOptionPane.showMessageDialog(null, "Documento Creado");
        } catch (HeadlessException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex);
        }
    }//GEN-LAST:event_btnExportarEjecutivoActionPerformed

    private void tblFacturacionEmpresaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFacturacionEmpresaMouseClicked


    }//GEN-LAST:event_tblFacturacionEmpresaMouseClicked

    private void txtFiltrarNombreInventarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltrarNombreInventarioKeyPressed
        try {
            String queryProducto = "SELECT \n"
                    + "    idProducto AS 'ID producto',\n"
                    + "    SKU,\n"
                    + "    categoria AS 'Categoría',\n"
                    + "    nombreProducto AS 'Producto',\n"
                    + "    FORMAT(precioVenta, 'es_CL') AS 'Precio Venta',\n"
                    + "    FORMAT(precioCosto, 'es_CL') AS 'Precio Costo',\n"
                    + "    stock AS 'Stock'\n"
                    + "FROM\n"
                    + "    inventario r\n"
                    + "        LEFT JOIN\n"
                    + "    distribuidor d ON r.IDDISTRIBUIDOR = d.idDistribuidor "
                    + "    where r.nombreProducto RLIKE ?;";
            String param = txtFiltrarNombreInventario.getText();
            PreparedStatement pst = cn.prepareStatement(queryProducto);
            pst.setString(1, param);
            ResultSet rs = (ResultSet) pst.executeQuery();
            tblProductoInventarioBodega.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
        }
    }//GEN-LAST:event_txtFiltrarNombreInventarioKeyPressed

    private void btnBuscarNombre2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarNombre2ActionPerformed

        try {
            String queryProducto = "SELECT \n"
                    + "    idProducto AS 'ID producto',\n"
                    + "    SKU,\n"
                    + "    categoria AS 'Categoría',\n"
                    + "    nombreProducto AS 'Producto',\n"
                    + "    FORMAT(precioVenta, 'es_CL') AS 'Precio Venta',\n"
                    + "    FORMAT(precioCosto, 'es_CL') AS 'Precio Costo',\n"
                    + "    stock AS 'Stock'\n"
                    + "FROM\n"
                    + "    inventario r\n"
                    + "        LEFT JOIN\n"
                    + "    distribuidor d ON r.IDDISTRIBUIDOR = d.idDistribuidor "
                    + "    where r.nombreProducto RLIKE ?;";
            String param = txtFiltrarNombreInventario.getText();
            PreparedStatement pst = cn.prepareStatement(queryProducto);
            pst.setString(1, param);
            ResultSet rs = (ResultSet) pst.executeQuery();
            tblProductoInventarioBodega.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnBuscarNombre2ActionPerformed

    private void txtSKUInventarioBodegaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSKUInventarioBodegaKeyPressed
        try {
            String queryProducto = "SELECT \n"
                    + "    idProducto AS 'ID producto',\n"
                    + "    SKU,\n"
                    + "    categoria AS 'Categoría',\n"
                    + "    nombreProducto AS 'Producto',\n"
                    + "    FORMAT(precioVenta, 'es_CL') AS 'Precio Venta',\n"
                    + "    FORMAT(precioCosto, 'es_CL') AS 'Precio Costo',\n"
                    + "    stock AS 'Stock'\n"
                    + "FROM\n"
                    + "    inventario r\n"
                    + "        LEFT JOIN\n"
                    + "    distribuidor d ON r.IDDISTRIBUIDOR = d.idDistribuidor where r.sku RLIKE ?;";
            String param = txtSKUInventarioBodega.getText();
            PreparedStatement pst = cn.prepareStatement(queryProducto);
            pst.setString(1, param);
            ResultSet rs = (ResultSet) pst.executeQuery();
            tblProductoInventarioBodega.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (Exception ex) {
            // JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
        }
    }//GEN-LAST:event_txtSKUInventarioBodegaKeyPressed
    private void btnBuscarSKU2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarSKU2ActionPerformed

        try {
            String queryProducto = "SELECT \n"
                    + "    idProducto AS 'ID producto',\n"
                    + "    SKU,\n"
                    + "    categoria AS 'Categoría',\n"
                    + "    nombreProducto AS 'Producto',\n"
                    + "    FORMAT(precioVenta, 'es_CL') AS 'Precio Venta',\n"
                    + "    FORMAT(precioCosto, 'es_CL') AS 'Precio Costo',\n"
                    + "    stock AS 'Stock'\n"
                    + "FROM\n"
                    + "    inventario r\n"
                    + "        LEFT JOIN\n"
                    + "    distribuidor d ON r.IDDISTRIBUIDOR = d.idDistribuidor where r.sku RLIKE ?;";
            String param = txtSKUInventarioBodega.getText();
            PreparedStatement pst = cn.prepareStatement(queryProducto);
            pst.setString(1, param);
            ResultSet rs = (ResultSet) pst.executeQuery();
            tblProductoInventarioBodega.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnBuscarSKU2ActionPerformed
    private void btnBuscarStatus1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarStatus1ActionPerformed

        try {
            String queryProducto = "SELECT \n"
                    + "    idProducto AS 'ID producto',\n"
                    + "    SKU,\n"
                    + "    categoria AS 'Categoría',\n"
                    + "    nombreProducto AS 'Producto',\n"
                    + "    FORMAT(precioVenta, 'es_CL') AS 'Precio Venta',\n"
                    + "    FORMAT(precioCosto, 'es_CL') AS 'Precio Costo',\n"
                    + "    stock AS 'Stock'\n"
                    + "FROM\n"
                    + "    inventario r\n"
                    + "        LEFT JOIN\n"
                    + "    distribuidor d ON r.IDDISTRIBUIDOR = d.idDistribuidor WHERE  r.statusproducto RLIKE ?;";
            String param = cmbStatusFiltrarInventario.getSelectedItem().toString();
            PreparedStatement pst = cn.prepareStatement(queryProducto);
            pst.setString(1, param);
            ResultSet rs = (ResultSet) pst.executeQuery();
            tblProductoInventarioBodega.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnBuscarStatus1ActionPerformed
    private void ReiniciarTablaProductos(JTable tblProducto) {
        try {
            String queryProducto = "SELECT \n"
                    + "    idProducto AS 'ID producto',\n"
                    + "    SKU,\n"
                    + "    categoria AS 'Categoría',\n"
                    + "    nombreProducto AS 'Producto',\n"
                    + "    FORMAT(precioVenta, 'es_CL') AS 'Precio Venta',\n"
                    + "    FORMAT(precioCosto, 'es_CL') AS 'Precio Costo',\n"
                    + "    stock AS 'Stock'\n"
                    + "FROM\n"
                    + "    inventario r\n"
                    + "        LEFT JOIN\n"
                    + "    distribuidor d ON r.IDDISTRIBUIDOR = d.idDistribuidor;";
            PreparedStatement pstProducto = cn.prepareStatement(queryProducto);
            ResultSet rsProducto = pstProducto.executeQuery();
            tblProductoInventarioBodega.setModel(DbUtils.resultSetToTableModel(rsProducto));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    private void btnReiniciarTablaProducto5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReiniciarTablaProducto5ActionPerformed
        ReiniciarTablaProductos(tblProductoInventarioBodega);
    }//GEN-LAST:event_btnReiniciarTablaProducto5ActionPerformed
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {

            String ruta = "";

            JFileChooser dlg = new JFileChooser();
            dlg.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int option = dlg.showOpenDialog(this);

            if (option == JFileChooser.APPROVE_OPTION) {
                File f = dlg.getSelectedFile();
                ruta = f.toString();
            }

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Inventario");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.RED.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Create a Row
            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < tblProductoInventarioBodega.getColumnCount(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(tblProductoInventarioBodega.getColumnName(i));
                cell.setCellStyle(headerCellStyle);
            }

            // Create Other rows and cells with contacts data
            int rowNum = 1;

            for (int i = 0; i < tblProductoInventarioBodega.getRowCount(); i++) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(tblProductoInventarioBodega.getValueAt(i, 0).toString());
                row.createCell(1).setCellValue(tblProductoInventarioBodega.getValueAt(i, 1).toString());
                row.createCell(2).setCellValue(tblProductoInventarioBodega.getValueAt(i, 2).toString());
                row.createCell(3).setCellValue(tblProductoInventarioBodega.getValueAt(i, 3).toString());
                row.createCell(4).setCellValue(tblProductoInventarioBodega.getValueAt(i, 4).toString());
                row.createCell(5).setCellValue(tblProductoInventarioBodega.getValueAt(i, 5).toString());
                row.createCell(6).setCellValue(tblProductoInventarioBodega.getValueAt(i, 6).toString());

            }

            // Resize all columns to fit the content size
            for (int i = 0; i < tblProductoInventarioBodega.getColumnCount(); i++) {
                sheet.autoSizeColumn(i);
            }

            try ( // Write the output to a file
                    FileOutputStream fileOut = new FileOutputStream(ruta + "\\" + "inventario_productos.xlsx")) {
                workbook.write(fileOut);
            }
            JOptionPane.showMessageDialog(null, "Documento Creado");
        } catch (HeadlessException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed
    private void cmbDistribuidorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbDistribuidorItemStateChanged
        try {
            String query1 = "SELECT \n"
                    + "    ot.CODIGOORDENCOMPRA AS 'CÓDIGO DE ORDEN DE COMPRA',\n"
                    + "    DEMANDANTE AS 'DEMANDANTE',\n"
                    + "    NOMBRE_PROVEEDOR AS 'EMPRESA',\n"
                    + "    dot.moneda AS 'MONEDA',\n"
                    + "    CASE\n"
                    + "        WHEN dot.moneda = 'USD' THEN REPLACE(IFNULL(total, 0), '$', '')\n"
                    + "        WHEN\n"
                    + "            dot.moneda = 'CLP'\n"
                    + "        THEN\n"
                    + "            REPLACE(REPLACE(IFNULL(total, 0), '$', ''),\n"
                    + "                '.',\n"
                    + "                '')\n"
                    + "        ELSE 0\n"
                    + "    END AS 'TOTAL'\n"
                    + "FROM\n"
                    + "    ORDENTRABAJO ot\n"
                    + "        JOIN\n"
                    + "    detalleordentrabajo dot ON ot.idOrden = dot.idOrden\n"
                    + "WHERE\n"
                    + "    NOMBRE_PROVEEDOR = ?\n"
                    + "GROUP BY ot.CODIGOORDENCOMPRA;";
            PreparedStatement pst1 = cn.prepareStatement(query1);
            pst1.setString(1, cmbDistribuidor.getSelectedItem().toString());
            ResultSet rs1 = pst1.executeQuery();
            tblFacturacionEmpresa.setModel(DbUtils.resultSetToTableModel(rs1));
        } catch (SQLException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Camino alternativo
        double facturacionDolares = 0;
        double facturacionPesos = 0;
        double facturacionTotal = 0;
        String moneda = "";
        for (int i = 0; i < tblFacturacionEmpresa.getRowCount(); i++) {
            moneda = tblFacturacionEmpresa.getValueAt(i, 3).toString();
            System.out.println("Moneda: " + moneda);
            double valor = 0;
            double dolares = Double.parseDouble(lblValorDolar.getText().substring(1));
            if (moneda.equals("USD")) {
                if (tblFacturacionEmpresa.getValueAt(i, 4).toString().isEmpty()) {
                    valor = valor + 0;
                    valor = valor * dolares;
                    facturacionDolares = facturacionDolares + valor;
                } else {
                    valor = Double.parseDouble(tblFacturacionEmpresa.getValueAt(i, 4).toString());
                    valor = valor * dolares;
                    facturacionDolares = facturacionDolares + valor;
                }
            } else {
                if (tblFacturacionEmpresa.getValueAt(i, 4).toString().isEmpty()) {
                    valor = valor + 0;

                    facturacionPesos = facturacionPesos + valor;
                } else {
                    valor = Double.parseDouble(tblFacturacionEmpresa.getValueAt(i, 4).toString());

                    facturacionPesos = facturacionPesos + valor;
                }
            }
        }
        System.out.println("Total: " + facturacionDolares);
        System.out.println("Total: " + facturacionPesos);
        facturacionTotal = facturacionDolares + facturacionPesos;
        System.out.println("Facturacion Total: " + facturacionTotal);

        /*
        try {

            String total = "";
            String query1 = "SELECT\n"
                    + "SUM(REPLACE(REPLACE(total, '$', ''),\n"
                    + "        '.',\n"
                    + "        ''))\n"
                    + "FROM\n"
                    + "    acimabasededatos.ordentrabajo\n"
                    + "    WHERE NOMBRE_PROVEEDOR = ?\n"
                    + "GROUP BY NOMBRE_PROVEEDOR;";
            PreparedStatement pst1 = cn.prepareStatement(query1);
            pst1.setString(1, cmbDistribuidor.getSelectedItem().toString());
            ResultSet rs1 = pst1.executeQuery();
            while (rs1.next()) {
                total = rs1.getString(1);
            }
            System.out.println("total:" + total);
            DecimalFormat formatea = new DecimalFormat("###,###.##");
            int totalFormat = 0;
            if (total != "") {
                totalFormat = Integer.parseInt(total);
            } else {
                totalFormat = 0;
            }

            lblFacturacion.setText("$" + formatea.format(totalFormat));

        } catch (SQLException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {

            String total = "";
            String query1 = "SELECT\n"
                    + "SUM(REPLACE(REPLACE(total, '$', ''),\n"
                    + "        '.',\n"
                    + "        ''))\n"
                    + "FROM\n"
                    + "    acimabasededatos.ordentrabajo;";
            PreparedStatement pst1 = cn.prepareStatement(query1);
            ResultSet rs1 = pst1.executeQuery();
            while (rs1.next()) {
                total = rs1.getString(1);
            }
            System.out.println("total:" + total);
            DecimalFormat formatea = new DecimalFormat("###,###.##");
            int totalFormat = 0;
            if (total != "") {
                totalFormat = Integer.parseInt(total);
            } else {
                totalFormat = 0;
            }

            lblTotalFacturacion.setText("$" + formatea.format(totalFormat));

        } catch (SQLException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         */

    }//GEN-LAST:event_cmbDistribuidorItemStateChanged

    private void tabPanelElementosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabPanelElementosMouseClicked
        if (lblValorDolar.getText().equals("$0")) {
            String respuesta = JOptionPane.showInputDialog(null, "Ingrese valor del dolar", "", JOptionPane.QUESTION_MESSAGE);
            double valorDolar = Double.parseDouble(respuesta);
            lblValorDolar.setText("$" + valorDolar);
        } else {
            //hacer nada
        }
    }//GEN-LAST:event_tabPanelElementosMouseClicked

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
            java.util.logging.Logger.getLogger(Reportes.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reportes.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reportes.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reportes.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reportes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarNombre;
    private javax.swing.JButton btnBuscarNombre2;
    private javax.swing.JButton btnBuscarSKU2;
    private javax.swing.JButton btnBuscarStatus1;
    private javax.swing.JButton btnExportarEjecutivo;
    private javax.swing.JButton btnExportarProductosExcel;
    private javax.swing.JButton btnReiniciarEjecutivos;
    private javax.swing.JButton btnReiniciarID;
    private javax.swing.JButton btnReiniciarNombre;
    private javax.swing.JButton btnReiniciarTablaProducto5;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> cmbDistribuidor;
    private javax.swing.JComboBox<String> cmbEjecutivoVentas;
    private javax.swing.JComboBox<String> cmbStatusFiltrarInventario;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane7;
    private javax.swing.JTabbedPane jTabbedPane8;
    private javax.swing.JLabel lblFacturacion;
    private javax.swing.JLabel lblTotalFacturacion;
    private javax.swing.JLabel lblValorDolar;
    private javax.swing.JPanel panelProductos;
    private javax.swing.JPanel panelVentasEjecutivo;
    private javax.swing.JTabbedPane tabPanelElementos;
    private javax.swing.JTable tblFacturacionEmpresa;
    public javax.swing.JTable tblProductoInventarioBodega;
    private javax.swing.JTable tblProductosVendidos;
    private javax.swing.JTable tblVentasEjecutivos;
    private javax.swing.JTextField txtFiltrarNombreInventario;
    private javax.swing.JTextField txtIDProducto;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txtSKUInventarioBodega;
    // End of variables declaration//GEN-END:variables
}
