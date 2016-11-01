package es.udc.fic.sgsi_magerit.AddEditThreat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.udc.fic.sgsi_magerit.Model.Asset.AssetDTO;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;

public class EstimateThreatFragment extends Fragment {

    private ListView lstOpcionesActivos;
    private List<AssetDTO> data;
    private ModelServiceImpl service;
    private ViewPager viewPager;
    private Long idProyecto;
    private AssetAdapter assetAdapter;

    public EstimateThreatFragment() {
    }

    public static EstimateThreatFragment newInstance() {
        EstimateThreatFragment fragment = new EstimateThreatFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estimate_threat_assets, container, false);
        service = new ModelServiceImpl(this.getActivity(), GlobalConstants.DATABASE_NAME, 1);
        Bundle args = getArguments();
        if (args != null) {
            this.idProyecto = args.getLong("idProyecto");
        }

        data = new ArrayList<AssetDTO>();
        data.addAll(leerFichero());
        System.out.println(data);
        assetAdapter = new AssetAdapter(this.getContext(), data);
        lstOpcionesActivos = (ListView) view.findViewById(R.id.LstOpciones);
        lstOpcionesActivos.setAdapter(assetAdapter);

        return view;
    }

    public class AssetAdapter extends ArrayAdapter<AssetDTO> {

        public AssetAdapter(Context context, List<AssetDTO> data) {
            super(context, android.R.layout.simple_list_item_single_choice, data);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());

            View item = inflater.inflate(R.layout.lisitem_threat_assets_estimation, null);
            TextView lblAssetName = (TextView) item.findViewById(R.id.asset);
            lblAssetName.setText("[" + data.get(position).getCodigoActivo() + "]" + " " + data.get(position).getNombreActivo());

            final ImageButton rc = (ImageButton) item.findViewById(R.id.icon);
            final TableLayout tb = (TableLayout) item.findViewById(R.id.tabla);
            tb.setVisibility(View.GONE);
            rc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tb.getVisibility() == View.GONE) {
                        rc.setImageResource(R.drawable.ic_less);
                        tb.setVisibility(View.VISIBLE);
                    } else {
                        rc.setImageResource(R.drawable.ic_more);
                        tb.setVisibility(View.GONE);
                    }
                }
            });
            return (item);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            Log.d("MyFragment", "Fragment is visible.");
            data.clear();
            data.addAll(leerFichero());
            assetAdapter.notifyDataSetChanged();
        } else
            Log.d("MyFragment", "Fragment is not visible.");
    }

    private List<AssetDTO> leerFichero() {
        List<AssetDTO> datos = new ArrayList<AssetDTO>();
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
        } catch (Exception ex) {
            Log.e("Ficheros", "Error al leer fichero desde memoria interna");
        }
        return datos;
    }

}
