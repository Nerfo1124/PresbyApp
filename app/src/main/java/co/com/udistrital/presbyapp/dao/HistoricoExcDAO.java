package co.com.udistrital.presbyapp.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import co.com.udistrital.presbyapp.dao.database.DataBaseHelper;
import co.com.udistrital.presbyapp.vo.HistoricoExcVO;

/**
 * Created by Fernando on 26/03/2016.
 */
public class HistoricoExcDAO {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static String TAG_LOG = "[Sin_nombre]";

    public DataBaseHelper dbh;
    public SQLiteDatabase db;
    public Context contexto;

    public HistoricoExcDAO(Context context) {
        try {
            contexto = context;
            dbh = new DataBaseHelper(context);
            db = dbh.getWritableDatabase();
        } catch (Exception e) {
            Log.e(TAG_LOG, "[EjercicioDAO] Error en EjercicioDAO: " + e.toString(), e);
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de consultar todos los registros de la tabla HISTORICO_EJERCICIO.
     *
     * @return
     */
    public List<HistoricoExcVO> list(int idUsuario) {
        try {
            List<HistoricoExcVO> listaHistorico = new ArrayList<HistoricoExcVO>();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ").append(dbh.TABLE_NAME_HISTORICO_EJER);
            sb.append(" WHERE " + dbh.HISTORICO_EJER_USU + " = ").append(idUsuario);
            sb.append(" ORDER BY " + dbh.HISTORICO_EJER_FECHA + " DESC");

            Log.d(TAG_LOG, "[list] SQL: " + sb.toString());

            Cursor listHistorico = db.rawQuery(sb.toString(), null);
            if (listHistorico.moveToFirst()) {
                do {
                    HistoricoExcVO vo = new HistoricoExcVO();
                    vo.setIdHistorico(listHistorico.getInt(0));
                    vo.setIdUsuario(listHistorico.getInt(1));
                    vo.setIdEjercicio(listHistorico.getInt(2));
                    vo.setFechaRegistro(sdf.parse(listHistorico.getString(3)));
                    listaHistorico.add(vo);
                } while (listHistorico.moveToNext());
            }
            return listaHistorico;
        } catch (Exception e) {
            Log.e(TAG_LOG, "[list] Error en HistoricoExcDAO: " + e.toString(), e);
            return null;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de realizar la insercion de datos en la tabla de EJERCICIO
     *
     * @param vo
     * @return
     */
    public boolean insert(HistoricoExcVO vo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ").append(dbh.TABLE_NAME_HISTORICO_EJER).append("(");
            sb.append(dbh.HISTORICO_EJER_USU + " , ").append(dbh.HISTORICO_EJER_EJERCICIO + " , ").append(dbh.HISTORICO_EJER_FECHA).append(")");
            sb.append(" VALUES (");
            sb.append("" + vo.getIdUsuario() + ",").append(vo.getIdEjercicio() + ",'").append(sdf.format(vo.getFechaRegistro()) + "'");
            sb.append(")");

            Log.d(TAG_LOG, "[insert] SQL: " + sb.toString());

            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            Log.e(TAG_LOG, "[insert] Error en HistoricoExcDAO: " + e.toString(), e);
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
            sb.append("SELECT MAX(" + dbh.HISTORICO_EJER_ID + ") FROM " + dbh.TABLE_NAME_HISTORICO_EJER);

            Log.d(TAG_LOG, "[consultLastID] SQL: " + sb.toString());

            Cursor id = db.rawQuery(sb.toString(), null);
            if (id.moveToFirst()) {
                response = id.getInt(0);
            }
        } catch (Exception ex) {
            Log.e(TAG_LOG, "[consultLastID] Error durante la ejecucion del metodo.", ex);
        }
        return response;
    }
}
