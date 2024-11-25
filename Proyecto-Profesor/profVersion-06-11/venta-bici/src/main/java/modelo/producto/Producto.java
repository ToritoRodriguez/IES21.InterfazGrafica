/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.producto;
import java.util.List;
import modelo.producto.marca.Modelo;
import modelo.proveedor.Proveedor;
/**
 *
 * @author Alumno
 */
public class Producto {
    
    private String codigo;
    private Categoria categoria;
    private Modelo modelo;
    private List<Producto> partes;
    private double precio;
    private String color;
    private String pathImagen;
    private Proveedor proveedor;

    public Producto(String codigo, Categoria categoria, Modelo modelo, List<Producto> partes, double precio, String color, String pathImagen, Proveedor proveedor) {
        this.codigo = codigo;
        this.categoria = categoria;
        this.modelo = modelo;
        this.partes = partes;
        this.precio = precio;
        this.color = color;
        this.pathImagen = pathImagen;
        this.proveedor = proveedor;
    }

    

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPathImagen() {
        return pathImagen;
    }

    public void setPathImagen(String pathImagen) {
        this.pathImagen = pathImagen;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public List<Producto> getPartes() {
        return partes;
    }

    public void setPartes(List<Producto> partes) {
        this.partes = partes;
    }
}
