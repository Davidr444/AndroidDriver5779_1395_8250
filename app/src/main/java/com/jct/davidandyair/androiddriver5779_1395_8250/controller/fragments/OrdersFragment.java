package com.jct.davidandyair.androiddriver5779_1395_8250.controller.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.jct.davidandyair.androiddriver5779_1395_8250.R;
import com.jct.davidandyair.androiddriver5779_1395_8250.controller.adapters.ExpandableListAdapter;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.backend.FactoryBackend;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.backend.FireBaseBackend;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.backend.IBackend;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Drive;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Driver;

import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment {
    View view;
    public static ExpandableListAdapter listAdapter;
    EditText distanceFilter;
    Driver driver;
    private ExpandableListView listView;
    public static List<Drive> driveList = new ArrayList<Drive>();

    public void getInstance(Driver driver){this.driver = driver;}
    public OrdersFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_orders,container,false);
        listView = (ExpandableListView) view.findViewById(R.id.orders_list_view);
        distanceFilter = (EditText) view.findViewById(R.id.FilterText);
        listView.setTextFilterEnabled(true);

        distanceFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count<before)
                    listAdapter.resetData();

                listAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        final Context context = this.getContext();
        FactoryBackend.getBackend().addNotifyDataChangeListener(new FireBaseBackend.NotifyDataChange<List<Drive>>() {
            @Override
            public void OnDataChanged(List<Drive> obj) {
                driveList = FactoryBackend.getBackend().getUnhandledDrives(null);
                listAdapter = new ExpandableListAdapter(context, driveList, driver);
                listView.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception exception) {

            }
        });

        return view;
    }
}
