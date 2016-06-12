package co.com.udistrital.presbyapp.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.com.udistrital.presbyapp.dao.database.DataBaseHelper;
import co.com.udistrital.presbyapp.vo.ReestablecerVO;

/**
 * <b>Descripcion: </b> Clase encargada de administrar las consultas en la BDD de la tabla
 * RESTABLECER.
 *
 * Created by Fernando on 17/01/2016.
 * @author Fernando Hernandez
 */
public class RestablecerDAO {

    private static String TAG_LOG = "[Sin_nombre]";

    public DataBaseHelper dbh;
    public SQLiteDatabase db;
    public Context contexto;

    public RestablecerDAO(Context context){
        try {
            contexto = context;
            dbh = new DataBaseHelper(context);
            db = dbh.getWritableDatabase();
        } catch (Exception e){
            Log.e(TAG_LOG, "[RestablecerDAO] Error en RestablecerDAO: " + e.toString());
        }
    }

    public List<ReestablecerVO> list() {
        try {
            List<ReestablecerVO> listaRes = new ArrayList<ReestablecerVO>();
            String columns[] = {dbh.RESTABLECER_ID, dbh.RESTABLECER_QUEST1, dbh.RESTABLECER_ANSW1,
                                dbh.RESTABLECER_QUEST2, dbh.RESTABLECER_ANSW2,dbh.RESTABLECER_TAMANO_FUENTE};
            Cursor fila = db.query(dbh.TABLE_NAME_RESTABLECER,columns,null,null,null,null,null);
            if(fila.moveToFirst()){
                do{
                    ReestablecerVO vo = new ReestablecerVO();
                    vo.setIdReestablecer(fila.getInt(0));
                    vo.setPregunta1(fila.getString(1));
                    vo.setRespuesta1(fila.getString(2));
                    vo.setPregunta2(fila.getString(3));
                    vo.setRespuesta2(fila.getString(4));
                    vo.setIdReestablecer(fila.getInt(5));
                    listaRes.add(vo);
                } while(fila.moveToNext());
            }
            return listaRes;
        } catch (Exception e){
            Log.e(TAG_LOG, "[list] Error en RestablecerDAO: " + e.toString());
            return null;
        }
    }

    public ReestablecerVO consult(int idRestablecer) {
        ReestablecerVO objRes = new ReestablecerVO();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ").append(dbh.TABLE_NAME_RESTABLECER);
            sb.append(" WHERE ").append(dbh.RESTABLECER_ID).append(" = ").append(idRestablecer);

            Log.d(TAG_LOG, "[consult] SQL: " + sb.toString());

            Cursor fila = db.rawQuery(sb.toString(), null);
            fila.moveToFirst();
            if (fila != null){
                objRes.setIdReestablecer(fila.getInt(0));
                objRes.setPregunta1(fila.getString(1));
                objRes.setRespuesta1(fila.getString(2));
                objRes.setPregunta2(fila.getString(3));
                objRes.setRespuesta2(fila.getString(4));
                objRes.setTamanoFuente(fila.getString(5));
            }
            return objRes;
        } catch (Exception e){
            Log.e(TAG_LOG, "[consult] Error en FormulaDAO - consult: " + e.toString());
            return null;
        }
    }

    public boolean insert(ReestablecerVO vo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ").append(dbh.TABLE_NAME_RESTABLECER).append("(");
            sb.append(dbh.RESTABLECER_QUEST1+",").append(dbh.RESTABLECER_ANSW1+",").append(dbh.RESTABLECER_QUEST2+",");
            sb.append(dbh.RESTABLECER_ANSW2+",").append(dbh.RESTABLECER_TAMANO_FUENTE).append(")");
            sb.append(" VALUES ('");
            sb.append(vo.getPregunta1() + "','").append(vo.getRespuesta1()).append("','");
            sb.append(vo.getPregunta2() + "','").append(vo.getRespuesta2()).append("',");
            sb.append("'" + vo.getTamanoFuente() + "'");
            sb.append(")");

            Log.d(TAG_LOG, "[insert] SQL: " + sb.toString());

            db.execSQL(sb.toString());
            return true;
        } catch (Exception e){
            Log.e(TAG_LOG, "[insert] Error en RestablecerDAO: " + e.toString());
            return false;
        }
    }

    public boolean update(ReestablecerVO vo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ").append(dbh.TABLE_NAME_RESTABLECER).append(" SET ");
            sb.append(dbh.RESTABLECER_QUEST1).append("='").append(vo.getPregunta1()).append("',");
            sb.append(dbh.RESTABLECER_ANSW1).append("='").append(vo.getRespuesta1()).append("',");
            sb.append(dbh.RESTABLECER_QUEST2).append("='").append(vo.getPregunta2()).append("',");
            sb.append(dbh.RESTABLECER_ANSW2).append("='").append(vo.getRespuesta2()).append("',");
            sb.append(dbh.RESTABLECER_TAMANO_FUENTE).append("=").append(vo.getTamanoFuente()).append(" ");
            sb.append("WHERE ").append(dbh.RESTABLECER_ID).append("=").append(vo.getIdReestablecer());

            Log.d(TAG_LOG, "[update] SQL: " + sb.toString());

            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            Log.e(TAG_LOG, "[update] Error en FormulaDAO: " + e.toString());
            return false;
        }
    }

    public boolean delete(int idRestablecer) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ").append(dbh.TABLE_NAME_RESTABLECER);
            sb.append(" WHERE ").append(dbh.RESTABLECER_ID).append(" = ").append(idRestablecer);

            Log.d(TAG_LOG, "[delete] SQL: " + sb.toString());

            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            Log.e(TAG_LOG, "[delete] Error en RestablecerDAO: " + e.toString());
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
            sb.append("SELECT MAX(" + dbh.RESTABLECER_ID + ") FROM " + dbh.TABLE_NAME_RESTABLECER);

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
