package es.udc.fic.sgsi_magerit.Model.ProjectSizing;

/**
 * Created by err0r on 31/05/2016.
 */
public class ParametrizacionVulnerabilidad {
    private Long idParametrizacionVulnerabilidad;
    private Long idProyecto;
    private Long idTipo;
    private Integer valorTiempo;
    private Integer valorTipoTiempo;
    private Double valor;
    private Boolean activado;

    public ParametrizacionVulnerabilidad(Long idParametrizacionVulnerabilidad, Long idProyecto, Long idTipo, Integer valorTiempo, Integer valorTipoTiempo, Double valor, Boolean activado) {
        this.idParametrizacionVulnerabilidad = idParametrizacionVulnerabilidad;
        this.idProyecto = idProyecto;
        this.idTipo = idTipo;
        this.valorTiempo = valorTiempo;
        this.valorTipoTiempo = valorTipoTiempo;
        this.valor = valor;
        this.activado = activado;
    }

    public Long getIdParametrizacionVulnerabilidad() {
        return idParametrizacionVulnerabilidad;
    }

    public void setIdParametrizacionVulnerabilidad(Long idParametrizacionVulnerabilidad) {
        this.idParametrizacionVulnerabilidad = idParametrizacionVulnerabilidad;
    }

    public Long getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Long idTipo) {
        this.idTipo = idTipo;
    }

    public Integer getValorTiempo() {
        return valorTiempo;
    }

    public void setValorTiempo(Integer valorTiempo) {
        this.valorTiempo = valorTiempo;
    }

    public Integer getValorTipoTiempo() {
        return valorTipoTiempo;
    }

    public void setValorTipoTiempo(Integer valorTipoTiempo) {
        this.valorTipoTiempo = valorTipoTiempo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Boolean getActivado() {
        return activado;
    }

    public void setActivado(Boolean activado) {
        this.activado = activado;
    }
}
