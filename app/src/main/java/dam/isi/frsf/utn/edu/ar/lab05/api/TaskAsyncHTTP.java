package dam.isi.frsf.utn.edu.ar.lab05.api;

import android.os.AsyncTask;
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

/**
 * Created by Pablo on 05/12/2016.
 */

public class TaskAsyncHTTP extends AsyncTask<Object, Void, Object> {

    private final String IP_SERVER = /*"10.0.2.2"*/"192.168.1.12";
    private final String PORT_SERVER = "4000";
    private final String TAG_LOG = "LAB06";
    private HttpURLConnection urlConnection;

    @Override
    protected Object doInBackground(Object... params) {
        String path = (String) params[0];
        String RequestMethod = (String) params[1];
        URL url=null;
        urlConnection = null;
        JSONObject jsonObject;
        DataOutputStream printout;
        try {
            switch (RequestMethod) {
                case "GET":
                    url = new URL("http://" + IP_SERVER + ":" + PORT_SERVER + "/" + path + "/");
                    Log.d(TAG_LOG, url.getPath() + " --> " + url.toString());
                    urlConnection = (HttpURLConnection) url.openConnection();
                    StringBuilder result = new StringBuilder();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    JSONArray jsonArray = new JSONArray(result.toString());

                    Log.d("RequestMethod", "["+RequestMethod+"] - " + jsonArray.toString());
                    Log.d("RequestMethod", "["+RequestMethod+"] - " + urlConnection.getResponseMessage());
                    return jsonArray;
                case "PUT":
                case "POST":
                    jsonObject = (JSONObject) params[2];
                    url = new URL("http://" + IP_SERVER + ":" + PORT_SERVER + "/" + path + "/");
                    if(RequestMethod.equals("PUT")) {
                        Integer id = (Integer) params[3];
                        url = new URL("http://" + IP_SERVER + ":" + PORT_SERVER + "/" + path + "/" + id);
                    }
                    Log.d(TAG_LOG, url.getPath() + " --> " + url.toString());
                    urlConnection = (HttpURLConnection) url.openConnection();


                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);
                    urlConnection.setChunkedStreamingMode(0);
                    urlConnection.setRequestMethod(RequestMethod);
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    printout = new DataOutputStream(urlConnection.getOutputStream());
                    printout.writeBytes(jsonObject.toString());
                    printout.flush();
                    printout.close();
                    Log.d("RequestMethod", "["+RequestMethod+"] - " + jsonObject.toString());
                    Log.d("RequestMethod", "["+RequestMethod+"] - " + urlConnection.getResponseMessage());
                    break;
                case "DELETE":
                    Integer id = (Integer) params[2];

                    url = new URL("http://" + IP_SERVER + ":" + PORT_SERVER + "/" + path + "/"+id);
                    Log.d(TAG_LOG, url.getPath() + " --> " + url.toString());
                    urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestMethod(RequestMethod);
                    urlConnection.setRequestProperty("Content-Type", "application/json");

                    Log.d("RequestMethod", "[POST] - " + urlConnection.getResponseMessage());
                    break;
                default:
                    break;

            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection!=null) urlConnection.disconnect();
        }

        return null;
    }
}
