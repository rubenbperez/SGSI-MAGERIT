package es.udc.fic.sgsi_magerit.AddEditSafeguards;

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
import es.udc.fic.sgsi_magerit.Model.Safeguard.SafeguardDTO;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;

public class IdentifySafeguardFragment extends Fragment {

    public static IdentifySafeguardFragment newInstance() {
        IdentifySafeguardFragment fragment = new IdentifySafeguardFragment();
        return fragment;
    }
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

    ModelService service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_identify_safeguard, container, false);
        service = new ModelServiceImpl(this.getActivity(), GlobalConstants.DATABASE_NAME,1);
        Bundle args = getArguments();
        if(args != null){
            this.idProyecto = args.getLong("idProyecto");
        }


        List<SafeguardDTO> listaSalvaguardasCreadas = service.obtenerSalvaguardas(idProyecto);

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
        String codeName;
        for (SafeguardDTO safeguard : listaSalvaguardasCreadas) {

            switch (safeguard.getIdListaTipo().intValue()) {
                case 0:
                    codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCIONES_GENERALES[safeguard.getIdTipo().intValue()];
                    listaProteccionesGenerales.remove(listaProteccionesGenerales.indexOf(codeName));
                    break;
                case 1:
                    codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_DATOS[safeguard.getIdTipo().intValue()];
                    listaProteccionDatos.remove(listaProteccionDatos.indexOf(codeName));
                    break;
                case 2:
                    codeName = GlobalConstants.SALVAGUARDA_TIPO_CLAVES_CRIPTO[safeguard.getIdTipo().intValue()];
                    listaProteccionCrypto.remove(listaProteccionCrypto.indexOf(codeName));
                    break;
                case 3:
                    codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_SERVICIOS[safeguard.getIdTipo().intValue()];
                    listaProteccionServicios.remove(listaProteccionServicios.indexOf(codeName));
                    break;
                case 4:
                    codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_SW[safeguard.getIdTipo().intValue()];
                    listaProteccionSW.remove(listaProteccionSW.indexOf(codeName));
                    break;
                case 5:
                    codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_HW[safeguard.getIdTipo().intValue()];
                    listaProteccionHW.remove(listaProteccionHW.indexOf(codeName));
                    break;
                case 6:
                    codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_COMS[safeguard.getIdTipo().intValue()];
                    listaProteccionComs.remove(listaProteccionComs.indexOf(codeName));
                    break;
                case 7:
                    codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_INTERCONEXION[safeguard.getIdTipo().intValue()];
                    listaProteccionIntercon.remove(listaProteccionIntercon.indexOf(codeName));
                    break;
                case 8:
                    codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_SOPORTES[safeguard.getIdTipo().intValue()];
                    listaProteccionSoporte.remove(listaProteccionSoporte.indexOf(codeName));
                    break;
                case 9:
                    codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_AUXILIAR[safeguard.getIdTipo().intValue()];
                    listaProteccionAux.remove(listaProteccionAux.indexOf(codeName));
                    break;
                case 10:
                    codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_INSTALACIONES[safeguard.getIdTipo().intValue()];
                    listaProteccionInst.remove(listaProteccionInst.indexOf(codeName));
                    break;
                case 11:
                    codeName = GlobalConstants.SALVAGUARDA_TIPO_PROTECCION_PERSONAL[safeguard.getIdTipo().intValue()];
                    listaProteccionPers.remove(listaProteccionPers.indexOf(codeName));
                    break;
                case 12:
                    codeName = GlobalConstants.SALVAGUARDA_TIPO_ORGANIZATIVO[safeguard.getIdTipo().intValue()];
                    listaProteccionOrg.remove(listaProteccionOrg.indexOf(codeName));
                    break;
                case 13:
                    codeName = GlobalConstants.SALVAGUARDA_TIPO_CONTINUIDAD_OPERACIONES[safeguard.getIdTipo().intValue()];
                    listaProteccionContOps.remove(listaProteccionContOps.indexOf(codeName));
                    break;
                case 14:
                    codeName = GlobalConstants.SALVAGUARDA_TIPO_EXTERNALIZACION[safeguard.getIdTipo().intValue()];
                    listaProteccionExtern.remove(listaProteccionExtern.indexOf(codeName));
                    break;
                case 15:
                    codeName = GlobalConstants.SALVAGUARDA_TIPO_ADQUISICION_DESARROLLO[safeguard.getIdTipo().intValue()];
                    listaProteccionAdqDes.remove(listaProteccionAdqDes.indexOf(codeName));
                    break;

            }
        }
        
        // Lista de proteccionesGenerales
        lstOpcionesProtecGenerales = (ListView) view.findViewById(R.id.ListEssential);
        ArrayAdapter<String> lstEssentialAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_single_choice, listaProteccionesGenerales);
        lstOpcionesProtecGenerales.setAdapter(lstEssentialAdapter);

        // Lista de proteccion DatosInformación
        lstOpcionesProtecDatos = (ListView) view.findViewById(R.id.ListDataInfo);
        ArrayAdapter<String> lstDataInfoAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_single_choice, listaProteccionDatos);
        lstOpcionesProtecDatos.setAdapter(lstDataInfoAdapter);

        // Lista de protecciones criptográficas
        lstOpcionesProtecCrypto = (ListView) view.findViewById(R.id.ListCryptKeps);
        ArrayAdapter<String> lstCryptKeysAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_single_choice, listaProteccionCrypto);
        lstOpcionesProtecCrypto.setAdapter(lstCryptKeysAdapter);

        // Lista de proteccion Servicios
        lstOpcionesProtecServices = (ListView) view.findViewById(R.id.ListServices);
        ArrayAdapter<String> lstServices = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_single_choice, listaProteccionServicios);
        lstOpcionesProtecServices.setAdapter(lstServices);

        // Lista de proteccion Software
        lstOpcionesProtecHW = (ListView) view.findViewById(R.id.ListSoftware);
        ArrayAdapter<String> lstSoftware = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_single_choice, listaProteccionSW);
        lstOpcionesProtecHW.setAdapter(lstSoftware);

        // Lista de proteccion Hardware
        lstOpcionesProtecComs = (ListView) view.findViewById(R.id.ListHardware);
        ArrayAdapter<String> lstHardware = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_single_choice, listaProteccionHW);
        lstOpcionesProtecComs.setAdapter(lstHardware);

        // Lista proteccion comunicacion
        lstOpcionesProtecSW = (ListView) view.findViewById(R.id.ListCommunicationNet);
        ArrayAdapter<String> lstCommunicationNets = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_single_choice, listaProteccionComs);
        lstOpcionesProtecSW.setAdapter(lstCommunicationNets);

        // Lista de proteccion interconexion
        lstOpcionesProtecIntercon = (ListView) view.findViewById(R.id.ListInfoSup);
        ArrayAdapter<String> interco = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_single_choice, listaProteccionIntercon);
        lstOpcionesProtecIntercon.setAdapter(interco);

        // Lista de Proteccion soportes
        lstOpcionesProtecSop = (ListView) view.findViewById(R.id.ListAuxEquip);
        ArrayAdapter<String> lstSop = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_single_choice, listaProteccionSoporte);
        lstOpcionesProtecSop.setAdapter(lstSop);

        // Lista de proteccion auxiliar
        lstOpcionesProtecAux = (ListView) view.findViewById(R.id.ListInstallations);
        ArrayAdapter<String> lstAux = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_single_choice, listaProteccionAux);
        lstOpcionesProtecAux.setAdapter(lstAux);

        // Lista proteccion instalaciones
        lstOpcionesProtecInst = (ListView) view.findViewById(R.id.ListPersonal);
        ArrayAdapter<String> lstInst = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_single_choice, listaProteccionInst);
        lstOpcionesProtecInst.setAdapter(lstInst);

        // Lista de proteccion personal
        lstOpcionesProtecPers = (ListView) view.findViewById(R.id.ListPersonal);
        ArrayAdapter<String> lstPers = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_single_choice, listaProteccionPers);
        lstOpcionesProtecPers.setAdapter(lstPers);

        // Lista de proteccion organizativa
        lstOpcionesProtecOrg = (ListView) view.findViewById(R.id.ListOrg);
        ArrayAdapter<String> lstOrg = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_single_choice, listaProteccionOrg);
        lstOpcionesProtecOrg.setAdapter(lstOrg);

        // Lista de proteccion cont operaciones
        lstOpcionesProtecContOps = (ListView) view.findViewById(R.id.ListContinuityOps);
        ArrayAdapter<String> lstContOps = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_single_choice, listaProteccionContOps);
        lstOpcionesProtecContOps.setAdapter(lstContOps);

        // Lista de proteccion terternalizacion
        lstOpcionesProtecExtern = (ListView) view.findViewById(R.id.ListExtern);
        ArrayAdapter<String> lstExtern = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_single_choice, listaProteccionExtern);
        lstOpcionesProtecExtern.setAdapter(lstExtern);

        // Lista de proteccion AdquisicionDesarrollo
        lstOpcionesProtecAdqDes = (ListView) view.findViewById(R.id.ListNewDevelop);
        ArrayAdapter<String> lstdqDes = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_single_choice, listaProteccionAdqDes);
        lstOpcionesProtecAdqDes.setAdapter(lstdqDes);

        // Añadimos el Spinner
        spinner = (Spinner) view.findViewById(R.id.spnMySpinner);
        spinnerAdapter =  ArrayAdapter.createFromResource(getContext(), R.array.Sizing_spinner_SafeguardType,
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
        
        lstOpcionesProtecGenerales.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer aux = itemCheckedProtecGenerales;
                limpiarListas();
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
                limpiarListas();
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
                limpiarListas();
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
                limpiarListas();
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
                limpiarListas();
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
                limpiarListas();
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
                limpiarListas();
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
                limpiarListas();
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
                limpiarListas();
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
                limpiarListas();
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
                limpiarListas();
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
                limpiarListas();
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
                limpiarListas();
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
                limpiarListas();
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
                limpiarListas();
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
                limpiarListas();
                if (aux != null && position == aux) {
                    lstOpcionesProtecAdqDes.setItemChecked(position, false);
                    itemCheckedProtecAdqDes = null;
                } else {
                    lstOpcionesProtecAdqDes.setItemChecked(position, true);
                    itemCheckedProtecAdqDes = position;
                }
            }
        });

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

    
    private void limpiarListas () {
        lstOpcionesProtecGenerales.clearChoices();
        itemCheckedProtecGenerales = null;
        lstOpcionesProtecDatos.clearChoices();
        itemCheckedProtecDatos = null;
        lstOpcionesProtecCrypto.clearChoices();
        itemCheckedProtecCrypto = null;
        lstOpcionesProtecServices.clearChoices();
        itemCheckedProtecServices = null;
        lstOpcionesProtecSW.clearChoices();
        itemCheckedProtecSW = null;
        lstOpcionesProtecHW.clearChoices();
        itemCheckedProtecHW = null;
        lstOpcionesProtecComs.clearChoices();
        itemCheckedProtecComs = null;
        lstOpcionesProtecIntercon.clearChoices();
        itemCheckedProtecIntercon = null;
        lstOpcionesProtecSop.clearChoices();
        itemCheckedProtecSop = null;
        lstOpcionesProtecAux.clearChoices();
        itemCheckedProtecAux = null;
        lstOpcionesProtecInst.clearChoices();
        itemCheckedProtecInst = null;
        lstOpcionesProtecPers.clearChoices();
        itemCheckedProtecPers = null;
        lstOpcionesProtecOrg.clearChoices();
        itemCheckedProtecOrg = null;
        lstOpcionesProtecContOps.clearChoices();
        itemCheckedProtecContOps = null;
        lstOpcionesProtecExtern.clearChoices();
        itemCheckedProtecExtern = null;
        lstOpcionesProtecAdqDes.clearChoices();
        itemCheckedProtecAdqDes = null;
    }
    
}
