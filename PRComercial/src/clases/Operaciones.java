/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import javax.swing.JTable;

/**
 *
 * @author Diego
 */
public class Operaciones {
 
    private String ConsultarOC= "SELECT idOrden, codigoOrdenCompra 'Codigo de OC', rutCliente 'Rut Cliente', DireccionDemandante, Telefono, Demandante 'Demandante', unidadCompra 'Unidad de Compra', fechaEnvioOC, codigoEstado, NombreOrdenCompra 'Nombre de OC', fechaEntregaProductos, direccionesDespacho, direccionEnvioFactura, idTipoDespacho, contactoPago, idFormaPAgo, contactoOC, emailEnvioFactura, especificacionComprador, especificacionProveedor,neto, dcto, subtotal, iva, impuestoEspecifico, total 'Total', codigo_autorizacion,nombre_proveedor 'Proveedor' FROM ordendecompra\n";

    public String getConsultarOC() {
        return ConsultarOC;
    }

    
    
    public void OcultarColumnsOC(JTable table){
        OcultarColumnas(table);
    }
    
    private void OcultarColumnas(JTable jtable){
        
        //columna 0 = idOrden
        jtable.getColumnModel().getColumn(0).setMaxWidth(0);
        jtable.getColumnModel().getColumn(0).setMinWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        
        //columna 3 = Direccion demandante
        jtable.getColumnModel().getColumn(3).setMaxWidth(0);
        jtable.getColumnModel().getColumn(3).setMinWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(3).setMinWidth(0);
        
        //columna 4 = Telefono
        jtable.getColumnModel().getColumn(4).setMaxWidth(0);
        jtable.getColumnModel().getColumn(4).setMinWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(4).setMinWidth(0);
        
        //columna 7 = FechaEnvioOC
        jtable.getColumnModel().getColumn(7).setMaxWidth(0);
        jtable.getColumnModel().getColumn(7).setMinWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(7).setMinWidth(0);
        
        //columna 8 = CodigoEstado
        jtable.getColumnModel().getColumn(8).setMaxWidth(0);
        jtable.getColumnModel().getColumn(8).setMinWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(8).setMaxWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(8).setMinWidth(0);
        
        //columna 10 = fechaEntregaProductos
        jtable.getColumnModel().getColumn(10).setMaxWidth(0);
        jtable.getColumnModel().getColumn(10).setMinWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(10).setMaxWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(10).setMinWidth(0);
        
        //columna 11 = direccionesDespacho
        jtable.getColumnModel().getColumn(11).setMaxWidth(0);
        jtable.getColumnModel().getColumn(11).setMinWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(11).setMaxWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(11).setMinWidth(0);
        
        //columna 12 = direccionEnvioFactura
        jtable.getColumnModel().getColumn(13).setMaxWidth(0);
        jtable.getColumnModel().getColumn(13).setMinWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(13).setMaxWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(13).setMinWidth(0);
        
        //columna 13 = idTipoDespacho
        jtable.getColumnModel().getColumn(13).setMaxWidth(0);
        jtable.getColumnModel().getColumn(13).setMinWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(13).setMaxWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(13).setMinWidth(0);
        
        //columna 14 = contactoPago
        jtable.getColumnModel().getColumn(14).setMaxWidth(0);
        jtable.getColumnModel().getColumn(14).setMinWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(14).setMaxWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(14).setMinWidth(0);
        
        //columna 15 = idFormaPAgo
        jtable.getColumnModel().getColumn(15).setMaxWidth(0);
        jtable.getColumnModel().getColumn(15).setMinWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(15).setMaxWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(15).setMinWidth(0);
        
        //columna 16 = contactoOC
        jtable.getColumnModel().getColumn(16).setMaxWidth(0);
        jtable.getColumnModel().getColumn(16).setMinWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(16).setMaxWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(16).setMinWidth(0);
        
        //columna 17 = emailEnvioFactura
        jtable.getColumnModel().getColumn(17).setMaxWidth(0);
        jtable.getColumnModel().getColumn(17).setMinWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(17).setMaxWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(17).setMinWidth(0);
    
        //columna 21 = especificacionComprador
        jtable.getColumnModel().getColumn(18).setMaxWidth(0);
        jtable.getColumnModel().getColumn(18).setMinWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(18).setMaxWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(18).setMinWidth(0);
        
        //columna 19 = especificacionProveedor
        jtable.getColumnModel().getColumn(19).setMaxWidth(0);
        jtable.getColumnModel().getColumn(19).setMinWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(19).setMaxWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(19).setMinWidth(0);
        
        //columna 20 = preciounitario
        jtable.getColumnModel().getColumn(20).setMaxWidth(0);
        jtable.getColumnModel().getColumn(20).setMinWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(20).setMaxWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(20).setMinWidth(0);
        
        //columna 21 = descuento
        jtable.getColumnModel().getColumn(21).setMaxWidth(0);
        jtable.getColumnModel().getColumn(21).setMinWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(21).setMaxWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(21).setMinWidth(0);
        
        //columna 22 = cargos
        jtable.getColumnModel().getColumn(22).setMaxWidth(0);
        jtable.getColumnModel().getColumn(22).setMinWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(22).setMaxWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(22).setMinWidth(0);
        
        //columna 23 = valorTotal
        jtable.getColumnModel().getColumn(23).setMaxWidth(0);
        jtable.getColumnModel().getColumn(23).setMinWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(23).setMaxWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(23).setMinWidth(0);
        
        //columna 24 = neto
        jtable.getColumnModel().getColumn(24).setMaxWidth(0);
        jtable.getColumnModel().getColumn(24).setMinWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(24).setMaxWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(24).setMinWidth(0);
        
        
        
        //columna 26 = subtotal
        jtable.getColumnModel().getColumn(26).setMaxWidth(0);
        jtable.getColumnModel().getColumn(26).setMinWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(26).setMaxWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(26).setMinWidth(0);
        
    }
}
