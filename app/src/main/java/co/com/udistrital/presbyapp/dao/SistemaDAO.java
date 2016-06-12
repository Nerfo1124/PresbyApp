package co.com.udistrital.presbyapp.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.com.udistrital.presbyapp.dao.database.DataBaseHelper;
import co.com.udistrital.presbyapp.vo.SistemaVO;

/**
 * <b>Descripcion: </b> Clase encargada de administrar las consultas en la BDD de la tabla SISTEMA.
 *
 * Created by Fernando on 17/01/2016.
 * @author Fernando Hernandez
 */
public class SistemaDAO {

    private static String TAG_LOG = "[PresbiciaApp]";

    public DataBaseHelper dbh;
    public SQLiteDatabase db;
    public Context contexto;

    public SistemaDAO(Context context){
        try {
            contexto = context;
            dbh = new DataBaseHelper(context);
            db = dbh.getWritableDatabase();
        } catch (Exception e){
            Log.e(TAG_LOG, "[SistemaDAO] Error en SistemaDAO: " + e.toString());
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de consultar todos los registros de la tabla SISTEMA.
     * @return
     */
    public List<SistemaVO> list() {
        try {
            List<SistemaVO> listaSistema = new ArrayList<SistemaVO>();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ").append(dbh.TABLE_NAME_SISTEMA);

            Log.d(TAG_LOG, "[list] SQL: " + sb.toString());

            Cursor listSistemas = db.rawQuery(sb.toString(), null);
            if(listSistemas.moveToFirst()){
                do{
                    SistemaVO vo = new SistemaVO();
                    vo.setIdSistema(listSistemas.getInt(0));
                    vo.setTamanoFuente(listSistemas.getInt(1));
                    vo.setFrecuencia(listSistemas.getInt(2));
                    listaSistema.add(vo);
                } while(listSistemas.moveToNext());
            }
            return listaSistema;
        } catch (Exception e){
            Log.e(TAG_LOG, "[list] Error en SesionDAO: " + e.toString());
            return null;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de realizar la consulta de un Registro en la tabla
     * SISTEMA de la BDD recibiendo como parametro el id del Registro.
     * @param idSistema
     * @return
     */
    public SistemaVO consult(int idSistema) {
        SistemaVO objSistema = new SistemaVO();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ").append(dbh.TABLE_NAME_SISTEMA);
            sb.append(" WHERE ").append(dbh.SISTEMA_ID).append(" = ").append(idSistema);

            Log.d(TAG_LOG, "[consult] SQL: " + sb.toString());

            Cursor fila = db.rawQuery(sb.toString(), null);
            fila.moveToFirst();
            if (fila != null){
                objSistema.setIdSistema(fila.getInt(0));
                objSistema.setTamanoFuente(fila.getInt(1));
                objSistema.setFrecuencia(fila.getInt(2));
            }
            return objSistema;
        } catch (Exception e){
            Log.e(TAG_LOG, "[consult] Error en SistemaDAO: " + e.toString());
            return null;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de realizar la insercion de datos en la tabla de SISTEMA
     * @param vo
     * @return
     */
    public boolean insert(SistemaVO vo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ").append(dbh.TABLE_NAME_SISTEMA).append("(");
            sb.append(dbh.SISTEMA_TAM_FUENTE + ",").append(dbh.SISTEMA_FRECUENCIA + ")");
            sb.append(" VALUES (");
            sb.append(vo.getTamanoFuente() + ",").append(vo.getFrecuencia());
            sb.append(")");

            Log.d(TAG_LOG, "[insert] SQL: " + sb.toString());

            db.execSQL(sb.toString());
            return true;
        } catch (Exception e){
            Log.e(TAG_LOG, "[insert] Error en SistemaDAO: " + e.toString());
            return false;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de actualizar un Registro de la tabla SISTEMA recibiendo
     * como parametro un objeto con los valores a actualizar.
     * @param vo
     * @return
     */
    public boolean update(SistemaVO vo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ").append(dbh.TABLE_NAME_SISTEMA).append(" SET ");
            sb.append(dbh.SISTEMA_TAM_FUENTE).append("=").append(vo.getTamanoFuente()).append(",");
            sb.append(dbh.SISTEMA_FRECUENCIA).append("=").append(vo.getFrecuencia()).append(" ");
            sb.append("WHERE ").append(dbh.SISTEMA_ID).append("=").append(vo.getIdSistema());

            Log.d(TAG_LOG, "[update] SQL: " + sb.toString());

            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            Log.e(TAG_LOG, "[update] Error en SistemanDAO " + e.toString());
            return false;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de realizar el borrado de un Registro en la tabla SISTEMA
     * recibiendo como parametro el id del Registro a eliminar.
     * @param idSistema
     * @return
     */
    public boolean delete(int idSistema) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ").append(dbh.TABLE_NAME_SISTEMA);
            sb.append(" WHERE ").append(dbh.SISTEMA_ID).append(" = ").append(idSistema);

            Log.d(TAG_LOG, "[delete] SQL: " + sb.toString());
            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            Log.e(TAG_LOG, "[delete] Error en SistemaDAO: " + e.toString());
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
            sb.append("SELECT MAX(" + dbh.SISTEMA_ID + ") FROM " + dbh.TABLE_NAME_SISTEMA);

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
