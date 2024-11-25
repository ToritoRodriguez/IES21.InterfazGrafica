package repositorio.dao.categoria;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import repositorio.dao.ConexionDb;
import modelo.producto.Categoria;
import modelo.producto.TipoCategoria;

/**
 *
 * @author rodri
 */

public class CategoriaDaoImpl implements IDaoCategoria {

    private ConexionDb conexionDb;

    public CategoriaDaoImpl() {
        this.conexionDb = new ConexionDb();
    }
    
    @Override
    // Método para insertar
    public void insertarNuevaCategoria(Categoria categoria) {
        String codigoCategoria = getProximoCodigoCategoria();

        String sqlCategoria = "INSERT INTO categorias (codigo, categoria, tipo) VALUES (?, ?, ?)";
        HashMap<Integer, Object> paramCategoria = new HashMap<>();
        paramCategoria.put(1, codigoCategoria);  
        paramCategoria.put(2, categoria.getCategoria());  
        paramCategoria.put(3, categoria.getTipo().toString());  

        conexionDb = new ConexionDb();
        try {
            PreparedStatement stmtCategoria = conexionDb.obtenerConexion().prepareStatement(sqlCategoria);
            stmtCategoria.setString(1, codigoCategoria);
            stmtCategoria.setString(2, categoria.getCategoria());
            stmtCategoria.setString(3, categoria.getTipo().toString());

            int affectedRowsCategoria = stmtCategoria.executeUpdate();

            if (affectedRowsCategoria > 0) {
                System.out.println("Categoría " + categoria.getCategoria() + " insertada con éxito con código: " + codigoCategoria);
            } else {
                System.out.println("Error al insertar la categoría.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar la categoría: " + e.getMessage());
        }
    }

    @Override
    public void eliminarCategoria(String codigoCategoria, String nombreCategoria, TipoCategoria tipoCategoria) {
        StringBuilder sqlQuery = new StringBuilder("DELETE FROM categorias WHERE 1=1");

        HashMap<Integer, Object> param = new HashMap<>();
        int index = 0;

        try {
            if (codigoCategoria != null && !codigoCategoria.isEmpty()) {
                sqlQuery.append(" AND codigo = ?");
                param.put(index++, codigoCategoria);
            }

            if (nombreCategoria != null && !nombreCategoria.isEmpty()) {
                sqlQuery.append(" AND categoria = ?");
                param.put(index++, nombreCategoria);
            }

            if (tipoCategoria != null) {
                sqlQuery.append(" AND tipo = ?");
                param.put(index++, tipoCategoria.name());
            }

            PreparedStatement stmtDelete = conexionDb.obtenerConexion().prepareStatement(sqlQuery.toString());

            for (int i = 0; i < param.size(); i++) {
                stmtDelete.setObject(i + 1, param.get(i));
            }

            int affectedRows = stmtDelete.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Categoría eliminada exitosamente.");
            } else {
                System.out.println("No se pudo eliminar la categoría.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar la categoría: " + e.getMessage());
        }
    }

    @Override
    public void modificarCategoria(String codigoCategoria, Categoria categoriaModificada) {
        String sqlUpdateCategoria = "UPDATE categorias SET categoria = ?, tipo = ? WHERE codigo = ?";

        try {
            try (PreparedStatement stmtUpdate = conexionDb.obtenerConexion().prepareStatement(sqlUpdateCategoria)) {

                String nombreCategoria = categoriaModificada.getCategoria();
                String tipoCategoria = categoriaModificada.getTipo() != null ? categoriaModificada.getTipo().toString() : "";

                stmtUpdate.setString(1, nombreCategoria);
                stmtUpdate.setString(2, tipoCategoria);
                stmtUpdate.setString(3, codigoCategoria);  

                int affectedRows = stmtUpdate.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Categoría con código " + codigoCategoria + " actualizada exitosamente.");
                } else {
                    System.out.println("No se pudo actualizar la categoría con código " + codigoCategoria);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al modificar la categoría: " + e.getMessage());
        }
    }

    @Override
    public List<Categoria> getCategorias(String codigoCategoria, String nombreCategoria, TipoCategoria tipoCategoria) {
        List<Categoria> categorias = new ArrayList<>();
        StringBuilder sqlQuery = new StringBuilder("SELECT * FROM categorias WHERE 1=1");

        HashMap<Integer, Object> param = new HashMap<>();
        int index = 0;

        try {
            if (codigoCategoria != null && !codigoCategoria.isEmpty()) {
                sqlQuery.append(" AND codigo = ?");
                param.put(index++, codigoCategoria);
            }

            if (nombreCategoria != null && !nombreCategoria.isEmpty()) {
                sqlQuery.append(" AND categoria LIKE ?");
                param.put(index++, "%" + nombreCategoria + "%");
            }

            if (tipoCategoria != null) {
                sqlQuery.append(" AND tipo = ?");
                param.put(index++, tipoCategoria.name());
            }

            ResultSet rs = conexionDb.ejecutarConsultaSqlConParametros(sqlQuery.toString(), param);

            while (rs.next()) {
                String codigo = rs.getString("codigo");  
                String nombre = rs.getString("categoria");
                String tipo = rs.getString("tipo");
                
                TipoCategoria tipoCat = TipoCategoria.valueOf(tipo);

                Categoria categoria = new Categoria(codigo, nombre, tipoCat);
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener las categorías: " + e.getMessage());
        }

        return categorias;
    }
    
    @Override
    public Categoria obtenerCategoria(String codigoCategoria, String nombreCategoria, TipoCategoria tipoCategoria) {
        StringBuilder sqlQuery = new StringBuilder("SELECT * FROM categorias WHERE 1=1");
        HashMap<Integer, Object> param = new HashMap<>();
        int index = 0;
        Categoria categoria = null;

        try {
            if (codigoCategoria != null && !codigoCategoria.isEmpty()) {
                sqlQuery.append(" AND codigo = ?");
                param.put(index++, codigoCategoria);
            }

            if (nombreCategoria != null && !nombreCategoria.isEmpty()) {
                sqlQuery.append(" AND categoria = ?");
                param.put(index++, nombreCategoria);
            }

            if (tipoCategoria != null) {
                sqlQuery.append(" AND tipo = ?");
                param.put(index++, tipoCategoria.name());  
            }

            for (Integer key : param.keySet()) {
                System.out.println("Parametro " + key + ": " + param.get(key));
            }

            ResultSet rs = conexionDb.ejecutarConsultaSqlConParametros(sqlQuery.toString(), param);

            if (rs.next()) {
                String codigo = rs.getString("codigo"); 
                String categoriaNombre = rs.getString("categoria");
                String tipoString = rs.getString("tipo");

                TipoCategoria tipo = TipoCategoria.valueOf(tipoString);

                categoria = new Categoria(codigo, categoriaNombre, tipo); 
            } else {
                System.out.println("No se encontró una categoría con los parámetros proporcionados.");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la categoría: " + e.getMessage());
        }

        return categoria;
    }
    
    @Override
    public List<Categoria> getCategoriasComboBox() {
        List<Categoria> categorias = new ArrayList<>();
        String sqlQuery = "SELECT codigo, categoria, tipo FROM categorias"; 

        try {
            ResultSet rs = conexionDb.ejecutarConsultaSql(sqlQuery);

            if (rs == null) {
                System.out.println("El ResultSet es null. Verifica la conexión a la base de datos.");
                return categorias;
            }

            while (rs.next()) {
                String codigo = rs.getString("codigo");  
                String categoria = rs.getString("categoria");
                String tipo = rs.getString("tipo");

                if (categoria != null && tipo != null && codigo != null) {
                    try {

                        TipoCategoria tipoCategoria = TipoCategoria.valueOf(tipo);

                        Categoria cat = new Categoria(codigo, categoria, tipoCategoria); 
                        categorias.add(cat);

                    } catch (IllegalArgumentException e) {

                        System.out.println("Tipo de categoría no válido: " + tipo);
                    }
                } else {
                    System.out.println("Categoria, tipo o código son null, omitiendo este registro.");
                }
            }
        } catch (SQLException e) {

            System.out.println("Error al obtener la lista de categorías: " + e.getMessage());
        }

        return categorias;
    }

    public String getProximoCodigoCategoria() {
        String sqlNextCode = "SELECT MAX(id) AS total FROM categorias";
        conexionDb = new ConexionDb();

        try {
            ResultSet rs = conexionDb.ejecutarConsultaSql(sqlNextCode);
            if (rs.next()) {
                return "CAT-" + (rs.getInt("total") + 1);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el próximo código de categoría: " + e.getMessage());
        }

        return "CAT-1"; 
    }
}