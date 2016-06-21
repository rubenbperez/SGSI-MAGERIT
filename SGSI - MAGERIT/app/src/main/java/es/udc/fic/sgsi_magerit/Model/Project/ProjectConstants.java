package es.udc.fic.sgsi_magerit.Model.Project;

import android.provider.BaseColumns;

/**
 * Created by err0r on 18/05/2016.
 */
public class ProjectConstants {
    //Metainformación de la base de datos
    public static final String TABLE_NAME = "PROJECT";
    public static final String STRING_TYPE = "TEXT";
    public static final String INT_TYPE = "INTEGER";
    public static final String REAL_TYPE = "REAL";

    //Columnas de la base de datos
    public static final String ID_PROYECTO = BaseColumns._ID;
    public static final String NOMBRE = "nombre";
    public static final String FECHA_CREACION = "fechaCreacion";
    public static final String DIRECTOR = "director";
    public static final String DESCRIPCION = "descripcion";
    public static final String VERSION = "version";
    public static final String ACTIVADO = "activado";
}
