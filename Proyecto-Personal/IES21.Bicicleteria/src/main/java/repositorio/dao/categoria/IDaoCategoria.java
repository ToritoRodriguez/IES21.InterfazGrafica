package repositorio.dao.categoria;

import java.util.List;
import modelo.producto.Categoria;
import modelo.producto.TipoCategoria;

/**
 *
 * @author rodri
 */

public interface IDaoCategoria {
    // Alta
    public void insertarNuevaCategoria(Categoria categoria);

    // Baja
    public void eliminarCategoria(String codigoCategoria, String nombreCategoria, TipoCategoria tipoCategoria);

    // Modificar
   public void modificarCategoria(String codigoCategoria, Categoria categoriaModificada);
    
    // Listar
    public List<Categoria> getCategorias(String codigoCategoria, String nombreCategoria, TipoCategoria tipoCategoria);
    
    // Obtener
    public Categoria obtenerCategoria(String codigoCategoria, String nombreCategoria, TipoCategoria tipoCategoria);
    
    // ComboBox
    public List<Categoria> getCategoriasComboBox();
}