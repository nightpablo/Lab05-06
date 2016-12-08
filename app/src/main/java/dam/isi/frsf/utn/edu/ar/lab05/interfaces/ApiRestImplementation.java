package dam.isi.frsf.utn.edu.ar.lab05.interfaces;

import java.util.List;

/**
 * Created by Pablo on 08/12/2016.
 */

public interface ApiRestImplementation<T> {
    public void crear(T entrada);
    public void borrar(Integer id);
    public void actualizar(T entrada);
    public List<T> listar();
    public T buscarPorId(Integer id);
}
