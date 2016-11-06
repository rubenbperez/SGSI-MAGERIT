package es.udc.fic.sgsi_magerit.AddEditThreat;

import android.app.Activity;
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
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import es.udc.fic.sgsi_magerit.AddEditAsset.AddEditAssetActivityConstants;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelService;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;

public class AddEditThreatActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ModelService service;
    private AddEditThreatFragmentPagerAdapter adapter;
    private Long idProyecto;
    List<AssetThreatDTO> dataFragment;

    public interface OnDataPass {
        public void onDataPass();
    }

    public ViewPager getViewPager() {
        return viewPager;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deleteFile("threats.tmp");
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
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(0, resultIntent);
                finish();
                deleteFile("threats.tmp");
                return true;
            case R.id.action_aceptar:
                item.setEnabled(false);
                if (!comprobarDatos())
                    return false;
                //setResult(3, resultIntent);
                //deleteFile("threats.tmp");
                item.setEnabled(true);
                return false;

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

    private boolean comprobarDatos() {
        Fragment fr1 = (Fragment) adapter.instantiateItem(viewPager, 0);
        Fragment fr2 = (Fragment) adapter.instantiateItem(viewPager, 1);
        boolean flag = false;

        ListView lstAmenazasDN = (ListView) fr1.getView().findViewById(R.id.ListDesastresNaturales);
        ListView lstAmenazasOI = (ListView) fr1.getView().findViewById(R.id.ListOrigenIndustrial);
        ListView lstAmenazasEF = (ListView) fr1.getView().findViewById(R.id.ListErroresFallos);
        ListView lstAmenazasAD = (ListView) fr1.getView().findViewById(R.id.ListAtaquesDeliberados);

        if (lstAmenazasDN.getCheckedItemCount() == 0 && lstAmenazasOI.getCheckedItemCount() == 0 &&
                lstAmenazasEF.getCheckedItemCount() == 0 && lstAmenazasAD.getCheckedItemCount() == 0) {
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
        return false;
    }

}
