package es.udc.fic.sgsi_magerit.ThreatsFragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
        View view = inflater.inflate(R.layout.fragment_assets, container, false);
        service = new ModelServiceImpl(getContext(), GlobalConstants.DATABASE_NAME,1);
        navView = (NavigationView)getActivity().findViewById(R.id.navview);

        idProyectoActivo = service.obtenerIdProyectoActivo();

        data = service.obtenerAmenazas(idProyectoActivo);
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
                startActivityForResult(intent, GlobalConstants.REQUEST_CODE_ADD_THREAT);
            }
        });
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

}
