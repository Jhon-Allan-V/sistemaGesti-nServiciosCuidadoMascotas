package cl.pucv.mascotas.facade;

import cl.pucv.mascotas.controller.GestorClientes;
import cl.pucv.mascotas.controller.GestorMascotas;
import cl.pucv.mascotas.controller.GestorServicios;
import cl.pucv.mascotas.model.Cliente;
import cl.pucv.mascotas.exception.ClienteNoEncontradoException;

public class SistemaFacade 
{
    
    private GestorClientes gestorClientes;
    private GestorMascotas gestorMascotas;
    private GestorServicios gestorServicios;
    
    public SistemaFacade()
    {
        gestorClientes = new GestorClientes(); 
        gestorMascotas = new GestorMascotas();
        gestorServicios = new GestorServicios();
    }
    
    public void registrarCliente(String rut, String nombre)
    {
        Cliente nuevo = new Cliente(rut, nombre);
       
        gestorClientes.agregarCliente(nuevo); 
    }
    public void registrarMascota(String rutdueno, String nombre, String raza,
            int edad, float peso, float altura)
    {
        gestorMascotas.agregarMascota(rutdueno, nombre, raza, edad, peso, altura);
    }
    
    public void registrarMascota(String rutdueno, String nombre, String raza,
            int edad, float peso, float altura, String tratoEspecial)
    {
        gestorMascotas.agregarMascota(rutdueno, nombre, raza, edad, peso, altura, tratoEspecial);
    }
    
    public void reservarServicio(String rutCliente,String idMascota, int codigoServicio) 
        throws ClienteNoEncontradoException
    {
        //GestorServicios.reservarServicio(rutCliente, codigoServicio);
        if (!gestorClientes.existeCliente(rutCliente)){
            throw new ClienteNoEncontradoException("No se puede generar la reserva: el cliente con RUT: " + rutCliente + " no existe.");
        }
        gestorServicios.reservarServicio(rutCliente, idMascota, codigoServicio);
    }
    public void cancelarServicio(int codigoServicio) 
    {
        gestorServicios.cancelarServicio(codigoServicio);
    }

    public void eliminarCliente(String rut) 
    {
        gestorClientes.eliminarCliente(rut);
    }
}
