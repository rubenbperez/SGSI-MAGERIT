package es.udc.fic.sgsi_magerit.Model.PendingTasks;

/**
 * Created by err0r on 15/08/2017.
 */
public class PendingTaskDTO {
    Long idActivo;
    String nombreActivo;
    Long idAmenaza;
    String nombreAmenaza;
    Long idSalvaguarda;
    String nombreSalvaguarda;
    Long idProyecto;
    Integer idTipo;
    Integer causa;

    public PendingTaskDTO(Long idActivo, String nombreActivo, Long idAmenaza, String nombreAmenaza,
                          Long idSalvaguarda, String nombreSalvaguarda,
                          Long idProyecto, Integer idTipo, Integer causa) {
        this.idActivo = idActivo;
        this.nombreActivo = nombreActivo;
        this.idAmenaza = idAmenaza;
        this.nombreAmenaza = nombreAmenaza;
        this.idSalvaguarda = idSalvaguarda;
        this.nombreSalvaguarda = nombreSalvaguarda;
        this.idProyecto = idProyecto;
        this.idTipo = idTipo;
        this.causa = causa;
    }

    public Long getIdActivo() {
        return idActivo;
    }

    public void setIdActivo(Long idActivo) {
        this.idActivo = idActivo;
    }

    public String getNombreActivo() {
        return nombreActivo;
    }

    public void setNombreActivo(String nombreActivo) {
        this.nombreActivo = nombreActivo;
    }

    public Long getIdAmenaza() {
        return idAmenaza;
    }

    public void setIdAmenaza(Long idAmenaza) {
        this.idAmenaza = idAmenaza;
    }

    public String getNombreAmenaza() {
        return nombreAmenaza;
    }

    public void setNombreAmenaza(String nombreAmenaza) {
        this.nombreAmenaza = nombreAmenaza;
    }

    public Long getIdSalvaguarda() {
        return idSalvaguarda;
    }

    public void setIdSalvaguarda(Long idSalvaguarda) {
        this.idSalvaguarda = idSalvaguarda;
    }

    public String getNombreSalvaguarda() {
        return nombreSalvaguarda;
    }

    public void setNombreSalvaguarda(String nombreSalvaguarda) {
        this.nombreSalvaguarda = nombreSalvaguarda;
    }

    public Long getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public Integer getCausa() {
        return causa;
    }

    public void setCausa(Integer causa) {
        this.causa = causa;
    }
}
