package repositorio.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author rodri
 */

public class ConexionDb {

    private String url = "jdbc:mysql://localhost:3306/bicicleteria_db"; 
    private String usuarioDb = "root";
    private String contrasena = ""; 
    
    public ConexionDb() {
        super();
    }
    
    public Connection obtenerConexion() {
        Connection conn = null;
        System.out.println("url: " + url);
        
        try {
            conn = DriverManager.getConnection(url, usuarioDb, contrasena);
            System.out.println("Conexión exitosa al servidor MySQL.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return conn;
    }

    public ResultSet ejecutarConsultaSql(String sql) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = obtenerConexion();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException("Error al ejecutar la consulta SQL");
        }

        return rs;
    }
    
    public int ejecutarConsultaUpdate(String sql, HashMap<Integer, Object> param) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int filasAfectadas = 0;

        try {
            conn = obtenerConexion();
            pstmt = conn.prepareStatement(sql);

            for (Integer index : param.keySet()) {
                Object obj = param.get(index);
                if (obj instanceof Integer) {
                    pstmt.setInt(index + 1, (Integer) obj);
                } else if (obj instanceof String) {
                    pstmt.setString(index + 1, (String) obj);
                } else if (obj instanceof Float) {
                    pstmt.setFloat(index + 1, (Float) obj);
                } else if (obj instanceof Double) {
                    pstmt.setDouble(index + 1, (Double) obj);
                } else if (obj instanceof Long) {
                    pstmt.setLong(index + 1, (Long) obj);
                } else if (obj instanceof Date) {
                    pstmt.setDate(index + 1, new java.sql.Date(((Date) obj).getTime()));
                }
            }

            filasAfectadas = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException("Error al ejecutar la consulta de actualización");
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return filasAfectadas;
    }
    
    public ResultSet ejecutarConsultaSqlConParametros(String sql, HashMap<Integer, Object> param) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = obtenerConexion();  
            pstmt = conn.prepareStatement(sql);

            for (Integer index : param.keySet()) {
                Object obj = param.get(index);
                if (obj instanceof Integer) {
                    pstmt.setInt(index + 1, (Integer) obj);
                } else if (obj instanceof String) {
                    pstmt.setString(index + 1, (String) obj);
                } else if (obj instanceof Float) {
                    pstmt.setFloat(index + 1, (Float) obj);
                } else if (obj instanceof Double) {
                    pstmt.setDouble(index + 1, (Double) obj);
                } else if (obj instanceof Long) {
                    pstmt.setLong(index + 1, (Long) obj);
                } else if (obj instanceof Date) {
                    pstmt.setDate(index + 1, new java.sql.Date(((Date) obj).getTime()));
                }
            }

            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException("Error al ejecutar la consulta SQL");
        }

        return rs;
    }
    
    public void ejecutarSqlConParametros(String sql, HashMap<Integer, Object> param) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = obtenerConexion(); 
            pstmt = conn.prepareStatement(sql);

            for (Integer index : param.keySet()) {
                Object obj = param.get(index);
                if (obj instanceof Integer) {
                    pstmt.setInt(index + 1, (Integer) obj);
                } else if (obj instanceof String) {
                    pstmt.setString(index + 1, (String) obj);
                } else if (obj instanceof Float) {
                    pstmt.setFloat(index + 1, (Float) obj);
                } else if (obj instanceof Double) {
                    pstmt.setDouble(index + 1, (Double) obj);
                } else if (obj instanceof Long) {
                    pstmt.setLong(index + 1, (Long) obj);
                } else if (obj instanceof Date) {
                    pstmt.setDate(index + 1, new java.sql.Date(((Date) obj).getTime()));
                }
            }

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int idGenerado = rs.getInt(1);
                System.out.println("ID generado: " + idGenerado);
            }
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
            throw new SQLException("Error conexión en ejecutarSqlConParametros sin RS");
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}