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

import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.Model.Safeguard.AssetThreatInfoDTO;
import es.udc.fic.sgsi_magerit.Model.Safeguard.Safeguard;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;

public class EstimateSafeguardFragment extends Fragment {

    private ModelServiceImpl service;
    private Long idProyecto;
    List<AssetThreatInfoDTO> expandableListTitle;
    ExpandableListAdapter expandableListAdapter;

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
        }

        HashMap<AssetThreatInfoDTO, List<Safeguard>> expandableListDetail = new HashMap<>();

        AssetThreatInfoDTO at = new AssetThreatInfoDTO((long)1,"act1","act",(long)1,(long)1,(long)1,(long)1);

        Safeguard sf = new Safeguard(null,(long)1,(long)1,(long)1,(long)3,(long)3,null,null,null,null,null,1,50,null);

        List<Safeguard> ls = new ArrayList<>();
        ls.add(sf);

        expandableListDetail.put(at,ls);

        final ExpandableListView expandableListView = (android.widget.ExpandableListView) view.findViewById(R.id.expandableList);
        expandableListTitle = new ArrayList<AssetThreatInfoDTO>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this.getContext(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        return view;
    }

    public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

        private Context context;
        private List<AssetThreatInfoDTO> expandableListTitle;
        private HashMap<AssetThreatInfoDTO, List<Safeguard>> expandableListDetail;

        public CustomExpandableListAdapter(Context context, List<AssetThreatInfoDTO> expandableListTitle,
                                           HashMap<AssetThreatInfoDTO, List<Safeguard>> expandableListDetail) {
            this.context = context;
            this.expandableListTitle = expandableListTitle;
            this.expandableListDetail = expandableListDetail;
        }

        @Override
        public Object getChild(int listPosition, int expandedListPosition) {
            return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                    .get(expandedListPosition);
        }

        @Override
        public long getChildId(int listPosition, int expandedListPosition) {
            return expandedListPosition;
        }

        @Override
        public View getChildView(int listPosition, final int expandedListPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            final Safeguard expandedList = (Safeguard) getChild(listPosition, expandedListPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.childitem_estimate_safeguard, null);
            }

            return convertView;
        }

        @Override
        public int getChildrenCount(int listPosition) {
            return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
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
                    .findViewById(R.id.asset_threat);
            listTitleTextView.setText("[" + listTitle.getCodigoActivo() + "] " + listTitle.getNombreActivo()
                    + " - " + obtenerNombreAmenaza(listTitle.getIdListaTipoAmenaza(),listTitle.getIdTipoAmenaza()));
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
