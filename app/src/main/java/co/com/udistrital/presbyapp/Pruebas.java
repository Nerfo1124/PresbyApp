package co.com.udistrital.presbyapp;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import co.com.udistrital.presbyapp.dao.EjercicioDAO;
import co.com.udistrital.presbyapp.dao.FormulaDAO;
import co.com.udistrital.presbyapp.dao.HistoricoDAO;
import co.com.udistrital.presbyapp.dao.RestablecerDAO;
import co.com.udistrital.presbyapp.dao.SesionDAO;
import co.com.udistrital.presbyapp.dao.SistemaDAO;
import co.com.udistrital.presbyapp.dao.UsuarioDAO;
import co.com.udistrital.presbyapp.security.Encrypter;
import co.com.udistrital.presbyapp.vo.EjercicioVO;
import co.com.udistrital.presbyapp.vo.FormulaVO;
import co.com.udistrital.presbyapp.vo.HistoricoVO;
import co.com.udistrital.presbyapp.vo.ReestablecerVO;
import co.com.udistrital.presbyapp.vo.SesionVO;
import co.com.udistrital.presbyapp.vo.SistemaVO;
import co.com.udistrital.presbyapp.vo.UsuarioVO;

public class Pruebas extends AppCompatActivity {

    private static final String TAG_LOG = "[PresbiciaApp]";
    Button btnComparar;
    EditText txtClave;
    TextView txtMensaje;

    private FormulaVO formulaVO;
    private ReestablecerVO restablecerVO;
    private SesionVO sesionVO;
    private SistemaVO sistemaVO;
    private UsuarioVO usuarioVO;
    private HistoricoVO historicoVO;
    private EjercicioVO ejercicioVO;

    private FormulaDAO formula;
    private RestablecerDAO restablecer;
    private SesionDAO sesion;
    private SistemaDAO sistema;
    private UsuarioDAO usuario;
    private HistoricoDAO historico;
    private EjercicioDAO ejercicio;

    AnimationDrawable frameAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pruebas);

        ImageView rocketImage = (ImageView) findViewById(R.id.imagePruebas);
        rocketImage.setBackgroundResource(R.drawable.palmeo);

        try {
            frameAnimation = (AnimationDrawable) rocketImage.getBackground();
            frameAnimation.start();
        } catch (Exception ex) {
            Log.e("[Sin_nombre]", "Error: ", ex);
        }

        /*btnComparar = (Button) findViewById(R.id.btnEncriptar);
        txtClave = (EditText) findViewById(R.id.txtPassPrueba);
        txtMensaje = (TextView) findViewById(R.id.txtMensajeR);

        formula = new FormulaDAO(this);
        usuario = new UsuarioDAO(this);
        historico = new HistoricoDAO(this);
        ejercicio = new EjercicioDAO(this);
        /*restablecer = new RestablecerDAO(this);
        sesion = new SesionDAO(this);
        sistema = new SistemaDAO(this);
        usuario = new UsuarioDAO(this);

        formulaVO = new FormulaVO();
        restablecerVO = new ReestablecerVO();
        sesionVO = new SesionVO();
        sistemaVO = new SistemaVO();
        usuarioVO = new UsuarioVO();

        restablecerVO.setPregunta1("¿Cuanto calzas?");
        restablecerVO.setPregunta2("¿?Dibujo animado favorito");
        restablecerVO.setRespuesta1("38");
        restablecerVO.setRespuesta2("Naruto");
        restablecerVO.setTamanoFuente("40");
        sesionVO.setUsuario("Nerfo");
        sesionVO.setContrasena("fernando1124");
        sistemaVO.setFrecuencia(1);
        sistemaVO.setTamanoFuente(40);
        formulaVO.setaVisualOD((float) 2.0);
        formulaVO.setaVisualOI((float) 2.0);

        usuarioVO.setNombreUsuario("Fernando");
        usuarioVO.setApellido1Usuario("Hernandez");
        usuarioVO.setApellido2Usuario("Anzola");
        usuarioVO.setSexo("Masculino");
        usuarioVO.setFechaNacimiento("24-11-1992");
        usuarioVO.setSesionUsuario(sesionVO);
        usuarioVO.setConfigUsuario(sistemaVO);
        usuarioVO.setRestablecerUsuario(restablecerVO);
        usuarioVO.setFormulaUsuario(formulaVO);

        usuario.insert(usuarioVO);

        /*usuarioVO = usuario.consult(1);
        Log.d("[Sin_nombre]", "Id: " + usuarioVO.getIdUsuario());
        Log.d("[Sin_nombre]", "Nombre: " + usuarioVO.getNombreUsuario());
        Log.d("[Sin_nombre]", "Apellido1: " + usuarioVO.getApellido1Usuario());
        Log.d("[Sin_nombre]", "Apellido2: " + usuarioVO.getApellido2Usuario());
        Log.d("[Sin_nombre]", "Fecha Nacimiento: " + usuarioVO.getFechaNacimiento());
        Log.d("[Sin_nombre]", "Sexo: " + usuarioVO.getSexo());

        List<UsuarioVO> usuarios = usuario.list();
        for(int i=0; i<usuarios.size();i++){
            usuarioVO = usuarios.get(i);
            Log.d("[Sin_nombre]", "Id: " + usuarioVO.getIdUsuario());
            Log.d("[Sin_nombre]", "Nombre: " + usuarioVO.getNombreUsuario());
            Log.d("[Sin_nombre]", "Apellido1: " + usuarioVO.getApellido1Usuario());
            Log.d("[Sin_nombre]", "Apellido2: " + usuarioVO.getApellido2Usuario());
            Log.d("[Sin_nombre]", "Fecha Nacimiento: " + usuarioVO.getFechaNacimiento());
            Log.d("[Sin_nombre]", "Sexo: " + usuarioVO.getSexo());
        }

        int ultimo = formula.consultLastID();
        Log.d("[Sin_nombre]", " Ultimo registro en Formula: " + ultimo);

        String msgEncriptar = "fernando1124";
        String msgEncriptado = Encrypter.getStringMessageDigest(msgEncriptar, Encrypter.SHA256);

        Log.d(TAG_LOG, "Mensaje Original: " + msgEncriptar);
        Log.d(TAG_LOG, "Mensaje Encriptado: " + msgEncriptado);

        try {
            List<HistoricoVO> historicos = historico.list();
            List<UsuarioVO> list = usuario.list();
            for (int i = 0; i < list.size(); i++) {
                usuarioVO = list.get(i);
                Log.d(TAG_LOG, "===================================================================================");
                Log.d(TAG_LOG, "Id: " + usuarioVO.getIdUsuario());
                Log.d(TAG_LOG, "Nombre: " + usuarioVO.getNombreUsuario());
                Log.d(TAG_LOG, "Apellido1: " + usuarioVO.getApellido1Usuario());
                Log.d(TAG_LOG, "Apellido2: " + usuarioVO.getApellido2Usuario());
                Log.d(TAG_LOG, "Fecha Nacimiento: " + usuarioVO.getFechaNacimiento());
                Log.d(TAG_LOG, "Sexo: " + usuarioVO.getSexo());
                Log.d(TAG_LOG, "Formula: " + (i + 1) + ": " + usuarioVO.getFormulaUsuario().getIdFormula());
                Log.d(TAG_LOG, "Formula Iz " + (i + 1) + ": " + usuarioVO.getFormulaUsuario().getaVisualOI());
                Log.d(TAG_LOG, "Formula Dr " + (i + 1) + ": " + usuarioVO.getFormulaUsuario().getaVisualOD());
                Log.d(TAG_LOG, "Formula Tam " + (i + 1) + ": " + usuarioVO.getFormulaUsuario().getTamanioFuente());
                Log.d(TAG_LOG, "Sesion " + (i + 1) + ": " + usuarioVO.getSesionUsuario().getIdSesion());
                Log.d(TAG_LOG, "Sesion " + (i + 1) + ": " + usuarioVO.getSesionUsuario().getUsuario());
                Log.d(TAG_LOG, "Sesion " + (i + 1) + ": " + usuarioVO.getSesionUsuario().getContrasena());
                Log.d(TAG_LOG, "Sistema " + (i + 1) + ": " + usuarioVO.getConfigUsuario().getIdSistema());
                Log.d(TAG_LOG, "Sistema " + (i + 1) + ": " + usuarioVO.getConfigUsuario().getFrecuencia());
                Log.d(TAG_LOG, "Sistema " + (i + 1) + ": " + usuarioVO.getConfigUsuario().getTamanoFuente());
                Log.d(TAG_LOG, "Restablecer " + (i + 1) + ": " + usuarioVO.getRestablecerUsuario().getIdReestablecer());
                Log.d(TAG_LOG, "Restablecer " + (i + 1) + ": " + usuarioVO.getRestablecerUsuario().getPregunta1());
                Log.d(TAG_LOG, "Restablecer " + (i + 1) + ": " + usuarioVO.getRestablecerUsuario().getPregunta2());
                Log.d(TAG_LOG, "Restablecer " + (i + 1) + ": " + usuarioVO.getRestablecerUsuario().getTamanoFuente());
                Log.d(TAG_LOG, "===================================================================================");
            }
            /*for (int j = 0; j < historicos.size(); j++) {
                historicoVO = historicos.get(j);
                Log.d(TAG_LOG, "===================================================================================");
                Log.d(TAG_LOG, "Historio " + (j + 1) + ": " + historicoVO.getId());
                Log.d(TAG_LOG, "Historio " + (j + 1) + ": " + historicoVO.getTiempo());
                Log.d(TAG_LOG, "Historio " + (j + 1) + ": " + historicoVO.getFechaHistorico());
                Log.d(TAG_LOG, "===================================================================================");
            }
            ejercicioVO = new EjercicioVO();
            ejercicio.insert(ejercicioVO);
        } catch (Exception ex) {
            Log.e("[Sin_nombre]", "Ocurrio un error en la consulta" + new Date(), ex);
        }*/
    }

    public void compararEncriptado(View v) {
        String respuesta = null;
        if (txtClave != null && !txtClave.getText().toString().trim().equals("")) {
            String claveCorrecta = "fernando1124";
            String claveIngresada = txtClave.getText().toString();

            String claveOriginalEncryptada = Encrypter.getStringMessageDigest(claveCorrecta, Encrypter.SHA256);
            if (Encrypter.getStringMessageDigest(claveIngresada, Encrypter.SHA256).equals(claveOriginalEncryptada)) {
                respuesta = "Clave ingresada correctamente: \n" + "Clave Encriptada: " + claveOriginalEncryptada;
                txtMensaje.setText(respuesta);
            } else {
                respuesta = "Clave ingresada no es correcta";
            }
        } else {
            respuesta = "Ingrese un valor valido";
            txtClave.setHint(respuesta);
            txtClave.setHintTextColor(Color.parseColor("#51FF1218"));
        }
    }
}
