package es.udc.fic.sgsi_magerit.Util;

/**
 * Created by err0r on 29/05/2016.
 */
public class GlobalConstants {
    public final static String DATABASE_NAME = "Database";
    public final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static Integer REQUEST_CODE_ADD_ACTIVITY = 1;
    public final static Integer REQUEST_CODE_EDIT_ACTIVITY = 2;
    public final static Long NULL_ID_PROYECTO = (long) 0;
    public final static Long NULL_ID_ACTIVO = (long) 0;
    public final static Long NULL_ID_LISTA_TIPO_AMENAZA = (long) -1;
    public final static String[] ID_TIPOS = {"Muy alta", "Alta", "Media", "Baja", "Muy Baja"};
    public final static String DATE_ERROR = "Ha habído un problema interno con las fechas. Inténtelo de nuevo más tarde";

    /********************************************************************************************/
    //LISTAS PARA TIPOS DE ACTIVOS

    public final static String[] TIPO_ESSENTIAL = {
            "[info][adm] Datos de interés para la Admin pública", "[info][vr] Datos vitales",
            "[info][per][A] Datos de carácter personal - Nivel Alto",
            "[info][per][M] Datos de carácter personal - Nivel Medio",
            "[info][per][B] Datos de carácter personal - Nivel Bajo",
            "[info][classified][C] Datos clasificados - Nivel Confidencial",
            "[info][classified][R] Datos clasificados - Difusión limitada",
            "[info][classified][UC] Datos clasificados - Sin Clasificar",
            "[info][classified][pub] Datos clasificados - De carácter Público",
            "[service] Servicio", "[bp] Proceso de negocio"};

    public final static String[] TIPO_ARCH_SYS = {"[sap] Punto de (acceso al) servicio",
            "[ip] Punto de interconexión", "[ext] Proporcionado por terceros", "[other] Otro"};

    public final static String[] TIPO_DATA_INFO = {"[files] Ficheros",
            "[backup] Copias de respaldo", "[conf] Datos de configuración",
            "[int] Datos de gestión interna", "[password] Credenciales",
            "[auth] Datos de validación de credenciales",
            "[acl] Datos de control de accesos", "[log] Registro de actividad", "[voice] Voz",
            "[mult] Multimedia", "[source] Código fuente", "[exe] Código ejecutable",
            "[test] Datos de prueba", "[other] Otro"};

    public final static String[] TIPO_CRYPT_KEYS = {
            "[info][encrypt][shared_secret] Claves de Cifra - Secreto compartido (clave simétrica)",
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

    public final static String[] TIPO_SERVICES = {
            "[anon] Anónimo (sin requerir identificación del usuario)",
            "[pub] al público en general (sin relación contractual)",
            "[ext] A usuarios externos (bajo una relación contractual)",
            "[int] interno (a usuarios de la propia organización)", "[www] World Wide Web",
            "[telnet] Acceso remoto a cuenta local",
            "[email] Correo electrónico", "[file] Almacenamiento de ficheros",
            "[ftp] Transferencia de ficheros",
            "[edi] Intercambio electrónico de datos", "[dir] Servicio de directorio",
            "[idm] Gestión de identidades",
            "[ipm] gestión de privilegios", "[pki] PKI - Infraestructura de clave pública"};

    public final static String[] TIPO_COM_NETS = {
            "[PSTN] Red telefónica", "[ISDN] RDSI (red digital)", "[X25] X25 (red de datos)",
            "[ADSL] ADSL", "[pp] punto a punto", "[radio] Comunicaciones de radio",
            "[wifi] Red inalábrica", "[mobile] Telefonía móvil", "[sat] Por satélite",
            "[LAN] Red local", "[VLAN] LAN virtual", "[MAN] Red metropolitana",
            "[VPN] Red privada virtual", "[Internet] Internet", "[other] Otro"};

    public final static String[] TIPO_SOFTWARE = {
            "[prp] Desarrollo propio(in house)", "[sub] Desarrollo a medida(subcontratado)",
            "[std][browser] Navegador web", "[std][www] Servidor de Presentación",
            "[std][app] Servidor de Aplicaciones", "[std][email_client] Cliente de correo electrónico",
            "[std][email_server] Servidor de correo electrónico", "[std][file] Servidor de ficheros",
            "[std][dbms] Sistema de gestión de bases de datos", "[std][tm] Monitor transaccional",
            "[std][office] Ofimática", "[std][av] Anti virus", "[std][os] Sistema Operativo",
            "[std][hypervisor] Gestor de máquinas virtuales", "[std][ts] Servidor de terminales",
            "[std][backup] Sistema de backup"};

    public final static String[] TIPO_HARDWARE = {
            "[host] Grandes equipos", "[mid] Equipos medios", "[pc] Informática personal",
            "[mobile] Informática móvil", "[pda] Agendas electrónicas", "[vhost] Equipo virtual",
            "[backup] Equipamiento de respaldo", "[cluster] Cluster", "[data] Almacén de Datos",
            "[peripheral][print] Periféricos - Medios de impresión",
            "[peripheral][scan] Periféricos - Escáneres",
            "[peripheral][crypto] Periféricos - Dispositivos Criptográficos",
            "[bp] Dispositivos de frontera", "[network][modem] Soporte de la red - Módems",
            "[network][hub] Soporte de la red - Concentradores",
            "[network][switch] Soporte de la red - Conmmutadores",
            "[network][router] Soporte de la red - Encaminadores",
            "[network][bridge] Soporte de la red - Pasarelas",
            "[network][Firewall] Soporte de la red - Cortafuegos",
            "[network][wap] Soporte de la red - Punto de acceso inalámbrico",
            "[pabx] Centralita telefónica", "[ipphone] Teléfono IP"};

    public final static String[] TIPO_INFO_SUP = {
            "[electronic][disk] Electrónicos - Discos",
            "[electronic][vdisk] Electrónicos - Discos Virtuales",
            "[electronic][san] Electrónicos - Almacenamiento en red",
            "[electronic][disquette] Electrónicos - Disquettes",
            "[electronic][cd] Electrónicos - CD-ROM", "[electronic][usb] Electrónicos - Memorias USB",
            "[electronic][dvd] Electrónicos - DVD",
            "[electronic][tape] Electrónicos - Cinta magnética",
            "[electronic][mc] Electrónicos - Tarjetas de memoria",
            "[electronic][ic] Electrónicos - Tarjetas intelogentes",
            "[non_electronic][printed] No electrónicos - Material impreso",
            "[non_electronic][tape] No electrónicos - Cinta de papel",
            "[non_electronic][film] No electrónicos - Microfilm",
            "[non_electronic][cards] Electrónicos - Tarjetas perforadas"};

    public final static String[] TIPO_AUX_EQUIP = {
            "[power] Fuentes de alimentación", "[ups] Sistemas de alimentación Ininterrumpida",
            "[gen] Generadores eléctricos", "[ac] Equipos de climatización",
            "[cabling][wire] Cableado - Cable eléctrico", "[cabling][fiber] Cableado - Fibra óptica",
            "[robot][tape] Robot - De cintas", "[robot][disk] Robot - De discos",
            "[supply] Suministros esenciales", "[Destroy] Equipos de destrucción de soportes de información",
            "[furniture] Mobiliario: armarios, etc…", "[safe] Cajas fuertes"};

    public final static String[] TIPO_INSTALLATIONS = {
            "[site] Recinto", "[building] Edificio", "[local] Cuarto",
            "[mobile][car] Plataformas móviles - Vehiculo Terrestre: Coche, camión, etc.",
            "[mobile][plane] Plataformas móviles - Vehiculo Aéreo: Avión, etc.",
            "[mobile][ship] Plataformas móviles - Vehículo marítimo: buque, lancha, etc.",
            "[mobile][shelter] Plataformas móviles - Contenedores", "[channel] Canalización",
            "[backup] Instalaciones de respaldo"
    };

    public final static String[] TIPO_PERSONAL = {
            "[ue] Usuarios externos", "[ui] Usuarios Internos", "[op] Operadores",
            "[adm] Administradores de sistemas", "[com] Administradores de comunicaciones",
            "[dba] Administradores de BBDD", "[sec] Administradores de seguridad",
            "[des] Desarrolladores / programadores", "[sub] Subcontratas", "[prov] Proveedores",
            "[other] Otro"};

    public final static String[] TIPO_OTHER = {
            "[other] Otro"};

    /********************************************************************************************/

    //LISTAS PARA AMENAZAS
    public final static String[] TIPO_AMENAZAS = {
            "[N] Desastres Naturales", "[I] De Origen Industrial", "[E] Errores y fallos no intencionados",
            "[A] Ataques Deliberados"
    };

    public final static String[] AMENAZAS_TIPO_DESASTRES_NATURALES = {
            "[N.1] Fuego", "[N.2] Daños por agua", "[N.3] Tormentas", "[N.4] Tormentas eléctricas",
            "[N.5] Huracanes", "[N.6] Terremotos", "[N.7] Tornados", "[N.8] Ciclones", "[N.9] Deslizamientos del terreno",
            "[N.10] Meteoritos", "[N.11] Tsunamis", "[N.12] Tormentas de invierno y frío extremo",
            "[N.13] Calor extremo", "[N.14] Volcanes"
    };

    public final static String[] AMENAZAS_TIPO_ORIGEN_INDUSTRIAL = {
            "[I.1] Fuego", "[I.2] Daños por agua", "[I.*] Desastres industriales", "[I.3] Contaminación mecánica",
            "[I.4] Contaminación electromagnética", "[I.5] Avería de origen físico o lógico",
            "[I.6] Corte del suministro eléctrico", "[I.7] Condiciones inadecuadas de temperatura o humedad",
            "[I.8] Fallo de servcios de comunicaciones", "[I.9] Interrupción de otros servicios y suministros esenciales",
            "[I.10] Degradación de los soportes de almacenamiento de la información",
            "[I.11] Emanaciones electromagnéticas"
    };

    public final static String[] AMENAZAS_TIPO_ERRORES_FALLOS_NO_INTENCIONADOS = {
            "[E.1] Errores de los usuarios", "[E.2] Errores del administrador", "[E.3] Errores de monitorización (log)",
            "[E.4] Errores de configuración", "[E.7] Deficiencias de la organización", "[E.8] Difusión de Software dañino",
            "[E.9] Errores de [re-]encaminamiento", "[E.10] Errores de secuencia", "[E.14] Errores de información",
            "[E.15] Alteración de la información", "[E.18] Destrucción de la información", "[E.19] Fugas de información",
            "[E.20] Vulnerabilidades de los programas (sw)", "E.20] Vulnerabilidades de los programas (sw)",
            "[E.23] Errores de mantenimiento / actualización de equipos (HW)", "[E.24] Caída del sistema por agotamiento de recursos",
            "[E.25] Pérdida de equipos", "[E.28] Indisponibilidad del personal"
    };

    public final static String[] AMENAZAS_TIPO_ATAQUES_DELIBERADOS = {
            "[A.3] Manipulación de los registros de actividad (log)", "[A.4] Manipulación de la configuración",
            "[A.5] Suplantación de la identidad del usuario", "[A.6] Abuso de privilegios de acceso",
            "[A.7] Uso no previsto", "[A.8] Difusión de Software Dañino", "[A.9] [RE-]encaminamiento de mensajes",
            "[A.10] Alteración de secuencia", "[A.11] Acceso no autorizado", "[A.12] Análisis de tráfico",
            "[A.13] Repudio", "[A.14] Interceptación de información", "[A.15] Modificación deliberada de la información",
            "[A.18] Destrucción de información", "[A.19] Divulgación de información", "[A.22] anipulación de programas",
            "[A.23] Manipulación de los equipos", "[A.24] Denegación de servicio", "[A.25] Robo", "[A.26] Ataque destructivo",
            "[A.27] Ocupación enemiga", "[A.28] Indisponibilidad del personal", "[A.29] Extorsión",
            "[A.30] Ingeniería social (picaresca)"
    };

}
