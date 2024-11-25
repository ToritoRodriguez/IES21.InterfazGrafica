package repositorio.dao.marca;

import java.util.List;
import modelo.producto.marca.Marca;

/**
 *
 * @author rodri
 */

public interface IDaoMarca{
    //Alta
    public void insertarNuevaMarca(Marca marca);
    
    // Baja
    public void eliminarMarca(String codigo, String nombre);

    // Modificar
    public void modificarMarca(String codigoMarca, Marca marcaModificada);

    // Listar
    public List<Marca> getMarcas(String codigoMarca, String nombreMarca);
    
    // Obtener
    public Marca obtenerMarca(String codigoMarca, String nombreMarca);
    
    public List<Marca> getMarcasComboBox();
}
