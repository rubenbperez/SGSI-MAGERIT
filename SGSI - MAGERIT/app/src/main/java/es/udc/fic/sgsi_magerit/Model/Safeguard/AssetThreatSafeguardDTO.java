package es.udc.fic.sgsi_magerit.Model.Safeguard;

/**
 * Created by err0r on 23/08/2017.
 */
public class AssetThreatSafeguardDTO {

    private Long idAmenaza;
    private Long idSalvaguarda;
    private Long idListaTipoAmenaza;
    private Long idTipoAmenaza;
    private Long idProyecto;
    private Boolean checked;

    public AssetThreatSafeguardDTO(Long idAmenaza, Long idSalvaguarda, Long idListaTipoAmenaza,
                                   Long idTipoAmenaza, Long idProyecto, Boolean checked) {
        this.idAmenaza = idAmenaza;
        this.idSalvaguarda = idSalvaguarda;
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

    public Long getIdSalvaguarda() {
        return idSalvaguarda;
    }

    public void setIdSalvaguarda(Long idSalvaguarda) {
        this.idSalvaguarda = idSalvaguarda;
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
