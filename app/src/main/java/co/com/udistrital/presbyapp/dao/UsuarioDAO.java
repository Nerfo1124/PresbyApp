package co.com.udistrital.presbyapp.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.com.udistrital.presbyapp.dao.database.DataBaseHelper;
import co.com.udistrital.presbyapp.vo.UsuarioVO;

/**
 * <b>Descripcion: </b> Clase encargada de administrar las consultas en la BDD de la tabla USUARIO.
 *
 * Created by Fernando on 17/01/2016.
 * @author Fernando Hernandez
 */
public class UsuarioDAO {

    private static String TAG_LOG = "[PresbiciaApp]";

    public Context contexto;
    private DataBaseHelper dbh;
    private SQLiteDatabase db;

    public UsuarioDAO(Context context){
        try {
            contexto = context;
            dbh = new DataBaseHelper(context);
            db = dbh.getWritableDatabase();
        } catch (Exception e){
            Log.e(TAG_LOG, "[UsuarioDAO] Error en UsuarioDAO: " + e.toString());
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de consultar todos los registros de la tabla USUARIO.
     * @return
     */
    public List<UsuarioVO> list() {
        try {
            List<UsuarioVO> listaUsuario = new ArrayList<UsuarioVO>();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ").append(dbh.TABLE_NAME_USUARIO);

            Log.d("Sin_nombre", "[list] SQL: " + sb.toString());

            Cursor listaUsuarios = db.rawQuery(sb.toString(), null);
            if(listaUsuarios.moveToFirst()){
                do{
                    UsuarioVO vo = new UsuarioVO();
                    vo.setIdUsuario(listaUsuarios.getInt(0));
                    vo.setNombreUsuario(listaUsuarios.getString(1));
                    vo.setApellido1Usuario(listaUsuarios.getString(2));
                    vo.setApellido2Usuario(listaUsuarios.getString(3));
                    vo.setFechaNacimiento(listaUsuarios.getString(4));
                    vo.setSexo(listaUsuarios.getString(5));

                    SesionDAO sesDao = new SesionDAO(contexto);
                    FormulaDAO forDao = new FormulaDAO(contexto);
                    SistemaDAO sisDao = new SistemaDAO(contexto);
                    RestablecerDAO resDao = new RestablecerDAO(contexto);

                    vo.setSesionUsuario(sesDao.consult(listaUsuarios.getInt(6)));
                    vo.setFormulaUsuario(forDao.consult(listaUsuarios.getInt(7)));
                    vo.setConfigUsuario(sisDao.consult(listaUsuarios.getInt(8)));
                    vo.setRestablecerUsuario(resDao.consult(listaUsuarios.getInt(9)));
                    listaUsuario.add(vo);
                } while(listaUsuarios.moveToNext());
            }
            return listaUsuario;
        } catch (Exception e){
            Log.e(TAG_LOG, "[list] Error en UsuarioDAO: " + e.toString());
            return null;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de realizar la consulta de un Registro en la tabla
     * USUARIO de la BDD recibiendo como parametro el id del Registro.
     * @param idUsuario
     * @return
     */
    public UsuarioVO consult(int idUsuario) {
        UsuarioVO objUsuario = new UsuarioVO();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ").append(dbh.TABLE_NAME_USUARIO);
            sb.append(" WHERE ").append(dbh.USUARIO_ID).append(" = ").append(idUsuario);

            Log.d(TAG_LOG, "[consult] SQL: " + sb.toString());

            Cursor fila = db.rawQuery(sb.toString(), null);
            fila.moveToFirst();
            if (fila != null) {
                objUsuario.setIdUsuario(fila.getInt(0));
                objUsuario.setNombreUsuario(fila.getString(1));
                objUsuario.setApellido1Usuario(fila.getString(2));
                objUsuario.setApellido2Usuario(fila.getString(3));
                objUsuario.setFechaNacimiento(fila.getString(4));
                objUsuario.setSexo(fila.getString(5));

                SesionDAO sesDao = new SesionDAO(contexto);
                FormulaDAO forDao = new FormulaDAO(contexto);
                SistemaDAO sisDao = new SistemaDAO(contexto);
                RestablecerDAO resDao = new RestablecerDAO(contexto);

                objUsuario.setSesionUsuario(sesDao.consult(fila.getInt(6)));
                objUsuario.setFormulaUsuario(forDao.consult(fila.getInt(7)));
                objUsuario.setConfigUsuario(sisDao.consult(fila.getInt(8)));
                objUsuario.setRestablecerUsuario(resDao.consult(fila.getInt(9)));
            }
            return objUsuario;
        } catch (Exception e) {
            Log.e(TAG_LOG, "[consult] Error en UsuarioDAO: " + e.toString(), e);
            return null;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de realizar la insercion de datos en la tabla de USUARIO
     * @param vo
     * @return
     */
    public boolean insert(UsuarioVO vo) {
        try {
            SesionDAO sesDao = new SesionDAO(contexto);
            FormulaDAO forDao = new FormulaDAO(contexto);
            SistemaDAO sisDao = new SistemaDAO(contexto);
            RestablecerDAO resDao = new RestablecerDAO(contexto);

            // Inserciones preConsulta
            sesDao.insert(vo.getSesionUsuario());
            forDao.insert(vo.getFormulaUsuario());
            sisDao.insert(vo.getConfigUsuario());
            resDao.insert(vo.getRestablecerUsuario());

            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ").append(dbh.TABLE_NAME_USUARIO).append("(");
            sb.append(dbh.USUARIO_NOMBRE + ",").append(dbh.USUARIO_APELLIDO1 + ",").append(dbh.USUARIO_APELLIDO2 + ",");
            sb.append(dbh.USUARIO_NACIMIENTO + ",").append(dbh.USUARIO_SEXO + ",").append(dbh.USUARIO_SESION + ",");
            sb.append(dbh.USUARIO_FORMULA + ",").append(dbh.USUARIO_SISTEMA + ",").append(dbh.USUARIO_REESTABLECER).append(")");
            sb.append(" VALUES (");
            sb.append("'" + vo.getNombreUsuario() + "','").append(vo.getApellido1Usuario() + "','").append(vo.getApellido2Usuario() + "',");
            sb.append("'" + vo.getFechaNacimiento() + "','").append(vo.getSexo() + "',").append(sesDao.consultLastID() + ",");
            sb.append(forDao.consultLastID() + ",").append(sisDao.consultLastID() + ",").append(resDao.consultLastID());
            sb.append(")");

            System.out.println("[insert] SQL: " + sb.toString());
            Log.d(TAG_LOG, "[insert] SQL: " + sb.toString());

            db.execSQL(sb.toString());
            return true;
        } catch (Exception e){
            System.out.println("[insert] Error en UsuarioDAO: " + e.toString());
            return false;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de actualizar un Registro de la tabla USUARIO recibiendo
     * como parametro un objeto con los valores a actualizar.
     * @param vo
     * @return
     */
    public boolean update(UsuarioVO vo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ").append(dbh.TABLE_NAME_USUARIO).append(" SET ");
            sb.append(dbh.USUARIO_NOMBRE).append("='").append(vo.getNombreUsuario()).append("',");
            sb.append(dbh.USUARIO_APELLIDO1).append("='").append(vo.getApellido1Usuario()).append("',");
            sb.append(dbh.USUARIO_APELLIDO2).append("='").append(vo.getApellido2Usuario()).append("',");
            sb.append(dbh.USUARIO_NACIMIENTO).append("='").append(vo.getFechaNacimiento()).append("',");
            sb.append(dbh.USUARIO_SEXO).append("='").append(vo.getSexo()).append("' ");
            sb.append("WHERE ").append(dbh.USUARIO_ID).append("=").append(vo.getIdUsuario());

            if(vo.getSesionUsuario() != null){
                SesionDAO sesDao = new SesionDAO(contexto);
                sesDao.update(vo.getSesionUsuario());
            }
            if(vo.getFormulaUsuario() != null){
                FormulaDAO forDao = new FormulaDAO(contexto);
                forDao.update(vo.getFormulaUsuario());
            }
            if(vo.getConfigUsuario() != null){
                SistemaDAO sisDao = new SistemaDAO(contexto);
                sisDao.update(vo.getConfigUsuario());
            }
            if(vo.getRestablecerUsuario() != null){
                RestablecerDAO resDao = new RestablecerDAO(contexto);
                resDao.update(vo.getRestablecerUsuario());
            }

            Log.d(TAG_LOG, "[update] SQL: " + sb.toString());
            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            System.out.println("[update] Error en UsuarioDAO " + e.toString());
            return false;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de realizar el borrado de un Registro en la tabla USUARIO
     * @param idUsuario
     * @return
     */
    public boolean delete(int idUsuario) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ").append(dbh.TABLE_NAME_USUARIO);
            sb.append(" WHERE ").append(dbh.USUARIO_ID).append(" = ").append(idUsuario);

            Log.d(TAG_LOG, "[delete] SQL: " + sb.toString());

            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            Log.e(TAG_LOG, "[delete] Error en UsuarioDAO: " + e.toString());
            return false;
        }
    }
}
