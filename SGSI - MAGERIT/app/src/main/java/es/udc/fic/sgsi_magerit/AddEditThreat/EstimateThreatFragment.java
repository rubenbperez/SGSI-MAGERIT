package es.udc.fic.sgsi_magerit.AddEditThreat;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.udc.fic.sgsi_magerit.Model.Asset.AssetDTO;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.Model.ProjectSizing.ProjectSizingConstants;
import es.udc.fic.sgsi_magerit.Model.Threat.AssetThreatDTO;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;

public class EstimateThreatFragment extends Fragment {

    private ListView lstOpcionesActivos;
    private List<AssetThreatDTO> data;
    private ModelServiceImpl service;
    private Long idProyecto;
    private AssetAdapter assetAdapter;
    List<Integer> idsParamDegradacion;
    List<Integer> idsParamProbabilidad;
    List<String> strParamDegradacion;
    List<String> strParamProbabilidad;
    private Long idListaTipoAmenazaRecibido;
    private Long idTipoAmenazaRecibido;

    public List<AssetThreatDTO> getdata() {
        return data;
    }

    public static EstimateThreatFragment newInstance() {
        EstimateThreatFragment fragment = new EstimateThreatFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_estimate_threat_assets, container, false);
        service = new ModelServiceImpl(this.getActivity(), GlobalConstants.DATABASE_NAME, 1);
        Bundle args = getArguments();
        if (args != null) {
            this.idProyecto = args.getLong("idProyecto");
            this.idListaTipoAmenazaRecibido = args.getLong("idListaTipoAmenaza", GlobalConstants.NULL_ID_LISTA_TIPO_AMENAZA);
            this.idTipoAmenazaRecibido = args.getLong("idTipoAmenaza", GlobalConstants.NULL_ID_LISTA_TIPO_AMENAZA);
        }

        idsParamDegradacion = service.obtenerParametrizacionesActivadas(
                idProyecto, ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_IMPACTO);

        idsParamProbabilidad = service.obtenerParametrizacionesActivadas(
                idProyecto, ProjectSizingConstants.TABLE_NAME_PARAMETRIZACION_VULNERABILIDAD);

        strParamDegradacion = new ArrayList<String>();
        strParamDegradacion.add("Seleccione");
        for (Integer i:idsParamDegradacion) {
            strParamDegradacion.add(GlobalConstants.ID_TIPOS[i-1]);
        }

        strParamProbabilidad = new ArrayList<String>();
        strParamProbabilidad.add("Seleccione");
        for (Integer i:idsParamProbabilidad) {
            strParamProbabilidad.add(GlobalConstants.ID_TIPOS[i-1]);
        }



        if (idListaTipoAmenazaRecibido != GlobalConstants.NULL_ID_LISTA_TIPO_AMENAZA &&
                idTipoAmenazaRecibido != GlobalConstants.NULL_ID_LISTA_TIPO_AMENAZA) {
            /*List<AssetDTO> activosSeleccionados= service.obtenerActivosConIdAmenaza(idListaTipoAmenazaRecibido,idTipoAmenazaRecibido,idProyecto);
            List<Long> idsActivosSeleccionados = new ArrayList<Long>();
            for(AssetDTO asset : activosSeleccionados ) {
                idsActivosSeleccionados.add(asset.getIdActivo());
            }*/
            data = service.obtenerAmenazasConIdAmenaza(idListaTipoAmenazaRecibido, idTipoAmenazaRecibido, idProyecto);

        } else {
            data = new ArrayList<AssetThreatDTO>();
            data.addAll(leerFichero());
        }



        assetAdapter = new AssetAdapter(this.getContext(), data);
        lstOpcionesActivos = (ListView) view.findViewById(R.id.LstOpciones);
        lstOpcionesActivos.setAdapter(assetAdapter);

        return view;
    }

    public class AssetAdapter extends ArrayAdapter<AssetThreatDTO> {

        private Spinner spinnerDisp1;
        private ArrayAdapter<String> spinnerAdapterDisp1;
        private Spinner spinnerDisp2;
        private ArrayAdapter<String> spinnerAdapterDisp2;
        private Spinner spinnerInt1;
        private ArrayAdapter<String> spinnerAdapterInt1;
        private Spinner spinnerInt2;
        private ArrayAdapter<String> spinnerAdapterInt2;
        private Spinner spinnerConf1;
        private ArrayAdapter<String> spinnerAdapterConf1;
        private Spinner spinnerConf2;
        private ArrayAdapter<String> spinnerAdapterConf2;
        private Spinner spinnerAut1;
        private ArrayAdapter<String> spinnerAdapterAut1;
        private Spinner spinnerAut2;
        private ArrayAdapter<String> spinnerAdapterAut2;
        private Spinner spinnerTraz1;
        private ArrayAdapter<String> spinnerAdapterTraz1;
        private Spinner spinnerTraz2;
        private ArrayAdapter<String> spinnerAdapterTraz2;

        public AssetAdapter(Context context, List<AssetThreatDTO> data) {
            super(context, android.R.layout.simple_list_item_single_choice, data);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());

            View item = inflater.inflate(R.layout.lisitem_threat_assets_estimation, null);
            TextView lblAssetName = (TextView) item.findViewById(R.id.asset);
            lblAssetName.setText("[" + data.get(position).getCodigoActivo() + "]" + " " + data.get(position).getNombreActivo());

            final ImageButton rc = (ImageButton) item.findViewById(R.id.icon);
            final TableLayout tb = (TableLayout) item.findViewById(R.id.tabla);

            if (data.get(position).getVisible())
            {
                rc.setImageResource(R.drawable.ic_less);
                tb.setVisibility(View.VISIBLE);
            } else {
                rc.setImageResource(R.drawable.ic_more);
                tb.setVisibility(View.GONE);
            }
            rc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tb.getVisibility() == View.GONE) {
                        rc.setImageResource(R.drawable.ic_less);
                        tb.setVisibility(View.VISIBLE);
                        data.get(position).setVisible(true);
                    } else {
                        rc.setImageResource(R.drawable.ic_more);
                        tb.setVisibility(View.GONE);
                        data.get(position).setVisible(false);
                    }
                }
            });

            TextView tv = (TextView) item.findViewById(R.id.asset);

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tb.getVisibility() == View.GONE) {
                        rc.setImageResource(R.drawable.ic_less);
                        tb.setVisibility(View.VISIBLE);
                        data.get(position).setVisible(true);
                    } else {
                        rc.setImageResource(R.drawable.ic_more);
                        tb.setVisibility(View.GONE);
                        data.get(position).setVisible(false);
                    }
                }
            });

            spinnerDisp1 = (Spinner) item.findViewById(R.id.spinnerDisponibilidad);
            spinnerDisp2 = (Spinner) item.findViewById(R.id.spinnerDisponibilidad2);

            spinnerAdapterDisp1 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamDegradacion);
            spinnerAdapterDisp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDisp1.setAdapter((SpinnerAdapter) spinnerAdapterDisp1);
            spinnerDisp1.setSelection(data.get(position).getIdDegradacionDisponibilidad());
            spinnerDisp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    data.get(position).setIdDegradacionDisponibilidad(index);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerAdapterDisp2 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamProbabilidad);
            spinnerAdapterDisp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDisp2.setAdapter((SpinnerAdapter) spinnerAdapterDisp2);
            spinnerDisp2.setSelection(data.get(position).getIdProbabilidadDisponibilidad());
            spinnerDisp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    data.get(position).setIdProbabilidadDisponibilidad(index);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            spinnerInt1 = (Spinner) item.findViewById(R.id.spinnerIntegridad);
            spinnerInt2 = (Spinner) item.findViewById(R.id.spinnerIntegridad2);

            spinnerAdapterInt1 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamDegradacion);
            spinnerAdapterInt1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerInt1.setAdapter((SpinnerAdapter) spinnerAdapterInt1);
            spinnerInt1.setSelection(data.get(position).getIdDegradacionIntegridad());
            spinnerInt1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    data.get(position).setIdDegradacionIntegridad(index);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerAdapterInt2 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamProbabilidad);
            spinnerAdapterInt2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerInt2.setAdapter((SpinnerAdapter) spinnerAdapterInt2);
            spinnerInt2.setSelection(data.get(position).getIdProbabilidadIntegridad());
            spinnerInt2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    data.get(position).setIdProbabilidadIntegridad(index);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            spinnerConf1 = (Spinner) item.findViewById(R.id.spinnerConfidencialidad);
            spinnerConf2 = (Spinner) item.findViewById(R.id.spinnerConfidencialidad2);

            spinnerAdapterConf1 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamDegradacion);
            spinnerAdapterConf1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerConf1.setAdapter((SpinnerAdapter) spinnerAdapterConf1);
            spinnerConf1.setSelection(data.get(position).getIdDegradacionConfidencialidad());
            spinnerConf1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    data.get(position).setIdDegradacionConfidencialidad(index);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerAdapterConf2 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamProbabilidad);
            spinnerAdapterConf2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerConf2.setAdapter((SpinnerAdapter) spinnerAdapterConf2);
            spinnerConf2.setSelection(data.get(position).getIdProbabilidadConfidencialidad());
            spinnerConf2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    data.get(position).setIdProbabilidadConfidencialidad(index);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerAut1 = (Spinner) item.findViewById(R.id.spinnerAutenticidad);
            spinnerAut2 = (Spinner) item.findViewById(R.id.spinnerAutenticidad2);

            spinnerAdapterAut1 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamDegradacion);
            spinnerAdapterAut1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAut1.setAdapter((SpinnerAdapter) spinnerAdapterAut1);
            spinnerAut1.setSelection(data.get(position).getIdDegradacionAutenticidad());
            spinnerAut1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    data.get(position).setIdDegradacionAutenticidad(index);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerAdapterAut2 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamProbabilidad);
            spinnerAdapterAut2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAut2.setAdapter((SpinnerAdapter) spinnerAdapterAut2);
            spinnerAut2.setSelection(data.get(position).getIdProbabilidadAutenticidad());
            spinnerAut2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    data.get(position).setIdProbabilidadAutenticidad(index);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            spinnerTraz1 = (Spinner) item.findViewById(R.id.spinnerTrazabilidad);
            spinnerTraz2 = (Spinner) item.findViewById(R.id.spinnerTrazabilidad2);

            spinnerAdapterTraz1 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamDegradacion);
            spinnerAdapterTraz1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTraz1.setAdapter((SpinnerAdapter) spinnerAdapterTraz1);
            spinnerTraz1.setSelection(data.get(position).getIdDegradacionTrazabilidad());
            spinnerTraz1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    data.get(position).setIdDegradacionTrazabilidad(index);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerAdapterTraz2 =  new ArrayAdapter<String> (getContext(),
                    android.R.layout.simple_spinner_item, strParamProbabilidad);
            spinnerAdapterTraz2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTraz2.setAdapter((SpinnerAdapter) spinnerAdapterTraz2);
            spinnerTraz2.setSelection(data.get(position).getIdProbabilidadTrazabilidad());
            spinnerTraz2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    int index = parent.getSelectedItemPosition();
                    data.get(position).setIdProbabilidadTrazabilidad(index);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            return (item);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
          recargarInfo();
        } else
            Log.d("MyFragment", "Fragment is not visible.");
    }


    protected void recargarInfo() {
        Log.d("MyFragment", "Fragment is visible.");
        List<AssetThreatDTO> listaFichero = leerFichero();

        if (listaFichero.isEmpty()) {
            data.clear();
            assetAdapter.notifyDataSetChanged();
            return;
        }

        if (data.isEmpty()) {
            data.addAll(listaFichero);
            assetAdapter.notifyDataSetChanged();
            return;
        }

        List<AssetThreatDTO> listaBorrar = new ArrayList<AssetThreatDTO>();

        Boolean flag = false;
        for (AssetThreatDTO asset: data)
        {
            flag = false;
            for (AssetThreatDTO asset2: listaFichero) {

                if (asset.getIdActivo() == asset2.getIdActivo()) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                listaBorrar.add(asset);
            }
        }

        data.removeAll(listaBorrar);

        for (AssetThreatDTO asset2: listaFichero)
        {
            flag = false;
            for (AssetThreatDTO asset: data) {

                if (asset.getIdActivo() == asset2.getIdActivo()) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                data.add(asset2);
            }
        }
        assetAdapter.notifyDataSetChanged();
    }

    private List<AssetThreatDTO> leerFichero() {
        List<AssetDTO> datos = new ArrayList<AssetDTO>();
        List<AssetThreatDTO> datosValidos = new ArrayList<AssetThreatDTO>();
        try {
            BufferedReader fin =
                    new BufferedReader(
                            new InputStreamReader(
                                    getActivity().openFileInput("threats.tmp")));
            String texto = fin.readLine();
            fin.close();
            List<Long> ids = new ArrayList<Long>();
            List<String> items = Arrays.asList(texto.split(","));
            for (String s : items) ids.add(Long.valueOf(s));
            datos = service.obtenerActivosPorId(idProyecto, ids);

            for (AssetDTO a : datos)
            {
                AssetThreatDTO assetThreat = new AssetThreatDTO(null, a.getIdActivo(), a.getIdProyecto(),
                        a.getCodigoActivo(), a.getNombreActivo(), null, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false);
                datosValidos.add(assetThreat);
            }

        } catch (Exception ex) {
            Log.e("Ficheros", "Error al leer fichero desde memoria interna");
        }
        return datosValidos;
    }

}
