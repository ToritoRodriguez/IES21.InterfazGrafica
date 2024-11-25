package modelo.pedido;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import modelo.cliente.Cliente;
import modelo.producto.Producto;
import modelo.vendedor.Vendedor;

/**
 *
 * @author rodri
 */

public class Pedido {

    private String codigo;  
    private LocalDate fecha;  
    private Vendedor vendedor;  
    private Cliente cliente;  
    private EstadoPedido estado;  
    private double totalPedido;
    private List<DetallePedido> detalles;

    public Pedido() {
        this.detalles = new ArrayList<>();
    }
    
    // Este constructor se usa en altaPedido
    public Pedido(LocalDate fecha, Vendedor vendedor, Cliente cliente, EstadoPedido estado, double totalPedido, List<DetallePedido> detalles) {
        this.fecha = fecha;
        this.vendedor = vendedor;
        this.cliente = cliente;
        this.estado = estado;
        this.totalPedido = totalPedido;
        this.detalles = detalles;
    }

    // Este constructor se usa en obtenerPedidos
    public Pedido(String codigo, Vendedor vendedor, Cliente cliente, LocalDate fecha, EstadoPedido estado, double totalPedido) {
        this.codigo = codigo;
        this.vendedor = vendedor;
        this.cliente = cliente;
        this.fecha = fecha;
        this.estado = estado;
        this.totalPedido = totalPedido;
    }
    
    public String getCodigo() {
        return codigo;  
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
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
        if (detalles == null) {
            detalles = new ArrayList<>(); // Si es null, inicializamos como lista vacía
        }
        return detalles;    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    public String getCodigoVendedor() {
        return vendedor.getCodigo();  
    }

    public String getCodigoCliente() {
        return cliente.getCodigo();  
    }
    
    // Método para agregar un producto al detalle del pedido
    public void agregarProducto(Producto producto, int cantidad) {
        // Comprobamos si el producto ya está en el detalle
        for (DetallePedido dp : detalles) {
            if (dp.getProducto().equals(producto)) {
                // Si el producto ya está, sumamos la cantidad
                dp.setCantidad(dp.getCantidad() + cantidad);
                return;
            }
        }
        // Si el producto no estaba, lo agregamos como nuevo detalle
        detalles.add(new DetallePedido(producto, cantidad));
    }

    // Método para eliminar un producto del detalle del pedido
    public void eliminarProducto(Producto producto) {
        detalles.removeIf(dp -> dp.getProducto().equals(producto));
    }
    
    @Override
    public String toString() {
        return this.codigo;
    }
}