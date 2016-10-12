package es.udc.fic.sgsi_magerit.Model.Asset;

import java.util.Calendar;

/**
 * Created by err0r on 07/06/2016.
 */
public class Asset {
    private Long idActivo;
    private Long idProyecto;
    private Long idValoracionDisp;
    private Long idValoracionInt;
    private Long idValoracionConf;
    private Long idValoracionAut;
    private Long idValoracionTraz;
    private String nombreActivo;
    private String codigoActivo;
    private String descripcionActivo;
    private String responsableActivo;
    private String ubicacionActivo;
    private Calendar fechaCreacion;

    public Asset(Long idActivo, Long idProyecto, Long idValoracionDisp, Long idValoracionInt,
                 Long idValoracionConf, Long idValoracionAut, Long idValoracionTraz,
                 String nombreActivo, String codigoActivo, String descripcionActivo,
                 String responsableActivo, String ubicacionActivo, Calendar fechaCreacion) {
        this.idActivo = idActivo;
        this.idProyecto = idProyecto;
        this.idValoracionDisp = idValoracionDisp;
        this.idValoracionInt = idValoracionInt;
        this.idValoracionConf = idValoracionConf;
        this.idValoracionAut = idValoracionAut;
        this.idValoracionTraz = idValoracionTraz;
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

    public Long getIdValoracionDisp() {
        return idValoracionDisp;
    }

    public void setIdValoracionDisp(Long idValoracionDisp) {
        this.idValoracionDisp = idValoracionDisp;
    }

    public Long getIdValoracionInt() {
        return idValoracionInt;
    }

    public void setIdValoracionInt(Long idValoracionInt) {
        this.idValoracionInt = idValoracionInt;
    }

    public Long getIdValoracionConf() {
        return idValoracionConf;
    }

    public void setIdValoracionConf(Long idValoracionConf) {
        this.idValoracionConf = idValoracionConf;
    }

    public Long getIdValoracionAut() {
        return idValoracionAut;
    }

    public void setIdValoracionAut(Long idValoracionAut) {
        this.idValoracionAut = idValoracionAut;
    }

    public Long getIdValoracionTraz() {
        return idValoracionTraz;
    }

    public void setIdValoracionTraz(Long idValoracionTraz) {
        this.idValoracionTraz = idValoracionTraz;
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
