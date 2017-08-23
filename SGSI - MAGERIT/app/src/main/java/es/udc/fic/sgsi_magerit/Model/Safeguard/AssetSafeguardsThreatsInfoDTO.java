package es.udc.fic.sgsi_magerit.Model.Safeguard;

/**
 * Created by err0r on 23/08/2017.
 */
public class AssetSafeguardsThreatsInfoDTO {


    private Long idListaTipoAmenaza;
    private Long idTipoAmenaza;
    private Long idListaTipoSalvaguarda;
    private Long idTipoSalvaguarda;
    private Boolean expanded;

    public AssetSafeguardsThreatsInfoDTO(Long idListaTipoAmenaza, Long idTipoAmenaza,
                                         Long idListaTipoSalvaguarda, Long idTipoSalvaguarda,
                                         Boolean expanded) {
        this.idListaTipoAmenaza = idListaTipoAmenaza;
        this.idTipoAmenaza = idTipoAmenaza;
        this.idListaTipoSalvaguarda = idListaTipoSalvaguarda;
        this.idTipoSalvaguarda = idTipoSalvaguarda;
        this.expanded = expanded;
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

    public Long getIdListaTipoSalvaguarda() {
        return idListaTipoSalvaguarda;
    }

    public void setIdListaTipoSalvaguarda(Long idListaTipoSalvaguarda) {
        this.idListaTipoSalvaguarda = idListaTipoSalvaguarda;
    }

    public Long getIdTipoSalvaguarda() {
        return idTipoSalvaguarda;
    }

    public void setIdTipoSalvaguarda(Long idTipoSalvaguarda) {
        this.idTipoSalvaguarda = idTipoSalvaguarda;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }
}
