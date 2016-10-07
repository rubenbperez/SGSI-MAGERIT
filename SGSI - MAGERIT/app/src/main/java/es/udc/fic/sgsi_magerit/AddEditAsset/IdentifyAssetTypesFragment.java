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

import es.udc.fic.sgsi_magerit.R;

public class IdentifyAssetTypesFragment extends Fragment {


    private Spinner spinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;

    final String[] listEssential = new String[]{"" +
            "[info][adm] Datos de interés para la Admin pública", "[info][vr] Datos vitales", "[info][per][A] Datos de carácter personal - Nivel Alto",
            "[info][per][M] Datos de carácter personal - Nivel Medio", "[info][per][B] Datos de carácter personal - Nivel Bajo",
            "[info][classified][C] Datos clasificados - Nivel Confidencial",  "[info][classified][R] Datos clasificados - Difusión limitada",
            "[info][classified][UC] Datos clasificados - Sin Clasificar",  "[info][classified][pub] Datos clasificados - De carácter Público",
            "[service] Servicio", "[bp] Proceso de negocio"};
    private ListView lstOpcionesEssential;

    final String[] listArchSys = new String[]{"[sap] Punto de (acceso al) servicio", "[ip] Punto de interconexión", "[ext] Proporcionado por terceros", "[other] Otro"};
    private ListView lstOpcionesArchSys;

    final String[] listDataInfo = new String[]{"[files] Ficheros", "[backup] Copias de respaldo", "[conf] Datos de configuración",
            "[int] Datos de gestión interna", "[password] Credenciales", "[auth] Datos de validación de credenciales",
            "[acl] Datos de control de accesos", "[log] Registro de actividad", "[voice] Voz", "[mult] Multimedia",
            "[source] Código fuente", "[exe] Código ejecutable", "[test] Datos de prueba", "[other] Otro"};
    private ListView lstOpcionesDataInfo;

    final String[] listCryptKeys = new String[]{"[info][encrypt][shared_secret] Claves de Cifra - Secreto compartido (clave simétrica)",
            "[info][encrypt][public_encryption] Claves de Cifra - Clave pública de cifra",
            "[info][encrypt][public_decryption] Claves de Cifra - Clave privada de descifrado",
            "[info][sign][shared_secret] Claves de Firma - Secreto compartido (clave simétrica)",
            "[info][sign][public_signature] Claves de Firma - Clave privada de firma",
            "[info][sign][public_verification] Claves de Firma - Clave pública de verificación de firma",
            "[com][channel] Protección de las comunicaciones - Claves de cifrado de canal",
            "[com][authentication] Protección de las comunicaciones - Claves de autenticación",
            "[com][verification] Protección de las comunicaciones - Claves de verificación de autenticación",
            "[disk][encrypt] Cifrado de soportes de la información - Claves de cifra",
            "[x509] Certificados de clave pública"};
    private ListView lstOpcionesCryptKeys;

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

    final String[] listSoftware = new String[]{
            "[prp] Desarrollo propio(in house)", "[sub] Desarrollo a medida(subcontratado)",
            "[std][browser] Navegador web", "[std][www] Servidor de Presentación","[std][app] Servidor de Aplicaciones",
            "[std][email_client] Cliente de correo electrónico", "[std][email_server] Servidor de correo electrónico",
            "[std][file] Servidor de ficheros", "[std][dbms] Sistema de gestión de bases de datos",
            "[std][tm] Monitor transaccional", "[std][office] Ofimática", "[std][av] Anti virus", "[std][os] Sistema Operativo",
            "[std][hypervisor] Gestor de máquinas virtuales", "[std][ts] Servidor de terminales", "[std][backup] Sistema de backup"};
    private ListView lstOpcionesSoftware;

    final String[] listHardware = new String[]{
            "[host] Grandes equipos", "[mid] Equipos medios", "[pc] Informática personal", "[mobile] Informática móvil",
            "[pda] Agendas electrónicas", "[vhost] Equipo virtual", "[backup] Equipamiento de respaldo", "[cluster] Cluster",
            "[data] Almacén de Datos", "[peripheral][print] Periféricos - Medios de impresión", "[peripheral][scan] Periféricos - Escáneres",
            "[peripheral][crypto] Periféricos - Dispositivos Criptográficos", "[bp] Dispositivos de frontera", "[network][modem] Soporte de la red - Módems",
            "[network][hub] Soporte de la red - Concentradores", "[network][switch] Soporte de la red - Conmmutadores",
            "[network][router] Soporte de la red - Encaminadores", "[network][bridge] Soporte de la red - Pasarelas",
            "[network][Firewall] Soporte de la red - Cortafuegos", "[network][wap] Soporte de la red - Punto de acceso inalámbrico",
            "[pabx] Centralita telefónica", "[ipphone] Teléfono IP"};
    private ListView lstOpcionesHardware;

    final String[] listInfoSup = new String[]{
            "[electronic][disk] Electrónicos - Discos", "[electronic][vdisk] Electrónicos - Discos Virtuales",
            "[electronic][san] Electrónicos - Almacenamiento en red", "[electronic][disquette] Electrónicos - Disquettes",
            "[electronic][cd] Electrónicos - CD-ROM", "[electronic][usb] Electrónicos - Memorias USB",
            "[electronic][dvd] Electrónicos - DVD", "[electronic][tape] Electrónicos - Cinta magnética",
            "[electronic][mc] Electrónicos - Tarjetas de memoria", "[electronic][ic] Electrónicos - Tarjetas intelogentes",
            "[non_electronic][printed] No electrónicos - Material impreso", "[non_electronic][tape] No electrónicos - Cinta de papel",
            "[non_electronic][film] No electrónicos - Microfilm", "[non_electronic][cards] Electrónicos - Tarjetas perforadas",
    };
    private ListView lstOpcionesInfoSup;

    final String[] listAuxEquip = new String[]{
            "[power] Fuentes de alimentación", "[ups] Sistemas de alimentación Ininterrumpida",
            "[gen] Generadores eléctricos", "[ac] Equipos de climatización", "[cabling][wire] Cableado - Cable eléctrico",
            "[cabling][fiber] Cableado - Fibra óptica", "[robot][tape] Robot - De cintas", "[robot][disk] Robot - De discos",
            "[supply] Suministros esenciales", "[Destroy] Equipos de destrucción de soportes de información",
            "[furniture] Mobiliario: armarios, etc…", "[safe] Cajas fuertes"
    };
    private ListView lstOpcionesAuxEquip;

    final String[] listInstallations = new String[]{
            "[site] Recinto", "[building] Edificio", "[local] Cuarto",
            "[mobile][car] Plataformas móviles - Vehiculo Terrestre: Coche, camión, etc.",
            "[mobile][plane] Plataformas móviles - Vehiculo Aéreo: Avión, etc.",
            "[mobile][ship] Plataformas móviles - Vehículo marítimo: buque, lancha, etc.",
            "[mobile][shelter] Plataformas móviles - Contenedores", "[channel] Canalización",
            "[backup] Instalaciones de respaldo"
    };
    private ListView lstOpcionesInstallations;

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

        // Lista de Activos esenciales
        lstOpcionesEssential = (ListView) view.findViewById(R.id.ListEssential);
        ArrayAdapter<String> lstEssentialAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, listEssential);
        lstOpcionesEssential.setAdapter(lstEssentialAdapter);

        // Lista de arquitectura del sistema
        lstOpcionesArchSys = (ListView) view.findViewById(R.id.ListSysArch);
        ArrayAdapter<String> lstArchSysAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, listArchSys);
        lstOpcionesArchSys.setAdapter(lstArchSysAdapter);

        // Lista de Datos e Información
        lstOpcionesDataInfo = (ListView) view.findViewById(R.id.ListDataInfo);
        ArrayAdapter<String> lstDataInfoAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, listDataInfo);
        lstOpcionesDataInfo.setAdapter(lstDataInfoAdapter);

        // Lista de Claves criptográficas
        lstOpcionesCryptKeys = (ListView) view.findViewById(R.id.ListCryptKeps);
        ArrayAdapter<String> lstCryptKeysAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, listCryptKeys);
        lstOpcionesCryptKeys.setAdapter(lstCryptKeysAdapter);

        // Lista de Servicios
        lstOpcionesServices = (ListView) view.findViewById(R.id.ListServices);
        ArrayAdapter<String> lstServices = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, listServices);
        lstOpcionesServices.setAdapter(lstServices);

        // Lista de Software
        lstOpcionesSoftware = (ListView) view.findViewById(R.id.ListSoftware);
        ArrayAdapter<String> lstSoftware = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, listSoftware);
        lstOpcionesSoftware.setAdapter(lstSoftware);

        // Lista de Hardware
        lstOpcionesHardware = (ListView) view.findViewById(R.id.ListHardware);
        ArrayAdapter<String> lstHardware = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, listHardware);
        lstOpcionesHardware.setAdapter(lstHardware);

        // Lista de Redes de comunicacion
        lstOpcionesCommunicationNets = (ListView) view.findViewById(R.id.ListCommunicationNet);
        ArrayAdapter<String> lstCommunicationNets = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, listCommunicationNets);
        lstOpcionesCommunicationNets.setAdapter(lstCommunicationNets);

        // Lista de Soportes de información
        lstOpcionesInfoSup = (ListView) view.findViewById(R.id.ListInfoSup);
        ArrayAdapter<String> lstInfoSup = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, listInfoSup);
        lstOpcionesInfoSup.setAdapter(lstInfoSup);

        // Lista de Equipamiento Auxiliar
        lstOpcionesAuxEquip = (ListView) view.findViewById(R.id.ListAuxEquip);
        ArrayAdapter<String> lstAuxEquip = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, listAuxEquip);
        lstOpcionesAuxEquip.setAdapter(lstAuxEquip);

        // Lista de Instalaciones
        lstOpcionesInstallations = (ListView) view.findViewById(R.id.ListInstallations);
        ArrayAdapter<String> lstInstallations = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_multiple_choice, listInstallations);
        lstOpcionesInstallations.setAdapter(lstInstallations);

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

}
