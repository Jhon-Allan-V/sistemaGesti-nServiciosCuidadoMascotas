package cl.pucv.mascotas.ui;

import cl.pucv.mascotas.facade.SistemaFacade;
import cl.pucv.mascotas.model.Cliente;
import cl.pucv.mascotas.model.Mascota;

import javax.swing.*;
import java.awt.*;

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
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);

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
        //botonServicios.addActionListener(e -> ventanaServicios());
        

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

            if (cliente == null) {
                JOptionPane.showMessageDialog(
                    ventana,
                    "No se encontró un cliente con ese RUT.",
                    "Cliente no encontrado",
                    JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            JOptionPane.showMessageDialog(
                ventana,
                cliente.toString(),
                "Cliente encontrado",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                ventana,
                "Error al buscar el cliente: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
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

            if (cliente == null) {
                JOptionPane.showMessageDialog(
                    ventana,
                    "No se encontró un cliente con ese RUT.",
                    "Cliente no encontrado",
                    JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

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

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                ventana,
                "No se pudo modificar el cliente: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
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

            if (cliente == null) {
                JOptionPane.showMessageDialog(
                    ventana,
                    "No se encontró un cliente con ese RUT.",
                    "Cliente no encontrado",
                    JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

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

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                ventana,
                "No se pudo eliminar el cliente: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    //Mascotas
    private void ventanaMascotas() {

        JFrame ventanaMascotas = new JFrame("Gestión de Mascotas");

        ventanaMascotas.setSize(400, 400);
        ventanaMascotas.setLocationRelativeTo(ventana);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));

        JButton botonAgregar = new JButton("Agregar mascota");
        JButton botonListar = new JButton("Listar mascotas");
        JButton botonBuscar = new JButton("Buscar mascota");
        JButton botonModificar = new JButton("Modificar mascota");
        JButton botonEliminar = new JButton("Eliminar mascota");
        JButton botonVolver = new JButton("Volver");

        botonAgregar.addActionListener(e -> agregarMascota());
        botonListar.addActionListener(e -> listarMascotas());
        botonBuscar.addActionListener(e -> buscarMascota());
        botonModificar.addActionListener(e -> modificarMascota());
        botonEliminar.addActionListener(e -> eliminarMascota());
        botonVolver.addActionListener(e -> ventanaMascotas.dispose());

        panel.add(botonAgregar);
        panel.add(botonListar);
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

    @Override 
    public void finalizar(){}

    public void opcionesInterfazGrafica(){}

}
