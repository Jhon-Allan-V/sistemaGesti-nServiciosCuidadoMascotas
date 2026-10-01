package cl.pucv.mascotas.facade;

import cl.pucv.mascotas.controller.GestorClientes;
import cl.pucv.mascotas.controller.GestorMascotas;
import cl.pucv.mascotas.controller.GestorServicios;

import cl.pucv.mascotas.model.Cliente;
import cl.pucv.mascotas.model.Mascota;
import cl.pucv.mascotas.model.Servicio;
import cl.pucv.mascotas.model.Peluqueria;
import cl.pucv.mascotas.model.Veterinaria;
import cl.pucv.mascotas.model.Reserva;

import cl.pucv.mascotas.repository.RepositorioClienteCSV;
import cl.pucv.mascotas.repository.RepositorioMascotaCSV;
import cl.pucv.mascotas.repository.RepositorioServicioCSV;
import cl.pucv.mascotas.exception.PersistenciaException;
import cl.pucv.mascotas.exception.ClienteNoEncontradoException;

import java.time.LocalDate;
import java.util.List;

public class SistemaFacade 
{
    
    private GestorClientes gestorClientes;
    private GestorMascotas gestorMascotas;
    private GestorServicios gestorServicios;

    private RepositorioClienteCSV repositorioClientes;
    private RepositorioMascotaCSV repositorioMascotas;
    private RepositorioServicioCSV repositorioServicios;
    
    public SistemaFacade()
    {
        gestorClientes = new GestorClientes(); 
        gestorMascotas = new GestorMascotas(gestorClientes);
        gestorServicios = new GestorServicios();

        repositorioClientes = new RepositorioClienteCSV();
        repositorioMascotas = new RepositorioMascotaCSV();
        repositorioServicios = new RepositorioServicioCSV();

        boolean seCargaronDatosGuardados = cargarDatosDesdeArchivos();

        if (!seCargaronDatosGuardados)
        {
            cargarDatosIniciales();
        }
    }

    // SIA-11: persistencia batch - carga los datos guardados en los archivos CSV
    // (si existen) al arrancar la aplicacion. Devuelve false si no habia nada
    // guardado todavia, para que en ese caso se usen los datos iniciales de ejemplo.
    private boolean cargarDatosDesdeArchivos()
    {
        try
        {
            List<Cliente> clientesGuardados = repositorioClientes.listarTodos();
            List<Mascota> mascotasGuardadas = repositorioMascotas.listarTodos();
            List<Servicio> serviciosGuardados = repositorioServicios.listarTodos();
            List<Reserva> reservasGuardadas = repositorioServicios.listarReservas();

            if (clientesGuardados.isEmpty() && serviciosGuardados.isEmpty())
            {
                return false;
            }

            gestorClientes.cargarClientes(clientesGuardados);
            gestorMascotas.cargarMascotas(mascotasGuardadas);
            gestorServicios.cargarServicios(serviciosGuardados);
            gestorServicios.cargarReservas(reservasGuardadas);

            return true;

        }
        catch (PersistenciaException e)
        {
            System.out.println("No se pudieron cargar los datos guardados (" + e.getMessage() + "). Se usaran datos iniciales de ejemplo.");
            return false;
        }
    }

    // SIA-11: persistencia batch - guarda el estado completo del sistema en los
    // archivos CSV. Se debe llamar al cerrar la aplicacion (en ambas interfaces).
    public void guardarDatosEnArchivos()
    {
        try
        {
            repositorioClientes.guardarTodos(listarClientes());
            repositorioMascotas.guardarTodos(listarMascotas());
            repositorioServicios.guardarTodos(listarServicios());
            repositorioServicios.guardarReservas(listarReservas());
        }
        catch (PersistenciaException e)
        {
            System.out.println("No se pudieron guardar los datos: " + e.getMessage());
        }
    }

    private void cargarDatosIniciales()
    {
        registrarCliente("11111111-1", "Juan Perez");
        registrarMascota("11111111-1", "Firulais", "Labrador", 3, 22.5f, 55f);

        registrarCliente("22222222-2", "Maria Soto");
        registrarMascota("22222222-2", "Michi", "Siames", 2, 4.2f, 25f, "Se asusta con ruidos fuertes");

       
        registrarServicioPeluqueria(1, "Baño y corte estandar", 12000, "Corte estandar", 45);
        registrarServicioVeterinaria(2, "Control general", 15000, "Dra. Ana Rojas", "Medicina general", "LIC-4521");
    }
    
    // Menu Usuario

    public void registrarCliente(String rut, String nombre)
    {
        Cliente nuevo = new Cliente(rut, nombre);
       
        gestorClientes.agregarCliente(nuevo); 
    }
    
    public Cliente buscarCliente(String rut) throws ClienteNoEncontradoException
    {
        Cliente cliente = gestorClientes.obtenerCliente(rut);

        if (cliente == null)
        {
            throw new ClienteNoEncontradoException(
                "No existe un cliente con el RUT " + rut
            );
        }

        return cliente;
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
    
    // Menu Mascotas              

    
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
    
    public void modificarMascota(String rutCliente, String id, String nombre, String raza, int edad, float peso, float altura, String tratoEspecial)
    {
        gestorMascotas.modificarMascota(rutCliente, id, nombre, raza, edad, peso, altura, tratoEspecial);
    }
    
    public void eliminarMascota(String rutCliente, String id)
    {
        gestorMascotas.eliminarMascota(rutCliente, id);
    }
    
    // Menu Servicio            
   
    
    public void reservarServicio(String rutCliente, String idMascota, int codigoServicio)
    {
        gestorServicios.reservarServicio(rutCliente, idMascota, codigoServicio);
    }
    
    public void cancelarServicio(int idReserva)
    {
        gestorServicios.cancelarServicio(idReserva);
    }

    public void registrarServicioPeluqueria(int codigo, String descripcion, double costo, String tipoCorte, int duracionCorte)
    {
        Peluqueria nuevo = new Peluqueria(codigo, "Peluqueria", descripcion, costo, LocalDate.now(), tipoCorte, duracionCorte);
        gestorServicios.agregarServicio(nuevo);
    }

    public void registrarServicioVeterinaria(int codigo, String descripcion, double costo, String nombreVeterinario, String especialidad, String licencia)
    {
        Veterinaria nuevo = new Veterinaria(codigo, "Veterinaria", descripcion, costo, LocalDate.now(), nombreVeterinario, especialidad, licencia);
        gestorServicios.agregarServicio(nuevo);
    }

    public boolean existeServicio(int codigoServicio)
    {
        return gestorServicios.EstaServicio(codigoServicio);
    }

    public List<Servicio> listarServicios()
    {
        return gestorServicios.listaServicios();
    }

    public List<Reserva> listarReservas()
    {
        return gestorServicios.listaReservas();
    }
}