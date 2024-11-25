package repositorio.dao.proveedor;

import java.util.List;
import modelo.proveedor.Proveedor;

/**
 *
 * @author rodri
 */

public interface IDaoProveedor{
    // Alta
    public void insertarNuevoProveedor(Proveedor proveedor);

    // Baja
    public void eliminarProveedor(String codigo, String nombre, String apellido, String nombreFantasia);
    
    // Modificar
    public void modificarProveedor(String codigo, Proveedor proveedor);

    // Listar
    public List<Proveedor> getProveedores(String codigo, String nombre, String apellido, String nombreFantasia);
    
    // Obtener
    public Proveedor obtenerProveedor(String codigo, String nombreFantasia);
    
    // ComboBox
    public List<Proveedor> getProveedoresComboBox();
}
