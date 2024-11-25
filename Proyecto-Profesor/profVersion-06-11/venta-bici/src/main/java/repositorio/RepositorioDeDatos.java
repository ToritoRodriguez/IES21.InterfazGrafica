/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import java.util.ArrayList;
import java.util.List;
import modelo.persona.Persona;
import modelo.producto.Producto;
import modelo.producto.marca.Marca;
import util.coleccion.ArrayListPersona;
import modelo.producto.marca.Modelo;
/**
 *
 * @author Alumno
 */
public abstract class RepositorioDeDatos {
    
    private static final int CANTIDAD_PERSONA_MAXIMA=10;
    
    public static int indice=0;
    
    public static Persona[]personas=new Persona[CANTIDAD_PERSONA_MAXIMA];
    
    public static ArrayListPersona<Persona> arrPersonas= new ArrayListPersona<Persona>();
    
    public static List<Producto> productos= new ArrayList<Producto>();
    
    public static List<Marca> marcas= new ArrayList<Marca>();
    
    public static List<Modelo> modelos= new ArrayList<Modelo>();
    
}
