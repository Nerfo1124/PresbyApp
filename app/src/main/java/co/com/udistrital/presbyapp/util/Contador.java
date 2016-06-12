package co.com.udistrital.presbyapp.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

import co.com.udistrital.presbyapp.R;
import co.com.udistrital.presbyapp.dao.HistoricoDAO;
import co.com.udistrital.presbyapp.dao.SistemaDAO;
import co.com.udistrital.presbyapp.view.Ejercicios;
import co.com.udistrital.presbyapp.vo.HistoricoVO;
import co.com.udistrital.presbyapp.vo.SistemaVO;

/**
 * Created by Usuario on 19/02/2016.
 */
public class  Contador extends Thread {

    private static String TAG_LOG = "[Prueba]";
    int notificationID = 1;
    static int frecuencia = 0, band = 0;

    public static int idUsuarioSesion = 0, SesionActiva = 0;

    boolean continua = true, siempre = true;
    public static int minutos = 00, segundos = 00, horas = 0;
    public static String tiempo = "";
    static Context c;

    public Contador(Context c) {
        this.c = c;
    }

    @SuppressWarnings("static-access")
    public void run() {
        try {
            ponerTiempo();
            while (siempre) {
                Log.e("[Prueba]", "POR FUERAAAAA hora:" + horas + " minutos:" + minutos + " segundos:" + segundos + " Frecuencia: " + band);
                if (ReiniciarContador()) {
                    Log.e("[Prueba]", "Parando por un minuto");
                    sleep(60000);
                } else {
                    if (continua) {
                        if (band == (horas * 60 + minutos)&&(horas *60+minutos)>=1) {
                            this.displayNotification();
                            band = band + frecuencia;

                        }
                        if (band < (horas * 60 + minutos)) {
                            band = band + frecuencia;
                        }

                        if (segundos == 59) {
                            segundos = 00;
                            minutos++;
                        }
                        if (minutos >= 59) {
                            minutos = 00;
                            horas++;
                        }
                        segundos++;
                        if (minutos <= 9)
                            tiempo = horas + ":0" + minutos + ":" + segundos;
                        else
                            tiempo = horas + ":" + minutos + ":" + segundos;
                        if (segundos <= 9)
                            tiempo = horas + ":" + minutos + ":0" + segundos;
                        else
                            tiempo = horas + ":" + minutos + ":" + segundos;
                        setTiempoContador();
                    }
                }
                this.sleep(1000);
            }
        } catch (Exception ex) {
            Log.d("Sin_nombre", "Error en la ejecucion del servicio", ex);
        }
    }

    public static void setTiempoContador() {
        try {
            Log.e("[Prueba]", "Guardando Tiempo De Contador");
            SharedPreferences preferencias = c.getSharedPreferences("Contador", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferencias.edit();
            editor.putString("Tiempo", tiempo);
            editor.commit();
        } catch (Exception e) {
            Log.e("[Prueba]", "Error " + e.toString(), e);
        }
    }

    public static String[] getTiempoContador() {
        try {
            SharedPreferences prefe = c.getSharedPreferences("Contador", Context.MODE_PRIVATE);
            String[] v = prefe.getString("Tiempo", "0:0:0").split(":");
            return v;
        } catch (Exception e) {
            Log.e(TAG_LOG, "Error " + e.toString(), e);
        }
        return null;
    }


    public static String[] getDatosUsuario() {
        try {
            SharedPreferences prefe = c.getSharedPreferences("Usuario", Context.MODE_PRIVATE);
            String[] v = prefe.getString("Datos", "0:0:0").split(":");
            return v;
        } catch (Exception e) {
            Log.e(TAG_LOG, "Error " + e.toString(), e);
        }
        return null;
    }

    public boolean ReiniciarContador() {
        Calendar c = Calendar.getInstance();
        Log.e("[Prueba]", "Fecha " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
        if (c.get(Calendar.HOUR_OF_DAY) == 23 && c.get(Calendar.MINUTE) == 59) {
            try {
                Log.e("[Prueba]", " REINICIANDO VARIABLES");
                HistoricoVO objH= new HistoricoVO();
                HistoricoVO objC= new HistoricoVO();
                HistoricoDAO objBD = new HistoricoDAO(this.c);
                objH.setIdUsuario(idUsuarioSesion);
                objH.setTiempo(tiempo);
                objH.setFechaHistorico(new Date());
                objC=objBD.consult2(idUsuarioSesion);
                if(objC==null)
                    objBD.insert(objH);
                else
                    objBD.update(objH);
                segundos = 0;
                minutos = 0;
                horas = 0;
                return true;
            } catch (Exception ex) {
                Log.e(TAG_LOG, "Error en Reinciar Contador", ex);
            }
        }
        return false;
    }

    protected void displayNotification() {
        try {
            Log.e(TAG_LOG, "[Sin_nombre] creando notificacion");
            Intent i = new Intent(c, Ejercicios.class);
            i.putExtra("notificationID", notificationID);

            PendingIntent pendingIntent = PendingIntent.getActivity(c, 0, i, 0);

            NotificationManager nm = (NotificationManager) c.getSystemService(c.NOTIFICATION_SERVICE);

            CharSequence ticker = "Sin-nombre: Debe realizar sus ejercicios";
            CharSequence contentTitle = "Sin-nombre - Ejercicios";
            CharSequence contentText = "Realiza ahora los ejercicios";
            Notification noti = new NotificationCompat.Builder(c)
                    .setContentIntent(pendingIntent)
                    .setTicker(ticker)
                    .setContentTitle(contentTitle)
                    .setContentText(contentText)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .addAction(R.mipmap.ic_launcher, ticker, pendingIntent)
                    .setVibrate(new long[]{100, 250, 100, 500})
                    .build();
            nm.notify(notificationID, noti);
            MediaPlayer mp = MediaPlayer.create(c, R.raw.notificacion);
            mp.start();
        } catch (Exception e) {
            Log.e(TAG_LOG, "Error " + e.toString(), e);
        }

    }


    public static void ponerTiempo(){
        String[] tie = getTiempoContador();
        String[] usu = getDatosUsuario();
        if (tie != null) {
            Log.e("[Prueba]", "Poniendo Tiempo Anterior " + tie[0] + ":" + tie[1] + ":" + tie[2]);
            horas = Integer.parseInt(tie[0]);
            minutos = Integer.parseInt(tie[1]);
            segundos = Integer.parseInt(tie[2]);
            idUsuarioSesion = Integer.parseInt(usu[0]);
            frecuencia = Integer.parseInt(usu[1]);
            SesionActiva = Integer.parseInt(usu[2]);
        }
    }
}

