package repositorio.dao.vendedor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import repositorio.dao.ConexionDb;
import modelo.vendedor.Vendedor;

/**
 *
 * @author rodri
 */

public class VendedorDaoImpl implements IDaoVendedor {

    private ConexionDb conexionDb;

    public VendedorDaoImpl() {
        this.conexionDb = new ConexionDb();
    }
    
    @Override
    public void insertarNuevoVendedor(Vendedor vendedor) {
        String codigoVendedor = getProximoCodigoVendedor();

        String sqlPersona = "INSERT INTO personas (nombre, apellido, dni, email, telefono) VALUES (?, ?, ?, ?, ?)";
        HashMap<Integer, Object> paramPersona = new HashMap<>();
        paramPersona.put(1, vendedor.getNombre());
        paramPersona.put(2, vendedor.getApellido());
        paramPersona.put(3, vendedor.getDni());
        paramPersona.put(4, vendedor.getEmail());
        paramPersona.put(5, vendedor.getTelefono());
        
        conexionDb = new ConexionDb();
        try {
            PreparedStatement stmtPersona = conexionDb.obtenerConexion().prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS);
            stmtPersona.setString(1, vendedor.getNombre());
            stmtPersona.setString(2, vendedor.getApellido());
            stmtPersona.setString(3, vendedor.getDni());
            stmtPersona.setString(4, vendedor.getEmail());
            stmtPersona.setString(5, vendedor.getTelefono());

            int affectedRowsPersona = stmtPersona.executeUpdate();

            if (affectedRowsPersona > 0) {
                ResultSet generatedKeysPersona = stmtPersona.getGeneratedKeys();
                if (generatedKeysPersona.next()) {
                    int personId = generatedKeysPersona.getInt(1);

                    String sqlVendedor = "INSERT INTO vendedores (id_persona, codigo, sucursal, cuit) VALUES (?, ?, ?, ?)";
                    
                    PreparedStatement stmtVendedor = conexionDb.obtenerConexion().prepareStatement(sqlVendedor);
                    stmtVendedor.setInt(1, personId);
                    stmtVendedor.setString(2, codigoVendedor);
                    stmtVendedor.setString(3, vendedor.getSucursal());
                    stmtVendedor.setString(4, vendedor.getCuit());

                    int affectedRowsVendedor = stmtVendedor.executeUpdate();

                    if (affectedRowsVendedor > 0) {
                        System.out.println("Nuevo vendedor insertado con éxito con código: " + codigoVendedor);
                    } else {
                        System.out.println("Error al insertar el vendedor.");
                    }
                }
            } else {
                System.out.println("Error al insertar la persona.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar el vendedor: " + e.getMessage());
        }
    }

    @Override
    public void eliminarVendedor(String codigo, String nombre, String apellido, String sucursal) {
        String sqlVendedorId = "SELECT id_persona FROM vendedores v "
                + "INNER JOIN personas p ON p.id = v.id_persona "
                + "WHERE 1 = 1 ";

        if (codigo != null && !codigo.isEmpty()) {
            sqlVendedorId += " AND v.codigo = ?";
        }
        if (nombre != null && !nombre.isEmpty()) {
            sqlVendedorId += " AND p.nombre = ?";
        }
        if (apellido != null && !apellido.isEmpty()) {
            sqlVendedorId += " AND p.apellido = ?";
        }
        if (sucursal != null && !sucursal.isEmpty()) {
            sqlVendedorId += " AND s.nombre = ?"; 
        }

        String sqlDeletePerson = "DELETE FROM personas WHERE id = ?";
        String sqlDeleteVendedor = "DELETE FROM vendedores WHERE codigo = ?";

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
        if (sucursal != null && !sucursal.isEmpty()) {
            param.put(paramIndex++, sucursal);
        }

        Integer idPersona = null; 

        conexionDb = new ConexionDb();

        try {
            ResultSet rs = conexionDb.ejecutarConsultaSqlConParametros(sqlVendedorId, param);
            if (rs.next()) {
                idPersona = rs.getInt("id_persona");

                if (idPersona != null) {
                    param.clear();
                    param.put(0, codigo);
                    int rowsDeletedVendedor = conexionDb.ejecutarConsultaUpdate(sqlDeleteVendedor, param);

                    if (rowsDeletedVendedor > 0) {
                        param.clear();
                        param.put(0, idPersona);
                        int rowsDeletedPerson = conexionDb.ejecutarConsultaUpdate(sqlDeletePerson, param);

                        if (rowsDeletedPerson > 0) {
                            System.out.println("El vendedor se eliminó exitosamente.");
                        } else {
                            System.out.println("No se pudo eliminar la persona asociada al vendedor.");
                        }
                    } else {
                        System.out.println("No se encontró el vendedor con los datos proporcionados.");
                    }
                } else {
                    System.out.println("Vendedor no encontrado, no se pudo eliminar.");
                }
            } else {
                System.out.println("No se encontró el vendedor con los datos proporcionados.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el vendedor: " + e.getMessage());
        }
    }

    @Override
    public void modificarVendedor(String codigo, Vendedor vendedor) {
        String sqlUpdateVendedor = "UPDATE vendedores SET cuit = ?, sucursal = ? WHERE codigo = ?";
        conexionDb = new ConexionDb();

        try {
            HashMap<Integer, Object> param = new HashMap<>();
            param.put(0, vendedor.getCuit());    
            param.put(1, vendedor.getSucursal()); 
            param.put(2, codigo);                

            System.out.println("Ejecutando actualización del vendedor con código: " + codigo);
            int rowsUpdated = conexionDb.ejecutarConsultaUpdate(sqlUpdateVendedor, param);

            if (rowsUpdated > 0) {
                System.out.println("Vendedor actualizado con éxito.");

                String sqlUpdatePersona = "UPDATE personas SET nombre = ?, apellido = ?, dni = ?, email = ?, telefono = ? WHERE id = (SELECT id_persona FROM vendedores WHERE codigo = ?)";
                param.clear(); 
                param.put(0, vendedor.getNombre()); 
                param.put(1, vendedor.getApellido()); 
                param.put(2, vendedor.getDni());     
                param.put(3, vendedor.getEmail());   
                param.put(4, vendedor.getTelefono()); 
                param.put(5, codigo);                 

                System.out.println("Ejecutando actualización de la persona asociada al vendedor con código: " + codigo);
                int rowsPersonaUpdated = conexionDb.ejecutarConsultaUpdate(sqlUpdatePersona, param);

                if (rowsPersonaUpdated > 0) {
                    System.out.println("La persona asociada al vendedor se actualizó con éxito.");
                } else {
                    System.out.println("No se encontró la persona asociada.");
                }
            } else {
                System.out.println("No se encontró el vendedor con código: " + codigo);
            }
        } catch (SQLException e) {
            System.out.println("Error al modificar el vendedor: " + e.getMessage());
        }
    }

    @Override
    public List<Vendedor> getVendedores(String codigo, String nombre, String apellido, String sucursal) {
        List<Vendedor> vendedores = new ArrayList<>();

        String sqlVendedores = "SELECT v.codigo, p.nombre, p.apellido, p.dni, p.telefono, p.email, v.cuit, v.sucursal "
                + "FROM vendedores v "
                + "INNER JOIN personas p ON p.id = v.id_persona "
                + "WHERE 1 = 1"; 

        if (codigo != null && !codigo.isEmpty()) {
            sqlVendedores += " AND v.codigo = ?";
        }
        if (nombre != null && !nombre.isEmpty()) {
            sqlVendedores += " AND p.nombre LIKE ?";
        }
        if (apellido != null && !apellido.isEmpty()) {
            sqlVendedores += " AND p.apellido LIKE ?";
        }
        if (sucursal != null && !sucursal.isEmpty()) {
            sqlVendedores += " AND v.sucursal LIKE ?";
        }

        conexionDb = new ConexionDb();

        try {
            HashMap<Integer, Object> param = new HashMap<>();
            int index = 0;

            if (codigo != null && !codigo.isEmpty()) {
                param.put(index++, codigo);
            }
            if (nombre != null && !nombre.isEmpty()) {
                param.put(index++, "%" + nombre + "%");
            }
            if (apellido != null && !apellido.isEmpty()) {
                param.put(index++, "%" + apellido + "%");
            }
            if (sucursal != null && !sucursal.isEmpty()) {
                param.put(index++, "%" + sucursal + "%");
            }

            ResultSet rs = conexionDb.ejecutarConsultaSqlConParametros(sqlVendedores, param);

            while (rs.next()) {
                Vendedor vendedor = new Vendedor(
                        rs.getString("cuit"),
                        rs.getString("sucursal"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni"),
                        rs.getString("telefono"),
                        rs.getString("email")
                );
                vendedor.setCodigo(rs.getString("codigo"));
                vendedores.add(vendedor);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la lista de vendedores: " + e.getMessage());
        }

        return vendedores;
    }

    @Override
    public Vendedor obtenerVendedor(String codigo, String nombre, String apellido) {
        Vendedor vendedor = null;
        StringBuilder sqlQuery = new StringBuilder("SELECT * FROM vendedores v ")
                .append("INNER JOIN personas p ON p.id = v.id_persona WHERE 1=1"); // Usar append en lugar de "+"
        HashMap<Integer, Object> param = new HashMap<>();
        int index = 0;

        try {
            // Construcción dinámica de la consulta SQL
            if (codigo != null && !codigo.isEmpty()) {
                sqlQuery.append(" AND v.codigo = ?");
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

            // Ejecutar la consulta con los parámetros dinámicos
            ResultSet rs = conexionDb.ejecutarConsultaSqlConParametros(sqlQuery.toString(), param);

            if (rs != null && rs.next()) {
                vendedor = new Vendedor(
                        rs.getString("cuit"),
                        rs.getString("codigo"),
                        rs.getString("sucursal"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni"),
                        rs.getString("telefono"),
                        rs.getString("email")
                );
            } else {
                System.out.println("No se encontró un vendedor con los parámetros especificados.");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el vendedor: " + e.getMessage());
        }

        return vendedor;
    }
    
    public String getProximoCodigoVendedor() {
        String sqlNextCode = "SELECT MAX(id) AS total FROM vendedores";
        conexionDb = new ConexionDb();

        try {
            ResultSet rs = conexionDb.ejecutarConsultaSql(sqlNextCode);
            if (rs.next()) {
                return "VEN-" + (rs.getInt("total") + 1);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el próximo código: " + e.getMessage());
        }

        return "VEN-1"; 
    }
}