package es.udc.fic.sgsi_magerit.Model.Safeguard;

/**
 * Created by err0r on 15/05/2017.
 */
public class AssetsSafeguardDTO {
    private Long idActivo;
    private Long idProyecto;
    private String nombreActivo;
    private String codigoActivo;
    private Boolean expanded;

    public AssetsSafeguardDTO(Long idActivo, Long idProyecto, String nombreActivo, String codigoActivo, Boolean expanded) {
        this.idActivo = idActivo;
        this.idProyecto = idProyecto;
        this.nombreActivo = nombreActivo;
        this.codigoActivo = codigoActivo;
        this.expanded = expanded;
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

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }
}
