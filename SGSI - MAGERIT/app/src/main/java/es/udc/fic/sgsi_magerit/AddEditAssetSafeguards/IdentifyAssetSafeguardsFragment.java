package es.udc.fic.sgsi_magerit.AddEditAssetSafeguards;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
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

import es.udc.fic.sgsi_magerit.Model.ModelService.ModelService;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.Model.Safeguard.SafeguardDTO;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;


public class IdentifyAssetSafeguardsFragment extends Fragment {

    private Long idProyecto;
    private Long idActivo;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;
    private ListView lstOpcionesProtecGenerales;
    private Integer itemCheckedProtecGenerales;
    private ListView lstOpcionesProtecDatos;
    private Integer itemCheckedProtecDatos;
    private ListView lstOpcionesProtecCrypto;
    private Integer itemCheckedProtecCrypto;
    private ListView lstOpcionesProtecServices;
    private Integer itemCheckedProtecServices;
    private ListView lstOpcionesProtecSW;
    private Integer itemCheckedProtecSW;
    private ListView lstOpcionesProtecHW;
    private Integer itemCheckedProtecHW;
    private ListView lstOpcionesProtecComs;
    private Integer itemCheckedProtecComs;
    private ListView lstOpcionesProtecIntercon;
    private Integer itemCheckedProtecIntercon;
    private ListView lstOpcionesProtecSop;
    private Integer itemCheckedProtecSop;
    private ListView lstOpcionesProtecAux;
    private Integer itemCheckedProtecAux;
    private ListView lstOpcionesProtecInst;
    private Integer itemCheckedProtecInst;
    private ListView lstOpcionesProtecPers;
    private Integer itemCheckedProtecPers;
    private ListView lstOpcionesProtecOrg;
    private Integer itemCheckedProtecOrg;
    private ListView lstOpcionesProtecContOps;
    private Integer itemCheckedProtecContOps;
    private ListView lstOpcionesProtecExtern;
    private Integer itemCheckedProtecExtern;
    private ListView lstOpcionesProtecAdqDes;
    private Integer itemCheckedProtecAdqDes;
    private ListView lstOpcionesEdits;

    private List<String> listaProteccionesGenerales;
    private List<String> listaProteccionDatos;
    private List<String> listaProteccionCrypto;
    private List<String> listaProteccionServicios;
    private List<String> listaProteccionSW;
    private List<String> listaProteccionHW;
    private List<String> listaProteccionComs;
    private List<String> listaProteccionIntercon;
    private List<String> listaProteccionSoporte;
    private List<String> listaProteccionAux;
    private List<String> listaProteccionInst;
    private List<String> listaProteccionPers;
    private List<String> listaProteccionOrg;
    private List<String> listaProteccionContOps;
    private List<String> listaProteccionExtern;
    private List<String> listaProteccionAdqDes;
    private List<String> listaEdit;

    ModelService service;
    
    public IdentifyAssetSafeguardsFragment() {
        // Required empty public constructor
    }


    public List<SafeguardDTO> getData () {
        List<SafeguardDTO> pairs = new ArrayList<>();
        SparseBooleanArray checked;

        checked = lstOpcionesProtecGenerales.getCheckedItemPositions();
        if (checked != null) {
            for (int i = 0; i < lstOpcionesProtecGenerales.getAdapter().getCount(); i++) {
                if (checked.get(i)) {
                    SafeguardDTO pair = new SafeguardDTO(0L, (long) i);
                    pairs.add(pair);
                }
            }
        }

        checked = lstOpcionesProtecDatos.getCheckedItemPositions();
        if (checked != null) {
            for (int i = 0; i < lstOpcionesProtecDatos.getAdapter().getCount(); i++) {
                if (checked.get(i)) {
                    SafeguardDTO pair = new SafeguardDTO(1L, (long) i);
                    pairs.add(pair);
                }
            }
        }

        checked = lstOpcionesProtecCrypto.getCheckedItemPositions();
        if (checked != null) {
            for (int i = 0; i < lstOpcionesProtecCrypto.getAdapter().getCount(); i++) {
                if (checked.get(i)) {
                    SafeguardDTO pair = new SafeguardDTO(2L, (long) i);
                    pairs.add(pair);
                }
            }
        }

        checked = lstOpcionesProtecServices.getCheckedItemPositions();
        if (checked != null) {
            for (int i = 0; i < lstOpcionesProtecServices.getAdapter().getCount(); i++) {
                if (checked.get(i)) {
                    SafeguardDTO pair = new SafeguardDTO(3L, (long) i);
                    pairs.add(pair);
                }
            }
        }

        checked = lstOpcionesProtecSW.getCheckedItemPositions();
        if (checked != null) {
            for (int i = 0; i < lstOpcionesProtecSW.getAdapter().getCount(); i++) {
                if (checked.get(i)) {
                    SafeguardDTO pair = new SafeguardDTO(4L, (long) i);
                    pairs.add(pair);
                }
            }
        }

        checked = lstOpcionesProtecHW.getCheckedItemPositions();
        if (checked != null) {
            for (int i = 0; i < lstOpcionesProtecHW.getAdapter().getCount(); i++) {
                if (checked.get(i)) {
                    SafeguardDTO pair = new SafeguardDTO(5L, (long) i);
                    pairs.add(pair);
                }
            }
        }

        checked = lstOpcionesProtecComs.getCheckedItemPositions();
        if (checked != null) {
            for (int i = 0; i < lstOpcionesProtecComs.getAdapter().getCount(); i++) {
                if (checked.get(i)) {
                    SafeguardDTO pair = new SafeguardDTO(6L, (long) i);
                    pairs.add(pair);
                }
            }
        }

        checked = lstOpcionesProtecIntercon.getCheckedItemPositions();
        if (checked != null) {
            for (int i = 0; i < lstOpcionesProtecIntercon.getAdapter().getCount(); i++) {
                if (checked.get(i)) {
                    SafeguardDTO pair = new SafeguardDTO(7L, (long) i);
                    pairs.add(pair);
                }
            }
        }

        checked = lstOpcionesProtecSop.getCheckedItemPositions();
        if (checked != null) {
            for (int i = 0; i < lstOpcionesProtecSop.getAdapter().getCount(); i++) {
                if (checked.get(i)) {
                    SafeguardDTO pair = new SafeguardDTO(8L, (long) i);
                    pairs.add(pair);
                }
            }
        }

        checked = lstOpcionesProtecAux.getCheckedItemPositions();
        if (checked != null) {
            for (int i = 0; i < lstOpcionesProtecAux.getAdapter().getCount(); i++) {
                if (checked.get(i)) {
                    SafeguardDTO pair = new SafeguardDTO(9L, (long) i);
                    pairs.add(pair);
                }
            }
        }

        checked = lstOpcionesProtecInst.getCheckedItemPositions();
        if (checked != null) {
            for (int i = 0; i < lstOpcionesProtecInst.getAdapter().getCount(); i++) {
                if (checked.get(i)) {
                    SafeguardDTO pair = new SafeguardDTO(10L, (long) i);
                    pairs.add(pair);
                }
            }
        }

        checked = lstOpcionesProtecPers.getCheckedItemPositions();
        if (checked != null) {
            for (int i = 0; i < lstOpcionesProtecPers.getAdapter().getCount(); i++) {
                if (checked.get(i)) {
                    SafeguardDTO pair = new SafeguardDTO(11L, (long) i);
                    pairs.add(pair);
                }
            }
        }

        checked = lstOpcionesProtecOrg.getCheckedItemPositions();
        if (checked != null) {
            for (int i = 0; i < lstOpcionesProtecOrg.getAdapter().getCount(); i++) {
                if (checked.get(i)) {
                    SafeguardDTO pair = new SafeguardDTO(12L, (long) i);
                    pairs.add(pair);
                }
            }
        }

        checked = lstOpcionesProtecContOps.getCheckedItemPositions();
        if (checked != null) {
            for (int i = 0; i < lstOpcionesProtecContOps.getAdapter().getCount(); i++) {
                if (checked.get(i)) {
                    SafeguardDTO pair = new SafeguardDTO(13L, (long) i);
                    pairs.add(pair);
                }
            }
        }

        checked = lstOpcionesProtecExtern.getCheckedItemPositions();
        if (checked != null) {
            for (int i = 0; i < lstOpcionesProtecExtern.getAdapter().getCount(); i++) {
                if (checked.get(i)) {
                    SafeguardDTO pair = new SafeguardDTO(14L, (long) i);
                    pairs.add(pair);
                }
            }
        }

        checked = lstOpcionesProtecAdqDes.getCheckedItemPositions();
        if (checked != null) {
            for (int i = 0; i < lstOpcionesProtecAdqDes.getAdapter().getCount(); i++) {
                if (checked.get(i)) {
                    SafeguardDTO pair = new SafeguardDTO(15L, (long) i);
                    pairs.add(pair);
                }
            }
        }
        
        return pairs;
    }

    public static IdentifyAssetSafeguardsFragment newInstance() {
        IdentifyAssetSafeguardsFragment fragment = new IdentifyAssetSafeguardsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View view = inflater.inflate(R.layout.fragment_identify_asset_safeguards, container, false);
        service = new ModelServiceImpl(this.getActivity(), GlobalConstants.DATABASE_NAME,1);
        Bundle args = getArguments();
        if(args != null){
            this.idProyecto = args.getLong("idProyecto");
            this.idActivo = args.getLong("idActivo");
        }

        listaProteccionesGenerales = new LinkedList<String>(Arrays.asList(GlobalConstants.SALVAGUARDA_TIPO_PROTECCIONES_GENERALES));
        listaProteccionDatos = new LinkedList<String>(Arrays.asList(GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_DATOS));
        listaProteccionCrypto = new LinkedList<String>(Arrays.asList(GlobalConstants.SALVAGUARDA_TIPO_CLAVES_CRIPTO));
        listaProteccionServicios = new LinkedList<String>(Arrays.asList(GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_SERVICIOS));
        listaProteccionSW = new LinkedList<String>(Arrays.asList(GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_SW));
        listaProteccionHW = new LinkedList<String>(Arrays.asList(GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_HW));
        listaProteccionComs = new LinkedList<String>(Arrays.asList(GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_COMS));
        listaProteccionIntercon = new LinkedList<String>(Arrays.asList(GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_INTERCONEXION));
        listaProteccionSoporte = new LinkedList<String>(Arrays.asList(GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_SOPORTES));
        listaProteccionAux = new LinkedList<String>(Arrays.asList(GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_AUXILIAR));
        listaProteccionInst = new LinkedList<String>(Arrays.asList(GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_INSTALACIONES));
        listaProteccionPers = new LinkedList<String>(Arrays.asList(GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_PERSONAL));
        listaProteccionOrg = new LinkedList<String>(Arrays.asList(GlobalConstants.SALVAGUARDA_TIPO_ORGANIZATIVO));
        listaProteccionContOps = new LinkedList<String>(Arrays.asList(GlobalConstants.SALVAGUARDA_TIPO_CONTINUIDAD_OPERACIONES));
        listaProteccionExtern = new LinkedList<String>(Arrays.asList(GlobalConstants.SALVAGUARDA_TIPO_EXTERNALIZACION));
        listaProteccionAdqDes = new LinkedList<String>(Arrays.asList(GlobalConstants.SALVAGUARDA_TIPO_ADQUISICION_DESARROLLO));


        // Lista de proteccionesGenerales
        lstOpcionesProtecGenerales = (ListView) view.findViewById(R.id.ListEssential);
        ArrayAdapter<String> lstEssentialAdapter = new ArrayAdapter<String>(this.getContext(), 
                android.R.layout.simple_list_item_multiple_choice, listaProteccionesGenerales);
        lstOpcionesProtecGenerales.setAdapter(lstEssentialAdapter);

        // Lista de proteccion DatosInformación
        lstOpcionesProtecDatos = (ListView) view.findViewById(R.id.ListDataInfo);
        ArrayAdapter<String> lstDataInfoAdapter = new ArrayAdapter<String>(this.getContext(), 
                android.R.layout.simple_list_item_multiple_choice, listaProteccionDatos);
        lstOpcionesProtecDatos.setAdapter(lstDataInfoAdapter);

        // Lista de protecciones criptográficas
        lstOpcionesProtecCrypto = (ListView) view.findViewById(R.id.ListCryptKeps);
        ArrayAdapter<String> lstCryptKeysAdapter = new ArrayAdapter<String>(this.getContext(), 
                android.R.layout.simple_list_item_multiple_choice, listaProteccionCrypto);
        lstOpcionesProtecCrypto.setAdapter(lstCryptKeysAdapter);

        // Lista de proteccion Servicios
        lstOpcionesProtecServices = (ListView) view.findViewById(R.id.ListServices);
        ArrayAdapter<String> lstServices = new ArrayAdapter<String>(this.getContext(), 
                android.R.layout.simple_list_item_multiple_choice, listaProteccionServicios);
        lstOpcionesProtecServices.setAdapter(lstServices);

        // Lista de proteccion Software
        lstOpcionesProtecHW = (ListView) view.findViewById(R.id.ListSoftware);
        ArrayAdapter<String> lstSoftware = new ArrayAdapter<String>(this.getContext(), 
                android.R.layout.simple_list_item_multiple_choice, listaProteccionSW);
        lstOpcionesProtecHW.setAdapter(lstSoftware);

        // Lista de proteccion Hardware
        lstOpcionesProtecComs = (ListView) view.findViewById(R.id.ListHardware);
        ArrayAdapter<String> lstHardware = new ArrayAdapter<String>(this.getContext(), 
                android.R.layout.simple_list_item_multiple_choice, listaProteccionHW);
        lstOpcionesProtecComs.setAdapter(lstHardware);

        // Lista proteccion comunicacion
        lstOpcionesProtecSW = (ListView) view.findViewById(R.id.ListCommunicationNet);
        ArrayAdapter<String> lstCommunicationNets = new ArrayAdapter<String>(this.getContext(), 
                android.R.layout.simple_list_item_multiple_choice, listaProteccionComs);
        lstOpcionesProtecSW.setAdapter(lstCommunicationNets);

        // Lista de proteccion interconexion
        lstOpcionesProtecIntercon = (ListView) view.findViewById(R.id.ListInfoSup);
        ArrayAdapter<String> interco = new ArrayAdapter<String>(this.getContext(), 
                android.R.layout.simple_list_item_multiple_choice, listaProteccionIntercon);
        lstOpcionesProtecIntercon.setAdapter(interco);

        // Lista de Proteccion soportes
        lstOpcionesProtecSop = (ListView) view.findViewById(R.id.ListAuxEquip);
        ArrayAdapter<String> lstSop = new ArrayAdapter<String>(this.getContext(), 
                android.R.layout.simple_list_item_multiple_choice, listaProteccionSoporte);
        lstOpcionesProtecSop.setAdapter(lstSop);

        // Lista de proteccion auxiliar
        lstOpcionesProtecAux = (ListView) view.findViewById(R.id.ListInstallations);
        ArrayAdapter<String> lstAux = new ArrayAdapter<String>(this.getContext(), 
                android.R.layout.simple_list_item_multiple_choice, listaProteccionAux);
        lstOpcionesProtecAux.setAdapter(lstAux);

        // Lista proteccion instalaciones
        lstOpcionesProtecInst = (ListView) view.findViewById(R.id.ListPersonal);
        ArrayAdapter<String> lstInst = new ArrayAdapter<String>(this.getContext(), 
                android.R.layout.simple_list_item_multiple_choice, listaProteccionInst);
        lstOpcionesProtecInst.setAdapter(lstInst);

        // Lista de proteccion personal
        lstOpcionesProtecPers = (ListView) view.findViewById(R.id.ListPersonal);
        ArrayAdapter<String> lstPers = new ArrayAdapter<String>(this.getContext(), 
                android.R.layout.simple_list_item_multiple_choice, listaProteccionPers);
        lstOpcionesProtecPers.setAdapter(lstPers);

        // Lista de proteccion organizativa
        lstOpcionesProtecOrg = (ListView) view.findViewById(R.id.ListOrg);
        ArrayAdapter<String> lstOrg = new ArrayAdapter<String>(this.getContext(), 
                android.R.layout.simple_list_item_multiple_choice, listaProteccionOrg);
        lstOpcionesProtecOrg.setAdapter(lstOrg);

        // Lista de proteccion cont operaciones
        lstOpcionesProtecContOps = (ListView) view.findViewById(R.id.ListContinuityOps);
        ArrayAdapter<String> lstContOps = new ArrayAdapter<String>(this.getContext(), 
                android.R.layout.simple_list_item_multiple_choice, listaProteccionContOps);
        lstOpcionesProtecContOps.setAdapter(lstContOps);

        // Lista de proteccion terternalizacion
        lstOpcionesProtecExtern = (ListView) view.findViewById(R.id.ListExtern);
        ArrayAdapter<String> lstExtern = new ArrayAdapter<String>(this.getContext(), 
                android.R.layout.simple_list_item_multiple_choice, listaProteccionExtern);
        lstOpcionesProtecExtern.setAdapter(lstExtern);

        // Lista de proteccion AdquisicionDesarrollo
        lstOpcionesProtecAdqDes = (ListView) view.findViewById(R.id.ListNewDevelop);
        ArrayAdapter<String> lstdqDes = new ArrayAdapter<String>(this.getContext(), 
                android.R.layout.simple_list_item_multiple_choice, listaProteccionAdqDes);
        lstOpcionesProtecAdqDes.setAdapter(lstdqDes);


        List<SafeguardDTO> listaSalvaguardasCreadas = service.obtenerSalvaguardasDeActivo(idProyecto, idActivo);
        for (SafeguardDTO safeguard : listaSalvaguardasCreadas) {

            switch (safeguard.getIdListaTipo().intValue()) {
                case 0:
                    lstOpcionesProtecGenerales.setItemChecked(safeguard.getIdTipo().intValue(),true);
                    break;
                case 1:
                    lstOpcionesProtecDatos.setItemChecked(safeguard.getIdTipo().intValue(),true);
                    break;
                case 2:
                    lstOpcionesProtecCrypto.setItemChecked(safeguard.getIdTipo().intValue(),true);
                    break;
                case 3:
                    lstOpcionesProtecServices.setItemChecked(safeguard.getIdTipo().intValue(),true);
                    break;
                case 4:
                    lstOpcionesProtecSW.setItemChecked(safeguard.getIdTipo().intValue(),true);
                    break;
                case 5:
                    lstOpcionesProtecHW.setItemChecked(safeguard.getIdTipo().intValue(),true);
                    break;
                case 6:
                    lstOpcionesProtecComs.setItemChecked(safeguard.getIdTipo().intValue(),true);
                    break;
                case 7:
                    lstOpcionesProtecIntercon.setItemChecked(safeguard.getIdTipo().intValue(),true);
                    break;
                case 8:
                    lstOpcionesProtecSop.setItemChecked(safeguard.getIdTipo().intValue(),true);
                    break;
                case 9:
                    lstOpcionesProtecAux.setItemChecked(safeguard.getIdTipo().intValue(),true);
                    break;
                case 10:
                    lstOpcionesProtecInst.setItemChecked(safeguard.getIdTipo().intValue(),true);
                    break;
                case 11:
                    lstOpcionesProtecPers.setItemChecked(safeguard.getIdTipo().intValue(),true);
                    break;
                case 12:
                    lstOpcionesProtecOrg.setItemChecked(safeguard.getIdTipo().intValue(),true);
                    break;
                case 13:
                    lstOpcionesProtecContOps.setItemChecked(safeguard.getIdTipo().intValue(),true);
                    break;
                case 14:
                    lstOpcionesProtecExtern.setItemChecked(safeguard.getIdTipo().intValue(),true);
                    break;
                case 15:
                    lstOpcionesProtecAdqDes.setItemChecked(safeguard.getIdTipo().intValue(),true);
                    break;
            }
        }

        // Añadimos el Spinner
        spinner = (Spinner) view.findViewById(R.id.spnMySpinner);
        spinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.Sizing_spinner_SafeguardType,
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

        /*lstOpcionesProtecGenerales.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer aux = itemCheckedProtecGenerales;
                
                if (aux != null && position == aux) {
                    lstOpcionesProtecGenerales.setItemChecked(position, false);
                    itemCheckedProtecGenerales = null;
                } else {
                    lstOpcionesProtecGenerales.setItemChecked(position, true);
                    itemCheckedProtecGenerales = position;
                }
            }
        });

        lstOpcionesProtecDatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer aux = itemCheckedProtecDatos;
                
                if (aux != null && position == aux) {
                    lstOpcionesProtecDatos.setItemChecked(position, false);
                    itemCheckedProtecDatos = null;
                } else {
                    lstOpcionesProtecDatos.setItemChecked(position, true);
                    itemCheckedProtecDatos = position;
                }
            }
        });

        lstOpcionesProtecCrypto.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer aux = itemCheckedProtecCrypto;
                
                if (aux != null && position == aux) {
                    lstOpcionesProtecCrypto.setItemChecked(position, false);
                    itemCheckedProtecCrypto = null;
                } else {
                    lstOpcionesProtecCrypto.setItemChecked(position, true);
                    itemCheckedProtecCrypto = position;
                }
            }
        });

        lstOpcionesProtecServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer aux = itemCheckedProtecServices;
                
                if (aux != null && position == aux) {
                    lstOpcionesProtecServices.setItemChecked(position, false);
                    itemCheckedProtecServices = null;
                } else {
                    lstOpcionesProtecServices.setItemChecked(position, true);
                    itemCheckedProtecServices = position;
                }
            }
        });

        lstOpcionesProtecSW.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer aux = itemCheckedProtecSW;
                
                if (aux != null && position == aux) {
                    lstOpcionesProtecSW.setItemChecked(position, false);
                    itemCheckedProtecSW = null;
                } else {
                    lstOpcionesProtecSW.setItemChecked(position, true);
                    itemCheckedProtecSW = position;
                }
            }
        });

        lstOpcionesProtecHW.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer aux = itemCheckedProtecHW;
                
                if (aux != null && position == aux) {
                    lstOpcionesProtecHW.setItemChecked(position, false);
                    itemCheckedProtecHW = null;
                } else {
                    lstOpcionesProtecHW.setItemChecked(position, true);
                    itemCheckedProtecHW = position;
                }
            }
        });

        lstOpcionesProtecComs.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer aux = itemCheckedProtecComs;
                
                if (aux != null && position == aux) {
                    lstOpcionesProtecComs.setItemChecked(position, false);
                    itemCheckedProtecComs = null;
                } else {
                    lstOpcionesProtecComs.setItemChecked(position, true);
                    itemCheckedProtecComs = position;
                }
            }
        });

        lstOpcionesProtecIntercon.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer aux = itemCheckedProtecIntercon;
                
                if (aux != null && position == aux) {
                    lstOpcionesProtecIntercon.setItemChecked(position, false);
                    itemCheckedProtecIntercon = null;
                } else {
                    lstOpcionesProtecIntercon.setItemChecked(position, true);
                    itemCheckedProtecIntercon = position;
                }
            }
        });

        lstOpcionesProtecSop.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer aux = itemCheckedProtecSop;
                
                if (aux != null && position == aux) {
                    lstOpcionesProtecSop.setItemChecked(position, false);
                    itemCheckedProtecSop = null;
                } else {
                    lstOpcionesProtecSop.setItemChecked(position, true);
                    itemCheckedProtecSop = position;
                }
            }
        });

        lstOpcionesProtecAux.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer aux = itemCheckedProtecAux;
                
                if (aux != null && position == aux) {
                    lstOpcionesProtecAux.setItemChecked(position, false);
                    itemCheckedProtecAux = null;
                } else {
                    lstOpcionesProtecAux.setItemChecked(position, true);
                    itemCheckedProtecAux = position;
                }
            }
        });

        lstOpcionesProtecInst.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer aux = itemCheckedProtecInst;
                
                if (aux != null && position == aux) {
                    lstOpcionesProtecInst.setItemChecked(position, false);
                    itemCheckedProtecInst = null;
                } else {
                    lstOpcionesProtecInst.setItemChecked(position, true);
                    itemCheckedProtecInst = position;
                }
            }
        });

        lstOpcionesProtecPers.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer aux = itemCheckedProtecPers;
                
                if (aux != null && position == aux) {
                    lstOpcionesProtecPers.setItemChecked(position, false);
                    lstOpcionesProtecGenerales = null;
                } else {
                    lstOpcionesProtecPers.setItemChecked(position, true);
                    itemCheckedProtecPers = position;
                }
            }
        });
        lstOpcionesProtecOrg.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer aux = itemCheckedProtecOrg;
                
                if (aux != null && position == aux) {
                    lstOpcionesProtecOrg.setItemChecked(position, false);
                    itemCheckedProtecOrg = null;
                } else {
                    lstOpcionesProtecOrg.setItemChecked(position, true);
                    itemCheckedProtecOrg = position;
                }
            }
        });

        lstOpcionesProtecContOps.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer aux = itemCheckedProtecOrg;
                
                if (aux != null && position == aux) {
                    lstOpcionesProtecContOps.setItemChecked(position, false);
                    itemCheckedProtecComs = null;
                } else {
                    lstOpcionesProtecContOps.setItemChecked(position, true);
                    itemCheckedProtecComs = position;
                }
            }
        });

        lstOpcionesProtecExtern.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer aux = itemCheckedProtecExtern;
                
                if (aux != null && position == aux) {
                    lstOpcionesProtecExtern.setItemChecked(position, false);
                    itemCheckedProtecExtern = null;
                } else {
                    lstOpcionesProtecExtern.setItemChecked(position, true);
                    itemCheckedProtecExtern = position;
                }
            }
        });

        lstOpcionesProtecAdqDes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer aux = itemCheckedProtecAdqDes;
                
                if (aux != null && position == aux) {
                    lstOpcionesProtecAdqDes.setItemChecked(position, false);
                    itemCheckedProtecAdqDes = null;
                } else {
                    lstOpcionesProtecAdqDes.setItemChecked(position, true);
                    itemCheckedProtecAdqDes = position;
                }
            }
        });*/
        return view;
    }

    private void ocultarListasMostrarLista(int valor) {

        if (valor == 0)
            lstOpcionesProtecGenerales.setVisibility(View.VISIBLE);
        else
            lstOpcionesProtecGenerales.setVisibility(View.GONE);

        if (valor == 1)
            lstOpcionesProtecDatos.setVisibility(View.VISIBLE);
        else
            lstOpcionesProtecDatos.setVisibility(View.GONE);

        if (valor == 2)
            lstOpcionesProtecCrypto.setVisibility(View.VISIBLE);
        else
            lstOpcionesProtecCrypto.setVisibility(View.GONE);

        if (valor == 3)
            lstOpcionesProtecServices.setVisibility(View.VISIBLE);
        else
            lstOpcionesProtecServices.setVisibility(View.GONE);

        if (valor == 4)
            lstOpcionesProtecHW.setVisibility(View.VISIBLE);
        else
            lstOpcionesProtecHW.setVisibility(View.GONE);

        if (valor == 5)
            lstOpcionesProtecComs.setVisibility(View.VISIBLE);
        else
            lstOpcionesProtecComs.setVisibility(View.GONE);

        if (valor == 6)
            lstOpcionesProtecSW.setVisibility(View.VISIBLE);
        else
            lstOpcionesProtecSW.setVisibility(View.GONE);

        if (valor == 7)
            lstOpcionesProtecIntercon.setVisibility(View.VISIBLE);
        else
            lstOpcionesProtecIntercon.setVisibility(View.GONE);

        if (valor == 8)
            lstOpcionesProtecSop.setVisibility(View.VISIBLE);
        else
            lstOpcionesProtecSop.setVisibility(View.GONE);

        if (valor == 9)
            lstOpcionesProtecAux.setVisibility(View.VISIBLE);
        else
            lstOpcionesProtecAux.setVisibility(View.GONE);

        if (valor == 10)
            lstOpcionesProtecInst.setVisibility(View.VISIBLE);
        else
            lstOpcionesProtecInst.setVisibility(View.GONE);

        if (valor == 11)
            lstOpcionesProtecPers.setVisibility(View.VISIBLE);
        else
            lstOpcionesProtecPers.setVisibility(View.GONE);

        if (valor == 12)
            lstOpcionesProtecOrg.setVisibility(View.VISIBLE);
        else
            lstOpcionesProtecOrg.setVisibility(View.GONE);

        if (valor == 13)
            lstOpcionesProtecContOps.setVisibility(View.VISIBLE);
        else
            lstOpcionesProtecContOps.setVisibility(View.GONE);

        if (valor == 14)
            lstOpcionesProtecExtern.setVisibility(View.VISIBLE);
        else
            lstOpcionesProtecExtern.setVisibility(View.GONE);

        if (valor == 15)
            lstOpcionesProtecAdqDes.setVisibility(View.VISIBLE);
        else
            lstOpcionesProtecAdqDes.setVisibility(View.GONE);
    }
}
