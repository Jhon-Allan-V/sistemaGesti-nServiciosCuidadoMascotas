package cl.pucv.mascotas.controller;

import cl.pucv.mascotas.model.Mascota;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorMascotas 
{
    private Map<String, Mascota> mascotas;
    private int ID;
    
    public GestorMascotas()
    {
        mascotas = new HashMap<>();
        ID = 1; 
    }
    
    public void agregarMascota(String rutDueno, String nombre, String raza, int edad, float peso, float altura)
    {
        String id = generarId(); 
        
        Mascota nueva = new Mascota(id, rutDueno, nombre, raza, edad, peso, altura);
        
        mascotas.put(id, nueva);
    }
    
    public void agregarMascota(String rutDueno, String nombre, String raza, int edad, float peso, float altura, String tratoEspecial)
    {
        String id = generarId(); 
        
        Mascota nueva = new Mascota(id, rutDueno, nombre, raza, edad, peso, altura, tratoEspecial);
        
        mascotas.put(id, nueva);
    }
    
    public List<Mascota> obtenerPorCliente(String rutCliente)
    {
        List<Mascota> mascotasCliente = new ArrayList<>();
        
        for(Mascota mascota : mascotas.values())
        {
            if(mascota.getRutDueno().equals(rutCliente))
            {
                mascotasCliente.add(mascota); 
            }
        }
        
        return mascotasCliente; 
    }
    
    private String generarId()
    {
        String id = "M" + ID;
        ID ++; 
        
        return id;
    }
    
}
