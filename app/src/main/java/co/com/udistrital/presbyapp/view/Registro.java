package co.com.udistrital.presbyapp.view;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Color;
import android.provider.Settings;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import co.com.udistrital.presbyapp.R;
import co.com.udistrital.presbyapp.dao.HistoricoLetraFrecuencuaDAO;
import co.com.udistrital.presbyapp.dao.SesionDAO;
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

/**
 * Created by Fernando on 06/03/2016.
 */
public class Registro extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private static String TAG_LOG = "[PresbiciaApp]";

    /**
     * vector que almacena cada una de las preguntas separadamente
     */
    String[] opciones = {"Seleccione una pregunta",
            "¿Nombre de tu mascota preferida?",
            "¿Lugar de nacimiento de tu padre?",
            "¿Cancion favorita?",
            "¿Mejor amigo?"};

    private UsuarioVO usuarioReg = new UsuarioVO();
    private UsuarioDAO objUsuarioDao;
    private SesionDAO objSesionDao;
    private String tamanioSistema;
    /**
     * Elementos para los grupos de tipo de sexo/formula
     */
    private RadioGroup grupoSexo, grupoFamilia;
    /**
     * Permiten agrupar  separadamente los elementos segun sea por formula o por ajuste manual
     */
    private RelativeLayout layoutAnimadoUno, layoutAnimadoDos;
    /**
     * Elementos que permite almacenar en forma de lista preguntas
     */
    private Spinner spPreguntasUno;
    private Spinner spPreguntasDos;
    /**
     * Elemento que permite generar las pestañas
     */
    private TabHost TbH;
    /**
     * Elementos que hacen referencia a los datos necesarios del usuario
     */
    private EditText txtFecha, txtNombreu, txtPassUno, txtPassDos, txtNombre, txtApellido, txtRespuestaUno, txtRespuestaDos;
    private SeekBar barra, barra2;//elementos que permite seleccionar la formula(numero) de cada ojo
    private TextView iz, de;//elementos que llevaran el valor de formula del ojo izquierdo y  del ojo derecho
    private EditText texto;
    private EditText tamanioF;
    private SeekBar seekBar,brillo;
    private NumberPicker fre;//elemento que permite selecionar el numero de horas para la frecuencia de tiempo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG_LOG, "Ingresando a la vista principal de Registro.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//funcion hacia atras
        objUsuarioDao = new UsuarioDAO(this);
        objSesionDao = new SesionDAO(this);
        pestanias();
        referenciaUno();
        referenciaDos();
        referenciaTres();
        abrir();
    }

    /**
     * metodo  que permite retroceder sin perder la informacion ingresada en la actividad anterior
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * este metodo se encarga de referenciar los elementos creados globalmente con los elementos que hay en el XML en la primera pestaña
     * esto con el fin de poder hacer cambios en dichos elementos
     */
    public void referenciaUno() {
        layoutAnimadoUno = (RelativeLayout) findViewById(R.id.dinamicoUno);
        layoutAnimadoDos = (RelativeLayout) findViewById(R.id.dinamicoDos);
        grupoSexo = (RadioGroup) findViewById(R.id.radiogrupoR1);
        grupoFamilia = (RadioGroup) findViewById(R.id.radiogrupo2R1);
        txtFecha = (EditText) findViewById(R.id.txtFechaR1);
        txtNombreu = (EditText) findViewById(R.id.txtUsuarioR1);
        txtPassUno = (EditText) findViewById(R.id.txtPasswordUnoR1);
        txtPassDos = (EditText) findViewById(R.id.txtPasswordDosR1);
        txtNombre = (EditText) findViewById(R.id.txtNombreR1);
        txtApellido = (EditText) findViewById(R.id.txtApellidoR1);
        spPreguntasUno = (Spinner) findViewById(R.id.selectorUnoR1);
        spPreguntasDos = (Spinner) findViewById(R.id.selectorDosR1);
        txtRespuestaUno = (EditText) findViewById(R.id.txtRespuestaUnoR1);
        txtRespuestaDos = (EditText) findViewById(R.id.txtRespuestaDosR1);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opciones);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opciones);
        //se agrega el  vector string a un vector adpatador  con el fin de que este se pueda ajustar al sppiner.
        spPreguntasUno.setAdapter(adapter1);
        spPreguntasDos.setAdapter(adapter2);
    }

    /**
     * <b>Descripcion: </b>Metodo que se encarga de referenciar los elementos creados globalmente con los elementos que hay en el XML en la segunda pestaña
     * esto con el fin de poder hacer cambios en dichos elementos
     */
    public void referenciaDos() {
        seekBar = (SeekBar) findViewById(R.id.sbBarra);
        texto = (EditText) findViewById(R.id.txtTexto);
        seekBar.setProgress((int) texto.getTextSize());
        tamanioF = (EditText) findViewById(R.id.txtTamanioFuente);
        tamanioF.setText("Tamaño de la Fuente: " + seekBar.getProgress() + "%");
        barra = (SeekBar) findViewById(R.id.barra);
        barra.setMax(20);
        barra.setOnSeekBarChangeListener(this);
        barra2 = (SeekBar) findViewById(R.id.barra2);
        barra2.setMax(20);
        barra2.setOnSeekBarChangeListener(this);
        brillo =(SeekBar)findViewById(R.id.barra4);
        brillo.setMax(255);
        brillo.setOnSeekBarChangeListener(this);
        iz = (TextView) findViewById(R.id.lblizq);
        iz.setText("0.0");
        de = (TextView) findViewById(R.id.lblder);
        de.setText("0.0");

        tamanioSistema = "" + texto.getTextSize();

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

    public void referenciaTres() {
        fre = (NumberPicker) findViewById(R.id.numero);
        fre.setMaxValue(300);
        fre.setMinValue(5);
        fre.setWrapSelectorWheel(false);
    }

    /**
     * metodo que ajusta el progreso de las barras.
     *
     * @param seekBar
     * @param progress
     * @param fromUser
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
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

    /**
     * metodo  que permite automaticamente abrir un dialogo que permite selecionar fechas.
     */
    public void abrir() {
        txtFecha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

    /**
     * este metodo realiza cambios en el tabhost, tales como agregar pestañas con sus respectivos diseños(Layout)
     */
    public void pestanias() {
        TbH = (TabHost) findViewById(R.id.tabHost);
        TbH.setup();
        TbH.addTab(TbH.newTabSpec("uno").setIndicator("Datos Personales").setContent(R.id.DatosPersonales));
        TbH.addTab(TbH.newTabSpec("dos").setIndicator("Tu Configuracion").setContent(R.id.Configuracion));
        TbH.addTab(TbH.newTabSpec("tres").setIndicator("Tu Tiempo").setContent(R.id.Tiempo));
        TbH.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
            }
        });
    }

    /**
     * este metodo hace referencia a un jbutton el cual debera revisar que todos los datos ingresados en la primera pestaña
     * se han correctos, al igual  que ocultar o mostrar el  layout  correspondiente a la siguiente pestaña(Formula, configuracion manual)
     * Tener en cuanta generalmente si se devuelve 0 cero  es por que los datos son incorrectos
     * pero   si  se devuelve uno 1 es por que los valores son los adecuados
     *
     * @param v
     */

    public void continuar(View v) {
        revisa(0);
    }

    public int revisa(int opc){
        try{
            int p = espaciosblancos();
            int r = 0, r2 = 0, r3 = 0,r4=0,r5=0;
            if (p == 1 ) {
                r = compararFecha();
                if (r == 1) {
                    r2 = compararPasswords();
                }
                if (r2 == 1) {
                    // Codigo o funcion que determina si el usuario ingresado  ya esta creado
                    String nombreUsuario = txtNombreu.getText().toString();
                    Log.d(TAG_LOG, "[continuar] Nombre de usuario a consultar: " + nombreUsuario);
                    r3 = objSesionDao.consultaNombreU(nombreUsuario);
                    if (r3 == 1) {
                        txtNombreu.setText("");
                        Log.d(TAG_LOG, "[continuar] Ya hay un usuario con el nombre ingresado.");
                        txtNombreu.setHint("Ya hay un usuario con este nombre");
                        txtNombreu.setHintTextColor(Color.parseColor("#51FF1218"));
                        r3=0;
                    }else
                        r3=1;
                }
                if (r3 == 1) {
                    if(Noprimero()==1)
                        r4=1;
                    else {
                        r4 = 0;
                        Toast.makeText(this,"Debe seleccionar una pregunta",Toast.LENGTH_LONG).show();
                    }
                }
                if(r4==1){
                    if(PreguntasDiferentes()==1)
                        r5=1;
                    else {
                        r5 = 0;
                        Toast.makeText(this,"Debe seleccionar preguntas diferentes",Toast.LENGTH_LONG).show();
                    }
                }
                if(r5==1) {
                    if (layoutAnimadoUno.getVisibility() == View.GONE)
                        layoutAnimadoUno.setVisibility(View.VISIBLE);//linea que muestra el layuot junto a todos sus elementos
                    if (layoutAnimadoDos.getVisibility() == View.VISIBLE)
                        layoutAnimadoDos.setVisibility(View.GONE);//metodo que oculpa el layout junto con todos sus elementos
                }
            }
            if (r == 1 && r2 == 1 && r3 == 1 && p == 1 && r4==1 && r5==1) {
                if (grupoFamilia.getCheckedRadioButtonId() != R.id.radiosiR1) {
                    if (layoutAnimadoDos.getVisibility() == View.GONE)
                        layoutAnimadoDos.setVisibility(View.VISIBLE);
                    if (layoutAnimadoUno.getVisibility() == View.VISIBLE)
                        layoutAnimadoUno.setVisibility(View.GONE);
                }
                if(opc==0)
                    TbH.setCurrentTab(1);
                return 1;
            }else
                return 0;
        }catch (Exception e){

        }
        return 0;
    }

    public void continuar2(View v) {
        if ("" + texto.getTextSize() != null && "" + texto.getTextSize() != "") {
            TbH.setCurrentTab(2);
        }
    }

    public int Noprimero(){
        try{
            if(spPreguntasUno.getSelectedItemPosition()==0||spPreguntasDos.getSelectedItemPosition()==0){
                return 0;
            }else
                return 1;
        }catch (Exception e){

        }
        return 0;
    }
    public int PreguntasDiferentes(){
        try{
            if(spPreguntasUno.getSelectedItemPosition()==spPreguntasDos.getSelectedItemPosition()){
                return 0;
            }else
                return 1;

        }catch (Exception e){

        }
        return 0;
    }

    /**
     * metodo  que verifica que los edittext tengan espacion en blanco o con " " y  en caso tal de
     * cumplan esta condicion se devolvera un entero con valor cero 0 o  si todos los edittext tienen
     * datos validos entonces se retornara un  uno 1. tambien muestra mensaje de datos no validos a cada
     * edittext que tiene datos invalidos
     *
     * @return valor 0 0 1.
     */
    public int espaciosblancos() {
        int r = 1;
        if ("".equals(txtNombreu.getText().toString()) || 0 == espacios(txtNombreu.getText().toString())) {
            r = 0;
            txtNombreu.setHint("Debe ingresar un nombre de usuario");
            txtNombreu.setHintTextColor(Color.parseColor("#51FF1218"));
        }

        if ("".equals(txtNombre.getText().toString()) || 0 == espacios(txtNombre.getText().toString())) {
            r = 0;
            txtNombre.setText("");
            txtNombre.setHint("Debe ngresar su nombre");
            txtNombre.setHintTextColor(Color.parseColor("#51FF1218"));//cambia a color rojo
        }

        if ("".equals(txtApellido.getText().toString()) || 0 == espacios(txtApellido.getText().toString())) {
            r = 0;
            txtApellido.setText("");
            txtApellido.setHint("Debe ingresar su apellido");
            txtApellido.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        if ("".equals(txtFecha.getText().toString())) {
            r = 0;
            txtFecha.setText("");
            txtFecha.setHint("Debe ingresar su fecha de nacimiento");
            txtFecha.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        if ("".equals(txtPassUno.getText().toString())) {
            r = 0;
            txtPassUno.setText("");
            txtPassUno.setHint("Debe ingresar una contraseña");
            txtPassUno.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        if ("".equals(txtPassDos.getText().toString())) {
            r = 0;
            txtPassDos.setText("");
            txtPassDos.setHint("Debe ingresar la contraseña");
            txtPassDos.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        if ("".equals(txtRespuestaUno.getText().toString()) || 0 == espacios(txtRespuestaUno.getText().toString())) {
            r = 0;
            txtRespuestaUno.setText("");
            txtRespuestaUno.setHint("Debe ingresar una respuesta acorde");
            txtRespuestaUno.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        if ("".equals(txtRespuestaDos.getText().toString()) || 0 == espacios(txtRespuestaDos.getText().toString())) {
            r = 0;
            txtRespuestaDos.setText("");
            txtRespuestaDos.setHint("Debe ingresar una respuesta acorde");
            txtRespuestaDos.setHintTextColor(Color.parseColor("#51FF1218"));
        }
        return r;
    }

    /**
     * metodo que verifica que la fecha ingresada este en el rango permitido y en caso de que sea
     * correcta retorna un uno 1 y en caso contrario un cero 0. muestra mensaje en caso de que haya
     * error con la fecha
     *
     * @return valor 1 o 0
     */
    public int compararFecha() {
        Calendar f = Calendar.getInstance();
        int anio = f.get(Calendar.YEAR);
        String[] fecha = txtFecha.getText().toString().split("-");
        anio = anio - Integer.parseInt(fecha[2]);
        if (anio > 15)
            return 1;
        else {
            txtFecha.setText("");
            txtFecha.setHint("Su Fecha Debe Ser Menor Al Año 2000");
            txtFecha.setHintTextColor(Color.parseColor("#51FF1218"));
            return 0;
        }
    }

    /**
     * metodo que compara las dos contraseñas ingresas, en caso de que las contraseñas no considan
     * se devolvera un entero  con el valor  cero 0, o en caso contrario se devolvera un uno 1.
     *
     * @return
     */
    public int compararPasswords() {
        if (txtPassUno.getText().toString().equals(txtPassDos.getText().toString()))
            return 1;
        else {
            txtPassDos.setText("");
            txtPassDos.setHint("Las contraseñas no coinciden, repitala");
            txtPassDos.setHintTextColor(Color.parseColor("#51FF1218"));
            return 0;
        }
    }

    /**
     * metodo  que recive un string, y revisa si este string comienza por algun espacio en blanco " "
     *
     * @param t variable que contiene el texto  a revisar
     * @return retorna 0 si el texto  inicia  con " " o  1 si inicia con algun otro  caracter diferente
     * al " "
     */
    public int espacios(String t) {
        if (t.charAt(0) == ' ')
            return 0;//retorna cero si el parametro  comienza con un espacio
        else
            return 1;//retorna uno  si el parametro  no comienza con un espacio
    }

    /**
     * <b>Descripcion: </b> Metodo que hace referencia a un jbutton encargado de aumentar el tamaño de la letra del texto
     * ubicado en la segunga pestaña en la opcion de ajuste manual
     *
     * @param v
     */
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

    /**
     * <b>Descripcion: </b> Metodo que hace referencia a un jbutton encargado de disminuir el tamaño de la letra del texto
     * ubicado en la segunga pestaña en la opcion de ajuste manual
     *
     * @param v
     */
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

    public void llenarUsuario() {
        usuarioReg.setNombreUsuario(txtNombre.getText().toString());
        Log.d(TAG_LOG, "Nombre: " + txtNombre.getText());
        String[] apellidos = txtApellido.getText().toString().split(" ");
        usuarioReg.setApellido1Usuario(apellidos[0]);
        Log.d(TAG_LOG, "[llenarUsuario] Tamaño del array: " + apellidos.length);
        if (apellidos.length > 1) {
            if (apellidos[1] != null && apellidos[1] != "")
                usuarioReg.setApellido2Usuario(apellidos[1]);
        }else {
            Log.d("Sin_nombre", "Se agrega Espacio " + apellidos.length);
            usuarioReg.setApellido2Usuario(" ");
        }
        usuarioReg.setFechaNacimiento(txtFecha.getText().toString());
        if (grupoSexo.getCheckedRadioButtonId() == R.id.rbhombreR1) {
            usuarioReg.setSexo("Masculino");
        } else if (grupoSexo.getCheckedRadioButtonId() == R.id.rbmujerR1) {
            usuarioReg.setSexo("Femenino");
        }

        usuarioReg.setSesionUsuario(llenarSesion());
        Log.e("Hola", "Entroooooooooooooooooooooooooooooooooooooooooooooooo");
        usuarioReg.setFormulaUsuario(llenarFormula());
        usuarioReg.setConfigUsuario(llenarSistema());
        usuarioReg.setRestablecerUsuario(llenarCuenta());
        objUsuarioDao.insert(usuarioReg);


        String datos="Los datos personales de su nuevo usuario son los siguientes: \nNombre de usuario:"+usuarioReg.getSesionUsuario().getUsuario()
                +"\nNombre:"+usuarioReg.getNombreUsuario()+"\nApellidos:"+usuarioReg.getApellido1Usuario()+" "+usuarioReg.getApellido2Usuario()
                +"\nFecha nacimiento:"+usuarioReg.getFechaNacimiento()+"\nSexo:"+usuarioReg.getSexo();

        Dialogo("Nuevos Datos",datos);
        Log.e("Hola", " [llenarUsuario] Usuario registrado satisfactoriamente.");
    }

    public SesionVO llenarSesion() {
        SesionVO datoSesion = new SesionVO();
        datoSesion.setUsuario(txtNombreu.getText().toString());
        datoSesion.setContrasena(Encrypter.getStringMessageDigest(txtPassDos.getText().toString(), Encrypter.SHA256));
        return datoSesion;
    }

    public FormulaVO llenarFormula() {
        try{
            FormulaVO datoFormula = new FormulaVO();
            if(!iz.getText().toString().trim().equals("") && !de.getText().toString().trim().equals("")) {
                datoFormula.setaVisualOD(Float.parseFloat(de.getText().toString()));
                datoFormula.setaVisualOI(Float.parseFloat(iz.getText().toString()));
            } else {
                datoFormula.setaVisualOD((float) 0.0);
                datoFormula.setaVisualOI((float) 0.0);
            }

            double promFormula = 0.0d;
            Log.d(TAG_LOG, "[llenarFormula] Promedio: " + (datoFormula.getaVisualOD() + datoFormula.getaVisualOI()) / 2.0d);
            if((datoFormula.getaVisualOD() + datoFormula.getaVisualOI()) != 0.0f) {
                Log.d(TAG_LOG, "Hola Mundo");
                promFormula = (datoFormula.getaVisualOD() + datoFormula.getaVisualOI()) / 2.0d;
                datoFormula.setTamanioFuente("" + OptometriaUtil.asignarTamanioXFormula(promFormula));
            } else {
                datoFormula.setTamanioFuente("" + texto.getTextSize());
            }

            Log.d(TAG_LOG, "Tamaño Fuente: " + datoFormula.getTamanioFuente().replaceAll("([,\\.][0-9]*)", ""));
            if (Integer.parseInt(datoFormula.getTamanioFuente().replaceAll("([,\\.][0-9]*)", "")) == 0) {
                datoFormula.setTamanioFuente("" + texto.getTextSize());
            }
            return datoFormula;
        }catch (Exception e){
            Log.e(TAG_LOG, "Error: " + e.getMessage().toString(), e);
        }
        return null;
    }


    public SistemaVO llenarSistema() {
        SistemaVO datosistema = new SistemaVO();
        datosistema.setFrecuencia(fre.getValue());
        datosistema.setTamanoFuente(texto.getTextSize());
        return datosistema;
    }

    /**
     * Metodo encargado de llenar la tabla Reestablecer.
     *
     * @return
     */
    public ReestablecerVO llenarCuenta() {
        ReestablecerVO datorestablecer = new ReestablecerVO();
        datorestablecer.setPregunta1(spPreguntasUno.getSelectedItem().toString());
        datorestablecer.setRespuesta1(txtRespuestaUno.getText().toString());
        datorestablecer.setPregunta2(spPreguntasDos.getSelectedItem().toString());
        datorestablecer.setRespuesta2(txtRespuestaDos.getText().toString());
        datorestablecer.setTamanoFuente(tamanioSistema);
        return datorestablecer;
    }

    /**
     * <b>Descripcion: </b> Metodo del boton Terminar, realiza la insercion de los datos de la actividad
     * registro en la BDD y finaliza la actividad.
     *
     * @param v
     */
    public void terminar(View v) {
        try {
            if(revisa(1)==1){
                llenarUsuario();
                //Log.d(TAG_LOG, "Se inserto satisfactoriamente el nuevo usuario.");
            }else {
                TbH.setCurrentTab(0);
                Toast.makeText(this, "Debe llenar todos los campos correctamente", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Log.d(TAG_LOG, "Error al mostrar Perfil de Usuario", ex);
        }
    }

    public void Dialogo(String tit, final String men) {
        try {
            new AlertDialog.Builder( this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(tit)
                    .setMessage(men)
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Registro.this.finish();
                        }
                    }).show();
        } catch (Exception e) {
            Log.e(TAG_LOG, "Error Dialogo " + e.toString(), e);
        }
    }
}
