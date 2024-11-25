/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vendedor;

import modelo.persona.Persona;

public class Vendedor extends Persona {
    
    private String codigo;
    private String sucursal;
    
    public Vendedor(){
        super();
    }
    public Vendedor(String sucursal) {
        this.codigo = genetarCodigo();
        this.sucursal = sucursal;
    }

    public Vendedor(String sucursal, String nombre, String apellido, int dni, String telefono, String email) {
        super(nombre, apellido, dni, telefono, email);
        this.codigo = genetarCodigo();
        this.sucursal = sucursal;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    @Override
    public String genetarCodigo() {
        return "V-"+contarVebdedores();
    }
    
    private int contarVebdedores(){
        return 1;
    }
}
