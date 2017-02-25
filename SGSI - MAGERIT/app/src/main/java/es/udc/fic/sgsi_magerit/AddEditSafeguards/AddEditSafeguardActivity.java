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

import es.udc.fic.sgsi_magerit.Model.ModelService.ModelService;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;

public class AddEditSafeguardActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private AddEditSafeguardFragmentPagerAdapter adapter;
    private ModelService service;
    private Long idProyecto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idProyecto = getIntent().getLongExtra("idProyecto", GlobalConstants.NULL_ID_PROYECTO);

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
                /*item.setEnabled(false);
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
                return true;*/
                break;
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
