package dam.isi.frsf.utn.edu.ar.lab05.api;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import dam.isi.frsf.utn.edu.ar.lab05.interfaces.ApiRestImplementation;
import dam.isi.frsf.utn.edu.ar.lab05.modelo.Prioridad;

/**
 * Created by Pablo on 08/12/2016.
 */

public class PrioridadApiRest implements ApiRestImplementation<Prioridad>{

    String NombreTabla = ProyectoDBApiRestMetaData.TABLA_PRIORIDAD;

    @Override
    public void crear(Prioridad entrada){new TaskAsyncHTTP().execute(NombreTabla,"POST",entrada.toJSON());}

    @Override
    public void borrar(Integer id) {
            new TaskAsyncHTTP().execute(NombreTabla,"DELETE",id);
    }

    @Override
    public void actualizar(Prioridad entrada) {new TaskAsyncHTTP().execute(NombreTabla,"PUT",entrada.toJSON(),entrada.getId());}

    @Override
    public List<Prioridad> listar() {
        List<Prioridad> lista_prioridad = new ArrayList<Prioridad>();
        try {
            JSONArray listar = (JSONArray) new TaskAsyncHTTP().execute(NombreTabla,"GET").get();
            for(int i=0;i<listar.length();i++){
                lista_prioridad.add(new Prioridad(listar.getJSONObject(i)));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lista_prioridad;
    }

    @Override
    public Prioridad buscarPorId(Integer id) {
        List<Prioridad> listaPrioridad = listar();
        for(Prioridad i: listaPrioridad)
            if(i.getId()==id)
                return i;
        return null;
    }
}
