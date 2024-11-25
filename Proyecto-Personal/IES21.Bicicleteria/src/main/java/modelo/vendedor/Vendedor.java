package modelo.vendedor;

import modelo.persona.Persona;

/**
 *
 * @author rodri
 */

public class Vendedor extends Persona {

    private int id;
    private String codigo;
    private String cuit;
    private String sucursal;
    
    public Vendedor() {
        super();
    }

    public Vendedor(String nombre, String apellido) {
        super(nombre, apellido); // Llamada al constructor de la clase base Persona
    }
    
    // Se usa en getVendedores y obtenerVendedorPorId
    public Vendedor(String cuit, String sucursal, String nombre, String apellido, String dni, String telefono, String email) {
        super(nombre, apellido, dni, telefono, email); 
        this.cuit = cuit;
        this.sucursal = sucursal;
    }
    
    // Se usa en obtenerVendedor
    public Vendedor(String cuit, String codigo, String sucursal, String nombre, String apellido, String dni, String telefono, String email) {
        super(nombre, apellido, dni, telefono, email);
        this.cuit = cuit;
        this.sucursal = sucursal;
        this.codigo = codigo;
    }
    
    // Este constructor se usa en obtenerPedidos
    public Vendedor(String codigo, String nombre, String apellido) {
        super(nombre, apellido);
        this.codigo = codigo;
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

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }
    
    @Override
    public String toString() {
        return this.codigo;
    }
}