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
import co.com.udistrital.presbyapp.vo.HistoricoLetraFrecuenciaVO;

/**
 * Created by Usuario on 09/06/2016.
 */
public class HistoricoLetraFrecuencuaDAO {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static String TAG_LOG = "[Sin_nombre]";

    public DataBaseHelper dbh;
    public SQLiteDatabase db;
    public Context contexto;

    public HistoricoLetraFrecuencuaDAO(Context context) {
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
    public List<HistoricoLetraFrecuenciaVO> list(int idUsuario) {
        try {
            List<HistoricoLetraFrecuenciaVO> listaHistorico = new ArrayList<HistoricoLetraFrecuenciaVO>();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ").append(dbh.TABLE_NAME_HISTORICO_LETRA_FREC);
            sb.append(" WHERE " + dbh.HISTORICO_LET_USU + " = ").append(idUsuario);
            sb.append(" ORDER BY " + dbh.HISTORICO_LET_FECHA + " DESC");

            Log.d(TAG_LOG, "[list] SQL: " + sb.toString());

            Cursor listHistorico = db.rawQuery(sb.toString(), null);
            if (listHistorico.moveToFirst()) {
                do {
                    HistoricoLetraFrecuenciaVO vo = new HistoricoLetraFrecuenciaVO();
                    vo.setId(listHistorico.getInt(0));
                    vo.setIdUsuario(listHistorico.getInt(1));
                    vo.setFrecuencia("" + listHistorico.getInt(2));
                    vo.setTamaño("" + listHistorico.getInt(3));
                    vo.setFechaHistorico(sdf.parse(listHistorico.getString(3)));
                    listaHistorico.add(vo);
                } while (listHistorico.moveToNext());
            }
            return listaHistorico;
        } catch (Exception e) {
            Log.e(TAG_LOG, "[list] Error en HistoricoLetraFrecuenciaVO: " + e.toString(), e);
            return null;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de realizar la insercion de datos en la tabla de EJERCICIO
     *
     * @param vo
     * @return
     */
    public boolean insert(HistoricoLetraFrecuenciaVO vo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ").append(dbh.TABLE_NAME_HISTORICO_LETRA_FREC).append("(");
            sb.append(dbh.HISTORICO_LET_USU + " , ").append(dbh.HISTORICO_LET_FRECUENCIA + " , ").append(dbh.HISTORICO_LET_LETRA+" , ").
                    append(dbh.HISTORICO_LET_FECHA).append(")");
            sb.append(" VALUES (");
            sb.append("" + vo.getIdUsuario() + ",").append(vo.getFrecuencia() +",").append(vo.getTamaño()+ ",'").append(sdf.format(vo.getFechaHistorico()) + "'");
            sb.append(")");
            Log.e("[1]", "[insert] SQL: " + sb.toString());
            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            Log.e(TAG_LOG, "[insert] Error en HistoricoLetraFrecuencia: " + e.toString(), e);
            return false;
        }
    }
}
