package co.com.udistrital.presbyapp.util;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;


public class  pantalla_on_off extends Service {

    private static String TAG_LOG = "[PresbyApp]";

    Contador contador = new Contador(this);

    public pantalla_on_off() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            contador.start();
            IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            BroadcastReceiver mReceiver = new Recevier(contador, this);
            registerReceiver(mReceiver, filter);
            Log.e(TAG_LOG, "Servicio creado!");
        } catch (Exception e) {
            Log.e(TAG_LOG, "Error " + e.toString(), e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            contador.stop();
            Log.e(TAG_LOG, "Servicio destruido!");
        } catch (Exception e) {
            Log.e(TAG_LOG, "Error " + e.toString(), e);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}


