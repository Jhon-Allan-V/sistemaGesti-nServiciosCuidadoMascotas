package cl.pucv.mascotas.ui;

import cl.pucv.mascotas.facade.SistemaFacade;
import cl.pucv.mascotas.model.Cliente;
import cl.pucv.mascotas.model.Mascota;
import cl.pucv.mascotas.model.Servicio;
import cl.pucv.mascotas.model.Reserva;
import java.util.List;
import java.util.Scanner;

import cl.pucv.mascotas.exception.ClienteNoEncontradoException;
//Funcion u objetivo: gestionar la consola o terminal en el que el usuario usara el programa


public class ConsolaTerminal implements InterfazUsuario{

    private SistemaFacade sistema;
    private Scanner scanner;
    private boolean activo;

    public ConsolaTerminal(SistemaFacade sistema){
        this.sistema = sistema;
        this.scanner = new Scanner(System.in);
    }

    public ConsolaTerminal(SistemaFacade sistema, Scanner scanner){
        this.sistema = sistema;
        this.scanner = scanner;
    }

    @Override
    public void iniciar(){
        activo = true;

        while(activo){
            mostrarMenu();
            int opcion = leerEntero("Ingrese opcion: ");

            switch(opcion){
                case 1: menuClientes(); break;
                case 2: menuMascotas(); break;
                case 3: menuServicios(); break;
                case 0: finalizar(); break;
                default: System.out.println("Opcion invalida.\n");
            }
        }
    }

    @Override
    public void mostrarMenu(){
        System.out.println("========================================");
        System.out.println(" MENU PRINCIPAL");
        System.out.println("========================================");
        System.out.println("1. Gestionar Clientes");
        System.out.println("2. Gestionar Mascotas");
        System.out.println("3. Reservas de Servicios");
        System.out.println("0. Salir");
    }

    @Override
    public void finalizar(){
        activo = false;
        sistema.guardarDatosEnArchivos();
        System.out.println("Cerrando el sistema. Hasta pronto!");
    }

    public void opcionesMenuConsola(){}

    
    // Clientes

    private void menuClientes(){
        boolean volver = false;

        while(!volver){
            System.out.println("\n--- GESTION DE CLIENTES ---");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Buscar cliente");
            System.out.println("4. Modificar cliente");
            System.out.println("5. Eliminar cliente");
            System.out.println("0. Volver");

            int opcion = leerEntero("Ingrese opcion: ");

            switch(opcion){
                case 1: registrarCliente(); break;
                case 2: listarClientes(); break;
                case 3: buscarCliente(); break;
                case 4: modificarCliente(); break;
                case 5: eliminarCliente(); break;
                case 0: volver = true; break;
                default: System.out.println("Opcion invalida.");
            }
        }
    }

    private void registrarCliente(){
        String rut = leerTexto("RUT: ");

        if(sistema.HayCliente(rut)){
            System.out.println("Ya existe un cliente con ese RUT.");
            return;
        }

        String nombre = leerTexto("Nombre: ");
        String correo = leerTextoOpcional("Correo (enter para omitir): ");
        String telefono = leerTextoOpcional("Telefono (enter para omitir): ");
        String direccion = leerTextoOpcional("Direccion (enter para omitir): ");

        sistema.registrarCliente(rut, nombre);
        sistema.modificarCliente(rut, nombre, correo, telefono, direccion);

        System.out.println("Cliente registrado con exito.");
    }

    private void listarClientes(){
        List<Cliente> clientes = sistema.listarClientes();

        if(clientes.isEmpty()){
            System.out.println("No hay clientes registrados.");
            return;
        }

        System.out.println("\nRUT | Nombre | Correo | Telefono | Direccion");
        for(Cliente c : clientes){
            System.out.println(c.getRutCliente() + " | " + c.getNombreCliente() + " | " +
                valorOVacio(c.getCorreoCliente()) + " | " + valorOVacio(c.getTelefono()) + " | " +
                valorOVacio(c.getDireccion()));
        }
    }

    private void buscarCliente()
    {
        String rut = leerTexto("RUT a buscar: ");

        try
        {
            Cliente cliente = sistema.buscarCliente(rut);

            System.out.println("Nombre: " + cliente.getNombreCliente());
            System.out.println("Correo: " + valorOVacio(cliente.getCorreoCliente()));
            System.out.println("Telefono: " + valorOVacio(cliente.getTelefono()));
            System.out.println("Direccion: " + valorOVacio(cliente.getDireccion()));
        }
        catch (ClienteNoEncontradoException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void modificarCliente(){
        String rut = leerTexto("RUT del cliente a modificar: ");

        if(!sistema.HayCliente(rut)){
            System.out.println("No existe un cliente con ese RUT.");
            return;
        }

        String nombre = leerTexto("Nuevo nombre: ");
        String correo = leerTextoOpcional("Nuevo correo (enter para omitir): ");
        String telefono = leerTextoOpcional("Nuevo telefono (enter para omitir): ");
        String direccion = leerTextoOpcional("Nueva direccion (enter para omitir): ");

        sistema.modificarCliente(rut, nombre, correo, telefono, direccion);
        System.out.println("Cliente modificado con exito.");
    }

    private void eliminarCliente(){
        String rut = leerTexto("RUT del cliente a eliminar: ");

        if(!sistema.HayCliente(rut)){
            System.out.println("No existe un cliente con ese RUT.");
            return;
        }

        String confirmacion = leerTexto("Esto tambien elimina sus mascotas. Confirmar? (s/n): ");

        if(confirmacion.equalsIgnoreCase("s")){
            sistema.eliminarCliente(rut);
            System.out.println("Cliente eliminado.");
        } else {
            System.out.println("Operacion cancelada.");
        }
    }


    // Mascotas
    private void menuMascotas(){
        boolean volver = false;

        while(!volver){
            System.out.println("\n--- GESTION DE MASCOTAS ---");
            System.out.println("1. Registrar mascota");
            System.out.println("2. Listar todas las mascotas");
            System.out.println("3. Buscar mascota");
            System.out.println("4. Listar mascotas de un cliente");
            System.out.println("5. Modificar mascota");
            System.out.println("6. Eliminar mascota");
            System.out.println("0. Volver");

            int opcion = leerEntero("Ingrese opcion: ");

            switch(opcion){
                case 1: registrarMascota(); break;
                case 2: listarTodasMascotas(); break;
                case 3: buscarMascota(); break;
                case 4: listarMascotasCliente(); break;
                case 5: modificarMascota(); break;
                case 6: eliminarMascota(); break;
                case 0: volver = true; break;
                default: System.out.println("Opcion invalida.");
            }
        }
    }

    private void registrarMascota(){
        String rutDueno = leerTexto("RUT del dueno: ");

        if(!sistema.HayCliente(rutDueno)){
            System.out.println("No existe un cliente con ese RUT. Registrelo primero.");
            return;
        }

        String nombre = leerTexto("Nombre mascota: ");
        String raza = leerTexto("Raza: ");
        int edad = leerEntero("Edad: ");
        float peso = leerFloat("Peso (kg): ");
        float altura = leerFloat("Altura (m): ");
        String trato = leerTextoOpcional("Trato especial (enter para omitir): ");

        if(trato == null){
            sistema.registrarMascota(rutDueno, nombre, raza, edad, peso, altura);
        } else {
            sistema.registrarMascota(rutDueno, nombre, raza, edad, peso, altura, trato);
        }

        System.out.println("Mascota registrada con exito.");
    }

    private void listarTodasMascotas(){
        imprimirMascotas(sistema.listarMascotas());
    }

    private void buscarMascota(){
        String rutDueno = leerTexto("RUT del dueno: ");
        String id = leerTexto("ID de la mascota: ");

        Mascota mascota = sistema.buscarMascota(rutDueno, id);

        if(mascota == null){
            System.out.println("No se encontro esa mascota para ese cliente.");
            return;
        }

        System.out.println("\n--- MASCOTA ENCONTRADA ---");
        System.out.println("ID: " + mascota.getId());
        System.out.println("Dueno: " + mascota.getRutDueno());
        System.out.println("Nombre: " + mascota.getNombre());
        System.out.println("Raza: " + mascota.getRaza());
        System.out.println("Edad: " + mascota.getEdad());
        System.out.println("Peso: " + mascota.getPeso());
        System.out.println("Altura: " + mascota.getAltura());
        System.out.println("Trato especial: " + valorOVacio(mascota.getTratoEspecial()));
    }

    private void listarMascotasCliente(){
        String rutDueno = leerTexto("RUT del cliente: ");

        if(!sistema.HayCliente(rutDueno)){
            System.out.println("No existe un cliente con ese RUT.");
            return;
        }

        imprimirMascotas(sistema.listarMascotasCliente(rutDueno));
    }

    private void imprimirMascotas(List<Mascota> mascotas){
        if(mascotas.isEmpty()){
            System.out.println("No hay mascotas para mostrar.");
            return;
        }

        System.out.println("\nID | Dueno | Nombre | Raza | Edad | Peso | Altura | Trato especial");
        for(Mascota m : mascotas){
            System.out.println(m.getId() + " | " + m.getRutDueno() + " | " + m.getNombre() + " | " +
                m.getRaza() + " | " + m.getEdad() + " | " + m.getPeso() + " | " + m.getAltura() +
                " | " + valorOVacio(m.getTratoEspecial()));
        }
    }

    private void modificarMascota(){
        String rutDueno = leerTexto("RUT del dueno: ");
        String id = leerTexto("ID de la mascota: ");

        Mascota mascota = sistema.buscarMascota(rutDueno, id);

        if(mascota == null){
            System.out.println("No se encontro esa mascota para ese cliente.");
            return;
        }

        String nombre = leerTexto("Nuevo nombre: ");
        String raza = leerTexto("Nueva raza: ");
        int edad = leerEntero("Nueva edad: ");
        float peso = leerFloat("Nuevo peso (kg): ");
        float altura = leerFloat("Nueva altura (m): ");
        String trato = leerTextoOpcional("Nuevo trato especial (enter para omitir): ");

        sistema.modificarMascota(rutDueno, id, nombre, raza, edad, peso, altura, trato);
        System.out.println("Mascota modificada con exito.");
    }

    private void eliminarMascota(){
        String rutDueno = leerTexto("RUT del dueno: ");
        String id = leerTexto("ID de la mascota: ");

        if(sistema.buscarMascota(rutDueno, id) == null){
            System.out.println("No se encontro esa mascota para ese cliente.");
            return;
        }

        sistema.eliminarMascota(rutDueno, id);
        System.out.println("Mascota eliminada.");
    }

    
    // Servicios (reservar / cancelar)
    
    private void menuServicios(){
        boolean volver = false;

        while(!volver){
            System.out.println("\n--- RESERVAS DE SERVICIOS ---");
            System.out.println("1. Registrar servicio de Peluqueria");
            System.out.println("2. Registrar servicio de Veterinaria");
            System.out.println("3. Listar servicios");
            System.out.println("4. Reservar servicio");
            System.out.println("5. Cancelar reserva");
            System.out.println("6. Listar reservas");
            System.out.println("7. Calcular total de reservas activas de un cliente");
            System.out.println("0. Volver");

            int opcion = leerEntero("Ingrese opcion: ");

            switch(opcion){
                case 1: registrarServicioPeluqueria(); break;
                case 2: registrarServicioVeterinaria(); break;
                case 3: listarServicios(); break;
                case 4: reservarServicio(); break;
                case 5: cancelarReserva(); break;
                case 6: listarReservas(); break;
                case 7: calcularTotalReservasActivas(); break;
                case 0: volver = true; break;
                default: System.out.println("Opcion invalida.");
            }
        }
    }

    private void registrarServicioPeluqueria(){
        int codigo = leerEntero("Codigo del servicio: ");

        if(sistema.existeServicio(codigo)){
            System.out.println("Ya existe un servicio con ese codigo.");
            return;
        }

        String descripcion = leerTexto("Descripcion: ");
        float costo = leerFloat("Costo: ");
        String tipoCorte = leerTexto("Tipo de corte: ");
        int duracionCorte = leerEntero("Duracion del corte (minutos): ");

        sistema.registrarServicioPeluqueria(codigo, descripcion, costo, tipoCorte, duracionCorte);
        System.out.println("Servicio de peluqueria registrado con exito.");
    }

    private void registrarServicioVeterinaria(){
        int codigo = leerEntero("Codigo del servicio: ");

        if(sistema.existeServicio(codigo)){
            System.out.println("Ya existe un servicio con ese codigo.");
            return;
        }

        String descripcion = leerTexto("Descripcion: ");
        float costo = leerFloat("Costo: ");
        String nombreVeterinario = leerTexto("Nombre del veterinario: ");
        String especialidad = leerTexto("Especialidad: ");
        String licencia = leerTexto("Licencia: ");

        sistema.registrarServicioVeterinaria(codigo, descripcion, costo, nombreVeterinario, especialidad, licencia);
        System.out.println("Servicio de veterinaria registrado con exito.");
    }

    private void listarServicios(){
        List<Servicio> servicios = sistema.listarServicios();

        if(servicios.isEmpty()){
            System.out.println("No hay servicios registrados.");
            return;
        }

        System.out.println("\n--- Listado de servicios ---");
        for(Servicio s : servicios){
            System.out.println(s);
        }
    }

    private void listarReservas(){
        List<Reserva> reservas = sistema.listarReservas();

        if(reservas.isEmpty()){
            System.out.println("No hay reservas registradas.");
            return;
        }

        System.out.println("\n--- Listado de reservas ---");
        for(Reserva r : reservas){
            System.out.println(r);
        }
    }

    private void reservarServicio(){
        String rutCliente = leerTexto("RUT del cliente: ");

        if(!sistema.HayCliente(rutCliente)){
            System.out.println("No existe un cliente con ese RUT.");
            return;
        }

        String idMascota = leerTexto("ID de la mascota: ");

        if(sistema.buscarMascota(rutCliente, idMascota) == null){
            System.out.println("No se encontro esa mascota para ese cliente.");
            return;
        }

        int codigoServicio = leerEntero("Codigo del servicio: ");

        sistema.reservarServicio(rutCliente, idMascota, codigoServicio);
    }

    private void cancelarReserva(){
        int idReserva = leerEntero("ID de la reserva a cancelar: ");
        sistema.cancelarServicio(idReserva);
        System.out.println("Reserva cancelada (si existia).");
    }

    private void calcularTotalReservasActivas()
    {
        String rut = leerTexto("RUT del cliente: ");

        double total = sistema.calcularTotalReservasActivas(rut);

        System.out.println(
            "Total de reservas activas del cliente: $" +
            String.format("%.0f", total)
        );
    }

    
    // Utilidades de lectura
    
    private String leerTexto(String mensaje){
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private String leerTextoOpcional(String mensaje){
        System.out.print(mensaje);
        String valor = scanner.nextLine().trim();
        return valor.isEmpty() ? null : valor;
    }

    private int leerEntero(String mensaje){
        while(true){
            System.out.print(mensaje);
            try{
                return Integer.parseInt(scanner.nextLine().trim());
            } catch(NumberFormatException e){
                System.out.println("Ingrese un numero entero valido.");
            }
        }
    }

    private float leerFloat(String mensaje){
        while(true){
            System.out.print(mensaje);
            try{
                return Float.parseFloat(scanner.nextLine().trim());
            } catch(NumberFormatException e){
                System.out.println("Ingrese un numero valido.");
            }
        }
    }

    private String valorOVacio(Object valor){
        return valor == null ? "" : String.valueOf(valor);
    }
}