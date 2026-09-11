package cl.pucv.mascotas.model;

import cl.pucv.mascotas.model.Mascota;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cliente {
    
    private String rut; //atributo obligatorio
    private String nombre; //atributo obligatorio
    private String correo; //opcional
    private String telefono; //opcional 
    private String direccion; //opcional
    
    private Map<String, Mascota> mascotas;

    //constructores + sobrecarga
    public Cliente(String rut, String nombre){
        this.rut = rut;
        this.nombre = nombre;
        this.correo = null;
        this.telefono = null;
        this.direccion = null;   
        this.mascotas = new HashMap<>();
    }

    public Cliente(String rut, String nombre, String correo){
        this.rut = rut;
        this.nombre = nombre;
        this.correo = correo; 
        this.telefono = null;
        this.direccion = null;
        this.mascotas = new HashMap<>();
        
    }

    public Cliente(String rut, String nombre, String correo, String telefono){
        this.rut = rut;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = null;
        this.mascotas = new HashMap<>();
    }

    public Cliente(String rut, String nombre, String correo, String telefono, String direccion){
        this.rut = rut;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.mascotas = new HashMap<>();
    }

    //getters
    public String getRutCliente(){return rut;}
    public String getNombreCliente(){return nombre;}
    public String getCorreoCliente(){return correo;}
    public String getTelefono(){return telefono;}
    public String getDireccion(){return direccion;}
    public Map<String, Mascota> getMascotas() {return mascotas;}

    //setters
    public void setRutCliente(String rut){this.rut = rut;}
    public void setNombreCliente(String nombre){this.nombre = nombre;}
    public void setCorreoCliente(String correo){this.correo = correo;}
    public void setTelefono(String telefono){this.telefono = telefono;}
    public void setDireccion(String direccion){this.direccion = direccion;}
    public void setMascotas(Map<String, Mascota> mascotas) {this.mascotas = mascotas;}
    
    // Manejo de mascotas por cliente
    
    public void agregarMascota(Mascota mascota)
    {
        mascotas.put(mascota.getId(), mascota);
    }
    public void eliminarMascota(String id)
    {
        mascotas.remove(id); 
    }
    public Mascota obtenerMascota(String id)
    {
        return mascotas.get(id);
    }

    public List<Mascota> listarMascotas() {
        return new ArrayList<>(mascotas.values());
    }

    @Override
    public String toString() {
        return "RUT: " + rut +
            " | Nombre: " + nombre +
            " | Correo: " + correo +
            " | Teléfono: " + telefono +
            " | Dirección: " + direccion;
    }
}
