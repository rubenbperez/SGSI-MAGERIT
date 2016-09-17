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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.udc.fic.sgsi_magerit.R;

public class IdentifyAssetTypesFragment extends Fragment {


    private Spinner spinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;

    final String[] listArchSys = new String[]{"[sap] Punto de (acceso al) servicio", "[ip] Punto de interconexión", "[ext] Proporcionado por terceros", "[other] Otro"};
    private ListView lstOpcionesArchSys;

    final String[] listDataInfo = new String[]{"[files] Ficheros", "[backup] Copias de respaldo", "[conf] Datos de configuración",
    "[int] Datos de gestión interna", "[password] Credenciales", "[auth] Datos de validación de credenciales",
    "[acl] Datos de control de accesos", "[log] Registro de actividad", "[voice] Voz", "[mult] Multimedia",
    "[source] Código fuente", "[exe] Código ejecutable", "[test] Datos de prueba", "[other] Otro"};
    private ListView lstOpcionesDataInfo;

    final String[] listServices = new String[]{"[anon] Anónimo (sin requerir identificación del usuario)",
    "[pub] al público en general (sin relación contractual)", "[ext] A usuarios externos (bajo una relación contractual)",
    "[int] interno (a usuarios de la propia organización)", "[www] World Wide Web", "[telnet] Acceso remoto a cuenta local",
    "[email] Correo electrónico", "[file] Almacenamiento de ficheros", "[ftp] Transferencia de ficheros",
    "[edi] Intercambio electrónico de datos", "[dir] Servicio de directorio", "[idm] Gestión de identidades",
    "[ipm] gestión de privilegios", "[pki] PKI - Infraestructura de clave pública"};
    private ListView lstOpcionesServices;

    final String[] listCommunicationNets = new String[]{
    "[PSTN] Red telefónica", "[ISDN] RDSI (red digital)", "[X25] X25 (red de datos)", "[ADSL] ADSL",
    "[pp] punto a punto", "[radio] Comunicaciones de radio", "[wifi] Red inalábrica", "[mobile] Telefonía móvil",
    "[sat] Por satélite", "[LAN] Red local", "[VLAN] LAN virtual", "[MAN] Red metropolitana", "[VPN] Red privada virtual",
    "[Internet] Internet", "[other] Otro"};
    private ListView lstOpcionesCommunicationNets;

    final String[] listPersonal = new String[]{
    "[ue] Usuarios externos", "[ui] Usuarios Internos", "[op] Operadores", "[adm] Administradores de sistemas",
    "[com] Administradores de comunicaciones", "[dba] Administradores de BBDD", "[sec] Administradores de seguridad",
    "[des] Desarrolladores / programadores", "[sub] Subcontratas", "[prov] Proveedores", "[other] Otro"};
    private ListView lstOpcionesPersonal;

    final String[] listOther = new String[]{
    "[other] Otro"};
    private ListView lstOpcionesOther;


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

        // Lista de arquitectura del sistema
        lstOpcionesArchSys = (ListView) view.findViewById(R.id.ListSysArch);
        ArrayAdapter<String> lstArchSysAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, listArchSys);
        lstOpcionesArchSys.setAdapter(lstArchSysAdapter);

        // Lista de Datos e Información
        lstOpcionesDataInfo = (ListView) view.findViewById(R.id.ListDataInfo);
        ArrayAdapter<String> lstDataInfoAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, listDataInfo);
        lstOpcionesDataInfo.setAdapter(lstDataInfoAdapter);

        // Lista de Servicios
        lstOpcionesServices = (ListView) view.findViewById(R.id.ListServices);
        ArrayAdapter<String> lstServices = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, listServices);
        lstOpcionesServices.setAdapter(lstServices);

        // Lista de Redes de comunicacion
        lstOpcionesCommunicationNets = (ListView) view.findViewById(R.id.ListCommunicationNet);
        ArrayAdapter<String> lstCommunicationNets = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, listCommunicationNets);
        lstOpcionesCommunicationNets.setAdapter(lstCommunicationNets);

        // Lista de Personal
        lstOpcionesPersonal = (ListView) view.findViewById(R.id.ListPersonal);
        ArrayAdapter<String> lstPersonal = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, listPersonal);
        lstOpcionesPersonal.setAdapter(lstPersonal);

        // Lista de Otro
        lstOpcionesOther = (ListView) view.findViewById(R.id.ListOther);
        ArrayAdapter<String> lstOther = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, listOther);
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
        return view;
    }

    private void ocultarListasMostrarLista(int valor) {

        if (valor == 1)
            lstOpcionesArchSys.setVisibility(View.VISIBLE);
        else
            lstOpcionesArchSys.setVisibility(View.GONE);

        if (valor == 2)
            lstOpcionesDataInfo.setVisibility(View.VISIBLE);
        else
            lstOpcionesDataInfo.setVisibility(View.GONE);

        if (valor == 4)
            lstOpcionesServices.setVisibility(View.VISIBLE);
        else
            lstOpcionesServices.setVisibility(View.GONE);

        if (valor == 7)
            lstOpcionesCommunicationNets.setVisibility(View.VISIBLE);
        else
            lstOpcionesCommunicationNets.setVisibility(View.GONE);

        if (valor == 11)
            lstOpcionesPersonal.setVisibility(View.VISIBLE);
        else
            lstOpcionesPersonal.setVisibility(View.GONE);

        if (valor == 12)
            lstOpcionesOther.setVisibility(View.VISIBLE);
        else
            lstOpcionesOther.setVisibility(View.GONE);



    }
}
