package es.udc.fic.sgsi_magerit.Model.Safeguard;

import java.util.Calendar;

/**
 * Created by err0r on 12/02/2017.
 */
public class Safeguard {
    private Long idSafeguard;
    private Long idThreat;
    private Long idActivo;
    private Long idProyecto;
    private Long idListaTipoSalvaguarda;
    private Long idTipoSalvaguarda;
    private Integer idControlSeguridadDisponibilidad;
    private Integer idControlSeguridadIntegridad;
    private Integer idControlSeguridadConfidencialidad;
    private Integer idControlSeguridadAutenticidad;
    private Integer idControlSeguridadTrazabilidad;
    private Integer tipoProteccion;
    private Integer eficacia;
    private Calendar fechaCreacion;

    public Safeguard(Long idSafeguard, Long idThreat, Long idActivo, Long idProyecto, Long idListaTipoSalvaguarda,
                     Long idTipoSalvaguarda, Integer idControlSeguridadDisponibilidad,
                     Integer idControlSeguridadIntegridad, Integer idControlSeguridadConfidencialidad,
                     Integer idControlSeguridadAutenticidad, Integer idControlSeguridadTrazabilidad,
                     Integer tipoProteccion, Integer eficacia, Calendar fechaCreacion) {
        this.idSafeguard = idSafeguard;
        this.idThreat = idThreat;
        this.idActivo = idActivo;
        this.idProyecto = idProyecto;
        this.idListaTipoSalvaguarda = idListaTipoSalvaguarda;
        this.idTipoSalvaguarda = idTipoSalvaguarda;
        this.idControlSeguridadDisponibilidad = idControlSeguridadDisponibilidad;
        this.idControlSeguridadIntegridad = idControlSeguridadIntegridad;
        this.idControlSeguridadConfidencialidad = idControlSeguridadConfidencialidad;
        this.idControlSeguridadAutenticidad = idControlSeguridadAutenticidad;
        this.idControlSeguridadTrazabilidad = idControlSeguridadTrazabilidad;
        this.tipoProteccion = tipoProteccion;
        this.eficacia = eficacia;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getIdSafeguard() {
        return idSafeguard;
    }

    public void setIdSafeguard(Long idSafeguard) {
        this.idSafeguard = idSafeguard;
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

    public Integer getIdControlSeguridadDisponibilidad() {
        return idControlSeguridadDisponibilidad;
    }

    public void setIdControlSeguridadDisponibilidad(Integer idControlSeguridadDisponibilidad) {
        this.idControlSeguridadDisponibilidad = idControlSeguridadDisponibilidad;
    }

    public Integer getIdControlSeguridadIntegridad() {
        return idControlSeguridadIntegridad;
    }

    public void setIdControlSeguridadIntegridad(Integer idControlSeguridadIntegridad) {
        this.idControlSeguridadIntegridad = idControlSeguridadIntegridad;
    }

    public Integer getIdControlSeguridadConfidencialidad() {
        return idControlSeguridadConfidencialidad;
    }

    public void setIdControlSeguridadConfidencialidad(Integer idControlSeguridadConfidencialidad) {
        this.idControlSeguridadConfidencialidad = idControlSeguridadConfidencialidad;
    }

    public Integer getIdControlSeguridadAutenticidad() {
        return idControlSeguridadAutenticidad;
    }

    public void setIdControlSeguridadAutenticidad(Integer idControlSeguridadAutenticidad) {
        this.idControlSeguridadAutenticidad = idControlSeguridadAutenticidad;
    }

    public Integer getIdControlSeguridadTrazabilidad() {
        return idControlSeguridadTrazabilidad;
    }

    public void setIdControlSeguridadTrazabilidad(Integer idControlSeguridadTrazabilidad) {
        this.idControlSeguridadTrazabilidad = idControlSeguridadTrazabilidad;
    }

    public Integer getTipoProteccion() {
        return tipoProteccion;
    }

    public void setTipoProteccion(Integer tipoProteccion) {
        this.tipoProteccion = tipoProteccion;
    }

    public Integer getEficacia() {
        return eficacia;
    }

    public void setEficacia(Integer eficacia) {
        this.eficacia = eficacia;
    }

    public Calendar getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Calendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}

