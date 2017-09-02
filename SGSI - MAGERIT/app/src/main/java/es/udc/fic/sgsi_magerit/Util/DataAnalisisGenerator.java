package es.udc.fic.sgsi_magerit.Util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import es.udc.fic.sgsi_magerit.Model.Analisis.AnalisisDTO;
import es.udc.fic.sgsi_magerit.Model.Analisis.AnalisisInfoDTO;
import es.udc.fic.sgsi_magerit.Model.Analisis.ParametrizacionAnalisisDTO;
import es.udc.fic.sgsi_magerit.Model.Analisis.ParametrizacionAnalisisInfoDTO;
import es.udc.fic.sgsi_magerit.Model.Analisis.SafeguardAnalisisDTO;
import es.udc.fic.sgsi_magerit.Model.Analisis.ThreatAnalisisDTO;

/**
 * Created by err0r on 30/08/2017.
 */
public class DataAnalisisGenerator {


    private static List<SafeguardAnalisisDTO> obtenerSalvaguardasValidasDeAmenaza(Long idThreat, List<SafeguardAnalisisDTO> safeguards, int tipoProteccion) {

        List<SafeguardAnalisisDTO> threatSafeguards = new ArrayList<>();

        for (SafeguardAnalisisDTO safeguard : safeguards) {
            if  (safeguard.getIdThreat() == idThreat && safeguard.getTipoProteccion() != null &&
                    safeguard.getTipoProteccion() == tipoProteccion)
                threatSafeguards.add(safeguard);
        }
        return threatSafeguards;
    }
    
    public static List<AnalisisDTO> generarDatosAnalisisRiesgo(List<AnalisisInfoDTO> dataAnalisis, ParametrizacionAnalisisDTO parametrizacion) {

        List<AnalisisDTO> listaAnalisis = new ArrayList<>();

        for (AnalisisInfoDTO activo : dataAnalisis) {

            Double valorDispMax = null;
            Double valorIntMax = null;
            Double valorConfMax = null;
            Double valorAutMax = null;
            Double valorTrazMax = null;

            List<ParametrizacionAnalisisInfoDTO> parametrizacionActivo = parametrizacion.getParametrizacionActivo();

            Double valoracionDisponibilidadActivo = null;
            Double valoracionIntegridadActivo = null;
            Double valoracionConfidencialidadActivo = null;
            Double valoracionAutenticidadActivo = null;
            Double valoracionTrazabilidadActivo = null;


            if (activo.getAsset().getIdValoracionDisp() != null && parametrizacionActivo.get(activo.getAsset().getIdValoracionDisp()).getActivado())
                valoracionDisponibilidadActivo =  parametrizacionActivo.get(activo.getAsset().getIdValoracionDisp()).getValor();

            if (activo.getAsset().getIdValoracionInt() != null && parametrizacionActivo.get(activo.getAsset().getIdValoracionInt()).getActivado())
                valoracionIntegridadActivo = parametrizacionActivo.get(activo.getAsset().getIdValoracionInt()).getValor();

            if (activo.getAsset().getIdValoracionConf() != null && parametrizacionActivo.get(activo.getAsset().getIdValoracionConf()).getActivado())
                valoracionConfidencialidadActivo = parametrizacionActivo.get(activo.getAsset().getIdValoracionConf()).getValor();

            if (activo.getAsset().getIdValoracionAut() != null && parametrizacionActivo.get(activo.getAsset().getIdValoracionAut()).getActivado())
                valoracionAutenticidadActivo = parametrizacionActivo.get(activo.getAsset().getIdValoracionAut()).getValor();

            if (activo.getAsset().getIdValoracionTraz() != null  && parametrizacionActivo.get(activo.getAsset().getIdValoracionTraz()).getActivado())
                valoracionTrazabilidadActivo = parametrizacionActivo.get(activo.getAsset().getIdValoracionTraz()).getValor();

            for (ThreatAnalisisDTO amenaza : activo.getThreats()) {

                Double valorDispParcial = null;
                Double valorIntParcial = null;
                Double valorConfParcial = null;
                Double valorAutParcial = null;
                Double valorTrazParcial = null;

                List<ParametrizacionAnalisisInfoDTO> parametrizacionAmenazaImpacto = parametrizacion.getParametrizacionImpacto();
                List<ParametrizacionAnalisisInfoDTO> parametrizacionAmenazaVulnerabilidad = parametrizacion.getParametrizacionVulnerabilidad();

                Double valoracionDegradacionDisponibilidadAmenaza = null;
                Double valoracionProbabilidadDisponibilidadAmenaza = null;
                if (amenaza.getIdDegradacionDisponibilidad() != null && parametrizacionAmenazaImpacto.get(amenaza.getIdDegradacionDisponibilidad()).getActivado())
                    valoracionDegradacionDisponibilidadAmenaza = parametrizacionAmenazaImpacto.get(amenaza.getIdDegradacionDisponibilidad()).getValor();
                if (amenaza.getIdProbabilidadDisponibilidad() != null && parametrizacionAmenazaVulnerabilidad.get(amenaza.getIdProbabilidadDisponibilidad()).getActivado())
                    valoracionProbabilidadDisponibilidadAmenaza =  parametrizacionAmenazaVulnerabilidad.get(amenaza.getIdProbabilidadDisponibilidad()).getValor();

                Double valoracionDegradacionIntegridadAmenaza = null;
                Double valoracionProbabilidadIntegridadAmenaza = null;
                if (amenaza.getIdDegradacionIntegridad() != null && parametrizacionAmenazaImpacto.get(amenaza.getIdDegradacionIntegridad()).getActivado())
                    valoracionDegradacionIntegridadAmenaza = parametrizacionAmenazaImpacto.get(amenaza.getIdDegradacionIntegridad()).getValor();
                if(amenaza.getIdProbabilidadIntegridad() != null && parametrizacionAmenazaVulnerabilidad.get(amenaza.getIdProbabilidadIntegridad()).getActivado())
                    valoracionProbabilidadIntegridadAmenaza = parametrizacionAmenazaVulnerabilidad.get(amenaza.getIdProbabilidadIntegridad()).getValor();

                Double valoracionDegradacionConfidencialidadAmenaza = null;
                Double valoracionProbabilidadConfidencialidadAmenaza = null;
                if (amenaza.getIdDegradacionConfidencialidad() != null && parametrizacionAmenazaImpacto.get(amenaza.getIdDegradacionConfidencialidad()).getActivado())
                    valoracionDegradacionConfidencialidadAmenaza = parametrizacionAmenazaImpacto.get(amenaza.getIdDegradacionConfidencialidad()).getValor();
                if (amenaza.getIdProbabilidadConfidencialidad() != null && parametrizacionAmenazaVulnerabilidad.get(amenaza.getIdProbabilidadConfidencialidad()).getActivado())
                    valoracionProbabilidadConfidencialidadAmenaza = parametrizacionAmenazaVulnerabilidad.get(amenaza.getIdProbabilidadConfidencialidad()).getValor();

                Double valoracionDegradacionAutenticidadAmenaza = null;
                Double valoracionProbabilidadAutenticidadAmenaza = null;
                if (amenaza.getIdDegradacionAutenticidad() != null && parametrizacionAmenazaImpacto.get(amenaza.getIdDegradacionAutenticidad()).getActivado())
                    valoracionDegradacionAutenticidadAmenaza = parametrizacionAmenazaImpacto.get(amenaza.getIdDegradacionAutenticidad()).getValor();
                if (amenaza.getIdProbabilidadAutenticidad() != null && parametrizacionAmenazaVulnerabilidad.get(amenaza.getIdProbabilidadAutenticidad()).getActivado())
                    valoracionProbabilidadAutenticidadAmenaza = parametrizacionAmenazaVulnerabilidad.get(amenaza.getIdProbabilidadAutenticidad()).getValor();

                Double valoracionDegradacionTrazabilidadAmenaza = null;
                Double valoracionProbabilidadTrazabilidadAmenaza = null;

                if (amenaza.getIdDegradacionTrazabilidad() != null && parametrizacionAmenazaImpacto.get(amenaza.getIdDegradacionTrazabilidad()).getActivado())
                    valoracionDegradacionTrazabilidadAmenaza = parametrizacionAmenazaImpacto.get(amenaza.getIdDegradacionTrazabilidad()).getValor();
                if (amenaza.getIdProbabilidadTrazabilidad() != null && parametrizacionAmenazaVulnerabilidad.get(amenaza.getIdProbabilidadTrazabilidad()).getActivado())
                    valoracionProbabilidadTrazabilidadAmenaza = parametrizacionAmenazaVulnerabilidad.get(amenaza.getIdProbabilidadTrazabilidad()).getValor();

                List<SafeguardAnalisisDTO> safeguardsAmenaza = obtenerSalvaguardasValidasDeAmenaza(amenaza.getIdThreat(), activo.getSafeguards(),0);

                if (valoracionDisponibilidadActivo != null && valoracionDegradacionDisponibilidadAmenaza != null &&
                        valoracionProbabilidadDisponibilidadAmenaza != null) {
                    valorDispParcial = valoracionDisponibilidadActivo * (valoracionDegradacionDisponibilidadAmenaza / 100) * valoracionProbabilidadDisponibilidadAmenaza;
                }
                if (valoracionIntegridadActivo != null && valoracionDegradacionIntegridadAmenaza != null &&
                        valoracionProbabilidadIntegridadAmenaza != null) {
                    valorIntParcial = valoracionIntegridadActivo * (valoracionDegradacionIntegridadAmenaza / 100) * valoracionProbabilidadIntegridadAmenaza;
                }

                if (valoracionConfidencialidadActivo != null && valoracionDegradacionConfidencialidadAmenaza != null &&
                        valoracionProbabilidadConfidencialidadAmenaza != null) {
                    valorConfParcial = valoracionConfidencialidadActivo * (valoracionDegradacionConfidencialidadAmenaza / 100) * valoracionProbabilidadConfidencialidadAmenaza;
                }

                if (valoracionAutenticidadActivo != null && valoracionDegradacionAutenticidadAmenaza != null &&
                        valoracionProbabilidadAutenticidadAmenaza != null) {
                    valorAutParcial = valoracionAutenticidadActivo * ( valoracionDegradacionAutenticidadAmenaza / 100) * valoracionProbabilidadAutenticidadAmenaza;
                }

                if (valoracionTrazabilidadActivo != null && valoracionDegradacionTrazabilidadAmenaza != null &&
                        valoracionProbabilidadTrazabilidadAmenaza != null) {
                    valorTrazParcial = valoracionTrazabilidadActivo * (valoracionDegradacionTrazabilidadAmenaza / 100) * valoracionProbabilidadTrazabilidadAmenaza;
                }

                if (safeguardsAmenaza == null || safeguardsAmenaza.isEmpty()) {
                    if (valorDispMax == null || (valorDispParcial != null && valorDispMax < valorDispParcial)) {
                        valorDispMax = valorDispParcial;
                    }

                    if (valorIntMax == null || (valorIntParcial != null && valorIntMax < valorIntParcial)) {
                        valorIntMax = valorIntParcial;
                    }

                    if (valorConfMax == null || (valorConfParcial != null && valorConfMax < valorConfParcial)) {
                        valorConfMax = valorConfParcial;
                    }

                    if (valorAutMax == null || (valorAutParcial != null && valorAutMax < valorAutParcial)) {
                        valorAutMax = valorAutParcial;
                    }

                    if (valorTrazMax == null || (valorTrazParcial != null && valorTrazMax < valorTrazParcial)) {
                        valorTrazMax = valorTrazParcial;
                    }
                } else {

                    for (SafeguardAnalisisDTO salvaguarda : safeguardsAmenaza) {

                        List<ParametrizacionAnalisisInfoDTO> parametrizacionSalvaguarda = parametrizacion.getParametrizacionControlSeguridad();

                        Double valoracionDisponibilidadSalvaguarda = null;
                        Double valoracionIntegridadSalvaguarda = null;
                        Double valoracionConfidencialidadSalvaguarda = null;
                        Double valoracionAutenticidadSalvaguarda = null;
                        Double valoracionTrazabilidadSalvaguarda = null;

                        Double valorDispParcial2 = valorDispParcial;
                        Double valorIntParcial2 = valorIntParcial;
                        Double valorConfParcial2 = valorConfParcial;
                        Double valorAutParcial2 = valorAutParcial;
                        Double valorTrazParcial2 = valorTrazParcial;


                        if (salvaguarda.getIdControlSeguridadDisponibilidad() != null && parametrizacionSalvaguarda.get(salvaguarda.getIdControlSeguridadDisponibilidad()).getActivado())
                            valoracionDisponibilidadSalvaguarda = parametrizacionSalvaguarda.get(salvaguarda.getIdControlSeguridadDisponibilidad()).getValor();

                        if (salvaguarda.getIdControlSeguridadIntegridad() != null && parametrizacionSalvaguarda.get(salvaguarda.getIdControlSeguridadIntegridad()).getActivado())
                            valoracionIntegridadSalvaguarda = parametrizacionSalvaguarda.get(salvaguarda.getIdControlSeguridadIntegridad()).getValor();

                        if (salvaguarda.getIdControlSeguridadConfidencialidad() != null && parametrizacionSalvaguarda.get(salvaguarda.getIdControlSeguridadConfidencialidad()).getActivado())
                            valoracionConfidencialidadSalvaguarda = parametrizacionSalvaguarda.get(salvaguarda.getIdControlSeguridadConfidencialidad()).getValor();

                        if (salvaguarda.getIdControlSeguridadAutenticidad() != null && parametrizacionSalvaguarda.get(salvaguarda.getIdControlSeguridadAutenticidad()).getActivado())
                            valoracionAutenticidadSalvaguarda = parametrizacionSalvaguarda.get(salvaguarda.getIdControlSeguridadAutenticidad()).getValor();

                        if (salvaguarda.getIdControlSeguridadTrazabilidad() != null && parametrizacionSalvaguarda.get(salvaguarda.getIdControlSeguridadTrazabilidad()).getActivado())
                            valoracionTrazabilidadSalvaguarda = parametrizacionSalvaguarda.get(salvaguarda.getIdControlSeguridadTrazabilidad()).getValor();

                        if (valorDispParcial2 != null && valoracionDisponibilidadSalvaguarda != null) {
                            valorDispParcial2 = valorDispParcial * ((100 - valoracionDisponibilidadSalvaguarda) / 100);
                        }

                        if (valorIntParcial != null && valoracionIntegridadSalvaguarda != null) {
                            valorIntParcial2 = valorIntParcial * ((100 - valoracionIntegridadSalvaguarda) / 100);
                        }

                        if (valorConfParcial != null && valoracionConfidencialidadSalvaguarda != null) {
                            valorConfParcial2 = valorConfParcial * ((100 - valoracionConfidencialidadSalvaguarda) / 100);
                        }

                        if (valorAutParcial != null && valoracionAutenticidadSalvaguarda != null) {
                            valorAutParcial2 = valorAutParcial * ((100 - valoracionAutenticidadSalvaguarda) / 100);
                        }

                        if (valorTrazParcial != null && valoracionTrazabilidadSalvaguarda != null) {
                            valorTrazParcial2 = valorTrazParcial * ((100 - valoracionAutenticidadSalvaguarda) / 100);
                        }

                        if (valorDispMax == null || (valorDispParcial2 != null && valorDispMax < valorDispParcial2)) {
                            valorDispMax = valorDispParcial2;
                        }

                        if (valorIntMax == null || (valorIntParcial2 != null && valorIntMax < valorIntParcial2)) {
                            valorIntMax = valorIntParcial2;
                        }

                        if (valorConfMax == null || (valorConfParcial2 != null && valorConfMax < valorConfParcial2)) {
                            valorConfMax = valorConfParcial2;
                        }

                        if (valorAutMax == null || (valorAutParcial2 != null && valorAutMax < valorAutParcial2)) {
                            valorAutMax = valorAutParcial2;
                        }

                        if (valorTrazMax == null || (valorTrazParcial2 != null && valorTrazMax < valorTrazParcial2)) {
                            valorTrazMax = valorTrazParcial2;
                        }
                    }

                }
            }

            if (valorDispMax == null && valoracionDisponibilidadActivo != null)
                valorDispMax = new Double(0);

            if (valorIntMax == null && valoracionIntegridadActivo != null)
                valorIntMax = new Double(0);

            if (valorConfMax == null && valoracionConfidencialidadActivo != null)
                valorConfMax = new Double(0);

            if (valorAutMax == null && valoracionAutenticidadActivo != null)
                valorAutMax = new Double(0);

            if (valorTrazMax == null && valoracionTrazabilidadActivo != null)
                valorTrazMax = new Double(0);

            AnalisisDTO element = new AnalisisDTO(activo.getAsset().getIdActivo(), activo.getAsset().getIdProyecto(),
                    activo.getAsset().getNombreActivo(), activo.getAsset().getCodigoActivo(), valorDispMax != null ? valorDispMax.longValue():null,
                    valorIntMax!=null? valorIntMax.longValue():null, valorConfMax!=null?valorConfMax.longValue():null,
                    valorAutMax!=null ? valorAutMax.longValue():null, valorTrazMax!=null ? valorTrazMax.longValue():null);

            listaAnalisis.add(element);
        }
        return listaAnalisis;
    }
    
    public static List<AnalisisDTO> generarDatosAnalisisImpacto(List<AnalisisInfoDTO> dataAnalisis, ParametrizacionAnalisisDTO parametrizacion) {

        List<AnalisisDTO> listaAnalisis = new ArrayList<>();

        for (AnalisisInfoDTO activo : dataAnalisis) {

            Double valorDispMax = null;
            Double valorIntMax = null;
            Double valorConfMax = null;
            Double valorAutMax = null;
            Double valorTrazMax = null;

            List<ParametrizacionAnalisisInfoDTO> parametrizacionActivo = parametrizacion.getParametrizacionActivo();

            Double valoracionDisponibilidadActivo = null;
            Double valoracionIntegridadActivo = null;
            Double valoracionConfidencialidadActivo = null;
            Double valoracionAutenticidadActivo = null;
            Double valoracionTrazabilidadActivo = null;


            if (activo.getAsset().getIdValoracionDisp() != null && parametrizacionActivo.get(activo.getAsset().getIdValoracionDisp()).getActivado())
                valoracionDisponibilidadActivo = parametrizacionActivo.get(activo.getAsset().getIdValoracionDisp()).getValor();

            if (activo.getAsset().getIdValoracionInt() != null && parametrizacionActivo.get(activo.getAsset().getIdValoracionInt()).getActivado())
                valoracionIntegridadActivo = parametrizacionActivo.get(activo.getAsset().getIdValoracionInt()).getValor();

            if (activo.getAsset().getIdValoracionConf() != null && parametrizacionActivo.get(activo.getAsset().getIdValoracionConf()).getActivado())
                valoracionConfidencialidadActivo = parametrizacionActivo.get(activo.getAsset().getIdValoracionConf()).getValor();

            if (activo.getAsset().getIdValoracionAut() != null && parametrizacionActivo.get(activo.getAsset().getIdValoracionAut()).getActivado())
                valoracionAutenticidadActivo = parametrizacionActivo.get(activo.getAsset().getIdValoracionAut()).getValor();

            if (activo.getAsset().getIdValoracionTraz() != null && parametrizacionActivo.get(activo.getAsset().getIdValoracionTraz()).getActivado())
                valoracionTrazabilidadActivo = parametrizacionActivo.get(activo.getAsset().getIdValoracionTraz()).getValor();

            for (ThreatAnalisisDTO amenaza : activo.getThreats()) {

                Double valorDispParcial = null;
                Double valorIntParcial = null;
                Double valorConfParcial = null;
                Double valorAutParcial = null;
                Double valorTrazParcial = null;

                List<ParametrizacionAnalisisInfoDTO> parametrizacionAmenazaImpacto = parametrizacion.getParametrizacionImpacto();
                List<ParametrizacionAnalisisInfoDTO> parametrizacionAmenazaVulnerabilidad = parametrizacion.getParametrizacionVulnerabilidad();

                Double valoracionDegradacionDisponibilidadAmenaza = null;
                Double valoracionProbabilidadDisponibilidadAmenaza = null;
                if (amenaza.getIdDegradacionDisponibilidad() != null && parametrizacionAmenazaImpacto.get(amenaza.getIdDegradacionDisponibilidad()).getActivado())
                    valoracionDegradacionDisponibilidadAmenaza = parametrizacionAmenazaImpacto.get(amenaza.getIdDegradacionDisponibilidad()).getValor();
                if (amenaza.getIdProbabilidadDisponibilidad() != null && parametrizacionAmenazaVulnerabilidad.get(amenaza.getIdProbabilidadDisponibilidad()).getActivado())
                    valoracionProbabilidadDisponibilidadAmenaza = parametrizacionAmenazaVulnerabilidad.get(amenaza.getIdProbabilidadDisponibilidad()).getValor();

                Double valoracionDegradacionIntegridadAmenaza = null;
                Double valoracionProbabilidadIntegridadAmenaza = null;
                if (amenaza.getIdDegradacionIntegridad() != null && parametrizacionAmenazaImpacto.get(amenaza.getIdDegradacionIntegridad()).getActivado())
                    valoracionDegradacionIntegridadAmenaza = parametrizacionAmenazaImpacto.get(amenaza.getIdDegradacionIntegridad()).getValor();
                if (amenaza.getIdProbabilidadIntegridad() != null && parametrizacionAmenazaVulnerabilidad.get(amenaza.getIdProbabilidadIntegridad()).getActivado())
                    valoracionProbabilidadIntegridadAmenaza = parametrizacionAmenazaVulnerabilidad.get(amenaza.getIdProbabilidadIntegridad()).getValor();

                Double valoracionDegradacionConfidencialidadAmenaza = null;
                Double valoracionProbabilidadConfidencialidadAmenaza = null;
                if (amenaza.getIdDegradacionConfidencialidad() != null && parametrizacionAmenazaImpacto.get(amenaza.getIdDegradacionConfidencialidad()).getActivado())
                    valoracionDegradacionConfidencialidadAmenaza = parametrizacionAmenazaImpacto.get(amenaza.getIdDegradacionConfidencialidad()).getValor();
                if (amenaza.getIdProbabilidadConfidencialidad() != null && parametrizacionAmenazaVulnerabilidad.get(amenaza.getIdProbabilidadConfidencialidad()).getActivado())
                    valoracionProbabilidadConfidencialidadAmenaza = parametrizacionAmenazaVulnerabilidad.get(amenaza.getIdProbabilidadConfidencialidad()).getValor();

                Double valoracionDegradacionAutenticidadAmenaza = null;
                Double valoracionProbabilidadAutenticidadAmenaza = null;
                if (amenaza.getIdDegradacionAutenticidad() != null && parametrizacionAmenazaImpacto.get(amenaza.getIdDegradacionAutenticidad()).getActivado())
                    valoracionDegradacionAutenticidadAmenaza = parametrizacionAmenazaImpacto.get(amenaza.getIdDegradacionAutenticidad()).getValor();
                if (amenaza.getIdProbabilidadAutenticidad() != null && parametrizacionAmenazaVulnerabilidad.get(amenaza.getIdProbabilidadAutenticidad()).getActivado())
                    valoracionProbabilidadAutenticidadAmenaza = parametrizacionAmenazaVulnerabilidad.get(amenaza.getIdProbabilidadAutenticidad()).getValor();

                Double valoracionDegradacionTrazabilidadAmenaza = null;
                Double valoracionProbabilidadTrazabilidadAmenaza = null;

                if (amenaza.getIdDegradacionTrazabilidad() != null && parametrizacionAmenazaImpacto.get(amenaza.getIdDegradacionTrazabilidad()).getActivado())
                    valoracionDegradacionTrazabilidadAmenaza = parametrizacionAmenazaImpacto.get(amenaza.getIdDegradacionTrazabilidad()).getValor();
                if (amenaza.getIdProbabilidadTrazabilidad() != null && parametrizacionAmenazaVulnerabilidad.get(amenaza.getIdProbabilidadTrazabilidad()).getActivado())
                    valoracionProbabilidadTrazabilidadAmenaza = parametrizacionAmenazaVulnerabilidad.get(amenaza.getIdProbabilidadTrazabilidad()).getValor();

                List<SafeguardAnalisisDTO> safeguardsAmenaza = obtenerSalvaguardasValidasDeAmenaza(amenaza.getIdThreat(), activo.getSafeguards(), 1);

                if (valoracionDisponibilidadActivo != null && valoracionDegradacionDisponibilidadAmenaza != null &&
                        valoracionProbabilidadDisponibilidadAmenaza != null) {
                    valorDispParcial = valoracionDisponibilidadActivo * (valoracionDegradacionDisponibilidadAmenaza / 100);
                }
                if (valoracionIntegridadActivo != null && valoracionDegradacionIntegridadAmenaza != null &&
                        valoracionProbabilidadIntegridadAmenaza != null) {
                    valorIntParcial = valoracionIntegridadActivo * (valoracionDegradacionIntegridadAmenaza / 100);
                }

                if (valoracionConfidencialidadActivo != null && valoracionDegradacionConfidencialidadAmenaza != null &&
                        valoracionProbabilidadConfidencialidadAmenaza != null) {
                    valorConfParcial = valoracionConfidencialidadActivo * (valoracionDegradacionConfidencialidadAmenaza / 100);
                }

                if (valoracionAutenticidadActivo != null && valoracionDegradacionAutenticidadAmenaza != null &&
                        valoracionProbabilidadAutenticidadAmenaza != null) {
                    valorAutParcial = valoracionAutenticidadActivo * (valoracionDegradacionAutenticidadAmenaza / 100);
                }

                if (valoracionTrazabilidadActivo != null && valoracionDegradacionTrazabilidadAmenaza != null &&
                        valoracionProbabilidadTrazabilidadAmenaza != null) {
                    valorTrazParcial = valoracionTrazabilidadActivo * (valoracionDegradacionTrazabilidadAmenaza / 100);
                }

                if (safeguardsAmenaza == null || safeguardsAmenaza.isEmpty()) {
                    if (valorDispMax == null || (valorDispParcial != null && valorDispMax < valorDispParcial)) {
                        valorDispMax = valorDispParcial;
                    }

                    if (valorIntMax == null || (valorIntParcial != null && valorIntMax < valorIntParcial)) {
                        valorIntMax = valorIntParcial;
                    }

                    if (valorConfMax == null || (valorConfParcial != null && valorConfMax < valorConfParcial)) {
                        valorConfMax = valorConfParcial;
                    }

                    if (valorAutMax == null || (valorAutParcial != null && valorAutMax < valorAutParcial)) {
                        valorAutMax = valorAutParcial;
                    }

                    if (valorTrazMax == null || (valorTrazParcial != null && valorTrazMax < valorTrazParcial)) {
                        valorTrazMax = valorTrazParcial;
                    }
                } else {

                    for (SafeguardAnalisisDTO salvaguarda : safeguardsAmenaza) {

                        List<ParametrizacionAnalisisInfoDTO> parametrizacionSalvaguarda = parametrizacion.getParametrizacionControlSeguridad();

                        Double valoracionDisponibilidadSalvaguarda = null;
                        Double valoracionIntegridadSalvaguarda = null;
                        Double valoracionConfidencialidadSalvaguarda = null;
                        Double valoracionAutenticidadSalvaguarda = null;
                        Double valoracionTrazabilidadSalvaguarda = null;

                        Double valorDispParcial2 = valorDispParcial;
                        Double valorIntParcial2 = valorIntParcial;
                        Double valorConfParcial2 = valorConfParcial;
                        Double valorAutParcial2 = valorAutParcial;
                        Double valorTrazParcial2 = valorTrazParcial;


                        if (salvaguarda.getIdControlSeguridadDisponibilidad() != null && parametrizacionSalvaguarda.get(salvaguarda.getIdControlSeguridadDisponibilidad()).getActivado())
                            valoracionDisponibilidadSalvaguarda = parametrizacionSalvaguarda.get(salvaguarda.getIdControlSeguridadDisponibilidad()).getValor();

                        if (salvaguarda.getIdControlSeguridadIntegridad() != null && parametrizacionSalvaguarda.get(salvaguarda.getIdControlSeguridadIntegridad()).getActivado())
                            valoracionIntegridadSalvaguarda = parametrizacionSalvaguarda.get(salvaguarda.getIdControlSeguridadIntegridad()).getValor();

                        if (salvaguarda.getIdControlSeguridadConfidencialidad() != null && parametrizacionSalvaguarda.get(salvaguarda.getIdControlSeguridadConfidencialidad()).getActivado())
                            valoracionConfidencialidadSalvaguarda = parametrizacionSalvaguarda.get(salvaguarda.getIdControlSeguridadConfidencialidad()).getValor();

                        if (salvaguarda.getIdControlSeguridadAutenticidad() != null && parametrizacionSalvaguarda.get(salvaguarda.getIdControlSeguridadAutenticidad()).getActivado())
                            valoracionAutenticidadSalvaguarda = parametrizacionSalvaguarda.get(salvaguarda.getIdControlSeguridadAutenticidad()).getValor();

                        if (salvaguarda.getIdControlSeguridadTrazabilidad() != null && parametrizacionSalvaguarda.get(salvaguarda.getIdControlSeguridadTrazabilidad()).getActivado())
                            valoracionTrazabilidadSalvaguarda = parametrizacionSalvaguarda.get(salvaguarda.getIdControlSeguridadTrazabilidad()).getValor();

                        if (valorDispParcial2 != null && valoracionDisponibilidadSalvaguarda != null) {
                            valorDispParcial2 = valorDispParcial * ((100 - valoracionDisponibilidadSalvaguarda) / 100);
                        }

                        if (valorIntParcial != null && valoracionIntegridadSalvaguarda != null) {
                            valorIntParcial2 = valorIntParcial * ((100 - valoracionIntegridadSalvaguarda) / 100);
                        }

                        if (valorConfParcial != null && valoracionConfidencialidadSalvaguarda != null) {
                            valorConfParcial2 = valorConfParcial * ((100 - valoracionConfidencialidadSalvaguarda) / 100);
                        }

                        if (valorAutParcial != null && valoracionAutenticidadSalvaguarda != null) {
                            valorAutParcial2 = valorAutParcial * ((100 - valoracionAutenticidadSalvaguarda) / 100);
                        }

                        if (valorTrazParcial != null && valoracionTrazabilidadSalvaguarda != null) {
                            valorTrazParcial2 = valorTrazParcial * ((100 - valoracionAutenticidadSalvaguarda) / 100);
                        }

                        if (valorDispMax == null || (valorDispParcial2 != null && valorDispMax < valorDispParcial2)) {
                            valorDispMax = valorDispParcial2;
                        }

                        if (valorIntMax == null || (valorIntParcial2 != null && valorIntMax < valorIntParcial2)) {
                            valorIntMax = valorIntParcial2;
                        }

                        if (valorConfMax == null || (valorConfParcial2 != null && valorConfMax < valorConfParcial2)) {
                            valorConfMax = valorConfParcial2;
                        }

                        if (valorAutMax == null || (valorAutParcial2 != null && valorAutMax < valorAutParcial2)) {
                            valorAutMax = valorAutParcial2;
                        }

                        if (valorTrazMax == null || (valorTrazParcial2 != null && valorTrazMax < valorTrazParcial2)) {
                            valorTrazMax = valorTrazParcial2;
                        }
                    }

                }

            }

            if (valorDispMax == null && valoracionDisponibilidadActivo != null)
                valorDispMax = new Double(0);

            if (valorIntMax == null && valoracionIntegridadActivo != null)
                valorIntMax = new Double(0);

            if (valorConfMax == null && valoracionConfidencialidadActivo != null)
                valorConfMax = new Double(0);

            if (valorAutMax == null && valoracionAutenticidadActivo != null)
                valorAutMax = new Double(0);

            if (valorTrazMax == null && valoracionTrazabilidadActivo != null)
                valorTrazMax = new Double(0);

            AnalisisDTO element = new AnalisisDTO(activo.getAsset().getIdActivo(), activo.getAsset().getIdProyecto(),
                    activo.getAsset().getNombreActivo(), activo.getAsset().getCodigoActivo(), valorDispMax != null ? valorDispMax.longValue() : null,
                    valorIntMax != null ? valorIntMax.longValue() : null, valorConfMax != null ? valorConfMax.longValue() : null,
                    valorAutMax != null ? valorAutMax.longValue() : null, valorTrazMax != null ? valorTrazMax.longValue() : null);

            listaAnalisis.add(element);
        }
        return listaAnalisis;
    }


    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();
    static {
        suffixes.put(1_000L, "K");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "G");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_000_000L, "E");
    }

    public static String formatIntegerPortrait(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return formatIntegerPortrait(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + formatIntegerPortrait(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }


    public static String formatIntegerLandscape (long value) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(value);
    }

}
