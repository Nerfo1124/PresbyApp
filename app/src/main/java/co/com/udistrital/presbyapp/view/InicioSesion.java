package co.com.udistrital.presbyapp.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import org.w3c.dom.Text;

import co.com.udistrital.presbyapp.R;
import co.com.udistrital.presbyapp.dao.RestablecerDAO;
import co.com.udistrital.presbyapp.dao.SesionDAO;
import co.com.udistrital.presbyapp.dao.UsuarioDAO;
import co.com.udistrital.presbyapp.security.Encrypter;
import co.com.udistrital.presbyapp.util.pantalla_on_off;
import co.com.udistrital.presbyapp.vo.ReestablecerVO;
import co.com.udistrital.presbyapp.vo.SesionVO;
import co.com.udistrital.presbyapp.vo.UsuarioVO;

/**
 * Created by Fernando on 06/03/2016.
 */
public class InicioSesion extends AppCompatActivity {

    private static String TAG_LOG = "[PresbiciaApp]";

    /**
     * Variables para el manejo de los componentes de aplicacion.
     */
    private EditText txtUsuario, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG_LOG, "Iniciando vista principal de la aplicacion.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        getSupportActionBar().setTitle("Inicio Sesion");
        //getSupportActionBar().setIcon(R.drawable.icono_home);
        txtUsuario = (EditText) findViewById(R.id.txtUserSesion);
        txtPassword = (EditText) findViewById(R.id.txtPassSesion);
        try{
            String v[] = getDatosUsuario();
            if(Integer.parseInt(v[2])==1){
                Intent intento = new Intent(this, Principal.class);
                intento.putExtra("idUsuario", "" +v[0]);
                startActivity(intento);
                finish();
            }
        }catch (Exception e){
            Log.e(TAG_LOG, "Error " + e.toString(), e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("[Inicio Sesion]", "Generando Opciones de Menu");
        getMenuInflater().inflate(R.menu.menu_inicio_sesion, menu);
        return true;
    }

    /**
     * <b>Descripcion: </b> Metodo para iniciar la Actividad de Registro.
     *
     * @param v
     */
    public void registrarCuenta(View v) {
        try{
            Log.e(TAG_LOG, "Entro");
            Intent intento = new Intent(getApplicationContext(), Registro.class);
            startActivity(intento);
        }catch (Exception e){
            Log.e(TAG_LOG, "Error " + e.toString(), e);
        }

    }

    /**
     * <b>Descripcion: </b> Metodo para generar un dialogo llamando un archivo  xml, el  cual permite la
     * autentificacion del usuario y posteriormente realiza el cambio de contraseña
     *
     * @param v
     */
    public void olvidoCuenta(View v) {
        UsuarioDAO userDao = new UsuarioDAO(this);
        userDao.consult(1);
        final Dialog personal = new Dialog(this);
        personal.setContentView(R.layout.recuperar_cuenta);
        personal.setTitle("\tRestablecer Contraseña");
        final EditText txtUsuario = (EditText) personal.findViewById(R.id.txtUserSearch);
        final EditText txtRespuestaUno = (EditText) personal.findViewById(R.id.txtAnswerUno);
        final EditText txtRespuestaDos = (EditText) personal.findViewById(R.id.txtAnswerDos);
        final TextView txtPreguntaUno = (TextView) personal.findViewById(R.id.lblQuestUno);
        final TextView txtPreguntaDos = (TextView) personal.findViewById(R.id.lblQuestDos);
        final TextView lblRestablecer = (TextView) personal.findViewById(R.id.lblPreguntas);
        final EditText txtPassUno = (EditText) personal.findViewById(R.id.txtPassResUno);
        final EditText txtPassDos = (EditText) personal.findViewById(R.id.txtPassResDos);
        final Button btnCambiarPass = (Button) personal.findViewById(R.id.btnRestablecerPassOlv);
        final Button btnBuscar = (Button) personal.findViewById(R.id.btnBuscar);
        final Button btnRestablecer = (Button) personal.findViewById(R.id.btnRestablecerPass);
        btnRestablecer.setEnabled(false);
        final SesionDAO sesion = new SesionDAO(this);
        final RestablecerDAO dao = new RestablecerDAO(this);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int codSesion;
                if (txtUsuario.getText().toString().trim().equals("")) {
                    txtUsuario.setHintTextColor(Color.parseColor("#51FF1218"));
                    txtUsuario.setHint("Debe digitar un usuario");
                } else {
                    codSesion = sesion.consultaNombreU(txtUsuario.getText().toString());
                    if (codSesion == 0) {
                        txtUsuario.setText("");
                        txtUsuario.setHint("No se encontro ese usuario");
                    } else {
                        ReestablecerVO restablecer = dao.consult(codSesion);
                        txtPreguntaUno.setText("" + restablecer.getPregunta1());
                        txtPreguntaDos.setText("" + restablecer.getPregunta2());
                        btnRestablecer.setEnabled(true);
                    }
                }
            }
        });

        btnRestablecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG_LOG, "Accionando boton Reestablecer");
                if (txtRespuestaUno.getText().toString().trim().equals("") &&
                        txtRespuestaDos.getText().toString().trim().equals("")) {
                    Log.d(TAG_LOG, "Valores vacios");
                    txtRespuestaUno.setHint("Debe digitar una respuesta");
                    txtRespuestaUno.setHintTextColor(Color.parseColor("#51FF1218"));
                    txtRespuestaDos.setHint("Debe digitar una respuesta");
                    txtRespuestaDos.setHintTextColor(Color.parseColor("#51FF1218"));
                } else {
                    int codSesion = sesion.consultaNombreU(txtUsuario.getText().toString());
                    ReestablecerVO restablecer = dao.consult(codSesion);
                    if (txtRespuestaUno.getText().toString().trim().equals(restablecer.getRespuesta1())) {
                        if (txtRespuestaDos.getText().toString().trim().equals(restablecer.getRespuesta2())) {
                            txtPreguntaUno.setVisibility(View.INVISIBLE);
                            txtRespuestaUno.setVisibility(View.INVISIBLE);
                            txtPreguntaDos.setVisibility(View.INVISIBLE);
                            txtRespuestaDos.setVisibility(View.INVISIBLE);
                            btnRestablecer.setVisibility(View.INVISIBLE);
                            lblRestablecer.setText("Ingrese los valores de su nueva contraseña");
                            txtPassUno.setVisibility(View.VISIBLE);
                            txtPassDos.setVisibility(View.VISIBLE);
                            btnCambiarPass.setVisibility(View.VISIBLE);
                        } else {
                            txtRespuestaDos.setText("");
                            txtRespuestaDos.setHint("Ingrese una respuesta valida");
                            txtRespuestaDos.setHintTextColor(Color.parseColor("#51FF1218"));
                        }
                    } else {
                        txtRespuestaUno.setText("");
                        txtRespuestaUno.setHint("Ingrese una respuesta valida");
                        txtRespuestaUno.setHintTextColor(Color.parseColor("#51FF1218"));
                    }
                }
            }
        });

        btnCambiarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtPassUno.getText().toString().trim().equals("") &&
                        txtPassDos.getText().toString().trim().equals("")) {
                    Log.d(TAG_LOG, "Valores vacios");
                    txtPassUno.setHint("Debe digitar una contraseña");
                    txtPassUno.setHintTextColor(Color.parseColor("#51FF1218"));
                    txtPassDos.setHint("Debe digitar una contraseña");
                    txtPassDos.setHintTextColor(Color.parseColor("#51FF1218"));
                } else {
                    int codSesion = sesion.consultaNombreU(txtUsuario.getText().toString());
                    SesionVO sesionNew = sesion.consult(codSesion);
                    if ("".equals(txtPassUno.getText().toString())) {
                        txtPassUno.setText("");
                        txtPassUno.setHint("Debe ingresar una contraseña");
                        txtPassUno.setHintTextColor(Color.parseColor("#51FF1218"));
                    } else {
                        if ("".equals(txtPassDos.getText().toString())) {
                            txtPassDos.setText("");
                            txtPassDos.setHint("Debe ingresar la contraseña");
                            txtPassDos.setHintTextColor(Color.parseColor("#51FF1218"));
                        } else {
                            if (txtPassUno.getText().toString().equals(txtPassDos.getText().toString())) {
                                sesionNew.setContrasena("" + Encrypter.getStringMessageDigest(txtPassDos.getText().toString(), Encrypter.SHA256));
                                sesion.update(sesionNew);
                                personal.cancel();
                            } else {
                                txtPassUno.setText("");
                                txtPassUno.setHint("Las contraseñas no coinciden, repitala");
                                txtPassUno.setHintTextColor(Color.parseColor("#51FF1218"));
                                txtPassDos.setText("");
                                txtPassDos.setHint("Las contraseñas no coinciden, repitala");
                                txtPassDos.setHintTextColor(Color.parseColor("#51FF1218"));
                            }
                        }
                    }
                }
            }
        });

        personal.show();

    }

    /**
     * metodo encargado de buscar el usuario ingresado en el dialogo y si no lo esta devuelbe mensaje
     * @param user es un  string el cual lleva el  nombre del usuario
     * @param opc es un int el  cual puede ser un 1 o un 2  el cual permite decidir que respuesta dar
     *            en caso de que no se encuentre el usuario
     * @return
     */
    public int verificarUsuario(String user, int opc, SesionDAO sesion) {
        int r = sesion.consultaNombreU(user);
        try{
            Log.d(TAG_LOG, "Valor retornado: " + r);
            if (r == 0 && opc == 1) {
                txtUsuario.setText("");
                txtUsuario.setHint("No hay un usuario registrado con este nombre");
                txtUsuario.setHintTextColor(Color.parseColor("#51FF1218"));
                txtPassword.setText("");
                return 0;
            }
            if (r == 0 && opc == 2) {
                return 0;
            }
        }catch (Exception e){
            Log.e(TAG_LOG, "Error " + e.toString(), e);
        }
        return r;
    }

    /**
     * <b>Descripcion: </b>metodo encargado de iniciar la actividad correspondiente al iniciar sesion o en
     * caso contrario devuelve mensaje de error en los datos ingresados
     * @param v
     */
    public void iniciarSesion(View v) {
        try{
            SesionDAO sesion = new SesionDAO(this);
            SesionVO sesionU = new SesionVO();
            int e = espaciosBlancos();
            int codSesion = 0;
            if (e == 1) {
                codSesion = verificarUsuario(txtUsuario.getText().toString(), 1, sesion);
                Log.d(TAG_LOG, "[iniciarSesion] Verificacion de Usuario, codigo = " + codSesion);
                if (codSesion != 0) {
                    sesionU = sesion.consult(codSesion);
                }
            }
            if (e == 1 && codSesion != 0) {
                String clave = Encrypter.getStringMessageDigest(txtPassword.getText().toString(), Encrypter.SHA256);
                if (sesionU.getContrasena().equals(clave)) {
                    Intent intento = new Intent(this, Principal.class);
                    intento.putExtra("idUsuario", "" + sesionU.getIdSesion());
                    startActivity(intento);
                    finish();
                }else{
                    Dialogo("Mensaje","La Contraseña ingresada es incorrecta",3);
                    txtPassword.setText("");
                    txtPassword.setHint("La Contraseña ingresada es incorrecta");
                    txtPassword.setHintTextColor(Color.parseColor("#51FF1218"));
                }
            }
        }catch (Exception e){
            Log.e(TAG_LOG, "Error " + e.toString(), e);
        }
    }

    /**
     * <b>Descripcion: </b> Metodo encargado de verificar que los elementos editables del xml no se encuentren
     * en blanco
     *
     * @return r = 1 si no hay espacion en blanco y
     *         r = 0 si hay espacios en blanco
     */
    public int espaciosBlancos() {
        int r = 1;
        if ("".equals(txtUsuario.getText().toString())) {
            r = 0;
            txtUsuario.setHint("Debe ingresar su nombre de usuario");
            txtUsuario.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        if ("".equals(txtPassword.getText().toString())) {
            r = 0;
            txtPassword.setHint("Debe ingresar su contraseña");
            txtPassword.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        return r;
    }

    /**
     * <b>Descripcion: </b>metodo que genera un dialogo el cual puede mostrar cualquier tipo de mensaje
     *
     * @param tit variable string que lleva en nombre del titulo del  dialogo
     * @param men variable string que lleva el mensaje que se quiere mostrar
     * @param opc variable entera que lleva un numero el  cual permite realizar una operacion
     *            especifica si es necesaria
     */
    public void Dialogo(String tit, final String men, final int opc) {
        try {
            new AlertDialog.Builder( this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(tit)
                    .setMessage(men)
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        } catch (Exception e) {
            Log.e(TAG_LOG, "Error Dialogo " + e.toString(), e);
        }
    }

    public String[] getDatosUsuario() {
        try {
            SharedPreferences prefe = getSharedPreferences("Usuario", Context.MODE_PRIVATE);
            String[] v = prefe.getString("Datos", "0:0:0").split(":");
            return v;
        } catch (Exception e) {
            Log.e(TAG_LOG, "Error " + e.toString(), e);
        }
        return null;
    }

}
