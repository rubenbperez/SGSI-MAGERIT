package es.udc.fic.sgsi_magerit.Model.Safeguard;

import java.util.List;

/**
 * Created by err0r on 15/05/2017.
 */
public class ThreatAssetsDTO {
    private Long idListaTipoAmenaza;
    private Long idTipoAmenaza;
    private Long idProyecto;
    private List<AssetsThreatDTO> activos;

    public ThreatAssetsDTO(Long idListaTipoAmenaza, Long idTipoAmenaza, Long idProyecto, List<AssetsThreatDTO> activos) {
        this.idListaTipoAmenaza = idListaTipoAmenaza;
        this.idTipoAmenaza = idTipoAmenaza;
        this.idProyecto = idProyecto;
        this.activos = activos;
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

    public List<AssetsThreatDTO> getActivos() {
        return activos;
    }

    public void setActivos(List<AssetsThreatDTO> activos) {
        this.activos = activos;
    }
}
