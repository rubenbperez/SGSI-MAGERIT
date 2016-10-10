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

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS " + ProjectConstants.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " +  ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_ACTIVO);
        db.execSQL("DROP TABLE IF EXISTS " +  ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_VULNERABILIDAD);
        db.execSQL("DROP TABLE IF EXISTS " +  ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_IMPACTO);
        db.execSQL("DROP TABLE IF EXISTS " +  ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_CONTROLSEGURIDAD);
        db.execSQL("DROP TABLE IF EXISTS " +  AssetConstants.TABLE_NAME);

        //Se crea la nueva versión de la tabla
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
        return db.insert(ProjectConstants.TABLE_NAME, null, nuevoProyecto);
    }

    @Override
    public boolean eliminarProyectoYDimensionamiento(Long idProyecto) {
        SQLiteDatabase db = getReadableDatabase();
        db.delete(ProjectConstants.TABLE_NAME, ProjectConstants.ID_PROYECTO + "=" + idProyecto,null);
        db.delete(ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_ACTIVO, ProjectSizingConstants.ID_PROYECTO + "=" + idProyecto,null);
        db.delete(ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_VULNERABILIDAD, ProjectSizingConstants.ID_PROYECTO + "=" + idProyecto,null);
        db.delete(ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_IMPACTO, ProjectSizingConstants.ID_PROYECTO + "=" + idProyecto,null);
        db.delete(ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_CONTROLSEGURIDAD, ProjectSizingConstants.ID_PROYECTO + "=" + idProyecto,null);
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
        return db.update(ProjectConstants.TABLE_NAME, nuevoProyecto, ProjectConstants.ID_PROYECTO
                + "=" + idProyecto, null);
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
            cursor.close();
        }
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
        return db.insert(AssetConstants.TABLE_NAME, null, nuevoActivo);
    }

    @Override
    public Integer comprobarNombreYCodigoActivoUnicos(String nombre, String codigo, Integer idProyecto) {

        Integer returnValue = -1;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + AssetConstants.ID_ACTIVO + " FROM " +
                AssetConstants.TABLE_NAME + " WHERE " + AssetConstants.NOMBRE + " LIKE '" + nombre +
                "' AND " + AssetConstants.ID_PROYECTO + "=" + idProyecto, null);

        if ((cursor.moveToFirst()) || cursor.getCount() > 0){
            cursor.close();
            return 1;
        }

        cursor = db.rawQuery("SELECT " + AssetConstants.ID_ACTIVO + " FROM " +
                AssetConstants.TABLE_NAME + " WHERE " + AssetConstants.CODIGO + " LIKE '" + codigo +
                "' AND " + AssetConstants.ID_PROYECTO + "=" + idProyecto, null);

        if ((cursor.moveToFirst()) || cursor.getCount() > 0){
            cursor.close();
            return 2;
        }

        //Devuelvo -1 si OK, 1 si Nombre repetido, 2 si codigo repetido.
        cursor.close();
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
        return db.insert(AssetConstants.TABLE_NAME_ACTIVO_TIPO_ACTIVO, null, nuevoActivoTipoActivo);
    }



}
