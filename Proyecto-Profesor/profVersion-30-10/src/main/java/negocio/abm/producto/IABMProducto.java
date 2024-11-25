/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.abm.producto;

import java.util.List;
import modelo.producto.Categoria;
import modelo.producto.Producto;
import modelo.producto.TipoCategoria;
import modelo.producto.marca.Modelo;
import modelo.proveedor.Proveedor;

public interface IABMProducto {

    public void cargarProducto(Producto producto);
   
    public void cargarProducto(String codigo, Categoria categoria, Modelo modelo,
            String color, String pathImagen, Proveedor proveedor);
    
    public boolean asignarPartesAProducto(String codigo,Producto producto);
    
    public void listarProductos(TipoCategoria tc);
    
    public void listarProductos(String codigoCategori,String modelo);
}
