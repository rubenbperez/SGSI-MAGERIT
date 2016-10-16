package es.udc.fic.sgsi_magerit.AddEditAsset;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import es.udc.fic.sgsi_magerit.Model.Asset.Asset;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelService;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;

public class AddEditAssetActivity extends AppCompatActivity {

    private Long idProyecto;
    private Long idActivoRecibido;
    private ViewPager viewPager;
    private ModelService service;
    private AddEditAssetFragmentPagerAdapter adapter;

    private TextInputLayout tilNombreActivo;
    private EditText etNombreActivo;
    private TextInputLayout tilCodigoActivo;
    private EditText etCodigoActivo;
    private EditText etDescripcion;
    private EditText etResponsable;
    private EditText etUbicacion;
    private Spinner spinnerValoracionDisponibilidad;
    private Spinner spinnerValoracionIntegridad;
    private Spinner spinnerValoracionConfidencialidad;
    private Spinner spinnerValoracionAutenticidad;
    private Spinner spinnerValoracionTrazabilidad;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        service = new ModelServiceImpl(this, GlobalConstants.DATABASE_NAME,1);
        idProyecto = getIntent().getLongExtra("idProyecto", GlobalConstants.NULL_ID_PROYECTO);
        idActivoRecibido = getIntent().getLongExtra("idActivo",GlobalConstants.NULL_ID_ACTIVO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_asset);
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpagerAsset);
        adapter = new AddEditAssetFragmentPagerAdapter(
                getSupportFragmentManager(),idProyecto, idActivoRecibido);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_return);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (idActivoRecibido == GlobalConstants.NULL_ID_ACTIVO)
            getSupportActionBar().setTitle(AddEditAssetActivityConstants.ACTIVITY_TITLE_CREAR);
        else {
            getSupportActionBar().setTitle(AddEditAssetActivityConstants.ACTIVITY_TITLE_EDITAR);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent resultIntent = new Intent();
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(0, resultIntent);
                finish();
                return true;
            case R.id.action_aceptar:
                item.setEnabled(false);
                setResult(1, resultIntent);
                if (controlarAceptar())
                {
                    item.setEnabled(true);
                    finish();
                    return true;
                }
                item.setEnabled(true);
                return false;
            case R.id.action_cancelar:
                setResult(0, resultIntent);
                finish();
                return true;
        }
        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_aceptar_cancelar, menu);
        return true;
    }

    public class AddEditAssetFragmentPagerAdapter extends FragmentPagerAdapter {

        Long idProyecto;
        Long idActivo;

        public AddEditAssetFragmentPagerAdapter(FragmentManager fm, Long idProyecto, Long idActivo) {
            super(fm);
            this.idProyecto = idProyecto;
            this.idActivo = idActivo;
        }

        @Override
        public int getCount() {
            return AddEditAssetActivityConstants.PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {

            Fragment f = null;
            Bundle args = new Bundle();
            args.putLong("idProyecto", idProyecto);
            args.putLong("idActivo", idActivo);
            switch(position) {
                case AddEditAssetActivityConstants.TAB_IDENTIFICACION:
                    f = IdentifyAssetTypesFragment.newInstance();
                    f.setArguments(args);
                    break;
                case AddEditAssetActivityConstants.TAB_VALORACION:
                    f = EstimateAssetFragment.newInstance();
                    f.setArguments(args);
                    break;
            }
            return f;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return AddEditAssetActivityConstants.tabTitles[position];
        }
    }

    private boolean controlarAceptar() {

        boolean flag = false;
        Fragment fr2 = (Fragment) adapter.instantiateItem(viewPager, 1);

        tilNombreActivo = (TextInputLayout) fr2.getView().findViewById(R.id.nombreActivoWrapper);
        etNombreActivo = (EditText) fr2.getView().findViewById(R.id.nombreActivo);

        if (etNombreActivo.getText().toString().isEmpty()) {
            tilNombreActivo.setErrorEnabled(true);
            tilNombreActivo.setError(AddEditAssetActivityConstants.ERROR_NOMBRE_ACTIVO);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)tilNombreActivo.getLayoutParams();
            params.setMargins(0,45,0,0);
            fr2.getView().requestLayout();
            flag = false;
        } else {
            tilNombreActivo.setError(null);
            tilNombreActivo.setErrorEnabled(false);
            flag = true;
        }

        tilCodigoActivo = (TextInputLayout) fr2.getView().findViewById(R.id.codigoActivoWrapper);
        etCodigoActivo = (EditText) fr2.getView().findViewById(R.id.codigoActivo);

        if (etCodigoActivo.getText().toString().isEmpty()) {
            tilCodigoActivo.setErrorEnabled(true);
            tilCodigoActivo.setError(AddEditAssetActivityConstants.ERROR_CODIGO_ACTIVO);
            flag = false;
        } else {
            tilCodigoActivo.setError(null);
            tilCodigoActivo.setErrorEnabled(false);
            flag = true;
        }

        if (!flag) {
            viewPager.setCurrentItem(AddEditAssetActivityConstants.TAB_VALORACION);
            return false;
        }
        //Hay datos en los campos obligatorios (y tienen en cuenta el idActivo)
        Integer retVal = service.comprobarNombreYCodigoActivoUnicos(etNombreActivo.getText().toString(),
                etCodigoActivo.getText().toString(), idProyecto.intValue(), idActivoRecibido.intValue());

        tilNombreActivo.setError(null);
        tilNombreActivo.setErrorEnabled(false);
        tilCodigoActivo.setError(null);
        tilCodigoActivo.setErrorEnabled(false);
        switch (retVal) {
            case -1:
                //Creamos activo y los tipos
                Long idActivoCreado = crearOEditarActivo();
                crearOEditarTiposDeActivo(idActivoCreado);
                flag = true;
                break;
            case 1:
                tilNombreActivo.setErrorEnabled(true);
                tilNombreActivo.setError(AddEditAssetActivityConstants.ERROR_NOMBRE_ACTIVO_DUPLICADO);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)tilNombreActivo.getLayoutParams();
                params.setMargins(0,45,0,0);
                viewPager.setCurrentItem(AddEditAssetActivityConstants.TAB_VALORACION);
                flag = false;
                break;
            case 2:
                tilCodigoActivo.setErrorEnabled(true);
                tilCodigoActivo.setError(AddEditAssetActivityConstants.ERROR_CODIGO_ACTIVO_DUPLICADO);
                viewPager.setCurrentItem(AddEditAssetActivityConstants.TAB_VALORACION);
                flag = false;
                break;
        }

        return flag;
    }

    private Long crearOEditarActivo() {
        Fragment fr2 = (Fragment) adapter.instantiateItem(viewPager, 1);
        //Creamos el activo primero
        etNombreActivo = (EditText) fr2.getView().findViewById(R.id.nombreActivo);
        etCodigoActivo = (EditText) fr2.getView().findViewById(R.id.codigoActivo);
        etDescripcion = (EditText) fr2.getView().findViewById(R.id.descripcionActivo);
        etResponsable = (EditText) fr2.getView().findViewById(R.id.responsableActivo);
        etUbicacion = (EditText) fr2.getView().findViewById(R.id.ubicacionActivo);
        SimpleDateFormat dateFormat = new SimpleDateFormat(GlobalConstants.DATE_FORMAT);
        Calendar fechaActual = Calendar.getInstance();
        String fechaActualStr = dateFormat.format(fechaActual.getTime());

        spinnerValoracionDisponibilidad = (Spinner) fr2.getView().findViewById(R.id.spinnerDisponibilidad);
        spinnerValoracionIntegridad = (Spinner) fr2.getView().findViewById(R.id.spinnerIntegridad);
        spinnerValoracionConfidencialidad= (Spinner) fr2.getView().findViewById(R.id.spinnerConfidencialidad);
        spinnerValoracionAutenticidad = (Spinner) fr2.getView().findViewById(R.id.spinnerAutenticidad);
        spinnerValoracionTrazabilidad = (Spinner) fr2.getView().findViewById(R.id.spinnerTrazabilidad);

        if (idActivoRecibido == GlobalConstants.NULL_ID_ACTIVO)
            return service.crearActivo(idProyecto.intValue(),devuelveIntTipoActivoONulo(spinnerValoracionDisponibilidad),
                    devuelveIntTipoActivoONulo(spinnerValoracionIntegridad),
                    devuelveIntTipoActivoONulo(spinnerValoracionConfidencialidad),
                    devuelveIntTipoActivoONulo(spinnerValoracionAutenticidad),
                    devuelveIntTipoActivoONulo(spinnerValoracionTrazabilidad),devuelveStringONulo(etNombreActivo),
                    devuelveStringONulo(etCodigoActivo), devuelveStringONulo(etDescripcion),
                    devuelveStringONulo(etResponsable), devuelveStringONulo(etUbicacion), fechaActualStr);
        else {
            service.editarActivo(idActivoRecibido, devuelveIntTipoActivoONulo(spinnerValoracionDisponibilidad),
                    devuelveIntTipoActivoONulo(spinnerValoracionIntegridad),
                    devuelveIntTipoActivoONulo(spinnerValoracionConfidencialidad),
                    devuelveIntTipoActivoONulo(spinnerValoracionAutenticidad),
                    devuelveIntTipoActivoONulo(spinnerValoracionTrazabilidad), devuelveStringONulo(etNombreActivo),
                    devuelveStringONulo(etCodigoActivo), devuelveStringONulo(etDescripcion),
                    devuelveStringONulo(etResponsable), devuelveStringONulo(etUbicacion));
            return idActivoRecibido;
        }
    }

    private void crearOEditarTiposDeActivo(Long idActivo) {
        Fragment fr1 = (Fragment) adapter.instantiateItem(viewPager, 0);

        lstOpcionesEssential = (ListView) fr1.getView().findViewById(R.id.ListEssential);
        lstOpcionesArchSys = (ListView) fr1.getView().findViewById(R.id.ListSysArch);
        lstOpcionesDataInfo = (ListView) fr1.getView().findViewById(R.id.ListDataInfo);
        lstOpcionesCryptKeys = (ListView) fr1.getView().findViewById(R.id.ListCryptKeps);
        lstOpcionesServices = (ListView) fr1.getView().findViewById(R.id.ListServices);
        lstOpcionesCommunicationNets = (ListView) fr1.getView().findViewById(R.id.ListCommunicationNet);
        lstOpcionesSoftware = (ListView) fr1.getView().findViewById(R.id.ListSoftware);
        lstOpcionesHardware = (ListView) fr1.getView().findViewById(R.id.ListHardware);
        lstOpcionesInfoSup = (ListView) fr1.getView().findViewById(R.id.ListInfoSup);
        lstOpcionesAuxEquip = (ListView) fr1.getView().findViewById(R.id.ListAuxEquip);
        lstOpcionesInstallations = (ListView) fr1.getView().findViewById(R.id.ListInstallations);
        lstOpcionesPersonal = (ListView) fr1.getView().findViewById(R.id.ListPersonal);
        lstOpcionesOther = (ListView) fr1.getView().findViewById(R.id.ListOther);

        if (idActivo != GlobalConstants.NULL_ID_ACTIVO)
            service.eliminarTiposActivoDeActivo(idActivo);

        crearActivoTipoActivo(lstOpcionesEssential, idActivo, 0);
        crearActivoTipoActivo(lstOpcionesArchSys, idActivo, 1);
        crearActivoTipoActivo(lstOpcionesDataInfo, idActivo, 2);
        crearActivoTipoActivo(lstOpcionesCryptKeys, idActivo, 3);
        crearActivoTipoActivo(lstOpcionesServices, idActivo, 4);
        crearActivoTipoActivo(lstOpcionesCommunicationNets, idActivo, 5);
        crearActivoTipoActivo(lstOpcionesSoftware, idActivo, 6);
        crearActivoTipoActivo(lstOpcionesHardware, idActivo, 7);
        crearActivoTipoActivo(lstOpcionesInfoSup, idActivo, 8);
        crearActivoTipoActivo(lstOpcionesAuxEquip, idActivo, 9);
        crearActivoTipoActivo(lstOpcionesInstallations, idActivo, 10);
        crearActivoTipoActivo(lstOpcionesPersonal, idActivo, 11);
        crearActivoTipoActivo(lstOpcionesOther, idActivo, 12);
    }

    private void crearActivoTipoActivo(ListView lst, Long idActivo, Integer idLista)
    {
        SparseBooleanArray checkedPositions = lst.getCheckedItemPositions();
        for (int i = 0; i <=lst.getAdapter().getCount(); i++) {
            if (checkedPositions.get(i))
                service.crearActivoTipoActivo(idActivo.intValue(),idProyecto.intValue(), idLista,i);
        }
    }
    

    private String devuelveStringONulo(EditText et)
    {
        String retVal = null;

        if (!et.getText().toString().isEmpty())
            retVal = et.getText().toString();
        else
            retVal = null;

        return retVal;
    }

    private Integer devuelveIntTipoActivoONulo(Spinner sp)
    {
        Integer i = null;

        Integer value = Arrays.asList(GlobalConstants.ID_TIPOS).indexOf(
                sp.getSelectedItem().toString());
        if (value != -1)
            i = value;
        else
            i = null;
        return i;
    }

}
