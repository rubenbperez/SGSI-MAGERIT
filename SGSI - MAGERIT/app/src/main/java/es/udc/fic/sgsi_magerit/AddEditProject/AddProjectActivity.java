package es.udc.fic.sgsi_magerit.AddEditProject;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelService;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;

public class AddProjectActivity extends AppCompatActivity implements SizingProjectFragment.OnEditProject{

    Long idProyecto;
    private TextInputLayout tilNombreProyecto;
    private EditText etNombreProyecto;
    private TextInputLayout tilDirectorProyecto;
    private EditText etDirectorProyecto;
    private TextInputLayout tilDescripcionProyecto;
    private EditText etDescripcionProyecto;
    private TextInputLayout tilVersionProyecto;
    private EditText etVersiónProyecto;
    private TextInputLayout tilFechaCreacionProyecto;
    private EditText etFechaCreacionProyecto;
    private CheckBox cbActivadoProyecto;
    private ViewPager viewPager;
    private AddProjectFragmentPagerAdapter adapter;
    private ModelService service;
    List<Long> idsParametrizacionActivos;
    List<Long> idsParametrizacionVulnerabilidad;
    List<Long> idsParametrizacionImpacto;
    List<Long> idsParametrizacionCntrlSeguridad;


    //TABLA 1: etNUMEROFILA_(COLUMNA)_TABLA
    private EditText et1_1_tabla1;
    private EditText et1_2_tabla1;
    private EditText et1_3_tabla1;
    private EditText et2_1_tabla1;
    private EditText et2_2_tabla1;
    private EditText et2_3_tabla1;
    private EditText et3_1_tabla1;
    private EditText et3_2_tabla1;
    private EditText et3_3_tabla1;
    private EditText et4_1_tabla1;
    private EditText et4_2_tabla1;
    private EditText et4_3_tabla1;
    private EditText et5_1_tabla1;
    private EditText et5_2_tabla1;
    private EditText et5_3_tabla1;
    private CheckBox cb1_tabla1;
    private CheckBox cb2_tabla1;
    private CheckBox cb3_tabla1;
    private CheckBox cb4_tabla1;
    private CheckBox cb5_tabla1;

    //TABLA 2: tvNUMEROFILA_(COLUMNA)_TABLA
    NumberPicker np2_1;
    NumberPicker sp2_1;
    NumberPicker np2_2;
    NumberPicker sp2_2;
    NumberPicker np2_3;
    NumberPicker sp2_3;
    NumberPicker np2_4;
    NumberPicker sp2_4;
    NumberPicker np2_5;
    NumberPicker sp2_5;
    private TextView tv1_tabla2;
    private TextView tv2_tabla2;
    private TextView tv3_tabla2;
    private TextView tv4_tabla2;
    private TextView tv5_tabla2;
    private CheckBox cb1_tabla2;
    private CheckBox cb2_tabla2;
    private CheckBox cb3_tabla2;
    private CheckBox cb4_tabla2;
    private CheckBox cb5_tabla2;



    //TABLA 3: npTABLA_FILA
    NumberPicker np3_1;
    NumberPicker np3_2;
    NumberPicker np3_3;
    NumberPicker np3_4;
    NumberPicker np3_5;
    private CheckBox cb1_tabla3;
    private CheckBox cb2_tabla3;
    private CheckBox cb3_tabla3;
    private CheckBox cb4_tabla3;
    private CheckBox cb5_tabla3;

    //TABLA 4
    NumberPicker np4_1;
    NumberPicker np4_2;
    NumberPicker np4_3;
    NumberPicker np4_4;
    NumberPicker np4_5;
    private CheckBox cb1_tabla4;
    private CheckBox cb2_tabla4;
    private CheckBox cb3_tabla4;
    private CheckBox cb4_tabla4;
    private CheckBox cb5_tabla4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        idProyecto = getIntent().getLongExtra("idProyecto",(long)0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpagerProject);
        adapter = new AddProjectFragmentPagerAdapter(
                getSupportFragmentManager(),idProyecto);

        viewPager.setAdapter(adapter);

        service = new ModelServiceImpl(this, GlobalConstants.DATABASE_NAME,1);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_return);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (idProyecto == 0)
            getSupportActionBar().setTitle(AddProjectActivityConstants.ACTIVITY_TITLE_CREAR);
        else
            getSupportActionBar().setTitle(AddProjectActivityConstants.ACTIVITY_TITLE_EDITAR);

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
                setResult(1, resultIntent);
                if (comprobarFragmentoDetallesProyecto() && comprobarFragmentoDimensionamientoProyectoTablaActivos()
                        && comprobarFragmentoDimensionamientoProyectoTablaVulnerabilidad() && cargarDatosTablaImpacto()
                        && cargarDatosTablaCtrlSeguridad()) {
                    // Si los datos están correctos podemos proceder a almacenar/editar los datos.
                    if (idProyecto == 0) { //en este caso añadimos proyecto
                        Long idProyectoCreado = crearProyecto(etNombreProyecto.getText().toString(),
                                etDirectorProyecto.getText().toString(),
                                etDescripcionProyecto.getText().toString(),
                                etVersiónProyecto.getText().toString(),
                                etFechaCreacionProyecto.getText().toString(),
                                cbActivadoProyecto.isChecked());

                        if (!crearDimensionamientoTablaValoracionActivos(idProyectoCreado, et1_1_tabla1.getText().toString(),
                                et1_2_tabla1.getText().toString(), et1_3_tabla1.getText().toString(),
                                cb1_tabla1.isChecked(), et2_1_tabla1.getText().toString(),
                                et2_2_tabla1.getText().toString(), et2_3_tabla1.getText().toString(),
                                cb2_tabla1.isChecked(), et3_1_tabla1.getText().toString(), et3_2_tabla1.getText().toString(),
                                et3_3_tabla1.getText().toString(), cb3_tabla1.isChecked(),
                                et4_1_tabla1.getText().toString(), et4_2_tabla1.getText().toString(),
                                et4_3_tabla1.getText().toString(),cb4_tabla1.isChecked(),
                                et5_1_tabla1.getText().toString(), et5_2_tabla1.getText().toString(),
                                et5_3_tabla1.getText().toString(), cb5_tabla1.isChecked())) {
                            service.eliminarProyectoYDimensionamiento(idProyectoCreado);
                            item.setEnabled(true);
                            return false;
                        }

                        if (!crearDimensionamientoTablaVulnerabilidad(idProyectoCreado, np2_1.getValue(),
                                sp2_1.getValue(), tv1_tabla2.getText().toString(), cb1_tabla2.isChecked(),
                                np2_2.getValue(), sp2_2.getValue(), tv2_tabla2.getText().toString(),
                                cb2_tabla2.isChecked(),  np2_3.getValue(), sp2_3.getValue(),
                                tv3_tabla2.getText().toString(), cb3_tabla2.isChecked(), np2_4.getValue(),
                                sp2_4.getValue(), tv4_tabla2.getText().toString(), cb4_tabla2.isChecked(),
                                np2_5.getValue(), sp2_5.getValue(), tv5_tabla2.getText().toString(),
                                cb5_tabla2.isChecked())) {
                            service.eliminarProyectoYDimensionamiento(idProyectoCreado);
                            item.setEnabled(true);
                            return false;
                        }
                        crearDimensionamientoTablaValoracionImpacto(idProyectoCreado, np3_1.getValue(),
                                cb1_tabla3.isChecked(), np3_2.getValue(), cb2_tabla3.isChecked(),
                                np3_3.getValue(), cb3_tabla3.isChecked(), np3_4.getValue(),
                                cb4_tabla3.isChecked(), np3_5.getValue(), cb5_tabla3.isChecked());

                        crearDimensionamientoTablaValoracionCtrlSeguridad(idProyectoCreado, np4_1.getValue(),
                                cb1_tabla4.isChecked(), np4_2.getValue(), cb2_tabla4.isChecked(),
                                np4_3.getValue(), cb3_tabla4.isChecked(), np4_4.getValue(),
                                cb4_tabla4.isChecked(), np4_5.getValue(), cb5_tabla4.isChecked());
                        setResult(1, resultIntent);
                        finish();
                        return true;

                    } else { //En este caso editamos proyecto
                        editarProyecto(idProyecto,etNombreProyecto.getText().toString(),
                                etDirectorProyecto.getText().toString(),
                                etDescripcionProyecto.getText().toString(),
                                etVersiónProyecto.getText().toString(),
                                etFechaCreacionProyecto.getText().toString(),
                                cbActivadoProyecto.isChecked());

                        if (!editarDimensionamientoTablaValoracionActivos(idsParametrizacionActivos, et1_1_tabla1.getText().toString(),
                                et1_2_tabla1.getText().toString(), et1_3_tabla1.getText().toString(),
                                cb1_tabla1.isChecked(), et2_1_tabla1.getText().toString(),
                                et2_2_tabla1.getText().toString(), et2_3_tabla1.getText().toString(),
                                cb2_tabla1.isChecked(), et3_1_tabla1.getText().toString(), et3_2_tabla1.getText().toString(),
                                et3_3_tabla1.getText().toString(), cb3_tabla1.isChecked(),
                                et4_1_tabla1.getText().toString(), et4_2_tabla1.getText().toString(),
                                et4_3_tabla1.getText().toString(),cb4_tabla1.isChecked(),
                                et5_1_tabla1.getText().toString(), et5_2_tabla1.getText().toString(),
                                et5_3_tabla1.getText().toString(), cb5_tabla1.isChecked())) {
                            item.setEnabled(true);
                            return false;
                        }

                        if (!editarDimensionamientoTablaVulnerabilidad(idsParametrizacionVulnerabilidad, np2_1.getValue(),
                                sp2_1.getValue(), tv1_tabla2.getText().toString(), cb1_tabla2.isChecked(),
                                np2_2.getValue(), sp2_2.getValue(), tv2_tabla2.getText().toString(),
                                cb2_tabla2.isChecked(),  np2_3.getValue(), sp2_3.getValue(),
                                tv3_tabla2.getText().toString(), cb3_tabla2.isChecked(), np2_4.getValue(),
                                sp2_4.getValue(), tv4_tabla2.getText().toString(), cb4_tabla2.isChecked(),
                                np2_5.getValue(), sp2_5.getValue(), tv5_tabla2.getText().toString(),
                                cb5_tabla2.isChecked())) {
                            item.setEnabled(true);
                            return false;
                        }
                        editarDimensionamientoTablaValoracionImpacto(idsParametrizacionImpacto, np3_1.getValue(),
                                cb1_tabla3.isChecked(), np3_2.getValue(), cb2_tabla3.isChecked(),
                                np3_3.getValue(), cb3_tabla3.isChecked(), np3_4.getValue(),
                                cb4_tabla3.isChecked(), np3_5.getValue(), cb5_tabla3.isChecked());

                        editarDimensionamientoTablaValoracionCtrlSeguridad(idsParametrizacionCntrlSeguridad, np4_1.getValue(),
                                cb1_tabla4.isChecked(), np4_2.getValue(), cb2_tabla4.isChecked(),
                                np4_3.getValue(), cb3_tabla4.isChecked(), np4_4.getValue(),
                                cb4_tabla4.isChecked(), np4_5.getValue(), cb5_tabla4.isChecked());
                        setResult(1, resultIntent);
                        finish();
                        return true;
                    }

                } else{
                    Toast.makeText(this, AddProjectActivityConstants.ERROR_BOTON_ACEPTAR,
                            Toast.LENGTH_SHORT).show();
                    item.setEnabled(true);
                    return false;
                }
            case R.id.action_cancelar:
                setResult(0, resultIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_aceptar_cancelar, menu);
        return true;
    }


    private boolean comprobarFragmentoDetallesProyecto() {

        Boolean flag = true;

        Fragment fr1 = (Fragment) adapter.instantiateItem(viewPager, 0);

        tilNombreProyecto = (TextInputLayout) fr1.getView().findViewById(R.id.nombreProyectoWrapper);
        tilNombreProyecto.setErrorEnabled(true);
        etNombreProyecto = (EditText) fr1.getView().findViewById(R.id.nombreProyecto);

        tilDirectorProyecto = (TextInputLayout) fr1.getView().findViewById(R.id.directorProyectoWrapper);
        tilDirectorProyecto.setErrorEnabled(true);
        etDirectorProyecto = (EditText) fr1.getView().findViewById(R.id.directorProyecto);

        tilDescripcionProyecto= (TextInputLayout) fr1.getView().findViewById(R.id.descripcionProyectoWrapper);
        tilDescripcionProyecto.setErrorEnabled(true);
        etDescripcionProyecto = (EditText) fr1.getView().findViewById(R.id.descripcionProyecto);

        tilVersionProyecto = (TextInputLayout) fr1.getView().findViewById(R.id.versionProyectoWrapper);
        tilVersionProyecto.setErrorEnabled(true);
        etVersiónProyecto = (EditText) fr1.getView().findViewById(R.id.versionProyecto);

        tilFechaCreacionProyecto = (TextInputLayout) fr1.getView().findViewById(R.id.fechaCreacionProyectoWrapper);
        etFechaCreacionProyecto = (EditText) fr1.getView().findViewById(R.id.fechaCreacionProyecto);

        cbActivadoProyecto = (CheckBox) fr1.getView().findViewById(R.id.proyectoActivado);

        if (etNombreProyecto.getText().toString().isEmpty()) {
            flag = false;
            tilNombreProyecto.setError(AddProjectActivityConstants.ERROR_NOMBRE_PROYECTO);
        } else {
            tilNombreProyecto.setError(null);
        }

        if (etDirectorProyecto.getText().toString().isEmpty()) {
            flag = false;
            tilDirectorProyecto.setError(AddProjectActivityConstants.ERROR_DIRECTOR_PROYECTO);
        } else {
            tilDirectorProyecto.setError(null);
        }

        if( etDescripcionProyecto.getText().toString().isEmpty()) {
            flag = false;
            tilDescripcionProyecto.setError(AddProjectActivityConstants.ERROR_DESC_PROYECTO);
        } else {
            tilDescripcionProyecto.setError(null);
        }

        if (etVersiónProyecto.getText().toString().isEmpty()) {
            flag = false;
            tilVersionProyecto.setError(AddProjectActivityConstants.ERROR_VERSION_PROYECTO);
        } else {
            tilVersionProyecto.setError(null);
        }
        if (!flag) {
            viewPager.setCurrentItem(AddProjectActivityConstants.TAB_DATOS_PROYECTO);
        }

        return flag;
    }



    private boolean comprobarFragmentoDimensionamientoProyectoTablaActivos() {

        Boolean flag = true;

        Fragment fr2 = (Fragment) adapter.instantiateItem(viewPager, 1);

        Spinner sp = (Spinner) fr2.getView().findViewById(R.id.spnMySpinner);

        //Comprobamos los valores de parametrizar Activos
        et1_1_tabla1 = (EditText) fr2.getView().findViewById(R.id.TxtInputTable1Row1_1);
        et1_2_tabla1 = (EditText) fr2.getView().findViewById(R.id.TxtInputTable1Row1_2);
        et1_3_tabla1 = (EditText) fr2.getView().findViewById(R.id.TxtInputTable1Row1_3);
        et2_1_tabla1 = (EditText) fr2.getView().findViewById(R.id.TxtInputTable1Row2_1);
        et2_2_tabla1 = (EditText) fr2.getView().findViewById(R.id.TxtInputTable1Row2_2);
        et2_3_tabla1 = (EditText) fr2.getView().findViewById(R.id.TxtInputTable1Row2_3);
        et3_1_tabla1 = (EditText) fr2.getView().findViewById(R.id.TxtInputTable1Row3_1);
        et3_2_tabla1 = (EditText) fr2.getView().findViewById(R.id.TxtInputTable1Row3_2);
        et3_3_tabla1 = (EditText) fr2.getView().findViewById(R.id.TxtInputTable1Row3_3);
        et4_1_tabla1 = (EditText) fr2.getView().findViewById(R.id.TxtInputTable1Row4_1);
        et4_2_tabla1 = (EditText) fr2.getView().findViewById(R.id.TxtInputTable1Row4_2);
        et4_3_tabla1 = (EditText) fr2.getView().findViewById(R.id.TxtInputTable1Row4_3);
        et5_1_tabla1 = (EditText) fr2.getView().findViewById(R.id.TxtInputTable1Row5_1);
        et5_2_tabla1 = (EditText) fr2.getView().findViewById(R.id.TxtInputTable1Row5_2);
        et5_3_tabla1 = (EditText) fr2.getView().findViewById(R.id.TxtInputTable1Row5_3);

        cb1_tabla1 = (CheckBox) fr2.getView().findViewById(R.id.checkBoxTable1Row1);
        cb2_tabla1 = (CheckBox) fr2.getView().findViewById(R.id.checkBoxTable1Row2);
        cb3_tabla1 = (CheckBox) fr2.getView().findViewById(R.id.checkBoxTable1Row3);
        cb4_tabla1 = (CheckBox) fr2.getView().findViewById(R.id.checkBoxTable1Row4);
        cb5_tabla1 = (CheckBox) fr2.getView().findViewById(R.id.checkBoxTable1Row5);

        if (et1_1_tabla1.getError() != null || et1_2_tabla1.getError() != null ||
                et1_3_tabla1.getError() != null || et2_1_tabla1.getError() != null ||
                et2_2_tabla1.getError() != null || et2_3_tabla1.getError() != null ||
                et4_1_tabla1.getError() != null || et4_2_tabla1.getError() != null ||
                et4_3_tabla1.getError() != null || et5_1_tabla1.getError() != null ||
                et5_2_tabla1.getError() != null || et5_3_tabla1.getError() != null)
            flag = false;

        if (!flag) {
            viewPager.setCurrentItem(AddProjectActivityConstants.TAB_DIMENSIONAMIENTO);
            sp.setSelection(0);
        }

        return flag;
    }

    private boolean comprobarFragmentoDimensionamientoProyectoTablaVulnerabilidad() {

        Boolean flag = true;

        Fragment fr2 = (Fragment) adapter.instantiateItem(viewPager, 1);
        Spinner sp = (Spinner) fr2.getView().findViewById(R.id.spnMySpinner);

        //Cargar ñps sp y los np
        np2_1 = (NumberPicker) fr2.getView().findViewById(R.id.numberPicker1_1Table2);
        sp2_1 = (NumberPicker) fr2.getView().findViewById(R.id.stringPicker1_2Table2);
        np2_2 = (NumberPicker) fr2.getView().findViewById(R.id.numberPicker2_1Table2);
        sp2_2 = (NumberPicker) fr2.getView().findViewById(R.id.stringPicker2_2Table2);
        np2_3 = (NumberPicker) fr2.getView().findViewById(R.id.numberPicker3_1Table2);
        sp2_3 = (NumberPicker) fr2.getView().findViewById(R.id.stringPicker3_2Table2);
        np2_4 = (NumberPicker) fr2.getView().findViewById(R.id.numberPicker4_1Table2);
        sp2_4 = (NumberPicker) fr2.getView().findViewById(R.id.stringPicker4_2Table2);
        np2_5 = (NumberPicker) fr2.getView().findViewById(R.id.numberPicker5_1Table2);
        sp2_5 = (NumberPicker) fr2.getView().findViewById(R.id.stringPicker5_2Table2);

        //Comprobamos los valores de parametrizar Vulnerabilidad
        tv1_tabla2 = (TextView) fr2.getView().findViewById(R.id.TextViewTable2Row1_3);
        tv2_tabla2 = (TextView) fr2.getView().findViewById(R.id.TextViewTable2Row2_3);
        tv3_tabla2 = (TextView) fr2.getView().findViewById(R.id.TextViewTable2Row3_3);
        tv4_tabla2 = (TextView) fr2.getView().findViewById(R.id.TextViewTable2Row4_3);
        tv5_tabla2 = (TextView) fr2.getView().findViewById(R.id.TextViewTable2Row5_3);

        cb1_tabla2 = (CheckBox) fr2.getView().findViewById(R.id.checkBoxTable2Row1);
        cb2_tabla2 = (CheckBox) fr2.getView().findViewById(R.id.checkBoxTable2Row2);
        cb3_tabla2 = (CheckBox) fr2.getView().findViewById(R.id.checkBoxTable2Row3);
        cb4_tabla2 = (CheckBox) fr2.getView().findViewById(R.id.checkBoxTable2Row4);
        cb5_tabla2 = (CheckBox) fr2.getView().findViewById(R.id.checkBoxTable2Row5);

        if (tv1_tabla2.getError() != null || tv2_tabla2.getError() != null ||
                tv3_tabla2.getError() != null || tv4_tabla2.getError() != null ||
                tv5_tabla2.getError() != null)
            flag = false;

        if (!flag) {
            viewPager.setCurrentItem(AddProjectActivityConstants.TAB_DIMENSIONAMIENTO);
            sp.setSelection(1);
        }

        return flag;
    }


    private boolean cargarDatosTablaImpacto() {

        Fragment fr2 = (Fragment) adapter.instantiateItem(viewPager, 1);

        np3_1 = (NumberPicker) fr2.getView().findViewById(R.id.numberPicker1Table3);
        np3_2 = (NumberPicker) fr2.getView().findViewById(R.id.numberPicker2Table3);
        np3_3 = (NumberPicker) fr2.getView().findViewById(R.id.numberPicker3Table3);
        np3_4 = (NumberPicker) fr2.getView().findViewById(R.id.numberPicker4Table3);
        np3_5 = (NumberPicker) fr2.getView().findViewById(R.id.numberPicker5Table3);

        cb1_tabla3 = (CheckBox) fr2.getView().findViewById(R.id.checkBoxTable3Row1);
        cb2_tabla3 = (CheckBox) fr2.getView().findViewById(R.id.checkBoxTable3Row2);
        cb3_tabla3 = (CheckBox) fr2.getView().findViewById(R.id.checkBoxTable3Row3);
        cb4_tabla3 = (CheckBox) fr2.getView().findViewById(R.id.checkBoxTable3Row4);
        cb5_tabla3 = (CheckBox) fr2.getView().findViewById(R.id.checkBoxTable3Row5);

        return true;

    }

    private boolean cargarDatosTablaCtrlSeguridad() {

        Fragment fr2 = (Fragment) adapter.instantiateItem(viewPager, 1);

        np4_1 = (NumberPicker) fr2.getView().findViewById(R.id.numberPicker1Table4);
        np4_2 = (NumberPicker) fr2.getView().findViewById(R.id.numberPicker2Table4);
        np4_3 = (NumberPicker) fr2.getView().findViewById(R.id.numberPicker3Table4);
        np4_4 = (NumberPicker) fr2.getView().findViewById(R.id.numberPicker4Table4);
        np4_5 = (NumberPicker) fr2.getView().findViewById(R.id.numberPicker5Table4);

        cb1_tabla4 = (CheckBox) fr2.getView().findViewById(R.id.checkBoxTable4Row1);
        cb2_tabla4 = (CheckBox) fr2.getView().findViewById(R.id.checkBoxTable4Row2);
        cb3_tabla4 = (CheckBox) fr2.getView().findViewById(R.id.checkBoxTable4Row3);
        cb4_tabla4 = (CheckBox) fr2.getView().findViewById(R.id.checkBoxTable4Row4);
        cb5_tabla4 = (CheckBox) fr2.getView().findViewById(R.id.checkBoxTable4Row5);

        return true;
    }

    @Override
    public void anadirIdsParametrizacionActivos(List<Long> idsParametrizacionActivo) {
        this.idsParametrizacionActivos = idsParametrizacionActivo;
    }

    @Override
    public void anadirIdsParametrizacionVulnerabilidad(List<Long> idsParametrizacionVulnerabilidad) {
        this.idsParametrizacionVulnerabilidad = idsParametrizacionVulnerabilidad;
    }

    @Override
    public void anadirIdsParametrizacionImpacto(List<Long> idsParametrizacionImpacto) {
        this.idsParametrizacionImpacto = idsParametrizacionImpacto;
    }

    @Override
    public void anadirIdsParametrizacionCtrlSeguridad(List<Long> idsParametrizacionCntrlSeguridad) {
        this.idsParametrizacionCntrlSeguridad = idsParametrizacionCntrlSeguridad;
    }


    public class AddProjectFragmentPagerAdapter extends FragmentPagerAdapter {

        Long idProyecto;

        public AddProjectFragmentPagerAdapter(FragmentManager fm,Long idProyecto) {
            super(fm);
            this.idProyecto = idProyecto;
        }

        @Override
        public int getCount() {
            return AddProjectActivityConstants.PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {

            Fragment f = null;
            Bundle args = new Bundle();
            args.putLong("idProyecto", idProyecto);

            switch(position) {
                case AddProjectActivityConstants.TAB_DATOS_PROYECTO:
                    f = AddProjectDetailsFragment.newInstance();
                    f.setArguments(args);
                    break;
                case AddProjectActivityConstants.TAB_DIMENSIONAMIENTO:
                    f = SizingProjectFragment.newInstance();
                    f.setArguments(args);
                    break;
            }
            return f;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return AddProjectActivityConstants.tabTitles[position];
        }
    }

    private Long crearProyecto(String nombreProyecto, String directorProyecto,
                              String descripcionProyecto,String version, String fechaCreacion,
                              Boolean activado) {
        return service.crearProyecto(nombreProyecto, directorProyecto, descripcionProyecto, version,
                fechaCreacion, activado);

    }

    private boolean crearDimensionamientoTablaValoracionActivos( Long idProyecto, String rangoInferior1, String rangoSuperior1,
                                                               String valor1, Boolean activado1,
                                                               String rangoInferior2, String rangoSuperior2,
                                                               String valor2, Boolean activado2,
                                                               String rangoInferior3, String rangoSuperior3,
                                                               String valor3, Boolean activado3,
                                                               String rangoInferior4, String rangoSuperior4,
                                                               String valor4, Boolean activado4,
                                                               String rangoInferior5, String rangoSuperior5,
                                                               String valor5, Boolean activado5) {

        if (rangoInferior1.isEmpty() || rangoSuperior1.isEmpty() || valor1.isEmpty() ||
                rangoInferior2.isEmpty() || rangoSuperior2.isEmpty() || valor2.isEmpty() ||
                rangoInferior3.isEmpty() || rangoSuperior3.isEmpty() || valor3.isEmpty() ||
                rangoInferior4.isEmpty() || rangoSuperior4.isEmpty() || valor4.isEmpty() ||
                rangoInferior5.isEmpty() || rangoSuperior5.isEmpty() || valor5.isEmpty()) {
            return false;
        }

        service.crearParametrizacionActivo(idProyecto,(long)1,
                Integer.parseInt(rangoSuperior1.replace(".","")),
                Integer.parseInt(rangoInferior1.replace(".","")),
                Integer.parseInt(valor1.replace(".","")),activado1);

        service.crearParametrizacionActivo(idProyecto,(long)2,
                Integer.parseInt(rangoSuperior2.replace(".","")),
                Integer.parseInt(rangoInferior2.replace(".","")),
                Integer.parseInt(valor2.replace(".","")),activado2);

        service.crearParametrizacionActivo(idProyecto,(long)3,
                Integer.parseInt(rangoSuperior3.replace(".","")),
                Integer.parseInt(rangoInferior3.replace(".","")),
                Integer.parseInt(valor3.replace(".","")),activado3);

        service.crearParametrizacionActivo(idProyecto,(long)4,
                Integer.parseInt(rangoSuperior4.replace(".","")),
                Integer.parseInt(rangoInferior4.replace(".","")),
                Integer.parseInt(valor4.replace(".","")),activado4);

        service.crearParametrizacionActivo(idProyecto,(long)5,
                Integer.parseInt(rangoSuperior5.replace(".","")),
                Integer.parseInt(rangoInferior5.replace(".","")),
                Integer.parseInt(valor5.replace(".","")),activado5);
        return true;
    }


    private boolean crearDimensionamientoTablaVulnerabilidad(Long idProyecto, Integer valorTiempo1, Integer valorTipoTiempo1,
                                                         String valor1, boolean activado1,
                                                         Integer valorTiempo2, Integer valorTipoTiempo2,
                                                         String valor2, boolean activado2,
                                                         Integer valorTiempo3, Integer valorTipoTiempo3,
                                                         String valor3, boolean activado3,
                                                         Integer valorTiempo4, Integer valorTipoTiempo4,
                                                         String valor4, boolean activado4,
                                                         Integer valorTiempo5, Integer valorTipoTiempo5,
                                                         String valor5, boolean activado5) {


        if (valor1.isEmpty() || valor2.isEmpty() || valor3.isEmpty() || valor4.isEmpty() || valor5.isEmpty()) {
            return false;
        }
        service.crearParametrizacionVulnerabilidad(idProyecto, (long)1, valorTiempo1, valorTipoTiempo1,
                Double.parseDouble(valor1.replace(",",".")),activado1);
        service.crearParametrizacionVulnerabilidad(idProyecto, (long)2, valorTiempo2, valorTipoTiempo2,
                Double.parseDouble(valor2.replace(",",".")),activado2);
        service.crearParametrizacionVulnerabilidad(idProyecto, (long)3, valorTiempo3, valorTipoTiempo3,
                Double.parseDouble(valor3.replace(",",".")), activado3);
        service.crearParametrizacionVulnerabilidad(idProyecto, (long)4, valorTiempo4, valorTipoTiempo4,
                Double.parseDouble(valor4.replace(",",".")), activado4);
        service.crearParametrizacionVulnerabilidad(idProyecto, (long)5, valorTiempo5, valorTipoTiempo5,
                Double.parseDouble(valor5.replace(",",".")), activado5);

        return true;
    }

    private void crearDimensionamientoTablaValoracionImpacto(Long idProyecto,
                                                             Integer valor1, Boolean activado1,
                                                             Integer valor2, Boolean activado2,
                                                             Integer valor3, Boolean activado3,
                                                             Integer valor4, Boolean activado4,
                                                             Integer valor5, Boolean activado5) {

        service.crearParametrizacionImpacto(idProyecto,(long)1, valor1, activado1);
        service.crearParametrizacionImpacto(idProyecto,(long)2, valor2, activado2);
        service.crearParametrizacionImpacto(idProyecto,(long)3, valor3, activado3);
        service.crearParametrizacionImpacto(idProyecto,(long)4, valor4, activado4);
        service.crearParametrizacionImpacto(idProyecto,(long)5, valor5, activado5);

    }



    private void crearDimensionamientoTablaValoracionCtrlSeguridad(Long idProyecto,
                                                                   Integer valor1, Boolean activado1,
                                                                   Integer valor2, Boolean activado2,
                                                                   Integer valor3, Boolean activado3,
                                                                   Integer valor4, Boolean activado4,
                                                                   Integer valor5, Boolean activado5) {
        service.crearParametrizacionControlSeguridad(idProyecto,(long)1, valor1, activado1);
        service.crearParametrizacionControlSeguridad(idProyecto,(long)2, valor2, activado2);
        service.crearParametrizacionControlSeguridad(idProyecto,(long)3, valor3, activado3);
        service.crearParametrizacionControlSeguridad(idProyecto,(long)4, valor4, activado4);
        service.crearParametrizacionControlSeguridad(idProyecto,(long)5, valor5, activado5);
    }

    private void editarProyecto(Long idProyecto, String nombreProyecto, String directorProyecto,
                               String descripcionProyecto,String version, String fechaCreacion,
                               Boolean activado) {
        service.editarProyecto(idProyecto, nombreProyecto, directorProyecto, descripcionProyecto, version,
                fechaCreacion, activado);

    }

    private boolean editarDimensionamientoTablaValoracionActivos( List<Long> idsDimensionamientoActivo,
                                                                 String rangoInferior1, String rangoSuperior1,
                                                                 String valor1, Boolean activado1,
                                                                 String rangoInferior2, String rangoSuperior2,
                                                                 String valor2, Boolean activado2,
                                                                 String rangoInferior3, String rangoSuperior3,
                                                                 String valor3, Boolean activado3,
                                                                 String rangoInferior4, String rangoSuperior4,
                                                                 String valor4, Boolean activado4,
                                                                 String rangoInferior5, String rangoSuperior5,
                                                                 String valor5, Boolean activado5) {

        if (rangoInferior1.isEmpty() || rangoSuperior1.isEmpty() || valor1.isEmpty() ||
                rangoInferior2.isEmpty() || rangoSuperior2.isEmpty() || valor2.isEmpty() ||
                rangoInferior3.isEmpty() || rangoSuperior3.isEmpty() || valor3.isEmpty() ||
                rangoInferior4.isEmpty() || rangoSuperior4.isEmpty() || valor4.isEmpty() ||
                rangoInferior5.isEmpty() || rangoSuperior5.isEmpty() || valor5.isEmpty()) {
            return false;
        }

        service.editarParametrizacionActivo(idsDimensionamientoActivo.get(0),
                Integer.parseInt(rangoSuperior1.replace(".","")),
                Integer.parseInt(rangoInferior1.replace(".","")),
                Integer.parseInt(valor1.replace(".","")),activado1);

        service.editarParametrizacionActivo(idsDimensionamientoActivo.get(1),
                Integer.parseInt(rangoSuperior2.replace(".","")),
                Integer.parseInt(rangoInferior2.replace(".","")),
                Integer.parseInt(valor2.replace(".","")),activado2);

        service.editarParametrizacionActivo(idsDimensionamientoActivo.get(2),
                Integer.parseInt(rangoSuperior3.replace(".","")),
                Integer.parseInt(rangoInferior3.replace(".","")),
                Integer.parseInt(valor3.replace(".","")),activado3);

        service.editarParametrizacionActivo(idsDimensionamientoActivo.get(3),
                Integer.parseInt(rangoSuperior4.replace(".","")),
                Integer.parseInt(rangoInferior4.replace(".","")),
                Integer.parseInt(valor4.replace(".","")),activado4);

        service.editarParametrizacionActivo(idsDimensionamientoActivo.get(4),
                Integer.parseInt(rangoSuperior5.replace(".","")),
                Integer.parseInt(rangoInferior5.replace(".","")),
                Integer.parseInt(valor5.replace(".","")),activado5);
        return true;
    }


    private boolean editarDimensionamientoTablaVulnerabilidad(List<Long> idsDimensionamientoVulnerabilidad,
                                                              Integer valorTiempo1, Integer valorTipoTiempo1,
                                                             String valor1, boolean activado1,
                                                             Integer valorTiempo2, Integer valorTipoTiempo2,
                                                             String valor2, boolean activado2,
                                                             Integer valorTiempo3, Integer valorTipoTiempo3,
                                                             String valor3, boolean activado3,
                                                             Integer valorTiempo4, Integer valorTipoTiempo4,
                                                             String valor4, boolean activado4,
                                                             Integer valorTiempo5, Integer valorTipoTiempo5,
                                                             String valor5, boolean activado5) {


        if (valor1.isEmpty() || valor2.isEmpty() || valor3.isEmpty() || valor4.isEmpty() || valor5.isEmpty()) {
            return false;
        }
        service.editarParametrizacionVulnerabilidad(idsDimensionamientoVulnerabilidad.get(0),
                valorTiempo1, valorTipoTiempo1, Double.parseDouble(valor1.replace(",",".")),activado1);
        service.editarParametrizacionVulnerabilidad(idsDimensionamientoVulnerabilidad.get(1),
                valorTiempo2, valorTipoTiempo2, Double.parseDouble(valor2.replace(",",".")),activado2);
        service.editarParametrizacionVulnerabilidad(idsDimensionamientoVulnerabilidad.get(2),
                valorTiempo3, valorTipoTiempo3, Double.parseDouble(valor3.replace(",",".")), activado3);
        service.editarParametrizacionVulnerabilidad(idsDimensionamientoVulnerabilidad.get(3),
                valorTiempo4, valorTipoTiempo4, Double.parseDouble(valor4.replace(",",".")), activado4);
        service.editarParametrizacionVulnerabilidad(idsDimensionamientoVulnerabilidad.get(4),
                valorTiempo5, valorTipoTiempo5, Double.parseDouble(valor5.replace(",",".")), activado5);

        return true;
    }

    private void editarDimensionamientoTablaValoracionImpacto(List<Long> idsDimensionamientoImpacto,
                                                             Integer valor1, Boolean activado1,
                                                             Integer valor2, Boolean activado2,
                                                             Integer valor3, Boolean activado3,
                                                             Integer valor4, Boolean activado4,
                                                             Integer valor5, Boolean activado5) {

        service.editarParametrizacionImpacto(idsDimensionamientoImpacto.get(0), valor1, activado1);
        service.editarParametrizacionImpacto(idsDimensionamientoImpacto.get(1), valor2, activado2);
        service.editarParametrizacionImpacto(idsDimensionamientoImpacto.get(2), valor3, activado3);
        service.editarParametrizacionImpacto(idsDimensionamientoImpacto.get(3), valor4, activado4);
        service.editarParametrizacionImpacto(idsDimensionamientoImpacto.get(4), valor5, activado5);

    }



    private void editarDimensionamientoTablaValoracionCtrlSeguridad(List<Long> idsDimensionamientoCtrlSeguridad,
                                                                   Integer valor1, Boolean activado1,
                                                                   Integer valor2, Boolean activado2,
                                                                   Integer valor3, Boolean activado3,
                                                                   Integer valor4, Boolean activado4,
                                                                   Integer valor5, Boolean activado5) {
        service.editarParametrizacionControlSeguridad(idsDimensionamientoCtrlSeguridad.get(0),
                valor1, activado1);
        service.editarParametrizacionControlSeguridad(idsDimensionamientoCtrlSeguridad.get(1),
                valor2, activado2);
        service.editarParametrizacionControlSeguridad(idsDimensionamientoCtrlSeguridad.get(2),
                valor3, activado3);
        service.editarParametrizacionControlSeguridad(idsDimensionamientoCtrlSeguridad.get(3),
                valor4, activado4);
        service.editarParametrizacionControlSeguridad(idsDimensionamientoCtrlSeguridad.get(4),
                valor5, activado5);
    }





}
