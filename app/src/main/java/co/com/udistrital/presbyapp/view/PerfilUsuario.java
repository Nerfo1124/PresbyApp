package co.com.udistrital.presbyapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import co.com.udistrital.presbyapp.R;
import co.com.udistrital.presbyapp.dao.UsuarioDAO;
import co.com.udistrital.presbyapp.vo.UsuarioVO;

public class PerfilUsuario extends AppCompatActivity {

    private static String TAG_LOG = "[Sin_nombre]";

    private EditText txtNameUsuario, txtFechaUsuario, txtSexo, txtIdSesion, txtFuentePerfil;
    private UsuarioDAO userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG_LOG, "Ingresando a la vista principal de Perfil.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);
        getSupportActionBar().setTitle("Tus Datos ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//funcion hacia atras

        // Recibiendo parametros de la Actividad InicioSesion
        Bundle bundle = getIntent().getExtras();
        int idUsuarioSesion = Integer.parseInt(bundle.getString("idUsuario"));
        Log.d(TAG_LOG, "Parametro recibido: " + idUsuarioSesion);

        userDao = new UsuarioDAO(this);

        txtNameUsuario = (EditText) findViewById(R.id.txtNombreUsuario);
        txtFechaUsuario = (EditText) findViewById(R.id.txtFechaNacimiento);
        txtSexo = (EditText) findViewById(R.id.txtSexoUsuario);
        txtIdSesion = (EditText) findViewById(R.id.txtIdSesion);
        txtFuentePerfil = (EditText) findViewById(R.id.txtFuentePerfil);

        // TODO Mostrando Valores en la Vista de Perfil
        try {
            UsuarioVO user;
            user = userDao.consult(idUsuarioSesion);

            txtNameUsuario.setText(user.getNombreUsuario() + " " + user.getApellido1Usuario() + " " + user.getApellido2Usuario());
            txtFechaUsuario.setText(user.getFechaNacimiento());
            txtSexo.setText(user.getSexo());
            Log.d(TAG_LOG, "[onCreate] Codigo usuario: " + user.getSesionUsuario().getIdSesion());
            txtIdSesion.setText("" + user.getSesionUsuario().getIdSesion());
            txtFuentePerfil.setText("" + user.getConfigUsuario().getTamanoFuente());
        } catch (Exception ex) {
            Log.e(TAG_LOG, "[onCreate] Error en la consulta del usuario: ", ex);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}