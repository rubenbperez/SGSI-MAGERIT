package es.udc.fic.sgsi_magerit.AddEditAsset;

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

import es.udc.fic.sgsi_magerit.AddEditProject.SizingProjectFragment;
import es.udc.fic.sgsi_magerit.R;

public class AddEditAssetActivity extends AppCompatActivity {

    private long idProyecto;
    private ViewPager viewPager;
    private AddEditAssetFragmentPagerAdapter adapter;
    String deleteme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        idProyecto = getIntent().getLongExtra("idProyecto",(long)0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new AddEditAssetFragmentPagerAdapter(
                getSupportFragmentManager(),idProyecto);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_return);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (idProyecto == 0)
            getSupportActionBar().setTitle(AddEditAssetActivityConstants.ACTIVITY_TITLE_CREAR);
        else
            getSupportActionBar().setTitle(AddEditAssetActivityConstants.ACTIVITY_TITLE_EDITAR);

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

        public AddEditAssetFragmentPagerAdapter(FragmentManager fm, Long idProyecto) {
            super(fm);
            this.idProyecto = idProyecto;
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

}
