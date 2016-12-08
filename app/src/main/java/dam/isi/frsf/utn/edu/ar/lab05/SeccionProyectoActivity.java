package dam.isi.frsf.utn.edu.ar.lab05;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dam.isi.frsf.utn.edu.ar.lab05.api.ProyectoApiRest;
import dam.isi.frsf.utn.edu.ar.lab05.modelo.Proyecto;

/**
 * Created by Pablo on 05/12/2016.
 */

public class SeccionProyectoActivity extends AppCompatActivity {

    private Spinner spinner_seccionProyecto;
    private List<Proyecto> listaProyecto;
    private Button button_CrearProyecto;
    private Button button_ModificarProyecto;
    private Button button_EliminarProyecto;
    ArrayAdapter<Proyecto> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seccion_proyecto);

        spinner_seccionProyecto = (Spinner) findViewById(R.id.spinner_seccionProyecto);
        button_CrearProyecto = (Button) findViewById(R.id.button_CrearProyecto);
        button_ModificarProyecto = (Button) findViewById(R.id.button_ModificarProyecto);
        button_EliminarProyecto = (Button) findViewById(R.id.button_EliminarProyecto);







        listaProyecto = new ProyectoApiRest().listar();
        adaptador = new ArrayAdapter<Proyecto>(this,android.R.layout.simple_spinner_item,listaProyecto);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_seccionProyecto.setAdapter(adaptador);

        button_CrearProyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                final View dialoglayout = inflater.inflate(R.layout.content_alta_proyecto, null);
                final EditText editText_NombreProyecto = (EditText) dialoglayout.findViewById(R.id.editText_NombreProyecto);

                AlertDialog.Builder builder = new AlertDialog.Builder(SeccionProyectoActivity.this);
                builder.setView(dialoglayout)
                        .setPositiveButton("Crear",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(editText_NombreProyecto.getText().toString().isEmpty())
                                            Toast.makeText(SeccionProyectoActivity.this, "¡Debe ingresar el nombre del proyecto!", Toast.LENGTH_SHORT).show();
                                        else {
                                            Proyecto nuevo = new Proyecto();
                                            nuevo.setNombre(editText_NombreProyecto.getText().toString());
                                            new ProyectoApiRest().crear(nuevo);
                                            listaProyecto = new ProyectoApiRest().listar();

                                            adaptador.clear();
                                            adaptador.addAll(listaProyecto);
                                            adaptador.notifyDataSetChanged();

                                        }
                                    }
                                })
                        .setNegativeButton("Cancelar",
                                null
                        );

                builder.create().show();
            }
        });


        button_ModificarProyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinner_seccionProyecto.getSelectedItem()==null) {
                    Toast.makeText(SeccionProyectoActivity.this, "¡Debe elegir un Proyecto!", Toast.LENGTH_SHORT).show();
                    return;
                }
                LayoutInflater inflater = getLayoutInflater();
                final View dialoglayout = inflater.inflate(R.layout.content_alta_proyecto, null);
                final TextView textView_TextoProyecto = (TextView) dialoglayout.findViewById(R.id.textView_TextoProyecto);
                textView_TextoProyecto.setText("MODIFICAR PROYECTO");
                final EditText editText_NombreProyecto = (EditText) dialoglayout.findViewById(R.id.editText_NombreProyecto);
                editText_NombreProyecto.setText(spinner_seccionProyecto.getSelectedItem().toString());


                AlertDialog.Builder builder = new AlertDialog.Builder(SeccionProyectoActivity.this);
                builder.setView(dialoglayout)
                        .setPositiveButton("Modificar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(editText_NombreProyecto.getText().toString().isEmpty())
                                            Toast.makeText(SeccionProyectoActivity.this, "¡Debe ingresar el nombre del proyecto!", Toast.LENGTH_SHORT).show();
                                        else {
                                            Proyecto nuevo = (Proyecto) spinner_seccionProyecto.getSelectedItem();
                                            nuevo.setNombre(editText_NombreProyecto.getText().toString());
                                            new ProyectoApiRest().actualizar(nuevo);
                                            listaProyecto = new ProyectoApiRest().listar();

                                            adaptador.clear();
                                            adaptador.addAll(listaProyecto);
                                            adaptador.notifyDataSetChanged();

                                        }
                                    }
                                })
                        .setNegativeButton("Cancelar",
                                null
                        );

                builder.create().show();
            }
        });

        button_EliminarProyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinner_seccionProyecto.getSelectedItem()==null) {
                    Toast.makeText(SeccionProyectoActivity.this, "¡Debe elegir un Proyecto!", Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(SeccionProyectoActivity.this);

                builder.setTitle("Eliminar Proyecto")
                        .setMessage("¿Estas seguro que deseas eliminar este proyecto?: "+spinner_seccionProyecto.getSelectedItem().toString())
                        .setPositiveButton("Si",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Proyecto nuevo = (Proyecto) spinner_seccionProyecto.getSelectedItem();
                                        new ProyectoApiRest().borrar(nuevo.getId());
                                        listaProyecto = new ProyectoApiRest().listar();

                                        adaptador.clear();
                                        adaptador.addAll(listaProyecto);
                                        adaptador.notifyDataSetChanged();
                                    }
                                })
                        .setNegativeButton("No",
                                null
                        );

                builder.create().show();

            }
        });



    }

}
