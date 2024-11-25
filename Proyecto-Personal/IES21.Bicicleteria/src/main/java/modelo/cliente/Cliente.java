package modelo.cliente;

import modelo.persona.Persona;

/**
 *
 * @author rodri
 */

public class Cliente extends Persona {

    private int id;
    private String codigo;
    private String cuil;

    public Cliente() {
        super();
    }
    
    public Cliente(String nombre, String apellido) {
        super(nombre, apellido); // Llamada al constructor de la clase base Persona
    }
    
    // Este constructor se usa en obtenerCliente
    public Cliente(String cuil, String nombre, String apellido, String dni, String telefono, String email) {
        super(nombre, apellido, dni, telefono, email);
        this.cuil = cuil;
    }
    
    // Este constructor se usa en obtenerClientePorId y getClientes
    public Cliente(String codigo, String cuil, String nombre, String apellido, String dni, String telefono, String email) {
        super(nombre, apellido, dni, telefono, email);
        this.codigo = codigo; 
        this.cuil = cuil;
    }

    // Este constructor se usa en obtenerPedidos
    public Cliente(String codigo, String nombre, String apellido) {
        super(nombre, apellido);
        this.codigo = codigo;
    }
    
    public Cliente(String codigo) {
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

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }
    
    @Override
    public String toString() {
        return this.codigo;
    }
}