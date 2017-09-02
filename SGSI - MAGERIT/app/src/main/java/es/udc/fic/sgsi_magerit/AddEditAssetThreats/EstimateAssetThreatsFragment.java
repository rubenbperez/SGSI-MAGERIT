package es.udc.fic.sgsi_magerit.AddEditAssetThreats;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.udc.fic.sgsi_magerit.Model.Asset.Asset;
import es.udc.fic.sgsi_magerit.Model.Asset.AssetDTO;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.Model.ProjectSizing.ProjectSizingConstants;
import es.udc.fic.sgsi_magerit.Model.Threat.AssetThreatDTO;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;


public class EstimateAssetThreatsFragment extends Fragment {

    private ListView lstOpcionesActivos;
    private List<AssetThreatDTO> data;
    private ModelServiceImpl service;
    private Long idProyecto;
    private ThreatAssetAdapter threatAssetAdapter;
    List<Integer> idsParamDegradacion;
    List<Integer> idsParamProbabilidad;
    List<String> strParamDegradacion;
    List<String> strParamProbabilidad;
    private Long idActivo;

    public static EstimateAssetThreatsFragment newInstance() {
        EstimateAssetThreatsFragment fragment = new EstimateAssetThreatsFragment();
        return fragment;
    }


    protected List<AssetThreatDTO> getData() {
        return data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_estimate_threat_assets, container, false);
        service = new ModelServiceImpl(this.getActivity(), GlobalConstants.DATABASE_NAME, 1);
        Bundle args = getArguments();
        if (args != null) {
            this.idProyecto = args.getLong("idProyecto");
            this.idActivo = args.getLong("idActivo");
        }

        idsParamDegradacion = service.obtenerParametrizacionesActivadas(
                idProyecto, ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_IMPACTO);

        idsParamProbabilidad = service.obtenerParametrizacionesActivadas(
                idProyecto, ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_VULNERABILIDAD);

        strParamDegradacion = new ArrayList<String>();
        strParamDegradacion.add("Seleccione");
        for (Integer i:idsParamDegradacion) {
            strParamDegradacion.add(GlobalConstants.ID_TIPOS[i-1]);
        }

        strParamProbabilidad = new ArrayList<String>();
        strParamProbabilidad.add("Seleccione");
        for (Integer i:idsParamProbabilidad) {
            strParamProbabilidad.add(GlobalConstants.ID_TIPOS[i-1]);
        }

        data = service.obtenerAmenazasDeActivo(idActivo, idProyecto);
        threatAssetAdapter = new ThreatAssetAdapter(this.getContext(), data);
        lstOpcionesActivos = (ListView) view.findViewById(R.id.LstOpciones);
        lstOpcionesActivos.setAdapter(threatAssetAdapter);

        return view;
    }

    public class ThreatAssetAdapter extends ArrayAdapter<AssetThreatDTO> {

        private Spinner spinnerDisp1;
        private ArrayAdapter<String> spinnerAdapterDisp1;
        private Spinner spinnerDisp2;
        private ArrayAdapter<String> spinnerAdapterDisp2;
        private Spinner spinnerInt1;
        private ArrayAdapter<String> spinnerAdapterInt1;
        private Spinner spinnerInt2;
        private ArrayAdapter<String> spinnerAdapterInt2;
        private Spinner spinnerConf1;
        private ArrayAdapter<String> spinnerAdapterConf1;
        private Spinner spinnerConf2;
        private ArrayAdapter<String> spinnerAdapterConf2;
        private Spinner spinnerAut1;
        private ArrayAdapter<String> spinnerAdapterAut1;
        private Spinner spinnerAut2;
        private ArrayAdapter<String> spinnerAdapterAut2;
        private Spinner spinnerTraz1;
        private ArrayAdapter<String> spinnerAdapterTraz1;
        private Spinner spinnerTraz2;
        private ArrayAdapter<String> spinnerAdapterTraz2;

        public ThreatAssetAdapter(Context context, List<AssetThreatDTO> data) {
            super(context, android.R.layout.simple_list_item_single_choice, data);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());

            View item = inflater.inflate(R.layout.lisitem_threat_assets_estimation, null);
            TextView lblAssetName = (TextView) item.findViewById(R.id.asset);

            Long idListaTipoAmenaza = data.get(position).getIdListaTipoAmenaza();
            Long idTipoAmenaza = data.get(position).getIdTipoAmenaza();
            String nombreAmenazaMostrar = "";

            switch (idListaTipoAmenaza.intValue()) {
                case 0:
                    nombreAmenazaMostrar = GlobalConstants.AMENAZAS_TIPO_DESASTRES_NATURALES[idTipoAmenaza.intValue()];
                    break;
                case 1:
                    nombreAmenazaMostrar = GlobalConstants.AMENAZAS_TIPO_ORIGEN_INDUSTRIAL[idTipoAmenaza.intValue()];
                    break;
                case 2:
                    nombreAmenazaMostrar = GlobalConstants.AMENAZAS_TIPO_ERRORES_FALLOS_NO_INTENCIONADOS[idTipoAmenaza.intValue()];
                    break;
                case 3:
                    nombreAmenazaMostrar = GlobalConstants.AMENAZAS_TIPO_ATAQUES_DELIBERADOS[idTipoAmenaza.intValue()];
                    break;
            }

            lblAssetName.setText(nombreAmenazaMostrar);

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

            TextView tv = (TextView) item.findViewById(R.id.asset);

            tv.setOnClickListener(new View.OnClickListener() {
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

            spinnerAdapterDisp1 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamDegradacion);
            spinnerAdapterDisp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDisp1.setAdapter((SpinnerAdapter) spinnerAdapterDisp1);
            if (data.get(position).getIdDegradacionDisponibilidad() != null && data.get(position).getIdDegradacionDisponibilidad() <= strParamDegradacion.size())
                spinnerDisp1.setSelection(data.get(position).getIdDegradacionDisponibilidad()+1);
            spinnerDisp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    if (index != 0)
                        data.get(position).setIdDegradacionDisponibilidad(index);
                    else
                        data.get(position).setIdDegradacionDisponibilidad(null);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerAdapterDisp2 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamProbabilidad);
            spinnerAdapterDisp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDisp2.setAdapter((SpinnerAdapter) spinnerAdapterDisp2);
            if (data.get(position).getIdProbabilidadDisponibilidad() != null && data.get(position).getIdProbabilidadDisponibilidad() <= strParamProbabilidad.size())
                spinnerDisp2.setSelection(data.get(position).getIdProbabilidadDisponibilidad()+1);
            spinnerDisp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    if (index != 0)
                        data.get(position).setIdProbabilidadDisponibilidad(index-1);
                    else
                        data.get(position).setIdProbabilidadDisponibilidad(null);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            spinnerInt1 = (Spinner) item.findViewById(R.id.spinnerIntegridad);
            spinnerInt2 = (Spinner) item.findViewById(R.id.spinnerIntegridad2);

            spinnerAdapterInt1 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamDegradacion);
            spinnerAdapterInt1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerInt1.setAdapter((SpinnerAdapter) spinnerAdapterInt1);
            if (data.get(position).getIdDegradacionIntegridad() != null && data.get(position).getIdDegradacionIntegridad() <= strParamDegradacion.size())
                spinnerInt1.setSelection(data.get(position).getIdDegradacionIntegridad()+1);
            spinnerInt1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    if (index != 0)
                        data.get(position).setIdDegradacionIntegridad(index-1);
                    else
                        data.get(position).setIdDegradacionIntegridad(null);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerAdapterInt2 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamProbabilidad);
            spinnerAdapterInt2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerInt2.setAdapter((SpinnerAdapter) spinnerAdapterInt2);
            if (data.get(position).getIdProbabilidadIntegridad() != null && data.get(position).getIdProbabilidadIntegridad() <= strParamProbabilidad.size())
                spinnerInt2.setSelection(data.get(position).getIdProbabilidadIntegridad()+1);
            spinnerInt2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    if (index != 0)
                        data.get(position).setIdProbabilidadIntegridad(index-1);
                    else
                        data.get(position).setIdProbabilidadIntegridad(null);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            spinnerConf1 = (Spinner) item.findViewById(R.id.spinnerConfidencialidad);
            spinnerConf2 = (Spinner) item.findViewById(R.id.spinnerConfidencialidad2);

            spinnerAdapterConf1 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamDegradacion);
            spinnerAdapterConf1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerConf1.setAdapter((SpinnerAdapter) spinnerAdapterConf1);
            if (data.get(position).getIdDegradacionConfidencialidad() != null  && data.get(position).getIdDegradacionConfidencialidad() <= strParamDegradacion.size())
                spinnerConf1.setSelection(data.get(position).getIdDegradacionConfidencialidad()+1);
            spinnerConf1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    if (index != 0)
                        data.get(position).setIdDegradacionConfidencialidad(index-1);
                    else
                        data.get(position).setIdDegradacionConfidencialidad(null);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerAdapterConf2 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamProbabilidad);
            spinnerAdapterConf2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerConf2.setAdapter((SpinnerAdapter) spinnerAdapterConf2);
            if (data.get(position).getIdProbabilidadConfidencialidad() != null && data.get(position).getIdProbabilidadConfidencialidad() <= strParamProbabilidad.size())
                spinnerConf2.setSelection(data.get(position).getIdProbabilidadConfidencialidad()+1);
            spinnerConf2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    if (index != 0)
                        data.get(position).setIdProbabilidadConfidencialidad(index-1);
                    else
                        data.get(position).setIdProbabilidadConfidencialidad(null);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerAut1 = (Spinner) item.findViewById(R.id.spinnerAutenticidad);
            spinnerAut2 = (Spinner) item.findViewById(R.id.spinnerAutenticidad2);

            spinnerAdapterAut1 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamDegradacion);
            spinnerAdapterAut1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAut1.setAdapter((SpinnerAdapter) spinnerAdapterAut1);
            if(data.get(position).getIdDegradacionAutenticidad() != null  && data.get(position).getIdDegradacionAutenticidad() <= strParamDegradacion.size())
                spinnerAut1.setSelection(data.get(position).getIdDegradacionAutenticidad()+1);
            spinnerAut1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    if (index != 0)
                        data.get(position).setIdDegradacionAutenticidad(index-1);
                    else
                        data.get(position).setIdDegradacionAutenticidad(null);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerAdapterAut2 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamProbabilidad);
            spinnerAdapterAut2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAut2.setAdapter((SpinnerAdapter) spinnerAdapterAut2);
            if (data.get(position).getIdProbabilidadAutenticidad() != null && data.get(position).getIdProbabilidadAutenticidad() <= strParamProbabilidad.size())
                spinnerAut2.setSelection(data.get(position).getIdProbabilidadAutenticidad()+1);
            spinnerAut2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    if (index != 0)
                        data.get(position).setIdProbabilidadAutenticidad(index-1);
                    else
                        data.get(position).setIdProbabilidadAutenticidad(null);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            spinnerTraz1 = (Spinner) item.findViewById(R.id.spinnerTrazabilidad);
            spinnerTraz2 = (Spinner) item.findViewById(R.id.spinnerTrazabilidad2);

            spinnerAdapterTraz1 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamDegradacion);
            spinnerAdapterTraz1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTraz1.setAdapter((SpinnerAdapter) spinnerAdapterTraz1);
            if (data.get(position).getIdDegradacionTrazabilidad()!= null && data.get(position).getIdDegradacionTrazabilidad() <= strParamDegradacion.size())
                spinnerTraz1.setSelection(data.get(position).getIdDegradacionTrazabilidad()+1);
            spinnerTraz1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    if (index != 0)
                        data.get(position).setIdDegradacionTrazabilidad(index-1);
                    else
                        data.get(position).setIdDegradacionTrazabilidad(null);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerAdapterTraz2 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamProbabilidad);
            spinnerAdapterTraz2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTraz2.setAdapter((SpinnerAdapter) spinnerAdapterTraz2);
            if (data.get(position).getIdProbabilidadTrazabilidad() != null  && data.get(position).getIdProbabilidadTrazabilidad() <= strParamProbabilidad.size())
                spinnerTraz2.setSelection(data.get(position).getIdProbabilidadTrazabilidad()+1);
            spinnerTraz2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    if (index != 0)
                        data.get(position).setIdProbabilidadTrazabilidad(index-1);
                    else
                        data.get(position).setIdProbabilidadTrazabilidad(null);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            return (item);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            recargarInfo();
        } else
            Log.d("MyFragment", "Fragment is not visible.");
    }


    protected void recargarInfo() {
        Log.d("MyFragment", "Fragment is visible.");
        List<AssetThreatDTO> listaPrimerFragmento = leerDatosFragmento1();

        //SI la lista del fragmento está vacía, la lista de estimación tmb
        if (listaPrimerFragmento.isEmpty()) {
            data.clear();
            threatAssetAdapter.notifyDataSetChanged();
            return;
        }

        // Si la lista de estimación está vacía, se añaden todos.
        if (data.isEmpty()) {
            data.addAll(listaPrimerFragmento);
            threatAssetAdapter.notifyDataSetChanged();
            return;
        }

        List<AssetThreatDTO> listaBorrar = new ArrayList<AssetThreatDTO>();

        Boolean flag = false;
        for (AssetThreatDTO threat: data)
        {
            flag = false;
            for (AssetThreatDTO threat2: listaPrimerFragmento) {

                if (threat.getIdListaTipoAmenaza() == threat2.getIdListaTipoAmenaza() &&
                        threat.getIdTipoAmenaza() == threat2.getIdTipoAmenaza()) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                listaBorrar.add(threat);
            }
        }

        data.removeAll(listaBorrar);

        for (AssetThreatDTO threat2: listaPrimerFragmento)
        {
            flag = false;
            for (AssetThreatDTO threat: data) {

                if (threat.getIdListaTipoAmenaza() == threat2.getIdListaTipoAmenaza() &&
                        threat.getIdTipoAmenaza() == threat2.getIdTipoAmenaza()) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                data.add(threat2);
            }
        }
        threatAssetAdapter.notifyDataSetChanged();
    }

    private List<AssetThreatDTO> leerDatosFragmento1() {

        Asset asset = null;
        AddEditAssetThreatsActivity.AddEditAssetThreatsFragmentPagerAdapter adapter;
        AddEditAssetThreatsActivity act = (AddEditAssetThreatsActivity) getActivity();
        adapter = (AddEditAssetThreatsActivity.AddEditAssetThreatsFragmentPagerAdapter) act.getViewPager().getAdapter();
        IdentifyThreatsAssetFragment fr0 = (IdentifyThreatsAssetFragment) adapter.instantiateItem(act.getViewPager(),0);

        ListView lstOpcDesastresNaturales= fr0.getLstOpcionesDesastresNaturales();
        ListView lstOpcOrigenIndustrial = fr0.getLstOpcionesOrigenIndustrial();
        ListView lstOpcErroresFallos = fr0.getLstOpcionesErroresFallos();
        ListView lstOpcAtaquesDeliberados = fr0.getLstOpcionesAtaquesDeliberados();

        try {
            asset = service.obtenerActivo(idActivo);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<AssetThreatDTO> datosValidos = new ArrayList<AssetThreatDTO>();
        datosValidos.addAll(getCheckedItems(lstOpcDesastresNaturales,asset,0));
        datosValidos.addAll(getCheckedItems(lstOpcOrigenIndustrial,asset,1));
        datosValidos.addAll(getCheckedItems(lstOpcErroresFallos,asset,2));
        datosValidos.addAll(getCheckedItems(lstOpcAtaquesDeliberados,asset,3));

        return datosValidos;
    }



    private List<AssetThreatDTO> getCheckedItems (ListView lst, Asset asset, Integer idListaTipoAmenaza) {
        SparseBooleanArray checked = lst.getCheckedItemPositions();
        List<AssetThreatDTO> elementosMarcados = new ArrayList<AssetThreatDTO>();
        for (int i = 0; i < lst.getAdapter().getCount(); i++) {
            if (checked.get(i)) {
                AssetThreatDTO assetThreat = new AssetThreatDTO(null, asset.getIdActivo(), asset.getIdProyecto(),
                        asset.getCodigoActivo(), asset.getNombreActivo(), (long)idListaTipoAmenaza, (long)i, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false);
                elementosMarcados.add(assetThreat);
            }
        }
        return elementosMarcados;
    }

}



