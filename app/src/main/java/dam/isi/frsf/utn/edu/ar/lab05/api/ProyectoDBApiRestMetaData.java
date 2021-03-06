package dam.isi.frsf.utn.edu.ar.lab05.api;

import android.provider.BaseColumns;

/**
 * Created by Pablo on 05/12/2016.
 */

public class ProyectoDBApiRestMetaData {
    public static final String NOMBRE_DB= "lab06db.json";
    public static final String TABLA_PROYECTO = "proyectos";
    public static final String TABLA_PRIORIDAD = "prioridades";
    public static final String TABLA_USUARIO = "usuarios";
    public static final String TABLA_TAREA = "tareas";


    public static class TablaProyectoMetadata implements BaseColumns {
        public static final String ID = "id";
        public static final String NOMBRE ="nombre";
    }

    public static class TablaPrioridadMetaData implements BaseColumns {
        public static final String ID = "id";
        public static final String PRIORIDAD = "prioridad";
    }

    public static class TablaTareaMetaData implements BaseColumns{
        public static final String ID = "id";
        public static final String DESCRIPCION = "descripcion";
        public static final String HORASESTIMADAS = "horasEstimadas";
        public static final String MINUTOSTRABAJADOS = "minutosTrabajados";
        public static final String PRIORIDAD = "prioridadId";
        public static final String RESPONSABLE = "usuarioId";
        public static final String PROYECTO = "proyectoId";
        public static final String FINALIZADA = "finalizada";
    }

    public static class TablaUsuarioMetaData implements BaseColumns{
        public static final String ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String CORREOELECTRONICO = "correoElectronico";
    }
}
