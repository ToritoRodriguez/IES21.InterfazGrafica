/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.abm.proveedor;

import java.util.List;
import modelo.cliente.Cliente;
import modelo.persona.Persona;
import modelo.proveedor.Proveedor;
import repositorio.RepositorioDeDatos;

/**
 *
 * @author Alumno
 */
public class ABMProveedor implements IAMBProveedor {

    @Override
    public void listarProveedoresFiltradoPorCodigo(String codigo) {
        for (Persona arrPersona : RepositorioDeDatos.arrPersonas) {
            if(arrPersona instanceof Proveedor){
                Proveedor proveedor=(Proveedor)arrPersona;
                if(proveedor.getCodigo().equals(codigo))
                    proveedor.toString();
            }
        }
    }

    @Override
    public void altaPersona(Persona persona) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void bajaPersona(Persona persona) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void modificarDatosPersona(Persona persona) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void listarPersonas(String tipo) {
        
    }

    @Override
    public void altaPersonas(Persona persona) {
        Proveedor proveedor=(Proveedor)persona;
       if(proveedor!=null){
           if(!existe(proveedor))
               RepositorioDeDatos.arrPersonas.add(proveedor);
           else
               System.out.println("El provedor ya existe");
       }else
            System.out.println("El proveedor no puede ser nulo");
    }

    @Override
    public List<?> consualtarPersonaPorFiltro(String nombre, String apellido, int dni) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public void bajaPersonas(Persona persona) {
        Proveedor proveedor=(Proveedor)persona;
       if(proveedor!=null){
           /*
           if(RepositorioDeDatos.arrPersonas.remove(cliente))
               System.out.println("El cliente fue eliminado con exito");
           */
            Proveedor proveedor2=(Proveedor)RepositorioDeDatos.arrPersonas
                    .remove(RepositorioDeDatos.arrPersonas.indexOf(proveedor));
            if(proveedor2!=null)
                System.out.println("El proveedor: "+proveedor2.getDni()+ " Fue eliominado con exito");
       }else
            System.out.println("El proveedor a eliminar no puede ser nulo");
    }

    @Override
    public void modificarDatosPersonas(Persona persona) {
        
    }

    @Override
    public void modificarDatosPersonas(Persona perOriginal, Persona perUpdate) {
            }

    @Override
    public void listarPersonass(String codigo, String nombre, String apellido, int dni) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String proximoCodigo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private boolean existe(Proveedor proveedor){
        /*
        if(RepositorioDeDatos.arrPersonas.contains(cli))
            return true;
        return false;
        */
        return RepositorioDeDatos.arrPersonas.contains(proveedor);
    }
    
}
