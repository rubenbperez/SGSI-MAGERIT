package es.udc.fic.sgsi_magerit.AddEditAssetSafeguards;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.udc.fic.sgsi_magerit.AddEditSafeguards.AddEditSafeguardActivityConstants;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelService;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.Model.Safeguard.AssetSafeguardsThreatsInfoDTO;
import es.udc.fic.sgsi_magerit.Model.Safeguard.AssetThreatSafeguardDTO;
import es.udc.fic.sgsi_magerit.Model.Safeguard.Safeguard;
import es.udc.fic.sgsi_magerit.Model.Safeguard.SafeguardInfoDTO;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;

public class AddEditAssetSafeguardsActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ModelService service;
    private AddEditAssetSafeguardsFragmentPagerAdapter adapter;
    private Long idProyecto;
    private Long idActivoRecibido;

    public ViewPager getViewPager() {
        return viewPager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idProyecto = getIntent().getLongExtra("idProyecto", GlobalConstants.NULL_ID_PROYECTO);
        idActivoRecibido = getIntent().getLongExtra("idActivo",GlobalConstants.NULL_ID_ACTIVO);
        service = new ModelServiceImpl(this, GlobalConstants.DATABASE_NAME,1);
        setContentView(R.layout.activity_add_edit_asset_threats);
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpagerAssetThreats);
        adapter = new AddEditAssetSafeguardsFragmentPagerAdapter(
                getSupportFragmentManager(),idProyecto);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_return);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(AddEditAssetSafeguardsActivityConstants.ACTIVITY_TITLE_EDITAR);

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
                setResult(0, resultIntent);
                item.setEnabled(false);
                if (!comprobarDatos()) {
                    item.setEnabled(true);
                    return false;
                }
                editarSalvaguardas();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_aceptar_cancelar, menu);
        return true;
    }

    public class AddEditAssetSafeguardsFragmentPagerAdapter extends FragmentPagerAdapter {

        Long idProyecto;


        public AddEditAssetSafeguardsFragmentPagerAdapter(FragmentManager fm, Long idProyecto) {
            super(fm);
            this.idProyecto = idProyecto;
        }

        @Override
        public int getCount() {
            return AddEditAssetSafeguardsActivityConstants.PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {

            Fragment f = null;
            Bundle args = new Bundle();
            switch (position) {
                case AddEditAssetSafeguardsActivityConstants.TAB_IDENTIFICACION:
                    f = IdentifyAssetSafeguardsFragment.newInstance();
                    args.putLong("idProyecto",idProyecto);
                    args.putLong("idActivo",idActivoRecibido);
                    f.setArguments(args);
                    break;
                case AddEditAssetSafeguardsActivityConstants.TAB_SELECCION:
                    f = IdentifyAssetSafeguardThreatsFragment.newInstance();
                    args.putLong("idProyecto",idProyecto);
                    args.putLong("idActivo",idActivoRecibido);
                    f.setArguments(args);
                    break;
                case AddEditAssetSafeguardsActivityConstants.TAB_VALORACION:
                    f = EstimateAssetSafeguardsFragment.newInstance();
                    args.putLong("idProyecto",idProyecto);
                    args.putLong("idActivo",idActivoRecibido);
                    f.setArguments(args);
                    break;
            }
            return f;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return AddEditAssetSafeguardsActivityConstants.tabTitles[position];
        }
    }

    private boolean comprobarDatos() {

        IdentifyAssetSafeguardThreatsFragment fr2 = (IdentifyAssetSafeguardThreatsFragment)
                adapter.instantiateItem(viewPager, 1);

        //fr2.recargarInfo();
        HashMap<SafeguardInfoDTO, List<AssetThreatSafeguardDTO>> expandableSafegardsThreatSelected = fr2.getData();

        boolean flag = false;

        for (Map.Entry<SafeguardInfoDTO, List<AssetThreatSafeguardDTO>> entrySelected : expandableSafegardsThreatSelected.entrySet()) {
            SafeguardInfoDTO keySelected = entrySelected.getKey();
            List<AssetThreatSafeguardDTO> valueSelected = entrySelected.getValue();

            for (AssetThreatSafeguardDTO safeguard : valueSelected) {
                if (safeguard.getChecked()) {
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                Toast.makeText(this, AddEditAssetSafeguardsActivityConstants.ERROR_SALVAGUARDA_SELECCIONADA_SIN_AMENAZAS, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void editarSalvaguardas() {
        EstimateAssetSafeguardsFragment fr3 = (EstimateAssetSafeguardsFragment)
                adapter.instantiateItem(viewPager, 2);

        fr3.recargarInfo();
        HashMap<AssetSafeguardsThreatsInfoDTO, List<Safeguard>> safeguards = fr3.getData();

        SimpleDateFormat dateFormat = new SimpleDateFormat(GlobalConstants.DATE_FORMAT);
        Calendar fechaActual = Calendar.getInstance();
        String fechaActualStr = dateFormat.format(fechaActual.getTime());


        List <Long> idsSalvaguardasComprobadas = new ArrayList<>();

        HashMap<AssetSafeguardsThreatsInfoDTO, List<Safeguard>> expandableBD = service.
                obtenerInfoSalvaguardasDeActivo(idProyecto,idActivoRecibido);
        
        for (Map.Entry<AssetSafeguardsThreatsInfoDTO, List<Safeguard>> entrySelected : safeguards.entrySet()) {
            AssetSafeguardsThreatsInfoDTO key = entrySelected.getKey();
            List<Safeguard> value = entrySelected.getValue();

            // Anadimos as novas
            if (value.get(0).getIdSafeguard() == null) {
                service.crearSalvaguarda(value.get(0).getIdActivo(), value.get(0).getIdProyecto(), value.get(0).getIdThreat(),
                        key.getIdListaTipoSalvaguarda().longValue(), key.getIdTipoSalvaguarda().longValue(),
                        value.get(0).getIdControlSeguridadDisponibilidad(),
                        value.get(0).getIdControlSeguridadIntegridad(),
                        value.get(0).getIdControlSeguridadConfidencialidad(),
                        value.get(0).getIdControlSeguridadAutenticidad(),
                        value.get(0).getIdControlSeguridadTrazabilidad(), value.get(0).getTipoProteccion(),
                        value.get(0).getEficacia(), fechaActualStr);
            } else { //editamos as que xa había
                service.editarValoracionSalvaguarda(value.get(0).getIdSafeguard(),
                        value.get(0).getIdControlSeguridadDisponibilidad(),
                        value.get(0).getIdControlSeguridadIntegridad(),
                        value.get(0).getIdControlSeguridadConfidencialidad(),
                        value.get(0).getIdControlSeguridadAutenticidad(),
                        value.get(0).getIdControlSeguridadTrazabilidad(), value.get(0).getTipoProteccion(),
                        value.get(0).getEficacia());
                idsSalvaguardasComprobadas.add(value.get(0).getIdSafeguard());
            }
        }



        //eliminamos as que se poidan  haber eliminado

        for (Map.Entry<AssetSafeguardsThreatsInfoDTO, List<Safeguard>> entryBD : expandableBD.entrySet()) {
            AssetSafeguardsThreatsInfoDTO keyBD = entryBD.getKey();
            Safeguard valueBD = entryBD.getValue().get(0);

            if (!idsSalvaguardasComprobadas.contains(valueBD.getIdSafeguard())) {
                service.eliminarSalvaguardaPorId(valueBD.getIdSafeguard(), idProyecto);
            }
        }

    }



    
}
