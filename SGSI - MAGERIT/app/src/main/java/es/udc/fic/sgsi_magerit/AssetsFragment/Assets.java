package es.udc.fic.sgsi_magerit.AssetsFragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
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
                intent.putExtra("idProyecto", idProyectoActivo);
                startActivityForResult(intent, GlobalConstants.REQUEST_CODE_ADD_ACTIVITY);

            }
        });
        registerForContextMenu(lstOpciones);
        return  view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent datas) {
        super.onActivityResult(requestCode, resultCode, datas);

        if (GlobalConstants.REQUEST_CODE_ADD_ACTIVITY == requestCode ||
                GlobalConstants.REQUEST_CODE_EDIT_ACTIVITY == requestCode) {

            if (resultCode == 1) {
                data.clear();
                    data.addAll(service.obtenerActivos(idProyectoActivo));
                adaptador.notifyDataSetChanged();
            }

            if (resultCode == 0)
                return;
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();

        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo)menuInfo;

        menu.setHeaderTitle(data.get(info.position).getNombreActivo());
        inflater.inflate(R.menu.menu_proyectos, menu);
    }

    //TODO
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int index = info.position;
        switch (item.getItemId()) {
            case R.id.menuOpcBorrar:
                /*Log.d("Posicion", Integer.toString(index));
                //service.eliminarProyectoYDimensionamiento(data.get(index).getId());
                data.remove(index);
                adaptador.notifyDataSetChanged();*/
                //comprobarElementosNavView(data,navView);
                break;

            case R.id.menuOpcEditar:
                /*Intent intent = new Intent(getActivity(), AddProjectActivity.class);
                intent.putExtra("idProyecto", data.get(index).getId()); //Optional parameters
                startActivityForResult(intent, GlobalConstants.REQUEST_CODE_EDIT_ACTIVITY);*/
                break;
        }

        return true;
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
            String descr = data.get(position).getDescripcionActivo();
            if (descr != null && !descr.isEmpty())
                lblAssetDescription.setText(descr);
            else
                lblAssetDescription.setVisibility(View.GONE);

            TextView lblAssetResp = (TextView)item.findViewById(R.id.asset_resp);
            String resp = data.get(position).getResponsableActivo();
            if (resp != null && !resp.isEmpty())
                lblAssetResp.setText(resp);
            else
                lblAssetResp.setVisibility(View.GONE);

            TextView lblAssetLoc = (TextView)item.findViewById(R.id.asset_loc);
            String loc = data.get(position).getUbicacionActivo();
            if (loc != null && !loc.isEmpty())
                lblAssetLoc.setText(loc);
            else
                lblAssetLoc.setVisibility(View.GONE);

            return(item);
        }
    }

}
