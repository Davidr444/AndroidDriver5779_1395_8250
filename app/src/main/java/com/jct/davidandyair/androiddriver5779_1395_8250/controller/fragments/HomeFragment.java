package com.jct.davidandyair.androiddriver5779_1395_8250.controller.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jct.davidandyair.androiddriver5779_1395_8250.R;
import com.jct.davidandyair.androiddriver5779_1395_8250.controller.activities.MainActivity;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.backend.FactoryBackend;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.backend.IBackend;

public class HomeFragment extends Fragment {
    View view;
    String name;
    TextView nameTextView;
    TextView availableDrives;
    IBackend backend;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        findViews();
        MainActivity mainActivity = (MainActivity)getActivity();


        //Display the driver name
        name = mainActivity.driver.getFirstName();
        nameTextView.setText(name + getString(R.string.welcome_driver));
        //Display the num of available drives
        availableDrives.setText(" " + Integer.toString(backend.getUnhandledDrives(null).size()) + " ");


        return view;
    }

    private void findViews()
    {
        backend = FactoryBackend.getBackend();
        nameTextView = view.findViewById(R.id.welcome_driver);
        availableDrives = view.findViewById(R.id.num_of_available);
    }
}
