/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.abm.proveedor;

import negocio.abm.cliente.IABMPersona;

/**
 *
 * @author Alumno
 */
public interface IAMBProveedor extends IABMPersona {
    
    public void listarProveedoresFiltradoPorCodigo(String codigo);
}
