package repositorio.dao.cliente;

import java.util.List;
import modelo.cliente.Cliente;

/**
 *
 * @author rodri
 */

public interface IDaoCliente{
    // Alta
    public void insertarNuevoCliente(Cliente cliente);
    
    // Baja
    public void eliminarCliente(String codigo, String nombre, String apellido);
    
    // Modificar
    public void modificarCliente(String codigo, Cliente cliente);

    // Listar
    public List<Cliente> getClientes(String codigo, String nombre, String apellido);
    
    //Obtener
    public Cliente obtenerCliente(String codigo, String nombre, String apellido);
}