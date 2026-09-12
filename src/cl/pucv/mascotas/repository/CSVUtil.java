package cl.pucv.mascotas.repository;

import cl.pucv.mascotas.exception.PersistenciaException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

final class CSVUtil {

    private CSVUtil() {
    }

    static void asegurarArchivo(Path ruta, String cabecera)
            throws PersistenciaException {

        try {

            Path padre = ruta.getParent();

            if (padre != null) {
                Files.createDirectories(padre);
            }

            if (!Files.exists(ruta)) {

                Files.write(
                        ruta,
                        (cabecera + System.lineSeparator())
                                .getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING
                );
            }

        } catch (IOException e) {

            throw new PersistenciaException(
                    "No se pudo crear el archivo: " + ruta,
                    e
            );
        }
    }

    static List<String> leerLineas(Path ruta, String cabecera)
            throws PersistenciaException {

        asegurarArchivo(ruta, cabecera);

        try {

            return Files.readAllLines(
                    ruta,
                    StandardCharsets.UTF_8
            );

        } catch (IOException e) {

            throw new PersistenciaException(
                    "No se pudo leer el archivo: " + ruta,
                    e
            );
        }
    }

    static void escribirLineas(
            Path ruta,
            String cabecera,
            List<String> lineas)
            throws PersistenciaException {

        asegurarArchivo(ruta, cabecera);

        List<String> salida = new ArrayList<>();

        salida.add(cabecera);
        salida.addAll(lineas);

        try {

            Files.write(
                    ruta,
                    salida,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );

        } catch (IOException e) {

            throw new PersistenciaException(
                    "No se pudo escribir el archivo: " + ruta,
                    e
            );
        }
    }

    static String crearLinea(String... campos) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < campos.length; i++) {

            if (i > 0) {
                sb.append(',');
            }

            sb.append(escapar(campos[i]));
        }

        return sb.toString();
    }

    static List<String> parsearLinea(String linea)
            throws PersistenciaException {

        List<String> campos = new ArrayList<>();

        StringBuilder actual = new StringBuilder();

        boolean entreComillas = false;

        for (int i = 0; i < linea.length(); i++) {

            char c = linea.charAt(i);

            if (c == '"') {

                if (entreComillas
                        && i + 1 < linea.length()
                        && linea.charAt(i + 1) == '"') {

                    actual.append('"');
                    i++;

                } else {

                    entreComillas = !entreComillas;
                }

            } else if (c == ',' && !entreComillas) {

                campos.add(actual.toString());
                actual.setLength(0);

            } else {

                actual.append(c);
            }
        }

        if (entreComillas) {

            throw new PersistenciaException(
                    "Linea CSV invalida: comillas sin cerrar."
            );
        }

        campos.add(actual.toString());

        return campos;
    }

    static String texto(String valor) {

        return valor == null ? "" : valor;
    }

    static String nuloSiVacio(String valor) {

        return valor == null || valor.trim().isEmpty()
                ? null
                : valor;
    }

    private static String escapar(String valor) {

        String seguro = texto(valor);

        boolean requiereComillas =
                seguro.indexOf(',') >= 0
                || seguro.indexOf('"') >= 0
                || seguro.indexOf('\n') >= 0
                || seguro.indexOf('\r') >= 0;

        seguro = seguro.replace("\"", "\"\"");

        return requiereComillas
                ? "\"" + seguro + "\""
                : seguro;
    }
}