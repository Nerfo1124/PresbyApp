package co.com.udistrital.presbyapp.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.com.udistrital.presbyapp.dao.database.DataBaseHelper;
import co.com.udistrital.presbyapp.vo.FormulaVO;

/**
 * <b>Descripcion: </b> Clase encargada de administrar las consultas en la BDD de la tabla FORMULA.
 *
 * Created by Fernando on 17/01/2016.
 * @author Fernando Hernandez
 */
public class FormulaDAO {

    private static String TAG_LOG = "[Sin_nombre]";

    public DataBaseHelper dbh;
    public SQLiteDatabase db;
    public Context contexto;

    public FormulaDAO(Context context){
        try {
            contexto = context;
            dbh = new DataBaseHelper(context);
            db = dbh.getWritableDatabase();
        } catch (Exception e){
            Log.e(TAG_LOG, "[FormulaDAO] Error en FormulaDAO: " + e.toString());
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de consultar todos los registros de la tabla FORMULA.
     * @return
     */
    public List<FormulaVO> list() {
        try {
            List<FormulaVO> listaFormula = new ArrayList<FormulaVO>();
            String columns[] = {dbh.FORMULA_ID, dbh.FORMULA_OJO_DER, dbh.FORMULA_OJO_IZQ, dbh.FORMULA_TAM_FUENTE};
            Cursor listFormulas = db.query(dbh.TABLE_NAME_FORMULA,columns,null,null,null,null,null);
            if(listFormulas.moveToFirst()){
                do{
                    FormulaVO vo = new FormulaVO();
                    vo.setIdFormula(listFormulas.getInt(0));
                    vo.setaVisualOD(listFormulas.getFloat(1));
                    vo.setaVisualOI(listFormulas.getFloat(2));
                    vo.setTamanioFuente(listFormulas.getString(3));
                    listaFormula.add(vo);
                } while(listFormulas.moveToNext());
            }
            return listaFormula;
        } catch (Exception e){
            Log.e(TAG_LOG, "[list] Error en FormulaDAO: " + e.toString());
            return null;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de realizar la consulta de un Registro en la tabla
     * FORMULA de la BDD recibiendo como parametro el id del Registro.
     * @param idFormula
     * @return
     */
    public FormulaVO consult(int idFormula) {
        FormulaVO objFormula = new FormulaVO();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ").append(dbh.TABLE_NAME_FORMULA);
            sb.append(" WHERE for_id = ").append(idFormula);

            Log.d(TAG_LOG, "[consult] SQL: " + sb.toString());

            Cursor fila = db.rawQuery(sb.toString(), null);
            fila.moveToFirst();
            if (fila != null){
                objFormula.setIdFormula(fila.getInt(0));
                objFormula.setaVisualOD(fila.getFloat(1));
                objFormula.setaVisualOI(fila.getFloat(2));
                objFormula.setTamanioFuente(fila.getString(3));
            }
            return objFormula;
        } catch (Exception e){
            Log.e(TAG_LOG, "[consult] Error en FormulaDAO - consult: " + e.toString());
            return null;
        }
    }

    /**
     * <b>Descripcion: </b> Metodo encargado de insertar datos en la tabla de FORMULA en la BDD.
     * @param vo
     * @return
     */
    public boolean insert(FormulaVO vo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ").append(dbh.TABLE_NAME_FORMULA).append("(");
            sb.append(dbh.FORMULA_OJO_DER + " , ").append(dbh.FORMULA_OJO_IZQ + " , ").append(dbh.FORMULA_TAM_FUENTE).append(")");
            sb.append(" VALUES (");
            sb.append(vo.getaVisualOD() + ",").append(vo.getaVisualOI() + ", '").append(vo.getTamanioFuente());
            sb.append("')");

            Log.d(TAG_LOG, "[insert] SQL: " + sb.toString());

            db.execSQL(sb.toString());
            return true;
        } catch (Exception e){
            Log.e(TAG_LOG, "[insert] Error en FormulaDAO: " + e.toString());
            return false;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de actualizar un Registro de la tabla FORMULA recibiendo
     * como parametro un objeto con los valores a actualizar.
     * @param vo
     * @return
     */
    public boolean update(FormulaVO vo) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ").append(dbh.TABLE_NAME_FORMULA).append(" SET ");
            sb.append(dbh.FORMULA_ID).append("=").append(vo.getIdFormula()).append(",");
            sb.append(dbh.FORMULA_OJO_DER).append("='").append(vo.getaVisualOD()).append("',");
            sb.append(dbh.FORMULA_OJO_IZQ).append("='").append(vo.getaVisualOI()).append("', ");
            sb.append(dbh.FORMULA_TAM_FUENTE).append("='").append(vo.getTamanioFuente()).append("' ");
            sb.append("WHERE ").append(dbh.FORMULA_ID).append("=").append(vo.getIdFormula());

            Log.e(TAG_LOG, "SQL: " + sb.toString());

            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            Log.e(TAG_LOG, "[update] Error en FormulaDAO: " + e.toString());
            return false;
        }
    }

    /**
     * <b>Descripcion: </b>Metodo encargado de realizar el borrado de un Registro en la tabla FORMULA
     * @param idFormula
     * @return
     */
    public boolean delete(int idFormula) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ").append(dbh.TABLE_NAME_FORMULA);
            sb.append(" WHERE ").append(dbh.FORMULA_ID).append(" = ").append(idFormula);

            System.out.println("SQL: " + sb.toString());

            db.execSQL(sb.toString());
            return true;
        } catch (Exception e) {
            Log.e(TAG_LOG, "[delete] Error en FormulaDAO: " + e.toString());
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
            sb.append("SELECT MAX(" + dbh.FORMULA_ID + ") FROM " + dbh.TABLE_NAME_FORMULA);

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
