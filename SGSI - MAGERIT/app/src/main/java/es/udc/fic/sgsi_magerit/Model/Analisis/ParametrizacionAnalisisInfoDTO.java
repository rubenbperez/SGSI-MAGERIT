package es.udc.fic.sgsi_magerit.Model.Analisis;

/**
 * Created by err0r on 30/08/2017.
 */
public class ParametrizacionAnalisisInfoDTO {
    Long id;
    Integer idProyecto;
    Integer idTipo;
    Double valor;
    Boolean activado;

    public ParametrizacionAnalisisInfoDTO(Long id, Integer idProyecto, Integer idTipo, Double valor, Boolean activado) {
        this.id = id;
        this.idProyecto = idProyecto;
        this.idTipo = idTipo;
        this.valor = valor;
        this.activado = activado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
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
