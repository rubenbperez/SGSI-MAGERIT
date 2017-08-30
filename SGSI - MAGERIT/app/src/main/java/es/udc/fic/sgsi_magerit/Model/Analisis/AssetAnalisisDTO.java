package es.udc.fic.sgsi_magerit.Model.Analisis;

/**
 * Created by err0r on 29/08/2017.
 */
public class AssetAnalisisDTO {
    private Long idActivo;
    private Long idProyecto;
    private Integer idValoracionDisp;
    private Integer idValoracionInt;
    private Integer idValoracionConf;
    private Integer idValoracionAut;
    private Integer idValoracionTraz;
    private String nombreActivo;
    private String codigoActivo;

    public AssetAnalisisDTO(Long idActivo, Long idProyecto, Integer idValoracionDisp, Integer idValoracionInt,
                            Integer idValoracionConf, Integer idValoracionAut,
                            Integer idValoracionTraz, String nombreActivo, String codigoActivo) {
        this.idActivo = idActivo;
        this.idProyecto = idProyecto;
        this.idValoracionDisp = idValoracionDisp;
        this.idValoracionInt = idValoracionInt;
        this.idValoracionConf = idValoracionConf;
        this.idValoracionAut = idValoracionAut;
        this.idValoracionTraz = idValoracionTraz;
        this.nombreActivo = nombreActivo;
        this.codigoActivo = codigoActivo;
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

    public Integer getIdValoracionDisp() {
        return idValoracionDisp;
    }

    public void setIdValoracionDisp(Integer idValoracionDisp) {
        this.idValoracionDisp = idValoracionDisp;
    }

    public Integer getIdValoracionInt() {
        return idValoracionInt;
    }

    public void setIdValoracionInt(Integer idValoracionInt) {
        this.idValoracionInt = idValoracionInt;
    }

    public Integer getIdValoracionConf() {
        return idValoracionConf;
    }

    public void setIdValoracionConf(Integer idValoracionConf) {
        this.idValoracionConf = idValoracionConf;
    }

    public Integer getIdValoracionAut() {
        return idValoracionAut;
    }

    public void setIdValoracionAut(Integer idValoracionAut) {
        this.idValoracionAut = idValoracionAut;
    }

    public Integer getIdValoracionTraz() {
        return idValoracionTraz;
    }

    public void setIdValoracionTraz(Integer idValoracionTraz) {
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
}
