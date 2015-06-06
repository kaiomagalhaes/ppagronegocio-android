package com.kty.kaio.ppagronegocio_android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class RecyclerViewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recycler_view_fragment, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.data_recycler_view);
        RecyclerViewFragmentAdapter recyclerViewFragmentAdapter = new RecyclerViewFragmentAdapter(getArguments().getStringArray("products"));
        recyclerView.setAdapter(recyclerViewFragmentAdapter);

        RecyclerView.LayoutManager relativeLayout = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(relativeLayout);
        return view;
    }

    public static RecyclerViewFragment newInstance(String[] products) {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putStringArray("products", products);
        fragment.setArguments(args);
        return fragment;
    }



}
