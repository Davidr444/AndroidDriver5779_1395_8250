package com.jct.davidandyair.androiddriver5779_1395_8250.controller.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.jct.davidandyair.androiddriver5779_1395_8250.R;
import com.jct.davidandyair.androiddriver5779_1395_8250.controller.adapters.ExpandableListAdapter;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.backend.FactoryBackend;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.backend.IBackend;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Drive;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Driver;

import java.util.List;

@SuppressLint("ValidFragment")
public class OrdersFragment extends Fragment {
    View view;
    ExpandableListView list;
    List<Drive> ordersList;
    ExpandableListAdapter adapter;
    Driver driver;

    public OrdersFragment(Driver driver){
        this.driver = driver;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_orders, container,false);
        list = (ExpandableListView) view.findViewById(R.id.orders_list_view);
        ordersList = FactoryBackend.getBackend().getUnhandledDrives(null);
        adapter = new ExpandableListAdapter(getContext(), ordersList, driver);
        list.setAdapter(adapter);

        return view;
    }
}
