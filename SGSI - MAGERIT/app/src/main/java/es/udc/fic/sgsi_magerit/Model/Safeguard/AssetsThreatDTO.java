package es.udc.fic.sgsi_magerit.Model.Safeguard;

/**
 * Created by err0r on 15/05/2017.
 */
public class AssetsThreatDTO {
    Long idActivo;
    Long idProyecto;
    Long idAmenaza;
    String nombreActivo;
    String codigoActivo;
    Boolean checked;

    public AssetsThreatDTO(Long idActivo, Long idProyecto, Long idAmenaza, String nombreActivo, String codigoActivo, Boolean checked) {
        this.idActivo = idActivo;
        this.idProyecto = idProyecto;
        this.idAmenaza = idAmenaza;
        this.nombreActivo = nombreActivo;
        this.codigoActivo = codigoActivo;
        this.checked = checked;
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

    public Long getIdAmenaza() {
        return idAmenaza;
    }

    public void setIdAmenaza(Long idAmenaza) {
        this.idAmenaza = idAmenaza;
    }

    public String getNombreActivo() {
        return nombreActivo;
    }

    public void setNombreActivo(String nombreActivo) {
        this.nombreActivo = nombreActivo;
    }

    public String getCodigoActivo() {
        return codigoActivo;
    }

    public void setCodigoActivo(String codigoActivo) {
        this.codigoActivo = codigoActivo;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
