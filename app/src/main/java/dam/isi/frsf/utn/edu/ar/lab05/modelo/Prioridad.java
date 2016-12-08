package dam.isi.frsf.utn.edu.ar.lab05.modelo;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import dam.isi.frsf.utn.edu.ar.lab05.api.ProyectoDBApiRestMetaData;

/**
 * Created by mdominguez on 06/10/16.
 */
public class Prioridad {

    private Integer id;
    private String prioridad;

    public Prioridad(){

    }

    public Prioridad(Integer id, String prioridad) {
        this.id = id;
        this.prioridad = prioridad;
    }

    public Prioridad(JSONObject jsonObject) {
        try {
            id = jsonObject.getInt(ProyectoDBApiRestMetaData.TablaPrioridadMetaData.ID);
            prioridad = jsonObject.getString(ProyectoDBApiRestMetaData.TablaPrioridadMetaData.PRIORIDAD);
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

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public String toString(){
        return prioridad;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ProyectoDBApiRestMetaData.TablaPrioridadMetaData.PRIORIDAD,prioridad);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("JSON-PRIORIDAD: ",jsonObject.toString());
        return jsonObject;
    }
}
