package es.udc.fic.sgsi_magerit.AddEditAssetSafeguards;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.udc.fic.sgsi_magerit.AddEditSafeguards.AddEditSafeguardActivity;
import es.udc.fic.sgsi_magerit.AddEditSafeguards.SafeguardThreatAssetsSelectionFragment;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelService;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.Model.Safeguard.AssetThreatInfoDTO;
import es.udc.fic.sgsi_magerit.Model.Safeguard.AssetThreatSafeguardDTO;
import es.udc.fic.sgsi_magerit.Model.Safeguard.Safeguard;
import es.udc.fic.sgsi_magerit.Model.Safeguard.SafeguardDTO;
import es.udc.fic.sgsi_magerit.Model.Safeguard.SafeguardInfoDTO;
import es.udc.fic.sgsi_magerit.Model.Safeguard.ThreatSafeguardDTO;
import es.udc.fic.sgsi_magerit.Model.Threat.AssetThreatDTO;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.Converter;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;


public class IdentifyAssetSafeguardThreatsFragment extends Fragment {

    private Long idProyecto;
    private Long idActivo;
    private ModelService service;
    List<SafeguardInfoDTO> expandableListTitle;
    ExpandableListAdapter expandableListAdapter;
    HashMap<SafeguardInfoDTO, List<AssetThreatSafeguardDTO>> expandableListDetail;
    ExpandableListView expandableListView;


    public HashMap<SafeguardInfoDTO, List<AssetThreatSafeguardDTO>> getData() {
        return expandableListDetail;
    }

    public IdentifyAssetSafeguardThreatsFragment() {
        // Required empty public constructor
    }

    public static IdentifyAssetSafeguardThreatsFragment newInstance() {
        IdentifyAssetSafeguardThreatsFragment fragment = new IdentifyAssetSafeguardThreatsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View view = inflater.inflate(R.layout.fragment_identify_asset_safeguard_threats, container, false);
        service = new ModelServiceImpl(this.getActivity(), GlobalConstants.DATABASE_NAME,1);
        Bundle args = getArguments();
        if(args != null){
            this.idProyecto = args.getLong("idProyecto");
            this.idActivo = args.getLong("idActivo");
        }

        expandableListDetail = service.obtenerSalvaguardasYAmenazasDeActivo(idProyecto, idActivo);

        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableList);
        expandableListTitle = new ArrayList<SafeguardInfoDTO>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this.getContext(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        return view;
    }

    public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

        private Context context;
        private List<SafeguardInfoDTO> expandableListTitle;
        private HashMap<SafeguardInfoDTO, List<AssetThreatSafeguardDTO>> expandableDetail;

        public CustomExpandableListAdapter(Context context, List<SafeguardInfoDTO> expandableListTitle,
                                           HashMap<SafeguardInfoDTO, List<AssetThreatSafeguardDTO>> expandableListDetail) {
            this.context = context;
            this.expandableListTitle = expandableListTitle;
            this.expandableDetail = expandableListDetail;
        }

        @Override
        public Object getChild(int listPosition, int expandedListPosition) {
            return this.expandableDetail.get(this.expandableListTitle.get(listPosition))
                    .get(expandedListPosition);
        }

        @Override
        public long getChildId(int listPosition, int expandedListPosition) {
            return expandedListPosition;
        }

        @Override
        public View getChildView(final int listPosition, final int expandedListPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            final AssetThreatSafeguardDTO expandedList = (AssetThreatSafeguardDTO) getChild(listPosition, expandedListPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.childitem_assets_threat, null);
            }
            TextView expandedListTextView = (TextView) convertView
                    .findViewById(R.id.asset);
            final CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean value = null;
                    if (cb.isChecked()) {
                        cb.setChecked(false);
                        expandedList.setChecked(false);
                        value = false;
                    } else {
                        cb.setChecked(true);
                        expandedList.setChecked(true);
                        value = true;
                    }
                    SafeguardInfoDTO key = expandableListTitle.get(listPosition);
                    expandableListDetail.get(key).get(expandedListPosition).setChecked(value);
                }
            });

            cb.setChecked(expandedList.getChecked());

            expandedListTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean value = null;
                    if (cb.isChecked()) {
                        cb.setChecked(false);
                        expandedList.setChecked(false);
                        value = false;
                    } else {
                        cb.setChecked(true);
                        expandedList.setChecked(true);
                        value = true;
                    }
                    SafeguardInfoDTO key = expandableListTitle.get(listPosition);
                    expandableListDetail.get(key).get(expandedListPosition).setChecked(value);
                }
            });

            expandedListTextView.setText(obtenerNombreAmenaza(expandedList.getIdListaTipoAmenaza(), expandedList.getIdTipoAmenaza()));
            return convertView;
        }

        @Override
        public int getChildrenCount(int listPosition) {
            return this.expandableDetail.get(this.expandableListTitle.get(listPosition))
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
            SafeguardInfoDTO listTitle = (SafeguardInfoDTO) getGroup(listPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context.
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.groupitem_threat_assets, null);
            }
            TextView listTitleTextView = (TextView) convertView
                    .findViewById(R.id.threat);
            listTitleTextView.setText(Converter.convertirNombreSalvaguarda(listTitle.getIdListaTipo().intValue(), listTitle.getIdTipo().intValue()));
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
    }

    private String obtenerNombreAmenaza (Long idListaTipoAmenaza, Long idTipoAmenaza) {

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

    protected void recargarInfo() {
        AddEditAssetSafeguardsActivity.AddEditAssetSafeguardsFragmentPagerAdapter adapter;
        AddEditAssetSafeguardsActivity act = (AddEditAssetSafeguardsActivity) getActivity();

        adapter = (AddEditAssetSafeguardsActivity.AddEditAssetSafeguardsFragmentPagerAdapter) act.getViewPager().getAdapter();
        IdentifyAssetSafeguardsFragment fr1 = (IdentifyAssetSafeguardsFragment)
                adapter.instantiateItem(act.getViewPager(), 0);

        List<SafeguardDTO> safegardsSelected = fr1.getData();

        HashMap<SafeguardInfoDTO, List<AssetThreatSafeguardDTO>> expandableListDetailNueva = new HashMap<>();
        List<SafeguardInfoDTO> expandableListTitleNuevo = new ArrayList<>();

        List<AssetThreatSafeguardDTO> threats = service.obtenerAmenazasDeActivo2(idActivo,idProyecto);
        for(Map.Entry<SafeguardInfoDTO, List<AssetThreatSafeguardDTO>>  entry : expandableListDetail.entrySet()) {
            SafeguardInfoDTO key = entry.getKey();
            List<AssetThreatSafeguardDTO> value = entry.getValue();

            //si ya estaba, la dejo. Si estaba, pero ya no la elimino
            boolean is = false;
            for ( SafeguardDTO sf: safegardsSelected) {
                if (key.getIdListaTipo() == sf.getIdListaTipo() && key.getIdTipo() == sf.getIdTipo()) {
                    is = true;
                    break;
                }
            }
            if (is) {
                expandableListDetailNueva.put(key, value);
                expandableListTitleNuevo.add(key);
            }
        }

        // anado las nuevas que no estaban antiguamente
        for ( SafeguardDTO sf: safegardsSelected) {
            boolean flag = true;
            if (!expandableListDetail.isEmpty()) {

                for (Map.Entry<SafeguardInfoDTO, List<AssetThreatSafeguardDTO>> entry : expandableListDetail.entrySet()) {
                    SafeguardInfoDTO key = entry.getKey();

                    if (key.getIdListaTipo() == sf.getIdListaTipo() && key.getIdTipo() == sf.getIdTipo()) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    SafeguardInfoDTO safeguard = new SafeguardInfoDTO(sf.getIdListaTipo(), sf.getIdTipo(), false);
                    expandableListTitleNuevo.add(safeguard);
                    expandableListDetailNueva.put(safeguard, threats);
                }
            } else {
                SafeguardInfoDTO safeguard = new SafeguardInfoDTO(sf.getIdListaTipo(), sf.getIdTipo(), false);
                expandableListTitleNuevo.add(safeguard);
                expandableListDetailNueva.put(safeguard, threats);
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
