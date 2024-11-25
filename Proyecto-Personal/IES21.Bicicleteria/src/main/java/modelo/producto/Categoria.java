package modelo.producto;

/**
 *
 * @author rodri
 */

public class Categoria {

    private int id;
    private String codigo;
    private String nombre;
    private String categoria;
    private TipoCategoria tipo;

    // Este constructor se usa en getCategoriasComboBox
    public Categoria(int id, String categoria, TipoCategoria tipo) {
        this.id = id;
        this.categoria = categoria;
        this.tipo = tipo;
    }
    
    // Este constructor se usa en getCategorias y obtenerCategoria
    public Categoria(String codigo, String nombre, TipoCategoria tipo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
    }
    
    // Este constructor se usa en el GUI de AltaCategoria 
    public Categoria(String categoria, TipoCategoria tipo) {
        this.categoria = categoria;
        this.tipo = tipo;
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
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public TipoCategoria getTipo() {
        return tipo;
    }

    public void setTipo(TipoCategoria tipo) {
        this.tipo = tipo;
    }

    public int getIdCategoria() {
        return this.id;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}