package modelo.producto.marca;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rodri
 */

public class Marca {

    private int id; 
    private String codigo;
    private String marca;
    private List<Modelo> modelos;
    
    // Este constructor se usa en getMarcas y obtenerMarca
    public Marca(String marca) {
        this.marca = marca;
        this.modelos = new ArrayList<>();
    }

    // Este constructor se usa para el ComboBox
    public Marca(String codigo, String nombre) {
        this.codigo = codigo;
        this.marca = nombre;
    }
    
    // Get and Set
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    public List<Modelo> getModelos() {
        return modelos;
    }

    public void setModelos(List<Modelo> modelos) {
        this.modelos = modelos;
    }
    
    @Override
    public String toString() {
        return this.marca;  // Esto asegura que el nombre de la marca se muestre en el JComboBox
    }
}