/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package repositorio.dao.cliente;

import java.util.List;
import modelo.cliente.Cliente;

public interface IDaoCliente {
    
    public void insertarCliente(Cliente cliente);
    
    public void modificarCliente(int id,Cliente cliente);
    
    public void eliminarCliente(int id);
    
    public Cliente obtenerCliente(String  codigo);
    
    public List<Cliente> getClientes(String codigo,String nombre, String apellido,int dni);
    
    public int getProximoCodigo();
}
