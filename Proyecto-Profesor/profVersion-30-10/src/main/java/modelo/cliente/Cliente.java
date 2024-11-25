package modelo.cliente;

import modelo.persona.Persona;
import repositorio.RepositorioDeDatos;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

public class Cliente extends Persona{
    
    private String codigo;
    private String cuil;
    
    
    public Cliente(){
        super();
    }
            
    public Cliente(String cuil) {
        this.cuil = cuil;
    }

    public Cliente(String cuil, String nombre, String apellido, int dni, String telefono, String email) {
        super(nombre, apellido, dni, telefono, email);
        this.cuil = cuil;
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
    public String genetarCodigo() {
       return "C-"+contarClientes();
    }
    
    private int contarClientes(){
        int contar=0;
        for(int i=0;i<RepositorioDeDatos.personas.length-1;i++){
            if(RepositorioDeDatos.personas[i] instanceof Cliente)
                contar++;      
        }
        return contar;
    }
    
    public String toStringCliente(){
        return toString()+ " CODIGO: "+this.codigo+ " CUIL: "+this.cuil;
    }
}
