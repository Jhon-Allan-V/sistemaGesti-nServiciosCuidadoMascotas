package cl.pucv.mascotas.controller;

import cl.pucv.mascotas.model.Cliente;
import cl.pucv.mascotas.model.Mascota;
import java.util.ArrayList;
import java.util.List;

public class GestorMascotas
{
    private GestorClientes gestorClientes;
    private int ID;
    
    public GestorMascotas(GestorClientes gestorClientes)
    {
        this.gestorClientes = gestorClientes;
        ID = 1; 
    }
    
    public void agregarMascota(String rutDueno, String nombre, String raza, int edad, float peso, float altura)
    {
        Cliente cliente = gestorClientes.obtenerCliente(rutDueno);
        
        if(cliente == null) {return;}
        
        String id = generarId(); 
        
        Mascota nueva = new Mascota(id, rutDueno, nombre, raza, edad, peso, altura);
        
        cliente.agregarMascota(nueva);
    }
    
    public void agregarMascota(String rutDueno, String nombre, String raza, int edad, float peso, float altura, String tratoEspecial)
    {
        Cliente cliente = gestorClientes.obtenerCliente(rutDueno);
        
        if(cliente == null) {return;}
        
        String id = generarId(); 
        
        Mascota nueva = new Mascota(id, rutDueno, nombre, raza, edad, peso, altura, tratoEspecial);
        
        cliente.agregarMascota(nueva);
    }
    
    public Mascota obtenerMascota(String rutDueno, String id)
    {
        Cliente cliente = gestorClientes.obtenerCliente(rutDueno);
        
        if(cliente == null) {return null;}
        
        return cliente.obtenerMascota(id);
    }
    
     public List<Mascota> obtenerPorCliente(String rutCliente)
    {
        Cliente cliente = gestorClientes.obtenerCliente(rutCliente);
        
        if(cliente == null) {
            return new ArrayList<>();
        }
        
        return (List<Mascota>) cliente.listarMascotas();
    }
     
    public List<Mascota> listarMascotas()
    {
        List<Mascota> todas = new ArrayList<>();
        
        for(Cliente cliente : gestorClientes.listarClientes())
        {
            todas.addAll(cliente.listarMascotas());
        }
        
        return todas;
    }
    
    public void modificarMascota(String rutCliente, String id, String nombre, String raza, int edad, float peso, float altura, String tratoEspecial)
    {
        Mascota mascota = obtenerMascota(rutCliente, id);
        
        if(mascota != null)
        {
            mascota.setNombre(nombre);
            mascota.setRaza(raza);
            mascota.setEdad(edad);
            mascota.setPeso(peso);
            mascota.setAltura(altura);
            mascota.setTratoEspecial(tratoEspecial);
        }
    }
    
    public void eliminarMascota(String rutCliente, String id)
    {
        Cliente cliente = gestorClientes.obtenerCliente(rutCliente);
        
        if(cliente != null)
        {
            cliente.eliminarMascota(id);
        }
    }
    
    private String generarId()
    {
        String id = "M" + ID;
        ID++; 
        
        return id;
    }

    // SIA-11: carga en bloque de mascotas leidas desde el archivo CSV al iniciar la aplicacion.
    // Cada mascota tiene su id y su rutDueno. solo hay que asociarla a su cliente
    // y dejar el contador id listo para que las nuevas mascotas no choquen con las ya cargadas.
    public void cargarMascotas(List<Mascota> listaMascotas)
    {
        for (Mascota mascota : listaMascotas)
        {
            Cliente cliente = gestorClientes.obtenerCliente(mascota.getRutDueno());

            if (cliente != null)
            {
                cliente.agregarMascota(mascota);
            }

            actualizarContadorId(mascota.getId());
        }
    }

    private void actualizarContadorId(String id)
    {
        try
        {
            int numero = Integer.parseInt(id.substring(1));

            if (numero >= ID)
            {
                ID = numero + 1;
            }
        }
        catch (Exception e)
        {
            // Id con formato inesperado: se ignora para efectos del contador.
        }
    }
}
