package es.udc.fic.sgsi_magerit.AddEditAsset;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import es.udc.fic.sgsi_magerit.Model.Asset.AssetAssetType;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelService;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;

public class IdentifyAssetTypesFragment extends Fragment {

    private Long idProyecto;
    private Long idActivo;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;
    private ListView lstOpcionesEssential;
    private ListView lstOpcionesArchSys;
    private ListView lstOpcionesDataInfo;
    private ListView lstOpcionesCryptKeys;
    private ListView lstOpcionesServices;
    private ListView lstOpcionesCommunicationNets;
    private ListView lstOpcionesSoftware;
    private ListView lstOpcionesHardware;
    private ListView lstOpcionesInfoSup;
    private ListView lstOpcionesAuxEquip;
    private ListView lstOpcionesInstallations;
    private ListView lstOpcionesPersonal;
    private ListView lstOpcionesOther;

    ModelService service;

    public IdentifyAssetTypesFragment() {
        // Required empty public constructor
    }

    public static IdentifyAssetTypesFragment newInstance() {
        IdentifyAssetTypesFragment fragment = new IdentifyAssetTypesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_identify_asset_types, container, false);
        service = new ModelServiceImpl(this.getActivity(), GlobalConstants.DATABASE_NAME,1);
        Bundle args = getArguments();
        if(args != null){
            this.idProyecto = args.getLong("idProyecto");
            this.idActivo = args.getLong("idActivo");
        }

        // Lista de Activos esenciales
        lstOpcionesEssential = (ListView) view.findViewById(R.id.ListEssential);
        ArrayAdapter<String> lstEssentialAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, GlobalConstants.TIPO_ESSENTIAL);
        lstOpcionesEssential.setAdapter(lstEssentialAdapter);

        // Lista de arquitectura del sistema
        lstOpcionesArchSys = (ListView) view.findViewById(R.id.ListSysArch);
        ArrayAdapter<String> lstArchSysAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, GlobalConstants.TIPO_ARCH_SYS);
        lstOpcionesArchSys.setAdapter(lstArchSysAdapter);

        // Lista de Datos e Información
        lstOpcionesDataInfo = (ListView) view.findViewById(R.id.ListDataInfo);
        ArrayAdapter<String> lstDataInfoAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, GlobalConstants.TIPO_DATA_INFO);
        lstOpcionesDataInfo.setAdapter(lstDataInfoAdapter);

        // Lista de Claves criptográficas
        lstOpcionesCryptKeys = (ListView) view.findViewById(R.id.ListCryptKeps);
        ArrayAdapter<String> lstCryptKeysAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, GlobalConstants.TIPO_CRYPT_KEYS);
        lstOpcionesCryptKeys.setAdapter(lstCryptKeysAdapter);

        // Lista de Servicios
        lstOpcionesServices = (ListView) view.findViewById(R.id.ListServices);
        ArrayAdapter<String> lstServices = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, GlobalConstants.TIPO_SERVICES);
        lstOpcionesServices.setAdapter(lstServices);

        // Lista de Software
        lstOpcionesSoftware = (ListView) view.findViewById(R.id.ListSoftware);
        ArrayAdapter<String> lstSoftware = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, GlobalConstants.TIPO_SOFTWARE);
        lstOpcionesSoftware.setAdapter(lstSoftware);

        // Lista de Hardware
        lstOpcionesHardware = (ListView) view.findViewById(R.id.ListHardware);
        ArrayAdapter<String> lstHardware = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, GlobalConstants.TIPO_HARDWARE);
        lstOpcionesHardware.setAdapter(lstHardware);

        // Lista de Redes de comunicacion
        lstOpcionesCommunicationNets = (ListView) view.findViewById(R.id.ListCommunicationNet);
        ArrayAdapter<String> lstCommunicationNets = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, GlobalConstants.TIPO_COM_NETS);
        lstOpcionesCommunicationNets.setAdapter(lstCommunicationNets);

        // Lista de Soportes de información
        lstOpcionesInfoSup = (ListView) view.findViewById(R.id.ListInfoSup);
        ArrayAdapter<String> lstInfoSup = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, GlobalConstants.TIPO_INFO_SUP);
        lstOpcionesInfoSup.setAdapter(lstInfoSup);

        // Lista de Equipamiento Auxiliar
        lstOpcionesAuxEquip = (ListView) view.findViewById(R.id.ListAuxEquip);
        ArrayAdapter<String> lstAuxEquip = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, GlobalConstants.TIPO_AUX_EQUIP);
        lstOpcionesAuxEquip.setAdapter(lstAuxEquip);

        // Lista de Instalaciones
        lstOpcionesInstallations = (ListView) view.findViewById(R.id.ListInstallations);
        ArrayAdapter<String> lstInstallations = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, GlobalConstants.TIPO_INSTALLATIONS);
        lstOpcionesInstallations.setAdapter(lstInstallations);

        // Lista de Personal
        lstOpcionesPersonal = (ListView) view.findViewById(R.id.ListPersonal);
        ArrayAdapter<String> lstPersonal = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, GlobalConstants.TIPO_PERSONAL);
        lstOpcionesPersonal.setAdapter(lstPersonal);

        // Lista de Otro
        lstOpcionesOther = (ListView) view.findViewById(R.id.ListOther);
        ArrayAdapter<String> lstOther = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, GlobalConstants.TIPO_OTHER);
        lstOpcionesOther.setAdapter(lstOther);

        // Añadimos el Spinner
        spinner = (Spinner) view.findViewById(R.id.spnMySpinner);
        spinnerAdapter =  ArrayAdapter.createFromResource(getContext(), R.array.Sizing_spinner_AssetType,
                android.R.layout.simple_spinner_item);

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

        cargarDatosEdicion(idActivo, view);
        return view;
    }

    private void ocultarListasMostrarLista(int valor) {

        if (valor == 0)
            lstOpcionesEssential.setVisibility(View.VISIBLE);
        else
            lstOpcionesEssential.setVisibility(View.GONE);

        if (valor == 1)
            lstOpcionesArchSys.setVisibility(View.VISIBLE);
        else
            lstOpcionesArchSys.setVisibility(View.GONE);

        if (valor == 2)
            lstOpcionesDataInfo.setVisibility(View.VISIBLE);
        else
            lstOpcionesDataInfo.setVisibility(View.GONE);

        if (valor == 3)
            lstOpcionesCryptKeys.setVisibility(View.VISIBLE);
        else
            lstOpcionesCryptKeys.setVisibility(View.GONE);

        if (valor == 4)
            lstOpcionesServices.setVisibility(View.VISIBLE);
        else
            lstOpcionesServices.setVisibility(View.GONE);

        if (valor == 5)
            lstOpcionesSoftware.setVisibility(View.VISIBLE);
        else
            lstOpcionesSoftware.setVisibility(View.GONE);

        if (valor == 6)
            lstOpcionesHardware.setVisibility(View.VISIBLE);
        else
            lstOpcionesHardware.setVisibility(View.GONE);

        if (valor == 7)
            lstOpcionesCommunicationNets.setVisibility(View.VISIBLE);
        else
            lstOpcionesCommunicationNets.setVisibility(View.GONE);

        if (valor == 8)
            lstOpcionesInfoSup.setVisibility(View.VISIBLE);
        else
            lstOpcionesInfoSup.setVisibility(View.GONE);

        if (valor == 9)
            lstOpcionesAuxEquip.setVisibility(View.VISIBLE);
        else
            lstOpcionesAuxEquip.setVisibility(View.GONE);

        if (valor == 10)
            lstOpcionesInstallations.setVisibility(View.VISIBLE);
        else
            lstOpcionesInstallations.setVisibility(View.GONE);

        if (valor == 11)
            lstOpcionesPersonal.setVisibility(View.VISIBLE);
        else
            lstOpcionesPersonal.setVisibility(View.GONE);

        if (valor == 12)
            lstOpcionesOther.setVisibility(View.VISIBLE);
        else
            lstOpcionesOther.setVisibility(View.GONE);
    }

    private void cargarDatosEdicion (Long idActivo, View view) {

        if (idActivo == GlobalConstants.NULL_ID_ACTIVO)
                return;

        List<AssetAssetType> tiposdeActivo = service.obtenerTiposDeActivo(idActivo);

        for (AssetAssetType at: tiposdeActivo)
        {
            switch (at.getIdListaTipo().intValue()){
                case 0:
                    lstOpcionesEssential.setItemChecked(at.getIdTipo().intValue(), true);
                    break;
                case 1:
                    lstOpcionesArchSys.setItemChecked(at.getIdTipo().intValue(), true);
                    break;
                case 2:
                    lstOpcionesDataInfo.setItemChecked(at.getIdTipo().intValue(), true);
                    break;
                case 3:
                    lstOpcionesCryptKeys.setItemChecked(at.getIdTipo().intValue(), true);
                    break;
                case 4:
                    lstOpcionesServices.setItemChecked(at.getIdTipo().intValue(), true);
                    break;
                case 5:
                    lstOpcionesSoftware.setItemChecked(at.getIdTipo().intValue(), true);
                    break;
                case 6:
                    lstOpcionesHardware.setItemChecked(at.getIdTipo().intValue(), true);
                    break;
                case 7:
                    lstOpcionesCommunicationNets.setItemChecked(at.getIdTipo().intValue(), true);
                    break;
                case 8:
                    lstOpcionesInfoSup.setItemChecked(at.getIdTipo().intValue(), true);
                    break;
                case 9:
                    lstOpcionesAuxEquip.setItemChecked(at.getIdTipo().intValue(), true);
                    break;
                case 10:
                    lstOpcionesInstallations.setItemChecked(at.getIdTipo().intValue(), true);
                    break;
                case 11:
                    lstOpcionesPersonal.setItemChecked(at.getIdTipo().intValue(), true);
                    break;
                case 12:
                    lstOpcionesOther.setItemChecked(at.getIdTipo().intValue(), true);
                    break;
            }
        }
    }

}
