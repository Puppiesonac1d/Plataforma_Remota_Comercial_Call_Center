package PlataformaVentas;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import clases.Conexion;
import java.awt.Component;
import java.awt.HeadlessException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author The_S
 */
public class Seguimiento extends javax.swing.JFrame {

    Conexion con = new Conexion();
    Connection cn = con.conecta();
    public boolean boolnv = false;
    public boolean boolcotizacion = false;
    public boolean boolingreso = false;
    public boolean boolsalida = false;

    public Seguimiento() {
        initComponents();
        btnNV.setEnabled(false);
        btnCotizacion.setEnabled(false);
        btnIngreso.setEnabled(false);
        btnSalida.setEnabled(false);
        try {

            String query = "SELECT \n"
                    + "    ot.idOrden AS 'Nota de Venta',\n"
                    + "    ot.codigoOrdenCompra AS 'Código de Orden de Compra',\n"
                    + "    ot.nombre_proveedor AS 'Empresa',\n"
                    + "    CASE\n"
                    + "        WHEN\n"
                    + "            EXISTS( SELECT \n"
                    + "                    idOrden\n"
                    + "                FROM\n"
                    + "                    detalle_Abastecimiento\n"
                    + "                WHERE\n"
                    + "                    idOrden = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'NOTA DE COMPRA CREADA'\n"
                    + "        WHEN\n"
                    + "            NOT EXISTS( SELECT \n"
                    + "                    idOrden\n"
                    + "                FROM\n"
                    + "                    detalle_Abastecimiento\n"
                    + "                WHERE\n"
                    + "                    idOrden = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'NOTA DE COMPRA NO CREADA'\n"
                    + "    END 'NOTAS DE COMPRA',\n"
                    + "    IFNULL(a.estado, '-') AS 'ESTADO DE PAGO',\n"
                    + "    CASE\n"
                    + "        WHEN\n"
                    + "            EXISTS( SELECT \n"
                    + "                    notaVenta\n"
                    + "                FROM\n"
                    + "                   ingreso\n"
                    + "                WHERE\n"
                    + "                    notaVenta = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'MERCADERÍA INGRESADA'\n"
                    + "        WHEN\n"
                    + "            NOT EXISTS( SELECT \n"
                    + "                    notaVenta\n"
                    + "                FROM\n"
                    + "                   ingreso\n"
                    + "                WHERE\n"
                    + "                    notaVenta = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'MERCADERÍA NO INGRESADA'\n"
                    + "    END 'INGRESO DE MERCADERIA',\n"
                    + "    CASE\n"
                    + "        WHEN\n"
                    + "            EXISTS( SELECT \n"
                    + "                    idOrden\n"
                    + "                FROM\n"
                    + "                    salida\n"
                    + "                WHERE\n"
                    + "                    idOrden = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'SALIDA DE MERCADERÍA REALIZADA'\n"
                    + "        WHEN\n"
                    + "            NOT EXISTS( SELECT \n"
                    + "                    idOrden\n"
                    + "                FROM\n"
                    + "                    salida\n"
                    + "                WHERE\n"
                    + "                    idOrden = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'SALIDA DE MERCADERÍA NO REALIZADA'\n"
                    + "    END 'SALIDA DE MERCADERÍA',\n"
                    + "    IFNULL(sa.numFactura,\n"
                    + "            'NO SE HA ASIGNADO UNA FACTURA') AS 'FACTURA',\n"
                    + "    IFNULL(sa.ordenTransporte,\n"
                    + "            'NO SE HA ASIGNADO UNA ORDEN DE TRANSPORTE') AS 'ORDEN DE TRANSPORTE'\n"
                    + "FROM\n"
                    + "    ordenTrabajo ot\n"
                    + "        LEFT JOIN\n"
                    + "    salida sa ON sa.idOrden = ot.idOrden\n"
                    + "        LEFT JOIN\n"
                    + "    detalle_abastecimiento da ON da.idOrden = ot.idOrden\n"
                    + "        LEFT JOIN\n"
                    + "    abastecimiento a ON a.numeroCotizacion = da.numeroCotizacion\n"
                    + "GROUP BY ot.idOrden\n"
                    + "ORDER BY ot.idOrden ASC;";
            PreparedStatement pst;
            pst = cn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            tblNV.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage()
            );
        }
        TableColorCellRenderer renderer = new TableColorCellRenderer();
        tblNV.setDefaultRenderer(Object.class, renderer);

    }

    public class TableColorCellRenderer implements TableCellRenderer {

        private final TableCellRenderer RENDERER = new DefaultTableCellRenderer();

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            int index = tblNV.getSelectedRow();

            if (row == index) {
                c.setBackground(new Color(57, 105, 138));
                c.setForeground(Color.WHITE);
            } else if (row % 2 == 0) {
                c.setBackground(Color.WHITE);
                c.setForeground(Color.BLACK);
                if (column == 3) {
                    if (tblNV.getValueAt(row, 3).toString().equals("NOTA DE COMPRA CREADA")) {
                        c.setBackground(Color.green);
                        c.setForeground(Color.BLACK);
                    }
                    if (tblNV.getValueAt(row, 3).toString().equals("NOTA DE COMPRA NO CREADA")) {
                        c.setBackground(Color.RED);
                        c.setForeground(Color.BLACK);
                    }
                }
                if (column == 4) {
                    if (tblNV.getValueAt(row, 4).toString().equals("Comprado")) {
                        c.setBackground(Color.green);
                        c.setForeground(Color.BLACK);
                    } else if (tblNV.getValueAt(row, 4).toString().equals("Nota de compra ingresada con productos faltantes")) {
                        c.setBackground(Color.YELLOW);
                        c.setForeground(Color.BLACK);
                    } else if (tblNV.getValueAt(row, 4).toString().equals("Nota de compra ingresada")) {
                        c.setBackground(Color.CYAN);
                        c.setForeground(Color.BLACK);
                    } else {
                        c.setBackground(Color.WHITE);
                        c.setForeground(Color.BLACK);
                    }
                }
                if (column == 5) {

                    if (tblNV.getValueAt(row, 5).toString().equals("MERCADERÍA INGRESADA")) {
                        c.setBackground(Color.green);
                        c.setForeground(Color.BLACK);

                    }
                    if (tblNV.getValueAt(row, 5).toString().equals("MERCADERÍA NO INGRESADA")) {
                        c.setBackground(Color.RED);
                        c.setForeground(Color.BLACK);
                    }
                }

                if (column == 6) {

                    if (tblNV.getValueAt(row, 6).toString().equals("SALIDA DE MERCADERÍA REALIZADA")) {
                        c.setBackground(Color.green);
                        c.setForeground(Color.BLACK);

                    }
                    if (tblNV.getValueAt(row, 6).toString().equals("SALIDA DE MERCADERÍA NO REALIZADA")) {
                        c.setBackground(Color.RED);
                        c.setForeground(Color.BLACK);
                    }
                }

                if (column == 7) {

                    if (!"NO SE HA ASIGNADO UNA FACTURA".equals(tblNV.getValueAt(row, 7).toString())) {
                        c.setBackground(Color.green);
                        c.setForeground(Color.BLACK);
                    } else {
                        c.setBackground(Color.RED);
                        c.setForeground(Color.BLACK);
                    }
                }

                if (column == 8) {

                    if (!"NO SE HA ASIGNADO UNA ORDEN DE TRANSPORTE".equals(tblNV.getValueAt(row, 8).toString())) {
                        c.setBackground(Color.green);
                        c.setForeground(Color.BLACK);
                    } else {
                        c.setBackground(Color.RED);
                        c.setForeground(Color.BLACK);
                    }
                }
            } else {
                c.setBackground(new Color(242, 242, 242));
                c.setForeground(Color.BLACK);
                if (column == 3) {
                    if (tblNV.getValueAt(row, 3).toString().equals("NOTA DE COMPRA CREADA")) {
                        c.setBackground(Color.green);
                        c.setForeground(Color.BLACK);
                    }
                    if (tblNV.getValueAt(row, 3).toString().equals("NOTA DE COMPRA NO CREADA")) {
                        c.setBackground(Color.RED);
                        c.setForeground(Color.BLACK);

                    }
                }
                if (column == 4) {
                    if (tblNV.getValueAt(row, 4).toString().equals("Comprado")) {
                        c.setBackground(Color.green);
                        c.setForeground(Color.BLACK);
                    } else if (tblNV.getValueAt(row, 4).toString().equals("Nota de compra ingresada con productos faltantes")) {
                        c.setBackground(Color.YELLOW);
                        c.setForeground(Color.BLACK);
                    } else if (tblNV.getValueAt(row, 4).toString().equals("Nota de compra ingresada")) {
                        c.setBackground(Color.CYAN);
                        c.setForeground(Color.BLACK);
                    } else {
                        c.setBackground(Color.WHITE);
                        c.setForeground(Color.BLACK);
                    }
                }
                if (column == 5) {

                    if (tblNV.getValueAt(row, 5).toString().equals("MERCADERÍA INGRESADA")) {
                        c.setBackground(Color.green);
                        c.setForeground(Color.BLACK);

                    }
                    if (tblNV.getValueAt(row, 5).toString().equals("MERCADERÍA NO INGRESADA")) {
                        c.setBackground(Color.RED);
                        c.setForeground(Color.BLACK);
                    }
                }

                if (column == 6) {

                    if (tblNV.getValueAt(row, 6).toString().equals("SALIDA DE MERCADERÍA REALIZADA")) {
                        c.setBackground(Color.green);
                        c.setForeground(Color.BLACK);

                    }
                    if (tblNV.getValueAt(row, 6).toString().equals("SALIDA DE MERCADERÍA NO REALIZADA")) {
                        c.setBackground(Color.RED);
                        c.setForeground(Color.BLACK);
                    }
                }

                if (column == 7) {

                    if (!"NO SE HA ASIGNADO UNA FACTURA".equals(tblNV.getValueAt(row, 7).toString())) {
                        c.setBackground(Color.green);
                        c.setForeground(Color.BLACK);
                    } else {
                        c.setBackground(Color.RED);
                        c.setForeground(Color.BLACK);
                    }
                }

                if (column == 8) {

                    if (!"NO SE HA ASIGNADO UNA ORDEN DE TRANSPORTE".equals(tblNV.getValueAt(row, 8).toString())) {
                        c.setBackground(Color.green);
                        c.setForeground(Color.BLACK);
                    } else {
                        c.setBackground(Color.RED);
                        c.setForeground(Color.BLACK);
                    }
                }
            }

            return c;
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

        detalleIngreso = new javax.swing.JFrame();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProductosIngreso = new javax.swing.JTable();
        lblNumeroIngreso = new javax.swing.JLabel();
        lblNumeroCotizacion = new javax.swing.JLabel();
        lblDistribuidor = new javax.swing.JLabel();
        lblTransporte = new javax.swing.JLabel();
        lblBodega = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblTipoIngreso = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        DetalleNotaCompra = new javax.swing.JFrame();
        jScrollPane5 = new javax.swing.JScrollPane();
        jPanel10 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        lblIva = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        lblNeto = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        txtDescuento = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        lblNumero = new javax.swing.JLabel();
        lblMail = new javax.swing.JLabel();
        lblContacto = new javax.swing.JLabel();
        lblProveedor = new javax.swing.JLabel();
        lblFecha1 = new javax.swing.JLabel();
        lblDistribuidor1 = new javax.swing.JLabel();
        lblNumeroCotizacion1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblOC = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        lblPromedio = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        lblDemoraDespacho = new javax.swing.JLabel();
        lblFormaPago = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        lblObservaciones = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jlabel98 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tablaDesechable = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblProductosDesechable = new javax.swing.JTable();
        detalleSalida = new javax.swing.JFrame();
        scrollpanel3 = new javax.swing.JScrollPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        lblNumeroNotaVenta = new javax.swing.JLabel();
        panelScroll = new javax.swing.JScrollPane();
        tblNVAsociadas = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lblDireccionDespacho = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        lblFechaSalida = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        lblNumFactura = new javax.swing.JLabel();
        panelSalida = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblBultos = new javax.swing.JTable();
        jPanel17 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblProductosBulto = new javax.swing.JTable();
        detalleNotaVenta = new javax.swing.JFrame();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel18 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel93 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jTabbedPane9 = new javax.swing.JTabbedPane();
        jScrollPane13 = new javax.swing.JScrollPane();
        tblProductosOrdenCompra = new javax.swing.JTable();
        txtDescripcionOC = new javax.swing.JTextField();
        jPanel34 = new javax.swing.JPanel();
        txtEstadoOC = new javax.swing.JTextField();
        jLabel100 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        txtRutCompradorOC = new javax.swing.JTextField();
        txtDireccionDemandanteOC = new javax.swing.JTextField();
        txtTelefonoComprador = new javax.swing.JTextField();
        jLabel98 = new javax.swing.JLabel();
        txtNombreDemandanteOC = new javax.swing.JTextField();
        txtUnidadCompraCliente = new javax.swing.JTextField();
        jLabel114 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        txtFechaEnvioOc = new javax.swing.JTextField();
        jPanel36 = new javax.swing.JPanel();
        jLabel90 = new javax.swing.JLabel();
        txtProveedorOC = new javax.swing.JTextField();
        jPanel37 = new javax.swing.JPanel();
        jLabel104 = new javax.swing.JLabel();
        txtNombreOrdenCompra = new javax.swing.JTextField();
        txtFechaEntregaProductoOC = new javax.swing.JTextField();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        txtDireccionesDespachoOC = new javax.swing.JTextField();
        txtDireccionEnvioFacturaOC = new javax.swing.JTextField();
        jLabel107 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        txtMailEnvioFactura = new javax.swing.JTextField();
        jLabel108 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        txtContactoOC = new javax.swing.JTextField();
        txtContactoPagoOC = new javax.swing.JTextField();
        txtFormaPagoOC = new javax.swing.JTextField();
        txtMetodoDespachoOC = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        txtCodigoOCOT = new javax.swing.JTextField();
        jPanel22 = new javax.swing.JPanel();
        jLabel115 = new javax.swing.JLabel();
        jLabel116 = new javax.swing.JLabel();
        jLabel117 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        jLabel119 = new javax.swing.JLabel();
        jLabel120 = new javax.swing.JLabel();
        jLabel121 = new javax.swing.JLabel();
        txtImpuestoEspecifico = new javax.swing.JTextField();
        txtIVAOC = new javax.swing.JTextField();
        txtSubtotalOC = new javax.swing.JTextField();
        txtCargosOC = new javax.swing.JTextField();
        txtDCTOOC = new javax.swing.JTextField();
        txtNetoOC = new javax.swing.JTextField();
        txtTotalOC = new javax.swing.JTextField();
        jLabel78 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        lblCodigoOCTitulo = new javax.swing.JLabel();
        txtConsultarOC = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnNV = new javax.swing.JButton();
        btnCotizacion = new javax.swing.JButton();
        btnIngreso = new javax.swing.JButton();
        btnSalida = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        lblFechaNV = new javax.swing.JLabel();
        lblFechaNotaCompra = new javax.swing.JLabel();
        lblFechaIngreso = new javax.swing.JLabel();
        lblFechaSalidaSeguimiento = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNV = new javax.swing.JTable();
        btnVolver = new javax.swing.JButton();
        btnReiniciar = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel7 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        txtCodigoOrdenCompra = new javax.swing.JTextField();
        btnBuscarOC = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        txtNumFactura = new javax.swing.JTextField();
        btnBuscarFactura = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        txtOrdenTransporte = new javax.swing.JTextField();
        btnOrdenTransporte = new javax.swing.JButton();

        detalleIngreso.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        detalleIngreso.setMinimumSize(new java.awt.Dimension(1280, 740));
        detalleIngreso.setSize(new java.awt.Dimension(1280, 740));

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel5.setText("Número de Ingreso de Mercadería:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel6.setText("Número de Cotización:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel8.setText("Distribuidor:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel9.setText("Transporte:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel10.setText("Bodega:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel11.setText("Fecha del Ingreso:");
        jLabel11.setToolTipText("");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel12.setText("Tipo de Ingreso:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel13.setText("Información de Producto:");

        tblProductosIngreso = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int celIndex){
                return false;
            }
        };
        tblProductosIngreso.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblProductosIngreso);

        lblNumeroIngreso.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblNumeroIngreso.setText("-");

        lblNumeroCotizacion.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblNumeroCotizacion.setText("-");

        lblDistribuidor.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblDistribuidor.setText("-");

        lblTransporte.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblTransporte.setText("-");

        lblBodega.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblBodega.setText("-");

        lblFecha.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblFecha.setText("-");

        lblTipoIngreso.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblTipoIngreso.setText("-");

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton1.setText("Cerrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNumeroIngreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblNumeroCotizacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblDistribuidor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblTransporte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblBodega, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblTipoIngreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jLabel13))
                        .addGap(10, 10, 10))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(1145, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblNumeroIngreso))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblNumeroCotizacion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblDistribuidor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblTransporte))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lblBodega))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblFecha))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblTipoIngreso))
                .addGap(24, 24, 24)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(10, 10, 10))
        );

        jScrollPane3.setViewportView(jPanel4);

        javax.swing.GroupLayout detalleIngresoLayout = new javax.swing.GroupLayout(detalleIngreso.getContentPane());
        detalleIngreso.getContentPane().setLayout(detalleIngresoLayout);
        detalleIngresoLayout.setHorizontalGroup(
            detalleIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalleIngresoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        detalleIngresoLayout.setVerticalGroup(
            detalleIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalleIngresoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
                .addContainerGap())
        );

        DetalleNotaCompra.setMinimumSize(new java.awt.Dimension(1324, 740));
        DetalleNotaCompra.setSize(new java.awt.Dimension(1324, 740));

        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane5.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane5.setMinimumSize(new java.awt.Dimension(1280, 740));
        jScrollPane5.setPreferredSize(new java.awt.Dimension(1280, 740));

        jPanel10.setMinimumSize(new java.awt.Dimension(1280, 740));

        jPanel12.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel40.setText("Iva:");

        lblIva.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblIva.setText("-");

        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblTotal.setText("-");

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel41.setText("Total:");

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel42.setText("Neto:");

        lblNeto.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblNeto.setText("-");

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel43.setText("Descuento:");

        txtDescuento.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        txtDescuento.setText("0");
        txtDescuento.setEnabled(false);
        txtDescuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDescuentoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43)
                    .addComponent(jLabel42)
                    .addComponent(jLabel40)
                    .addComponent(jLabel41))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNeto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescuento)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(lblNeto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(lblIva, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
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
        tblProductos.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(tblProductos);

        jPanel11.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel14.setText("Número:");

        lblNumero.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblNumero.setText("-");

        lblMail.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblMail.setText("-");

        lblContacto.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblContacto.setText("-");

        lblProveedor.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblProveedor.setText("-");

        lblFecha1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblFecha1.setText("-");

        lblDistribuidor1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblDistribuidor1.setText("-");

        lblNumeroCotizacion1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblNumeroCotizacion1.setText("-");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel3.setText("Número de Nota de Compra:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel4.setText("Empresa:");

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel34.setText("Fecha:");

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel36.setText("Proveedor:");

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel38.setText("Contacto:");

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel39.setText("Mail:");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel38)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel39, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(10, 10, 10)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblContacto, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
                    .addComponent(lblNumero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblFecha1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDistribuidor1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNumeroCotizacion1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblNumeroCotizacion1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblDistribuidor1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(lblFecha1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProveedor)
                    .addComponent(jLabel36))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(lblContacto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(lblMail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(lblNumero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        panelSalida.setVisible(false);

        tblOC.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane7.setViewportView(tblOC);

        jPanel13.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel44.setText("Margen Promedio:");

        lblPromedio.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblPromedio.setText("0%");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPromedio)
                .addContainerGap(248, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(lblPromedio))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel45.setText("Tiempo de Despacho:");

        lblDemoraDespacho.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblDemoraDespacho.setText("-");

        lblFormaPago.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblFormaPago.setText("-");

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel46.setText("Forma de Pago:");

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel47.setText("Observaciones:");

        lblObservaciones.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblObservaciones.setText("-");

        lblEstado.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblEstado.setText("-");

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel48.setText("Estado:");

        jlabel98.setVisible(false);
        jlabel98.setText("jLabel10");

        jScrollPane9.setVisible(false);

        tablaDesechable.setVisible(false);
        tablaDesechable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SKU", "Producto", "Cantidad"
            }
        ));
        jScrollPane9.setViewportView(tablaDesechable);

        jScrollPane8.setVisible(false);

        tblProductosDesechable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(tblProductosDesechable);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel48)
                            .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel46)
                            .addComponent(jLabel45))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblObservaciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblFormaPago, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDemoraDespacho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jlabel98)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(lblDemoraDespacho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(lblFormaPago, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(lblObservaciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEstado)
                    .addComponent(jLabel48))
                .addGap(8, 8, 8)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlabel98)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(111, 111, 111))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(520, 520, 520)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)))
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(10, 10, 10))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6)))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))))
        );

        jScrollPane5.setViewportView(jPanel10);

        javax.swing.GroupLayout DetalleNotaCompraLayout = new javax.swing.GroupLayout(DetalleNotaCompra.getContentPane());
        DetalleNotaCompra.getContentPane().setLayout(DetalleNotaCompraLayout);
        DetalleNotaCompraLayout.setHorizontalGroup(
            DetalleNotaCompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DetalleNotaCompraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1304, Short.MAX_VALUE)
                .addContainerGap())
        );
        DetalleNotaCompraLayout.setVerticalGroup(
            DetalleNotaCompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DetalleNotaCompraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 681, Short.MAX_VALUE)
                .addGap(49, 49, 49))
        );

        detalleSalida.setMinimumSize(new java.awt.Dimension(1280, 740));

        scrollpanel3.setMaximumSize(new java.awt.Dimension(1280, 740));

        jPanel15.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel15.setText("Número de Nota de Venta:");

        lblNumeroNotaVenta.setText("jLabel6");

        tblNVAsociadas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Notas de Venta"
            }
        ));
        panelScroll.setViewportView(tblNVAsociadas);

        jLabel16.setText("Notas de venta asociadas:");

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Resumen de Salida");

        jLabel18.setText("Dirección de Despacho:");

        lblDireccionDespacho.setText("jLabel10");

        jLabel19.setText("Fecha de Salida de Mercadería:");

        lblFechaSalida.setText("jLabel11");

        jLabel20.setText("Número de Factura:");

        lblNumFactura.setText("jLabel12");

        javax.swing.GroupLayout panelSalidaLayout = new javax.swing.GroupLayout(panelSalida);
        panelSalida.setLayout(panelSalidaLayout);
        panelSalidaLayout.setHorizontalGroup(
            panelSalidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        panelSalidaLayout.setVerticalGroup(
            panelSalidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNumFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDireccionDespacho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNumeroNotaVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(lblFechaSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(panelScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(lblNumeroNotaVenta))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(lblNumFactura))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(lblDireccionDespacho))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(lblFechaSalida))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(panelScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Bultos");

        tblBultos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane10.setViewportView(tblBultos);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Productos en Bultos");

        tblProductosBulto.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane11.setViewportView(tblProductosBulto);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane11))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        scrollpanel3.setViewportView(jPanel5);

        javax.swing.GroupLayout detalleSalidaLayout = new javax.swing.GroupLayout(detalleSalida.getContentPane());
        detalleSalida.getContentPane().setLayout(detalleSalidaLayout);
        detalleSalidaLayout.setHorizontalGroup(
            detalleSalidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalleSalidaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollpanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        detalleSalidaLayout.setVerticalGroup(
            detalleSalidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalleSalidaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollpanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        detalleNotaVenta.setSize(new java.awt.Dimension(1280, 740));

        jPanel21.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel93.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel93.setText("Información de la empresa:");

        jLabel102.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel102.setText("Información del Demandante:");

        jLabel103.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel103.setText("Información de Orden:");

        jLabel113.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel113.setText("Detalle de Orden:");

        tblProductosOrdenCompra = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int celIndex){
                return false;
            }
        };
        tblProductosOrdenCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código de Producto", "Producto", "Cantidad", "Especificación de Comprador", "Especificación de Proveedor", "Moneda", "Precio Unitario", "Cargos", "Descuento", "Valor Total"
            }
        ));
        jScrollPane13.setViewportView(tblProductosOrdenCompra);

        jTabbedPane9.addTab("Información de Productos", jScrollPane13);

        jPanel34.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel100.setText("Estado:");

        jLabel101.setText("Teléfono:");

        jLabel97.setText("Dirección Demandante:");

        jLabel96.setText("Rut de Comprador:");

        jLabel98.setText("Demandante:");

        jLabel114.setText("Unidad de Compra:");

        jLabel99.setText("Fecha de Envio OC:");

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel100)
                    .addComponent(jLabel97)
                    .addComponent(jLabel101)
                    .addComponent(jLabel96))
                .addGap(10, 10, 10)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTelefonoComprador, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addComponent(txtDireccionDemandanteOC)
                    .addComponent(txtRutCompradorOC)
                    .addComponent(txtEstadoOC))
                .addGap(39, 39, 39)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel99)
                    .addComponent(jLabel98)
                    .addComponent(jLabel114))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtUnidadCompraCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                    .addComponent(txtFechaEnvioOc)
                    .addComponent(txtNombreDemandanteOC))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel98)
                    .addComponent(txtNombreDemandanteOC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel96)
                    .addComponent(txtRutCompradorOC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel114)
                    .addComponent(txtUnidadCompraCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel97)
                    .addComponent(txtDireccionDemandanteOC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel99)
                    .addComponent(txtFechaEnvioOc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel101)
                    .addComponent(txtTelefonoComprador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEstadoOC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel100))
                .addContainerGap())
        );

        jPanel36.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel90.setText("Proveedor:");

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel90)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtProveedorOC, javax.swing.GroupLayout.PREFERRED_SIZE, 447, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProveedorOC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel90))
                .addContainerGap())
        );

        jPanel37.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel104.setText("Nombre de Orden de Compra:");

        txtNombreOrdenCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreOrdenCompraActionPerformed(evt);
            }
        });

        jLabel105.setText("Fecha de Entrega Productos:");

        jLabel106.setText("Direcciones de Despacho:");

        txtDireccionesDespachoOC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionesDespachoOCActionPerformed(evt);
            }
        });

        txtDireccionEnvioFacturaOC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionEnvioFacturaOCActionPerformed(evt);
            }
        });

        jLabel107.setText("Dirección de Envío de factura:");

        jLabel112.setText("Mail de envío de factura:");

        jLabel108.setText("Metodo de Despacho:");

        jLabel110.setText("Forma de Pago:");

        jLabel109.setText("Contacto de Pago:");

        jLabel111.setText("Contacto OC:");

        jLabel49.setText("Código de Orden de Compra:");

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel107)
                    .addComponent(jLabel112)
                    .addComponent(jLabel106)
                    .addComponent(jLabel105)
                    .addComponent(jLabel104, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtDireccionEnvioFacturaOC, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addComponent(txtDireccionesDespachoOC, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFechaEntregaProductoOC, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombreOrdenCompra, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMailEnvioFactura))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel110)
                    .addComponent(jLabel108)
                    .addComponent(jLabel109)
                    .addComponent(jLabel111, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtContactoOC)
                    .addComponent(txtContactoPagoOC)
                    .addComponent(txtFormaPagoOC, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                    .addComponent(txtMetodoDespachoOC)
                    .addComponent(txtCodigoOCOT))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel104)
                    .addComponent(txtNombreOrdenCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel108)
                    .addComponent(txtMetodoDespachoOC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFechaEntregaProductoOC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel105)
                        .addComponent(txtFormaPagoOC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel110)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDireccionesDespachoOC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel109)
                    .addComponent(txtContactoPagoOC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel106))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel107)
                    .addComponent(txtDireccionEnvioFacturaOC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel111)
                    .addComponent(txtContactoOC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMailEnvioFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel112)
                    .addComponent(jLabel49)
                    .addComponent(txtCodigoOCOT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel22.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel115.setText("Neto:");

        jLabel116.setText("Dcto:");

        jLabel117.setText("Cargos:");

        jLabel118.setText("Subtotal:");

        jLabel119.setText("19% IVA:");

        jLabel120.setText("Impuesto Especifico:");

        jLabel121.setText("Total:");

        txtSubtotalOC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSubtotalOCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel118)
                    .addComponent(jLabel117)
                    .addComponent(jLabel116)
                    .addComponent(jLabel115))
                .addGap(59, 59, 59)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCargosOC)
                    .addComponent(txtSubtotalOC)
                    .addComponent(txtDCTOOC)
                    .addComponent(txtNetoOC, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel121)
                    .addComponent(jLabel119)
                    .addComponent(jLabel120))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtImpuestoEspecifico, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(txtTotalOC, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtIVAOC))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel115)
                    .addComponent(txtNetoOC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel78)
                    .addComponent(jLabel119)
                    .addComponent(txtIVAOC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel116)
                    .addComponent(txtDCTOOC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel120)
                    .addComponent(txtImpuestoEspecifico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel117)
                    .addComponent(txtCargosOC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel121)
                    .addComponent(txtTotalOC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel118)
                    .addComponent(txtSubtotalOC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane9)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                        .addGap(583, 583, 583)
                        .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addComponent(jLabel113)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDescripcionOC, javax.swing.GroupLayout.PREFERRED_SIZE, 732, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel37, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel103, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel102, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel93, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel34, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel102)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel93)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel103)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDescripcionOC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel113))
                .addGap(50, 50, 50))
        );

        jPanel38.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblCodigoOCTitulo.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblCodigoOCTitulo.setText("Número de Orden de Compra:");

        txtConsultarOC.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCodigoOCTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtConsultarOC, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigoOCTitulo)
                    .addComponent(txtConsultarOC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(52, 52, 52))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane4.setViewportView(jPanel18);

        javax.swing.GroupLayout detalleNotaVentaLayout = new javax.swing.GroupLayout(detalleNotaVenta.getContentPane());
        detalleNotaVenta.getContentPane().setLayout(detalleNotaVentaLayout);
        detalleNotaVentaLayout.setHorizontalGroup(
            detalleNotaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalleNotaVentaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1260, Short.MAX_VALUE)
                .addContainerGap())
        );
        detalleNotaVentaLayout.setVerticalGroup(
            detalleNotaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalleNotaVentaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Seguimiento de Ordenes de Compra");
        setMinimumSize(new java.awt.Dimension(1280, 720));
        setSize(new java.awt.Dimension(1280, 720));

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Estado de orden de compra");

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnNV.setBackground(java.awt.Color.red);
        btnNV.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnNV.setText("Nota de Venta");
        btnNV.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNV.setMaximumSize(new java.awt.Dimension(324, 35));
        btnNV.setMinimumSize(new java.awt.Dimension(324, 35));
        btnNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNVActionPerformed(evt);
            }
        });

        btnCotizacion.setBackground(java.awt.Color.red);
        btnCotizacion.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnCotizacion.setText("Nota de Compra / Cotización");
        btnCotizacion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCotizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCotizacionActionPerformed(evt);
            }
        });

        btnIngreso.setBackground(java.awt.Color.red);
        btnIngreso.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnIngreso.setText("Ingreso de Mercadería");
        btnIngreso.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresoActionPerformed(evt);
            }
        });

        btnSalida.setBackground(java.awt.Color.red);
        btnSalida.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnSalida.setText("Salida de Mercadería");
        btnSalida.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalidaActionPerformed(evt);
            }
        });

        jLabel28.setText("Fecha de Creación:");

        jLabel29.setText("Fecha de Creación:");

        jLabel30.setText("Fecha de Creación:");

        jLabel31.setText("Fecha de Creación:");

        lblFechaNV.setText("-");

        lblFechaNotaCompra.setText("-");

        lblFechaIngreso.setText("-");

        lblFechaSalidaSeguimiento.setText("-");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCotizacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnIngreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblFechaNV))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblFechaNotaCompra))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblFechaIngreso))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblFechaSalidaSeguimiento)))
                .addGap(27, 27, 27))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28)
                    .addComponent(lblFechaNV))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCotizacion)
                    .addComponent(jLabel29)
                    .addComponent(lblFechaNotaCompra))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIngreso)
                    .addComponent(jLabel30)
                    .addComponent(lblFechaIngreso))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalida)
                    .addComponent(jLabel31)
                    .addComponent(lblFechaSalidaSeguimiento))
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Seleccionar número de Orden de Compra");

        tblNV = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int celIndex){
                return false;
            }
        };
        tblNV.setModel(new javax.swing.table.DefaultTableModel(
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
        tblNV.getTableHeader().setReorderingAllowed(false);
        tblNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNVMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNV);

        btnVolver.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        btnReiniciar.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnReiniciar.setText("Reiniciar filtros y mostrar todo");
        btnReiniciar.setToolTipText("");
        btnReiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReiniciarActionPerformed(evt);
            }
        });

        jTabbedPane1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jComboBox1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Acima Global SPA", "Acima Soluciones", "Importadora Vive Mas" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(550, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Buscar por ACIMA GLOBAL / ACIMA SOLUCIONES / Vive Más", jPanel3);

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel27.setText("Código de Orden de Compra:");

        txtCodigoOrdenCompra.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        btnBuscarOC.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnBuscarOC.setText("Buscar");
        btnBuscarOC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarOCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigoOrdenCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarOC)
                .addContainerGap(180, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(txtCodigoOrdenCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarOC))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Buscar por Código de Orden de Compra", jPanel7);

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel32.setText("Número de factura:");

        txtNumFactura.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        btnBuscarFactura.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnBuscarFactura.setText("Buscar");
        btnBuscarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarFacturaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNumFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarFactura)
                .addContainerGap(358, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(txtNumFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarFactura))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Buscar por Factura", jPanel8);

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel33.setText("Orden de Transporte:");

        txtOrdenTransporte.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        btnOrdenTransporte.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnOrdenTransporte.setText("Buscar");
        btnOrdenTransporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenTransporteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtOrdenTransporte, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOrdenTransporte)
                .addContainerGap(299, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(txtOrdenTransporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOrdenTransporte))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Buscar por Orden de Transporte", jPanel9);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnVolver))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jTabbedPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReiniciar)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnReiniciar)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVolver)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        detalleIngreso.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNVActionPerformed
        int index = tblNV.getSelectedRow();
        String valor = tblNV.getValueAt(index, 0).toString();

        txtConsultarOC.setText(valor);
        detalleNotaVenta.setVisible(true);
        try {
            String tipoOrden = "";
            //JOptionPane.showMessageDialog(null, "La Orden de Compra se encuentra en la base de datos");
            //Consulta de BD
            String query = "SELECT * FROM acimabasededatos.ordentrabajo oc join detalleordentrabajo doc on oc.codigoordencompra = doc.codigoordencompra\n"
                    + "where oc.idOrden=?;";
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, valor);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                txtProveedorOC.setText(rs.getString("nombre_proveedor"));
                txtRutCompradorOC.setText(rs.getString("rutCliente"));
                txtDireccionDemandanteOC.setText(rs.getString("direcciondemandante"));
                txtTelefonoComprador.setText(rs.getString("telefono"));
                txtNombreDemandanteOC.setText(rs.getString("demandante"));
                txtUnidadCompraCliente.setText(rs.getString("unidadcompra"));
                txtFechaEnvioOc.setText(rs.getString("fechaenviooc"));
                txtEstadoOC.setText(rs.getString("codigoestado"));
                txtCodigoOCOT.setText(rs.getString("codigoordencompra"));
                txtNombreOrdenCompra.setText(rs.getString("nombreordencompra"));
                txtFechaEntregaProductoOC.setText(rs.getString("fechaAceptacion"));
                txtDireccionesDespachoOC.setText(rs.getString("direccionesdespacho"));
                txtDireccionEnvioFacturaOC.setText(rs.getString("direccionEnvioFactura"));
                txtMetodoDespachoOC.setText(rs.getString("tipoDespacho"));
                txtContactoPagoOC.setText(rs.getString("contactoPago"));
                txtFormaPagoOC.setText(rs.getString("FormaPago"));
                txtContactoOC.setText(rs.getString("contactoOC"));
                txtMailEnvioFactura.setText(rs.getString("emailEnvioFactura"));
                txtCargosOC.setText(rs.getString("doc.cargos"));
                txtNetoOC.setText(rs.getString("neto"));
                txtDCTOOC.setText(rs.getString("dcto"));
                txtSubtotalOC.setText(rs.getString("subtotal"));
                txtIVAOC.setText(rs.getString("iva"));
                txtImpuestoEspecifico.setText(rs.getString("impuestoEspecifico"));
                txtTotalOC.setText(rs.getString("total"));

            }
            String queryProducto = "Select doc.codigoProducto as 'Código de Producto',\n"
                    + "SUBSTRING_INDEX(doc.nombreProducto, ')', -1) as 'Nombre de Producto',doc.cantidad as 'Cantidad',\n"
                    + "doc.moneda as 'Moneda',doc.precioUnitario as 'Precio Unitario', doc.descuento as 'Descuento',\n"
                    + "doc.cargos as 'Cargos', doc.valorTotal as 'Valor Total' from detalleordentrabajo doc\n"
                    + "join ordentrabajo oc on doc.codigoordencompra = oc.codigoordencompra\n"
                    + "where oc.idOrden=?";
            PreparedStatement pstProd = cn.prepareStatement(queryProducto);
            pstProd.setString(1, valor);
            ResultSet rsProd = pstProd.executeQuery();
            tblProductosOrdenCompra.setModel(DbUtils.resultSetToTableModel(rsProd));
            System.out.println("La consulta fue realizada con éxito");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnNVActionPerformed

    private void btnCotizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCotizacionActionPerformed
        try {
            //Indice de la tabla para indicar la nota de venta que se quiere consultar...
            int index = tblNV.getSelectedRow();
            int idOrden = Integer.parseInt(tblNV.getValueAt(index, 0).toString());

            JTable table = new JTable();
            //Cargar en una jtable en un dialogo que nota de compra se quiere ver...
            String queryProducto = "Select abastecimiento.numeroCotizacion as 'Notas de Compra' "
                    + "from abastecimiento join detalle_abastecimiento on "
                    + "detalle_abastecimiento.numeroCotizacion = abastecimiento.numeroCotizacion "
                    + "where detalle_abastecimiento.idOrden = ? group by abastecimiento.numeroCotizacion";
            PreparedStatement pstProd = cn.prepareStatement(queryProducto);
            pstProd.setInt(1, idOrden);
            ResultSet rsProd = pstProd.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rsProd));

            JOptionPane.showMessageDialog(null, new JScrollPane(table));

            int index_seleccionado = table.getSelectedRow();
            String numeroCotizacion = table.getValueAt(index_seleccionado, 0).toString();

            DetalleNotaCompra.setVisible(true);
            String query = "select * from abastecimiento where numeroCotizacion = ?;";
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, numeroCotizacion);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                lblNumeroCotizacion1.setText(rs.getString("numeroCotizacion"));
                lblDistribuidor1.setText(rs.getString("distribuidor"));
                lblFecha1.setText(rs.getString("fecha"));
                lblProveedor.setText(rs.getString("proveedor"));

                lblDemoraDespacho.setText(rs.getString("DemoraDespacho"));
                lblFormaPago.setText(rs.getString("FormaPago"));
                lblObservaciones.setText(rs.getString("observaciones"));
                lblEstado.setText(rs.getString("estado"));
                txtDescuento.setText(Double.toString(rs.getDouble("descuento")));
                lblNeto.setText("$0");
                lblIva.setText("$0");
                lblTotal.setText("$0");

            }

            String queryProveedores = "select * from proveedores p join telefono_proveedor tp on tp.rut_proveedor = p.rut_proveedor join correo_proveedor c on c.rut_proveedor = tp.rut_proveedor where p.nombre_proveedor = ?;";
            PreparedStatement pstProveedores = cn.prepareStatement(queryProveedores);
            pstProveedores.setString(1, lblProveedor.getText());
            ResultSet rsProveedores = pstProveedores.executeQuery();
            while (rsProveedores.next()) {
                lblContacto.setText(rsProveedores.getString("p.Contacto"));
                lblMail.setText(rsProveedores.getString("c.correo"));
                lblNumero.setText(rsProveedores.getString("tp.Telefono"));
            }

            DecimalFormat formatea = new DecimalFormat("###,###.##");

            String query2 = "SELECT\n"
                    + "da.codigoOrdenCompra AS 'Código de orden de compra',\n"
                    + "da.sku AS 'SKU',\n"
                    + "da.nombre AS 'Producto',\n"
                    + "da.cantidad AS 'Cantidad',\n"
                    + "da.precioUnitario AS 'Precio Unitario',\n"
                    + "CASE  WHEN (da.precioUnitario *  (select dot.cantidad from detalleordentrabajo dot where dot.idOrden = da.idOrden and da.codigoProducto = dot.codigoProducto) ) IS NULL THEN 0\n"
                    + "	ELSE (da.precioUnitario *  (select dot.cantidad from detalleordentrabajo dot where dot.idOrden = da.idOrden and da.codigoProducto = dot.codigoProducto) )\n"
                    + "    END AS 'Total de Nota de Venta',\n"
                    + "da.precioCosto AS 'Precio Costo',\n"
                    + "da.margen AS 'Margen',\n"
                    + "da.codigoProducto,\n"
                    + "da.idOrden\n"
                    + "FROM\n"
                    + "detalle_abastecimiento da\n"
                    + "WHERE\n"
                    + "da.numeroCotizacion = ?;";
            PreparedStatement pst2 = cn.prepareStatement(query2);
            pst2.setString(1, numeroCotizacion);
            ResultSet rs2 = pst2.executeQuery();
            tblProductos.setModel(DbUtils.resultSetToTableModel(rs2));

            double precioUnitario = 0;
            double precioTotal = 0;
            double precioCosto = 0;
            double neto = 0;
            double iva = 0;
            double total = 0;
            double promedioMargen = 0;
            tblProductos.getColumnModel().getColumn(8).setMinWidth(0);
            tblProductos.getColumnModel().getColumn(8).setMaxWidth(0);

            tblProductos.getColumnModel().getColumn(9).setMinWidth(0);
            tblProductos.getColumnModel().getColumn(9).setMaxWidth(0);

            for (int i = 0; i < tblProductos.getRowCount(); i++) {

                promedioMargen = promedioMargen + Double.parseDouble(tblProductos.getValueAt(i, 7).toString());

                precioUnitario = Integer.parseInt(tblProductos.getValueAt(i, 4).toString());
                precioTotal = Integer.parseInt(tblProductos.getValueAt(i, 5).toString());
                precioCosto = Integer.parseInt(tblProductos.getValueAt(i, 6).toString());
                tblProductos.setValueAt(java.text.NumberFormat.getCurrencyInstance().format(precioUnitario), i, 4);
                tblProductos.setValueAt(java.text.NumberFormat.getCurrencyInstance().format(precioTotal), i, 5);
                tblProductos.setValueAt(java.text.NumberFormat.getCurrencyInstance().format(precioCosto), i, 6);

                tblProductos.setValueAt(tblProductos.getValueAt(i, 4).toString().substring(2), i, 4);
                tblProductos.setValueAt(tblProductos.getValueAt(i, 5).toString().substring(2), i, 5);
                tblProductos.setValueAt(tblProductos.getValueAt(i, 6).toString().substring(2), i, 6);

                neto = neto + (Integer.parseInt(tblProductos.getValueAt(i, 6).toString().substring(1).replace(".", "")) * Integer.parseInt(tblProductos.getValueAt(i, 3).toString()));

            }
            DecimalFormat df = new DecimalFormat("#.00");

            lblPromedio.setText(df.format(promedioMargen / tblProductos.getRowCount()) + "%");

            double descuento = Double.parseDouble(txtDescuento.getText().replace("$", "").replace(".", "").replace(",", "."));
            neto = neto - descuento;
            iva = (int) (neto * 0.19);
            total = neto + iva;

            txtDescuento.setText("$" + formatea.format(descuento));

            lblNeto.setText("$" + formatea.format(neto));
            lblIva.setText("$" + formatea.format(iva));
            lblTotal.setText("$" + formatea.format(total));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: Debe seleccionar una cotizacion " + ex.getMessage());
        }
    }//GEN-LAST:event_btnCotizacionActionPerformed

    private void btnIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresoActionPerformed
        try {
            int index = tblNV.getSelectedRow();
            String valor = tblNV.getValueAt(index, 0).toString();

            detalleIngreso.setVisible(true);
            String query = "SELECT * FROM ingreso ing \n"
                    + "left join ordentrabajo ot on ot.idOrden= ing.notaVenta \n"
                    + "left join bodega bo on bo.idBodega = ing.idBodega where ing.notaVenta = ?";
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, valor);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                lblNumeroIngreso.setText(Integer.toString(rs.getInt("ing.idIngreso")));
                lblNumeroCotizacion.setText(rs.getString("ing.numeroCotizacion"));
                lblDistribuidor.setText(rs.getString("ing.nombreDistribuidor"));
                lblTransporte.setText(rs.getString("ing.tipoTransporte"));
                lblBodega.setText(rs.getString("bo.nombreBodega"));
                lblFecha.setText(rs.getString("ing.fechaIngreso"));
                lblTipoIngreso.setText(rs.getString("ing.tipoIngreso"));
            }
            String queryProducto = "SELECT \n"
                    + "    ing.notaVenta as 'ID de Orden de Compra',\n"
                    + "    ing.codigoOrdenCompra as 'Código de orden de compra',\n"
                    + "    ot.codigoProducto as 'ID de Producto',\n"
                    + "    inv.SKU,\n"
                    + "    ot.nombreProducto AS 'Nombre de Producto',\n"
                    + "    ing.stockIngresado AS 'Stock Ingresado'\n"
                    + "FROM\n"
                    + "    ingreso ing\n"
                    + "        LEFT JOIN\n"
                    + "    detalleordentrabajo ot ON ot.idOrden = ing.notaVenta\n"
                    + "	     LEFT JOIN \n"
                    + "         inventario inv on inv.IDPRODUCTO = ot.codigoProducto\n"
                    + "WHERE\n"
                    + "    ing.notaVenta = ?;";
            PreparedStatement pstProd = cn.prepareStatement(queryProducto);
            pstProd.setString(1, valor);
            ResultSet rsProd = pstProd.executeQuery();
            tblProductosIngreso.setModel(DbUtils.resultSetToTableModel(rsProd));
            System.out.println("La consulta fue realizada con éxito");

        } catch (SQLException ex) {
            Logger.getLogger(Seguimiento.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnIngresoActionPerformed

    private void btnSalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalidaActionPerformed
        int index = tblNV.getSelectedRow();
        try {
            detalleSalida.setVisible(true);
            String querySalida = "select idOrden, DATE_FORMAT(DATE(fechaSalida), '%d/%m/%Y'), direccionDespacho,numFactura from salida where idOrden = ? GROUP BY idOrden;";
            PreparedStatement pst;
            pst = cn.prepareStatement(querySalida);
            pst.setInt(1, Integer.parseInt(tblNV.getValueAt(index, 0).toString()));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                lblNumeroNotaVenta.setText(rs.getString(1));
                lblFechaSalida.setText(rs.getString(2));
                lblDireccionDespacho.setText(rs.getString(3));
                lblNumFactura.setText(Integer.toString(rs.getInt(4)));
            }

            String queryOC = "select codigoOrdenCompra from salida where idOrden = ? GROUP BY idOrden;";
            PreparedStatement pstOC;
            pstOC = cn.prepareStatement(queryOC);
            pstOC.setInt(1, Integer.parseInt(tblNV.getValueAt(index, 0).toString()));
            ResultSet rsOC = pstOC.executeQuery();
            tblNVAsociadas.setModel(DbUtils.resultSetToTableModel(rsOC));

            String queryBulto = "Select codigoBulto as 'Número de Bulto' , largo as 'Largo', alto as 'Alto', ancho as 'Ancho',\n"
                    + "chofer as 'Conductor', guiaDespacho as 'Guía de Despacho', u.NombreUsuario as 'Encargado' \n"
                    + "from bulto b join usuario u on b.encargado = u.codigo_autorizacion  where idOrden = ?;";
            PreparedStatement pstBulto;
            pstBulto = cn.prepareStatement(queryBulto);
            pstBulto.setInt(1, Integer.parseInt(tblNV.getValueAt(index, 0).toString()));
            ResultSet rsBulto = pstBulto.executeQuery();
            tblBultos.setModel(DbUtils.resultSetToTableModel(rsBulto));

            String queryProductos = "select codigoBulto as 'Número de Bulto' , idProducto as 'Código de Producto',"
                    + " nombreProducto as 'Nombre de Producto', stockRestado as 'Cantidad de Producto'\n"
                    + "from detalleSalida where idOrden = ?;";
            PreparedStatement pstProd;
            pstProd = cn.prepareStatement(queryProductos);
            pstProd.setInt(1, Integer.parseInt(tblNV.getValueAt(index, 0).toString()));
            ResultSet rsProd = pstProd.executeQuery();
            tblProductosBulto.setModel(DbUtils.resultSetToTableModel(rsProd));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnSalidaActionPerformed

    private void tblNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNVMouseClicked
        try {

            int index = tblNV.getSelectedRow();

            TableColorCellRenderer renderer = new TableColorCellRenderer();
            tblNV.setDefaultRenderer(Object.class, renderer);
            tblNV.repaint();
            int notaVenta = Integer.parseInt(tblNV.getValueAt(index, 0).toString());

            int nv = 0;
            int cotizacion = 0;
            int ingreso = 0;
            int salida = 0;

            //Nota de Venta
            try {
                String queryNV = "SELECT idOrden,  LEFT(fechaenvioOC,10) from ordenTrabajo where idOrden = ?;";
                PreparedStatement pstNV = cn.prepareStatement(queryNV);
                pstNV.setInt(1, notaVenta);
                ResultSet rsNV = pstNV.executeQuery();
                while (rsNV.next()) {
                    nv = rsNV.getInt(1);
                    lblFechaNV.setText(rsNV.getString(2));
                }
                if (lblFechaNV.getText().equals("-")) {
                    lblFechaNV.setText("-");
                } else {
                    String anio_1 = lblFechaNV.getText().substring(0, 4);
                    String mes_1 = lblFechaNV.getText().substring(5, 7);
                    String dia_1 = lblFechaNV.getText().substring(8, 10);

                    String fecha_1 = dia_1 + "/" + mes_1 + "/" + anio_1;

                    lblFechaNV.setText(fecha_1);
                }

                if (nv == notaVenta) {
                    btnNV.setEnabled(true);
                    boolnv = true;
                    btnNV.setBackground(Color.GREEN);
                } else {
                    btnNV.setEnabled(false);
                    boolnv = false;
                    btnNV.setBackground(Color.RED);
                    lblFechaNV.setText("-");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error con la operacion de Nota de Venta: " + ex.getMessage());
            }
            //Cotizaciones
            try {
                String queryCotizaciones = "SELECT idOrden,fecha from detalle_Abastecimiento join abastecimiento on detalle_abastecimiento.numeroCotizacion "
                        + "= abastecimiento.numeroCotizacion where idOrden = ?;";
                PreparedStatement pstCotizaciones = cn.prepareStatement(queryCotizaciones);
                pstCotizaciones.setInt(1, notaVenta);
                ResultSet rsCotizaciones = pstCotizaciones.executeQuery();
                while (rsCotizaciones.next()) {
                    cotizacion = rsCotizaciones.getInt(1);
                    lblFechaNotaCompra.setText(rsCotizaciones.getDate(2).toString());
                }

                if (lblFechaNotaCompra.getText().equals("-")) {
                    lblFechaNotaCompra.setText("-");
                } else {
                    String anio_2 = lblFechaNotaCompra.getText().substring(0, 4);
                    String mes_2 = lblFechaNotaCompra.getText().substring(5, 7);
                    String dia_2 = lblFechaNotaCompra.getText().substring(8, 10);
                    String fecha_2 = dia_2 + "/" + mes_2 + "/" + anio_2;
                    lblFechaNotaCompra.setText(fecha_2);
                }

                if (cotizacion == notaVenta) {
                    btnCotizacion.setEnabled(true);
                    boolcotizacion = true;
                    btnCotizacion.setBackground(Color.GREEN);
                } else {
                    btnCotizacion.setEnabled(false);
                    boolcotizacion = false;
                    btnCotizacion.setBackground(Color.RED);
                    lblFechaNotaCompra.setText("-");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error con la operacion de Cotizacion: " + ex.getMessage());
            }
            try {
                String queryIngreso = "select notaventa, LEFT(fechaIngreso,10) from ingreso i where NOTAVENTA = ?";
                PreparedStatement pstIngreso = cn.prepareStatement(queryIngreso);
                pstIngreso.setString(1, Integer.toString(notaVenta));
                ResultSet rsIngreso = pstIngreso.executeQuery();
                while (rsIngreso.next()) {
                    ingreso = rsIngreso.getInt(1);
                    lblFechaIngreso.setText(rsIngreso.getTimestamp(2).toString().substring(0, 10));
                }

                System.out.println(lblFechaIngreso.getText());

                if (ingreso == notaVenta) {
                    btnIngreso.setEnabled(true);
                    boolingreso = true;
                    btnIngreso.setBackground(Color.GREEN);
                } else {
                    btnIngreso.setEnabled(false);
                    boolingreso = false;
                    lblFechaIngreso.setText("-");
                    btnIngreso.setBackground(Color.RED);
                }

                if (lblFechaIngreso.getText().equals("-")) {
                    lblFechaIngreso.setText("-");
                } else {
                    String anio_3 = lblFechaIngreso.getText().substring(0, 2);
                    String mes_3 = lblFechaIngreso.getText().substring(5, 7);
                    String dia_3 = lblFechaIngreso.getText().substring(8, 10);

                    String fecha_3 = dia_3 + "/" + mes_3 + "/" + anio_3;
                    System.out.println(fecha_3);
                    lblFechaIngreso.setText(fecha_3);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error con la operacion de Ingreso de Mercadería: " + ex.getMessage());
            }

            //Salida
            try {
                String querySalida = "SELECT idOrden,fechaSalida FROM salida where idOrden = ?;";
                PreparedStatement pstSalida = cn.prepareStatement(querySalida);
                pstSalida.setInt(1, notaVenta);
                ResultSet rsSalida = pstSalida.executeQuery();
                while (rsSalida.next()) {
                    salida = rsSalida.getInt(1);
                    lblFechaSalidaSeguimiento.setText(rsSalida.getTimestamp(2).toString().substring(0, 10));
                }

                if (salida == notaVenta) {
                    btnSalida.setEnabled(true);
                    boolsalida = true;
                    btnSalida.setBackground(Color.GREEN);

                } else {
                    btnSalida.setEnabled(false);
                    boolsalida = false;
                    btnSalida.setBackground(Color.RED);
                    lblFechaSalidaSeguimiento.setText("-");
                }
                if (lblFechaSalidaSeguimiento.getText().equals("-")) {
                    lblFechaSalidaSeguimiento.setText("-");
                } else {
                    String anio_4 = lblFechaSalidaSeguimiento.getText().substring(0, 3);
                    String mes_4 = lblFechaSalidaSeguimiento.getText().substring(5, 7);
                    String dia_4 = lblFechaSalidaSeguimiento.getText().substring(8, 10);

                    String fecha_4 = dia_4 + "/" + mes_4 + "/" + anio_4;
                    System.out.println(fecha_4);
                    lblFechaSalidaSeguimiento.setText(fecha_4);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error con la operacion de Salida: " + ex.getMessage());
            }

        } catch (HeadlessException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_tblNVMouseClicked

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReiniciarActionPerformed
        try {
            String query = "SELECT \n"
                    + "    ot.idOrden AS 'Nota de Venta',\n"
                    + "    ot.codigoOrdenCompra AS 'Código de Orden de Compra',\n"
                    + "    ot.nombre_proveedor AS 'Empresa',\n"
                    + "    CASE\n"
                    + "        WHEN\n"
                    + "            EXISTS( SELECT \n"
                    + "                    idOrden\n"
                    + "                FROM\n"
                    + "                    detalle_Abastecimiento\n"
                    + "                WHERE\n"
                    + "                    idOrden = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'NOTA DE COMPRA CREADA'\n"
                    + "        WHEN\n"
                    + "            NOT EXISTS( SELECT \n"
                    + "                    idOrden\n"
                    + "                FROM\n"
                    + "                    detalle_Abastecimiento\n"
                    + "                WHERE\n"
                    + "                    idOrden = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'NOTA DE COMPRA NO CREADA'\n"
                    + "    END 'NOTAS DE COMPRA',\n"
                    + "    IFNULL(a.estado, '-') AS 'ESTADO DE PAGO',\n"
                    + "    CASE\n"
                    + "        WHEN\n"
                    + "            EXISTS( SELECT \n"
                    + "                    notaVenta\n"
                    + "                FROM\n"
                    + "                   ingreso\n"
                    + "                WHERE\n"
                    + "                    notaVenta = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'MERCADERÍA INGRESADA'\n"
                    + "        WHEN\n"
                    + "            NOT EXISTS( SELECT \n"
                    + "                    notaVenta\n"
                    + "                FROM\n"
                    + "                   ingreso\n"
                    + "                WHERE\n"
                    + "                    notaVenta = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'MERCADERÍA NO INGRESADA'\n"
                    + "    END 'INGRESO DE MERCADERIA',\n"
                    + "    CASE\n"
                    + "        WHEN\n"
                    + "            EXISTS( SELECT \n"
                    + "                    idOrden\n"
                    + "                FROM\n"
                    + "                    salida\n"
                    + "                WHERE\n"
                    + "                    idOrden = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'SALIDA DE MERCADERÍA REALIZADA'\n"
                    + "        WHEN\n"
                    + "            NOT EXISTS( SELECT \n"
                    + "                    idOrden\n"
                    + "                FROM\n"
                    + "                    salida\n"
                    + "                WHERE\n"
                    + "                    idOrden = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'SALIDA DE MERCADERÍA NO REALIZADA'\n"
                    + "    END 'SALIDA DE MERCADERÍA',\n"
                    + "    IFNULL(sa.numFactura,\n"
                    + "            'NO SE HA ASIGNADO UNA FACTURA') AS 'FACTURA',\n"
                    + "    IFNULL(sa.ordenTransporte,\n"
                    + "            'NO SE HA ASIGNADO UNA ORDEN DE TRANSPORTE') AS 'ORDEN DE TRANSPORTE'\n"
                    + "FROM\n"
                    + "    ordenTrabajo ot\n"
                    + "        LEFT JOIN\n"
                    + "    salida sa ON sa.idOrden = ot.idOrden\n"
                    + "        LEFT JOIN\n"
                    + "    detalle_abastecimiento da ON da.idOrden = ot.idOrden\n"
                    + "        LEFT JOIN\n"
                    + "    abastecimiento a ON a.numeroCotizacion = da.numeroCotizacion\n"
                    + "GROUP BY ot.idOrden\n"
                    + "ORDER BY ot.idOrden ASC;";
            PreparedStatement pst;
            pst = cn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            tblNV.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnReiniciarActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        try {
            String query = "SELECT \n"
                    + "    ot.idOrden AS 'Nota de Venta',\n"
                    + "    ot.codigoOrdenCompra AS 'Código de Orden de Compra',\n"
                    + "    ot.nombre_proveedor AS 'Empresa',\n"
                    + "    CASE\n"
                    + "        WHEN\n"
                    + "            EXISTS( SELECT \n"
                    + "                    idOrden\n"
                    + "                FROM\n"
                    + "                    detalle_Abastecimiento\n"
                    + "                WHERE\n"
                    + "                    idOrden = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'NOTA DE COMPRA CREADA'\n"
                    + "        WHEN\n"
                    + "            NOT EXISTS( SELECT \n"
                    + "                    idOrden\n"
                    + "                FROM\n"
                    + "                    detalle_Abastecimiento\n"
                    + "                WHERE\n"
                    + "                    idOrden = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'NOTA DE COMPRA NO CREADA'\n"
                    + "    END 'NOTAS DE COMPRA',\n"
                    + "    IFNULL(a.estado, '-') AS 'ESTADO DE PAGO',\n"
                    + "    CASE\n"
                    + "        WHEN\n"
                    + "            EXISTS( SELECT \n"
                    + "                    notaVenta\n"
                    + "                FROM\n"
                    + "                   ingreso\n"
                    + "                WHERE\n"
                    + "                    notaVenta = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'MERCADERÍA INGRESADA'\n"
                    + "        WHEN\n"
                    + "            NOT EXISTS( SELECT \n"
                    + "                    notaVenta\n"
                    + "                FROM\n"
                    + "                   ingreso\n"
                    + "                WHERE\n"
                    + "                    notaVenta = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'MERCADERÍA NO INGRESADA'\n"
                    + "    END 'INGRESO DE MERCADERIA',\n"
                    + "    CASE\n"
                    + "        WHEN\n"
                    + "            EXISTS( SELECT \n"
                    + "                    idOrden\n"
                    + "                FROM\n"
                    + "                    salida\n"
                    + "                WHERE\n"
                    + "                    idOrden = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'SALIDA DE MERCADERÍA REALIZADA'\n"
                    + "        WHEN\n"
                    + "            NOT EXISTS( SELECT \n"
                    + "                    idOrden\n"
                    + "                FROM\n"
                    + "                    salida\n"
                    + "                WHERE\n"
                    + "                    idOrden = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'SALIDA DE MERCADERÍA NO REALIZADA'\n"
                    + "    END 'SALIDA DE MERCADERÍA',\n"
                    + "    IFNULL(sa.numFactura,\n"
                    + "            'NO SE HA ASIGNADO UNA FACTURA') AS 'FACTURA',\n"
                    + "    IFNULL(sa.ordenTransporte,\n"
                    + "            'NO SE HA ASIGNADO UNA ORDEN DE TRANSPORTE') AS 'ORDEN DE TRANSPORTE'\n"
                    + "FROM\n"
                    + "    ordenTrabajo ot\n"
                    + "        LEFT JOIN\n"
                    + "    salida sa ON sa.idOrden = ot.idOrden\n"
                    + "        LEFT JOIN\n"
                    + "    detalle_abastecimiento da ON da.idOrden = ot.idOrden\n"
                    + "        LEFT JOIN\n"
                    + "    abastecimiento a ON a.numeroCotizacion = da.numeroCotizacion\n"
                    + "where ot.nombre_proveedor RLIKE ? \n"
                    + "GROUP BY ot.idOrden\n"
                    + "ORDER BY ot.idOrden ASC;";
            PreparedStatement pst;
            pst = cn.prepareStatement(query);
            pst.setString(1, jComboBox1.getSelectedItem().toString());
            ResultSet rs = pst.executeQuery();
            tblNV.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (SQLException ex) {
            Logger.getLogger(Seguimiento.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void btnBuscarOCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarOCActionPerformed
        try {
            String query = "SELECT \n"
                    + "    ot.idOrden AS 'Nota de Venta',\n"
                    + "    ot.codigoOrdenCompra AS 'Código de Orden de Compra',\n"
                    + "    ot.nombre_proveedor AS 'Empresa',\n"
                    + "    CASE\n"
                    + "        WHEN\n"
                    + "            EXISTS( SELECT \n"
                    + "                    idOrden\n"
                    + "                FROM\n"
                    + "                    detalle_Abastecimiento\n"
                    + "                WHERE\n"
                    + "                    idOrden = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'NOTA DE COMPRA CREADA'\n"
                    + "        WHEN\n"
                    + "            NOT EXISTS( SELECT \n"
                    + "                    idOrden\n"
                    + "                FROM\n"
                    + "                    detalle_Abastecimiento\n"
                    + "                WHERE\n"
                    + "                    idOrden = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'NOTA DE COMPRA NO CREADA'\n"
                    + "    END 'NOTAS DE COMPRA',\n"
                    + "    IFNULL(a.estado, '-') AS 'ESTADO DE PAGO',\n"
                    + "    CASE\n"
                    + "        WHEN\n"
                    + "            EXISTS( SELECT \n"
                    + "                    notaVenta\n"
                    + "                FROM\n"
                    + "                   ingreso\n"
                    + "                WHERE\n"
                    + "                    notaVenta = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'MERCADERÍA INGRESADA'\n"
                    + "        WHEN\n"
                    + "            NOT EXISTS( SELECT \n"
                    + "                    notaVenta\n"
                    + "                FROM\n"
                    + "                   ingreso\n"
                    + "                WHERE\n"
                    + "                    notaVenta = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'MERCADERÍA NO INGRESADA'\n"
                    + "    END 'INGRESO DE MERCADERIA',\n"
                    + "    CASE\n"
                    + "        WHEN\n"
                    + "            EXISTS( SELECT \n"
                    + "                    idOrden\n"
                    + "                FROM\n"
                    + "                    salida\n"
                    + "                WHERE\n"
                    + "                    idOrden = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'SALIDA DE MERCADERÍA REALIZADA'\n"
                    + "        WHEN\n"
                    + "            NOT EXISTS( SELECT \n"
                    + "                    idOrden\n"
                    + "                FROM\n"
                    + "                    salida\n"
                    + "                WHERE\n"
                    + "                    idOrden = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'SALIDA DE MERCADERÍA NO REALIZADA'\n"
                    + "    END 'SALIDA DE MERCADERÍA',\n"
                    + "    IFNULL(sa.numFactura,\n"
                    + "            'NO SE HA ASIGNADO UNA FACTURA') AS 'FACTURA',\n"
                    + "    IFNULL(sa.ordenTransporte,\n"
                    + "            'NO SE HA ASIGNADO UNA ORDEN DE TRANSPORTE') AS 'ORDEN DE TRANSPORTE'\n"
                    + "FROM\n"
                    + "    ordenTrabajo ot\n"
                    + "        LEFT JOIN\n"
                    + "    salida sa ON sa.idOrden = ot.idOrden\n"
                    + "        LEFT JOIN\n"
                    + "    detalle_abastecimiento da ON da.idOrden = ot.idOrden\n"
                    + "        LEFT JOIN\n"
                    + "    abastecimiento a ON a.numeroCotizacion = da.numeroCotizacion\n"
                    + "where ot.codigoOrdenCompra RLIKE ? \n"
                    + "GROUP BY ot.idOrden\n"
                    + "ORDER BY ot.idOrden ASC;";
            PreparedStatement pst;
            pst = cn.prepareStatement(query);
            pst.setString(1, txtCodigoOrdenCompra.getText());
            ResultSet rs = pst.executeQuery();
            tblNV.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (SQLException ex) {
            Logger.getLogger(Seguimiento.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBuscarOCActionPerformed

    private void btnBuscarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarFacturaActionPerformed
        try {
            String query = "SELECT \n"
                    + "    ot.idOrden AS 'Nota de Venta',\n"
                    + "    ot.codigoOrdenCompra AS 'Código de Orden de Compra',\n"
                    + "    ot.nombre_proveedor AS 'Empresa',\n"
                    + "    CASE\n"
                    + "        WHEN\n"
                    + "            EXISTS( SELECT \n"
                    + "                    idOrden\n"
                    + "                FROM\n"
                    + "                    detalle_Abastecimiento\n"
                    + "                WHERE\n"
                    + "                    idOrden = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'NOTA DE COMPRA CREADA'\n"
                    + "        WHEN\n"
                    + "            NOT EXISTS( SELECT \n"
                    + "                    idOrden\n"
                    + "                FROM\n"
                    + "                    detalle_Abastecimiento\n"
                    + "                WHERE\n"
                    + "                    idOrden = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'NOTA DE COMPRA NO CREADA'\n"
                    + "    END 'NOTAS DE COMPRA',\n"
                    + "    IFNULL(a.estado, '-') AS 'ESTADO DE PAGO',\n"
                    + "    CASE\n"
                    + "        WHEN\n"
                    + "            EXISTS( SELECT \n"
                    + "                    notaVenta\n"
                    + "                FROM\n"
                    + "                   ingreso\n"
                    + "                WHERE\n"
                    + "                    notaVenta = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'MERCADERÍA INGRESADA'\n"
                    + "        WHEN\n"
                    + "            NOT EXISTS( SELECT \n"
                    + "                    notaVenta\n"
                    + "                FROM\n"
                    + "                   ingreso\n"
                    + "                WHERE\n"
                    + "                    notaVenta = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'MERCADERÍA NO INGRESADA'\n"
                    + "    END 'INGRESO DE MERCADERIA',\n"
                    + "    CASE\n"
                    + "        WHEN\n"
                    + "            EXISTS( SELECT \n"
                    + "                    idOrden\n"
                    + "                FROM\n"
                    + "                    salida\n"
                    + "                WHERE\n"
                    + "                    idOrden = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'SALIDA DE MERCADERÍA REALIZADA'\n"
                    + "        WHEN\n"
                    + "            NOT EXISTS( SELECT \n"
                    + "                    idOrden\n"
                    + "                FROM\n"
                    + "                    salida\n"
                    + "                WHERE\n"
                    + "                    idOrden = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'SALIDA DE MERCADERÍA NO REALIZADA'\n"
                    + "    END 'SALIDA DE MERCADERÍA',\n"
                    + "    IFNULL(sa.numFactura,\n"
                    + "            'NO SE HA ASIGNADO UNA FACTURA') AS 'FACTURA',\n"
                    + "    IFNULL(sa.ordenTransporte,\n"
                    + "            'NO SE HA ASIGNADO UNA ORDEN DE TRANSPORTE') AS 'ORDEN DE TRANSPORTE'\n"
                    + "FROM\n"
                    + "    ordenTrabajo ot\n"
                    + "        LEFT JOIN\n"
                    + "    salida sa ON sa.idOrden = ot.idOrden\n"
                    + "        LEFT JOIN\n"
                    + "    detalle_abastecimiento da ON da.idOrden = ot.idOrden\n"
                    + "        LEFT JOIN\n"
                    + "    abastecimiento a ON a.numeroCotizacion = da.numeroCotizacion\n"
                    + "where sal.numFactura RLIKE ? \n"
                    + "GROUP BY ot.idOrden\n"
                    + "ORDER BY ot.idOrden ASC;";
            PreparedStatement pst;
            pst = cn.prepareStatement(query);
            pst.setString(1, txtNumFactura.getText());
            ResultSet rs = pst.executeQuery();
            tblNV.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (SQLException ex) {
            Logger.getLogger(Seguimiento.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBuscarFacturaActionPerformed

    private void btnOrdenTransporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenTransporteActionPerformed
        try {
            String query = "SELECT \n"
                    + "    ot.idOrden AS 'Nota de Venta',\n"
                    + "    ot.codigoOrdenCompra AS 'Código de Orden de Compra',\n"
                    + "    ot.nombre_proveedor AS 'Empresa',\n"
                    + "    CASE\n"
                    + "        WHEN\n"
                    + "            EXISTS( SELECT \n"
                    + "                    idOrden\n"
                    + "                FROM\n"
                    + "                    detalle_Abastecimiento\n"
                    + "                WHERE\n"
                    + "                    idOrden = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'NOTA DE COMPRA CREADA'\n"
                    + "        WHEN\n"
                    + "            NOT EXISTS( SELECT \n"
                    + "                    idOrden\n"
                    + "                FROM\n"
                    + "                    detalle_Abastecimiento\n"
                    + "                WHERE\n"
                    + "                    idOrden = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'NOTA DE COMPRA NO CREADA'\n"
                    + "    END 'NOTAS DE COMPRA',\n"
                    + "    IFNULL(a.estado, '-') AS 'ESTADO DE PAGO',\n"
                    + "    CASE\n"
                    + "        WHEN\n"
                    + "            EXISTS( SELECT \n"
                    + "                    notaVenta\n"
                    + "                FROM\n"
                    + "                   ingreso\n"
                    + "                WHERE\n"
                    + "                    notaVenta = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'MERCADERÍA INGRESADA'\n"
                    + "        WHEN\n"
                    + "            NOT EXISTS( SELECT \n"
                    + "                    notaVenta\n"
                    + "                FROM\n"
                    + "                   ingreso\n"
                    + "                WHERE\n"
                    + "                    notaVenta = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'MERCADERÍA NO INGRESADA'\n"
                    + "    END 'INGRESO DE MERCADERIA',\n"
                    + "    CASE\n"
                    + "        WHEN\n"
                    + "            EXISTS( SELECT \n"
                    + "                    idOrden\n"
                    + "                FROM\n"
                    + "                    salida\n"
                    + "                WHERE\n"
                    + "                    idOrden = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'SALIDA DE MERCADERÍA REALIZADA'\n"
                    + "        WHEN\n"
                    + "            NOT EXISTS( SELECT \n"
                    + "                    idOrden\n"
                    + "                FROM\n"
                    + "                    salida\n"
                    + "                WHERE\n"
                    + "                    idOrden = ot.idOrden)\n"
                    + "        THEN\n"
                    + "            'SALIDA DE MERCADERÍA NO REALIZADA'\n"
                    + "    END 'SALIDA DE MERCADERÍA',\n"
                    + "    IFNULL(sa.numFactura,\n"
                    + "            'NO SE HA ASIGNADO UNA FACTURA') AS 'FACTURA',\n"
                    + "    IFNULL(sa.ordenTransporte,\n"
                    + "            'NO SE HA ASIGNADO UNA ORDEN DE TRANSPORTE') AS 'ORDEN DE TRANSPORTE'\n"
                    + "FROM\n"
                    + "    ordenTrabajo ot\n"
                    + "        LEFT JOIN\n"
                    + "    salida sa ON sa.idOrden = ot.idOrden\n"
                    + "        LEFT JOIN\n"
                    + "    detalle_abastecimiento da ON da.idOrden = ot.idOrden\n"
                    + "        LEFT JOIN\n"
                    + "    abastecimiento a ON a.numeroCotizacion = da.numeroCotizacion\n"
                    + "where sal.ordenTransporte RLIKE ? \n"
                    + "GROUP BY ot.idOrden\n"
                    + "ORDER BY ot.idOrden ASC;";
            PreparedStatement pst;
            pst = cn.prepareStatement(query);
            pst.setString(1, txtOrdenTransporte.getText());
            ResultSet rs = pst.executeQuery();
            tblNV.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (SQLException ex) {
            Logger.getLogger(Seguimiento.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnOrdenTransporteActionPerformed

    private void txtDescuentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyPressed
        if (txtDescuento.equals("")) {
            DecimalFormat formatea = new DecimalFormat("###,###.##");

            txtDescuento.setText("$" + 0);

            int index = tblProductos.getRowCount();
            double descuento = 0;
            double neto = 0;
            double iva = 0;
            double total = 0;
            for (int i = 0; i < index; i++) {

                double value = Double.parseDouble(lblNeto.getText().substring(1).replace(".", "").replace(",", "."));

                double value2 = Double.parseDouble(tblProductos.getValueAt(i, 6).toString().substring(1).replace(".", "").replace(",", "."));
                System.out.println("Value: " + value);
                System.out.println("Value 2: " + value2);
                neto = (neto + (value2 * Double.parseDouble(tblProductos.getValueAt(i, 3).toString()))) - descuento;

            }
            neto = neto - descuento;
            lblNeto.setText("$" + formatea.format(neto));
            iva = (neto * 0.19);
            System.out.println(iva);
            lblIva.setText("$" + formatea.format(iva));

            total = (neto + iva);

            lblTotal.setText("$" + formatea.format(total));

        } else {
            DecimalFormat formatea = new DecimalFormat("###,###.##");

            txtDescuento.setText("$" + formatea.format(Double.parseDouble(txtDescuento.getText().replace("$", "").replace(".", "").replace(",", "."))));

            // System.out.println(formatea.format(valor));
            //Nos devuelve 1.000
            int index = tblProductos.getRowCount();
            double descuento = Double.parseDouble(txtDescuento.getText().replace("$", "").replace(".", "").replace(",", "."));
            double neto = 0;
            double iva = 0;
            double total = 0;
            for (int i = 0; i < index; i++) {

                double value = Double.parseDouble(lblNeto.getText().substring(1).replace(".", "").replace(",", "."));

                double value2 = Double.parseDouble(tblProductos.getValueAt(i, 6).toString().substring(1).replace(".", "").replace(",", "."));
                System.out.println("Value: " + value);
                System.out.println("Value 2: " + value2);
                neto = (neto + (value2 * Double.parseDouble(tblProductos.getValueAt(i, 3).toString())));
            }
            neto = neto - descuento;
            lblNeto.setText("$" + formatea.format(neto));
            iva = (neto * 0.19);
            System.out.println(iva);
            lblIva.setText("$" + formatea.format(iva));

            total = (neto + iva);

            lblTotal.setText("$" + formatea.format(total));

        }
    }//GEN-LAST:event_txtDescuentoKeyPressed

    private void txtNombreOrdenCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreOrdenCompraActionPerformed

    }//GEN-LAST:event_txtNombreOrdenCompraActionPerformed

    private void txtDireccionesDespachoOCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionesDespachoOCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionesDespachoOCActionPerformed

    private void txtDireccionEnvioFacturaOCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionEnvioFacturaOCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionEnvioFacturaOCActionPerformed

    private void txtSubtotalOCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSubtotalOCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSubtotalOCActionPerformed

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
            java.util.logging.Logger.getLogger(Seguimiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Seguimiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Seguimiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Seguimiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Seguimiento().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame DetalleNotaCompra;
    private javax.swing.JButton btnBuscarFactura;
    private javax.swing.JButton btnBuscarOC;
    private javax.swing.JButton btnCotizacion;
    private javax.swing.JButton btnIngreso;
    private javax.swing.JButton btnNV;
    private javax.swing.JButton btnOrdenTransporte;
    private javax.swing.JButton btnReiniciar;
    private javax.swing.JButton btnSalida;
    private javax.swing.JButton btnVolver;
    private javax.swing.JFrame detalleIngreso;
    private javax.swing.JFrame detalleNotaVenta;
    private javax.swing.JFrame detalleSalida;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
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
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane9;
    private javax.swing.JLabel jlabel98;
    private javax.swing.JLabel lblBodega;
    private javax.swing.JLabel lblCodigoOCTitulo;
    public javax.swing.JLabel lblContacto;
    public javax.swing.JLabel lblDemoraDespacho;
    public javax.swing.JLabel lblDireccionDespacho;
    private javax.swing.JLabel lblDistribuidor;
    public javax.swing.JLabel lblDistribuidor1;
    public javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFecha;
    public javax.swing.JLabel lblFecha1;
    private javax.swing.JLabel lblFechaIngreso;
    private javax.swing.JLabel lblFechaNV;
    private javax.swing.JLabel lblFechaNotaCompra;
    private javax.swing.JLabel lblFechaSalida;
    private javax.swing.JLabel lblFechaSalidaSeguimiento;
    public javax.swing.JLabel lblFormaPago;
    public javax.swing.JLabel lblIva;
    public javax.swing.JLabel lblMail;
    public javax.swing.JLabel lblNeto;
    public javax.swing.JLabel lblNumFactura;
    public javax.swing.JLabel lblNumero;
    private javax.swing.JLabel lblNumeroCotizacion;
    public javax.swing.JLabel lblNumeroCotizacion1;
    private javax.swing.JLabel lblNumeroIngreso;
    public javax.swing.JLabel lblNumeroNotaVenta;
    public javax.swing.JLabel lblObservaciones;
    public javax.swing.JLabel lblPromedio;
    public javax.swing.JLabel lblProveedor;
    public javax.swing.JLabel lblTipoIngreso;
    public javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTransporte;
    private javax.swing.JPanel panelSalida;
    private javax.swing.JScrollPane panelScroll;
    private javax.swing.JScrollPane scrollpanel3;
    private javax.swing.JTable tablaDesechable;
    public javax.swing.JTable tblBultos;
    public javax.swing.JTable tblNV;
    public javax.swing.JTable tblNVAsociadas;
    private javax.swing.JTable tblOC;
    public javax.swing.JTable tblProductos;
    public javax.swing.JTable tblProductosBulto;
    private javax.swing.JTable tblProductosDesechable;
    private javax.swing.JTable tblProductosIngreso;
    public javax.swing.JTable tblProductosOrdenCompra;
    public javax.swing.JTextField txtCargosOC;
    public javax.swing.JTextField txtCodigoOCOT;
    private javax.swing.JTextField txtCodigoOrdenCompra;
    public javax.swing.JTextField txtConsultarOC;
    public javax.swing.JTextField txtContactoOC;
    public javax.swing.JTextField txtContactoPagoOC;
    public javax.swing.JTextField txtDCTOOC;
    public javax.swing.JTextField txtDescripcionOC;
    public javax.swing.JTextField txtDescuento;
    public javax.swing.JTextField txtDireccionDemandanteOC;
    public javax.swing.JTextField txtDireccionEnvioFacturaOC;
    public javax.swing.JTextField txtDireccionesDespachoOC;
    public javax.swing.JTextField txtEstadoOC;
    public javax.swing.JTextField txtFechaEntregaProductoOC;
    public javax.swing.JTextField txtFechaEnvioOc;
    public javax.swing.JTextField txtFormaPagoOC;
    public javax.swing.JTextField txtIVAOC;
    public javax.swing.JTextField txtImpuestoEspecifico;
    public javax.swing.JTextField txtMailEnvioFactura;
    public javax.swing.JTextField txtMetodoDespachoOC;
    public javax.swing.JTextField txtNetoOC;
    public javax.swing.JTextField txtNombreDemandanteOC;
    public javax.swing.JTextField txtNombreOrdenCompra;
    private javax.swing.JTextField txtNumFactura;
    private javax.swing.JTextField txtOrdenTransporte;
    public javax.swing.JTextField txtProveedorOC;
    public javax.swing.JTextField txtRutCompradorOC;
    public javax.swing.JTextField txtSubtotalOC;
    public javax.swing.JTextField txtTelefonoComprador;
    public javax.swing.JTextField txtTotalOC;
    public javax.swing.JTextField txtUnidadCompraCliente;
    // End of variables declaration//GEN-END:variables
}
