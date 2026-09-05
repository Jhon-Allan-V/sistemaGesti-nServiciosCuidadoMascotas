package cl.pucv.mascotas.ui;

import cl.pucv.mascotas.facade.SistemaFacade;

/*
Funcion u objetivo: gestionar la pantalla grafica en el que el usuario usara el programa
*/

public class PantallaInterfaz implements InterfazUsuario{
    
    private SistemaFacade sistema;

    public PantallaInterfaz(SistemaFacade sistema){
        this.sistema = sistema;
    }

    @Override
    public void iniciar(){}

    @Override
    public void mostrarMenu(){}

    @Override 
    public void finalizar(){}

    public void opcionesInterfazGrafica(){}

}
