package es.udc.fic.sgsi_magerit.ThreatsFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import es.udc.fic.sgsi_magerit.AddEditThreat.AddEditThreatActivity;
import es.udc.fic.sgsi_magerit.Model.Asset.Asset;
import es.udc.fic.sgsi_magerit.Model.Asset.AssetDTO;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.Model.Project.Project;
import es.udc.fic.sgsi_magerit.Model.Threat.Threat;
import es.udc.fic.sgsi_magerit.Model.Threat.ThreatDTO;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;


public class Threats extends Fragment {

    private FloatingActionButton fabButton;
    private ListView lstOpciones;
    private List<ThreatDTO> data;
    private ModelServiceImpl service;
    private ThreatAdapter adaptador;
    private long idProyectoActivo;
    private NavigationView navView;

    public Threats() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_threats, container, false);
        service = new ModelServiceImpl(getContext(), GlobalConstants.DATABASE_NAME,1);
        navView = (NavigationView)getActivity().findViewById(R.id.navview);
        idProyectoActivo = service.obtenerIdProyectoActivo();
        data = service.obtenerAmenazas(idProyectoActivo);
        comprobarElementosNavView(data,navView);
        adaptador = new ThreatAdapter(this.getContext(), data);

        lstOpciones = (ListView)view.findViewById(R.id.LstOpciones);
        lstOpciones.setAdapter(adaptador);

        fabButton = (FloatingActionButton)view.findViewById(R.id.fab);
        fabButton.setBackgroundTintList(
                getResources().getColorStateList(R.color.fab_color));

        fabButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddEditThreatActivity.class);
                intent.putExtra("idProyecto", idProyectoActivo);
                startActivityForResult(intent, GlobalConstants.REQUEST_CODE_ADD_ACTIVITY);
            }
        });
        registerForContextMenu(lstOpciones);
        return  view;
    }


    public class ThreatAdapter extends ArrayAdapter<ThreatDTO> {

        public ThreatAdapter(Context context, List<ThreatDTO> data) {
            super(context, android.R.layout.simple_list_item_single_choice, data);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());

            View item = inflater.inflate(R.layout.listitem_threats, null);
            ThreatDTO threat = data.get(position);
            String codeName = null;
            String threatType = GlobalConstants.TIPO_AMENAZAS[threat.getIdListaTipo().intValue()];

            switch (threat.getIdListaTipo().intValue()){
                case 0:
                    codeName = GlobalConstants.AMENAZAS_TIPO_DESASTRES_NATURALES[threat.getIdTipo().intValue()];
                    break;
                case 1:
                    codeName = GlobalConstants.AMENAZAS_TIPO_ORIGEN_INDUSTRIAL[threat.getIdTipo().intValue()];
                    break;
                case 2:
                    codeName = GlobalConstants.AMENAZAS_TIPO_ERRORES_FALLOS_NO_INTENCIONADOS[threat.getIdTipo().intValue()];
                    break;
                case 3:
                    codeName = GlobalConstants.AMENAZAS_TIPO_ATAQUES_DELIBERADOS[threat.getIdTipo().intValue()];
                    break;
            }

            TextView lblThreatNameAndCode = (TextView)item.findViewById(R.id.threat_code_name);
            lblThreatNameAndCode.setText(codeName);

            TextView lblThreatType = (TextView)item.findViewById(R.id.threat_type);

            if (threatType != null && !threatType.isEmpty())
                lblThreatType.setText(threatType);
            else
                lblThreatType.setVisibility(View.GONE);
            return(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent datas) {
        super.onActivityResult(requestCode, resultCode, datas);

        if (GlobalConstants.REQUEST_CODE_ADD_ACTIVITY == requestCode)  {

            if (resultCode == 1) {
                data.clear();
                data.addAll(service.obtenerAmenazas(idProyectoActivo));
                adaptador.notifyDataSetChanged();
            }

            if (resultCode == 0)
                adaptador.notifyDataSetChanged();
                return;
        }
        comprobarElementosNavView(data,navView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();

        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo)menuInfo;

        String title = "";
        switch(data.get(info.position).getIdListaTipo().intValue()) {
            case 0:
                title = GlobalConstants.AMENAZAS_TIPO_DESASTRES_NATURALES[data.get(info.position).getIdTipo().intValue()];
                break;
            case 1:
                title = GlobalConstants.AMENAZAS_TIPO_ORIGEN_INDUSTRIAL[data.get(info.position).getIdTipo().intValue()];
                break;
            case 2:
                title = GlobalConstants.AMENAZAS_TIPO_ERRORES_FALLOS_NO_INTENCIONADOS[data.get(info.position).getIdTipo().intValue()];
                break;
            case 3:
                title = GlobalConstants.AMENAZAS_TIPO_ATAQUES_DELIBERADOS[data.get(info.position).getIdTipo().intValue()];
                break;
        }
        menu.setHeaderTitle(title);
        inflater.inflate(R.menu.menu_editar_borrar, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        final int index = info.position;
        switch (item.getItemId()) {
            case R.id.menuOpcBorrar:
                Log.d("Posicion", Integer.toString(index));
                /*service.eliminarAmenaza(data.get(index).getIdListaTipo(), data.get(index).getIdTipo(), idProyectoActivo);
                data.remove(index);
                adaptador.notifyDataSetChanged();
                comprobarElementosNavView(data,navView);*/
                AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
                dialog.setTitle("Confirmación");
                dialog.setMessage("¿Está seguro de querer eliminar este tipo de amenazas?");
                dialog.setCancelable(false);
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int buttonId) {
                                Log.i("Dialogos", "Confirmacion Aceptada.");
                                service.eliminarAmenaza(data.get(index).getIdListaTipo(), data.get(index).getIdTipo(), idProyectoActivo);
                                data.remove(index);
                                comprobarElementosNavView(data,navView);
                                adaptador.notifyDataSetChanged();
                                dialog.cancel();
                            }
                        });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int buttonId) {
                                Log.i("Dialogos", "Confirmacion Cancelada.");
                                dialog.cancel();
                            }
                        });
                dialog.show();
                break;
            case R.id.menuOpcEditar:
                Intent intent = new Intent(getActivity(), AddEditThreatActivity.class);
                intent.putExtra("idListaTipoAmenaza", data.get(index).getIdListaTipo()); //Optional parameters
                intent.putExtra("idTipoAmenaza", data.get(index).getIdTipo()); //Optional parameters
                intent.putExtra("idProyecto", idProyectoActivo);
                startActivityForResult(intent, GlobalConstants.REQUEST_CODE_EDIT_ACTIVITY);
                break;
        }

        return true;
    }

    private void comprobarElementosNavView(List<ThreatDTO> assets, NavigationView navView) {

        if (assets.isEmpty()) {
            navView.getMenu().findItem(R.id.menuSalvaguardas).setEnabled(false);
        } else {
            navView.getMenu().findItem(R.id.menuSalvaguardas).setEnabled(true);
        }
    }


}
