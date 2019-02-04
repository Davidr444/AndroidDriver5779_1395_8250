package com.jct.davidandyair.androiddriver5779_1395_8250.controller.fragments;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jct.davidandyair.androiddriver5779_1395_8250.R;
import com.jct.davidandyair.androiddriver5779_1395_8250.controller.adapters.ListViewAdapter;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.backend.FactoryBackend;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Drive;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Driver;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class MyDrivesFragment extends Fragment {
    View view;
    public static List<Drive> finishedDrives;
    ListView finishedDrivesListView;
    ListViewAdapter listViewAdapter;
    Driver driver;

    public MyDrivesFragment(Driver driver){
        this.driver = driver;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_drives, container, false);
        finishedDrivesListView = (ListView) view.findViewById(R.id.List_View);
        finishedDrives = FactoryBackend.getBackend().getDriversDrives(driver, null);// todo: check
        listViewAdapter = new ListViewAdapter(finishedDrives, getContext(), driver);
        finishedDrivesListView.setAdapter(listViewAdapter);

        return view;
    }
}