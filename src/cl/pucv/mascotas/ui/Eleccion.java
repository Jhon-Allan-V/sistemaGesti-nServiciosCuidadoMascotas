package cl.pucv.mascotas.ui;

import cl.pucv.mascotas.facade.SistemaFacade;
//import cl.pucv.mascotas.ui.*;
import java.util.Scanner;


public class Eleccion {
    
    private InterfazUsuario interfaz; 
    private final SistemaFacade sistema; 

    public Eleccion(SistemaFacade sistema){
          this.sistema = sistema;
    }

    public void tomarEleccion(){

        Scanner eleccion = new Scanner(System.in);

        System.out.println("Seleccione el modo de ejecucion de el programa:");
        System.out.println("1. Modo Consola o Terminal.");
        System.out.println("2. Modo Ventana (interfaz).");
        System.out.println("Ingrese opcion (1 o 2): ");

        int opcion = 1;

        try{
            opcion = Integer.parseInt(eleccion.nextLine());
        } catch(NumberFormatException e){
            System.out.println("Entrada invalida; Se iniciara en modo Consola o Terminal por defecto.");
        }

        if (opcion == 2){
            this.interfaz = new PantallaInterfaz(this.sistema);
        } else {
            this.interfaz = new ConsolaTerminal(this.sistema);
        }
        
        this.interfaz.iniciar();
    }
}
