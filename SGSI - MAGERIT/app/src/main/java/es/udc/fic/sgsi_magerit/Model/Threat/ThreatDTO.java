package es.udc.fic.sgsi_magerit.Model.Threat;

/**
 * Created by err0r on 16/10/2016.
 */
public class ThreatDTO {

    Long idListaTipo;
    Long idTipo;

    public ThreatDTO(Long idListaTipo, Long idTipo) {
        this.idListaTipo = idListaTipo;
        this.idTipo = idTipo;
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
}
