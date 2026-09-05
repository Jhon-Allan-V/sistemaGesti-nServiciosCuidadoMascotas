package cl.pucv.mascotas.ui;

import cl.pucv.mascotas.facade.SistemaFacade;
import java.util.Scanner;

/*
Funcion u objetivo: gestionar la consola o terminal en el que el usuario usara el programa
*/

public class ConsolaTerminal implements InterfazUsuario{

    private SistemaFacade sistema;
    private Scanner scanner;
    
    public ConsolaTerminal(SistemaFacade sistema){
        this.sistema = sistema;
    }

    @Override
    public void iniciar(){}

    @Override
    public void mostrarMenu(){}

    @Override 
    public void finalizar(){}

    public void opcionesMenuConsola(){}



}
