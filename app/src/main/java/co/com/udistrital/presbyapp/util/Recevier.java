package co.com.udistrital.presbyapp.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

import co.com.udistrital.presbyapp.dao.HistoricoDAO;
import co.com.udistrital.presbyapp.view.InicioSesion;
import co.com.udistrital.presbyapp.vo.HistoricoVO;

/**
 * Created by Usuario on 19/02/2016.
 */
public class  Recevier extends BroadcastReceiver {

    Contador contador;
    pantalla_on_off p;

    public Recevier() {
    }

    public Recevier(Contador c, pantalla_on_off p){
        this.p=p;
        this.contador= c;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        try{
            if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
                contador.continua=true;
                Revisar();
            }
            if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
                contador.continua=false;
                Guardar();
            }
        }catch (Exception e){
            Log.e("[PresbyApp]", " Error en Receiver: ", e);
        }

    }

    public void Guardar(){
        Log.e("[Reinicio]", " Guardando!!!!!!!! ");
        HistoricoVO objH= new HistoricoVO();
        HistoricoVO objC= new HistoricoVO();
        HistoricoDAO objBD = new HistoricoDAO(contador.c);
        objH.setIdUsuario(contador.idUsuarioSesion);
        objH.setTiempo(contador.tiempo);
        objH.setFechaHistorico(new Date());
        objC=objBD.consult2(contador.idUsuarioSesion);
        if(objC==null)
            objBD.insert(objH);
        else
            objBD.update(objH);
    }

    public void Revisar(){
        Log.e("[Reinicio]", " Revisandoooooooo!!!!!!!! ");
        HistoricoVO objC= new HistoricoVO();
        HistoricoDAO objBD = new HistoricoDAO(contador.c);
        objC=objBD.consult2(contador.idUsuarioSesion);
        if(objC==null){
            Log.e("[Reinicio]", " Reincio variablesssssssssssssssssss :D :D :D :D  ");
            contador.horas=0;
            contador.minutos=0;
            contador.segundos=0;
        }
    }

}
