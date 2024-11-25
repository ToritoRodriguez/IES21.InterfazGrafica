/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.abm.pedido;

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
public class ABMPedido implements IABMPedido {

    @Override
    public void crearPedido(Vendedor vendedor, Cliente cliente, List<DetallePedido> detalles) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public DetallePedido crearDetalle(Producto producto, int cantidad, int descueto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Pedido> traerTodosLosPedidios() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Pedido> pedidosPorVendedor(String codigo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Pedido> pedidosPorCliente(String codigo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
