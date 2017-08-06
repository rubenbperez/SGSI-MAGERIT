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

import java.text.SimpleDateFormat;
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
                /*if (idListaTipoAmenazaRecibido != GlobalConstants.NULL_ID_LISTA_TIPO_AMENAZA
                        && idListaTipoAmenazaRecibido != GlobalConstants.NULL_ID_LISTA_TIPO_AMENAZA) {
                    editarAmenaza();
                    setResult(0,resultIntent);
                } else {*/
                //crearSalvaguarda();
                //setResult(1, resultIntent);
                //}

                crearSalvaguarda();
                setResult(1,resultIntent);
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
