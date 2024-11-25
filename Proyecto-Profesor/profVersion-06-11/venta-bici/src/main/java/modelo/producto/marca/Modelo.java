/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.producto.marca;

/**
 *
 * @author Alumno
 */
public class Modelo {
     private String modelo;
     private Marca marca;
     private String descripcion;
     private Rodado rodado;

    public Modelo(String modelo, Marca marca, String descripcion, Rodado rodado) {
        this.modelo = modelo;
        this.marca = marca;
        this.descripcion = descripcion;
        this.rodado = rodado;
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
     
    
}
