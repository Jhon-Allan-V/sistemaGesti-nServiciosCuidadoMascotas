package cl.pucv.mascotas.ui;

import cl.pucv.mascotas.facade.SistemaFacade;
import cl.pucv.mascotas.model.Cliente;
import cl.pucv.mascotas.model.Mascota;
import cl.pucv.mascotas.model.Servicio;
import cl.pucv.mascotas.model.Reserva;

import javax.swing.*;
import java.awt.*;

import cl.pucv.mascotas.exception.ClienteNoEncontradoException;

/*
Funcion u objetivo: gestionar la pantalla grafica en el que el usuario usara el programa
*/

public class PantallaInterfaz implements InterfazUsuario{
    
    private SistemaFacade sistema;
    private JFrame ventana;

    public PantallaInterfaz(SistemaFacade sistema){
        this.sistema = sistema;
    }

    @Override
    public void iniciar(){
        mostrarMenu();
    }

    @Override
    public void mostrarMenu(){
        ventana = new JFrame("Sistema de Gestión de Mascotas");

        ventana.setSize(500, 400);
        ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ventana.setLocationRelativeTo(null);

        ventana.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                finalizar();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        JLabel titulo = new JLabel(
            "SISTEMA DE GESTIÓN DE MASCOTAS",
            SwingConstants.CENTER
        );

        JButton botonClientes = new JButton("Gestión de Clientes");
        JButton botonMascotas = new JButton("Gestión de Mascotas");
        JButton botonServicios = new JButton("Gestión de Servicios");
        JButton botonSalir = new JButton("Salir");

        botonClientes.addActionListener(e -> ventanaClientes());
        botonMascotas.addActionListener(e -> ventanaMascotas());
        botonServicios.addActionListener(e -> ventanaServicios());
        botonSalir.addActionListener(e -> finalizar());
        

        panel.add(titulo);
        panel.add(botonClientes);
        panel.add(botonMascotas);
        panel.add(botonServicios);
        panel.add(botonSalir);

        ventana.add(panel);

        ventana.setVisible(true);
    }

    //Clientes
    private void ventanaClientes() {

        JFrame ventanaClientes = new JFrame("Gestión de Clientes");

        ventanaClientes.setSize(400, 400);
        ventanaClientes.setLocationRelativeTo(ventana);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));

        JButton botonAgregar = new JButton("Agregar cliente");
        JButton botonListar = new JButton("Listar clientes");
        JButton botonBuscar = new JButton("Buscar cliente");
        JButton botonModificar = new JButton("Modificar cliente");
        JButton botonEliminar = new JButton("Eliminar cliente");
        JButton botonVolver = new JButton("Volver");

        
        panel.add(botonAgregar);
        panel.add(botonListar);
        panel.add(botonBuscar);
        panel.add(botonModificar);
        panel.add(botonEliminar);
        panel.add(botonVolver);

        botonAgregar.addActionListener(e -> agregarCliente());
        botonListar.addActionListener(e -> listarClientes());
        botonBuscar.addActionListener(e -> buscarCliente());
        botonModificar.addActionListener(e -> modificarCliente());
        botonEliminar.addActionListener(e -> eliminarCliente());
        botonVolver.addActionListener(e -> ventanaClientes.dispose());

        ventanaClientes.add(panel);
        ventanaClientes.setVisible(true);
    }

    private void agregarCliente() {

        String rut = JOptionPane.showInputDialog(
            ventana,
            "Ingrese el RUT del cliente:"
        );

        if (rut == null || rut.trim().isEmpty()) {
            return;
        }

        String nombre = JOptionPane.showInputDialog(
            ventana,
            "Ingrese el nombre del cliente:"
        );

        if (nombre == null || nombre.trim().isEmpty()) {
            return;
        }

        try {

            sistema.registrarCliente(rut, nombre);

            JOptionPane.showMessageDialog(
                ventana,
                "Cliente registrado correctamente."
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                ventana,
                "No se pudo registrar el cliente: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void listarClientes() {

        java.util.List<Cliente> clientes = sistema.listarClientes();

        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(
                ventana,
                "No hay clientes registrados.",
                "Lista de clientes",
                JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        StringBuilder texto = new StringBuilder();

        for (Cliente cliente : clientes) {
            texto.append(cliente.toString()).append("\n");
        }

        JOptionPane.showMessageDialog(
            ventana,
            texto.toString(),
            "Lista de clientes",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void buscarCliente() {
        String rut = JOptionPane.showInputDialog(
            ventana,
            "Ingrese el RUT del cliente:"
        );

        if (rut == null || rut.trim().isEmpty()) {
            return;
        }

        try {
            Cliente cliente = sistema.buscarCliente(rut);

            JOptionPane.showMessageDialog(
                ventana,
                cliente.toString(),
                "Cliente encontrado",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (ClienteNoEncontradoException ex) {
            JOptionPane.showMessageDialog(
                ventana,
                ex.getMessage(),
                "Cliente no encontrado",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void modificarCliente() {
        String rut = JOptionPane.showInputDialog(
            ventana,
            "Ingrese el RUT del cliente que desea modificar:"
        );

        if (rut == null || rut.trim().isEmpty()) {
            return;
        }

        try {
            Cliente cliente = sistema.buscarCliente(rut);


            String nombre = JOptionPane.showInputDialog(
                ventana,
                "Ingrese el nuevo nombre:",
                cliente.getNombreCliente()
            );

            if (nombre == null) return;

            String correo = JOptionPane.showInputDialog(
                ventana,
                "Ingrese el nuevo correo:",
                cliente.getCorreoCliente()
            );

            if (correo == null) return;

            String telefono = JOptionPane.showInputDialog(
                ventana,
                "Ingrese el nuevo teléfono:",
                cliente.getTelefono()
            );

            if (telefono == null) return;

            String direccion = JOptionPane.showInputDialog(
                ventana,
                "Ingrese la nueva dirección:",
                cliente.getDireccion()
            );

            if (direccion == null) return;

            sistema.modificarCliente(
                rut,
                nombre,
                correo,
                telefono,
                direccion
            );

            JOptionPane.showMessageDialog(
                ventana,
                "Cliente modificado correctamente."
            );

        } catch (ClienteNoEncontradoException ex) {
            JOptionPane.showMessageDialog(
                ventana,
                ex.getMessage(),
                "Cliente no encontrado",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void eliminarCliente() {
        String rut = JOptionPane.showInputDialog(
            ventana,
            "Ingrese el RUT del cliente que desea eliminar:"
        );

        if (rut == null || rut.trim().isEmpty()) {
            return;
        }

        try {
            Cliente cliente = sistema.buscarCliente(rut);


            int confirmacion = JOptionPane.showConfirmDialog(
                ventana,
                "¿Está seguro de eliminar al cliente " + cliente.getNombreCliente() + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
            );

            if (confirmacion != JOptionPane.YES_OPTION) {
                return;
            }

            sistema.eliminarCliente(rut);

            JOptionPane.showMessageDialog(
                ventana,
                "Cliente eliminado correctamente."
            );

        } catch (ClienteNoEncontradoException ex) {
            JOptionPane.showMessageDialog(
                ventana,
                ex.getMessage(),
                "Cliente no encontrado",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }

    //Mascotas
    private void ventanaMascotas() {

        JFrame ventanaMascotas = new JFrame("Gestión de Mascotas");

        ventanaMascotas.setSize(400, 400);
        ventanaMascotas.setLocationRelativeTo(ventana);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 10, 10));

        JButton botonAgregar = new JButton("Agregar mascota");
        JButton botonListar = new JButton("Listar mascotas");
        JButton botonListarCliente = new JButton("Listar mascotas de un cliente");
        JButton botonBuscar = new JButton("Buscar mascota");
        JButton botonModificar = new JButton("Modificar mascota");
        JButton botonEliminar = new JButton("Eliminar mascota");
        JButton botonVolver = new JButton("Volver");

        botonAgregar.addActionListener(e -> agregarMascota());
        botonListar.addActionListener(e -> listarMascotas());
        botonListarCliente.addActionListener(e -> listarMascotasCliente());
        botonBuscar.addActionListener(e -> buscarMascota());
        botonModificar.addActionListener(e -> modificarMascota());
        botonEliminar.addActionListener(e -> eliminarMascota());
        botonVolver.addActionListener(e -> ventanaMascotas.dispose());

        panel.add(botonAgregar);
        panel.add(botonListar);
        panel.add(botonListarCliente);
        panel.add(botonBuscar);
        panel.add(botonModificar);
        panel.add(botonEliminar);
        panel.add(botonVolver);

        ventanaMascotas.add(panel);
        ventanaMascotas.setVisible(true);
    }

    private void agregarMascota() {

        String rutDueno = JOptionPane.showInputDialog(
                ventana,
                "Ingrese el RUT del dueño:"
        );

        if (rutDueno == null) {
            return;
        }

        // Verificamos que el cliente exista
        if (!sistema.HayCliente(rutDueno)) {
            JOptionPane.showMessageDialog(
                    ventana,
                    "No existe un cliente con ese RUT."
            );
            return;
        }

        String nombre = JOptionPane.showInputDialog(
                ventana,
                "Ingrese el nombre de la mascota:"
        );

        if (nombre == null) {
            return;
        }

        String raza = JOptionPane.showInputDialog(
                ventana,
                "Ingrese la raza:"
        );

        if (raza == null) {
            return;
        }

        String edadTexto = JOptionPane.showInputDialog(
                ventana,
                "Ingrese la edad:"
        );

        if (edadTexto == null) {
            return;
        }

        String pesoTexto = JOptionPane.showInputDialog(
                ventana,
                "Ingrese el peso:"
        );

        if (pesoTexto == null) {
            return;
        }

        String alturaTexto = JOptionPane.showInputDialog(
                ventana,
                "Ingrese la altura:"
        );

        if (alturaTexto == null) {
            return;
        }

        String tratoEspecial = JOptionPane.showInputDialog(
                ventana,
                "Ingrese el trato especial (deje vacío si no tiene):"
        );

        if (tratoEspecial == null) {
            return;
        }

        try {

            int edad = Integer.parseInt(edadTexto);
            float peso = Float.parseFloat(pesoTexto);
            float altura = Float.parseFloat(alturaTexto);

            if (tratoEspecial.trim().isEmpty()) {

                sistema.registrarMascota(
                        rutDueno,
                        nombre,
                        raza,
                        edad,
                        peso,
                        altura
                );

            } else {

                sistema.registrarMascota(
                        rutDueno,
                        nombre,
                        raza,
                        edad,
                        peso,
                        altura,
                        tratoEspecial
                );
            }

            JOptionPane.showMessageDialog(
                    ventana,
                    "Mascota agregada correctamente."
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    ventana,
                    "Edad, peso y altura deben ser valores numéricos."
            );
        }
    }

    private void listarMascotas() {

        java.util.List<Mascota> mascotas = sistema.listarMascotas();

        if (mascotas.isEmpty()) {
            JOptionPane.showMessageDialog(
                ventana,
                "No hay mascotas registradas.",
                "Lista de mascotas",
                JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        StringBuilder texto = new StringBuilder();

        for (Mascota mascota : mascotas) {
            texto.append(mascota.toString()).append("\n");
        }

        JOptionPane.showMessageDialog(
            ventana,
            texto.toString(),
            "Lista de mascotas",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void listarMascotasCliente() {

        String rutCliente = JOptionPane.showInputDialog(
                ventana,
                "Ingrese el RUT del cliente:"
        );

        if (rutCliente == null || rutCliente.trim().isEmpty()) {
            return;
        }

        if (!sistema.HayCliente(rutCliente)) {
            JOptionPane.showMessageDialog(
                    ventana,
                    "No existe un cliente con ese RUT.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        java.util.List<Mascota> mascotas =
                sistema.listarMascotasCliente(rutCliente);

        if (mascotas.isEmpty()) {
            JOptionPane.showMessageDialog(
                    ventana,
                    "El cliente no tiene mascotas registradas.",
                    "Mascotas del cliente",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        StringBuilder texto = new StringBuilder();

        for (Mascota mascota : mascotas) {
            texto.append(mascota.toString()).append("\n");
        }

        JOptionPane.showMessageDialog(
                ventana,
                texto.toString(),
                "Mascotas del cliente",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void buscarMascota() {

        String rutDueno = JOptionPane.showInputDialog(
            ventana,
            "Ingrese el RUT del dueño:"
        );

        if (rutDueno == null || rutDueno.trim().isEmpty()) {
            return;
        }

        if (!sistema.HayCliente(rutDueno)) {
            JOptionPane.showMessageDialog(
                ventana,
                "No existe un cliente con ese RUT.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        String id = JOptionPane.showInputDialog(
            ventana,
            "Ingrese el ID de la mascota:"
        );

        if (id == null || id.trim().isEmpty()) {
            return;
        }

        try {

            Mascota mascota = sistema.buscarMascota(rutDueno, id);

            if (mascota == null) {
                JOptionPane.showMessageDialog(
                    ventana,
                    "No se encontró una mascota con ese ID.",
                    "Mascota no encontrada",
                    JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            JOptionPane.showMessageDialog(
                ventana,
                mascota.toString(),
                "Mascota encontrada",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                ventana,
                "Error al buscar la mascota: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void modificarMascota() {

        String rutDueno = JOptionPane.showInputDialog(
            ventana,
            "Ingrese el RUT del dueño:"
        );

        if (rutDueno == null || rutDueno.trim().isEmpty()) {
            return;
        }

        if (!sistema.HayCliente(rutDueno)) {
            JOptionPane.showMessageDialog(
                ventana,
                "No existe un cliente con ese RUT.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        String id = JOptionPane.showInputDialog(
            ventana,
            "Ingrese el ID de la mascota:"
        );

        if (id == null || id.trim().isEmpty()) {
            return;
        }

        try {

            Mascota mascota = sistema.buscarMascota(rutDueno, id);

            if (mascota == null) {
                JOptionPane.showMessageDialog(
                    ventana,
                    "No se encontró una mascota con ese ID.",
                    "Mascota no encontrada",
                    JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            String nombre = JOptionPane.showInputDialog(
                ventana,
                "Ingrese el nuevo nombre:",
                mascota.getNombre()
            );

            if (nombre == null) return;

            String raza = JOptionPane.showInputDialog(
                ventana,
                "Ingrese la nueva raza:",
                mascota.getRaza()
            );

            if (raza == null) return;

            String edadTexto = JOptionPane.showInputDialog(
                ventana,
                "Ingrese la nueva edad:",
                mascota.getEdad()
            );

            if (edadTexto == null) return;

            String pesoTexto = JOptionPane.showInputDialog(
                ventana,
                "Ingrese el nuevo peso:",
                mascota.getPeso()
            );

            if (pesoTexto == null) return;

            String alturaTexto = JOptionPane.showInputDialog(
                ventana,
                "Ingrese la nueva altura:",
                mascota.getAltura()
            );

            if (alturaTexto == null) return;

            String tratoEspecial = JOptionPane.showInputDialog(
                ventana,
                "Ingrese el nuevo trato especial:",
                mascota.getTratoEspecial()
            );

            if (tratoEspecial == null) return;

            int edad = Integer.parseInt(edadTexto);
            float peso = Float.parseFloat(pesoTexto);
            float altura = Float.parseFloat(alturaTexto);

            sistema.modificarMascota(
                rutDueno,
                id,
                nombre,
                raza,
                edad,
                peso,
                altura,
                tratoEspecial
            );

            JOptionPane.showMessageDialog(
                ventana,
                "Mascota modificada correctamente."
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                ventana,
                "Edad, peso y altura deben ser valores numéricos.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                ventana,
                "No se pudo modificar la mascota: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void eliminarMascota() {

        String rutDueno = JOptionPane.showInputDialog(
            ventana,
            "Ingrese el RUT del dueño:"
        );

        if (rutDueno == null || rutDueno.trim().isEmpty()) {
            return;
        }

        if (!sistema.HayCliente(rutDueno)) {
            JOptionPane.showMessageDialog(
                ventana,
                "No existe un cliente con ese RUT.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        String id = JOptionPane.showInputDialog(
            ventana,
            "Ingrese el ID de la mascota:"
        );

        if (id == null || id.trim().isEmpty()) {
            return;
        }

        try {

            Mascota mascota = sistema.buscarMascota(rutDueno, id);

            if (mascota == null) {
                JOptionPane.showMessageDialog(
                    ventana,
                    "No se encontró una mascota con ese ID.",
                    "Mascota no encontrada",
                    JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(
                ventana,
                "¿Está seguro de eliminar a " + mascota.getNombre() + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
            );

            if (confirmacion != JOptionPane.YES_OPTION) {
                return;
            }

            sistema.eliminarMascota(rutDueno, id);

            JOptionPane.showMessageDialog(
                ventana,
                "Mascota eliminada correctamente."
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                ventana,
                "No se pudo eliminar la mascota: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    //Servicios
    private void ventanaServicios() {
        JFrame ventanaServicios = new JFrame("Gestión de Servicios");
        ventanaServicios.setSize(400, 450);
        ventanaServicios.setLocationRelativeTo(ventana);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 1, 10, 10));

        JButton botonPeluqueria = new JButton("Registrar servicio de Peluquería");
        JButton botonVeterinaria = new JButton("Registrar servicio de Veterinaria");
        JButton botonListarServicios = new JButton("Listar servicios");
        JButton botonReservar = new JButton("Reservar servicio");
        JButton botonCancelar = new JButton("Cancelar reserva");
        JButton botonListarReservas = new JButton("Listar reservas");
        JButton botonTotalReservas = new JButton("Calcular total de reservas activas");
        JButton botonVolver = new JButton("Volver");

        botonPeluqueria.addActionListener(e -> registrarServicioPeluqueria());
        botonVeterinaria.addActionListener(e -> registrarServicioVeterinaria());
        botonListarServicios.addActionListener(e -> listarServicios());
        botonReservar.addActionListener(e -> reservarServicio());
        botonCancelar.addActionListener(e -> cancelarReserva());
        botonListarReservas.addActionListener(e -> listarReservas());
        botonTotalReservas.addActionListener(e -> calcularTotalReservasActivas());
        botonVolver.addActionListener(e -> ventanaServicios.dispose());

        panel.add(botonPeluqueria);
        panel.add(botonVeterinaria);
        panel.add(botonListarServicios);
        panel.add(botonReservar);
        panel.add(botonCancelar);
        panel.add(botonListarReservas);
        panel.add(botonTotalReservas);
        panel.add(botonVolver);

        ventanaServicios.add(panel);
        ventanaServicios.setVisible(true);
    }

    private void registrarServicioPeluqueria() {
        try {
            String codigoTexto = JOptionPane.showInputDialog(
                ventana,
                "Código del servicio:"
            );

            if (codigoTexto == null || codigoTexto.trim().isEmpty()) return;

            int codigo = Integer.parseInt(codigoTexto);

            if (sistema.existeServicio(codigo)) {
                JOptionPane.showMessageDialog(
                    ventana,
                    "Ya existe un servicio con ese código.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            String descripcion = JOptionPane.showInputDialog(
                ventana,
                "Descripción del servicio:"
            );

            if (descripcion == null || descripcion.trim().isEmpty()) return;

            String costoTexto = JOptionPane.showInputDialog(
                ventana,
                "Costo del servicio:"
            );

            if (costoTexto == null || costoTexto.trim().isEmpty()) return;

            double costo = Double.parseDouble(costoTexto);

            String tipoCorte = JOptionPane.showInputDialog(
                ventana,
                "Tipo de corte:"
            );

            if (tipoCorte == null || tipoCorte.trim().isEmpty()) return;

            String duracionTexto = JOptionPane.showInputDialog(
                ventana,
                "Duración del corte en minutos:"
            );

            if (duracionTexto == null || duracionTexto.trim().isEmpty()) return;

            int duracion = Integer.parseInt(duracionTexto);

            sistema.registrarServicioPeluqueria(
                codigo,
                descripcion,
                costo,
                tipoCorte,
                duracion
            );

            JOptionPane.showMessageDialog(
                ventana,
                "Servicio de peluquería registrado correctamente."
            );

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                ventana,
                "Código, costo y duración deben ser valores numéricos.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void registrarServicioVeterinaria() {
        try {
            String codigoTexto = JOptionPane.showInputDialog(
                ventana,
                "Código del servicio:"
            );

            if (codigoTexto == null || codigoTexto.trim().isEmpty()) return;

            int codigo = Integer.parseInt(codigoTexto);

            if (sistema.existeServicio(codigo)) {
                JOptionPane.showMessageDialog(
                    ventana,
                    "Ya existe un servicio con ese código.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            String descripcion = JOptionPane.showInputDialog(
                ventana,
                "Descripción del servicio:"
            );

            if (descripcion == null || descripcion.trim().isEmpty()) return;

            String costoTexto = JOptionPane.showInputDialog(
                ventana,
                "Costo del servicio:"
            );

            if (costoTexto == null || costoTexto.trim().isEmpty()) return;

            double costo = Double.parseDouble(costoTexto);

            String nombreVeterinario = JOptionPane.showInputDialog(
                ventana,
                "Nombre del veterinario:"
            );

            if (nombreVeterinario == null || nombreVeterinario.trim().isEmpty()) return;

            String especialidad = JOptionPane.showInputDialog(
                ventana,
                "Especialidad:"
            );

            if (especialidad == null || especialidad.trim().isEmpty()) return;

            String licencia = JOptionPane.showInputDialog(
                ventana,
                "Licencia:"
            );

            if (licencia == null || licencia.trim().isEmpty()) return;

            sistema.registrarServicioVeterinaria(
                codigo,
                descripcion,
                costo,
                nombreVeterinario,
                especialidad,
                licencia
            );

            JOptionPane.showMessageDialog(
                ventana,
                "Servicio veterinario registrado correctamente."
            );

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                ventana,
                "Código y costo deben ser valores numéricos.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void listarServicios() {
        StringBuilder texto = new StringBuilder();

        for (Servicio servicio : sistema.listarServicios()) {
            texto.append(servicio.toString()).append("\n");
        }

        if (texto.length() == 0) {
            texto.append("No hay servicios registrados.");
        }

        JTextArea area = new JTextArea(texto.toString());
        area.setEditable(false);

        JOptionPane.showMessageDialog(
            ventana,
            new JScrollPane(area),
            "Listado de Servicios",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void listarReservas() {
        StringBuilder texto = new StringBuilder();

        for (Reserva reserva : sistema.listarReservas()) {
            texto.append(reserva.toString()).append("\n");
        }

        if (texto.length() == 0) {
            texto.append("No hay reservas registradas.");
        }

        JTextArea area = new JTextArea(texto.toString());
        area.setEditable(false);

        JOptionPane.showMessageDialog(
            ventana,
            new JScrollPane(area),
            "Listado de Reservas",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    private void cancelarReserva() {
        String idTexto = JOptionPane.showInputDialog(
            ventana,
            "Ingrese el ID de la reserva:"
        );

        if (idTexto == null || idTexto.trim().isEmpty()) return;

        try {
            int idReserva = Integer.parseInt(idTexto);

            sistema.cancelarServicio(idReserva);

            JOptionPane.showMessageDialog(
                ventana,
                "Reserva cancelada correctamente."
            );

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                ventana,
                "El ID de la reserva debe ser un número.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                ventana,
                "No se pudo cancelar la reserva: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }


    private void reservarServicio() {
        String rutCliente = JOptionPane.showInputDialog(
            ventana,
            "Ingrese el RUT del cliente:"
        );

        if (rutCliente == null || rutCliente.trim().isEmpty()) return;

        if (!sistema.HayCliente(rutCliente)) {
            JOptionPane.showMessageDialog(
                ventana,
                "No existe un cliente con ese RUT.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        String idMascota = JOptionPane.showInputDialog(
            ventana,
            "Ingrese el ID de la mascota:"
        );

        if (idMascota == null || idMascota.trim().isEmpty()) return;

        Mascota mascota = sistema.buscarMascota(rutCliente, idMascota);

        if (mascota == null) {
            JOptionPane.showMessageDialog(
                ventana,
                "No existe una mascota con ese ID para este cliente.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        String codigoTexto = JOptionPane.showInputDialog(
            ventana,
            "Ingrese el código del servicio:"
        );

        if (codigoTexto == null || codigoTexto.trim().isEmpty()) return;

        try {
            int codigoServicio = Integer.parseInt(codigoTexto);

            sistema.reservarServicio(
                rutCliente,
                idMascota,
                codigoServicio
            );

            JOptionPane.showMessageDialog(
                ventana,
                "Servicio reservado correctamente."
            );

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                ventana,
                "El código del servicio debe ser un número.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                ventana,
                "No se pudo realizar la reserva: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void calcularTotalReservasActivas()
    {
        String rut = JOptionPane.showInputDialog(
            ventana,
            "RUT del cliente:"
        );

        if (rut == null || rut.trim().isEmpty())
        {
            return;
        }

        double total = sistema.calcularTotalReservasActivas(rut);

        JOptionPane.showMessageDialog(
            ventana,
            "Total de reservas activas: $" +
            String.format("%.0f", total),
            "Total de reservas",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    @Override 
    public void finalizar(){
        sistema.guardarDatosEnArchivos();
        JOptionPane.showMessageDialog(ventana, "Datos guardados. Cerrando el sistema. Hasta pronto!");
        System.exit(0);
    }

    public void opcionesInterfazGrafica(){}

}
