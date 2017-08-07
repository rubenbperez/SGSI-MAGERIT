package es.udc.fic.sgsi_magerit.AddEditThreat;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import es.udc.fic.sgsi_magerit.Model.ModelService.ModelService;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.Model.Project.ProjectConstants;
import es.udc.fic.sgsi_magerit.Model.Threat.AssetThreatDTO;
import es.udc.fic.sgsi_magerit.Model.Threat.Threat;
import es.udc.fic.sgsi_magerit.Model.Threat.ThreatDTO;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;

public class AddEditThreatActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ModelService service;
    private AddEditThreatFragmentPagerAdapter adapter;
    private Long idProyecto;
    private Long idListaTipoAmenazaRecibido;
    private Long idTipoAmenazaRecibido;

    public ViewPager getViewPager() {
        return viewPager;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deleteFile("threats.tmp");
        service = new ModelServiceImpl(this, GlobalConstants.DATABASE_NAME,1);
        idProyecto = getIntent().getLongExtra("idProyecto", GlobalConstants.NULL_ID_PROYECTO);
        idListaTipoAmenazaRecibido = getIntent().getLongExtra("idListaTipoAmenaza", GlobalConstants.NULL_ID_LISTA_TIPO_AMENAZA);
        idTipoAmenazaRecibido = getIntent().getLongExtra("idTipoAmenaza", GlobalConstants.NULL_ID_LISTA_TIPO_AMENAZA);
        setContentView(R.layout.activity_add_edit_threat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpagerThreat);
        viewPager.setOffscreenPageLimit(2);
        adapter = new AddEditThreatFragmentPagerAdapter(
                getSupportFragmentManager(),idProyecto, idListaTipoAmenazaRecibido, idTipoAmenazaRecibido);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_return);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (idListaTipoAmenazaRecibido != GlobalConstants.NULL_ID_LISTA_TIPO_AMENAZA &&
                idTipoAmenazaRecibido != GlobalConstants.NULL_ID_LISTA_TIPO_AMENAZA) {
            getSupportActionBar().setTitle(AddEditThreatActivityConstants.ACTIVITY_TITLE_EDITAR);
        } else {
            getSupportActionBar().setTitle(AddEditThreatActivityConstants.ACTIVITY_TITLE_CREAR);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent resultIntent = new Intent();
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(0, resultIntent);
                finish();
                deleteFile("threats.tmp");
                return true;
            case R.id.action_aceptar:
                item.setEnabled(false);
                if (!comprobarDatos()) {
                    item.setEnabled(true);
                    return false;
                }
                if (idListaTipoAmenazaRecibido != GlobalConstants.NULL_ID_LISTA_TIPO_AMENAZA
                        && idListaTipoAmenazaRecibido != GlobalConstants.NULL_ID_LISTA_TIPO_AMENAZA) {
                    editarAmenaza();
                    setResult(0,resultIntent);
                } else {
                    crearAmenaza();
                    setResult(1, resultIntent);
                }
                deleteFile("threats.tmp");
                item.setEnabled(true);
                finish();
                return true;
            case R.id.action_cancelar:
                setResult(0, resultIntent);
                finish();
                deleteFile("threats.tmp");
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_aceptar_cancelar, menu);
        return true;
    }

    public class AddEditThreatFragmentPagerAdapter extends FragmentPagerAdapter {

        Long idProyecto;
        Long idListaTipoAmenazaRecibido;
        Long idTipoAmenazaRecibido;

        public AddEditThreatFragmentPagerAdapter(FragmentManager fm, Long idProyecto,
                                                 Long idListaTipoAmenazaRecibido,
                                                 Long idTipoAmenazaRecibido) {
            super(fm);
            this.idProyecto = idProyecto;
            this.idListaTipoAmenazaRecibido = idListaTipoAmenazaRecibido;
            this.idTipoAmenazaRecibido = idTipoAmenazaRecibido;
        }

        @Override
        public int getCount() {
            return AddEditThreatActivityConstants.PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {

            Fragment f = null;
            Bundle args = new Bundle();
            args.putLong("idProyecto", idProyecto);
            args.putLong("idListaTipoAmenaza", idListaTipoAmenazaRecibido);
            args.putLong("idTipoAmenaza",idTipoAmenazaRecibido);
            switch (position) {
                case AddEditThreatActivityConstants.TAB_INDENTIFICACION:
                    f = IdentifyThreatFragment.newInstance();
                    f.setArguments(args);
                    break;
                case AddEditThreatActivityConstants.TAB_SELECCION:
                    f = ThreatAssetsSelectionFragment.newInstance();
                    f.setArguments(args);
                    break;
                case AddEditThreatActivityConstants.TAB_VALORACION:
                    f = EstimateThreatFragment.newInstance();
                    f.setArguments(args);
                    break;
            }
            return f;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return AddEditThreatActivityConstants.tabTitles[position];
        }
    }

    private boolean comprobarDatos() {
        Fragment fr1 = (Fragment) adapter.instantiateItem(viewPager, 0);
        Fragment fr2 = (Fragment) adapter.instantiateItem(viewPager, 1);
        boolean flag = false;

        EstimateThreatFragment fr = (EstimateThreatFragment) adapter.instantiateItem(viewPager, 2);
        fr.recargarInfo();
        List<AssetThreatDTO> data = fr.getdata();

        ListView lstAmenazasDN = (ListView) fr1.getView().findViewById(R.id.ListDesastresNaturales);
        ListView lstAmenazasOI = (ListView) fr1.getView().findViewById(R.id.ListOrigenIndustrial);
        ListView lstAmenazasEF = (ListView) fr1.getView().findViewById(R.id.ListErroresFallos);
        ListView lstAmenazasAD = (ListView) fr1.getView().findViewById(R.id.ListAtaquesDeliberados);
        ListView lstAmenazasEdit = (ListView) fr1.getView().findViewById(R.id.ListEdicion);

        if (lstAmenazasDN.getCheckedItemCount() == 0 && lstAmenazasOI.getCheckedItemCount() == 0 &&
                lstAmenazasEF.getCheckedItemCount() == 0 && lstAmenazasAD.getCheckedItemCount() == 0
                && lstAmenazasEdit.getCheckedItemCount() == 0) {
            flag = true;
        }

        if (flag) {
            viewPager.setCurrentItem(AddEditThreatActivityConstants.TAB_INDENTIFICACION);
            Toast.makeText(this, AddEditThreatActivityConstants.ERROR_AMENAZA_NO_SELECCIONADA, Toast.LENGTH_SHORT).show();
            return false;
        }

        ListView lstAssets = (ListView) fr2.getView().findViewById(R.id.LstOpciones);
        if (lstAssets.getCheckedItemCount() == 0) {
            viewPager.setCurrentItem(AddEditThreatActivityConstants.TAB_SELECCION);
            Toast.makeText(this, AddEditThreatActivityConstants.ERROR_ACTIVO_NO_SELECCIONADO, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean crearAmenaza() {
        IdentifyThreatFragment fr1 = (IdentifyThreatFragment) adapter.instantiateItem(viewPager, 0);

        Long idListaAmenaza = null;
        Long idTipoAmenaza = null;

        //Obtenemos la amenaza seleccionada. Se identidica por lista y por valor dentro de la lista
        ListView lstAmenazasDN = (ListView) fr1.getView().findViewById(R.id.ListDesastresNaturales);
        ListView lstAmenazasOI = (ListView) fr1.getView().findViewById(R.id.ListOrigenIndustrial);
        ListView lstAmenazasEF = (ListView) fr1.getView().findViewById(R.id.ListErroresFallos);
        ListView lstAmenazasAD = (ListView) fr1.getView().findViewById(R.id.ListAtaquesDeliberados);

        if (lstAmenazasDN.getCheckedItemCount() != 0)
        {
            idListaAmenaza = (long)0;
            idTipoAmenaza = (long) Arrays.asList(GlobalConstants.AMENAZAS_TIPO_DESASTRES_NATURALES).indexOf(
            fr1.getListaAmenazasDesastresNaturales().get(lstAmenazasDN.getCheckedItemPosition()));
        }

        if (lstAmenazasOI.getCheckedItemCount() != 0)
        {
            idListaAmenaza = (long)1;
            idTipoAmenaza = (long) Arrays.asList(GlobalConstants.AMENAZAS_TIPO_ORIGEN_INDUSTRIAL).indexOf(
                    fr1.getListaAmenazasOrigenIndustrial().get(lstAmenazasOI.getCheckedItemPosition()));
        }

        if (lstAmenazasEF.getCheckedItemCount() != 0)
        {
            idListaAmenaza = (long)2;
            idTipoAmenaza = (long) Arrays.asList(GlobalConstants.AMENAZAS_TIPO_ERRORES_FALLOS_NO_INTENCIONADOS).indexOf(
                    fr1.getListaAmenazasErroresFallos().get(lstAmenazasEF.getCheckedItemPosition()));
        }

        if (lstAmenazasAD.getCheckedItemCount() != 0)
        {
            idListaAmenaza = (long)3;
            idTipoAmenaza = (long) Arrays.asList(GlobalConstants.AMENAZAS_TIPO_ATAQUES_DELIBERADOS).indexOf(
                    fr1.getListaAmenazasAtaquesDeliberados().get(lstAmenazasAD.getCheckedItemPosition()));
        }


        //Obtenemos la lista que hay en el fragmento de valoracion
        EstimateThreatFragment fr3 = (EstimateThreatFragment) adapter.instantiateItem(viewPager, 2);
        List<AssetThreatDTO> listaAmenazas = fr3.getdata();

        SimpleDateFormat dateFormat = new SimpleDateFormat(GlobalConstants.DATE_FORMAT);
        Calendar fechaActual = Calendar.getInstance();
        String fechaActualStr = dateFormat.format(fechaActual.getTime());

        if (!listaAmenazas.isEmpty()) {

            for (AssetThreatDTO at: listaAmenazas) {
                service.crearAmenaza(at.getIdActivo(),at.getIdProyecto(),idListaAmenaza, idTipoAmenaza,
                        at.getIdDegradacionDisponibilidad(), at.getIdProbabilidadDisponibilidad(),
                        at.getIdDegradacionIntegridad(), at.getIdProbabilidadIntegridad(),
                        at.getIdDegradacionConfidencialidad(),at.getIdProbabilidadConfidencialidad(),
                        at.getIdDegradacionAutenticidad(), at.getIdDegradacionAutenticidad(),
                        at.getIdDegradacionTrazabilidad(), at.getIdDegradacionTrazabilidad(),
                        fechaActualStr);
            }
        }
        return true;
    }

    private boolean editarAmenaza() {

        //Obtenemos la lista que hay en el fragmento de valoracion
        EstimateThreatFragment fr3 = (EstimateThreatFragment) adapter.instantiateItem(viewPager, 2);
        List<AssetThreatDTO> listaAmenazasFr3 = fr3.getdata();
        List<Long> listaAmenazasBD = service.obtenerIdsAmenazaActivoPorTipoAmenaza
                (idListaTipoAmenazaRecibido, idTipoAmenazaRecibido, idProyecto);

        //Sabemos lo que hay en base de datos y lo que hay en el fragmento 3.

        if (listaAmenazasFr3.isEmpty())
            return false;

        SimpleDateFormat dateFormat = new SimpleDateFormat(GlobalConstants.DATE_FORMAT);
        Calendar fechaActual = Calendar.getInstance();
        String fechaActualStr = dateFormat.format(fechaActual.getTime());

        List<Integer> elementosComprobados = new ArrayList<>();

        if (!listaAmenazasFr3.isEmpty()) {
            for (AssetThreatDTO at : listaAmenazasFr3) {

                if (listaAmenazasBD.contains(at.getIdThreat())) {
                    service.editarValoracionAmenaza(at.getIdThreat(),
                            at.getIdDegradacionDisponibilidad(), at.getIdProbabilidadDisponibilidad(),
                            at.getIdDegradacionIntegridad(), at.getIdProbabilidadIntegridad(),
                            at.getIdDegradacionConfidencialidad(), at.getIdProbabilidadConfidencialidad(),
                            at.getIdDegradacionAutenticidad(), at.getIdDegradacionAutenticidad(),
                            at.getIdDegradacionTrazabilidad(), at.getIdDegradacionTrazabilidad());
                } else {
                    service.crearAmenaza(at.getIdActivo(), at.getIdProyecto(), idListaTipoAmenazaRecibido, idTipoAmenazaRecibido,
                            at.getIdDegradacionDisponibilidad(), at.getIdProbabilidadDisponibilidad(),
                            at.getIdDegradacionIntegridad(), at.getIdProbabilidadIntegridad(),
                            at.getIdDegradacionConfidencialidad(), at.getIdProbabilidadConfidencialidad(),
                            at.getIdDegradacionAutenticidad(), at.getIdDegradacionAutenticidad(),
                            at.getIdDegradacionTrazabilidad(), at.getIdDegradacionTrazabilidad(),
                            fechaActualStr);
                }
                elementosComprobados.add(listaAmenazasBD.indexOf(at.getIdThreat()));
            }
        }
        //Eliminamos los elementos que estan en Fr3 pero no en BBDD
        // ahora eliminamos de BD las que no estén en la otra lista

        for (int i=0; i<listaAmenazasBD.size(); i++) {
            if (!elementosComprobados.contains(i)) {
                service.eliminarAmenazaActivo(listaAmenazasBD.get(i));
            }
        }
        return true;
    }


}
