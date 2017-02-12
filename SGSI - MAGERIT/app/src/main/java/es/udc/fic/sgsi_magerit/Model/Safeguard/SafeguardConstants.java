package es.udc.fic.sgsi_magerit.Model.Safeguard;

import android.provider.BaseColumns;

/**
 * Created by err0r on 12/02/2017.
 */
public class SafeguardConstants {

    //Metainformación de la base de datos
    public static final String TABLE_NAME = "SAFEGUARD";
    public static final String STRING_TYPE = "TEXT";
    public static final String INT_TYPE = "INTEGER";
    public static final String REAL_TYPE = "REAL";

    //Columnas de la base de datos
    public static final String ID_SAFEGUARD = BaseColumns._ID;
    public static final String ID_ACTIVO = "idActivo";
    public static final String ID_AMENAZA = "idAmenaza";
    public static final String ID_PROYECTO = "idProyecto";
    public static final String ID_TIPO_SALVAGUARDA = "idTipoAmenaza";
    public static final String ID_LISTA_TIPO_SALVAGUARDA = "idListaTipoAmenaza";
    public static final String ID_VALORACION_CONTROLSEGURIDAD_DISPONIBILIDAD = "idValoracionControlSeguridadDisponibilidad";
    public static final String ID_VALORACION_CONTROLSEGURIDAD_INTEGRIDAD = "idValoracionControlSeguridadIntegridad";
    public static final String ID_VALORACION_CONTROLSEGURIDAD_CONFIDENCIALIDAD = "idValoracionControlSeguridadConfidencialidad";
    public static final String ID_VALORACION_CONTROLSEGURIDAD_AUTENTICIDAD = "idValoracionControlSeguridadAutenticidad";
    public static final String ID_VALORACION_CONTROLSEGURIDAD_TRAZABILIDAD = "idValoracionControlSeguridadTrazabilidad";
    public static final String TIPO_PROTECCION = "tipoProteccion";
    public static final String EFICACIA = "eficacia";
    public static final String FECHA_CREACION = "fechaCreacion";

}
