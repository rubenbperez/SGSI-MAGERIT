package es.udc.fic.sgsi_magerit.AddEditThreat;

/**
 * Created by err0r on 02/11/2016.
 */
public class AssetThreatDTO {

    private Long idActivo;
    private Long idProyecto;
    private String codigoActivo;
    private String nombreActivo;
    private Integer idDegradaciónDisponibilidad;
    private Integer idProbabilidadDisponibilidad;
    private Integer idDegradaciónIntegridad;
    private Integer idProbabilidadIntegridad;
    private Integer idDegradaciónConfidencialidad;
    private Integer idProbabilidadConfidencialidad;
    private Integer idDegradaciónAutenticidad;
    private Integer idProbabilidadAutenticidad;
    private Integer idDegradaciónTrazabilidad;
    private Integer idProbabilidadTrazabilidad;
    private Boolean visible;

    public AssetThreatDTO(Long idActivo, Long idProyecto, String codigoActivo, String nombreActivo,
                          Integer idDegradaciónDisponibilidad, Integer idProbabilidadDisponibilidad,
                          Integer idDegradaciónIntegridad, Integer idProbabilidadIntegridad,
                          Integer idDegradaciónConfidencialidad, Integer idProbabilidadConfidencialidad,
                          Integer idDegradaciónAutenticidad, Integer idProbabilidadAutenticidad,
                          Integer idDegradaciónTrazabilidad, Integer idProbabilidadTrazabilidad,
                          Boolean visible) {
        this.idActivo = idActivo;
        this.idProyecto = idProyecto;
        this.codigoActivo = codigoActivo;
        this.nombreActivo = nombreActivo;
        this.idDegradaciónDisponibilidad = idDegradaciónDisponibilidad;
        this.idProbabilidadDisponibilidad = idProbabilidadDisponibilidad;
        this.idDegradaciónIntegridad = idDegradaciónIntegridad;
        this.idProbabilidadIntegridad = idProbabilidadIntegridad;
        this.idDegradaciónConfidencialidad = idDegradaciónConfidencialidad;
        this.idProbabilidadConfidencialidad = idProbabilidadConfidencialidad;
        this.idDegradaciónAutenticidad = idDegradaciónAutenticidad;
        this.idProbabilidadAutenticidad = idProbabilidadAutenticidad;
        this.idDegradaciónTrazabilidad = idDegradaciónTrazabilidad;
        this.idProbabilidadTrazabilidad = idProbabilidadTrazabilidad;
        this.visible = visible;
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

    public Integer getIdDegradaciónDisponibilidad() {
        return idDegradaciónDisponibilidad;
    }

    public void setIdDegradaciónDisponibilidad(Integer idDegradaciónDisponibilidad) {
        this.idDegradaciónDisponibilidad = idDegradaciónDisponibilidad;
    }

    public Integer getIdProbabilidadDisponibilidad() {
        return idProbabilidadDisponibilidad;
    }

    public void setIdProbabilidadDisponibilidad(Integer idProbabilidadDisponibilidad) {
        this.idProbabilidadDisponibilidad = idProbabilidadDisponibilidad;
    }

    public Integer getIdDegradaciónIntegridad() {
        return idDegradaciónIntegridad;
    }

    public void setIdDegradaciónIntegridad(Integer idDegradaciónIntegridad) {
        this.idDegradaciónIntegridad = idDegradaciónIntegridad;
    }

    public Integer getIdProbabilidadIntegridad() {
        return idProbabilidadIntegridad;
    }

    public void setIdProbabilidadIntegridad(Integer idProbabilidadIntegridad) {
        this.idProbabilidadIntegridad = idProbabilidadIntegridad;
    }

    public Integer getIdDegradaciónConfidencialidad() {
        return idDegradaciónConfidencialidad;
    }

    public void setIdDegradaciónConfidencialidad(Integer idDegradaciónConfidencialidad) {
        this.idDegradaciónConfidencialidad = idDegradaciónConfidencialidad;
    }

    public Integer getIdProbabilidadConfidencialidad() {
        return idProbabilidadConfidencialidad;
    }

    public void setIdProbabilidadConfidencialidad(Integer idProbabilidadConfidencialidad) {
        this.idProbabilidadConfidencialidad = idProbabilidadConfidencialidad;
    }

    public Integer getIdDegradaciónAutenticidad() {
        return idDegradaciónAutenticidad;
    }

    public void setIdDegradaciónAutenticidad(Integer idDegradaciónAutenticidad) {
        this.idDegradaciónAutenticidad = idDegradaciónAutenticidad;
    }

    public Integer getIdProbabilidadAutenticidad() {
        return idProbabilidadAutenticidad;
    }

    public void setIdProbabilidadAutenticidad(Integer idProbabilidadAutenticidad) {
        this.idProbabilidadAutenticidad = idProbabilidadAutenticidad;
    }

    public Integer getIdDegradaciónTrazabilidad() {
        return idDegradaciónTrazabilidad;
    }

    public void setIdDegradaciónTrazabilidad(Integer idDegradaciónTrazabilidad) {
        this.idDegradaciónTrazabilidad = idDegradaciónTrazabilidad;
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
