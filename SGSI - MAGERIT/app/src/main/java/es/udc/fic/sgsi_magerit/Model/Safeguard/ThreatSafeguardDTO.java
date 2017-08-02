package es.udc.fic.sgsi_magerit.Model.Safeguard;


/**
 * Created by err0r on 15/05/2017.
 */
public class ThreatSafeguardDTO {
    private Long idAmenaza;
    private Long idListaTipoAmenaza;
    private Long idTipoAmenaza;
    private Long idProyecto;
    private Boolean checked;

    public ThreatSafeguardDTO(Long idAmenaza, Long idListaTipoAmenaza, Long idTipoAmenaza, Long idProyecto, Boolean checked) {
        this.idAmenaza = idAmenaza;
        this.idListaTipoAmenaza = idListaTipoAmenaza;
        this.idTipoAmenaza = idTipoAmenaza;
        this.idProyecto = idProyecto;
        this.checked = checked;
    }

    public Long getIdAmenaza() {
        return idAmenaza;
    }

    public void setIdAmenaza(Long idAmenaza) {
        this.idAmenaza = idAmenaza;
    }

    public Long getIdListaTipoAmenaza() {
        return idListaTipoAmenaza;
    }

    public void setIdListaTipoAmenaza(Long idListaTipoAmenaza) {
        this.idListaTipoAmenaza = idListaTipoAmenaza;
    }

    public Long getIdTipoAmenaza() {
        return idTipoAmenaza;
    }

    public void setIdTipoAmenaza(Long idTipoAmenaza) {
        this.idTipoAmenaza = idTipoAmenaza;
    }

    public Long getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
