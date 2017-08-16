package es.udc.fic.sgsi_magerit.Model.Analisis;

/**
 * Created by err0r on 09/08/2017.
 */
public class AnalisisDTO {

    private long idActivo;
    private long idProyecto;
    private String nombreActivo;
    private String codigoActivo;
    private Long valoracionDisponibilidad;
    private Long valoracionIntegridad;
    private Long valoracionConfidencialidad;
    private Long valoracionAutenticidad;
    private Long valoracionTrazabilidad;

    public AnalisisDTO(long idActivo, long idProyecto, String nombreActivo, String codigoActivo,
                       Long valoracionDisponibilidad, Long valoracionIntegridad,
                       Long valoracionConfidencialidad, Long valoracionAutenticidad,
                       Long valoracionTrazabilidad) {

        this.idActivo = idActivo;
        this.idProyecto = idProyecto;
        this.nombreActivo = nombreActivo;
        this.codigoActivo = codigoActivo;
        this.valoracionDisponibilidad = valoracionDisponibilidad;
        this.valoracionIntegridad = valoracionIntegridad;
        this.valoracionConfidencialidad = valoracionConfidencialidad;
        this.valoracionAutenticidad = valoracionAutenticidad;
        this.valoracionTrazabilidad = valoracionTrazabilidad;
    }

    public long getIdActivo() {
        return idActivo;
    }

    public void setIdActivo(long idActivo) {
        this.idActivo = idActivo;
    }

    public long getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(long idProyecto) {
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

    public Long getValoracionDisponibilidad() {
        return valoracionDisponibilidad;
    }

    public void setValoracionDisponibilidad(Long valoracionDisponibilidad) {
        this.valoracionDisponibilidad = valoracionDisponibilidad;
    }

    public Long getValoracionIntegridad() {
        return valoracionIntegridad;
    }

    public void setValoracionIntegridad(Long valoracionIntegridad) {
        this.valoracionIntegridad = valoracionIntegridad;
    }

    public Long getValoracionConfidencialidad() {
        return valoracionConfidencialidad;
    }

    public void setValoracionConfidencialidad(Long valoracionConfidencialidad) {
        this.valoracionConfidencialidad = valoracionConfidencialidad;
    }

    public Long getValoracionAutenticidad() {
        return valoracionAutenticidad;
    }

    public void setValoracionAutenticidad(Long valoracionAutenticidad) {
        this.valoracionAutenticidad = valoracionAutenticidad;
    }

    public Long getValoracionTrazabilidad() {
        return valoracionTrazabilidad;
    }

    public void setValoracionTrazabilidad(Long valoracionTrazabilidad) {
        this.valoracionTrazabilidad = valoracionTrazabilidad;
    }
}
