package cl.pucv.mascotas;

import cl.pucv.mascotas.config.InformacionLocal;
import cl.pucv.mascotas.ui.Eleccion;
import cl.pucv.mascotas.facade.SistemaFacade;

public class Aplicacion {
    
    private InformacionLocal informacionLocal;
    private SistemaFacade sistema;
    private Eleccion eleccion;
    
    public Aplicacion(){
        this.informacionLocal = new InformacionLocal("Local LOS MERLUZOS", "IBC", "123456789", "LOSMERLUZOS@GMAIL.COM");
        this.sistema = new SistemaFacade();
        this.eleccion = new Eleccion(sistema);
    }

    public void arrancar(){
        
        System.out.println("=========================================");
        System.out.println(" Bienvenido a: " + informacionLocal.getNombre());
        System.out.println(" Dirección : " + informacionLocal.getDireccion());
        System.out.println(" Correo    : " +  informacionLocal.getCorreo());
        System.out.println(" Telefono  : " +  informacionLocal.getTelefono());
        System.out.println("=========================================\n");

        eleccion.tomarEleccion();
    }

}
