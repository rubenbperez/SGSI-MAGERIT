package es.udc.fic.sgsi_magerit.AddEditAssetThreats;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import es.udc.fic.sgsi_magerit.Model.ModelService.ModelService;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.Model.Threat.ThreatDTO;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;


public class IdentifyThreatsAssetFragment extends Fragment {

    private Long idProyecto;
    private Long idActivo;

    private Spinner spinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;
    private ListView lstOpcionesDesastresNaturales;
    private ListView lstOpcionesOrigenIndustrial;
    private ListView lstOpcionesErroresFallos;
    private ListView lstOpcionesAtaquesDeliberados;

    private List<String> listaAmenazasDesastresNaturales;
    private List<String> listaAmenazasOrigenIndustrial;
    private List<String> listaAmenazasErroresFallos;
    private List<String> listaAmenazasAtaquesDeliberados;

    private List<ThreatDTO> data;

    ModelService service;

    public ListView getLstOpcionesDesastresNaturales() {
        return lstOpcionesDesastresNaturales;
    }

    public ListView getLstOpcionesOrigenIndustrial() {
        return lstOpcionesOrigenIndustrial;
    }

    public ListView getLstOpcionesErroresFallos() {
        return lstOpcionesErroresFallos;
    }

    public ListView getLstOpcionesAtaquesDeliberados() {
        return lstOpcionesAtaquesDeliberados;
    }

    public IdentifyThreatsAssetFragment() {
    }

    public static IdentifyThreatsAssetFragment newInstance() {
        IdentifyThreatsAssetFragment fragment = new IdentifyThreatsAssetFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_identify_threats_asset, container, false);

        service = new ModelServiceImpl(this.getActivity(), GlobalConstants.DATABASE_NAME,1);
        Bundle args = getArguments();
        if(args != null){
            this.idProyecto = args.getLong("idProyecto");
            this.idActivo = args.getLong("idActivo");
        }

        // Añadimos el Spinner
        spinner = (Spinner) view.findViewById(R.id.spnMySpinner);
        spinnerAdapter =  ArrayAdapter.createFromResource(getContext(), R.array.Sizing_spinner_Threats,
                android.R.layout.simple_spinner_item);

        List<ThreatDTO> listaAmenazasCreadas = service.obtenerAmenazas(idProyecto);
        listaAmenazasDesastresNaturales = new LinkedList<String>(Arrays.asList(GlobalConstants.AMENAZAS_TIPO_DESASTRES_NATURALES));
        listaAmenazasOrigenIndustrial = new LinkedList<String>(Arrays.asList(GlobalConstants.AMENAZAS_TIPO_ORIGEN_INDUSTRIAL));
        listaAmenazasErroresFallos = new LinkedList<String>(Arrays.asList(GlobalConstants.AMENAZAS_TIPO_ERRORES_FALLOS_NO_INTENCIONADOS));
        listaAmenazasAtaquesDeliberados = new LinkedList<String>(Arrays.asList(GlobalConstants.AMENAZAS_TIPO_ATAQUES_DELIBERADOS));

        // Lista de Desastres Naturales
        lstOpcionesDesastresNaturales = (ListView) view.findViewById(R.id.ListDesastresNaturales);
        ArrayAdapter<String> lstDesastresNaturales = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_list_item_multiple_choice, listaAmenazasDesastresNaturales);
        lstOpcionesDesastresNaturales.setAdapter(lstDesastresNaturales);

        // Lista de Origen Industrial
        lstOpcionesOrigenIndustrial = (ListView) view.findViewById(R.id.ListOrigenIndustrial);
        ArrayAdapter<String> lstOrigenIndustrial = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_list_item_multiple_choice, listaAmenazasOrigenIndustrial);
        lstOpcionesOrigenIndustrial.setAdapter(lstOrigenIndustrial);

        // Lista de Errores y Fallos no intencionados
        lstOpcionesErroresFallos = (ListView) view.findViewById(R.id.ListErroresFallos);
        ArrayAdapter<String> lstErroresFallos = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_list_item_multiple_choice, listaAmenazasErroresFallos);
        lstOpcionesErroresFallos.setAdapter(lstErroresFallos);

        // Lista de Ataques deliberados
        lstOpcionesAtaquesDeliberados = (ListView) view.findViewById(R.id.ListAtaquesDeliberados);
        final ArrayAdapter<String> lstAtaquesDeliberados = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_list_item_multiple_choice, listaAmenazasAtaquesDeliberados);
        lstOpcionesAtaquesDeliberados.setAdapter(lstAtaquesDeliberados);

        //CargarDatos
        data = service.obtenerTiposAmenazasDeActivo(idActivo, idProyecto);

        for (ThreatDTO threat : data) {

            switch (threat.getIdListaTipo().intValue()) {
                case 0:
                    lstOpcionesDesastresNaturales.setItemChecked(threat.getIdTipo().intValue(),true);
                    break;
                case 1:
                    lstOpcionesOrigenIndustrial.setItemChecked(threat.getIdTipo().intValue(),true);
                    break;
                case 2:
                    lstOpcionesErroresFallos.setItemChecked(threat.getIdTipo().intValue(),true);
                    break;
                case 3:
                    lstOpcionesAtaquesDeliberados.setItemChecked(threat.getIdTipo().intValue(),true);
                    break;
            }
        }

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) spinnerAdapter);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        ocultarListasMostrarLista(position);
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
        return view;
    }

    private void ocultarListasMostrarLista(int valor) {

        if (valor == 0)
            lstOpcionesDesastresNaturales.setVisibility(View.VISIBLE);
        else
            lstOpcionesDesastresNaturales.setVisibility(View.GONE);

        if (valor == 1)
            lstOpcionesOrigenIndustrial.setVisibility(View.VISIBLE);
        else
            lstOpcionesOrigenIndustrial.setVisibility(View.GONE);

        if (valor == 2)
            lstOpcionesErroresFallos.setVisibility(View.VISIBLE);
        else
            lstOpcionesErroresFallos.setVisibility(View.GONE);

        if (valor == 3)
            lstOpcionesAtaquesDeliberados.setVisibility(View.VISIBLE);
        else
            lstOpcionesAtaquesDeliberados.setVisibility(View.GONE);
    }
}
