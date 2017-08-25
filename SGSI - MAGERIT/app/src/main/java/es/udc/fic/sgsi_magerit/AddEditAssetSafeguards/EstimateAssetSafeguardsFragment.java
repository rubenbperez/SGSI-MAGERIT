package es.udc.fic.sgsi_magerit.AddEditAssetSafeguards;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.Model.ProjectSizing.ProjectSizingConstants;
import es.udc.fic.sgsi_magerit.Model.Safeguard.AssetSafeguardsThreatsInfoDTO;
import es.udc.fic.sgsi_magerit.Model.Safeguard.AssetThreatInfoDTO;
import es.udc.fic.sgsi_magerit.Model.Safeguard.AssetThreatSafeguardDTO;
import es.udc.fic.sgsi_magerit.Model.Safeguard.Safeguard;
import es.udc.fic.sgsi_magerit.Model.Safeguard.SafeguardInfoDTO;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.Converter;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;

public class EstimateAssetSafeguardsFragment extends Fragment {

    private ModelServiceImpl service;
    private Long idProyecto;
    private Long idActivo;
    List<AssetSafeguardsThreatsInfoDTO> expandableListTitle;
    ExpandableListAdapter expandableListAdapter;
    HashMap<AssetSafeguardsThreatsInfoDTO, List<Safeguard>> expandableListDetail;
    List<Integer> idsParamCtrlSeguridad;
    List<String> strParamCtrlSeguridad;
    List<String> strParamTProteccion;
    ExpandableListView expandableListView;

    public HashMap<AssetSafeguardsThreatsInfoDTO, List<Safeguard>> getData() {
        return expandableListDetail;
    }

    public static EstimateAssetSafeguardsFragment newInstance() {
        EstimateAssetSafeguardsFragment fragment = new EstimateAssetSafeguardsFragment();
        return fragment;
    }

    public EstimateAssetSafeguardsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_estimate_asset_safeguards, container, false);

        service = new ModelServiceImpl(this.getActivity(), GlobalConstants.DATABASE_NAME, 1);
        Bundle args = getArguments();

        if (args != null) {
            this.idProyecto = args.getLong("idProyecto");
            this.idActivo = args.getLong("idActivo", GlobalConstants.NULL_ID_LISTA_TIPO_SALVAGUARDA);
        }

        idsParamCtrlSeguridad = service.obtenerParametrizacionesActivadas(idProyecto,
                ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_CONTROLSEGURIDAD);

        expandableListDetail = service.obtenerInfoSalvaguardasDeActivo(idProyecto,idActivo);

        expandableListTitle = new ArrayList<AssetSafeguardsThreatsInfoDTO>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this.getContext(), expandableListTitle, expandableListDetail);
        expandableListView = (android.widget.ExpandableListView) view.findViewById(R.id.expandableList);
        expandableListView.setAdapter(expandableListAdapter);

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
        private List<AssetSafeguardsThreatsInfoDTO> expandableListTitle;
        private HashMap<AssetSafeguardsThreatsInfoDTO, List<Safeguard>> expandableListDetailAdapter;

        public CustomExpandableListAdapter(Context context, List<AssetSafeguardsThreatsInfoDTO> expandableListTitle,
                                           HashMap<AssetSafeguardsThreatsInfoDTO, List<Safeguard>> expandableListDetail) {
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
                    expandedListPosition).getIdControlSeguridadDisponibilidad() != null)
                spinnerDisp.setSelection(expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                        expandedListPosition).getIdControlSeguridadDisponibilidad());

            spinnerDisp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    if (index != 0)
                        expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                                expandedListPosition).setIdControlSeguridadDisponibilidad(index);
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
                    expandedListPosition).getIdControlSeguridadIntegridad() != null)
                spinnerInt.setSelection(expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                        expandedListPosition).getIdControlSeguridadIntegridad());

            spinnerInt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    if (index != 0)
                        expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                                expandedListPosition).setIdControlSeguridadIntegridad(index);
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
                    expandedListPosition).getIdControlSeguridadConfidencialidad() != null)
                spinnerConf.setSelection(expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                        expandedListPosition).getIdControlSeguridadConfidencialidad());

            spinnerConf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    if (index != 0)
                        expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                                expandedListPosition).setIdControlSeguridadConfidencialidad(index);
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
                    expandedListPosition).getIdControlSeguridadAutenticidad() != null)
                spinnerAut.setSelection(expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                        expandedListPosition).getIdControlSeguridadAutenticidad());

            spinnerAut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    if (index != 0)
                        expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                                expandedListPosition).setIdControlSeguridadAutenticidad(index);
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
                    expandedListPosition).getIdControlSeguridadTrazabilidad() != null)
                spinnerTraz.setSelection(expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                        expandedListPosition).getIdControlSeguridadTrazabilidad());

            spinnerTraz.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    if (index != 0)
                        expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                                expandedListPosition).setIdControlSeguridadTrazabilidad(index);
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
                        expandedListPosition).getTipoProteccion());
            spinnerTProtec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    if (index != 0)
                        expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                                expandedListPosition).setTipoProteccion(index);
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
            AssetSafeguardsThreatsInfoDTO listTitle = (AssetSafeguardsThreatsInfoDTO) getGroup(listPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context.
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.groupitem_asset_safeguard_threats, null);
            }
            TextView listTitleTextView = (TextView) convertView
                    .findViewById(R.id.threat);
            TextView listTitleTextView2 = (TextView) convertView
                    .findViewById(R.id.safeguard);
            listTitleTextView.setText(Converter.convertirNombreAmenaza(listTitle.getIdListaTipoAmenaza().intValue(),
                    listTitle.getIdTipoAmenaza().intValue()));
            listTitleTextView2.setText(Converter.convertirNombreSalvaguarda(listTitle.getIdListaTipoSalvaguarda().intValue(),
                    listTitle.getIdTipoSalvaguarda().intValue()));

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

    protected void recargarInfo() {
        AddEditAssetSafeguardsActivity.AddEditAssetSafeguardsFragmentPagerAdapter adapter;
        AddEditAssetSafeguardsActivity act = (AddEditAssetSafeguardsActivity) getActivity();

        adapter = (AddEditAssetSafeguardsActivity.AddEditAssetSafeguardsFragmentPagerAdapter) act.getViewPager().getAdapter();
        IdentifyAssetSafeguardThreatsFragment fr2 = (IdentifyAssetSafeguardThreatsFragment)
                adapter.instantiateItem(act.getViewPager(), 1);

        HashMap<SafeguardInfoDTO, List<AssetThreatSafeguardDTO>> expandableSafegardsThreatSelected = fr2.getData();
        HashMap<AssetSafeguardsThreatsInfoDTO, List<Safeguard>> expandableListDetailNueva = new HashMap<>();
        List<AssetSafeguardsThreatsInfoDTO> expandableListTitleNuevo = new ArrayList<>();

        if (!expandableListDetail.isEmpty()) {
            for (Map.Entry<AssetSafeguardsThreatsInfoDTO, List<Safeguard>> entry : expandableListDetail.entrySet()) {
                AssetSafeguardsThreatsInfoDTO key = entry.getKey();
                List<Safeguard> value = entry.getValue();

                //si ya estaba, la dejo. Si estaba, pero ya no la elimino

                for (Map.Entry<SafeguardInfoDTO, List<AssetThreatSafeguardDTO>> entrySelected : expandableSafegardsThreatSelected.entrySet()) {
                    SafeguardInfoDTO keySelected = entrySelected.getKey();
                    List<AssetThreatSafeguardDTO> valueSelected = entrySelected.getValue();

                    boolean flag = false;

                    for (AssetThreatSafeguardDTO aux : valueSelected) {

                        if (aux.getChecked() && aux.getIdListaTipoAmenaza() == key.getIdListaTipoAmenaza() && aux.getIdTipoAmenaza() ==
                                key.getIdTipoAmenaza() && keySelected.getIdListaTipo() == key.getIdListaTipoSalvaguarda()
                                && keySelected.getIdTipo() == key.getIdTipoSalvaguarda()) {
                            flag = true;
                            break;
                        }
                    }

                    if (flag) {
                        expandableListDetailNueva.put(key, value);
                        expandableListTitleNuevo.add(key);
                    }
                }

            }
        }

        // anado las nuevas que no estaban antiguamente
        for(Map.Entry<SafeguardInfoDTO, List<AssetThreatSafeguardDTO>>  entrySelected : expandableSafegardsThreatSelected.entrySet()) {
            SafeguardInfoDTO keySelected = entrySelected.getKey();
            List<AssetThreatSafeguardDTO> valueSelected = entrySelected.getValue();

            if (!expandableListDetail.isEmpty()) {

                for (AssetThreatSafeguardDTO aux: valueSelected) {
                    boolean flag = false;
                    for (Map.Entry<AssetSafeguardsThreatsInfoDTO, List<Safeguard>> entryActual : expandableListDetail.entrySet()) {
                        AssetSafeguardsThreatsInfoDTO key = entryActual.getKey();
                        List<Safeguard> value = entryActual.getValue();
                        if (aux.getIdListaTipoAmenaza() == key.getIdListaTipoAmenaza() && aux.getIdTipoAmenaza() ==
                                key.getIdTipoAmenaza() && keySelected.getIdListaTipo() == key.getIdListaTipoSalvaguarda()
                                && keySelected.getIdTipo() == key.getIdTipoSalvaguarda()) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag && aux.getChecked()) {
                        AssetSafeguardsThreatsInfoDTO infoDTO = new AssetSafeguardsThreatsInfoDTO(
                                aux.getIdListaTipoAmenaza(), aux.getIdTipoAmenaza(),
                                keySelected.getIdListaTipo(), keySelected.getIdTipo(), false);
                        Safeguard safeguard = new Safeguard(null, aux.getIdAmenaza(),
                                idActivo, idProyecto, keySelected.getIdListaTipo(),
                                keySelected.getIdTipo(), null, null, null, null, null, null, null, null);
                        List<Safeguard> listSafeguard = new ArrayList<>();
                        listSafeguard.add(safeguard);
                        expandableListDetailNueva.put(infoDTO, listSafeguard);
                        expandableListTitleNuevo.add(infoDTO);
                    }

                }
            } else { //anadimos todos los elementos
                for (Map.Entry<SafeguardInfoDTO, List<AssetThreatSafeguardDTO>> entrySelected2 : expandableSafegardsThreatSelected.entrySet()) {
                    SafeguardInfoDTO key2 = entrySelected2.getKey();
                    List<AssetThreatSafeguardDTO> value2 = entrySelected2.getValue();

                    for (AssetThreatSafeguardDTO aux : value2) {
                        if (aux.getChecked()) {
                            AssetSafeguardsThreatsInfoDTO infoDTO = new AssetSafeguardsThreatsInfoDTO(
                                    aux.getIdListaTipoAmenaza(), aux.getIdTipoAmenaza(),
                                    key2.getIdListaTipo(), key2.getIdTipo(), false);
                            Safeguard safeguard = new Safeguard(null, aux.getIdAmenaza(),
                                    idActivo, idProyecto, key2.getIdListaTipo(),
                                    key2.getIdTipo(), null, null, null, null, null, null, null, null);

                            List<Safeguard> listSafeguard = new ArrayList<>();
                            listSafeguard.add(safeguard);
                            expandableListDetailNueva.put(infoDTO, listSafeguard);
                            expandableListTitleNuevo.add(infoDTO);
                        }
                    }
                }
            }
        }

        expandableListDetail = expandableListDetailNueva;
        expandableListTitle = expandableListTitleNuevo;

        expandableListAdapter = new CustomExpandableListAdapter(this.getContext(), expandableListTitle, expandableListDetailNueva);
        expandableListView.setAdapter(expandableListAdapter);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            Log.d("MyFragment", "Fragment is visible.");
            recargarInfo();
        } else
            Log.d("MyFragment", "Fragment is not visible.");
    }



}
