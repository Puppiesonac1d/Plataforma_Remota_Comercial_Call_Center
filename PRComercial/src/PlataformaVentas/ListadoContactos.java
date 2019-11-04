package PlataformaVentas;

import clases.Conexion;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.ComboBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Diego Alfaro Fierro, Diego González Romàn
 */
public class ListadoContactos extends javax.swing.JFrame {

    Conexion con = new Conexion();
    Connection cn = con.conecta();
    private final DefaultTableModel tableModel = new DefaultTableModel();
    private final DefaultTableModel tableModel2 = new DefaultTableModel();
    private String queryListaContactos = "Select co.IDContacto,\n"
            + "            co.correo AS 'Correo',\n"
            + "            co.Nombre,\n"
            + "            co.Numero,\n"
            + "            org.NombreOrganizacion,\n"
            + "            r.nombreRegion AS 'Region',\n"
            + "            com.NombreComuna,\n"
            + "            co.EstadoMail AS 'Estado'\n"
            + "            FROM contactos co join region r on co.idRegion = r.idRegion\n"
            + "            left join region reg on reg.idRegion = co.IDRegion\n"
            + "            left join comuna com on com.IDRegion = reg.IDRegion \n"
            + "            left join organizacion org on org.IDOrganizacion = co.IDOrganizacion\n";

    public ListadoContactos() {
        initComponents();

        jPanel4.setBackground(new Color(0, 0, 0, 30));
        LlenarTabla();
        cboComuna.disable();
        LlenarRegion();
        LLenadoComuna(cboRegion4, cboComuna4);
        LLenadoComuna(cboRegion, cboComuna);
        btnMailDeliveryNE.setBackground(Color.red);
        btnMailDeliveryCE.setBackground(Color.red);
        btnMailDeliverySR.setBackground(Color.red);
        btnMailEnviado.setBackground(Color.green);
        btnMailEnviado1.setBackground(Color.green);
        btnMailEnviado2.setBackground(Color.green);
        btnMailNoEnviado.setBackground(Color.orange);
        btnMailNoEnviado1.setBackground(Color.orange);
        btnMailNoEnviado2.setBackground(Color.orange);

    }

    public void LlenarRegion() {
        try {
            String query = "select NombreRegion from region ORDER BY IDRegion";
            PreparedStatement pst = cn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                cboRegion.addItem(rs.getString(1));
                cboRegion1.addItem(rs.getString(1));
                cboRegion2.addItem(rs.getString(1));
                cboRegion3.addItem(rs.getString(1));
                cboRegion4.addItem(rs.getString(1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void LLenadoComuna(JComboBox comboRegion, JComboBox comboComuna) {
        //Llenado del cboComuna
        try {
            String query = "select co.nombreComuna "
                    + "from Comuna co join Region re on co.IDRegion = re.IDRegion "
                    + "where re.idRegion = ?  "
                    + "order by co.nombreComuna asc";
            int param = comboRegion.getSelectedIndex()-1;
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setInt(1, param);
            ResultSet rs = pst.executeQuery();
           // cboComuna.addItem("Todas las Comunas");
            while (rs.next()) {
                comboComuna.addItem(rs.getString(1));
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void OcultarDatos() {
        tblCorreosLeidos.getColumnModel().getColumn(0).setMaxWidth(0);
        tblCorreosLeidos.getColumnModel().getColumn(0).setMinWidth(0);
        tblCorreosLeidos.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        tblCorreosLeidos.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        tblCorreosLeidos.getColumnModel().getColumn(6).setMaxWidth(0);
        tblCorreosLeidos.getColumnModel().getColumn(6).setMinWidth(0);
        tblCorreosLeidos.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
        tblCorreosLeidos.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);

        tblResultadoCorreoNoLeido.getColumnModel().getColumn(0).setMaxWidth(0);
        tblResultadoCorreoNoLeido.getColumnModel().getColumn(0).setMinWidth(0);
        tblResultadoCorreoNoLeido.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        tblResultadoCorreoNoLeido.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        tblResultadoCorreoNoLeido.getColumnModel().getColumn(6).setMaxWidth(0);
        tblResultadoCorreoNoLeido.getColumnModel().getColumn(6).setMinWidth(0);
        tblResultadoCorreoNoLeido.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
        tblResultadoCorreoNoLeido.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);

        tblMailDelivery.getColumnModel().getColumn(0).setMaxWidth(0);
        tblMailDelivery.getColumnModel().getColumn(0).setMinWidth(0);
        tblMailDelivery.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        tblMailDelivery.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        tblMailDelivery.getColumnModel().getColumn(6).setMaxWidth(0);
        tblMailDelivery.getColumnModel().getColumn(6).setMinWidth(0);
        tblMailDelivery.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
        tblMailDelivery.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);

        tblNoEnviados.getColumnModel().getColumn(0).setMaxWidth(0);
        tblNoEnviados.getColumnModel().getColumn(0).setMinWidth(0);
        tblNoEnviados.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        tblNoEnviados.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        tblNoEnviados.getColumnModel().getColumn(6).setMaxWidth(0);
        tblNoEnviados.getColumnModel().getColumn(6).setMinWidth(0);
        tblNoEnviados.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
        tblNoEnviados.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);

        tblSinRespuesta.getColumnModel().getColumn(0).setMaxWidth(0);
        tblSinRespuesta.getColumnModel().getColumn(0).setMinWidth(0);
        tblSinRespuesta.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        tblSinRespuesta.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        tblSinRespuesta.getColumnModel().getColumn(6).setMaxWidth(0);
        tblSinRespuesta.getColumnModel().getColumn(6).setMinWidth(0);
        tblSinRespuesta.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
        tblSinRespuesta.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);

        /*
         //Ajuste de la Columna Correos en ambas tablas
         tblResultadoCorreoNoLeido.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(320);
         tblResultadoCorreoNoLeido.getTableHeader().getColumnModel().getColumn(2).setMinWidth(320);

         tblCorreosLeidos.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(320);
         tblCorreosLeidos.getTableHeader().getColumnModel().getColumn(2).setMinWidth(320);

         tblMailDelivery.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(320);
         tblMailDelivery.getTableHeader().getColumnModel().getColumn(2).setMinWidth(320);

         tblSinRespuesta.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(320);
         tblSinRespuesta.getTableHeader().getColumnModel().getColumn(2).setMinWidth(320);

         tblNoEnviados.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(320);
         tblNoEnviados.getTableHeader().getColumnModel().getColumn(2).setMinWidth(320);
        
         */
        //Ajuste de la Columna Numero en ambas tablas
        tblResultadoCorreoNoLeido.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(90);
        tblResultadoCorreoNoLeido.getTableHeader().getColumnModel().getColumn(3).setMinWidth(90);

        tblCorreosLeidos.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(90);
        tblCorreosLeidos.getTableHeader().getColumnModel().getColumn(3).setMinWidth(90);

        tblMailDelivery.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(90);
        tblMailDelivery.getTableHeader().getColumnModel().getColumn(3).setMinWidth(90);

        tblSinRespuesta.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(90);
        tblSinRespuesta.getTableHeader().getColumnModel().getColumn(3).setMinWidth(90);

        tblNoEnviados.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(90);
        tblNoEnviados.getTableHeader().getColumnModel().getColumn(3).setMinWidth(90);
        /*
         //Ajuste de la Columna Region a ambas tablas
         tblResultadoCorreoNoLeido.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(300);
         tblResultadoCorreoNoLeido.getTableHeader().getColumnModel().getColumn(5).setMinWidth(300);

         tblCorreosLeidos.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(300);
         tblCorreosLeidos.getTableHeader().getColumnModel().getColumn(5).setMinWidth(300);

         tblMailDelivery.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(300);
         tblMailDelivery.getTableHeader().getColumnModel().getColumn(5).setMinWidth(300);

         tblSinRespuesta.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(300);
         tblSinRespuesta.getTableHeader().getColumnModel().getColumn(5).setMinWidth(300);

         tblNoEnviados.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(300);
         tblNoEnviados.getTableHeader().getColumnModel().getColumn(5).setMinWidth(300);
         */
        //Ajuste de la Columna Estado a todas las tablas
        tblResultadoCorreoNoLeido.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(140);
        tblResultadoCorreoNoLeido.getTableHeader().getColumnModel().getColumn(7).setMinWidth(140);

        tblCorreosLeidos.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(140);
        tblCorreosLeidos.getTableHeader().getColumnModel().getColumn(7).setMinWidth(140);

        tblMailDelivery.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(140);
        tblMailDelivery.getTableHeader().getColumnModel().getColumn(7).setMinWidth(140);

        tblSinRespuesta.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(140);
        tblSinRespuesta.getTableHeader().getColumnModel().getColumn(7).setMinWidth(140);

        tblNoEnviados.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(140);
        tblNoEnviados.getTableHeader().getColumnModel().getColumn(7).setMinWidth(140);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel4 = new javax.swing.JPanel();
        elementos = new javax.swing.JPanel();
        btnVolver = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lbCodigo = new javax.swing.JLabel();
        lblIDUsuario = new javax.swing.JLabel();
        lblCredencial = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_NoEnviados = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNoEnviados = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jTabbedPane6 = new javax.swing.JTabbedPane();
        PnNombre4 = new javax.swing.JPanel();
        txtNombre4 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        PnCorreo4 = new javax.swing.JPanel();
        txtCorreo4 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        PnUbicacion4 = new javax.swing.JPanel();
        cboComuna4 = new javax.swing.JComboBox<String>();
        cboRegion4 = new javax.swing.JComboBox<String>();
        PnNombreOrganizacion4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txt_NombreOrganizacion4 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrllPnNoEnviados1 = new javax.swing.JScrollPane();
        tblSinRespuesta = new javax.swing.JTable();
        btnMailEnviado2 = new javax.swing.JButton();
        btnMailDeliverySR = new javax.swing.JButton();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        PnNombre3 = new javax.swing.JPanel();
        txtNombre3 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        PnCorreo3 = new javax.swing.JPanel();
        txtCorreo3 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        PnUbicacion3 = new javax.swing.JPanel();
        cboComuna3 = new javax.swing.JComboBox<String>();
        cboRegion3 = new javax.swing.JComboBox<String>();
        PnNombreOrganizacion3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txt_NombreOrganizacion3 = new javax.swing.JTextField();
        btnMailNoEnviado2 = new javax.swing.JButton();
        Jpnl_CorreosNoEnviados = new javax.swing.JPanel();
        jScrllPnNoEnviados = new javax.swing.JScrollPane();
        tblResultadoCorreoNoLeido = new javax.swing.JTable();
        btnMailEnviado = new javax.swing.JButton();
        btnMailDeliveryNE = new javax.swing.JButton();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        PnNombre1 = new javax.swing.JPanel();
        txtNombre1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        PnCorreo1 = new javax.swing.JPanel();
        txtCorreo1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        PnUbicacion1 = new javax.swing.JPanel();
        cboComuna1 = new javax.swing.JComboBox<String>();
        cboRegion1 = new javax.swing.JComboBox<String>();
        PnNombreOrganizacion1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txt_NombreOrganizacion1 = new javax.swing.JTextField();
        Jpnl_CorreosEnviados = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCorreosLeidos = new javax.swing.JTable();
        btnMailNoEnviado = new javax.swing.JButton();
        btnMailDeliveryCE = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        PnNombre = new javax.swing.JPanel();
        txtNombre = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        PnCorreo = new javax.swing.JPanel();
        txtCorreo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        PnUbicacion = new javax.swing.JPanel();
        cboComuna = new javax.swing.JComboBox<String>();
        cboRegion = new javax.swing.JComboBox<String>();
        PnNombreOrganizacion = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txt_NombreOrganizacion = new javax.swing.JTextField();
        Jpnl_MailDelivery = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblMailDelivery = new javax.swing.JTable();
        btnMailNoEnviado1 = new javax.swing.JButton();
        btnMailEnviado1 = new javax.swing.JButton();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        PnNombre2 = new javax.swing.JPanel();
        txtNombre2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        PnCorreo2 = new javax.swing.JPanel();
        txtCorreo2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        PnUbicacion2 = new javax.swing.JPanel();
        cboComuna2 = new javax.swing.JComboBox<String>();
        cboRegion2 = new javax.swing.JComboBox<String>();
        PnNombreOrganizacion2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txt_NombreOrganizacion2 = new javax.swing.JTextField();
        b_Titulo = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Listado de Contactos");
        setMaximumSize(new java.awt.Dimension(1280, 685));
        setMinimumSize(new java.awt.Dimension(1280, 685));
        setPreferredSize(new java.awt.Dimension(1280, 685));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLayeredPane1.setMaximumSize(new java.awt.Dimension(1280, 686));
        jLayeredPane1.setMinimumSize(new java.awt.Dimension(1280, 686));

        elementos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnVolver.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        jLabel2.setToolTipText("");

        lbCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lblCredencial.setVisible(false);
        lblCredencial.setText("jLabel4");

        tbl_NoEnviados.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tbl_NoEnviados.setMaximumSize(new java.awt.Dimension(1000, 500));
        tbl_NoEnviados.setPreferredSize(new java.awt.Dimension(1000, 500));

        tblNoEnviados = new javax.swing.JTable(){
            boolean[] canEdit = new boolean[]{
                false, true, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        tblNoEnviados.setModel(new javax.swing.table.DefaultTableModel(
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
        tblNoEnviados.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tblNoEnviados);

        jButton1.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jButton1.setText("Correo Enviado");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTabbedPane6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtNombre4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtNombre4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre4KeyPressed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Nombre:");

        javax.swing.GroupLayout PnNombre4Layout = new javax.swing.GroupLayout(PnNombre4);
        PnNombre4.setLayout(PnNombre4Layout);
        PnNombre4Layout.setHorizontalGroup(
            PnNombre4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnNombre4Layout.createSequentialGroup()
                .addContainerGap(170, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNombre4, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(125, 125, 125))
        );
        PnNombre4Layout.setVerticalGroup(
            PnNombre4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnNombre4Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(PnNombre4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane6.addTab("Nombre ", PnNombre4);

        txtCorreo4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCorreo4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCorreo4KeyPressed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Correo:");

        javax.swing.GroupLayout PnCorreo4Layout = new javax.swing.GroupLayout(PnCorreo4);
        PnCorreo4.setLayout(PnCorreo4Layout);
        PnCorreo4Layout.setHorizontalGroup(
            PnCorreo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnCorreo4Layout.createSequentialGroup()
                .addContainerGap(179, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCorreo4, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(126, 126, 126))
        );
        PnCorreo4Layout.setVerticalGroup(
            PnCorreo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnCorreo4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PnCorreo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCorreo4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addContainerGap(119, Short.MAX_VALUE))
        );

        jTabbedPane6.addTab("Correo", PnCorreo4);

        cboComuna4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboComuna4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione su comuna" }));
        cboComuna4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboComuna4ItemStateChanged(evt);
            }
        });

        cboRegion4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboRegion4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione Region", "Todas las regiones" }));
        cboRegion4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboRegion4ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout PnUbicacion4Layout = new javax.swing.GroupLayout(PnUbicacion4);
        PnUbicacion4.setLayout(PnUbicacion4Layout);
        PnUbicacion4Layout.setHorizontalGroup(
            PnUbicacion4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnUbicacion4Layout.createSequentialGroup()
                .addContainerGap(215, Short.MAX_VALUE)
                .addGroup(PnUbicacion4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboRegion4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboComuna4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(215, Short.MAX_VALUE))
        );
        PnUbicacion4Layout.setVerticalGroup(
            PnUbicacion4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnUbicacion4Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(cboRegion4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(cboComuna4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane6.addTab("Ubicación", PnUbicacion4);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("Nombre de la Organización:");

        txt_NombreOrganizacion4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_NombreOrganizacion4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_NombreOrganizacion4KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout PnNombreOrganizacion4Layout = new javax.swing.GroupLayout(PnNombreOrganizacion4);
        PnNombreOrganizacion4.setLayout(PnNombreOrganizacion4Layout);
        PnNombreOrganizacion4Layout.setHorizontalGroup(
            PnNombreOrganizacion4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnNombreOrganizacion4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_NombreOrganizacion4, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addContainerGap())
        );
        PnNombreOrganizacion4Layout.setVerticalGroup(
            PnNombreOrganizacion4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnNombreOrganizacion4Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(PnNombreOrganizacion4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txt_NombreOrganizacion4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane6.addTab("Nombre Organización", PnNombreOrganizacion4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 970, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTabbedPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbl_NoEnviados.addTab("Correo no enviado", jPanel1);

        jPanel2.setMaximumSize(new java.awt.Dimension(1000, 500));
        jPanel2.setPreferredSize(new java.awt.Dimension(1000, 500));

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jScrllPnNoEnviados1.setMaximumSize(new java.awt.Dimension(452, 402));
        jScrllPnNoEnviados1.setMinimumSize(new java.awt.Dimension(452, 402));

        tblSinRespuesta = new javax.swing.JTable(){
            boolean[] canEdit = new boolean[]{
                false, true, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        tblSinRespuesta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSinRespuesta.getTableHeader().setReorderingAllowed(false);
        jScrllPnNoEnviados1.setViewportView(tblSinRespuesta);

        btnMailEnviado2.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        btnMailEnviado2.setText("Correo Leído");
        btnMailEnviado2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMailEnviado2ActionPerformed(evt);
            }
        });

        btnMailDeliverySR.setBackground(new java.awt.Color(204, 255, 204));
        btnMailDeliverySR.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        btnMailDeliverySR.setText("Mail Delivery");
        btnMailDeliverySR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMailDeliverySRActionPerformed(evt);
            }
        });

        jTabbedPane5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtNombre3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtNombre3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre3KeyPressed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Nombre:");

        javax.swing.GroupLayout PnNombre3Layout = new javax.swing.GroupLayout(PnNombre3);
        PnNombre3.setLayout(PnNombre3Layout);
        PnNombre3Layout.setHorizontalGroup(
            PnNombre3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnNombre3Layout.createSequentialGroup()
                .addContainerGap(225, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNombre3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(125, 125, 125))
        );
        PnNombre3Layout.setVerticalGroup(
            PnNombre3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnNombre3Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(PnNombre3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab("Nombre ", PnNombre3);

        txtCorreo3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCorreo3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCorreo3KeyPressed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Correo:");

        javax.swing.GroupLayout PnCorreo3Layout = new javax.swing.GroupLayout(PnCorreo3);
        PnCorreo3.setLayout(PnCorreo3Layout);
        PnCorreo3Layout.setHorizontalGroup(
            PnCorreo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnCorreo3Layout.createSequentialGroup()
                .addContainerGap(234, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCorreo3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(126, 126, 126))
        );
        PnCorreo3Layout.setVerticalGroup(
            PnCorreo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnCorreo3Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(PnCorreo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCorreo3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab("Correo", PnCorreo3);

        cboComuna3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboComuna3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione su comuna" }));
        cboComuna3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboComuna3ItemStateChanged(evt);
            }
        });

        cboRegion3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboRegion3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione Region" }));
        cboRegion3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboRegion3ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout PnUbicacion3Layout = new javax.swing.GroupLayout(PnUbicacion3);
        PnUbicacion3.setLayout(PnUbicacion3Layout);
        PnUbicacion3Layout.setHorizontalGroup(
            PnUbicacion3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnUbicacion3Layout.createSequentialGroup()
                .addContainerGap(242, Short.MAX_VALUE)
                .addGroup(PnUbicacion3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboRegion3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboComuna3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(243, Short.MAX_VALUE))
        );
        PnUbicacion3Layout.setVerticalGroup(
            PnUbicacion3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnUbicacion3Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(cboRegion3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(cboComuna3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab("Ubicación", PnUbicacion3);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("Nombre de la Organización:");

        txt_NombreOrganizacion3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_NombreOrganizacion3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_NombreOrganizacion3KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout PnNombreOrganizacion3Layout = new javax.swing.GroupLayout(PnNombreOrganizacion3);
        PnNombreOrganizacion3.setLayout(PnNombreOrganizacion3Layout);
        PnNombreOrganizacion3Layout.setHorizontalGroup(
            PnNombreOrganizacion3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnNombreOrganizacion3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_NombreOrganizacion3, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
                .addContainerGap())
        );
        PnNombreOrganizacion3Layout.setVerticalGroup(
            PnNombreOrganizacion3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnNombreOrganizacion3Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(PnNombreOrganizacion3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txt_NombreOrganizacion3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab("Nombre Organización", PnNombreOrganizacion3);

        btnMailNoEnviado2.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        btnMailNoEnviado2.setText("Correo No Leído");
        btnMailNoEnviado2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMailNoEnviado2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnMailEnviado2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMailNoEnviado2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMailDeliverySR))
                            .addComponent(jTabbedPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(437, Short.MAX_VALUE))
                    .addComponent(jScrllPnNoEnviados1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrllPnNoEnviados1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMailEnviado2)
                    .addComponent(btnMailDeliverySR)
                    .addComponent(btnMailNoEnviado2))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Sin respuesta", jPanel3);

        jScrllPnNoEnviados.setMaximumSize(new java.awt.Dimension(452, 402));
        jScrllPnNoEnviados.setMinimumSize(new java.awt.Dimension(452, 402));

        tblResultadoCorreoNoLeido = new javax.swing.JTable(){
            boolean[] canEdit = new boolean[]{
                false, true, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        tblResultadoCorreoNoLeido.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrllPnNoEnviados.setViewportView(tblResultadoCorreoNoLeido);

        btnMailEnviado.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        btnMailEnviado.setText("Correo Leído");
        btnMailEnviado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMailEnviadoActionPerformed(evt);
            }
        });

        btnMailDeliveryNE.setBackground(new java.awt.Color(204, 0, 0));
        btnMailDeliveryNE.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        btnMailDeliveryNE.setText("Mail Delivery");
        btnMailDeliveryNE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMailDeliveryNEActionPerformed(evt);
            }
        });

        jTabbedPane3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtNombre1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtNombre1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre1KeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Nombre:");

        javax.swing.GroupLayout PnNombre1Layout = new javax.swing.GroupLayout(PnNombre1);
        PnNombre1.setLayout(PnNombre1Layout);
        PnNombre1Layout.setHorizontalGroup(
            PnNombre1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnNombre1Layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(125, 125, 125))
        );
        PnNombre1Layout.setVerticalGroup(
            PnNombre1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnNombre1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(PnNombre1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Nombre ", PnNombre1);

        txtCorreo1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCorreo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCorreo1KeyPressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Correo:");

        javax.swing.GroupLayout PnCorreo1Layout = new javax.swing.GroupLayout(PnCorreo1);
        PnCorreo1.setLayout(PnCorreo1Layout);
        PnCorreo1Layout.setHorizontalGroup(
            PnCorreo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnCorreo1Layout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCorreo1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(126, 126, 126))
        );
        PnCorreo1Layout.setVerticalGroup(
            PnCorreo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnCorreo1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(PnCorreo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCorreo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Correo", PnCorreo1);

        cboComuna1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboComuna1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione su comuna" }));
        cboComuna1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboComuna1ItemStateChanged(evt);
            }
        });

        cboRegion1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboRegion1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione Region", "Todas las regiones" }));
        cboRegion1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboRegion1ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout PnUbicacion1Layout = new javax.swing.GroupLayout(PnUbicacion1);
        PnUbicacion1.setLayout(PnUbicacion1Layout);
        PnUbicacion1Layout.setHorizontalGroup(
            PnUbicacion1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnUbicacion1Layout.createSequentialGroup()
                .addContainerGap(152, Short.MAX_VALUE)
                .addGroup(PnUbicacion1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboRegion1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboComuna1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(153, Short.MAX_VALUE))
        );
        PnUbicacion1Layout.setVerticalGroup(
            PnUbicacion1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnUbicacion1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(cboRegion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(cboComuna1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Ubicación", PnUbicacion1);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("Nombre de la Organización:");

        txt_NombreOrganizacion1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_NombreOrganizacion1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_NombreOrganizacion1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout PnNombreOrganizacion1Layout = new javax.swing.GroupLayout(PnNombreOrganizacion1);
        PnNombreOrganizacion1.setLayout(PnNombreOrganizacion1Layout);
        PnNombreOrganizacion1Layout.setHorizontalGroup(
            PnNombreOrganizacion1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnNombreOrganizacion1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_NombreOrganizacion1, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                .addContainerGap())
        );
        PnNombreOrganizacion1Layout.setVerticalGroup(
            PnNombreOrganizacion1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnNombreOrganizacion1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(PnNombreOrganizacion1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txt_NombreOrganizacion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Nombre Organización", PnNombreOrganizacion1);

        javax.swing.GroupLayout Jpnl_CorreosNoEnviadosLayout = new javax.swing.GroupLayout(Jpnl_CorreosNoEnviados);
        Jpnl_CorreosNoEnviados.setLayout(Jpnl_CorreosNoEnviadosLayout);
        Jpnl_CorreosNoEnviadosLayout.setHorizontalGroup(
            Jpnl_CorreosNoEnviadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Jpnl_CorreosNoEnviadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Jpnl_CorreosNoEnviadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Jpnl_CorreosNoEnviadosLayout.createSequentialGroup()
                        .addGroup(Jpnl_CorreosNoEnviadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Jpnl_CorreosNoEnviadosLayout.createSequentialGroup()
                                .addComponent(btnMailEnviado, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMailDeliveryNE))
                            .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Jpnl_CorreosNoEnviadosLayout.createSequentialGroup()
                        .addComponent(jScrllPnNoEnviados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        Jpnl_CorreosNoEnviadosLayout.setVerticalGroup(
            Jpnl_CorreosNoEnviadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Jpnl_CorreosNoEnviadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrllPnNoEnviados, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(Jpnl_CorreosNoEnviadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMailEnviado)
                    .addComponent(btnMailDeliveryNE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Correo no leído", Jpnl_CorreosNoEnviados);

        jScrollPane1.setMaximumSize(new java.awt.Dimension(452, 402));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(452, 402));

        tblCorreosLeidos = new javax.swing.JTable(){
            boolean[] canEdit = new boolean[]{
                false, true, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        tblCorreosLeidos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblCorreosLeidos);

        btnMailNoEnviado.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        btnMailNoEnviado.setText("Correo No Leído");
        btnMailNoEnviado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMailNoEnviadoActionPerformed(evt);
            }
        });

        btnMailDeliveryCE.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        btnMailDeliveryCE.setText("Mail Delivery");
        btnMailDeliveryCE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMailDeliveryCEActionPerformed(evt);
            }
        });

        jTabbedPane2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtNombre.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Nombre:");

        javax.swing.GroupLayout PnNombreLayout = new javax.swing.GroupLayout(PnNombre);
        PnNombre.setLayout(PnNombreLayout);
        PnNombreLayout.setHorizontalGroup(
            PnNombreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnNombreLayout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(125, 125, 125))
        );
        PnNombreLayout.setVerticalGroup(
            PnNombreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnNombreLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(PnNombreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Nombre ", PnNombre);

        txtCorreo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCorreo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCorreoKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Correo:");

        javax.swing.GroupLayout PnCorreoLayout = new javax.swing.GroupLayout(PnCorreo);
        PnCorreo.setLayout(PnCorreoLayout);
        PnCorreoLayout.setHorizontalGroup(
            PnCorreoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnCorreoLayout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(126, 126, 126))
        );
        PnCorreoLayout.setVerticalGroup(
            PnCorreoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnCorreoLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(PnCorreoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Correo", PnCorreo);

        cboComuna.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboComuna.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione su comuna" }));
        cboComuna.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboComunaItemStateChanged(evt);
            }
        });

        cboRegion.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboRegion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione Region", "Todas las regiones" }));
        cboRegion.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboRegionItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout PnUbicacionLayout = new javax.swing.GroupLayout(PnUbicacion);
        PnUbicacion.setLayout(PnUbicacionLayout);
        PnUbicacionLayout.setHorizontalGroup(
            PnUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnUbicacionLayout.createSequentialGroup()
                .addContainerGap(152, Short.MAX_VALUE)
                .addGroup(PnUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboRegion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboComuna, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(153, Short.MAX_VALUE))
        );
        PnUbicacionLayout.setVerticalGroup(
            PnUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnUbicacionLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(cboRegion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(cboComuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Ubicación", PnUbicacion);

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("Nombre de la Organización:");

        txt_NombreOrganizacion.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_NombreOrganizacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_NombreOrganizacionKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout PnNombreOrganizacionLayout = new javax.swing.GroupLayout(PnNombreOrganizacion);
        PnNombreOrganizacion.setLayout(PnNombreOrganizacionLayout);
        PnNombreOrganizacionLayout.setHorizontalGroup(
            PnNombreOrganizacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnNombreOrganizacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_NombreOrganizacion, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                .addContainerGap())
        );
        PnNombreOrganizacionLayout.setVerticalGroup(
            PnNombreOrganizacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnNombreOrganizacionLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(PnNombreOrganizacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txt_NombreOrganizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Nombre Organización", PnNombreOrganizacion);

        javax.swing.GroupLayout Jpnl_CorreosEnviadosLayout = new javax.swing.GroupLayout(Jpnl_CorreosEnviados);
        Jpnl_CorreosEnviados.setLayout(Jpnl_CorreosEnviadosLayout);
        Jpnl_CorreosEnviadosLayout.setHorizontalGroup(
            Jpnl_CorreosEnviadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Jpnl_CorreosEnviadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Jpnl_CorreosEnviadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Jpnl_CorreosEnviadosLayout.createSequentialGroup()
                        .addGroup(Jpnl_CorreosEnviadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Jpnl_CorreosEnviadosLayout.createSequentialGroup()
                                .addComponent(btnMailNoEnviado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMailDeliveryCE))
                            .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Jpnl_CorreosEnviadosLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        Jpnl_CorreosEnviadosLayout.setVerticalGroup(
            Jpnl_CorreosEnviadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Jpnl_CorreosEnviadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(Jpnl_CorreosEnviadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMailNoEnviado)
                    .addComponent(btnMailDeliveryCE))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Correo Leído", Jpnl_CorreosEnviados);

        jScrollPane3.setMaximumSize(new java.awt.Dimension(452, 402));
        jScrollPane3.setMinimumSize(new java.awt.Dimension(452, 402));

        tblMailDelivery = new javax.swing.JTable(){
            boolean[] canEdit = new boolean[]{
                false, true, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        tblMailDelivery.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblMailDelivery);

        btnMailNoEnviado1.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        btnMailNoEnviado1.setText("Correo No Leído");
        btnMailNoEnviado1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMailNoEnviado1ActionPerformed(evt);
            }
        });

        btnMailEnviado1.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        btnMailEnviado1.setText("Correo Leído");
        btnMailEnviado1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMailEnviado1ActionPerformed(evt);
            }
        });

        jTabbedPane4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtNombre2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtNombre2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombre2KeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Nombre:");

        javax.swing.GroupLayout PnNombre2Layout = new javax.swing.GroupLayout(PnNombre2);
        PnNombre2.setLayout(PnNombre2Layout);
        PnNombre2Layout.setHorizontalGroup(
            PnNombre2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnNombre2Layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNombre2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(125, 125, 125))
        );
        PnNombre2Layout.setVerticalGroup(
            PnNombre2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnNombre2Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(PnNombre2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Nombre ", PnNombre2);

        txtCorreo2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCorreo2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCorreo2KeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Correo:");

        javax.swing.GroupLayout PnCorreo2Layout = new javax.swing.GroupLayout(PnCorreo2);
        PnCorreo2.setLayout(PnCorreo2Layout);
        PnCorreo2Layout.setHorizontalGroup(
            PnCorreo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnCorreo2Layout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCorreo2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(126, 126, 126))
        );
        PnCorreo2Layout.setVerticalGroup(
            PnCorreo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnCorreo2Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(PnCorreo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCorreo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Correo", PnCorreo2);

        cboComuna2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboComuna2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione su comuna" }));
        cboComuna2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboComuna2ItemStateChanged(evt);
            }
        });

        cboRegion2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboRegion2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione Region", "Todas las regiones" }));
        cboRegion2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboRegion2ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout PnUbicacion2Layout = new javax.swing.GroupLayout(PnUbicacion2);
        PnUbicacion2.setLayout(PnUbicacion2Layout);
        PnUbicacion2Layout.setHorizontalGroup(
            PnUbicacion2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnUbicacion2Layout.createSequentialGroup()
                .addContainerGap(152, Short.MAX_VALUE)
                .addGroup(PnUbicacion2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboRegion2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboComuna2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(153, Short.MAX_VALUE))
        );
        PnUbicacion2Layout.setVerticalGroup(
            PnUbicacion2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnUbicacion2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(cboRegion2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(cboComuna2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Ubicación", PnUbicacion2);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("Nombre de la Organización:");

        txt_NombreOrganizacion2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_NombreOrganizacion2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_NombreOrganizacion2KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout PnNombreOrganizacion2Layout = new javax.swing.GroupLayout(PnNombreOrganizacion2);
        PnNombreOrganizacion2.setLayout(PnNombreOrganizacion2Layout);
        PnNombreOrganizacion2Layout.setHorizontalGroup(
            PnNombreOrganizacion2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnNombreOrganizacion2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_NombreOrganizacion2, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                .addContainerGap())
        );
        PnNombreOrganizacion2Layout.setVerticalGroup(
            PnNombreOrganizacion2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnNombreOrganizacion2Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(PnNombreOrganizacion2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txt_NombreOrganizacion2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Nombre Organización", PnNombreOrganizacion2);

        javax.swing.GroupLayout Jpnl_MailDeliveryLayout = new javax.swing.GroupLayout(Jpnl_MailDelivery);
        Jpnl_MailDelivery.setLayout(Jpnl_MailDeliveryLayout);
        Jpnl_MailDeliveryLayout.setHorizontalGroup(
            Jpnl_MailDeliveryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Jpnl_MailDeliveryLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Jpnl_MailDeliveryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Jpnl_MailDeliveryLayout.createSequentialGroup()
                        .addGroup(Jpnl_MailDeliveryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Jpnl_MailDeliveryLayout.createSequentialGroup()
                                .addComponent(btnMailEnviado1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMailNoEnviado1))
                            .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 605, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        Jpnl_MailDeliveryLayout.setVerticalGroup(
            Jpnl_MailDeliveryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Jpnl_MailDeliveryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(Jpnl_MailDeliveryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMailEnviado1)
                    .addComponent(btnMailNoEnviado1))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Mail Delivery", Jpnl_MailDelivery);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tbl_NoEnviados.addTab("Correo enviado", jPanel2);

        jScrollPane4.setViewportView(tbl_NoEnviados);

        javax.swing.GroupLayout elementosLayout = new javax.swing.GroupLayout(elementos);
        elementos.setLayout(elementosLayout);
        elementosLayout.setHorizontalGroup(
            elementosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(elementosLayout.createSequentialGroup()
                .addGroup(elementosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(elementosLayout.createSequentialGroup()
                        .addGap(1001, 1001, 1001)
                        .addComponent(lblCredencial)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                        .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(elementosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4)))
                .addContainerGap())
            .addGroup(elementosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(elementosLayout.createSequentialGroup()
                    .addGap(0, 571, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 570, Short.MAX_VALUE)))
            .addGroup(elementosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(elementosLayout.createSequentialGroup()
                    .addGap(0, 570, Short.MAX_VALUE)
                    .addComponent(lbCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 569, Short.MAX_VALUE)))
            .addGroup(elementosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(elementosLayout.createSequentialGroup()
                    .addGap(0, 536, Short.MAX_VALUE)
                    .addComponent(lblIDUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 536, Short.MAX_VALUE)))
        );
        elementosLayout.setVerticalGroup(
            elementosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, elementosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(elementosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVolver)
                    .addComponent(lblCredencial))
                .addContainerGap())
            .addGroup(elementosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(elementosLayout.createSequentialGroup()
                    .addGap(0, 233, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 232, Short.MAX_VALUE)))
            .addGroup(elementosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(elementosLayout.createSequentialGroup()
                    .addGap(0, 233, Short.MAX_VALUE)
                    .addComponent(lbCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 232, Short.MAX_VALUE)))
            .addGroup(elementosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(elementosLayout.createSequentialGroup()
                    .addGap(0, 233, Short.MAX_VALUE)
                    .addComponent(lblIDUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 233, Short.MAX_VALUE)))
        );

        b_Titulo.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        b_Titulo.setText("Listado de contactos");

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PlataformaVentas/Imagenes/acima-logo-200p.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(elementos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(349, 349, 349)
                        .addComponent(b_Titulo)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(b_Titulo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(elementos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(33, 33, 33))
        );

        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PlataformaVentas/Imagenes/BackgroundNew.png"))); // NOI18N
        fondo.setAutoscrolls(true);
        fondo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fondo, javax.swing.GroupLayout.PREFERRED_SIZE, 1460, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fondo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 686, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jLayeredPane1.setLayer(jPanel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(fondo, javax.swing.JLayeredPane.DEFAULT_LAYER);

        getContentPane().add(jLayeredPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed
    public void LlenarTabla() {
        try {
            String query = queryListaContactos
                    + "where co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta ='Correo No Leido' AND co.IDusuario = ? \n"
                    + "group by co.IDContacto";
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, lblIDUsuario.getText());
            ResultSet rs = pst.executeQuery();
            tblResultadoCorreoNoLeido.setModel(DbUtils.resultSetToTableModel(rs));

            String query2 = queryListaContactos
                    + "where co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Correo Leido' AND co.IDusuario = ? \n"
                    + "group by co.IDContacto";
            PreparedStatement pst2 = cn.prepareStatement(query2);
            pst2.setString(1, lblIDUsuario.getText());
            ResultSet rs2 = pst2.executeQuery();
            tblCorreosLeidos.setModel(DbUtils.resultSetToTableModel(rs2));

            String query3 = queryListaContactos
                    + "where co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Mail Delivery' AND co.IDusuario = ?\n"
                    + "group by co.IDContacto";
            PreparedStatement pst3 = cn.prepareStatement(query3);
            pst3.setString(1, lblIDUsuario.getText());
            ResultSet rs3 = pst3.executeQuery();
            tblMailDelivery.setModel(DbUtils.resultSetToTableModel(rs3));

            String query4 = queryListaContactos
                    + "where co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Sin Respuesta' AND co.IDusuario = ?\n"
                    + "group by co.IDContacto";
            PreparedStatement pst4 = cn.prepareStatement(query4);
            pst4.setString(1, lblIDUsuario.getText());
            ResultSet rs4 = pst4.executeQuery();
            tblSinRespuesta.setModel(DbUtils.resultSetToTableModel(rs4));

            String query5 = queryListaContactos
                    + "where co.EstadoMail = 'Correo No Enviado' AND co.IDusuario = ?\n"
                    + "group by co.IDContacto";
            PreparedStatement pst5 = cn.prepareStatement(query5);
            pst5.setString(1, lblIDUsuario.getText());
            ResultSet rs5 = pst5.executeQuery();
            tblNoEnviados.setModel(DbUtils.resultSetToTableModel(rs5));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        OcultarDatos();
    }

    private void btnMailEnviadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMailEnviadoActionPerformed
        if (tblResultadoCorreoNoLeido.getSelectedRow() != -1) {
            String respuesta = JOptionPane.showInputDialog(null, "Escriba Su codigo de autorización.", "Permisos de acción", JOptionPane.INFORMATION_MESSAGE);
            DefaultTableModel modelo = (DefaultTableModel) tblResultadoCorreoNoLeido.getModel();
            int codigo = Integer.parseInt((modelo.getValueAt(tblResultadoCorreoNoLeido.getSelectedRow(), 0)).toString());

            if (lbCodigo.getText().equals(respuesta)) {
                JOptionPane.showMessageDialog(null, "Codigo de autorización Correcto.", "Correcto", JOptionPane.INFORMATION_MESSAGE);

                try {
                    String Comentario = "Se cambio el EstadoMail del contacto id nro." + codigo + "a leido por el empleado con id nro. " + lblIDUsuario.getText();

                    String query = "INSERT INTO movimientosventas (IDUsuario,Movimiento) VALUES (?,?)";
                    PreparedStatement pst = cn.prepareStatement(query);
                    pst.setInt(1, Integer.parseInt(lblIDUsuario.getText()));
                    pst.setString(2, Comentario);
                    int up = pst.executeUpdate();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }

                try {
                    String query = "Update contactos SET EstadoRespuesta = 'Correo Leido' where IDContacto = ? ";
                    PreparedStatement pst = cn.prepareStatement(query);
                    pst.setInt(1, codigo);
                    int up = pst.executeUpdate();
                    DefaultTableModel model = (DefaultTableModel) tblResultadoCorreoNoLeido.getModel();
                    model.setRowCount(0);

                    LlenarTabla();
                    String credencial;
                    int idUsuario = 0;
                    credencial = lblCredencial.getText();
                    idUsuario = Integer.parseInt(lblIDUsuario.getText());
                    String query3 = "INSERT INTO movimientosventas(`IDUsuario`, `FechaMovimiento`, `Movimiento`) VALUES (?, CURRENT_TIMESTAMP, 'Llamó Cliente')";
                    PreparedStatement pst3 = cn.prepareStatement(query3);
                    pst3.setInt(1, idUsuario);
                    up = pst3.executeUpdate();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Codigo de autorizacion invalido", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para proceder.");
        }
    }//GEN-LAST:event_btnMailEnviadoActionPerformed

    private void btnMailNoEnviadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMailNoEnviadoActionPerformed
        if (tblCorreosLeidos.getSelectedRow() != -1) {

            String respuesta = JOptionPane.showInputDialog(null, "Escriba Su codigo de autorización.", "Permisos de acción", JOptionPane.INFORMATION_MESSAGE);
            DefaultTableModel modelo = (DefaultTableModel) tblCorreosLeidos.getModel();
            int codigo = Integer.parseInt((modelo.getValueAt(tblCorreosLeidos.getSelectedRow(), 0)).toString());

            if (lbCodigo.getText().equals(respuesta)) {
                JOptionPane.showMessageDialog(null, "Codigo de autorización Correcto.", "Correcto", JOptionPane.INFORMATION_MESSAGE);

                try {
                    String Comentario = "Se cambio el EstadoMail del contacto id nro." + codigo + "a no leido por el empleado con id nro. " + lblIDUsuario.getText();

                    String query = "INSERT INTO movimientosventas (IDUsuario,Movimiento) VALUES (?,?)";
                    PreparedStatement pst = cn.prepareStatement(query);
                    pst.setInt(1, Integer.parseInt(lblIDUsuario.getText()));
                    pst.setString(2, Comentario);
                    int up = pst.executeUpdate();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
                try {
                    String query = "Update contactos SET EstadoRespuesta = 'Correo No Leido' where IDContacto = ? ";
                    PreparedStatement pst = cn.prepareStatement(query);
                    pst.setInt(1, codigo);
                    int up = pst.executeUpdate();
                    DefaultTableModel model = (DefaultTableModel) tblCorreosLeidos.getModel();
                    model.setRowCount(0);

                    LlenarTabla();
                    String credencial;
                    int idUsuario = 0;
                    credencial = lblCredencial.getText();
                    idUsuario = Integer.parseInt(lblIDUsuario.getText());
                    String query3 = "INSERT INTO movimientosventas(`IDUsuario`, `FechaMovimiento`, `Movimiento`) VALUES (?, CURRENT_TIMESTAMP, 'Llamó Cliente')";
                    PreparedStatement pst3 = cn.prepareStatement(query3);
                    pst3.setInt(1, idUsuario);
                    up = pst3.executeUpdate();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Codigo de autorizacion invalido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para proceder.");
        }
    }//GEN-LAST:event_btnMailNoEnviadoActionPerformed

    private void btnMailDeliveryCEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMailDeliveryCEActionPerformed
        if (tblCorreosLeidos.getSelectedRow() != -1) {

            String respuesta = JOptionPane.showInputDialog(null, "Escriba Su codigo de autorización.", "Permisos de acción", JOptionPane.INFORMATION_MESSAGE);
            DefaultTableModel modelo = (DefaultTableModel) tblCorreosLeidos.getModel();
            int codigo = Integer.parseInt((modelo.getValueAt(tblCorreosLeidos.getSelectedRow(), 0)).toString());

            if (lbCodigo.getText().equals(respuesta)) {
                JOptionPane.showMessageDialog(null, "Codigo de autorización Correcto.", "Correcto", JOptionPane.INFORMATION_MESSAGE);

                try {
                    String Comentario = "Se cambio el EstadoMail del contacto id nro." + codigo + "a Mail Delivery por el empleado con id nro. " + lblIDUsuario.getText();

                    String query = "INSERT INTO movimientosventas (IDUsuario,Movimiento) VALUES (?,?)";
                    PreparedStatement pst = cn.prepareStatement(query);
                    pst.setInt(1, Integer.parseInt(lblIDUsuario.getText()));
                    pst.setString(2, Comentario);
                    int up = pst.executeUpdate();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
                try {
                    String query = "Update contactos SET EstadoRespuesta = 'Mail Delivery' where IDContacto = ? ";
                    PreparedStatement pst = cn.prepareStatement(query);
                    pst.setInt(1, codigo);
                    int up = pst.executeUpdate();
                    DefaultTableModel model = (DefaultTableModel) tblCorreosLeidos.getModel();
                    model.setRowCount(0);

                    LlenarTabla();
                    String credencial;
                    int idUsuario = 0;
                    credencial = lblCredencial.getText();
                    idUsuario = Integer.parseInt(lblIDUsuario.getText());
                    String query3 = "INSERT INTO movimientosventas(`IDUsuario`, `FechaMovimiento`, `Movimiento`) VALUES (?, CURRENT_TIMESTAMP, 'Llamó Cliente')";
                    PreparedStatement pst3 = cn.prepareStatement(query3);
                    pst3.setInt(1, idUsuario);
                    up = pst3.executeUpdate();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Codigo de autorizacion invalido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para proceder.");
        }
    }//GEN-LAST:event_btnMailDeliveryCEActionPerformed

    private void btnMailDeliveryNEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMailDeliveryNEActionPerformed
        if (tblResultadoCorreoNoLeido.getSelectedRow() != -1) {

            String respuesta = JOptionPane.showInputDialog(null, "Escriba Su codigo de autorización.", "Permisos de acción", JOptionPane.INFORMATION_MESSAGE);
            DefaultTableModel modelo = (DefaultTableModel) tblResultadoCorreoNoLeido.getModel();
            int codigo = Integer.parseInt((modelo.getValueAt(tblResultadoCorreoNoLeido.getSelectedRow(), 0)).toString());

            if (lbCodigo.getText().equals(respuesta)) {
                JOptionPane.showMessageDialog(null, "Codigo de autoSrización Correcto.", "Correcto", JOptionPane.INFORMATION_MESSAGE);

                try {
                    String Comentario = "Se cambio el EstadoMail del contacto id nro." + codigo + "a Mail Delivery por el empleado con id nro. " + lblIDUsuario.getText();

                    String query = "INSERT INTO movimientosventas (IDUsuario,Movimiento) VALUES (?,?)";
                    PreparedStatement pst = cn.prepareStatement(query);
                    pst.setInt(1, Integer.parseInt(lblIDUsuario.getText()));
                    pst.setString(2, Comentario);
                    int up = pst.executeUpdate();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
                try {
                    String query = "Update contactos SET EstadoRespuesta = 'Mail Delivery' where IDContacto = ? ";
                    PreparedStatement pst = cn.prepareStatement(query);
                    pst.setInt(1, codigo);
                    int up = pst.executeUpdate();
                    DefaultTableModel model = (DefaultTableModel) tblResultadoCorreoNoLeido.getModel();
                    model.setRowCount(0);

                    LlenarTabla();
                    String credencial;
                    int idUsuario = 0;
                    credencial = lblCredencial.getText();
                    idUsuario = Integer.parseInt(lblIDUsuario.getText());
                    String query3 = "INSERT INTO movimientosventas(`IDUsuario`, `FechaMovimiento`, `Movimiento`) VALUES (?, CURRENT_TIMESTAMP, 'Llamó Cliente')";
                    PreparedStatement pst3 = cn.prepareStatement(query3);
                    pst3.setInt(1, idUsuario);
                    up = pst3.executeUpdate();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Codigo de autorizacion invalido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para proceder.");
        }
    }//GEN-LAST:event_btnMailDeliveryNEActionPerformed

    private void btnMailNoEnviado1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMailNoEnviado1ActionPerformed
        if (tblMailDelivery.getSelectedRow() != -1) {
            String respuesta = JOptionPane.showInputDialog(null, "Escriba Su codigo de autorización.", "Permisos de acción", JOptionPane.INFORMATION_MESSAGE);
            DefaultTableModel modelo = (DefaultTableModel) tblMailDelivery.getModel();
            int codigo = Integer.parseInt((modelo.getValueAt(tblMailDelivery.getSelectedRow(), 0)).toString());

            if (lbCodigo.getText().equals(respuesta)) {
                JOptionPane.showMessageDialog(null, "Codigo de autorización Correcto.", "Correcto", JOptionPane.INFORMATION_MESSAGE);

                try {
                    String Comentario = "Se cambio el EstadoMail del contacto id nro." + codigo + "a no leido por el empleado con id nro. " + lblIDUsuario.getText();

                    String query = "INSERT INTO movimientosventas (IDUsuario,Movimiento) VALUES (?,?)";
                    PreparedStatement pst = cn.prepareStatement(query);
                    pst.setInt(1, Integer.parseInt(lblIDUsuario.getText()));
                    pst.setString(2, Comentario);
                    int up = pst.executeUpdate();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }

                try {
                    String query = "Update contactos SET EstadoRespuesta = 'Correo No Leido' where IDContacto = ? ";
                    PreparedStatement pst = cn.prepareStatement(query);
                    pst.setInt(1, codigo);
                    int up = pst.executeUpdate();
                    DefaultTableModel model = (DefaultTableModel) tblMailDelivery.getModel();
                    model.setRowCount(0);

                    LlenarTabla();
                    String credencial;
                    int idUsuario = 0;
                    credencial = lblCredencial.getText();
                    idUsuario = Integer.parseInt(lblIDUsuario.getText());
                    String query3 = "INSERT INTO movimientosventas(`IDUsuario`, `FechaMovimiento`, `Movimiento`) VALUES (?, CURRENT_TIMESTAMP, 'Llamó Cliente')";
                    PreparedStatement pst3 = cn.prepareStatement(query3);
                    pst3.setInt(1, idUsuario);
                    up = pst3.executeUpdate();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Codigo de autorizacion invalido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para proceder.");
        }
    }//GEN-LAST:event_btnMailNoEnviado1ActionPerformed

    private void btnMailEnviado1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMailEnviado1ActionPerformed
        if (tblMailDelivery.getSelectedRow() != -1) {
            try {
                DefaultTableModel modelo = (DefaultTableModel) tblMailDelivery.getModel();
                int codigo = Integer.parseInt((modelo.getValueAt(tblMailDelivery.getSelectedRow(), 0)).toString());

                String query = "Update contactos SET EstadoRespuesta = 'Correo Leido' where IDContacto = ? ";
                PreparedStatement pst = cn.prepareStatement(query);
                pst.setInt(1, codigo);
                int up = pst.executeUpdate();
                DefaultTableModel model = (DefaultTableModel) tblMailDelivery.getModel();
                model.setRowCount(0);

                LlenarTabla();
                String credencial;
                int idUsuario = 0;
                credencial = lblCredencial.getText();
                idUsuario = Integer.parseInt(lblIDUsuario.getText());
                String query3 = "INSERT INTO movimientosventas(`IDUsuario`, `FechaMovimiento`, `Movimiento`) VALUES (?, CURRENT_TIMESTAMP, 'Llamó Cliente')";
                PreparedStatement pst3 = cn.prepareStatement(query3);
                pst3.setInt(1, idUsuario);
                up = pst3.executeUpdate();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para proceder.");
        }
    }//GEN-LAST:event_btnMailEnviado1ActionPerformed

    private void txtCorreoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreoKeyPressed
        try {
            String query = queryListaContactos
                    + "WHERE co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Correo Leido' AND co.correo like ? AND co.IDusuario = ?"
                    + "group by co.IDContacto";
            String param = txtCorreo.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, "%" + param + "%");
            pst.setString(2, lblIDUsuario.getText());
            ResultSet rs = pst.executeQuery();
            tblCorreosLeidos.setModel(DbUtils.resultSetToTableModel(rs));
            OcultarDatos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_txtCorreoKeyPressed

    private void cboRegionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboRegionItemStateChanged
        String busqueda = cboRegion.getSelectedItem().toString();
        //Filtro para todas las regiones
        if (busqueda.equals("Todas las Regiones")) {
            cboComuna.disable();
            try {
                String query = queryListaContactos
                        + "where co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Correo Leido' AND co.IDusuario = ? \n"
                        + "group by co.IDContacto";

                PreparedStatement pst = cn.prepareStatement(query);
                pst.setString(1, lblIDUsuario.getText());
                ResultSet rs = pst.executeQuery();
                tblCorreosLeidos.setModel(DbUtils.resultSetToTableModel(rs));
                OcultarDatos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "");
            }
        } else if (busqueda.equals("Seleccione Region")) {
            cboComuna.disable();
        } else {
            try {
                cboComuna.disable();
                cboComuna.removeAllItems();

                /*
                 Filtro para una region en especifica
                 */
                if (cboComuna.getItemCount() != 0) {
                    cboComuna.removeAllItems();
                }
                String query = queryListaContactos
                        + "where co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Correo Leido' AND reg.NombreRegion = ? AND co.IDusuario = ? \n"
                        + "group by co.IDContacto";
                String param = cboRegion.getSelectedItem().toString();
                PreparedStatement pst = cn.prepareStatement(query);
                pst.setString(1, param);
                pst.setString(2, lblIDUsuario.getText());
                ResultSet rs = pst.executeQuery();
                tblCorreosLeidos.setModel(DbUtils.resultSetToTableModel(rs));
                OcultarDatos();

                LLenadoComuna(cboRegion, cboComuna);
                cboComuna.enable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "");
            }
        }
    }//GEN-LAST:event_cboRegionItemStateChanged

    private void cboComunaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboComunaItemStateChanged
        //Filtro de comunas
        String busquedaComuna = cboComuna.getSelectedItem().toString();

        //Filtro todas las comunas
        if (busquedaComuna.equals("Todas las Comunas")) {
            try {
                String query = queryListaContactos
                        + "where co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Correo Leido' AND reg.NombreRegion = ? AND co.IDusuario = ? \n"
                        + "group by co.IDContacto";
                String param = cboRegion.getSelectedItem().toString();
                PreparedStatement pst = cn.prepareStatement(query);
                pst.setString(1, param);
                pst.setString(2, lblIDUsuario.getText());
                ResultSet rs = pst.executeQuery();
                tblCorreosLeidos.setModel(DbUtils.resultSetToTableModel(rs));
                OcultarDatos();

            } catch (Exception ex) {
                //JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        } else {
            try {
                //Filtro para comuna especifica
                String query = queryListaContactos
                        + "where co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Correo Leido' AND com.NombreComuna = ? AND co.IDusuario = ? \n"
                        + "group by co.IDContacto";
                String param = cboComuna.getSelectedItem().toString();
                PreparedStatement pst = cn.prepareStatement(query);
                pst.setString(1, param);
                pst.setString(2, lblIDUsuario.getText());
                ResultSet rs = pst.executeQuery();
                tblCorreosLeidos.setModel(DbUtils.resultSetToTableModel(rs));
                OcultarDatos();
            } catch (Exception ex) {
                //JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }//GEN-LAST:event_cboComunaItemStateChanged

    private void txtNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyPressed
        try {
            String query = queryListaContactos
                    + "WHERE co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Correo Leido' AND co.Nombre like ? AND co.IDusuario = ? \n"
                    + "group by co.IDContacto";
            String param = txtNombre.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, "%" + param + "%");
            pst.setString(2, lblIDUsuario.getText());
            ResultSet rs = pst.executeQuery();
            tblCorreosLeidos.setModel(DbUtils.resultSetToTableModel(rs));
            OcultarDatos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_txtNombreKeyPressed

    private void txtNombre1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre1KeyPressed
        try {
            String query = queryListaContactos
                    + "WHERE co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Correo No Leido' AND co.Nombre like ? AND co.IDusuario = ? \n"
                    + "group by co.IDContacto";
            String param = txtNombre1.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, "%" + param + "%");
            pst.setString(2, lblIDUsuario.getText());
            ResultSet rs = pst.executeQuery();
            tblResultadoCorreoNoLeido.setModel(DbUtils.resultSetToTableModel(rs));
            OcultarDatos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_txtNombre1KeyPressed

    private void txtCorreo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo1KeyPressed
        try {
            String query = queryListaContactos
                    + "WHERE co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Correo No Leido' AND co.correo like ? AND co.IDusuario = ?"
                    + "group by co.IDContacto";
            String param = txtCorreo1.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, "%" + param + "%");
            pst.setString(2, lblIDUsuario.getText());
            ResultSet rs = pst.executeQuery();
            tblResultadoCorreoNoLeido.setModel(DbUtils.resultSetToTableModel(rs));
            OcultarDatos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_txtCorreo1KeyPressed

    private void cboComuna1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboComuna1ItemStateChanged
        //Filtro de comunas
        String busquedaComuna = cboComuna1.getSelectedItem().toString();

        //Filtro todas las comunas
        if (busquedaComuna.equals("Todas las Comunas")) {
            try {
                String query = queryListaContactos
                        + "where co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Correo No Leido' AND reg.NombreRegion = ? AND co.IDusuario = ? \n"
                        + "group by co.IDContacto";
                String param = cboRegion1.getSelectedItem().toString();
                PreparedStatement pst = cn.prepareStatement(query);
                pst.setString(1, param);
                pst.setString(2, lblIDUsuario.getText());
                ResultSet rs = pst.executeQuery();
                tblCorreosLeidos.setModel(DbUtils.resultSetToTableModel(rs));
                OcultarDatos();

            } catch (Exception ex) {
                //JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        } else {
            try {
                //Filtro para comuna especifica
                String query = queryListaContactos
                        + "where co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Correo No Leido' AND com.NombreComuna = ? AND co.IDusuario = ? \n"
                        + "group by co.IDContacto";
                String param = cboComuna1.getSelectedItem().toString();
                PreparedStatement pst = cn.prepareStatement(query);
                pst.setString(1, param);
                pst.setString(2, lblIDUsuario.getText());
                ResultSet rs = pst.executeQuery();
                tblCorreosLeidos.setModel(DbUtils.resultSetToTableModel(rs));
                OcultarDatos();
            } catch (Exception ex) {
                //JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }//GEN-LAST:event_cboComuna1ItemStateChanged

    private void cboRegion1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboRegion1ItemStateChanged
        String busqueda = cboRegion1.getSelectedItem().toString();
        //Filtro para todas las regiones
        if (busqueda.equals("Todas las Regiones")) {
            cboComuna1.disable();
            try {
                String query = queryListaContactos
                        + "where co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Correo No Leido' AND co.IDusuario = ? \n"
                        + "group by co.IDContacto";

                PreparedStatement pst = cn.prepareStatement(query);
                pst.setString(1, lblIDUsuario.getText());
                ResultSet rs = pst.executeQuery();
                tblResultadoCorreoNoLeido.setModel(DbUtils.resultSetToTableModel(rs));
                OcultarDatos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "");
            }
        } else if (busqueda.equals("Seleccione Region")) {
            cboComuna1.disable();
        } else {
            try {
                cboComuna1.disable();
                cboComuna1.removeAllItems();

                /*
                 Filtro para una region en especifica
                 */
                if (cboComuna1.getItemCount() != 0) {
                    cboComuna1.removeAllItems();
                }
                String query = queryListaContactos
                        + "where co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Correo No Leido' AND reg.NombreRegion = ? AND co.IDusuario = ? \n"
                        + "group by co.IDContacto";
                String param = cboRegion1.getSelectedItem().toString();
                PreparedStatement pst = cn.prepareStatement(query);
                pst.setString(1, param);
                pst.setString(2, lblIDUsuario.getText());
                ResultSet rs = pst.executeQuery();
                tblResultadoCorreoNoLeido.setModel(DbUtils.resultSetToTableModel(rs));
                OcultarDatos();

                LLenadoComuna(cboRegion1, cboComuna1);
                cboComuna1.enable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "");
            }
        }
    }//GEN-LAST:event_cboRegion1ItemStateChanged

    private void txtNombre2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre2KeyPressed
        try {
            String query = queryListaContactos
                    + "WHERE co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Mail Delivery' AND co.Nombre like ? AND co.IDusuario = ? \n"
                    + "group by co.IDContacto";
            String param = txtNombre2.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, "%" + param + "%");
            pst.setString(2, lblIDUsuario.getText());
            ResultSet rs = pst.executeQuery();
            tblMailDelivery.setModel(DbUtils.resultSetToTableModel(rs));
            OcultarDatos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_txtNombre2KeyPressed

    private void txtCorreo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo2KeyPressed
        try {
            String query = queryListaContactos
                    + "WHERE co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Mail Delivery' AND co.correo like ? AND co.IDusuario = ? "
                    + "group by co.IDContacto";
            String param = txtCorreo2.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, "%" + param + "%");
            pst.setString(2, lblIDUsuario.getText());
            ResultSet rs = pst.executeQuery();
            tblMailDelivery.setModel(DbUtils.resultSetToTableModel(rs));
            OcultarDatos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_txtCorreo2KeyPressed

    private void cboComuna2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboComuna2ItemStateChanged
        //Filtro de comunas
        String busquedaComuna = cboComuna2.getSelectedItem().toString();

        //Filtro todas las comunas
        if (busquedaComuna.equals("Todas las Comunas")) {
            try {
                String query = queryListaContactos
                        + "where co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Mail Delivery' AND reg.NombreRegion = ? AND co.IDusuario = ? \n"
                        + "group by co.IDContacto";
                String param = cboRegion2.getSelectedItem().toString();
                PreparedStatement pst = cn.prepareStatement(query);
                pst.setString(1, param);
                pst.setString(2, lblIDUsuario.getText());
                ResultSet rs = pst.executeQuery();
                tblMailDelivery.setModel(DbUtils.resultSetToTableModel(rs));
                OcultarDatos();

            } catch (Exception ex) {
                //JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        } else {
            try {
                //Filtro para comuna especifica
                String query = queryListaContactos
                        + "where co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Mail Delivery' AND com.NombreComuna = ? AND co.IDusuario = ? \n"
                        + "group by co.IDContacto";
                String param = cboComuna2.getSelectedItem().toString();
                PreparedStatement pst = cn.prepareStatement(query);
                pst.setString(1, param);
                pst.setString(2, lblIDUsuario.getText());
                ResultSet rs = pst.executeQuery();
                tblMailDelivery.setModel(DbUtils.resultSetToTableModel(rs));
                OcultarDatos();
            } catch (Exception ex) {
                //JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }//GEN-LAST:event_cboComuna2ItemStateChanged

    private void cboRegion2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboRegion2ItemStateChanged
        String busqueda = cboRegion2.getSelectedItem().toString();
        //Filtro para todas las regiones
        if (busqueda.equals("Todas las Regiones")) {
            cboComuna2.disable();
            try {
                String query = queryListaContactos
                        + "where co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Mail Delivery' AND co.IDusuario = ? \n"
                        + "group by co.IDContacto";

                PreparedStatement pst = cn.prepareStatement(query);
                pst.setString(1, lblIDUsuario.getText());
                ResultSet rs = pst.executeQuery();
                tblMailDelivery.setModel(DbUtils.resultSetToTableModel(rs));
                OcultarDatos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "");
            }
        } else if (busqueda.equals("Seleccione Region")) {
            cboComuna2.disable();
        } else {
            try {
                cboComuna2.disable();
                cboComuna2.removeAllItems();

                /*
                 Filtro para una region en especifica
                 */
                if (cboComuna2.getItemCount() != 0) {
                    cboComuna2.removeAllItems();
                }
                String query = queryListaContactos
                        + "where co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Mail Delivery' AND reg.NombreRegion = ? AND co.IDusuario = ? \n"
                        + "group by co.IDContacto";
                String param = cboRegion2.getSelectedItem().toString();
                PreparedStatement pst = cn.prepareStatement(query);
                pst.setString(1, param);
                pst.setString(2, lblIDUsuario.getText());
                ResultSet rs = pst.executeQuery();
                tblMailDelivery.setModel(DbUtils.resultSetToTableModel(rs));
                OcultarDatos();

                LLenadoComuna(cboRegion2, cboComuna2);
                cboComuna2.enable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "");
            }
        }
    }//GEN-LAST:event_cboRegion2ItemStateChanged

    private void btnMailEnviado2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMailEnviado2ActionPerformed
        if (tblSinRespuesta.getSelectedRow() != -1) {

            String respuesta = JOptionPane.showInputDialog(null, "Escriba Su codigo de autorización.", "Permisos de acción", JOptionPane.INFORMATION_MESSAGE);
            DefaultTableModel modelo = (DefaultTableModel) tblSinRespuesta.getModel();
            int codigo = Integer.parseInt((modelo.getValueAt(tblSinRespuesta.getSelectedRow(), 0)).toString());

            if (lbCodigo.getText().equals(respuesta)) {
                JOptionPane.showMessageDialog(null, "Codigo de autorización Correcto.", "Correcto", JOptionPane.INFORMATION_MESSAGE);

                try {
                    String Comentario = "Se cambio el EstadoRespuesta del contacto id nro." + codigo + "a leido por el empleado con id nro. " + lblIDUsuario.getText();

                    String query = "INSERT INTO movimientosventas (IDUsuario,Movimiento) VALUES (?,?)";
                    PreparedStatement pst = cn.prepareStatement(query);
                    pst.setInt(1, Integer.parseInt(lblIDUsuario.getText()));
                    pst.setString(2, Comentario);
                    int up = pst.executeUpdate();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
                try {
                    String query = "Update contactos SET EstadoRespuesta = 'Correo Leido' where IDContacto = ? ";
                    PreparedStatement pst = cn.prepareStatement(query);
                    pst.setInt(1, codigo);
                    int up = pst.executeUpdate();
                    DefaultTableModel model = (DefaultTableModel) tblSinRespuesta.getModel();
                    model.setRowCount(0);

                    LlenarTabla();
                    String credencial;
                    int idUsuario = 0;
                    credencial = lblCredencial.getText();
                    idUsuario = Integer.parseInt(lblIDUsuario.getText());
                    String query3 = "INSERT INTO movimientosventas(`IDUsuario`, `FechaMovimiento`, `Movimiento`) VALUES (?, CURRENT_TIMESTAMP, 'Llamó Cliente')";
                    PreparedStatement pst3 = cn.prepareStatement(query3);
                    pst3.setInt(1, idUsuario);
                    up = pst3.executeUpdate();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Codigo de autorizacion invalido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para proceder.");
        }
    }//GEN-LAST:event_btnMailEnviado2ActionPerformed

    private void btnMailDeliverySRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMailDeliverySRActionPerformed
        if (tblSinRespuesta.getSelectedRow() != -1) {

            String respuesta = JOptionPane.showInputDialog(null, "Escriba Su codigo de autorización.", "Permisos de acción", JOptionPane.INFORMATION_MESSAGE);
            DefaultTableModel modelo = (DefaultTableModel) tblSinRespuesta.getModel();
            int codigo = Integer.parseInt((modelo.getValueAt(tblSinRespuesta.getSelectedRow(), 0)).toString());

            if (lbCodigo.getText().equals(respuesta)) {
                JOptionPane.showMessageDialog(null, "Codigo de autorización Correcto.", "Correcto", JOptionPane.INFORMATION_MESSAGE);

                try {
                    String Comentario = "Se cambio el EstadoMail del contacto id nro." + codigo + "a Mail Delivery por el empleado con id nro. " + lblIDUsuario.getText();

                    String query = "INSERT INTO movimientosventas (IDUsuario,Movimiento) VALUES (?,?)";
                    PreparedStatement pst = cn.prepareStatement(query);
                    pst.setInt(1, Integer.parseInt(lblIDUsuario.getText()));
                    pst.setString(2, Comentario);
                    int up = pst.executeUpdate();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
                try {
                    String query = "Update contactos SET EstadoRespuesta = 'Mail Delivery' where IDContacto = ? ";
                    PreparedStatement pst = cn.prepareStatement(query);
                    pst.setInt(1, codigo);
                    int up = pst.executeUpdate();
                    DefaultTableModel model = (DefaultTableModel) tblSinRespuesta.getModel();
                    model.setRowCount(0);

                    LlenarTabla();
                    String credencial;
                    int idUsuario = 0;
                    credencial = lblCredencial.getText();
                    idUsuario = Integer.parseInt(lblIDUsuario.getText());
                    String query3 = "INSERT INTO movimientosventas(`IDUsuario`, `FechaMovimiento`, `Movimiento`) VALUES (?, CURRENT_TIMESTAMP, 'Llamó Cliente')";
                    PreparedStatement pst3 = cn.prepareStatement(query3);
                    pst3.setInt(1, idUsuario);
                    up = pst3.executeUpdate();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Codigo de autorizacion invalido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para proceder.");
        }
    }//GEN-LAST:event_btnMailDeliverySRActionPerformed

    private void txtNombre3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre3KeyPressed
        try {
            String query = queryListaContactos
                    + "WHERE co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Sin Respuesta' AND co.Nombre like ? AND co.IDusuario = ? \n"
                    + "group by co.IDContacto";
            String param = txtNombre3.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, "%" + param + "%");
            pst.setString(2, lblIDUsuario.getText());
            ResultSet rs = pst.executeQuery();
            tblSinRespuesta.setModel(DbUtils.resultSetToTableModel(rs));
            OcultarDatos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_txtNombre3KeyPressed

    private void txtCorreo3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo3KeyPressed
        try {
            String query = queryListaContactos
                    + "WHERE co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Sin Respuesta' AND co.correo like ? AND co.IDusuario = ?"
                    + "group by co.IDContacto";
            String param = txtCorreo3.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, "%" + param + "%");
            pst.setString(2, lblIDUsuario.getText());
            ResultSet rs = pst.executeQuery();
            tblSinRespuesta.setModel(DbUtils.resultSetToTableModel(rs));
            OcultarDatos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_txtCorreo3KeyPressed

    private void cboComuna3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboComuna3ItemStateChanged
        //Filtro de comunas
        String busquedaComuna = cboComuna3.getSelectedItem().toString();

        //Filtro todas las comunas
        if (busquedaComuna.equals("Todas las Comunas")) {
            try {
                String query = queryListaContactos
                        + "where co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Sin Respuesta' AND reg.NombreRegion = ? AND co.IDusuario = ? \n"
                        + "group by co.IDContacto";
                String param = cboRegion3.getSelectedItem().toString();
                PreparedStatement pst = cn.prepareStatement(query);
                pst.setString(1, param);
                pst.setString(2, lblIDUsuario.getText());
                ResultSet rs = pst.executeQuery();
                tblSinRespuesta.setModel(DbUtils.resultSetToTableModel(rs));
                OcultarDatos();

            } catch (Exception ex) {
                //JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        } else {
            try {
                //Filtro para comuna especifica
                String query = queryListaContactos
                        + "where co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Sin Respuesta' AND com.NombreComuna = ? AND co.IDusuario = ? \n"
                        + "group by co.IDContacto";
                String param = cboComuna3.getSelectedItem().toString();
                PreparedStatement pst = cn.prepareStatement(query);
                pst.setString(1, param);
                pst.setString(2, lblIDUsuario.getText());
                ResultSet rs = pst.executeQuery();
                tblSinRespuesta.setModel(DbUtils.resultSetToTableModel(rs));
                OcultarDatos();
            } catch (Exception ex) {
                //JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }//GEN-LAST:event_cboComuna3ItemStateChanged

    private void cboRegion3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboRegion3ItemStateChanged
        String busqueda = cboRegion3.getSelectedItem().toString();
        //Filtro para todas las regiones
        if (busqueda.equals("Todas las Regiones")) {
            cboComuna3.disable();
            try {
                String query = queryListaContactos
                        + "where co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Sin Respuesta' AND co.IDusuario = ? \n"
                        + "group by co.IDContacto";

                PreparedStatement pst = cn.prepareStatement(query);
                pst.setString(1, lblIDUsuario.getText());
                ResultSet rs = pst.executeQuery();
                tblSinRespuesta.setModel(DbUtils.resultSetToTableModel(rs));
                OcultarDatos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "");
            }
        } else if (busqueda.equals("Seleccione Region")) {
            cboComuna3.disable();
        } else {
            try {
                cboComuna3.disable();
                cboComuna3.removeAllItems();

                /*
                 Filtro para una region en especifica
                 */
                if (cboComuna3.getItemCount() != 0) {
                    cboComuna3.removeAllItems();
                }
                String query = queryListaContactos
                        + "where co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Sin Respuesta' AND reg.NombreRegion = ? AND co.IDusuario = ? \n"
                        + "group by co.IDContacto";
                String param = cboRegion3.getSelectedItem().toString();
                PreparedStatement pst = cn.prepareStatement(query);
                pst.setString(1, param);
                pst.setString(2, lblIDUsuario.getText());
                ResultSet rs = pst.executeQuery();
                tblSinRespuesta.setModel(DbUtils.resultSetToTableModel(rs));
                OcultarDatos();

                LLenadoComuna(cboRegion3, cboComuna3);
                cboComuna3.enable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "");
            }
        }
    }//GEN-LAST:event_cboRegion3ItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (tblNoEnviados.getSelectedRow() != -1) {

            String respuesta = JOptionPane.showInputDialog(null, "Escriba Su codigo de autorización.", "Permisos de acción", JOptionPane.INFORMATION_MESSAGE);
            DefaultTableModel modelo = (DefaultTableModel) tblNoEnviados.getModel();
            int codigo = Integer.parseInt((modelo.getValueAt(tblNoEnviados.getSelectedRow(), 0)).toString());

            if (lbCodigo.getText().equals(respuesta)) {
                JOptionPane.showMessageDialog(null, "Codigo de autorización Correcto.", "Correcto", JOptionPane.INFORMATION_MESSAGE);

                try {
                    String Comentario = "Se cambio el EstadoMail del contacto id nro." + codigo + "a enviado por el empleado con id nro. " + lblIDUsuario.getText();

                    String query = "INSERT INTO movimientosventas (IDUsuario,Movimiento) VALUES (?,?)";
                    PreparedStatement pst = cn.prepareStatement(query);
                    pst.setInt(1, Integer.parseInt(lblIDUsuario.getText()));
                    pst.setString(2, Comentario);
                    int up = pst.executeUpdate();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }

                try {

                    String query = "Update contactos SET EstadoMail='Correo Enviado', EstadoRespuesta='Sin Respuesta' where IDContacto = ? ";
                    PreparedStatement pst = cn.prepareStatement(query);
                    pst.setInt(1, codigo);
                    int up = pst.executeUpdate();
                    DefaultTableModel model = (DefaultTableModel) tblNoEnviados.getModel();
                    model.setRowCount(0);

                    LlenarTabla();
                    String credencial;
                    int idUsuario = 0;
                    credencial = lblCredencial.getText();
                    idUsuario = Integer.parseInt(lblIDUsuario.getText());
                    String query3 = "INSERT INTO movimientosventas(`IDUsuario`, `FechaMovimiento`, `Movimiento`) VALUES (?, CURRENT_TIMESTAMP, 'Llamó Cliente')";
                    PreparedStatement pst3 = cn.prepareStatement(query3);
                    pst3.setInt(1, idUsuario);
                    up = pst3.executeUpdate();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "" + ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Codigo de autorizacion invalido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para proceder.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnMailNoEnviado2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMailNoEnviado2ActionPerformed
        if (tblSinRespuesta.getSelectedRow() != -1) {

            String respuesta = JOptionPane.showInputDialog(null, "Escriba Su codigo de autorización.", "Permisos de acción", JOptionPane.INFORMATION_MESSAGE);
            DefaultTableModel modelo = (DefaultTableModel) tblSinRespuesta.getModel();
            int codigo = Integer.parseInt((modelo.getValueAt(tblSinRespuesta.getSelectedRow(), 0)).toString());

            if (lbCodigo.getText().equals(respuesta)) {
                JOptionPane.showMessageDialog(null, "Codigo de autorización Correcto.", "Correcto", JOptionPane.INFORMATION_MESSAGE);

                try {
                    String Comentario = "Se cambio el EstadoMail del contacto id nro." + codigo + "a no leido por el empleado con id nro. " + lblIDUsuario.getText();

                    String query = "INSERT INTO movimientosventas (IDUsuario,Movimiento) VALUES (?,?)";
                    PreparedStatement pst = cn.prepareStatement(query);
                    pst.setInt(1, Integer.parseInt(lblIDUsuario.getText()));
                    pst.setString(2, Comentario);
                    int up = pst.executeUpdate();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
                try {
                    String query = "Update contactos SET EstadoRespuesta = 'Correo No Leido' where IDContacto = ? ";
                    PreparedStatement pst = cn.prepareStatement(query);
                    pst.setInt(1, codigo);
                    int up = pst.executeUpdate();
                    DefaultTableModel model = (DefaultTableModel) tblSinRespuesta.getModel();
                    model.setRowCount(0);

                    LlenarTabla();
                    String credencial;
                    int idUsuario = 0;
                    credencial = lblCredencial.getText();
                    idUsuario = Integer.parseInt(lblIDUsuario.getText());
                    String query3 = "INSERT INTO movimientosventas(`IDUsuario`, `FechaMovimiento`, `Movimiento`) VALUES (?, CURRENT_TIMESTAMP, 'Llamó Cliente')";
                    PreparedStatement pst3 = cn.prepareStatement(query3);
                    pst3.setInt(1, idUsuario);
                    up = pst3.executeUpdate();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Codigo de autorizacion invalido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para proceder.");
        }
    }//GEN-LAST:event_btnMailNoEnviado2ActionPerformed

    private void txtNombre4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre4KeyPressed
        try {
            String query = queryListaContactos
                    + "WHERE co.EstadoMail = 'Correo No Enviado' AND co.Nombre like ? AND co.IDusuario = ? \n"
                    + "group by co.IDContacto";
            String param = txtNombre4.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, "%" + param + "%");
            pst.setString(2, lblIDUsuario.getText());
            ResultSet rs = pst.executeQuery();
            tblNoEnviados.setModel(DbUtils.resultSetToTableModel(rs));
            OcultarDatos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_txtNombre4KeyPressed

    private void txtCorreo4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo4KeyPressed
        try {
            String query = queryListaContactos
                    + "WHERE co.EstadoMail = 'Correo No Enviado' AND co.correo like ? AND co.IDusuario = ?"
                    + "group by co.IDContacto";
            String param = txtCorreo4.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, "%" + param + "%");
            pst.setString(2, lblIDUsuario.getText());
            ResultSet rs = pst.executeQuery();
            tblNoEnviados.setModel(DbUtils.resultSetToTableModel(rs));
            OcultarDatos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_txtCorreo4KeyPressed

    private void cboComuna4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboComuna4ItemStateChanged
        //Filtro de comunas
        String busquedaComuna = cboComuna4.getSelectedItem().toString();

        //Filtro todas las comunas
        if (busquedaComuna.equals("Todas las Comunas")) {
            try {
                String query = queryListaContactos
                        + "where co.EstadoMail = 'Correo No Enviado' AND reg.NombreRegion = ? AND co.IDusuario = ? \n"
                        + "group by co.IDContacto";
                String param = cboRegion4.getSelectedItem().toString();
                PreparedStatement pst = cn.prepareStatement(query);
                pst.setString(1, param);
                pst.setString(2, lblIDUsuario.getText());
                ResultSet rs = pst.executeQuery();
                tblNoEnviados.setModel(DbUtils.resultSetToTableModel(rs));
                OcultarDatos();

            } catch (Exception ex) {
                //JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        } else {
            try {
                //Filtro para comuna especifica
                String query = queryListaContactos
                        + "where co.EstadoMail = 'Correo No Enviado' AND com.NombreComuna = ? AND co.IDusuario = ? \n"
                        + "group by co.IDContacto";
                String param = cboComuna4.getSelectedItem().toString();
                PreparedStatement pst = cn.prepareStatement(query);
                pst.setString(1, param);
                pst.setString(2, lblIDUsuario.getText());
                ResultSet rs = pst.executeQuery();
                tblNoEnviados.setModel(DbUtils.resultSetToTableModel(rs));
                OcultarDatos();
            } catch (Exception ex) {
                //JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }//GEN-LAST:event_cboComuna4ItemStateChanged

    private void cboRegion4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboRegion4ItemStateChanged
        String busqueda = cboRegion4.getSelectedItem().toString();
        //Filtro para todas las regiones
        if (busqueda.equals("Todas las Regiones")) {
            cboComuna4.disable();
            try {
                String query = queryListaContactos
                        + "where co.EstadoMail = 'Correo No Enviado' AND co.IDusuario = ?\n"
                        + "group by co.IDContacto";

                PreparedStatement pst = cn.prepareStatement(query);
                pst.setString(1, lblIDUsuario.getText());
                ResultSet rs = pst.executeQuery();
                tblNoEnviados.setModel(DbUtils.resultSetToTableModel(rs));
                OcultarDatos();
            } catch (Exception ex) {
            }
        } else if (busqueda.equals("Seleccione Region")) {
            cboComuna4.disable();
        } else {
            try {
                cboComuna4.disable();
                cboComuna4.removeAllItems();

                /*
                 Filtro para una region en especifica
                 */
                if (cboComuna4.getItemCount() != 0) {
                    cboComuna4.removeAllItems();
                }
                String query = queryListaContactos
                        + "where co.EstadoMail = 'Correo No Enviado' AND reg.NombreRegion = ? AND co.IDusuario = ? \n"
                        + "group by co.IDContacto";
                String param = cboRegion4.getSelectedItem().toString();
                PreparedStatement pst = cn.prepareStatement(query);
                pst.setString(1, param);
                pst.setString(2, lblIDUsuario.getText());
                ResultSet rs = pst.executeQuery();
                tblNoEnviados.setModel(DbUtils.resultSetToTableModel(rs));
                OcultarDatos();

                LLenadoComuna(cboRegion4, cboComuna4);
                cboComuna4.enable();
            } catch (Exception ex) {
            }
        }
    }//GEN-LAST:event_cboRegion4ItemStateChanged

    private void txt_NombreOrganizacion4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_NombreOrganizacion4KeyReleased
        try {
            String query = queryListaContactos
                    + "WHERE co.EstadoMail = 'Correo No Enviado' AND org.NombreOrganizacion like ? AND co.IDusuario = ? \n"
                    + "group by co.IDContacto";
            String param = txt_NombreOrganizacion4.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, "%" + param + "%");
            pst.setString(2, lblIDUsuario.getText());
            ResultSet rs = pst.executeQuery();
            tblNoEnviados.setModel(DbUtils.resultSetToTableModel(rs));
            OcultarDatos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_txt_NombreOrganizacion4KeyReleased

    private void txt_NombreOrganizacion3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_NombreOrganizacion3KeyReleased
        try {
            String query = queryListaContactos
                    + "WHERE co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Sin Respuesta' AND org.NombreOrganizacion like ? AND co.IDusuario = ? \n"
                    + "group by co.IDContacto";
            String param = txt_NombreOrganizacion3.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, "%" + param + "%");
            pst.setString(2, lblIDUsuario.getText());
            ResultSet rs = pst.executeQuery();
            tblSinRespuesta.setModel(DbUtils.resultSetToTableModel(rs));
            OcultarDatos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_txt_NombreOrganizacion3KeyReleased

    private void txt_NombreOrganizacion1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_NombreOrganizacion1KeyReleased
        try {
            String query = queryListaContactos
                    + "WHERE co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Correo No Leido' AND org.NombreOrganizacion like ? AND co.IDusuario = ? \n"
                    + "group by co.IDContacto";
            String param = txt_NombreOrganizacion1.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, "%" + param + "%");
            pst.setString(2, lblIDUsuario.getText());
            ResultSet rs = pst.executeQuery();
            tblResultadoCorreoNoLeido.setModel(DbUtils.resultSetToTableModel(rs));
            OcultarDatos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_txt_NombreOrganizacion1KeyReleased

    private void txt_NombreOrganizacionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_NombreOrganizacionKeyReleased
        try {
            String query = queryListaContactos
                    + "WHERE co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Correo Leido' AND org.NombreOrganizacion like ? AND co.IDusuario = ? \n"
                    + "group by co.IDContacto";
            String param = txt_NombreOrganizacion.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, "%" + param + "%");
            pst.setString(2, lblIDUsuario.getText());
            ResultSet rs = pst.executeQuery();
            tblCorreosLeidos.setModel(DbUtils.resultSetToTableModel(rs));
            OcultarDatos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_txt_NombreOrganizacionKeyReleased

    private void txt_NombreOrganizacion2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_NombreOrganizacion2KeyReleased
        try {
            String query = queryListaContactos
                    + "WHERE co.EstadoMail = 'Correo Enviado' AND co.EstadoRespuesta='Mail Delivery' AND org.NombreOrganizacion like ? AND co.IDusuario = ? \n"
                    + "group by co.IDContacto";
            String param = txt_NombreOrganizacion2.getText();
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, "%" + param + "%");
            pst.setString(2, lblIDUsuario.getText());
            ResultSet rs = pst.executeQuery();
            tblMailDelivery.setModel(DbUtils.resultSetToTableModel(rs));
            OcultarDatos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_txt_NombreOrganizacion2KeyReleased

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
            java.util.logging.Logger.getLogger(ListadoContactos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListadoContactos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListadoContactos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListadoContactos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListadoContactos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Jpnl_CorreosEnviados;
    private javax.swing.JPanel Jpnl_CorreosNoEnviados;
    private javax.swing.JPanel Jpnl_MailDelivery;
    private javax.swing.JPanel PnCorreo;
    private javax.swing.JPanel PnCorreo1;
    private javax.swing.JPanel PnCorreo2;
    private javax.swing.JPanel PnCorreo3;
    private javax.swing.JPanel PnCorreo4;
    private javax.swing.JPanel PnNombre;
    private javax.swing.JPanel PnNombre1;
    private javax.swing.JPanel PnNombre2;
    private javax.swing.JPanel PnNombre3;
    private javax.swing.JPanel PnNombre4;
    private javax.swing.JPanel PnNombreOrganizacion;
    private javax.swing.JPanel PnNombreOrganizacion1;
    private javax.swing.JPanel PnNombreOrganizacion2;
    private javax.swing.JPanel PnNombreOrganizacion3;
    private javax.swing.JPanel PnNombreOrganizacion4;
    private javax.swing.JPanel PnUbicacion;
    private javax.swing.JPanel PnUbicacion1;
    private javax.swing.JPanel PnUbicacion2;
    private javax.swing.JPanel PnUbicacion3;
    private javax.swing.JPanel PnUbicacion4;
    private javax.swing.JLabel b_Titulo;
    private javax.swing.JButton btnMailDeliveryCE;
    private javax.swing.JButton btnMailDeliveryNE;
    private javax.swing.JButton btnMailDeliverySR;
    private javax.swing.JButton btnMailEnviado;
    private javax.swing.JButton btnMailEnviado1;
    private javax.swing.JButton btnMailEnviado2;
    private javax.swing.JButton btnMailNoEnviado;
    private javax.swing.JButton btnMailNoEnviado1;
    private javax.swing.JButton btnMailNoEnviado2;
    private javax.swing.JButton btnVolver;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboComuna;
    private javax.swing.JComboBox<String> cboComuna1;
    private javax.swing.JComboBox<String> cboComuna2;
    private javax.swing.JComboBox<String> cboComuna3;
    private javax.swing.JComboBox<String> cboComuna4;
    private javax.swing.JComboBox<String> cboRegion;
    private javax.swing.JComboBox<String> cboRegion1;
    private javax.swing.JComboBox<String> cboRegion2;
    private javax.swing.JComboBox<String> cboRegion3;
    private javax.swing.JComboBox<String> cboRegion4;
    private javax.swing.JPanel elementos;
    private javax.swing.JLabel fondo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrllPnNoEnviados;
    private javax.swing.JScrollPane jScrllPnNoEnviados1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTabbedPane jTabbedPane6;
    public javax.swing.JLabel lbCodigo;
    public javax.swing.JLabel lblCredencial;
    public javax.swing.JLabel lblIDUsuario;
    public javax.swing.JTable tblCorreosLeidos;
    public javax.swing.JTable tblMailDelivery;
    public javax.swing.JTable tblNoEnviados;
    public javax.swing.JTable tblResultadoCorreoNoLeido;
    public javax.swing.JTable tblSinRespuesta;
    private javax.swing.JTabbedPane tbl_NoEnviados;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtCorreo1;
    private javax.swing.JTextField txtCorreo2;
    private javax.swing.JTextField txtCorreo3;
    private javax.swing.JTextField txtCorreo4;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombre1;
    private javax.swing.JTextField txtNombre2;
    private javax.swing.JTextField txtNombre3;
    private javax.swing.JTextField txtNombre4;
    private javax.swing.JTextField txt_NombreOrganizacion;
    private javax.swing.JTextField txt_NombreOrganizacion1;
    private javax.swing.JTextField txt_NombreOrganizacion2;
    private javax.swing.JTextField txt_NombreOrganizacion3;
    private javax.swing.JTextField txt_NombreOrganizacion4;
    // End of variables declaration//GEN-END:variables
}
