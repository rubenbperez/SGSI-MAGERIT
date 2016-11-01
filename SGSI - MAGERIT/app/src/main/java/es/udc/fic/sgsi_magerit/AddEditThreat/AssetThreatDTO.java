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
    private Boolean activadoDisponibilidad;
    private Integer idDegradaciónIntegridad;
    private Integer idProbabilidadIntegridad;
    private Boolean activadoIntegridad;
    private Integer idDegradaciónConfidencialidad;
    private Integer idProbabilidadConfidencialidad;
    private Boolean activadoConfidencialidad;
    private Integer idDegradaciónAutenticidad;
    private Integer idProbabilidadAutenticidad;
    private Boolean activadoAutenticidad;
    private Integer idDegradaciónTrazabilidad;
    private Integer idProbabilidadTrazabilidad;
    private Boolean activadoTrazabilidad;

    public AssetThreatDTO(Long idActivo, Long idProyecto, String codigoActivo, String nombreActivo,
                          Integer idDegradaciónDisponibilidad, Integer idProbabilidadDisponibilidad,
                          Boolean activadoDisponibilidad, Integer idDegradaciónIntegridad,
                          Integer idProbabilidadIntegridad, Boolean activadoIntegridad,
                          Integer idDegradaciónConfidencialidad, Integer idProbabilidadConfidencialidad,
                          Boolean activadoConfidencialidad, Integer idDegradaciónAutenticidad,
                          Integer idProbabilidadAutenticidad, Boolean activadoAutenticidad,
                          Integer idDegradaciónTrazabilidad, Integer idProbabilidadTrazabilidad,
                          Boolean activadoTrazabilidad) {
        this.idActivo = idActivo;
        this.idProyecto = idProyecto;
        this.codigoActivo = codigoActivo;
        this.nombreActivo = nombreActivo;
        this.idDegradaciónDisponibilidad = idDegradaciónDisponibilidad;
        this.idProbabilidadDisponibilidad = idProbabilidadDisponibilidad;
        this.activadoDisponibilidad = activadoDisponibilidad;
        this.idDegradaciónIntegridad = idDegradaciónIntegridad;
        this.idProbabilidadIntegridad = idProbabilidadIntegridad;
        this.activadoIntegridad = activadoIntegridad;
        this.idDegradaciónConfidencialidad = idDegradaciónConfidencialidad;
        this.idProbabilidadConfidencialidad = idProbabilidadConfidencialidad;
        this.activadoConfidencialidad = activadoConfidencialidad;
        this.idDegradaciónAutenticidad = idDegradaciónAutenticidad;
        this.idProbabilidadAutenticidad = idProbabilidadAutenticidad;
        this.activadoAutenticidad = activadoAutenticidad;
        this.idDegradaciónTrazabilidad = idDegradaciónTrazabilidad;
        this.idProbabilidadTrazabilidad = idProbabilidadTrazabilidad;
        this.activadoTrazabilidad = activadoTrazabilidad;
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

    public Boolean getActivadoDisponibilidad() {
        return activadoDisponibilidad;
    }

    public void setActivadoDisponibilidad(Boolean activadoDisponibilidad) {
        this.activadoDisponibilidad = activadoDisponibilidad;
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

    public Boolean getActivadoIntegridad() {
        return activadoIntegridad;
    }

    public void setActivadoIntegridad(Boolean activadoIntegridad) {
        this.activadoIntegridad = activadoIntegridad;
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

    public Boolean getActivadoConfidencialidad() {
        return activadoConfidencialidad;
    }

    public void setActivadoConfidencialidad(Boolean activadoConfidencialidad) {
        this.activadoConfidencialidad = activadoConfidencialidad;
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

    public Boolean getActivadoAutenticidad() {
        return activadoAutenticidad;
    }

    public void setActivadoAutenticidad(Boolean activadoAutenticidad) {
        this.activadoAutenticidad = activadoAutenticidad;
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

    public Boolean getActivadoTrazabilidad() {
        return activadoTrazabilidad;
    }

    public void setActivadoTrazabilidad(Boolean activadoTrazabilidad) {
        this.activadoTrazabilidad = activadoTrazabilidad;
    }
}
