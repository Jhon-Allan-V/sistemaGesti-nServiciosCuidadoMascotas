package cl.pucv.mascotas.controller;

import cl.pucv.mascotas.model.Cliente;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorClientes 
{
    private  Map<String, Cliente> clientes; 
    
    public GestorClientes()
    {
        clientes = new HashMap<>(); 
    }
    
    public void agregarCliente(Cliente cliente)
    {
        clientes.put(cliente.getRutCliente(), cliente);
    }
    
    public void eliminarCliente(String rut)
    {
        clientes.remove(rut);
    }
    
    public boolean existeCliente(String rut)
    {
        return clientes.containsKey(rut);
    }
    
    public Cliente obtenerCliente(String rut)
    {
        return clientes.get(rut); 
    }
    
    public List<Cliente> listarClientes()
    {
        return new ArrayList<>(clientes.values());
    }
    
    public void modificarCliente(String rut, String nombre, String correo, String telefono, String direccion)
    {
        Cliente cliente = clientes.get(rut);
        
        if(cliente != null)
        {
            cliente.setNombreCliente(nombre);
            cliente.setCorreoCliente(correo);
            cliente.setTelefono(telefono);
            cliente.setDireccion(direccion);
        }
    }
}
