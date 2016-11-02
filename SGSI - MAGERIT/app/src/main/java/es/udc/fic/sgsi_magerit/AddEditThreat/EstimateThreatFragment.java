package es.udc.fic.sgsi_magerit.AddEditThreat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.udc.fic.sgsi_magerit.Model.Asset.AssetDTO;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.Model.ProjectSizing.ParametrizacionDTO;
import es.udc.fic.sgsi_magerit.Model.ProjectSizing.ProjectSizingConstants;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;

public class EstimateThreatFragment extends Fragment {

    private ListView lstOpcionesActivos;
    private List<AssetThreatDTO> data;
    private ModelServiceImpl service;
    private ViewPager viewPager;
    private Long idProyecto;
    private AssetAdapter assetAdapter;
    List<Integer> idsParamDegradacion;
    List<Integer> idsParamProbabilidad;
    List<String> strParamDegradacion;
    List<String> strParamProbabilidad;


    public EstimateThreatFragment() {
    }

    public static EstimateThreatFragment newInstance() {
        EstimateThreatFragment fragment = new EstimateThreatFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estimate_threat_assets, container, false);
        service = new ModelServiceImpl(this.getActivity(), GlobalConstants.DATABASE_NAME, 1);
        Bundle args = getArguments();
        if (args != null) {
            this.idProyecto = args.getLong("idProyecto");
        }

        idsParamDegradacion = service.obtenerParametrizacionesActivadas(
                idProyecto, ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_IMPACTO);

        idsParamProbabilidad = service.obtenerParametrizacionesActivadas(
                idProyecto, ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_VULNERABILIDAD);

        strParamDegradacion = new ArrayList<String>();
        strParamDegradacion.add("Seleccione Degradación");
        for (Integer i:idsParamDegradacion) {
            strParamDegradacion.add(GlobalConstants.ID_TIPOS[i-1]);
        }

        strParamProbabilidad = new ArrayList<String>();
        strParamProbabilidad.add("Seleccione Probabilidad");
        for (Integer i:idsParamProbabilidad) {
            strParamProbabilidad.add(GlobalConstants.ID_TIPOS[i-1]);
        }

        data = new ArrayList<AssetThreatDTO>();
        data.addAll(leerFichero());
        assetAdapter = new AssetAdapter(this.getContext(), data);
        lstOpcionesActivos = (ListView) view.findViewById(R.id.LstOpciones);
        lstOpcionesActivos.setAdapter(assetAdapter);

        return view;
    }

    public class AssetAdapter extends ArrayAdapter<AssetThreatDTO> {

        private Spinner spinnerDisp1;
        private ArrayAdapter<String> spinnerAdapterDisp1;
        private Spinner spinnerDisp2;
        private ArrayAdapter<String> spinnerAdapterDisp2;
        private CheckBox cbDisp;
        private Spinner spinnerInt1;
        private ArrayAdapter<String> spinnerAdapterInt1;
        private Spinner spinnerInt2;
        private ArrayAdapter<String> spinnerAdapterInt2;
        private CheckBox cbInt;
        private Spinner spinnerConf1;
        private ArrayAdapter<String> spinnerAdapterConf1;
        private Spinner spinnerConf2;
        private ArrayAdapter<String> spinnerAdapterConf2;
        private CheckBox cbConf;
        private Spinner spinnerAut1;
        private ArrayAdapter<String> spinnerAdapterAut1;
        private Spinner spinnerAut2;
        private ArrayAdapter<String> spinnerAdapterAut2;
        private CheckBox cbAut;
        private Spinner spinnerTraz1;
        private ArrayAdapter<String> spinnerAdapterTraz1;
        private Spinner spinnerTraz2;
        private ArrayAdapter<String> spinnerAdapterTraz2;
        private CheckBox cbTraz;

        public AssetAdapter(Context context, List<AssetThreatDTO> data) {
            super(context, android.R.layout.simple_list_item_single_choice, data);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());

            View item = inflater.inflate(R.layout.lisitem_threat_assets_estimation, null);
            TextView lblAssetName = (TextView) item.findViewById(R.id.asset);
            lblAssetName.setText("[" + data.get(position).getCodigoActivo() + "]" + " " + data.get(position).getNombreActivo());

            final ImageButton rc = (ImageButton) item.findViewById(R.id.icon);
            final TableLayout tb = (TableLayout) item.findViewById(R.id.tabla);

            if (data.get(position).getVisible())
            {
                rc.setImageResource(R.drawable.ic_less);
                tb.setVisibility(View.VISIBLE);
            } else {
                rc.setImageResource(R.drawable.ic_more);
                tb.setVisibility(View.GONE);
            }
            rc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tb.getVisibility() == View.GONE) {
                        rc.setImageResource(R.drawable.ic_less);
                        tb.setVisibility(View.VISIBLE);
                        data.get(position).setVisible(true);
                    } else {
                        rc.setImageResource(R.drawable.ic_more);
                        tb.setVisibility(View.GONE);
                        data.get(position).setVisible(false);
                    }
                }
            });

            spinnerDisp1 = (Spinner) item.findViewById(R.id.spinnerDisponibilidad);
            spinnerDisp2 = (Spinner) item.findViewById(R.id.spinnerDisponibilidad2);
            cbDisp = (CheckBox) item.findViewById(R.id.disp);

            spinnerAdapterDisp1 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamDegradacion);
            spinnerAdapterDisp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDisp1.setAdapter((SpinnerAdapter) spinnerAdapterDisp1);

            cbDisp.setChecked(data.get(position).getActivadoDisponibilidad());

            spinnerAdapterDisp2 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamProbabilidad);
            spinnerAdapterDisp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDisp2.setAdapter((SpinnerAdapter) spinnerAdapterDisp2);
            
            spinnerInt1 = (Spinner) item.findViewById(R.id.spinnerIntegridad);
            spinnerInt2 = (Spinner) item.findViewById(R.id.spinnerIntegridad2);
            cbInt = (CheckBox) item.findViewById(R.id.integ);

            spinnerAdapterInt1 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamDegradacion);
            spinnerAdapterInt1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerInt1.setAdapter((SpinnerAdapter) spinnerAdapterInt1);

            spinnerAdapterInt2 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamProbabilidad);
            spinnerAdapterInt2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerInt2.setAdapter((SpinnerAdapter) spinnerAdapterInt2);

            cbInt.setChecked(data.get(position).getActivadoIntegridad());

            spinnerConf1 = (Spinner) item.findViewById(R.id.spinnerConfidencialidad);
            spinnerConf2 = (Spinner) item.findViewById(R.id.spinnerConfidencialidad2);
            cbConf = (CheckBox) item.findViewById(R.id.confidenc);

            spinnerAdapterConf1 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamDegradacion);
            spinnerAdapterConf1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerConf1.setAdapter((SpinnerAdapter) spinnerAdapterConf1);

            spinnerAdapterConf2 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamProbabilidad);
            spinnerAdapterConf2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerConf2.setAdapter((SpinnerAdapter) spinnerAdapterConf2);

            cbConf.setChecked(data.get(position).getActivadoConfidencialidad());

            spinnerAut1 = (Spinner) item.findViewById(R.id.spinnerAutenticidad);
            spinnerAut2 = (Spinner) item.findViewById(R.id.spinnerAutenticidad2);
            cbAut = (CheckBox) item.findViewById(R.id.autent);

            spinnerAdapterAut1 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamDegradacion);
            spinnerAdapterAut1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAut1.setAdapter((SpinnerAdapter) spinnerAdapterAut1);

            spinnerAdapterAut2 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamProbabilidad);
            spinnerAdapterAut2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAut2.setAdapter((SpinnerAdapter) spinnerAdapterAut2);

            cbAut.setChecked(data.get(position).getActivadoAutenticidad());

            spinnerTraz1 = (Spinner) item.findViewById(R.id.spinnerTrazabilidad);
            spinnerTraz2 = (Spinner) item.findViewById(R.id.spinnerTrazabilidad2);
            cbTraz = (CheckBox) item.findViewById(R.id.traza);

            spinnerAdapterTraz1 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamDegradacion);
            spinnerAdapterTraz1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTraz1.setAdapter((SpinnerAdapter) spinnerAdapterTraz1);

            spinnerAdapterTraz2 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamProbabilidad);
            spinnerAdapterTraz2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTraz2.setAdapter((SpinnerAdapter) spinnerAdapterTraz2);

            cbTraz.setChecked(data.get(position).getActivadoTrazabilidad());

            return (item);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            Log.d("MyFragment", "Fragment is visible.");
            data.clear();
            data.addAll(leerFichero());
            assetAdapter.notifyDataSetChanged();
        } else
            Log.d("MyFragment", "Fragment is not visible.");
    }

    private List<AssetThreatDTO> leerFichero() {
        List<AssetDTO> datos = new ArrayList<AssetDTO>();
        List<AssetThreatDTO> datosValidos = new ArrayList<AssetThreatDTO>();
        try {
            BufferedReader fin =
                    new BufferedReader(
                            new InputStreamReader(
                                    getActivity().openFileInput("threats.tmp")));
            String texto = fin.readLine();
            fin.close();
            List<Long> ids = new ArrayList<Long>();
            List<String> items = Arrays.asList(texto.split(","));
            for (String s : items) ids.add(Long.valueOf(s));
            datos = service.obtenerActivosPorId(idProyecto, ids);

            for (AssetDTO a : datos)
            {
                AssetThreatDTO assetThreat = new AssetThreatDTO(a.getIdActivo(), a.getIdProyecto(), a.getCodigoActivo(),
                        a.getNombreActivo(), 1, 1, false, 1, 1, false, 1, 1, false, 1, 1, false, 1, 1, false, false);
                datosValidos.add(assetThreat);
            }

        } catch (Exception ex) {
            Log.e("Ficheros", "Error al leer fichero desde memoria interna");
        }
        return datosValidos;
    }

}
