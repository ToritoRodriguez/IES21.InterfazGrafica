/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package principal;

import java.util.Scanner;
import modelo.cliente.Cliente;
import modelo.vendedor.Vendedor;
import negocio.abm.cliente.ABMCliente;
import negocio.abm.cliente.exeption.ClienteException;
import repositorio.RepositorioDeDatos;

public class VentaBici {
    
    private static ABMCliente aBMCliente= new ABMCliente();
    
    public static void main(String[] args) {      
        menu();
    }
    private static void menu(){
        System.out.println("====================== MENU===========================");
        System.out.println("=================== SELECIONE UNA OPCION ==============");
        System.out.println("1 ABM CLIENTE");
        System.out.println("2 ABM VENDEDOR");
        System.out.println("3 ABM PROVEEDOR");
        System.out.println("4 SALIR");
        Scanner scan= new Scanner(System.in);
        int opt=scan.nextInt();
        while(opt!=4){
         switch(opt){
             case 1:
                 subMenuAbmCliente();
                 break;
             case 2:
                 break;
             case 3:
                break;
             default:
                 System.out.println("Ingrese una opcion comprendida entre 1 y 4");
                 break;
         }
         opt=scan.nextInt();
        }
    }
    
    private static void subMenuAbmCliente(){
        System.out.println("=================== SUB MENU ABM CLIENTE ==============");
        System.out.println("1 ALTA CLIENTE");
        System.out.println("2 BAJA CLIENTE");
        System.out.println("3 MODIFICAR CLIENTE");
        System.out.println("4 LISTAR TODOS LOS CLIENTES");
        System.out.println("5 LISTAR CLIENTE POR FILTRO");
        System.out.println("6 VOLVER AL MENU PRIMNCIPAL");
        Scanner scan= new Scanner(System.in);
        int opt=scan.nextInt();
        while(opt!=6){
         switch(opt){
             case 1:
                 try{
                 altaCliente();
                 }catch(ClienteException e){
                     System.out.println(e.getMessage());
                 }
                 break;
             case 2:
                 break;
             case 3:
                break;
             case 4:
                 aBMCliente.listarPersonass(null, null,null,0);
                 break;
             case 5:
                 buscarClientePorFiltro(); 
                 break;
            default:
                System.out.println("Ingrese una opcion comprendida entre 1 y 6");
                break;
         }
         subMenuAbmCliente();
         opt=scan.nextInt();
        }
        menu();
    }
    
    private static void altaCliente()throws ClienteException{
        Scanner scan= new Scanner(System.in);
        System.out.println("Ingrese el cuil del cliente");
        String cuil=scan.next();
        System.out.println("Ingrese nombre del clinete");
        String nombre=scan.next();
        System.out.println("Ingrese apellido del cliente");
        String apellido=scan.next();
        System.out.println("Ingrese el dni del cliente");
        int dni=scan.nextInt();
        System.out.println("Ingese telefono del cliente");
        String telefono = scan.next();
        System.out.println("Ingrese el email del cliente");
        String email= scan.next();
        Cliente cliente= new Cliente(cuil, nombre, apellido, dni, telefono, email);
        ABMCliente aBMCliente= new ABMCliente();
        cliente.setCodigo(aBMCliente.proximoCodigo());
        //System.out.println(cliente.getCodigo());
        aBMCliente.altaPersonas(cliente);
    }
    
    private static void buscarClientePorFiltro(){
        Scanner scan= new Scanner(System.in);
        System.out.println("Ingrese el codigo de cliente");
        String codigo=scan.next();
        System.out.println("Ingrese el nombre de cliente");
        String nombre=scan.next();
        System.out.println("Ingrese el apellido de cliente");
        String apellido=scan.next();
        System.out.println("Ingrese el dni de cliente");
        int dni=scan.nextInt();
        aBMCliente.listarPersonass(codigo, nombre, apellido, dni);
    }
}
