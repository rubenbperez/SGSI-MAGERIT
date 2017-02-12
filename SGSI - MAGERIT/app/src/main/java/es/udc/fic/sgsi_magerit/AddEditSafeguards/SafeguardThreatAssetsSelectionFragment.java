package es.udc.fic.sgsi_magerit.AddEditSafeguards;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.udc.fic.sgsi_magerit.R;

public class SafeguardThreatAssetsSelectionFragment extends Fragment {

    public static SafeguardThreatAssetsSelectionFragment newInstance() {
        SafeguardThreatAssetsSelectionFragment fragment = new SafeguardThreatAssetsSelectionFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_safeguard_threat_assets_selection, container, false);
        return view;
    }

}
