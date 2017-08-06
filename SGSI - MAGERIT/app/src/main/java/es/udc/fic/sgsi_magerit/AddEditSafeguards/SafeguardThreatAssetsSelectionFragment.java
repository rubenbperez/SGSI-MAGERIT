package es.udc.fic.sgsi_magerit.AddEditSafeguards;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.Model.Safeguard.AssetsSafeguardDTO;
import es.udc.fic.sgsi_magerit.Model.Safeguard.ThreatSafeguardDTO;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;

public class SafeguardThreatAssetsSelectionFragment extends Fragment {
    private ModelServiceImpl service;
    private Long idProyecto;
    private Long idListaTipoSalvaguarda;
    private Long idTipoSalvaguarda;
    List<AssetsSafeguardDTO> expandableListTitle;
    ExpandableListAdapter expandableListAdapter;
    HashMap<AssetsSafeguardDTO, List<ThreatSafeguardDTO>> expandableListDetail;

    public HashMap<AssetsSafeguardDTO, List<ThreatSafeguardDTO>> getExpandableListDetail() {
        return expandableListDetail;
    }

    public List<AssetsSafeguardDTO> getExpandableListTitle() {
        return expandableListTitle;
    }

    public Boolean assetsSelected (){
        Boolean retVal = false;

        for (Map.Entry<AssetsSafeguardDTO, List<ThreatSafeguardDTO>> entry : expandableListDetail.entrySet())
        {
            for (ThreatSafeguardDTO element : entry.getValue())
                if (element.getChecked())
                    return true;
        }
        return retVal;
    }

    public static SafeguardThreatAssetsSelectionFragment newInstance() {
        SafeguardThreatAssetsSelectionFragment fragment = new SafeguardThreatAssetsSelectionFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_safeguard_threat_assets_selection, container, false);
        service = new ModelServiceImpl(this.getActivity(), GlobalConstants.DATABASE_NAME, 1);
        Bundle args = getArguments();
        if (args != null) {
            this.idProyecto = args.getLong("idProyecto");
            this.idListaTipoSalvaguarda = args.getLong("idListaTipoSalvaguarda", GlobalConstants.NULL_ID_LISTA_TIPO_SALVAGUARDA);
            this.idTipoSalvaguarda = args.getLong("idTipoSalvaguarda", GlobalConstants.NULL_ID_LISTA_TIPO_SALVAGUARDA);
        }

        expandableListDetail = service.obtenerAmenazasDeActivos(idProyecto, idListaTipoSalvaguarda, idTipoSalvaguarda);

        final ExpandableListView expandableListView = (ExpandableListView) view.findViewById(R.id.expandableList);
        expandableListTitle = new ArrayList<AssetsSafeguardDTO>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this.getContext(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        return view;
    }


    public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

        private Context context;
        private List<AssetsSafeguardDTO> expandableListTitle;
        private HashMap<AssetsSafeguardDTO, List<ThreatSafeguardDTO>> expandableDetail;

        public CustomExpandableListAdapter(Context context, List<AssetsSafeguardDTO> expandableListTitle,
                                           HashMap<AssetsSafeguardDTO, List<ThreatSafeguardDTO>> expandableListDetail) {
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
            final ThreatSafeguardDTO expandedList = (ThreatSafeguardDTO) getChild(listPosition, expandedListPosition);
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
                    AssetsSafeguardDTO key = expandableListTitle.get(listPosition);
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
                    AssetsSafeguardDTO key = expandableListTitle.get(listPosition);
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
            AssetsSafeguardDTO listTitle = (AssetsSafeguardDTO) getGroup(listPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context.
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.groupitem_threat_assets, null);
            }
            TextView listTitleTextView = (TextView) convertView
                    .findViewById(R.id.threat);
            listTitleTextView.setText("[" + listTitle.getCodigoActivo() + "] " + listTitle.getNombreActivo());
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


}
