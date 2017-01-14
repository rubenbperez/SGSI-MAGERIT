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

import java.util.ArrayList;
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
    private Long idListaTipoAmenazaRecibido;
    private Long idTipoAmenazaRecibido;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;
    private ListView lstOpcionesDesastresNaturales;
    private Integer itemCheckedDesastresNaturales = null;
    private ListView lstOpcionesOrigenIndustrial;
    private Integer itemCheckedOrigenIndustrial = null;
    private ListView lstOpcionesErroresFallos;
    private Integer itemCheckedErroresFallos = null;
    private ListView lstOpcionesAtaquesDeliberados;
    private Integer itemCheckedAtaquesDeliberados = null;

    private ListView lstOpcionesEdicion;

    private List<String> listaAmenazasDesastresNaturales;
    private List<String> listaAmenazasOrigenIndustrial;
    private List<String> listaAmenazasErroresFallos;
    private List<String> listaAmenazasAtaquesDeliberados;

    private List<String> listaEdicion;

    ModelService service;

    protected List<String> getListaAmenazasDesastresNaturales() {
        return listaAmenazasDesastresNaturales;
    }

    protected List<String> getListaAmenazasOrigenIndustrial() {
        return listaAmenazasOrigenIndustrial;
    }

    protected List<String> getListaAmenazasErroresFallos() {
        return listaAmenazasErroresFallos;
    }

    protected List<String> getListaAmenazasAtaquesDeliberados() {
        return listaAmenazasAtaquesDeliberados;
    }


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
            this.idListaTipoAmenazaRecibido = args.getLong("idListaTipoAmenaza", GlobalConstants.NULL_ID_LISTA_TIPO_AMENAZA);
            this.idTipoAmenazaRecibido = args.getLong("idTipoAmenaza", GlobalConstants.NULL_ID_LISTA_TIPO_AMENAZA);
        //EN caso de ser una edición, existe una lista extra donde se muestran las amenazas.
        }

        // Añadimos el Spinner
        spinner = (Spinner) view.findViewById(R.id.spnMySpinner);
        spinnerAdapter =  ArrayAdapter.createFromResource(getContext(), R.array.Sizing_spinner_Threats,
                android.R.layout.simple_spinner_item);

        if (idListaTipoAmenazaRecibido != GlobalConstants.NULL_ID_LISTA_TIPO_AMENAZA
                && idListaTipoAmenazaRecibido != GlobalConstants.NULL_ID_LISTA_TIPO_AMENAZA) {

            listaEdicion = new ArrayList<>();
            switch(idListaTipoAmenazaRecibido.intValue()) {
                case 0:
                    listaEdicion.add(GlobalConstants.AMENAZAS_TIPO_DESASTRES_NATURALES[idTipoAmenazaRecibido.intValue()]);
                    break;
                case 1:
                    listaEdicion.add(GlobalConstants.AMENAZAS_TIPO_ORIGEN_INDUSTRIAL[idTipoAmenazaRecibido.intValue()]);
                    break;
                case 2:
                    listaEdicion.add(GlobalConstants.AMENAZAS_TIPO_ERRORES_FALLOS_NO_INTENCIONADOS[idTipoAmenazaRecibido.intValue()]);
                    break;
                case 3:
                    listaEdicion.add(GlobalConstants.AMENAZAS_TIPO_ATAQUES_DELIBERADOS[idTipoAmenazaRecibido.intValue()]);
                    break;
            }

            lstOpcionesEdicion = (ListView) view.findViewById(R.id.ListEdicion);
            lstOpcionesEdicion.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.GONE);
            ArrayAdapter<String> lstEdicion = new ArrayAdapter<String>(this.getContext(),
                    android.R.layout.simple_list_item_single_choice, listaEdicion);
            lstOpcionesEdicion.setAdapter(lstEdicion);
            lstOpcionesEdicion.setItemChecked(0,true);
            return view;
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
                android.R.layout.simple_list_item_single_choice, listaAmenazasAtaquesDeliberados);
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
