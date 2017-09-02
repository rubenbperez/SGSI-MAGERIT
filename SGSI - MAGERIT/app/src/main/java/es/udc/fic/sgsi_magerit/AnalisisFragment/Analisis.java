package es.udc.fic.sgsi_magerit.AnalisisFragment;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

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
    //OrientationEventListener mOrientationListener;
    private Integer radioSelected;
    List<AnalisisInfoDTO> datas;
    ParametrizacionAnalisisDTO parametrizacion;
    Integer orientacion;

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
                    data = DataAnalisisGenerator.generarDatosAnalisisRiesgo(datas,parametrizacion);
                    adaptador.notifyDataSetChanged();
                }
                else {
                    radioSelected = Arrays.asList(GlobalConstants.RADIOBUTTON_ANALISIS).indexOf("Impacto");
                    data = DataAnalisisGenerator.generarDatosAnalisisImpacto(datas,parametrizacion);
                    adaptador.notifyDataSetChanged();
                }
            }
        });

        //ESTE NON RULA BEN
        /*OrientationEventListener mOrientationListener;
        mOrientationListener = new OrientationEventListener(this.getContext(),
                SensorManager.SENSOR_DELAY_UI) {
            @Override
            public void onOrientationChanged(int orientation) {
                Log.d("ORIENTACION", "Orientation changed to " + orientation);
                /*if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                    Log.d("ORIENTACION","Portrait");
                    orientacion = GlobalConstants.ORIENTACION_PORTRAIT;
                    adaptador.notifyDataSetChanged();
                } else {
                    Log.d("ORIENTACION", "landscape");
                    orientacion = GlobalConstants.ORIENTACION_LANDSCAPE;
                    adaptador.notifyDataSetChanged();
                }
            }
        };

        if (mOrientationListener.canDetectOrientation()) {
            mOrientationListener.enable();
        }*/

        SensorManager sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(new SensorEventListener() {
            int orientation=-1;

            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.values[1]<6.5 && event.values[1]>-6.5) {
                    if (orientation!=1) {
                        orientacion = GlobalConstants.ORIENTACION_LANDSCAPE;
                        adaptador.notifyDataSetChanged();
                        Log.d("Sensor", "Landscape");
                    }
                    orientation=1;
                } else {
                    if (orientation!=0) {
                        orientacion = GlobalConstants.ORIENTACION_PORTRAIT;
                        adaptador.notifyDataSetChanged();
                        Log.d("Sensor", "Portrait");
                    }
                    orientation=0;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // TODO Auto-generated method stub

            }
        }, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);


        datas = service.obtenerDatosAnalisis(idProyectoActivo);
        parametrizacion = service.obtenerParametrizacionProyectoParaAnalisis(idProyectoActivo);

        data = DataAnalisisGenerator.generarDatosAnalisisRiesgo(datas,parametrizacion);

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
            Long disp = data.get(position).getValoracionDisponibilidad();
            if (disp != null)
                if (orientacion == GlobalConstants.ORIENTACION_LANDSCAPE)
                    lblDisp.setText(DataAnalisisGenerator.formatIntegerLandscape(disp));
                else
                    lblDisp.setText(DataAnalisisGenerator.formatIntegerPortrait(disp));
            else
                lblDisp.setText("-");

            TextView lblInt = (TextView)item.findViewById(R.id.integ);
            Long integ = data.get(position).getValoracionIntegridad();
            if (integ != null)
                if (orientacion == GlobalConstants.ORIENTACION_LANDSCAPE)
                    lblInt.setText(DataAnalisisGenerator.formatIntegerLandscape(integ));
                else
                    lblInt.setText(DataAnalisisGenerator.formatIntegerPortrait(integ));
            else
                lblInt.setText("-");

            TextView lblConf = (TextView)item.findViewById(R.id.conf);
            Long conf = data.get(position).getValoracionConfidencialidad();
            if (conf != null)
                if (orientacion == GlobalConstants.ORIENTACION_LANDSCAPE)
                    lblConf.setText(DataAnalisisGenerator.formatIntegerLandscape(conf));
                else
                    lblConf.setText(DataAnalisisGenerator.formatIntegerPortrait(conf));
            else
                lblConf.setText("-");

            TextView lblAut = (TextView)item.findViewById(R.id.aut);
            Long aut = data.get(position).getValoracionAutenticidad();
            if (aut != null)
                if (orientacion == GlobalConstants.ORIENTACION_LANDSCAPE)
                    lblAut.setText(DataAnalisisGenerator.formatIntegerLandscape(aut));
            else
                lblAut.setText(DataAnalisisGenerator.formatIntegerPortrait(aut));
            else
                lblAut.setText("-");

            TextView lblTraz = (TextView)item.findViewById(R.id.traz);
            Long traz = data.get(position).getValoracionTrazabilidad();
            if (traz != null)
                if (orientacion == GlobalConstants.ORIENTACION_LANDSCAPE)
                    lblTraz.setText(DataAnalisisGenerator.formatIntegerLandscape(traz));
            else
                lblTraz.setText(DataAnalisisGenerator.formatIntegerPortrait(traz));
            else
                lblTraz.setText("-");

            return(item);
        }
    }

    private boolean isPortrait(int orientation) {
        return (orientation >= (270) && orientation <= 360) || (orientation >= 0 && orientation <= 90);
    }

}
