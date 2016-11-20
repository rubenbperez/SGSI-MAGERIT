package es.udc.fic.sgsi_magerit.AddEditThreat;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import es.udc.fic.sgsi_magerit.Model.Asset.Asset;
import es.udc.fic.sgsi_magerit.Model.Asset.AssetDTO;
import es.udc.fic.sgsi_magerit.Model.ModelService.ModelServiceImpl;
import es.udc.fic.sgsi_magerit.Model.Threat.ThreatDTO;
import es.udc.fic.sgsi_magerit.R;
import es.udc.fic.sgsi_magerit.Util.GlobalConstants;


public class ThreatAssetsSelectionFragment extends Fragment {

    private ListView lstOpcionesActivos;
    private List<AssetDTO> data;
    private ModelServiceImpl service;
    private Long idProyecto;
    private Long idListaTipoAmenazaRecibido;
    private Long idTipoAmenazaRecibido;

    public ThreatAssetsSelectionFragment() {
    }

    public static ThreatAssetsSelectionFragment newInstance() {
        ThreatAssetsSelectionFragment fragment = new ThreatAssetsSelectionFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_threat_assets_selection, container, false);
        service = new ModelServiceImpl(this.getActivity(), GlobalConstants.DATABASE_NAME, 1);
        Bundle args = getArguments();
        if (args != null) {
            this.idProyecto = args.getLong("idProyecto");
            this.idListaTipoAmenazaRecibido = args.getLong("idListaTipoAmenaza", GlobalConstants.NULL_ID_LISTA_TIPO_AMENAZA);
            this.idTipoAmenazaRecibido = args.getLong("idTipoAmenaza", GlobalConstants.NULL_ID_LISTA_TIPO_AMENAZA);
        }

        if (idListaTipoAmenazaRecibido != GlobalConstants.NULL_ID_LISTA_TIPO_AMENAZA
                && idListaTipoAmenazaRecibido != GlobalConstants.NULL_ID_LISTA_TIPO_AMENAZA) {
            data = service.obtenerActivosConIdAmenaza(idListaTipoAmenazaRecibido,idTipoAmenazaRecibido,idProyecto);

        } else {
            data = service.obtenerActivos(idProyecto);
        }
        List<String> assetNames = new ArrayList<String>();

        for (AssetDTO asset: data) {
            assetNames.add("[" + asset.getCodigoActivo() + "]" + " "+ asset.getNombreActivo());
        }


        lstOpcionesActivos = (ListView) view.findViewById(R.id.LstOpciones);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_list_item_multiple_choice, assetNames);
        lstOpcionesActivos.setAdapter(adaptador);


        //Marcamos los elementos que tienen que estar marcados
        for (int i=0; i<data.size(); i++) {
            lstOpcionesActivos.setItemChecked(i,data.get(i).getChecked());
        }

        lstOpcionesActivos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                data.get(position).setChecked(lstOpcionesActivos.isItemChecked(position));

                try {
                    OutputStreamWriter fout =
                            new OutputStreamWriter(
                                    getActivity().openFileOutput("threats.tmp", Context.MODE_PRIVATE));

                    fout.write("");
                    String aux = "";
                    for (int i=0; i<data.size(); i++)
                    {
                        if (data.get(i).getChecked())
                            aux += (data.get(i).getIdActivo().toString()) + ",";
                    }
                    if (aux.endsWith(","))
                        aux = aux.substring(0, aux.length()-1);
                    fout.write(aux);
                    fout.close();

                } catch (Exception ex) {
                    Log.e("Ficheros", "Error al escribir fichero a memoria interna");
                }

            }
        });
        return view;
    }


}