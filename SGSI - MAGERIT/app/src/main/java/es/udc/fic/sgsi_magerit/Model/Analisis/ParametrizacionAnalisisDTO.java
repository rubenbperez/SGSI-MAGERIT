package es.udc.fic.sgsi_magerit.Model.Analisis;

import java.util.List;

/**
 * Created by err0r on 30/08/2017.
 */
public class ParametrizacionAnalisisDTO {

    List<ParametrizacionAnalisisInfoDTO> parametrizacionActivo;
    List<ParametrizacionAnalisisInfoDTO> parametrizacionVulnerabilidad;
    List<ParametrizacionAnalisisInfoDTO> parametrizacionImpacto;
    List<ParametrizacionAnalisisInfoDTO> parametrizacionControlSeguridad;

    public ParametrizacionAnalisisDTO(List<ParametrizacionAnalisisInfoDTO> parametrizacionActivo,
                                      List<ParametrizacionAnalisisInfoDTO> parametrizacionVulnerabilidad,
                                      List<ParametrizacionAnalisisInfoDTO> parametrizacionImpacto,
                                      List<ParametrizacionAnalisisInfoDTO> parametrizacionControlSeguridad) {
        this.parametrizacionActivo = parametrizacionActivo;
        this.parametrizacionVulnerabilidad = parametrizacionVulnerabilidad;
        this.parametrizacionImpacto = parametrizacionImpacto;
        this.parametrizacionControlSeguridad = parametrizacionControlSeguridad;
    }

    public List<ParametrizacionAnalisisInfoDTO> getParametrizacionActivo() {
        return parametrizacionActivo;
    }

    public void setParametrizacionActivo(List<ParametrizacionAnalisisInfoDTO> parametrizacionActivo) {
        this.parametrizacionActivo = parametrizacionActivo;
    }

    public List<ParametrizacionAnalisisInfoDTO> getParametrizacionVulnerabilidad() {
        return parametrizacionVulnerabilidad;
    }

    public void setParametrizacionVulnerabilidad(List<ParametrizacionAnalisisInfoDTO> parametrizacionVulnerabilidad) {
        this.parametrizacionVulnerabilidad = parametrizacionVulnerabilidad;
    }

    public List<ParametrizacionAnalisisInfoDTO> getParametrizacionImpacto() {
        return parametrizacionImpacto;
    }

    public void setParametrizacionImpacto(List<ParametrizacionAnalisisInfoDTO> parametrizacionImpacto) {
        this.parametrizacionImpacto = parametrizacionImpacto;
    }

    public List<ParametrizacionAnalisisInfoDTO> getParametrizacionControlSeguridad() {
        return parametrizacionControlSeguridad;
    }

    public void setParametrizacionControlSeguridad(List<ParametrizacionAnalisisInfoDTO> parametrizacionControlSeguridad) {
        this.parametrizacionControlSeguridad = parametrizacionControlSeguridad;
    }
}


