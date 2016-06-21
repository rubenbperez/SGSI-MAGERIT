package es.udc.fic.sgsi_magerit.ProjectsFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.ParseException;
import java.util.List;

import es.udc.fic.sgsi_magerit.AddEditProject.AddProjectActivity;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;
import es.udc.fic.sgsi_magerit.Model.Project.Project;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;

/**
 * Created by err0r on 06/05/2016.
 */
public class Projects extends Fragment {
    private List<Project> data;

    private ListView lstOpciones;
    private FloatingActionButton fabButton;
    private ModelServiceImpl service;
    private ProjectAdapter adaptador;
    private NavigationView navView;

    public Projects() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        navView = (NavigationView)getActivity().findViewById(R.id.navview);

        service = new ModelServiceImpl(getContext(), GlobalConstants.DATABASE_NAME,1);
        try {
            data = service.obtenerProyectos();
            comprobarElementosNavView(data,navView);
        } catch (ParseException e) {
            //TODO
        }

        adaptador =
                new ProjectAdapter(this.getContext(), data);

        View view = inflater.inflate(R.layout.fragment_projects, container, false);
        lstOpciones = (ListView)view.findViewById(R.id.LstOpciones);
        lstOpciones.setAdapter(adaptador);
        fabButton = (FloatingActionButton)view.findViewById(R.id.fab);
        fabButton.setBackgroundTintList(
                getResources().getColorStateList(R.color.fab_color));

        fabButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddProjectActivity.class);
                intent.putExtra("idProyecto", GlobalConstants.NULL_ID_PROYECTO); //Optional parameters
                startActivityForResult(intent, GlobalConstants.REQUEST_CODE_ADD_ACTIVITY);
            }
        });


        lstOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                RadioButton radioButton = (RadioButton) v.findViewById(R.id.radioButton);
                Long proyectoAMarcar = data.get(position).getId();
                Long proyectoADesmarcar = null;

                for (int i = 0; i<a.getCount(); i++) {
                    if (data.get(i).getActivated()) {
                        data.get(i).setActivated(false);
                        proyectoADesmarcar = data.get(i).getId();
                        adaptador.notifyDataSetChanged();
                        break;
                    }
                }
                if (radioButton.isChecked()) {
                    data.get(position).setActivated(false);
                    radioButton.setChecked(false);
                } else {
                    radioButton.setChecked(true);
                    data.get(position).setActivated(true);
                }
                comprobarElementosNavView(data,navView);

                service.marcarProyectoComoMarcadoYEliminarAnterior(
                        proyectoADesmarcar, proyectoAMarcar);
            }
        });

        registerForContextMenu(lstOpciones);
        return view;

    }

    private void comprobarElementosNavView(List<Project> proyectos, NavigationView navView) {
        for (Project p: proyectos) {
            if (p.getActivated()) {
                navView.getMenu().findItem(R.id.menuActivos).setEnabled(true);
                break;
            } else {
                navView.getMenu().findItem(R.id.menuActivos).setEnabled(false);
            }

        }
    }

    public class ProjectAdapter extends ArrayAdapter<Project> {

        public ProjectAdapter(Context context, List<Project> data) {
            super(context, android.R.layout.simple_list_item_single_choice, data);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());

            View item = inflater.inflate(R.layout.listitem_projects, null);
            TextView lblProjectName = (TextView) item.findViewById(R.id.project_name);
            lblProjectName.setText(data.get(position).getNombre());

            TextView lblProjectDescription = (TextView) item.findViewById(R.id.project_desc);
            lblProjectDescription.setText(data.get(position).getDescripcion());

            TextView lblProjectDirector = (TextView) item.findViewById(R.id.project_dir);
            lblProjectDirector.setText(data.get(position).getDirector());

            RadioButton lblRatioButton = (RadioButton) item.findViewById(R.id.radioButton);
            lblRatioButton.setChecked(data.get(position).getActivated());
            return(item);
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

        menu.setHeaderTitle(data.get(info.position).getNombre());
        inflater.inflate(R.menu.menu_proyectos, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int index = info.position;
        switch (item.getItemId()) {
            case R.id.menuOpcBorrar:
                Log.d("Posicion", Integer.toString(index));
                service.eliminarProyectoYDimensionamiento(data.get(index).getId());
                data.remove(index);
                adaptador.notifyDataSetChanged();
                comprobarElementosNavView(data,navView);
                break;

            case R.id.menuOpcEditar:
                Intent intent = new Intent(getActivity(), AddProjectActivity.class);
                intent.putExtra("idProyecto", data.get(index).getId()); //Optional parameters
                startActivityForResult(intent, GlobalConstants.REQUEST_CODE_EDIT_ACTIVITY);
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
                try {
                    data.addAll(service.obtenerProyectos());
                } catch (ParseException e) {
                    //TODO
                }
                adaptador.notifyDataSetChanged();
                comprobarElementosNavView(data,navView);
            }

            if (resultCode == 0)
                return;
        }
    }

}