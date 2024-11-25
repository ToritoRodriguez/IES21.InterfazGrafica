package modelo.producto;

import modelo.producto.marca.Marca;
import modelo.producto.marca.Modelo;
import modelo.proveedor.Proveedor;

/**
 *
 * @author rodri
 */

public class Producto {

    private int id; 
    private String codigo;
    private String nombre;
    private Marca marca;
    private double precio;
    private String descripcion;
    private int cantidad;
    private Categoria categoria; 
    private Modelo modelo; 
    private Proveedor proveedor; 
    private String pathImagen;

    // Este constructor se usa en getProductos
    public Producto(String nombre, double precio, String descripcion, int cantidad, 
                    Categoria categoria, Modelo modelo, Proveedor proveedor, String pathImagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.categoria = categoria;
        this.modelo = modelo;
        this.proveedor = proveedor;
        this.pathImagen = pathImagen;
    }

    // Este constructor se usa en obtenerProducto
    public Producto(String codigo, String nombre, String descripcion, Categoria categoria, Modelo modelo, Proveedor proveedor, float precio, String pathImagen, int cantidad, Marca marca) {
        this.codigo = codigo;  
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.modelo = modelo;
        this.proveedor = proveedor;
        this.precio = precio;
        this.pathImagen = pathImagen;
        this.cantidad = cantidad;
        this.marca = marca; 
    }
    
    // Este constructor se usa en getProductosComboBox
    public Producto(String codigo, String nombre, double precio) {
        this.codigo = codigo;  
        this.nombre = nombre; 
        this.precio = precio;
    }
    
    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public String getPathImagen() {
        return pathImagen;
    }

    public void setPathImagen(String pathImagen) {
        this.pathImagen = pathImagen;
    }

    public int getIdCategoria() {
        return categoria != null ? categoria.getId() : 0;
    }

    public String getCodigoProveedor() {
        return proveedor != null ? proveedor.getCodigo() : null;
    }
    
    @Override
    public String toString() {
        return this.nombre;
    }
}