package es.udc.fic.sgsi_magerit.Util;

import java.util.ArrayList;
import java.util.List;

import es.udc.fic.sgsi_magerit.Model.Analisis.AnalisisDTO;
import es.udc.fic.sgsi_magerit.Model.Analisis.AnalisisInfoDTO;
import es.udc.fic.sgsi_magerit.Model.Analisis.ParametrizacionAnalisisDTO;
import es.udc.fic.sgsi_magerit.Model.Analisis.SafeguardAnalisisDTO;
import es.udc.fic.sgsi_magerit.Model.Analisis.ThreatAnalisisDTO;

/**
 * Created by err0r on 30/08/2017.
 */
public class DataAnalisisGenerator {

    /*private ThreatAnalisisDTO obtenerValorAmenazaParaRiesgo(ThreatAnalisisDTO amenaza, List<SafeguardAnalisisDTO> safeguards) {

        ThreatAnalisisDTO valorAmenaza = null;

        Integer valorDisp = null;
        Integer valorInt = null;
        Integer valorConf = null;
        Integer valorAut = null;
        Integer valorTraz = null;


        Integer valoracionDegradacionDisponibilidadAmenaza = amenaza.getIdDegradacionDisponibilidad();
        Integer valoracionProbabilidadDisponibilidadAmenaza = amenaza.getIdProbabilidadDisponibilidad();
        Integer valoracionDegradacionIntegridadAmenaza = amenaza.getIdDegradacionIntegridad();
        Integer valoracionProbabilidadIntegridadAmenaza = amenaza.getIdProbabilidadIntegridad();
        Integer valoracionDegradacionConfidencialidadAmenaza = amenaza.getIdDegradacionConfidencialidad();
        Integer valoracionProbabilidadConfidencialidadAmenaza = amenaza.getIdProbabilidadConfidencialidad();
        Integer valoracionDegradacionAutenticidadAmenaza = amenaza.getIdDegradacionDisponibilidad();
        Integer valoracionProbabilidadAutenticidadAmenaza = amenaza.getIdProbabilidadAutenticidad();
        Integer valoracionDegradacionTrazabilidadAmenaza = amenaza.getIdDegradacionTrazabilidad();
        Integer valoracionProbabilidadTrazabilidadAmenaza = amenaza.getIdProbabilidadTrazabilidad();


        for (SafeguardAnalisisDTO safeguard : safeguards) {

            if (safeguard.getIdTipoSalvaguarda() != 0) {
                continue;
            }

            Integer valoracionDisponibilidadSalvaguarda = safeguard.getIdControlSeguridadDisponibilidad();
            Integer valoracionIntegridadSalvaguarda = safeguard.getIdControlSeguridadIntegridad();
            Integer valoracionConfidencialidadSalvaguarda = safeguard.getIdControlSeguridadConfidencialidad();
            Integer valoracionAutenticidadSalvaguarda = safeguard.getIdControlSeguridadAutenticidad();
            Integer valoracionTrazabilidadSalvaguarda = safeguard.getIdControlSeguridadTrazabilidad();

            if (valoracionDisponibilidadSalvaguarda != null) {
                if (valorDisp != null)
                    valorDisp = valorDisp * valoracionDisponibilidadSalvaguarda;
                else
                    valorDisp = valoracionDisponibilidadSalvaguarda;
            }
            if (valoracionIntegridadSalvaguarda != null) {
                if (valorInt != null)
                    valorInt = valorInt * valoracionIntegridadSalvaguarda;
                else
                    valorInt = valoracionIntegridadSalvaguarda;
            }

            if (valoracionConfidencialidadSalvaguarda != null) {
                if (valorConf != null)
                    valorInt = valorConf * valoracionConfidencialidadSalvaguarda;
                else
                    valorConf = valoracionConfidencialidadSalvaguarda;
            }
            if (valoracionAutenticidadSalvaguarda != null) {
                if (valorAut != null)
                    valorAut = valorAut * valoracionAutenticidadSalvaguarda;
                else
                    valorAut = valoracionAutenticidadSalvaguarda;
            }
            if (valoracionTrazabilidadSalvaguarda != null) {
                if (valorTraz != null)
                    valorTraz = valorTraz * valoracionTrazabilidadSalvaguarda;
                else
                    valorTraz = valoracionTrazabilidadSalvaguarda;
            }

        }


        return valorAmenaza;

    }

    private static List<SafeguardAnalisisDTO> obtenerSalvaguardasDeAmenaza (long idThreat,
                                                                 List<SafeguardAnalisisDTO> salvaguardasActivo) {
        List<SafeguardAnalisisDTO> salvaguardasAmenaza = new ArrayList<>();

        for (SafeguardAnalisisDTO salvaguardaActivo : salvaguardasActivo) {

            if (salvaguardaActivo.getIdThreat() == idThreat) {
                salvaguardasAmenaza.add(salvaguardaActivo);
            }
        }
        return salvaguardasAmenaza;
    }*/



    public static List<AnalisisDTO> generarDatosAnalisisRiesgo(List<AnalisisInfoDTO> dataAnalisis, ParametrizacionAnalisisDTO parametrizacion) {

        List<AnalisisDTO> listaAnalisis = new ArrayList<>();

        
        for (AnalisisInfoDTO activo : dataAnalisis) {

            Integer valorDispResult = null;
            Integer valorIntResult = null;
            Integer valorConfResult = null;
            Integer valorAutResult = null;
            Integer valorTrazResult = null;

            Integer valorDisp = null;
            Integer valorInt = null;
            Integer valorConf = null;
            Integer valorAut = null;
            Integer valorTraz = null;
            
            Integer valoracionDisponibilidadActivo = activo.getAsset().getIdValoracionDisp();
            Integer valoracionIntegridadActivo = activo.getAsset().getIdValoracionDisp();
            Integer valoracionConfidencialidadActivo = activo.getAsset().getIdValoracionDisp();
            Integer valoracionAutenticidadActivo = activo.getAsset().getIdValoracionDisp();
            Integer valoracionTrazabilidadActivo = activo.getAsset().getIdValoracionDisp();
            
            valorDisp = valoracionDisponibilidadActivo;
            valorInt = valoracionIntegridadActivo;
            valorConf = valoracionConfidencialidadActivo;
            valorAut = valoracionAutenticidadActivo;
            valorTraz = valoracionTrazabilidadActivo;
            
            for (ThreatAnalisisDTO amenaza : activo.getThreats()) {
                
                Integer valoracionDegradacionDisponibilidadAmenaza = amenaza.getIdDegradacionDisponibilidad();
                Integer valoracionProbabilidadDisponibilidadAmenaza = amenaza.getIdProbabilidadDisponibilidad();
                Integer valoracionDegradacionIntegridadAmenaza = amenaza.getIdDegradacionIntegridad();
                Integer valoracionProbabilidadIntegridadAmenaza = amenaza.getIdProbabilidadIntegridad();
                Integer valoracionDegradacionConfidencialidadAmenaza = amenaza.getIdDegradacionConfidencialidad();
                Integer valoracionProbabilidadConfidencialidadAmenaza = amenaza.getIdProbabilidadConfidencialidad();
                Integer valoracionDegradacionAutenticidadAmenaza = amenaza.getIdDegradacionDisponibilidad();
                Integer valoracionProbabilidadAutenticidadAmenaza = amenaza.getIdProbabilidadAutenticidad();
                Integer valoracionDegradacionTrazabilidadAmenaza = amenaza.getIdDegradacionTrazabilidad();
                Integer valoracionProbabilidadTrazabilidadAmenaza = amenaza.getIdProbabilidadTrazabilidad();
                
                if (valorDisp != null && valoracionDegradacionDisponibilidadAmenaza != null &&
                        valoracionProbabilidadDisponibilidadAmenaza != null) {
                    valorDisp = valorDisp * valoracionDegradacionDisponibilidadAmenaza * valoracionProbabilidadDisponibilidadAmenaza;
                }

                if (valorInt != null && valoracionDegradacionIntegridadAmenaza != null &&
                        valoracionProbabilidadIntegridadAmenaza != null) {
                    valorInt = valorInt * valoracionDegradacionIntegridadAmenaza * valoracionProbabilidadIntegridadAmenaza;
                }

                if (valorConf != null && valoracionDegradacionConfidencialidadAmenaza != null &&
                        valoracionProbabilidadConfidencialidadAmenaza != null) {
                    valorConf = valorConf * valoracionDegradacionConfidencialidadAmenaza * valoracionProbabilidadConfidencialidadAmenaza;
                }

                if (valorAut != null && valoracionDegradacionAutenticidadAmenaza != null &&
                        valoracionProbabilidadAutenticidadAmenaza != null) {
                    valorAut = valorAut * valoracionDegradacionAutenticidadAmenaza * valoracionProbabilidadAutenticidadAmenaza;
                }

                if (valorTraz != null && valoracionDegradacionTrazabilidadAmenaza != null &&
                        valoracionProbabilidadTrazabilidadAmenaza != null) {
                    valorTraz = valorTraz * valoracionDegradacionTrazabilidadAmenaza * valoracionProbabilidadTrazabilidadAmenaza;
                }
                
                for (SafeguardAnalisisDTO salvaguarda : activo.getSafeguards()) {

                    Integer valoracionDisponibilidadSalvaguarda = salvaguarda.getIdControlSeguridadDisponibilidad();
                    Integer valoracionIntegridadSalvaguarda = salvaguarda.getIdControlSeguridadIntegridad();
                    Integer valoracionConfidencialidadSalvaguarda = salvaguarda.getIdControlSeguridadConfidencialidad();
                    Integer valoracionAutenticidadSalvaguarda = salvaguarda.getIdControlSeguridadAutenticidad();
                    Integer valoracionTrazabilidadSalvaguarda = salvaguarda.getIdControlSeguridadTrazabilidad();
                    
                    
                    if (salvaguarda.getIdTipoSalvaguarda() != 0)
                        continue;
                    
                    if (valorDisp != null && valoracionDisponibilidadSalvaguarda != null) {
                        valorDisp = valorDisp * valoracionDisponibilidadSalvaguarda;
                    }

                    if (valorInt != null && valoracionIntegridadSalvaguarda != null) {
                        valorInt = valorInt * valoracionIntegridadSalvaguarda;
                    }

                    if (valorConf != null && valoracionConfidencialidadSalvaguarda != null) {
                        valorConf = valorConf * valoracionConfidencialidadSalvaguarda;
                    }

                    if (valorAut != null && valoracionAutenticidadSalvaguarda != null) {
                        valorAut = valorAut * valoracionAutenticidadSalvaguarda;
                    }

                    if (valorTraz != null && valoracionTrazabilidadSalvaguarda != null) {
                        valorTraz = valorTraz * valoracionTrazabilidadSalvaguarda;
                    }
                    
                    if (valorDispResult == null)
                        valorDispResult = valorDisp;
                    else {
                        if (valorDisp > valorDispResult)
                            valorDispResult = valorDisp;
                    }
                    
                    if (valorIntResult == null)
                        valorIntResult = valorInt;
                    else {
                        if (valorInt > valorIntResult)
                            valorIntResult = valorInt;
                    }
                    
                    if (valorConfResult == null)
                        valorConfResult = valorConf;
                    else {
                        if (valorConf > valorConfResult)
                            valorConfResult = valorConf;
                    }
                    
                    if (valorAutResult == null)
                        valorAutResult = valorAut;
                    else {
                        if (valorAut > valorAutResult)
                            valorAutResult = valorAut;
                    }

                    if (valorTrazResult == null)
                        valorTrazResult = valorTraz;
                    else {
                        if (valorTraz > valorTrazResult)
                            valorTrazResult = valorTraz;
                    }
                }
            }
            if (valorDispResult != null && valorDispResult == valoracionDisponibilidadActivo)
                valorDispResult = 0;

            if (valorIntResult != null && valorIntResult == valoracionIntegridadActivo)
                valorIntResult = 0;

            if (valorConfResult != null && valorConfResult == valoracionConfidencialidadActivo)
                valorConfResult = 0;

            if (valorAutResult != null && valorAutResult == valoracionAutenticidadActivo)
                valorAutResult = 0;

            if (valorTrazResult != null && valorTrazResult == valoracionTrazabilidadActivo)
                valorTrazResult = 0;

            AnalisisDTO element = new AnalisisDTO(activo.getAsset().getIdActivo(), activo.getAsset().getIdProyecto(),
                    activo.getAsset().getNombreActivo(), activo.getAsset().getCodigoActivo(), valorDispResult,
                    valorIntResult, valorConfResult, valorAutResult,valorTrazResult);

            listaAnalisis.add(element);
        }

        return listaAnalisis;
    }

    public static AnalisisDTO generarDatosAnalisisImpacto(List<AnalisisInfoDTO> dataAnalisis, ParametrizacionAnalisisDTO parametrizacion) {

        return null;
    }

}
