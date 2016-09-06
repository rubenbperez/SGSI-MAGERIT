package es.udc.fic.sgsi_magerit.AddEditAsset;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.udc.fic.sgsi_magerit.R;

public class IdentifyAssetTypesFragment extends Fragment {

    public IdentifyAssetTypesFragment() {
        // Required empty public constructor
    }

    public static IdentifyAssetTypesFragment newInstance() {
        IdentifyAssetTypesFragment fragment = new IdentifyAssetTypesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_identify_asset_types, container, false);
        return view;
    }

}
