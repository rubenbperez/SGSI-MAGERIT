package es.udc.fic.sgsi_magerit.AddEditAssetThreats;

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

public class AddEditAssetThreatsActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ModelService service;
    private AddEditAssetThreatsFragmentPagerAdapter adapter;

    public ViewPager getViewPager() {
        return viewPager;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deleteFile("threats.tmp");
        service = new ModelServiceImpl(this, GlobalConstants.DATABASE_NAME,1);
        setContentView(R.layout.activity_add_edit_asset_threats);
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        //TODO: idProyecto
        Long idProyecto = (long) 0;

        viewPager = (ViewPager) findViewById(R.id.viewpagerAssetThreats);
        adapter = new AddEditAssetThreatsFragmentPagerAdapter(
                getSupportFragmentManager(),idProyecto);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_return);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getSupportActionBar().setTitle(AddEditAssetThreatsActivityConstants.ACTIVITY_TITLE_EDITAR);

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

    public class AddEditAssetThreatsFragmentPagerAdapter extends FragmentPagerAdapter {

        Long idProyecto;


        public AddEditAssetThreatsFragmentPagerAdapter(FragmentManager fm, Long idProyecto) {
            super(fm);
            this.idProyecto = idProyecto;
        }

        @Override
        public int getCount() {
            return AddEditAssetThreatsActivityConstants.PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {

            Fragment f = null;
            Bundle args = new Bundle();
            switch (position) {
                case AddEditAssetThreatsActivityConstants.TAB_IDENTIFICACION:
                    f = IdentifyAssetThreatsFragment.newInstance();
                    f.setArguments(args);
                    break;
                case AddEditAssetThreatsActivityConstants.TAB_VALORACION:
                    f = EstimateAssetThreatsFragment.newInstance();
                    f.setArguments(args);
                    break;
            }
            return f;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return AddEditAssetThreatsActivityConstants.tabTitles[position];
        }
    }

}
