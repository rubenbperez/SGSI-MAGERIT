package es.udc.fic.sgsi_magerit.Model.Asset;

/**
 * Created by err0r on 12/10/2016.
 */
public class AssetAssetType {
    Long idActivoTipoActivo;
    Long idActivo;
    Long idProyecto;
    Long idListaTipo;
    Long idTipo;

    public AssetAssetType(Long idActivoTipoActivo, Long idActivo, Long idProyecto, Long idListaTipo,
                          Long idTipo) {
        this.idActivoTipoActivo = idActivoTipoActivo;
        this.idActivo = idActivo;
        this.idProyecto = idProyecto;
        this.idListaTipo = idListaTipo;
        this.idTipo = idTipo;
    }

    public Long getIdactivoTipoActivo() {
        return idActivoTipoActivo;
    }

    public void setIdactivoTipoActivo(Long idactivoTipoActivo) {
        this.idActivoTipoActivo = idactivoTipoActivo;
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
