/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 * @author Alumno
 */
public class ConexionDb {
    
    private String url="jdbc:mysql://localhost:3306/db_bicicleteria";
    private String userDb="root";
    private String password="";
    
    public ConexionDb(){
        super();
    }
    
    public Connection conectionDb(){
        Connection conn = null;
        System.out.println("url: "+url);
        try {
            conn = DriverManager.getConnection(url, userDb, password);
            System.out.println("Connected to the mySQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public ResultSet ejecutarConsultaSql(String sql) throws SQLException {
    	Connection conn=null;
    	Statement stmt;
    	ResultSet rs=null;
        try {
                conn=conectionDb();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
                //conn.close();
                System.out.println(e.getMessage());
                throw new SQLException("Error conexion");
        }finally {
                //conn.close();
        }
        return rs;
    }
    
    public ResultSet ejecutarConsultaSqlConParametros(String sql,HashMap<Integer,Object> param) throws SQLException {
    	Connection conn=null;
    	PreparedStatement  pstmt;
    	ResultSet rs=null;
        try {
            conn=conectionDb();
            pstmt = conn.prepareStatement(sql);
            for (Integer index : param.keySet()) {
                    Object obj=param.get(index);
                    if(obj instanceof Integer)
                            pstmt.setInt(index+1, (Integer)obj);
                    if(obj instanceof String)
                            pstmt.setString(index+1, (String)obj);
                    if(obj instanceof Float)
                            pstmt.setFloat(index+1, (Float)obj);
                    if(obj instanceof Double)
                            pstmt.setDouble(index+1, (Double)obj);
                    if(obj instanceof Boolean)
                            pstmt.setBoolean(index+1, (Boolean)obj);
                    if(obj instanceof Long)
                            pstmt.setLong(index+1, (Long)obj);
                    if(obj instanceof Date)
                         pstmt.setDate(index+1, (java.sql.Date)obj);
            }
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
                //conn.close();
                System.out.println(e.getMessage());
                throw new SQLException("Error conexion");
        }finally {
               //conn.close();
        }
        return rs;
    }
    
    public void ejecutarSqlConParametros(String sql,HashMap<Integer,Object> param) throws SQLException {
    	Connection conn=null;
    	PreparedStatement  pstmt;
        try {
            conn=conectionDb();
            pstmt = conn.prepareStatement(sql);
            
            for (Integer index : param.keySet()) {
                    Object obj=param.get(index);
                    if(obj instanceof Integer)
                            pstmt.setInt(index+1, (Integer)obj);
                    if(obj instanceof String)
                            pstmt.setString(index+1, (String)obj);
                    if(obj instanceof Float)
                            pstmt.setFloat(index+1, (Float)obj);
                    if(obj instanceof Double)
                            pstmt.setDouble(index+1, (Double)obj);
                    if(obj instanceof Boolean)
                            pstmt.setBoolean(index+1, (Boolean)obj);
                    if(obj instanceof Long)
                            pstmt.setLong(index+1, (Long)obj);
                    if(obj instanceof Date)
                         pstmt.setDate(index+1, (java.sql.Date)obj);
            }
           int i=0;
           pstmt.executeUpdate();
           ResultSet rs=pstmt.getGeneratedKeys();
           if(rs.next())
               i=rs.getInt(1);
        } catch (SQLException e) {
                //conn.close();
                System.out.println(e.getSQLState());
                System.out.println(e.getMessage());
                throw new SQLException("Error conexion");
        }finally {
                //conn.close();
        }
    }
}
