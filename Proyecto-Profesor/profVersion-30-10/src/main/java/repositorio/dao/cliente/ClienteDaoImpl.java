/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio.dao.cliente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.text.MaskFormatter;
import modelo.cliente.Cliente;
import repositorio.dao.ConexionDb;

public class ClienteDaoImpl implements IDaoCliente {
    
    private ConexionDb conexionDb;
    
    private static final String SQL_PERSONA="insert into personas(nombre,apellido,dni,email,telefono) "
                + " values(?,?,?,?,?)";
    private static final String SQL_GET_PERSONA=" select * from personas where dni=?"; 
    
    @Override
    public void insertarCliente(Cliente cliente) {
        
        String sqlCliente="insert into clientes(id_persona,codigo,cuil) "
                + " values(?,?,?)";
        HashMap<Integer,Object> param= new HashMap<Integer,Object>();
        param.put(0, cliente.getNombre());
        param.put(1, cliente.getApellido());
        param.put(2, cliente.getDni());
        param.put(3, cliente.getEmail());
        param.put(4, cliente.getTelefono());
        conexionDb= new ConexionDb();
        try{
            conexionDb.ejecutarSqlConParametros(SQL_PERSONA, param);
            int idPersona=0;
            param.clear();
            param.put(0, cliente.getDni());
            ResultSet rs=conexionDb.ejecutarConsultaSqlConParametros(SQL_GET_PERSONA, param);
            if(rs.next())
                idPersona=rs.getInt("id");
            if(idPersona>0){
                param.clear();
                param.put(0, idPersona);
                param.put(1, cliente.getCodigo());
                param.put(2, cliente.getCuil());
                conexionDb.ejecutarSqlConParametros(sqlCliente, param);
                System.out.println("El clinete se grabo exitosamente");
            }
        }catch(SQLException e){
            
        }
    }

    @Override
    public void modificarCliente(int id, Cliente cliente) {
     String sqlUpdateCli="UPDATE cliente set cuil=? WHWRE id=?";
     String sqlUpdatePer="UPDATE persona set nombre=?,apellido=?,dni=?,email=?,telefono=?"
             + " WHERE id=(SELECT id FROM clientes WHERE id=?)";
     HashMap<Integer,Object> param= new HashMap<Integer,Object>();
      param.put(0, cliente.getCuil());
      param.put(1, id);
      conexionDb= new ConexionDb();
      try{
          conexionDb.ejecutarSqlConParametros(sqlUpdateCli, param);
          param.clear();
          param.put(0, cliente.getNombre());
          param.put(1, cliente.getApellido());
          param.put(2, cliente.getDni());
          param.put(3, cliente.getEmail());
          param.put(4, cliente.getTelefono());
          param.put(5, id);
          conexionDb.ejecutarSqlConParametros(sqlUpdatePer, param);
          System.out.println("El cliente e actiañizao de forma correcta");
      }catch(SQLException e){
          System.out.println("Error: "+e.getMessage());
      }
    }
    
    @Override
    public void eliminarCliente(int id) {
       String sqlidCli="SELECT p.id FROM clientes c "
               +" INNER JOIN personas p id.=c.id_persona where c.id="+id;
       
       String sqlEliPers="DELETE FROM PERSONA WHERE id=?";
       String sqlEliCli="DELETE FROM CLIENTES WHERE id=?";
       
       HashMap<Integer,Object> param= new HashMap<Integer,Object>();
       param.put(0,id);
       
       Integer idPersona=0;
       conexionDb= new ConexionDb();
       try{
           ResultSet rs=conexionDb.ejecutarConsultaSql(sqlidCli);
           if(rs.next())
               idPersona=rs.getInt("p.id");
           param.put(0,id);
           conexionDb.ejecutarSqlConParametros(sqlEliCli, param);
           param.clear();
           param.put(0,idPersona);
           conexionDb.ejecutarSqlConParametros(sqlEliPers, param);        
            System.out.println("El clinete se elimino exitosamente");
       }catch(SQLException e){
           System.out.println("Error: "+e.getMessage());
       }
    }

    @Override
    public Cliente obtenerCliente(String codigo) {
        String sqlCliente="select * from clientes c  "
                + " inner join personas p on p.id=c.id_persona "
                + " where c.codigo='"+codigo+"'";
        Cliente cliente=null; 
        conexionDb=new ConexionDb();
        try{
            ResultSet rs=conexionDb.ejecutarConsultaSql(sqlCliente);
            if(rs.next())
                cliente=new Cliente( rs.getString("cuil"), rs.getString("nombre")
                        , rs.getString("apellido"),rs.getInt("dni"),rs.getString("telefono")
                        ,rs.getString("email"));
        }catch(SQLException e){
            
        }
       return cliente;  
    }

    @Override
    public List<Cliente> getClientes(String codigo, String nombre, String apellido, int dni) {
       List<Cliente> clientes= new ArrayList<Cliente>();
        String sqlClientes="select * from clientes c  "
                + " inner join personas p on p.id=c.id_persona "
                + " where 1=1 ";
        
        if(codigo!=null && !codigo.equals(""))
            sqlClientes=sqlClientes + " and c.codigo='"+codigo+"'";
        if(nombre!=null && !nombre.equals(""))
            sqlClientes=sqlClientes + " and p.nombre='"+nombre+"'";
        if(dni>0)
            sqlClientes=sqlClientes + " and p.dni="+dni;
        
        conexionDb=new ConexionDb();
        try{
            ResultSet rs=conexionDb.ejecutarConsultaSql(sqlClientes);
            while(rs.next()){
               Cliente cliente=new Cliente( rs.getString("cuil"), rs.getString("nombre")
                        , rs.getString("apellido"),rs.getInt("dni"),rs.getString("telefono")
                        ,rs.getString("email"));
                clientes.add(cliente);
            }
                
        }catch(SQLException e){
            
        }
       return clientes;
     }

    @Override
    public int getProximoCodigo() {
     String sqlNextCodigo="select max(id) as total from clientes ";
     conexionDb=new ConexionDb();
     try{
        ResultSet rs=conexionDb.ejecutarConsultaSql(sqlNextCodigo);
        if(rs.next())
         return rs.getInt("total")+1;
     }catch(SQLException e){
         
     }
     return 0;
    }
}
