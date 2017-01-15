package es.udc.fic.sgsi_magerit.Model.Threat;

/**
 * Created by err0r on 02/11/2016.
 */
public class AssetThreatDTO {
    private Long idThreat;
    private Long idActivo;
    private Long idProyecto;
    private String codigoActivo;
    private String nombreActivo;
    private Long idListaTipoAmenaza;
    private Long idTipoAmenaza;
    private Integer idDegradacionDisponibilidad;
    private Integer idProbabilidadDisponibilidad;
    private Integer idDegradacionIntegridad;
    private Integer idProbabilidadIntegridad;
    private Integer idDegradacionConfidencialidad;
    private Integer idProbabilidadConfidencialidad;
    private Integer idDegradacionAutenticidad;
    private Integer idProbabilidadAutenticidad;
    private Integer idDegradacionTrazabilidad;
    private Integer idProbabilidadTrazabilidad;
    private Boolean visible;


    public AssetThreatDTO(Long idThreat, Long idActivo, Long idProyecto, String codigoActivo,
                          String nombreActivo, Long idListaTipoAmenaza, Long idTipoAmenaza,
                          Integer idDegradacionDisponibilidad, Integer idProbabilidadDisponibilidad,
                          Integer idDegradacionIntegridad, Integer idProbabilidadIntegridad,
                          Integer idDegradacionConfidencialidad, Integer idProbabilidadConfidencialidad,
                          Integer idDegradacionAutenticidad, Integer idProbabilidadAutenticidad,
                          Integer idDegradacionTrazabilidad, Integer idProbabilidadTrazabilidad,
                          Boolean visible) {
        this.idThreat = idThreat;
        this.idActivo = idActivo;
        this.idProyecto = idProyecto;
        this.codigoActivo = codigoActivo;
        this.nombreActivo = nombreActivo;

        this.idListaTipoAmenaza = idListaTipoAmenaza;
        this.idTipoAmenaza = idTipoAmenaza;
        this.idDegradacionDisponibilidad = idDegradacionDisponibilidad;
        this.idProbabilidadDisponibilidad = idProbabilidadDisponibilidad;
        this.idDegradacionIntegridad = idDegradacionIntegridad;
        this.idProbabilidadIntegridad = idProbabilidadIntegridad;
        this.idDegradacionConfidencialidad = idDegradacionConfidencialidad;
        this.idProbabilidadConfidencialidad = idProbabilidadConfidencialidad;
        this.idDegradacionAutenticidad = idDegradacionAutenticidad;
        this.idProbabilidadAutenticidad = idProbabilidadAutenticidad;
        this.idDegradacionTrazabilidad = idDegradacionTrazabilidad;
        this.idProbabilidadTrazabilidad = idProbabilidadTrazabilidad;
        this.visible = visible;
    }

    public Long getIdThreat() {
        return idThreat;
    }

    public void setIdThreat(Long idThreat) {
        this.idThreat = idThreat;
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

    public String getCodigoActivo() {
        return codigoActivo;
    }

    public void setCodigoActivo(String codigoActivo) {
        this.codigoActivo = codigoActivo;
    }

    public String getNombreActivo() {
        return nombreActivo;
    }

    public void setNombreActivo(String nombreActivo) {
        this.nombreActivo = nombreActivo;
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

    public Integer getIdDegradacionDisponibilidad() {
        return idDegradacionDisponibilidad;
    }

    public void setIdDegradacionDisponibilidad(Integer idDegradacionDisponibilidad) {
        this.idDegradacionDisponibilidad = idDegradacionDisponibilidad;
    }

    public Integer getIdProbabilidadDisponibilidad() {
        return idProbabilidadDisponibilidad;
    }

    public void setIdProbabilidadDisponibilidad(Integer idProbabilidadDisponibilidad) {
        this.idProbabilidadDisponibilidad = idProbabilidadDisponibilidad;
    }

    public Integer getIdDegradacionIntegridad() {
        return idDegradacionIntegridad;
    }

    public void setIdDegradacionIntegridad(Integer idDegradacionIntegridad) {
        this.idDegradacionIntegridad = idDegradacionIntegridad;
    }

    public Integer getIdProbabilidadIntegridad() {
        return idProbabilidadIntegridad;
    }

    public void setIdProbabilidadIntegridad(Integer idProbabilidadIntegridad) {
        this.idProbabilidadIntegridad = idProbabilidadIntegridad;
    }

    public Integer getIdDegradacionConfidencialidad() {
        return idDegradacionConfidencialidad;
    }

    public void setIdDegradacionConfidencialidad(Integer idDegradacionConfidencialidad) {
        this.idDegradacionConfidencialidad = idDegradacionConfidencialidad;
    }

    public Integer getIdProbabilidadConfidencialidad() {
        return idProbabilidadConfidencialidad;
    }

    public void setIdProbabilidadConfidencialidad(Integer idProbabilidadConfidencialidad) {
        this.idProbabilidadConfidencialidad = idProbabilidadConfidencialidad;
    }

    public Integer getIdDegradacionAutenticidad() {
        return idDegradacionAutenticidad;
    }

    public void setIdDegradacionAutenticidad(Integer idDegradacionAutenticidad) {
        this.idDegradacionAutenticidad = idDegradacionAutenticidad;
    }

    public Integer getIdProbabilidadAutenticidad() {
        return idProbabilidadAutenticidad;
    }

    public void setIdProbabilidadAutenticidad(Integer idProbabilidadAutenticidad) {
        this.idProbabilidadAutenticidad = idProbabilidadAutenticidad;
    }

    public Integer getIdDegradacionTrazabilidad() {
        return idDegradacionTrazabilidad;
    }

    public void setIdDegradacionTrazabilidad(Integer idDegradacionTrazabilidad) {
        this.idDegradacionTrazabilidad = idDegradacionTrazabilidad;
    }

    public Integer getIdProbabilidadTrazabilidad() {
        return idProbabilidadTrazabilidad;
    }

    public void setIdProbabilidadTrazabilidad(Integer idProbabilidadTrazabilidad) {
        this.idProbabilidadTrazabilidad = idProbabilidadTrazabilidad;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
