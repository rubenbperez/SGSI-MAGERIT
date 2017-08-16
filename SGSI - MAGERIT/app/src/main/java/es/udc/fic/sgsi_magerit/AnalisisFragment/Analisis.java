package es.udc.fic.sgsi_magerit.AnalisisFragment;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.udc.fic.sgsi_magerit.Model.Analisis.AnalisisDTO;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;

public class Analisis extends Fragment {

    private ListView lstOpciones;
    private List<AnalisisDTO> data;
    private ModelServiceImpl service;
    private AnalisisAdapter adaptador;
    private long idProyectoActivo;
    OrientationEventListener mOrientationListener;


    public Analisis() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analisis, container, false);
        service = new ModelServiceImpl(getContext(), GlobalConstants.DATABASE_NAME, 1);
        idProyectoActivo = service.obtenerIdProyectoActivo();

        OrientationEventListener mOrientationListener;
        mOrientationListener = new OrientationEventListener(this.getContext(),
                SensorManager.SENSOR_DELAY_UI) {
            @Override
            public void onOrientationChanged(int orientation) {
                Log.d("ORIENTACION",
                        "Orientation changed to " + orientation);
            }
        };

        if (mOrientationListener.canDetectOrientation()) {
            mOrientationListener.enable();
        }


        AnalisisDTO a1 = new AnalisisDTO(1,1,"hola","hl",100000L,500000L,1000000L,5000L,null);
        AnalisisDTO a2 = new AnalisisDTO(1,1,"hey","brrr",3500L,861000L,35000L,null,null);
        AnalisisDTO a3 = new AnalisisDTO(1,1,"acff","tuu",null,null,500000L,null,null);

        data = new ArrayList<>();
        data.add(a1);
        data.add(a2);
        data.add(a3);

        adaptador =
                new AnalisisAdapter(this.getContext(), data);

        lstOpciones = (ListView) view.findViewById(R.id.LstOpciones);
        lstOpciones.setAdapter(adaptador);

        return view;
    }

    /*@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d("Orientacion", "Landscape");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.d("Orientacion", "Portrait");
        }
    } android:configChanges="orientation|screenSize"*/



    public class AnalisisAdapter extends ArrayAdapter<AnalisisDTO> {

        public AnalisisAdapter(Context context, List<AnalisisDTO> data) {
            super(context, android.R.layout.simple_list_item_single_choice, data);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());

            View item = inflater.inflate(R.layout.listitem_analisis, null);

            TextView lblAssetCode = (TextView)item.findViewById(R.id.asset_code);
            lblAssetCode.setText("[" + data.get(position).getCodigoActivo() + "] " +
                    data.get(position).getNombreActivo());

            TextView lblDisp = (TextView)item.findViewById(R.id.disp);
            Long disp = data.get(position).getValoracionDisponibilidad();
            if (disp != null)
                lblDisp.setText(disp.toString());
            else
                lblDisp.setText("-");

            TextView lblInt = (TextView)item.findViewById(R.id.integ);
            Long integ = data.get(position).getValoracionIntegridad();
            if (integ != null)
                lblInt.setText(integ.toString());
            else
                lblInt.setText("-");

            TextView lblConf = (TextView)item.findViewById(R.id.conf);
            Long conf = data.get(position).getValoracionConfidencialidad();
            if (conf != null)
                lblConf.setText(conf.toString());
            else
                lblConf.setText("-");

            TextView lblAut = (TextView)item.findViewById(R.id.aut);
            Long aut = data.get(position).getValoracionAutenticidad();
            if (aut != null)
                lblAut.setText(aut.toString());
            else
                lblAut.setText("-");

            TextView lblTraz = (TextView)item.findViewById(R.id.traz);
            Long traz = data.get(position).getValoracionTrazabilidad();
            if (traz != null)
                lblTraz.setText(traz.toString());
            else
                lblTraz.setText("-");

            return(item);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mOrientationListener.disable();
    }

    private boolean isPortrait(int orientation) {
        return (orientation >= (360 - 90) && orientation <= 360) || (orientation >= 0 && orientation <= 90);
    }


}
