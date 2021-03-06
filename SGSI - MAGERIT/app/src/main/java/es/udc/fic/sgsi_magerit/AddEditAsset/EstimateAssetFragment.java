package es.udc.fic.sgsi_magerit.AddEditAsset;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import es.udc.fic.sgsi_magerit.AddEditProject.AddProjectActivityConstants;
import es.udc.fic.sgsi_magerit.Model.Asset.Asset;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelService;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.Model.Project.ProjectConstants;
import es.udc.fic.sgsi_magerit.Model.ProjectSizing.ProjectSizingConstants;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;

public class EstimateAssetFragment extends Fragment {

    private TextInputLayout tilNombreActivo;
    private EditText etNombreActivo;

    private Spinner spinnerValoracionDisponibilidad;
    private ArrayAdapter<String> spinnerAdapterValoracionDisponibilidad;

    private Spinner spinnerValoracionIntegridad;
    private ArrayAdapter<String> spinnerAdapterValoracionIntegridad;

    private Spinner spinnerValoracionConfidencialidad;
    private ArrayAdapter<String> spinnerAdapterValoracionConfidencialidad;

    private Spinner spinnerValoracionAutenticidad;
    private ArrayAdapter<String> spinnerAdapterValoracionAutenticidad;

    private Spinner spinnerValoracionTrazabilidad;
    private ArrayAdapter<String> spinnerAdapterValoracionTrazabilidad;

    private ModelService service;
    private Long idProyecto;
    private Long idActivo;
    private List<String> strParamActivo;

    public EstimateAssetFragment() {
        // Required empty public constructor
    }

    public static EstimateAssetFragment newInstance() {
        EstimateAssetFragment fragment = new EstimateAssetFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        if(args != null){
            this.idProyecto = args.getLong("idProyecto");
            this.idActivo = args.getLong("idActivo");
        }
        View view = inflater.inflate(R.layout.fragment_estimate_asset, container, false);
        service = new ModelServiceImpl(this.getContext(), GlobalConstants.DATABASE_NAME,1);
        List<Integer> idsParamActivo= service.obtenerParametrizacionesActivadas(
                idProyecto, ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_ACTIVO);
        strParamActivo = new ArrayList<String>();

        strParamActivo.add("Seleccione");

        for (Integer i:idsParamActivo) {
            strParamActivo.add(GlobalConstants.ID_TIPOS[i-1]);
        }

        spinnerValoracionDisponibilidad = (Spinner) view.findViewById(R.id.spinnerDisponibilidad);
        spinnerAdapterValoracionDisponibilidad =  new ArrayAdapter<String> (getContext(),
                android.R.layout.simple_spinner_item, strParamActivo);
        spinnerAdapterValoracionDisponibilidad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerValoracionDisponibilidad.setAdapter((SpinnerAdapter) spinnerAdapterValoracionDisponibilidad);

        spinnerValoracionIntegridad = (Spinner) view.findViewById(R.id.spinnerIntegridad);
        spinnerAdapterValoracionIntegridad =  new ArrayAdapter<String> (getContext(),
                android.R.layout.simple_spinner_item, strParamActivo);
        spinnerAdapterValoracionIntegridad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerValoracionIntegridad.setAdapter((SpinnerAdapter) spinnerAdapterValoracionIntegridad);

        spinnerValoracionConfidencialidad = (Spinner) view.findViewById(R.id.spinnerConfidencialidad);
        spinnerAdapterValoracionConfidencialidad =  new ArrayAdapter<String> (getContext(),
                android.R.layout.simple_spinner_item, strParamActivo);
        spinnerAdapterValoracionConfidencialidad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerValoracionConfidencialidad.setAdapter((SpinnerAdapter) spinnerAdapterValoracionConfidencialidad);

        spinnerValoracionAutenticidad = (Spinner) view.findViewById(R.id.spinnerAutenticidad);
        spinnerAdapterValoracionAutenticidad =  new ArrayAdapter<String> (getContext(),
                android.R.layout.simple_spinner_item, strParamActivo);
        spinnerAdapterValoracionAutenticidad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerValoracionAutenticidad.setAdapter((SpinnerAdapter) spinnerAdapterValoracionAutenticidad);

        spinnerValoracionTrazabilidad = (Spinner) view.findViewById(R.id.spinnerTrazabilidad);
        spinnerAdapterValoracionTrazabilidad =  new ArrayAdapter<String> (getContext(),
                android.R.layout.simple_spinner_item, strParamActivo);

        spinnerAdapterValoracionTrazabilidad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerValoracionTrazabilidad.setAdapter((SpinnerAdapter) spinnerAdapterValoracionTrazabilidad);

        tilNombreActivo = (TextInputLayout) view.findViewById(R.id.nombreActivoWrapper);
        etNombreActivo = (EditText) view.findViewById(R.id.nombreActivo);


        etNombreActivo.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!etNombreActivo.getText().toString().isEmpty() || hasFocus) {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)tilNombreActivo.getLayoutParams();
                    params.setMargins(0,45,0,0);
                    v.requestLayout();
                } else {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)tilNombreActivo.getLayoutParams();
                    params.setMargins(0,0,0,0);
                    v.requestLayout();
                }

            }
        });
        cargarDatosEdicion(idActivo, view);
        return view;
    }


    private void cargarDatosEdicion (Long idActivo, View view) {

        if (idActivo == GlobalConstants.NULL_ID_ACTIVO)
            return;

        Asset activo = null;
        try {
            activo = service.obtenerActivo(idActivo);
        } catch (ParseException e) {
            //NO debería saltar nunca
            Toast.makeText(getContext(), GlobalConstants.DATE_ERROR, Toast.LENGTH_LONG).show();
        }

        if (activo != null) {
            EditText etNombreActivo = (EditText) view.findViewById(R.id.nombreActivo);
            EditText etCodigoActivo = (EditText) view.findViewById(R.id.codigoActivo);
            EditText etResponsableActivo = (EditText) view.findViewById(R.id.responsableActivo);
            EditText etDescripcionActivo = (EditText) view.findViewById(R.id.descripcionActivo);
            EditText etUbicacionActivo = (EditText) view.findViewById(R.id.ubicacionActivo);
            //TODO: Solucionar el bug de forma que si no estan todos habilitados esta parte no funciona

            if (devuelveValorSpinner(activo.getIdValoracionDisp()).intValue() > 0)
                spinnerValoracionDisponibilidad.setSelection(strParamActivo.indexOf(GlobalConstants.ID_TIPOS[devuelveValorSpinner(activo.getIdValoracionDisp())-1]));
            else
                spinnerValoracionDisponibilidad.setSelection(0);

            if (devuelveValorSpinner(activo.getIdValoracionInt()).intValue() > 0)
                spinnerValoracionIntegridad.setSelection(strParamActivo.indexOf(GlobalConstants.ID_TIPOS[devuelveValorSpinner(activo.getIdValoracionInt())-1]));
            else
                spinnerValoracionIntegridad.setSelection(0);

            if (devuelveValorSpinner(activo.getIdValoracionConf()).intValue() > 0)
                spinnerValoracionConfidencialidad.setSelection(strParamActivo.indexOf(GlobalConstants.ID_TIPOS[devuelveValorSpinner(activo.getIdValoracionConf())-1]));
            else
                spinnerValoracionConfidencialidad.setSelection(0);

            if (devuelveValorSpinner(activo.getIdValoracionAut()).intValue() > 0)
                spinnerValoracionAutenticidad.setSelection(strParamActivo.indexOf(GlobalConstants.ID_TIPOS[devuelveValorSpinner(activo.getIdValoracionAut()-1)]));
            else
                spinnerValoracionAutenticidad.setSelection(0);

            if (devuelveValorSpinner(activo.getIdValoracionTraz()).intValue() > 0)
                spinnerValoracionTrazabilidad.setSelection(strParamActivo.indexOf(GlobalConstants.ID_TIPOS[devuelveValorSpinner(activo.getIdValoracionTraz()-1)]));
            else
                spinnerValoracionTrazabilidad.setSelection(0);
            // EJEMPLO ANTERIOR: spinnerValoracionTrazabilidad.setSelection(devuelveValorSpinner(activo.getIdValoracionTraz()));

            etNombreActivo.setText(activo.getNombreActivo());
            etCodigoActivo.setText(activo.getCodigoActivo());
            etResponsableActivo.setText(activo.getResponsableActivo());
            etDescripcionActivo.setText(activo.getDescripcionActivo());
            etUbicacionActivo.setText(activo.getUbicacionActivo());

        }
    }

    private Integer devuelveValorSpinner(Long i){

        if (i == null)
            return 0;
        else
            return i.intValue() + 1; //Añadimos uno porque es necesario quitarse el valor 0 del seleccionar
    }

}
