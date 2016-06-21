package es.udc.fic.sgsi_magerit.Model.Asset;

import java.util.Calendar;

/**
 * Created by err0r on 07/06/2016.
 */
public class Asset {
    private Long idActivo;
    private Long idProyecto;
    private Long idValoracionActivo;
    private String nombreActivo;
    private String codigoActivo;
    private String descripcionActivo;
    private String responsableActivo;
    private String ubicacionActivo;
    private Calendar fechaCreacion;

    public Asset(Long idActivo, Long idProyecto, Long idValoracionActivo, String nombreActivo,
                 String codigoActivo, String descripcionActivo, String responsableActivo,
                 String ubicacionActivo, Calendar fechaCreacion) {
        this.idActivo = idActivo;
        this.idProyecto = idProyecto;
        this.idValoracionActivo = idValoracionActivo;
        this.nombreActivo = nombreActivo;
        this.codigoActivo = codigoActivo;
        this.descripcionActivo = descripcionActivo;
        this.responsableActivo = responsableActivo;
        this.ubicacionActivo = ubicacionActivo;
        this.fechaCreacion = fechaCreacion;
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

    public Long getIdValoracionActivo() {
        return idValoracionActivo;
    }

    public void setIdValoracionActivo(Long idValoracionActivo) {
        this.idValoracionActivo = idValoracionActivo;
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

    public String getDescripcionActivo() {
        return descripcionActivo;
    }

    public void setDescripcionActivo(String descripcionActivo) {
        this.descripcionActivo = descripcionActivo;
    }

    public String getResponsableActivo() {
        return responsableActivo;
    }

    public void setResponsableActivo(String responsableActivo) {
        this.responsableActivo = responsableActivo;
    }

    public String getUbicacionActivo() {
        return ubicacionActivo;
    }

    public void setUbicacionActivo(String ubicacionActivo) {
        this.ubicacionActivo = ubicacionActivo;
    }

    public Calendar getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Calendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
