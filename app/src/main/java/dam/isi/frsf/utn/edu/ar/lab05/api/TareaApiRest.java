package dam.isi.frsf.utn.edu.ar.lab05.api;

import android.content.ContentValues;

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

    String NombreTabla = ProyectoDBApiRestMetaData.TABLA_TAREA;

    @Override
    public void crear(Tarea entrada) {
        new TaskAsyncHTTP().execute(NombreTabla,"POST",entrada.toJSON());
    }

    @Override
    public void borrar(Integer id) {
        new TaskAsyncHTTP().execute(NombreTabla,"DELETE",id);
    }

    @Override
    public void actualizar(Tarea entrada) {
        new TaskAsyncHTTP().execute(NombreTabla,"PUT",entrada.toJSON(),entrada.getId());
    }

    @Override
    public List<Tarea> listar() {
        List<Tarea> lista_tareas = new ArrayList<Tarea>();
        try {
            JSONArray listar = (JSONArray) new TaskAsyncHTTP().execute(NombreTabla,"GET").get();
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

    public void finalizar(Integer idTarea){
        //Establecemos los campos-valores a actualizar
        Tarea elregistro = buscarPorId(idTarea);
        elregistro.setFinalizada(true);
        actualizar(elregistro);
    }

    public List<Tarea> listarDesviosPlanificacion(Boolean soloTerminadas,Integer desvioMaximoMinutos){
        // retorna una lista de todas las tareas que tardaron más (en exceso) o menos (por defecto)
        // que el tiempo planificado.
        // si la bandera soloTerminadas es true, se busca en las tareas terminadas, sino en todas.
        List<Tarea> listatarea = listar();
        List<Tarea> listaretorno = new ArrayList<Tarea>();
        for(Tarea i:listatarea){
            if(!soloTerminadas) {
                if (Math.abs(i.getMinutosTrabajados() - i.getHorasEstimadas() * 60) < desvioMaximoMinutos)
                    listaretorno.add(i);
            }
            else{
                if(i.getFinalizada())
                    if (Math.abs(i.getMinutosTrabajados() - i.getHorasEstimadas() * 60) < desvioMaximoMinutos)
                        listaretorno.add(i);
            }
        }
        return listaretorno;
    }
}
