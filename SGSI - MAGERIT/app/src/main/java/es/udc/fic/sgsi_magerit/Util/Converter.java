package es.udc.fic.sgsi_magerit.Util;

import android.widget.Spinner;

import java.util.Arrays;

/**
 * Created by err0r on 15/08/2017.
 */
public class Converter {
    
    public static String convertirNombreAmenaza(Integer idLista, Integer idTipo) {
        
        String nombre = "?";

        switch(idLista) {
            case 0:
                nombre = GlobalConstants.AMENAZAS_TIPO_DESASTRES_NATURALES[idTipo];
                break;
            case 1:
                nombre = GlobalConstants.AMENAZAS_TIPO_ORIGEN_INDUSTRIAL[idTipo];
                break;
            case 2:
                nombre = GlobalConstants.AMENAZAS_TIPO_ERRORES_FALLOS_NO_INTENCIONADOS[idTipo];
                break;
            case 3:
                nombre = GlobalConstants.AMENAZAS_TIPO_ATAQUES_DELIBERADOS[idTipo];
                break;
        }
        return nombre;
    }

    public static String convertirNombreSalvaguarda(Integer idLista, Integer idTipo) {
        String codeName = "";
        switch (idLista) {
            case 0:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCIONES_GENERALES[idTipo];
                break;
            case 1:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_DATOS[idTipo];
                break;
            case 2:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_CLAVES_CRIPTO[idTipo];
                break;
            case 3:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_SERVICIOS[idTipo];
                break;
            case 4:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_SW[idTipo];
                break;
            case 5:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_HW[idTipo];
                break;
            case 6:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_COMS[idTipo];
                break;
            case 7:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_INTERCONEXION[idTipo];
                break;
            case 8:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_SOPORTES[idTipo];
                break;
            case 9:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_AUXILIAR[idTipo];
                break;
            case 10:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_INSTALACIONES[idTipo];
                break;
            case 11:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_PERSONAL[idTipo];
                break;
            case 12:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_ORGANIZATIVO[idTipo];
                break;
            case 13:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_CONTINUIDAD_OPERACIONES[idTipo];
                break;
            case 14:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_EXTERNALIZACION[idTipo];
                break;
            case 15:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_ADQUISICION_DESARROLLO[idTipo];
                break;
        }
        return codeName;
    }


}
