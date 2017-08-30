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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.udc.fic.sgsi_magerit.Model.Analisis.AnalisisDTO;
import es.udc.fic.sgsi_magerit.Model.Analisis.AnalisisInfoDTO;
import es.udc.fic.sgsi_magerit.Model.Analisis.ParametrizacionAnalisisDTO;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.DataAnalisisGenerator;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;

public class Analisis extends Fragment {

    private ListView lstOpciones;
    private List<AnalisisDTO> data;
    private ModelServiceImpl service;
    private AnalisisAdapter adaptador;
    private long idProyectoActivo;
    OrientationEventListener mOrientationListener;
    private Integer radioSelected;


    public Analisis() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analisis, container, false);
        service = new ModelServiceImpl(getContext(), GlobalConstants.DATABASE_NAME, 1);
        idProyectoActivo = service.obtenerIdProyectoActivo();


        radioSelected = Arrays.asList(GlobalConstants.RADIOBUTTON_ANALISIS).indexOf("Riesgo");
        RadioGroup rGroup = (RadioGroup)view.findViewById(R.id.toggle);
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.riesgo) {
                    radioSelected = Arrays.asList(GlobalConstants.RADIOBUTTON_ANALISIS).indexOf("Riesgo");
                }
                else {
                    radioSelected = Arrays.asList(GlobalConstants.RADIOBUTTON_ANALISIS).indexOf("Impacto");
                }
            }
        });


        OrientationEventListener mOrientationListener;
        mOrientationListener = new OrientationEventListener(this.getContext(),
                SensorManager.SENSOR_DELAY_UI) {
            @Override
            public void onOrientationChanged(int orientation) {
                Log.d("ORIENTACION", "Orientation changed to " + orientation);
                if (isPortrait(orientation)) {
                    Log.d("ORIENTACION","Portrait");
                } else {
                    Log.d("ORIENTACION", "landscape");
                }
            }
        };

        List<AnalisisInfoDTO> datas = service.obtenerDatosAnalisis(idProyectoActivo);
        ParametrizacionAnalisisDTO parametrizacion = service.obtenerParametrizacionProyectoParaAnalisis(idProyectoActivo);

        data = DataAnalisisGenerator.generarDatosAnalisisRiesgo(datas,parametrizacion);
        

        if (mOrientationListener.canDetectOrientation()) {
            mOrientationListener.enable();
        }


        /*AnalisisDTO a1 = new AnalisisDTO(1,1,"hola","hl",100000,500000,1000000,5000,null);
        AnalisisDTO a2 = new AnalisisDTO(1,1,"hey","brrr",3500,861000,35000,null,null);
        AnalisisDTO a3 = new AnalisisDTO(1,1,"acff","tuu",null,null,500000,null,null);

        data = new ArrayList<>();
        data.add(a1);
        data.add(a2);
        data.add(a3);*/

        adaptador =
                new AnalisisAdapter(this.getContext(), data);

        lstOpciones = (ListView) view.findViewById(R.id.LstOpciones);
        lstOpciones.setAdapter(adaptador);

        return view;
    }

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
            Integer disp = data.get(position).getValoracionDisponibilidad();
            if (disp != null)
                lblDisp.setText(disp.toString());
            else
                lblDisp.setText("-");

            TextView lblInt = (TextView)item.findViewById(R.id.integ);
            Integer integ = data.get(position).getValoracionIntegridad();
            if (integ != null)
                lblInt.setText(integ.toString());
            else
                lblInt.setText("-");

            TextView lblConf = (TextView)item.findViewById(R.id.conf);
            Integer conf = data.get(position).getValoracionConfidencialidad();
            if (conf != null)
                lblConf.setText(conf.toString());
            else
                lblConf.setText("-");

            TextView lblAut = (TextView)item.findViewById(R.id.aut);
            Integer aut = data.get(position).getValoracionAutenticidad();
            if (aut != null)
                lblAut.setText(aut.toString());
            else
                lblAut.setText("-");

            TextView lblTraz = (TextView)item.findViewById(R.id.traz);
            Integer traz = data.get(position).getValoracionTrazabilidad();
            if (traz != null)
                lblTraz.setText(traz.toString());
            else
                lblTraz.setText("-");

            return(item);
        }
    }

    private boolean isPortrait(int orientation) {
        return (orientation >= (270) && orientation <= 360) || (orientation >= 0 && orientation <= 90);
    }


}
