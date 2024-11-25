package repositorio.dao.cliente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import modelo.cliente.Cliente;
import repositorio.dao.ConexionDb;

/**
 *
 * @author rodri
 */

public class ClienteDaoImpl implements IDaoCliente{

    private ConexionDb conexionDb;

    public ClienteDaoImpl() {
        this.conexionDb = new ConexionDb();
    }
    
    @Override
    public void insertarNuevoCliente(Cliente cliente) {
        
        String codigoCliente = getProximoCodigoCliente();

        String sqlPersona = "INSERT INTO personas (nombre, apellido, dni, email, telefono) VALUES (?, ?, ?, ?, ?)";
        HashMap<Integer, Object> paramPersona = new HashMap<>();
        paramPersona.put(1, cliente.getNombre());
        paramPersona.put(2, cliente.getApellido());
        paramPersona.put(3, cliente.getDni());
        paramPersona.put(4, cliente.getEmail());
        paramPersona.put(5, cliente.getTelefono());

        conexionDb = new ConexionDb();
        try {
            PreparedStatement stmtPersona = conexionDb.obtenerConexion().prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS);
            stmtPersona.setString(1, cliente.getNombre());
            stmtPersona.setString(2, cliente.getApellido());
            stmtPersona.setString(3, cliente.getDni());
            stmtPersona.setString(4, cliente.getEmail());
            stmtPersona.setString(5, cliente.getTelefono());

            int affectedRowsPersona = stmtPersona.executeUpdate();

            if (affectedRowsPersona > 0) {
                ResultSet generatedKeysPersona = stmtPersona.getGeneratedKeys();
                if (generatedKeysPersona.next()) {
                    int personId = generatedKeysPersona.getInt(1); 

                    String sqlClient = "INSERT INTO clientes (codigo, id_persona, cuil) VALUES (?, ?, ?)";
                    PreparedStatement stmtClient = conexionDb.obtenerConexion().prepareStatement(sqlClient);
                    stmtClient.setString(1, codigoCliente); 
                    stmtClient.setInt(2, personId);
                    stmtClient.setString(3, cliente.getCuil()); 

                    int affectedRowsClient = stmtClient.executeUpdate();

                    if (affectedRowsClient > 0) {
                        System.out.println("Nuevo cliente insertado con éxito con código: " + codigoCliente);
                    } else {
                        System.out.println("Error al insertar el cliente.");
                    }
                }
            } else {
                System.out.println("Error al insertar la persona.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar el cliente: " + e.getMessage());
        }
    }

    @Override
    public void eliminarCliente(String codigo, String nombre, String apellido) {
        String sqlClientId = "SELECT id_persona FROM clientes c "
                + "INNER JOIN personas p ON p.id = c.id_persona "
                + "WHERE 1 = 1 ";  

        if (codigo != null && !codigo.isEmpty()) {
            sqlClientId += " AND c.codigo = ?";
        }
        if (nombre != null && !nombre.isEmpty()) {
            sqlClientId += " AND p.nombre = ?";
        }
        if (apellido != null && !apellido.isEmpty()) {
            sqlClientId += " AND p.apellido = ?";
        }

        String sqlDeletePerson = "DELETE FROM personas WHERE id = ?"; 
        String sqlDeleteClient = "DELETE FROM clientes WHERE codigo = ?";  

        HashMap<Integer, Object> param = new HashMap<>();
        int paramIndex = 0;  

        if (codigo != null && !codigo.isEmpty()) {
            param.put(paramIndex++, codigo);
        }
        if (nombre != null && !nombre.isEmpty()) {
            param.put(paramIndex++, nombre);
        }
        if (apellido != null && !apellido.isEmpty()) {
            param.put(paramIndex++, apellido);
        }

        Integer idPersona = 0;  

        conexionDb = new ConexionDb();

        try {
            ResultSet rs = conexionDb.ejecutarConsultaSqlConParametros(sqlClientId, param);
            if (rs.next()) {
                idPersona = rs.getInt("id_persona");  
            }

            if (idPersona != 0) {
                param.clear();
                param.put(0, codigo);  
                int rowsDeletedClient = conexionDb.ejecutarConsultaUpdate(sqlDeleteClient, param);

                if (rowsDeletedClient > 0) {
                    param.clear();
                    param.put(0, idPersona); 
                    int rowsDeletedPerson = conexionDb.ejecutarConsultaUpdate(sqlDeletePerson, param);

                    if (rowsDeletedPerson > 0) {
                        System.out.println("El cliente se eliminó exitosamente.");
                    } else {
                        System.out.println("No se pudo eliminar la persona asociada al cliente.");
                    }
                } else {
                    System.out.println("No se encontró el cliente con los datos proporcionados.");
                }
            } else {
                System.out.println("Cliente no encontrado, no se pudo eliminar.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el cliente: " + e.getMessage());
        }
    }
    
    @Override
    public void modificarCliente(String codigo, Cliente cliente) {
        conexionDb = new ConexionDb();

        String sqlUpdateClient = "UPDATE clientes SET cuil = ? WHERE codigo = ?";
        HashMap<Integer, Object> param = new HashMap<>();
        param.put(0, cliente.getCuil());
        param.put(1, codigo);
        try {
            int rowsUpdated = conexionDb.ejecutarConsultaUpdate(sqlUpdateClient, param);

            if (rowsUpdated > 0) {
                String sqlUpdatePer = "UPDATE personas SET nombre = ?, apellido = ?, dni = ?, email = ?, telefono = ? "
                        + "WHERE id = (SELECT id_persona FROM clientes WHERE codigo = ?)";
                param.clear();
                param.put(0, cliente.getNombre());
                param.put(1, cliente.getApellido());
                param.put(2, cliente.getDni());
                param.put(3, cliente.getEmail());
                param.put(4, cliente.getTelefono());
                param.put(5, codigo);

                int rowsPersonUpdated = conexionDb.ejecutarConsultaUpdate(sqlUpdatePer, param);

                if (rowsPersonUpdated > 0) {
                    System.out.println("El cliente se actualizó con éxito");
                } else {
                    System.out.println("No se encontró la persona asociada al código proporcionado");
                }
            } else {
                System.out.println("No se encontró el cliente con el código proporcionado");
            }
        } catch (SQLException e) {
            System.out.println("Error al modificar el cliente: " + e.getMessage());
        }
    }

    @Override
    public List<Cliente> getClientes(String codigo, String nombre, String apellido) {
        List<Cliente> clientes = new ArrayList<>();
        String sqlClients = "SELECT c.codigo, p.nombre, p.apellido, p.dni, p.telefono, p.email, c.cuil "
                + "FROM clientes c "
                + "INNER JOIN personas p ON p.id = c.id_persona "
                + "WHERE 1 = 1 ";

        if (codigo != null && !codigo.isEmpty()) {
            sqlClients += " AND c.codigo = ?";
        }
        if (nombre != null && !nombre.isEmpty()) {
            sqlClients += " AND p.nombre LIKE ?";
        }
        if (apellido != null && !apellido.isEmpty()) {
            sqlClients += " AND p.apellido LIKE ?";
        }

        conexionDb = new ConexionDb();

        try (PreparedStatement stmt = conexionDb.obtenerConexion().prepareStatement(sqlClients)) {
            int index = 1;

            if (codigo != null && !codigo.isEmpty()) {
                stmt.setString(index++, codigo);
            }

            if (nombre != null && !nombre.isEmpty()) {
                stmt.setString(index++, "%" + nombre + "%");
            }

            if (apellido != null && !apellido.isEmpty()) {
                stmt.setString(index++, "%" + apellido + "%");
            }

            try (ResultSet rs = stmt.executeQuery()) {
                int clienteCount = 0;

                while (rs.next()) {
                    Cliente cliente = new Cliente(
                            rs.getString("codigo"),
                            rs.getString("cuil"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("dni"),
                            rs.getString("telefono"),
                            rs.getString("email")
                    );
                    cliente.setCodigo(rs.getString("codigo"));
                    clientes.add(cliente);
                    clienteCount++;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la lista de clientes: " + e.getMessage());
        }

        return clientes;
    }

    @Override
    public Cliente obtenerCliente(String codigo, String nombre, String apellido) {
        Cliente cliente = null;
        StringBuilder sqlQuery = new StringBuilder("SELECT * FROM clientes c ")
                .append("INNER JOIN personas p ON p.id = c.id_persona WHERE 1=1");
        HashMap<Integer, Object> param = new HashMap<>();
        int index = 0;

        try {
            if (codigo != null && !codigo.isEmpty()) {
                sqlQuery.append(" AND c.codigo = ?");
                param.put(index++, codigo);
            }

            if (nombre != null && !nombre.isEmpty()) {
                sqlQuery.append(" AND p.nombre LIKE ?");
                param.put(index++, "%" + nombre + "%");
            }

            if (apellido != null && !apellido.isEmpty()) {
                sqlQuery.append(" AND p.apellido LIKE ?");
                param.put(index++, "%" + apellido + "%");
            }

            ResultSet rs = conexionDb.ejecutarConsultaSqlConParametros(sqlQuery.toString(), param);

            if (rs != null && rs.next()) {
                cliente = new Cliente(
                        rs.getString("codigo"),
                        rs.getString("cuil"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni"),
                        rs.getString("telefono"),
                        rs.getString("email")
                );
            } else {
                System.out.println("No se encontró un cliente con los parámetros especificados.");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el cliente: " + e.getMessage());
        }

        return cliente;
    }
    
    public String getProximoCodigoCliente(){
        String sqlNextCode = "SELECT MAX(id) AS total FROM clientes";
        conexionDb = new ConexionDb();

        try {
            ResultSet rs = conexionDb.ejecutarConsultaSql(sqlNextCode);

            if (rs.next()) {
                return "CLI-" + (rs.getInt("total") + 1);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el próximo código: " + e.getMessage());
        }

        return "CLI-1"; 
    }
}