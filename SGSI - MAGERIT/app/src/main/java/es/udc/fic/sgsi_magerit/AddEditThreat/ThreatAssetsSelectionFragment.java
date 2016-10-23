package es.udc.fic.sgsi_magerit.AddEditThreat;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.udc.fic.sgsi_magerit.Model.Asset.Asset;
import es.udc.fic.sgsi_magerit.Model.Asset.AssetDTO;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.Model.Threat.ThreatDTO;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;


public class ThreatAssetsSelectionFragment extends Fragment {

    private ListView lstOpcionesActivos;
    private List<AssetDTO> data;
    private ModelServiceImpl service;
    private Long idProyecto;

    public ThreatAssetsSelectionFragment() {
    }

    public static ThreatAssetsSelectionFragment newInstance() {
        ThreatAssetsSelectionFragment fragment = new ThreatAssetsSelectionFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_threat_assets_selection, container, false);
        service = new ModelServiceImpl(this.getActivity(), GlobalConstants.DATABASE_NAME, 1);
        Bundle args = getArguments();
        if (args != null) {
            this.idProyecto = args.getLong("idProyecto");
        }

        data = service.obtenerActivos(idProyecto);
        List<String> assetNames = new ArrayList<String>();

        lstOpcionesActivos = (ListView) view.findViewById(R.id.LstOpciones);
        AssetAdapter adaptador =
                new AssetAdapter(this.getContext(), data);
        lstOpcionesActivos.setAdapter(adaptador);

        return view;
    }

    public class AssetAdapter extends ArrayAdapter<AssetDTO> {

        public AssetAdapter(Context context, List<AssetDTO> data) {
            super(context, android.R.layout.simple_list_item_single_choice, data);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());

            View item = inflater.inflate(R.layout.listitem_threat_assets, null);
            TextView lblAssetName = (TextView) item.findViewById(R.id.asset);
            lblAssetName.setText("[" + data.get(position).getCodigoActivo() + "]" + " " + data.get(position).getNombreActivo());

            final ImageButton rc = (ImageButton) item.findViewById(R.id.icon);
            final CheckBox checkBox = (CheckBox) item.findViewById(R.id.checkBox);
            final TableLayout tb = (TableLayout) item.findViewById(R.id.tabla);

            rc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!checkBox.isChecked()) {
                        return;
                    }
                    if (tb.getVisibility() == View.GONE) {
                        rc.setImageResource(R.drawable.ic_less);
                        tb.setVisibility(View.VISIBLE);
                    } else {
                        rc.setImageResource(R.drawable.ic_more);
                        tb.setVisibility(View.GONE);
                    }
                }
            });

            return(item);
        }


    }
}