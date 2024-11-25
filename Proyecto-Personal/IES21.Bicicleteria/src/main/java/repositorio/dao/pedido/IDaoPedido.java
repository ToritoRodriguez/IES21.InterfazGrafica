package repositorio.dao.pedido;

import java.util.List;
import modelo.pedido.DetallePedido;
import modelo.pedido.Pedido;

/**
 *
 * @author rodri
 */

public interface IDaoPedido {
    public void insertarNuevoPedido(Pedido pedido);

    public void eliminarPedido(String codigoPedido);
    
    public void modificarPedido(String codigoPedido, Pedido pedidoModificado);
    
    public List<Pedido> obtenerPedidos(String codigoPedido, String nombreVendedor, String apellidoVendedor,
            String nombreCliente, String apellidoCliente , String fechaDesde , String fechaHasta);
    
    public List<DetallePedido> obtenerDetallesPorPedido(String codigoPedido);
    
    
}
