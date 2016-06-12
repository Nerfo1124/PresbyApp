package co.com.udistrital.presbyapp.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.com.udistrital.presbyapp.dao.database.DataBaseHelper;
import co.com.udistrital.presbyapp.vo.EjercicioVO;

/**
 * Created by Fernando on 26/03/2016.
 */
public class EjercicioDAO {

    private static String TAG_LOG = "[PresbyApp]";

    public DataBaseHelper dbh;
    public SQLiteDatabase db;
    public Context contexto;

    public EjercicioDAO(Context context) {
        try {
            contexto = context;
            dbh = new DataBaseHelper(context);
            db = dbh.getWritableDatabase();
        } catch (Exception e) {
            Log.e(TAG_LOG, "[EjercicioDAO] Error en EjercicioDAO: " + e.toString(), e);
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de consultar todos los registros de la tabla EJERCICIO.
     *
     * @return
     */
    public List<EjercicioVO> list() {
        try {
            List<EjercicioVO> listaEjercicio = new ArrayList<EjercicioVO>();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ").append(dbh.TABLE_NAME_EJERCICIO);

            Log.d(TAG_LOG, "[list] SQL: " + sb.toString());

            Cursor listEjercicios = db.rawQuery(sb.toString(), null);
            if (listEjercicios.moveToFirst()) {
                do {
                    EjercicioVO vo = new EjercicioVO();
                    vo.setIdEjercicio(listEjercicios.getInt(0));
                    vo.setNombre(listEjercicios.getString(1));
                    vo.setImagenURL(listEjercicios.getString(2));
                    vo.setDescripcion(listEjercicios.getString(3));
                    listaEjercicio.add(vo);
                } while (listEjercicios.moveToNext());
            }
            return listaEjercicio;
        } catch (Exception e) {
            Log.e(TAG_LOG, "[list] Error en EjercicioDAO: " + e.toString(), e);
            return null;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de realizar la consulta de un Registro en la tabla
     * EJERCICIO de la BDD recibiendo como parametro el id del Registro.
     *
     * @param idEjercicio
     * @return
     */
    public EjercicioVO consult(int idEjercicio) {
        EjercicioVO objEjercicio = new EjercicioVO();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ").append(dbh.TABLE_NAME_EJERCICIO);
            sb.append(" WHERE ").append(dbh.EJERCICIO_ID).append(" = ").append(idEjercicio);

            Log.d(TAG_LOG, "[consult] SQL: " + sb.toString());

            Cursor fila = db.rawQuery(sb.toString(), null);
            fila.moveToFirst();
            if (fila != null) {
                objEjercicio.setIdEjercicio(fila.getInt(0));
                objEjercicio.setNombre(fila.getString(1));
                objEjercicio.setImagenURL(fila.getString(2));
                objEjercicio.setDescripcion(fila.getString(3));
            }
            return objEjercicio;
        } catch (Exception e) {
            Log.e(TAG_LOG, "[consult] Error en EjercicioDAO: " + e.toString(), e);
            return null;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de realizar la insercion de datos en la tabla de EJERCICIO
     *
     * @param vo
     * @return
     */
    public boolean insert(EjercicioVO vo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ").append(dbh.TABLE_NAME_EJERCICIO).append("(");
            sb.append(dbh.EJERCICIO_NOMBRE + " , ").append(dbh.EJERCICIO_IMAGEN + " , ").append(dbh.EJERCICIO_DESCRIPCION).append(")");
            sb.append(" VALUES (");
            sb.append("'" + vo.getNombre() + "','").append(vo.getImagenURL() + "','").append(vo.getDescripcion() + "'");
            sb.append(")");

            Log.d(TAG_LOG, "[insert] SQL: " + sb.toString());

            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            Log.e(TAG_LOG, "[insert] Error en EjercicioDAO: " + e.toString(), e);
            return false;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de actualizar un Registro de la tabla Ejercicio recibiendo
     * como parametro un objeto con los valores a actualizar.
     *
     * @param vo
     * @return
     */
    public boolean update(EjercicioVO vo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ").append(dbh.TABLE_NAME_EJERCICIO).append(" SET ");
            sb.append(dbh.EJERCICIO_NOMBRE).append("='").append(vo.getNombre()).append("',");
            sb.append(dbh.EJERCICIO_IMAGEN).append("='").append(vo.getImagenURL()).append("', ");
            sb.append(dbh.EJERCICIO_DESCRIPCION).append("='").append(vo.getDescripcion()).append("' ");
            sb.append("WHERE ").append(dbh.EJERCICIO_ID).append("=").append(vo.getIdEjercicio());

            Log.d(TAG_LOG, "[update] SQL: " + sb.toString());

            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            Log.e(TAG_LOG, "[update] Error en E " + e.toString(), e);
            return false;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de realizar el borrado de un Registro en la tabla EJERCICIO
     *
     * @param idEjercicio
     * @return
     */
    public boolean delete(int idEjercicio) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ").append(dbh.TABLE_NAME_EJERCICIO);
            sb.append(" WHERE ").append(dbh.EJERCICIO_ID).append(" = ").append(idEjercicio);

            Log.d(TAG_LOG, "[delete] SQL: " + sb.toString());

            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            Log.e(TAG_LOG, "[delete] Error en SesionDAO: " + e.toString(), e);
            return false;
        }
    }

    /**
     * <b>Descripcion: </b> Metodo encargado de devolver el id del ultimo registro en BDD.
     *
     * @return
     */
    public int consultLastID() {
        int response = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT MAX(" + dbh.EJERCICIO_ID + ") FROM " + dbh.TABLE_NAME_EJERCICIO);

            Log.d(TAG_LOG, "[consultLastID] SQL: " + sb.toString());

            Cursor id = db.rawQuery(sb.toString(), null);
            if (id.moveToFirst()) {
                response = id.getInt(0);
            }
        } catch (Exception ex) {
            Log.e(TAG_LOG, "[consultLastID] Error durante la ejecucion del metodo.");
        }
        return response;
    }
}
