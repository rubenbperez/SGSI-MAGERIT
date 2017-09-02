package es.udc.fic.sgsi_magerit.SafeguardsFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.List;

import es.udc.fic.sgsi_magerit.AddEditSafeguards.AddEditSafeguardActivity;
import es.udc.fic.sgsi_magerit.Model.Asset.AssetDTO;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.Model.Safeguard.SafeguardDTO;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;

public class Safeguards extends Fragment {

    private FloatingActionButton fabButton;
    private ListView lstOpciones;
    private List<SafeguardDTO> data;
    private ModelServiceImpl service;
    private SafeguardAdapter adaptador;
    private long idProyectoActivo;
    private NavigationView navView;

    public Safeguards() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_safeguards, container, false);
        service = new ModelServiceImpl(getContext(), GlobalConstants.DATABASE_NAME, 1);
        navView = (NavigationView) getActivity().findViewById(R.id.navview);

        idProyectoActivo = service.obtenerIdProyectoActivo();

        data = service.obtenerSalvaguardas(idProyectoActivo);
        adaptador = new SafeguardAdapter(this.getContext(), data);

        lstOpciones = (ListView) view.findViewById(R.id.LstOpciones);
        lstOpciones.setAdapter(adaptador);

        fabButton = (FloatingActionButton) view.findViewById(R.id.fab);
        fabButton.setBackgroundTintList(
                getResources().getColorStateList(R.color.fab_color));

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddEditSafeguardActivity.class);
                intent.putExtra("idProyecto", idProyectoActivo);
                startActivityForResult(intent, GlobalConstants.REQUEST_CODE_ADD_ACTIVITY);
            }
        });
        registerForContextMenu(lstOpciones);
        return view;
    }

    public class SafeguardAdapter extends ArrayAdapter<SafeguardDTO> {

        public SafeguardAdapter(Context context, List<SafeguardDTO> data) {
            super(context, android.R.layout.simple_list_item_single_choice, data);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());

            View item = inflater.inflate(R.layout.listitem_safeguards, null);
            SafeguardDTO safeguard = data.get(position);
            String codeName = obtenerCodidoYNombreSalvaguarda(safeguard);
            String safeguardType = GlobalConstants.TIPO_SALVAGUARDAS[safeguard.getIdListaTipo().intValue()];

            TextView lblSafeguardNameAndCode = (TextView) item.findViewById(R.id.safeguard_code_name);
            lblSafeguardNameAndCode.setText(codeName);

            TextView lblSafeguardType = (TextView) item.findViewById(R.id.safeguard_type);

            if (safeguardType != null && !safeguardType.isEmpty())
                lblSafeguardType.setText(safeguardType);
            else
                lblSafeguardType.setVisibility(View.GONE);
            return (item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();

        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) menuInfo;

        String title = obtenerCodidoYNombreSalvaguarda(data.get(info.position));

        menu.setHeaderTitle(title);
        inflater.inflate(R.menu.menu_editar_borrar, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int index = info.position;
        switch (item.getItemId()) {
            case R.id.menuOpcBorrar:
                AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
                dialog.setTitle("Confirmación");
                dialog.setMessage("¿Está seguro de querer eliminar este tipo de salvaguardas?");
                dialog.setCancelable(false);
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int buttonId) {
                                Log.i("Dialogos", "Confirmacion Aceptada.");
                                service.eliminarSalvaguardaPorTipo(data.get(index).getIdListaTipo(), data.get(index).getIdTipo(), idProyectoActivo);
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
                Intent intent = new Intent(getActivity(), AddEditSafeguardActivity.class);
                intent.putExtra("idListaTipoSalvaguarda", data.get(index).getIdListaTipo()); //Optional parameters
                intent.putExtra("idTipoSalvaguarda", data.get(index).getIdTipo()); //Optional parameters
                intent.putExtra("idProyecto", idProyectoActivo);
                startActivityForResult(intent, GlobalConstants.REQUEST_CODE_EDIT_ACTIVITY);
                break;
        }

        return true;
    }

    private void comprobarElementosNavView(List<SafeguardDTO> safeguard, NavigationView navView) {

        comprobarElementosNavViewGenerico(service.obtenerActivos(idProyectoActivo),navView, R.id.menuAmenazas); //Amenazas
        comprobarElementosNavViewGenerico(service.obtenerAmenazas(idProyectoActivo),navView, R.id.menuSalvaguardas); //salvaguardas
        comprobarElementosNavViewGenerico(service.obtenerActivos(idProyectoActivo),navView, R.id.menuAnalisis); //Analisis
        comprobarElementosNavViewGenerico(service.obtenerActivos(idProyectoActivo),navView, R.id.menuTareasPendientes); //Tareas Pendientes
    }

    private void comprobarElementosNavViewGenerico(List<?> lst, NavigationView navView, Integer menu) {
        if (lst.isEmpty()) {
            navView.getMenu().findItem(menu).setEnabled(false);
        } else {
            navView.getMenu().findItem(menu).setEnabled(true);
        }

    }

    private String obtenerCodidoYNombreSalvaguarda(SafeguardDTO safeguard) {
        String codeName = "";
        switch (safeguard.getIdListaTipo().intValue()) {
            case 0:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCIONES_GENERALES[safeguard.getIdTipo().intValue()];
                break;
            case 1:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_DATOS[safeguard.getIdTipo().intValue()];
                break;
            case 2:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_CLAVES_CRIPTO[safeguard.getIdTipo().intValue()];
                break;
            case 3:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_SERVICIOS[safeguard.getIdTipo().intValue()];
                break;
            case 4:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_SW[safeguard.getIdTipo().intValue()];
                break;
            case 5:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_HW[safeguard.getIdTipo().intValue()];
                break;
            case 6:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_COMS[safeguard.getIdTipo().intValue()];
                break;
            case 7:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_INTERCONEXION[safeguard.getIdTipo().intValue()];
                break;
            case 8:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_SOPORTES[safeguard.getIdTipo().intValue()];
                break;
            case 9:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_AUXILIAR[safeguard.getIdTipo().intValue()];
                break;
            case 10:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_INSTALACIONES[safeguard.getIdTipo().intValue()];
                break;
            case 11:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_PERSONAL[safeguard.getIdTipo().intValue()];
                break;
            case 12:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_ORGANIZATIVO[safeguard.getIdTipo().intValue()];
                break;
            case 13:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_CONTINUIDAD_OPERACIONES[safeguard.getIdTipo().intValue()];
                break;
            case 14:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_EXTERNALIZACION[safeguard.getIdTipo().intValue()];
                break;
            case 15:
                codeName = GlobalConstants.SALVAGUARDA_TIPO_ADQUISICION_DESARROLLO[safeguard.getIdTipo().intValue()];
                break;
        }
        return codeName;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent datas) {
        super.onActivityResult(requestCode, resultCode, datas);

        if (GlobalConstants.REQUEST_CODE_ADD_ACTIVITY == requestCode)  {

            if (resultCode == 1) {
                data.clear();
                data.addAll(service.obtenerSalvaguardas(idProyectoActivo));
                adaptador.notifyDataSetChanged();
            }

            if (resultCode == 0)
                return;
        }
    }

}
