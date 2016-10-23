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

        for (AssetDTO asset: data) {
            assetNames.add("[" + asset.getCodigoActivo() + "]" + " "+ asset.getNombreActivo());
        }


        lstOpcionesActivos = (ListView) view.findViewById(R.id.LstOpciones);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_list_item_multiple_choice, assetNames);
        lstOpcionesActivos.setAdapter(adaptador);

        return view;
    }


}