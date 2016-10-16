package es.udc.fic.sgsi_magerit.Model.Threat;

/**
 * Created by err0r on 16/10/2016.
 */
public class Threat {

    Long idThreat;
    Long idActivo;
    Long idProyecto;
    Long idListaTipo;
    Long idTipo;
    Long idFrec;
    Long idImpact;

    public Threat(Long idThreat, Long idActivo, Long idProyecto, Long idListaTipo, Long idTipo, Long idFrec, Long idImpact) {
        this.idThreat = idThreat;
        this.idActivo = idActivo;
        this.idProyecto = idProyecto;
        this.idListaTipo = idListaTipo;
        this.idTipo = idTipo;
        this.idFrec = idFrec;
        this.idImpact = idImpact;
    }

    public Long getIdImpact() {
        return idImpact;
    }

    public void setIdImpact(Long idImpact) {
        this.idImpact = idImpact;
    }

    public Long getIdThreat() {
        return idThreat;
    }

    public void setIdThreat(Long idThreat) {
        this.idThreat = idThreat;
    }

    public Long getIdActivo() {
        return idActivo;
    }

    public void setIdActivo(Long idActivo) {
        this.idActivo = idActivo;
    }

    public Long getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Long getIdListaTipo() {
        return idListaTipo;
    }

    public void setIdListaTipo(Long idListaTipo) {
        this.idListaTipo = idListaTipo;
    }

    public Long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Long idTipo) {
        this.idTipo = idTipo;
    }

    public Long getIdFrec() {
        return idFrec;
    }

    public void setIdFrec(Long idFrec) {
        this.idFrec = idFrec;
    }
}
