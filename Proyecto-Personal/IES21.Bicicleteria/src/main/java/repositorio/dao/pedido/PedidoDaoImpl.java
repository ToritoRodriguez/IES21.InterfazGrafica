package repositorio.dao.pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import modelo.cliente.Cliente;
import modelo.pedido.DetallePedido;
import repositorio.dao.ConexionDb;
import modelo.pedido.Pedido;
import modelo.vendedor.Vendedor;
import modelo.pedido.EstadoPedido;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import modelo.producto.Producto;
import repositorio.dao.producto.ProductoDaoImpl;

/**
 *
 * @author rodri
 */

public class PedidoDaoImpl implements IDaoPedido {

    private ConexionDb conexionDb;

    public PedidoDaoImpl() {
        this.conexionDb = new ConexionDb();
    }
    
    @Override
    public void insertarNuevoPedido(Pedido pedido) {
        String SQL_INSERT_PEDIDO = "INSERT INTO pedido (fecha, codigo_vendedor, codigo_cliente, codigo, estado, total_pedido) VALUES (?, ?, ?, ?, ?, ?)";

        String proximoCodigoPedido = getProximoCodigoPedido(); 

        try {
            
            if (pedido.getVendedor() == null || pedido.getVendedor().getCodigo() == null) {
                throw new IllegalArgumentException("El vendedor es obligatorio y su código no puede ser null");
            }
            if (pedido.getCliente() == null || pedido.getCliente().getCodigo() == null) {
                throw new IllegalArgumentException("El cliente es obligatorio y su código no puede ser null");
            }
            if (proximoCodigoPedido == null || proximoCodigoPedido.isEmpty()) {
                throw new IllegalStateException("No se pudo generar un código para el pedido");
            }

            LocalDate localDate = pedido.getFecha();
            java.sql.Date sqlDate = (localDate != null) ? java.sql.Date.valueOf(localDate) : null;
            if (sqlDate == null) {
                throw new IllegalArgumentException("La fecha del pedido no puede ser null");
            }

            try (PreparedStatement stmtPedido = conexionDb.obtenerConexion().prepareStatement(SQL_INSERT_PEDIDO, Statement.RETURN_GENERATED_KEYS)) {
                stmtPedido.setDate(1, sqlDate);
                stmtPedido.setString(2, pedido.getVendedor().getCodigo());  
                stmtPedido.setString(3, pedido.getCliente().getCodigo());   
                stmtPedido.setString(4, proximoCodigoPedido);              
                stmtPedido.setString(5, pedido.getEstado().name());         
                stmtPedido.setDouble(6, pedido.getTotalPedido());          

                int affectedRowsPedido = stmtPedido.executeUpdate();
                
                if (affectedRowsPedido > 0) {
                    pedido.setCodigo(proximoCodigoPedido);
                    System.out.println("Pedido insertado con éxito con código: " + proximoCodigoPedido);

                    insertarDetallesPedido(proximoCodigoPedido, pedido.getDetalles());
                } else {
                    System.out.println("Error al insertar el pedido.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar el pedido: " + e.getMessage());
        } catch (IllegalArgumentException | IllegalStateException ex) {
            System.out.println("Error en los datos del pedido: " + ex.getMessage());
        }
    }

    private void insertarDetallesPedido(String codigoPedido, List<DetallePedido> detalles) throws SQLException {
        String SQL_INSERT_DETALLE_PEDIDO = "INSERT INTO detalle_pedido (codigo_pedido, codigo_producto, codigo, cantidad, precio, descuento, total) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmtDetalle = conexionDb.obtenerConexion().prepareStatement(SQL_INSERT_DETALLE_PEDIDO)) {

            Connection conn = conexionDb.obtenerConexion();
            conn.setAutoCommit(false);

            for (DetallePedido detalle : detalles) {

                stmtDetalle.setString(1, codigoPedido); 
                stmtDetalle.setString(2, detalle.getProducto().getCodigo());  
                stmtDetalle.setString(3, detalle.getCodigo());  
                stmtDetalle.setInt(4, detalle.getCantidad());  
                stmtDetalle.setDouble(5, detalle.getPrecio());  
                stmtDetalle.setInt(6, detalle.getDescuento());  
                stmtDetalle.setDouble(7, detalle.getTotal());  
                stmtDetalle.addBatch();  
            }

            stmtDetalle.executeBatch();

            conn.commit();
            System.out.println("Detalles del pedido insertados correctamente.");
        } catch (SQLException e) {
            
            conexionDb.obtenerConexion().rollback();
            System.out.println("Error al insertar detalles del pedido: " + e.getMessage());
            throw e;  
        } finally {

            if (conexionDb.obtenerConexion() != null) {
                conexionDb.obtenerConexion().setAutoCommit(true);
            }
        }
    }
    
    @Override
    public List<Pedido> obtenerPedidos(String codigoPedido, String nombreVendedor, String apellidoVendedor,
            String nombreCliente, String apellidoCliente, String fechaDesde, String fechaHasta) {
        List<Pedido> listaPedidos = new ArrayList<>();
        StringBuilder sqlQuery = new StringBuilder(
                "SELECT p.codigo, p.codigo_vendedor, p.codigo_cliente, p.fecha, p.estado, p.total_pedido, "
                + "pv.nombre AS vendedor_nombre, pv.apellido AS vendedor_apellido, "
                + "pc.nombre AS cliente_nombre, pc.apellido AS cliente_apellido "
                + "FROM pedido p "
                + "JOIN vendedores v ON p.codigo_vendedor = v.codigo "
                + "JOIN personas pv ON v.id_persona = pv.id "
                + "JOIN clientes c ON p.codigo_cliente = c.codigo "
                + "JOIN personas pc ON c.id_persona = pc.id "
                + "WHERE 1=1");

        HashMap<Integer, Object> param = new HashMap<>();
        int index = 0;

        if (codigoPedido != null && !codigoPedido.isEmpty()) {
            sqlQuery.append(" AND p.codigo = ?");
            param.put(index++, codigoPedido);
        }

        if ((nombreVendedor != null && !nombreVendedor.isEmpty())
                || (apellidoVendedor != null && !apellidoVendedor.isEmpty())) {
            sqlQuery.append(" AND (");
            if (nombreVendedor != null && !nombreVendedor.isEmpty()) {
                sqlQuery.append("pv.nombre LIKE ?");
                param.put(index++, "%" + nombreVendedor + "%");
            }
            if (apellidoVendedor != null && !apellidoVendedor.isEmpty()) {
                if (nombreVendedor != null && !nombreVendedor.isEmpty()) {
                    sqlQuery.append(" OR ");
                }
                sqlQuery.append("pv.apellido LIKE ?");
                param.put(index++, "%" + apellidoVendedor + "%");
            }
            sqlQuery.append(")");
        }

        if ((nombreCliente != null && !nombreCliente.isEmpty())
                || (apellidoCliente != null && !apellidoCliente.isEmpty())) {
            sqlQuery.append(" AND (");
            if (nombreCliente != null && !nombreCliente.isEmpty()) {
                sqlQuery.append("pc.nombre LIKE ?");
                param.put(index++, "%" + nombreCliente + "%");
            }
            if (apellidoCliente != null && !apellidoCliente.isEmpty()) {
                if (nombreCliente != null && !nombreCliente.isEmpty()) {
                    sqlQuery.append(" OR ");
                }
                sqlQuery.append("pc.apellido LIKE ?");
                param.put(index++, "%" + apellidoCliente + "%");
            }
            sqlQuery.append(")");
        }

        if (fechaDesde != null && !fechaDesde.isEmpty()) {
            sqlQuery.append(" AND p.fecha >= ?");
            param.put(index++, fechaDesde);
        }
        if (fechaHasta != null && !fechaHasta.isEmpty()) {
            sqlQuery.append(" AND p.fecha <= ?");
            param.put(index++, fechaHasta);
        }

        try (ResultSet rs = conexionDb.ejecutarConsultaSqlConParametros(sqlQuery.toString(), param)) {
            while (rs.next()) {

                Vendedor vendedor = new Vendedor(
                        rs.getString("codigo_vendedor"),
                        rs.getString("vendedor_nombre"),
                        rs.getString("vendedor_apellido")
                );

                Cliente cliente = new Cliente(
                        rs.getString("codigo_cliente"),
                        rs.getString("cliente_nombre"),
                        rs.getString("cliente_apellido")
                );

                LocalDate fecha = LocalDate.parse(rs.getString("fecha"), DateTimeFormatter.ISO_DATE);

                Pedido pedido = new Pedido(
                        rs.getString("codigo"),
                        vendedor,
                        cliente,
                        fecha,
                        EstadoPedido.valueOf(rs.getString("estado")),
                        rs.getDouble("total_pedido")
                );

                listaPedidos.add(pedido);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los pedidos: " + e.getMessage());
        }

        return listaPedidos;
    }
    
    @Override
    public List<DetallePedido> obtenerDetallesPorPedido(String codigoPedido) {
        List<DetallePedido> detalles = new ArrayList<>();
        String sql = "SELECT * FROM detalle_pedido WHERE codigo_pedido = ?";

        try (PreparedStatement stmt = conexionDb.obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, codigoPedido);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Obtener datos del ResultSet
                    String codigoProducto = rs.getString("codigo_producto");

                    // Verificación y depuración
                    if (codigoProducto == null || codigoProducto.trim().isEmpty()) {
                        System.out.println("Error: Código de producto inválido en detalle del pedido.");
                        continue; // Continuar con el siguiente registro si el código del producto es inválido
                    }

                    System.out.println("Cargando producto con código: " + codigoProducto);

                    // Obtener el producto
                    ProductoDaoImpl productoDao = new ProductoDaoImpl();
                    Producto producto = productoDao.obtenerProducto(codigoProducto, null, null, null, null, null, 0);

                    if (producto == null) {
                        System.out.println("Error: No se encontró el producto con código: " + codigoProducto);
                        continue; // Si no se encuentra el producto, se omite este detalle
                    }

                    // Crear el objeto DetallePedido
                    DetallePedido detalle = new DetallePedido(
                            codigoPedido, // Código del pedido
                            codigoProducto, // Código del producto
                            producto, // Producto asociado
                            rs.getInt("cantidad"), // Cantidad
                            rs.getDouble("precio"), // Precio
                            rs.getInt("descuento"), // Descuento
                            rs.getDouble("total") // Total
                    );

                    // Añadir el detalle a la lista
                    detalles.add(detalle);

                    // Depuración: Mostrar detalles del pedido cargado
                    System.out.println("Detalle cargado: " + detalle);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los detalles del pedido: " + e.getMessage());
            e.printStackTrace();  // Mostrar el error completo
        }
        return detalles;
    }


    @Override
    public void eliminarPedido(String codigoPedido) {
        conexionDb = new ConexionDb();
        try {
            // Eliminar los detalles del pedido asociados al código del pedido
            try (PreparedStatement stmtDetalle = conexionDb.obtenerConexion().prepareStatement(
                    "DELETE FROM detalle_pedido WHERE codigo_pedido = ?")) {
                stmtDetalle.setString(1, codigoPedido);
                stmtDetalle.executeUpdate();
            }

            // Eliminar el pedido asociado al código del pedido
            try (PreparedStatement stmtPedido = conexionDb.obtenerConexion().prepareStatement(
                    "DELETE FROM pedido WHERE codigo = ?")) {
                stmtPedido.setString(1, codigoPedido);
                stmtPedido.executeUpdate();
            }

            System.out.println("Pedido eliminado exitosamente con código: " + codigoPedido);
        } catch (SQLException e) {
            System.out.println("Error al eliminar el pedido: " + e.getMessage());
        }
    }
    
    @Override
    public void modificarPedido(String codigoPedido, Pedido pedidoModificado) {
        String sqlUpdatePedido = "UPDATE pedido SET fecha = ?, codigo_vendedor = ?, codigo_cliente = ?, estado = ?, total_pedido = ? WHERE codigo = ?";

        try {
            if (codigoPedido != null && !codigoPedido.isEmpty()) {
                // Actualizar los campos del pedido
                try (PreparedStatement stmtUpdate = conexionDb.obtenerConexion().prepareStatement(sqlUpdatePedido)) {
                    stmtUpdate.setDate(1, java.sql.Date.valueOf(pedidoModificado.getFecha()));  // Fecha del pedido
                    stmtUpdate.setString(2, pedidoModificado.getVendedor().getCodigo());  // Código del vendedor
                    stmtUpdate.setString(3, pedidoModificado.getCliente().getCodigo());   // Código del cliente
                    stmtUpdate.setString(4, pedidoModificado.getEstado().name());  // Estado del pedido
                    stmtUpdate.setDouble(5, pedidoModificado.getTotalPedido());  // Total del pedido
                    stmtUpdate.setString(6, codigoPedido);  // Código del pedido a modificar

                    // Ejecutar la actualización
                    int affectedRows = stmtUpdate.executeUpdate();
                    if (affectedRows > 0) {
                        System.out.println("Pedido con código " + codigoPedido + " actualizado exitosamente.");
                    } else {
                        System.out.println("No se pudo actualizar el pedido con código " + codigoPedido);
                    }
                }
            } else {
                System.out.println("El código del pedido no puede ser nulo o vacío.");
            }
        } catch (SQLException e) {
            System.out.println("Error al modificar el pedido: " + e.getMessage());
        }
    }
    
    public String getProximoCodigoPedido() {
        String sqlNextCode = "SELECT MAX(id) AS total FROM pedido";
        conexionDb = new ConexionDb();

        try {
            ResultSet rs = conexionDb.ejecutarConsultaSql(sqlNextCode);
            if (rs.next()) {
                return "PED-" + (rs.getInt("total") + 1);  // Se suma 1 al valor máximo de ID para obtener el próximo código
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el próximo código de pedido: " + e.getMessage());
        }

        return "PED-1";  // Si no hay registros, se comienza con "PED-1"
    }
}