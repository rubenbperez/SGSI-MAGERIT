package es.udc.fic.sgsi_magerit.Model.ProjectSizing;

/**
 * Created by err0r on 31/05/2016.
 */
public class ParametrizacionActivo {
    private Long idParametrizacionActivo;
    private Long idProyecto;
    private Long idTipo;
    private Integer rangoSuperior;
    private Integer rangoInferior;
    private Integer valor;
    private Boolean activado;

    public ParametrizacionActivo(Long idParametrizacionActivo, Long idProyecto, Long idTipo, Integer rangoSuperior, Integer rangoInferior, Integer valor, Boolean activado) {
        this.idParametrizacionActivo = idParametrizacionActivo;
        this.idProyecto = idProyecto;
        this.idTipo = idTipo;
        this.rangoSuperior = rangoSuperior;
        this.rangoInferior = rangoInferior;
        this.valor = valor;
        this.activado = activado;
    }

    public Long getIdParametrizacionActivo() {
        return idParametrizacionActivo;
    }

    public void setIdParametrizacionActivo(Long idParametrizacionActivo) {
        this.idParametrizacionActivo = idParametrizacionActivo;
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

    public Integer getRangoSuperior() {
        return rangoSuperior;
    }

    public void setRangoSuperior(Integer rangoSuperior) {
        this.rangoSuperior = rangoSuperior;
    }

    public Integer getRangoInferior() {
        return rangoInferior;
    }

    public void setRangoInferior(Integer rangoInferior) {
        this.rangoInferior = rangoInferior;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Boolean getActivado() {
        return activado;
    }

    public void setActivado(Boolean activado) {
        this.activado = activado;
    }
}
