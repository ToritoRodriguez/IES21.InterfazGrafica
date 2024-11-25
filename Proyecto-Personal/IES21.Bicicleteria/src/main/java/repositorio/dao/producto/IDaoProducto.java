package repositorio.dao.producto;

import java.util.List;
import modelo.producto.Producto;

/**
 *
 * @author rodri
 */

public interface IDaoProducto {
    public void insertarNuevoProducto(Producto producto);

    public void eliminarProducto(String codigoProducto, String nombreProducto);

    public void modificarProducto(String codigoProducto, Producto productoModificado);

    public Producto obtenerProducto(String codigoProducto, String nombreProducto, String codigoCategoria,
            String codigoModelo, String codigoProveedor, String codigoMarca, int cantidad);
    
    public List<Producto> getProductos(String codigoProducto, String nombreProducto, String codigoCategoria,
            String codigoModelo, String codigoProveedor, int cantidad, String codigoMarca);
    
    public List<Producto> getProductosComboBox();
}