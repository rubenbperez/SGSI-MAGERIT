package es.udc.fic.sgsi_magerit.Model.Analisis;

/**
 * Created by err0r on 09/08/2017.
 */
public class AnalisisDTO {

    private long idActivo;
    private long idProyecto;
    private String nombreActivo;
    private String codigoActivo;
    private Integer valoracionDisponibilidad;
    private Integer valoracionIntegridad;
    private Integer valoracionConfidencialidad;
    private Integer valoracionAutenticidad;
    private Integer valoracionTrazabilidad;

    public AnalisisDTO(long idActivo, long idProyecto, String nombreActivo, String codigoActivo,
                       Integer valoracionDisponibilidad, Integer valoracionIntegridad,
                       Integer valoracionConfidencialidad, Integer valoracionAutenticidad, Integer valoracionTrazabilidad) {
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

    public Integer getValoracionDisponibilidad() {
        return valoracionDisponibilidad;
    }

    public void setValoracionDisponibilidad(Integer valoracionDisponibilidad) {
        this.valoracionDisponibilidad = valoracionDisponibilidad;
    }

    public Integer getValoracionIntegridad() {
        return valoracionIntegridad;
    }

    public void setValoracionIntegridad(Integer valoracionIntegridad) {
        this.valoracionIntegridad = valoracionIntegridad;
    }

    public Integer getValoracionConfidencialidad() {
        return valoracionConfidencialidad;
    }

    public void setValoracionConfidencialidad(Integer valoracionConfidencialidad) {
        this.valoracionConfidencialidad = valoracionConfidencialidad;
    }

    public Integer getValoracionAutenticidad() {
        return valoracionAutenticidad;
    }

    public void setValoracionAutenticidad(Integer valoracionAutenticidad) {
        this.valoracionAutenticidad = valoracionAutenticidad;
    }

    public Integer getValoracionTrazabilidad() {
        return valoracionTrazabilidad;
    }

    public void setValoracionTrazabilidad(Integer valoracionTrazabilidad) {
        this.valoracionTrazabilidad = valoracionTrazabilidad;
    }
}
