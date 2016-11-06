package es.udc.fic.sgsi_magerit.Model.Threat;

import android.provider.BaseColumns;

/**
 * Created by err0r on 13/06/2016.
 */
public class ThreatConstants {

    //Metainformación de la base de datos
    public static final String TABLE_NAME = "THREAT";
    public static final String STRING_TYPE = "TEXT";
    public static final String INT_TYPE = "INTEGER";
    public static final String REAL_TYPE = "REAL";

    //Columnas de la base de datos
    public static final String ID_AMENAZA_ACTIVO = BaseColumns._ID;
    public static final String ID_ACTIVO = "idActivo";
    public static final String ID_PROYECTO = "idProyecto";
    public static final String ID_TIPO_AMENAZA = "idTipoAmenaza";
    public static final String ID_LISTA_TIPO_AMENAZA = "idListaTipoAmenaza";
    public static final String ID_VALORACION_DEGRADACION_DISPONIBILIDAD = "idValoracionDegradacionDisponibilidad";
    public static final String ID_VALORACION_PROBABILIDAD_DISPONIBILIDAD = "idValoracionProbabilidadDisponibilidad";
    public static final String ID_VALORACION_DEGRADACION_INTEGRIDAD = "idValoracionDegradacionIntegridad";
    public static final String ID_VALORACION_PROBABILIDAD_INTEGRIDAD = "idValoracionProbabilidadIntegridad";
    public static final String ID_VALORACION_DEGRADACION_CONFIDENCIALIDAD = "idValoracionDegradacionConfidencialidad";
    public static final String ID_VALORACION_PROBABILIDAD_CONFIDENCIALIDAD = "idValoracionProbabilidadConfidencialidad";
    public static final String ID_VALORACION_DEGRADACION_AUTENTICIDAD = "idValoracionDegradacionAutenticidad";
    public static final String ID_VALORACION_PROBABILIDAD_AUTENTICIDAD = "idValoracionProbabilidadAutenticidad";
    public static final String ID_VALORACION_DEGRADACION_TRAZABILIDAD = "idValoracionDegradacionTrazabilidad";
    public static final String ID_VALORACION_PROBABILIDAD_TRAZABILIDAD = "idValoracionProbabilidadTrazabilidad";
    public static final String FECHA_CREACION = "fechaCreacion";
}
