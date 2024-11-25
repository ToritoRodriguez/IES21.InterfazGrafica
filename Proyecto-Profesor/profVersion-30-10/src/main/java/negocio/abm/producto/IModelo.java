/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.abm.producto;

import java.util.List;
import modelo.producto.marca.Modelo;
/**
 *
 * @author Alumno
 */
public interface IModelo {
    
    public List<Modelo> tarerModeloPorMarca(String marca);
    
    public Modelo taerModelo(String modelo);
    
}
