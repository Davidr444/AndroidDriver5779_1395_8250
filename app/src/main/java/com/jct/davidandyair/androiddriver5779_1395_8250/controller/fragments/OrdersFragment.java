package com.jct.davidandyair.androiddriver5779_1395_8250.controller.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jct.davidandyair.androiddriver5779_1395_8250.R;

public class OrdersFragment extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_orders,container,false);
        return view;
    }
}
