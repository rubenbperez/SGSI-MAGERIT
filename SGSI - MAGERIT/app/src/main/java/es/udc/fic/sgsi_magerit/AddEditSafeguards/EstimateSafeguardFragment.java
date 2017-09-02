package es.udc.fic.sgsi_magerit.AddEditSafeguards;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.Model.ProjectSizing.ProjectSizingConstants;
import es.udc.fic.sgsi_magerit.Model.Safeguard.AssetThreatInfoDTO;
import es.udc.fic.sgsi_magerit.Model.Safeguard.AssetsSafeguardDTO;
import es.udc.fic.sgsi_magerit.Model.Safeguard.Safeguard;
import es.udc.fic.sgsi_magerit.Model.Safeguard.ThreatSafeguardDTO;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;

public class EstimateSafeguardFragment extends Fragment {

    private ModelServiceImpl service;
    private Long idProyecto;
    private Long idListaTipoSalvaguarda;
    private Long idTipoSalvaguarda;
    List<AssetThreatInfoDTO> expandableListTitle;
    ExpandableListAdapter expandableListAdapter;
    HashMap<AssetsSafeguardDTO, List<ThreatSafeguardDTO>> expandableListFr2;
    List<AssetsSafeguardDTO> expandableTitleFr2;
    HashMap<AssetThreatInfoDTO, List<Safeguard>> expandableListDetail;
    ExpandableListView expandableListView;
    List<Integer> idsParamCtrlSeguridad;
    List<String> strParamCtrlSeguridad;
    List<String> strParamTProteccion;

    public HashMap<AssetThreatInfoDTO, List<Safeguard>> getData() {
        return expandableListDetail;
    }

    public static EstimateSafeguardFragment newInstance() {
        EstimateSafeguardFragment fragment = new EstimateSafeguardFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_estimate_safeguard, container, false);

        service = new ModelServiceImpl(this.getActivity(), GlobalConstants.DATABASE_NAME, 1);
        Bundle args = getArguments();
        if (args != null) {
            this.idProyecto = args.getLong("idProyecto");
            this.idListaTipoSalvaguarda = args.getLong("idListaTipoSalvaguarda", GlobalConstants.NULL_ID_LISTA_TIPO_SALVAGUARDA);
            this.idTipoSalvaguarda = args.getLong("idTipoSalvaguarda", GlobalConstants.NULL_ID_LISTA_TIPO_SALVAGUARDA);
        }

        //Este fragmento se carga antes que el fragmento 2 de modo que leemos directamente de BD
        if (idListaTipoSalvaguarda != GlobalConstants.NULL_ID_LISTA_TIPO_SALVAGUARDA &&
                idTipoSalvaguarda != GlobalConstants.NULL_ID_LISTA_TIPO_SALVAGUARDA) {
            try {
                expandableListDetail = service.obtenerInfoSalvaguardasPorIdTipo(idProyecto, idListaTipoSalvaguarda, idTipoSalvaguarda);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            expandableListTitle = new ArrayList<AssetThreatInfoDTO>(expandableListDetail.keySet());
            expandableListAdapter = new CustomExpandableListAdapter(this.getContext(), expandableListTitle, expandableListDetail);
            expandableListView = (android.widget.ExpandableListView) view.findViewById(R.id.expandableList);
            expandableListView.setAdapter(expandableListAdapter);
        } else {

            //Antiguamente intentabamos leerlo
            //leerDatosFragmento2();
            expandableListDetail = cargarDatosFr2();
            expandableListTitle = new ArrayList<AssetThreatInfoDTO>(expandableListDetail.keySet());
            expandableListAdapter = new CustomExpandableListAdapter(this.getContext(), expandableListTitle, expandableListDetail);
            expandableListView = (android.widget.ExpandableListView) view.findViewById(R.id.expandableList);
            expandableListView.setAdapter(expandableListAdapter);
        }
        idsParamCtrlSeguridad = service.obtenerParametrizacionesActivadas(idProyecto,
                ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_CONTROLSEGURIDAD);

        strParamCtrlSeguridad = new ArrayList<String>();
        strParamCtrlSeguridad.add("Seleccione");
        for (Integer i:idsParamCtrlSeguridad) {
            strParamCtrlSeguridad.add(GlobalConstants.ID_TIPOS[i-1]);
        }
        strParamTProteccion = new ArrayList<String>();
        strParamTProteccion.add("Seleccione");
        for (String e: GlobalConstants.TIPO_PROTECCION)
            strParamTProteccion.add(e);

        return view;
    }

    public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

        private Spinner spinnerDisp;
        private ArrayAdapter<String> spinnerAdapterDisp;
        private Spinner spinnerInt;
        private ArrayAdapter<String> spinnerAdapterInt;
        private Spinner spinnerConf;
        private ArrayAdapter<String> spinnerAdapterConf;
        private Spinner spinnerAut;
        private ArrayAdapter<String> spinnerAdapterAut;
        private Spinner spinnerTraz;
        private ArrayAdapter<String> spinnerAdapterTraz;
        private Spinner spinnerTProtec;
        private ArrayAdapter<String> spinnerAdapterTProtec;

        private Context context;
        private List<AssetThreatInfoDTO> expandableListTitle;
        private HashMap<AssetThreatInfoDTO, List<Safeguard>> expandableListDetailAdapter;

        public CustomExpandableListAdapter(Context context, List<AssetThreatInfoDTO> expandableListTitle,
                                           HashMap<AssetThreatInfoDTO, List<Safeguard>> expandableListDetail) {
            this.context = context;
            this.expandableListTitle = expandableListTitle;
            this.expandableListDetailAdapter = expandableListDetail;
        }

        @Override
        public Object getChild(int listPosition, int expandedListPosition) {
            return this.expandableListDetailAdapter.get(this.expandableListTitle.get(listPosition))
                    .get(expandedListPosition);
        }

        @Override
        public long getChildId(int listPosition, int expandedListPosition) {
            return expandedListPosition;
        }

        @Override
        public View getChildView(final int listPosition, final int expandedListPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            final Safeguard expandedList = (Safeguard) getChild(listPosition, expandedListPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.childitem_estimate_safeguard, null);
            }

            spinnerDisp = (Spinner) convertView.findViewById(R.id.spinnerDisponibilidad);
            spinnerAdapterDisp =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamCtrlSeguridad);
            spinnerAdapterDisp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDisp.setAdapter((SpinnerAdapter) spinnerAdapterDisp);
            spinnerDisp.setAdapter((SpinnerAdapter) spinnerAdapterDisp);

            if (expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                    expandedListPosition).getIdControlSeguridadDisponibilidad() != null  && expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                    expandedListPosition).getIdControlSeguridadDisponibilidad() <= strParamCtrlSeguridad.size())
                spinnerDisp.setSelection(expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                    expandedListPosition).getIdControlSeguridadDisponibilidad()+1);

            spinnerDisp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    if (index != 0)
                    expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                            expandedListPosition).setIdControlSeguridadDisponibilidad(index-1);
                    else
                        expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                                expandedListPosition).setIdControlSeguridadDisponibilidad(null);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerInt = (Spinner) convertView.findViewById(R.id.spinnerIntegridad);
            spinnerAdapterInt =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamCtrlSeguridad);
            spinnerAdapterInt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerInt.setAdapter((SpinnerAdapter) spinnerAdapterInt);

            if (expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                    expandedListPosition).getIdControlSeguridadIntegridad() != null && expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                    expandedListPosition).getIdControlSeguridadIntegridad() <= strParamCtrlSeguridad.size())
                spinnerInt.setSelection(expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                        expandedListPosition).getIdControlSeguridadIntegridad()+1);

            spinnerInt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    if (index != 0)
                        expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                                expandedListPosition).setIdControlSeguridadIntegridad(index-1);
                    else
                        expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                                expandedListPosition).setIdControlSeguridadIntegridad(null);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerConf = (Spinner) convertView.findViewById(R.id.spinnerConfidencialidad);
            spinnerAdapterConf =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamCtrlSeguridad);
            spinnerAdapterConf.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerConf.setAdapter((SpinnerAdapter) spinnerAdapterConf);

            if (expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                    expandedListPosition).getIdControlSeguridadConfidencialidad() != null  && expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                    expandedListPosition).getIdControlSeguridadConfidencialidad() <= strParamCtrlSeguridad.size())
                spinnerConf.setSelection(expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                expandedListPosition).getIdControlSeguridadConfidencialidad()+1);

            spinnerConf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    if (index != 0)
                    expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                            expandedListPosition).setIdControlSeguridadConfidencialidad(index-1);
                    else
                        expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                                expandedListPosition).setIdControlSeguridadConfidencialidad(null);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerAut = (Spinner) convertView.findViewById(R.id.spinnerAutenticidad);
            spinnerAdapterAut =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamCtrlSeguridad);
            spinnerAdapterAut.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAut.setAdapter((SpinnerAdapter) spinnerAdapterAut);

            if (expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                    expandedListPosition).getIdControlSeguridadAutenticidad() != null  && expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                    expandedListPosition).getIdControlSeguridadAutenticidad() <= strParamCtrlSeguridad.size())
                spinnerAut.setSelection(expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                expandedListPosition).getIdControlSeguridadAutenticidad()+1);

            spinnerAut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    if (index != 0)
                        expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                                expandedListPosition).setIdControlSeguridadAutenticidad(index-1);
                    else
                        expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                                expandedListPosition).setIdControlSeguridadAutenticidad(null);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerTraz = (Spinner) convertView.findViewById(R.id.spinnerTrazabilidad);
            spinnerAdapterTraz =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamCtrlSeguridad);
            spinnerAdapterTraz.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTraz.setAdapter((SpinnerAdapter) spinnerAdapterTraz);

            if (expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                    expandedListPosition).getIdControlSeguridadTrazabilidad() != null && expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                    expandedListPosition).getIdControlSeguridadTrazabilidad() <= strParamCtrlSeguridad.size())
                spinnerTraz.setSelection(expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                        expandedListPosition).getIdControlSeguridadTrazabilidad()+1);

            spinnerTraz.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    if (index != 0)
                    expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                            expandedListPosition).setIdControlSeguridadTrazabilidad(index-1);
                    else
                        expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                                expandedListPosition).setIdControlSeguridadTrazabilidad(null);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerTProtec= (Spinner) convertView.findViewById(R.id.spinnerTProteccion);
            spinnerAdapterTProtec =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamTProteccion);
            spinnerAdapterTProtec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTProtec.setAdapter((SpinnerAdapter) spinnerAdapterTProtec);
            if (expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                    expandedListPosition).getTipoProteccion() != null)
                spinnerTProtec.setSelection(expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                        expandedListPosition).getTipoProteccion()+1);
            spinnerTProtec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    if (index != 0)
                    expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                            expandedListPosition).setTipoProteccion(index-1);
                    else
                        expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                                expandedListPosition).setTipoProteccion(null);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            NumberPicker np = (NumberPicker) convertView.findViewById(R.id.numberPicker);
            np.setMinValue(0);
            np.setMaxValue(20);
            String[] valueSet = new String[21];
            int aux = 0;
            for (int i = 0; i <= 100; i += 5) {
                valueSet[aux] = String.valueOf(i);
                aux++;
            }
            np.setDisplayedValues(valueSet);
            np.setValue(0);

            if (expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                    expandedListPosition).getEficacia() != null)
                np.setValue(expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                        expandedListPosition).getEficacia());

            np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                            expandedListPosition).setEficacia(newVal);
                }
            });


            return convertView;
        }

        @Override
        public int getChildrenCount(int listPosition) {
            return this.expandableListDetailAdapter.get(this.expandableListTitle.get(listPosition))
                    .size();
        }

        @Override
        public Object getGroup(int listPosition) {
            return this.expandableListTitle.get(listPosition);
        }

        @Override
        public int getGroupCount() {
            return this.expandableListTitle.size();
        }

        @Override
        public long getGroupId(int listPosition) {
            return listPosition;
        }

        @Override
        public View getGroupView(int listPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            AssetThreatInfoDTO listTitle = (AssetThreatInfoDTO) getGroup(listPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context.
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.groupitem_threat_asset_safeguard, null);
            }
            TextView listTitleTextView = (TextView) convertView
                    .findViewById(R.id.asset);
            TextView listTitleTextView2 = (TextView) convertView
                    .findViewById(R.id.threat);
            listTitleTextView.setText("[" + listTitle.getCodigoActivo() + "] " + listTitle.getNombreActivo());
            listTitleTextView2.setText(obtenerNombreAmenaza(listTitle.getIdListaTipoAmenaza(), listTitle.getIdTipoAmenaza()));

            //listTitleTextView.setText("[" + listTitle.getCodigoActivo() + "] " + listTitle.getNombreActivo());
            //listTitle2TextView.setText(obtenerNombreAmenaza(listTitle.getIdListaTipoAmenaza(), listTitle.getIdTipoAmenaza()));
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int listPosition, int expandedListPosition) {
            return true;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

    }

    private String obtenerNombreAmenaza(Long idListaTipoAmenaza, Long idTipoAmenaza) {

        String codeName = "";
        switch (idListaTipoAmenaza.intValue()) {
            case 0:
                codeName = GlobalConstants.AMENAZAS_TIPO_DESASTRES_NATURALES[idTipoAmenaza.intValue()];
                break;
            case 1:
                codeName = GlobalConstants.AMENAZAS_TIPO_ORIGEN_INDUSTRIAL[idTipoAmenaza.intValue()];
                break;
            case 2:
                codeName = GlobalConstants.AMENAZAS_TIPO_ERRORES_FALLOS_NO_INTENCIONADOS[idTipoAmenaza.intValue()];
                break;
            case 3:
                codeName = GlobalConstants.AMENAZAS_TIPO_ATAQUES_DELIBERADOS[idTipoAmenaza.intValue()];
                break;
        }
        return codeName;
    }

    private void leerDatosFragmento2() {

        AddEditSafeguardActivity.AddEditSafeguardFragmentPagerAdapter adapter;
        AddEditSafeguardActivity act = (AddEditSafeguardActivity) getActivity();
        adapter = (AddEditSafeguardActivity.AddEditSafeguardFragmentPagerAdapter) act.getViewPager().getAdapter();
        SafeguardThreatAssetsSelectionFragment fr2 = (SafeguardThreatAssetsSelectionFragment)
                adapter.instantiateItem(act.getViewPager(), 1);

        expandableListFr2 = fr2.getExpandableListDetail();
        expandableTitleFr2 = fr2.getExpandableListTitle();
    }

    private HashMap<AssetThreatInfoDTO, List<Safeguard>> cargarDatosFr2() {
        HashMap<AssetThreatInfoDTO, List<Safeguard>> expandableListDetail = new HashMap<>();
        if (expandableListTitle != null)
            expandableListTitle.clear();
        else
            expandableListTitle = new ArrayList<>();
        if (expandableListFr2 != null && !expandableListFr2.isEmpty()) {
            for (Map.Entry<AssetsSafeguardDTO, List<ThreatSafeguardDTO>> entry : expandableListFr2.entrySet()) {
                AssetsSafeguardDTO key = entry.getKey();
                List<ThreatSafeguardDTO> value = entry.getValue();


                for (ThreatSafeguardDTO threat : value) {
                    if (threat.getChecked()) {
                        List<Safeguard> listSafeguard = new ArrayList<>();
                        Safeguard safeguard = new Safeguard(null, threat.getIdAmenaza(), key.getIdActivo(),
                                idProyecto, null, null, null, null, null, null, null, null, null, null);
                        Long idAmenaza = threat.getIdAmenaza();
                        Long idListaTipoAmenaza = threat.getIdListaTipoAmenaza();
                        Long idTipoAmenaza = threat.getIdTipoAmenaza();
                        listSafeguard.add(safeguard);
                        AssetThreatInfoDTO assetThreat = new AssetThreatInfoDTO(key.getIdActivo(),
                                key.getNombreActivo(), key.getCodigoActivo(), idAmenaza, idListaTipoAmenaza,
                                idTipoAmenaza, key.getIdProyecto());
                        expandableListDetail.put(assetThreat, listSafeguard);
                        expandableListTitle.add(assetThreat);
                    }
                }
            }
        }
        return expandableListDetail;
    }

    protected void recargarInfo() {
        Log.d("MyFragment", "Fragment is visible.");
        leerDatosFragmento2();
        HashMap<AssetThreatInfoDTO, List<Safeguard>> expandableListDetailFr = new HashMap<>();
        HashMap<AssetThreatInfoDTO, List<Safeguard>> expandableListDetailActual = new HashMap<>();
        //expandableListDetail = cargarDatosFr2();

        expandableListDetailFr = cargarDatosFr2();

        List<Safeguard> safeguardsFr = new ArrayList<>();

        for (Map.Entry<AssetThreatInfoDTO, List<Safeguard>> entry : expandableListDetailFr.entrySet()) {
            safeguardsFr.add(entry.getValue().get(0));

        }

        List<Safeguard> safeguardsAnterior = new ArrayList<>();

        for (Map.Entry<AssetThreatInfoDTO, List<Safeguard>> entry : expandableListDetail.entrySet()) {
            safeguardsAnterior.add(entry.getValue().get(0));

        }


        for (Safeguard safe: safeguardsAnterior){
            for (int i=0; i<safeguardsFr.size(); i++) {
                if (safeguardsFr.get(i).getIdThreat() == safe.getIdThreat()) {
                    safeguardsFr.set(i,safe);
                }
            }
        }


        int aux = 0;
        for (Map.Entry<AssetThreatInfoDTO, List<Safeguard>> entry : expandableListDetailFr.entrySet()) {
            List<Safeguard> listElement = new ArrayList<Safeguard>();
            listElement.add(safeguardsFr.get(aux));
            expandableListDetailActual.put(entry.getKey(), listElement);
            aux++;
        }

        expandableListDetail = expandableListDetailActual;

        expandableListAdapter = new CustomExpandableListAdapter(this.getContext(), expandableListTitle, expandableListDetailActual);
        expandableListView.setAdapter(expandableListAdapter);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            recargarInfo();
        } else
            Log.d("MyFragment", "Fragment is not visible.");
    }

}

