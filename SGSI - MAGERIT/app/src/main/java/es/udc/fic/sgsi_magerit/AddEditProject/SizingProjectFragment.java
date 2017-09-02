package es.udc.fic.sgsi_magerit.AddEditProject;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;
import es.udc.fic.sgsi_magerit.Model.ProjectSizing.ParametrizacionActivo;
import es.udc.fic.sgsi_magerit.Model.ProjectSizing.ParametrizacionControlSeguridad;
import es.udc.fic.sgsi_magerit.Model.ProjectSizing.ParametrizacionDTO;
import es.udc.fic.sgsi_magerit.Model.ProjectSizing.ParametrizacionImpacto;
import es.udc.fic.sgsi_magerit.Model.ProjectSizing.ParametrizacionVulnerabilidad;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelService;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;


/**
 * A simple {@link Fragment} subclass.
 */
public class SizingProjectFragment extends Fragment implements View.OnFocusChangeListener {
    Long idProyecto;
    Integer posicionCursor = -1;
    NumberFormat format;
    boolean overridingText = false;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;
    private String[] valoresStringPicker;
    private ModelService service;
    private ParametrizacionDTO parametrizacionDTO;
    private OnEditProject onEditProject;


    public static SizingProjectFragment newInstance() {
        SizingProjectFragment fragment = new SizingProjectFragment();
        return fragment;
    }

    public SizingProjectFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            onEditProject = (OnEditProject) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


    public interface OnEditProject{
        public void anadirIdsParametrizacionActivos(List<Long> idsParametrizacionActivo);
        public void anadirIdsParametrizacionVulnerabilidad(List<Long> idsParametrizacionVulnerabilidad);
        public void anadirIdsParametrizacionImpacto(List<Long> idsParametrizacionImpacto);
        public void anadirIdsParametrizacionCtrlSeguridad(List<Long> idsParametrizacionCntrlSeguridad);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        service = new ModelServiceImpl(this.getContext(), GlobalConstants.DATABASE_NAME,1);
        Bundle args = getArguments();
        if(args != null){
            this.idProyecto = args.getLong("idProyecto");
        }

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_sizing_project, container, false);

        // Añadimos el Spinner
        spinner = (Spinner) view.findViewById(R.id.spnMySpinner);
        spinnerAdapter =  ArrayAdapter.createFromResource(getContext(), R.array.Sizing_spinner,
                android.R.layout.simple_spinner_item);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) spinnerAdapter);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        switch (position){
                            case 0:
                                view.findViewById(R.id.tabla2).setVisibility(TableLayout.GONE);
                                view.findViewById(R.id.tabla3).setVisibility(TableLayout.GONE);
                                view.findViewById(R.id.tabla4).setVisibility(TableLayout.GONE);
                                view.findViewById(R.id.tabla1).setVisibility(TableLayout.VISIBLE);
                                break;
                            case 1:
                                view.findViewById(R.id.tabla1).setVisibility(TableLayout.GONE);
                                view.findViewById(R.id.tabla3).setVisibility(TableLayout.GONE);
                                view.findViewById(R.id.tabla4).setVisibility(TableLayout.GONE);
                                view.findViewById(R.id.tabla2).setVisibility(TableLayout.VISIBLE);
                                break;
                            case 2:
                                view.findViewById(R.id.tabla1).setVisibility(TableLayout.GONE);
                                view.findViewById(R.id.tabla2).setVisibility(TableLayout.GONE);
                                view.findViewById(R.id.tabla4).setVisibility(TableLayout.GONE);
                                view.findViewById(R.id.tabla3).setVisibility(TableLayout.VISIBLE);
                                break;
                            case 3:
                                view.findViewById(R.id.tabla1).setVisibility(TableLayout.GONE);
                                view.findViewById(R.id.tabla2).setVisibility(TableLayout.GONE);
                                view.findViewById(R.id.tabla3).setVisibility(TableLayout.GONE);
                                view.findViewById(R.id.tabla4).setVisibility(TableLayout.VISIBLE);
                                break;
                            default:
                                view.findViewById(R.id.tabla1).setVisibility(TableLayout.GONE);
                                view.findViewById(R.id.tabla2).setVisibility(TableLayout.GONE);
                                view.findViewById(R.id.tabla3).setVisibility(TableLayout.GONE);
                                view.findViewById(R.id.tabla4).setVisibility(TableLayout.GONE);
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        // añadimos los Listeners y Controles sobre las tablas creadas
        if (idProyecto != 0) {
            parametrizacionDTO = service.obtenerParametrizacionDeProyecto(idProyecto);
            enviarIdsAActividad(parametrizacionDTO);
            tablaActivos(view, parametrizacionDTO.getpActivo());
            tablaVulnerabilidad(view, parametrizacionDTO.getpVulnerabilidad());
            tablaImpacto(view, parametrizacionDTO.getpImpacto());
            tablaControlSeguridad(view, parametrizacionDTO.getpControlSeguridad());
        } else {
            tablaActivos(view, null);
            tablaVulnerabilidad(view, null);
            tablaImpacto(view, null);
            tablaControlSeguridad(view,null);
        }
        return view;
    }

    private void enviarIdsAActividad(ParametrizacionDTO parametrizacion) {
        List<Long> idsParametrizacionActivos = new ArrayList<Long>();
        List<Long> idsParametrizacionVulnerabilidad = new ArrayList<Long>();
        List<Long> idsParametrizacionImpacto = new ArrayList<Long>();
        List<Long> idsParametrizacionCntrlSeguridad = new ArrayList<Long>();

        idsParametrizacionActivos.add(parametrizacion.getpActivo().get(0).
                getIdParametrizacionActivo());
        idsParametrizacionActivos.add(parametrizacion.getpActivo().get(1).
                getIdParametrizacionActivo());
        idsParametrizacionActivos.add(parametrizacion.getpActivo().get(2).
                getIdParametrizacionActivo());
        idsParametrizacionActivos.add(parametrizacion.getpActivo().get(3).
                getIdParametrizacionActivo());
        idsParametrizacionActivos.add(parametrizacion.getpActivo().get(4).
                getIdParametrizacionActivo());

        idsParametrizacionVulnerabilidad.add(parametrizacion.getpVulnerabilidad().get(0).
                getIdParametrizacionVulnerabilidad());
        idsParametrizacionVulnerabilidad.add(parametrizacion.getpVulnerabilidad().get(1).
                getIdParametrizacionVulnerabilidad());
        idsParametrizacionVulnerabilidad.add(parametrizacion.getpVulnerabilidad().get(2).
                getIdParametrizacionVulnerabilidad());
        idsParametrizacionVulnerabilidad.add(parametrizacion.getpVulnerabilidad().get(3).
                getIdParametrizacionVulnerabilidad());
        idsParametrizacionVulnerabilidad.add(parametrizacion.getpVulnerabilidad().get(4).
                getIdParametrizacionVulnerabilidad());

        idsParametrizacionImpacto.add(parametrizacion.getpImpacto().get(0).
                getIdParametrizacionImpacto());
        idsParametrizacionImpacto.add(parametrizacion.getpImpacto().get(1).
                getIdParametrizacionImpacto());
        idsParametrizacionImpacto.add(parametrizacion.getpImpacto().get(2).
                getIdParametrizacionImpacto());
        idsParametrizacionImpacto.add(parametrizacion.getpImpacto().get(3).
                getIdParametrizacionImpacto());
        idsParametrizacionImpacto.add(parametrizacion.getpImpacto().get(4).
                getIdParametrizacionImpacto());

        idsParametrizacionCntrlSeguridad.add(parametrizacion.getpControlSeguridad().get(0).
                getIdParametrizacionControlSeguridad());
        idsParametrizacionCntrlSeguridad.add(parametrizacion.getpControlSeguridad().get(1).
                getIdParametrizacionControlSeguridad());
        idsParametrizacionCntrlSeguridad.add(parametrizacion.getpControlSeguridad().get(2).
                getIdParametrizacionControlSeguridad());
        idsParametrizacionCntrlSeguridad.add(parametrizacion.getpControlSeguridad().get(3).
                getIdParametrizacionControlSeguridad());
        idsParametrizacionCntrlSeguridad.add(parametrizacion.getpControlSeguridad().get(4).
                getIdParametrizacionControlSeguridad());

        onEditProject.anadirIdsParametrizacionActivos(idsParametrizacionActivos);
        onEditProject.anadirIdsParametrizacionVulnerabilidad(idsParametrizacionVulnerabilidad);
        onEditProject.anadirIdsParametrizacionImpacto(idsParametrizacionImpacto);
        onEditProject.anadirIdsParametrizacionCtrlSeguridad(idsParametrizacionCntrlSeguridad);

    }




    private void annadirControlCheckBoxes (CheckBox cb1, CheckBox cb2, CheckBox cb3, CheckBox cb4,
                                           CheckBox cb5) {
        cb1.setOnClickListener(new checkCheckBoxClick(cb1, cb2, cb3, cb4, cb5));
        cb2.setOnClickListener(new checkCheckBoxClick(cb1, cb2, cb3, cb4, cb5));
        cb3.setOnClickListener(new checkCheckBoxClick(cb1, cb2, cb3, cb4, cb5));
        cb4.setOnClickListener(new checkCheckBoxClick(cb1, cb2, cb3, cb4, cb5));
        cb5.setOnClickListener(new checkCheckBoxClick(cb1, cb2, cb3, cb4, cb5));
    }



    private void cargarValoresTablaActivos(List<ParametrizacionActivo> pActivos, EditText et1_1, EditText et1_2,
                                           EditText et1_3, CheckBox cbt1_1, EditText et2_1, EditText et2_2,
                                           EditText et2_3, CheckBox cbt1_2,EditText et3_1, EditText et3_2,
                                           EditText et3_3, CheckBox cbt1_3,EditText et4_1, EditText et4_2,
                                           EditText et4_3, CheckBox cbt1_4,EditText et5_1, EditText et5_2,
                                           EditText et5_3, CheckBox cbt1_5) {

        if (pActivos == null) {
            et1_1.setText(AddProjectActivityConstants.RANGO1_VALOR1_TABLA1);
            et2_1.setText(AddProjectActivityConstants.RANGO2_VALOR1_TABLA1);
            et3_1.setText(AddProjectActivityConstants.RANGO3_VALOR1_TABLA1);
            et4_1.setText(AddProjectActivityConstants.RANGO4_VALOR1_TABLA1);
            et5_1.setText(AddProjectActivityConstants.RANGO5_VALOR1_TABLA1);
            et5_2.setText(AddProjectActivityConstants.RANGO5_VALOR2_TABLA1);

            et1_3.setText(AddProjectActivityConstants.VALOR1_TABLA1);
            et2_3.setText(AddProjectActivityConstants.VALOR2_TABLA1);
            et3_3.setText(AddProjectActivityConstants.VALOR3_TABLA1);
            et4_3.setText(AddProjectActivityConstants.VALOR4_TABLA1);
            et5_3.setText(AddProjectActivityConstants.VALOR5_TABLA1);
        } else {
            if (pActivos.size() == 5) {
                et1_1.setText(pActivos.get(0).getRangoInferior().toString());
                et2_1.setText(pActivos.get(1).getRangoInferior().toString());
                et3_1.setText(pActivos.get(2).getRangoInferior().toString());
                et4_1.setText(pActivos.get(3).getRangoInferior().toString());
                et5_1.setText(pActivos.get(4).getRangoInferior().toString());
                et5_2.setText(pActivos.get(4).getRangoSuperior().toString());

                et1_3.setText(pActivos.get(0).getValor().toString());
                et2_3.setText(pActivos.get(1).getValor().toString());
                et3_3.setText(pActivos.get(2).getValor().toString());
                et4_3.setText(pActivos.get(3).getValor().toString());
                et5_3.setText(pActivos.get(4).getValor().toString());

                cbt1_1.setChecked(pActivos.get(0).getActivado());
                cbt1_2.setChecked(pActivos.get(1).getActivado());
                cbt1_3.setChecked(pActivos.get(2).getActivado());
                cbt1_4.setChecked(pActivos.get(3).getActivado());
                cbt1_5.setChecked(pActivos.get(4).getActivado());

            }
        }

        formatearEditText(et1_1, null, false);
        formatearEditText(et1_2,et2_1,false);
        formatearEditText(et2_2,et3_1,false);
        formatearEditText(et3_2,et4_1,false);
        formatearEditText(et4_2,et5_1,false);
        formatearEditText(et5_2,null, false);

        formatearEditText(et1_3,null, false);
        formatearEditText(et2_3,null, false);
        formatearEditText(et3_3,null, false);
        formatearEditText(et4_3,null, false);
        formatearEditText(et5_3,null, false);
    }

    private void tablaActivos (View view, List<ParametrizacionActivo> pActivos) {
        //Listeners para Parametrizar Activos
        format = NumberFormat.getInstance(Locale.GERMAN);
        EditText et1_1 = (EditText) view.findViewById(R.id.TxtInputTable1Row1_1);
        EditText et1_2 = (EditText) view.findViewById(R.id.TxtInputTable1Row1_2);
        EditText et1_3 = (EditText) view.findViewById(R.id.TxtInputTable1Row1_3);
        EditText et2_1 = (EditText) view.findViewById(R.id.TxtInputTable1Row2_1);
        EditText et2_2 = (EditText) view.findViewById(R.id.TxtInputTable1Row2_2);
        EditText et2_3 = (EditText) view.findViewById(R.id.TxtInputTable1Row2_3);
        EditText et3_1 = (EditText) view.findViewById(R.id.TxtInputTable1Row3_1);
        EditText et3_2 = (EditText) view.findViewById(R.id.TxtInputTable1Row3_2);
        EditText et3_3 = (EditText) view.findViewById(R.id.TxtInputTable1Row3_3);
        EditText et4_1 = (EditText) view.findViewById(R.id.TxtInputTable1Row4_1);
        EditText et4_2 = (EditText) view.findViewById(R.id.TxtInputTable1Row4_2);
        EditText et4_3 = (EditText) view.findViewById(R.id.TxtInputTable1Row4_3);
        EditText et5_1 = (EditText) view.findViewById(R.id.TxtInputTable1Row5_1);
        EditText et5_2 = (EditText) view.findViewById(R.id.TxtInputTable1Row5_2);
        EditText et5_3 = (EditText) view.findViewById(R.id.TxtInputTable1Row5_3);

        et1_1.addTextChangedListener(new textWatcherInRange(et1_1, null, et1_1, et1_2, et1_3, et2_1,
                et2_2, et2_3, et3_1, et3_2, et3_3, et4_1, et4_2, et4_3, et5_1, et5_2, et5_3));
        et1_1.setOnFocusChangeListener(this);
        et1_2.addTextChangedListener(new textWatcherInRange(et1_2, et2_1, et1_1, et1_2, et1_3, et2_1,
                et2_2, et2_3, et3_1, et3_2, et3_3, et4_1, et4_2, et4_3, et5_1, et5_2, et5_3));
        et1_2.setOnFocusChangeListener(this);
        et1_3.addTextChangedListener(new textWatcherOutOfRange1(et1_3, et1_1, et1_2));
        et1_3.setOnFocusChangeListener(this);

        et2_1.addTextChangedListener(new textWatcherInRange(et2_1, et1_2, et1_1, et1_2, et1_3, et2_1,
                et2_2, et2_3, et3_1, et3_2, et3_3, et4_1, et4_2, et4_3, et5_1, et5_2, et5_3));
        et2_1.setOnFocusChangeListener(this);
        et2_2.addTextChangedListener(new textWatcherInRange(et2_2, et3_1, et1_1, et1_2, et1_3, et2_1,
                et2_2, et2_3, et3_1, et3_2, et3_3, et4_1, et4_2, et4_3, et5_1, et5_2, et5_3));
        et2_2.setOnFocusChangeListener(this);
        et2_3.addTextChangedListener(new textWatcherOutOfRange1(et2_3, et2_1, et2_2));
        et2_3.setOnFocusChangeListener(this);

        et3_1.addTextChangedListener(new textWatcherInRange(et3_1, et2_2, et1_1, et1_2, et1_3, et2_1,
                et2_2, et2_3, et3_1, et3_2, et3_3, et4_1, et4_2, et4_3, et5_1, et5_2, et5_3));
        et3_1.setOnFocusChangeListener(this);
        et3_2.addTextChangedListener(new textWatcherInRange(et3_2, et4_1, et1_1, et1_2, et1_3, et2_1,
                et2_2, et2_3, et3_1, et3_2, et3_3, et4_1, et4_2, et4_3, et5_1, et5_2, et5_3));
        et3_2.setOnFocusChangeListener(this);
        et3_3.addTextChangedListener(new textWatcherOutOfRange1(et3_3, et3_1, et3_2));
        et3_3.setOnFocusChangeListener(this);

        et4_1.addTextChangedListener(new textWatcherInRange(et4_1, et3_2, et1_1, et1_2, et1_3, et2_1,
                et2_2, et2_3, et3_1, et3_2, et3_3, et4_1, et4_2, et4_3, et5_1, et5_2, et5_3));
        et4_1.setOnFocusChangeListener(this);
        et4_2.addTextChangedListener(new textWatcherInRange(et4_2, et5_1, et1_1, et1_2, et1_3, et2_1,
                et2_2, et2_3, et3_1, et3_2, et3_3, et4_1, et4_2, et4_3, et5_1, et5_2, et5_3));
        et4_2.setOnFocusChangeListener(this);
        et4_3.addTextChangedListener(new textWatcherOutOfRange1(et4_3, et4_1, et4_2));
        et4_3.setOnFocusChangeListener(this);

        et5_1.addTextChangedListener(new textWatcherInRange(et5_1, et4_2, et1_1, et1_2, et1_3, et2_1,
                et2_2, et2_3, et3_1, et3_2, et3_3, et4_1, et4_2, et4_3, et5_1, et5_2, et5_3));
        et5_1.setOnFocusChangeListener(this);
        et5_2.addTextChangedListener(new textWatcherInRange(et5_2, null, et1_1, et1_2, et1_3, et2_1,
                et2_2, et2_3, et3_1, et3_2, et3_3, et4_1, et4_2, et4_3, et5_1, et5_2, et5_3));
        et5_2.setOnFocusChangeListener(this);
        et5_3.addTextChangedListener(new textWatcherOutOfRange1(et5_3, et5_1, et5_2));
        et5_3.setOnFocusChangeListener(this);

        CheckBox cbt1_1 = (CheckBox) view.findViewById(R.id.checkBoxTable1Row1);
        CheckBox cbt1_2 = (CheckBox) view.findViewById(R.id.checkBoxTable1Row2);
        CheckBox cbt1_3 = (CheckBox) view.findViewById(R.id.checkBoxTable1Row3);
        CheckBox cbt1_4 = (CheckBox) view.findViewById(R.id.checkBoxTable1Row4);
        CheckBox cbt1_5 = (CheckBox) view.findViewById(R.id.checkBoxTable1Row5);

        annadirControlCheckBoxes(cbt1_1, cbt1_2, cbt1_3, cbt1_4, cbt1_5);
        cargarValoresTablaActivos(pActivos, et1_1, et1_2, et1_3, cbt1_1, et2_1, et2_2, et2_3, cbt1_2,
                et3_1, et3_2, et3_3, cbt1_3, et4_1, et4_2, et4_3, cbt1_4, et5_1, et5_2, et5_3, cbt1_5);
    }


    private void setMinMaxValuesToIntegerNumberPickerTable2(NumberPicker np, Integer v2) {
        np.setMaxValue(100);
        np.setMinValue(1);
    }

    private void setMinMaxValuesToStringNumberPickerTable2(NumberPicker sp) {
        sp.setMinValue(0);
        sp.setMaxValue(AddProjectActivityConstants.VALORES_STRING_SINGULAR.length-1);

    }


    private void cargarValoresTablaVulnerabilidad (List<ParametrizacionVulnerabilidad>
                                                   pVulnerabilidades, NumberPicker np1,
                                                   NumberPicker sp1, TextView tv1, CheckBox cbt2_1,
                                                   NumberPicker np2, NumberPicker sp2, TextView tv2,
                                                   CheckBox cbt2_2, NumberPicker np3,
                                                   NumberPicker sp3, TextView tv3, CheckBox cbt2_3,
                                                   NumberPicker np4, NumberPicker sp4, TextView tv4,
                                                   CheckBox cbt2_4, NumberPicker np5,
                                                   NumberPicker sp5, TextView tv5, CheckBox cbt2_5) {

        DecimalFormat df = new DecimalFormat("0.0000");
        Double op;
        sp1.setSaveFromParentEnabled(false);
        sp1.setSaveEnabled(true);
        sp2.setSaveFromParentEnabled(false);
        sp2.setSaveEnabled(true);
        sp3.setSaveFromParentEnabled(false);
        sp3.setSaveEnabled(true);
        sp4.setSaveFromParentEnabled(false);
        sp4.setSaveEnabled(true);
        sp5.setSaveFromParentEnabled(false);
        sp5.setSaveEnabled(true);

        setMinMaxValuesToIntegerNumberPickerTable2(np1,100);
        setMinMaxValuesToIntegerNumberPickerTable2(np2,60);
        setMinMaxValuesToIntegerNumberPickerTable2(np3,30);
        setMinMaxValuesToIntegerNumberPickerTable2(np4,30);
        setMinMaxValuesToIntegerNumberPickerTable2(np5,10);

        setMinMaxValuesToStringNumberPickerTable2(sp1);
        setMinMaxValuesToStringNumberPickerTable2(sp2);
        setMinMaxValuesToStringNumberPickerTable2(sp3);
        setMinMaxValuesToStringNumberPickerTable2(sp4);
        setMinMaxValuesToStringNumberPickerTable2(sp5);

        if (pVulnerabilidades == null) {
            np1.setValue(AddProjectActivityConstants.VALOR_NP1_TABLA2);
            sp1.setValue(AddProjectActivityConstants.VALOR_SP1_TABLA2);
            sp1.setDisplayedValues(AddProjectActivityConstants.VALORES_STRING_SINGULAR);
            np2.setValue(AddProjectActivityConstants.VALOR_NP2_TABLA2);
            sp2.setValue(AddProjectActivityConstants.VALOR_SP2_TABLA2);
            sp2.setDisplayedValues(AddProjectActivityConstants.VALORES_STRING_PLURAL);
            np3.setValue(AddProjectActivityConstants.VALOR_NP3_TABLA2);
            sp3.setValue(AddProjectActivityConstants.VALOR_SP3_TABLA2);
            sp3.setDisplayedValues(AddProjectActivityConstants.VALORES_STRING_PLURAL);
            np4.setValue(AddProjectActivityConstants.VALOR_NP4_TABLA2);
            sp4.setValue(AddProjectActivityConstants.VALOR_SP4_TABLA2);
            sp4.setDisplayedValues(AddProjectActivityConstants.VALORES_STRING_PLURAL);
            np5.setValue(AddProjectActivityConstants.VALOR_NP5_TABLA2);
            sp5.setValue(AddProjectActivityConstants.VALOR_SP5_TABLA2);
            sp5.setDisplayedValues(AddProjectActivityConstants.VALORES_STRING_SINGULAR);

            op = (1 / ((double) np1.getValue()));
            tv1.setText(df.format(op));

            op = (1 / ((double) np2.getValue() * AddProjectActivityConstants.DIAS_EN_SEMANA));
            tv2.setText(df.format(op));

            op = (1 / ((double) np3.getValue() * AddProjectActivityConstants.DIAS_EN_MES));
            tv3.setText(df.format(op));

            op = (1 / ((double) np4.getValue() * AddProjectActivityConstants.DIAS_EN_MES));
            tv4.setText(df.format(op));

            op = (1 / ((double) np5.getValue() * AddProjectActivityConstants.DIAS_EN_AÑO));
            tv5.setText(df.format(op));

        } else {
            if (pVulnerabilidades.size() == 5) {
                np1.setValue(pVulnerabilidades.get(0).getValorTiempo());
                sp1.setValue(pVulnerabilidades.get(0).getValorTipoTiempo());
                if (np1.getValue() == 1)
                    sp1.setDisplayedValues(AddProjectActivityConstants.VALORES_STRING_SINGULAR);
                else
                    sp1.setDisplayedValues(AddProjectActivityConstants.VALORES_STRING_PLURAL);
                np2.setValue(pVulnerabilidades.get(1).getValorTiempo());
                sp2.setValue(pVulnerabilidades.get(1).getValorTipoTiempo());
                if (np2.getValue() == 1)
                    sp2.setDisplayedValues(AddProjectActivityConstants.VALORES_STRING_SINGULAR);
                else
                    sp2.setDisplayedValues(AddProjectActivityConstants.VALORES_STRING_PLURAL);
                np3.setValue(pVulnerabilidades.get(2).getValorTiempo());
                sp3.setValue(pVulnerabilidades.get(2).getValorTipoTiempo());
                if (np3.getValue() == 1)
                    sp3.setDisplayedValues(AddProjectActivityConstants.VALORES_STRING_SINGULAR);
                else
                    sp3.setDisplayedValues(AddProjectActivityConstants.VALORES_STRING_PLURAL);
                np4.setValue(pVulnerabilidades.get(3).getValorTiempo());
                sp4.setValue(pVulnerabilidades.get(3).getValorTipoTiempo());
                if (np4.getValue() == 1)
                    sp4.setDisplayedValues(AddProjectActivityConstants.VALORES_STRING_SINGULAR);
                else
                    sp4.setDisplayedValues(AddProjectActivityConstants.VALORES_STRING_PLURAL);
                np5.setValue(pVulnerabilidades.get(4).getValorTiempo());
                sp5.setValue(pVulnerabilidades.get(4).getValorTipoTiempo());
                if (np5.getValue() == 1)
                    sp5.setDisplayedValues(AddProjectActivityConstants.VALORES_STRING_SINGULAR);
                else
                    sp5.setDisplayedValues(AddProjectActivityConstants.VALORES_STRING_PLURAL);

                tv1.setText(df.format(pVulnerabilidades.get(0).getValor()).toString());
                tv2.setText(df.format(pVulnerabilidades.get(1).getValor()).toString());
                tv3.setText(df.format(pVulnerabilidades.get(2).getValor()).toString());
                tv4.setText(df.format(pVulnerabilidades.get(3).getValor()).toString());
                tv5.setText(df.format(pVulnerabilidades.get(4).getValor()).toString());

                cbt2_1.setChecked(pVulnerabilidades.get(0).getActivado());
                cbt2_2.setChecked(pVulnerabilidades.get(1).getActivado());
                cbt2_3.setChecked(pVulnerabilidades.get(2).getActivado());
                cbt2_4.setChecked(pVulnerabilidades.get(3).getActivado());
                cbt2_5.setChecked(pVulnerabilidades.get(4).getActivado());

            }
        }
    }

    private void tablaVulnerabilidad (View view, List<ParametrizacionVulnerabilidad> pVulnerabilidades) {
        // Listeners para Vulnerabilidad
        NumberPicker numberPicker2_1 = (NumberPicker) view.findViewById(R.id.numberPicker1_1Table2);
        NumberPicker stringPicker2_1 = (NumberPicker) view.findViewById(R.id.stringPicker1_2Table2);
        NumberPicker numberPicker2_2 = (NumberPicker) view.findViewById(R.id.numberPicker2_1Table2);
        NumberPicker stringPicker2_2 = (NumberPicker) view.findViewById(R.id.stringPicker2_2Table2);
        NumberPicker numberPicker2_3 = (NumberPicker) view.findViewById(R.id.numberPicker3_1Table2);
        NumberPicker stringPicker2_3 = (NumberPicker) view.findViewById(R.id.stringPicker3_2Table2);
        NumberPicker numberPicker2_4 = (NumberPicker) view.findViewById(R.id.numberPicker4_1Table2);
        NumberPicker stringPicker2_4 = (NumberPicker) view.findViewById(R.id.stringPicker4_2Table2);
        NumberPicker numberPicker2_5 = (NumberPicker) view.findViewById(R.id.numberPicker5_1Table2);
        NumberPicker stringPicker2_5 = (NumberPicker) view.findViewById(R.id.stringPicker5_2Table2);
        valoresStringPicker = AddProjectActivityConstants.VALORES_STRING_SINGULAR;


        TextView tv2_1 = (TextView) view.findViewById(R.id.TextViewTable2Row1_3);
        TextView tv2_2 = (TextView) view.findViewById(R.id.TextViewTable2Row2_3);
        TextView tv2_3 = (TextView) view.findViewById(R.id.TextViewTable2Row3_3);
        TextView tv2_4 = (TextView) view.findViewById(R.id.TextViewTable2Row4_3);
        TextView tv2_5 = (TextView) view.findViewById(R.id.TextViewTable2Row5_3);

        numberPicker2_1.setOnValueChangedListener(new numberPickerChangeListener(numberPicker2_1, stringPicker2_1,
                AddProjectActivityConstants.VALORES_STRING_SINGULAR,
                AddProjectActivityConstants.VALORES_STRING_PLURAL, tv2_1));

        stringPicker2_1.setOnValueChangedListener(new stringPickerChangeListener(stringPicker2_1,numberPicker2_1,tv2_1));

        numberPicker2_2.setOnValueChangedListener(new numberPickerChangeListener(numberPicker2_2, stringPicker2_2,
                AddProjectActivityConstants.VALORES_STRING_SINGULAR,
                AddProjectActivityConstants.VALORES_STRING_PLURAL, tv2_2));

        stringPicker2_2.setOnValueChangedListener(new stringPickerChangeListener(stringPicker2_2,numberPicker2_2,tv2_2));

        numberPicker2_3.setOnValueChangedListener(new numberPickerChangeListener(numberPicker2_3, stringPicker2_3,
                AddProjectActivityConstants.VALORES_STRING_SINGULAR,
                AddProjectActivityConstants.VALORES_STRING_PLURAL, tv2_3));

        stringPicker2_3.setOnValueChangedListener(new stringPickerChangeListener(stringPicker2_3,numberPicker2_3,tv2_3));

        numberPicker2_4.setOnValueChangedListener(new numberPickerChangeListener(numberPicker2_4, stringPicker2_4,
                AddProjectActivityConstants.VALORES_STRING_SINGULAR,
                AddProjectActivityConstants.VALORES_STRING_PLURAL, tv2_4));

        stringPicker2_4.setOnValueChangedListener(new stringPickerChangeListener(stringPicker2_4,numberPicker2_4,tv2_4));

        numberPicker2_5.setOnValueChangedListener(new numberPickerChangeListener(numberPicker2_5, stringPicker2_5,
                AddProjectActivityConstants.VALORES_STRING_SINGULAR,
                AddProjectActivityConstants.VALORES_STRING_PLURAL, tv2_5));

        stringPicker2_5.setOnValueChangedListener(new stringPickerChangeListener(stringPicker2_5,numberPicker2_5,tv2_5));

        tv2_1.addTextChangedListener(new vulnerabilidadTextWatcher(tv2_1, tv2_2, tv2_3,
                tv2_4, tv2_5));
        tv2_2.addTextChangedListener(new vulnerabilidadTextWatcher(tv2_1, tv2_2, tv2_3,
                tv2_4, tv2_5));
        tv2_3.addTextChangedListener(new vulnerabilidadTextWatcher(tv2_1, tv2_2, tv2_3,
                tv2_4, tv2_5));
        tv2_4.addTextChangedListener(new vulnerabilidadTextWatcher(tv2_1, tv2_2, tv2_3,
                tv2_4, tv2_5));
        tv2_5.addTextChangedListener(new vulnerabilidadTextWatcher(tv2_1, tv2_2, tv2_3,
                tv2_4, tv2_5));

        CheckBox cbt2_1 = (CheckBox) view.findViewById(R.id.checkBoxTable2Row1);
        CheckBox cbt2_2 = (CheckBox) view.findViewById(R.id.checkBoxTable2Row2);
        CheckBox cbt2_3 = (CheckBox) view.findViewById(R.id.checkBoxTable2Row3);
        CheckBox cbt2_4 = (CheckBox) view.findViewById(R.id.checkBoxTable2Row4);
        CheckBox cbt2_5 = (CheckBox) view.findViewById(R.id.checkBoxTable2Row5);

        annadirControlCheckBoxes(cbt2_1, cbt2_2, cbt2_3, cbt2_4, cbt2_5);
        cargarValoresTablaVulnerabilidad(pVulnerabilidades, numberPicker2_1, stringPicker2_1, tv2_1,
                cbt2_1, numberPicker2_2, stringPicker2_2, tv2_2, cbt2_2, numberPicker2_3,
                stringPicker2_3, tv2_3, cbt2_3, numberPicker2_4, stringPicker2_4, tv2_4, cbt2_4,
                numberPicker2_5, stringPicker2_5 ,tv2_5, cbt2_5);
    }


    private void cargarValoresTablaImpactoYControlSeguridad(NumberPicker np1, NumberPicker np2,
                                                            NumberPicker np3, NumberPicker np4,
                                                            NumberPicker np5, Integer np1Value,
                                                            Integer np2Value, Integer np3Value,
                                                            Integer np4Value, Integer np5Value,
                                                            CheckBox cb1, CheckBox cb2,
                                                            CheckBox cb3, CheckBox cb4,
                                                            CheckBox cb5, Boolean cb1Value,
                                                            Boolean cb2Value, Boolean cb3Value,
                                                            Boolean cb4Value, Boolean cb5Value) {

        np1.setMaxValue(AddProjectActivityConstants.VALOR_NP_MAXIMO);
        np1.setValue(np1Value);
        np1.setMinValue(np2Value+1);

        np2.setMaxValue(np1Value-1);
        np2.setValue(np2Value);
        np2.setMinValue(np3Value+1);

        np3.setMaxValue(np2Value-1);
        np3.setValue(np3Value);
        np3.setMinValue(np4Value+1);

        np4.setMaxValue(np3Value-1);
        np4.setValue(np4Value);
        np4.setMinValue(np5Value+1);

        np5.setMaxValue(np4Value-1);
        np5.setValue(np5Value);
        np5.setMinValue(AddProjectActivityConstants.VALOR_NP_MINIMO);

        cb1.setChecked(cb1Value);
        cb2.setChecked(cb2Value);
        cb3.setChecked(cb3Value);
        cb4.setChecked(cb4Value);
        cb5.setChecked(cb5Value);
    }



    private void tablaImpacto (View view, List<ParametrizacionImpacto> pImpactos) {
        //Listeners para Parametrizar impacto
        NumberPicker numberPicker3_1 = (NumberPicker) view.findViewById(R.id.numberPicker1Table3);
        NumberPicker numberPicker3_2 = (NumberPicker) view.findViewById(R.id.numberPicker2Table3);
        NumberPicker numberPicker3_3 = (NumberPicker) view.findViewById(R.id.numberPicker3Table3);
        NumberPicker numberPicker3_4 = (NumberPicker) view.findViewById(R.id.numberPicker4Table3);
        NumberPicker numberPicker3_5 = (NumberPicker) view.findViewById(R.id.numberPicker5Table3);

        numberPicker3_1.setOnValueChangedListener(new MinAndMaxPercentageNumberPickerListener(null,
                numberPicker3_1, numberPicker3_2));

        numberPicker3_2.setOnValueChangedListener(new MinAndMaxPercentageNumberPickerListener(
                numberPicker3_1, numberPicker3_2, numberPicker3_3));

        numberPicker3_3.setOnValueChangedListener(new MinAndMaxPercentageNumberPickerListener(
                numberPicker3_2, numberPicker3_3, numberPicker3_4));

        numberPicker3_4.setOnValueChangedListener(new MinAndMaxPercentageNumberPickerListener(
                numberPicker3_3, numberPicker3_4, numberPicker3_5));

        numberPicker3_5.setOnValueChangedListener(new MinAndMaxPercentageNumberPickerListener(
                numberPicker3_4, numberPicker3_5, null));

        CheckBox cbt3_1 = (CheckBox) view.findViewById(R.id.checkBoxTable3Row1);
        CheckBox cbt3_2 = (CheckBox) view.findViewById(R.id.checkBoxTable3Row2);
        CheckBox cbt3_3 = (CheckBox) view.findViewById(R.id.checkBoxTable3Row3);
        CheckBox cbt3_4 = (CheckBox) view.findViewById(R.id.checkBoxTable3Row4);
        CheckBox cbt3_5 = (CheckBox) view.findViewById(R.id.checkBoxTable3Row5);

        annadirControlCheckBoxes(cbt3_1, cbt3_2, cbt3_3, cbt3_4, cbt3_5);

        if (pImpactos == null) {
            cargarValoresTablaImpactoYControlSeguridad(numberPicker3_1, numberPicker3_2,
                    numberPicker3_3, numberPicker3_4, numberPicker3_5,
                    AddProjectActivityConstants.VALOR_NP1_TABLA3,
                    AddProjectActivityConstants.VALOR_NP2_TABLA3,
                    AddProjectActivityConstants.VALOR_NP3_TABLA3,
                    AddProjectActivityConstants.VALOR_NP4_TABLA3,
                    AddProjectActivityConstants.VALOR_NP5_TABLA3, cbt3_1, cbt3_2, cbt3_3,
                    cbt3_4, cbt3_5, true, true, true, true,true);
        } else {
            if (pImpactos.size() == 5) {
                cargarValoresTablaImpactoYControlSeguridad(numberPicker3_1, numberPicker3_2,
                        numberPicker3_3, numberPicker3_4, numberPicker3_5,
                        pImpactos.get(0).getValor(),
                        pImpactos.get(1).getValor(),
                        pImpactos.get(2).getValor(),
                        pImpactos.get(3).getValor(),
                        pImpactos.get(4).getValor(), cbt3_1, cbt3_2, cbt3_3,
                        cbt3_4, cbt3_5, pImpactos.get(0).getActivado(), pImpactos.get(1).getActivado(),
                        pImpactos.get(2).getActivado(),pImpactos.get(3).getActivado(),
                        pImpactos.get(4).getActivado());
            }
        }
    }


    private void tablaControlSeguridad(View view, List<ParametrizacionControlSeguridad> pControlesSeguridad) {
        //Parametrizar control de seguridad
        NumberPicker numberPicker4_1 = (NumberPicker) view.findViewById(R.id.numberPicker1Table4);
        NumberPicker numberPicker4_2 = (NumberPicker) view.findViewById(R.id.numberPicker2Table4);
        NumberPicker numberPicker4_3 = (NumberPicker) view.findViewById(R.id.numberPicker3Table4);
        NumberPicker numberPicker4_4 = (NumberPicker) view.findViewById(R.id.numberPicker4Table4);
        NumberPicker numberPicker4_5 = (NumberPicker) view.findViewById(R.id.numberPicker5Table4);

        numberPicker4_1.setOnValueChangedListener(new MinAndMaxPercentageNumberPickerListener(null,
                numberPicker4_1, numberPicker4_2));

        numberPicker4_2.setOnValueChangedListener(new MinAndMaxPercentageNumberPickerListener(
                numberPicker4_1, numberPicker4_2, numberPicker4_3));

        numberPicker4_3.setOnValueChangedListener(new MinAndMaxPercentageNumberPickerListener(
                numberPicker4_2, numberPicker4_3, numberPicker4_4));

        numberPicker4_4.setOnValueChangedListener(new MinAndMaxPercentageNumberPickerListener(
                numberPicker4_3, numberPicker4_4, numberPicker4_5));

        numberPicker4_5.setOnValueChangedListener(new MinAndMaxPercentageNumberPickerListener(
                numberPicker4_4, numberPicker4_5, null));

        CheckBox cbt4_1 = (CheckBox) view.findViewById(R.id.checkBoxTable4Row1);
        CheckBox cbt4_2 = (CheckBox) view.findViewById(R.id.checkBoxTable4Row2);
        CheckBox cbt4_3 = (CheckBox) view.findViewById(R.id.checkBoxTable4Row3);
        CheckBox cbt4_4 = (CheckBox) view.findViewById(R.id.checkBoxTable4Row4);
        CheckBox cbt4_5 = (CheckBox) view.findViewById(R.id.checkBoxTable4Row5);

        annadirControlCheckBoxes(cbt4_1, cbt4_2, cbt4_3, cbt4_4, cbt4_5);

        if (pControlesSeguridad == null) {
            cargarValoresTablaImpactoYControlSeguridad(numberPicker4_1, numberPicker4_2,
                    numberPicker4_3, numberPicker4_4, numberPicker4_5,
                    AddProjectActivityConstants.VALOR_NP1_TABLA4,
                    AddProjectActivityConstants.VALOR_NP2_TABLA4,
                    AddProjectActivityConstants.VALOR_NP3_TABLA4,
                    AddProjectActivityConstants.VALOR_NP4_TABLA4,
                    AddProjectActivityConstants.VALOR_NP5_TABLA4, cbt4_1, cbt4_2, cbt4_3,
                    cbt4_4, cbt4_5, true, true, true, true,true);
        } else {
            if (pControlesSeguridad.size() == 5) {
                cargarValoresTablaImpactoYControlSeguridad(numberPicker4_1, numberPicker4_2,
                        numberPicker4_3, numberPicker4_4, numberPicker4_5,
                        pControlesSeguridad.get(0).getValor(),
                        pControlesSeguridad.get(1).getValor(),
                        pControlesSeguridad.get(2).getValor(),
                        pControlesSeguridad.get(3).getValor(),
                        pControlesSeguridad.get(4).getValor(), cbt4_1, cbt4_2, cbt4_3,
                        cbt4_4, cbt4_5, pControlesSeguridad.get(0).getActivado(),
                        pControlesSeguridad.get(1).getActivado(),
                        pControlesSeguridad.get(2).getActivado(),
                        pControlesSeguridad.get(3).getActivado(),
                        pControlesSeguridad.get(4).getActivado());
            }
        }
    }



    private class textWatcherInRange implements TextWatcher {
        EditText et;
        EditText identicalEt;

        EditText et1_1;
        EditText et1_2;
        EditText et1_3;
        EditText et2_1;
        EditText et2_2;
        EditText et2_3;
        EditText et3_1;
        EditText et3_2;
        EditText et3_3;
        EditText et4_1;
        EditText et4_2;
        EditText et4_3;
        EditText et5_1;
        EditText et5_2;
        EditText et5_3;

        public textWatcherInRange(EditText et, EditText identicalEt, EditText et1_1, EditText et1_2,
                                  EditText et1_3, EditText et2_1, EditText et2_2, EditText et2_3,
                                  EditText et3_1, EditText et3_2, EditText et3_3, EditText et4_1,
                                  EditText et4_2, EditText et4_3, EditText et5_1, EditText et5_2,
                                  EditText et5_3) {
            this.et = et;
            this.identicalEt = identicalEt;
            this.et1_1 = et1_1;
            this.et1_2 = et1_2;
            this.et1_3 = et1_3;
            this.et2_1 = et2_1;
            this.et2_2 = et2_2;
            this.et2_3 = et2_3;
            this.et3_1 = et3_1;
            this.et3_2 = et3_2;
            this.et3_3 = et3_3;
            this.et4_1 = et4_1;
            this.et4_2 = et4_2;
            this.et4_3 = et4_3;
            this.et5_1 = et5_1;
            this.et5_2 = et5_2;
            this.et5_3 = et5_3;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String textToShow;
            if (overridingText) //|| s.toString().isEmpty() || s.toString() == "" || s == null
                return;

            posicionCursor = start + count;

            overridingText = true;
            if (identicalEt != null) {
                identicalEt.setText(s.toString());
                if (s.length() <= posicionCursor)
                    identicalEt.setSelection(posicionCursor);
                else
                    identicalEt.setSelection(identicalEt.length());
            }
            overridingText = false;

            checkEt(et1_1, et1_2, et2_2, et3_2, et4_2, et5_2, 5, null);
            checkEt(et1_2, et1_1, et2_2, et3_2, et4_2, et5_2, 4, et2_1);
            checkEt(et2_2, et1_1, et1_2, et3_2, et4_2, et5_2, 3, et3_1);
            checkEt(et3_2, et1_1, et1_2, et2_2, et4_2, et5_2, 2, et4_1);
            checkEt(et4_2, et1_1, et1_2, et2_2, et3_2, et5_2, 1, et5_1);
            checkEt(et5_2, et1_1, et1_2, et2_2, et3_2, et4_2, 0, null);

            checkRango(et1_3, et1_1, et1_2);
            checkRango(et2_3, et2_1, et2_2);
            checkRango(et3_3, et3_1, et3_2);
            checkRango(et4_3, et4_1, et4_2);
            checkRango(et5_3, et5_1, et5_2);

        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        private void checkEt(EditText et, EditText et1Compare,
                             EditText et2Compare, EditText et3Compare, EditText et4Compare,
                             EditText et5Compare, Integer numberOfLowerElements, EditText identicalEt) {
            Boolean flagClean = true;
            if (et.getText().toString().isEmpty()) {
                et.setError(et.getError());
                return;
            }

            switch (numberOfLowerElements) {
                case 5:
                    if (!et1Compare.getText().toString().isEmpty() && Long.parseLong(et.getText().toString().replace(".", "")) <
                            Long.parseLong(et1Compare.getText().toString().replace(".", ""))) {
                        et.setError(AddProjectActivityConstants.ERROR_NUMERO_MAYOR_A + et1Compare.getText().toString().replace(".", ""));
                        flagClean = false;
                        break;
                    }
                case 4:
                    if (!et2Compare.getText().toString().isEmpty() && Long.parseLong(et.getText().toString().replace(".", "")) <
                            Long.parseLong(et2Compare.getText().toString().replace(".", ""))) {
                        et.setError(AddProjectActivityConstants.ERROR_NUMERO_MAYOR_A + et2Compare.getText().toString().replace(".", ""));
                        flagClean = false;
                        break;
                    }
                case 3:
                    if (!et3Compare.getText().toString().isEmpty() && Long.parseLong(et.getText().toString().replace(".", "")) <
                            Long.parseLong(et3Compare.getText().toString().replace(".", ""))) {
                        et.setError(AddProjectActivityConstants.ERROR_NUMERO_MAYOR_A + et3Compare.getText().toString().replace(".", ""));
                        flagClean = false;
                        break;
                    }
                case 2:
                    if (!et4Compare.getText().toString().isEmpty() && Long.parseLong(et.getText().toString().replace(".", "")) <
                            Long.parseLong(et4Compare.getText().toString().replace(".", ""))) {
                        et.setError(AddProjectActivityConstants.ERROR_NUMERO_MAYOR_A + et4Compare.getText().toString().replace(".", ""));
                        flagClean = false;
                        break;
                    }
                case 1:
                    if (!et5Compare.getText().toString().isEmpty() && Long.parseLong(et.getText().toString().replace(".", "")) <
                            Long.parseLong(et5Compare.getText().toString().replace(".", ""))) {
                        et.setError(AddProjectActivityConstants.ERROR_NUMERO_MAYOR_A + et5Compare.getText().toString().replace(".", ""));
                        flagClean = false;
                        break;
                    }
                case 0:
                    break;
                default:
                    break;
            }

            switch (5 - numberOfLowerElements) {
                case 5:
                    if (!et5Compare.getText().toString().isEmpty() && Long.parseLong(et.getText().toString().replace(".", "")) >
                            Long.parseLong(et5Compare.getText().toString().replace(".", ""))) {
                        et.setError(AddProjectActivityConstants.ERROR_NUMERO_MENOR_A + et5Compare.getText().toString().replace(".", ""));
                        flagClean = false;
                        break;
                    }
                case 4:
                    if (!et4Compare.getText().toString().isEmpty() && Long.parseLong(et.getText().toString().replace(".", "")) >
                            Long.parseLong(et4Compare.getText().toString().replace(".", ""))) {
                        et.setError(AddProjectActivityConstants.ERROR_NUMERO_MENOR_A + et4Compare.getText().toString().replace(".", ""));
                        flagClean = false;
                        break;
                    }
                case 3:
                    if (!et3Compare.getText().toString().isEmpty() && Long.parseLong(et.getText().toString().replace(".", "")) >
                            Long.parseLong(et3Compare.getText().toString().replace(".", ""))) {
                        et.setError(AddProjectActivityConstants.ERROR_NUMERO_MENOR_A + et3Compare.getText().toString().replace(".", ""));
                        flagClean = false;
                        break;
                    }
                case 2:
                    if (!et2Compare.getText().toString().isEmpty() && Long.parseLong(et.getText().toString().replace(".", "")) >
                            Long.parseLong(et2Compare.getText().toString().replace(".", ""))) {
                        et.setError(AddProjectActivityConstants.ERROR_NUMERO_MENOR_A + et2Compare.getText().toString().replace(".", ""));
                        flagClean = false;
                        break;
                    }
                case 1:
                    if (!et1Compare.getText().toString().isEmpty() && Long.parseLong(et.getText().toString().replace(".", "")) >
                            Long.parseLong(et1Compare.getText().toString().replace(".", ""))) {
                        et.setError(AddProjectActivityConstants.ERROR_NUMERO_MENOR_A + et1Compare.getText().toString().replace(".", ""));
                        flagClean = false;
                        break;
                    }
                case 0:
                    break;
                default:
                    break;
            }
            if (flagClean) {
                et.setError(null);
            }

            if (identicalEt != null) {
                identicalEt.setError(et.getError());
            }
        }

        private void checkRango(EditText etPrimario, EditText etRangoSuperior, EditText etRangoInferior) {
            if (etPrimario.getText().toString().isEmpty() || etRangoSuperior.getText().toString()
                    .isEmpty() || etRangoInferior.getText().toString().isEmpty()) {
                etPrimario.setError(null);
                return;
            }
            Long etPrimarioLong = Long.parseLong(etPrimario.getText().toString().replace(".", ""));
            if (etPrimarioLong > Long.parseLong(etRangoSuperior.getText().toString().replace(".", ""))
                    || etPrimarioLong < Long.parseLong(etRangoInferior.getText().toString().replace(".", ""))) {
                etPrimario.setError(AddProjectActivityConstants.ERROR_NUMERO_COMPRENDIDO);
            } else {
                etPrimario.setError(null);
            }
        }
    }

    private class textWatcherOutOfRange1 implements TextWatcher {

        private EditText etPrimario;
        private EditText etRangoSuperior;
        private EditText etRangoInferior;

        public textWatcherOutOfRange1(EditText etPrimario, EditText etRangoSuperior,
                                      EditText etRangoInferior) {

            this.etPrimario = etPrimario;
            this.etRangoSuperior = etRangoSuperior;
            this.etRangoInferior = etRangoInferior;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (etPrimario.getText().toString().isEmpty()) {
                etPrimario.setError(null);
                return;
            }
            Long etPrimarioLong = Long.parseLong(etPrimario.getText().toString().replace(".", ""));

            if (!etRangoSuperior.getText().toString().isEmpty() &&
                    !etRangoInferior.getText().toString().isEmpty()) {
                if (etPrimarioLong > Long.parseLong(etRangoSuperior.getText().toString().replace(".", ""))
                        || etPrimarioLong < Long.parseLong(etRangoInferior.getText().toString().replace(".", ""))) {
                    etPrimario.setError(AddProjectActivityConstants.ERROR_NUMERO_COMPRENDIDO);
                } else {
                    etPrimario.setError(null);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }


    private void formatearEditText(EditText editText1, EditText editText2, Boolean editText1HasFocus) {

        if (editText1.getText().toString() == "" || editText1 == null || editText1.getText().toString().isEmpty())
            return;
        if (!editText1HasFocus) {
            editText1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});
            if (editText2 != null)
                editText2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});
            String str = editText1.getText().toString();
            editText1.setText(format.format(Long.parseLong(str.replace(".", ""))));
        } else {
            editText1.setText(editText1.getText().toString().replace(".", ""));
            editText1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7)});
            if (editText2 != null)
                editText2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7)});
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.TxtInputTable1Row1_1:
                EditText et1_1 = (EditText) v.findViewById(R.id.TxtInputTable1Row1_1);
                formatearEditText(et1_1, null, hasFocus);
                break;

            case R.id.TxtInputTable1Row1_2:
                EditText et1_2 = (EditText) v.findViewById(R.id.TxtInputTable1Row1_2);
                EditText et2_1_identic = (EditText) getView().findViewById(R.id.TxtInputTable1Row2_1);
                formatearEditText(et1_2, et2_1_identic, hasFocus);
                break;

            case R.id.TxtInputTable1Row1_3:
                EditText et1_3 = (EditText) v.findViewById(R.id.TxtInputTable1Row1_3);
                formatearEditText(et1_3, null, hasFocus);
                break;

            case R.id.TxtInputTable1Row2_1:
                EditText et2_1 = (EditText) v.findViewById(R.id.TxtInputTable1Row2_1);
                EditText et1_2_identic = (EditText) getView().findViewById(R.id.TxtInputTable1Row1_2);
                formatearEditText(et2_1, et1_2_identic, hasFocus);
                break;

            case R.id.TxtInputTable1Row2_2:
                EditText et2_2 = (EditText) v.findViewById(R.id.TxtInputTable1Row2_2);
                EditText et3_1_identic = (EditText) getView().findViewById(R.id.TxtInputTable1Row3_1);
                formatearEditText(et2_2, et3_1_identic, hasFocus);
                break;

            case R.id.TxtInputTable1Row2_3:
                EditText et2_3 = (EditText) v.findViewById(R.id.TxtInputTable1Row2_3);
                formatearEditText(et2_3, null, hasFocus);
                break;

            case R.id.TxtInputTable1Row3_1:
                EditText et3_1 = (EditText) v.findViewById(R.id.TxtInputTable1Row3_1);
                EditText et2_2_identic = (EditText) getView().findViewById(R.id.TxtInputTable1Row2_2);
                formatearEditText(et3_1, et2_2_identic, hasFocus);
                break;

            case R.id.TxtInputTable1Row3_2:
                EditText et3_2 = (EditText) v.findViewById(R.id.TxtInputTable1Row3_2);
                EditText et4_1_identic = (EditText) getView().findViewById(R.id.TxtInputTable1Row4_1);
                formatearEditText(et3_2, et4_1_identic, hasFocus);
                break;

            case R.id.TxtInputTable1Row3_3:
                EditText et3_3 = (EditText) v.findViewById(R.id.TxtInputTable1Row3_3);
                formatearEditText(et3_3, null, hasFocus);
                break;

            case R.id.TxtInputTable1Row4_1:
                EditText et4_1 = (EditText) v.findViewById(R.id.TxtInputTable1Row4_1);
                EditText et3_2_identic = (EditText) getView().findViewById(R.id.TxtInputTable1Row3_2);
                formatearEditText(et4_1, et3_2_identic, hasFocus);
                break;

            case R.id.TxtInputTable1Row4_2:
                EditText et4_2 = (EditText) v.findViewById(R.id.TxtInputTable1Row4_2);
                EditText et5_1_identic = (EditText) getView().findViewById(R.id.TxtInputTable1Row5_1);
                formatearEditText(et4_2, et5_1_identic, hasFocus);
                break;

            case R.id.TxtInputTable1Row4_3:
                EditText et4_3 = (EditText) v.findViewById(R.id.TxtInputTable1Row4_3);
                formatearEditText(et4_3, null, hasFocus);
                break;

            case R.id.TxtInputTable1Row5_1:
                EditText et5_1 = (EditText) v.findViewById(R.id.TxtInputTable1Row5_1);
                EditText et4_2_identic = (EditText) getView().findViewById(R.id.TxtInputTable1Row4_2);
                formatearEditText(et5_1, et4_2_identic, hasFocus);
                break;

            case R.id.TxtInputTable1Row5_2:
                EditText et5_2 = (EditText) v.findViewById(R.id.TxtInputTable1Row5_2);
                formatearEditText(et5_2, null, hasFocus);
                break;

            case R.id.TxtInputTable1Row5_3:
                EditText et5_3 = (EditText) v.findViewById(R.id.TxtInputTable1Row5_3);
                formatearEditText(et5_3, null, hasFocus);
                break;
        }
    }


    private class checkCheckBoxClick implements View.OnClickListener {

        CheckBox cb1;
        CheckBox cb2;
        CheckBox cb3;
        CheckBox cb4;
        CheckBox cb5;

        public checkCheckBoxClick(CheckBox cb1, CheckBox cb2, CheckBox cb3, CheckBox cb4, CheckBox cb5) {
            this.cb1 = cb1;
            this.cb2 = cb2;
            this.cb3 = cb3;
            this.cb4 = cb4;
            this.cb5 = cb5;
        }

        @Override
        public void onClick(View v) {
            Integer checkedBoxes = 0;
            if (cb1.isChecked())
                    checkedBoxes++;

            if (cb2.isChecked())
                checkedBoxes++;

            if (cb3.isChecked())
                checkedBoxes++;

            if (cb4.isChecked())
                checkedBoxes++;

            if (cb5.isChecked())
                checkedBoxes++;

            if (checkedBoxes < 3) {
                Toast.makeText(getContext(), AddProjectActivityConstants.ERROR_MIN_ACTIVADOS,
                        Toast.LENGTH_SHORT).show();
                ((CheckBox) v).setChecked(true);
            }
        }
    }


    private class numberPickerChangeListener implements NumberPicker.OnValueChangeListener {

        private NumberPicker numberPicker;
        private NumberPicker stringPicker;
        private String[] valoresStringPickerSingular;
        private String[] valoresStringPickerPlural;
        private TextView textView;

        public numberPickerChangeListener(NumberPicker numberPicker, NumberPicker stringPicker,
                                          String[] valoresStringPickerSingular,
                                          String[] valoresStringPickerPlural, TextView textView) {
            this.numberPicker = numberPicker;
            this.stringPicker = stringPicker;
            this.valoresStringPickerSingular = valoresStringPickerSingular;
            this.valoresStringPickerPlural = valoresStringPickerPlural;
            this.textView = textView;
        }

        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            Double op;
            DecimalFormat df = new DecimalFormat("0.0000");
            String[] valoresStringPicker;
            if (newVal == 1) {
                valoresStringPicker = valoresStringPickerSingular;
            } else {
                valoresStringPicker = valoresStringPickerPlural;
            }

            if (stringPicker.getDisplayedValues()!=valoresStringPicker)
                    stringPicker.setDisplayedValues(valoresStringPicker);

            switch (stringPicker.getValue()) {
                case 0:
                    op = (1 / (double) numberPicker.getValue());
                    textView.setText(df.format(op));
                    break;
                case 1:
                    op = (1 / ((double) numberPicker.getValue() * AddProjectActivityConstants.DIAS_EN_SEMANA));
                    textView.setText(df.format(op));
                    break;
                case 2:
                    op = (1 / ((double) numberPicker.getValue() * AddProjectActivityConstants.DIAS_EN_MES));
                    textView.setText(df.format(op));
                    break;
                case 3:
                    op = (1 / ((double) numberPicker.getValue() * AddProjectActivityConstants.DIAS_EN_AÑO));
                    textView.setText(df.format(op));
                    break;
                default:
                    op = (1 / (double) numberPicker.getValue());
                    textView.setText(df.format(op));
                    break;

            }

        }
    }


    private class stringPickerChangeListener implements NumberPicker.OnValueChangeListener {

        private NumberPicker stringPicker;
        private NumberPicker numberPicker;
        private TextView textView;


        public stringPickerChangeListener(NumberPicker stringPicker, NumberPicker numberPicker,
                                          TextView textview) {
            this.stringPicker = stringPicker;
            this.numberPicker = numberPicker;
            this.textView = textview;
        }

        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            Double op;
            DecimalFormat df = new DecimalFormat();
            switch (newVal) {
                case 0:
                    numberPicker.setMinValue(1);
                    numberPicker.setMaxValue(100);
                    op = (1/(double)numberPicker.getValue());
                    textView.setText(df.format(op));
                    break;
                case 1:
                    numberPicker.setMinValue(1);
                    numberPicker.setMaxValue(60);
                    op = (1/((double)numberPicker.getValue()*AddProjectActivityConstants.DIAS_EN_SEMANA));
                    textView.setText(df.format(op));
                    break;
                case 2:
                    numberPicker.setMinValue(1);
                    numberPicker.setMaxValue(30);
                    op = (1/((double)numberPicker.getValue()*AddProjectActivityConstants.DIAS_EN_MES));
                    textView.setText(df.format(op));
                    break;
                case 3:
                    numberPicker.setMinValue(1);
                    numberPicker.setMaxValue(10);
                    op = (1/((double)numberPicker.getValue()*AddProjectActivityConstants.DIAS_EN_AÑO));
                    textView.setText(df.format(op));
                    break;
                default:
                    numberPicker.setMinValue(1);
                    numberPicker.setMaxValue(100);
                    op = (1/(double)numberPicker.getValue());
                    textView.setText(df.format(op));
                    break;
            }
        }
    }


    private class vulnerabilidadTextWatcher implements TextWatcher {

        private TextView tv1;
        private TextView tv2;
        private TextView tv3;
        private TextView tv4;
        private TextView tv5;

        public vulnerabilidadTextWatcher(TextView tv1, TextView tv2, TextView tv3, TextView tv4, TextView tv5) {
            this.tv1 = tv1;
            this.tv2 = tv2;
            this.tv3 = tv3;
            this.tv4 = tv4;
            this.tv5 = tv5;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkTv(tv1,tv1,tv2,tv3,tv4,tv5,4);
            checkTv(tv2,tv1,tv2,tv3,tv4,tv5,3);
            checkTv(tv3,tv1,tv2,tv3,tv4,tv5,2);
            checkTv(tv4,tv1,tv2,tv3,tv4,tv5,1);
            checkTv(tv5,tv1,tv2,tv3,tv4,tv5,0);

        }

        @Override
        public void afterTextChanged(Editable s) {

        }


        private void checkTv(TextView tvCompare, TextView tv1, TextView tv2, TextView tv3, TextView tv4, TextView tv5, Integer numberOfLowerElements) {
            Boolean cleanFlag = true;
            switch (numberOfLowerElements) {
                case 4:
                    if (Double.parseDouble(tvCompare.getText().toString().replace(",",".")) <= Double.parseDouble(tv2.getText().toString().replace(",","."))) {
                        cleanFlag = false;
                        tvCompare.setError(AddProjectActivityConstants.ERROR_NUMERO_MAYOR_A + tv2.getText().toString());
                        break;
                    }
                case 3:
                    if (Double.parseDouble(tvCompare.getText().toString().replace(",",".")) <= Double.parseDouble(tv3.getText().toString().replace(",","."))) {
                        cleanFlag = false;
                        tvCompare.setError(AddProjectActivityConstants.ERROR_NUMERO_MAYOR_A + tv3.getText().toString());
                        break;
                    }
                case 2:
                    if (Double.parseDouble(tvCompare.getText().toString().replace(",",".")) <= Double.parseDouble(tv4.getText().toString().replace(",","."))) {
                        cleanFlag = false;
                        tvCompare.setError(AddProjectActivityConstants.ERROR_NUMERO_MAYOR_A + tv4.getText().toString());
                        break;
                    }
                case 1:
                    if (Double.parseDouble(tvCompare.getText().toString().replace(",",".")) <= Double.parseDouble(tv5.getText().toString().replace(",","."))) {
                        cleanFlag = false;
                        tvCompare.setError(AddProjectActivityConstants.ERROR_NUMERO_MAYOR_A + tv5.getText().toString());
                        break;
                    }
                default:

            }

            switch (4 - numberOfLowerElements) {
                case 4:
                    if (Double.parseDouble(tvCompare.getText().toString().replace(",",".")) >= Double.parseDouble(tv4.getText().toString().replace(",","."))) {
                        cleanFlag = false;
                        tvCompare.setError(AddProjectActivityConstants.ERROR_NUMERO_MENOR_A + tv4.getText().toString());
                        break;
                    }
                case 3:
                    if (Double.parseDouble(tvCompare.getText().toString().replace(",",".")) >= Double.parseDouble(tv3.getText().toString().replace(",","."))) {
                        cleanFlag = false;
                        tvCompare.setError(AddProjectActivityConstants.ERROR_NUMERO_MENOR_A + tv3.getText().toString());
                        break;
                    }
                case 2:
                    if (Double.parseDouble(tvCompare.getText().toString().replace(",",".")) >= Double.parseDouble(tv2.getText().toString().replace(",","."))) {
                        cleanFlag = false;
                        tvCompare.setError(AddProjectActivityConstants.ERROR_NUMERO_MENOR_A + tv2.getText().toString());
                        break;
                    }
                case 1:
                    if (Double.parseDouble(tvCompare.getText().toString().replace(",",".")) >= Double.parseDouble(tv1.getText().toString().replace(",","."))) {
                        cleanFlag = false;
                        tvCompare.setError(AddProjectActivityConstants.ERROR_NUMERO_MENOR_A + tv1.getText().toString());
                        break;
                    }
                default:

            }

            if (cleanFlag) {
                tvCompare.setError(null);
            }
        }

    }

    private class MinAndMaxPercentageNumberPickerListener implements NumberPicker.OnValueChangeListener {

        private NumberPicker aboveModifiedNumberPicker;
        private NumberPicker modifiedNumberPicker;
        private NumberPicker underModifiedNumberPicker;

        public MinAndMaxPercentageNumberPickerListener(NumberPicker aboveModifiedNumberPicker, NumberPicker modifiedNumberPicker, NumberPicker underModifiedNumberPicker) {
            this.aboveModifiedNumberPicker = aboveModifiedNumberPicker;
            this.modifiedNumberPicker = modifiedNumberPicker;
            this.underModifiedNumberPicker = underModifiedNumberPicker;
        }

        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

            if (aboveModifiedNumberPicker != null)
                aboveModifiedNumberPicker.setMinValue(modifiedNumberPicker.getValue()+1);

            if (underModifiedNumberPicker != null)
                underModifiedNumberPicker.setMaxValue(modifiedNumberPicker.getValue()-1);

        }
    }


}