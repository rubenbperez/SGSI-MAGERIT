package es.udc.fic.sgsi_magerit.Model.ProjectSizing;

import java.util.List;

/**
 * Created by err0r on 04/06/2016.
 */
public class ParametrizacionDTO {
    private List<ParametrizacionActivo> pActivo;
    private List<ParametrizacionVulnerabilidad> pVulnerabilidad;
    private List<ParametrizacionImpacto> pImpacto;
    private List<ParametrizacionControlSeguridad> pControlSeguridad;

    public ParametrizacionDTO(List<ParametrizacionActivo> pActivo, List<ParametrizacionVulnerabilidad>
            pVulnerabilidad, List<ParametrizacionImpacto> pImpacto, List<ParametrizacionControlSeguridad>
            pControlSeguridad) {
        this.pActivo = pActivo;
        this.pVulnerabilidad = pVulnerabilidad;
        this.pImpacto = pImpacto;
        this.pControlSeguridad = pControlSeguridad;
    }

    public List<ParametrizacionActivo> getpActivo() {
        return pActivo;
    }

    public void setpActivo(List<ParametrizacionActivo> pActivo) {
        this.pActivo = pActivo;
    }

    public List<ParametrizacionVulnerabilidad> getpVulnerabilidad() {
        return pVulnerabilidad;
    }

    public void setpVulnerabilidad(List<ParametrizacionVulnerabilidad> pVulnerabilidad) {
        this.pVulnerabilidad = pVulnerabilidad;
    }

    public List<ParametrizacionImpacto> getpImpacto() {
        return pImpacto;
    }

    public void setpImpacto(List<ParametrizacionImpacto> pImpacto) {
        this.pImpacto = pImpacto;
    }

    public List<ParametrizacionControlSeguridad> getpControlSeguridad() {
        return pControlSeguridad;
    }

    public void setpControlSeguridad(List<ParametrizacionControlSeguridad> pControlSeguridad) {
        this.pControlSeguridad = pControlSeguridad;
    }
}
