/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package principal;

import java.util.Scanner;
import modelo.cliente.Cliente;
import modelo.vendedor.Vendedor;
import negocio.abm.cliente.ABMCliente;
import negocio.abm.cliente.exeption.ClienteException;
import principal.vista.JFormMenu;
import repositorio.RepositorioDeDatos;

/**
 *
 * @author Alumno
 */
public class VentaBici {
    
    private static ABMCliente aBMCliente= new ABMCliente();
    
    public static void main(String[] args) {
        /*
        Cliente c1=new Cliente("20-33521457-0","JUAN", "PEREZ", 33521457, "3541521451","jperez@gmail.com");
        RepositorioDeDatos.personas[0]=c1;
        Cliente c2=new Cliente("20-35521787-0","MARCOS", "GUTIERRE", 35521787, "3515236874","mgutierre@gmail.com");
        RepositorioDeDatos.personas[1]=c2;
        Vendedor v1= new Vendedor( "CENTRO","VERONICA", "LOPEZ", 298785475, "3515326541", "vlopez@gmail.com");
        RepositorioDeDatos.personas[2]=v1;
        Cliente c3=new Cliente("20-35521785-0","LORENA", "RAMOS",35521785, "3515836874","lramos@gmail.com");
        RepositorioDeDatos.personas[3]=c3;
        Vendedor v2= new Vendedor( "OESTE","JULIAN", "SOSA", 398785475, "3515316541", "jsosa@gmail.com");
        RepositorioDeDatos.personas[4]=v2;
      
        
        System.out.println("Listar Clientes");
        int indice=0;
        while(indice<RepositorioDeDatos.personas.length-1){
            if(RepositorioDeDatos.personas[indice]!=null && RepositorioDeDatos.personas[indice] instanceof Cliente)
                System.out.println(((Cliente)RepositorioDeDatos.personas[indice]).toStringCliente());
            indice++;
        }
       
        System.out.println("Listar VEndedores");
        
        try{
            Cliente c1=new Cliente("20-33521457-0","JUAN", "PEREZ", 33521457, "3541521451","jperez@gmail.com");
            aBMCliente.altaPersonas(c1);
            Cliente c2=new Cliente("20-35521787-0","MARCOS", "GUTIERRE", 35521787, "3515236874","mgutierre@gmail.com");
            aBMCliente.altaPersonas(c2);
            Vendedor v1= new Vendedor( "CENTRO","VERONICA", "LOPEZ", 298785475, "3515326541", "vlopez@gmail.com");
            //RepositorioDeDatos.personas[2]=v1;
            Cliente c3=new Cliente("20-35521785-0","LORENA", "RAMOS",35521785, "3515836874","lramos@gmail.com");
            aBMCliente.altaPersonas(c3);
            System.out.println("Listado Sin Modificaciòn");
            aBMCliente.listarPersonass("");
            Cliente c4=new Cliente("20-35521785-0","LAURA", "RAMOS",35521785, "3515836874","lramos@gmail.com");
            aBMCliente.modificarDatosPersonas(c4);
            //RepositorioDeDatos.personas[3]=c3;
            //Vendedor v2= new Vendedor( "OESTE","JULIAN", "SOSA", 398785475, "3515316541", "jsosa@gmail.com");
            //RepositorioDeDatos.personas[4]=v2;
            System.out.println("Listado con Modificaciòn");
            aBMCliente.listarPersonass("");
            Cliente clienteEliminar=new Cliente();
            clienteEliminar.setDni(35521787);
            aBMCliente.bajaPersonas(clienteEliminar);

            System.out.println("Listado con con baja");
            aBMCliente.listarPersonass("");

           Cliente c5=new Cliente("20-33521457-0","PABLO", "FERRERA", 33521457, "3541521451","fferreyra@gmail.com");

          aBMCliente.modificarDatosPersonas(c1, c5);

          System.out.println("Listado con modificacio sobrecarga 2 parametros en mopdificar");
          aBMCliente.listarPersonass("");
        }catch (ClienteException e){
            System.out.println("Error: "+e.getMessage());
        }
        */        
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
        System.out.println("6 MENU");
        System.out.println("7 VOLVER AL MENU PRIMNCIPAL");
        Scanner scan= new Scanner(System.in);
        int opt=scan.nextInt();
        while(opt!=7){
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
             case 6:
                levantarVistaMenu();
                break;
         }
         subMenuAbmCliente();
         opt=scan.nextInt();
        }
        menu();
    }
    
    private static void levantarVistaMenu(){
         java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFormMenu().setVisible(true);
            }
        });
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
