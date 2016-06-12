package co.com.udistrital.presbyapp.util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

import co.com.udistrital.presbyapp.R;
import co.com.udistrital.presbyapp.dao.EjercicioDAO;
import co.com.udistrital.presbyapp.vo.EjercicioVO;
import co.com.udistrital.presbyapp.vo.HistoricoExcVO;

/**
 * Created by Fernando on 02/04/2016.
 */
public class ArrayHistorico extends android.widget.ArrayAdapter<HistoricoExcVO> {

    private static final String LOG_TAG = "[Sin_nombre]";
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final Context context;
    private final List<HistoricoExcVO> historicoList;
    private EjercicioDAO daoE;

    public ArrayHistorico(Context context, List<HistoricoExcVO> historicoList) {
        super(context, R.layout.activity_seguimiento, historicoList);
        this.context = context;
        this.historicoList = historicoList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d(LOG_TAG, "Ingresando al metodo getView...");

        View ejercicioInfoView;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        daoE = new EjercicioDAO(context);

        // Se agregan objetos a la lista principal.
        ejercicioInfoView = inflater.inflate(R.layout.item_historico, parent, false);
        TextView txtEjercicio = (TextView) ejercicioInfoView.findViewById(R.id.txtHistEjercicio);
        String nombreEjercicio = daoE.consult(historicoList.get(position).getIdEjercicio()).getNombre();
        txtEjercicio.setText(nombreEjercicio);
        TextView txtFechaRegistro = (TextView) ejercicioInfoView.findViewById(R.id.txtFechaEjercicio);
        txtFechaRegistro.setText(sdf.format(historicoList.get(position).getFechaRegistro()));
        return ejercicioInfoView;
    }
}
