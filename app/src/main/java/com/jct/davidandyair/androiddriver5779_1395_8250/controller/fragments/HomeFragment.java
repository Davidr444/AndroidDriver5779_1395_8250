package com.jct.davidandyair.androiddriver5779_1395_8250.controller.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jct.davidandyair.androiddriver5779_1395_8250.R;
import com.jct.davidandyair.androiddriver5779_1395_8250.controller.activities.MainActivity;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Driver;

public class HomeFragment extends Fragment {
    View view;
    String name;
    TextView nameTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        MainActivity mainActivity = (MainActivity)getActivity();

        //Display the driver name
        name = mainActivity.driver.getFirstName();
        nameTextView = view.findViewById(R.id.welcome_driver);
        nameTextView.setText(name + R.string.welcome_driver);

        return view;
    }
}
