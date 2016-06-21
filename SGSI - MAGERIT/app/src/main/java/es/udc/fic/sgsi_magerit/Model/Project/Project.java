package es.udc.fic.sgsi_magerit.Model.Project;

import java.util.Calendar;

/**
 * Created by err0r on 11/05/2016.
 */
public class Project {
    private Long id;
    private String nombre;
    private Calendar fechaCreacion;
    private String director;
    private String descripcion;
    private String version;
    private Boolean activated;

    public Project() {
    }

    public Project(Long id, String nombre, Calendar fechaCreacion, String director, String descripcion, String version, Boolean activated) {
        this.id = id;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.director = director;
        this.descripcion = descripcion;
        this.version = version;
        this.activated = activated;
    }

    public Project(Boolean activated, String version, String descripcion, String director, Calendar fechaCreacion, String nombre) {
        this.activated = activated;
        this.version = version;
        this.descripcion = descripcion;
        this.director = director;
        this.fechaCreacion = fechaCreacion;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Calendar getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Calendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }
}
