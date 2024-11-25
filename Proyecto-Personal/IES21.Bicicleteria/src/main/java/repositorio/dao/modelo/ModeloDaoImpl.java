package repositorio.dao.modelo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.producto.marca.Marca;
import repositorio.dao.ConexionDb;
import modelo.producto.marca.Modelo;
import modelo.producto.marca.Rodado;


/**
 *
 * @author rodri
 */

public class ModeloDaoImpl implements IDaoModelo {

    private ConexionDb conexionDb;

    public ModeloDaoImpl() {
        this.conexionDb = new ConexionDb();
    }

    @Override
    public void insertarNuevoModelo(Modelo modelo) {
        String codigoModelo = getProximoCodigoModelo();
        String sqlInsertModelo = "INSERT INTO modelos (codigo, modelo, codigo_marca, rodado, descripcion) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmtModelo = conexionDb.obtenerConexion().prepareStatement(sqlInsertModelo);

            stmtModelo.setString(1, codigoModelo); 

            stmtModelo.setString(2, modelo.getModelo()); 

            stmtModelo.setString(3, modelo.getMarca().getCodigo()); 

            stmtModelo.setString(4, modelo.getRodado().toString());

            String descripcion = modelo.getDescripcion();
            if (descripcion == null || descripcion.isEmpty()) {
                descripcion = ""; 
            }
            stmtModelo.setString(5, descripcion);

            int affectedRows = stmtModelo.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Modelo insertado con éxito con código: " + codigoModelo);
            } else {
                System.out.println("Error al insertar el modelo.");
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar el modelo: " + e.getMessage());
        }
    }

    @Override
    public void eliminarModelo(String codigoModelo, String nombreModelo) {
        StringBuilder sqlDelete = new StringBuilder("DELETE FROM modelos WHERE 1=1");
        HashMap<Integer, Object> param = new HashMap<>();
        int index = 1;

        try {

            if (codigoModelo != null && !codigoModelo.isEmpty()) {
                sqlDelete.append(" AND codigo = ?");
                param.put(index++, codigoModelo);
            }
            if (nombreModelo != null && !nombreModelo.isEmpty()) {
                sqlDelete.append(" AND modelo = ?");
                param.put(index++, nombreModelo);
            }

            try (PreparedStatement stmtDelete = conexionDb.obtenerConexion().prepareStatement(sqlDelete.toString())) {
                for (Map.Entry<Integer, Object> entry : param.entrySet()) {
                    stmtDelete.setObject(entry.getKey(), entry.getValue());
                }

                int affectedRows = stmtDelete.executeUpdate();
                
                if (affectedRows > 0) {
                    System.out.println("Modelo eliminado exitosamente.");
                } else {
                    System.out.println("No se encontró un modelo con los criterios especificados.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el modelo: " + e.getMessage());
        }
    }

    @Override
    public void modificarModelo(String codigoModelo, Modelo modeloModificado) {
        String sqlUpdateModelo = "UPDATE modelos SET modelo = ?, codigo_marca = ?, descripcion = ?, rodado = ? WHERE codigo = ?";

        try {

            try (PreparedStatement stmtUpdate = conexionDb.obtenerConexion().prepareStatement(sqlUpdateModelo)) {
                stmtUpdate.setString(1, modeloModificado.getModelo());

                stmtUpdate.setString(2, modeloModificado.getMarca().getCodigo());  

                stmtUpdate.setString(3, modeloModificado.getDescripcion());
                stmtUpdate.setString(4, modeloModificado.getRodado().toString());
                stmtUpdate.setString(5, codigoModelo);  

                int affectedRows = stmtUpdate.executeUpdate();
                
                if (affectedRows > 0) {
                    System.out.println("Modelo con código " + codigoModelo + " actualizado exitosamente.");
                } else {
                    System.out.println("No se pudo actualizar el modelo con código " + codigoModelo + ". Puede que no exista o no haya cambios.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al modificar el modelo: " + e.getMessage());
        }
    }

    @Override
    public Modelo obtenerModelo(String codigoModelo, String nombreModelo, String codigoMarca, Rodado rodado) {
        Modelo modelo = null;

        StringBuilder sqlQuery = new StringBuilder("SELECT m.codigo, m.modelo, m.descripcion, m.rodado, m.codigo_marca, mar.marca AS nombre_marca ");
        sqlQuery.append("FROM modelos m ");
        sqlQuery.append("JOIN marcas mar ON m.codigo_marca = mar.codigo ");  // Usamos mar.codigo en lugar de mar.id
        sqlQuery.append("WHERE 1=1");

        HashMap<Integer, Object> param = new HashMap<>();
        int index = 0;

        try {
            if (codigoModelo != null && !codigoModelo.isEmpty()) {
                sqlQuery.append(" AND m.codigo = ?");  
                param.put(index++, codigoModelo); 
            }

            if (nombreModelo != null && !nombreModelo.isEmpty()) {
                sqlQuery.append(" AND m.modelo LIKE ?");
                param.put(index++, "%" + nombreModelo + "%");
            }

            if (codigoMarca != null && !codigoMarca.isEmpty()) {
                sqlQuery.append(" AND mar.codigo = ?"); 
                param.put(index++, codigoMarca); 
            }

            if (rodado != null) {
                sqlQuery.append(" AND m.rodado = ?");
                param.put(index++, rodado.name());
            }

            // Ejecutar consulta
            try (ResultSet rs = conexionDb.ejecutarConsultaSqlConParametros(sqlQuery.toString(), param)) {
                if (rs.next()) {

                    String codigo = rs.getString("codigo");  
                    String nombre = rs.getString("modelo");
                    String descripcion = rs.getString("descripcion");
                    Marca marca = new Marca(rs.getString("nombre_marca")); 
                    Rodado rodadoObj = Rodado.valueOf(rs.getString("rodado"));

                    modelo = new Modelo(codigo, nombre, marca, descripcion, rodadoObj);  
                } else {
                    System.out.println("No se encontró un modelo que coincida con los criterios especificados.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el modelo: " + e.getMessage());
        }

        return modelo;
    }

    @Override
    public List<Modelo> getModelos(String codigoModelo, String nombreModelo, String codigoMarca, Rodado rodado) {
        List<Modelo> modelos = new ArrayList<>();
        StringBuilder sqlQuery = new StringBuilder("SELECT m.codigo AS codigo_modelo, m.modelo, mar.codigo AS codigo_marca, ");
        sqlQuery.append("mar.marca AS nombre_marca, m.descripcion, m.rodado ");
        sqlQuery.append("FROM modelos m ");
        sqlQuery.append("JOIN marcas mar ON m.codigo_marca = mar.codigo ");
        sqlQuery.append("WHERE 1=1"); 

        HashMap<Integer, Object> param = new HashMap<>();
        int index = 0;

        try {

            if (codigoModelo != null && !codigoModelo.isEmpty()) {
                sqlQuery.append(" AND m.codigo = ?");
                param.put(index++, codigoModelo); 
            }
            if (nombreModelo != null && !nombreModelo.isEmpty()) {
                sqlQuery.append(" AND m.modelo LIKE ?");
                param.put(index++, "%" + nombreModelo + "%");
            }
            if (codigoMarca != null && !codigoMarca.isEmpty()) {
                sqlQuery.append(" AND mar.codigo = ?");
                param.put(index++, codigoMarca); 
            }
            if (rodado != null) {
                sqlQuery.append(" AND m.rodado = ?");
                param.put(index++, rodado.name());
            }

            try (ResultSet rs = conexionDb.ejecutarConsultaSqlConParametros(sqlQuery.toString(), param)) {

                while (rs.next()) {

                    String codigo = rs.getString("codigo_modelo");  
                    String nombre = rs.getString("modelo");
                    String codigoMarcaRecuperado = rs.getString("codigo_marca");
                    String nombreMarca = rs.getString("nombre_marca");
                    String descripcion = rs.getString("descripcion");
                    Rodado rodadoObjeto = Rodado.valueOf(rs.getString("rodado"));

                    Marca marca = new Marca(codigoMarcaRecuperado, nombreMarca);

                    Modelo modelo = new Modelo(codigo, nombre, marca, descripcion, rodadoObjeto);

                    modelos.add(modelo);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la lista de modelos: " + e.getMessage());
        }

        return modelos;
    }
    
    @Override
    public List<Modelo> getModelosComboBox() {
        List<Modelo> modelos = new ArrayList<>();
        String sqlQuery = "SELECT modelo, codigo FROM modelos";  

        try {
            ResultSet rs = conexionDb.ejecutarConsultaSql(sqlQuery);

            while (rs.next()) {
                String codigo = rs.getString("codigo");  
                String modelo = rs.getString("modelo");

                if (codigo != null && modelo != null) {
                    Modelo mod = new Modelo(codigo, modelo); 
                    modelos.add(mod);  
                } else {
                    System.out.println("Advertencia: Modelo o código nulo.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la lista de modelos: " + e.getMessage());
        }

        return modelos;
    }

    
    public String getProximoCodigoModelo() {
        String sqlNextCode = "SELECT MAX(id) AS total FROM modelos";
        conexionDb = new ConexionDb();

        try {
            ResultSet rs = conexionDb.ejecutarConsultaSql(sqlNextCode);

            if (rs.next()) {
                int nextId = rs.getInt("total") + 1; 
                return "MOD-" + Year.now().getValue() + "-" + nextId; 
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el próximo código para el modelo: " + e.getMessage());
        }

        return "MOD-" + Year.now().getValue() + "-1"; 
    }
}