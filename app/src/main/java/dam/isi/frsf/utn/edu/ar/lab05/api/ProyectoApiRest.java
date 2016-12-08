package dam.isi.frsf.utn.edu.ar.lab05.api;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import dam.isi.frsf.utn.edu.ar.lab05.interfaces.ApiRestImplementation;
import dam.isi.frsf.utn.edu.ar.lab05.modelo.Proyecto;

/**
 * Created by martdominguez on 20/10/2016.
 */
public class ProyectoApiRest implements ApiRestImplementation<Proyecto> {

    public void crear(Proyecto entrada){new TaskAsyncHTTP().execute("proyectos","POST",entrada.toJSON());}
    public void borrar(Integer id){
        new TaskAsyncHTTP().execute("proyectos","DELETE",id);
    }
    public void actualizar(Proyecto entrada){new TaskAsyncHTTP().execute("proyectos","PUT",entrada.toJSON(),entrada.getId());}

    public List<Proyecto> listar(){
        List<Proyecto> lista_proyectos = new ArrayList<Proyecto>();
        try {
            JSONArray listar = (JSONArray) new TaskAsyncHTTP().execute("proyectos","GET").get();
            for(int i=0;i<listar.length();i++){
                lista_proyectos.add(new Proyecto(listar.getJSONObject(i)));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lista_proyectos;
    }

    public Proyecto buscarPorId(Integer id){
        List<Proyecto> listaProyecto = listar();
        for(Proyecto i: listaProyecto)
            if(i.getId()==id)
                return i;
        return null;
    }
}