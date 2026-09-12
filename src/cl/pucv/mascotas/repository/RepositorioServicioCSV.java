package cl.pucv.mascotas.repository;

import cl.pucv.mascotas.exception.PersistenciaException;
import cl.pucv.mascotas.model.Peluqueria;
import cl.pucv.mascotas.model.Reserva;
import cl.pucv.mascotas.model.Servicio;
import cl.pucv.mascotas.model.Veterinaria;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class RepositorioServicioCSV
        implements Repositorio<Servicio, Integer> {

    private static final String CABECERA_SERVICIOS =
            "codigo,tipo,descripcion,costo,fecha,subtipo,dato1,dato2,dato3";

    private static final String CABECERA_RESERVAS =
            "idReserva,rutCliente,idMascota,codigoServicio,fechaReserva,estado";

    private final Path rutaArchivo;
    private final Path rutaReservas;

    public RepositorioServicioCSV() {

        this(
                Paths.get("data", "servicios.csv"),
                Paths.get("data", "reservas.csv")
        );
    }

    public RepositorioServicioCSV(
            String rutaServicios,
            String rutaReservas) {

        this(
                Paths.get(rutaServicios),
                Paths.get(rutaReservas)
        );
    }

    private RepositorioServicioCSV(
            Path rutaArchivo,
            Path rutaReservas) {

        this.rutaArchivo = rutaArchivo;
        this.rutaReservas = rutaReservas;
    }

    @Override
    public void guardar(Servicio entidad)
            throws PersistenciaException {

        if (entidad == null) {

            throw new PersistenciaException(
                    "No se puede guardar un servicio nulo."
            );
        }

        List<Servicio> servicios =
                listarTodos();

        boolean actualizado = false;

        for (int i = 0; i < servicios.size(); i++) {

            if (servicios.get(i).getCodigo()
                    == entidad.getCodigo()) {

                servicios.set(i, entidad);

                actualizado = true;

                break;
            }
        }

        if (!actualizado) {

            servicios.add(entidad);
        }

        guardarTodos(servicios);
    }

    @Override
    public void eliminar(Integer codigo)
            throws PersistenciaException {

        List<Servicio> servicios =
                listarTodos();

        servicios.removeIf(
                servicio ->
                servicio.getCodigo() == codigo
        );

        guardarTodos(servicios);
    }

    @Override
    public Servicio buscarPorId(Integer codigo)
            throws PersistenciaException {

        for (Servicio servicio : listarTodos()) {

            if (servicio.getCodigo() == codigo) {

                return servicio;
            }
        }

        return null;
    }

    @Override
    public List<Servicio> listarTodos()
            throws PersistenciaException {

        List<String> lineas =
                CSVUtil.leerLineas(
                        rutaArchivo,
                        CABECERA_SERVICIOS
                );

        List<Servicio> servicios =
                new ArrayList<>();

        for (int i = 1; i < lineas.size(); i++) {

            String linea =
                    lineas.get(i).trim();

            if (linea.isEmpty()) {
                continue;
            }

            List<String> c =
                    CSVUtil.parsearLinea(linea);

            if (c.size() != 9) {

                throw new PersistenciaException(
                        "Registro de servicio invalido en linea "
                        + (i + 1)
                );
            }

            try {

                int codigo =
                        Integer.parseInt(c.get(0));

                String tipo =
                        c.get(1);

                String descripcion =
                        c.get(2);

                double costo =
                        Double.parseDouble(c.get(3));

                LocalDate fecha =
                        LocalDate.parse(c.get(4));

                String subtipo =
                        c.get(5);

                if ("VETERINARIA".equalsIgnoreCase(subtipo)) {

                    Veterinaria veterinaria =
                            new Veterinaria(
                                    codigo,
                                    tipo,
                                    descripcion,
                                    costo,
                                    fecha,
                                    c.get(6),
                                    c.get(7),
                                    c.get(8)
                            );

                    servicios.add(veterinaria);

                } else if ("PELUQUERIA"
                        .equalsIgnoreCase(subtipo)) {

                    Peluqueria peluqueria =
                            new Peluqueria(
                                    codigo,
                                    tipo,
                                    descripcion,
                                    costo,
                                    fecha,
                                    c.get(6),
                                    Integer.parseInt(c.get(7))
                            );

                    servicios.add(peluqueria);

                } else {

                    throw new PersistenciaException(
                            "Subtipo de servicio desconocido: "
                            + subtipo
                    );
                }

            } catch (NumberFormatException
                    | DateTimeParseException e) {

                throw new PersistenciaException(
                        "Datos invalidos en servicios.csv, linea "
                        + (i + 1),
                        e
                );
            }
        }

        return servicios;
    }

    public void guardarTodos(List<Servicio> servicios)
            throws PersistenciaException {

        List<String> lineas =
                new ArrayList<>();

        for (Servicio servicio : servicios) {

            String subtipo;

            String dato1 = "";
            String dato2 = "";
            String dato3 = "";

            if (servicio instanceof Veterinaria) {

                Veterinaria veterinaria =
                        (Veterinaria) servicio;

                subtipo = "VETERINARIA";

                dato1 =
                        veterinaria.getNombreVeterinario();

                dato2 =
                        veterinaria.getEspecialidad();

                dato3 =
                        veterinaria.getLicencia();

            } else if (servicio instanceof Peluqueria) {

                Peluqueria peluqueria =
                        (Peluqueria) servicio;

                subtipo = "PELUQUERIA";

                dato1 =
                        peluqueria.getTipoCorte();

                dato2 =
                        String.valueOf(
                                peluqueria.getDuracionCorte()
                        );

            } else {

                throw new PersistenciaException(
                        "Tipo de servicio no soportado."
                );
            }

            lineas.add(
                    CSVUtil.crearLinea(
                            String.valueOf(
                                    servicio.getCodigo()
                            ),
                            servicio.getTipo(),
                            servicio.getDescripcion(),
                            String.valueOf(
                                    servicio.getCosto()
                            ),
                            servicio.fecha().toString(),
                            subtipo,
                            dato1,
                            dato2,
                            dato3
                    )
            );
        }

        CSVUtil.escribirLineas(
                rutaArchivo,
                CABECERA_SERVICIOS,
                lineas
        );
    }

    public void guardarReserva(Reserva reserva)
            throws PersistenciaException {

        if (reserva == null) {

            throw new PersistenciaException(
                    "No se puede guardar una reserva nula."
            );
        }

        List<Reserva> reservas =
                listarReservas();

        boolean actualizada = false;

        for (int i = 0; i < reservas.size(); i++) {

            if (reservas.get(i).getIdReserva()
                    == reserva.getIdReserva()) {

                reservas.set(i, reserva);

                actualizada = true;

                break;
            }
        }

        if (!actualizada) {

            reservas.add(reserva);
        }

        guardarReservas(reservas);
    }

    public void eliminarReserva(int idReserva)
            throws PersistenciaException {

        List<Reserva> reservas =
                listarReservas();

        reservas.removeIf(
                reserva ->
                reserva.getIdReserva() == idReserva
        );

        guardarReservas(reservas);
    }

    public Reserva buscarReservaPorId(int idReserva)
            throws PersistenciaException {

        for (Reserva reserva : listarReservas()) {

            if (reserva.getIdReserva()
                    == idReserva) {

                return reserva;
            }
        }

        return null;
    }

    public List<Reserva> listarReservas()
            throws PersistenciaException {

        List<String> lineas =
                CSVUtil.leerLineas(
                        rutaReservas,
                        CABECERA_RESERVAS
                );

        List<Reserva> reservas =
                new ArrayList<>();

        for (int i = 1; i < lineas.size(); i++) {

            String linea =
                    lineas.get(i).trim();

            if (linea.isEmpty()) {
                continue;
            }

            List<String> c =
                    CSVUtil.parsearLinea(linea);

            if (c.size() != 6) {

                throw new PersistenciaException(
                        "Registro de reserva invalido en linea "
                        + (i + 1)
                );
            }

            try {

                Reserva reserva =
                        new Reserva(
                                Integer.parseInt(c.get(0)),
                                c.get(1),
                                c.get(2),
                                Integer.parseInt(c.get(3)),
                                LocalDate.parse(c.get(4)),
                                c.get(5)
                        );

                reservas.add(reserva);

            } catch (NumberFormatException
                    | DateTimeParseException e) {

                throw new PersistenciaException(
                        "Datos invalidos en reservas.csv, linea "
                        + (i + 1),
                        e
                );
            }
        }

        return reservas;
    }

    public void guardarReservas(
            List<Reserva> reservas)
            throws PersistenciaException {

        List<String> lineas =
                new ArrayList<>();

        for (Reserva reserva : reservas) {

            lineas.add(
                    CSVUtil.crearLinea(
                            String.valueOf(
                                    reserva.getIdReserva()
                            ),
                            reserva.getRutCliente(),
                            reserva.getIdMascota(),
                            String.valueOf(
                                    reserva.getCodigoServicio()
                            ),
                            reserva.getFechaReserva()
                                    .toString(),
                            reserva.getEstado()
                    )
            );
        }

        CSVUtil.escribirLineas(
                rutaReservas,
                CABECERA_RESERVAS,
                lineas
        );
    }
}