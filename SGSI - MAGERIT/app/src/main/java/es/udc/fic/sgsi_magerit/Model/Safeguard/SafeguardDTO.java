package es.udc.fic.sgsi_magerit.Model.Safeguard;

/**
 * Created by err0r on 12/02/2017.
 */
public class SafeguardDTO {
    Long idListaTipo;
    Long idTipo;

    public SafeguardDTO(Long idListaTipo, Long idTipo) {
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
