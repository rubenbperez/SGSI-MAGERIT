package es.udc.fic.sgsi_magerit.AddEditThreat;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.udc.fic.sgsi_magerit.R;


public class IdentifyThreatFragment extends Fragment {

    public IdentifyThreatFragment() {
    }

    public static IdentifyThreatFragment newInstance() {
        IdentifyThreatFragment fragment = new IdentifyThreatFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_identify_threat, container, false);
        return view;
    }
}
