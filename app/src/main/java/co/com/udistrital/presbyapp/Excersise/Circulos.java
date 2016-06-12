package co.com.udistrital.presbyapp.Excersise;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;


import java.util.Date;

import co.com.udistrital.presbyapp.R;
import co.com.udistrital.presbyapp.dao.HistoricoExcDAO;
import co.com.udistrital.presbyapp.vo.HistoricoExcVO;


public class Circulos extends AppCompatActivity {

    private static String TAG_LOG = "[PresbiciaApp]";

    ImageView imagen;
    int cont = 0;
    Chronometer c;
    Button boton;
    CountDownTimer desc;
    boolean ini=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circulos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ejercicio - Circulos");
        try{
            c = (Chronometer) findViewById(R.id.chronometer);
            imagen = (ImageView) findViewById(R.id.imagen);
            boton = (Button)findViewById(R.id.btngirar);
        }catch (Exception e){
            Log.e(TAG_LOG, "Error-Circulos ",e);
        }

    }

    public void girar(View v) {
        try{
            if(boton.getText().equals("Comenzar")) {
                desc = new CountDownTimer(57000, 5600) {
                    public void onTick(long millisUntilFinished) {
                        ini = true;
                        Log.e("[Prueba]", "seg: " + millisUntilFinished);
                        c.setText("Vueltas Restantes: " + (10 - cont));
                        if (cont < 5) {
                            Animation giro;
                            giro = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.girarder);
                            giro.reset();
                            imagen.startAnimation(giro);
                            cont++;
                        } else {
                            if (cont < 10) {
                                Animation giro;
                                giro = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.girarizq);
                                giro.reset();
                                imagen.startAnimation(giro);
                                cont++;
                            } else
                                cont = 0;
                        }
                    }
                    public void onFinish() {
                        c.setText("FINALIZADO");
                        cont = 0;
                        HistoricoExcVO objE = new HistoricoExcVO();
                        HistoricoExcDAO objDB = new HistoricoExcDAO(getApplicationContext());
                        objE.setIdUsuario(Integer.parseInt(getDatosUsuario()));
                        objE.setIdEjercicio(3);
                        objE.setFechaRegistro(new Date());
                        objDB.insert(objE);
                        Log.e(TAG_LOG, "HOLA: " + objE.getIdUsuario());
                        c.setText("FINALIZADO");
                        boton.setText("Comenzar");
                        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.notificacion);
                        mp.start();
                    }
                };
                desc.start();
                boton.setText("Cancelar");
            }else{
                desc.cancel();
                c.setText("00:00");
                boton.setText("Comenzar");
            }
        }catch (Exception e){
            Log.e(TAG_LOG, "Error-Circulos ",e);
        }
    }

    public String getDatosUsuario() {
        try {
            SharedPreferences prefe = getSharedPreferences("Usuario", Context.MODE_PRIVATE);
            String[] v = prefe.getString("Datos", "0:0:0").split(":");
            return v[0];
        } catch (Exception e) {
            Log.e(TAG_LOG, "Error " + e.toString(), e);
        }
        return null;
    }

    public void girar(int dir) {
        try{
            if (dir == 1) {
                Animation giro;
                giro = AnimationUtils.loadAnimation(this, R.anim.girarder);
                giro.reset();
                imagen.startAnimation(giro);
            } else {
                Animation giro;
                giro = AnimationUtils.loadAnimation(this, R.anim.girarizq);
                giro.reset();
                imagen.startAnimation(giro);
            }
        }catch (Exception e){
            Log.e(TAG_LOG, "Error " + e.toString(), e);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if(ini)
                desc.cancel();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(ini)
                desc.cancel();
            Circulos.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
