package co.com.udistrital.presbyapp.util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import co.com.udistrital.presbyapp.R;
import co.com.udistrital.presbyapp.vo.EjercicioVO;

/**
 * Created by Fernando on 27/03/2016.
 */
public class ArrayAdapter extends android.widget.ArrayAdapter<EjercicioVO> {

    private static final String LOG_TAG = "[Sin_nombre]";

    private final Context context;
    private final List<EjercicioVO> ejercicioList;

    public ArrayAdapter(Context context, List<EjercicioVO> ejercicioList) {
        super(context, R.layout.activity_ejercicios, ejercicioList);
        this.context = context;
        this.ejercicioList = ejercicioList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d(LOG_TAG, "Ingresando al metodo getView...");

        View friendInfoView;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Se agregan objetos a la lista principal.
        friendInfoView = inflater.inflate(R.layout.layout_ejercicio, parent, false);
        TextView txtEjercicio = (TextView) friendInfoView.findViewById(R.id.txtNameEjercicio);
        txtEjercicio.setText(ejercicioList.get(position).getNombre());
        TextView txtDescripcion = (TextView) friendInfoView.findViewById(R.id.txtDescripcionExc);
        txtDescripcion.setText(ejercicioList.get(position).getDescripcion());

        // Fragmento de codigo encargado de asignar una imagen al imageView
        ImageView imageView = (ImageView) friendInfoView.findViewById(R.id.avatar);
        Picasso.with(context).load("file:///android_asset/" + ejercicioList.get(position).getImagenURL()).into(imageView);
        return friendInfoView;
    }
}
