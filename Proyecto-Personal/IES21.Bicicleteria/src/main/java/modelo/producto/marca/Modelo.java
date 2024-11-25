package modelo.producto.marca;

/**
 *
 * @author rodri
 */

public class Modelo {

    private int id; 
    private String codigo;
    private String modelo;
    private Marca marca;
    private String descripcion;
    private Rodado rodado;

    public Modelo(int id, String codigo, String modelo, Marca marca, String descripcion, Rodado rodado) {
        this.id = id;
        this.codigo = codigo;
        this.modelo = modelo;
        this.marca = marca;
        this.descripcion = descripcion;
        this.rodado = rodado;
    }
    
    public Modelo(String codigo, String modelo, Marca marca, String descripcion, Rodado rodado) {
        this.codigo = codigo;
        this.modelo = modelo;
        this.marca = marca;
        this.descripcion = descripcion;
        this.rodado = rodado;
    }
    
    public Modelo(int id, String modelo, Marca marca, String descripcion, Rodado rodado) {
        this.id = id; 
        this.modelo = modelo;
        this.marca = marca;
        this.descripcion = descripcion;
        this.rodado = rodado;
    }

    public Modelo(String codigo, String modelo) {
        this.codigo = codigo;
        this.modelo = modelo;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Rodado getRodado() {
        return rodado;
    }

    public void setRodado(Rodado rodado) {
        this.rodado = rodado;
    }

    public int getIdModelo() {
        return this.id;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    @Override
    public String toString() {
        return modelo; // Mostramos solo el nombre del modelo
    }
}