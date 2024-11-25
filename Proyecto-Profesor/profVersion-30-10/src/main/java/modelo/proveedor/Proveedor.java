/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.proveedor;

import java.util.Calendar;
import java.util.Date;
import modelo.cliente.Cliente;
import modelo.persona.Persona;
import repositorio.RepositorioDeDatos;

public class Proveedor extends Persona {
    
    private String codigo;
    private String nombreFantasia;
    private String cuit;

    public Proveedor(String codigo, String nombreFantasia, String cuit) {
        this.codigo = codigo;
        this.nombreFantasia = nombreFantasia;
        this.cuit = cuit;
    }

    public Proveedor(String codigo, String nombreFantasia, String cuit, String nombre, String apellido, int dni) {
        super(nombre, apellido, dni,"", "");
        this.codigo = codigo;
        this.nombreFantasia = nombreFantasia;
        this.cuit = cuit;
    }

    @Override
    public String genetarCodigo() {
        Calendar calendar=Calendar.getInstance();
        int anio=calendar.get(Calendar.YEAR);
        return "P-"+anio+"-"+contarProveedores();
    }
    
    private int contarProveedores(){
        return (RepositorioDeDatos.arrPersonas!=null 
                && RepositorioDeDatos.arrPersonas.size()==0)?1:RepositorioDeDatos.arrPersonas.size()+1 ;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombreFantasia() {
        return nombreFantasia;
    }

    public void setNombreFantasia(String nombreFantasia) {
        this.nombreFantasia = nombreFantasia;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }
   
    public String toString(){
        return "NOMBRE: "+getNombre()+ " Apellido: "+getApellido()+" ";
    }
}
