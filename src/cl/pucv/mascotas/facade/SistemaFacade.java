package cl.pucv.mascotas.facade;

import cl.pucv.mascotas.controller.GestorClientes;
import cl.pucv.mascotas.controller.GestorMascotas;
import cl.pucv.mascotas.controller.GestorServicios;

import cl.pucv.mascotas.model.Cliente;
import cl.pucv.mascotas.model.Mascota;

import java.util.List;

public class SistemaFacade 
{
    
    private GestorClientes gestorClientes;
    private GestorMascotas gestorMascotas;
    private GestorServicios gestorServicios;
    
    public SistemaFacade()
    {
        gestorClientes = new GestorClientes(); 
        gestorMascotas = new GestorMascotas(gestorClientes);
        gestorServicios = new GestorServicios();
    }
    
    //----------------------------
    // Menu Usuario
    //----------------------------
    
    public void registrarCliente(String rut, String nombre)
    {
        Cliente nuevo = new Cliente(rut, nombre);
       
        gestorClientes.agregarCliente(nuevo); 
    }
    
    public Cliente buscarCliente(String rut) 
    {
        return gestorClientes.obtenerCliente(rut);
    }
    
    public List<Cliente> listarClientes()
    {
        return gestorClientes.listarClientes();
    }
    
    public void modificarCliente(String rut, String nombre, String correo, String telefono, String direccion)
    {
        gestorClientes.modificarCliente(rut, nombre, correo, telefono, direccion);
    }
    
    public void eliminarCliente(String rut)
    {
        gestorClientes.eliminarCliente(rut); 
    }
    
    public boolean HayCliente(String rut)
    {
        return gestorClientes.existeCliente(rut); 
    }
    
    //----------------------------
    // Menu Mascotas              
    //----------------------------
    
    public void registrarMascota(String rutDueno, String nombre, String raza, int edad, float peso, float altura)
    {
        gestorMascotas.agregarMascota(rutDueno, nombre, raza, edad, peso, altura);
    }
    
    public void registrarMascota(String rutDueno, String nombre, String raza, int edad, float peso, float altura, String tratoEspecial)
    {
        gestorMascotas.agregarMascota(rutDueno, nombre, raza, edad, peso, altura, tratoEspecial);
    }
    
    public Mascota buscarMascota(String rutDueno, String id)
    {
        return gestorMascotas.obtenerMascota(rutDueno, id);
    }
    
    public List<Mascota> listarMascotas()
    {
        return gestorMascotas.listarMascotas();
    }
    
    public List<Mascota> listarMascotasCliente(String rutCliente)
    {
        return gestorMascotas.obtenerPorCliente(rutCliente);
    }
    
    public void modificarMascosta(String rutCliente, String id, String nombre, String raza, int edad, float peso, float altura, String tratoEspecial)
    {
        gestorMascotas.modificarMascota(rutCliente, id, nombre, raza, edad, peso, altura, tratoEspecial);
    }
    
    public void eliminarMascota(String rutCliente, String id)
    {
        gestorMascotas.eliminarMascota(rutCliente, id);
    }
    
    //----------------------------
    // Menu Servicio            
    //----------------------------
    
    public void reservarServicio(String rutCliente, String idMascota, int codigoServicio)
    {
        gestorServicios.reservarServicio(rutCliente, idMascota, codigoServicio);
    }
    
    public void cancelarServicio(int idReserva)
    {
        gestorServicios.cancelarServicio(idReserva);
    }
}
