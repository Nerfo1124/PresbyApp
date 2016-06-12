package co.com.udistrital.presbyapp.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import co.com.udistrital.presbyapp.R;
import co.com.udistrital.presbyapp.dao.HistoricoDAO;
import co.com.udistrital.presbyapp.dao.HistoricoExcDAO;
import co.com.udistrital.presbyapp.util.ArrayHistorico;
import co.com.udistrital.presbyapp.vo.HistoricoExcVO;
import co.com.udistrital.presbyapp.vo.HistoricoVO;

public class Seguimiento extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String LOG_TAG = "[Sin_nombre]";

    int idUsuarioSesion;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    float v[]={0,0,0,0,0,0,0};
    GraphView graph;
    Date d7, d6, d5, d4, d3, d2,d1;
    TextView txtHistoricoEx;

    ListView historicoView;
    ArrayHistorico myArrayHistorico = null;

    List<HistoricoExcVO> historicoInfoList = null;
    Context mContext;

    private TabHost TabHostHistorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimiento);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Seguimientos ");
        cargarTabHost();
        try{
            Bundle bundle = getIntent().getExtras();
            idUsuarioSesion = Integer.parseInt(bundle.getString("idUsuario"));
            cargardatos();
            grafica();
        }catch (Exception e){
            Log.e("[Sin_nombre]", "[onCreate] Error en Seguimiento: " + e.toString(), e);
        }

        mContext = this;

        try {
            historicoView = (ListView) findViewById(R.id.listaHistorico);
            historicoView.setOnItemClickListener(this);
            HistoricoExcDAO dao = new HistoricoExcDAO(mContext);
            historicoInfoList = dao.list(idUsuarioSesion);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error durante la carga de los historicos.", e);
        }

        Log.d(LOG_TAG, "Error durante la carga de los historicos." + historicoInfoList);
        if (historicoInfoList != null && !historicoInfoList.isEmpty()) {
            myArrayHistorico = new ArrayHistorico(mContext, historicoInfoList);
            historicoView.setAdapter((ListAdapter) myArrayHistorico);
        } else {
            txtHistoricoEx = (TextView) findViewById(R.id.txtHistoricoTitulo);
            txtHistoricoEx.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void cargardatos(){
        try{
            Calendar calendar = Calendar.getInstance();
            d7 = retrocederfecha(calendar);
            d6 = retrocederfecha(calendar);
            d5 = retrocederfecha(calendar);
            d4 = retrocederfecha(calendar);
            d3 = retrocederfecha(calendar);
            d2 = retrocederfecha(calendar);
            d1 = retrocederfecha(calendar);
            consulta(d7,6);
            consulta(d6,5);
            consulta(d5,4);
            consulta(d4,3);
            consulta(d3,2);
            consulta(d2,1);
            consulta(d1,0);
        }catch (Exception e){
            Log.e("[Sin_nombre]", "[Cargardatos] Error en Seguimiento: " + e.toString(), e);
        }
    }

    public void grafica(){

        graph= (GraphView) findViewById(R.id.graph);
        graph.setTitle("Historico Uso");
        graph.setTitleTextSize(50);
        graph.setTitleColor(Color.GREEN);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(d1, v[0]),
                new DataPoint(d2, v[1]),
                new DataPoint(d3, v[2]),
                new DataPoint(d4, v[3]),
                new DataPoint(d5, v[4]),
                new DataPoint(d6, v[5]),
                new DataPoint(d7, v[6])

        });
        series.setColor(Color.GREEN);
        series.setDrawDataPoints(true);

        graph.addSeries(series);
        graph.getGridLabelRenderer().setTextSize(1);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(7); // only 4 because of the space

// set manual x bounds to have nice steps
        graph.getViewport().setMaxY(8);
        graph.getViewport().setMinY(0);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d7.getTime());
        graph.getViewport().setXAxisBoundsManual(true);

    }

    public Date retrocederfecha(Calendar calendar){
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public void consulta(Date dia,int pos){
        try {
            HistoricoVO objH = new HistoricoVO();
            HistoricoDAO objBD = new HistoricoDAO(this);
            objH = objBD.consultIdFecha(idUsuarioSesion, dia);
            if (objH == null)
                v[pos] = 0;
            else{
                String s[]=objH.getTiempo().split(":");
                float min= Float.parseFloat(s[0])+Float.parseFloat(s[1])/60;
                Log.e("[Sin_nombre]", "[consulta] min: " + min);
                v[pos] = min;
            }
        }catch (Exception e){
            Log.e("[Sin_nombre]", "[consulta] Error en Seguimiento: " + e.toString(), e);
        }
    }

    public void insertar(View v){
        HistoricoVO objH = new HistoricoVO();
        HistoricoDAO objBD = new HistoricoDAO(this);
        objH.setIdUsuario(idUsuarioSesion);
        objH.setFechaHistorico(d1);
        objH.setTiempo("0:10:50");
        objBD.insert(objH);
        objH.setFechaHistorico(d2);
        objH.setTiempo("0:20:15");
        objBD.insert(objH);
        objH.setFechaHistorico(d3);
        objH.setTiempo("0:15:45");
        objBD.insert(objH);
        objH.setFechaHistorico(d4);
        objH.setTiempo("0:30:15");
        objBD.insert(objH);
        objH.setFechaHistorico(d5);
        objH.setTiempo("0:45:00");
        objBD.insert(objH);
        objH.setFechaHistorico(d6);
        objH.setTiempo("0:1:15");
        objBD.insert(objH);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void cargarTabHost() {
        TabHostHistorico = (TabHost) findViewById(R.id.tabHost3);
        TabHostHistorico.setup();
        TabHostHistorico.addTab(TabHostHistorico.newTabSpec("HistUno").setIndicator("Historico de Uso").setContent(R.id.historicoUno));
        TabHostHistorico.addTab(TabHostHistorico.newTabSpec("HistDos").setIndicator("Historico de Ejercicios").setContent(R.id.historicoDos));
    }
}
