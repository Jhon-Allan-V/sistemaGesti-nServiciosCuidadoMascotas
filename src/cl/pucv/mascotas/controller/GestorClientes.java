package cl.pucv.mascotas.controller;

import cl.pucv.mascotas.model.Cliente;
import java.util.HashMap;
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
    
    public boolean estaCliente(String rut)
    {
        return clientes.containskey(rut);
    }
    
    public Cliente obtenerCliente(String rut)
    {
        return clientes.get(rut); 
    }
}
