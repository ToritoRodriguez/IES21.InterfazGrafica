/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.abm.cliente;

import java.util.List;
import modelo.persona.Persona;
import negocio.abm.cliente.exeption.ClienteException;

/**
 *
 * @author Alumno
 */
public interface IABMPersona {
    
    public static final String ES_CLIENTE="cliente";
    public static final String ES_VENDEDOR="vendedor";
    
    public void altaPersona(Persona persona);
    
    public void bajaPersona(Persona persona);
    
    public void modificarDatosPersona(Persona persona);
    
    public void listarPersonas(String tipo);
    
    public void altaPersonas(Persona persona)throws ClienteException;
    
    public void bajaPersonas(Persona persona);
    
    public void modificarDatosPersonas(Persona persona);
    
    public void modificarDatosPersonas(Persona perOriginal,Persona perUpdate);
    
    public void listarPersonass(String codigo,String nombre,String apellido,int dni);
    
    public String proximoCodigo();
    
    public List<?> consualtarPersonaPorFiltro(String nombre,String apellido, int dni);
   
    
}
