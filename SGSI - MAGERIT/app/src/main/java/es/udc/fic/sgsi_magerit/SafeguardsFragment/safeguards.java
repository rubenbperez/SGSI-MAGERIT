package es.udc.fic.sgsi_magerit.SafeguardsFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.Model.Threat.ThreatDTO;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;

public class Safeguards extends Fragment {

    private FloatingActionButton fabButton;
    private ListView lstOpciones;
    private List<ThreatDTO> data;
    private ModelServiceImpl service;
    //private SafeguardAdapter adaptador;
    private long idProyectoActivo;
    private NavigationView navView;

    public Safeguards() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_threats, container, false);
        service = new ModelServiceImpl(getContext(), GlobalConstants.DATABASE_NAME,1);
        navView = (NavigationView)getActivity().findViewById(R.id.navview);

        idProyectoActivo = service.obtenerIdProyectoActivo();

        data = service.obtenerAmenazas(idProyectoActivo);
        //adaptador = new SafeguardAdapter(this.getContext(), data);

        lstOpciones = (ListView)view.findViewById(R.id.LstOpciones);
        //lstOpciones.setAdapter(adaptador);

        fabButton = (FloatingActionButton)view.findViewById(R.id.fab);
        fabButton.setBackgroundTintList(
                getResources().getColorStateList(R.color.fab_color));

        fabButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getActivity(),AddEditThreatActivity.class);
                intent.putExtra("idProyecto", idProyectoActivo);
                startActivityForResult(intent, GlobalConstants.REQUEST_CODE_ADD_ACTIVITY);*/
            }
        });
        registerForContextMenu(lstOpciones);
        return  view;
    }

}
