package es.udc.fic.sgsi_magerit.Model.ModelService;

import java.text.ParseException;
import java.util.List;

import es.udc.fic.sgsi_magerit.Model.Asset.Asset;
import es.udc.fic.sgsi_magerit.Model.Asset.AssetAssetType;
import es.udc.fic.sgsi_magerit.Model.Asset.AssetDTO;
import es.udc.fic.sgsi_magerit.Model.ProjectSizing.ParametrizacionDTO;
import es.udc.fic.sgsi_magerit.Model.Project.Project;
import es.udc.fic.sgsi_magerit.Model.Threat.Threat;
import es.udc.fic.sgsi_magerit.Model.Threat.ThreatDTO;

/**
 * Created by err0r on 18/05/2016.
 */
public interface ModelService {

    public long crearProyecto (String nombreProyecto, String directorProyecto,
                               String descripcionProyecto,String version, String fechaCreacion,
                               Boolean activado);

    public boolean eliminarProyectoYDimensionamiento(Long idProyecto);

    public long editarProyecto(Long idProyecto, String nombreProyecto, String directorProyecto,
                               String descripcionProyecto,String version, String fechaCreacion,
                               Boolean activado);

    public Project obtenerDetallesProyecto(Long idProyecto) throws ParseException;

    public List<Project> obtenerProyectos() throws ParseException;

    public void marcarProyectoComoMarcadoYEliminarAnterior(Long proyectoADesmarcar,
                                                           Long proyectoAMarcar);


    public void crearParametrizacionActivo (Long idProyecto, Long idTipo,
                                            Integer rangoSuperior, Integer rangoInferior,
                                            Integer valor, Boolean activado);
    public List<Integer> obtenerParametrizacionesActivadas(Long idProyecto, String Table);

    public void crearParametrizacionVulnerabilidad (Long idProyecto,
                                                    Long idTipo, Integer valorTiempo,
                                                    Integer valorTipoTiempo, Double valor,
                                                    Boolean activado);

    public void crearParametrizacionImpacto (Long idProyecto, Long idTipo,
                                             Integer valor, Boolean activado);

    public void crearParametrizacionControlSeguridad (Long idProyecto, Long idTipo,
                                                      Integer valor, Boolean activado);

    public void editarParametrizacionActivo (Long idParametrizacion,
                                             Integer rangoSuperior, Integer rangoInferior,
                                             Integer valor, Boolean activado);

    public void editarParametrizacionVulnerabilidad (Long idParametrizacion, Integer valorTiempo,
                                                     Integer valorTipoTiempo, Double valor,
                                                     Boolean activado);

    public void editarParametrizacionImpacto (Long idParametrizacion, Integer valor, Boolean activado);

    public void editarParametrizacionControlSeguridad (Long idParametrizacion, Integer valor, Boolean activado);

    public ParametrizacionDTO obtenerParametrizacionDeProyecto(Long idProyecto);

    public List<AssetDTO> obtenerActivos(long idProyecto);

    public long obtenerIdProyectoActivo();

    public long crearActivo(Integer idProyecto, Integer idValoracionDisp, Integer idValoracionInt,
                            Integer idValoracionConf, Integer idValoracionAut, Integer idValoracionTraza,
                            String nombre, String codigo, String desc, String responsable, String ubicacion,
                            String fechaCreacion);

    public Integer comprobarNombreYCodigoActivoUnicos(String nombre, String codigo, Integer idProyecto, Integer idActivo);

    public long crearActivoTipoActivo(Integer idActivo, Integer idProyecto, Integer idLista, Integer idTipoActivo);

    public boolean eliminarActivosYTiposActivo(Long idActivo);

    public Asset obtenerActivo (Long idActivo) throws ParseException;

    public List<AssetAssetType> obtenerTiposDeActivo (Long idActivo);

    public long editarActivo(Long idActivo, Integer idValoracionDisp, Integer idValoracionInt,
                             Integer idValoracionConf, Integer idValoracionAut, Integer idValoracionTraza,
                             String nombre, String codigo, String desc, String responsable, String ubicacion);

    public boolean eliminarTiposActivoDeActivo(Long idActivo);

    public List<ThreatDTO> obtenerAmenazas(long idProyecto);

    public List<AssetDTO> obtenerActivosPorId(long idProyecto, List<Long> idsActivos);

    public long crearAmenaza(Long idActivo, Long idProyecto, Long idListaTipoAmenaza,
                             Long idTipoAmenaza, Integer idDegradacionDisponibilidad,
                             Integer idProbabilidadDisponibilidad, Integer idDegradacionIntegridad,
                             Integer idProbabilidadIntegridad, Integer idDegradacionConfidencialidad,
                             Integer idProbabilidadConfidencialidad, Integer idDegradacionAutenticidad,
                             Integer idProbabilidadAutenticidad, Integer idDegradacionTrazabilidad,
                             Integer idProbabilidadTrazabilidad, String fechaCreacion);

    public boolean eliminarAmenaza(Long idListaTipoAmenaza, Long idTipoAmenaza, Long idProyecto);

    public List<AssetDTO> obtenerActivosConIdAmenaza (Long idListaTipoAmenaza, Long idTipoAmenaza, Long idProyecto);
}
