package modelo.proveedor;

import modelo.persona.Persona;

/**
 *
 * @author rodri
 */

public class Proveedor extends Persona{

    private int id;
    private String codigo;
    private String nombreFantasia;
    private String cuit;

    public Proveedor() {
        super();
    }
    
    // Este constuctor se usa en obtenerProducto y getProductos
    public Proveedor(String codigo, String nombreFantasia, String cuit) {
        this.codigo = codigo;
        this.nombreFantasia = nombreFantasia;
        this.cuit = cuit;
    }

    // Este constructor se usa en obtenerProveedor
    public Proveedor(String cuit, String nombreFantasia, String nombre, String apellido, String dni, String telefono, String email) {
        super(nombre, apellido, dni, telefono, email);
        this.nombreFantasia = nombreFantasia;
        this.cuit = cuit;
    }
    
    // Este constructor se usa en getProveedores
    public Proveedor(String codigo, String cuit, String nombreFantasia, String nombre, String apellido, String dni, String telefono, String email) {
        super(nombre, apellido, dni, telefono, email);
        this.codigo = codigo;  
        this.nombreFantasia = nombreFantasia;
        this.cuit = cuit;
    }

    // Este constructor se usa en buscarProveedorPorId
    public Proveedor(int id, String codigo, String nombreFantasia, String cuit) {
        this.id = id;
        this.codigo = codigo;
        this.nombreFantasia = nombreFantasia;
        this.cuit = cuit;
    }
    
    // Este constructor se usa en getProveedoresComboBox
    public Proveedor(String codigo, String nombreFantasia) {
        this.codigo = codigo;
        this.nombreFantasia = nombreFantasia;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getNombreFantasia() {
        return nombreFantasia;
    }

    public void setNombreFantasia(String nombreFantasia) {
        this.nombreFantasia = nombreFantasia;
    }
    
    @Override
    public String toString() {
        return nombreFantasia; 
    }
}