package co.com.udistrital.presbyapp.dao.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Rolando Baron on 19/09/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static String TAG_LOG = "[PresbyApp]";

    // ========================================================
    //Tabla de Formula
    public static final String TABLE_NAME_FORMULA = "FORMULA";
    public static final String FORMULA_ID = "for_id";
    public static final String FORMULA_OJO_DER = "for_av_od";
    public static final String FORMULA_OJO_IZQ = "for_av_oi";
    public static final String FORMULA_TAM_FUENTE = "for_tam_fuente";

    public static final String CREATE_TABLE_FORMULA =
            "CREATE TABLE " + TABLE_NAME_FORMULA + " ( "
                    + FORMULA_ID + " integer primary key autoincrement not null, "
                    + FORMULA_OJO_DER + " integer not null, "
                    + FORMULA_OJO_IZQ + " integer not null, "
                    + FORMULA_TAM_FUENTE + " text not null "
                    + " ) ";

    // ========================================================
    // Tabla de Sesion

    public static final String TABLE_NAME_SESION = "SESION";
    public static final String SESION_ID = "ses_id";
    public static final String SESION_USER = "ses_user";
    public static final String SESION_PASS = "ses_pass";

    public static final String CREATE_TABLE_SESION =
            "CREATE TABLE " + TABLE_NAME_SESION + " ( "
                    + SESION_ID + " integer primary key autoincrement not null, "
                    + SESION_USER + " text not null, "
                    + SESION_PASS + " text not null "
            + " ) ";

    // ========================================================
    // Tabla de Sistema
    public static final String TABLE_NAME_SISTEMA = "SISTEMA";
    public static final String SISTEMA_ID = "sis_id";
    public static final String SISTEMA_TAM_FUENTE = "sis_tam_fuente";
    public static final String SISTEMA_FRECUENCIA = "sis_frecuencia";

    public static final String CREATE_TABLE_SISTEMA =
            "CREATE TABLE " + TABLE_NAME_SISTEMA + " ( "
                    + SISTEMA_ID + " integer primary key autoincrement not null, "
                    + SISTEMA_TAM_FUENTE + " integer not null, "
                    + SISTEMA_FRECUENCIA + " integer not null "
            + " ) ";

    // ========================================================
    // Tabla de Restablecer
    public static final String TABLE_NAME_RESTABLECER = "RESTABLECER";
    public static final String RESTABLECER_ID = "res_id";
    public static final String RESTABLECER_QUEST1 = "res_pregunta1";
    public static final String RESTABLECER_ANSW1 = "res_respuesta1";
    public static final String RESTABLECER_QUEST2 = "res_pregunta2";
    public static final String RESTABLECER_ANSW2 = "res_respuesta2";
    public static final String RESTABLECER_TAMANO_FUENTE = "res_tam_fuente_sistema";

    public static final String CREATE_TABLE_RESTABLECER =
            "CREATE TABLE " + TABLE_NAME_RESTABLECER + " ( "
            + RESTABLECER_ID + " integer primary key autoincrement not null, "
            + RESTABLECER_QUEST1 + " text not null, "
            + RESTABLECER_ANSW1 + " text not null, "
            + RESTABLECER_QUEST2 + " text, "
            + RESTABLECER_ANSW2 + " text, "
            + RESTABLECER_TAMANO_FUENTE+ " text ) ";

    // ========================================================
    // Tabla de Usuarios
    public static final String TABLE_NAME_USUARIO = "USUARIO";
    public static final String USUARIO_ID = "usu_id";
    public static final String USUARIO_NOMBRE = "usu_nombre";
    public static final String USUARIO_APELLIDO1 = "usu_apellido1";
    public static final String USUARIO_APELLIDO2 = "usu_apellido2";
    public static final String USUARIO_NACIMIENTO = "usu_fecha_nacimiento";
    public static final String USUARIO_SEXO = "usu_sexo";
    public static final String USUARIO_SESION = "Sesion_id";
    public static final String USUARIO_FORMULA = "Formula_id";
    public static final String USUARIO_SISTEMA = "Sistema_id";
    public static final String USUARIO_REESTABLECER = "Restablecer_id";

    public static final String CREATE_TABLE_USUARIO =
            "CREATE TABLE " + TABLE_NAME_USUARIO + " ( "
                    + USUARIO_ID + " integer primary key autoincrement not null, "
                    + USUARIO_NOMBRE + " text not null, "
                    + USUARIO_APELLIDO1 + " text not null, "
                    + USUARIO_APELLIDO2 + " text, "
                    + USUARIO_NACIMIENTO + " text not null, "
                    + USUARIO_SEXO + " text not null, "
                    + USUARIO_SESION + " integer, "
                    + USUARIO_FORMULA + " integer, "
                    + USUARIO_SISTEMA + " integer, "
                    + USUARIO_REESTABLECER + " integer, "
                    + "foreign key(" + USUARIO_SESION + ") references SESION(" + SESION_ID + "), "
                    + "foreign key(" + USUARIO_FORMULA + ") references FORMULA(" + FORMULA_ID + "), "
                    + "foreign key(" + USUARIO_SISTEMA + ") references SISTEMA(" + SISTEMA_ID + "), "
                    + "foreign key(" + USUARIO_REESTABLECER + ") references RESTABLECER(" + RESTABLECER_ID + ")"
                    + " ) ";

    // ========================================================
    // Tabla de Historico Uso de Dispositivo
    public static final String TABLE_NAME_HISTORICO = "HISTORICO_USO_DISPOSITIVO";
    public static final String HISTORICO_ID = "his_id";
    public static final String HISTORICO_ID_USUARIO = "his_id_user";
    public static final String HISTORICO_TIEMPO = "his_tiempo_uso";
    public static final String HISTORICO_FECHA = "his_fecha_registro";

    public static final String CREATE_TABLE_HISTORICO =
            "CREATE TABLE " + TABLE_NAME_HISTORICO + "("
                    + HISTORICO_ID + " integer primary key autoincrement not null, "
                    + HISTORICO_ID_USUARIO + " integer not null, "
                    + HISTORICO_TIEMPO + " text not null, "
                    + HISTORICO_FECHA + " text not null "
                    + " ) ";

    private static final String INSERT_HISTORICO_1 = "INSERT INTO HISTORICO_USO_DISPOSITIVO(his_id_user , his_tiempo_uso , his_fecha_registro) VALUES (1,'2:45:01','2016-06-4')";
    private static final String INSERT_HISTORICO_2 = "INSERT INTO HISTORICO_USO_DISPOSITIVO(his_id_user , his_tiempo_uso , his_fecha_registro) VALUES (1,'2:03:01','2016-06-5')";
    private static final String INSERT_HISTORICO_3 = "INSERT INTO HISTORICO_USO_DISPOSITIVO(his_id_user , his_tiempo_uso , his_fecha_registro) VALUES (1,'3:32:01','2016-06-6')";
    private static final String INSERT_HISTORICO_4 = "INSERT INTO HISTORICO_USO_DISPOSITIVO(his_id_user , his_tiempo_uso , his_fecha_registro) VALUES (1,'5:23:01','2016-06-7')";
    private static final String INSERT_HISTORICO_5 = "INSERT INTO HISTORICO_USO_DISPOSITIVO(his_id_user , his_tiempo_uso , his_fecha_registro) VALUES (1,'1:54:01','2016-06-8')";
    private static final String INSERT_HISTORICO_6 = "INSERT INTO HISTORICO_USO_DISPOSITIVO(his_id_user , his_tiempo_uso , his_fecha_registro) VALUES (1,'4:12:01','2016-06-9')";
    private static final String INSERT_HISTORICO_7 = "INSERT INTO HISTORICO_USO_DISPOSITIVO(his_id_user , his_tiempo_uso , his_fecha_registro) VALUES (1,'5:08:01','2016-06-10')";
    private static final String INSERT_HISTORICO_8 = "INSERT INTO HISTORICO_USO_DISPOSITIVO(his_id_user , his_tiempo_uso , his_fecha_registro) VALUES (1,'3:32:01','2016-06-11')";

    // ========================================================
    // Tabla de Ejercicios
    public static final String TABLE_NAME_EJERCICIO = "EJERCICIO";
    public static final String EJERCICIO_ID = "eje_id";
    public static final String EJERCICIO_NOMBRE = "eje_nombre";
    public static final String EJERCICIO_IMAGEN = "eje_imagen";
    public static final String EJERCICIO_DESCRIPCION = "eje_descripcion";

    public static final String CREATE_TABLE_EJERCICIO =
            "CREATE TABLE " + TABLE_NAME_EJERCICIO + "("
                    + EJERCICIO_ID + " integer primary key autoincrement not null, "
                    + EJERCICIO_NOMBRE + " text not null, "
                    + EJERCICIO_IMAGEN + " text not null, "
                    + EJERCICIO_DESCRIPCION + " text not null "
                    + " ) ";

    public static final String INSERT_EXCERSICE_1 =
            "INSERT INTO EJERCICIO(" + EJERCICIO_NOMBRE + " , " + EJERCICIO_IMAGEN + " , " + EJERCICIO_DESCRIPCION + ") "
                    + "VALUES ('Parpadeo','parpadeo.png','Ejercicio que consiste en parpadear la mayor cantidad de veces en un tiempo definido en la aplicacion.')";
    public static final String INSERT_EXCERSICE_2 =
            "INSERT INTO EJERCICIO(" + EJERCICIO_NOMBRE + " , " + EJERCICIO_IMAGEN + " , " + EJERCICIO_DESCRIPCION + ") "
                    + "VALUES ('Acercamiento','acercamiento.png','Ejercicio que consiste en acercar y alejar un lapiz de acuerdo a la descripcion del ejercicio en la aplicacion.')";
    public static final String INSERT_EXCERSICE_3 =
            "INSERT INTO EJERCICIO(" + EJERCICIO_NOMBRE + " , " + EJERCICIO_IMAGEN + " , " + EJERCICIO_DESCRIPCION + ") "
                    + "VALUES ('Circulos','circulos.png','Ejercicio que consiste en dar circulos con los ojos con el apoyo de un objeto visual en la pantalla del celular.')";
    public static final String INSERT_EXCERSICE_4 =
            "INSERT INTO EJERCICIO(" + EJERCICIO_NOMBRE + " , " + EJERCICIO_IMAGEN + " , " + EJERCICIO_DESCRIPCION + ") "
                    + "VALUES ('Masaje','masajes.png','Ejercicio que consiste en dar lijeros masajes circulares a los ojos con apoyo de las palmas de las manos.')";
    public static final String INSERT_EXCERSICE_5 =
            "INSERT INTO EJERCICIO(" + EJERCICIO_NOMBRE + " , " + EJERCICIO_IMAGEN + " , " + EJERCICIO_DESCRIPCION + ") "
                    + "VALUES ('LejosCerca','cerca-lejos.png','Ejercicio que consiste en visualizar una serie de objetos cercanos y lejanos que se encuentren alrededor.')";
    public static final String INSERT_EXCERSICE_6 =
            "INSERT INTO EJERCICIO(" + EJERCICIO_NOMBRE + " , " + EJERCICIO_IMAGEN + " , " + EJERCICIO_DESCRIPCION + ") "
                    + "VALUES ('Palmeo','palmeo.png','Ejercicio que consiste en poner las palmas de las manos sobre los ojos sin hacer presion sobre ellos.')";
    /*public static final String INSERT_EXCERSICE_7 =
            "INSERT INTO EJERCICIO(" + EJERCICIO_NOMBRE + " , " + EJERCICIO_IMAGEN + " , " + EJERCICIO_DESCRIPCION + ") "
                    + "VALUES ('En Construccion!!!','cons.png','Breve descripcion del ejercicio.')";
    public static final String INSERT_EXCERSICE_8 =
            "INSERT INTO EJERCICIO(" + EJERCICIO_NOMBRE + " , " + EJERCICIO_IMAGEN + " , " + EJERCICIO_DESCRIPCION + ") "
                    + "VALUES ('En Construccion!!!','cons.png','Breve descripcion del ejercicio.')";
    public static final String INSERT_EXCERSICE_9 =
            "INSERT INTO EJERCICIO(" + EJERCICIO_NOMBRE + " , " + EJERCICIO_IMAGEN + " , " + EJERCICIO_DESCRIPCION + ") "
                    + "VALUES ('En Construccion!!!','cons.png','Breve descripcion del ejercicio.')";
    public static final String INSERT_EXCERSICE_10 =
           "INSERT INTO EJERCICIO(" + EJERCICIO_NOMBRE + " , " + EJERCICIO_IMAGEN + " , " + EJERCICIO_DESCRIPCION + ") "
                   + "VALUES ('En Construccion!!!','cons.png','Breve descripcion del ejercicio.')";*/

    // ========================================================
    // Tabla de Historico ejercicios realizados.
    public static final String TABLE_NAME_HISTORICO_EJER = "HISTORICO_EJERCICIO";
    public static final String HISTORICO_EJER_ID = "his_eje_id";
    public static final String HISTORICO_EJER_USU = "Usuario_id";
    public static final String HISTORICO_EJER_EJERCICIO = "Ejercicio_id";
    public static final String HISTORICO_EJER_FECHA = "his_eje_fecha";

    public static final String CREATE_TABLE_HISTORICO_EX =
            "CREATE TABLE " + TABLE_NAME_HISTORICO_EJER + "("
                    + HISTORICO_EJER_ID + " integer primary key autoincrement not null, "
                    + HISTORICO_EJER_USU + " integer, "
                    + HISTORICO_EJER_EJERCICIO + " integer, "
                    + HISTORICO_EJER_FECHA + " text not null, "
                    + "foreign key(" + HISTORICO_EJER_USU + ") references " + TABLE_NAME_USUARIO + "(" + USUARIO_ID + "),"
                    + "foreign key(" + HISTORICO_EJER_EJERCICIO + ") references " + TABLE_NAME_EJERCICIO + "(" + EJERCICIO_ID + ")"
                    + " ) ";

    // ========================================================

    // ========================================================
    // Tabla de Historico ejercicios realizados.
    public static final String TABLE_NAME_HISTORICO_LETRA_FREC = "HISTORICO_LETRA_FRECUENCIA";
    public static final String HISTORICO_LET_ID = "his_let_id";
    public static final String HISTORICO_LET_USU = "Usuario_id";
    public static final String HISTORICO_LET_FRECUENCIA = "his_let_fre";
    public static final String HISTORICO_LET_LETRA = "his_let_letra";
    public static final String HISTORICO_LET_FECHA = "his_let_fecha";

    public static final String CREATE_TABLE_HISTORICO_LETRA_FREC =
            "CREATE TABLE " + TABLE_NAME_HISTORICO_LETRA_FREC + "("
                    + HISTORICO_LET_ID + " integer primary key autoincrement not null, "
                    + HISTORICO_LET_USU + " integer, "
                    + HISTORICO_LET_FRECUENCIA + " integer, "
                    + HISTORICO_LET_LETRA + " text not null, "
                    + HISTORICO_LET_FECHA + " text not null, "
                    + "foreign key(" + HISTORICO_LET_USU + ") references " + TABLE_NAME_USUARIO + "(" + USUARIO_ID + ")"
                    + " ) ";

    // ========================================================
    private static final String DB_NAME = "presbicia.sqlite";
    private static final int DB_SCHEMA_VERSION = 1;
    Context contexto;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_SCHEMA_VERSION);
        Log.d(TAG_LOG, "[DataBaseHelper] DataBase Name: " + DB_NAME + " Version: " + DB_SCHEMA_VERSION);
        contexto = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FORMULA);
        Log.d(TAG_LOG, "[DataBaseHelper] SQL: " + CREATE_TABLE_FORMULA);
        db.execSQL(CREATE_TABLE_SISTEMA);
        Log.d(TAG_LOG, "[DataBaseHelper] SQL: " + CREATE_TABLE_SISTEMA);
        db.execSQL(CREATE_TABLE_SESION);
        Log.d(TAG_LOG, "[DataBaseHelper] SQL: " + CREATE_TABLE_SESION);
        db.execSQL(CREATE_TABLE_RESTABLECER);
        Log.d(TAG_LOG, "[DataBaseHelper] SQL: " + CREATE_TABLE_RESTABLECER);
        db.execSQL(CREATE_TABLE_USUARIO);
        Log.d(TAG_LOG, "[DataBaseHelper] SQL: " + CREATE_TABLE_USUARIO);
        db.execSQL(CREATE_TABLE_HISTORICO);
        Log.d(TAG_LOG, "[DataBaseHelper] SQL: " + CREATE_TABLE_HISTORICO);
        db.execSQL(CREATE_TABLE_EJERCICIO);
        Log.d(TAG_LOG, "[DataBaseHelper] SQL: " + CREATE_TABLE_EJERCICIO);
        db.execSQL(INSERT_EXCERSICE_1);
        db.execSQL(INSERT_EXCERSICE_2);
        db.execSQL(INSERT_EXCERSICE_3);
        db.execSQL(INSERT_EXCERSICE_4);
        db.execSQL(INSERT_EXCERSICE_5);
        db.execSQL(INSERT_EXCERSICE_6);
        db.execSQL(INSERT_HISTORICO_1);
        db.execSQL(INSERT_HISTORICO_2);
        db.execSQL(INSERT_HISTORICO_3);
        db.execSQL(INSERT_HISTORICO_4);
        db.execSQL(INSERT_HISTORICO_5);
        db.execSQL(INSERT_HISTORICO_6);
        db.execSQL(INSERT_HISTORICO_7);
        db.execSQL(INSERT_HISTORICO_8);
        db.execSQL(CREATE_TABLE_HISTORICO_EX);
        Log.d(TAG_LOG, "[DataBaseHelper] SQL: " + CREATE_TABLE_HISTORICO_EX);
        db.execSQL(CREATE_TABLE_HISTORICO_LETRA_FREC);
        Log.d(TAG_LOG, "[DataBaseHelper] SQL: " + CREATE_TABLE_HISTORICO_LETRA_FREC);

        Log.i(TAG_LOG, "Base de Datos creada satisfactoriamente.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Pendiente por validar.
    }
}