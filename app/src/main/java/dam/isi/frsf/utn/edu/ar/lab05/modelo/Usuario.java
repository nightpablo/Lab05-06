package dam.isi.frsf.utn.edu.ar.lab05.modelo;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import dam.isi.frsf.utn.edu.ar.lab05.api.ProyectoDBApiRestMetaData;

/**
 * Created by mdominguez on 06/10/16.
 */
public class Usuario {

    private Integer id;
    private String nombre;
    private String correoElectronico;

    public Usuario(){

    }

    public Usuario(Integer id, String nombre, String correoElectronico) {
        this.id = id;
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
    }

    public Usuario(JSONObject jsonObject) {
        try {
            id = jsonObject.getInt(ProyectoDBApiRestMetaData.TablaUsuarioMetaData.ID);
            nombre = jsonObject.getString(ProyectoDBApiRestMetaData.TablaUsuarioMetaData.NOMBRE);
            correoElectronico = jsonObject.getString(ProyectoDBApiRestMetaData.TablaUsuarioMetaData.CORREOELECTRONICO);
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    @Override
    public String toString(){
        return nombre;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ProyectoDBApiRestMetaData.TablaUsuarioMetaData.NOMBRE, nombre);
            jsonObject.put(ProyectoDBApiRestMetaData.TablaUsuarioMetaData.CORREOELECTRONICO, correoElectronico);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("JSON-USUARIO: ",jsonObject.toString());
        return jsonObject;
    }
}
