package cl.pucv.mascotas.controller;

import cl.pucv.mascotas.model.Reserva;
import cl.pucv.mascotas.model.Servicio;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorServicios
{
    private Map<Integer, Servicio> servicios;
    private Map<Integer, Reserva> reservas; 
    private int IDReserva; 
    
    public GestorServicios()
    {
        servicios = new HashMap<>();
        reservas = new HashMap<>();
        IDReserva = 1;
    }
    
    public void agregarServicio(Servicio servicio)
    {
        servicios.put(servicio.getCodigo(), servicio);
    }
    
    public boolean EstaServicio(int codigoServicio)
    {
        return servicios.containsKey(codigoServicio);
    }
    
    public Servicio obtenerServicio(int codigoServicio)
    {
        return servicios.get(codigoServicio);
    }
    
    public void reservarServicio(String rutCliente, String IdMascota, int codigoServicio)
    {
        if(servicios.containsKey(codigoServicio))
        {
            int idReserva = generarIdReserva();
            
            Reserva nueva = new Reserva();
            
            nueva.setIdReserva(idReserva);
            nueva.setRutCliente(rutCliente);
            nueva.setIdMascota(IdMascota);
            nueva.setCodigoSservicio(codigoServicio);
            nueva.setFechaReserva(LocalDate.now());
            nueva.setEstado("ACTIVA");
            
            reservas.put(idReserva, nueva); 
        }
    }
    
    
    public void cancelarServicio(int idReserva)
    {
        Reserva reserva = reservas.get(idReserva);
        
        if(reserva != null)
        {
            reserva.setEstado("CANCELADA"); 
        }
    }
    
    public List<Servicio> listaServicios()
    {
        return new ArrayList<>(servicios.values());
    }
    
    public List<Reserva> listaReservas()
    {
        return new ArrayList<>(reservas.values());
    }
    
    private int generarIdReserva()
    {
        int id = IDReserva;
        IDReserva++;
        
        return id; 
    }
}