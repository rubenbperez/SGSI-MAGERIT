package es.udc.fic.sgsi_magerit.Model.Asset;

import android.provider.BaseColumns;

/**
 * Created by err0r on 13/06/2016.
 */
public class AssetConstants {

    //Metainformación de la base de datos
    public static final String TABLE_NAME = "ASSET";
    public static final String STRING_TYPE = "TEXT";
    public static final String INT_TYPE = "INTEGER";
    public static final String REAL_TYPE = "REAL";

    //Columnas de la base de datos
    public static final String ID_ACTIVO = BaseColumns._ID;
    public static final String ID_PROYECTO = "idProyecto";
    public static final String ID_VALORACION_ACTIVO_DISPONIBILIDAD = "idValoracionActivoDisponibilidad";
    public static final String ID_VALORACION_ACTIVO_INTEGRIDAD = "idValoracionActivoIntegridad";
    public static final String ID_VALORACION_ACTIVO_CONFIDENCIALIDAD = "idValoracionActivoConfidencialidad";
    public static final String ID_VALORACION_ACTIVO_AUTENTICIDAD = "idValoracionActivoAutenticidad";
    public static final String ID_VALORACION_ACTIVO_TRAZABILIDAD = "idValoracionActivoTrazabilidad";
    public static final String NOMBRE = "nombre";
    public static final String CODIGO = "codigo";
    public static final String DESCRIPCION = "descripcion";
    public static final String RESPONSABLE = "responsable";
    public static final String UBICACION = "ubicacion";
    public static final String FECHA_CREACION = "fechaCreacion";

    public static final String TABLE_NAME_ACTIVO_TIPO_ACTIVO = "ACTIVO_TIPO_ACTIVO";
    public static final String ID_ACTIVO_TABLA_TIPO_ACTIVO = "idActivo";
    public static final String ID_ACTIVO_TIPO_ACTIVO = "idActivoTipoActivo";
    public static final String ID_TIPO_ACTIVO = "idTipoActivo";
    public static final String ID_LISTA_TIPO_ACTIVO = "idListaTipoActivo";
}
