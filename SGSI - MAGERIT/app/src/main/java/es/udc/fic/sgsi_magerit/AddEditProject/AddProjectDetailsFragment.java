package es.udc.fic.sgsi_magerit.AddEditProject;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelService;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.Model.Project.Project;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddProjectDetailsFragment extends Fragment {

    Long idProyecto;
    private TextInputLayout tilNombreProyecto;
    private EditText etNombreProyecto;
    private TextInputLayout tilDirectorProyecto;
    private EditText etDirectorProyecto;
    private TextInputLayout tilDescripcionProyecto;
    private EditText etDescripcionProyecto;
    private TextInputLayout tilVersionProyecto;
    private EditText etVersionProyecto;
    private TextInputLayout tilFechaCreacionProyecto;
    private EditText etFechaCreacionProyecto;
    private CheckBox proyectoActivado;
    private Calendar fechaActual;
    private ModelService service;

    public static AddProjectDetailsFragment newInstance() {
        AddProjectDetailsFragment fragment = new AddProjectDetailsFragment();
        return fragment;
    }

    public AddProjectDetailsFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_addproject_details, container, false);
        tilNombreProyecto = (TextInputLayout) view.findViewById(R.id.nombreProyectoWrapper);
        tilNombreProyecto.setErrorEnabled(true);
        etNombreProyecto = (EditText) view.findViewById(R.id.nombreProyecto);

        etNombreProyecto.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && etNombreProyecto.getText().toString().isEmpty()) {
                    tilNombreProyecto.setError(AddProjectActivityConstants.ERROR_NOMBRE_PROYECTO);
                }
            }
        });

        tilDirectorProyecto = (TextInputLayout) view.findViewById(R.id.directorProyectoWrapper);
        tilDirectorProyecto.setErrorEnabled(true);
        etDirectorProyecto = (EditText) view.findViewById(R.id.directorProyecto);

        etDirectorProyecto.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && etDirectorProyecto.getText().toString().isEmpty()) {
                    tilDirectorProyecto.setError(AddProjectActivityConstants.ERROR_DIRECTOR_PROYECTO);
                }
            }
        });

        tilDescripcionProyecto= (TextInputLayout) view.findViewById(R.id.descripcionProyectoWrapper);
        tilDescripcionProyecto.setErrorEnabled(true);
        etDescripcionProyecto = (EditText) view.findViewById(R.id.descripcionProyecto);

        etDescripcionProyecto.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && etDescripcionProyecto.getText().toString().isEmpty()) {
                    tilDescripcionProyecto.setError(AddProjectActivityConstants.ERROR_DESC_PROYECTO);
                }
            }
        });

        tilVersionProyecto = (TextInputLayout) view.findViewById(R.id.versionProyectoWrapper);
        tilVersionProyecto.setErrorEnabled(true);
        etVersionProyecto = (EditText) view.findViewById(R.id.versionProyecto);

        etVersionProyecto.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && etVersionProyecto.getText().toString().isEmpty()) {
                    tilVersionProyecto.setError(AddProjectActivityConstants.ERROR_VERSION_PROYECTO);
                }
            }
        });

        tilFechaCreacionProyecto = (TextInputLayout) view.findViewById(R.id.fechaCreacionProyectoWrapper);
        etFechaCreacionProyecto = (EditText) view.findViewById(R.id.fechaCreacionProyecto);

        proyectoActivado = (CheckBox) view.findViewById(R.id.proyectoActivado);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpagerProject);

        CheckBox cbActivadoProyecto = (CheckBox) view.findViewById(R.id.proyectoActivado);

        SimpleDateFormat dateFormat = new SimpleDateFormat(GlobalConstants.DATE_FORMAT);

        if (idProyecto != 0) {
            try {
                Project project = service.obtenerDetallesProyecto(idProyecto);
                if (project != null) {
                    etNombreProyecto.setText(project.getNombre());
                    etDirectorProyecto.setText(project.getDirector());
                    etDescripcionProyecto.setText(project.getDescripcion());
                    etVersionProyecto.setText(project.getVersion());
                    etFechaCreacionProyecto.setText(dateFormat.format(project.getFechaCreacion().getTime()));
                    proyectoActivado.setChecked(project.getActivated());
                }
            } catch (ParseException e) {
                e.printStackTrace(); //TODO
            }
        } else {

            fechaActual = Calendar.getInstance();
            String fechaActuaStr = dateFormat.format(fechaActual.getTime());
            etFechaCreacionProyecto.setText(fechaActuaStr);
        }


        return (ScrollView) view;
    }

}
