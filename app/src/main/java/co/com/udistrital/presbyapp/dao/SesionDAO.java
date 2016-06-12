package co.com.udistrital.presbyapp.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.com.udistrital.presbyapp.dao.database.DataBaseHelper;
import co.com.udistrital.presbyapp.vo.SesionVO;

/**
 * <b>Descripcion: </b> Clase encargada de administrar las consultas en la BDD de la tabla SESION.
 *
 * Created by Fernando on 17/01/2016.
 * @author Fernando Hernandez
 */
public class SesionDAO {

    private static String TAG_LOG = "[PresbiciaApp]";

    public DataBaseHelper dbh;
    public SQLiteDatabase db;
    public Context contexto;

    public SesionDAO(Context context) {
        try {
            contexto = context;
            dbh = new DataBaseHelper(context);
            db = dbh.getWritableDatabase();
        } catch (Exception e){
            Toast.makeText(context, "[SesionDAO] Error en SesionDAO: " + e.toString(), Toast.LENGTH_SHORT).show();
            Log.e(TAG_LOG, "[SesionDAO] Error en SesionDAO: " + e.toString());
        }
    }

    /**
     * <b>Descripcion: </b>Metodo para consultar un nombre de usuario en la BDD
     */
    public int consultaNombreU( String userName) {
        int r = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT  * FROM ").append(dbh.TABLE_NAME_SESION).append(" WHERE ");
            sb.append(dbh.SESION_USER).append(" = '").append(userName).append("'");

            Log.d(TAG_LOG, "[consultaNombreU] SQL: " + sb.toString());

            Cursor fila = db.rawQuery( sb.toString() , null);
            if (fila.getCount() == 1) {
                fila.moveToFirst();
                if (fila != null) {
                    r = fila.getInt(0);
                    Log.d(TAG_LOG, "Codigo del usuario: " + r);
                }
            }
            Log.d(TAG_LOG, "R a retornar: " + r);
            return r;
        }catch (Exception e){
            Toast.makeText(contexto,"Error:"+ e.toString(),Toast.LENGTH_SHORT).show();
            Log.e(TAG_LOG, "[consultaNombreU] Error en SesionDAO:" + e.toString());
        }
        return r;
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de consultar todos los registros de la tabla SESION.
     * @return
     */
    public List<SesionVO> list() {
        try {
            List<SesionVO> listaSesion = new ArrayList<SesionVO>();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ").append(dbh.TABLE_NAME_SESION);

            Log.d(TAG_LOG, "[list] SQL: " + sb.toString());

            Cursor listSesiones = db.rawQuery(sb.toString(), null);
            if(listSesiones.moveToFirst()){
                do{
                    SesionVO vo = new SesionVO();
                    vo.setIdSesion(listSesiones.getInt(0));
                    vo.setUsuario(listSesiones.getString(1));
                    vo.setContrasena(listSesiones.getString(2));
                    listaSesion.add(vo);
                } while(listSesiones.moveToNext());
            }
            return listaSesion;
        } catch (Exception e){
            Toast.makeText(contexto, "[list] Error en SesionDAO: " + e.toString(), Toast.LENGTH_SHORT).show();
            Log.e(TAG_LOG, "[list] Error en SesionDAO: " + e.toString());
            return null;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de realizar la consulta de un Registro en la tabla
     * SESION de la BDD recibiendo como parametro el id del Registro.
     * @param idSesion
     * @return
     */
    public SesionVO consult(int idSesion) {
        SesionVO objSesion = new SesionVO();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ").append(dbh.TABLE_NAME_SESION);
            sb.append(" WHERE ").append(dbh.SESION_ID).append(" = ").append(idSesion);

            Log.d(TAG_LOG, "[consult] SQL: " + sb.toString());

            Cursor fila = db.rawQuery(sb.toString(), null);
            fila.moveToFirst();
            if (fila != null){
                objSesion.setIdSesion(fila.getInt(0));
                objSesion.setUsuario(fila.getString(1));
                objSesion.setContrasena(fila.getString(2));
            }
            return objSesion;
        } catch (Exception e){
            Toast.makeText(contexto, "[consult] Error en SesionDAO: " + e.toString(), Toast.LENGTH_SHORT ).show();
            Log.e(TAG_LOG, "[consult] Error en SesionDAO: " + e.toString());
            return null;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de realizar la insercion de datos en la tabla de SESION
     * @param vo
     * @return
     */
    public boolean insert(SesionVO vo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ").append(dbh.TABLE_NAME_SESION).append("(");
            sb.append(dbh.SESION_USER+" , ").append(dbh.SESION_PASS).append(")");
            sb.append(" VALUES (");
            sb.append("'" + vo.getUsuario() + "','").append(vo.getContrasena() + "'");
            sb.append(")");

            Log.d(TAG_LOG, "[insert] SQL: " + sb.toString());

            db.execSQL(sb.toString());
            return true;
        } catch (Exception e){
            Toast.makeText(contexto, "[insert] Error en SesionDAO: " + e.toString(), Toast.LENGTH_SHORT ).show();
            Log.e(TAG_LOG, "[insert] Error en SesionDAO: " + e.toString());
            return false;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de actualizar un Registro de la tabla SESION recibiendo
     * como parametro un objeto con los valores a actualizar.
     * @param vo
     * @return
     */
    public boolean update(SesionVO vo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ").append(dbh.TABLE_NAME_SESION).append(" SET ");
            sb.append(dbh.SESION_USER).append("='").append(vo.getUsuario()).append("',");
            sb.append(dbh.SESION_PASS).append("='").append(vo.getContrasena()).append("' ");
            sb.append("WHERE ").append(dbh.SESION_ID).append("=").append(vo.getIdSesion());

            Log.d(TAG_LOG, "[update] SQL: " + sb.toString());

            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            Toast.makeText(contexto, "[update] Error en SesionDAO " + e.toString(), Toast.LENGTH_SHORT).show();
            Log.e(TAG_LOG, "[update] Error en SesionDAO " + e.toString());
            return false;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de realizar el borrado de un Registro en la tabla SESION
     * @param idSesion
     * @return
     */
    public boolean delete(int idSesion) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ").append(dbh.TABLE_NAME_SESION);
            sb.append(" WHERE ").append(dbh.SESION_ID).append(" = ").append(idSesion);

            Log.d(TAG_LOG, "[delete] SQL: " + sb.toString());

            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            Toast.makeText(contexto, "[delete] Error en SesionDAO: " + e.toString(), Toast.LENGTH_SHORT).show();
            Log.e(TAG_LOG, "[delete] Error en SesionDAO: " + e.toString());
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
            sb.append("SELECT MAX(" + dbh.SESION_ID + ") FROM " + dbh.TABLE_NAME_SESION);

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
