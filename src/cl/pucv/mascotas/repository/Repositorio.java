package cl.pucv.mascotas.repository;

import cl.pucv.mascotas.exception.PersistenciaException;
import java.util.List;

public interface Repositorio<T, ID> {

    void guardar(T entidad) throws PersistenciaException;

    void eliminar(ID id) throws PersistenciaException;

    T buscarPorId(ID id) throws PersistenciaException;

    List<T> listarTodos() throws PersistenciaException;
}