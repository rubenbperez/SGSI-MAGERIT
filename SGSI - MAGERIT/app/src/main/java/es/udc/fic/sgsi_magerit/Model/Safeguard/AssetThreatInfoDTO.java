package es.udc.fic.sgsi_magerit.Model.Safeguard;

/**
 * Created by err0r on 02/08/2017.
 */
public class AssetThreatInfoDTO {
    private Long idActivo;
    private String nombreActivo;
    private String codigoActivo;
    private Long idAmenaza;
    private Long idListaTipoAmenaza;
    private Long idTipoAmenaza;
    private Long idProyecto;

    public AssetThreatInfoDTO(Long idActivo, String nombreActivo, String codigoActivo, Long idAmenaza,
                              Long idListaTipoAmenaza, Long idTipoAmenaza, Long idProyecto) {
        this.idActivo = idActivo;
        this.nombreActivo = nombreActivo;
        this.codigoActivo = codigoActivo;
        this.idAmenaza = idAmenaza;
        this.idListaTipoAmenaza = idListaTipoAmenaza;
        this.idTipoAmenaza = idTipoAmenaza;
        this.idProyecto = idProyecto;
    }

    public Long getIdActivo() {
        return idActivo;
    }

    public void setIdActivo(Long idActivo) {
        this.idActivo = idActivo;
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
}
