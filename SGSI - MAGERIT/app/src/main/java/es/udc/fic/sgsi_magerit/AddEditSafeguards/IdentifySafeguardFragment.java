package es.udc.fic.sgsi_magerit.AddEditSafeguards;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.udc.fic.sgsi_magerit.R;

public class IdentifySafeguardFragment extends Fragment {

    public static IdentifySafeguardFragment newInstance() {
        IdentifySafeguardFragment fragment = new IdentifySafeguardFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_identify_safeguard, container, false);
        return view;
    }

}
