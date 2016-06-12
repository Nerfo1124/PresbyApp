package co.com.udistrital.presbyapp.view;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import co.com.udistrital.presbyapp.R;
import co.com.udistrital.presbyapp.dao.FormulaDAO;
import co.com.udistrital.presbyapp.dao.HistoricoDAO;
import co.com.udistrital.presbyapp.dao.SistemaDAO;
import co.com.udistrital.presbyapp.dao.UsuarioDAO;
import co.com.udistrital.presbyapp.util.Contador;
import co.com.udistrital.presbyapp.util.ProgressCircle;
import co.com.udistrital.presbyapp.util.pantalla_on_off;
import co.com.udistrital.presbyapp.vo.FormulaVO;
import co.com.udistrital.presbyapp.vo.HistoricoVO;
import co.com.udistrital.presbyapp.vo.SistemaVO;
import co.com.udistrital.presbyapp.vo.UsuarioVO;

public class Principal extends AppCompatActivity {

    private static String TAG_LOG = "[PresbyApp]";

    /**
     * Variable del Usuario que mantenien la sesion
     */
    private UsuarioVO usuarioSesion;
    int idUsuarioSesion,aux;
    boolean primero;
    private SistemaVO objS = new SistemaVO();
    private FormulaVO objF = new FormulaVO();

    ProgressCircle progressCircle;
    Switch s1,s2;
    MyTask myTask=new MyTask();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        progressCircle = (ProgressCircle) findViewById(R.id.progress_circle);
        try{
            cargarDatosIniciales();
            String s[]=getDatosUsuario();
            if(s[0]=="0")
                aux=idUsuarioSesion;
            else
                aux=Integer.parseInt(s[0]);
            if(idUsuarioSesion!=aux){
                guardarContador();
                HistoricoVO objH= new HistoricoVO();
                HistoricoDAO objBD = new HistoricoDAO(this);
                objH=objBD.consult2(idUsuarioSesion);
                if(objH==null){
                    Contador.horas=0;
                    Contador.minutos=0;
                    Contador.segundos=0;
                }else{
                    String v[]= objH.getTiempo().split(":");
                    Contador.horas=Integer.parseInt(v[0]);
                    Contador.minutos=Integer.parseInt(v[1]);
                    Contador.segundos=Integer.parseInt(v[2]);
                }
            }
            cargarSwitchLetra();
            cargarSwitchLetra2();
            setDatosUsuario(1);
            startService(new Intent(this, pantalla_on_off.class));
            this.myTask.execute();
    }catch (Exception e){
        Log.e("[Error]", "Error " + e.toString(), e);
    }
    }

    public void setTiempoContadorP(int ho,int mi, int se) {
        try{
            Log.e(TAG_LOG, "Guardando Tiempo De Contador");
            SharedPreferences preferencias=getSharedPreferences("Contador", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferencias.edit();
            editor.putString("Tiempo", ho + ":" + mi + ":" + se);
            editor.commit();
        }catch (Exception e){
            Log.e(TAG_LOG, "Error " + e.toString(), e);
        }
    }

    public String[] getTiempoContador() {
        try {
            SharedPreferences prefe = getSharedPreferences("Contador", Context.MODE_PRIVATE);
            String[] v = prefe.getString("Tiempo", "0:0:0").split(":");
            return v;
        } catch (Exception e) {
            Log.e(TAG_LOG, "Error " + e.toString(), e);
        }
        return null;
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

    public void setDatosUsuario(int s){
        try {
            Log.e("[Hola]", "Guardando Datos Usuario");
            SharedPreferences preferencias = getSharedPreferences("Usuario", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferencias.edit();
            editor.putString("Datos", idUsuarioSesion+":"+this.objS.getFrecuencia()+":"+s);
            editor.commit();
        } catch (Exception e) {
            Log.e("[Prueba]", "Error " + e.toString(), e);
        }
    }

    public void guardarContador(){
        String s[]=getTiempoContador();
        HistoricoVO objH= new HistoricoVO();
        HistoricoVO objC= new HistoricoVO();
        HistoricoDAO objBD = new HistoricoDAO(this);
        objH.setIdUsuario(aux);
        objH.setTiempo(s[0]+":"+s[1]+":"+s[2]);
        objH.setFechaHistorico(new Date());
        objC=objBD.consult2(aux);
        if(objC==null)
            objBD.insert(objH);
        else
            objBD.update(objH);
    }

    public void cargarDatosIniciales(){
        // Recibiendo parametros de la Actividad InicioSesion
        Bundle bundle = getIntent().getExtras();
        idUsuarioSesion = Integer.parseInt(bundle.getString("idUsuario"));
        Log.d(TAG_LOG, "Parametro recibido: " + idUsuarioSesion);
        //
        UsuarioDAO daoU=new UsuarioDAO(this);
        usuarioSesion = daoU.consult(idUsuarioSesion);
        getSupportActionBar().setTitle("BIENVENIDO: " + usuarioSesion.getNombreUsuario());
    }

    public void cargarSwitchLetra(){
        try {
            SistemaDAO objBD = new SistemaDAO(getApplicationContext());
            objS = objBD.consult(idUsuarioSesion);
            s1=(Switch)findViewById(R.id.letramanual);
            s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if(primero){
                            primero=false;
                            Log.e(TAG_LOG, " entro a primero uno ");
                        }else {
                            bloquearSwitch();
                            s2.setChecked(false);
                            Settings.System.putFloat(getBaseContext().getContentResolver(), Settings.System.FONT_SCALE, (float) objS.getTamanoFuente() / 40);
                            toast();
                        }
                    } else {
                        bloquearSwitch();
                        Settings.System.putFloat(getBaseContext().getContentResolver(), Settings.System.FONT_SCALE, (float) 1);
                        toast();
                    }
                }
            });
        } catch (Exception e) {
            Log.e("[Error]","Error: "+e.getMessage(),e);
        }

    }

    public void cargarSwitchLetra2(){
        FormulaDAO objBD = new FormulaDAO(getApplicationContext());
        objF = objBD.consult(idUsuarioSesion);
        s2=(Switch)findViewById(R.id.letraautomatica);
        s2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    if (isChecked) {
                        if (primero) {
                            primero = false;
                            Log.e("[presbyapp]", " entro a primero dos ");
                        } else {
                            bloquearSwitch();
                            s1.setChecked(false);
                            Settings.System.putFloat(getBaseContext().getContentResolver(), Settings.System.FONT_SCALE, Float.parseFloat(objF.getTamanioFuente()) / 40);
                            toast();
                        }
                    } else {
                        bloquearSwitch();
                        Settings.System.putFloat(getBaseContext().getContentResolver(), Settings.System.FONT_SCALE, (float) 1);
                        toast();
                    }
                } catch (Exception e) {
                    Log.e("[Error]","Error: "+e.getMessage(),e);
                }
            }
        });
        revisarletra();
    }

    public void revisarletra(){
        try{
            Float s=Settings.System.getFloat(getBaseContext().getContentResolver(), Settings.System.FONT_SCALE);
            primero=false;
            if(s==1){
                s1.setChecked(false);
                s2.setChecked(false);
            }else{
                if(s==objS.getTamanoFuente() / 40) {
                    primero=true;
                    s1.setChecked(true);
                    Log.e("[presbyapp]", " S1 ");
                }
                if(s==Float.parseFloat(objF.getTamanioFuente())/40) {
                    primero=true;
                    Log.e("[presbyapp]", " S2 ");
                    s2.setChecked(true);
                }
            }
        }catch (Exception e) {
            Log.e("[Error]", "Error: " + e.getMessage(), e);
        }
    }

    public void bloquearSwitch(){
        s1.setEnabled(false);
        s2.setEnabled(false);
    }
    public void toast(){
        Toast toast3 = new Toast(getApplicationContext());
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.notificacion, (ViewGroup) findViewById(R.id.lytLayout));
        TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);
        txtMsg.setText("Debe reiniciar su dispositivo para poder aplicar los cambios en el tamaño de letra");
        toast3.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
        toast3.setDuration(Toast.LENGTH_LONG);
        toast3.setView(layout);
        toast3.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
    return true;
}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.ejercicio) {
            Intent intent = new Intent(this, Ejercicios.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.cuenta) {
            Intent perfil = new Intent(this, PerfilUsuario.class);
            perfil.putExtra("idUsuario", "" + usuarioSesion.getIdUsuario());
            startActivity(perfil);
        }
        if (id == R.id.modificar) {
            Intent modificar = new Intent(this, ModificacionDatos.class);
            modificar.putExtra("idUsuarioM", "" + idUsuarioSesion);
            startActivity(modificar);
        }
        if (id == R.id.cerrar) {
            Dialogo("¿Cerrar Sesion?", "\tDesea Cerrar Sesion?",0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Desea Salir?")
                    .setMessage("Cierre sesion por favor")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void Dialogo(String tit, final String men, final int opc) {
        try {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(tit)
                    .setMessage(men)
                    .setCancelable(false)
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (opc == 0) {
                                 if (myTask!=null)
                                    myTask.onCancelled();
                                Intent i = new Intent(getApplicationContext(), InicioSesion.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                setDatosUsuario(0);
                                Principal.this.finish();
                            }
                        }
                    }).show();
        } catch (Exception e) {
            Log.e("[Error]", "Error: " + e.getMessage(), e);
        }
    }

    public void seg(View v){
        try{
            Intent seg = new Intent(this, Seguimiento.class);
            seg.putExtra("idUsuario", "" + usuarioSesion.getIdUsuario());
            startActivity(seg);
        }catch (Exception e){
            Log.e("[Error]","Error: "+e.getMessage(),e);
        }
    }

    public void ejercicio(View v){
        Intent intent  = new Intent(this,Ejercicios.class);
        startActivity(intent);
    }




    private class MyTask extends AsyncTask<String, String, String> {

            public boolean cent;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                cent = true;
            }

            @Override
            protected String doInBackground(String... params) {
                while (cent) {
                    try {
                            String v[]=getTiempoContador();
                            Float minutos= Float.parseFloat(v[0])*60+Float.parseFloat(v[1]);
                            Float m = minutos/480;
                            publishProgress(""+m);
                            Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(String... values) {
                progressCircle.setProgress(Float.parseFloat(values[0]));
                progressCircle.startAnimation();
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                cent = false;
            }
    }

}
