/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author Diego González
 */
public class Cotizacion {

    private String nombreGenerico;
    private String sku;
    private int cantidad;
    private int precio;
    private double subtotal;
    private double iva;
    private double neto;
    private double total;

    public Cotizacion(String nombreGenerico, String sku, int cantidad, int precio, double subtotal, double iva, double neto, double total) {
        this.nombreGenerico = nombreGenerico;
        this.sku = sku;
        this.cantidad = cantidad;
        this.precio = precio;
        this.subtotal = subtotal;
        this.iva = iva;
        this.neto = neto;
        this.total = total;
    }

    /**
     * @return the nombreGenerico
     */
    public String getNombreGenerico() {
        return nombreGenerico;
    }

    /**
     * @param nombreGenerico the nombreGenerico to set
     */
    public void setNombreGenerico(String nombreGenerico) {
        this.nombreGenerico = nombreGenerico;
    }

    /**
     * @return the sku
     */
    public String getSku() {
        return sku;
    }

    /**
     * @param sku the sku to set
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the precio
     */
    public int getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(int precio) {
        this.precio = precio;
    }

    /**
     * @return the subtotal
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     * @param subtotal the subtotal to set
     */
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * @return the iva
     */
    public double getIva() {
        return iva;
    }

    /**
     * @param iva the iva to set
     */
    public void setIva(double iva) {
        this.iva = iva;
    }

    /**
     * @return the neto
     */
    public double getNeto() {
        return neto;
    }

    /**
     * @param neto the neto to set
     */
    public void setNeto(double neto) {
        this.neto = neto;
    }

    /**
     * @return the total
     */
    public double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total) {
        this.total = total;
    }
}
