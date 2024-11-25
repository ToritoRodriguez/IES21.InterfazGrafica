/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.abm.producto;

import modelo.producto.Categoria;
import modelo.producto.Producto;
import modelo.producto.TipoCategoria;
import modelo.producto.marca.Modelo;
import modelo.proveedor.Proveedor;

/**
 *
 * @author Alumno
 */
public class ABMProduto implements IABMProducto {

    @Override
    public void cargarProducto(Producto producto) {
        
    }

    @Override
    public void cargarProducto(String codigo, Categoria categoria, Modelo modelo, String color, String pathImagen, Proveedor proveedor) {
    
    }

    @Override
    public boolean asignarPartesAProducto(String codigo, Producto producto) {
        return false;
    }

    @Override
    public void listarProductos(TipoCategoria tc) {
    }

    @Override
    public void listarProductos(String codigoCategori, String modelo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
