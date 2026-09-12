package cl.pucv.mascotas.repository;

import cl.pucv.mascotas.exception.PersistenciaException;
import cl.pucv.mascotas.model.Mascota;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RepositorioMascotaCSV
        implements Repositorio<Mascota, String> {

    private static final String CABECERA =
            "id,rutDueno,nombre,raza,edad,peso,altura,tratoEspecial";

    private final Path rutaArchivo;

    public RepositorioMascotaCSV() {

        this(Paths.get("data", "mascotas.csv"));
    }

    public RepositorioMascotaCSV(String rutaArchivo) {

        this(Paths.get(rutaArchivo));
    }

    private RepositorioMascotaCSV(Path rutaArchivo) {

        this.rutaArchivo = rutaArchivo;
    }

    @Override
    public void guardar(Mascota entidad)
            throws PersistenciaException {

        if (entidad == null) {

            throw new PersistenciaException(
                    "No se puede guardar una mascota nula."
            );
        }

        List<Mascota> mascotas =
                listarTodos();

        boolean actualizada = false;

        for (int i = 0; i < mascotas.size(); i++) {

            if (mascotas.get(i)
                    .getId()
                    .equals(entidad.getId())) {

                mascotas.set(i, entidad);

                actualizada = true;

                break;
            }
        }

        if (!actualizada) {

            mascotas.add(entidad);
        }

        guardarTodos(mascotas);
    }

    @Override
    public void eliminar(String id)
            throws PersistenciaException {

        List<Mascota> mascotas =
                listarTodos();

        mascotas.removeIf(
                mascota ->
                mascota.getId().equals(id)
        );

        guardarTodos(mascotas);
    }

    @Override
    public Mascota buscarPorId(String id)
            throws PersistenciaException {

        for (Mascota mascota : listarTodos()) {

            if (mascota.getId().equals(id)) {

                return mascota;
            }
        }

        return null;
    }

    @Override
    public List<Mascota> listarTodos()
            throws PersistenciaException {

        List<String> lineas =
                CSVUtil.leerLineas(
                        rutaArchivo,
                        CABECERA
                );

        List<Mascota> mascotas =
                new ArrayList<>();

        for (int i = 1; i < lineas.size(); i++) {

            String linea =
                    lineas.get(i).trim();

            if (linea.isEmpty()) {
                continue;
            }

            List<String> c =
                    CSVUtil.parsearLinea(linea);

            if (c.size() != 8) {

                throw new PersistenciaException(
                        "Registro de mascota invalido en linea "
                        + (i + 1)
                );
            }

            try {

                Mascota mascota =
                        new Mascota(
                                c.get(0),
                                c.get(1),
                                c.get(2),
                                c.get(3),
                                Integer.parseInt(c.get(4)),
                                Float.parseFloat(c.get(5)),
                                Float.parseFloat(c.get(6)),
                                CSVUtil.nuloSiVacio(c.get(7))
                        );

                mascotas.add(mascota);

            } catch (NumberFormatException e) {

                throw new PersistenciaException(
                        "Datos numericos invalidos en mascotas.csv, linea "
                        + (i + 1),
                        e
                );
            }
        }

        return mascotas;
    }

    public void guardarTodos(List<Mascota> mascotas)
            throws PersistenciaException {

        List<String> lineas =
                new ArrayList<>();

        for (Mascota mascota : mascotas) {

            lineas.add(
                    CSVUtil.crearLinea(
                            mascota.getId(),
                            mascota.getRutDueno(),
                            mascota.getNombre(),
                            mascota.getRaza(),
                            String.valueOf(mascota.getEdad()),
                            String.valueOf(mascota.getPeso()),
                            String.valueOf(mascota.getAltura()),
                            mascota.getTratoEspecial()
                    )
            );
        }

        CSVUtil.escribirLineas(
                rutaArchivo,
                CABECERA,
                lineas
        );
    }
}