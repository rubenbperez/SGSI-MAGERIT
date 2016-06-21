package es.udc.fic.sgsi_magerit.Model.ProjectSizing;

/**
 * Created by err0r on 31/05/2016.
 */
public class ParametrizacionControlSeguridad {
    private Long idParametrizacionControlSeguridad;
    private Long idProyecto;
    private Long idTipo;
    private Integer valor;
    private Boolean activado;

    public ParametrizacionControlSeguridad(Long idParametrizacionControlSeguridad, Long idProyecto, Long idTipo, Integer valor, Boolean activado) {
        this.idParametrizacionControlSeguridad = idParametrizacionControlSeguridad;
        this.idProyecto = idProyecto;
        this.idTipo = idTipo;
        this.valor = valor;
        this.activado = activado;
    }

    public Long getIdParametrizacionControlSeguridad() {
        return idParametrizacionControlSeguridad;
    }

    public void setIdParametrizacionControlSeguridad(Long idParametrizacionControlSeguridad) {
        this.idParametrizacionControlSeguridad = idParametrizacionControlSeguridad;
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
