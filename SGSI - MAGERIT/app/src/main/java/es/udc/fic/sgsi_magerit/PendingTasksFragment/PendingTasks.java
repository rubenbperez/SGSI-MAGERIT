package es.udc.fic.sgsi_magerit.PendingTasksFragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import es.udc.fic.sgsi_magerit.AddEditAsset.AddEditAssetActivity;
import es.udc.fic.sgsi_magerit.AddEditAssetSafeguards.AddEditAssetSafeguardsActivity;
import es.udc.fic.sgsi_magerit.AddEditAssetThreats.AddEditAssetThreatsActivity;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.Model.PendingTasks.PendingTaskDTO;
import es.udc.fic.sgsi_magerit.Model.Project.Project;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;


public class PendingTasks extends Fragment {

    private ListView lstOpciones;
    private ModelServiceImpl service;
    private List<PendingTaskDTO> data;
    private Long idProyecto;
    private PendingTaskAdapter adaptador;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pending_tasks, container, false);

        service = new ModelServiceImpl(getContext(), GlobalConstants.DATABASE_NAME,1);
        idProyecto = service.obtenerIdProyectoActivo();
        data = service.obtenerTareasPendientes(idProyecto);

        Collections.sort(data,new Comparator() {

            public int compare(Object o1, Object o2) {

                Long x1 = ((PendingTaskDTO) o1).getIdActivo();
                Long x2 = ((PendingTaskDTO) o2).getIdActivo();
                int sComp = x1.compareTo(x2);

                if (sComp != 0) {
                    return sComp;
                } else {
                    Integer x3 = ((PendingTaskDTO) o1).getIdTipo();
                    Integer x4 = ((PendingTaskDTO) o2).getIdTipo();
                    return x3.compareTo(x4);
                }
            }});

        PendingTaskAdapter adaptador =
                new PendingTaskAdapter(this.getContext(), data);

        lstOpciones = (ListView)view.findViewById(R.id.LstPendingTasks);
        lstOpciones.setAdapter(adaptador);

        registerForContextMenu(lstOpciones);
        return view;
    }


    public class PendingTaskAdapter extends ArrayAdapter<PendingTaskDTO> {

        public PendingTaskAdapter(Context context, List<PendingTaskDTO> data) {
            super(context, android.R.layout.simple_list_item_single_choice, data);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());

            View item = inflater.inflate(R.layout.listitem_pendingtasks, null);
            TextView lblAssetName = (TextView) item.findViewById(R.id.asset);
            TextView lblThreatName = (TextView) item.findViewById(R.id.threat);
            TextView lblSafeguardName = (TextView) item.findViewById(R.id.safeguard);

            lblAssetName.setText(data.get(position).getNombreActivo());

            if (data.get(position).getIdTipo() == 1 || data.get(position).getIdTipo() == 2) {
                LinearLayout linearThreat = (LinearLayout) item.findViewById(R.id.linearThreat);
                linearThreat.setVisibility(View.VISIBLE);
                lblThreatName.setText(data.get(position).getNombreAmenaza());
            }

            if (data.get(position).getIdTipo() == 2) {
                LinearLayout linearSafeguard = (LinearLayout) item.findViewById(R.id.linearSafeguard);
                linearSafeguard.setVisibility(View.VISIBLE);
                lblSafeguardName.setText(data.get(position).getNombreSalvaguarda());
            }

            TextView lblPendingTask = (TextView) item.findViewById(R.id.pending_task);
            String text = getPendingTaskText(data.get(position));
            lblPendingTask.setText(text);

            return(item);
        }

    }

    private String getPendingTaskText(PendingTaskDTO task) {

        String text = "?";

        switch (task.getCausa()) {

            case 0: //Sin valoraciones
                if (task.getIdTipo() == 0)
                    text = "El Activo no ha recibido ninguna valoración.";
                if (task.getIdTipo() == 1)
                    text = "La Amenaza no ha recibido ninguna valoración.";
                if (task.getIdTipo() == 2)
                    text = "La Salvaguarda no ha recibido ninguna valoración.";
                break;

            case 1:
                text = "El Activo no tiene amenazas definidas.";
                break;

            case 2:
                text = "La amenaza no tiene degradación en alguna de sus valoraciones pero sí tiene probabilidad.";
                break;

            case 3:
                text = "La Amenaza no tiene probabilidad en alguna de sus valoraciones pero sí tiene degradación.";
                break;

            case 4:
                text = "La Amenaza no tiene Salvaguardas definidas.";
                break;

            case 5:
                text = "La Salvaguarda no tiene definido un tipo de protección.";
                break;
        }

        return text;
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
        inflater.inflate(R.menu.menu_resolver, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        final int index = info.position;
        switch (item.getItemId()) {
            case R.id.menuOpcResolver:

                switch(data.get(index).getIdTipo().intValue()) {
                    case 0:
                        if (data.get(index).getCausa() == 0) {
                            Intent intent = new Intent(getActivity(), AddEditAssetActivity.class);
                            intent.putExtra("idActivo", data.get(index).getIdActivo()); //Optional parameters
                            intent.putExtra("idProyecto", idProyecto);
                            startActivityForResult(intent, GlobalConstants.REQUEST_CODE_EDIT_ACTIVITY);
                        } else {
                            Intent intent = new Intent(getActivity(), AddEditAssetThreatsActivity.class);
                            intent.putExtra("idActivo", data.get(index).getIdActivo()); //Optional parameters
                            intent.putExtra("idProyecto", idProyecto);
                            startActivityForResult(intent, GlobalConstants.REQUEST_CODE_EDIT_ACTIVITY);
                        }
                        break;
                    case 1:
                        if (data.get(index).getCausa() != 4) {
                            Intent intent = new Intent(getActivity(), AddEditAssetThreatsActivity.class);
                            intent.putExtra("idActivo", data.get(index).getIdActivo()); //Optional parameters
                            intent.putExtra("idProyecto", idProyecto);
                            startActivityForResult(intent, GlobalConstants.REQUEST_CODE_EDIT_ACTIVITY);
                        } else {
                            Intent intent = new Intent(getActivity(), AddEditAssetSafeguardsActivity.class);
                            intent.putExtra("idActivo", data.get(index).getIdActivo()); //Optional parameters
                            intent.putExtra("idProyecto", idProyecto);
                            startActivityForResult(intent, GlobalConstants.REQUEST_CODE_EDIT_ACTIVITY);
                        }
                        break;
                    case 2:
                        Intent intent = new Intent(getActivity(), AddEditAssetSafeguardsActivity.class);
                        intent.putExtra("idActivo", data.get(index).getIdActivo()); //Optional parameters
                        intent.putExtra("idProyecto", idProyecto);
                        startActivityForResult(intent, GlobalConstants.REQUEST_CODE_EDIT_ACTIVITY);
                        break;
                }
                break;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent datas) {
        super.onActivityResult(requestCode, resultCode, datas);

        if (GlobalConstants.REQUEST_CODE_ADD_ACTIVITY == requestCode ||
                GlobalConstants.REQUEST_CODE_EDIT_ACTIVITY == requestCode) {

            if (resultCode == 1) {
                data.clear();
                data.addAll(data = service.obtenerTareasPendientes(idProyecto));
                adaptador.notifyDataSetChanged();
            }

            if (resultCode == 0)
                return;
        }
    }


}
