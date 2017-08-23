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

import es.udc.fic.sgsi_magerit.Model.ModelService.ModelService;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
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
                item.setEnabled(false);
                //editarAmenazas();
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
    
    
}
