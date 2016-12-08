package dam.isi.frsf.utn.edu.ar.lab05.api;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import dam.isi.frsf.utn.edu.ar.lab05.interfaces.ApiRestImplementation;
import dam.isi.frsf.utn.edu.ar.lab05.modelo.Tarea;

/**
 * Created by Pablo on 08/12/2016.
 */

public class TareaApiRest implements ApiRestImplementation<Tarea>{

    @Override
    public void crear(Tarea entrada) {
        new TaskAsyncHTTP().execute("tareas","POST",entrada.toJSON());
    }

    @Override
    public void borrar(Integer id) {
        new TaskAsyncHTTP().execute("tareas","DELETE",id);
    }

    @Override
    public void actualizar(Tarea entrada) {
        new TaskAsyncHTTP().execute("tareas","PUT",entrada.toJSON(),entrada.getId());
    }

    @Override
    public List<Tarea> listar() {
        List<Tarea> lista_tareas = new ArrayList<Tarea>();
        try {
            JSONArray listar = (JSONArray) new TaskAsyncHTTP().execute("tareas","GET").get();
            for(int i=0;i<listar.length();i++){
                lista_tareas.add(new Tarea(listar.getJSONObject(i)));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lista_tareas;
    }

    @Override
    public Tarea buscarPorId(Integer id) {
        List<Tarea> listaTarea = listar();
        for(Tarea i: listaTarea)
            if(i.getId()==id)
                return i;
        return null;
    }
}
