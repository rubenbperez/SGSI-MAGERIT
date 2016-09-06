package es.udc.fic.sgsi_magerit.AssetsFragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.udc.fic.sgsi_magerit.AddEditAsset.AddEditAssetActivity;
import es.udc.fic.sgsi_magerit.AddEditProject.AddProjectActivity;
import es.udc.fic.sgsi_magerit.Model.Asset.Asset;
import es.udc.fic.sgsi_magerit.Model.Asset.AssetDTO;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;


public class Assets extends Fragment {

    private FloatingActionButton fabButton;
    private ListView lstOpciones;
    private List<AssetDTO> data;
    private ModelServiceImpl service;
    private AssetAdapter adaptador;
    private long idProyectoActivo;

    public Assets() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assets, container, false);
        service = new ModelServiceImpl(getContext(), GlobalConstants.DATABASE_NAME,1);

        idProyectoActivo = service.obtenerIdProyectoActivo();

        data = service.obtenerActivos(idProyectoActivo);
        adaptador =
                new AssetAdapter(this.getContext(), data);

        lstOpciones = (ListView)view.findViewById(R.id.LstOpciones);
        lstOpciones.setAdapter(adaptador);

        fabButton = (FloatingActionButton)view.findViewById(R.id.fab);
        fabButton.setBackgroundTintList(
                getResources().getColorStateList(R.color.fab_color));

        fabButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddEditAssetActivity.class);
                intent.putExtra("idActivo", GlobalConstants.NULL_ID_PROYECTO); //Optional parameters
                startActivityForResult(intent, GlobalConstants.REQUEST_CODE_ADD_ACTIVITY);
            }
        });

        return  view;
    }


    public class AssetAdapter extends ArrayAdapter<AssetDTO> {

        public AssetAdapter(Context context, List<AssetDTO> data) {
            super(context, android.R.layout.simple_list_item_single_choice, data);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());

            View item = inflater.inflate(R.layout.listitem_assets, null);

            TextView lblAssetNameAndCode = (TextView)item.findViewById(R.id.asset_code_name);
            lblAssetNameAndCode.setText("[" + data.get(position).getCodigoActivo() + "]" + " "+
            data.get(position).getNombreActivo());

            TextView lblAssetDescription = (TextView)item.findViewById(R.id.asset_desc);
            lblAssetDescription.setText(data.get(position).getDescripcionActivo());

            TextView lblAssetResp = (TextView)item.findViewById(R.id.asset_resp);
            lblAssetResp.setText(data.get(position).getResponsableActivo());

            TextView lblAssetLoc = (TextView)item.findViewById(R.id.asset_loc);
            lblAssetLoc.setText(data.get(position).getUbicacionActivo());

            return(item);
        }
    }

}
