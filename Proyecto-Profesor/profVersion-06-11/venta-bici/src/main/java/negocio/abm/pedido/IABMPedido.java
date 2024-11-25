/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.abm.pedido;

import com.sun.nio.sctp.PeerAddressChangeNotification;
import java.util.List;
import modelo.cliente.Cliente;
import modelo.pedido.DetallePedido;
import modelo.pedido.Pedido;
import modelo.producto.Producto;
import modelo.vendedor.Vendedor;

/**
 *
 * @author Alumno
 */
public interface IABMPedido {
    
    public void crearPedido(Vendedor vendedor,Cliente cliente,List<DetallePedido>  detalles);
    
    public DetallePedido crearDetalle(Producto producto,int cantidad,int descueto);
    
    public List<Pedido> traerTodosLosPedidios();
    
    public List<Pedido> pedidosPorVendedor(String codigo);
    
    public List<Pedido> pedidosPorCliente(String codigo); 
    
    
}
