package repositorio.dao.proveedor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import modelo.proveedor.Proveedor;
import repositorio.dao.ConexionDb;

/**
 *
 * @author rodri
 */

public class ProveedorDaoImpl implements IDaoProveedor{

    private ConexionDb conexionDb;

    public ProveedorDaoImpl() {
        this.conexionDb = new ConexionDb();
    }

    @Override
    public void insertarNuevoProveedor(Proveedor proveedor) {
        String codigoProveedor = getProximoCodigoProveedor();

        String sqlPersona = "INSERT INTO personas (nombre, apellido, dni, email, telefono) VALUES (?, ?, ?, ?, ?)";
        String sqlProveedor = "INSERT INTO proveedores (id_persona, codigo, nombre_fantasia, cuit) VALUES (?, ?, ?, ?)";

        conexionDb = new ConexionDb();
        try {
            PreparedStatement stmtPersona = conexionDb.obtenerConexion().prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS);
            stmtPersona.setString(1, proveedor.getNombre());
            stmtPersona.setString(2, proveedor.getApellido());
            stmtPersona.setString(3, proveedor.getDni());
            stmtPersona.setString(4, proveedor.getEmail());
            stmtPersona.setString(5, proveedor.getTelefono());

            int affectedRowsPersona = stmtPersona.executeUpdate();

            if (affectedRowsPersona > 0) {
                ResultSet generatedKeysPersona = stmtPersona.getGeneratedKeys();
                if (generatedKeysPersona.next()) {
                    int personId = generatedKeysPersona.getInt(1);

                    PreparedStatement stmtProveedor = conexionDb.obtenerConexion().prepareStatement(sqlProveedor);
                    stmtProveedor.setInt(1, personId);
                    stmtProveedor.setString(2, codigoProveedor);
                    stmtProveedor.setString(3, proveedor.getNombreFantasia());
                    stmtProveedor.setString(4, proveedor.getCuit());

                    int affectedRowsProveedor = stmtProveedor.executeUpdate();

                    if (affectedRowsProveedor > 0) {
                        System.out.println("Nuevo proveedor insertado con éxito con código: " + codigoProveedor);
                    } else {
                        System.out.println("Error al insertar el proveedor.");
                    }
                }
            } else {
                System.out.println("Error al insertar la persona.");
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar el proveedor: " + e.getMessage());
        }
    }

    @Override
    public void eliminarProveedor(String codigo, String nombre, String apellido, String nombreFantasia) {
        String sqlProveedorId = "SELECT id_persona FROM proveedores p "
                + "INNER JOIN personas pe ON pe.id = p.id_persona "
                + "WHERE 1 = 1 ";

        if (codigo != null && !codigo.isEmpty()) {
            sqlProveedorId += " AND p.codigo = ?";
        }
        if (nombre != null && !nombre.isEmpty()) {
            sqlProveedorId += " AND pe.nombre = ?";
        }
        if (apellido != null && !apellido.isEmpty()) {
            sqlProveedorId += " AND pe.apellido = ?";
        }
        if (nombreFantasia != null && !nombreFantasia.isEmpty()) {
            sqlProveedorId += " AND p.nombre_fantasia = ?";
        }

        String sqlDeletePerson = "DELETE FROM personas WHERE id = ?";
        String sqlDeleteProveedor = "DELETE FROM proveedores WHERE codigo = ?";

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
        if (nombreFantasia != null && !nombreFantasia.isEmpty()) {
            param.put(paramIndex++, nombreFantasia);
        }

        Integer idPersona = null;  

        conexionDb = new ConexionDb();

        try {
            ResultSet rs = conexionDb.ejecutarConsultaSqlConParametros(sqlProveedorId, param);
            if (rs.next()) {
                idPersona = rs.getInt("id_persona");

                if (idPersona != null) {
                    param.clear();
                    param.put(0, codigo);
                    int rowsDeletedProveedor = conexionDb.ejecutarConsultaUpdate(sqlDeleteProveedor, param);

                    if (rowsDeletedProveedor > 0) {
                        param.clear();
                        param.put(0, idPersona);
                        int rowsDeletedPerson = conexionDb.ejecutarConsultaUpdate(sqlDeletePerson, param);

                        if (rowsDeletedPerson > 0) {
                            System.out.println("El proveedor y la persona asociada se eliminaron exitosamente.");
                        } else {
                            System.out.println("No se pudo eliminar la persona asociada al proveedor.");
                        }
                    } else {
                        System.out.println("No se encontró el proveedor con los datos proporcionados.");
                    }
                } else {
                    System.out.println("Proveedor no encontrado, no se pudo eliminar.");
                }
            } else {
                System.out.println("No se encontró el proveedor con los datos proporcionados.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el proveedor: " + e.getMessage());
        }
    }
    
    @Override
    public void modificarProveedor(String codigo, Proveedor proveedor) {
        conexionDb = new ConexionDb();

        String sqlUpdateProveedor = "UPDATE proveedores SET nombre_fantasia = ?, cuit = ? WHERE codigo = ?";
        HashMap<Integer, Object> paramProveedor = new HashMap<>();
        paramProveedor.put(0, proveedor.getNombreFantasia());
        paramProveedor.put(1, proveedor.getCuit());
        paramProveedor.put(2, codigo);  

        try {
            conexionDb.ejecutarConsultaUpdate(sqlUpdateProveedor, paramProveedor);

            String sqlUpdatePersona = "UPDATE personas SET nombre = ?, apellido = ?, dni = ?, email = ?, telefono = ? "
                    + "WHERE id = (SELECT id_persona FROM proveedores WHERE codigo = ?)";

            HashMap<Integer, Object> paramPersona = new HashMap<>();
            paramPersona.put(0, proveedor.getNombre());
            paramPersona.put(1, proveedor.getApellido());
            paramPersona.put(2, proveedor.getDni());
            paramPersona.put(3, proveedor.getEmail());
            paramPersona.put(4, proveedor.getTelefono());
            paramPersona.put(5, codigo);  
            conexionDb.ejecutarConsultaUpdate(sqlUpdatePersona, paramPersona);

            System.out.println("El proveedor se actualizó con éxito.");
        } catch (SQLException e) {
            System.err.println("Error al modificar el proveedor: " + e.getMessage());
        }
    }

    @Override
    public List<Proveedor> getProveedores(String codigo, String nombre, String apellido, String nombreFantasia) {
        List<Proveedor> proveedores = new ArrayList<>();

        String sqlProveedores = "SELECT pr.codigo, p.nombre, p.apellido, p.dni, p.telefono, p.email, pr.nombre_fantasia, pr.cuit "
                + "FROM proveedores pr "
                + "INNER JOIN personas p ON p.id = pr.id_persona "
                + "WHERE 1 = 1";

        if (codigo != null && !codigo.isEmpty()) {
            sqlProveedores += " AND pr.codigo = ?";
        }
        if (nombre != null && !nombre.isEmpty()) {
            sqlProveedores += " AND p.nombre LIKE ?";
        }
        if (apellido != null && !apellido.isEmpty()) {
            sqlProveedores += " AND p.apellido LIKE ?";
        }
        if (nombreFantasia != null && !nombreFantasia.isEmpty()) {
            sqlProveedores += " AND pr.nombre_fantasia LIKE ?";
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
            if (nombreFantasia != null && !nombreFantasia.isEmpty()) {
                param.put(index++, "%" + nombreFantasia + "%");
            }

            ResultSet rs = conexionDb.ejecutarConsultaSqlConParametros(sqlProveedores, param);

            while (rs.next()) {
                Proveedor proveedor = new Proveedor(
                        rs.getString("codigo"),
                        rs.getString("cuit"),
                        rs.getString("nombre_fantasia"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni"),
                        rs.getString("telefono"),
                        rs.getString("email")
                );
                proveedores.add(proveedor);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener la lista de proveedores: " + e.getMessage());
        }

        return proveedores;
    }
    
    @Override
    public Proveedor obtenerProveedor(String codigo, String nombreFantasia) {
        // Inicializa la consulta SQL
        String sqlProveedor = "SELECT * FROM proveedores pr "
                + "INNER JOIN personas p ON p.id = pr.id_persona "
                + "WHERE 1 = 1"; // Esto es para facilitar la adición condicional de filtros

        // Agregar condiciones a la consulta dependiendo de los parámetros
        if (codigo != null && !codigo.isEmpty()) {
            sqlProveedor += " AND pr.codigo = ?";
        }
        if (nombreFantasia != null && !nombreFantasia.isEmpty()) {
            sqlProveedor += " AND pr.nombre_fantasia = ?";
        }

        Proveedor proveedor = null;
        conexionDb = new ConexionDb();

        try {
            // Crear el HashMap para pasar los parámetros a la consulta
            HashMap<Integer, Object> param = new HashMap<>();

            int index = 0;
            if (codigo != null && !codigo.isEmpty()) {
                param.put(index++, codigo);  // Primero el código, si está presente
            }
            if (nombreFantasia != null && !nombreFantasia.isEmpty()) {
                param.put(index++, nombreFantasia);  // Luego el nombre de fantasía, si está presente
            }

            // Ejecutar la consulta SQL con los parámetros
            ResultSet rs = conexionDb.ejecutarConsultaSqlConParametros(sqlProveedor, param);

            // Si hay resultados, creamos el objeto proveedor
            if (rs.next()) {
                proveedor = new Proveedor(
                        rs.getString("codigo"),
                        rs.getString("cuit"),
                        rs.getString("nombre_fantasia"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni"),
                        rs.getString("telefono"),
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el proveedor: " + e.getMessage());
        }

        return proveedor;
    }
    
    @Override
    public List<Proveedor> getProveedoresComboBox() {
        List<Proveedor> proveedores = new ArrayList<>();

        String sqlQuery = "SELECT pr.codigo, pr.nombre_fantasia FROM proveedores pr";

        try {
            ResultSet rs = conexionDb.ejecutarConsultaSql(sqlQuery);

            while (rs.next()) {
                String codigo = rs.getString("codigo");
                String nombreFantasia = rs.getString("nombre_fantasia");

                Proveedor proveedor = new Proveedor(codigo, nombreFantasia);
                proveedores.add(proveedor);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la lista de proveedores: " + e.getMessage());
        }

        return proveedores;
    }
    
    public String getProximoCodigoProveedor() {
        String sqlNextCode = "SELECT MAX(id) AS total FROM proveedores";
        conexionDb = new ConexionDb();

        try {
            ResultSet rs = conexionDb.ejecutarConsultaSql(sqlNextCode);

            if (rs.next()) {
                int nextId = rs.getInt("total") + 1;
                return "PROV-" + Year.now().getValue() + "-" + nextId;
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el próximo código: " + e.getMessage());
        }

        return "PROV-" + Year.now().getValue() + "-1";
    }
}
