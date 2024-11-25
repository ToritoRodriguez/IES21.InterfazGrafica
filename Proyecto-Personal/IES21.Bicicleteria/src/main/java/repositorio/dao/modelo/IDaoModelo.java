package repositorio.dao.modelo;
import java.util.List;
import modelo.producto.marca.Modelo;
import modelo.producto.marca.Rodado;

/**
 *
 * @author rodri
 */

public interface IDaoModelo {
    public void insertarNuevoModelo(Modelo modelo);

    public void eliminarModelo(String codigoModelo, String nombreModelo);
    
    public void modificarModelo(String codigoModelo, Modelo modeloModificado);

    public Modelo obtenerModelo(String codigoModelo, String nombreModelo, String codigoMarca, Rodado rodado);
    
    public List<Modelo> getModelos(String codigoModelo, String nombreModelo, String codigoMarca, Rodado rodado);
    
    public List<Modelo> getModelosComboBox();
}