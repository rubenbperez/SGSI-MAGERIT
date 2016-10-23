package es.udc.fic.sgsi_magerit.AddEditThreat;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import es.udc.fic.sgsi_magerit.Model.Asset.AssetAssetType;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelService;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.Model.Threat.ThreatDTO;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;


public class IdentifyThreatFragment extends Fragment {

    private Long idProyecto;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;
    private ListView lstOpcionesDesastresNaturales;
    Integer itemCheckedDesastresNaturales = null;
    private ListView lstOpcionesOrigenIndustrial;
    Integer itemCheckedOrigenIndustrial = null;
    private ListView lstOpcionesErroresFallos;
    Integer itemCheckedErroresFallos = null;
    private ListView lstOpcionesAtaquesDeliberados;
    Integer itemCheckedAtaquesDeliberados = null;

    private List<String> listaAmenazasDesastresNaturales;
    private List<String> listaAmenazasOrigenIndustrial;
    private List<String> listaAmenazasErroresFallos;
    private List<String> listaAmenazasAtaquesDeliberados;

    ModelService service;

    public IdentifyThreatFragment() {
    }

    public static IdentifyThreatFragment newInstance() {
        IdentifyThreatFragment fragment = new IdentifyThreatFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_identify_threat, container, false);

        service = new ModelServiceImpl(this.getActivity(), GlobalConstants.DATABASE_NAME,1);
        Bundle args = getArguments();
        if(args != null){
            this.idProyecto = args.getLong("idProyecto");
        }

        List<ThreatDTO> listaAmenazasCreadas = service.obtenerAmenazas(idProyecto);
        listaAmenazasDesastresNaturales = new LinkedList<String>(Arrays.asList(GlobalConstants.AMENAZAS_TIPO_DESASTRES_NATURALES));
        listaAmenazasOrigenIndustrial = new LinkedList<String>(Arrays.asList(GlobalConstants.AMENAZAS_TIPO_ORIGEN_INDUSTRIAL));
        listaAmenazasErroresFallos = new LinkedList<String>(Arrays.asList(GlobalConstants.AMENAZAS_TIPO_ERRORES_FALLOS_NO_INTENCIONADOS));
        listaAmenazasAtaquesDeliberados = new LinkedList<String>(Arrays.asList(GlobalConstants.AMENAZAS_TIPO_ATAQUES_DELIBERADOS));
        String codeName = null;

        for (ThreatDTO threat : listaAmenazasCreadas) {

            switch (threat.getIdListaTipo().intValue()) {
                case 0:
                    codeName = GlobalConstants.AMENAZAS_TIPO_DESASTRES_NATURALES[threat.getIdTipo().intValue()];
                    listaAmenazasDesastresNaturales.remove(listaAmenazasDesastresNaturales.indexOf(codeName));
                    break;
                case 1:
                    codeName = GlobalConstants.AMENAZAS_TIPO_ORIGEN_INDUSTRIAL[threat.getIdTipo().intValue()];
                    listaAmenazasOrigenIndustrial.remove(listaAmenazasOrigenIndustrial.indexOf(codeName));
                    break;
                case 2:
                    codeName = GlobalConstants.AMENAZAS_TIPO_ERRORES_FALLOS_NO_INTENCIONADOS[threat.getIdTipo().intValue()];
                    listaAmenazasErroresFallos.remove(listaAmenazasErroresFallos.indexOf(codeName));
                    break;
                case 3:
                    codeName = GlobalConstants.AMENAZAS_TIPO_ATAQUES_DELIBERADOS[threat.getIdTipo().intValue()];
                    listaAmenazasAtaquesDeliberados.remove(listaAmenazasAtaquesDeliberados.indexOf(codeName));
                    break;
            }
        }

        // Añadimos el Spinner
        spinner = (Spinner) view.findViewById(R.id.spnMySpinner);
        spinnerAdapter =  ArrayAdapter.createFromResource(getContext(), R.array.Sizing_spinner_Threats,
                android.R.layout.simple_spinner_item);

        // Lista de Desastres Naturales
        lstOpcionesDesastresNaturales = (ListView) view.findViewById(R.id.ListDesastresNaturales);
        ArrayAdapter<String> lstDesastresNaturales = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_list_item_single_choice, listaAmenazasDesastresNaturales);
        lstOpcionesDesastresNaturales.setAdapter(lstDesastresNaturales);

        // Lista de Origen Industrial
        lstOpcionesOrigenIndustrial = (ListView) view.findViewById(R.id.ListOrigenIndustrial);
        ArrayAdapter<String> lstOrigenIndustrial = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_list_item_single_choice, listaAmenazasOrigenIndustrial);
        lstOpcionesOrigenIndustrial.setAdapter(lstOrigenIndustrial);

        // Lista de Errores y Fallos no intencionados
        lstOpcionesErroresFallos = (ListView) view.findViewById(R.id.ListErroresFallos);
        ArrayAdapter<String> lstErroresFallos = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_list_item_single_choice, listaAmenazasErroresFallos);
        lstOpcionesErroresFallos.setAdapter(lstErroresFallos);

        // Lista de Ataques deliberados
        lstOpcionesAtaquesDeliberados = (ListView) view.findViewById(R.id.ListAtaquesDeliberados);
        final ArrayAdapter<String> lstAtaquesDeliberados = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_list_item_single_choice, listaAmenazasErroresFallos);
        lstOpcionesAtaquesDeliberados.setAdapter(lstAtaquesDeliberados);

        // Si estaba seleccionado el item lo deseleccionamos (limpiamos)
        //al seleccionar un elemento. Limpiamos las otras listas
        lstOpcionesDesastresNaturales.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lstOpcionesOrigenIndustrial.clearChoices();
                itemCheckedOrigenIndustrial = null;
                lstOpcionesErroresFallos.clearChoices();
                itemCheckedErroresFallos = null;
                lstOpcionesAtaquesDeliberados.clearChoices();
                itemCheckedAtaquesDeliberados = null;

                if (itemCheckedDesastresNaturales != null && position == itemCheckedDesastresNaturales) {
                    lstOpcionesDesastresNaturales.setItemChecked(position, false);
                    itemCheckedDesastresNaturales = null;
                } else
                    itemCheckedDesastresNaturales = position;
            }
        });

        lstOpcionesOrigenIndustrial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lstOpcionesDesastresNaturales.clearChoices();
                itemCheckedDesastresNaturales = null;
                lstOpcionesErroresFallos.clearChoices();
                itemCheckedErroresFallos = null;
                lstOpcionesAtaquesDeliberados.clearChoices();
                itemCheckedAtaquesDeliberados = null;

                if (itemCheckedOrigenIndustrial!= null && position == itemCheckedOrigenIndustrial) {
                    lstOpcionesOrigenIndustrial.setItemChecked(position, false);
                    itemCheckedOrigenIndustrial = null;
                } else
                    itemCheckedOrigenIndustrial = position;
            }
        });

        lstOpcionesErroresFallos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lstOpcionesDesastresNaturales.clearChoices();
                itemCheckedDesastresNaturales = null;
                lstOpcionesOrigenIndustrial.clearChoices();
                itemCheckedOrigenIndustrial = null;
                lstOpcionesAtaquesDeliberados.clearChoices();
                itemCheckedAtaquesDeliberados = null;

                if (itemCheckedErroresFallos != null && position == itemCheckedErroresFallos) {
                    lstOpcionesErroresFallos.setItemChecked(position, false);
                    itemCheckedErroresFallos = null;
                } else
                    itemCheckedErroresFallos = position;
            }
        });

        lstOpcionesAtaquesDeliberados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lstOpcionesDesastresNaturales.clearChoices();
                itemCheckedDesastresNaturales = null;
                lstOpcionesOrigenIndustrial.clearChoices();
                itemCheckedOrigenIndustrial = null;
                lstOpcionesErroresFallos.clearChoices();
                itemCheckedErroresFallos = null;

                if (itemCheckedAtaquesDeliberados != null && position == itemCheckedAtaquesDeliberados) {
                    lstOpcionesAtaquesDeliberados.setItemChecked(position, false);
                    itemCheckedAtaquesDeliberados = null;
                } else
                    itemCheckedAtaquesDeliberados = position;
            }
        });

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
