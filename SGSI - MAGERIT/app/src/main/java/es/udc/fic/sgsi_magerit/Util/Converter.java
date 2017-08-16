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

}
