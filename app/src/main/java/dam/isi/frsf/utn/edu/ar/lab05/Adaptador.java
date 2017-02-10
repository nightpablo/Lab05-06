package dam.isi.frsf.utn.edu.ar.lab05;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

import dam.isi.frsf.utn.edu.ar.lab05.api.TareaApiRest;
import dam.isi.frsf.utn.edu.ar.lab05.modelo.Tarea;

public class Adaptador extends ArrayAdapter<Tarea> {

    Context context;

    public Adaptador (Context context, List<Tarea> tareas){
        super(context, 0, tareas);
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        if(convertView == null) {
            convertView = inflater.inflate(R.layout.fila_tarea, parent,false);

            holder = new ViewHolder();

            holder.nombre = (TextView) convertView.findViewById(R.id.tareaTitulo);
            holder.tiempoAsignado = (TextView) convertView.findViewById(R.id.tareaMinutosAsignados);
            holder.tiempoTrabajado = (TextView) convertView.findViewById(R.id.tareaMinutosTrabajados);
            holder.prioridad = (TextView) convertView.findViewById(R.id.tareaPrioridad);
            holder.responsable = (TextView) convertView.findViewById(R.id.tareaResponsable);
            holder.finalizada = (CheckBox) convertView.findViewById(R.id.tareaFinalizada);

            holder.btnFinalizar = (Button) convertView.findViewById(R.id.tareaBtnFinalizada);
            holder.btnEditar = (Button) convertView.findViewById(R.id.tareaBtnEditarDatos);
            holder.btnBorrar = (Button) convertView.findViewById(R.id.tareaBtnBorrar);
            holder.btnEstado = (ToggleButton) convertView.findViewById(R.id.tareaBtnTrabajando);


            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.btnEstado.setTag(getItem(position).getId());
        holder.btnEstado.setOnClickListener(new View.OnClickListener() {
            private long TiempoInicial=0;
            private long TiempoFinal=0;
            @Override
            public void onClick(View v) {
                if(holder.finalizada.isChecked()){
                    Toast.makeText(context, "No se puede trabajar, la tarea está finalizada", Toast.LENGTH_SHORT).show();
                    holder.btnEstado.setChecked(false);
                    return;
                }
                boolean isChecked = holder.btnEstado.isChecked();

                if(isChecked) {
                    TiempoInicial = System.currentTimeMillis();
                }
                else {
                    if(TiempoInicial!=0){


                        TiempoFinal = System.currentTimeMillis();

                        int tiempoTrabajadoCalculado = ((int) (long) (TiempoFinal-TiempoInicial)/1000);
                        if(tiempoTrabajadoCalculado-5<0)
                            return;



                        TareaApiRest registro = new TareaApiRest();

                        Tarea miTarea = registro.buscarPorId((Integer) v.getTag());
                        tiempoTrabajadoCalculado = (int) tiempoTrabajadoCalculado/5 + miTarea.getMinutosTrabajados();
                        miTarea.setMinutosTrabajados(tiempoTrabajadoCalculado);
                        registro.actualizar(miTarea);

                        TiempoInicial=0;
                        TiempoFinal=0;

                        holder.tiempoTrabajado.setText(tiempoTrabajadoCalculado + " minutos ");


                    }




                }
            }
        });


        holder.nombre.setText(getItem(position).getDescripcion());
        Integer horasAsigandas = getItem(position).getHorasEstimadas();
        holder.tiempoAsignado.setText(horasAsigandas * 60 + " minutos ");

        Integer minutosAsigandos = getItem(position).getMinutosTrabajados();
        holder.tiempoTrabajado.setText(minutosAsigandos + " minutos ");
        String p = getItem(position).getPrioridad().getPrioridad();
        holder.prioridad.setText(p);
        holder.responsable.setText(getItem(position).getResponsable().getNombre()+" ");
        holder.finalizada.setChecked(getItem(position).getFinalizada());

        holder.finalizada.setClickable(false);

        holder.btnEditar.setTag(getItem(position).getId());
        holder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.finalizada.isChecked()){
                    Toast.makeText(context, "No se puede editar, la tarea está finalizada", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(holder.btnEstado.isChecked()){
                    Toast.makeText(context, "No se puede editar, la tarea está trabajando", Toast.LENGTH_SHORT).show();
                    return;
                }
                final Integer idTarea = (Integer) view.getTag();
                Intent intEditarAct = new Intent(context, AltaTareaActivity.class);
                intEditarAct.putExtra("ID_TAREA", idTarea);
                context.startActivity(intEditarAct);

            }
        });

        holder.btnFinalizar.setTag(getItem(position).getId());
        holder.btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Integer idTarea = (Integer) view.getTag();
                if(holder.finalizada.isChecked()){
                            Toast.makeText(context, "La tarea ya está finalizada", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else if(holder.btnEstado.isChecked()){
                            Toast.makeText(context, "No se puede finalizar, la tarea está trabajando", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Log.d("LAB05-MAIN", "finalizar tarea : --- " + idTarea);
                        new TareaApiRest().finalizar(idTarea);


                        holder.finalizada.setChecked(true);


                    }
                }
        );


        holder.btnBorrar.setTag(getItem(position).getId());
        holder.btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.btnEstado.isChecked()){
                    Toast.makeText(context, "No se puede borrar, la tarea está trabajando", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Acá preguntamos si deseamos borrar o no!
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final Integer idTarea = (Integer) v.getTag();
                builder.setTitle("Borrar Tarea")
                        .setMessage("¿Está seguro que desea borrar esta tarea?")
                        .setPositiveButton("Si",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        TareaApiRest registro = new TareaApiRest();
                                        registro.borrar(idTarea);
                                        remove(getItem(position));
                                    }
                                })
                        .setNegativeButton("No",
                                null);

                builder.create().show();


            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView nombre;
        TextView tiempoAsignado;
        TextView tiempoTrabajado;
        TextView prioridad;
        TextView responsable;
        CheckBox finalizada;
        Button btnFinalizar;
        Button btnEditar;
        Button btnBorrar;
        ToggleButton btnEstado;

    }
}
