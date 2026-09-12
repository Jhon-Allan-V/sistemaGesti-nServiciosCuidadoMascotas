package cl.pucv.mascotas.repository;

import cl.pucv.mascotas.exception.PersistenciaException;
import cl.pucv.mascotas.model.Cliente;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RepositorioClienteCSV
        implements Repositorio<Cliente, String> {

    private static final String CABECERA =
            "rut,nombre,correo,telefono,direccion";

    private final Path rutaArchivo;

    public RepositorioClienteCSV() {

        this(Paths.get("data", "clientes.csv"));
    }

    public RepositorioClienteCSV(String rutaArchivo) {

        this(Paths.get(rutaArchivo));
    }

    private RepositorioClienteCSV(Path rutaArchivo) {

        this.rutaArchivo = rutaArchivo;
    }

    @Override
    public void guardar(Cliente entidad)
            throws PersistenciaException {

        if (entidad == null) {

            throw new PersistenciaException(
                    "No se puede guardar un cliente nulo."
            );
        }

        List<Cliente> clientes = listarTodos();

        boolean actualizado = false;

        for (int i = 0; i < clientes.size(); i++) {

            if (clientes.get(i)
                    .getRutCliente()
                    .equals(entidad.getRutCliente())) {

                clientes.set(i, entidad);

                actualizado = true;

                break;
            }
        }

        if (!actualizado) {

            clientes.add(entidad);
        }

        guardarTodos(clientes);
    }

    @Override
    public void eliminar(String rut)
            throws PersistenciaException {

        List<Cliente> clientes = listarTodos();

        clientes.removeIf(
                cliente ->
                cliente.getRutCliente().equals(rut)
        );

        guardarTodos(clientes);
    }

    @Override
    public Cliente buscarPorId(String rut)
            throws PersistenciaException {

        for (Cliente cliente : listarTodos()) {

            if (cliente.getRutCliente().equals(rut)) {

                return cliente;
            }
        }

        return null;
    }

    @Override
    public List<Cliente> listarTodos()
            throws PersistenciaException {

        List<String> lineas =
                CSVUtil.leerLineas(
                        rutaArchivo,
                        CABECERA
                );

        List<Cliente> clientes =
                new ArrayList<>();

        for (int i = 1; i < lineas.size(); i++) {

            String linea =
                    lineas.get(i).trim();

            if (linea.isEmpty()) {
                continue;
            }

            List<String> c =
                    CSVUtil.parsearLinea(linea);

            if (c.size() != 5) {

                throw new PersistenciaException(
                        "Registro de cliente invalido en linea "
                        + (i + 1)
                );
            }

            clientes.add(
                    new Cliente(
                            c.get(0),
                            c.get(1),
                            CSVUtil.nuloSiVacio(c.get(2)),
                            CSVUtil.nuloSiVacio(c.get(3)),
                            CSVUtil.nuloSiVacio(c.get(4))
                    )
            );
        }

        return clientes;
    }

    public void guardarTodos(List<Cliente> clientes)
            throws PersistenciaException {

        List<String> lineas =
                new ArrayList<>();

        for (Cliente cliente : clientes) {

            lineas.add(
                    CSVUtil.crearLinea(
                            cliente.getRutCliente(),
                            cliente.getNombreCliente(),
                            cliente.getCorreoCliente(),
                            cliente.getTelefono(),
                            cliente.getDireccion()
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