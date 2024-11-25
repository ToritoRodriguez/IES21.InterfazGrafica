/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.pedido;

import java.util.Date;
import java.util.List;
import modelo.cliente.Cliente;
import modelo.vendedor.Vendedor;

public class Pedido {
    
    private Date fecha;
    private Vendedor vendedor;
    private Cliente cliente;
    private EstadoPedido estado;
    private double totalPedido;
    private List<DetallePedido> detalles;

    public Pedido(Date fecha, Vendedor vendedor, Cliente cliente, EstadoPedido estado, double totalPedido, List<DetallePedido> detalles) {
        this.fecha = fecha;
        this.vendedor = vendedor;
        this.cliente = cliente;
        this.estado = estado;
        this.totalPedido = totalPedido;
        this.detalles = detalles;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(double totalPedido) {
        this.totalPedido = totalPedido;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }
}
