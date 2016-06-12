package co.com.udistrital.presbyapp.view;

import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import co.com.udistrital.presbyapp.R;
import co.com.udistrital.presbyapp.dao.FormulaDAO;
import co.com.udistrital.presbyapp.dao.HistoricoLetraFrecuencuaDAO;
import co.com.udistrital.presbyapp.dao.RestablecerDAO;
import co.com.udistrital.presbyapp.dao.SesionDAO;
import co.com.udistrital.presbyapp.dao.SistemaDAO;
import co.com.udistrital.presbyapp.dao.UsuarioDAO;
import co.com.udistrital.presbyapp.security.Encrypter;
import co.com.udistrital.presbyapp.util.DateDialog;
import co.com.udistrital.presbyapp.util.OptometriaUtil;
import co.com.udistrital.presbyapp.vo.FormulaVO;
import co.com.udistrital.presbyapp.vo.HistoricoLetraFrecuenciaVO;
import co.com.udistrital.presbyapp.vo.ReestablecerVO;
import co.com.udistrital.presbyapp.vo.SesionVO;
import co.com.udistrital.presbyapp.vo.SistemaVO;
import co.com.udistrital.presbyapp.vo.UsuarioVO;

public class ModificacionDatos extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private static String TAG_LOG = "[PresbiciaApp]";

    /**
     * Elemento que permite generar las pestañas
     */
    private TabHost TabHDos;
    /**
     * vector que almacena cada una de las preguntas separadamente
     */
    String[] opciones = {"Seleccione una pregunta",
            "¿Nombre de tu mascota preferida?",
            "¿Lugar de nacimiento de tu padre?",
            "¿Cancion favorita?",
            "¿Mejor amigo?"};

    /**
     * Variable de usuario en sesion.
     */
    private int idUsuario;

    private Spinner preguntasUno;
    private Spinner preguntasDos;
    private SeekBar seekBar,brillo;
    private EditText texto;
    private SeekBar barra, barra2;//elementos que permite seleccionar la formula(numero) de cada ojo
    private NumberPicker fre;//elemento que permite selecionar el numero de horas para la frecuencia de tiempo
    private EditText txtIdUsuario, txtNombreCompleto, txtApellidoCompleto, txtFechaNacimiento, txtSexoM;
    private TextView iz, de;//elementos que llevaran el valor de formula del ojo izquierdo y  del ojo derecho
    private EditText txtUser, txtPassUno, txtPassDos;
    private EditText txtRespuestaUno, txtRespuestaDos;
    private UsuarioVO usuario;
    private EditText tamanioF;
    private UsuarioDAO dao;
    private SesionVO sesion;
    private SesionDAO daoS;
    private ReestablecerVO restablecer;
    private RestablecerDAO daoR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificacion_datos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Modificacion de datos ");
        cargarDatosIniciales();
        ultimaReferencias();
        cargarTabHost();
        vistaInicial();
        cargardatos();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void cargarDatosIniciales() {
        // Recibiendo parametros de la Actividad InicioSesion
        Bundle bundle = getIntent().getExtras();
        idUsuario = Integer.parseInt(bundle.getString("idUsuarioM"));
        Log.d(TAG_LOG, "Parametro recibido: " + idUsuario);
    }

    public void ultimaReferencias(){
        tamanioF = (EditText) findViewById(R.id.txtTamanioFuente);
        seekBar = (SeekBar) findViewById(R.id.sbBarra);
        texto = (EditText) findViewById(R.id.txtTexto);
        seekBar.setProgress((int) texto.getTextSize());
        tamanioF.setText("Tamaño de la Fuente: " + seekBar.getProgress() + "%");
        barra = (SeekBar) findViewById(R.id.barraM);
        barra.setMax(20);
        barra.setOnSeekBarChangeListener(this);
        barra2 = (SeekBar) findViewById(R.id.barra2M);
        barra2.setMax(20);
        barra2.setOnSeekBarChangeListener(this);
        brillo = (SeekBar) findViewById(R.id.barra5);
        brillo.setMax(255);
        brillo.setOnSeekBarChangeListener(this);
        iz = (TextView) findViewById(R.id.lblizq);
        iz.setText("0.0");
        de = (TextView) findViewById(R.id.lblder);
        de.setText("0.0");
        referenciaTres();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tamanioF.setText("Tamaño de la Fuente: " + progress + "%");
                texto.setTextSize(TypedValue.COMPLEX_UNIT_PX, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void vistaInicial() {
        /* Codigo para llenar los Spinner */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opciones);
        preguntasUno = (Spinner) findViewById(R.id.spPreguntaUno);
        preguntasDos = (Spinner) findViewById(R.id.spPreguntaDos);

        preguntasUno.setAdapter(adapter);
        preguntasDos.setAdapter(adapter);

        txtRespuestaUno = (EditText) findViewById(R.id.txtRespuestaUnoMD);
        txtRespuestaDos = (EditText) findViewById(R.id.txtRespuestaDosMD);

        txtIdUsuario = (EditText) findViewById(R.id.txtIdUserMD);
        txtNombreCompleto = (EditText) findViewById(R.id.txtNombreMD);
        txtApellidoCompleto = (EditText) findViewById(R.id.txtApellidoMD);
        txtFechaNacimiento = (EditText) findViewById(R.id.txtFechaNacimientoMD);
        txtSexoM = (EditText) findViewById(R.id.txtSexoMD);

        txtUser = (EditText) findViewById(R.id.txtUserMD);
        txtPassUno = (EditText) findViewById(R.id.txtPassUnoMD);
        txtPassDos = (EditText) findViewById(R.id.txtPassDosMD);

        dao = new UsuarioDAO(this);
        try {
            usuario = dao.consult(idUsuario);
            txtIdUsuario.setText("" + usuario.getIdUsuario());
            txtNombreCompleto.setText("" + usuario.getNombreUsuario());
            Log.e(TAG_LOG, "lo que lleva el objeto:" +usuario.getApellido2Usuario() + ":");
            txtApellidoCompleto.setText("" + usuario.getApellido1Usuario() + " " + usuario.getApellido2Usuario());
            txtFechaNacimiento.setText("" + usuario.getFechaNacimiento());
            txtSexoM.setText("" + usuario.getSexo());

            sesion = usuario.getSesionUsuario();
            txtUser.setText("" + sesion.getUsuario());
            txtPassUno.setText("" + sesion.getContrasena());
            txtPassDos.setText("" + sesion.getContrasena());
        } catch (Exception e) {
            Log.e(TAG_LOG, "Error en la ejecucion de la Actividad: ", e);
        }
    }

    private void cargarTabHost() {
        TabHDos = (TabHost) findViewById(R.id.tabHost2);
        TabHDos.setup();
        TabHDos.addTab(TabHDos.newTabSpec("ModUno").setIndicator("Datos Personales").setContent(R.id.modificarUno));
        TabHDos.addTab(TabHDos.newTabSpec("ModDos").setIndicator("Datos de Seguridad").setContent(R.id.modificarDos));
        TabHDos.addTab(TabHDos.newTabSpec("ModTres").setIndicator("Datos de Sesion").setContent(R.id.modificarTres));
        TabHDos.addTab(TabHDos.newTabSpec("ModCuatro").setIndicator("Datos de Configuracion").setContent(R.id.modificarCuatro));
    }

    public void modificarDatosPersonales(View v) {
        try {
            dao = new UsuarioDAO(this);
            usuario.setIdUsuario(Integer.parseInt(txtIdUsuario.getText().toString()));
            usuario.setNombreUsuario(txtNombreCompleto.getText().toString());
            String[] apellidos = txtApellidoCompleto.getText().toString().split(" ");
            Log.d(TAG_LOG, "Tamaño de array: " + apellidos.length);
            if (apellidos.length > 1) {
                if (apellidos[1] != null || !apellidos[1].trim().equals("")) {
                    usuario.setApellido1Usuario(apellidos[0]);
                    usuario.setApellido2Usuario(apellidos[1]);
                }
            } else {
                usuario.setApellido1Usuario(apellidos[0]);
                usuario.setApellido2Usuario("");
            }
            usuario.setFechaNacimiento(txtFechaNacimiento.getText().toString());
            usuario.setSexo(txtSexoM.getText().toString());
            dao.update(usuario);
            Toast.makeText(this, " Datos modificados correctamente! ", Toast.LENGTH_LONG).show();
            this.finish();
        }catch (Exception e){
            Log.e(TAG_LOG, "Error " + e.toString(), e);
        }
    }

    /**
     * <b>Descripcion: </b> Metodo encargado de realizar la modificacion de los datos para restablecer
     * la contraseña de seguridad e inicio de sesion.
     *
     * @param v
     */
    public void modificarSeguridad(View v) {
        try{
            daoR = new RestablecerDAO(this);
            restablecer = dao.consult(idUsuario).getRestablecerUsuario();
            String quest1 = preguntasUno.getSelectedItem().toString();
            String quest2 = preguntasDos.getSelectedItem().toString();
            if (!quest1.equals(quest2)) {
                if (!txtRespuestaUno.getText().toString().trim().equals("") && !txtRespuestaDos.getText().toString().equals("")) {
                    restablecer.setPregunta1(quest1);
                    restablecer.setPregunta2(quest2);
                    restablecer.setRespuesta1(txtRespuestaUno.getText().toString());
                    restablecer.setRespuesta2(txtRespuestaDos.getText().toString());
                    daoR.update(restablecer);
                    Toast.makeText(this, " Datos modificados correctamente! ", Toast.LENGTH_LONG).show();
                    this.finish();
                } else {
                    txtRespuestaUno.setText("");
                    txtRespuestaUno.setHint("Debe ingresar respuesta a la pregunta.");
                    txtRespuestaUno.setHintTextColor(Color.parseColor("#51FF1218"));
                    txtRespuestaDos.setText("");
                    txtRespuestaDos.setHint("Debe ingresar respuesta a la pregunta.");
                    txtRespuestaDos.setHintTextColor(Color.parseColor("#51FF1218"));
                }
            } else {
                txtRespuestaUno.setText("");
                txtRespuestaUno.setHint("Las preguntas deben ser diferentes.");
                txtRespuestaUno.setHintTextColor(Color.parseColor("#51FF1218"));
                txtRespuestaDos.setText("");
                txtRespuestaDos.setHint("Las preguntas deben ser diferentes.");
                txtRespuestaDos.setHintTextColor(Color.parseColor("#51FF1218"));
            }
        }catch (Exception e){
            Log.e(TAG_LOG, "Error " + e.toString(), e);
        }

    }

    /**
     * <b>Descripcion: </b> Metodo encargado de actualizar el nombre de usuario y la nueva
     * contraseña del usuario en sesion
     *
     * @param v
     */
    public void modificarDatosSesion(View v) {
        try{
            daoS = new SesionDAO(this);
            sesion = dao.consult(idUsuario).getSesionUsuario();
            sesion.setUsuario(txtUser.getText().toString());
            if (validarCamposPass()) {
                String claveAux = txtPassDos.getText().toString();
                String claveUpdate = Encrypter.getStringMessageDigest(claveAux, Encrypter.SHA256);
                sesion.setContrasena(claveUpdate);
                daoS.update(sesion);
                Toast.makeText(this, " Datos modificados correctamente! ", Toast.LENGTH_LONG).show();
                this.finish();
            } else {
                txtPassUno.setText("");
                txtPassUno.setHint("Las contraseñas deben ser iguales");
                txtPassUno.setHintTextColor(Color.parseColor("#51FF1218"));
                txtPassDos.setText("");
                txtPassDos.setHint("Las contraseñas deben ser iguales");
                txtPassDos.setHintTextColor(Color.parseColor("#51FF1218"));
            }
        }catch (Exception e){
            Log.e(TAG_LOG, "Error " + e.toString(), e);
        }
    }

    public void ActualizarFormula() {
        Log.e(TAG_LOG, "Entro A Actualizar Formula");
        FormulaVO datoFormula = new FormulaVO();
        FormulaDAO objDB = new FormulaDAO(this);
        if(!iz.getText().toString().trim().equals("") && !de.getText().toString().trim().equals("")) {
            datoFormula.setaVisualOD(Float.parseFloat(de.getText().toString()));
            datoFormula.setaVisualOI(Float.parseFloat(iz.getText().toString()));
        } else {
            datoFormula.setaVisualOD((float) 0.0);
            datoFormula.setaVisualOI((float) 0.0);
        }

        double promFormula = 0.0d;
        Log.d(TAG_LOG, "[llenarFormula] Promedio: " + (datoFormula.getaVisualOD() + datoFormula.getaVisualOI())/2.0d);
        if((datoFormula.getaVisualOD() + datoFormula.getaVisualOI()) != 0.0f) {
            promFormula = (datoFormula.getaVisualOD() + datoFormula.getaVisualOI()) / 2.0d;
            datoFormula.setTamanioFuente("" + OptometriaUtil.asignarTamanioXFormula(promFormula));
        }
        if (Integer.parseInt(datoFormula.getTamanioFuente().replaceAll("([,\\.][0-9]*)", "")) == 0) {
            datoFormula.setTamanioFuente("" + texto.getTextSize());
        }
        datoFormula.setIdFormula(idUsuario);
        objDB.update(datoFormula);

    }

    public boolean validarCamposPass() {
        boolean response = false;
        String valor1 = txtPassUno.getText().toString();
        String valor2 = txtPassDos.getText().toString();
        if (valor1.trim().equals(valor2)) {
            response = true;
        }
        return response;
    }

    public void limpiarCamposSesion(View v) {
        txtUser.setText("");
        txtPassUno.setText("");
        txtPassDos.setText("");
    }

    /**
     * <b>Descripcion: </b> Metodo que permite cargar un Dialogo para seleccionar una fecha.
     */
    public void abrir() {
        txtFechaNacimiento.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DateDialog Dialog = new DateDialog(v);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Dialog.show(ft, "DatePiker");
                }
            }
        });
    }

    /* INICIO Bloque para los botones de Formula Medica */

    public void mas(View v) {
        barra.setProgress(barra.getProgress() + 1);
    }

    public void menos(View v) {
        barra.setProgress(barra.getProgress() - 1);
    }

    public void mas2(View v) {
        barra2.setProgress(barra2.getProgress() + 1);
    }

    public void menos2(View v) {
        barra2.setProgress(barra2.getProgress() - 1);
    }
    /* FIN Bloque botones de Formula Medica */

    public void referenciaTres() {
        fre = (NumberPicker) findViewById(R.id.numero);
        fre.setMaxValue(300);
        fre.setMinValue(5);
        fre.setWrapSelectorWheel(false);

    }

    public void menosET(View v) {
        int progreso;
        int newProgreso;
        if (v.getId() == R.id.btnMenos) {
            progreso = seekBar.getProgress();
            if (progreso > 0) {
                newProgreso = progreso - 1;
                seekBar.setProgress(newProgreso);
                tamanioF.setText("Tamaño de la Fuente: " + newProgreso + "%");
                texto.setTextSize(TypedValue.COMPLEX_UNIT_PX, newProgreso);
            }
        }
    }

    public void masET(View v) {
        int progreso;
        int newProgreso;
        if (v.getId() == R.id.btnMas) {
            progreso = seekBar.getProgress();
            if (progreso < 100) {
                newProgreso = progreso + 1;
                seekBar.setProgress(newProgreso);
                tamanioF.setText("Tamaño de la Fuente: " + newProgreso + "%");
                texto.setTextSize(TypedValue.COMPLEX_UNIT_PX, newProgreso);
            }
        }
    }

    public void cargardatos(){
        SistemaVO objS;
        SistemaDAO objBD=new SistemaDAO(this);
        FormulaDAO objBD2=new FormulaDAO(this);
        FormulaVO objF=objBD2.consult(idUsuario);
        Log.e(TAG_LOG, "llega: " + objF.getaVisualOI() + ": " + objF.getaVisualOD());
        barra.setProgress((int)(objF.getaVisualOI() * 4));
        barra2.setProgress((int)(objF.getaVisualOD() * 4));
        objS=objBD.consult(idUsuario);
        fre.setValue(objS.getFrecuencia());
        seekBar.setProgress((int)objS.getTamanoFuente());

    }

    public void guardar(View v){
        SistemaVO objS = new SistemaVO();
        SistemaDAO objBD = new SistemaDAO(this);
        HistoricoLetraFrecuenciaVO objH = new HistoricoLetraFrecuenciaVO();
        HistoricoLetraFrecuencuaDAO objB = new HistoricoLetraFrecuencuaDAO(this);
        objH.setIdUsuario(idUsuario);
        objH.setFrecuencia("" + fre.getValue());
        objH.setTamaño("" + texto.getTextSize());
        objH.setFechaHistorico(new Date());
        objB.insert(objH);
        objS.setFrecuencia(fre.getValue());
        objS.setTamanoFuente(texto.getTextSize());
        objS.setIdSistema(idUsuario);
        objBD.update(objS);
        Log.e("[1]", "Entro al view guardar");
        ActualizarFormula();
        Toast.makeText(this, " Datos modificados correctamente! ", Toast.LENGTH_LONG).show();
        this.finish();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.e(TAG_LOG,"Progreso:"+progress);
        if (seekBar.equals(barra))
            iz.setText("" + OptometriaUtil.rangoFormulaMedica(progress));
        if (seekBar.equals(barra2))
            de.setText("" + OptometriaUtil.rangoFormulaMedica(progress));
        if(seekBar.equals(brillo)){
            if(progress<1) {progress=1;}
            Settings.System.putInt(getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
