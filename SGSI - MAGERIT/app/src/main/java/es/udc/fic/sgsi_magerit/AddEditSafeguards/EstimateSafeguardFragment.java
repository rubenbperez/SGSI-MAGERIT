package es.udc.fic.sgsi_magerit.AddEditSafeguards;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.udc.fic.sgsi_magerit.R;

public class EstimateSafeguardFragment extends Fragment {

    public static EstimateSafeguardFragment newInstance() {
        EstimateSafeguardFragment fragment = new EstimateSafeguardFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_estimate_safeguard, container, false);
        return view;
    }
}
