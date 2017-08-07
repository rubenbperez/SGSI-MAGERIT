package es.udc.fic.sgsi_magerit.AddEditSafeguards;

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
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.udc.fic.sgsi_magerit.AddEditProject.AddProjectActivityConstants;
import es.udc.fic.sgsi_magerit.AddEditThreat.EstimateThreatFragment;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelService;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.Model.Safeguard.AssetThreatInfoDTO;
import es.udc.fic.sgsi_magerit.Model.Safeguard.Safeguard;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;

public class AddEditSafeguardActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private AddEditSafeguardFragmentPagerAdapter adapter;
    private ModelService service;
    private Long idProyecto;
    private Long idListaTipoSalvaguardaRecibido;
    private Long idTipoSalvaguardaRecibido;

    public ViewPager getViewPager() {
        return viewPager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idProyecto = getIntent().getLongExtra("idProyecto", GlobalConstants.NULL_ID_PROYECTO);
        idListaTipoSalvaguardaRecibido = getIntent().getLongExtra("idListaTipoSalvaguarda", GlobalConstants.NULL_ID_LISTA_TIPO_SALVAGUARDA);
        idTipoSalvaguardaRecibido = getIntent().getLongExtra("idTipoSalvaguarda", GlobalConstants.NULL_ID_LISTA_TIPO_SALVAGUARDA);

        service = new ModelServiceImpl(this, GlobalConstants.DATABASE_NAME,1);
        setContentView(R.layout.activity_add_edit_safeguard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpagerSafeguard);
        viewPager.setOffscreenPageLimit(2);
        adapter = new AddEditSafeguardFragmentPagerAdapter(
                getSupportFragmentManager(), idProyecto);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_return);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(AddEditSafeguardActivityConstants.ACTIVITY_TITLE_CREAR);

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
                if (!comprobarDatos()) {
                    item.setEnabled(true);
                    return false;
                }
                if (idListaTipoSalvaguardaRecibido != GlobalConstants.NULL_ID_LISTA_TIPO_SALVAGUARDA
                        && idListaTipoSalvaguardaRecibido != GlobalConstants.NULL_ID_LISTA_TIPO_SALVAGUARDA) {
                    editarSalvaguarda();
                    setResult(0,resultIntent);
                } else {
                crearSalvaguarda();
                setResult(1, resultIntent);
                }
                item.setEnabled(true);
                finish();
                return true;
            case R.id.action_cancelar:
                setResult(0, resultIntent);
                finish();
                return true;
        }
        return false;
    }

    private boolean comprobarDatos() {
        boolean flag = true;
        IdentifySafeguardFragment fr1 = (IdentifySafeguardFragment) adapter.instantiateItem(viewPager, 0);
        IdentifySafeguardFragment.SafeguardTypePair pair = fr1.getData();

        if (pair.getIdListaSafeguard() == null || pair.getIdTipoSafeguard() == null) {
            viewPager.setCurrentItem(AddEditSafeguardActivityConstants.TAB_INDENTIFICACION);
            Toast.makeText(this, AddEditSafeguardActivityConstants.ERROR_SALVAGUARDA_NO_SELECCIONADA, Toast.LENGTH_SHORT).show();
            return false;
        }

        SafeguardThreatAssetsSelectionFragment fr2 = (SafeguardThreatAssetsSelectionFragment) adapter.instantiateItem(viewPager, 1);

        if (!fr2.assetsSelected()) {
            viewPager.setCurrentItem(AddEditSafeguardActivityConstants.TAB_SELECCION);
            Toast.makeText(this, AddEditSafeguardActivityConstants.ERROR_AMENAZA_NO_SELECCIONADA, Toast.LENGTH_SHORT).show();
            return false;
        }

        return flag;
    }

    private void crearSalvaguarda() {
        boolean flag = true;
        EstimateSafeguardFragment fr3 = (EstimateSafeguardFragment) adapter.instantiateItem(viewPager, 2);
        fr3.recargarInfo();
        HashMap<AssetThreatInfoDTO, List<Safeguard>> expandableSafeguard = fr3.getData();

        IdentifySafeguardFragment fr1 = (IdentifySafeguardFragment) adapter.instantiateItem(viewPager, 0);
        IdentifySafeguardFragment.SafeguardTypePair pair = fr1.getData();

        SimpleDateFormat dateFormat = new SimpleDateFormat(GlobalConstants.DATE_FORMAT);
        Calendar fechaActual = Calendar.getInstance();
        String fechaActualStr = dateFormat.format(fechaActual.getTime());

        for (Map.Entry<AssetThreatInfoDTO, List<Safeguard>> entry : expandableSafeguard.entrySet()) {

            Safeguard safeguard = entry.getValue().get(0);

            service.crearSalvaguarda(safeguard.getIdActivo(), safeguard.getIdProyecto(), safeguard.getIdThreat(),
                    pair.getIdListaSafeguard().longValue(), pair.getIdTipoSafeguard().longValue(),
                    safeguard.getIdControlSeguridadDisponibilidad(),
                    safeguard.getIdControlSeguridadIntegridad(),
                    safeguard.getIdControlSeguridadConfidencialidad(),
                    safeguard.getIdControlSeguridadAutenticidad(),
                    safeguard.getIdControlSeguridadTrazabilidad(), safeguard.getTipoProteccion(),
                    safeguard.getEficacia(), fechaActualStr);
        }
    }

    private boolean editarSalvaguarda() {

        //Obtenemos la lista que hay en el fragmento de valoracion
        EstimateSafeguardFragment fr3 = (EstimateSafeguardFragment) adapter.instantiateItem(viewPager, 2);
        fr3.recargarInfo();
        HashMap<AssetThreatInfoDTO, List<Safeguard>> expandableSafeguardFr3 = fr3.getData();

        HashMap<AssetThreatInfoDTO, List<Safeguard>> expandableSafeguardBD = new HashMap<>();

        try {
            expandableSafeguardBD = service.obtenerInfoSalvaguardasPorIdTipo(idProyecto,
                    idListaTipoSalvaguardaRecibido, idTipoSalvaguardaRecibido);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        IdentifySafeguardFragment fr1 = (IdentifySafeguardFragment) adapter.instantiateItem(viewPager, 0);
        IdentifySafeguardFragment.SafeguardTypePair pair = fr1.getData();

        List<Integer> idsSalvaguardasComprobadas = new ArrayList<>();

        //Sabemos lo que hay en base de datos y lo que hay en el fragmento 3.

        if (expandableSafeguardFr3.isEmpty())
            return false;

        SimpleDateFormat dateFormat = new SimpleDateFormat(GlobalConstants.DATE_FORMAT);
        Calendar fechaActual = Calendar.getInstance();
        String fechaActualStr = dateFormat.format(fechaActual.getTime());
        Boolean flagActualizado = false;

        for (Map.Entry<AssetThreatInfoDTO, List<Safeguard>> entryFr3 : expandableSafeguardFr3.entrySet()) {
            AssetThreatInfoDTO keyFr3 = entryFr3.getKey();
            Safeguard valueFr3 = entryFr3.getValue().get(0);
            flagActualizado = false;

            for (Map.Entry<AssetThreatInfoDTO, List<Safeguard>> entryBD : expandableSafeguardBD.entrySet()) {
                AssetThreatInfoDTO keyBD = entryBD.getKey();
                Safeguard valueBD = entryBD.getValue().get(0);

                // si está, actualizamos
                if (valueFr3.getIdThreat() == valueBD.getIdThreat()) {
                    service.editarValoracionSalvaguarda(valueFr3.getIdSafeguard(),
                            valueFr3.getIdControlSeguridadDisponibilidad(),
                            valueFr3.getIdControlSeguridadIntegridad(),
                            valueFr3.getIdControlSeguridadConfidencialidad(),
                            valueFr3.getIdControlSeguridadAutenticidad(),
                            valueFr3.getIdControlSeguridadTrazabilidad(), valueFr3.getTipoProteccion(),
                            valueFr3.getEficacia());
                    flagActualizado = true;
                    break;
                }
            }
            //si no ha sido actualizado lo anadimos
            if (!flagActualizado) {
                service.crearSalvaguarda(valueFr3.getIdActivo(), valueFr3.getIdProyecto(), valueFr3.getIdThreat(),
                        pair.getIdListaSafeguard().longValue(), pair.getIdTipoSafeguard().longValue(),
                        valueFr3.getIdControlSeguridadDisponibilidad(),
                        valueFr3.getIdControlSeguridadIntegridad(),
                        valueFr3.getIdControlSeguridadConfidencialidad(),
                        valueFr3.getIdControlSeguridadAutenticidad(),
                        valueFr3.getIdControlSeguridadTrazabilidad(), valueFr3.getTipoProteccion(),
                        valueFr3.getEficacia(), fechaActualStr);
            }
            idsSalvaguardasComprobadas.add(valueFr3.getIdThreat().intValue());
        }

        // Eliminamos las que ya no existan
        for (Map.Entry<AssetThreatInfoDTO, List<Safeguard>> entryBD : expandableSafeguardBD.entrySet()) {
            AssetThreatInfoDTO keyBD = entryBD.getKey();
            Safeguard valueBD = entryBD.getValue().get(0);

            if (!idsSalvaguardasComprobadas.contains(valueBD.getIdThreat().intValue())) {
                service.eliminarSalvaguardaPorId(valueBD.getIdSafeguard(), idProyecto);
            }
        }
        return true;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_aceptar_cancelar, menu);
        return true;
    }

    public class AddEditSafeguardFragmentPagerAdapter extends FragmentPagerAdapter {

        private Long idProyecto;

        public AddEditSafeguardFragmentPagerAdapter(FragmentManager fm, Long idProyecto) {
            super(fm);
            this.idProyecto = idProyecto;
        }

        @Override
        public int getCount() {
            return AddEditSafeguardActivityConstants.PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {

            Fragment f = null;
            Bundle args = new Bundle();
            args.putLong("idProyecto", idProyecto);
            args.putLong("idListaTipoSalvaguarda", idListaTipoSalvaguardaRecibido); //Optional parameters
            args.putLong("idTipoSalvaguarda", idTipoSalvaguardaRecibido); //Optional parameters

            switch (position) {
                case AddEditSafeguardActivityConstants.TAB_INDENTIFICACION:
                    f = IdentifySafeguardFragment.newInstance();
                    f.setArguments(args);
                    break;
                case AddEditSafeguardActivityConstants.TAB_SELECCION:
                    f = SafeguardThreatAssetsSelectionFragment.newInstance();
                    f.setArguments(args);
                    break;
                case AddEditSafeguardActivityConstants.TAB_VALORACION:
                    f = EstimateSafeguardFragment.newInstance();
                    f.setArguments(args);
                    break;
            }
            return f;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return AddEditSafeguardActivityConstants.tabTitles[position];
        }
    }


}
