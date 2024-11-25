package modelo.pedido;

import modelo.producto.Producto;

/**
 *
 * @author rodri
 */

public class DetallePedido {

    private String codigoProducto;  
    private String codigo;  
    private Producto producto;
    private String codigoPedido;
    private int cantidad;
    private double precio;
    private int descuento;
    private double total;  

    // Este constructor se usa en obtenerDetallesPorPedido
    public DetallePedido(String codigoPedido, String codigoProducto, Producto producto, int cantidad, double precio, int descuento, double total) {
        this.codigo = codigoPedido;  
        this.codigoProducto = codigoProducto;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.descuento = descuento;
        this.total = calcularTotal(); 
    }

    public DetallePedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }
    
    public String getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }
    
    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public double getTotal() {
        return total;  
    }

    private double calcularTotal() {
        double totalSinDescuento = cantidad * precio;
        double descuentoAplicado = totalSinDescuento * (descuento / 100.0);
        return totalSinDescuento - descuentoAplicado;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getNombreProducto() {
        return producto != null ? producto.getNombre() : "Desconocido";  
    }
    
    public void asignarCodigoPedido(String codigoPedido) {
        this.codigo = codigoPedido; 
    }
}
