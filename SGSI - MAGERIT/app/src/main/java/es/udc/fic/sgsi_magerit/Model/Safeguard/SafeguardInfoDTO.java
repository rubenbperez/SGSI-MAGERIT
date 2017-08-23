package es.udc.fic.sgsi_magerit.Model.Safeguard;

/**
 * Created by err0r on 23/08/2017.
 */
public class SafeguardInfoDTO {
    Long idListaTipo;
    Long idTipo;
    Boolean expanded;

    public SafeguardInfoDTO(Long idListaTipo, Long idTipo, Boolean expanded) {
        this.idListaTipo = idListaTipo;
        this.idTipo = idTipo;
        this.expanded = expanded;
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

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }
}
