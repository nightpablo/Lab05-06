package dam.isi.frsf.utn.edu.ar.lab05.dao;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import static android.R.attr.data;

/**
 * Created by martdominguez on 20/10/2016.
 */
public class RestClient {

    private final String IP_SERVER = /*"10.0.2.2"*/"10.15.152.62";
    private final String PORT_SERVER = "4000";
    private final String TAG_LOG = "LAB06";

    public JSONObject getById(Integer id,String path) {

        //Ejecuto una tarea asincrónica para poder abrir la url
        TareaAsincronicaRestClientLectura myTask = new TareaAsincronicaRestClientLectura();

        myTask.execute(new Object[]{"Lectura",path,id});

        try {
            return myTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }


    public JSONArray getByAll(Integer id,String path) {
        JSONArray resultado = null;
        return resultado;
    }
    public void crear(JSONObject objeto,String path) {

        TareaAsincronicaRestClientLectura myTask = new TareaAsincronicaRestClientLectura();
        myTask.execute(new Object[]{"Escritura",path,objeto});

        /*
        TareaAsincronicaRestClientEscritura myTask = new TareaAsincronicaRestClientEscritura();
        myTask.execute(new Object[]{path,objeto});*/

    }
    public void actualizar(JSONObject objeto,String path) {
    }

    public void borrar(Integer id,String path) {
    }

    private class TareaAsincronicaRestClientLectura extends AsyncTask<Object, Void, JSONObject> {
        private HttpURLConnection urlConnection;

        @Override
        protected JSONObject doInBackground(Object... params) {

            String Accion = (String) params[0];
            StringBuilder result = new StringBuilder();;
            String path = (String) params[1];

            switch(Accion){
                case "Lectura":
                    Integer id = (Integer) params[2];
                    try {
                        URL url = new URL("http://"+IP_SERVER+":"+PORT_SERVER+"/"+path+"/"+id);

                        Log.d(TAG_LOG,url.getPath()+ " --> "+url.toString());
                        urlConnection= (HttpURLConnection) url.openConnection();
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }

                        JSONObject jsonObject = new JSONObject(result.toString());
                        Log.i("JSON-RESULT: ",jsonObject.toString());
                        if(urlConnection!=null) urlConnection.disconnect();
                        return jsonObject;
                    }
                    catch (IOException e) {
                        Log.e("TEST-ARR",e.getMessage(),e);
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return null;
                case "Escritura":
                    JSONObject objeto = (JSONObject) params[2];


                    try {
                        URL url = new URL("http://"+IP_SERVER+":"+PORT_SERVER+"/"+path+"/");

                        Log.d(TAG_LOG,url.getPath()+ " --> "+url.toString());

                        urlConnection= (HttpURLConnection) url.openConnection();
                        urlConnection.setDoInput(true);
                        urlConnection.setDoOutput(true);
                        urlConnection.setChunkedStreamingMode(0);
                        urlConnection.setRequestMethod("POST");
                        urlConnection.setRequestProperty("Content-Type","application/json");


                        DataOutputStream printout = new DataOutputStream(urlConnection.getOutputStream());

                        printout.writeBytes(objeto.toString());
                        printout.flush ();
                        printout.close ();

                        Log.d("TEST-ARR","FIN!!! "+urlConnection.getResponseMessage());

                    }
                    catch (IOException e) {
                        Log.e("TEST-ARR",e.getMessage(),e);
                        e.printStackTrace();
                    }
                    break;
                case "Actualizacion":
                    break;
                case "Eliminacion":
                    break;
                default:
                    break;
            }
            return null;
        }

    }
}