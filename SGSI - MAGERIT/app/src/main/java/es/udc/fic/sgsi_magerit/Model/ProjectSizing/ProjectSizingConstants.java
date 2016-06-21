package es.udc.fic.sgsi_magerit.Model.ProjectSizing;

import android.provider.BaseColumns;

/**
 * Created by err0r on 31/05/2016.
 */
public class ProjectSizingConstants {

    public static final String TABLE_NAME_PARAMETRIZACION_ACTIVO = "PARAMETRIZACION_ACTIVO";
    public static final String TABLE_NAME_PARAMETRIZACION_VULNERABILIDAD = "PARAMETRIZACION_VULNERABILIDAD";
    public static final String TABLE_NAME_PARAMETRIZACION_IMPACTO = "PARAMETRIZACION_IMPACTO";
    public static final String TABLE_NAME_PARAMETRIZACION_CONTROLSEGURIDAD = "PARAMETRIZACION_CONTROLSEGURIDAD";
    public static final String STRING_TYPE = "TEXT";
    public static final String INT_TYPE = "INTEGER";
    public static final String REAL_TYPE = "REAL";

    //Columnas de la base de datos
    public static final String ID_PARAMETRIZACION_ACTIVO = BaseColumns._ID;
    public static final String ID_PARAMETRIZACION_VULNERABILIDAD = BaseColumns._ID;
    public static final String ID_PARAMETRIZACION_IMPACTO= BaseColumns._ID;
    public static final String ID_PARAMETRIZACION_CONTROLSEGURIDAD= BaseColumns._ID;
    public static final String ID_PROYECTO = "idProyecto";
    public static final String ID_TIPO = "idTipo";
    public static final String RANGO_SUPERIOR = "rangoSuperior";
    public static final String RANGO_INFERIOR = "rangoInferior";
    public static final String VALOR_TIEMPO = "valorTiempo";
    public static final String VALOR_TIPO_TIEMPO = "valorTipoTiempo";
    public static final String VALOR = "valor";
    public static final String ACTIVADO = "activado";

}
