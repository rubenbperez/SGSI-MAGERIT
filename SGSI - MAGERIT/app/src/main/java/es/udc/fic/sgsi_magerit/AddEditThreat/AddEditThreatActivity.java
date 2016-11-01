package es.udc.fic.sgsi_magerit.AddEditThreat;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;

import es.udc.fic.sgsi_magerit.Model.ModelService.ModelService;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;

public class AddEditThreatActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ModelService service;
    private AddEditThreatFragmentPagerAdapter adapter;
    private Long idProyecto;

    public ViewPager getViewPager() {
        return viewPager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = new ModelServiceImpl(this, GlobalConstants.DATABASE_NAME,1);
        idProyecto = getIntent().getLongExtra("idProyecto", GlobalConstants.NULL_ID_PROYECTO);
        setContentView(R.layout.activity_add_edit_threat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

       /*try
        {
            OutputStreamWriter fout=
                    new OutputStreamWriter(
                            openFileOutput("threats.tmp", Context.MODE_APPEND));
            fout.close();
        }
        catch (Exception ex)
        {
            Log.e("Ficheros", "Error al escribir fichero a memoria interna");
        }*/

        viewPager = (ViewPager) findViewById(R.id.viewpagerThreat);
        viewPager.setOffscreenPageLimit(2);
        adapter = new AddEditThreatFragmentPagerAdapter(
                getSupportFragmentManager(),idProyecto);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_return);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(AddEditThreatActivityConstants.ACTIVITY_TITLE_CREAR);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent resultIntent = new Intent();
        deleteFile("threats.tmp");
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(0, resultIntent);
                finish();
                return true;
            case R.id.action_aceptar:
                /*item.setEnabled(false);
                setResult(3, resultIntent);
                item.setEnabled(true);*/
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

    public class AddEditThreatFragmentPagerAdapter extends FragmentPagerAdapter {

        Long idProyecto;

        public AddEditThreatFragmentPagerAdapter(FragmentManager fm, Long idProyecto) {
            super(fm);
            this.idProyecto = idProyecto;
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

}
