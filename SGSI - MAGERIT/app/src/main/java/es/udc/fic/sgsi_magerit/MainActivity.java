package es.udc.fic.sgsi_magerit;


import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import es.udc.fic.sgsi_magerit.AnalisisFragment.Analisis;
import es.udc.fic.sgsi_magerit.AssetsFragment.Assets;
import es.udc.fic.sgsi_magerit.PendingTasksFragment.PendingTasks;
import es.udc.fic.sgsi_magerit.ProjectsFragment.Projects;
import es.udc.fic.sgsi_magerit.SafeguardsFragment.Safeguards;
import es.udc.fic.sgsi_magerit.ThreatsFragment.Threats;

public class MainActivity extends AppCompatActivity {

    private Toolbar appbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navView = (NavigationView)findViewById(R.id.navview);
        appbar = (Toolbar)findViewById(R.id.appbar);
        setSupportActionBar(appbar);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            navView.getMenu().getItem(0).setChecked(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Projects()).commit();
            getSupportActionBar().setTitle(navView.getMenu().getItem(0).getTitle());
        }

        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        boolean fragmentTransaction = false;
                        Fragment fragment = null;

                        switch (menuItem.getItemId()) {
                            case R.id.menuProjects:
                                fragment = new Projects();
                                fragmentTransaction = true;
                                break;

                            case R.id.menuActivos:
                                fragment = new Assets();
                                fragmentTransaction = true;
                                break;

                            case R.id.menuAmenazas:
                                fragment = new Threats();
                                fragmentTransaction = true;
                                break;

                            case R.id.menuSalvaguardas:
                                fragment = new Safeguards();
                                fragmentTransaction = true;
                                break;

                            case R.id.menuAnalisis:
                                fragment = new Analisis();
                                fragmentTransaction = true;
                                break;

                            case R.id.menuTareasPendientes:
                                fragment = new PendingTasks();
                                fragmentTransaction = true;
                                break;
                        }

                        if(fragmentTransaction) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .commit();

                            menuItem.setChecked(true);
                            getSupportActionBar().setTitle(menuItem.getTitle());
                        }

                        drawerLayout.closeDrawers();

                        return true;
                    }
                });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            //...
        }

        return super.onOptionsItemSelected(item);
    }

}

