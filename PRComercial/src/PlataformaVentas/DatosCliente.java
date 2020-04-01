package PlataformaVentas;

import clases.Conexion;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Diego Alfaro Fierro, Diego González Romàn
 */
public class DatosCliente extends javax.swing.JFrame {

    Conexion con = new Conexion();
    Connection cn = con.conecta();

    public DatosCliente() {
        initComponents();

        cmbComuna.setEnabled(false);
        lblComuna.setVisible(false);
        lblRegion.setVisible(false);
        txtUnidad.setEnabled(false);
        txtUnidad.setVisible(false);
        TxtOrganización.setEnabled(false);
        TxtOrganización.setVisible(false);
        jChBox_Organizacion.setSelected(false);
        jChBox_UCompra.setSelected(false);

        try {
            String query = "select Detalle from unidadDeCompra ORDER BY idUnidadCompra";
            PreparedStatement pst = cn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                cmbUnidadCompra.addItem(rs.getString(1));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        try {
            String query = "select NombreRegion from region ORDER BY IDRegion";
            PreparedStatement pst = cn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                cmbRegion.addItem(rs.getString(1));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        /*try {
         String query = "select NombreRegion from region ORDER BY IDRegion";
         PreparedStatement pst = cn.prepareStatement(query);
         ResultSet rs = pst.executeQuery();
         while (rs.next()) {
         cmbRegion.addItem(rs.getString(1));
         }
         } catch (Exception ex) {
         JOptionPane.showMessageDialog(null, ex.getMessage());
         }*/

        try {
            String query = "select NombreOrganizacion from organizacion ORDER BY IDOrganizacion";
            PreparedStatement pst = cn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                cmbOrganizacion.addItem(rs.getString(1));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    public void IngresarCliente() {
        if (jChBox_UCompra.isSelected()) {
            if (jChBox_Organizacion.isSelected()) {
                if (txtRut.getText() == "" || txtNombre.getText() == ""
                        || cmbOrganizacion.getSelectedItem().toString() == "Seleccione Organizacion" || txtRazon.getText() == ""
                        || cmbArea.getSelectedItem().toString() == "Seleccione Área" || txtTelefono.getText() == ""
                        || cmbRegion.getSelectedItem().toString() == "Seleccione Region" || txtCiudad.getText() == ""
                        || txtDireccion.getText() == "") {
                    JOptionPane.showMessageDialog(null, "No pueden haber campos vacíos");
                } else {
                    String rut;
                    String nom;
                    String app;
                    int corp = 0;
                    String cargo;
                    String razon;
                    int unidad;
                    String correo;
                    String telefono;
                    String ciudad;
                    String direccion;
                    String query = "";
                    rut = txtRut.getText();
                    nom = txtNombre.getText();

                    corp = cmbOrganizacion.getSelectedIndex();
                    cargo = txtCargo.getText();
                    razon = txtRazon.getText();
                    unidad = cmbUnidadCompra.getSelectedIndex();
                    correo = txtCiudad.getText();
                    telefono = cmbArea.getSelectedItem().toString() + txtTelefono.getText();

                    ciudad = txtCiudad.getText();

                    direccion = txtDireccion.getText();
                    try {
                        String query2 = "select idRegion,idComuna from comuna where nombreComuna=?";
                        String param = cmbComuna.getSelectedItem().toString();
                        PreparedStatement pst2 = cn.prepareStatement(query2);
                        pst2.setString(1, param);
                        ResultSet rs2 = pst2.executeQuery();
                        int region = 0;
                        int comuna = 0;
                        while (rs2.next()) {
                            region = rs2.getInt("idRegion");
                            comuna = rs2.getInt("idComuna");

                        }
                        String query4 = "insert into unidadDeCompra (detalle) values (?)";
                        String param3 = txtUnidad.getText();
                        PreparedStatement pst4 = cn.prepareStatement(query4);
                        pst4.setString(1, param3);
                        int up2 = pst4.executeUpdate();

                        String query5 = "insert into organizacion (NombreOrganizacion) values (?)";
                        String param5 = TxtOrganización.getText();
                        PreparedStatement pst5 = cn.prepareStatement(query5);
                        pst5.setString(1, param5);
                        int up5 = pst5.executeUpdate();

                        try {

                            String query3 = "select idUnidadCompra from unidadDeCompra where Detalle=?";
                            String param2 = txtUnidad.getText();
                            PreparedStatement pst3 = cn.prepareStatement(query3);
                            pst3.setString(1, param2);
                            ResultSet rs3 = pst3.executeQuery();
                            while (rs3.next()) {
                                unidad = rs3.getInt("idUnidadCompra");
                            }
                            //Insertar Cliente
                            query = "INSERT INTO cliente (rut,nombre,IdOrganizacion,cargo,razon_social,idUnidadCompra,correo,telefono,idregion,idcomuna,ciudad,direccion) VALUES (?,?,(select IdOrganizacion from organizacion where NombreOrganizacion = ?),?,?,?,?,?,?,?,?,?)";
                            PreparedStatement insert = cn.prepareStatement(query);
                            insert.setString(1, rut);
                            insert.setString(2, nom);
                            insert.setString(3, TxtOrganización.getText());
                            insert.setString(4, cargo);
                            insert.setString(5, razon);
                            insert.setInt(6, unidad);
                            insert.setString(7, correo);
                            insert.setString(8, telefono);
                            insert.setInt(9, region);
                            insert.setInt(10, comuna);
                            insert.setString(11, ciudad);
                            insert.setString(12, direccion);
                            int i = insert.executeUpdate();

                            /*
                             Query que permite actualizar los id de cliente
                             SET @count = 0;
                             UPDATE `cliente` SET `cliente`.`id` = @count:= @count + 1;
                             */
                            //Poner contador de ID en 0
                            query = "SET @count = 0";
                            PreparedStatement count = cn.prepareStatement(query);
                            int s = count.executeUpdate();
                            //Acualizar
                            query = "UPDATE `cliente` SET `cliente`.`idCliente` = @count:= @count + 1";
                            PreparedStatement update = cn.prepareStatement(query);
                            int u = update.executeUpdate();

                            if (i > 0) {
                                txtNombre.setText("");
                                txtCiudad.setText("");
                                txtCargo.setText("");
                                txtCorreo.setText("");
                                cmbOrganizacion.setSelectedIndex(0);
                                txtTelefono.setText("");
                                cmbRegion.setSelectedIndex(0);
                                cmbArea.setSelectedIndex(0);
                                txtRazon.setText("");
                                txtUnidad.setText("");
                                txtDireccion.setText("");
                                txtRut.setText("");
                                JOptionPane.showMessageDialog(null, "Registro Guardado con Exito");
                            }

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        }
                        String credencial;
                        int idUsuario = 0;
                        int up;
                        credencial = lblCredencial.getText();
                        idUsuario = Integer.parseInt(lblIDUsuario.getText());
                        String query6 = "INSERT INTO movimientosventas(`IDUsuario`, `Movimiento`) VALUES (?,'Ingresar Cliente')";
                        PreparedStatement pst6 = cn.prepareStatement(query6);
                        pst5.setInt(1, idUsuario);

                        up = pst6.executeUpdate();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
            } else if (jChBox_Organizacion.isSelected() == false) {
                if (txtRut.getText() == "" || txtNombre.getText() == ""
                        || cmbOrganizacion.getSelectedItem().toString() == "Seleccione Organizacion" || txtRazon.getText() == ""
                        || cmbArea.getSelectedItem().toString() == "Seleccione Área" || txtTelefono.getText() == ""
                        || cmbRegion.getSelectedItem().toString() == "Seleccione Region" || txtCiudad.getText() == ""
                        || txtDireccion.getText() == "") {
                    JOptionPane.showMessageDialog(null, "No pueden haber campos vacíos");
                } else {
                    String rut;
                    String nom;
                    String app;
                    String corp;
                    String cargo;
                    String razon;
                    int unidad;
                    String correo;
                    String telefono;
                    String ciudad;
                    String direccion;
                    String query = "";
                    int organizacion = 0;
                    rut = txtRut.getText();
                    nom = txtNombre.getText();

                    corp = cmbOrganizacion.getSelectedItem().toString();
                    cargo = txtCargo.getText();
                    razon = txtRazon.getText();
                    unidad = cmbUnidadCompra.getSelectedIndex();
                    correo = txtCiudad.getText();
                    telefono = cmbArea.getSelectedItem().toString() + txtTelefono.getText();

                    ciudad = txtCiudad.getText();

                    try {
                        String query4 = "select IDOrganizacion from organizacion where NombreOrganizacion=?";
                        String param4 = corp;
                        PreparedStatement pst4 = cn.prepareStatement(query4);
                        pst4.setString(1, param4);
                        ResultSet rs4 = pst4.executeQuery();

                        while (rs4.next()) {
                            organizacion = rs4.getInt("IDOrganizacion");
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }

                    direccion = txtDireccion.getText();
                    try {
                        String query2 = "select idRegion,idComuna from comuna where nombreComuna=?";
                        String param = cmbComuna.getSelectedItem().toString();
                        PreparedStatement pst2 = cn.prepareStatement(query2);
                        pst2.setString(1, param);
                        ResultSet rs2 = pst2.executeQuery();
                        int region = 0;
                        int comuna = 0;
                        while (rs2.next()) {
                            region = rs2.getInt("idRegion");
                            comuna = rs2.getInt("idComuna");

                        }
                        String query4 = "insert into unidadDeCompra (detalle) values (?)";
                        String param3 = txtUnidad.getText();
                        PreparedStatement pst4 = cn.prepareStatement(query4);
                        pst4.setString(1, param3);
                        int up2 = pst4.executeUpdate();
                        try {

                            String query3 = "select idUnidadCompra from unidadDeCompra where Detalle=?";
                            String param2 = txtUnidad.getText();
                            PreparedStatement pst3 = cn.prepareStatement(query3);
                            pst3.setString(1, param2);
                            ResultSet rs3 = pst3.executeQuery();
                            while (rs3.next()) {
                                unidad = rs3.getInt("idUnidadCompra");
                            }
                            //Insertar Cliente
                            query = "INSERT INTO cliente (rut,nombre,IDorganizacion,cargo,razon_social,idUnidadCompra,correo,telefono,idregion,idcomuna,ciudad,direccion) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                            PreparedStatement insert = cn.prepareStatement(query);
                            insert.setString(1, rut);
                            insert.setString(2, nom);
                            insert.setInt(3, organizacion);
                            insert.setString(4, cargo);
                            insert.setString(5, razon);
                            insert.setInt(6, unidad);
                            insert.setString(7, correo);
                            insert.setString(8, telefono);
                            insert.setInt(9, region);
                            insert.setInt(10, comuna);
                            insert.setString(11, ciudad);
                            insert.setString(12, direccion);
                            int i = insert.executeUpdate();

                            /*
                             Query que permite actualizar los id de cliente
                             SET @count = 0;
                             UPDATE `cliente` SET `cliente`.`id` = @count:= @count + 1;
                             */
                            //Poner contador de ID en 0
                            query = "SET @count = 0";
                            PreparedStatement count = cn.prepareStatement(query);
                            int s = count.executeUpdate();
                            //Acualizar
                            query = "UPDATE `cliente` SET `cliente`.`idCliente` = @count:= @count + 1";
                            PreparedStatement update = cn.prepareStatement(query);
                            int u = update.executeUpdate();

                            if (i > 0) {
                                txtNombre.setText("");
                                txtCiudad.setText("");
                                txtCargo.setText("");
                                txtCorreo.setText("");
                                cmbOrganizacion.setSelectedIndex(0);
                                txtTelefono.setText("");
                                cmbRegion.setSelectedIndex(0);
                                cmbArea.setSelectedIndex(0);
                                txtRazon.setText("");
                                txtUnidad.setText("");
                                txtDireccion.setText("");
                                txtRut.setText("");
                                JOptionPane.showMessageDialog(null, "Registro Guardado con Exito");
                            }

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        }
                        String credencial;
                        int idUsuario = 0;
                        int up;
                        credencial = lblCredencial.getText();
                        idUsuario = Integer.parseInt(lblIDUsuario.getText());
                        String query5 = "INSERT INTO movimientosventas(`IDUsuario`, `Movimiento`) VALUES (?,'Ingresar Cliente')";
                        PreparedStatement pst5 = cn.prepareStatement(query5);
                        pst5.setInt(1, idUsuario);

                        up = pst5.executeUpdate();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
            }

        } else if (jChBox_UCompra.isSelected() == false) {
            if (jChBox_Organizacion.isSelected()) {

                if (txtRut.getText() == "" || txtNombre.getText() == ""
                        || cmbOrganizacion.getSelectedItem().toString() == "Seleccione Organizacion" || txtRazon.getText() == ""
                        || cmbArea.getSelectedItem().toString() == "Seleccione Área" || txtTelefono.getText() == ""
                        || cmbRegion.getSelectedItem().toString() == "Seleccione Region" || txtCiudad.getText() == ""
                        || txtDireccion.getText() == "") {
                    JOptionPane.showMessageDialog(null, "No pueden haber campos vacíos");
                } else {
                    String rut;
                    String nom;
                    String app;
                    int corp = 0;
                    String cargo;
                    String razon;
                    int unidad = 0;
                    String correo;
                    String telefono;
                    String ciudad;
                    String direccion;
                    String query = "";
                    rut = txtRut.getText();
                    nom = txtNombre.getText();

                    corp = cmbOrganizacion.getSelectedIndex();
                    cargo = txtCargo.getText();
                    razon = txtRazon.getText();
                    unidad = cmbUnidadCompra.getSelectedIndex();
                    correo = txtCiudad.getText();
                    telefono = cmbArea.getSelectedItem().toString() + txtTelefono.getText();

                    ciudad = txtCiudad.getText();

                    direccion = txtDireccion.getText();
                    try {
                        String query2 = "select idRegion,idComuna from comuna where nombreComuna=?";
                        String param = cmbComuna.getSelectedItem().toString();
                        PreparedStatement pst2 = cn.prepareStatement(query2);
                        pst2.setString(1, param);
                        ResultSet rs2 = pst2.executeQuery();
                        int region = 0;
                        int comuna = 0;
                        while (rs2.next()) {
                            region = rs2.getInt("idRegion");
                            comuna = rs2.getInt("idComuna");

                        }

                        String query5 = "insert into organizacion (NombreOrganizacion) values (?)";
                        String param5 = TxtOrganización.getText();
                        PreparedStatement pst5 = cn.prepareStatement(query5);
                        pst5.setString(1, param5);
                        int up5 = pst5.executeUpdate();

                        //Insertar Cliente
                        query = "INSERT INTO cliente (rut,nombre,IDorganizacion,cargo,razon_social,idUnidadCompra,correo,telefono,idregion,idcomuna,ciudad,direccion) VALUES (?,?,(select IdOrganizacion from organizacion where NombreOrganizacion = ?),?,?,?,?,?,?,?,?,?)";
                        PreparedStatement insert = cn.prepareStatement(query);
                        insert.setString(1, rut);
                        insert.setString(2, nom);
                        insert.setString(3, TxtOrganización.getText());
                        insert.setString(4, cargo);
                        insert.setString(5, razon);
                        insert.setInt(6, unidad);
                        insert.setString(7, correo);
                        insert.setString(8, telefono);
                        insert.setInt(9, region);
                        insert.setInt(10, comuna);
                        insert.setString(11, ciudad);
                        insert.setString(12, direccion);
                        int i = insert.executeUpdate();

                        /*
                         Query que permite actualizar los id de cliente
                         SET @count = 0;
                         UPDATE `cliente` SET `cliente`.`id` = @count:= @count + 1;
                         */
                        //Poner contador de ID en 0
                        query = "SET @count = 0";
                        PreparedStatement count = cn.prepareStatement(query);
                        int s = count.executeUpdate();
                        //Acualizar
                        query = "UPDATE `cliente` SET `cliente`.`idCliente` = @count:= @count + 1";
                        PreparedStatement update = cn.prepareStatement(query);
                        int u = update.executeUpdate();

                        if (i > 0) {
                            txtNombre.setText("");
                            txtCiudad.setText("");
                            txtCargo.setText("");
                            txtCorreo.setText("");
                            cmbOrganizacion.setSelectedIndex(0);
                            txtTelefono.setText("");
                            cmbRegion.setSelectedIndex(0);
                            cmbArea.setSelectedIndex(0);
                            txtRazon.setText("");
                            txtUnidad.setText("");
                            txtDireccion.setText("");
                            txtRut.setText("");
                            JOptionPane.showMessageDialog(null, "Registro Guardado con Exito");
                        }

                        int idUsuario = 0;
                        int up;
                        idUsuario = Integer.parseInt(lblIDUsuario.getText());
                        String query4 = "INSERT INTO movimientosventas(`IDUsuario`, `Movimiento`) VALUES (?,'Ingresar Cliente')";
                        PreparedStatement pst4 = cn.prepareStatement(query4);
                        pst4.setInt(1, idUsuario);
                        up = pst4.executeUpdate();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }

            } else if (jChBox_Organizacion.isSelected() == false) {
                if (txtRut.getText() == "" || txtNombre.getText() == ""
                        || cmbOrganizacion.getSelectedItem().toString() == "Seleccione Organizacion" || txtRazon.getText() == ""
                        || cmbArea.getSelectedItem().toString() == "Seleccione Área" || txtTelefono.getText() == ""
                        || cmbRegion.getSelectedItem().toString() == "Seleccione Region" || txtCiudad.getText() == ""
                        || txtDireccion.getText() == "") {
                    JOptionPane.showMessageDialog(null, "No pueden haber campos vacíos");
                } else {
                    String rut;
                    String nom;
                    String app;
                    int corp = 0;
                    String cargo;
                    String razon;
                    int unidad = 0;
                    String correo;
                    String telefono;
                    String ciudad;
                    String direccion;
                    String query = "";
                    rut = txtRut.getText();
                    nom = txtNombre.getText();

                    corp = cmbOrganizacion.getSelectedIndex();
                    cargo = txtCargo.getText();
                    razon = txtRazon.getText();
                    unidad = cmbUnidadCompra.getSelectedIndex();
                    correo = txtCorreo.getText();
                    telefono = cmbArea.getSelectedItem().toString() + txtTelefono.getText();

                    ciudad = txtCiudad.getText();

                    direccion = txtDireccion.getText();
                    try {
                        String query2 = "select idRegion,idComuna from comuna where nombreComuna=?";
                        String param = cmbComuna.getSelectedItem().toString();
                        PreparedStatement pst2 = cn.prepareStatement(query2);
                        pst2.setString(1, param);
                        ResultSet rs2 = pst2.executeQuery();
                        int region = 0;
                        int comuna = 0;
                        while (rs2.next()) {
                            region = rs2.getInt("idRegion");
                            comuna = rs2.getInt("idComuna");

                        }

                        //Insertar Cliente
                        query = "INSERT INTO cliente (rut,nombre,IDorganizacion,cargo,razon_social,idUnidadCompra,correo,telefono,idregion,idcomuna,ciudad,direccion) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                        PreparedStatement insert = cn.prepareStatement(query);
                        insert.setString(1, rut);
                        insert.setString(2, nom);
                        insert.setInt(3, corp);
                        insert.setString(4, cargo);
                        insert.setString(5, razon);
                        insert.setInt(6, unidad);
                        insert.setString(7, correo);
                        insert.setString(8, telefono);
                        insert.setInt(9, region);
                        insert.setInt(10, comuna);
                        insert.setString(11, ciudad);
                        insert.setString(12, direccion);
                        int i = insert.executeUpdate();

                        /*
                         Query que permite actualizar los id de cliente
                         SET @count = 0;
                         UPDATE `cliente` SET `cliente`.`id` = @count:= @count + 1;
                         */
                        //Poner contador de ID en 0
                        query = "SET @count = 0";
                        PreparedStatement count = cn.prepareStatement(query);
                        int s = count.executeUpdate();
                        //Acualizar
                        query = "UPDATE `cliente` SET `cliente`.`idCliente` = @count:= @count + 1";
                        PreparedStatement update = cn.prepareStatement(query);
                        int u = update.executeUpdate();

                        if (i > 0) {
                            txtNombre.setText("");
                            txtCiudad.setText("");
                            txtCargo.setText("");
                            txtCorreo.setText("");
                            cmbOrganizacion.setSelectedIndex(0);
                            txtTelefono.setText("");
                            cmbRegion.setSelectedIndex(0);
                            cmbArea.setSelectedIndex(0);
                            txtRazon.setText("");
                            txtUnidad.setText("");
                            txtDireccion.setText("");
                            txtRut.setText("");
                            JOptionPane.showMessageDialog(null, "Registro Guardado con Exito");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }
            }

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

        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtCiudad = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cmbRegion = new javax.swing.JComboBox<String>();
        btnGuardar = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        txtCargo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cmbComuna = new javax.swing.JComboBox<String>();
        cmbArea = new javax.swing.JComboBox<String>();
        jLabel1 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtRazon = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnCotizacion = new javax.swing.JButton();
        lblRegion = new javax.swing.JLabel();
        lblComuna = new javax.swing.JLabel();
        txtRut = new javax.swing.JFormattedTextField();
        txtUnidad = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jChBox_UCompra = new javax.swing.JCheckBox();
        cmbUnidadCompra = new javax.swing.JComboBox<String>();
        TxtOrganización = new javax.swing.JTextField();
        cmbOrganizacion = new javax.swing.JComboBox<String>();
        jChBox_Organizacion = new javax.swing.JCheckBox();
        Fondo = new javax.swing.JLabel();
        lblCredencial = new javax.swing.JLabel();
        lblIDUsuario = new javax.swing.JLabel();
        lbCodigo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Agregar Cliente");
        setBackground(new java.awt.Color(153, 0, 255));
        setMinimumSize(new java.awt.Dimension(825, 650));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Datos del cliente");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 150, -1));

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel9.setText("Nombre");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 125, -1, -1));

        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel11.setText("Organización");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 165, -1, -1));

        jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel12.setText("Correo");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 325, -1, -1));

        txtCiudad.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        getContentPane().add(txtCiudad, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 480, 400, -1));

        txtTelefono.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        getContentPane().add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 360, 320, -1));

        txtNombre.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        getContentPane().add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 400, -1));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel2.setText("Región");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 405, -1, -1));

        cmbRegion.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        cmbRegion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione Región" }));
        cmbRegion.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbRegionItemStateChanged(evt);
            }
        });
        getContentPane().add(cmbRegion, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 400, 400, 35));

        btnGuardar.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        btnGuardar.setText("Guardar Cliente");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 570, -1, 36));

        jLabel15.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel15.setText("Cargo");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 205, -1, -1));

        txtCargo.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        getContentPane().add(txtCargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, 400, -1));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel3.setText("Comuna");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 445, 90, -1));

        cmbComuna.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        cmbComuna.setEnabled(false);
        cmbComuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbComunaActionPerformed(evt);
            }
        });
        getContentPane().add(cmbComuna, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 440, 400, -1));

        cmbArea.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        cmbArea.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+56" }));
        getContentPane().add(cmbArea, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 360, -1, -1));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel1.setText("Ciudad");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 485, -1, -1));

        txtCorreo.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        getContentPane().add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 320, 400, -1));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel4.setText("U. de Compra");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 285, -1, 33));

        txtRazon.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        getContentPane().add(txtRazon, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 240, 400, -1));

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel6.setText("Teléfono");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 365, -1, 33));

        txtDireccion.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        getContentPane().add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 520, 400, -1));

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel7.setText("Direccion");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 525, -1, -1));

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel10.setText("Rut");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 85, -1, -1));

        btnCotizacion.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        btnCotizacion.setText("Guardar Cliente y Cotizar");
        btnCotizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCotizacionActionPerformed(evt);
            }
        });
        getContentPane().add(btnCotizacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 570, -1, -1));
        getContentPane().add(lblRegion, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 472, -1, -1));
        getContentPane().add(lblComuna, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 472, -1, -1));

        try {
            txtRut.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###-A")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtRut.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        getContentPane().add(txtRut, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 400, -1));

        txtUnidad.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        getContentPane().add(txtUnidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 280, 400, -1));

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jLabel5.setText("Razón Social");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 245, -1, 33));

        jChBox_UCompra.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jChBox_UCompra.setSelected(true);
        jChBox_UCompra.setText("Nueva U. de Compra");
        jChBox_UCompra.setActionCommand("");
        jChBox_UCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChBox_UCompraActionPerformed(evt);
            }
        });
        getContentPane().add(jChBox_UCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 290, 210, 20));

        cmbUnidadCompra.setFont(new java.awt.Font("sansserif", 0, 20)); // NOI18N
        cmbUnidadCompra.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione Unidad de Compra" }));
        getContentPane().add(cmbUnidadCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 280, 400, -1));

        TxtOrganización.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        getContentPane().add(TxtOrganización, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, 400, -1));

        cmbOrganizacion.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        cmbOrganizacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione Organización" }));
        getContentPane().add(cmbOrganizacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, 400, -1));

        jChBox_Organizacion.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jChBox_Organizacion.setSelected(true);
        jChBox_Organizacion.setText("Nueva Organización");
        jChBox_Organizacion.setActionCommand("");
        jChBox_Organizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChBox_OrganizacionActionPerformed(evt);
            }
        });
        getContentPane().add(jChBox_Organizacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 170, 210, 20));

        Fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PlataformaVentas/Imagenes/Blanco.png"))); // NOI18N
        getContentPane().add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        lblCredencial.setText("jLabel13");
        getContentPane().add(lblCredencial, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 580, -1, -1));

        lblIDUsuario.setText("jLabel14");
        getContentPane().add(lblIDUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 580, -1, -1));

        lbCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        getContentPane().add(lbCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        Acusador();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void Acusador() {
        String id = lblIDUsuario.getText();
        String respuesta = JOptionPane.showInputDialog(null, "Escriba Su codigo de autorización.", "Permisos de acción", JOptionPane.INFORMATION_MESSAGE);
        if (lbCodigo.getText().equals(respuesta)) {
            JOptionPane.showMessageDialog(null, "Codigo de autorización Correcto.", "Correcto", JOptionPane.INFORMATION_MESSAGE);
            IngresarCliente();
            String idCliente = "";
            try {
                String query1 = "Select IDCliente from Cliente order by IDCliente desc limit 1";
                PreparedStatement pst1 = cn.prepareStatement(query1);
                ResultSet rs = pst1.executeQuery();
                if (rs.next()) {
                    idCliente = rs.getString("IDCliente");
                }

                String Comentario = "El usuario nro. " + id + " a ingresado al Cliente con el id nro. " + idCliente;

                String query = "INSERT INTO movimientosventas (IDUsuario,Movimiento) VALUES (?,?)";
                PreparedStatement pst = cn.prepareStatement(query);
                pst.setInt(1, Integer.parseInt(lblIDUsuario.getText()));
                pst.setString(2, Comentario);
                int up = pst.executeUpdate();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Codigo de autorizacion invalido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cmbComunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbComunaActionPerformed

    }//GEN-LAST:event_cmbComunaActionPerformed

    private void btnCotizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCotizacionActionPerformed

        BuscarProducto buscar = new BuscarProducto();
        buscar.lblRut.setText(txtRut.getText());
        Acusador();
        buscar.setVisible(true);


    }//GEN-LAST:event_btnCotizacionActionPerformed

    private void jChBox_UCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChBox_UCompraActionPerformed
        if (jChBox_UCompra.isSelected()) {
            txtUnidad.setEnabled(true);
            txtUnidad.setVisible(true);
            cmbUnidadCompra.setVisible(false);
            cmbUnidadCompra.setEnabled(false);
        } else {
            cmbUnidadCompra.setVisible(true);
            cmbUnidadCompra.setEnabled(true);
            txtUnidad.setEnabled(false);
            txtUnidad.setVisible(false);
        }
    }//GEN-LAST:event_jChBox_UCompraActionPerformed

    private void jChBox_OrganizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChBox_OrganizacionActionPerformed
        if (jChBox_Organizacion.isSelected()) {
            TxtOrganización.setEnabled(true);
            TxtOrganización.setVisible(true);
            cmbOrganizacion.setVisible(false);
            cmbOrganizacion.setEnabled(false);
        } else {
            cmbOrganizacion.setVisible(true);
            cmbOrganizacion.setEnabled(true);
            TxtOrganización.setEnabled(false);
            TxtOrganización.setVisible(false);
        }
    }//GEN-LAST:event_jChBox_OrganizacionActionPerformed

    private void cmbRegionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbRegionItemStateChanged
        String region = cmbRegion.getSelectedItem().toString();

        if (region.equals("Seleccione Región")) {
            cmbComuna.disable();
        } else {
            cmbComuna.removeAllItems();
            cmbComuna.enable();

            try {
                String query = "select co.nombreComuna "
                        + "from Comuna co join Region re on co.IDRegion = re.IDRegion "
                        + "where re.NombreRegion = ?  "
                        + "order by co.nombreComuna asc";
                String param = region;
                PreparedStatement pst = cn.prepareStatement(query);
                pst.setString(1, param);
                ResultSet rs = pst.executeQuery();
                cmbComuna.addItem("Todas las Comunas");
                while (rs.next()) {
                    cmbComuna.addItem(rs.getString(1));
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }

        }
    }//GEN-LAST:event_cmbRegionItemStateChanged

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
            java.util.logging.Logger.getLogger(DatosCliente.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DatosCliente.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DatosCliente.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DatosCliente.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DatosCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fondo;
    private javax.swing.JTextField TxtOrganización;
    private javax.swing.JButton btnCotizacion;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmbArea;
    private javax.swing.JComboBox<String> cmbComuna;
    private javax.swing.JComboBox<String> cmbOrganizacion;
    private javax.swing.JComboBox<String> cmbRegion;
    private javax.swing.JComboBox<String> cmbUnidadCompra;
    private javax.swing.JCheckBox jChBox_Organizacion;
    private javax.swing.JCheckBox jChBox_UCompra;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    public javax.swing.JLabel lbCodigo;
    private javax.swing.JLabel lblComuna;
    public javax.swing.JLabel lblCredencial;
    public javax.swing.JLabel lblIDUsuario;
    private javax.swing.JLabel lblRegion;
    public javax.swing.JTextField txtCargo;
    private javax.swing.JTextField txtCiudad;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDireccion;
    public javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRazon;
    private javax.swing.JFormattedTextField txtRut;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtUnidad;
    // End of variables declaration//GEN-END:variables

    private Login Login() {
        throw new UnsupportedOperationException("Ha ocurrido un error");
    }
}
