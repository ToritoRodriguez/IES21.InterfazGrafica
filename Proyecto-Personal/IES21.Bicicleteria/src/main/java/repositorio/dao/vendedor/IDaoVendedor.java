package repositorio.dao.vendedor;

import java.util.List;
import modelo.vendedor.Vendedor;

/**
 *
 * @author rodri
 */

public interface IDaoVendedor {
    // Alta
    public void insertarNuevoVendedor(Vendedor vendedor);

    // Baja
    public void eliminarVendedor(String codigo, String nombre, String apellido, String sucursal);

    // Modificar
    public void modificarVendedor(String codigo, Vendedor vendedor);

    // Listar
    public List<Vendedor> getVendedores(String codigo, String nombre, String apellido, String sucursal);
    
    // Obtener
    public Vendedor obtenerVendedor(String codigo, String nombre, String apellido);
}
