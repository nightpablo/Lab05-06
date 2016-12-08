package dam.isi.frsf.utn.edu.ar.lab05.modelo;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import dam.isi.frsf.utn.edu.ar.lab05.api.PrioridadApiRest;
import dam.isi.frsf.utn.edu.ar.lab05.api.ProyectoApiRest;
import dam.isi.frsf.utn.edu.ar.lab05.api.UsuarioApiRest;
import dam.isi.frsf.utn.edu.ar.lab05.api.ProyectoDBApiRestMetaData;

/**
 * Created by mdominguez on 06/10/16.
 */
public class Tarea {

    private String descripcion;


    private Integer id;
    private Integer horasEstimadas;
    private Integer minutosTrabajados;
    private Boolean finalizada;
    private Proyecto proyecto;
    private Prioridad prioridad;
    private Usuario responsable;

    public Tarea() {
    }

    public Tarea(Integer id, Integer horasEstimadas, Integer minutosTrabajados, Boolean finalizada, Proyecto proyecto, Prioridad prioridad, Usuario responsable) {
        this.id = id;
        this.horasEstimadas = horasEstimadas;
        this.minutosTrabajados = minutosTrabajados;
        this.finalizada = finalizada;
        this.proyecto = proyecto;
        this.prioridad = prioridad;
        this.responsable = responsable;
    }

    public Tarea(JSONObject jsonObject) {
        try {
            id = jsonObject.getInt(ProyectoDBApiRestMetaData.TablaTareaMetaData.ID);
            descripcion = jsonObject.getString(ProyectoDBApiRestMetaData.TablaTareaMetaData.DESCRIPCION);
            horasEstimadas = jsonObject.getInt(ProyectoDBApiRestMetaData.TablaTareaMetaData.HORASESTIMADAS);
            minutosTrabajados = jsonObject.getInt(ProyectoDBApiRestMetaData.TablaTareaMetaData.MINUTOSTRABAJADOS);
            prioridad = new PrioridadApiRest().buscarPorId(
                    jsonObject.getInt(ProyectoDBApiRestMetaData.TablaTareaMetaData.PRIORIDAD)
            );
            responsable = new UsuarioApiRest().buscarPorId(
                    jsonObject.getInt(ProyectoDBApiRestMetaData.TablaTareaMetaData.RESPONSABLE)
            );
            proyecto = new ProyectoApiRest().buscarPorId(
                    jsonObject.getInt(ProyectoDBApiRestMetaData.TablaTareaMetaData.PROYECTO)
            );
            finalizada = jsonObject.getInt(ProyectoDBApiRestMetaData.TablaTareaMetaData.FINALIZADA)==1? true:false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getHorasEstimadas() {
        return horasEstimadas;
    }

    public void setHorasEstimadas(Integer horasEstimadas) {
        this.horasEstimadas = horasEstimadas;
    }

    public Integer getMinutosTrabajados() {
        return minutosTrabajados;
    }

    public void setMinutosTrabajados(Integer minutosTrabajados) {
        this.minutosTrabajados = minutosTrabajados;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getFinalizada() {
        return finalizada;
    }

    public void setFinalizada(Boolean finalizada) {
        this.finalizada = finalizada;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public Usuario getResponsable() {
        return responsable;
    }

    public void setResponsable(Usuario responsable) {
        this.responsable = responsable;
    }

    @Override
    public String toString() {
        return "Proyecto: " + proyecto.getNombre() +
                "\nID: " + id + "\nDescripción: " + descripcion +
                "\nHora Estimada: " + horasEstimadas +
                "\nMinutos Trabajados: " + minutosTrabajados
                + "\nFinalizada: " + finalizada
                + "\nPrioridad: " + prioridad.getPrioridad()
                + "\nResponsable: " + responsable.getNombre();

    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ProyectoDBApiRestMetaData.TablaTareaMetaData.DESCRIPCION, descripcion);
            jsonObject.put(ProyectoDBApiRestMetaData.TablaTareaMetaData.HORASESTIMADAS, horasEstimadas);
            jsonObject.put(ProyectoDBApiRestMetaData.TablaTareaMetaData.MINUTOSTRABAJADOS, minutosTrabajados);
            jsonObject.put(ProyectoDBApiRestMetaData.TablaTareaMetaData.PRIORIDAD, prioridad.getId());
            jsonObject.put(ProyectoDBApiRestMetaData.TablaTareaMetaData.RESPONSABLE, responsable.getId());
            jsonObject.put(ProyectoDBApiRestMetaData.TablaTareaMetaData.PROYECTO, proyecto.getId());
            jsonObject.put(ProyectoDBApiRestMetaData.TablaTareaMetaData.FINALIZADA, finalizada?1:0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("JSON-TAREA: ",jsonObject.toString());
        return jsonObject;
    }


}