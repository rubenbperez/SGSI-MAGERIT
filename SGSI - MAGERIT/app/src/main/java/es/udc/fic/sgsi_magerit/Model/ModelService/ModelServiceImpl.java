package es.udc.fic.sgsi_magerit.Model.ModelService;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.udc.fic.sgsi_magerit.Model.Asset.Asset;
import es.udc.fic.sgsi_magerit.Model.Asset.AssetAssetType;
import es.udc.fic.sgsi_magerit.Model.Asset.AssetConstants;
import es.udc.fic.sgsi_magerit.Model.Asset.AssetDTO;
import es.udc.fic.sgsi_magerit.Model.ProjectSizing.ParametrizacionActivo;
import es.udc.fic.sgsi_magerit.Model.ProjectSizing.ParametrizacionControlSeguridad;
import es.udc.fic.sgsi_magerit.Model.ProjectSizing.ParametrizacionDTO;
import es.udc.fic.sgsi_magerit.Model.ProjectSizing.ParametrizacionImpacto;
import es.udc.fic.sgsi_magerit.Model.ProjectSizing.ParametrizacionVulnerabilidad;
import es.udc.fic.sgsi_magerit.Model.ProjectSizing.ProjectSizingConstants;
import es.udc.fic.sgsi_magerit.Model.Project.Project;
import es.udc.fic.sgsi_magerit.Model.Project.ProjectConstants;
import es.udc.fic.sgsi_magerit.Model.Threat.Threat;
import es.udc.fic.sgsi_magerit.Model.Threat.ThreatConstants;
import es.udc.fic.sgsi_magerit.Model.Threat.ThreatDTO;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;

/**
 * Created by err0r on 13/05/2016.
 */
public class ModelServiceImpl extends SQLiteOpenHelper implements ModelService {

    String sqlCreateTableProyectos = "CREATE TABLE " + ProjectConstants.TABLE_NAME + "(" +
            ProjectConstants.ID_PROYECTO + " " + ProjectConstants.INT_TYPE + " PRIMARY KEY AUTOINCREMENT, " +
            ProjectConstants.NOMBRE + " " + ProjectConstants.STRING_TYPE + " NOT NULL, " +
            ProjectConstants.FECHA_CREACION + " " + ProjectConstants.STRING_TYPE + " NOT NULL, " +
            ProjectConstants.DIRECTOR + " " + ProjectConstants.STRING_TYPE + ", " +
            ProjectConstants.DESCRIPCION + " " + ProjectConstants.STRING_TYPE + ", " +
            ProjectConstants.VERSION + " " + ProjectConstants.STRING_TYPE + ", " +
            ProjectConstants.ACTIVADO + " " + ProjectConstants.INT_TYPE + " NOT NULL CHECK (" +
            ProjectConstants.ACTIVADO + " = 0 OR " + ProjectConstants.ACTIVADO + " = 1));";

    String sqlCreateParametrizacionActivo = "CREATE TABLE " + ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_ACTIVO + "(" +
            ProjectSizingConstants.ID_PARAMETRIZACION_ACTIVO + " " + ProjectSizingConstants.INT_TYPE + " PRIMARY KEY AUTOINCREMENT, " +
            ProjectSizingConstants.ID_PROYECTO + " " + ProjectSizingConstants.INT_TYPE + " NOT NULL, " +
            ProjectSizingConstants.ID_TIPO + " " + ProjectSizingConstants.INT_TYPE + " NOT NULL, " +
            ProjectSizingConstants.RANGO_SUPERIOR + " " + ProjectSizingConstants.INT_TYPE + "NOT NULL, " +
            ProjectSizingConstants.RANGO_INFERIOR + " " + ProjectSizingConstants.INT_TYPE + "NOT NULL, " +
            ProjectSizingConstants.VALOR + " " + ProjectSizingConstants.INT_TYPE + " NOT NULL, " +
            ProjectSizingConstants.ACTIVADO + " " + ProjectSizingConstants.INT_TYPE + " NOT NULL CHECK (" +
            ProjectSizingConstants.ACTIVADO + " = 0 OR " + ProjectSizingConstants.ACTIVADO + " = 1));";

    String sqlCreateParametrizacionVulnerabilidad = "CREATE TABLE " + ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_VULNERABILIDAD + "(" +
            ProjectSizingConstants.ID_PARAMETRIZACION_VULNERABILIDAD + " " + ProjectSizingConstants.INT_TYPE + " PRIMARY KEY AUTOINCREMENT, " +
            ProjectSizingConstants.ID_PROYECTO + " " + ProjectSizingConstants.INT_TYPE + " NOT NULL, " +
            ProjectSizingConstants.ID_TIPO + " " + ProjectSizingConstants.INT_TYPE + " NOT NULL, " +
            ProjectSizingConstants.VALOR_TIEMPO+  " " + ProjectSizingConstants.INT_TYPE + "NOT NULL, " +
            ProjectSizingConstants.VALOR_TIPO_TIEMPO + " " + ProjectSizingConstants.INT_TYPE + "NOT NULL, " +
            ProjectSizingConstants.VALOR + " " + ProjectSizingConstants.REAL_TYPE + " NOT NULL, " +
            ProjectSizingConstants.ACTIVADO + " " + ProjectSizingConstants.INT_TYPE + " NOT NULL CHECK (" +
            ProjectSizingConstants.ACTIVADO + " = 0 OR " + ProjectSizingConstants.ACTIVADO + " = 1));";

    String sqlCreateParametrizacionImpacto = "CREATE TABLE " + ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_IMPACTO + "(" +
            ProjectSizingConstants.ID_PARAMETRIZACION_IMPACTO + " " + ProjectSizingConstants.INT_TYPE + " PRIMARY KEY AUTOINCREMENT, " +
            ProjectSizingConstants.ID_PROYECTO + " " + ProjectSizingConstants.INT_TYPE + " NOT NULL, " +
            ProjectSizingConstants.ID_TIPO + " " + ProjectSizingConstants.INT_TYPE + " NOT NULL, " +
            ProjectSizingConstants.VALOR + " " + ProjectSizingConstants.INT_TYPE + " NOT NULL, " +
            ProjectSizingConstants.ACTIVADO + " " + ProjectSizingConstants.INT_TYPE + " NOT NULL CHECK (" +
            ProjectSizingConstants.ACTIVADO + " = 0 OR " + ProjectSizingConstants.ACTIVADO + " = 1));";

    String sqlCreateParametrizacionControlSeguridad = "CREATE TABLE " + ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_CONTROLSEGURIDAD + "(" +
            ProjectSizingConstants.ID_PARAMETRIZACION_CONTROLSEGURIDAD + " " + ProjectSizingConstants.INT_TYPE + " PRIMARY KEY AUTOINCREMENT, " +
            ProjectSizingConstants.ID_PROYECTO + " " + ProjectSizingConstants.INT_TYPE + " NOT NULL, " +
            ProjectSizingConstants.ID_TIPO + " " + ProjectSizingConstants.INT_TYPE + " NOT NULL, " +
            ProjectSizingConstants.VALOR + " " + ProjectSizingConstants.INT_TYPE + " NOT NULL, " +
            ProjectSizingConstants.ACTIVADO + " " + ProjectSizingConstants.INT_TYPE + " NOT NULL CHECK (" +
            ProjectSizingConstants.ACTIVADO + " = 0 OR " + ProjectSizingConstants.ACTIVADO + " = 1));";

    String sqlCreateTableActivos = "CREATE TABLE " + AssetConstants.TABLE_NAME + "(" +
            AssetConstants.ID_ACTIVO + " " + AssetConstants.INT_TYPE + " PRIMARY KEY AUTOINCREMENT, " +
            AssetConstants.ID_PROYECTO + " " + AssetConstants.INT_TYPE + " NOT NULL, " +
            AssetConstants.ID_VALORACION_ACTIVO_DISPONIBILIDAD + " " + AssetConstants.INT_TYPE + ", " +
            AssetConstants.ID_VALORACION_ACTIVO_INTEGRIDAD + " " + AssetConstants.INT_TYPE + ", " +
            AssetConstants.ID_VALORACION_ACTIVO_CONFIDENCIALIDAD + " " + AssetConstants.INT_TYPE + ", " +
            AssetConstants.ID_VALORACION_ACTIVO_AUTENTICIDAD + " " + AssetConstants.INT_TYPE + ", " +
            AssetConstants.ID_VALORACION_ACTIVO_TRAZABILIDAD + " " + AssetConstants.INT_TYPE + ", " +
            AssetConstants.NOMBRE + " " + AssetConstants.STRING_TYPE + " NOT NULL, " +
            AssetConstants.CODIGO + " " + AssetConstants.STRING_TYPE + " NOT NULL, " +
            AssetConstants.DESCRIPCION + " " + AssetConstants.STRING_TYPE + ", " +
            AssetConstants.RESPONSABLE + " " + AssetConstants.STRING_TYPE + ", " +
            AssetConstants.UBICACION + " " + AssetConstants.STRING_TYPE + ", " +
            AssetConstants.FECHA_CREACION + " " + AssetConstants.STRING_TYPE  + " NOT NULL);";

    String sqlCreateTableActivoTipoActivo = "CREATE TABLE " + AssetConstants.TABLE_NAME_ACTIVO_TIPO_ACTIVO + "(" +
            AssetConstants.ID_ACTIVO_TIPO_ACTIVO + " " + AssetConstants.INT_TYPE + " PRIMARY KEY AUTOINCREMENT, " +
            AssetConstants.ID_ACTIVO_TABLA_TIPO_ACTIVO + " " + AssetConstants.INT_TYPE + " NOT NULL, " +
            AssetConstants.ID_PROYECTO + " " + AssetConstants.INT_TYPE + " NOT NULL, " +
            AssetConstants.ID_LISTA_TIPO_ACTIVO + " " + AssetConstants.INT_TYPE + " NOT NULL, " +
            AssetConstants.ID_TIPO_ACTIVO + " " + AssetConstants.INT_TYPE + " NOT NULL);";

    String sqlCreateTableAmenazas = "CREATE TABLE " + ThreatConstants.TABLE_NAME + "(" +
            ThreatConstants.ID_AMENAZA_ACTIVO + " " + ThreatConstants.INT_TYPE + " PRIMARY KEY AUTOINCREMENT, " +
            ThreatConstants.ID_ACTIVO + " " + ThreatConstants.INT_TYPE + " NOT NULL, " +
            ThreatConstants.ID_PROYECTO + " " + ThreatConstants.INT_TYPE + " NOT NULL, " +
            ThreatConstants.ID_LISTA_TIPO_AMENAZA + " " + ThreatConstants.INT_TYPE + " NOT NULL, " +
            ThreatConstants.ID_TIPO_AMENAZA + " " + ThreatConstants.INT_TYPE + " NOT NULL, " +
            ThreatConstants.ID_VALORACION_DEGRADACION_DISPONIBILIDAD + " " + ThreatConstants.INT_TYPE + ", " +
            ThreatConstants.ID_VALORACION_PROBABILIDAD_DISPONIBILIDAD + " " + ThreatConstants.INT_TYPE + ", " +
            ThreatConstants.ID_VALORACION_DEGRADACION_INTEGRIDAD + " " + ThreatConstants.INT_TYPE + ", " +
            ThreatConstants.ID_VALORACION_PROBABILIDAD_INTEGRIDAD + " " + ThreatConstants.INT_TYPE + ", " +
            ThreatConstants.ID_VALORACION_DEGRADACION_CONFIDENCIALIDAD + " " + ThreatConstants.INT_TYPE + ", " +
            ThreatConstants.ID_VALORACION_PROBABILIDAD_CONFIDENCIALIDAD + " " + ThreatConstants.INT_TYPE + ", " +
            ThreatConstants.ID_VALORACION_DEGRADACION_AUTENTICIDAD + " " + ThreatConstants.INT_TYPE + ", " +
            ThreatConstants.ID_VALORACION_PROBABILIDAD_AUTENTICIDAD + " " + ThreatConstants.INT_TYPE + ", " +
            ThreatConstants.ID_VALORACION_DEGRADACION_TRAZABILIDAD + " " + ThreatConstants.INT_TYPE + ", " +
            ThreatConstants.ID_VALORACION_PROBABILIDAD_TRAZABILIDAD + " " + ThreatConstants.INT_TYPE + ", " +
            ThreatConstants.FECHA_CREACION + " " + AssetConstants.STRING_TYPE  + " NOT NULL);";

    public ModelServiceImpl(Context contexto, String nombre, int version) {
        super(contexto, nombre, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateTableProyectos);
        db.execSQL(sqlCreateParametrizacionActivo);
        db.execSQL(sqlCreateParametrizacionVulnerabilidad);
        db.execSQL(sqlCreateParametrizacionImpacto);
        db.execSQL(sqlCreateParametrizacionControlSeguridad);
        db.execSQL(sqlCreateTableActivos);
        db.execSQL(sqlCreateTableActivoTipoActivo);
        db.execSQL(sqlCreateTableAmenazas);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       //Se elimina la version anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS " + ProjectConstants.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " +  ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_ACTIVO);
        db.execSQL("DROP TABLE IF EXISTS " +  ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_VULNERABILIDAD);
        db.execSQL("DROP TABLE IF EXISTS " +  ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_IMPACTO);
        db.execSQL("DROP TABLE IF EXISTS " +  ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_CONTROLSEGURIDAD);
        db.execSQL("DROP TABLE IF EXISTS " +  AssetConstants.TABLE_NAME);

        //Se crea la nueva version de la tabla
        db.execSQL(sqlCreateTableProyectos);
        db.execSQL(sqlCreateParametrizacionActivo);
        db.execSQL(sqlCreateParametrizacionVulnerabilidad);
        db.execSQL(sqlCreateParametrizacionImpacto);
        db.execSQL(sqlCreateParametrizacionControlSeguridad);
        db.execSQL(sqlCreateTableActivos);
    }

    @Override
    public long crearProyecto(String nombreProyecto, String directorProyecto,
                              String descripcionProyecto,String version, String fechaCreacion,
                              Boolean activado) {

        SQLiteDatabase db = getReadableDatabase();
        if (activado) {
            ContentValues editarActivadoAntiguo = new ContentValues();
            editarActivadoAntiguo.put(ProjectConstants.ACTIVADO,0);
            db.update(ProjectConstants.TABLE_NAME,editarActivadoAntiguo,ProjectConstants.ACTIVADO + "=1",null);
        }

        //Creamos el registro a insertar como objeto ContentValues
        ContentValues nuevoProyecto = new ContentValues();
        nuevoProyecto.put(ProjectConstants.NOMBRE,nombreProyecto);
        nuevoProyecto.put(ProjectConstants.DIRECTOR,directorProyecto);
        nuevoProyecto.put(ProjectConstants.DESCRIPCION,descripcionProyecto);
        nuevoProyecto.put(ProjectConstants.VERSION,version);
        nuevoProyecto.put(ProjectConstants.FECHA_CREACION,fechaCreacion);
        nuevoProyecto.put(ProjectConstants.ACTIVADO,(activado)?1:0);

        //Insertamos el registro en la base de datos
        long id = db.insert(ProjectConstants.TABLE_NAME, null, nuevoProyecto);
        db.close();
        return id;
    }

    @Override
    public boolean eliminarProyectoYDimensionamiento(Long idProyecto) {
        SQLiteDatabase db = getReadableDatabase();
        db.delete(ProjectConstants.TABLE_NAME, ProjectConstants.ID_PROYECTO + "=" + idProyecto,null);
        db.delete(ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_ACTIVO, ProjectSizingConstants.ID_PROYECTO + "=" + idProyecto,null);
        db.delete(ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_VULNERABILIDAD, ProjectSizingConstants.ID_PROYECTO + "=" + idProyecto,null);
        db.delete(ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_IMPACTO, ProjectSizingConstants.ID_PROYECTO + "=" + idProyecto,null);
        db.delete(ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_CONTROLSEGURIDAD, ProjectSizingConstants.ID_PROYECTO + "=" + idProyecto,null);
        db.close();
        return true;

    }

    @Override
    public long editarProyecto(Long idProyecto, String nombreProyecto, String directorProyecto,
                               String descripcionProyecto,String version, String fechaCreacion,
                               Boolean activado) {
        SQLiteDatabase db = getReadableDatabase();
        if (activado) {
            ContentValues editarActivadoAntiguo = new ContentValues();
            editarActivadoAntiguo.put(ProjectConstants.ACTIVADO,0);
            db.update(ProjectConstants.TABLE_NAME,editarActivadoAntiguo,ProjectConstants.ACTIVADO + "=1",null);
        }

        //Creamos el registro a editar como objeto ContentValues
        ContentValues nuevoProyecto = new ContentValues();
        nuevoProyecto.put(ProjectConstants.NOMBRE,nombreProyecto);
        nuevoProyecto.put(ProjectConstants.DIRECTOR,directorProyecto);
        nuevoProyecto.put(ProjectConstants.DESCRIPCION,descripcionProyecto);
        nuevoProyecto.put(ProjectConstants.VERSION,version);
        nuevoProyecto.put(ProjectConstants.FECHA_CREACION,fechaCreacion);
        nuevoProyecto.put(ProjectConstants.ACTIVADO,(activado)?1:0);

        //Insertamos el registro en la base de datos
        long id = db.update(ProjectConstants.TABLE_NAME, nuevoProyecto, ProjectConstants.ID_PROYECTO
                + "=" + idProyecto, null);
        db.close();
        return id;
    }

    @Override
    public Project obtenerDetallesProyecto(Long idProyecto) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Project pr = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ProjectConstants.TABLE_NAME + " WHERE "
                + ProjectConstants.ID_PROYECTO + "=" + idProyecto , null);

        if (cursor.moveToFirst()) {
            Long id = cursor.getLong(0);
            String nombre = cursor.getString(1);
            String fechaCreacion = cursor.getString(2);
            String director = cursor.getString(3);
            String descripcion = cursor.getString(4);
            String version = cursor.getString(5);
            Integer activado = cursor.getInt(6);
            Boolean ac;
            if (activado == 1)
                ac = true;
            else
                ac = false;

            Calendar cal = Calendar.getInstance();
            cal.setTime(dateFormat.parse(fechaCreacion));
            Log.w("wii", fechaCreacion);
            pr = new Project(id, nombre, cal, director, descripcion, version, ac);

        }
        cursor.close();
        db.close();
        return pr;
    }

    @Override
    public List<Project> obtenerProyectos() throws ParseException {
        List <Project> proyectos = new ArrayList<Project>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ProjectConstants.TABLE_NAME, null);
        if (cursor.moveToFirst()){
            do {
                Long id = cursor.getLong(0);
                String nombre = cursor.getString(1);
                String fechaCreacion = cursor.getString(2);
                String director = cursor.getString(3);
                String descripcion = cursor.getString(4);
                String version = cursor.getString(5);
                Integer activado = cursor.getInt(6);
                Boolean ac;
                if (activado == 1)
                    ac = true;
                else
                    ac = false;

                Calendar cal = Calendar.getInstance();
                cal.setTime(dateFormat.parse(fechaCreacion));
                Log.w("wii", fechaCreacion);
                Project pr = new Project(id, nombre, cal, director, descripcion, version, ac);
                proyectos.add(pr);
            } while ( (cursor.moveToNext()));
        }
        cursor.close();
        db.close();
        return proyectos;
    }

    @Override
    public void marcarProyectoComoMarcadoYEliminarAnterior(Long proyectoADesmarcar,
                                                           Long proyectoAMarcar) {
        SQLiteDatabase db = getReadableDatabase();
        //Establecemos los campos-valores a actualizar
        ContentValues valores = new ContentValues();
        valores.put(ProjectConstants.ACTIVADO,1);
        db.update(ProjectConstants.TABLE_NAME, valores, ProjectConstants.ID_PROYECTO + "="+
                proyectoAMarcar, null);

        valores.clear();
        valores.put(ProjectConstants.ACTIVADO,0);
        //Actualizamos el registro en la base de datos
        db.update(ProjectConstants.TABLE_NAME, valores, ProjectConstants.ID_PROYECTO + "="+
                proyectoADesmarcar, null);
        db.close();

    }


    @Override
    public void crearParametrizacionActivo (Long idProyecto, Long idTipo,
                                            Integer rangoSuperior, Integer rangoInferior,
                                            Integer valor, Boolean activado) {
        SQLiteDatabase db = getReadableDatabase();
        //Creamos el registro a insertar como objeto ContentValues
        ContentValues nuevoParametrizacionActivo = new ContentValues();
        nuevoParametrizacionActivo.put(ProjectSizingConstants.ID_PROYECTO,idProyecto);
        nuevoParametrizacionActivo.put(ProjectSizingConstants.ID_TIPO,idTipo);
        nuevoParametrizacionActivo.put(ProjectSizingConstants.RANGO_SUPERIOR,rangoSuperior);
        nuevoParametrizacionActivo.put(ProjectSizingConstants.RANGO_INFERIOR,rangoInferior);
        nuevoParametrizacionActivo.put(ProjectSizingConstants.VALOR,valor);
        nuevoParametrizacionActivo.put(ProjectSizingConstants.ACTIVADO,(activado)?1:0);

        //Insertamos el registro en la base de datos
        db.insert(ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_ACTIVO, null,
                nuevoParametrizacionActivo);
        db.close();
    }

    @Override
    public List<Integer> obtenerParametrizacionesActivadas(Long idProyecto, String table) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+ ProjectSizingConstants.ID_TIPO +" FROM " +
                table + " WHERE " + ProjectSizingConstants.ID_PROYECTO + " = " + idProyecto + " AND " +
                ProjectSizingConstants.ACTIVADO + " = 1", null);

        List<Integer> idsTipos = new ArrayList<Integer>();
        if (cursor.moveToFirst()) {
            do {
                Integer idTipo = cursor.getInt(0);
                idsTipos.add(idTipo);
            } while ((cursor.moveToNext()));
        }
        cursor.close();
        db.close();
        return idsTipos;
    }



    @Override
    public ParametrizacionDTO obtenerParametrizacionDeProyecto(Long idProyecto) {
        SQLiteDatabase db = getReadableDatabase();

        List<ParametrizacionActivo> parametrizacionActivos = new ArrayList<ParametrizacionActivo>();
        List<ParametrizacionVulnerabilidad> parametrizacionVulnerabilidad =
                new ArrayList<ParametrizacionVulnerabilidad>();
        List<ParametrizacionImpacto> parametrizacionImpacto = new ArrayList<ParametrizacionImpacto>();
        List<ParametrizacionControlSeguridad> parametrizacionControlSeguridad =
                new ArrayList<ParametrizacionControlSeguridad>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_ACTIVO
                + " WHERE " + ProjectSizingConstants.ID_PROYECTO + "=" + idProyecto + " ORDER BY " +
                ProjectSizingConstants.ID_TIPO + " ASC", null);

        if (cursor.moveToFirst()){
            do {
                Long id = cursor.getLong(0);
                Long idProyecto2 = cursor.getLong(0);
                Long idTipo = cursor.getLong(2);
                Integer rangoSup = cursor.getInt(3);
                Integer rangoInf = cursor.getInt(4);
                Integer valor = cursor.getInt(5);
                Integer activado = cursor.getInt(6);
                Boolean ac;
                if (activado == 1)
                    ac = true;
                else
                    ac = false;

                ParametrizacionActivo pa = new ParametrizacionActivo(
                        id, idProyecto2, idTipo, rangoSup, rangoInf, valor, ac);
                parametrizacionActivos.add(pa);
            } while ( (cursor.moveToNext()));
        }
        cursor.close();


        cursor = db.rawQuery("SELECT * FROM " + ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_VULNERABILIDAD
                + " WHERE " + ProjectSizingConstants.ID_PROYECTO + "=" + idProyecto + " ORDER BY " +
                ProjectSizingConstants.ID_TIPO + " ASC", null);

        if (cursor.moveToFirst()){
            do {
                Long id = cursor.getLong(0);
                Long idProyecto2 = cursor.getLong(0);
                Long idTipo = cursor.getLong(2);
                Integer valorTiempo = cursor.getInt(3);
                Integer valorTipoTiempo = cursor.getInt(4);
                Double valor = cursor.getDouble(5);
                Integer activado = cursor.getInt(6);
                Boolean ac;
                if (activado == 1)
                    ac = true;
                else
                    ac = false;

                ParametrizacionVulnerabilidad pv = new ParametrizacionVulnerabilidad(
                        id, idProyecto2, idTipo, valorTiempo, valorTipoTiempo, valor, ac);
                parametrizacionVulnerabilidad.add(pv);
            } while ( (cursor.moveToNext()));
        }
        cursor.close();



        cursor = db.rawQuery("SELECT * FROM " + ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_IMPACTO
                + " WHERE " + ProjectSizingConstants.ID_PROYECTO + "=" + idProyecto + " ORDER BY " +
                ProjectSizingConstants.ID_TIPO + " ASC", null);

        if (cursor.moveToFirst()){
            do {
                Long id = cursor.getLong(0);
                Long idProyecto2 = cursor.getLong(0);
                Long idTipo = cursor.getLong(2);
                Integer valor = cursor.getInt(3);
                Integer activado = cursor.getInt(4);
                Boolean ac;
                if (activado == 1)
                    ac = true;
                else
                    ac = false;

                ParametrizacionImpacto pi = new ParametrizacionImpacto(
                        id, idProyecto2, idTipo, valor, ac);
                parametrizacionImpacto.add(pi);
            } while ( (cursor.moveToNext()));
        }
        cursor.close();


        cursor = db.rawQuery("SELECT * FROM " + ProjectSizingConstants.
                TABLE_NAME_PARAMETRIZACION_CONTROLSEGURIDAD
                + " WHERE " + ProjectSizingConstants.ID_PROYECTO + "=" + idProyecto + " ORDER BY " +
                ProjectSizingConstants.ID_TIPO + " ASC", null);

        if (cursor.moveToFirst()){
            do {
                Long id = cursor.getLong(0);
                Long idProyecto2 = cursor.getLong(0);
                Long idTipo = cursor.getLong(2);
                Integer valor = cursor.getInt(3);
                Integer activado = cursor.getInt(4);
                Boolean ac;
                if (activado == 1)
                    ac = true;
                else
                    ac = false;

                ParametrizacionControlSeguridad pcs = new ParametrizacionControlSeguridad(
                        id, idProyecto2, idTipo, valor, ac);
                parametrizacionControlSeguridad.add(pcs);
            } while ( (cursor.moveToNext()));
        }
        cursor.close();
        db.close();
        return new ParametrizacionDTO(parametrizacionActivos, parametrizacionVulnerabilidad,
                parametrizacionImpacto, parametrizacionControlSeguridad);
    }

    @Override
    public void crearParametrizacionVulnerabilidad (Long idProyecto,
                                                    Long idTipo, Integer valorTiempo,
                                                    Integer valorTipoTiempo, Double valor,
                                                    Boolean activado) {
        SQLiteDatabase db = getReadableDatabase();
        //Creamos el registro a insertar como objeto ContentValues
        ContentValues nuevoParametrizacionVulnerabilidad = new ContentValues();
        nuevoParametrizacionVulnerabilidad.put(ProjectSizingConstants.ID_PROYECTO,idProyecto);
        nuevoParametrizacionVulnerabilidad.put(ProjectSizingConstants.ID_TIPO,idTipo);
        nuevoParametrizacionVulnerabilidad.put(ProjectSizingConstants.VALOR_TIEMPO,valorTiempo);
        nuevoParametrizacionVulnerabilidad.put(ProjectSizingConstants.VALOR_TIPO_TIEMPO,valorTipoTiempo);
        nuevoParametrizacionVulnerabilidad.put(ProjectSizingConstants.VALOR,valor);
        nuevoParametrizacionVulnerabilidad.put(ProjectSizingConstants.ACTIVADO,(activado)?1:0);

        //Insertamos el registro en la base de datos
        db.insert(ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_VULNERABILIDAD, null,
                nuevoParametrizacionVulnerabilidad);

        db.close();
    }

    @Override
    public void crearParametrizacionImpacto (Long idProyecto, Long idTipo,
                                             Integer valor, Boolean activado) {
        SQLiteDatabase db = getReadableDatabase();
        //Creamos el registro a insertar como objeto ContentValues
        ContentValues nuevoParametrizacionImpacto = new ContentValues();
        nuevoParametrizacionImpacto.put(ProjectSizingConstants.ID_PROYECTO,idProyecto);
        nuevoParametrizacionImpacto.put(ProjectSizingConstants.ID_TIPO,idTipo);
        nuevoParametrizacionImpacto.put(ProjectSizingConstants.VALOR,valor);
        nuevoParametrizacionImpacto.put(ProjectSizingConstants.ACTIVADO,(activado)?1:0);

        //Insertamos el registro en la base de datos
        db.insert(ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_IMPACTO, null,
                nuevoParametrizacionImpacto);

        db.close();
    }

    @Override
    public void crearParametrizacionControlSeguridad (Long idProyecto, Long idTipo,
                                                      Integer valor, Boolean activado) {
        SQLiteDatabase db = getReadableDatabase();
        //Creamos el registro a insertar como objeto ContentValues
        ContentValues nuevoParametrizacionControlSeguridad = new ContentValues();
        nuevoParametrizacionControlSeguridad.put(ProjectSizingConstants.ID_PROYECTO,idProyecto);
        nuevoParametrizacionControlSeguridad.put(ProjectSizingConstants.ID_TIPO,idTipo);
        nuevoParametrizacionControlSeguridad.put(ProjectSizingConstants.VALOR,valor);
        nuevoParametrizacionControlSeguridad.put(ProjectSizingConstants.ACTIVADO,(activado)?1:0);

        //Insertamos el registro en la base de datos
        db.insert(ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_CONTROLSEGURIDAD, null,
                nuevoParametrizacionControlSeguridad);

        db.close();
    }

    @Override
    public void editarParametrizacionActivo(Long idParametrizacion, Integer rangoSuperior, Integer rangoInferior, Integer valor, Boolean activado) {
        SQLiteDatabase db = getReadableDatabase();
        //Creamos el registro a insertar como objeto ContentValues
        ContentValues editarParametrizacionActivo = new ContentValues();
        editarParametrizacionActivo.put(ProjectSizingConstants.RANGO_SUPERIOR,rangoSuperior);
        editarParametrizacionActivo.put(ProjectSizingConstants.RANGO_INFERIOR,rangoInferior);
        editarParametrizacionActivo.put(ProjectSizingConstants.VALOR,valor);
        editarParametrizacionActivo.put(ProjectSizingConstants.ACTIVADO,(activado)?1:0);

        //Editamos el registro en la base de datos
        db.update(ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_ACTIVO, editarParametrizacionActivo,
                ProjectSizingConstants.ID_PARAMETRIZACION_ACTIVO + "=" + idParametrizacion, null);

        db.close();
    }

    @Override
    public void editarParametrizacionVulnerabilidad(Long idParametrizacion, Integer valorTiempo, Integer valorTipoTiempo, Double valor, Boolean activado) {
        SQLiteDatabase db = getReadableDatabase();
        //Creamos el registro a editar como objeto ContentValues
        ContentValues editarParametrizacionVulnerabilidad = new ContentValues();
        editarParametrizacionVulnerabilidad.put(ProjectSizingConstants.VALOR_TIEMPO,valorTiempo);
        editarParametrizacionVulnerabilidad.put(ProjectSizingConstants.VALOR_TIPO_TIEMPO,valorTipoTiempo);
        editarParametrizacionVulnerabilidad.put(ProjectSizingConstants.VALOR,valor);
        editarParametrizacionVulnerabilidad.put(ProjectSizingConstants.ACTIVADO,(activado)?1:0);

        //Editamos el registro en la base de datos
        db.update(ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_VULNERABILIDAD,
                editarParametrizacionVulnerabilidad,
                ProjectSizingConstants.ID_PARAMETRIZACION_VULNERABILIDAD + "=" + idParametrizacion,
                null);

        db.close();
    }

    @Override
    public void editarParametrizacionImpacto(Long idParametrizacion, Integer valor, Boolean activado) {
        SQLiteDatabase db = getReadableDatabase();
        //Creamos el registro a editar como objeto ContentValues
        ContentValues editarParametrizacionImpacto = new ContentValues();
        editarParametrizacionImpacto.put(ProjectSizingConstants.VALOR,valor);
        editarParametrizacionImpacto.put(ProjectSizingConstants.ACTIVADO,(activado)?1:0);

        //Editamos el registro en la base de datos
        db.update(ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_IMPACTO,
                editarParametrizacionImpacto,
                ProjectSizingConstants.ID_PARAMETRIZACION_IMPACTO + "=" + idParametrizacion, null);

        db.close();
    }

    @Override
    public void editarParametrizacionControlSeguridad(Long idParametrizacion, Integer valor, Boolean activado) {

        SQLiteDatabase db = getReadableDatabase();
        //Creamos el registro a editar como objeto ContentValues
        ContentValues editarParametrizacionControlSeguridad = new ContentValues();
        editarParametrizacionControlSeguridad.put(ProjectSizingConstants.VALOR,valor);
        editarParametrizacionControlSeguridad.put(ProjectSizingConstants.ACTIVADO,(activado)?1:0);

        //Editamos el registro en la base de datos
        db.update(ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_CONTROLSEGURIDAD,
                editarParametrizacionControlSeguridad,
                ProjectSizingConstants.ID_PARAMETRIZACION_CONTROLSEGURIDAD + "=" + idParametrizacion,
                null);

        db.close();
    }

    @Override
    public List<AssetDTO> obtenerActivos(long idProyecto) {
        List <AssetDTO> activos = new ArrayList<AssetDTO>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " +
                AssetConstants.ID_ACTIVO + ", " + AssetConstants.ID_PROYECTO + ", " +
                AssetConstants.NOMBRE + ", " + AssetConstants.CODIGO + ", " +
                AssetConstants.DESCRIPCION + ", " + AssetConstants.RESPONSABLE + ", " +
                AssetConstants.UBICACION + " FROM " + AssetConstants.TABLE_NAME + " WHERE " +
                AssetConstants.ID_PROYECTO + " = " + idProyecto, null);

        if (cursor.moveToFirst()){
            do {
                Long idActivo = cursor.getLong(0);
                Long idProyectoBD = cursor.getLong(1);
                String nombre = cursor.getString(2);
                String codigo = cursor.getString(3);
                String descripcion = cursor.getString(4);
                String responsable = cursor.getString(5);
                String ubicacion = cursor.getString(6);

                AssetDTO asset = new AssetDTO(idActivo, idProyectoBD, nombre, codigo, descripcion,
                        responsable, ubicacion);
                activos.add(asset);
            } while ( (cursor.moveToNext()));
        }
        cursor.close();
        db.close();
        return activos;
    }

    @Override
    public long obtenerIdProyectoActivo() {

        Long idProyecto = null;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + ProjectConstants.ID_PROYECTO + " FROM " +
                ProjectConstants.TABLE_NAME + " WHERE " + ProjectConstants.ACTIVADO + "=" + 1 , null);
        if (cursor.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                idProyecto= cursor.getLong(0);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return idProyecto;
    }

    @Override
    public long crearActivo(Integer idProyecto, Integer idValoracionDisp, Integer idValoracionInt,
            Integer idValoracionConf, Integer idValoracionAut, Integer idValoracionTraza,
            String nombre, String codigo, String desc, String responsable, String ubicacion,
            String fechaCreacion) {

        SQLiteDatabase db = getReadableDatabase();

        //Creamos el registro a insertar como objeto ContentValues
        ContentValues nuevoActivo = new ContentValues();
        nuevoActivo.put(AssetConstants.ID_PROYECTO, idProyecto);
        nuevoActivo.put(AssetConstants.ID_VALORACION_ACTIVO_DISPONIBILIDAD,idValoracionDisp);
        nuevoActivo.put(AssetConstants.ID_VALORACION_ACTIVO_INTEGRIDAD,idValoracionInt);
        nuevoActivo.put(AssetConstants.ID_VALORACION_ACTIVO_CONFIDENCIALIDAD,idValoracionConf);
        nuevoActivo.put(AssetConstants.ID_VALORACION_ACTIVO_AUTENTICIDAD,idValoracionAut);
        nuevoActivo.put(AssetConstants.ID_VALORACION_ACTIVO_TRAZABILIDAD,idValoracionTraza);
        nuevoActivo.put(AssetConstants.NOMBRE,nombre);
        nuevoActivo.put(AssetConstants.CODIGO,codigo);
        nuevoActivo.put(AssetConstants.DESCRIPCION,desc);
        nuevoActivo.put(AssetConstants.RESPONSABLE,responsable);
        nuevoActivo.put(AssetConstants.UBICACION,ubicacion);
        nuevoActivo.put(AssetConstants.FECHA_CREACION,fechaCreacion);

        //Insertamos el registro en la base de datos
        long id = db.insert(AssetConstants.TABLE_NAME, null, nuevoActivo);
        db.close();
        return id;
    }

    @Override
    public Integer comprobarNombreYCodigoActivoUnicos(String nombre, String codigo, Integer idProyecto,
    Integer idActivo) {

        Integer returnValue = -1;

        String query = "SELECT " + AssetConstants.ID_ACTIVO + " FROM " +
                AssetConstants.TABLE_NAME + " WHERE " + AssetConstants.NOMBRE + " LIKE '" + nombre +
                "' AND " + AssetConstants.ID_PROYECTO + "=" + idProyecto;

        if (idActivo != GlobalConstants.NULL_ID_ACTIVO.intValue()) {
            query += " AND " + AssetConstants.ID_ACTIVO + "!=" + idActivo;
        }

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if ((cursor.moveToFirst()) || cursor.getCount() > 0){
            cursor.close();
            db.close();
            return 1;
        }

        query = "SELECT " + AssetConstants.ID_ACTIVO + " FROM " +
                AssetConstants.TABLE_NAME + " WHERE " + AssetConstants.CODIGO + " LIKE '" + codigo +
                "' AND " + AssetConstants.ID_PROYECTO + "=" + idProyecto;

        if (idActivo != GlobalConstants.NULL_ID_ACTIVO.intValue()) {
            query += " AND " + AssetConstants.ID_ACTIVO + "!=" + idActivo;
        }
        cursor.close();
        cursor = db.rawQuery(query, null);

        if ((cursor.moveToFirst()) || cursor.getCount() > 0){
            cursor.close();
            db.close();
            return 2;
        }

        //Devuelvo -1 si OK, 1 si Nombre repetido, 2 si codigo repetido.
        cursor.close();
        db.close();
        return returnValue;
    }

    @Override
    public long crearActivoTipoActivo(Integer idActivo,
                              Integer idProyecto, Integer idLista, Integer idTipoActivo) {

        SQLiteDatabase db = getReadableDatabase();

        //Creamos el registro a insertar como objeto ContentValues
        ContentValues nuevoActivoTipoActivo = new ContentValues();
        nuevoActivoTipoActivo.put(AssetConstants.ID_ACTIVO_TABLA_TIPO_ACTIVO, idActivo);
        nuevoActivoTipoActivo.put(AssetConstants.ID_PROYECTO,idProyecto);
        nuevoActivoTipoActivo.put(AssetConstants.ID_LISTA_TIPO_ACTIVO,idLista);
        nuevoActivoTipoActivo.put(AssetConstants.ID_TIPO_ACTIVO,idTipoActivo);

        //Insertamos el registro en la base de datos
        long id = db.insert(AssetConstants.TABLE_NAME_ACTIVO_TIPO_ACTIVO, null, nuevoActivoTipoActivo);
        db.close();
        return id;
    }

    @Override
    public boolean eliminarActivosYTiposActivo(Long idActivo) {
        SQLiteDatabase db = getReadableDatabase();
        db.delete(AssetConstants.TABLE_NAME, AssetConstants.ID_ACTIVO + "=" + idActivo, null);
        db.delete(AssetConstants.TABLE_NAME_ACTIVO_TIPO_ACTIVO, AssetConstants.ID_ACTIVO_TABLA_TIPO_ACTIVO
                + "=" + idActivo, null);
        db.close();
        return true;

    }

    @Override
    public Asset obtenerActivo (Long idActivo) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(GlobalConstants.DATE_FORMAT);
        Asset as = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + AssetConstants.TABLE_NAME + " WHERE "
                + AssetConstants.ID_ACTIVO + "=" + idActivo , null);

        if (cursor.moveToFirst()) {
            Long idActivoDB = cursor.getLong(0);
            Long idProyecto = cursor.getLong(1);
            Long idValoracionDisp = null;
            if (!cursor.isNull(2))
                idValoracionDisp = cursor.getLong(2);

            Long idValoracionInt = null;
            if (!cursor.isNull(3))
                idValoracionInt = cursor.getLong(3);

            Long idValoracionConf = null;
            if (!cursor.isNull(4))
                idValoracionConf = cursor.getLong(4);

            Long idValoracionAut = null;
            if(!cursor.isNull(5))
                    idValoracionAut = cursor.getLong(5);

            Long idValoracionTraz = null;
            if (!cursor.isNull(6))
                idValoracionTraz = cursor.getLong(6);

            String nombre = cursor.getString(7);
            String codigo = cursor.getString(8);
            String desc = cursor.getString(9);
            String resp = cursor.getString(10);
            String ubicacion = cursor.getString(11);
            String fechaCreacion = cursor.getString(12);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateFormat.parse(fechaCreacion));

            as = new Asset(idActivoDB, idProyecto, idValoracionDisp, idValoracionInt, idValoracionConf,
            idValoracionAut, idValoracionTraz, nombre, codigo, desc, resp, ubicacion, cal);
        }
        cursor.close();
        db.close();
        return as;
    }

    @Override
    public List<AssetAssetType> obtenerTiposDeActivo (Long idActivo)
    {
        List<AssetAssetType> tiposActivo = new ArrayList<AssetAssetType>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT *" + " FROM " + AssetConstants.TABLE_NAME_ACTIVO_TIPO_ACTIVO
                + " WHERE " + AssetConstants.ID_ACTIVO_TABLA_TIPO_ACTIVO + " = " + idActivo, null);

        if (cursor.moveToFirst()){
            do {
                Long idActivoTipoActivo = cursor.getLong(0);
                Long idActivob = cursor.getLong(1);
                Long idProyecto = cursor.getLong(2);
                Long idListaTipo = cursor.getLong(3);
                Long idTipo = cursor.getLong(4);

                AssetAssetType assetType = new AssetAssetType(idActivoTipoActivo, idActivob,
                        idProyecto, idListaTipo, idTipo);

                tiposActivo.add(assetType);
            } while ( (cursor.moveToNext()));
        }

        cursor.close();
        db.close();
        return  tiposActivo;
    }


    @Override
    public long editarActivo(Long idActivo, Integer idValoracionDisp, Integer idValoracionInt,
                             Integer idValoracionConf, Integer idValoracionAut, Integer idValoracionTraza,
                             String nombre, String codigo, String desc, String responsable, String ubicacion) {
        SQLiteDatabase db = getReadableDatabase();

        //Creamos el registro a editar como objeto ContentValues
        ContentValues nuevoActivo = new ContentValues();
        nuevoActivo.put(AssetConstants.ID_VALORACION_ACTIVO_DISPONIBILIDAD,idValoracionDisp);
        nuevoActivo.put(AssetConstants.ID_VALORACION_ACTIVO_INTEGRIDAD,idValoracionInt);
        nuevoActivo.put(AssetConstants.ID_VALORACION_ACTIVO_CONFIDENCIALIDAD,idValoracionConf);
        nuevoActivo.put(AssetConstants.ID_VALORACION_ACTIVO_AUTENTICIDAD,idValoracionAut);
        nuevoActivo.put(AssetConstants.ID_VALORACION_ACTIVO_TRAZABILIDAD,idValoracionTraza);
        nuevoActivo.put(AssetConstants.NOMBRE,nombre);
        nuevoActivo.put(AssetConstants.CODIGO,codigo);
        nuevoActivo.put(AssetConstants.DESCRIPCION,desc);
        nuevoActivo.put(AssetConstants.RESPONSABLE,responsable);
        nuevoActivo.put(AssetConstants.UBICACION,ubicacion);

        //Insertamos el registro en la base de datos
        long id = db.update(AssetConstants.TABLE_NAME, nuevoActivo, AssetConstants.ID_ACTIVO
                + "=" + idActivo, null);
        db.close();
        return id;
    }

    @Override
    public boolean eliminarTiposActivoDeActivo(Long idActivo)
    {
        SQLiteDatabase db = getReadableDatabase();
        db.delete(AssetConstants.TABLE_NAME_ACTIVO_TIPO_ACTIVO, AssetConstants.ID_ACTIVO_TABLA_TIPO_ACTIVO
                + "=" + idActivo, null);
        db.close();
        return true;
    }

    @Override
    public List<ThreatDTO> obtenerAmenazas(long idProyecto) {
        List <ThreatDTO> amenazas = new ArrayList<ThreatDTO>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT " +
                ThreatConstants.ID_LISTA_TIPO_AMENAZA + ", " + ThreatConstants.ID_TIPO_AMENAZA  +
                " FROM " + ThreatConstants.TABLE_NAME + " WHERE " +
                AssetConstants.ID_PROYECTO + " = " + idProyecto, null);
        if (cursor.moveToFirst()){
            do {
                Long idTipoLista = cursor.getLong(0);
                Long idTipoAmenaza = cursor.getLong(1);
                ThreatDTO threatDTO = new ThreatDTO(idTipoLista, idTipoAmenaza);
                amenazas.add(threatDTO);
            } while ( (cursor.moveToNext()));
        }
        cursor.close();
        db.close();
        return amenazas;
    }

    @Override
    public List<AssetDTO> obtenerActivosPorId(long idProyecto, List<Long> idsActivos) {
        List <AssetDTO> activos = new ArrayList<AssetDTO>();

        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT " +
                AssetConstants.ID_ACTIVO + ", " + AssetConstants.ID_PROYECTO + ", " +
                AssetConstants.NOMBRE + ", " + AssetConstants.CODIGO + ", " +
                AssetConstants.DESCRIPCION + ", " + AssetConstants.RESPONSABLE + ", " +
                AssetConstants.UBICACION + " FROM " + AssetConstants.TABLE_NAME + " WHERE " +
                AssetConstants.ID_PROYECTO + " = " + idProyecto;

        if (!idsActivos.isEmpty())
        {
            query += " AND " +  AssetConstants.ID_ACTIVO + " IN (" + android.text.TextUtils.join(",", idsActivos)
                + ")";
        }


        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do {
                Long idActivo = cursor.getLong(0);
                Long idProyectoBD = cursor.getLong(1);
                String nombre = cursor.getString(2);
                String codigo = cursor.getString(3);
                String descripcion = cursor.getString(4);
                String responsable = cursor.getString(5);
                String ubicacion = cursor.getString(6);

                AssetDTO asset = new AssetDTO(idActivo, idProyectoBD, nombre, codigo, descripcion,
                        responsable, ubicacion);
                activos.add(asset);
            } while ( (cursor.moveToNext()));
        }
        cursor.close();
        db.close();
        return activos;
    }

    @Override
    public long crearAmenaza(Long idActivo, Long idProyecto, Long idListaTipoAmenaza,
                             Long idTipoAmenaza, Integer idDegradacionDisponibilidad,
                             Integer idProbabilidadDisponibilidad, Integer idDegradacionIntegridad,
                             Integer idProbabilidadIntegridad, Integer idDegradacionConfidencialidad,
                             Integer idProbabilidadConfidencialidad, Integer idDegradacionAutenticidad,
                             Integer idProbabilidadAutenticidad, Integer idDegradacionTrazabilidad,
                             Integer idProbabilidadTrazabilidad, String fechaCreacion) {

        SQLiteDatabase db = getReadableDatabase();

        //Creamos el registro a insertar como objeto ContentValues
        ContentValues nuevaAmenaza = new ContentValues();
        nuevaAmenaza.put(ThreatConstants.ID_ACTIVO,idActivo);
        nuevaAmenaza.put(ThreatConstants.ID_PROYECTO, idProyecto);
        nuevaAmenaza.put(ThreatConstants.ID_LISTA_TIPO_AMENAZA, idListaTipoAmenaza);
        nuevaAmenaza.put(ThreatConstants.ID_TIPO_AMENAZA, idTipoAmenaza);
        nuevaAmenaza.put(ThreatConstants.ID_VALORACION_DEGRADACION_DISPONIBILIDAD, idDegradacionDisponibilidad);
        nuevaAmenaza.put(ThreatConstants.ID_VALORACION_PROBABILIDAD_DISPONIBILIDAD, idProbabilidadDisponibilidad);
        nuevaAmenaza.put(ThreatConstants.ID_VALORACION_DEGRADACION_INTEGRIDAD, idDegradacionIntegridad);
        nuevaAmenaza.put(ThreatConstants.ID_VALORACION_PROBABILIDAD_INTEGRIDAD, idProbabilidadIntegridad);
        nuevaAmenaza.put(ThreatConstants.ID_VALORACION_DEGRADACION_CONFIDENCIALIDAD, idDegradacionConfidencialidad);
        nuevaAmenaza.put(ThreatConstants.ID_VALORACION_PROBABILIDAD_CONFIDENCIALIDAD, idProbabilidadConfidencialidad);
        nuevaAmenaza.put(ThreatConstants.ID_VALORACION_DEGRADACION_AUTENTICIDAD, idDegradacionAutenticidad);
        nuevaAmenaza.put(ThreatConstants.ID_VALORACION_PROBABILIDAD_AUTENTICIDAD, idProbabilidadAutenticidad);
        nuevaAmenaza.put(ThreatConstants.ID_VALORACION_DEGRADACION_TRAZABILIDAD, idDegradacionTrazabilidad);
        nuevaAmenaza.put(ThreatConstants.ID_VALORACION_PROBABILIDAD_TRAZABILIDAD, idProbabilidadTrazabilidad);
        nuevaAmenaza.put(ThreatConstants.FECHA_CREACION,fechaCreacion);
        //Insertamos el registro en la base de datos
        long id = db.insert(ThreatConstants.TABLE_NAME, null, nuevaAmenaza);
        db.close();
        return id;
    }

    @Override
    public boolean eliminarAmenaza(Long idListaTipoAmenaza, Long idTipoAmenaza, Long idProyecto) {
        SQLiteDatabase db = getReadableDatabase();
        db.delete(ThreatConstants.TABLE_NAME, ThreatConstants.ID_PROYECTO
                + "=" + idProyecto + " AND " + ThreatConstants.ID_LISTA_TIPO_AMENAZA + "=" +
                idListaTipoAmenaza + " AND " + ThreatConstants.ID_TIPO_AMENAZA + "=" + idTipoAmenaza, null);
        db.close();
        return true;

    }

}
