/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PlataformaVentas;

import clases.Conexion;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Diego Alfaro Fierro, Diego González Romàn
 */
public class BuscarProducto extends javax.swing.JFrame {

    Conexion con = new Conexion();
    Connection cn = con.conecta();
    private final DefaultTableModel tableModel = new DefaultTableModel();
    private double[][] data;
    DefaultTableModel model;
    protected boolean bottom = true;
    protected boolean top = true;

    private void ocultarColumna() {
        tblResultado.getColumnModel().getColumn(0).setMaxWidth(0);
        tblResultado.getColumnModel().getColumn(0).setMinWidth(0);
        tblResultado.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        tblResultado.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
    }

    private String consultaBusqueda = "SELECT p.IDProducto as 'ID de Producto',\n"
            + "p.SKU as 'SKU',\n"
            + "cm.NombreConvenio as 'Nombre de Convenio Marco',\n"
            + "p.producto as 'Nombre de Producto',\n"
            + "p.categoria as 'Categoria',\n"
            + "dis.nombreDistribuidor 'Distribuidor',\n"
            + "p.precioVenta as 'Precio de Venta',\n"
            + "p.StatusProducto as 'Status'\n"
            + "FROM producto p \n"
            + "Join distribuidor dis ON p.idDistribuidor = dis.idDistribuidor\n"
            + "left JOIN convenioMarco cm ON p.codigoConvenio = cm.codigoConvenio\n";

    /**
     * Creates new form Cotizador
     */
    public void llenarTabla() {

        try {
            String query2 = "Select * from convenioMarco";
            PreparedStatement pst2;
            pst2 = cn.prepareStatement(query2);
            ResultSet rs = pst2.executeQuery();
            tblResultado.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
        }
    }

    public BuscarProducto() {
        initComponents();

        jPanel9.setBackground(new Color(0, 0, 0, 30));

        llenarTabla();
        lblUsuario.setVisible(false);
        try {
            String query = consultaBusqueda
                    + "ORDER BY p.idProducto";
            PreparedStatement pst = cn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            tblResultado.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
        }
        try {
            String query = "select NombreConvenio from conveniomarco order by CodigoConvenio desc";
            PreparedStatement pst = cn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                cmbCM.addItem(rs.getString(1));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
        }

        try {
            String query = "Select categoria from producto group by categoria";
            PreparedStatement pst = cn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                cmbCategoria.addItem(rs.getString(1));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
        }
        try {
            String query = "Select nombreDistribuidor from distribuidor";
            PreparedStatement pst = cn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                cmbDistribuidor.addItem(rs.getString(1));
                cmbDistribuidor1.addItem(rs.getString(1));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
        }

        Date sistFecha = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MMM/YYYY");
        txtFecha.setText(formato.format(sistFecha));
        lblUsuario.setVisible(false);
        btnGuardaPDF.setEnabled(true);
        btnCalcular.setEnabled(false);
        btnBorrarProducto.setEnabled(false);
        ocultarColumna();
    }

    public PdfPCell getCell(String text, Font font, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    public static PdfPCell createImageCell(String path) throws DocumentException, IOException {
        Image img = Image.getInstance(path);
        PdfPCell cell = new PdfPCell(img, true);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    public void tableLayout(PdfPTable table, float[][] width, float[] height,
            int headerRows, int rowStart, PdfContentByte[] canvas) {
        float widths[] = width[0];
        float y1 = height[0];
        float y2 = height[height.length - 1];
        float x1 = widths[0];
        float x2 = widths[widths.length - 1];
        PdfContentByte cb = canvas[PdfPTable.LINECANVAS];
        cb.moveTo(x1, y1);
        cb.lineTo(x1, y2);
        cb.moveTo(x2, y1);
        cb.lineTo(x2, y2);
        if (top) {
            cb.moveTo(x1, y1);
            cb.lineTo(x2, y1);
        }
        if (bottom) {
            cb.moveTo(x1, y2);
            cb.lineTo(x2, y2);
        }
        cb.stroke();
        cb.resetRGBColorStroke();
        bottom = true;
        top = true;
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
        jPanel9 = new javax.swing.JPanel();
        lblIDUsuario = new javax.swing.JLabel();
        lblIDUsuario1 = new javax.swing.JLabel();
        lblIDCliente = new javax.swing.JLabel();
        lblUsuario1 = new javax.swing.JLabel();
        lblCorreoUsuario = new javax.swing.JLabel();
        lblDescuento = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lbCodigo = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        btnVolver = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        panelProductos = new javax.swing.JPanel();
        lblRut = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        btnRealizarCotizacion = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblResultado = new javax.swing.JTable();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbCM = new javax.swing.JComboBox<String>();
        jPanel2 = new javax.swing.JPanel();
        lblNombreProducto = new javax.swing.JLabel();
        txtNombreProducto = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtIDProducto = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtSKU = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        cmbCategoria = new javax.swing.JComboBox<String>();
        jPanel8 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        cmbDistribuidor = new javax.swing.JComboBox<String>();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelCotizacion = new javax.swing.JPanel();
        panelDatos = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblCorreo = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblCorporacion = new javax.swing.JLabel();
        lblCargo = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblRazon = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblUnidadDeCompra = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblRegion = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        lblCiudad = new javax.swing.JLabel();
        lblComuna = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        txtRutCotizacion = new javax.swing.JFormattedTextField();
        btnGuardaPDF = new javax.swing.JButton();
        btnCalcular = new javax.swing.JButton();
        btnBorrarProducto = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblCotizacion_2 = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        lblNetoTotal = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        lblIVATotal = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        lblBrutoTotal = new javax.swing.JLabel();
        panel_oculto = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblCotizacion = new javax.swing.JTable();
        jLabel25 = new javax.swing.JLabel();
        cmbDistribuidor1 = new javax.swing.JComboBox();
        lblCredencial = new javax.swing.JLabel();
        lblCredencial1 = new javax.swing.JLabel();
        lblFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Realizar Cotización");
        setBackground(java.awt.Color.white);
        setMaximumSize(new java.awt.Dimension(1280, 686));
        setMinimumSize(new java.awt.Dimension(1280, 686));
        setResizable(false);

        jLayeredPane1.setMaximumSize(new java.awt.Dimension(1280, 686));
        jLayeredPane1.setMinimumSize(new java.awt.Dimension(1280, 686));

        lblIDUsuario.setVisible(false);

        lblIDUsuario1.setVisible(false);

        lblIDCliente.setVisible(false);

        lblUsuario1.setVisible(false);

        lblCorreoUsuario.setVisible(false);

        lblDescuento.setVisible(false);

        lblTotal.setVisible(false);
        lblTotal.setFont(new java.awt.Font("Tahoma", 0, 1)); // NOI18N

        lbCodigo.setVisible(false);
        lbCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel17.setVisible(false);

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PlataformaVentas/Imagenes/acima-logo-200p.png"))); // NOI18N

        jPanel10.setBackground(new java.awt.Color(219, 219, 219));

        btnVolver.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panelProductos.setBackground(new java.awt.Color(219, 219, 219));
        panelProductos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblRut.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        lblRut.setToolTipText("");

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel1.setText("Cotización para:");

        lblUsuario.setVisible(false);

        btnRealizarCotizacion.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        btnRealizarCotizacion.setText("Agregar a Cotización");
        btnRealizarCotizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRealizarCotizacionActionPerformed(evt);
            }
        });

        tblResultado = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int celIndex){
                return false;
            }
        };
        tblResultado.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        tblResultado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Convenio Marco", "Convenio Marco ID", "Nombre Genérico", "SKU", "Producto", "Precio", "Descripción", "Regiones", "Condicion de Despacho (%)", "Días Hábiles", "Link Del Producto", "Status", "Stock de Productos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblResultado);

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setAutoscrolls(true);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setLabelFor(cmbCM);
        jLabel2.setText("Buscando:");

        cmbCM.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        cmbCM.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todos los Productos" }));
        cmbCM.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCMItemStateChanged(evt);
            }
        });
        cmbCM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCMActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel2)
                .addGap(30, 30, 30)
                .addComponent(cmbCM, javax.swing.GroupLayout.PREFERRED_SIZE, 615, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(368, 368, 368))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbCM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
        );

        jTabbedPane1.addTab("Buscar Por Convenio Marco", jPanel1);

        lblNombreProducto.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lblNombreProducto.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNombreProducto.setLabelFor(cmbCM);
        lblNombreProducto.setText("Buscando:");

        txtNombreProducto.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        txtNombreProducto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNombreProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreProductoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblNombreProducto)
                .addGap(30, 30, 30)
                .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(480, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreProducto)
                    .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
        );

        jTabbedPane1.addTab("Buscar Por Nombre", jPanel2);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel3.setText("Buscando:");

        txtIDProducto.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        txtIDProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIDProductoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel3)
                .addGap(30, 30, 30)
                .addComponent(txtIDProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(480, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIDProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
        );

        jTabbedPane1.addTab("Buscar Por ID", jPanel3);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel4.setText("Buscando:");

        txtSKU.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        txtSKU.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSKUKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel4)
                .addGap(30, 30, 30)
                .addComponent(txtSKU, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(480, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSKU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
        );

        jTabbedPane1.addTab("Buscar Por SKU", jPanel5);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel13.setText("Buscando:");

        cmbCategoria.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        cmbCategoria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todos los Productos" }));
        cmbCategoria.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCategoriaItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel13)
                .addGap(30, 30, 30)
                .addComponent(cmbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 615, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(265, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
        );

        jTabbedPane1.addTab("Buscar Por Categoría", jPanel7);

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel15.setText("Buscando:");

        cmbDistribuidor.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        cmbDistribuidor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todos los Productos" }));
        cmbDistribuidor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbDistribuidorItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel15)
                .addGap(30, 30, 30)
                .addComponent(cmbDistribuidor, javax.swing.GroupLayout.PREFERRED_SIZE, 615, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(265, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbDistribuidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
        );

        jTabbedPane1.addTab("Buscar Por Distibuidor", jPanel8);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 51, 51));
        jLabel9.setText("<html> <p align=\"left\">Envio a todas las regiones<br/>\nCondición de despacho: 0%</p>  </html>");

        javax.swing.GroupLayout panelProductosLayout = new javax.swing.GroupLayout(panelProductos);
        panelProductos.setLayout(panelProductosLayout);
        panelProductosLayout.setHorizontalGroup(
            panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductosLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRealizarCotizacion)
                    .addGroup(panelProductosLayout.createSequentialGroup()
                        .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(panelProductosLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblRut, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1030, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        panelProductosLayout.setVerticalGroup(
            panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblRut, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRealizarCotizacion)
                .addGap(10, 10, 10))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Buscar Por Distribuidor");

        jScrollPane5.setViewportView(panelProductos);

        jTabbedPane2.addTab("Buscar Productos", jScrollPane5);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panelCotizacion.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelDatos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel5.setText("Nombre");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 13, -1, -1));

        lblNombre.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jPanel4.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 13, 265, 26));

        lblCorreo.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jPanel4.add(lblCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 45, 265, 26));

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel6.setText("Correo");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 45, -1, -1));

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel7.setText("Entidad");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 77, -1, -1));

        lblCorporacion.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jPanel4.add(lblCorporacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 77, 265, 26));

        lblCargo.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jPanel4.add(lblCargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 109, 265, 26));

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel8.setText("Cargo");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 109, -1, -1));

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel10.setText("Razón Social");
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 141, -1, -1));

        lblRazon.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jPanel4.add(lblRazon, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 141, 265, 26));

        jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel12.setText("U. de Compra");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 173, -1, -1));

        lblUnidadDeCompra.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jPanel4.add(lblUnidadDeCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 173, 265, 26));

        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel14.setText("Telefono");
        jPanel6.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));

        jLabel16.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel16.setText("Región");
        jPanel6.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 43, -1, -1));

        lblRegion.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jPanel6.add(lblRegion, new org.netbeans.lib.awtextra.AbsoluteConstraints(194, 43, 290, 26));

        lblTelefono.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jPanel6.add(lblTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(194, 11, 290, 26));

        jLabel20.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel20.setText("Ciudad");
        jPanel6.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 75, -1, -1));

        lblCiudad.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jPanel6.add(lblCiudad, new org.netbeans.lib.awtextra.AbsoluteConstraints(194, 75, 290, 26));

        lblComuna.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jPanel6.add(lblComuna, new org.netbeans.lib.awtextra.AbsoluteConstraints(194, 107, 290, 26));

        jLabel21.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel21.setText("Direccion");
        jPanel6.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 139, -1, -1));

        lblDireccion.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jPanel6.add(lblDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(194, 139, 290, 26));

        jLabel18.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel18.setText("Comuna");
        jPanel6.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 107, -1, -1));

        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel11.setText("Fecha de Cotización");
        jPanel6.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 173, -1, -1));

        txtFecha.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jPanel6.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(194, 171, 290, 28));

        jPanel4.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(464, 13, -1, -1));

        panelDatos.addTab("Informacion de Cliente", jPanel4);

        panelCotizacion.add(panelDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 1160, 250));

        btnBuscar.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        panelCotizacion.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, -1, -1));

        try {
            txtRutCotizacion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###-H")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtRutCotizacion.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        txtRutCotizacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRutCotizacionKeyPressed(evt);
            }
        });
        panelCotizacion.add(txtRutCotizacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 232, -1));

        btnGuardaPDF.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        btnGuardaPDF.setText("Guardar Cotización y generar PDF");
        btnGuardaPDF.setActionCommand("");
        btnGuardaPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardaPDFActionPerformed(evt);
            }
        });
        panelCotizacion.add(btnGuardaPDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 570, -1, -1));

        btnCalcular.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        btnCalcular.setText("Calcular Precios");
        btnCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularActionPerformed(evt);
            }
        });
        panelCotizacion.add(btnCalcular, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 570, -1, -1));

        btnBorrarProducto.setFont(new java.awt.Font("sansserif", 0, 20)); // NOI18N
        btnBorrarProducto.setText("Borrar Producto");
        btnBorrarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarProductoActionPerformed(evt);
            }
        });
        panelCotizacion.add(btnBorrarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 570, 239, -1));

        tblCotizacion_2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID de Producto", "Convenio Marco", "Nombre Producto", "SKU", "Precio", "Cantidad", "Neto", "Total"
            }
        ));
        tblCotizacion_2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCotizacion_2MouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblCotizacion_2);

        panelCotizacion.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 1170, 200));

        jPanel11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel23.setText("Neto: $");

        lblNetoTotal.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblNetoTotal.setText("0");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel22.setText("IVA:   $");

        lblIVATotal.setVisible(true);
        lblIVATotal.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblIVATotal.setText("0");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel24.setText("Total: $");

        lblBrutoTotal.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblBrutoTotal.setText("0");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBrutoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIVATotal, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                    .addComponent(lblNetoTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(lblNetoTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(lblIVATotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(lblBrutoTotal))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        panelCotizacion.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 570, 380, 120));

        panel_oculto.setVisible(false);

        jScrollPane3.setMaximumSize(new java.awt.Dimension(1280, 740));
        jScrollPane3.setPreferredSize(new java.awt.Dimension(1280, 740));

        tblCotizacion = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int celIndex){
                return false;
            }
        };
        tblCotizacion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tblCotizacion.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        tblCotizacion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID de Producto", "Convenio Marco", "Nombre Producto", "SKU", "Precio", "Cantidad", "Neto", "IVA", "Cargo", "Descuento", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, false, false, false, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblCotizacion);
        if (tblCotizacion.getColumnModel().getColumnCount() > 0) {
            tblCotizacion.getColumnModel().getColumn(7).setHeaderValue("IVA");
        }

        javax.swing.GroupLayout panel_ocultoLayout = new javax.swing.GroupLayout(panel_oculto);
        panel_oculto.setLayout(panel_ocultoLayout);
        panel_ocultoLayout.setHorizontalGroup(
            panel_ocultoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ocultoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_ocultoLayout.setVerticalGroup(
            panel_ocultoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_ocultoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelCotizacion.add(panel_oculto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 620, -1, -1));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel25.setText("Distribuidor:");
        panelCotizacion.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 30, -1, -1));

        cmbDistribuidor1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        cmbDistribuidor1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione Distribuidor" }));
        panelCotizacion.add(cmbDistribuidor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 320, -1));

        jScrollPane1.setViewportView(panelCotizacion);

        jTabbedPane2.addTab("Generar Cotización", jScrollPane1);

        jScrollPane4.setViewportView(jTabbedPane2);

        lblCredencial.setVisible(false);
        lblCredencial.setText("jLabel3");

        lblCredencial1.setVisible(false);
        lblCredencial1.setText("jLabel4");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1206, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(lblCredencial1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCredencial)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 288, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCredencial)
                            .addComponent(lblCredencial1))
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(1259, 1259, 1259)
                .addComponent(lblCorreoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblIDUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(166, 166, 166)
                .addComponent(jLabel17)
                .addGap(1747, 1747, 1747)
                .addComponent(lblIDUsuario1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(lblIDCliente))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(0, 403, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lbCodigo)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addGap(490, 490, 490)
                            .addComponent(lblTotal))
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addGap(544, 544, 544)
                            .addComponent(lblUsuario1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addGap(1110, 1110, 1110)
                            .addComponent(lblDescuento)))
                    .addGap(0, 493, Short.MAX_VALUE)))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(lblIDUsuario1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(831, 831, 831))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel17)))
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(lblIDCliente)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIDUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCorreoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))))
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(0, 137, Short.MAX_VALUE)
                    .addComponent(lbCodigo)
                    .addGap(33, 33, 33)
                    .addComponent(lblUsuario1, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(368, 368, 368)
                    .addComponent(lblDescuento)
                    .addGap(260, 260, 260)
                    .addComponent(lblTotal)
                    .addGap(0, 156, Short.MAX_VALUE)))
        );

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PlataformaVentas/Imagenes/BackgroundNew.png"))); // NOI18N
        lblFondo.setToolTipText("");
        lblFondo.setMinimumSize(new java.awt.Dimension(0, 0));
        lblFondo.setPreferredSize(new java.awt.Dimension(100, 100));

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 1244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblFondo, javax.swing.GroupLayout.DEFAULT_SIZE, 1280, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblFondo, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE))
        );
        jLayeredPane1.setLayer(jPanel9, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lblFondo, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    public void BorrarColumnas() {
        DefaultTableModel modeloNuevo = (DefaultTableModel) tblCotizacion.getModel();
        tblCotizacion_2.setModel(modeloNuevo);
        tblCotizacion_2.removeColumn(tblCotizacion_2.getColumnModel().getColumn(7));
        tblCotizacion_2.removeColumn(tblCotizacion_2.getColumnModel().getColumn(8));
        tblCotizacion_2.removeColumn(tblCotizacion_2.getColumnModel().getColumn(9));
        System.out.println("se han quitado columnas");
    }

    private void btnRealizarCotizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRealizarCotizacionActionPerformed

        try {
            TableModel cotizacion = tblResultado.getModel();
            int indexs[] = tblResultado.getSelectedRows();

            Object[] row = new Object[15];

            lblUsuario.setText(lblUsuario.getText());
            lblIDUsuario.setText(lblIDUsuario.getText());
            txtRutCotizacion.setText(lblRut.getText());
            lblCredencial.setText(lblCredencial.getText());
            lblIDUsuario.setText(lblIDUsuario.getText());
            DefaultTableModel modeloNuevo = (DefaultTableModel) tblCotizacion.getModel();
            TableModel modelo = tblResultado.getModel();
            int selectedRows[] = tblResultado.getSelectedRows();

            for (int i = 0; i < indexs.length; i++) {
                row[0] = cotizacion.getValueAt(indexs[i], 0);
                row[1] = cotizacion.getValueAt(indexs[i], 2);
                row[2] = cotizacion.getValueAt(indexs[i], 3);
                row[3] = cotizacion.getValueAt(indexs[i], 1);

                int row4 = 0;
                row4 = Integer.parseInt(cotizacion.getValueAt(indexs[i], 6).toString());
                String rows = java.text.NumberFormat.getCurrencyInstance().format(row4);
                System.out.println(rows);
                row[4] = rows;
                modeloNuevo.addRow(row);
            }

            JOptionPane.showMessageDialog(null, "Producto agregado a Cotizacion");
            btnBorrarProducto.setEnabled(true);
            //Se agrega el modelo nuevo
            tblCotizacion_2.setModel(modeloNuevo);
            System.out.println(tblCotizacion.getColumnCount());

            if (tblCotizacion_2.getColumnCount() >= 10) {
                System.out.println("Se han quitado columnas");
                tblCotizacion_2.removeColumn(tblCotizacion_2.getColumnModel().getColumn(9));
                tblCotizacion_2.removeColumn(tblCotizacion_2.getColumnModel().getColumn(8));
                tblCotizacion_2.removeColumn(tblCotizacion_2.getColumnModel().getColumn(7));
            } else {
                System.out.println("No se han quitado columnas");
            }

        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnRealizarCotizacionActionPerformed

    private void txtSKUKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSKUKeyPressed
        try {
            String query = consultaBusqueda
                    + "WHERE p.SKU LIKE ? ORDER BY p.idProducto";
            String param = txtSKU.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, "%" + param + "%");
            ResultSet rs = pst.executeQuery();
            tblResultado.setModel(DbUtils.resultSetToTableModel(rs));
            ocultarColumna();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
        }

    }//GEN-LAST:event_txtSKUKeyPressed

    private void txtIDProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDProductoKeyPressed
        try {
            String query = consultaBusqueda
                    + "WHERE p.idProducto LIKE ? ORDER BY p.idProducto";
            String param = txtIDProducto.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, "%" + param + "%");
            ResultSet rs = pst.executeQuery();
            tblResultado.setModel(DbUtils.resultSetToTableModel(rs));
            ocultarColumna();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
        }
    }//GEN-LAST:event_txtIDProductoKeyPressed

    private void txtNombreProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProductoKeyPressed
        try {
            String query = consultaBusqueda
                    + "WHERE p.producto LIKE ? ORDER BY p.idProducto";
            String param = txtNombreProducto.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, "%" + param + "%");
            ResultSet rs = pst.executeQuery();
            tblResultado.setModel(DbUtils.resultSetToTableModel(rs));
            ocultarColumna();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
        }

    }//GEN-LAST:event_txtNombreProductoKeyPressed

    private void cmbCMItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCMItemStateChanged

    }//GEN-LAST:event_cmbCMItemStateChanged

    private void cmbCMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCMActionPerformed
        try {
            if (cmbCM.getSelectedItem().toString() == "Todos los Productos") {
                String query = consultaBusqueda
                        + "ORDER BY p.idProducto";
                PreparedStatement pst = cn.prepareStatement(query);
                ResultSet rs = pst.executeQuery();
                tblResultado.setModel(DbUtils.resultSetToTableModel(rs));
                ocultarColumna();
            } else {
                String query = consultaBusqueda
                        + "WHERE cm.nombreConvenio=? ORDER BY p.idProducto";
                String param = cmbCM.getSelectedItem().toString();
                PreparedStatement pst = cn.prepareStatement(query);
                pst.setString(1, param);
                ResultSet rs = pst.executeQuery();
                tblResultado.setModel(DbUtils.resultSetToTableModel(rs));
                ocultarColumna();

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
        }
    }//GEN-LAST:event_cmbCMActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            String query = "SELECT `IDCliente`, region.`nombreRegion`, comuna.`NombreComuna`, `Rut`, `Nombre`, org.nombreOrganizacion,u.detalle, `Cargo`, `Razon_social`, `Correo`, `Telefono`, `Ciudad`, `Direccion`, `fechaIngreso` FROM `cliente` join region on cliente.IDRegion = region.IDRegion join comuna on comuna.IDComuna = cliente.IDComuna join organizacion org on org.idOrganizacion=cliente.idOrganizacion join unidaddecompra u on u.idunidadcompra=cliente.idunidadcompra WHERE cliente.rut=?";
            String param = txtRutCotizacion.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, param);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                lblIDCliente.setText(rs.getString("IDCliente"));
                lblNombre.setText(rs.getString("Nombre"));
                lblCorreo.setText(rs.getString("Correo"));
                lblCorporacion.setText(rs.getString("nombreOrganizacion"));
                lblUnidadDeCompra.setText(rs.getString("u.detalle"));
                lblCargo.setText(rs.getString("Cargo"));
                lblRazon.setText(rs.getString("Razon_Social"));
                lblTelefono.setText(rs.getString("Telefono"));
                lblRegion.setText(rs.getString("region.nombreRegion"));
                lblComuna.setText(rs.getString("comuna.nombreComuna"));
                lblCiudad.setText(rs.getString("Ciudad"));
                lblDireccion.setText(rs.getString("Direccion"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
        }
        btnCalcular.setEnabled(true);
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtRutCotizacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRutCotizacionKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                String query = "SELECT `IDCliente`, region.`nombreRegion`, comuna.`NombreComuna`, `Rut`, `Nombre`, org.nombreOrganizacion,u.detalle, `Cargo`, `Razon_social`, `Correo`, `Telefono`, `Ciudad`, `Direccion`, `fechaIngreso` FROM `cliente` join region on cliente.IDRegion = region.IDRegion join comuna on comuna.IDComuna = cliente.IDComuna join organizacion org on org.idOrganizacion=cliente.idOrganizacion join unidaddecompra u on u.idunidadcompra=cliente.idunidadcompra WHERE cliente.rut=?";
                String param = txtRutCotizacion.getText();
                PreparedStatement pst = cn.prepareStatement(query);
                pst.setString(1, param);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    lblIDCliente.setText(rs.getString("IDCliente"));
                    lblNombre.setText(rs.getString("Nombre"));
                    lblCorreo.setText(rs.getString("Correo"));
                    lblCorporacion.setText(rs.getString("nombreOrganizacion"));
                    lblUnidadDeCompra.setText(rs.getString("u.detalle"));
                    lblCargo.setText(rs.getString("Cargo"));
                    lblRazon.setText(rs.getString("Razon_Social"));
                    lblTelefono.setText(rs.getString("Telefono"));
                    lblRegion.setText(rs.getString("region.nombreRegion"));
                    lblComuna.setText(rs.getString("comuna.nombreComuna"));
                    lblCiudad.setText(rs.getString("Ciudad"));
                    lblDireccion.setText(rs.getString("Direccion"));
                }
                //JOptionPane.showMessageDialog(null, "LISTO");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
            }
            btnCalcular.setEnabled(true);
        }
    }//GEN-LAST:event_txtRutCotizacionKeyPressed

    private void btnGuardaPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaPDFActionPerformed

        int idCot = 0;
//Metodo para Escoger la ruta donde se guardara el reporte
        String respuesta = JOptionPane.showInputDialog(null, "Escriba Su codigo de autorización.", "Permisos de acción", JOptionPane.INFORMATION_MESSAGE);
        String idCotizacion = "";
        if (lbCodigo.getText().equals(respuesta)) {

            JOptionPane.showMessageDialog(null, "Codigo de autorización Correcto.", "Correcto", JOptionPane.INFORMATION_MESSAGE);

            //INSERTAR EN BASE DE DATOS
            try {
                int idCliente = 0;
                int idUsuario = 0;

                int up2;
                idUsuario = Integer.parseInt(lblIDUsuario.getText());
                String queryMovimientos = "INSERT INTO movimientosventas(`IDUsuario`, `Movimiento`) VALUES (?,'Realizar Cotización')";
                PreparedStatement pst2 = cn.prepareStatement(queryMovimientos);
                pst2.setInt(1, idUsuario);
                up2 = pst2.executeUpdate();
                String queryCount = "select count(idCotizacion) from cotizacion";
                PreparedStatement pst3 = cn.prepareStatement(queryCount);

                ResultSet rsCount = pst3.executeQuery();

                String queryMax = "select Max(idCotizacion) from cotizacion";
                PreparedStatement pst4 = cn.prepareStatement(queryMax);
                ResultSet rsMax = pst4.executeQuery();

                while (rsMax.next()) {
                    idCot = rsMax.getInt(1);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error: hhh" + ex.getMessage());
            }

            try {
                System.out.println("Error en www: " + lblBrutoTotal.getText());

                String query = "INSERT INTO cotizacion (`IDUsuario`, `IDCliente`,`Bruto`,`Respondido`,`NetoTotal`,`IvaTotal`,distribuidor) VALUES (?,?,?,?,?,?,?)";
                PreparedStatement pst = cn.prepareStatement(query);
                //idUsuario
                pst.setInt(1, Integer.parseInt(lblIDUsuario.getText()));
                //idCliente
                pst.setInt(2, Integer.parseInt(lblIDCliente.getText()));
                //Neto

                double valor_3 = Double.parseDouble(lblBrutoTotal.getText());
                valor_3 = valor_3 * 1000;
                System.out.println("valor 3: " + valor_3);

                pst.setInt(3, (int) valor_3);

                //Estado No Respondido
                pst.setString(4, "No Respondido");

                double valor_5 = Double.parseDouble(lblNetoTotal.getText());
                valor_5 = valor_5 * 1000;
                System.out.println("valor 5: " + valor_5);

                pst.setInt(5, (int) valor_5);

                double valor_6 = Double.parseDouble(lblIVATotal.getText());

                valor_6 = valor_6 * 1000;
                System.out.println("valor 6: " + valor_6);

                pst.setInt(6, (int) valor_6);

                pst.setInt(7, cmbDistribuidor1.getSelectedIndex());
                int up = pst.executeUpdate();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error: www" + ex.getMessage());
            }

            String ruta = "";

            JFileChooser dlg = new JFileChooser();
            dlg.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int option = dlg.showOpenDialog(this);

            if (option == JFileChooser.APPROVE_OPTION) {
                File f = dlg.getSelectedFile();
                ruta = f.toString();
            }

            try {
                TableModel modelo = tblCotizacion.getModel();
                int selectedRows = tblCotizacion.getRowCount();

                for (int i = 0; i < selectedRows; i++) {
                    String queryDetalle = "insert into detallecotizacion (idCotizacion, idProducto, precioVenta, cantidad, iva_debito, neto, cargo, descuento,total)\n"
                            + "values((select idCotizacion from cotizacion order by idCotizacion DESC limit 1),(select p.id from producto p join distribuidor d on p.idDistribuidor = d.idDistribuidor where p.sku = ?),?,?,?,?,?,?,?)";

                    PreparedStatement pst4 = cn.prepareStatement(queryDetalle);

                    String valor3 = tblCotizacion.getValueAt(i, 4).toString().substring(3);
                    double valor_3 = Double.parseDouble(valor3);
                    valor_3 = valor_3 * 1000;
                    System.out.println("valor 3 en bbb: " + valor_3);

                    String valor6 = tblCotizacion.getValueAt(i, 6).toString().substring(3);
                    double valor_6 = Double.parseDouble(valor6);
                    valor_6 = valor_6 * 1000;

                    System.out.println("valor 6 en bbb: " + valor_6);

                    String valor7 = tblCotizacion.getValueAt(i, 8).toString();
                    double valor_7 = Double.parseDouble(valor7.substring(3));

                    valor_7 = valor_7 * 1000;

                    System.out.println("valor 7 en bbb: " + valor_7);

                    String valor8 = tblCotizacion.getValueAt(i, 9).toString();
                    int valor_8 = Integer.parseInt(valor8.substring(3));

                    valor_8 = valor_8 * 1000;

                    System.out.println("valor 8: " + valor_8);

                    String valor9 = tblCotizacion.getValueAt(i, 10).toString();
                    double valor_9 = Double.parseDouble(valor9.substring(3));

                    valor_9 = valor_9 * 1000;

                    System.out.println("valor 9: " + valor_9);

                    pst4.setInt(1, Integer.parseInt(tblCotizacion.getValueAt(i, 3).toString()));
                    pst4.setInt(2, (int) valor_3);
                    pst4.setInt(3, Integer.parseInt(tblCotizacion.getValueAt(i, 5).toString()));
                    pst4.setInt(4, Integer.parseInt(tblCotizacion.getValueAt(i, 7).toString()));
                    pst4.setInt(5, (int) valor_6);
                    pst4.setDouble(6, valor_7);
                    pst4.setDouble(7, valor_8);
                    pst4.setDouble(8, valor_9);

                    int detalle = pst4.executeUpdate();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:bbb " + ex.getMessage());
            }

            try {

                String queryDetalle = "select idCotizacion from cotizacion order by idCotizacion DESC limit 1";
                PreparedStatement pst4 = cn.prepareStatement(queryDetalle);
                ResultSet rs5 = pst4.executeQuery(queryDetalle);
                if (rs5.next()) {
                    idCotizacion = rs5.getString("idCotizacion");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error:aaa " + ex.getMessage());
            }

            //Fecha
            Date sistFecha = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("dd-MMM-YYYY");
            //Crear PDF
            try {
                Document doc = new Document(PageSize.A4);
                try {
                    Date sistHora = new Date();
                    String pmAm = "hh:mm a";
                    SimpleDateFormat format = new SimpleDateFormat(pmAm);
                    Calendar hoy = Calendar.getInstance();
                    String hora = (String.format(format.format(sistHora), hoy));
                    hora = hora.replace(":", "-");
                    PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(ruta + "\\" + lblNombre.getText() + "_Fecha_" + formato.format(sistFecha) + "_hora_" + hora + "_Cotizacion_" + idCotizacion + ".pdf"));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FormularioCotizacion.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                doc.open();
                //Añadir la imagen
                try {
                    Image logoAcima = Image.getInstance("src\\plataformaVentas\\Imagenes\\acima-logo-400p.png");
                    logoAcima.scaleAbsolute(210, 112);
                    doc.add(logoAcima);
                    Paragraph separacion0 = new Paragraph("_______________________________________________________________________________________", FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, null));
                    doc.add(separacion0);
                    Paragraph nro = new Paragraph("Número de Cotización: " + idCotizacion, FontFactory.getFont(FontFactory.TIMES, 12, Font.NORMAL, null));
                    nro.setAlignment(Paragraph.ALIGN_RIGHT);
                    doc.add(nro);
                    Paragraph separacion01 = new Paragraph("_______________________________________________________________________________________", FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, null));
                    doc.add(separacion01);

                } catch (BadElementException ex) {
                    Logger.getLogger(FormularioCotizacion.class
                            .getName()).log(Level.SEVERE, null, ex);

                } catch (IOException ex) {
                    Logger.getLogger(FormularioCotizacion.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

                //Añadir la información
                Paragraph header = new Paragraph("Para cualquier duda relacionada con la siguiente cotización, favor contactar a :", FontFactory.getFont(FontFactory.TIMES, 12, Font.NORMAL, null));
                doc.add(header);
                Paragraph header2 = new Paragraph("Ejecutivo de Cuenta: " + lblUsuario.getText() + " Correo:" + lblCorreoUsuario.getText() + " Télefono: +56 2 3210 7900", FontFactory.getFont(FontFactory.TIMES, 12, Font.NORMAL, null));
                doc.add(header2);
                Paragraph separacion = new Paragraph("_______________________________________________________________________________________", FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, null));
                doc.add(separacion);
                Paragraph titulo = new Paragraph("Información de Cliente", FontFactory.getFont(FontFactory.TIMES, 12, Font.NORMAL, null));
                doc.add(titulo);

                PdfPTable tableDatos = new PdfPTable(2);
                tableDatos.setWidthPercentage(100);
                tableDatos.addCell(new Phrase("Razón Social: " + lblRazon.getText(), FontFactory.getFont(FontFactory.TIMES, 12)));
                tableDatos.addCell(new Phrase("Rut: " + txtRutCotizacion.getText(), FontFactory.getFont(FontFactory.TIMES, 12)));
                tableDatos.addCell(new Phrase("Dirección: " + lblDireccion.getText(), FontFactory.getFont(FontFactory.TIMES, 12)));
                tableDatos.addCell(new Phrase("Fecha de Cotización: " + txtFecha.getText(), FontFactory.getFont(FontFactory.TIMES, 12)));
                tableDatos.addCell(new Phrase("Ciudad: " + lblCiudad.getText(), FontFactory.getFont(FontFactory.TIMES, 12)));
                tableDatos.addCell(new Phrase("Atención a: " + lblNombre.getText(), FontFactory.getFont(FontFactory.TIMES, 12)));
                tableDatos.setSpacingBefore(15f);
                tableDatos.setWidthPercentage(100);
                Paragraph alineaDatos = new Paragraph();
                alineaDatos.add(tableDatos);
                doc.add(alineaDatos);

                Paragraph separacion2 = new Paragraph("_______________________________________________________________________________________", FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, null));
                doc.add(separacion2);
                Paragraph tablas = new Paragraph("Información de Cotizacion ", FontFactory.getFont(FontFactory.TIMES, 12, Font.NORMAL, null));
                doc.add(tablas);
                try {
                    PdfPTable pdfTable = new PdfPTable(tblCotizacion_2.getColumnCount());
                    //Parámetros de espaciado y ancho
                    pdfTable.setSpacingBefore(15f);
                    pdfTable.setWidthPercentage(100);
                    //Añadir Valores
                    for (int i = 0; i < tblCotizacion_2.getColumnCount(); i++) {
                        pdfTable.addCell(new Phrase(tblCotizacion_2.getColumnName(i), FontFactory.getFont(FontFactory.HELVETICA, 8)));
                    }
                    //Extraer valores de la Jtable al PDF
                    for (int rows = 0; rows < tblCotizacion_2.getRowCount(); rows++) {
                        pdfTable.addCell(new Phrase(tblCotizacion_2.getModel().getValueAt(rows, 0).toString(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
                        pdfTable.addCell(new Phrase(tblCotizacion_2.getModel().getValueAt(rows, 1).toString(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
                        pdfTable.addCell(new Phrase(tblCotizacion_2.getModel().getValueAt(rows, 2).toString(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
                        pdfTable.addCell(new Phrase(tblCotizacion_2.getModel().getValueAt(rows, 3).toString(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
                        pdfTable.addCell(new Phrase(tblCotizacion_2.getModel().getValueAt(rows, 4).toString(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
                        pdfTable.addCell(new Phrase(tblCotizacion_2.getModel().getValueAt(rows, 5).toString(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
                        pdfTable.addCell(new Phrase(tblCotizacion_2.getModel().getValueAt(rows, 6).toString(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
                        pdfTable.addCell(new Phrase(tblCotizacion_2.getModel().getValueAt(rows, 10).toString(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
                    }
                    doc.add(pdfTable);
                } catch (DocumentException ex) {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error: tabla" + ex.getMessage());
                }

                Paragraph neto = new Paragraph("Neto: " + lblNetoTotal.getText(), FontFactory.getFont(FontFactory.TIMES, 12, Font.NORMAL, null));
                neto.setAlignment(Paragraph.ALIGN_RIGHT);
                Paragraph iva = new Paragraph("IVA: " + lblIVATotal.getText(), FontFactory.getFont(FontFactory.TIMES, 12, Font.NORMAL, null));
                iva.setAlignment(Paragraph.ALIGN_RIGHT);
                Paragraph total = new Paragraph("Total: " + lblBrutoTotal.getText(), FontFactory.getFont(FontFactory.TIMES, 12, Font.NORMAL, null));
                total.setAlignment(Paragraph.ALIGN_RIGHT);
                doc.add(neto);
                doc.add(iva);
                doc.add(total);

                Paragraph nota = new Paragraph("Esta cotización debe ser respondida dentro de 15 días hábiles", FontFactory.getFont(FontFactory.TIMES, 12, Font.NORMAL, null));
                doc.add(nota);
                Paragraph separacion3 = new Paragraph("_______________________________________________________________________________________", FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, null));
                separacion3.setSpacingBefore(15f);
                separacion3.setSpacingBefore(15f);
                doc.add(separacion3);
                //Iconos
                try {
                    PdfPTable table = new PdfPTable(2);
                    table.setWidths(new int[]{1, 12});
                    table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                    Image img1 = Image.getInstance("src\\PlataformaVentas\\Imagenes\\phone-icon-11-64.png");
                    Image img2 = Image.getInstance("src\\PlataformaVentas\\Imagenes\\mail-64.png");
                    table.addCell(new PdfPCell(img1, true));
                    table.addCell(new Phrase("Central telefónica: +56-232 107 900", FontFactory.getFont(FontFactory.TIMES, 12)));
                    table.addCell(new PdfPCell(img2, true));
                    table.addCell(new Phrase("ventas@acima.cl - comercial@acima.cl - gerencia@acima.cl", FontFactory.getFont(FontFactory.TIMES, 12)));
                    Paragraph tableFooter = new Paragraph();
                    tableFooter.add(table);
                    tableFooter.setSpacingBefore(15f);
                    tableFooter.setSpacingBefore(15f);
                    tableFooter.setAlignment(Paragraph.ALIGN_RIGHT);
                    doc.add(tableFooter);
                    doc.close();
                    JOptionPane.showMessageDialog(null, "PDF Generado Correctamente");

                } catch (BadElementException | IOException ex) {
                    Logger.getLogger(FormularioCotizacion.class
                            .getName()).log(Level.SEVERE, null, ex);

                }
            } catch (DocumentException ex) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error: sss" + ex.getMessage());
            }

        } else {
            JOptionPane.showMessageDialog(null, "Codigo de autorizacion invalido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardaPDFActionPerformed

    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularActionPerformed
        try {
            int selectedRows[] = tblCotizacion_2.getSelectedRows();
            /*
             Calculos Diego
             */
            int cantidad = 0;
            /*descuento del precio total en double*/
            double descuento = 0;

            /*Cargo que se le hara al totalFinal*/
            int cargo = 0;

            /*descuento en numeros entre 0=0descuento y 1=100 por ciento de descuento*/
            double descuentoPorcentaje;

            /*precio que sera descontado*/
            double precioDescuento;

            /*precio que sera descontado*/
            int precioDescuento_Entero;

            /*Precio Producto*/
            double precio;

            /*Cantidad * Precio*/
            int Neto = 0;

            /*Total con descuento = Neto-precioDescuento_Entero */
            int precioConDescuento = 0;
            /*Neto*0.19*/
            double IVA;
            /*Neto*0.19*/
            int IVA_entero = 0;
            /*Neto+iva+cargo+precioDescuento*/
            int TotalFinal = 0;
            /*
             Fin Calculos Diego
             */

            for (int i : selectedRows) {
                int resp = JOptionPane.showConfirmDialog(null, "¿Desea ingresar cantidad?", "", JOptionPane.YES_NO_OPTION);
                if (resp == 0) {
                    String cant = JOptionPane.showInputDialog("Ingrese Cantidad para Producto: ");
                    cantidad = Integer.parseInt(cant);
                    tblCotizacion.getModel().setValueAt(cantidad, tblCotizacion.getSelectedRow(), 5);
                } else {
                    tblCotizacion.getModel().setValueAt(0, tblCotizacion.getSelectedRow(), 5);
                }

                int resp_2 = JOptionPane.showConfirmDialog(null, "¿Desea ingresar descuento?", "", JOptionPane.YES_NO_OPTION);
                if (resp_2 == 0) {
                    //Metodo para Descuento
                    String dcto = JOptionPane.showInputDialog("Ingrese Descuento: ");
                    descuento = Double.parseDouble(dcto);
                    tblCotizacion.getModel().setValueAt(descuento, tblCotizacion.getSelectedRow(), 9);
                } else {
                    tblCotizacion.getModel().setValueAt(0, tblCotizacion.getSelectedRow(), 9);
                }

                int resp_3 = JOptionPane.showConfirmDialog(null, "¿Desea ingresar cargo?", "", JOptionPane.YES_NO_OPTION);
                if (resp_3 == 0) {
                    //Metodo para Cargo
                    String carg = JOptionPane.showInputDialog("Ingrese Cargo: ");
                    cargo = Integer.parseInt(carg);
                    tblCotizacion.getModel().setValueAt(cargo, tblCotizacion.getSelectedRow(), 8);
                } else {
                    tblCotizacion.getModel().setValueAt(0, tblCotizacion.getSelectedRow(), 8);
                }
                if (descuento == 0) {
                    precio = Double.valueOf(tblCotizacion.getValueAt(i, 4).toString().substring(3)) * 1000;
                    Neto = (int) (precio * cantidad);
                    TotalFinal = Neto + cargo;
                } else {
                    precio = Double.valueOf(tblCotizacion.getValueAt(i, 4).toString().substring(3)) * 1000;
                    descuentoPorcentaje = descuento / 100;
                    Neto = (int) (precio * cantidad);
                    TotalFinal = Neto + cargo;
                    TotalFinal = (int) ((int) TotalFinal - (TotalFinal * descuentoPorcentaje));
                }

                System.out.println(precio);
                System.out.println(Neto);
                System.out.println(cargo);
                System.out.println(descuento);
                System.out.println(TotalFinal);

                //java.text.NumberFormat.getCurrencyInstance().format(
                String neto_format = java.text.NumberFormat.getCurrencyInstance().format(Neto);
                String cargo_format = java.text.NumberFormat.getCurrencyInstance().format(cargo);
                String descuento_format = java.text.NumberFormat.getCurrencyInstance().format(descuento);
                String TotalFinal_format = java.text.NumberFormat.getCurrencyInstance().format(TotalFinal);
                tblCotizacion.setValueAt(neto_format, i, 6);
                tblCotizacion.setValueAt(19, i, 7);
                tblCotizacion.setValueAt(cargo_format, i, 8);
                tblCotizacion.setValueAt(descuento_format, i, 9);
                tblCotizacion.setValueAt(TotalFinal_format, i, 10);
            }
            int sumaNeto = 0;
            double sumaIVA = 0;
            int sumaTotal = 0;

            for (int r = 0; r < tblCotizacion.getRowCount(); r++) {

                String total_dummy_1 = tblCotizacion.getValueAt(r, 10).toString().substring(3);
                double total_dummy_2 = Double.parseDouble(total_dummy_1);
                total_dummy_2 = Math.round(total_dummy_2 * 1000);

                System.out.println(total_dummy_1);
                System.out.println(total_dummy_2);

                sumaNeto = (int) (sumaNeto + total_dummy_2);
                sumaTotal = (int) (sumaTotal + total_dummy_2);
            }

            System.out.println(sumaNeto);
            System.out.println(sumaTotal);
            sumaIVA = (sumaTotal * 0.19);
            sumaTotal = (int) (sumaTotal + sumaIVA);

            lblNetoTotal.setText("" + java.text.NumberFormat.getCurrencyInstance().format(sumaNeto));
            lblIVATotal.setText("" + java.text.NumberFormat.getCurrencyInstance().format(Math.round(sumaIVA)));
            lblBrutoTotal.setText("" + java.text.NumberFormat.getCurrencyInstance().format(sumaTotal));

            lblNetoTotal.setText(lblNetoTotal.getText().substring(3));
            lblIVATotal.setText(lblIVATotal.getText().substring(3));
            lblBrutoTotal.setText(lblBrutoTotal.getText().substring(3));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: Aún faltan productos por seleccionar " + ex.getMessage());
        }
    }//GEN-LAST:event_btnCalcularActionPerformed

    private void btnBorrarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarProductoActionPerformed
        DefaultTableModel modeloTabla = (DefaultTableModel) tblCotizacion.getModel();
        // Se obtiene el numero de columna seleccionado
        try {
            int seleccion = tblCotizacion.getSelectedRow();
            //Se borra
            modeloTabla.removeRow(seleccion);
            if (tblCotizacion.getRowCount() == 0) {
                btnBorrarProducto.setEnabled(false);
            }
            int sumaNeto = 0;
            double sumaIVA = 0;
            int sumaTotal = 0;
            for (int r = 0; r < tblCotizacion.getRowCount(); r++) {
                String total_dummy_1 = tblCotizacion.getValueAt(r, 10).toString().substring(3);
                double total_dummy_2 = Double.parseDouble(total_dummy_1);
                total_dummy_2 = Math.round(total_dummy_2 * 1000);

                System.out.println(total_dummy_1);
                System.out.println(total_dummy_2);

                sumaNeto = (int) (sumaNeto + total_dummy_2);
                sumaTotal = (int) (sumaTotal + total_dummy_2);
            }
            sumaIVA = (sumaTotal * 0.19);
            sumaTotal = (int) (sumaTotal + sumaIVA);
            lblNetoTotal.setText("" + sumaNeto);
            lblIVATotal.setText("" + Math.round(sumaIVA));
            lblBrutoTotal.setText("" + sumaTotal);
            JOptionPane.showMessageDialog(null, "Producto borrado de Cotizacion");
            btnBorrarProducto.setEnabled(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: no hay un producto seleccionado " + ex.getMessage());
        }
    }//GEN-LAST:event_btnBorrarProductoActionPerformed

    private void cmbCategoriaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCategoriaItemStateChanged
        try {
            if (cmbCategoria.getSelectedItem().toString() == "Todos los Productos") {
                String query = consultaBusqueda
                        + "ORDER BY p.idProducto";
                PreparedStatement pst = cn.prepareStatement(query);
                ResultSet rs = pst.executeQuery();
                tblResultado.setModel(DbUtils.resultSetToTableModel(rs));
                ocultarColumna();

            } else {
                String query = consultaBusqueda
                        + "WHERE p.categoria=? ORDER BY p.idProducto";
                String param = cmbCategoria.getSelectedItem().toString();
                PreparedStatement pst = cn.prepareStatement(query);
                pst.setString(1, param);
                ResultSet rs = pst.executeQuery();
                tblResultado.setModel(DbUtils.resultSetToTableModel(rs));
                ocultarColumna();

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
        }
    }//GEN-LAST:event_cmbCategoriaItemStateChanged

    private void cmbDistribuidorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbDistribuidorItemStateChanged
        try {
            if (cmbDistribuidor.getSelectedItem().toString() == "Todos los Productos") {
                String query = consultaBusqueda
                        + "ORDER BY p.idProducto";
                PreparedStatement pst = cn.prepareStatement(query);
                ResultSet rs = pst.executeQuery();
                tblResultado.setModel(DbUtils.resultSetToTableModel(rs));
                ocultarColumna();

            } else {
                String query = consultaBusqueda
                        + "WHERE dis.nombreDistribuidor = ? ORDER BY p.idProducto";
                String param = cmbDistribuidor.getSelectedItem().toString();
                PreparedStatement pst = cn.prepareStatement(query);
                pst.setString(1, param);
                ResultSet rs = pst.executeQuery();
                tblResultado.setModel(DbUtils.resultSetToTableModel(rs));
                ocultarColumna();

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
        }
    }//GEN-LAST:event_cmbDistribuidorItemStateChanged

    private void tblCotizacion_2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCotizacion_2MouseClicked
        tblCotizacion.clearSelection();
        tblCotizacion.addRowSelectionInterval(tblCotizacion_2.getSelectedRow(), tblCotizacion_2.getSelectedRow());
    }//GEN-LAST:event_tblCotizacion_2MouseClicked

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
            java.util.logging.Logger.getLogger(BuscarProducto.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BuscarProducto.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BuscarProducto.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BuscarProducto.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BuscarProducto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBorrarProducto;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCalcular;
    private javax.swing.JButton btnGuardaPDF;
    private javax.swing.JButton btnRealizarCotizacion;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> cmbCM;
    private javax.swing.JComboBox<String> cmbCategoria;
    private javax.swing.JComboBox<String> cmbDistribuidor;
    private javax.swing.JComboBox cmbDistribuidor1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    public javax.swing.JLabel lbCodigo;
    private javax.swing.JLabel lblBrutoTotal;
    private javax.swing.JLabel lblCargo;
    private javax.swing.JLabel lblCiudad;
    private javax.swing.JLabel lblComuna;
    private javax.swing.JLabel lblCorporacion;
    private javax.swing.JLabel lblCorreo;
    public javax.swing.JLabel lblCorreoUsuario;
    public javax.swing.JLabel lblCredencial;
    public javax.swing.JLabel lblCredencial1;
    private javax.swing.JLabel lblDescuento;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblIDCliente;
    public javax.swing.JLabel lblIDUsuario;
    public javax.swing.JLabel lblIDUsuario1;
    private javax.swing.JLabel lblIVATotal;
    private javax.swing.JLabel lblNetoTotal;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNombreProducto;
    private javax.swing.JLabel lblRazon;
    private javax.swing.JLabel lblRegion;
    public javax.swing.JLabel lblRut;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblUnidadDeCompra;
    public javax.swing.JLabel lblUsuario;
    public javax.swing.JLabel lblUsuario1;
    private javax.swing.JPanel panelCotizacion;
    private javax.swing.JTabbedPane panelDatos;
    private javax.swing.JPanel panelProductos;
    public javax.swing.JPanel panel_oculto;
    public javax.swing.JTable tblCotizacion;
    private javax.swing.JTable tblCotizacion_2;
    public javax.swing.JTable tblResultado;
    private javax.swing.JLabel txtFecha;
    private javax.swing.JTextField txtIDProducto;
    private javax.swing.JTextField txtNombreProducto;
    public javax.swing.JFormattedTextField txtRutCotizacion;
    private javax.swing.JTextField txtSKU;
    // End of variables declaration//GEN-END:variables
}
