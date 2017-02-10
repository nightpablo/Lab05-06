package dam.isi.frsf.utn.edu.ar.lab05.api;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import dam.isi.frsf.utn.edu.ar.lab05.interfaces.ApiRestImplementation;
import dam.isi.frsf.utn.edu.ar.lab05.modelo.Usuario;

/**
 * Created by Pablo on 08/12/2016.
 */

public class UsuarioApiRest implements ApiRestImplementation<Usuario> {

    String NombreTabla = ProyectoDBApiRestMetaData.TABLA_USUARIO;

    @Override
    public void crear(Usuario entrada) {
        new TaskAsyncHTTP().execute(NombreTabla,"POST",entrada.toJSON());
    }

    @Override
    public void borrar(Integer id) {
        new TaskAsyncHTTP().execute(NombreTabla,"DELETE",id);
    }

    @Override
    public void actualizar(Usuario entrada) {
        new TaskAsyncHTTP().execute(NombreTabla,"PUT",entrada.toJSON(),entrada.getId());
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> lista_usuario = new ArrayList<Usuario>();
        try {
            JSONArray listar = (JSONArray) new TaskAsyncHTTP().execute(NombreTabla,"GET").get();
            for(int i=0;i<listar.length();i++){
                lista_usuario.add(new Usuario(listar.getJSONObject(i)));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lista_usuario;
    }

    @Override
    public Usuario buscarPorId(Integer id) {
        List<Usuario> listaUsuario = listar();
        for(Usuario i: listaUsuario)
            if(i.getId()==id)
                return i;
        return null;
    }
}
